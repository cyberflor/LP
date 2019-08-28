/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sampleStructure;

import LabPLANET.utilities.LPNulls;
import LabPLANET.utilities.LPParadigm;
import databases.Rdbms;
import functionalJava.audit.SampleAudit;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPDate;
import LabPLANET.utilities.LPPlatform;
import static LabPLANET.utilities.LPPlatform.trapErrorMessage;
import LabPLANET.utilities.LPMath;
import com.sun.rowset.CachedRowSetImpl;
import databases.DataDataIntegrity;
import functionalJava.ChangeOfCustody.ChangeOfCustody;
import functionalJava.parameter.Parameter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class DataSample {
    
    public static final String CONFIG_SAMPLE_STATUSREVIEWED="sample_statusReviewed";
    public static final String CONFIG_SAMPLE_STATUSCANCELED="sample_statusCanceled";            

    
    public static final String TABLENAME_CONFIG_SAMPLE_RULES= "sample_rules";   
        public static final String FIELDNAME_CONFIG_SAMPLE_RULES_ANALYST_ASSIGNMENT_MODE = "analyst_assignment_mode";
    
    public static final String TABLENAME_DATA_SAMPLE= "sample";    
        public static final String FIELDNAME_ALIQUOTID = "aliquot_id";
        public static final String FIELDNAME_ANALYSIS = "analysis";
        public static final String FIELDNAME_ANALYST = "analyst";
        public static final String FIELDNAME_ENTEREDBY="entered_by";
        public static final String FIELDNAME_ENTEREDON="entered_on";
        public static final String FIELDNAME_CODE ="code";
        public static final String FIELDNAME_CODE_VERSION= "code_version";
        public static final String FIELDNAME_DATA_INCUBATION_END= "incubation_end";
        public static final String FIELDNAME_DATA_INCUBATION_PASSED= "incubation_passed";
        public static final String FIELDNAME_DATA_INCUBATION_START= "incubation_start";
        public static final String FIELDNAME_DATA_RECEIVED_BY= "received_by";
        public static final String FIELDNAME_DATA_RECEIVED_ON= "received_on";
        public static final String FIELDNAME_SAMPLE_CONFIG_CODE= "sample_config_code";
        public static final String FIELDNAME_SAMPLE_CONFIG_CODE_VERSION= "sample_config_code_version";
        public static final String FIELDNAME_SAMPLE_ID="sample_id";
        public static final String FIELDNAME_DATA_SAMPLING_COMMENT= "sampling_comment";
        public static final String FIELDNAME_DATA_SAMPLING_DATE= "sampling_date";
        public static final String FIELDNAME_SPEC_CODE ="spec_code";
        public static final String FIELDNAME_SPEC_CODE_VERSION= "spec_code_version";
        public static final String FIELDNAME_SPEC_VARIATION_NAME="spec_variation_name";
        public static final String FIELDNAME_SPEC_EVAL="spec_eval";
        public static final String FIELDNAME_STATUS = "status";
        public static final String FIELDNAME_STATUS_PREVIOUS = "status_previous";
        public static final String FIELDNAME_TEST_ID="test_id";
        public static final String FIELDNAME_RESULT_ID="result_id";
        public static final String FIELDNAME_VARIATION_NAME= "variation_name";
        public static final String FIELDNAME_VOLUME_FOR_ALIQ="volume_for_aliq";
        public static final String FIELDNAME_VOLUME_FOR_ALIQ_UOM="volume_for_aliq_uom";

        
    
    
    public static final String DIAGNOSES_SUCCESS = "SUCCESS";
    
    private static final String ERROR_TRAPPING_ERROR_INSERTING_SAMPLE_RECORD = "DataSample_errorInsertingSampleRecord";
    static final String ERROR_TRAPPING_DATA_SAMPLE_NOT_FOUND = "DataSample_SampleNotFound";

    String classVersion = "0.1";
    String errorCode ="";
    Object[] errorDetailVariables= new Object[0];
    
    String[] mandatoryFields = null;
    Object[] mandatoryFieldsValue = null;

    DataDataIntegrity labIntChecker = new DataDataIntegrity();

    

    /**
     * Este es el constructor para DataSample
     * @param grouperName
     */
    public DataSample(){
    }
    

    /**
     *
     */
    public void logSampleBySchedule(){
        // Not implemented yet
    }

    /**
     *
     * @param schemaPrefix
     * @param sampleTemplate
     * @param sampleTemplateVersion
     * @param sampleFieldName
     * @param sampleFieldValue
     * @param userName
     * @param appSessionId
     * @param userRole
     * @return
     */
    public Object[] logSampleDev( String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Integer appSessionId) {
        return logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, appSessionId, true, 1);
    }

    /**
     *
     * @param schemaPrefix
     * @param sampleTemplate
     * @param sampleTemplateVersion
     * @param sampleFieldName
     * @param sampleFieldValue
     * @param userName
     * @param userRole
     * @param appSessionId
     * @return
     */
    public Object[] logSample(String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Integer appSessionId) {
        return logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, appSessionId, false, 1);
    }

    /**
     *
     * @param schemaPrefix
     * @param sampleTemplate
     * @param sampleTemplateVersion
     * @param sampleFieldName
     * @param sampleFieldValue
     * @param userName
     * @param userRole
     * @param appSessionId
     * @param numSamplesToLog
     * @return
     */
    public Object[] logSample(String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Integer appSessionId, Integer numSamplesToLog) {
        return logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, appSessionId, false, numSamplesToLog);
    }


Object[] logSample( String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Integer appSessionId, Boolean devMode, Integer numSamplesToLog) {
    Object[] diagnoses = new Object[7];
        String tableName = TABLENAME_DATA_SAMPLE; 
        String actionName = "Insert";
        String auditActionName = "LOG_SAMPLE";
        
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);    
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG); 
        
        String sampleLevel = tableName;
