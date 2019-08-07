/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.requirement;

import LabPLANET.utilities.LPPlatform;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 *
 * @author Administrator
 */
public class RequirementLogFile {
    private RequirementLogFile(){    throw new IllegalStateException("Utility class");}    
    
    static final void requirementLogFileNew(String procedureName){
        String newLogFileName = "Requirements.txt";        
        ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");        
        String logDir = prop.getString("logDirPath");

        String logFile = logDir + "/" + newLogFileName;
        logFile = logFile.replace("/", "\\");        
    }
    public static final void requirementsLogEntry(String logFile, String functionName, String entryValue, Integer numTabs){ 
        String methodName = LPPlatform.getClassMethodName();
        FileWriter fw = null;  
        try{
            fw = new FileWriter(logFile, true);                  
            String newEntry = "";
            if (numTabs!=null){
                for (Integer i=0;i<numTabs;i++){
                    newEntry = newEntry + "     ";
                }
            }
            newEntry = newEntry + methodName + ": " + entryValue + "\n";            
            fw.append(newEntry);

            fw.close();        
        }catch(IOException e){ 
            String errMessage = e.getMessage();
        }
    }    
    
}
