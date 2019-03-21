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

        String tableName = "user_method";
        
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);  
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);   
        
        String userMethodNotAssigned = Parameter.getParameterBundle(schemaConfigName, "userMethodCertificate_notAssigned");
        String userMethodInactive = Parameter.getParameterBundle(schemaConfigName, "userMethodCertificate_inactive");
        String userMethodCertified = Parameter.getParameterBundle(schemaConfigName, "userMethodCertificate_certified");
        
        String[] whereFieldName = new String[0];
        Object[] whereFieldValue = new Object[0];
        String[] getFieldName = new String[0];
        
        whereFieldName = LPArray.addValueToArray1D(whereFieldName, "user_id");
        whereFieldValue = LPArray.addValueToArray1D(whereFieldValue, userName);
        whereFieldName = LPArray.addValueToArray1D(whereFieldName, "analysis");
        whereFieldValue = LPArray.addValueToArray1D(whereFieldValue, analysis);
        whereFieldName = LPArray.addValueToArray1D(whereFieldName, "method_name");
        whereFieldValue = LPArray.addValueToArray1D(whereFieldValue, methodName);
        whereFieldName = LPArray.addValueToArray1D(whereFieldName, "method_version");
        whereFieldValue = LPArray.addValueToArray1D(whereFieldValue, methodVersion);
        
        getFieldName = LPArray.addValueToArray1D(getFieldName, "active");
        getFieldName = LPArray.addValueToArray1D(getFieldName, "train_interval");
        getFieldName = LPArray.addValueToArray1D(getFieldName, "last_training_on");
        getFieldName = LPArray.addValueToArray1D(getFieldName, "last_analysis_on");
        
        Object[][] userMethodData = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, whereFieldName, whereFieldValue, getFieldName);
        if ("LABPLANET_FALSE".equals(userMethodData[0][0].toString())){return userMethodNotAssigned;}    
        
        Boolean userMethodActive = (Boolean) userMethodData[0][0];
        if (!userMethodActive){return userMethodInactive;}
        else{return userMethodCertified;}                
    }    
}