//        if (this.getSampleGrouper()!=null){sampleLevel=this.getSampleGrouper()+"_"+sampleLevel;}

        mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, sampleLevel, actionName);
        
        String sampleStatusFirst = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), sampleLevel+"_statusFirst");     

        sampleFieldName = LPArray.addValueToArray1D(sampleFieldName, FIELDNAME_STATUS);
        sampleFieldValue = LPArray.addValueToArray1D(sampleFieldValue, sampleStatusFirst);
        // mandatoryFields = getSampleMandatoryFields();
        Object[] fieldNameValueArrayChecker = LPParadigm.fieldNameValueArrayChecker(sampleFieldName, sampleFieldValue);
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(fieldNameValueArrayChecker[0].toString())){return fieldNameValueArrayChecker;}        
        // spec is not mandatory but when any of the fields involved is added to the parameters 
        //  then it turns mandatory all the fields required for linking this entity.
        Integer fieldIndexSpecCode = Arrays.asList(sampleFieldName).indexOf(FIELDNAME_SPEC_CODE);
        Integer fieldIndexSpecCodeVersion = Arrays.asList(sampleFieldName).indexOf(FIELDNAME_SPEC_CODE_VERSION);
        Integer fieldIndexSpecVariationName = Arrays.asList(sampleFieldName).indexOf(FIELDNAME_SPEC_VARIATION_NAME);
        if ((fieldIndexSpecCode!=-1) || (fieldIndexSpecCodeVersion!=-1) || (fieldIndexSpecVariationName!=-1)){
            mandatoryFields = LPArray.addValueToArray1D(mandatoryFields, FIELDNAME_SPEC_CODE);
            mandatoryFields = LPArray.addValueToArray1D(mandatoryFields, FIELDNAME_SPEC_CODE_VERSION);
            mandatoryFields = LPArray.addValueToArray1D(mandatoryFields, FIELDNAME_SPEC_VARIATION_NAME);
        }

        mandatoryFieldsValue = new Object[mandatoryFields.length];
        String mandatoryFieldsMissing = "";
        for (Integer inumLines=0;inumLines<mandatoryFields.length;inumLines++){
            String currField = mandatoryFields[inumLines];
            boolean contains = Arrays.asList(sampleFieldName).contains(currField.toLowerCase());
            if (!contains){
                Object[] sampleDefaultFieldValues = labIntChecker.getTableFieldsDefaulValues(schemaDataName, sampleLevel, actionName);                
                if (mandatoryFieldsMissing.length()>0){mandatoryFieldsMissing = mandatoryFieldsMissing + ",";}
                mandatoryFieldsMissing = mandatoryFieldsMissing + currField;
            }else{
                Integer valuePosic = Arrays.asList(sampleFieldName).indexOf(currField);
                mandatoryFieldsValue[inumLines] = sampleFieldValue[valuePosic]; 
                if (FIELDNAME_SAMPLE_CONFIG_CODE.equals(currField)){String configCode = sampleFieldValue[Arrays.asList(sampleFieldName).indexOf(currField)].toString();}
            }        
        }            
        if (mandatoryFieldsMissing.length()>0){
           errorCode = "DataSample_MissingMandatoryFields";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);    
        }               
        
        //schemaConfigName = "em-demo-a-config";
        //Object[][] diagnosis = Rdbms.getRecordFieldsByFilter(schemaConfigName, tableName, new String[]{FIELDNAME_CODE,FIELDNAME_CODE_VERSION}, new Object[]{sampleTemplate, sampleTemplateVersion}, new String[]{FIELDNAME_CODE,FIELDNAME_CODE_VERSION});
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, tableName, new String[]{FIELDNAME_CODE,FIELDNAME_CODE_VERSION}, new Object[]{sampleTemplate, sampleTemplateVersion});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnosis[0].toString())){
           errorCode = "DataSample_MissingConfigCode";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleTemplate);
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleTemplateVersion);
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaConfigName);
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, diagnosis[5]);
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);    
        }
        //String[] specialFields = getSpecialFields();
        //String[] specialFieldsFunction = getSpecialFieldsFunction();
        String[] specialFields = labIntChecker.getStructureSpecialFields(schemaDataName, sampleLevel+"_"+"sampleStructure", actionName);
        String[] specialFieldsFunction = labIntChecker.getStructureSpecialFieldsFunction(schemaDataName, sampleLevel+"_"+"sampleStructure", actionName);
        Integer specialFieldIndex = -1;
        
        for (Integer inumLines=0;inumLines<sampleFieldName.length;inumLines++){
            String currField = tableName+"." + sampleFieldName[inumLines];
            boolean contains = Arrays.asList(specialFields).contains(currField);
            if (contains){                    
                    specialFieldIndex = Arrays.asList(specialFields).indexOf(currField);
                    String aMethod = specialFieldsFunction[specialFieldIndex];
                    Method method = null;
                    try {
                        Class<?>[] paramTypes = {Rdbms.class, String[].class, String.class, String.class, Integer.class};
                        method = getClass().getDeclaredMethod(aMethod, paramTypes);
                    } catch (NoSuchMethodException | SecurityException ex) {
                            errorCode = "LabPLANETPlatform_SpecialFunctionReturnedEXCEPTION";
                            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ex.getMessage());
                            return trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
                    }
                    Object specialFunctionReturn=null;      
                    try {
                        if (method!=null){ specialFunctionReturn = method.invoke(this, null, schemaPrefix, sampleTemplate, sampleTemplateVersion);}
                    } catch (IllegalAccessException | NullPointerException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(DataSample.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if ( (specialFunctionReturn==null) || (specialFunctionReturn!=null && specialFunctionReturn.toString().contains("ERROR")) ){
                        errorCode = "DataSample_SpecialFunctionReturnedERROR";
                        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, currField);
                        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, aMethod);
                        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, LPNulls.replaceNull(specialFunctionReturn));
                        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                            
                    } 
            }
        }        
        sampleFieldName = LPArray.addValueToArray1D(sampleFieldName, FIELDNAME_SAMPLE_CONFIG_CODE);    
        sampleFieldValue = LPArray.addValueToArray1D(sampleFieldValue, sampleTemplate);
        sampleFieldName = LPArray.addValueToArray1D(sampleFieldName, FIELDNAME_SAMPLE_CONFIG_CODE_VERSION);    
        sampleFieldValue = LPArray.addValueToArray1D(sampleFieldValue, sampleTemplateVersion); 
        
        if (LPArray.valuePosicInArray(sampleFieldName, "custodian")==-1){
            ChangeOfCustody coc = new ChangeOfCustody();
            Object[] changeOfCustodyEnable = coc.isChangeOfCustodyEnable(schemaDataName, tableName);
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(changeOfCustodyEnable[0].toString())){
                sampleFieldName = LPArray.addValueToArray1D(sampleFieldName, "custodian");    
                sampleFieldValue = LPArray.addValueToArray1D(sampleFieldValue, userName);             
            }
        }
        
        
        if (numSamplesToLog==null){numSamplesToLog=1;}
        
        for (int iNumSamplesToLog=0; iNumSamplesToLog<numSamplesToLog; iNumSamplesToLog++ ){        
    
            tableName =TABLENAME_DATA_SAMPLE;

            diagnoses = Rdbms.insertRecordInTable(schemaDataName, tableName, sampleFieldName, sampleFieldValue);
            if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                errorCode = ERROR_TRAPPING_ERROR_INSERTING_SAMPLE_RECORD;
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, diagnoses[diagnoses.length-2]);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
            }                                

            Object[] fieldsOnLogSample = LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");
            diagnoses = LPArray.addValueToArray1D(diagnoses, diagnoses[diagnoses.length-1]);

            if (Rdbms.TBL_NO_KEY.equalsIgnoreCase(diagnoses[diagnoses.length-1].toString())){return diagnoses;}
            
            Integer sampleId = Integer.parseInt(diagnoses[diagnoses.length-1].toString());
            
            SampleAudit smpAudit = new SampleAudit();            
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, tableName, sampleId, 
                    sampleId, null, null, 
                    fieldsOnLogSample, userName, userRole, appSessionId);
            Integer transactionId = null;
            DataSampleAnalysis dataSmpAna = new DataSampleAnalysis();
            dataSmpAna.autoSampleAnalysisAdd(schemaPrefix, sampleId, userName, userRole, sampleFieldName, sampleFieldValue, "LOGGED", appSessionId, transactionId);
            
            autoSampleAliquoting(schemaPrefix, sampleId, userName, userRole, sampleFieldName, sampleFieldValue, "LOGGED", appSessionId, transactionId);
            
        }
        return diagnoses;  
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param userRole
     * @param appSessionId
     * @return
     */
    public Object[] sampleReception( String schemaPrefix, String userName, Integer sampleId, String userRole, Integer appSessionId) {
        String tableName = TABLENAME_DATA_SAMPLE; 
        String receptionStatus = "RECEIVED";
        String auditActionName = "SAMPLE_RECEPTION";
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA); 
    
        Object[][] currSampleStatus = Rdbms.getRecordFieldsByFilter(schemaDataName, TABLENAME_DATA_SAMPLE, new String[]{FIELDNAME_SAMPLE_ID}, 
                                                    new Object[]{sampleId}, new String[]{FIELDNAME_STATUS, FIELDNAME_DATA_RECEIVED_BY,FIELDNAME_DATA_RECEIVED_ON, FIELDNAME_STATUS});
        if (LPPlatform.LAB_FALSE==currSampleStatus[0][0]){
            errorCode = ERROR_TRAPPING_DATA_SAMPLE_NOT_FOUND;
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleId);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }
        if (currSampleStatus[0][1]!=null){ 
            errorCode = "DataSample_SampleAlreadyReceived";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, currSampleStatus[0][2]);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }
        String currentStatus = (String) currSampleStatus[0][0];

        String[] sampleFieldName = new String[4];    
        Object[] sampleFieldValue = new Object[4];
        Integer iField = 0;
        sampleFieldName[iField] = FIELDNAME_STATUS;                     sampleFieldValue[iField] = receptionStatus;
        iField++;
        sampleFieldName[iField] = FIELDNAME_STATUS_PREVIOUS;        sampleFieldValue[iField] = currentStatus;
        iField++;    
        sampleFieldName[iField] = FIELDNAME_DATA_RECEIVED_BY;                         sampleFieldValue[iField] = userName;
        iField++;
        sampleFieldName[iField] = FIELDNAME_DATA_RECEIVED_ON;                         sampleFieldValue[iField] = (new Date(System.currentTimeMillis()));
        iField++;

        Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, 
                                                new String[] {FIELDNAME_SAMPLE_ID}, new Object[]{sampleId});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
            String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);

            SampleAudit smpAudit = new SampleAudit();       
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE, sampleId, sampleId, null, null, fieldsForAudit, userName, userRole, appSessionId);
        }    
        return diagnoses;
}

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param newDate
     * @param userRole
     * @return
     */
    public Object[] changeSamplingDate( String schemaPrefix, String userName, Integer sampleId, Date newDate, String userRole){
        String tableName = TABLENAME_DATA_SAMPLE; 
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA); 

        String auditActionName = "SAMPLE_CHANGE_SAMPLING_DATE";

        String[] sampleFieldName = new String[1];
        Object[] sampleFieldValue = new Object[1];
        Integer iField = 0;

        sampleFieldName[iField] = FIELDNAME_DATA_SAMPLING_DATE;    
        sampleFieldValue[iField] = newDate;
        iField++;

        Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {FIELDNAME_SAMPLE_ID}, new Object[]{sampleId});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
            errorCode = "DataSample_SamplingDateChangedSuccessfully";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));        
            diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);

            String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);

            SampleAudit smpAudit = new SampleAudit();
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE, sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
        }    
        return diagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param comment
     * @param userRole
     * @return
     */
    public Object[] sampleReceptionCommentAdd( String schemaPrefix, String userName, Integer sampleId, String comment, String userRole){
        String tableName = TABLENAME_DATA_SAMPLE; 
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA); 

        String auditActionName = "SAMPLE_RECEPTION_COMMENT_ADD";

        String[] sampleFieldName = new String[1];
        Object[] sampleFieldValue = new Object[1];
        Integer iField = 0;

        sampleFieldName[iField] = FIELDNAME_DATA_SAMPLING_COMMENT;    
        sampleFieldValue[iField] = comment;
        iField++;

        Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {FIELDNAME_SAMPLE_ID}, new Object[]{sampleId});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){        
            errorCode = "DataSample_SampleReceptionCommentAdd";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleId);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
            diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);                

            String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
            SampleAudit smpAudit = new SampleAudit();
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE, sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
        } 
        return diagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param comment
     * @param userRole
     * @return
     */
    public Object[] sampleReceptionCommentRemove( String schemaPrefix, String userName, Integer sampleId, String comment, String userRole) {
        String tableName = TABLENAME_DATA_SAMPLE; 
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA); 

        String auditActionName = "SAMPLE_RECEPTION_COMMENT_REMOVE";

        String[] sampleFieldName = new String[1];
        Object[] sampleFieldValue = new Object[1];
        Integer iField = 0;

        sampleFieldName[iField] = FIELDNAME_DATA_SAMPLING_COMMENT;    
        sampleFieldValue[iField] = "";
        iField++;

        Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {FIELDNAME_SAMPLE_ID}, new Object[]{sampleId});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
            errorCode = "DataSample_SampleReceptionCommentRemoved";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleId);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
            diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);                

            String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
            SampleAudit smpAudit = new SampleAudit();
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE, sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
        } 
        return diagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param userRole
     * @return
     */
    public Object[] setSampleStartIncubationDateTime( String schemaPrefix, String userName, Integer sampleId, String userRole) {
        String tableName = TABLENAME_DATA_SAMPLE; 
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA); 

        String auditActionName = "SAMPLE_SET_INCUBATION_START";

        String[] sampleFieldName = new String[2];
        Object[] sampleFieldValue = new Object[2];
        Integer iField = 0;

        sampleFieldName[iField] = FIELDNAME_DATA_INCUBATION_START;    
        sampleFieldValue[iField] = (new Date(System.currentTimeMillis()));
        iField++;

        sampleFieldName[iField] = FIELDNAME_DATA_INCUBATION_PASSED;    
        sampleFieldValue[iField] = false;
        iField++;

        Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {FIELDNAME_SAMPLE_ID}, new Object[]{sampleId});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
            errorCode = "DataSample_SampleIncubationStartedSuccessfully";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleId);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
            diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);                

            String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
            SampleAudit smpAudit = new SampleAudit();
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE, sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
        } 
        return diagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param userRole
     * @return
     */
    public Object[] setSampleEndIncubationDateTime( String schemaPrefix, String userName, Integer sampleId, String userRole) {
        String tableName = TABLENAME_DATA_SAMPLE;     
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA); 
    
        String auditActionName = "SAMPLE_SET_INCUBATION_START";

        Object[][] incubationStart = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, 
                new String[]{FIELDNAME_SAMPLE_ID}, new Object[]{sampleId}, new String[] {FIELDNAME_DATA_INCUBATION_START, FIELDNAME_DATA_INCUBATION_PASSED});

        if (incubationStart[0][0]==null){        
            errorCode = "DataSample_SampleIncubationEnded_NotStartedYet";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleId);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }
        String[] sampleFieldName = new String[2];
        Object[] sampleFieldValue = new Object[2];
        Integer iField = 0;

        sampleFieldName[iField] = FIELDNAME_DATA_INCUBATION_END;    
        sampleFieldValue[iField] = (new Date(System.currentTimeMillis()));
        iField++;

        sampleFieldName[iField] = FIELDNAME_DATA_INCUBATION_PASSED;    
        sampleFieldValue[iField] = true;
        iField++;

        Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {FIELDNAME_SAMPLE_ID}, new Object[]{sampleId});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
            errorCode = "DataSample_SampleIncubationEndedSuccessfully";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleId);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
            diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);                

            String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
            SampleAudit smpAudit = new SampleAudit();
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE, sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
        } 
        return diagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param testId
     * @param analyst
     * @param userRole
     */
    public void _sampleAssignAnalyst( String schemaPrefix, String userName, Integer testId, String analyst, String userRole){
        // Not implemented yet
    }

        

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param parentAuditAction
     * @param userRole
     * @return
     */
    public static Object[] sampleEvaluateStatus( String schemaPrefix, String userName, Integer sampleId, String parentAuditAction, String userRole){ 
    
    String auditActionName = "SAMPLE_EVALUATE_STATUS";
    if (parentAuditAction!=null){auditActionName = parentAuditAction + ":"+auditActionName;}

    String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA); 
    String tableName = DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS;      
        
    String sampleStatusFirst = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusFirst");
    String sampleStatusInReceived = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusReceived");
    
    Object[][] sampleInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, TABLENAME_DATA_SAMPLE, new String[]{FIELDNAME_SAMPLE_ID},
            new Object[]{sampleId}, new String[]{FIELDNAME_STATUS});
    if ( (sampleStatusFirst.equalsIgnoreCase(sampleInfo[0][0].toString())) || (sampleStatusInReceived.equalsIgnoreCase(sampleInfo[0][0].toString()))){
        String[] fieldsForAudit = new String[0];
        fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, FIELDNAME_STATUS+": keep status "+sampleInfo[0][0].toString());
        SampleAudit smpAudit = new SampleAudit();        
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE, sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);              
        return new Object[]{LPPlatform.LAB_TRUE};
    }
    
    String sampleStatusIncomplete = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusIncomplete");
    String sampleStatusComplete = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusComplete");
    
    String smpAnaNewStatus="";
    
    Object[] diagnoses =  Rdbms.existsRecord(schemaDataName, DataSampleAnalysis.TABLENAME_DATA_SAMPLE_ANALYSIS, 
                                        new String[]{FIELDNAME_SAMPLE_ID,"status in|"}, 
                                        new Object[]{sampleId, "NOT_STARTED|STARTED|INCOMPLETE"});
    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){smpAnaNewStatus=sampleStatusIncomplete;}
    else{smpAnaNewStatus=sampleStatusComplete;}
    
    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, TABLENAME_DATA_SAMPLE, 
            new String[]{FIELDNAME_STATUS}, 
            new Object[]{smpAnaNewStatus},
            new String[]{FIELDNAME_SAMPLE_ID},
            new Object[]{sampleId});
    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
        String[] fieldsForAudit = new String[0];
        fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, FIELDNAME_STATUS+":"+smpAnaNewStatus);
        SampleAudit smpAudit = new SampleAudit();        
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, TABLENAME_DATA_SAMPLE, sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);        
    }      
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
     * @return
     */
    public Object[] sampleResultReview( String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole){
        
        Object[] diagnoses = new Object[7];
        String tableName = DataSampleAnalysisResult.TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT;  
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA); 
            
        String sampleAnalysisResultStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSampleAnalysisResult.CONFIG_SAMPLEANALYSISRESULT_STATUSCANCELED);
        String sampleAnalysisResultStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSampleAnalysisResult.CONFIG_SAMPLEANALYSISRESULT_STATUSREVIEWED);
        
        Object[] samplesToReview = new Object[0];
        Object[] testsToReview = new Object[0];
        Object[] testsSampleToReview = new Object[0];
        
        String[] fieldsToRetrieve = new String[]{FIELDNAME_STATUS,FIELDNAME_RESULT_ID,FIELDNAME_TEST_ID, FIELDNAME_SAMPLE_ID};
        
        String cancelScope = ""; 
        Integer cancelScopeId = 0;
        if (sampleId!=null ){cancelScope = FIELDNAME_SAMPLE_ID; cancelScopeId=sampleId; }
        if (testId!=null ){cancelScope = FIELDNAME_TEST_ID; cancelScopeId=testId; }
        if (resultId!=null){cancelScope = FIELDNAME_RESULT_ID; cancelScopeId=resultId;}
        Object[][] objectInfo = null;
        objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, 
                                                            new String[]{cancelScope}, new Object[]{cancelScopeId}, fieldsToRetrieve);
        if (objectInfo.length==0){
            errorCode = "DataSample_SampleAnalysisResultNotFound";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, resultId.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                  
        }else{
            for (Integer iResToCancel=0;iResToCancel<objectInfo.length;iResToCancel++){
                String currStatus = (String) objectInfo[iResToCancel][0];
                if (!(sampleAnalysisResultStatusCanceled.equalsIgnoreCase(currStatus))){    
                resultId = (Integer) objectInfo[iResToCancel][1];
                testId = (Integer) objectInfo[iResToCancel][2];
                sampleId = (Integer) objectInfo[iResToCancel][3];  
                if (!(sampleAnalysisResultStatusReviewed.equalsIgnoreCase(currStatus))){    
                    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, DataSampleAnalysisResult.TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, 
                                                                        new String[]{FIELDNAME_STATUS, FIELDNAME_STATUS_PREVIOUS}, new Object[]{sampleAnalysisResultStatusReviewed, currStatus}, 
                                                                        new String[]{FIELDNAME_RESULT_ID, FIELDNAME_STATUS}, new Object[]{resultId, "<>"+sampleAnalysisResultStatusCanceled});
                    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, FIELDNAME_STATUS+":"+sampleAnalysisResultStatusReviewed);
                        fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, FIELDNAME_STATUS_PREVIOUS+":"+currStatus);
                        SampleAudit smpAudit = new SampleAudit();
                        smpAudit.sampleAuditAdd(schemaPrefix, "REVIEW_RESULT", DataSampleAnalysisResult.TABLENAME_DATA_SAMPLE_ANALYSIS_RESULT, resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);        
                    }else{
                        return diagnoses;
                    }                        
                }else{
                    errorCode = "DataSample_SampleAnalysisResultNotReviable";
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, resultId.toString());
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleAnalysisResultStatusReviewed);                    
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                  
                     }    
                }
                if ((cancelScope.equalsIgnoreCase(FIELDNAME_SAMPLE_ID)) && (!LPArray.valueInArray(samplesToReview, sampleId)))
                        {samplesToReview = LPArray.addValueToArray1D(samplesToReview, sampleId);}
                if ((cancelScope.equalsIgnoreCase(FIELDNAME_SAMPLE_ID) || cancelScope.equalsIgnoreCase(FIELDNAME_TEST_ID)) && (!LPArray.valueInArray(testsToReview, testId)))
                    {testsToReview = LPArray.addValueToArray1D(testsToReview, testId);
                     testsSampleToReview = LPArray.addValueToArray1D(testsSampleToReview, sampleId);
                    }
            }    
        }    
        return diagnoses;
    }
    
    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param userRole
     * @param sampleId
     */
    public void sampleReview( String schemaPrefix, String userName, String userRole, Integer sampleId){
        Object[] diagnoses = new Object[7];
        String tableName = TABLENAME_DATA_SAMPLE;  
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA); 
            
        String sampleAnalysisStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSampleAnalysis.CONFIG_SAMPLEANALYSIS_STATUSCANCELED);
        String sampleAnalysisStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), DataSampleAnalysis.CONFIG_SAMPLEANALYSIS_STATUSREVIEWED);
        String sampleStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLE_STATUSCANCELED);
        String sampleStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), CONFIG_SAMPLE_STATUSREVIEWED);
        
        Object[][] objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, 
                                                            new String[]{FIELDNAME_SAMPLE_ID},
                                                            new Object[]{sampleId},
                                                            new String[]{FIELDNAME_STATUS,FIELDNAME_STATUS_PREVIOUS,FIELDNAME_SAMPLE_ID, FIELDNAME_SAMPLE_ID});
        String currStatus = (String) objectInfo[0][0];               
        if ( (!(sampleStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleStatusReviewed.equalsIgnoreCase(currStatus))) && (sampleId!=null) ){
            diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, 
                                                                new String[]{FIELDNAME_STATUS, FIELDNAME_STATUS_PREVIOUS}, new Object[]{sampleStatusReviewed, currStatus}, 
                                                                new String[]{FIELDNAME_SAMPLE_ID}, new Object[]{sampleId});                                                        
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                String[] fieldsForAudit = new String[0];
                fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, FIELDNAME_STATUS+":"+sampleStatusCanceled);
                fieldsForAudit = LPArray.addValueToArray1D(fieldsForAudit, FIELDNAME_STATUS_PREVIOUS+":"+currStatus);
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd(schemaPrefix, "REVIEW_SAMPLE", tableName, sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);                            
            }                        
        }else{
            errorCode = "DataSample_SampleNotReviewable";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, LPNulls.replaceNull(sampleId));
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, currStatus);                    
            diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                       
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
     * @param parameters
     * @param schemaPrefix
     * @param template
     * @param templateVersion
     * @return
     */
    
    public String specialFieldCheckSampleStatus( String[] parameters, String schemaPrefix, String template, Integer templateVersion){ //, String schemaPrefix, String analysisList){                        

        String myDiagnoses = "";        
        String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
        
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
        
        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(FIELDNAME_STATUS);
        String status = mandatoryFieldsValue[specialFieldIndex].toString();     
        if (status.length()==0){myDiagnoses = "ERROR: The parameter status cannot be null"; return myDiagnoses;}
        
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, TABLENAME_CONFIG_SAMPLE_RULES, new String[] {FIELDNAME_CODE, FIELDNAME_CODE_VERSION}, new Object[] {template, templateVersion});
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnosis[0].toString())){
            myDiagnoses = "ERROR: The sample_rule record for "+template+" does not exist in schema"+schemaConfigName+". ERROR: "+diagnosis[5];}
        else{    
            String[] fieldNames = new String[1];
            Object[] fieldValues = new Object[1];

            fieldNames[0]=FIELDNAME_CODE;
            fieldValues[0]=template;
            
            String[] fieldFilter = new String[] {FIELDNAME_CODE, FIELDNAME_CODE_VERSION, "statuses", "default_status"};
            
            Object[][] records = Rdbms.getRecordFieldsByFilter(schemaConfigName, TABLENAME_CONFIG_SAMPLE_RULES, fieldNames, fieldValues, fieldFilter);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(records[0][0].toString())){
                myDiagnoses = "ERROR: Problem on getting sample rules for " + template + " exists but the rule record is missing in the schema "+schemaConfigName;            
                return myDiagnoses;
            }
            
            
            String statuses = records[0][2].toString();
            
            if (LPArray.valueInArray(statuses.split("\\|", -1), status)){
                myDiagnoses = DIAGNOSES_SUCCESS;                            
            }else{
                myDiagnoses = "ERROR: The status " + status + " is not of one the defined status (" + statuses + " for the template " + template + " exists but the rule record is missing in the schema "+schemaConfigName;                                            
            }            
        }        
        return myDiagnoses;
    }

    /**
     *
     * @param parameters
     * @param schemaPrefix
     * @param template
     * @param templateVersion
     * @return
     */
    public String specialFieldCheckSampleSpecCode( String[] parameters, String schemaPrefix, String template, Integer templateVersion){ 

        String myDiagnoses = "";        
        String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
        
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 

        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(FIELDNAME_SPEC_CODE);
        String specCode = (String) mandatoryFieldsValue[specialFieldIndex];     
        if (specCode.length()==0){myDiagnoses = "ERROR: The parameter spec_code cannot be null"; return myDiagnoses;}

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(FIELDNAME_SPEC_CODE_VERSION);
        Integer specCodeVersion = (Integer) mandatoryFieldsValue[specialFieldIndex];     
        if (specCodeVersion==null){myDiagnoses = "ERROR: The parameter spec_code_version cannot be null"; return myDiagnoses;}

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(FIELDNAME_SPEC_VARIATION_NAME);
        String specVariationName = (String) mandatoryFieldsValue[specialFieldIndex];     
        if (specVariationName.length()==0){myDiagnoses = "ERROR: The parameter spec_variation_name cannot be null"; return myDiagnoses;}
                
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, "spec_limits", 
                new String[] {FIELDNAME_CODE, "config_version", FIELDNAME_VARIATION_NAME}, 
                new Object[] {specCode, specCodeVersion, specVariationName});
        
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnosis[0].toString())){
            myDiagnoses = "ERROR: The sample_rule record for "+template+" does not exist in schema"+schemaConfigName+". ERROR: "+diagnosis[5];
            return myDiagnoses;}
        
        myDiagnoses = DIAGNOSES_SUCCESS; 
        return myDiagnoses;}
    
    
        

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

    
    public void autoSampleAliquoting( String schemaPrefix, Integer sampleId, String userName, String userRole, String[] sampleFieldName, Object[] sampleFieldValue, String eventName, Integer appSessionId, Integer transactionId){
        LPParadigm.fieldNameValueArrayChecker(sampleFieldName, sampleFieldValue);
// This code is commented because the method, at least by now, return void instead of anything else        
//        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(fieldNameValueArrayChecker[0].toString())){}                
    }
       
