/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.analysis;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;

/**
 * Class for anything related to analysis user method
 * @author Fran Gomez
 */
public class UserMethod {

    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "user_method"; 
    
    String[] diagnoses = new String[7];
        
/**
 * This function evaluate and return which is the current certification level for a given user and for one particular user method and version.
 *  It is considered: "Not assigned" when no records in table user_method found. // Inactive when found but expired // Certified when found and not expired.
 *  The specific values for all 3 values are configured in the parameter field for the procedure, the entries names are: 
 *  userMethodCertificate_notAssigned, userMethodCertificate_inactive, userMethodCertificate_certified.
 * @param rdbm Rdbms - Database Communication channel
 * @param schemaPrefix String - Procedure name
 * @param analysis String - Analysis name
 * @param methodName String - Method Name
 * @param methodVersion Integer - Method version
 * @param userName String User name
 * @return String - The certification level
 */    
    public String userMethodCertificationLevel(Rdbms rdbm, String schemaPrefix, String analysis, String methodName, Integer methodVersion, String userName){

        String diagnostic = "";
        String tableName = "user_method";
        
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);  
        schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName);   
        
        String userMethodNotAssigned = rdbm.getParameterBundle(schemaConfigName, "userMethodCertificate_notAssigned");
        String userMethodInactive = rdbm.getParameterBundle(schemaConfigName, "userMethodCertificate_inactive");
        String userMethodCertified = rdbm.getParameterBundle(schemaConfigName, "userMethodCertificate_certified");
        
        String[] whereFieldName = new String[0];
        Object[] whereFieldValue = new Object[0];
        String[] getFieldName = new String[0];
        
        whereFieldName = labArr.addValueToArray1D(whereFieldName, "user_id");
        whereFieldValue = labArr.addValueToArray1D(whereFieldValue, userName);
        whereFieldName = labArr.addValueToArray1D(whereFieldName, "analysis");
        whereFieldValue = labArr.addValueToArray1D(whereFieldValue, analysis);
        whereFieldName = labArr.addValueToArray1D(whereFieldName, "method_name");
        whereFieldValue = labArr.addValueToArray1D(whereFieldValue, methodName);
        whereFieldName = labArr.addValueToArray1D(whereFieldName, "method_version");
        whereFieldValue = labArr.addValueToArray1D(whereFieldValue, methodVersion);
        
        getFieldName = labArr.addValueToArray1D(getFieldName, "active");
        getFieldName = labArr.addValueToArray1D(getFieldName, "train_interval");
        getFieldName = labArr.addValueToArray1D(getFieldName, "last_training_on");
        getFieldName = labArr.addValueToArray1D(getFieldName, "last_analysis_on");
        
        Object[][] userMethodData = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, tableName, whereFieldName, whereFieldValue, getFieldName);
        if ("FALSE".equals(userMethodData[0][3].toString())){return userMethodNotAssigned;}    
        
        Boolean userMethodActive = (Boolean) userMethodData[0][0];
        if (!userMethodActive){return userMethodInactive;}
        else{return userMethodCertified;}                
    }    
}
