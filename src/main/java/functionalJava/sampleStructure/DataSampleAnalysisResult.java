/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sampleStructure;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPNulls;
import LabPLANET.utilities.LPPlatform;
import databases.DataDataIntegrity;
import databases.Rdbms;
import functionalJava.audit.SampleAudit;
import functionalJava.materialSpec.DataSpec;
import functionalJava.parameter.Parameter;
import functionalJava.unitsOfMeasurement.UnitsOfMeasurement;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class DataSampleAnalysisResult {

    String[] mandatoryFields = null;
    Object[] mandatoryFieldsValue = null;
    DataDataIntegrity labIntChecker = new DataDataIntegrity(); 
    String errorCode ="";
    Object[] errorDetailVariables= new Object[0];
    
    public static final String TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT = "sample_analysis_result";
        public static final String FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_PARAM_NAME = "param_name";
        public static final String FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_PRETTY_VALUE = "pretty_value";
        public static final String FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_RAW_VALUE = "raw_value";
        public static final String FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM = "uom";
        public static final String FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM_CONVERSION_MODE = "uom_conversion_mode";

    public static final String CONFIG_SAMPLEANALYSISRESULT_STATUSREVIEWED = "sampleAnalysisResult_statusReviewed";
    public static final String CONFIG_SAMPLEANALYSISRESULT_STATUSCANCELED = "sampleAnalysisResult_statusCanceled";

    public DataSampleAnalysisResult(){
    }    
    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param testId
     * @param resultId
     * @param userRole
     * @param dataSample
     * @return
     */
    public Object[] sampleAnalysisResultCancelBack(String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole, DataSample dataSample) {
        String actionName = "CANCEL_BACK";
        String tableName = TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT;
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        String[] diagnoses = new String[6];
        String sampleAnalysisResultStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLEANALYSISRESULT_STATUSCANCELED);
        String sampleAnalysisResultStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLEANALYSISRESULT_STATUSREVIEWED);
        String sampleAnalysisStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSampleAnalysis.CONFIG_SAMPLEANALYSIS_STATUSCANCELED);
        String sampleAnalysisStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSampleAnalysis.CONFIG_SAMPLEANALYSIS_STATUSREVIEWED);
        String sampleStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSample.CONFIG_SAMPLE_STATUSCANCELED);
        String sampleStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSample.CONFIG_SAMPLE_STATUSREVIEWED);
        Object[] samplesToCancel = new Object[0];
        Object[] testsToCancel = new Object[0];
        Object[] testsSampleToCancel = new Object[0];
        String cancelScope = "";
        Integer cancelScopeId = 0;
        if (sampleId != null) {
            cancelScope = DataSample.FIELDNAME_SAMPLE_ID;
            cancelScopeId = sampleId;
        }
        if (testId != null) {
            cancelScope = DataSample.FIELDNAME_TEST_ID;
            cancelScopeId = testId;
        }
        if (resultId != null) {
            cancelScope = DataSample.FIELDNAME_RESULT_ID;
            cancelScopeId = resultId;
        }
        Object[][] objectInfo = null;
        objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, new String[]{cancelScope}, new Object[]{cancelScopeId}, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_RESULT_ID, DataSample.FIELDNAME_TEST_ID, DataSample.FIELDNAME_SAMPLE_ID});
        if (objectInfo.length == 0) {
            String[] filter = new String[]{DataSample.FIELDNAME_SAMPLE_ID + ":" + sampleId.toString() + " test_id:" + testId.toString() + " result_id:" + resultId.toString()};
            dataSample.errorCode = "DataSample_SampleAnalysisResultNotFound";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, Arrays.toString(filter));
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        } else {
            for (Integer iResToCancel = 0; iResToCancel < objectInfo.length; iResToCancel++) {
                String currStatus = (String) objectInfo[iResToCancel][0];
                if (!(sampleAnalysisResultStatusCanceled.equalsIgnoreCase(currStatus))) {
                    resultId = (Integer) objectInfo[iResToCancel][1];
                    testId = (Integer) objectInfo[iResToCancel][2];
                    sampleId = (Integer) objectInfo[iResToCancel][3];
                    if (!(sampleAnalysisResultStatusReviewed.equalsIgnoreCase(currStatus))) {
                        diagnoses = (String[]) Rdbms.updateRecordFieldsByFilter(schemaDataName, TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS}, new Object[]{sampleAnalysisResultStatusCanceled, currStatus}, new String[]{DataSample.FIELDNAME_RESULT_ID}, new Object[]{resultId});
                        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0])) {
                            String[] fieldsForAudit = new String[0];
                            fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS + ":" + sampleAnalysisResultStatusCanceled);
                            fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS_PREVIOUS + ":" + currStatus);
                            SampleAudit smpAudit = new SampleAudit();
                            smpAudit.sampleAuditAdd(schemaPrefix, actionName, TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
                        }
                    } else {
                        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                        diagnoses[0] = elements[1].getClassName() + "." + elements[1].getMethodName();
                        diagnoses[1] = dataSample.classVersion;
                        diagnoses[2] = "Code Line " + (elements[1].getLineNumber());
                        diagnoses[3] = "FALSE";
                        diagnoses[4] = "RESULT ALREADY CANCELED";
                        diagnoses[5] = "The result " + resultId.toString() + " has status " + currStatus + " then cannot be canceled in schema " + schemaDataName;
                    }
                }
                if ((cancelScope.equalsIgnoreCase(DataSample.FIELDNAME_SAMPLE_ID)) && (!LPArray.valueInArray(samplesToCancel, sampleId))) {
                    samplesToCancel = LPArray.addValueToArray1D(samplesToCancel, sampleId);
                }
                if ((cancelScope.equalsIgnoreCase(DataSample.FIELDNAME_SAMPLE_ID) || cancelScope.equalsIgnoreCase(DataSample.FIELDNAME_TEST_ID)) && (!LPArray.valueInArray(testsToCancel, testId))) {
                    testsToCancel = LPArray.addValueToArray1D(testsToCancel, testId);
                    testsSampleToCancel = LPArray.addValueToArray1D(testsSampleToCancel, sampleId);
                }
            }
        }
        for (Integer iTstToCancel = 0; iTstToCancel < testsToCancel.length; iTstToCancel++) {
            Integer currTest = (Integer) testsToCancel[iTstToCancel];
            if (currTest != null) {
                objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{currTest}, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS, DataSample.FIELDNAME_TEST_ID, DataSample.FIELDNAME_SAMPLE_ID});
                String currStatus = (String) objectInfo[0][0];
                if ((!(sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleAnalysisStatusReviewed.equalsIgnoreCase(currStatus))) && (currTest != null)) {
                    diagnoses = (String[]) Rdbms.updateRecordFieldsByFilter(schemaDataName, DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS}, new Object[]{sampleAnalysisStatusCanceled, currStatus}, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{currTest});
                    if ("TRUE".equalsIgnoreCase(diagnoses[3])) {
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS + ":" + sampleAnalysisStatusCanceled);
                        fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS_PREVIOUS + ":" + currStatus);
                        SampleAudit smpAudit = new SampleAudit();
                        smpAudit.sampleAuditAdd(schemaPrefix, actionName, DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, currTest, sampleId, currTest, null, fieldsForAudit, userName, userRole);
                    }
                } else {
                    diagnoses[5] = "The test " + currTest.toString() + " has status " + currStatus + " then cannot be canceled in schema " + schemaDataName;
                }
            }
        }
        for (Integer iSmpToCancel = 0; iSmpToCancel < samplesToCancel.length; iSmpToCancel++) {
            Integer currSample = (Integer) samplesToCancel[iSmpToCancel];
            objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, DataSample.TABLENAME_DATA_SAMPLE, new String[]{DataSample.FIELDNAME_SAMPLE_ID}, new Object[]{currSample}, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS, DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_SAMPLE_ID});
            String currStatus = (String) objectInfo[0][0];
            if ((!(sampleStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleStatusReviewed.equalsIgnoreCase(currStatus))) && (currSample != null)) {
                diagnoses = (String[]) Rdbms.updateRecordFieldsByFilter(schemaDataName, DataSample.TABLENAME_DATA_SAMPLE, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS}, new Object[]{sampleStatusCanceled, currStatus}, new String[]{DataSample.FIELDNAME_SAMPLE_ID}, new Object[]{currSample});
                if ("TRUE".equalsIgnoreCase(diagnoses[3])) {
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS + ":" + sampleStatusCanceled);
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS_PREVIOUS + ":" + currStatus);
                    SampleAudit smpAudit = new SampleAudit();
                    smpAudit.sampleAuditAdd(schemaPrefix, actionName, DataSample.TABLENAME_DATA_SAMPLE, currSample, currSample, null, null, fieldsForAudit, userName, userRole);
                }
            } else {
                diagnoses[5] = "The sample " + LPNulls.replaceNull(currSample) + " has status " + currStatus + " then cannot be canceled in schema " + schemaDataName;
            }
        }
        return diagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param resultId
     * @param resultValue
     * @param userRole
     * @param dataSample
     * @return
     * @throws IllegalArgumentException
     */
    public Object[] sampleAnalysisResultEntry(String schemaPrefix, String userName, Integer resultId, Object resultValue, String userRole, DataSample dataSample) {
        String tableName = TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT;
        String actionName = "Insert";
        String auditActionName = "SAMPLE_ANALYSIS_RESULT_ENTRY";
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);
        String specEvalNoSpec = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusSpecEvalNoSpec");
        String specEvalNoSpecParamLimit = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusSpecEvalNoSpecParamLimit");
        String resultStatusDefault = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusFirst");
        String resultStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLEANALYSISRESULT_STATUSREVIEWED);
        String resultStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLEANALYSISRESULT_STATUSCANCELED);
        String specArgumentsSeparator = "\\*";
        Boolean specMinSpecStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_minStrictSpecWhenNotSpecified"));
        Boolean specMinControlStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_minStrictControlWhenNotSpecified"));
        Boolean specMaxControlStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_maxStrictControlWhenNotSpecified"));
        Boolean specMaxSpecStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_maxStrictSpecWhenNotSpecified"));
        dataSample.mandatoryFields = dataSample.labIntChecker.getTableMandatoryFields(schemaDataName, DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, actionName);
        String[] fieldsName = new String[0];
        Object[] fieldsValue = new Object[0];
        fieldsName = LPArray.addValueToArray1D(fieldsName, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_RAW_VALUE);
        fieldsValue = LPArray.addValueToArray1D(fieldsValue, resultValue);
        Object[][] resultData = Rdbms.getRecordFieldsByFilter(schemaDataName, TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, new String[]{DataSample.FIELDNAME_RESULT_ID}, new Object[]{resultId}, new String[]{DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_TEST_ID, DataSample.FIELDNAME_ANALYSIS, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_PARAM_NAME, DataSample.FIELDNAME_STATUS, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_RAW_VALUE, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM_CONVERSION_MODE});
        if (LPPlatform.LAB_FALSE.equals(resultData[0][0].toString())) {
            dataSample.errorCode = "DataSample_SampleAnalysisResultNotFound";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, resultId.toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        Integer sampleId = (Integer) resultData[0][0];
        Integer testId = (Integer) resultData[0][1];
        String analysis = (String) resultData[0][2];
        String methodName = (String) resultData[0][3];
        Integer methodVersion = (Integer) resultData[0][4];
        String paramName = (String) resultData[0][5];
        String currResultStatus = (String) resultData[0][6];
        String currRawValue = (String) resultData[0][7];
        String resultUomName = (String) resultData[0][8];
        String resultUomConversionMode = (String) resultData[0][9];
        if (resultStatusReviewed.equalsIgnoreCase(currResultStatus) || resultStatusCanceled.equalsIgnoreCase(currResultStatus)) {
            dataSample.errorCode = "DataSample_SampleAnalysisResultLocked";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, currResultStatus);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, resultId.toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaConfigName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        if ((currRawValue != null) && (currRawValue.equalsIgnoreCase(resultValue.toString()))) {
            dataSample.errorCode = "DataSample_SampleAnalysisResultSampleValue";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, resultId.toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, currRawValue);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        Object[][] sampleData = Rdbms.getRecordFieldsByFilter(schemaDataName, DataSample.TABLENAME_DATA_SAMPLE, new String[]{DataSample.FIELDNAME_SAMPLE_ID}, new Object[]{sampleId}, new String[]{DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_SAMPLE_CONFIG_CODE, DataSample.FIELDNAME_SAMPLE_CONFIG_CODE_VERSION});
        if (LPPlatform.LAB_FALSE.equals(sampleData[0][0].toString())) {
            dataSample.errorCode = DataSample.ERROR_TRAPPING_DATA_SAMPLE_NOT_FOUND;
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, sampleId.toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        String sampleConfigCode = (String) sampleData[0][4];
        Integer sampleConfigCodeVersion = (Integer) sampleData[0][5];
        Object[][] sampleSpecData = Rdbms.getRecordFieldsByFilter(schemaDataName, DataSample.TABLENAME_DATA_SAMPLE, new String[]{DataSample.FIELDNAME_SAMPLE_ID}, new Object[]{sampleId}, new String[]{DataSample.FIELDNAME_SPEC_CODE, DataSample.FIELDNAME_SPEC_CODE_VERSION, DataSample.FIELDNAME_SPEC_VARIATION_NAME, DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_SAMPLE_CONFIG_CODE, DataSample.FIELDNAME_SAMPLE_CONFIG_CODE_VERSION});
        String sampleSpecCode = null;
        Integer sampleSpecCodeVersion = null;
        String sampleSpecVariationName = null;
        if ((sampleSpecData[0][0] != null) && (!LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleSpecData[0][0].toString()))) {
            sampleSpecCode = sampleSpecData[0][0].toString();
            sampleSpecCodeVersion = Integer.valueOf(sampleSpecData[0][1].toString());
            sampleSpecVariationName = sampleSpecData[0][2].toString();
            String status = sampleSpecData[0][3].toString();
        }
        Object[][] sampleRulesData = Rdbms.getRecordFieldsByFilter(schemaConfigName, DataSample.TABLENAME_CONFIG_SAMPLE_RULES, new String[]{DataSample.FIELDNAME_CODE, DataSample.FIELDNAME_CODE_VERSION}, new Object[]{sampleConfigCode, sampleConfigCodeVersion}, new String[]{"test_analyst_required"});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleRulesData[0][0].toString())) {
            dataSample.errorCode = "DataSample_SampleRulesNotFound";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, DataSample.FIELDNAME_CONFIG_SAMPLE_RULES_ANALYST_ASSIGNMENT_MODE);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, sampleConfigCode);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, sampleConfigCodeVersion);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaConfigName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        Boolean analystRequired = (Boolean) sampleRulesData[0][0];
        if (analystRequired) {
            Object[][] testData = Rdbms.getRecordFieldsByFilter(schemaDataName, DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{testId}, new String[]{DataSample.FIELDNAME_TEST_ID, DataSample.FIELDNAME_ANALYST, "analyst_assigned_on"});
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleRulesData[0][0].toString())) {
                dataSample.errorCode = "DataSample_SampleAnalysisNotFound";
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testId.toString());
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
            }
            String testAnalyst = (String) testData[0][1];
            if (testAnalyst == null) {
                dataSample.errorCode = "DataSample_SampleAnalysisRuleAnalystNotAssigned";
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testId.toString());
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, sampleConfigCode);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, sampleConfigCodeVersion.toString());
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
            }
            if (!testAnalyst.equalsIgnoreCase(userName)) {
                dataSample.errorCode = "DataSample_SampleAnalysisRuleOtherAnalystEnterResult";
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testId.toString());
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testAnalyst);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, userName);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
            }
        }
        String newResultStatus = currResultStatus;
        if (currResultStatus == null) {
            newResultStatus = resultStatusDefault;
        }
        if (newResultStatus.equalsIgnoreCase(resultStatusDefault)) {
            newResultStatus = "ENTERED";
        } else {
            newResultStatus = "RE-ENTERED";
        }
        if (sampleSpecCode == null) {
            fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_SPEC_EVAL);
            fieldsValue = LPArray.addValueToArray1D(fieldsValue, specEvalNoSpec);
            fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_ENTEREDBY);
            fieldsValue = LPArray.addValueToArray1D(fieldsValue, userName);
            fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_ENTEREDON);
            fieldsValue = LPArray.addValueToArray1D(fieldsValue, Rdbms.getCurrentDate());
            fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_STATUS);
            fieldsValue = LPArray.addValueToArray1D(fieldsValue, newResultStatus);
            fieldsName = LPArray.addValueToArray1D(fieldsName, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_PRETTY_VALUE);
            Object[] prettyValue = sarRawToPrettyResult(resultValue);
            fieldsValue = LPArray.addValueToArray1D(fieldsValue, prettyValue[1]);
            Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, fieldsName, fieldsValue, new String[]{DataSample.FIELDNAME_RESULT_ID}, new Object[]{resultId});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
            }
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                DataSampleAnalysis.sampleAnalysisEvaluateStatus(schemaPrefix, userName, sampleId, testId, auditActionName, userRole);
            }
            String[] whereFields = new String[]{"user_id", DataSample.FIELDNAME_ANALYSIS, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION};
            Object[] whereFieldsValue = new Object[]{userName, analysis, methodName, methodVersion};
            String[] updFields = new String[]{"last_analysis_on", "last_sample", "last_sample_analysis"};
            Object[] updFieldsValue = new Object[]{Rdbms.getLocalDate(), sampleId, testId};
            Object[][] userMethodInfo;
            userMethodInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "user_method", whereFields, whereFieldsValue, new String[]{"user_method_id", "user_id", DataSample.FIELDNAME_ANALYSIS, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION});
            if (!(LPPlatform.LAB_FALSE.equalsIgnoreCase(userMethodInfo[0][0].toString()))) {
                diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "user_method", updFields, updFieldsValue, whereFields, whereFieldsValue);
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                    updFields = LPArray.addValueToArray1D(updFields, whereFields);
                    updFieldsValue = LPArray.addValueToArray1D(updFieldsValue, whereFieldsValue);
                    String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(updFields, updFieldsValue, ":");
                    auditActionName = "UPDATE LAST ANALYSIS USER METHOD";
                    SampleAudit smpAudit = new SampleAudit();
                    smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "user_method", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
                }
            }
            return diagnoses;
        }
        Object[][] specLimits = Rdbms.getRecordFieldsByFilter(schemaConfigName, "spec_limits", new String[]{DataSample.FIELDNAME_CODE, "config_version", DataSample.FIELDNAME_VARIATION_NAME, DataSample.FIELDNAME_ANALYSIS, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION, "parameter"}, new Object[]{sampleSpecCode, sampleSpecCodeVersion, sampleSpecVariationName, analysis, methodName, methodVersion, paramName}, new String[]{"limit_id", "rule_type", "rule_variables", "limit_id", FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM_CONVERSION_MODE});
        //    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(specLimits[0][0].toString())){
        if ((LPPlatform.LAB_FALSE.equalsIgnoreCase(specLimits[0][0].toString())) && (!"Rdbms_NoRecordsFound".equalsIgnoreCase(specLimits[0][4].toString()))) {
            return LPArray.array2dTo1d(specLimits);
        }
        if ((LPPlatform.LAB_FALSE.equalsIgnoreCase(specLimits[0][0].toString())) && ("Rdbms_NoRecordsFound".equalsIgnoreCase(specLimits[0][4].toString()))) {
            fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_SPEC_EVAL);
            fieldsValue = LPArray.addValueToArray1D(fieldsValue, specEvalNoSpecParamLimit);
            fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_ENTEREDBY);
            fieldsValue = LPArray.addValueToArray1D(fieldsValue, userName);
            fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_ENTEREDON);
            fieldsValue = LPArray.addValueToArray1D(fieldsValue, Rdbms.getCurrentDate());
            fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_STATUS);
            fieldsValue = LPArray.addValueToArray1D(fieldsValue, newResultStatus);
            fieldsName = LPArray.addValueToArray1D(fieldsName, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_PRETTY_VALUE);
            Object[] prettyValue = sarRawToPrettyResult(resultValue);
            fieldsValue = LPArray.addValueToArray1D(fieldsValue, prettyValue[1]);
            Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, fieldsName, fieldsValue, new String[]{DataSample.FIELDNAME_RESULT_ID}, new Object[]{resultId});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
            }
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                DataSampleAnalysis.sampleAnalysisEvaluateStatus(schemaPrefix, userName, sampleId, testId, auditActionName, userRole);
            }
            /*        String[] whereFields = new String[]{"user_id", FIELDNAME_ANALYSIS, FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION};
            Object[] whereFieldsValue = new Object[]{userName, analysis, methodName, methodVersion};
            String[] updFields = new String[]{"last_analysis_on", "last_sample", "last_sample_analysis"};
            Object[] updFieldsValue = new Object[]{Rdbms.getLocalDate(), sampleId, testId};
            Object[][] userMethodInfo;
            userMethodInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "user_analysis_method",
            whereFields,
            whereFieldsValue,
            new String[]{"user_analysis_method_id", "user_id", FIELDNAME_ANALYSIS, FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION});
            if (!(LPPlatform.LAB_FALSE.equalsIgnoreCase(userMethodInfo[0][0].toString())) ){
            diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "user_analysis_method",
            updFields, updFieldsValue, whereFields, whereFieldsValue);
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){                updFields = LPArray.addValueToArray1D(updFields, whereFields);
            updFieldsValue = LPArray.addValueToArray1D(updFieldsValue, whereFieldsValue);
            String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(updFields, updFieldsValue, ":");
            auditActionName = "UPDATE LAST ANALYSIS USER METHOD";
            SampleAudit smpAudit = new SampleAudit();
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "user_analysis_method", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
            }
            } */
            return diagnoses;
        }
        Integer limitId = (Integer) specLimits[0][0];
        String ruleType = (String) specLimits[0][1];
        String ruleVariables = (String) specLimits[0][2];
        String specUomName = (String) specLimits[0][4];
        String specUomConversionMode = (String) specLimits[0][5];
        Boolean requiresUnitsConversion = false;
        BigDecimal resultConverted = null;
        if (resultUomName != null) {
            if ((!resultUomName.equalsIgnoreCase(specUomName)) && (specUomConversionMode == null || specUomConversionMode.equalsIgnoreCase("DISABLED") || ((!specUomConversionMode.contains(resultUomName)) && !specUomConversionMode.equalsIgnoreCase("ALL")))) {
                dataSample.errorCode = "DataSample_SampleAnalysisResult_ConversionNotAllowed";
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, specUomConversionMode);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, specUomName);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, resultUomName);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, limitId.toString());
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
            }
            requiresUnitsConversion = true;
            UnitsOfMeasurement uom = new UnitsOfMeasurement();
            Object[] convDiagnoses = uom.convertValue(schemaPrefix, new BigDecimal(resultValue.toString()), resultUomName, specUomName);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(convDiagnoses[0].toString())) {
                dataSample.errorCode = "DataSample_SampleAnalysisResult_ConverterFALSE";
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, resultId.toString());
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, convDiagnoses[3].toString());
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
            }
            resultConverted = (BigDecimal) convDiagnoses[1];
        }
        DataSpec resChkSpec = new DataSpec();
        Object[] resSpecEvaluation = null;
        switch (ruleType.toLowerCase()) {
            case "qualitative":
                String[] qualitSpecTestingArray = ruleVariables.split(specArgumentsSeparator);
                String specRuleType = qualitSpecTestingArray[0];
                String specValues = qualitSpecTestingArray[1];
                String specSeparator = null;
                if (qualitSpecTestingArray.length == 3) {
                    specSeparator = qualitSpecTestingArray[2];
                }
                String specListName = null;
                resSpecEvaluation = resChkSpec.resultCheck((String) resultValue, specRuleType, specValues, specSeparator, specListName);
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(resSpecEvaluation[0].toString())) {
                    return resSpecEvaluation;
                    //errorCode = "DataSample_SampleAnalysisResult_QualitativeSpecNotRecognized";
                    // errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, resultId.toString());
                    // errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ruleVariables);
                    // errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                    // return labPlat.trapErrorMessage(LPPlatform.LAB_FALSE, classVersion, errorCode, errorDetailVariables);
                }
                fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_SPEC_EVAL);
                fieldsValue = LPArray.addValueToArray1D(fieldsValue, resSpecEvaluation[resSpecEvaluation.length - 1]);
                fieldsName = LPArray.addValueToArray1D(fieldsName, "spec_eval_detail");
                fieldsValue = LPArray.addValueToArray1D(fieldsValue, resSpecEvaluation[resSpecEvaluation.length - 2]);
                fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_ENTEREDBY);
                fieldsValue = LPArray.addValueToArray1D(fieldsValue, userName);
                fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_ENTEREDON);
                fieldsValue = LPArray.addValueToArray1D(fieldsValue, Rdbms.getCurrentDate());
                fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_STATUS);
                fieldsValue = LPArray.addValueToArray1D(fieldsValue, newResultStatus);
                Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, fieldsName, fieldsValue, new String[]{DataSample.FIELDNAME_RESULT_ID}, new Object[]{resultId});
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                    String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
                    SampleAudit smpAudit = new SampleAudit();
                    smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
                }
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                    DataSampleAnalysis.sampleAnalysisEvaluateStatus(schemaPrefix, userName, sampleId, testId, auditActionName, userRole);
                }
                return diagnoses;
            case "quantitative":
                resultValue = Float.parseFloat(resultValue.toString());
                String[] quantiSpecTestingArray = ruleVariables.split(specArgumentsSeparator);
                Float minSpec = null;
                Boolean minStrict = specMinSpecStrictDefault;
                Float maxSpec = null;
                Boolean maxStrict = specMaxSpecStrictDefault;
                Float minControl = null;
                Boolean minControlStrict = specMinControlStrictDefault;
                Float maxControl = null;
                Boolean maxControlStrict = specMaxControlStrictDefault;
                for (Integer iField = 0; iField < quantiSpecTestingArray.length; iField++) {
                    String curParam = quantiSpecTestingArray[iField];
                    if (curParam.toUpperCase().contains("MINSPEC")) {
                        curParam = curParam.replace("MINSPEC", "");
                        minSpec = Float.parseFloat(curParam);
                    }
                    if (curParam.toUpperCase().contains("MINSPECSTRICT")) {
                        curParam = curParam.replace("MINSPECSTRICT", "");
                        minStrict = Boolean.getBoolean(curParam);
                    }
                    if (curParam.toUpperCase().contains("MINCONTROL")) {
                        curParam = curParam.replace("MINCONTROL", "");
                        minControl = Float.parseFloat(curParam);
                    }
                    if (curParam.toUpperCase().contains("MINCONTROLSTRICT")) {
                        curParam = curParam.replace("MINCONTROLSTRICT", "");
                        minControlStrict = Boolean.getBoolean(curParam);
                    }
                    if (curParam.toUpperCase().contains("MAXCONTROL")) {
                        curParam = curParam.replace("MAXCONTROL", "");
                        maxControl = Float.parseFloat(curParam);
                    }
                    if (curParam.toUpperCase().contains("MAXCONTROLTRICT")) {
                        curParam = curParam.replace("MAXCONTROLSTRICT", "");
                        maxControlStrict = Boolean.getBoolean(curParam);
                    }
                    if (curParam.toUpperCase().contains("MAXSPEC")) {
                        curParam = curParam.replace("MAXSPEC", "");
                        maxSpec = Float.parseFloat(curParam);
                    }
                    if (curParam.toUpperCase().contains("MAXSPECSTRICT")) {
                        curParam = curParam.replace("MAXSPECSTRICT", "");
                        maxStrict = Boolean.getBoolean(curParam);
                    }
                }
                if (ruleVariables.contains("CONTROL")) {
                    if (requiresUnitsConversion) {
                        resSpecEvaluation = resChkSpec.resultCheckFloat(resultConverted.floatValue(), minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);
                    } else {
                        resSpecEvaluation = resChkSpec.resultCheckFloat((Float) resultValue, minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);
                    }
                } else {
                    if (requiresUnitsConversion) {
                        resSpecEvaluation = resChkSpec.resultCheckFloat(resultConverted.floatValue(), minSpec, maxSpec, minStrict, maxStrict);
                    } else {
                        resSpecEvaluation = resChkSpec.resultCheckFloat((Float) resultValue, minSpec, maxSpec, minStrict, maxStrict);
                    }
                    //                resSpecEvaluation = resChkSpec.resultCheckFloat((Float) resultValue, (Float) minSpec, (Float) maxSpec, (Boolean) minStrict, (Boolean) maxStrict);
                }
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(resSpecEvaluation[0].toString())) {
                    return resSpecEvaluation;
                    //errorCode = "DataSample_SampleAnalysisResult_QuantitativeSpecNotRecognized";
                    //errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, resultId.toString());
                    //errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ruleVariables);
                    //errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                    //return labPlat.trapErrorMessage(LPPlatform.LAB_FALSE, classVersion, errorCode, errorDetailVariables);
                }
                String specEval = (String) resSpecEvaluation[resSpecEvaluation.length - 1];
                String specEvalDetail = (String) resSpecEvaluation[resSpecEvaluation.length - 2];
                if (requiresUnitsConversion) {
                    specEvalDetail = specEvalDetail + " in " + specUomName;
                }
                fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_SPEC_EVAL);
                fieldsValue = LPArray.addValueToArray1D(fieldsValue, specEval);
                fieldsName = LPArray.addValueToArray1D(fieldsName, "spec_eval_detail");
                fieldsValue = LPArray.addValueToArray1D(fieldsValue, specEvalDetail);
                fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_ENTEREDBY);
                fieldsValue = LPArray.addValueToArray1D(fieldsValue, userName);
                fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_ENTEREDON);
                fieldsValue = LPArray.addValueToArray1D(fieldsValue, Rdbms.getCurrentDate());
                fieldsName = LPArray.addValueToArray1D(fieldsName, DataSample.FIELDNAME_STATUS);
                fieldsValue = LPArray.addValueToArray1D(fieldsValue, newResultStatus);
                diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, fieldsName, fieldsValue, new String[]{DataSample.FIELDNAME_RESULT_ID}, new Object[]{resultId});
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                    String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
                    SampleAudit smpAudit = new SampleAudit();
                    smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
                }
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                    DataSampleAnalysis.sampleAnalysisEvaluateStatus(schemaPrefix, userName, sampleId, testId, auditActionName, userRole);
                }
                String[] whereFields = new String[]{"user_id", DataSample.FIELDNAME_ANALYSIS, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION};
                Object[] whereFieldsValue = new Object[]{userName, analysis, methodName, methodVersion};
                String[] updFields = new String[]{"last_analysis_on", "last_sample", "last_sample_analysis"};
                Object[] updFieldsValue = new Object[]{Rdbms.getLocalDate(), sampleId, testId};
                Object[][] userMethodInfo;
                userMethodInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "user_analysis_method", whereFields, whereFieldsValue, new String[]{"user_analysis_method_id", "user_id", DataSample.FIELDNAME_ANALYSIS, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION});
                if (!(LPPlatform.LAB_FALSE.equalsIgnoreCase(userMethodInfo[0][0].toString()))) {
                    Object diagnoses2 = Rdbms.updateRecordFieldsByFilter(schemaDataName, "user_analysis_method", updFields, updFieldsValue, whereFields, whereFieldsValue);
                    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                        updFields = LPArray.addValueToArray1D(updFields, whereFields);
                        updFieldsValue = LPArray.addValueToArray1D(updFieldsValue, whereFieldsValue);
                        String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(updFields, updFieldsValue, ":");
                        auditActionName = auditActionName + ":" + "UPDATE USER METHOD RECORD";
                        SampleAudit smpAudit = new SampleAudit();
                        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "user_analysis_method", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
                    }
                }
                return diagnoses;
            default:
                dataSample.errorCode = "DataSample_SampleAnalysisResult_SpecRuleNotImplemented";
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, resultId.toString());
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, ruleType);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
    }

    public Object[] sarChangeUom(String schemaPrefix, Integer resultId, String newuom, String userName, String userRole, DataSample dataSample) {
        String auditActionName = "CHANGE_uom";
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        String tableName = TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT;
        Object[][] resultInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, new String[]{DataSample.FIELDNAME_RESULT_ID}, new Object[]{resultId}, new String[]{DataSample.FIELDNAME_RESULT_ID, DataSample.FIELDNAME_STATUS, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_PARAM_NAME, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_RAW_VALUE, DataSample.FIELDNAME_TEST_ID, DataSample.FIELDNAME_SAMPLE_ID, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM_CONVERSION_MODE});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(resultInfo[0][0].toString())) {
            return LPArray.array2dTo1d(resultInfo);
        }
        String paramName = resultInfo[0][2].toString();
        String curruom = resultInfo[0][3].toString();
        String currValue = resultInfo[0][4].toString();
        Integer testId = Integer.valueOf(resultInfo[0][5].toString());
        Integer sampleId = Integer.valueOf(resultInfo[0][6].toString());
        String specUomConversionMode = resultInfo[0][7].toString();
        if (specUomConversionMode == null || specUomConversionMode.equalsIgnoreCase("DISABLED") || ((!specUomConversionMode.contains(newuom)) && !specUomConversionMode.equalsIgnoreCase("ALL"))) {
            dataSample.errorCode = "DataSample_SampleAnalysisResult_ConversionNotAllowed";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, specUomConversionMode);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, newuom);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, curruom);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, resultId.toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        UnitsOfMeasurement uom = new UnitsOfMeasurement();
        Object[] convDiagnoses = uom.convertValue(schemaPrefix, new BigDecimal(currValue), curruom, newuom);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(convDiagnoses[0].toString())) {
            dataSample.errorCode = "DataSample_SampleAnalysisResult_ConverterFALSE";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, resultId.toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, convDiagnoses[3].toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        BigDecimal resultConverted = (BigDecimal) convDiagnoses[convDiagnoses.length - 2];
        String[] updFieldNames = new String[]{FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_RAW_VALUE, FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM};
        Object[] updFieldValues = new Object[]{resultConverted.toString(), newuom};
        Object[] updateRecordFieldsByFilter = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, updFieldNames, updFieldValues, new String[]{DataSample.FIELDNAME_RESULT_ID}, new Object[]{resultId});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(updateRecordFieldsByFilter[0].toString())) {
            return updateRecordFieldsByFilter;
        }
        SampleAudit smpAudit = new SampleAudit();
        auditActionName = auditActionName + " FOR " + paramName;
        Object[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(updFieldNames, updFieldValues, ":");
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, tableName, resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
        return updateRecordFieldsByFilter;
    }

    public Object[] sarRawToPrettyResult(Object rawValue) {
        return new Object[]{LPPlatform.LAB_TRUE, rawValue};
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param testId
     * @param resultId
     * @param userRole
     * @param dataSample
     * @return
     */
    public Object[] sampleAnalysisResultUnCancel(String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole, DataSample dataSample) {
        Object[] diagnoses = new Object[7];
        String tableName = TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT;
        String auditActionName = "SAMPLE_ANALYSIS_RESULT_UNCANCELING";
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        String sampleAnalysisResultStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLEANALYSISRESULT_STATUSCANCELED);
        String sampleAnalysisStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSampleAnalysis.CONFIG_SAMPLEANALYSIS_STATUSCANCELED);
        String sampleStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSample.CONFIG_SAMPLE_STATUSCANCELED);
        String cancelScope = "";
        Integer cancelScopeId = 0;
        if (sampleId != null) {
            cancelScope = DataSample.FIELDNAME_SAMPLE_ID;
            cancelScopeId = sampleId;
        }
        if (testId != null) {
            cancelScope = DataSample.FIELDNAME_TEST_ID;
            cancelScopeId = testId;
        }
        if (resultId != null) {
            cancelScope = DataSample.FIELDNAME_RESULT_ID;
            cancelScopeId = resultId;
        }
        Object[][] resultInfo = null;
        resultInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, new String[]{cancelScope}, new Object[]{cancelScopeId}, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS, DataSample.FIELDNAME_RESULT_ID, DataSample.FIELDNAME_TEST_ID, DataSample.FIELDNAME_SAMPLE_ID});
        if (resultInfo.length == 0) {
            String[] filter = new String[]{DataSample.FIELDNAME_SAMPLE_ID + ":" + sampleId.toString() + " " + DataSample.FIELDNAME_TEST_ID + ":" + testId.toString() + " " + DataSample.FIELDNAME_RESULT_ID + ":" + resultId.toString()};
            dataSample.errorCode = DataSample.ERROR_TRAPPING_DATA_SAMPLE_NOT_FOUND;
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, Arrays.toString(filter));
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        Object[] samplesToUnCancel = new Object[0];
        Object[] testsToUnCancel = new Object[0];
        String[] diagPerResult = new String[0];
        for (Integer iResToCancel = 0; iResToCancel < resultInfo.length; iResToCancel++) {
            String currResultStatus = (String) resultInfo[iResToCancel][0];
            String statusPrevious = (String) resultInfo[iResToCancel][1];
            resultId = (Integer) resultInfo[iResToCancel][2];
            testId = (Integer) resultInfo[iResToCancel][3];
            sampleId = (Integer) resultInfo[iResToCancel][4];
            if (!(sampleAnalysisResultStatusCanceled.equalsIgnoreCase(currResultStatus))) {
                dataSample.errorCode = "DataSample_SampleUnCancel_StatusNotExpected";
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, resultInfo[0][0].toString());
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, sampleAnalysisResultStatusCanceled);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
                diagPerResult = LPArray.addValueToArray1D(diagPerResult, "Result " + resultId.toString() + " not uncanceled because current status is " + currResultStatus);
            } else {
                resultId = (Integer) resultInfo[iResToCancel][2];
                diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, new String[]{DataSample.FIELDNAME_STATUS_PREVIOUS, DataSample.FIELDNAME_STATUS}, new Object[]{sampleAnalysisResultStatusCanceled, statusPrevious}, new String[]{DataSample.FIELDNAME_RESULT_ID}, new Object[]{resultId});
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS_PREVIOUS + ":" + sampleAnalysisResultStatusCanceled);
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS + ":" + statusPrevious);
                    SampleAudit smpAudit = new SampleAudit();
                    smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
                }
                diagPerResult = LPArray.addValueToArray1D(diagPerResult, "Result " + resultId.toString() + " UNCANCELED ");
            }
            if ((cancelScope.equalsIgnoreCase(DataSample.FIELDNAME_SAMPLE_ID)) && (!LPArray.valueInArray(samplesToUnCancel, sampleId))) {
                samplesToUnCancel = LPArray.addValueToArray1D(samplesToUnCancel, sampleId);
            }
            if ((cancelScope.equalsIgnoreCase(DataSample.FIELDNAME_SAMPLE_ID) || cancelScope.equalsIgnoreCase(DataSample.FIELDNAME_TEST_ID)) && (!LPArray.valueInArray(testsToUnCancel, testId))) {
                testsToUnCancel = LPArray.addValueToArray1D(testsToUnCancel, testId);
            }
        }
        for (Integer iTstToUnCancel = 0; iTstToUnCancel < testsToUnCancel.length; iTstToUnCancel++) {
            Integer currTest = (Integer) testsToUnCancel[iTstToUnCancel];
            Object[][] objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{currTest}, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS, DataSample.FIELDNAME_TEST_ID, DataSample.FIELDNAME_SAMPLE_ID});
            String currStatus = (String) objectInfo[0][0];
            String currPrevStatus = (String) objectInfo[0][1];
            if ((sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus)) && (currTest != null)) {
                diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS}, new Object[]{currPrevStatus, sampleAnalysisResultStatusCanceled}, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{currTest});
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS_PREVIOUS + ":" + sampleAnalysisResultStatusCanceled);
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS + ":" + currPrevStatus);
                    SampleAudit smpAudit = new SampleAudit();
                    smpAudit.sampleAuditAdd(schemaPrefix, "UNCANCEL_RESULT", DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, currTest, sampleId, currTest, null, fieldsForAudit, userName, userRole);
                }
            } else {
                diagnoses[5] = "The test " + LPNulls.replaceNull(currTest) + " has status " + currStatus + " then cannot be canceled in schema " + schemaDataName;
            }
        }
        for (Integer iSmpToUnCancel = 0; iSmpToUnCancel < samplesToUnCancel.length; iSmpToUnCancel++) {
            Integer currSample = (Integer) samplesToUnCancel[iSmpToUnCancel];
            Object[][] objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, DataSample.TABLENAME_DATA_SAMPLE, new String[]{DataSample.FIELDNAME_SAMPLE_ID}, new Object[]{currSample}, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS, DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_SAMPLE_ID});
            String currStatus = (String) objectInfo[0][0];
            String currPrevStatus = (String) objectInfo[0][1];
            if ((sampleStatusCanceled.equalsIgnoreCase(currStatus)) && (currSample != null)) {
                diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, DataSample.TABLENAME_DATA_SAMPLE, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS}, new Object[]{currPrevStatus, sampleAnalysisResultStatusCanceled}, new String[]{DataSample.FIELDNAME_SAMPLE_ID}, new Object[]{currSample});
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS_PREVIOUS + ":" + sampleAnalysisResultStatusCanceled);
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS + ":" + currPrevStatus);
                    SampleAudit smpAudit = new SampleAudit();
                    smpAudit.sampleAuditAdd(schemaPrefix, "UNCANCEL_RESULT", DataSample.TABLENAME_DATA_SAMPLE, currSample, currSample, null, null, fieldsForAudit, userName, userRole);
                }
            } else {
                diagnoses[5] = "The sample " + LPNulls.replaceNull(currSample) + " has status " + currStatus + " then cannot be canceled in schema " + schemaDataName;
            }
        }
        diagnoses[5] = Arrays.toString(diagPerResult);
        return diagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param testId
     * @param resultId
     * @param userRole
     * @param dataSample
     * @return
     */
    public Object[] sampleAnalysisResultCancel(String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole, DataSample dataSample) {
        Object[] diagnoses = new Object[7];
        String actionName = "CANCEL_RESULT";
        String tableName = TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT;
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        String sampleAnalysisResultStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLEANALYSISRESULT_STATUSCANCELED);
        String sampleAnalysisResultStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLEANALYSISRESULT_STATUSREVIEWED);
        String sampleAnalysisStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSampleAnalysis.CONFIG_SAMPLEANALYSIS_STATUSCANCELED);
        String sampleAnalysisStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSampleAnalysis.CONFIG_SAMPLEANALYSIS_STATUSREVIEWED);
        String sampleStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSample.CONFIG_SAMPLE_STATUSCANCELED);
        String sampleStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSample.CONFIG_SAMPLE_STATUSREVIEWED);
        Object[] samplesToCancel = new Object[0];
        Object[] testsToCancel = new Object[0];
        Object[] testsSampleToCancel = new Object[0];
        String cancelScope = "";
        Integer cancelScopeId = 0;
        if (sampleId != null) {
            cancelScope = DataSample.FIELDNAME_SAMPLE_ID;
            cancelScopeId = sampleId;
        }
        if (testId != null) {
            cancelScope = DataSample.FIELDNAME_TEST_ID;
            cancelScopeId = testId;
        }
        if (resultId != null) {
            cancelScope = DataSample.FIELDNAME_RESULT_ID;
            cancelScopeId = resultId;
        }
        Object[][] objectInfo = null;
        objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, new String[]{cancelScope}, new Object[]{cancelScopeId}, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_RESULT_ID, DataSample.FIELDNAME_TEST_ID, DataSample.FIELDNAME_SAMPLE_ID});
        if (objectInfo.length == 0) {
            String[] filter = new String[]{DataSample.FIELDNAME_SAMPLE_ID + ":" + sampleId.toString() + DataSample.FIELDNAME_TEST_ID + ":" + testId.toString() + DataSample.FIELDNAME_RESULT_ID + ":" + resultId.toString()};
            dataSample.errorCode = DataSample.ERROR_TRAPPING_DATA_SAMPLE_NOT_FOUND;
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, Arrays.toString(filter));
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        } else {
            for (Integer iResToCancel = 0; iResToCancel < objectInfo.length; iResToCancel++) {
                String currStatus = (String) objectInfo[iResToCancel][0];
                if (!(sampleAnalysisResultStatusCanceled.equalsIgnoreCase(currStatus))) {
                    resultId = (Integer) objectInfo[iResToCancel][1];
                    testId = (Integer) objectInfo[iResToCancel][2];
                    sampleId = (Integer) objectInfo[iResToCancel][3];
                    if (!(sampleAnalysisResultStatusReviewed.equalsIgnoreCase(currStatus))) {
                        diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS}, new Object[]{sampleAnalysisResultStatusCanceled, currStatus}, new String[]{DataSample.FIELDNAME_RESULT_ID}, new Object[]{resultId});
                        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                            String[] fieldsForAudit = new String[0];
                            fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS + ":" + sampleAnalysisResultStatusCanceled);
                            fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS_PREVIOUS + ":" + currStatus);
                            SampleAudit smpAudit = new SampleAudit();
                            smpAudit.sampleAuditAdd(schemaPrefix, actionName, TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
                        }
                    } else {
                        dataSample.errorCode = "DataSample_SampleAnalysisResultCancelation_StatusNotExpected";
                        dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, resultId.toString());
                        dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, currStatus);
                        dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
                    }
                }
                if ((cancelScope.equalsIgnoreCase(DataSample.FIELDNAME_SAMPLE_ID)) && (!LPArray.valueInArray(samplesToCancel, sampleId))) {
                    samplesToCancel = LPArray.addValueToArray1D(samplesToCancel, sampleId);
                }
                if ((cancelScope.equalsIgnoreCase(DataSample.FIELDNAME_SAMPLE_ID) || cancelScope.equalsIgnoreCase(DataSample.FIELDNAME_TEST_ID)) && (!LPArray.valueInArray(testsToCancel, testId))) {
                    testsToCancel = LPArray.addValueToArray1D(testsToCancel, testId);
                    testsSampleToCancel = LPArray.addValueToArray1D(testsSampleToCancel, sampleId);
                }
            }
        }
        for (Integer iTstToCancel = 0; iTstToCancel < testsToCancel.length; iTstToCancel++) {
            Integer currTest = (Integer) testsToCancel[iTstToCancel];
            objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{currTest}, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS, DataSample.FIELDNAME_TEST_ID, DataSample.FIELDNAME_SAMPLE_ID});
            String currStatus = (String) objectInfo[0][0];
            if ((!(sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleAnalysisStatusReviewed.equalsIgnoreCase(currStatus))) && (currTest != null)) {
                diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS}, new Object[]{sampleAnalysisStatusCanceled, currStatus}, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{currTest});
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS + ":" + sampleAnalysisStatusCanceled);
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS_PREVIOUS + ":" + currStatus);
                    SampleAudit smpAudit = new SampleAudit();
                    smpAudit.sampleAuditAdd(schemaPrefix, actionName, DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, currTest, sampleId, currTest, null, fieldsForAudit, userName, userRole);
                }
            } else {
                dataSample.errorCode = "DataSample_SampleAnalysisCancelation_StatusNotExpected";
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, LPNulls.replaceNull(currTest));
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, currStatus);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
            }
        }
        for (Integer iSmpToCancel = 0; iSmpToCancel < samplesToCancel.length; iSmpToCancel++) {
            Integer currSample = (Integer) samplesToCancel[iSmpToCancel];
            objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, DataSample.TABLENAME_DATA_SAMPLE, new String[]{DataSample.FIELDNAME_SAMPLE_ID}, new Object[]{currSample}, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS, DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_SAMPLE_ID});
            String currStatus = (String) objectInfo[0][0];
            if ((!(sampleStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleStatusReviewed.equalsIgnoreCase(currStatus))) && (currSample != null)) {
                diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, DataSample.TABLENAME_DATA_SAMPLE, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS}, new Object[]{sampleStatusCanceled, currStatus}, new String[]{DataSample.FIELDNAME_SAMPLE_ID}, new Object[]{currSample});
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS + ":" + sampleStatusCanceled);
                    fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS_PREVIOUS + ":" + currStatus);
                    SampleAudit smpAudit = new SampleAudit();
                    smpAudit.sampleAuditAdd(schemaPrefix, actionName, DataSample.TABLENAME_DATA_SAMPLE, currSample, currSample, null, null, fieldsForAudit, userName, userRole);
                }
            } else {
                dataSample.errorCode = "DataSample_SampleAnalysisCancelation_StatusNotExpected";
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, LPNulls.replaceNull(currSample));
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, currStatus);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
            }
        }
        return diagnoses;
    }
    
}
