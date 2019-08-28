/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sampleStructure;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPDate;
import LabPLANET.utilities.LPNulls;
import LabPLANET.utilities.LPParadigm;
import LabPLANET.utilities.LPPlatform;
import databases.DataDataIntegrity;
import databases.Rdbms;
import functionalJava.analysis.UserMethod;
import functionalJava.audit.SampleAudit;
import functionalJava.parameter.Parameter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class DataSampleAnalysis {

    public static final String TABLENAME_DATA_SAMPLE_ANALYSIS = "sample_analysis";
    public static final String FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION = "method_version";
    public static final String FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME = "method_name";

    String[] mandatoryFields = null;
    Object[] mandatoryFieldsValue = null;
    DataDataIntegrity labIntChecker = new DataDataIntegrity(); 
    String errorCode ="";
    Object[] errorDetailVariables= new Object[0];
    
    public static final String CONFIG_SAMPLEANALYSIS_STATUSCANCELED = "sampleAnalysis_statusCanceled";
    public static final String CONFIG_SAMPLEANALYSIS_STATUSREVIEWED = "sampleAnalysis_statusReviewed";

    private static final String ERROR_TRAPPING_DATA_SAMPLE_ANALYSIS_ADD_TO_SAMPLE_MISSING_MANDATORY_FIELDS= "DataSample_sampleAnalaysisAddToSample_MissingMandatoryFields";

    public DataSampleAnalysis(){
    }    
    /**
     *  Automate the sample analysis assignment as to be triggered by any sample action.<br>
     *      Assigned to the actions: LOGSAMPLE.
     * @param schemaPrefix
     * @param sampleId
     * @param userName
     * @param sampleFieldName
     * @param userRole
     * @param sampleFieldValue
     * @param eventName
     * @param appSessionId
     * @param transactionId
     */
    public void autoSampleAnalysisAdd(String schemaPrefix, Integer sampleId, String userName, String userRole, String[] sampleFieldName, Object[] sampleFieldValue, String eventName, Integer appSessionId, Integer transactionId) {
        Object[][] anaName = new Object[2][3];
        anaName[0][0] = "pH";
        anaName[0][1] = "pH method";
        anaName[0][2] = 1;
        anaName[1][0] = "LOD";
        anaName[1][1] = "LOD Method";
        anaName[1][2] = 1;
        Object[] fieldNameValueArrayChecker = LPParadigm.fieldNameValueArrayChecker(sampleFieldName, sampleFieldValue);
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(fieldNameValueArrayChecker[0].toString())) {
            return;
        }
        for (Object[] anaName1 : anaName) {
            String[] fieldsName = new String[]{DataSample.FIELDNAME_ANALYSIS, FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION};
            Object[] fieldsValue = new Object[]{(String) anaName1[0], (String) anaName1[1], (Integer) anaName1[2]};
            sampleAnalysisAddtoSample(schemaPrefix, userName, sampleId, fieldsName, fieldsValue, userRole);
        }
    }

    /**
     *
     * @param schemaPrefix
     * @param template
     * @param templateVersion
     * @param dataSample
     * @return
     */
    public String specialFieldCheckSampleAnalysisAnalyst(String schemaPrefix, String template, Integer templateVersion, DataSample dataSample) {
        //, String schemaPrefix, String analysisList){
        String myDiagnoses = "";
        String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
        if (1 == 1) {
            myDiagnoses = "ERROR: specialFieldCheckSampleAnalysisAnalyst not implemented yet.";
            return myDiagnoses;
        }
        Integer specialFieldIndex = Arrays.asList(dataSample.mandatoryFields).indexOf(DataSample.FIELDNAME_STATUS);
        String status = dataSample.mandatoryFieldsValue[specialFieldIndex].toString();
        if (status.length() == 0) {
            myDiagnoses = "ERROR: The parameter status cannot be null";
            return myDiagnoses;
        }
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, DataSample.TABLENAME_CONFIG_SAMPLE_RULES, new String[]{DataSample.FIELDNAME_CODE, DataSample.FIELDNAME_CODE_VERSION}, new Object[]{template, templateVersion});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnosis[0].toString())) {
            myDiagnoses = "ERROR: The sample_rule record for " + template + " does not exist in schema" + schemaConfigName + ". ERROR: " + diagnosis[5];
        } else {
            String[] fieldNames = new String[1];
            Object[] fieldValues = new Object[1];
            fieldNames[0] = DataSample.FIELDNAME_CODE;
            fieldValues[0] = template;
            String[] fieldFilter = new String[]{DataSample.FIELDNAME_CODE, DataSample.FIELDNAME_CODE_VERSION, "statuses", "default_status"};
            Object[][] records = Rdbms.getRecordFieldsByFilter(schemaConfigName, DataSample.TABLENAME_CONFIG_SAMPLE_RULES, fieldNames, fieldValues, fieldFilter);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(records[0][0].toString())) {
                myDiagnoses = "ERROR: Problem on getting sample rules for " + template + " exists but the rule record is missing in the schema " + schemaConfigName;
                return myDiagnoses;
            }
            String statuses = records[0][2].toString();
            if (LPArray.valueInArray(statuses.split("\\|", -1), status)) {
                myDiagnoses = DataSample.DIAGNOSES_SUCCESS;
            } else {
                myDiagnoses = "ERROR: The status " + status + " is not of one the defined status (" + statuses + " for the template " + template + " exists but the rule record is missing in the schema " + schemaConfigName;
            }
        }
        return myDiagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param userRole
     * @param testId
     * @param dataSample
     * @return diagnoses
     */
    public Object[] sampleAnalysisReview(String schemaPrefix, String userName, String userRole, Integer testId, DataSample dataSample) {
        Object[] diagnoses = new Object[7];
        String tableName = TABLENAME_DATA_SAMPLE_ANALYSIS;
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        String sampleAnalysisStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLEANALYSIS_STATUSCANCELED);
        String sampleAnalysisStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLEANALYSIS_STATUSREVIEWED);
        Object[][] objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{testId}, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS, DataSample.FIELDNAME_TEST_ID, DataSample.FIELDNAME_SAMPLE_ID});
        String currStatus = (String) objectInfo[0][0];
        Integer sampleId = (Integer) objectInfo[0][3];
        if ((!(sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleAnalysisStatusReviewed.equalsIgnoreCase(currStatus))) && (testId != null)) {
            diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, new String[]{DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_STATUS_PREVIOUS}, new Object[]{sampleAnalysisStatusReviewed, currStatus}, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{testId});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                String[] fieldsForAudit = new String[0];
                fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS + ":" + sampleAnalysisStatusCanceled);
                fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS_PREVIOUS + ":" + currStatus);
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd(schemaPrefix, "REVIEW_SAMPLE_ANALYSIS", tableName, testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
            }
            return diagnoses;
        } else {
            dataSample.errorCode = "DataSample_SampleAnalysisResultNotReviewable";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, LPNulls.replaceNull(testId));
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, currStatus);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
    }
    /*
    private Map getDefaultValuesTemplate(String schema, String tsample, String template) throws SQLException {
    String q = "SELECT column_name,\n" +
    "\n" +
    "(SELECT CASE\n" +
    "          WHEN isc.column_default IS NOT NULL THEN isc.column_default\n" +
    "          WHEN tsd.value IS NOT NULL THEN tsd.value\n" +
    "          ELSE null\n" +
    "       END) \n" +
    "\n" +
    "fielDefault\n" +
    "FROM information_schema.columns isc\n" +
    "left join template.sample ts on (isc.table_schema=ts.schema)\n" +
    "left join template.sample_default tsd on (isc.column_name = tsd.field)\n" +
    "WHERE isc.table_name = '"+tsample+"' and isc.table_schema='"+schema+"' and ts.sample_name = '"+template+"'\n" +
    "ORDER BY ordinal_position;";
    Map<String, String> myMap = new HashMap<>();
    try{
    CachedRowSetImpl res = rdbms.prepRdQuery(q, null);
    while (res.next()) {
    String col_name = res.getString("column_name");
    String col_value = res.getString("fielDefault");
    myMap.put(col_name, col_value);
    }
    }catch(SQLException ex){
    }
    return  myMap;
    }
     */

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param testId
     * @param parentAuditAction
     * @param userRole
     * @return
     */
    public static Object[] sampleAnalysisEvaluateStatus(String schemaPrefix, String userName, Integer sampleId, Integer testId, String parentAuditAction, String userRole) {
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        String tableName = DataSampleAnalysisResult.TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT;
        String auditActionName = "SAMPLE_ANALYSIS_EVALUATE_STATUS";
        if (parentAuditAction != null) {
            auditActionName = parentAuditAction + ":" + auditActionName;
        }
        String sampleAnalysisStatusIncomplete = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusIncomplete");
        String sampleAnalysisStatusComplete = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusComplete");
        String smpAnaNewStatus = "";
        Object[] diagnoses = Rdbms.existsRecord(schemaDataName, tableName, new String[]{DataSample.FIELDNAME_TEST_ID, DataSample.FIELDNAME_STATUS, "mandatory"}, new Object[]{testId, "BLANK", true});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
            smpAnaNewStatus = sampleAnalysisStatusIncomplete;
        } else {
            smpAnaNewStatus = sampleAnalysisStatusComplete;
        }
        diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, TABLENAME_DATA_SAMPLE_ANALYSIS, new String[]{DataSample.FIELDNAME_STATUS}, new Object[]{smpAnaNewStatus}, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{testId});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
            String[] fieldsForAudit = new String[0];
            fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, DataSample.FIELDNAME_STATUS + ":" + smpAnaNewStatus);
            SampleAudit smpAudit = new SampleAudit();
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE_ANALYSIS, testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
        }
        DataSample.sampleEvaluateStatus(schemaPrefix, userName, sampleId, parentAuditAction, userRole);
        return diagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param testId
     * @param newAnalyst
     * @param userRole
     * @param dataSample
     * @return
     */
    public Object[] sampleAnalysisAssignAnalyst(String schemaPrefix, String userName, Integer testId, String newAnalyst, String userRole, DataSample dataSample) {
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);
        String tableName = TABLENAME_DATA_SAMPLE_ANALYSIS;
        Boolean assignTestAnalyst = false;
        String auditActionName = "SAMPLE_ANALYSIS_ANALYST_ASSIGNMENT";
        String testStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLEANALYSIS_STATUSREVIEWED);
        String testStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLEANALYSIS_STATUSCANCELED);
        String assignmentModes = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_analystAssigmentModes");
        Object[][] testData = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{testId}, new String[]{DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_STATUS, DataSample.FIELDNAME_ANALYST, DataSample.FIELDNAME_ANALYSIS, FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(testData[0][0].toString())) {
            dataSample.errorCode = "DataSample_SampleAnalysisNotFound";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testId.toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        Integer sampleId = (Integer) testData[0][0];
        String testStatus = (String) testData[0][1];
        String testCurrAnalyst = (String) testData[0][2];
        String testAnalysis = (String) testData[0][3];
        String testMethodName = (String) testData[0][4];
        Integer testMethodVersion = (Integer) testData[0][5];
        if (testCurrAnalyst == null ? newAnalyst == null : testCurrAnalyst.equals(newAnalyst)) {
            dataSample.errorCode = "DataSample_SampleAnalysisAssignment_SameAnalyst";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testCurrAnalyst);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testId.toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        // the test status cannot be reviewed or canceled, should be checked
        if ((testCurrAnalyst != null) && (testStatus.equalsIgnoreCase(testStatusReviewed) || testStatus.equalsIgnoreCase(testStatusCanceled))) {
            dataSample.errorCode = "DataSample_SampleAnalysisAssignment_SampleAnalysisLocked";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testStatus);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testId.toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, newAnalyst);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        Object[][] sampleData = Rdbms.getRecordFieldsByFilter(schemaDataName, DataSample.TABLENAME_DATA_SAMPLE, new String[]{DataSample.FIELDNAME_SAMPLE_ID}, new Object[]{sampleId}, new String[]{DataSample.FIELDNAME_SAMPLE_CONFIG_CODE, DataSample.FIELDNAME_SAMPLE_CONFIG_CODE_VERSION});
        String sampleConfigCode = (String) sampleData[0][0];
        Integer sampleConfigCodeVersion = (Integer) sampleData[0][1];
        Object[][] sampleRulesData = Rdbms.getRecordFieldsByFilter(schemaConfigName, DataSample.TABLENAME_CONFIG_SAMPLE_RULES, new String[]{DataSample.FIELDNAME_CODE, DataSample.FIELDNAME_CODE_VERSION}, new Object[]{sampleConfigCode, sampleConfigCodeVersion}, new String[]{DataSample.FIELDNAME_CODE, DataSample.FIELDNAME_CODE_VERSION, DataSample.FIELDNAME_CONFIG_SAMPLE_RULES_ANALYST_ASSIGNMENT_MODE});
        String testAssignmentMode = (String) sampleRulesData[0][2];
        if (testAssignmentMode == null) {
            testAssignmentMode = "null";
        }
        if (!assignmentModes.contains(testAssignmentMode)) {
            dataSample.errorCode = "DataSample_SampleAnalysisAssignment_AssignmentModeNotRecognized";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, DataSample.FIELDNAME_CONFIG_SAMPLE_RULES_ANALYST_ASSIGNMENT_MODE);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, sampleConfigCode);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, sampleConfigCodeVersion);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testAssignmentMode);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, assignmentModes);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaPrefix);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testId.toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, newAnalyst);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        if (testAssignmentMode.equalsIgnoreCase("DISABLE")) {
            assignTestAnalyst = true;
        } else {
            UserMethod ana = new UserMethod();
            String userMethodCertificationMode = ana.userMethodCertificationLevel(schemaPrefix, testAnalysis, testMethodName, testMethodVersion, newAnalyst);
            String userCertifiedModes = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_analystAssigmentMode" + testAssignmentMode);
            String[] userMethodModesArr = userCertifiedModes.split("\\|");
            assignTestAnalyst = LPArray.valueInArray(userMethodModesArr, userMethodCertificationMode);
            if (!assignTestAnalyst) {
                dataSample.errorCode = "DataSample_SampleAnalysisAssignment_AssignmentModeNotImplemented";
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testAssignmentMode);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, Arrays.toString(userMethodModesArr));
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, userMethodCertificationMode);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
            }
        }
        if (assignTestAnalyst) {
            String[] updateFieldName = new String[0];
            Object[] updateFieldValue = new Object[0];
            updateFieldName = LPArray.addValueToArray1D(updateFieldName, DataSample.FIELDNAME_ANALYST);
            updateFieldValue = LPArray.addValueToArray1D(updateFieldValue, newAnalyst);
            updateFieldName = LPArray.addValueToArray1D(updateFieldName, "analyst_assigned_on");
            updateFieldValue = LPArray.addValueToArray1D(updateFieldValue, LPDate.getTimeStampLocalDate());
            updateFieldName = LPArray.addValueToArray1D(updateFieldName, "analyst_assigned_by");
            updateFieldValue = LPArray.addValueToArray1D(updateFieldValue, userName);
            Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, updateFieldName, updateFieldValue, new String[]{DataSample.FIELDNAME_TEST_ID}, new Object[]{testId});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                String msgCode = "DataSample_SampleAnalysisAssignment_Successfully";
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testId.toString());
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, newAnalyst);
                dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
                diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, msgCode, dataSample.errorDetailVariables);
                String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(updateFieldName, updateFieldValue, ":");
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE_ANALYSIS, testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
            }
            dataSample.errorCode = "DataSample_SampleAnalysisAssignment_databaseReturnedError";
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testId.toString());
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, newAnalyst);
            dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
        }
        dataSample.errorCode = "DataSample_SampleAnalysisAssignment_EscapeByUnhandledException";
        dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, schemaPrefix);
        dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, userName);
        dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, testId.toString());
        dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, newAnalyst);
        dataSample.errorDetailVariables = LPArray.addValueToArray1D(dataSample.errorDetailVariables, userRole);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, dataSample.errorCode, dataSample.errorDetailVariables);
    }

    /**
     *
     * @param schemaPrefix
     * @param dataSample
     * @return
     */
    public String specialFieldCheckSampleAnalysisMethod(String schemaPrefix, DataSample dataSample) {
        //, String schemaPrefix, String analysisList){
        String myDiagnoses = "";
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);
        Integer specialFieldIndex = Arrays.asList(dataSample.mandatoryFields).indexOf(DataSample.FIELDNAME_ANALYSIS);
        String analysis = (String) dataSample.mandatoryFieldsValue[specialFieldIndex];
        if (analysis.length() == 0) {
            myDiagnoses = "ERROR: The parameter analysis cannot be null";
            return myDiagnoses;
        }
        specialFieldIndex = Arrays.asList(dataSample.mandatoryFields).indexOf(FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME);
        String methodName = (String) dataSample.mandatoryFieldsValue[specialFieldIndex];
        if (methodName.length() == 0) {
            myDiagnoses = "ERROR: The parameter method_name cannot be null";
            return myDiagnoses;
        }
        specialFieldIndex = Arrays.asList(dataSample.mandatoryFields).indexOf(FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION);
        Integer methodVersion = (Integer) dataSample.mandatoryFieldsValue[specialFieldIndex];
        if (methodVersion == null) {
            myDiagnoses = "ERROR: The parameter method_version cannot be null";
            return myDiagnoses;
        }
        String[] fieldNames = new String[3];
        Object[] fieldValues = new Object[3];
        fieldNames[0] = DataSample.FIELDNAME_ANALYSIS;
        fieldValues[0] = analysis;
        fieldNames[1] = FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME;
        fieldValues[1] = methodName;
        fieldNames[2] = FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION;
        fieldValues[2] = methodVersion;
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, "analysis_method", fieldNames, fieldValues);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnosis[0].toString())) {
            myDiagnoses = DataSample.DIAGNOSES_SUCCESS;
        } else {
            diagnosis = Rdbms.existsRecord(schemaConfigName, DataSample.FIELDNAME_ANALYSIS, new String[]{DataSample.FIELDNAME_CODE}, new Object[]{analysis});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnosis[0].toString())) {
                myDiagnoses = "ERROR: The analysis " + analysis + " exists but the method " + methodName + " with version " + methodVersion + " was not found in the schema " + schemaPrefix;
            } else {
                myDiagnoses = "ERROR: The analysis " + analysis + " is not found in the schema " + schemaPrefix;
            }
        }
        return myDiagnoses;
    }
