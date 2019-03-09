/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import databases.SqlStatement;
import functionalJava.parameter.Parameter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.json.simple.JSONObject;
import databases.Rdbms;

/**
 * LPPlatform is a library for methods solving topics that are specifically part of the LabPLANET Paradigm.
 * @author Fran Gomez
 */
public class LPPlatform {
    String classVersion = "0.1";
    
    public static String LAB_ENCODER_UTF8 = "utf-8";
    public static String LAB_TRUE = "LABPLANET_TRUE";
    public static String LAB_FALSE = "LABPLANET_FALSE";
    
    private static final String CONFIG_FILES_FOLDER = "LabPLANET";
    private static final String CONFIG_FILES_ERRORTRAPING = "\"errorTraping\"";

    private static final String CONFIG_PROC_FILE_NAME = "-procedure";
    private static final String ENCRYPTION_KEY = "Bar12345Bar12345";
    private static final String JAVADOC_CLASS_FLDNAME = "class";
    private static final String JAVADOC_METHOD_FLDNAME = "method";
    private static final String JAVADOC_LINE_FLDNAME = "line";
    
    /**
     *
     * @param schemaPrefix
     * @param actionName
     * @return
     */
    public static Object[] procActionEnabled(String schemaPrefix, String actionName){
        actionName = actionName.toUpperCase();
        String errorCode = ""; String errorDetail = "";
        Object[] errorDetailVariables = new Object[0];                
        String[] procedureActions = Parameter.getParameterBundle(schemaPrefix.replace("\"", "")+CONFIG_PROC_FILE_NAME, "procedureActions").split("\\|");
        
        if (LabPLANETArray.valueInArray(procedureActions, "ALL")){
            errorCode = "ACTION_ENABLED_BY_ALL";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);
            return trapErrorMessage(LAB_TRUE, errorCode, errorDetailVariables);
        }
        if ( (procedureActions.length==1 && "".equals(procedureActions[0])) ){
            errorCode = "userRoleActionEnabled_denied_ruleNotFound";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(procedureActions));
            return trapErrorMessage(LAB_FALSE, errorCode, errorDetailVariables);
        }else if(!LabPLANETArray.valueInArray(procedureActions, actionName)){    
            errorCode = "userRoleActionEnabled_denied";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(procedureActions));
            return trapErrorMessage(LAB_FALSE, errorCode, errorDetailVariables);            
        }else{
            errorCode = "userRoleActionEnabled_enabled";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);
            return trapErrorMessage(LAB_TRUE, errorCode, errorDetailVariables);               
        }    
    }    
    
    /**
     *
     * @param schemaPrefix
     * @param userRole
     * @param actionName
     * @return
     */
    public static Object[] procUserRoleActionEnabled(String schemaPrefix, String userRole, String actionName){
        String errorCode = ""; String errorDetail = "";
        Object[] errorDetailVariables = new Object[0];            
        String[] procedureActionsUserRoles = Parameter.getParameterBundle(schemaPrefix.replace("\"", "")+CONFIG_PROC_FILE_NAME, "actionEnabled"+actionName).split("\\|");
        
        if (LabPLANETArray.valueInArray(procedureActionsUserRoles, "ALL")){
            errorCode = "userRoleActionEnabled_ALL";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);            
            return trapErrorMessage(LAB_TRUE, errorCode, errorDetailVariables);                    
        }
        if ( (procedureActionsUserRoles.length==1 && "".equals(procedureActionsUserRoles[0])) ){
            errorCode = "userRoleActionEnabled_missedParameter";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);            
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, procedureActionsUserRoles);                        
            return trapErrorMessage(LAB_FALSE, errorCode, errorDetailVariables);        
        }else if(!LabPLANETArray.valueInArray(procedureActionsUserRoles, userRole)){    
            errorCode = "userRoleActionEnabled_roleNotIncluded";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, userRole);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(procedureActionsUserRoles));            
            return trapErrorMessage(LAB_FALSE, errorCode, errorDetailVariables);      
        }else{
            errorCode = "userRoleActionEnabled_enabled";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);
            return trapErrorMessage(LAB_TRUE, errorCode, errorDetailVariables);        
        }            
    }

    /**
     *
     * @param schemaPrefix
     * @param actionName
     * @return
     */
    public static Object[] procActionRequiresUserConfirmation(String schemaPrefix, String actionName){
        
        actionName = actionName.toUpperCase();
        String errorCode = ""; String errorDetail = "";
        Object[] errorDetailVariables = new Object[0];
        String[] procedureActions = Parameter.getParameterBundle(schemaPrefix.replace("\"", "")+CONFIG_PROC_FILE_NAME, "verifyUserRequired").split("\\|");
        
        if (LabPLANETArray.valueInArray(procedureActions, "ALL")){
            errorCode = "VERIFY_USER_REQUIRED_BY_ALL";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);
            return trapErrorMessage(LAB_TRUE, errorCode, errorDetailVariables);
        }
        if ( (procedureActions.length==1 && "".equals(procedureActions[0])) ){
            errorCode = "verifyUserRequired_denied_ruleNotFound";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(procedureActions));
            return trapErrorMessage(LAB_FALSE, errorCode, errorDetailVariables);
        }else if(!LabPLANETArray.valueInArray(procedureActions, actionName)){    
            errorCode = "verifyUserRequired_denied";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(procedureActions));
            return trapErrorMessage(LAB_FALSE, errorCode, errorDetailVariables);            
        }else{
            errorCode = "verifyUserRequired_enabled";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);
            return trapErrorMessage(LAB_TRUE, errorCode, errorDetailVariables);               
        }    
    }    

    /**
     *
     * @param schemaPrefix
     * @param actionName
     * @return
     */
    public static Object[] procActionRequiresEsignConfirmation(String schemaPrefix, String actionName){
        
        actionName = actionName.toUpperCase();
        String errorCode = ""; String errorDetail = "";
        Object[] errorDetailVariables = new Object[0];
        String[] procedureActions = Parameter.getParameterBundle(schemaPrefix.replace("\"", "")+CONFIG_PROC_FILE_NAME, "eSignRequired").split("\\|");
        
        if (LabPLANETArray.valueInArray(procedureActions, "ALL")){
            errorCode = "ESIGN_REQUIRED_BY_ALL";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);
            return trapErrorMessage(LAB_TRUE, errorCode, errorDetailVariables);
        }
        if ( (procedureActions.length==1 && "".equals(procedureActions[0])) ){
            errorCode = "eSignRequired_denied_ruleNotFound";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(procedureActions));
            return trapErrorMessage(LAB_FALSE, errorCode, errorDetailVariables);
        }else if(!LabPLANETArray.valueInArray(procedureActions, actionName)){    
            errorCode = "eSignRequired_denied";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(procedureActions));
            return trapErrorMessage(LAB_FALSE, errorCode, errorDetailVariables);            
        }else{
            errorCode = "eSignRequired_enabled";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName);
            return trapErrorMessage(LAB_TRUE, errorCode, errorDetailVariables);               
        }    
    }    
    
    /**
     *
     * @param schemaName
     * @param tableName
     * @param fieldName
     * @return
     */
    public static Boolean isEncryptedField(String schemaName, String tableName, String fieldName){
        Boolean diagnoses = false;
        if ((schemaName==null) || (tableName==null) || (fieldName==null) ) {return diagnoses;}
        String parameterName = "encrypted_"+tableName;
        schemaName = schemaName.replace("\"", "");
        
        if ( fieldName.contains(" ")){fieldName=fieldName.substring(0, fieldName.indexOf(' '));}
        String tableEncrytedFields = Parameter.getParameterBundle(schemaName, parameterName);
        if ( (tableEncrytedFields==null) ){return diagnoses;}
        if ( ("".equals(tableEncrytedFields)) ){return diagnoses;}        
        LabPLANETArray LabPLANETArray = new LabPLANETArray();
        return LabPLANETArray.valueInArray(tableEncrytedFields.split("\\|"), fieldName);        
    }
    
    /**
     *
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public static HashMap<String, String> encryptEncryptableFieldsAddBoth(String fieldName, String fieldValue){    
        return encryptEncryptableFields(false, fieldName, fieldValue);
    }    

    /**
     *
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public static HashMap<String, String> encryptEncryptableFieldsOverride(String fieldName, String fieldValue){    
        return encryptEncryptableFields(true, fieldName, fieldValue);
    }    
    
    private static HashMap<String, String> encryptEncryptableFields(Boolean override, String fieldName, String fieldValue){        
        HashMap<String, String> hm = new HashMap<>();        
        
        if (!fieldName.toUpperCase().contains("IN")){
            Object[] encStr = encryptString(fieldValue);
            if (override){
                fieldValue=encStr[1].toString();
            }else{
                fieldName=fieldName+" in|";       
                fieldValue=fieldValue+"|"+encStr[1];
            }
        }else{
            SqlStatement sql = new SqlStatement();
            String separator = sql.inSeparator(fieldName);
            String[] valuesArr = fieldValue.split(separator);
            String valuesEncripted = "";
            for (String fn: valuesArr){
                Object[] encStr = encryptString(fn);
                if (override){
                    valuesEncripted = encStr[1]+separator;
                }else{
                    fieldValue=fieldValue+separator+encStr[1];
                }                
            }
            if (valuesEncripted.length()>0){
                valuesEncripted=valuesEncripted.substring(0, valuesEncripted.length()-2);
                fieldValue=valuesEncripted;
            }                    
        }
        
        
        hm.put(fieldName, fieldValue);
        return hm;
    }
    
    /**
     *
     * @param stringToEncrypt
     * @return
     */
    public static Object[] encryptString(String stringToEncrypt){
        Object[] diagnoses = new Object[3];            

        String key = ENCRYPTION_KEY; // 128 bit key
        try{
            String text = stringToEncrypt;
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b: encrypted) {
                sb.append((char)b);
            }

            // the encrypted String
            String enc = sb.toString();
            diagnoses[0] = true;
            diagnoses[1] = enc;            
            diagnoses[2] = stringToEncrypt; 
            return diagnoses;
        }
        catch(InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){
            diagnoses[0] = false;
            diagnoses[1] = e.getMessage();            
            diagnoses[2] = stringToEncrypt; 
            return diagnoses;
        }             
    }  
    
    /**
     *
     * @param encryptedString
     * @return
     */
    public static Object[] decryptString(String encryptedString){
        Object[] diagnoses = new Object[3];
        String key = ENCRYPTION_KEY; 
        try{                    
            String enc = encryptedString;
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            // for decryption
            byte[] bb = new byte[enc.length()];
            for (int i=0; i<enc.length(); i++) {
                bb[i] = (byte) enc.charAt(i);
            }

            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(bb));
            System.err.println("decrypted:" + decrypted);   
            diagnoses[0] = true;
            diagnoses[1] = decrypted;            
            diagnoses[2] = encryptedString; 
            return diagnoses;
        }
        catch(InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){            diagnoses[0] = false;
            diagnoses[1] = e.getMessage();            
            diagnoses[2] = encryptedString; 
            return diagnoses;
        }
    }    
      
