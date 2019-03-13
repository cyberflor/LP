/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.requirement;

import databases.Rdbms;

/**
 *
 * @author Administrator
 */
public class Requirement {
    String classVersion = "0.1";

    /**
     *
     * @param schemaPrefix
     * @return
     */
    public Object[][] getProcedureBySchemaPrefix( String schemaPrefix){
                
        String schemaName = "requirements";
        String tableName = "procedure";
        String[] whereFldName = new String[]{"schema_prefix"};
        Object[] whereFldValue = new Object[]{schemaPrefix};
        String[] fieldsToRetrieve = new String[]{"name","version","name","version"};
        
        Object[][] diagnoses = Rdbms.getRecordFieldsByFilter(schemaName, tableName, whereFldName, whereFldValue, fieldsToRetrieve);        
        
        return diagnoses;
    }    
}
