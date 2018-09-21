/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.requirement;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;

/**
 *
 * @author Administrator
 */
public class Requirement {

    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "requirement"; 
    
    public Object[][] getProcedureBySchemaPrefix(Rdbms rdbm, String schemaPrefix){
                
        String schemaName = "requirements";
        String tableName = "procedure";
        String[] whereFldName = new String[]{"schema_prefix"};
        Object[] whereFldValue = new Object[]{schemaPrefix};
        String[] fieldsToRetrieve = new String[]{"name","version","name","version"};
        
        Object[][] diagnoses = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, whereFldName, whereFldValue, fieldsToRetrieve);        
        
        return diagnoses;
    }    
}