/**
 * addJavaClassDoc is the method that should reduce the lines of code for justifying lines of code against its requirement
 * to keep the track about which is the requirement covered by each section in each method.
 * When running the code in Dev-Mode then it should mark as 'covered' the existing requirement or create one record for this given requirement
 * The parameter.config.labtimus mandatoryFields_requerimentsJavaDoc defines which are the mandatory fields that should be added
 * to the peer fields/values to let this call be consider fill enough to proceed.
 * 
 * @param fields String[] - which are the properties being passed.
 * @param values Object[] - which are the values for the properties defined above
 * @param elementsDev StackTraceElement[] - Provides info from the context such as the ClassName + MethodName + LineNumber
 */
    public static void addJavaClassDoc(String[] fields, Object[] values, StackTraceElement[] elementsDev) {
                
        String schemaName = "requirements";
        String tableName = "java_class_doc";
        String[] fldName = new String[0];
        Object[] fldValue = new Object[0];
        String currField = "";        
        String mandatoryFieldsStr = Parameter.getParameterBundle("labtimus", "mandatoryFields_requerimentsJavaDoc");
        String[] mandatoryFields = mandatoryFieldsStr.split("\\|");
        

        fldName = LabPLANETArray.addValueToArray1D(fldName, JAVADOC_CLASS_FLDNAME);         fldValue = LabPLANETArray.addValueToArray1D(fldValue, elementsDev[1].getClassName()); 
        fldName = LabPLANETArray.addValueToArray1D(fldName, JAVADOC_METHOD_FLDNAME);         fldValue = LabPLANETArray.addValueToArray1D(fldValue, elementsDev[1].getMethodName());     
        fldName = LabPLANETArray.addValueToArray1D(fldName, JAVADOC_LINE_FLDNAME);         fldValue = LabPLANETArray.addValueToArray1D(fldValue, elementsDev[1].getLineNumber());
        
        for (Integer iNumFields=0;iNumFields<fields.length;iNumFields++){
            if ( (fields[iNumFields]!=null) && (values[iNumFields]!=null) ){
                fldName = LabPLANETArray.addValueToArray1D(fldName, fields[iNumFields]);         fldValue = LabPLANETArray.addValueToArray1D(fldValue, values[iNumFields]); 
            }
        }
        
        String[] getFilterFldName = new String[0];
        Object[] getFilterFldValue = new Object[0];    
        getFilterFldName = LabPLANETArray.addValueToArray1D(getFilterFldName, JAVADOC_CLASS_FLDNAME);        getFilterFldValue = LabPLANETArray.addValueToArray1D(getFilterFldValue, elementsDev[1].getClassName()); 
        getFilterFldName = LabPLANETArray.addValueToArray1D(getFilterFldName, JAVADOC_METHOD_FLDNAME);       getFilterFldValue = LabPLANETArray.addValueToArray1D(getFilterFldValue, elementsDev[1].getMethodName());     
        currField = "class_version";
        Integer specialFieldIndex = Arrays.asList(fldName).indexOf(currField);
        if (specialFieldIndex==-1){return;}
        getFilterFldName = LabPLANETArray.addValueToArray1D(getFilterFldName, currField);      getFilterFldValue = LabPLANETArray.addValueToArray1D(getFilterFldValue, fldValue[specialFieldIndex]);     
        currField = "line_name";
        specialFieldIndex = Arrays.asList(fldName).indexOf(currField);
        if (specialFieldIndex==-1){return;}
        getFilterFldName = LabPLANETArray.addValueToArray1D(getFilterFldName, currField);      getFilterFldValue = LabPLANETArray.addValueToArray1D(getFilterFldValue, fldValue[specialFieldIndex]);     
        
        String[] getFields = new String[] {"id",JAVADOC_LINE_FLDNAME,"last_update_on","created_on"};        
        Object[][] diagnoses = Rdbms.getRecordFieldsByFilter(schemaName, tableName, getFilterFldName, getFilterFldValue, getFields);
        if (LAB_FALSE.equalsIgnoreCase(diagnoses[0][0].toString())){        
            Object[] diagnosesInsert = Rdbms.insertRecordInTable(schemaName, tableName, fldName, fldValue);
            String diag = diagnosesInsert[3].toString();
        }else{
            String[] fieldsUpdate = new String[0];
            Object[] fieldsUpdateValue = new Object[0];
            currField = JAVADOC_LINE_FLDNAME;
            if (elementsDev[1].getLineNumber()!=(Integer) fldValue[Arrays.asList(fldName).indexOf(currField)]){
                fieldsUpdate = LabPLANETArray.addValueToArray1D(fieldsUpdate, currField);        fieldsUpdateValue = LabPLANETArray.addValueToArray1D(fieldsUpdateValue, elementsDev[1].getLineNumber());                 
            }
            if (fieldsUpdate.length>0){Object[] updateRecordFieldsByFilter = Rdbms.updateRecordFieldsByFilter(schemaName, tableName, fieldsUpdate, fieldsUpdateValue, getFilterFldName, getFilterFldValue);
            }
        }    
    }
