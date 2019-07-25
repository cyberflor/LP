/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sampleStructure;

import functionalJava.testingScripts.LPTestingOutFormat;

/**
 *
 * @author Administrator
 */
public class DataSampleUtilities {
    
    public static Object[] getSchemaSampleStatusList(String schemaPrefix){      
        //sample_statuses:LOGGED|RECEIVED|INCOMPLETE|COMPLETE|CANCELED; está en properties.data
        String stList = "LOGGED|RECEIVED|INCOMPLETE|COMPLETE|CANCELED";
        return LPTestingOutFormat.csvExtractFieldValueStringArr(stList);
        //return new String[0];
    }

    public static Object[] getSchemaSampleStatusList(String schemaPrefix, String language){      
        //sample_statuses:LOGGED|RECEIVED|INCOMPLETE|COMPLETE|CANCELED; está en properties.data
        String stList = "";
        if (language==null){language="en";}
       switch (language){
           case "en":
               stList = "Logged|RECEIVED|INCOMPLETE|COMPLETE|CANCELED";
               break;
           case "es":
               stList = "Registrada|RECEIVED|INCOMPLETE|COMPLETE|CANCELED";
               break;
           default:
               stList = "LOGGED|RECEIVED|INCOMPLETE|COMPLETE|CANCELED";
               break;
       }
        
        return LPTestingOutFormat.csvExtractFieldValueStringArr(stList);
        //return new String[0];
    }
    
}