public Object[] logSampleAliquot( String schemaPrefix, Integer sampleId, String[] smpAliqFieldName, Object[] smpAliqFieldValue, String userName, String userRole, Integer appSessionId) {    
    String parentTableName = TABLENAME_DATA_SAMPLE;
    String tableName = "sample_aliq";
    String auditActionName = "LOG_SAMPLE_ALIQUOT";

    String schemaDataName = LPPlatform.SCHEMA_DATA;
    String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
    
    Object[] fieldNameValueArrayChecker = LPParadigm.fieldNameValueArrayChecker(smpAliqFieldName, smpAliqFieldValue);
    if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(fieldNameValueArrayChecker[0].toString())){return fieldNameValueArrayChecker;}        

    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);    
    schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 

    BigDecimal aliqVolume = BigDecimal.ZERO;
    String aliqVolumeuom = "";
    
    String actionEnabledSampleAliquotVolumeRequired = Parameter.getParameterBundle(schemaPrefix.replace("\"", "")+"-data", "sampleAliquot_volumeRequired");   
    if (actionEnabledSampleAliquotVolumeRequired.toUpperCase().contains("ENABLE")){
        String[] mandatorySampleFields = new String[]{FIELDNAME_VOLUME_FOR_ALIQ, FIELDNAME_VOLUME_FOR_ALIQ_UOM};
        String[] mandatorySampleAliqFields = new String[]{"volume", "volume_uom"};
        Object[][] sampleInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, parentTableName, new String[] {FIELDNAME_SAMPLE_ID}, new Object[]{sampleId}, mandatorySampleFields);
        if ( (sampleInfo[0][0]!=null) && (LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleInfo[0][0].toString())) ){
            return LPArray.array2dTo1d(sampleInfo);}    

        if (sampleInfo[0][1]==null) {
            errorCode = "DataSample_sampleAliquoting_volumeCannotBeNegativeorZero";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "null");
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                 
        }          
        
        BigDecimal smpVolume = new BigDecimal(sampleInfo[0][0].toString());           
        String smpVolumeuom = (String) sampleInfo[0][1];  
        
        aliqVolume = new BigDecimal(smpAliqFieldValue[LPArray.valuePosicInArray(smpAliqFieldName, smpAliqFieldName[0])].toString());         
        aliqVolumeuom = (String) smpAliqFieldValue[LPArray.valuePosicInArray(mandatorySampleAliqFields, smpAliqFieldName[1])];
        
        Object[] diagnoses = LPMath.extractPortion(schemaPrefix, smpVolume, smpVolumeuom, sampleId, aliqVolume, aliqVolumeuom, -999);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses[0].toString())){return diagnoses;}    
        
        aliqVolume = new BigDecimal(diagnoses[diagnoses.length-1].toString());
        
        smpVolume = smpVolume.add(aliqVolume.negate());
        String[] smpVolFldName = new String[]{FIELDNAME_VOLUME_FOR_ALIQ};
        Object[] smpVolFldValue = new Object[]{smpVolume};
        Object[] updateSampleVolume = Rdbms.updateRecordFieldsByFilter(schemaDataName, parentTableName, 
                smpVolFldName, smpVolFldValue, new String[]{FIELDNAME_SAMPLE_ID}, new Object[]{sampleId});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(updateSampleVolume[0].toString())){
            return updateSampleVolume;}    
        SampleAudit smpAudit = new SampleAudit();
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, parentTableName, sampleId, 
                sampleId, null, null, 
                LPArray.joinTwo1DArraysInOneOf1DString(smpVolFldName, smpVolFldValue, ":"), userName, userRole, appSessionId);        
    }
    
    smpAliqFieldName = LPArray.addValueToArray1D(smpAliqFieldName, FIELDNAME_SAMPLE_ID);
    smpAliqFieldValue = LPArray.addValueToArray1D(smpAliqFieldValue, sampleId);
    smpAliqFieldName = LPArray.addValueToArray1D(smpAliqFieldName, FIELDNAME_VOLUME_FOR_ALIQ);
    smpAliqFieldValue = LPArray.addValueToArray1D(smpAliqFieldValue, aliqVolume);
    smpAliqFieldName = LPArray.addValueToArray1D(smpAliqFieldName, FIELDNAME_VOLUME_FOR_ALIQ_UOM);
    smpAliqFieldValue = LPArray.addValueToArray1D(smpAliqFieldValue, aliqVolumeuom);
    smpAliqFieldName = LPArray.addValueToArray1D(smpAliqFieldName, "created_by");
    smpAliqFieldValue = LPArray.addValueToArray1D(smpAliqFieldValue, userName);
    smpAliqFieldName = LPArray.addValueToArray1D(smpAliqFieldName, "created_on");  
    smpAliqFieldValue = LPArray.addValueToArray1D(smpAliqFieldValue, LPDate.getTimeStampLocalDate());

    Object[] diagnoses = Rdbms.insertRecordInTable(schemaDataName, tableName, smpAliqFieldName, smpAliqFieldValue);
    if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
        errorCode = ERROR_TRAPPING_ERROR_INSERTING_SAMPLE_RECORD;
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, diagnoses[diagnoses.length-2]);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
    }
    if (Rdbms.TBL_NO_KEY.equalsIgnoreCase(diagnoses[diagnoses.length-1].toString())){
        errorCode = "Object created but aliquot id cannot be get back to continue with the logic";
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
    }
    Integer aliquotId = Integer.parseInt(diagnoses[diagnoses.length-1].toString());
    Object[] fieldsOnLogSample = LPArray.joinTwo1DArraysInOneOf1DString(smpAliqFieldName, smpAliqFieldValue, ":");
    SampleAudit smpAudit = new SampleAudit();
    smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, tableName, aliquotId, aliquotId,
            sampleId, null, null, 
            fieldsOnLogSample, userName, userRole, appSessionId);
    Integer transactionId = null;

    return diagnoses;
    }