/**
 * The schema names are instances per procedure + nature of the data (config/data/requirements...)
 * This method has as a purpose on helping on build the concatenation
 * At the same time it solves the problem on using some symbols like "-" in the name that requires quoted the name
 * If the schemaName is already contained in the schemaPrefix it won't be concatenated again.
 * @param schemaPrefix String - Basically the Procedure Name
 * @param schemaName String - Which is the nature of the data (config/data/requirements)
 * @return String
 */    
    public static String buildSchemaName(String schemaPrefix, String schemaName){
        if (schemaPrefix.length()>0){
            //Remove this to re-create the schemaName when not called for the first time.
            schemaPrefix = schemaPrefix.replace("\"", "");
            schemaName = schemaName.replace("\"", "");
            schemaName = schemaName.replace(schemaPrefix+"-", "");

            if (!schemaPrefix.contains(schemaName)){            
                schemaName = schemaPrefix + "-" + schemaName;
                return "\""+schemaName+"\"";
            }else{
                return "\""+schemaPrefix+"\"";}
        }
        schemaName = schemaName.replace("\"", "");
        return "\""+schemaName+"\"";                  
    }
    
/**
 * When logging/creating objects that conceptually are mandatory on be part of a structure for a field added or required
 * to get all the fields consider mandatory we invoke the specific parameter field called in the way of "table_name_mandatoryFieldsAction" containing a peer entries in the way of:
 * A call per each mandaotry field to the method mandatoryFieldsByDependency will add the prerrequisites as mandatory too
 * All fields should be in context when the action is performed and not null.
 * The entry is stored in the specific data.properties file for this particular procedure.
 *      where the content is expressed in the way of fieldNAmes between spaces where the first field is the one having the prerrequisites. 
 *      and all different fields separated by pipe, "|".
 *      Example: project_fieldsAddingMandatoryInsert:analysis method_name method_version*analysis method_name method_version|method_name*analysis method_name method_version|spec*spec spec_code spec_code_version
 * @param schemaName - Schema where the template belongs to
 * @param fieldNames[] - Fields for the filter to find and get the prerrequisites.
     * @param fieldValues
 * @param tableName. Table where the template is stored in.
 * @param actionName. The action in the database INSERT/UPDATE/DELETE (lowercas preferred).
 * @return String[] All prerrequisite fields for all the fields added to the fieldNames input argument array, when position 3 is set to FALSE then the template is not found.
 */  
    public static Object[][] mandatoryFieldsCheck(String schemaName, String[] fieldNames, Object[] fieldValues, String tableName, String actionName){
        Object[][] diagnoses = new Object[3][6];
       
        String propertyName = tableName+"_mandatoryFields"+actionName;
        
        String mandatoryFieldsToCheck = Parameter.getParameterBundle(schemaName.replace("\"", ""), propertyName);
        String mandatoryFieldsToCheckDefault = Parameter.getParameterBundle(schemaName.replace("\"", ""), propertyName+"Default");
        
        String[] mandatoryFields = mandatoryFieldsToCheck.split("\\|");
        
        mandatoryFields = mandatoryFieldsByDependency(schemaName, fieldNames, tableName, actionName);
        String[] mandatoryFieldsDefault = mandatoryFieldsToCheckDefault.split("\\|");

        String mandatoryFieldsMissing = "";
        for (Integer inumLines=0;inumLines<mandatoryFields.length;inumLines++){
            String currField = mandatoryFields[inumLines];
            boolean contains = Arrays.asList(fieldNames).contains(currField.toLowerCase());
            String defValueType = "";
            Object defValueFormat = null;
            if ( (!contains) || (fieldValues[inumLines]==null) ){
                Boolean addIt = false;
                if (mandatoryFieldsToCheckDefault.contains(currField)){
                    Integer endPosic = mandatoryFieldsToCheckDefault.indexOf(currField);
                    String defValue = mandatoryFieldsToCheckDefault.substring(endPosic, mandatoryFieldsToCheckDefault.length());
                    if (defValue.startsWith(currField+"*")){
                        defValue = defValue.replace(currField+"*", "");
                        String[] defValues = defValue.split("\\|");
                        defValue=defValues[0];     
                        defValues = defValue.split("\\*");
                        defValue=defValues[0];     
                        defValueType=defValues[1];                          
                        if (defValue!=null){
                            addIt=true;}
                    }
                    if (addIt){
                        switch (defValueType.toUpperCase()){
                            case "INTEGER":
                                defValueFormat = Integer.parseInt(defValue);
                                break;
                            case "STRING":
                                defValueFormat = defValue;
                                break;
                            default:
                                break;
                        }
                        if (!contains){
                            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, currField);
                            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, defValueFormat);
                        }else{
                            fieldValues[inumLines] = defValueFormat;
                        }    
                    }
                }
                if (!addIt){
                    if (mandatoryFieldsMissing.length()>0){mandatoryFieldsMissing = mandatoryFieldsMissing + ",";}
                    mandatoryFieldsMissing = mandatoryFieldsMissing + currField;
                }
            }        
        }            
        if (mandatoryFieldsMissing.length()>0){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0][0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[0][1]= "-999";
            diagnoses[0][2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[0][3]="FALSE";
            diagnoses[0][4]="ERROR:Missing Mandatory Fields";
            diagnoses[0][5]="Mandatory fields not found: "+mandatoryFieldsMissing;
            return diagnoses;
        }        
        for (Integer i=0;i<fieldNames.length;i++){
            if (i>=6){diagnoses = LabPLANETArray.addColumnToArray2D(diagnoses, "");}
            diagnoses[1][i] = fieldNames[i];}
        for (Integer i=0;i<fieldNames.length;i++){diagnoses[2][i] = fieldValues[i];}
        diagnoses[0][0] = LAB_TRUE;
        return diagnoses;
    }

