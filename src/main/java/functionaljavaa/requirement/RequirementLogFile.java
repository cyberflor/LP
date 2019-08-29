/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionaljavaa.requirement;

import lbplanet.utilities.LPFrontEnd;
import lbplanet.utilities.LPPlatform;
import functionaljavaa.parameter.Parameter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 *
 * @author Administrator
 */
public class RequirementLogFile {
    private RequirementLogFile(){    throw new IllegalStateException("Utility class");}    
    
    static final void requirementLogFileNew(String procedureName){
        String newLogFileName = "Requirements.txt";        
        ResourceBundle prop = ResourceBundle.getBundle(Parameter.BUNDLE_TAG_PARAMETER_CONFIG_CONF);        
        String logDir = prop.getString("logDirPath");

        String logFile = logDir + "/" + newLogFileName;
        logFile = logFile.replace("/", "\\");        
    }
    public static final void requirementsLogEntry(String logFile, String functionName, String entryValue, Integer numTabs){ 
        String methodName = LPPlatform.getClassMethodName();
        FileWriter fw = null;  
        try{
            fw = new FileWriter(logFile, true);                  
            StringBuilder newEntryBuilder = new StringBuilder();
            if (numTabs!=null){
                for (Integer i=0;i<numTabs;i++){
                    newEntryBuilder.append( "     ");
                }
            }
            newEntryBuilder.append(methodName).append(": ").append(entryValue).append("\n");            
            fw.append(newEntryBuilder);

            fw.close();        
        }catch(IOException e){ 
            java.util.logging.Logger.getLogger(LPFrontEnd.class.getName()).log(Level.SEVERE, null, e);
        }
    }    
    
}