public Object[] logSampleSubAliquot( String schemaPrefix, Integer aliquotId, String[] smpSubAliqFieldName, Object[] smpSubAliqFieldValue, String userName, String userRole, Integer appSessionId) {
    String parentTableName = "sample_aliq";
    String tableName = "sample_aliq_sub";
    String auditActionName = "LOG_SAMPLE_SUBALIQUOT";

    String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);    
    
    Object[] fieldNameValueArrayChecker = LPParadigm.fieldNameValueArrayChecker(smpSubAliqFieldName, smpSubAliqFieldValue);
    if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(fieldNameValueArrayChecker[0].toString())){return fieldNameValueArrayChecker;}          

    Integer sampleId = 0;
    String[] mandatoryAliquotFields = new String[]{FIELDNAME_SAMPLE_ID};
    String actionEnabledSampleSubAliquotVolumeRequired = Parameter.getParameterBundle(schemaPrefix.replace("\"", "")+"-data", "sampleSubAliquot_volumeRequired");             

    if (actionEnabledSampleSubAliquotVolumeRequired.toUpperCase().contains("ENABLE")){
        mandatoryAliquotFields = LPArray.addValueToArray1D(mandatoryAliquotFields, FIELDNAME_VOLUME_FOR_ALIQ);
        mandatoryAliquotFields = LPArray.addValueToArray1D(mandatoryAliquotFields, FIELDNAME_VOLUME_FOR_ALIQ_UOM);
        
        String[] mandatorySampleSubAliqFields = new String[]{"volume", "volume_uom"};
        Object[][] aliquotInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, parentTableName, new String[] {FIELDNAME_ALIQUOTID}, new Object[]{aliquotId}, mandatoryAliquotFields);
         if ( (aliquotInfo[0][0]!=null) && (LPPlatform.LAB_FALSE.equalsIgnoreCase(aliquotInfo[0][0].toString())) ){
            return LPArray.array2dTo1d(aliquotInfo);}    
        for (String fv: mandatorySampleSubAliqFields){
            if (LPArray.valuePosicInArray(smpSubAliqFieldName, fv) == -1){
                errorCode = "DataSample_sampleSubAliquoting_volumeAndUomMandatory";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "sampleAliquot_volumeRequired");
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(smpSubAliqFieldName));
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, aliquotId.toString());
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                
            }
        }
        if (aliquotInfo[0][1]==null) {
            errorCode = "DataSample_sampleAliquoting_volumeCannotBeNegativeorZero";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "null");
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                 
        }          
        sampleId = (Integer) aliquotInfo[0][0];
        BigDecimal aliqVolume = new BigDecimal(aliquotInfo[0][1].toString());           
        String aliqVolumeuom = (String) aliquotInfo[0][2];  
        
        BigDecimal subAliqVolume = new BigDecimal(smpSubAliqFieldValue[LPArray.valuePosicInArray(smpSubAliqFieldName, smpSubAliqFieldName[0])].toString());         
        String subAliqVolumeuom = (String) smpSubAliqFieldValue[LPArray.valuePosicInArray(mandatorySampleSubAliqFields, smpSubAliqFieldName[1])];
        
        Object[] diagnoses = LPMath.extractPortion(schemaPrefix, aliqVolume, aliqVolumeuom, sampleId, subAliqVolume, subAliqVolumeuom, aliquotId);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses[0].toString())){return diagnoses;}    
        subAliqVolume = new BigDecimal(diagnoses[diagnoses.length-1].toString());
        
        aliqVolume = aliqVolume.add(subAliqVolume.negate());
        String[] smpVolFldName = new String[]{FIELDNAME_VOLUME_FOR_ALIQ};
        Object[] smpVolFldValue = new Object[]{aliqVolume};
        Object[] updateSampleVolume = Rdbms.updateRecordFieldsByFilter(schemaDataName, parentTableName, 
                smpVolFldName, smpVolFldValue, new String[]{FIELDNAME_ALIQUOTID}, new Object[]{aliquotId});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(updateSampleVolume[0].toString())){
            return updateSampleVolume;}    
        SampleAudit smpAudit = new SampleAudit();
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, parentTableName, aliquotId, aliquotId, 
                sampleId, null, null, 
                LPArray.joinTwo1DArraysInOneOf1DString(smpVolFldName, smpVolFldValue, ":"), userName, userRole, appSessionId);        
    }
    if (!actionEnabledSampleSubAliquotVolumeRequired.toUpperCase().contains("ENABLE")){
        Object[][] aliquotInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, parentTableName, new String[] {FIELDNAME_ALIQUOTID}, new Object[]{aliquotId}, mandatoryAliquotFields);
        sampleId = (Integer) aliquotInfo[0][0];
    }
    
    
    smpSubAliqFieldName = LPArray.addValueToArray1D(smpSubAliqFieldName, FIELDNAME_SAMPLE_ID);
    smpSubAliqFieldValue = LPArray.addValueToArray1D(smpSubAliqFieldValue, sampleId);
    smpSubAliqFieldName = LPArray.addValueToArray1D(smpSubAliqFieldName, FIELDNAME_ALIQUOTID);
    smpSubAliqFieldValue = LPArray.addValueToArray1D(smpSubAliqFieldValue, aliquotId);
    smpSubAliqFieldName = LPArray.addValueToArray1D(smpSubAliqFieldName, "created_by");
    smpSubAliqFieldValue = LPArray.addValueToArray1D(smpSubAliqFieldValue, userName);
    smpSubAliqFieldName = LPArray.addValueToArray1D(smpSubAliqFieldName, "created_on");  
    smpSubAliqFieldValue = LPArray.addValueToArray1D(smpSubAliqFieldValue, LPDate.getTimeStampLocalDate());
    
    Object[] diagnoses = Rdbms.insertRecordInTable(schemaDataName, tableName, smpSubAliqFieldName, smpSubAliqFieldValue);
    if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
        errorCode = ERROR_TRAPPING_ERROR_INSERTING_SAMPLE_RECORD;
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, diagnoses[diagnoses.length-2]);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
    }
    Integer subaliquotId = Integer.parseInt(diagnoses[diagnoses.length-1].toString());
    Object[] fieldsOnLogSample = LPArray.joinTwo1DArraysInOneOf1DString(smpSubAliqFieldName, smpSubAliqFieldValue, ":");
    SampleAudit smpAudit = new SampleAudit();
    smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, tableName, subaliquotId, subaliquotId, aliquotId,
            sampleId, null, null, 
            fieldsOnLogSample, userName, userRole, appSessionId);
    Integer transactionId = null;

    return diagnoses;
    }

    public static String sampleEntireStructureData(String schemaPrefix, Integer sampleId, String sampleFieldToRetrieve, String sampleAnalysisFieldToRetrieve, String sampleAnalysisFieldToSort,
            String sarFieldToRetrieve, String sarFieldToSort, String sampleAuditFieldToRetrieve, String sampleAuditResultFieldToSort){
        
        return sampleEntireStructureDataPostgres(schemaPrefix, sampleId, sampleFieldToRetrieve, sampleAnalysisFieldToRetrieve, sampleAnalysisFieldToSort,
                sarFieldToRetrieve, sarFieldToSort, sampleAuditFieldToRetrieve, sampleAuditResultFieldToSort);
    }
    private static String sampleEntireStructureDataPostgres(String schemaPrefix, Integer sampleId, String sampleFieldToRetrieve, String sampleAnalysisFieldToRetrieve, String sampleAnalysisFieldToSort,
            String sarFieldToRetrieve, String sarFieldToSort, String sampleAuditFieldToRetrieve, String sampleAuditResultFieldToSort){
        
        String [] sampleFieldToRetrieveArr = new String[0];        
            if (sampleFieldToRetrieve!=null){sampleFieldToRetrieveArr=sampleFieldToRetrieve.split("\\|");                   
            }else {sampleFieldToRetrieveArr=new String[0];}
            sampleFieldToRetrieveArr = LPArray.addValueToArray1D(sampleFieldToRetrieveArr, new String[]{FIELDNAME_SAMPLE_ID, FIELDNAME_STATUS});
            sampleFieldToRetrieve = LPArray.convertArrayToString(sampleFieldToRetrieveArr, ", ", "");       
        String [] sampleAnalysisFieldToRetrieveArr = new String[0];        
            if (sampleAnalysisFieldToRetrieve!=null){sampleAnalysisFieldToRetrieveArr=sampleAnalysisFieldToRetrieve.split("\\|");                   
            }else {sampleAnalysisFieldToRetrieveArr=new String[0];}
            sampleAnalysisFieldToRetrieveArr = LPArray.addValueToArray1D(sampleAnalysisFieldToRetrieveArr, new String[]{FIELDNAME_TEST_ID, FIELDNAME_STATUS});
            sampleAnalysisFieldToRetrieve = LPArray.convertArrayToString(sampleAnalysisFieldToRetrieveArr, ", ", "");
            if (sampleAnalysisFieldToSort==null){sampleAnalysisFieldToSort=FIELDNAME_TEST_ID;}                           
        String[] sarFieldToRetrieveArr = new String[0];
            if (sarFieldToRetrieve!=null){sarFieldToRetrieveArr=sarFieldToRetrieve.split("\\|");                   
            }else {sarFieldToRetrieveArr=new String[0];}
            sarFieldToRetrieveArr = LPArray.addValueToArray1D(sarFieldToRetrieveArr, new String[]{DataSample.FIELDNAME_RESULT_ID, DataSample.FIELDNAME_STATUS});
            sarFieldToRetrieve = LPArray.convertArrayToString(sarFieldToRetrieveArr, ", ", "");
            if (sarFieldToSort==null){sarFieldToSort=DataSample.FIELDNAME_RESULT_ID;}                            
        String[] sampleAuditFieldToRetrieveArr = new String[0];
            if (sampleAuditFieldToRetrieve!=null){sampleAuditFieldToRetrieveArr=sampleAuditFieldToRetrieve.split("\\|");                   
            }else {sampleAuditFieldToRetrieveArr=new String[0];}
            sampleAuditFieldToRetrieveArr = LPArray.addValueToArray1D(sampleAuditFieldToRetrieveArr, 
                    new String[]{SampleAudit.FIELD_NAME_DATA_AUDIT_SAMPLE_AUDIT_ID, SampleAudit.FIELD_NAME_DATA_AUDIT_SAMPLE_TRANSACTION_ID, 
                         SampleAudit.FIELD_NAME_DATA_AUDIT_SAMPLE_ACTION_NAME, SampleAudit.FIELD_NAME_DATA_AUDIT_SAMPLE_PERSON, SampleAudit.FIELD_NAME_DATA_AUDIT_SAMPLE_USER_ROLE});
            sampleAuditFieldToRetrieve = LPArray.convertArrayToString(sampleAuditFieldToRetrieveArr, ", ", "");
            if (sampleAuditResultFieldToSort==null){sampleAuditResultFieldToSort=SampleAudit.FIELD_NAME_DATA_AUDIT_SAMPLE_AUDIT_ID;}                            
        
        try {
            String schemaData = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
            String schemaDataAudit = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA_AUDIT);
            String sqlSelect=" select ";
            String sqlOrderBy=" order by ";
            String qry = "";
            qry = qry  + "select row_to_json(sQry)from "
                    +" ( "+sqlSelect+" "+sampleFieldToRetrieve+", "
                    +" ( "+sqlSelect+" COALESCE(array_to_json(array_agg(row_to_json(saQry))),'[]') from  "
                    +"( "+sqlSelect+" "+sampleAnalysisFieldToRetrieve+", "
                    +"( "+sqlSelect+" COALESCE(array_to_json(array_agg(row_to_json(sarQry))),'[]') from "
                    +"( "+sqlSelect+" "+sarFieldToRetrieve+" from "+schemaData+".sample_analysis_result sar where sar.test_id=sa.test_id "
                    +sqlOrderBy+sarFieldToSort+"     ) sarQry    ) as sample_analysis_result "
                    +"from "+schemaData+".sample_analysis sa where sa.sample_id=s.sample_id "
                    +sqlOrderBy+sampleAnalysisFieldToSort+"      ) saQry    ) as sample_analysis,"
                    + "( "+sqlSelect+" COALESCE(array_to_json(array_agg(row_to_json(sauditQry))),'[]') from  "
                    +"( "+sqlSelect+" "+sampleAuditFieldToRetrieve
                    +"from "+schemaDataAudit+".sample saudit where saudit.sample_id=s.sample_id "
                    +sqlOrderBy+sampleAuditResultFieldToSort+"      ) sauditQry    ) as sample_audit "
                    +"from "+schemaData+".sample s where s.sample_id in ("+ "?"+" ) ) sQry   ";
            
            CachedRowSetImpl prepRdQuery = Rdbms.prepRdQuery(qry, new Object[]{sampleId});
            
            
            boolean first = prepRdQuery.first();
            String finalString = "";
            if (prepRdQuery.getString(1)==null){
                return LPPlatform.LAB_FALSE;
            }
            String jsonarrayf = prepRdQuery.getString(1);
            return jsonarrayf;
        } catch (SQLException ex) {
            Logger.getLogger(DataSample.class.getName()).log(Level.SEVERE, null, ex);
            return LPPlatform.LAB_FALSE;
        }        
    }

}