/**
 * When logging/creating objects that conceptually are mandatory on be part of a structure for a field added or required
 * to get all the fields involved per field we invoke the specific parameter field called in the way of "table_name_fieldsAddingMandatoryAction" containing a peer entries in the way of:
 * The entry is stored in the specific data.properties file for this particular procedure.
 *      where the content is expressed in the way of fieldNAmes between spaces where the first field is the one having the prerrequisites. 
 *      and all different fields separated by pipe, "|".
 *      Example: project_fieldsAddingMandatoryInsert:analysis method_name method_version*analysis method_name method_version|method_name*analysis method_name method_version|spec*spec spec_code spec_code_version
 * @param schemaName - Schema where the template belongs to
 * @param fieldNames[] - Fields for the filter to find and get the prerrequisites.
 * @param tableName. Table where the template is stored in.
 * @param actionName. The action in the database INSERT/UPDATE/DELETE (lowercas preferred).
 * @return String[] All prerrequisite fields for all the fields added to the fieldNames input argument array, when position 3 is set to FALSE then the template is not found.
 */   
    public static String[] mandatoryFieldsByDependency(String schemaName, String[] fieldNames, String tableName, String actionName){
        String[] diagnoses = new String[6];
        
        String propertyName = tableName+"_fieldsAddingMandatory"+actionName;
        String mandatoryFieldsByDependency = Parameter.getParameterBundle(schemaName.replace("\"", ""), propertyName);
        String[] mandatoryByDependency = mandatoryFieldsByDependency.split("\\|");

        for (Integer inumLines=0;inumLines<fieldNames.length;inumLines++){
            String currField = fieldNames[inumLines];
            Boolean contains = mandatoryFieldsByDependency.contains(currField);            
            String defValueType = "";
            Object defValueFormat = null;
            if ( contains ){
                Integer fieldIndexSpecCode = Arrays.asList(mandatoryByDependency).indexOf(currField);
                if (fieldIndexSpecCode!=-1){
                    String[] propertyEntryValue = mandatoryByDependency[fieldIndexSpecCode].split("\\*");
                    if (propertyEntryValue.length==2) {
                        if (Arrays.asList(propertyEntryValue[0]).contains(currField.toLowerCase())) {
                            String[] fieldToAddByDependency = propertyEntryValue[1].split(" ");
                            for (String fAdd: fieldToAddByDependency){
                                if (Arrays.asList(fieldNames).indexOf(fAdd)==-1){
                                    fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, fAdd);
                                }    
                            }    
                        }    
                    }
                }    
            }        
        }            
        return fieldNames;
    }

