/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionaljavaa.requirement;

import lbplanet.utilities.LPPlatform;
import databases.DbObjectsRequirementsTables;
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
    public static final Object[][] getProcedureBySchemaPrefix( String schemaPrefix){
                
        String schemaName = LPPlatform.SCHEMA_REQUIREMENTS;
        String tableName = DbObjectsRequirementsTables.TABLE_NAME_REQS_PROCEDURE_INFO;
        String[] whereFldName = new String[]{DbObjectsRequirementsTables.FIELD_NAME_REQS_PROCEDURE_INFO_SCHEMA_PREFIX};
        Object[] whereFldValue = new Object[]{schemaPrefix};
        String[] fieldsToRetrieve = new String[]{DbObjectsRequirementsTables.FIELD_NAME_REQS_PROCEDURE_INFO_NAME, DbObjectsRequirementsTables.FIELD_NAME_REQS_PROCEDURE_INFO_VERSION};
        
        return Rdbms.getRecordFieldsByFilter(schemaName, tableName, whereFldName, whereFldValue, fieldsToRetrieve);        
    }    
}
