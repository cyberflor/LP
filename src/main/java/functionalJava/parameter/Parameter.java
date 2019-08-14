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
    public static final String BUNDLE_TAG_PARAMETER_CONFIG_CONF="parameter.config.conf";
    public static final String BUNDLE_TAG_TRANSLATION_DIR_PATH="translationDirPath";
    

    /**
     *
     * @param parameterFolder
     * @param schemaName
     * @param areaName
     * @param parameterName
     * @param language
     * @return
     **/
    public static String getParameterBundle(String parameterFolder, String schemaName, String areaName, String parameterName, String language) {
        ResourceBundle prop = null;
        if (parameterFolder==null){parameterFolder="config";}
        String filePath = "parameter."+parameterFolder+"."+schemaName;
        if (areaName!=null){filePath=filePath+"-"+areaName;}
        if (language != null) {filePath=filePath+"_" + language;}
        
        try {
            prop = ResourceBundle.getBundle(filePath);
            if (!prop.containsKey(parameterName)) {
                return "";
            } else {
                return prop.getString(parameterName);
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
        ResourceBundle prop = ResourceBundle.getBundle("parameter.config." + configFile + "_" + language);
        if (!prop.containsKey(parameterName)) {
            return "";
        } else {
            return prop.getString(parameterName);
        }
    }

    /**
     *
     * @param configFile
     * @param parameterName
     * @return
     */
    public static String getParameterBundle(String configFile, String parameterName) {
        try {
            ResourceBundle prop = ResourceBundle.getBundle("parameter.config." + configFile);
            if (!prop.containsKey(parameterName)) {
                return "";
            } else {
                return prop.getString(parameterName);
            }
        } catch (Exception e) {
            return "";
        }
    }

    private void addTagInPropertiesFile(String fileName, String entryName, String entryValue, String testBackup) throws IOException{
         
        String newEntry = "";
        
        ResourceBundle prop = ResourceBundle.getBundle(BUNDLE_TAG_PARAMETER_CONFIG_CONF);        
        String translationsDir = prop.getString(BUNDLE_TAG_TRANSLATION_DIR_PATH);
        translationsDir = translationsDir.replace("/", "\\");
          
        String fileidt = translationsDir + "\\" + fileName + "_es_CO.properties";        
        String currFileName = fileName + "_es_CO.properties";
        
        //String existingEntryValue = prop.getString(entryName);
        
        if (!prop.containsKey(entryName)){        
            if (fileName.equalsIgnoreCase("USERNAV")){ newEntry = entryName + ":" +entryValue;}
            else { newEntry = entryName + "=" +entryValue;}

            try (FileWriter fileWriter = new FileWriter(fileidt, true)){
                if (!newEntry.isEmpty()){
                    newEntry = newEntry + "\n";
                    fileWriter.append(newEntry);
                    }
            }
        }
    }
    
    /**
     *
     * @param fileName
     * @param entryName
     * @param entryValue
     * @return
     * @throws java.io.IOException
     */
    public String addTagInPropertiesFile(String fileName, String entryName, String entryValue) throws IOException{

        //FileWriter fw = null;
        String newEntry = "";

        ResourceBundle propConfig = ResourceBundle.getBundle(BUNDLE_TAG_PARAMETER_CONFIG_CONF);        
        String translationsDir = propConfig.getString(BUNDLE_TAG_TRANSLATION_DIR_PATH);
        translationsDir = translationsDir.replace("/", "\\");

        File[] transFiles = propertiesFiles(fileName);
        for (File f: transFiles)
        {
            String translationPath = "view.text.translation." + f.getName().replace(".properties", "");
            String fileidt = translationsDir + "\\" + f.getName();

            ResourceBundle prop = ResourceBundle.getBundle(translationPath);        
            try{    
                return " Exists the tag in " + f.getName() + " for the entry " + entryName + " and value " + entryValue;
            }catch(MissingResourceException ex)
            {
                String newLogEntry = " created tag in " + f.getName() + " for the entry " + entryName + " and value " + entryValue;

                if (fileName.equalsIgnoreCase("USERNAV")){ newEntry = entryName + ":" +entryValue;}
                else { newEntry = entryName + "=" +entryValue;}
                
                try (FileWriter fw = new FileWriter(fileidt, true)){
                //fw = new FileWriter(fileidt, true);

                if (!newEntry.isEmpty()){
                    newEntry = newEntry + "\n";
                    fw.append(newEntry);
                    }
                //fw.close();
                }
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
    public File[] propertiesFiles(String fileName){

        ResourceBundle propConfig = ResourceBundle.getBundle(BUNDLE_TAG_PARAMETER_CONFIG_CONF);        
        String translationsDir = propConfig.getString(BUNDLE_TAG_TRANSLATION_DIR_PATH);
        translationsDir = translationsDir.replace("/", "\\");

        File dir = new File(translationsDir);
        return dir.listFiles((File dir1, String name) -> name.contains(fileName));       
    }    
     
}