/**
 * When logging/creating objects that conceptually requires one template to define its nature then one call to this method is required
 * to get all those parameters and log/create the new instance accordingly.
 * Each procedure has a specific parameter field called in the way of "procedureName-config" containing a peer entries in the way of:
 *      tableName__configTableName = Specify the table name where the template is stored.
 *      tableName_configTableKeyFields = Specify the mandatory fields that should be present in the peer fieldNames/fieldValues
 *                                       to link the new object with its template in the proper and expected way. 
 * @param schemaName - Schema where the template belongs to
 * @param fieldNames - Fields for the filter to find and get the proper template.
 * @param fieldValues - Values for the fields described above.
 * @param tableName. Table where the template is stored in.
 * @return String[] when position 3 is set to FALSE then the template is not found.
 */   
    public static Object[] configObjectExists( String schemaName, String[] fieldNames, Object[] fieldValues, String tableName){
        String errorCode = ""; String errorDetail = "";
        Object[] errorDetailVariables = new Object[0];        
        String configTableNamePropertyName = tableName+"_configTableName";
        String configTableKeyFieldsPropertyName = tableName+"_configTableKeyFields";        
        
        String configTableName = Parameter.getParameterBundle(schemaName.replace("\"", ""), configTableNamePropertyName);
        String configTableKeyFields = Parameter.getParameterBundle(schemaName.replace("\"", ""), configTableKeyFieldsPropertyName);

        String[] configTableKeyFieldName = configTableKeyFields.split("\\|");
        Object[] configTableKeyFielValue = new Object[0];
        
        String missingFieldInArray = "";
        for (Integer i=0;i<configTableKeyFieldName.length;i++){
            
            String currField = configTableKeyFieldName[i];
            String[] currFields = currField.split("\\*");
            currField=currFields[0];     
            String currFieldType =currFields[1];              
            Integer fieldPosic = Arrays.asList(fieldNames).indexOf(currField);            
            if (fieldPosic==-1){
                if (missingFieldInArray.length()>0){missingFieldInArray=missingFieldInArray+", ";}
                missingFieldInArray = missingFieldInArray + currField;                
            }else{
                Object currFieldInFormat = null;
                switch (currFieldType.toUpperCase()){
                    case "INTEGER":
                        currFieldInFormat = Integer.parseInt(fieldValues[fieldPosic].toString());
                        break;
                    case "STRING":
                        currFieldInFormat = fieldValues[fieldPosic].toString();
                        break;
                    default:
                        break;
                }                
                configTableKeyFieldName[i] = currField;
                configTableKeyFielValue = LabPLANETArray.addValueToArray1D(configTableKeyFielValue, currFieldInFormat);
            }
                
        }       
        Object[] diagnosis = Rdbms.existsRecord(schemaName, configTableName, configTableKeyFieldName, configTableKeyFielValue);
        if (!LAB_TRUE.equalsIgnoreCase(diagnosis[0].toString())){            
           String[] configTableFilter = LabPLANETArray.joinTwo1DArraysInOneOf1DString(configTableKeyFieldName, configTableKeyFielValue, ":");
           errorCode = "LabPLANETPlatform_MissingTableConfigCode";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(configTableFilter));
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, diagnosis[5]);
           return (String[]) trapErrorMessage(LAB_FALSE, errorCode, errorDetailVariables);
        }    

        
        return diagnosis;
    }