/*    public void autoSampleAnalysisAdd( String schemaPrefix, Integer sampleId, String userName, String userRole, String[] sampleFieldName, Object[] sampleFieldValue, String eventName, Integer appSessionId, Integer transactionId){
        
        DataSampleAnalysis.autoSampleAnalysisAdd(schemaPrefix, sampleId, userName, userRole, sampleFieldName, sampleFieldValue, eventName, appSessionId, transactionId);
    }*/
    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param fieldName
     * @param fieldValue
     * @param userRole
     * @return
     */
    public Object[] sampleAnalysisAddtoSample(String schemaPrefix, String userName, Integer sampleId, String[] fieldName, Object[] fieldValue, String userRole) {
        //DataSample dataSample = new DataSample();        
        String tableName = TABLENAME_DATA_SAMPLE_ANALYSIS;
        String actionName = "Insert";
        String auditActionName = "ADD_SAMPLE_ANALYSIS";
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);
        String sampleLevel = DataSample.TABLENAME_DATA_SAMPLE;
        String sampleTableName = DataSample.TABLENAME_DATA_SAMPLE;
        mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, sampleLevel + tableName, actionName);
        Object[] fieldNameValueArrayChecker = LPParadigm.fieldNameValueArrayChecker(fieldName, fieldValue);
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(fieldNameValueArrayChecker[0].toString())) {
            return fieldNameValueArrayChecker;
        }
        mandatoryFieldsValue = new Object[mandatoryFields.length];
        String mandatoryFieldsMissing = "";
        for (Integer inumLines = 0; inumLines < mandatoryFields.length; inumLines++) {
            String currField = mandatoryFields[inumLines];
            boolean contains = Arrays.asList(fieldName).contains(currField.toLowerCase());
            if (!contains) {
                if (mandatoryFieldsMissing.length() > 0) {
                    mandatoryFieldsMissing = mandatoryFieldsMissing + ",";
                }
                mandatoryFieldsMissing = mandatoryFieldsMissing + currField;
            } else {
                Integer valuePosic = Arrays.asList(fieldName).indexOf(currField);
                mandatoryFieldsValue[inumLines] = fieldValue[valuePosic];
            }
        }
        if (mandatoryFieldsMissing.length() > 0) {
            errorDetailVariables = new String[]{mandatoryFieldsMissing, Arrays.toString(fieldName), schemaConfigName};
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_DATA_SAMPLE_ANALYSIS_ADD_TO_SAMPLE_MISSING_MANDATORY_FIELDS, errorDetailVariables);
        }
        // set first status. Begin
        String firstStatus = Parameter.getParameterBundle(schemaDataName, "sampleAnalysis_statusFirst");
        Integer specialFieldIndex = Arrays.asList(fieldName).indexOf(DataSample.FIELDNAME_STATUS);
        if (specialFieldIndex == -1) {
            fieldName = LPArray.addValueToArray1D(fieldName, DataSample.FIELDNAME_STATUS);
            fieldValue = LPArray.addValueToArray1D(fieldValue, firstStatus);
        } else {
            fieldValue[specialFieldIndex] = firstStatus;
        }
        // set first status. End
        // Spec Business Rule. Allow other analyses. Begin
        Object[][] sampleData = Rdbms.getRecordFieldsByFilter(schemaDataName, sampleTableName, new String[]{DataSample.FIELDNAME_SAMPLE_ID}, new Object[]{sampleId}, new String[]{DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_STATUS});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleData[0][0].toString())) {
            errorCode = DataSample.ERROR_TRAPPING_DATA_SAMPLE_NOT_FOUND;
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }
        Object[][] sampleSpecData = Rdbms.getRecordFieldsByFilter(schemaDataName, sampleTableName, new String[]{DataSample.FIELDNAME_SAMPLE_ID}, new Object[]{sampleId}, new String[]{DataSample.FIELDNAME_SAMPLE_ID, DataSample.FIELDNAME_SPEC_CODE, DataSample.FIELDNAME_SPEC_CODE_VERSION, DataSample.FIELDNAME_SPEC_VARIATION_NAME, DataSample.FIELDNAME_STATUS});
        if ((sampleSpecData[0][0] == null) || (!LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleSpecData[0][0].toString()))) {
            String sampleSpecCode = (String) sampleSpecData[0][1];
            Integer sampleSpecCodeVersion = (Integer) sampleSpecData[0][2];
            String sampleSpecVariationName = (String) sampleSpecData[0][3];
            if (sampleSpecCode != null) {
                Object[][] specRules = Rdbms.getRecordFieldsByFilter(schemaConfigName, "spec_rules", new String[]{DataSample.FIELDNAME_CODE, "config_version"}, new Object[]{sampleSpecCode, sampleSpecCodeVersion}, new String[]{"allow_other_analysis", "allow_multi_spec", DataSample.FIELDNAME_CODE, "config_version"});
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(specRules[0][0].toString())) {
                    errorCode = "DataSample_SpecRuleNotFound";
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleSpecCode);
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleSpecCodeVersion.toString());
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
                }
                if (!Boolean.valueOf(specRules[0][0].toString())) {
                    String[] specAnalysisFieldName = new String[]{DataSample.FIELDNAME_ANALYSIS, FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION};
                    Object[] specAnalysisFieldValue = new Object[0];
                    for (String iFieldN : specAnalysisFieldName) {
                        specialFieldIndex = Arrays.asList(fieldName).indexOf(iFieldN);
                        if (specialFieldIndex == -1) {
                            specAnalysisFieldValue = LPArray.addValueToArray1D(fieldValue, null);
                        } else {
                            specAnalysisFieldValue = LPArray.addValueToArray1D(specAnalysisFieldValue, fieldValue[specialFieldIndex]);
                        }
                    }
                    specAnalysisFieldName = LPArray.addValueToArray1D(specAnalysisFieldName, DataSample.FIELDNAME_CODE);
                    specAnalysisFieldValue = LPArray.addValueToArray1D(specAnalysisFieldValue, sampleSpecCode);
                    specAnalysisFieldName = LPArray.addValueToArray1D(specAnalysisFieldName, "config_version");
                    specAnalysisFieldValue = LPArray.addValueToArray1D(specAnalysisFieldValue, sampleSpecCodeVersion);
                    specAnalysisFieldName = LPArray.addValueToArray1D(specAnalysisFieldName, DataSample.FIELDNAME_VARIATION_NAME);
                    specAnalysisFieldValue = LPArray.addValueToArray1D(specAnalysisFieldValue, sampleSpecVariationName);
                    Object[] analysisInSpec = Rdbms.existsRecord(schemaConfigName, "spec_limits", specAnalysisFieldName, specAnalysisFieldValue);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(analysisInSpec[0].toString())) {
                        errorCode = "DataSample_SpecLimitNotFound";
                        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LPArray.joinTwo1DArraysInOneOf1DString(specAnalysisFieldName, specAnalysisFieldValue, ":")));
                        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
                    }
                }
            }
        }
        // Spec Business Rule. Allow other analyses. End
        //    String[] specialFields = getSpecialFields();
        //    String[] specialFieldsFunction = getSpecialFieldsFunction();
        String[] specialFields = labIntChecker.getStructureSpecialFields(schemaDataName, sampleLevel + "Structure", actionName);
        String[] specialFieldsFunction = labIntChecker.getStructureSpecialFieldsFunction(schemaDataName, sampleLevel + "Structure", actionName);
        for (Integer inumLines = 0; inumLines < fieldName.length; inumLines++) {
            String currField = tableName + "." + fieldName[inumLines];
            boolean contains = Arrays.asList(specialFields).contains(currField);
            if (contains) {
                specialFieldIndex = Arrays.asList(specialFields).indexOf(currField);
                String aMethod = specialFieldsFunction[specialFieldIndex];
                Method method = null;
                try {
                    Class<?>[] paramTypes = {Rdbms.class, String.class};
                    method = getClass().getDeclaredMethod(aMethod, paramTypes);
                } catch (NoSuchMethodException | SecurityException ex) {
                    errorCode = "DataSample_SpecialFunctionReturnedERROR";
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, currField);
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, aMethod);
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ex.getMessage());
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
                }
                Object specialFunctionReturn = null;
                try {
                    if (method != null) {
                        specialFunctionReturn = method.invoke(this, schemaPrefix);
                    }
                } catch (IllegalAccessException | NullPointerException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(DataSample.class.getName()).log(Level.SEVERE, null, ex);
                }
                if ((specialFunctionReturn == null) || (specialFunctionReturn != null && specialFunctionReturn.toString().contains("ERROR"))) {
                    errorCode = "DataSample_SpecialFunctionReturnedERROR";
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, currField);
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, aMethod);
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, LPNulls.replaceNull(specialFunctionReturn));
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
                }
            }
        }
        specialFieldIndex = 0;
        Object value = null;
        Object[] whereResultFieldValue = new Object[0];
        String[] whereResultFieldName = new String[0];
        String fieldNeed = DataSample.FIELDNAME_ANALYSIS;
        whereResultFieldName = LPArray.addValueToArray1D(whereResultFieldName, fieldNeed);
        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(fieldNeed);
        if (specialFieldIndex == -1) {
            specialFieldIndex = Arrays.asList(fieldName).indexOf(fieldNeed);
            if (specialFieldIndex == -1) {
                errorDetailVariables = new String[]{fieldNeed, Arrays.toString(mandatoryFields), schemaDataName};
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_DATA_SAMPLE_ANALYSIS_ADD_TO_SAMPLE_MISSING_MANDATORY_FIELDS, errorDetailVariables);
            }
            value = fieldValue[specialFieldIndex];
        } else {
            value = mandatoryFieldsValue[specialFieldIndex];
        }
        whereResultFieldValue = LPArray.addValueToArray1D(whereResultFieldValue, value);
        fieldNeed = FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME;
        whereResultFieldName = LPArray.addValueToArray1D(whereResultFieldName, fieldNeed);
        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(fieldNeed);
        if (specialFieldIndex == -1) {
            specialFieldIndex = Arrays.asList(fieldName).indexOf(fieldNeed);
            if (specialFieldIndex == -1) {
                errorDetailVariables = new String[]{fieldNeed, Arrays.toString(mandatoryFields), schemaDataName};
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_DATA_SAMPLE_ANALYSIS_ADD_TO_SAMPLE_MISSING_MANDATORY_FIELDS, errorDetailVariables);
            }
            value = fieldValue[specialFieldIndex];
        } else {
            value = mandatoryFieldsValue[specialFieldIndex];
        }
        whereResultFieldValue = LPArray.addValueToArray1D(whereResultFieldValue, value);
        fieldNeed = FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION;
        whereResultFieldName = LPArray.addValueToArray1D(whereResultFieldName, fieldNeed);
        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(fieldNeed);
        if (specialFieldIndex == -1) {
            specialFieldIndex = Arrays.asList(fieldName).indexOf(fieldNeed);
            if (specialFieldIndex == -1) {
                errorDetailVariables = new String[]{fieldNeed, Arrays.toString(mandatoryFields), schemaDataName};
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_DATA_SAMPLE_ANALYSIS_ADD_TO_SAMPLE_MISSING_MANDATORY_FIELDS, errorDetailVariables);
            }
            value = fieldValue[specialFieldIndex];
        } else {
            value = mandatoryFieldsValue[specialFieldIndex];
        }
        whereResultFieldValue = LPArray.addValueToArray1D(whereResultFieldValue, value);
        String[] getResultFields = new String[0];
        getResultFields = LPArray.addValueToArray1D(getResultFields, DataSampleAnalysisResult.FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_PARAM_NAME);
        getResultFields = LPArray.addValueToArray1D(getResultFields, "mandatory");
        getResultFields = LPArray.addValueToArray1D(getResultFields, DataSample.FIELDNAME_ANALYSIS);
        getResultFields = LPArray.addValueToArray1D(getResultFields, "param_type");
        getResultFields = LPArray.addValueToArray1D(getResultFields, "num_replicas");
        getResultFields = LPArray.addValueToArray1D(getResultFields, DataSampleAnalysisResult.FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM);
        getResultFields = LPArray.addValueToArray1D(getResultFields, DataSampleAnalysisResult.FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM_CONVERSION_MODE);
        Object[][] resultFieldRecords = Rdbms.getRecordFieldsByFilter(schemaConfigName, "analysis_method_params", whereResultFieldName, whereResultFieldValue, getResultFields);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(resultFieldRecords[0][0].toString())) {
            errorCode = "DataSample_AnalysisMethodParamsNotFound";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LPArray.joinTwo1DArraysInOneOf1DString(whereResultFieldName, whereResultFieldValue, ":")));
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }
        resultFieldRecords = LPArray.addColumnToArray2D(resultFieldRecords, sampleId);
        getResultFields = LPArray.addValueToArray1D(getResultFields, DataSample.FIELDNAME_SAMPLE_ID);
        resultFieldRecords = LPArray.addColumnToArray2D(resultFieldRecords, 0);
        getResultFields = LPArray.addValueToArray1D(getResultFields, DataSample.FIELDNAME_TEST_ID);
        // This is temporary !!!! ***************************************************************
        specialFieldIndex = Arrays.asList(getResultFields).indexOf(DataSample.FIELDNAME_STATUS);
        if (specialFieldIndex == -1) {
            firstStatus = Parameter.getParameterBundle(schemaDataName, "sampleAnalysisResult_statusFirst");
            resultFieldRecords = LPArray.addColumnToArray2D(resultFieldRecords, firstStatus);
            getResultFields = LPArray.addValueToArray1D(getResultFields, DataSample.FIELDNAME_STATUS);
        }
        // This is temporary !!!! ***************************************************************
        String[] resultMandatoryFields = mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, sampleLevel, actionName);
        String[] resultDefaulFields = labIntChecker.getTableFieldsDefaulValues(schemaDataName, tableName, actionName);
        Object[] resultDefaulFieldValue = labIntChecker.getTableFieldsDefaulValues(schemaDataName, tableName, actionName);
        Object[] resultMandatoryFieldsValue = new Object[resultMandatoryFields.length];
        String resultMandatoryFieldsMissing = "";
        for (Integer inumLines = 0; inumLines < resultMandatoryFieldsValue.length; inumLines++) {
            String currField = resultMandatoryFields[inumLines];
            boolean contains = Arrays.asList(getResultFields).contains(currField.toLowerCase());
            if (!contains) {
                Integer valuePosic = Arrays.asList(resultDefaulFields).indexOf(currField.toLowerCase());
                if (valuePosic == -1) {
                    if (resultMandatoryFieldsMissing.length() > 0) {
                        resultMandatoryFieldsMissing = resultMandatoryFieldsMissing + ",";
                    }
                    resultMandatoryFieldsMissing = resultMandatoryFieldsMissing + currField;
                } else {
                    Object currFieldValue = resultDefaulFieldValue[valuePosic];
                    resultFieldRecords = LPArray.addColumnToArray2D(resultFieldRecords, currFieldValue);
                    getResultFields = LPArray.addValueToArray1D(getResultFields, currField);
                }
            }
        }
        if (resultMandatoryFieldsMissing.length() > 0) {
            errorCode = "DataSample_MissingMandatoryFields";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }
        fieldName = LPArray.addValueToArray1D(fieldName, DataSample.FIELDNAME_SAMPLE_ID);
        fieldValue = LPArray.addValueToArray1D(fieldValue, sampleId);
        fieldName = LPArray.addValueToArray1D(fieldName, "added_on");
        fieldValue = LPArray.addValueToArray1D(fieldValue, Rdbms.getCurrentDate());
        fieldName = LPArray.addValueToArray1D(fieldName, "added_by");
        fieldValue = LPArray.addValueToArray1D(fieldValue, userName);
        String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(fieldName, fieldValue, ":");
        Object[] diagnoses = Rdbms.insertRecordInTable(schemaDataName, sampleLevel + "_analysis", fieldName, fieldValue);
        Integer testId = Integer.parseInt(diagnoses[diagnoses.length - 1].toString());
        SampleAudit smpAudit = new SampleAudit();
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, sampleLevel + "_analysis", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
        Integer valuePosic = Arrays.asList(getResultFields).indexOf(DataSample.FIELDNAME_TEST_ID);
        if (valuePosic == -1) {
            resultFieldRecords = LPArray.addColumnToArray2D(resultFieldRecords, testId);
            getResultFields = LPArray.addValueToArray1D(getResultFields, DataSample.FIELDNAME_TEST_ID);
        } else {
            resultFieldRecords = LPArray.setColumnValueToArray2D(resultFieldRecords, valuePosic, testId);
        }
        valuePosic = Arrays.asList(getResultFields).indexOf(FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME);
        if (valuePosic == -1) {
            resultFieldRecords = LPArray.addColumnToArray2D(resultFieldRecords, fieldValue[Arrays.asList(fieldName).indexOf(FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME)]);
            getResultFields = LPArray.addValueToArray1D(getResultFields, FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME);
        }
        valuePosic = Arrays.asList(getResultFields).indexOf(FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION);
        if (valuePosic == -1) {
            resultFieldRecords = LPArray.addColumnToArray2D(resultFieldRecords, fieldValue[Arrays.asList(fieldName).indexOf(FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION)]);
            getResultFields = LPArray.addValueToArray1D(getResultFields, FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION);
        }
        for (Object[] resultFieldRecord : resultFieldRecords) {
            Object[] fieldVal = new Object[0];
            for (int col = 0; col < resultFieldRecords[0].length; col++) {
                fieldVal = LPArray.addValueToArray1D(fieldVal, resultFieldRecord[col]);
            }
            valuePosic = Arrays.asList(getResultFields).indexOf("num_replicas");
            Integer numReplicas = 1;
            String resultReplicaFieldName = "replica";
            if (valuePosic == -1) {
                valuePosic = Arrays.asList(getResultFields).indexOf("replica");
                if (valuePosic == -1) {
                    getResultFields = LPArray.addValueToArray1D(getResultFields, resultReplicaFieldName);
                    fieldVal = LPArray.addValueToArray1D(fieldVal, numReplicas);
                    valuePosic = fieldVal.length - 1;
                }
            } else {
                numReplicas = (Integer) fieldVal[valuePosic];
                getResultFields[valuePosic] = resultReplicaFieldName;
                if ((numReplicas == null) || (numReplicas == 0)) {
                    numReplicas = 1;
                    fieldVal[valuePosic] = 1;
                }
            }
            for (Integer iNumReps = 1; iNumReps <= numReplicas; iNumReps++) {
                fieldVal[valuePosic] = iNumReps;
                diagnoses = Rdbms.insertRecordInTable(schemaDataName, sampleLevel + "_analysis_result", getResultFields, fieldVal);
                fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(getResultFields, fieldVal, ":");
                Integer resultId = Integer.parseInt(diagnoses[diagnoses.length - 1].toString());
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, sampleLevel + "_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
            }
        }
        //String[] diagnoses2 = sampleAnalysisEvaluateStatus(schemaPrefix, userName, sampleId, testId);
        Object[] diagnoses3 = DataSample.sampleEvaluateStatus(schemaPrefix, userName, sampleId, auditActionName, userRole);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses3[0].toString())) {
            return diagnoses3;
        }
        errorCode = "DataSample_SampleAnalysisAddedSuccessfully";
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "");
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, testId.toString());
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);
    }
    
}
