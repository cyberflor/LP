/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.requirement;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPPlatform;

/**
 *
 * @author Administrator
 */
public class RequirementDeployment {
/**
     *
     * @param procedure
     * @param pVersion
     * @return
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public String procedureDeployment ( String procedure, Integer pVersion)  {    
        
        String schemaNamePrefix = "jonadavid-dev";
        
        String[] schemaNames = new String[0];        
        schemaNames = LPArray.addValueToArray1D(schemaNames, LPPlatform.SCHEMA_CONFIG);
        schemaNames = LPArray.addValueToArray1D(schemaNames, LPPlatform.SCHEMA_DATA);        
        schemaNames = LPArray.addValueToArray1D(schemaNames, LPPlatform.SCHEMA_DATA_AUDIT); 

        //createDBSchemas(schemaNamePrefix, schemaNames);
        // createDBSchemasTable(schemaNamePrefix, schemaNames);
       
        return "";
    }
}
