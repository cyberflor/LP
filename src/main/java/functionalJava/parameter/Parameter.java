/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author Administrator
 */
public class Parameter {

    /**
     *
     * @param parameterFolder
     * @param schemaName
     * @param AreaName
     * @param parameterName
     * @param language
     * @return
     **/
    public static String getParameterBundle(String parameterFolder, String schemaName, String AreaName, String parameterName, String language) {
        ResourceBundle prop = null;
        FileWriter fw = null;
        String newEntry = "";
        if (parameterFolder==null){parameterFolder="config";}
        String filePath = "parameter."+parameterFolder+"."+schemaName;
        if (AreaName!=null){filePath=filePath+"-"+AreaName;}
        if (language != null) {filePath=filePath+"_" + language;}
        
        try {
            prop = ResourceBundle.getBundle(filePath);
            if (!prop.containsKey(parameterName)) {
                return "";
            } else {
                String paramValue = prop.getString(parameterName);
                return paramValue;
            }
        } catch (Exception e) {
            return "";
        }
    }


    /**
     *
     * @param configFile
     * @param parameterName
     * @param language
     * @return
     */
    public static String getParameterBundleInConfigFile(String configFile, String parameterName, String language) {
        FileWriter fw = null;
        String newEntry = "";
        ResourceBundle prop = ResourceBundle.getBundle("parameter.config." + configFile + "_" + language);
        if (!prop.containsKey(parameterName)) {
            return "";
        } else {
            String paramValue = prop.getString(parameterName);
            return paramValue;
        }
    }

    /**
     *
     * @param configFile
     * @param parameterName
     * @return
     */
    public static String getParameterBundle(String configFile, String parameterName) {
        FileWriter fw = null;
        String newEntry = "";
        try {
            ResourceBundle prop = ResourceBundle.getBundle("parameter.config." + configFile);
            if (!prop.containsKey(parameterName)) {
                return "";
            } else {
                String paramValue = prop.getString(parameterName);
                return paramValue;
            }
        } catch (Exception e) {
            return "";
        }
    }

    private void addTagInPropertiesFile(String fileName, String entryName, String entryValue, String testBackup) throws IOException{
         
        FileWriter fw = null;
        String newEntry = "";
        
        ResourceBundle prop = ResourceBundle.getBundle("parameter.config.conf");        
        String translationsDir = prop.getString("translationDirPath");
        translationsDir = translationsDir.replace("/", "\\");
          
        String fileidt = translationsDir + "\\" + fileName + "_es_CO.properties";
        
        String currFileName = fileName + "_es_CO.properties";
        
        //String existingEntryValue = prop.getString(entryName);
        
        if (!prop.containsKey(entryName)){        
            if (fileName.equalsIgnoreCase("USERNAV")){ newEntry = entryName + ":" +entryValue;}
            else { newEntry = entryName + "=" +entryValue;}

            fw = new FileWriter(fileidt, true);

            if (!newEntry.isEmpty()){
                newEntry = newEntry + "\n";
                fw.append(newEntry);
                }
        }

        fw.close();
    }
    
    /**
     *
     * @param fileName
     * @param entryName
     * @param entryValue
     * @return
     * @throws Exception
     */
    public String addTagInPropertiesFile(String fileName, String entryName, String entryValue) throws IOException{

        String methodName = "addTagInPropertiesFile";    
        FileWriter fw = null;
        String newEntry = "";

        ResourceBundle propConfig = ResourceBundle.getBundle("parameter.config.conf");        
        String translationsDir = propConfig.getString("translationDirPath");
        translationsDir = translationsDir.replace("/", "\\");

        File[] transFiles = PropertiesFiles(fileName);
        for (File f: transFiles)
        {
            String translationPath = "view.text.translation." + f.getName().replace(".properties", "");
            String fileidt = translationsDir + "\\" + f.getName();

            ResourceBundle prop = ResourceBundle.getBundle(translationPath);        
            try{    
                String existingEntryValue = prop.getString(entryName);                
                String newLogEntry = " Exists the tag in " + f.getName() + " for the entry " + entryName + " and value " + entryValue;
                return newLogEntry;
                
            }catch(MissingResourceException ex)
            {
                String newLogEntry = " created tag in " + f.getName() + " for the entry " + entryName + " and value " + entryValue;

                if (fileName.equalsIgnoreCase("USERNAV")){ newEntry = entryName + ":" +entryValue;}
                else { newEntry = entryName + "=" +entryValue;}

                fw = new FileWriter(fileidt, true);

                if (!newEntry.isEmpty()){
                    newEntry = newEntry + "\n";
                    fw.append(newEntry);
                    }
                fw.close();

                return newLogEntry;
            }
        }    
        return "Nothing done";
    }

    /**
     *
     * @param fileName
     * @return
     */
    public File[] PropertiesFiles(String fileName){

        FileWriter fw = null;
        String newEntry = "";
        String curFiles = "";

        ResourceBundle propConfig = ResourceBundle.getBundle("parameter.config.conf");        
        String translationsDir = propConfig.getString("translationDirPath");
        translationsDir = translationsDir.replace("/", "\\");

        File dir = new File(translationsDir);
        File [] files = dir.listFiles((File dir1, String name) -> name.contains(fileName));       
        return files;
    }    
     
}
