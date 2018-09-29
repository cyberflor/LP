/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

import LabPLANET.utilities.LabPLANETPlatform;

/**
 *
 * @author Administrator
 */
public class DataDataIntegrity {
    
    Rdbms rdbm = new Rdbms();

    public String[] getTableMandatoryFields(String schemaName, String tableName, String actionName){
        
        String[] myMandatoryFields = new String[0];
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        String schemaDataName = labPlat.buildSchemaName(schemaName, "data");
        
        String propertyEntryName = tableName+"_mandatoryFields"+actionName;        
        String propertyEntryValue = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), propertyEntryName);        
        if (propertyEntryValue.length()>0){
            myMandatoryFields = propertyEntryValue.split("\\|");
        }                  
        return myMandatoryFields;
    }

    public String[] getTableFieldsDefaulValues(String schemaName, String tableName, String actionName){
        
        String[] myMandatoryFields = new String[0];
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        String schemaDataName = labPlat.buildSchemaName(schemaName, "data");
        
        String propertyEntryName = tableName+"_fieldsDefaultValues"+actionName;        
        
        String propertyEntryValue = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), propertyEntryName);        
        if (propertyEntryValue.length()>0){
            myMandatoryFields = propertyEntryValue.split("\\|");
        }                  
        return myMandatoryFields;
    }    

    public String[] getStructureSpecialFields(String schemaName, String tableName, String actionName){
        
        String[] myMandatoryFields = new String[0];
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        String schemaDataName = labPlat.buildSchemaName(schemaName, "data");
        
        String propertyEntryName = tableName+"_specialFields";        
        
        String propertyEntryValue = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), propertyEntryName);        
        if (propertyEntryValue.length()>0){
            myMandatoryFields = propertyEntryValue.split("\\|");
        }                  
        return myMandatoryFields;
    }        
    
    public String[] getStructureSpecialFieldsFunction(String schemaName, String tableName, String actionName){
        
        String[] myMandatoryFields = new String[0];
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        String schemaDataName = labPlat.buildSchemaName(schemaName, "data");
        
        String propertyEntryName = tableName+"_specialFieldsFunction";        
        
        String propertyEntryValue = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), propertyEntryName);        
        if (propertyEntryValue.length()>0){
            myMandatoryFields = propertyEntryValue.split("\\|");
        }                  
        return myMandatoryFields;
    }      
}
