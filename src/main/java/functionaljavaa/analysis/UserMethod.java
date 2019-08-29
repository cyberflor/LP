/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionaljavaa.analysis;

import lbplanet.utilities.LPArray;
import lbplanet.utilities.LPPlatform;
import databases.Rdbms;
import functionaljavaa.audit.SampleAudit;
import functionaljavaa.parameter.Parameter;
import functionaljavaa.samplestructure.DataSample;
import functionaljavaa.samplestructure.DataSampleAnalysis;

/**
 * Class for anything related to analysis user method
 * @author Fran Gomez
 */
public class UserMethod {
   String classVersion = "0.1";
   public static final String TABLENAME_DATA_USER_METHOD="user_method";   
        public static final String FIELDNAME_DATA_USER_METHOD_ACTIVE="active";
        public static final String FIELDNAME_DATA_USER_METHOD_ANALYSIS="analysis";
        public static final String FIELDNAME_DATA_USER_METHOD_LAST_ANALYSIS_ON="last_training_on";
        
        public static final String FIELDNAME_DATA_USER_METHOD_LAST_TRAINING_ON="last_analysis_on";
        public static final String FIELDNAME_DATA_USER_METHOD_LAST_SAMPLE="last_sample";
        public static final String FIELDNAME_DATA_USER_METHOD_LAST_SAMPLE_ANALYSIS="last_sample_analysis";
        public static final String FIELDNAME_DATA_USER_METHOD_METHOD_NAME="method_name";
        public static final String FIELDNAME_DATA_USER_METHOD_METHOD_VERSION="method_version";
        public static final String FIELDNAME_DATA_USER_METHOD_TRAIN_INTERVAL="train_interval";
        public static final String FIELDNAME_DATA_USER_METHOD_USER_ID="user_id";
        public static final String FIELDNAME_DATA_USER_METHOD_USER_METHOD_ID="user_method_id";
        
        
 
/**
 * This function evaluate and return which is the current certification level for a given user and for one particular user method and version.
 *  It is considered: "Not assigned" when no records in table user_method found. // Inactive when found but expired // Certified when found and not expired.
 *  The specific values for all 3 values are configured in the parameter field for the procedure, the entries names are: 
 *  userMethodCertificate_notAssigned, userMethodCertificate_inactive, userMethodCertificate_certified.
 * Parameter Bundle: 
 *      config-userMethodCertificate_notAssigned, userMethodCertificate_inactive, userMethodCertificate_certified
 * @param schemaPrefix String - Procedure name
 * @param analysis String - Analysis name
 * @param methodName String - Method Name
 * @param methodVersion Integer - Method version
 * @param userName String User name
 * @return String - The certification level
 */    
    public String userMethodCertificationLevel( String schemaPrefix, String analysis, String methodName, Integer methodVersion, String userName){
                
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);  
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);   
        
        String userMethodNotAssigned = Parameter.getParameterBundle(schemaConfigName, "userMethodCertificate_notAssigned");
        String userMethodInactive = Parameter.getParameterBundle(schemaConfigName, "userMethodCertificate_inactive");
        String userMethodCertified = Parameter.getParameterBundle(schemaConfigName, "userMethodCertificate_certified");
        
        String[] whereFieldName = new String[]{FIELDNAME_DATA_USER_METHOD_USER_ID, FIELDNAME_DATA_USER_METHOD_ANALYSIS,
                FIELDNAME_DATA_USER_METHOD_METHOD_NAME, FIELDNAME_DATA_USER_METHOD_METHOD_VERSION};
        Object[] whereFieldValue = new Object[]{userName, analysis, methodName, methodVersion};
        String[] getFieldName = new String[]{FIELDNAME_DATA_USER_METHOD_ACTIVE, FIELDNAME_DATA_USER_METHOD_TRAIN_INTERVAL,
                FIELDNAME_DATA_USER_METHOD_LAST_TRAINING_ON, FIELDNAME_DATA_USER_METHOD_LAST_ANALYSIS_ON};
                
        Object[][] userMethodData = Rdbms.getRecordFieldsByFilter(schemaDataName, TABLENAME_DATA_USER_METHOD, whereFieldName, whereFieldValue, getFieldName);
        if (LPPlatform.LAB_FALSE.equals(userMethodData[0][0].toString())){return userMethodNotAssigned;}    
        
        Boolean userMethodActive = (Boolean) userMethodData[0][0];
        if (!userMethodActive){return userMethodInactive;}
        else{return userMethodCertified;}                
    }    
    public static Object[] newUserMethodEntry(String schemaPrefix, String userName, String userRole, String analysis, String methodName, Integer methodVersion, Integer sampleId, Integer testId){
        String auditActionName = "UPDATE LAST ANALYSIS USER METHOD";
        String schemaDataName=LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        Object[] diagnoses = new Object[]{LPPlatform.LAB_FALSE};
        String[] whereFields = new String[]{UserMethod.FIELDNAME_DATA_USER_METHOD_USER_ID, DataSample.FIELDNAME_ANALYSIS, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION};
        Object[] whereFieldsValue = new Object[]{userName, analysis, methodName, methodVersion};
        String[] updFields = new String[]{UserMethod.FIELDNAME_DATA_USER_METHOD_LAST_TRAINING_ON, UserMethod.FIELDNAME_DATA_USER_METHOD_LAST_SAMPLE, FIELDNAME_DATA_USER_METHOD_LAST_SAMPLE_ANALYSIS};
        Object[] updFieldsValue = new Object[]{Rdbms.getLocalDate(), sampleId, testId};
        Object[][] userMethodInfo;
        userMethodInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, UserMethod.TABLENAME_DATA_USER_METHOD, whereFields, whereFieldsValue, new String[]{FIELDNAME_DATA_USER_METHOD_USER_METHOD_ID, UserMethod.FIELDNAME_DATA_USER_METHOD_USER_ID, DataSample.FIELDNAME_ANALYSIS, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION});
        if (!(LPPlatform.LAB_FALSE.equalsIgnoreCase(userMethodInfo[0][0].toString()))) {
            diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, UserMethod.TABLENAME_DATA_USER_METHOD, updFields, updFieldsValue, whereFields, whereFieldsValue);
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())) {
                updFields = LPArray.addValueToArray1D(updFields, whereFields);
                updFieldsValue = LPArray.addValueToArray1D(updFieldsValue, whereFieldsValue);
                String[] fieldsForAudit = LPArray.joinTwo1DArraysInOneOf1DString(updFields, updFieldsValue, ":");
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, UserMethod.TABLENAME_DATA_USER_METHOD, testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
            }
        }
        return diagnoses;        
    }
}



