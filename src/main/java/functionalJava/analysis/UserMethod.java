/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.analysis;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPPlatform;
import databases.Rdbms;
import functionalJava.parameter.Parameter;

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
        public static final String FIELDNAME_DATA_USER_METHOD_METHOD_NAME="method_name";
        public static final String FIELDNAME_DATA_USER_METHOD_METHOD_VERSION="method_version";
        public static final String FIELDNAME_DATA_USER_METHOD_TRAIN_INTERVAL="train_interval";
        public static final String FIELDNAME_DATA_USER_METHOD_USER_ID="user_id";
        
        
 
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
}
