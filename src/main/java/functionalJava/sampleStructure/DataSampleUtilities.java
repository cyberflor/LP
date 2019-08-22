/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sampleStructure;

import LabPLANET.utilities.LPPlatform;
import functionalJava.parameter.Parameter;
import functionalJava.testingScripts.LPTestingOutFormat;

/**
 *
 * @author Administrator
 */
public class DataSampleUtilities {
    private DataSampleUtilities(){    throw new IllegalStateException("Utility class");}    
    
    public static Object[] getSchemaSampleStatusList(String schemaPrefix){      
        return getSchemaSampleStatusList(schemaPrefix, "en");
    }

    public static Object[] getSchemaSampleStatusList(String schemaPrefix, String language){      
        String stList = "";
        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        if (language==null){language="en";}
       switch (language){
           case "en":
               stList = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statuses"); 
               break;
           case "es":
               stList = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statuses_es"); 
               break;
           default:
               stList = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statuses"); 
               break;
       }        
        return LPTestingOutFormat.csvExtractFieldValueStringArr(stList);
    }
    
}
