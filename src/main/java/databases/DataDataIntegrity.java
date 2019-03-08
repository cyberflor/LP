/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

import LabPLANET.utilities.LabPLANETPlatform;
import functionalJava.parameter.Parameter;

/**
 *
 * @author Administrator
 */
public class DataDataIntegrity {
    

    /**
     *
     * @param schemaName
     * @param tableName
     * @param actionName
     * @return
     */
    public String[] getTableMandatoryFields(String schemaName, String tableName, String actionName){
        
        String[] myMandatoryFields = new String[0];
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        String schemaDataName = LabPLANETPlatform.buildSchemaName(schemaName, "data");
        
        String propertyEntryName = tableName+"_mandatoryFields"+actionName;        
        String propertyEntryValue = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), propertyEntryName);        
        if (propertyEntryValue.length()>0){
            myMandatoryFields = propertyEntryValue.split("\\|");
        }                  
        return myMandatoryFields;
    }

    /**
     *
     * @param schemaName
     * @param tableName
     * @param actionName
     * @return
     */
    public String[] getTableFieldsDefaulValues(String schemaName, String tableName, String actionName){
        
        String[] myMandatoryFields = new String[0];
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        String schemaDataName = LabPLANETPlatform.buildSchemaName(schemaName, "data");
        
        String propertyEntryName = tableName+"_fieldsDefaultValues"+actionName;        
        
        String propertyEntryValue = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), propertyEntryName);        
        if (propertyEntryValue.length()>0){
            myMandatoryFields = propertyEntryValue.split("\\|");
        }                  
        return myMandatoryFields;
    }    

    /**
     *
     * @param schemaName
     * @param tableName
     * @param actionName
     * @return
     */
    public String[] getStructureSpecialFields(String schemaName, String tableName, String actionName){
        
        String[] myMandatoryFields = new String[0];
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        String schemaDataName = LabPLANETPlatform.buildSchemaName(schemaName, "data");
        
        String propertyEntryName = tableName+"_specialFields";        
        
        String propertyEntryValue = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), propertyEntryName);        
        if (propertyEntryValue.length()>0){
            myMandatoryFields = propertyEntryValue.split("\\|");
        }                  
        return myMandatoryFields;
    }        
    
    /**
     *
     * @param schemaName
     * @param tableName
     * @param actionName
     * @return
     */
    public String[] getStructureSpecialFieldsFunction(String schemaName, String tableName, String actionName){
        
        String[] myMandatoryFields = new String[0];
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        String schemaDataName = LabPLANETPlatform.buildSchemaName(schemaName, "data");
        
        String propertyEntryName = tableName+"_specialFieldsFunction";        
        
        String propertyEntryValue = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), propertyEntryName);        
        if (propertyEntryValue.length()>0){
            myMandatoryFields = propertyEntryValue.split("\\|");
        }                  
        return myMandatoryFields;
    }      
}