/**
 * In some cases the field value requires check a kind of logic to verify that the value is aligned with a particular business rule.
 * When this is required then a peer should be added to the properties field
 * Each procedure has a specific parameter field called in the way of "procedureName-config" containing a peer entries in the way of: 
 *      tableName+"_specialFieldsCheck - Specify the field having this need.
 *      tableName+"_specialFieldsCheck_methodName - The method to be invoked that contains the logic.
 * @param rdbm - Rdbms - database communication channel
 * @param schemaName - String - Procedure
 * @param fieldNames - String[] - fields involved in the actionName being performed
 * @param fieldValues - Object[] - field values 
 * @param tableName - String - Table Name
 * @param actionName - String - action being performed
 * @return String[] - Returns detailed info about the evaluation and where it ends, position 3 set to TRUE means all is ok otherwise FALSE.
 */    
    public String[] specialFieldsCheck(String schemaName, String[] fieldNames, Object[] fieldValues, String tableName, String actionName){
        String errorCode = ""; String errorDetail = "";
        Object[] errorDetailVariables = new Object[0];        
        String specialFieldNamePropertyName = tableName+"_specialFieldsCheck";
        String specialFieldMethodNamePropertyName = tableName+"_specialFieldsCheck_methodName";        
        
        String specialFieldName = Parameter.getParameterBundle(schemaName.replace("\"", ""), specialFieldNamePropertyName);
        String specialFieldMethodName = Parameter.getParameterBundle(schemaName.replace("\"", ""), specialFieldMethodNamePropertyName);
        String[] specialFields = specialFieldName.split("\\|");
        String[] specialFieldsMethods = specialFieldMethodName.split("\\|");
        String specialFieldsCheck = "";
        Integer specialFieldIndex = -1;
        
        for (Integer inumLines=0;inumLines<fieldNames.length;inumLines++){
            String currField = fieldNames[inumLines];
            String currFieldValue = fieldValues[inumLines].toString();
            boolean contains = Arrays.asList(specialFields).contains(currField);
            if (contains){                    
                    try {                    
                        specialFieldIndex = Arrays.asList(specialFields).indexOf(currField);
                        String aMethod = specialFieldsMethods[specialFieldIndex];
                        Method method = null;
                        try {
                            Class<?>[] paramTypes = {Rdbms.class, String[].class, Object[].class, String.class};
                            method = getClass().getDeclaredMethod(aMethod, paramTypes);
                        } catch (NoSuchMethodException | SecurityException ex) {
                        }
                        Object specialFunctionReturn = method.invoke(this, fieldNames, fieldValues, schemaName);
                        if (specialFunctionReturn.toString().contains("ERROR")){
                            errorCode = "LabPLANETPlatform_SpecialFunctionReturnedERROR";
                            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currField);
                            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aMethod);
                            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, specialFunctionReturn.toString());                            
                            return (String[]) trapErrorMessage(LAB_FALSE, errorCode, errorDetailVariables);
                        }
                    } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException ex) {
                            errorCode = "LabPLANETPlatform_SpecialFunctionCausedException";
                            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currField);
                            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, ex.getCause());
                            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, ex.getMessage());                            
                            return (String[]) trapErrorMessage(LAB_FALSE, errorCode, errorDetailVariables);                      
                    }
            }
        }         
        errorCode = "LabPLANETPlatform_SpecialFunctionAllSuccess";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, specialFieldName.replace("\\|", ", "));
        return (String[]) trapErrorMessage(LAB_TRUE, errorCode, errorDetailVariables);                      
    }
/**
 * Get Class Method Name dynamically for the method that call this method.
 * @return String - Class method name
 */
    public static String getClassMethodName(){
        return Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX].getMethodName();
    }
    
    private static final int CLIENT_CODE_STACK_INDEX;
    
    static{
        int i = 0;
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()){
            i++;
            if (ste.getClassName().equals(LPPlatform.class.getName())){
                break;
            }
        }
        CLIENT_CODE_STACK_INDEX = i;
    }
    
/**
 * trapErrorMessage is the method that should reduce the lines of code for justifying lines of code against its requirement
 * to keep the track about which is the requirement covered by each section in each method.
 * When running the code in Dev-Mode then it should mark as 'covered' the existing requirement or create one record for this given requirement
 * The parameter.config.labtimus mandatoryFields_requerimentsJavaDoc defines which are the mandatory fields that should be added
 * to the peer fields/values to let this call be consider fill enough to proceed.
 * 
 * @param rdbm Rdbms - The connection channel to the database.
     * @param evaluation
 * @param classVersion String - The LabPLANET internal class version
 * @param errorCode String - Error code
     * @param errorVariables
     * @return Object[]
 */
    public static Object[] trapErrorMessage(String evaluation, String errorCode, Object[] errorVariables) {
                
        Object[] fldValue = new Object[7];
        String errorDetail = "";
        Object[] errorDetailVariables = new Object[0];
        String className = Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX].getFileName(); 
        String classFullName = Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX].getClassName(); 
        String methodName = Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX].getMethodName(); 
        Integer lineNumber = Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX].getLineNumber(); 
        className = className.replace(".java", "");
        Boolean errorCodeFromBundle = true;
        String errorCodeText = Parameter.getParameterBundle(CONFIG_FILES_FOLDER, "errorTraping", null, className+"_"+errorCode, null);
        if (errorCodeText.length()==0){errorCodeText = Parameter.getParameterBundle(CONFIG_FILES_FOLDER, "errorTraping", null, errorCode, null);}
        if (errorCodeText.length()==0){errorCodeText = errorCode; errorCodeFromBundle=false;}
        
        if (!errorCodeFromBundle){
            if ( errorVariables.length>0){errorDetail = (String) errorVariables[0];
                if (errorVariables!=null){
                    for (int iVarValue=1; iVarValue<errorVariables.length; iVarValue++){
                        errorDetail = errorDetail.replace("<*"+String.valueOf(iVarValue)+"*>", errorVariables[iVarValue].toString());
                    }
                }
            }            
        }else{
            errorDetail = Parameter.getParameterBundle(CONFIG_FILES_FOLDER, "errorTraping", null, className+"_"+errorCode+"_detail", null);
            if (errorDetail.length()==0){errorDetail = Parameter.getParameterBundle(CONFIG_FILES_FOLDER, "errorTraping", null, errorCode+"_detail", null);}
            if (errorVariables!=null){
                for (int iVarValue=1; iVarValue<=errorVariables.length; iVarValue++){
                    errorDetail = errorDetail.replace("<*"+String.valueOf(iVarValue)+"*>", errorVariables[iVarValue-1].toString());
                }
            }
        }
        fldValue[0] = evaluation; 
        fldValue[1] = classFullName + "." + methodName;
        fldValue[2] = "-999";
        fldValue[3] = "Code line " + lineNumber.toString();
        fldValue[4] = errorCode;
        fldValue[5] = errorCodeText;
        fldValue[6] = errorDetail;

         return fldValue;
  }
    
    /**
     *
     * @param errorArray
     * @return
     */
    public static JSONObject trapErrorMessageJSON(Object[] errorArray){
                
        JSONObject errorJson = new JSONObject();
            errorJson.put("evaluation", errorArray[0]);
            errorJson.put(JAVADOC_CLASS_FLDNAME, errorArray[1]);
            errorJson.put("classVersion", errorArray[2]);
            errorJson.put("Code line", errorArray[3]);
            errorJson.put("errorCode", errorArray[4]);
            errorJson.put("errorCodeText", errorArray[5]);
            errorJson.put("errorDetail", errorArray[6]);
        return errorJson;
    }
    
}