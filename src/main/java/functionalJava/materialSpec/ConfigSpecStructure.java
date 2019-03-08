/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.materialSpec;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;
import static LabPLANET.utilities.LabPLANETPlatform.trapErrorMessage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The specification is considered one structure belonging to the material definition.<br>
 * This class contains all the required to verify that anything related to this structure will be properly defined accordingly
 * @version 0.1 
 * @author Fran Gomez
 */
public class ConfigSpecStructure {

    Object[] diagnoses = new Object[6];
    String classVersion = "Class Version=0.1";
    
    String[] mandatoryFields = new String[1];
    Object[] mandatoryFieldValue = new String[0];
        
    String mandatoryFieldsMissing = "";
    
    LabPLANETArray LabPLANETArray = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();

    
    private String[] getSpecialFields(){
        String[] mySpecialFields = new String[6];
        
        mySpecialFields[0]="spec.analyses";
        mySpecialFields[1]="spec.variation_names";        
        mySpecialFields[2]="spec_limits.variation_name";
        mySpecialFields[3]="spec_limits.analysis";
        mySpecialFields[4]="spec_limits.rule_type";
        
        return mySpecialFields;
    }
    
    private String[] getSpecialFieldsFunction(){
        String[] mySpecialFields = new String[6];
                
        mySpecialFields[0]="specialFieldCheckSpecAnalyses";        
        mySpecialFields[1]="specialFieldCheckSpecVariationNames";
        mySpecialFields[2]="specialFieldCheckSpecLimitsVariationName";
        mySpecialFields[3]="specialFieldCheckSpecLimitsAnalysis";
        mySpecialFields[4]="specialFieldCheckSpecLimitsRuleType";

        return mySpecialFields;
    }

    private String[] getSpecMandatoryFields(){
        String[] myMandatoryFields = new String[2];
        myMandatoryFields[0] = "code";
        myMandatoryFields[1] = "config_version";
        return myMandatoryFields;
    }
    
    private String[] getSpecLimitsMandatoryFields(){
        String[] myMandatoryFields = new String[9];
        myMandatoryFields[0] = "variation_name";
        myMandatoryFields[1] = "analysis";
        myMandatoryFields[2] = "method_name";
        myMandatoryFields[3] = "method_version";
        myMandatoryFields[4] = "parameter";
        myMandatoryFields[5] = "rule_type";
        myMandatoryFields[6] = "rule_variables";
        myMandatoryFields[7] = "code";
        myMandatoryFields[8] = "config_version";
        return myMandatoryFields;
    }
    
    /**
     *
     * @param parameters
     * @return
     */
    public String specialFieldCheckSpecAnalyses(String[] parameters){
                
        String myDiagnoses = "";
        String schemaPrefix = parameters[0];
        String variationNames = parameters[1];
        
        String schemaName = "config";
        
        schemaName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaName);

        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("code");        
        String specCode = (String) mandatoryFieldValue[specialFieldIndex];

        String variationNameExist = "";

if (1==1){myDiagnoses="SUCCESS, but not implemeneted yet"; return myDiagnoses;}
        
        //String[] variationNameArray = variationNames.split("\\|", -1);
        
        Object[] variationNameDiagnosticArray = specVariationGetNamesList(schemaPrefix, specCode);
        if (!variationNameDiagnosticArray[3].toString().equalsIgnoreCase("TRUE")){
            myDiagnoses = "SUCCESS";
        }
        else{
            String[] currVariationNameArray = variationNameDiagnosticArray[4].toString().split("\\|", -1);
            for (String currVariation: currVariationNameArray){   
                if (!variationNames.contains(currVariation)){
                    if (variationNameExist.length()>0){variationNameExist=variationNameExist+" , ";}
                    variationNameExist=variationNameExist+currVariation;
                }            
            }                
        }
        if (variationNameExist.length()>0){
            myDiagnoses = "ERROR: Those variations (" +variationNameExist+") are part of the spec "+specCode+ " and cannot be removed from the variations name by this method";
        }else{    
            myDiagnoses = "SUCCESS";
        }        
        
        return myDiagnoses;
    }
            
    /**
     *
     * @param rdbm
     * @param parameters
     * @return
     */
    public String specialFieldCheckSpecVariationNames( String[] parameters){
                
        String myDiagnoses = "";
        String schemaPrefix = parameters[0];
        String variationNames = parameters[1];

        
        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("code");        
        String specCode = (String) mandatoryFieldValue[specialFieldIndex];

        String variationNameExist = "";

        
        //String[] variationNameArray = variationNames.split("\\|", -1);
        
        Object[] variationNameDiagnosticArray = specVariationGetNamesList(schemaPrefix, specCode);
        if (!variationNameDiagnosticArray[3].toString().equalsIgnoreCase("TRUE")){
            myDiagnoses = "SUCCESS";
        }
        else{
            String[] currVariationNameArray = variationNameDiagnosticArray[4].toString().split("\\|", -1);
            for (String currVariation: currVariationNameArray){   
                if (!variationNames.contains(currVariation)){
                    if (variationNameExist.length()>0){variationNameExist=variationNameExist+" , ";}
                    variationNameExist=variationNameExist+currVariation;
                }            
            }                
        }
        if (variationNameExist.length()>0){
            myDiagnoses = "ERROR: Those variations (" +variationNameExist+") are part of the spec "+specCode+ " and cannot be removed from the variations name by this method";
        }else{    
            myDiagnoses = "SUCCESS";
        }        
        
        return myDiagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @return
     * @throws SQLException
     */
    public String specialFieldCheckSpecLimitsVariationName(String schemaPrefix) throws SQLException{ //, String schemaPrefix, String analysisList){                        
                
        String analysesMissing = "";
        String myDiagnoses = "";        
        String query = "";
        String specVariations = "";
        String schemaName = "config";
        
        mandatoryFields = getSpecLimitsMandatoryFields();

        schemaName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaName);

        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("variation_name");
        String varationName = (String) mandatoryFieldValue[specialFieldIndex];

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("code");
        String specCode = (String) mandatoryFieldValue[specialFieldIndex];

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("config_version");
        Integer specCodeVersion = (Integer) mandatoryFieldValue[specialFieldIndex];

        Object[][] recordFieldsByFilter = Rdbms.getRecordFieldsByFilter(schemaName, "spec", new String[]{"code","config_version"}, new Object[]{specCode, specCodeVersion}, 
                new String[]{"variation_names","code","config_version","code"});
        if ("FALSE".equalsIgnoreCase(recordFieldsByFilter[0][3].toString())){
            myDiagnoses = "ERROR: "+ recordFieldsByFilter[0][3]; return myDiagnoses;
        }              
        
        specVariations = recordFieldsByFilter[0][0].toString();
        String[] strArray = specVariations.split("\\|", -1);
        
        if (Arrays.asList(strArray).indexOf(varationName)==-1){
            myDiagnoses = "ERROR: The variation " + varationName + " is not one of the variations ("+ specVariations.replace("|", ", ") + ") on spec "+specCode+"  in the schema "+schemaPrefix+". Missed analysis="+analysesMissing;
        }else{    
            myDiagnoses = "SUCCESS";
        }        
        return myDiagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @return
     */
    public String specialFieldCheckSpecLimitsAnalysis(String schemaPrefix){ //, String schemaPrefix, String analysisList){                        

        String myDiagnoses = "";  
        String schemaName = "config";
        
        schemaName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaName);

        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("analysis");
        String analysis =(String)  mandatoryFieldValue[specialFieldIndex];     
        if (analysis.length()==0){myDiagnoses = "ERROR: The parameter analysis cannot be null"; return myDiagnoses;}

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("method_name");
        String methodName = (String) mandatoryFieldValue[specialFieldIndex];     
        if (methodName.length()==0){myDiagnoses = "ERROR: The parameter method_name cannot be null"; return myDiagnoses;}

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("method_version");        
        Integer methodVersion = (Integer) mandatoryFieldValue[specialFieldIndex];     
        if (methodVersion==null){myDiagnoses = "ERROR: The parameter method_version cannot be null"; return myDiagnoses;}
                
        String[] fieldNames = new String[3];
        Object[] fieldValues = new Object[3];
                
        fieldNames[0]="analysis";
        fieldValues[0]=analysis;
        fieldNames[1]="method_name";
        fieldValues[1]=methodName;
        fieldNames[2]="method_version";        
        fieldValues[2]=methodVersion;                            
        
        Object[] diagnosis = Rdbms.existsRecord(schemaName, "analysis_method", fieldNames, fieldValues);        
        if ("LABPLANET_TRUE".equalsIgnoreCase(diagnosis[0].toString())){
            myDiagnoses = "SUCCESS";        }
        else{    
            diagnosis = Rdbms.existsRecord(schemaName, "analysis", 
                    new String[]{"code"}, new Object[]{analysis});
            if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                myDiagnoses = "ERROR: The analysis " + analysis + " exists but the method " + methodName +" with version "+ methodVersion+ " was not found in the schema "+schemaPrefix;            
            }
            else{
                myDiagnoses = "ERROR: The analysis " + analysis + " is not found in the schema "+schemaPrefix;            
            }
        }        
        return myDiagnoses;
    }
    
    /**
     *bm
     * @param schemaPrefix
     * @return
     */
    public String specialFieldCheckSpecLimitsRuleType(String schemaPrefix){ //, String schemaPrefix, String analysisList){                        
        
        String schemaName = "config";
        
        schemaName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaName);

        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("rule_type");
        String ruleType = (String) mandatoryFieldValue[specialFieldIndex];        
        
        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("rule_variables");
        String ruleVariables = (String) mandatoryFieldValue[specialFieldIndex];                
        
        String myDiagnoses = "";        
        
        String[] ruleVariablesArr = ruleVariables.split("\\*", -1);
        switch (ruleType.toUpperCase()){
            case "QUALITATIVE":
                if (ruleVariablesArr.length!=3 && ruleVariablesArr.length!=2){
                    myDiagnoses="ERROR: Qualitative rule type requires 2 or 3 parameters and the string ("+ruleVariables+") contains "+ruleVariablesArr.length+ " parameters";
                    return myDiagnoses;
                }
                ConfigSpecRule qualSpec = new ConfigSpecRule();
                Object[] isCorrect = null;
                if (ruleVariablesArr.length==2){isCorrect = qualSpec.specLimitIsCorrectQualitative(schemaName, ruleVariablesArr[0], ruleVariablesArr[1], null);}                
                else{isCorrect = qualSpec.specLimitIsCorrectQualitative(schemaName, ruleVariablesArr[0], ruleVariablesArr[1], ruleVariablesArr[2]);}
                if ((Boolean) isCorrect[0]==true){myDiagnoses="SUCCESS";}
                else{myDiagnoses="ERROR: "+isCorrect[1];}
                break;
            case "QUANTITATIVE": 
                Float minSpec = null;
                Float maxSpec = null;
                Float minControl = null;
                Float maxControl = null;
                for (String ruleVar: ruleVariablesArr){
                    if (ruleVar.contains("MINSPEC")){ruleVar = ruleVar.replace("MINSPEC", ""); minSpec=Float.parseFloat(ruleVar);}
                    if (ruleVar.contains("MAXSPEC")){ruleVar = ruleVar.replace("MAXSPEC", ""); maxSpec=Float.parseFloat(ruleVar);}
                    if (ruleVar.contains("MINCONTROL")){ruleVar = ruleVar.replace("MINCONTROL", ""); minControl=Float.parseFloat(ruleVar);}
                    if (ruleVar.contains("MAXCONTROL")){ruleVar = ruleVar.replace("MAXCONTROL", ""); maxControl=Float.parseFloat(ruleVar);}
                }
                if (ruleVariablesArr.length!=4 && ruleVariablesArr.length!=4){
                    myDiagnoses="ERROR: Qualitative rule type requires 4 or 4 parameters and the string ("+ruleVariables+") contains "+ruleVariablesArr.length+ " parameters";
                    return myDiagnoses;
                }                
                ConfigSpecRule quantSpec2 = new ConfigSpecRule();
                isCorrect = quantSpec2.specLimitIsCorrectQuantitative(minSpec, maxSpec, minControl, maxControl);                
                if ((Boolean) isCorrect[0]==true){myDiagnoses="SUCCESS";}
                else{myDiagnoses="ERROR: "+isCorrect[1];}
                break;       
            default:   
                myDiagnoses = "ERROR: The rule type " + ruleType + " is not recognized";                
                break;
        }
        return myDiagnoses;                    
    }
    
    /**
     *
     * @param schemaPrefix
     * @param code
     * @return
     */
    public Object[] _specRemove(String schemaPrefix, String code){
        return diagnoses;
    }
        
    /**
     *
     * @param schemaPrefix
     * @param specCode
     * @param specCodeVersion
     * @param specFieldName
     * @param specFieldValue
     * @return
     * @throws SQLException
     */
    public Object[] specUpdate( String schemaPrefix, String specCode, Integer specCodeVersion, String[] specFieldName, Object[] specFieldValue) throws SQLException{
        
        String schemaName = "config";        
        Object[] diagnoses = new Object[6];
        
        String errorCode = "DataSample_SpecialFunctionReturnedERROR";
        Object[] errorDetailVariables = new Object[0];
         
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        schemaName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaName);
            
        diagnoses = Rdbms.existsRecord(schemaName, "spec", new String[]{"code", "config_version"}, new Object[] {specCode, specCodeVersion});        
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnoses[0].toString())){return diagnoses;}
        
        String[] specialFields = getSpecialFields();
        String[] specialFieldsFunction = getSpecialFieldsFunction();
        
        String specialFieldsCheck = "";
        for (Integer inumLines=0;inumLines<specFieldName.length;inumLines++){
            String currField = "spec." + specFieldName[inumLines];
            String currFieldValue = specFieldValue[inumLines].toString();
            boolean contains = Arrays.asList(specialFields).contains(currField);
            if (contains){                    
                Integer specialFieldIndex = Arrays.asList(specialFields).indexOf(currField);
                String aMethod = specialFieldsFunction[specialFieldIndex];
                Method method = null;
                try {
                    Class<?>[] paramTypes = {Rdbms.class, String[].class};
                    method = getClass().getDeclaredMethod(aMethod, paramTypes);
                } catch (NoSuchMethodException | SecurityException ex) {
                    Logger.getLogger(ConfigSpecStructure.class.getName()).log(Level.SEVERE, null, ex);
                }
                String[] parameters = new String[3];
                parameters[0]=schemaName;
                parameters[1]=currFieldValue;
                parameters[2]=specCode;
                Object specialFunctionReturn = null;      
                try {                        
                    specialFunctionReturn = method.invoke(this, (Object[]) parameters);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(ConfigSpecStructure.class.getName()).log(Level.SEVERE, null, ex);
                }
                if ("ERROR".equalsIgnoreCase(specialFunctionReturn.toString())){
                    errorCode = "DataSample_SpecialFunctionReturnedERROR";
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currField);
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aMethod);
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, specialFunctionReturn.toString());
                    return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                                                
                }
                //String specialFunctionReturnStatus = String.valueOf(specialFunctionReturn);
            }
        }      
        try{
            String[] whereFieldNames = new String[0];
            whereFieldNames = LabPLANETArray.addValueToArray1D(whereFieldNames, "code");
            whereFieldNames = LabPLANETArray.addValueToArray1D(whereFieldNames, "config_version");
            Object[] whereFieldValues = new Object[0];
            whereFieldValues = LabPLANETArray.addValueToArray1D(whereFieldValues, specCode);
            whereFieldValues = LabPLANETArray.addValueToArray1D(whereFieldValues, specCodeVersion);            
            diagnoses = Rdbms.updateRecordFieldsByFilter(schemaName, "spec", specFieldName, specFieldValue, whereFieldNames, whereFieldValues);

           if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
               diagnoses = Rdbms.insertRecordInTable(schemaName, "spec_rules", new String[]{"code", "config_version", "allow_other_analysis", "allow_multi_spec"}, new Object[] {specCode, 1, false, false});
               return diagnoses;
           }else{
               return diagnoses;
           }
           
/*           String query = "update "+ schemaName + ".spec set ";
           for (String fieldName: specFieldName){
               query=query+fieldName+"=? ";
           }
           query=query+ " where code=? and =? ";

           Object[] fieldValues = new Object[specFieldName.length+2];
           
           
           System.arraycopy(specFieldValue, 0, fieldValues, 0, specFieldValue.length);

           fieldValues[specFieldValue.length-1]=specCode;
           fieldValues[specFieldValue.length]=specCodeVersion;

           Integer queryInsertNum = rdbm.prepUpQuery(query, fieldValues);

           if (queryInsertNum > 0){
               query= "insert into "+ schemaName + ".spec_rules (code, config_version, allow_other_analysis, allow_multi_spec) values (?,?,?,?)";
               queryInsertNum = rdbm.prepUpQuery(query, new Object[] {specCode, 1, false, false});

               StackTraceElement[] elements = Thread.currentThread().getStackTrace();
               diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
               diagnoses[1]= classVersion;
               diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
               diagnoses[3]="TRUE";
               diagnoses[4]="SUCCESS: SPEC UPDATED";
               diagnoses[5]="The Spec "+specCode+" was updated successfully";
               return diagnoses;
           }
*/
       } catch (IllegalArgumentException ex) {
           Logger.getLogger(ConfigSpecStructure.class.getName()).log(Level.SEVERE, null, ex);
       }  
        errorCode = "UnhandledExceptionInCode";
        String Params = "SchemaPrefix: "+schemaPrefix+"specCode"+specCode+"specCodeVersion"+specCodeVersion.toString()
                +"specFieldName"+Arrays.toString(specFieldName)+"specFieldValue"+Arrays.toString(specFieldValue);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Params);        
        return trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);
    }

    /**
     *
     * @param rdbm
     * @param schemaPrefix
     * @param specFieldName
     * @param specFieldValue
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Object[] specNew( String schemaPrefix, String[] specFieldName, Object[] specFieldValue ) throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{                          
        String newCode = "";
        String schemaName = "config";
        String query = "";
        String errorCode = "";
        String[] errorDetailVariables = new String[0];
        
        schemaName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaName);

        mandatoryFields = getSpecMandatoryFields();
        
        LabPLANETArray lpa = new LabPLANETArray();

        String[] checkTwoArraysSameLength = lpa.checkTwoArraysSameLength(specFieldName, specFieldValue);
        if ("LABPLANET_FALSE".equalsIgnoreCase(checkTwoArraysSameLength[0])){return checkTwoArraysSameLength;}

        if (lpa.duplicates(specFieldName)){
           errorCode = "DataSample_FieldsDuplicated";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(specFieldName));
           return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                      
        }

        mandatoryFieldsMissing = "";
        for (Integer inumLines=0;inumLines<mandatoryFields.length;inumLines++){
            String currField = mandatoryFields[inumLines];
            boolean contains = Arrays.asList(specFieldName).contains(currField.toLowerCase());
            if (!contains){
                if (mandatoryFieldsMissing.length()>0){mandatoryFieldsMissing = mandatoryFieldsMissing + ",";}
                mandatoryFieldsMissing = mandatoryFieldsMissing + currField;
            }
            else{
                Object currFieldValue = specFieldValue[Arrays.asList(specFieldName).indexOf(currField.toLowerCase())];
                mandatoryFieldValue = (Object[]) LabPLANETArray.addValueToArray1D(mandatoryFieldValue, currFieldValue);
            }
            
        }            
        if (mandatoryFieldsMissing.length()>0){
           errorCode = "DataSample_MissingMandatoryFields";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                
        }

        Integer fieldIndex = Arrays.asList(specFieldName).indexOf("code");
        newCode = specFieldValue[fieldIndex].toString();
        fieldIndex = Arrays.asList(specFieldName).indexOf("config_version");
        Integer newCodeVersion = (Integer) specFieldValue[fieldIndex];

        String[] specialFields = getSpecialFields();
        String[] specialFieldsFunction = getSpecialFieldsFunction();
        String specialFieldsCheck = "";
        for (Integer inumLines=0;inumLines<specFieldName.length;inumLines++){
            String currField = "spec." + specFieldName[inumLines];
            String currFieldValue = specFieldValue[inumLines].toString();
            boolean contains = Arrays.asList(specialFields).contains(currField);
            if (contains){                    
                    Integer specialFieldIndex = Arrays.asList(specialFields).indexOf(currField);
                    String aMethod = specialFieldsFunction[specialFieldIndex];
                    Method method = null;
                    try {
                        Class<?>[] paramTypes = {Rdbms.class, String[].class};
                        method = getClass().getDeclaredMethod(aMethod, paramTypes);
                    } catch (NoSuchMethodException | SecurityException ex) {
                        Logger.getLogger(ConfigSpecStructure.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String[] parameters = new String[3];
                    parameters[0]=schemaName;
                    parameters[1]=currFieldValue;
                    parameters[2]=newCode;
                    Object specialFunctionReturn = method.invoke(this, (Object[]) parameters);      
                    if (specialFunctionReturn.toString().contains("ERROR")){
                        errorCode = "DataSample_SpecialFunctionReturnedERROR";
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currField);
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aMethod);
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, specialFunctionReturn.toString());
                        return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                            
                    }
                    //String specialFunctionReturnStatus = String.valueOf(specialFunctionReturn);
            }
        }
        diagnoses = Rdbms.existsRecord(schemaName, "spec", new String[]{"code", "config_version"}, new Object[] {newCode, newCodeVersion});        
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnoses[0].toString())){
            errorCode = "specRecord_AlreadyExists";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, newCode);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, newCodeVersion.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
            return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);           
        }
        try{
            diagnoses = Rdbms.insertRecordInTable(schemaName, "spec", specFieldName, specFieldValue);            
            
            diagnoses = Rdbms.insertRecordInTable(schemaName, "spec_rules", 
                    new String[]{"code", "config_version", "allow_other_analysis", "allow_multi_spec"}, 
                    new Object[]{newCode, newCodeVersion, false, false});       
            if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                errorCode = "specRecord_createdSuccessfully";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, newCode);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
                return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_TRUE", errorCode, errorDetailVariables);                   
            }    
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ConfigSpecStructure.class.getName()).log(Level.SEVERE, null, ex);
        }                    
        errorCode = "UnhandledExceptionInCode";
        String params = "schemaPrefix: " + schemaPrefix+"specFieldName: "+Arrays.toString(specFieldName)+"specFieldValue: "+Arrays.toString(specFieldValue);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, params);
        return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                  
    }
    
    /**
     *dbm
     * @param schemaPrefix
     * @param specCode
     * @return
     */
    public Object[] specVariationGetNamesList( String schemaPrefix, String specCode){

        String schemaName = "config";
        Object[] diagnosis = new Object[6];
        String variationList = "";
        String errorCode ="";
        
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        schemaName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaName);
        
        Object[][] variationListArray = Rdbms.getRecordFieldsByFilter(schemaName, "spec_limits", new String[]{"code"}, new Object[]{specCode}, new String[]{"name"});
        if ("LABPLANET_FALSE".equalsIgnoreCase(variationListArray[0][0].toString())){            
            return LabPLANETArray.array2dTo1d(variationListArray);
        }else{
            for (int i=0;i<=variationListArray.length;i++){
                 if (variationList.length()>0){variationList=variationList+"|";}
                 variationList=variationList+variationListArray[i][0].toString();
             }
            errorCode = "specVariationGetNamesList_successfully";
            return LabPLANETPlatform.trapErrorMessage("LABPLANET_TRUE", errorCode, new String[]{variationList});            
        }
/*        String query = ""; //distinct on (name)
        query = "select name from " + schemaName + ".spec_limits "
                + "   where code=? ";               
        try{
            ResultSet res = rdbm.prepRdQuery(query, new Object [] {specCode});
            res.last();

            if (res.getRow()>0){  
                
                String[] variationListArray = new String[res.getRow()];
                res.first();
                for (int i=0;i<=res.getRow();i++){
                    if (variationList.length()>0){variationList=variationList+"|";}
                    variationList=variationList+res.getString("name");
                }
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                diagnosis[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                diagnosis[1]= classVersion;
                diagnosis[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
                diagnosis[3]="TRUE";
                diagnosis[4]=variationList;
                diagnosis[5]=String.valueOf(res.getRow())+" records found, Query: "+query;
                return diagnosis;  
            }
            else{
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                diagnosis[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                diagnosis[1]= classVersion;
                diagnosis[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
                diagnosis[3]="FALSE";
                diagnosis[4]="ERROR:VARIATIONS NOT FOUND";
                diagnosis[5]="No records found, Query: "+query;
                return diagnosis;         
            }

        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnosis[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnosis[1]= classVersion;
            diagnosis[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnosis[3]="FALSE";
            diagnosis[4]="ERROR:DB RETURNED ERROR";
            diagnosis[5]="The database returned error: "+ermessage+ " Query: "+query;
            return diagnosis;                
        }   
*/
    }
    
    /**
     *
     * @param schemaPrefix
     * @param specFieldName
     * @param specFieldValue
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Object[] specLimitNew( String schemaPrefix, String[] specFieldName, Object[] specFieldValue ) throws SQLException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
                          
        String code = "";
        String schemaName = "config";
        String query = "";
        String errorCode="";
        Object[]  errorDetailVariables= new Object[0];

        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        LabPLANETArray LabPLANETArray = new LabPLANETArray();
        schemaName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaName);

        mandatoryFields = getSpecLimitsMandatoryFields();

        String[] checkTwoArraysSameLength = LabPLANETArray.checkTwoArraysSameLength(specFieldName, specFieldValue);
        if ("LABPLANET_FALSE".equalsIgnoreCase(checkTwoArraysSameLength[0])){return checkTwoArraysSameLength;}

        if (LabPLANETArray.duplicates(specFieldName)){
           errorCode = "DataSample_FieldsDuplicated";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(specFieldName));
           return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                      
        }
        
        mandatoryFieldsMissing = "";
        for (Integer inumLines=0;inumLines<mandatoryFields.length;inumLines++){
            String currField = mandatoryFields[inumLines];
            boolean contains = Arrays.asList(specFieldName).contains(currField.toLowerCase());
            if (!contains){
                if (mandatoryFieldsMissing.length()>0){mandatoryFieldsMissing = mandatoryFieldsMissing + ",";}
                mandatoryFieldsMissing = mandatoryFieldsMissing + currField;
            }
            else{
                Object currFieldValue = specFieldValue[Arrays.asList(specFieldName).indexOf(currField.toLowerCase())];
                mandatoryFieldValue = LabPLANETArray.addValueToArray1D(mandatoryFieldValue, currFieldValue);
            }
        }                    
        Integer fieldIndex = Arrays.asList(specFieldName).indexOf("code");
        code = specFieldValue[fieldIndex].toString();
        fieldIndex = Arrays.asList(specFieldName).indexOf("config_version");
        Integer codeVersion = (Integer) specFieldValue[fieldIndex];

        diagnoses = Rdbms.existsRecord(schemaName, "spec", new String[]{"code", "config_version"}, new Object[] {code, codeVersion});        
        if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){                       
            return diagnoses;
        }
        
        if (mandatoryFieldsMissing.length()>0){
           errorCode = "DataSample_MissingMandatoryFields";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);    
        }
        
        String[] specialFields = getSpecialFields();
        String[] specialFieldsFunction = getSpecialFieldsFunction();
        String specialFieldsCheck = "";
        for (Integer inumLines=0;inumLines<specFieldName.length;inumLines++){
            String currField = "spec_limits." + specFieldName[inumLines];
            String currFieldValue = specFieldValue[inumLines].toString();
            boolean contains = Arrays.asList(specialFields).contains(currField);
            if (contains){                    
                Integer specialFieldIndex = Arrays.asList(specialFields).indexOf(currField);
                String aMethod = specialFieldsFunction[specialFieldIndex];
                Method method = null;
                try {
                    Class<?>[] paramTypes = {Rdbms.class, String.class};
                    method = getClass().getDeclaredMethod(aMethod, paramTypes);
                } catch (NoSuchMethodException | SecurityException ex) {
                    Logger.getLogger(ConfigSpecStructure.class.getName()).log(Level.SEVERE, null, ex);
                }                        
                try {
                    Object specialFunctionReturn = method.invoke(this, schemaName);      
                    if (specialFunctionReturn.toString().contains("ERROR")){
                        errorCode = "DataSample_SpecialFunctionReturnedERROR";
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currField);
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aMethod);
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, specialFunctionReturn.toString());
                        return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                            
                    }
                }
                catch(InvocationTargetException ite){
                    errorCode = "LabPLANETPlatform_SpecialFunctionCausedException";
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aMethod);
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, ite.getMessage());                        
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "Spec Limits");
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
                    return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                         
                }
            }
        }                        

        fieldIndex = Arrays.asList(specFieldName).indexOf("analysis");
        String analysis = (String) specFieldValue[fieldIndex];
        fieldIndex = Arrays.asList(specFieldName).indexOf("method_name");
        String methodName = (String) specFieldValue[fieldIndex];
        fieldIndex = Arrays.asList(specFieldName).indexOf("method_version");
        Integer methodVersion = (Integer) specFieldValue[fieldIndex]; 
        String tableName = "analysis_method";
        String[] whereFields = new String[]{"analysis", "method_name", "method_version"};
        Object[] whereFieldsValue = new Object[] {analysis, methodName, methodVersion};
        diagnoses = Rdbms.existsRecord(schemaName, tableName, whereFields, whereFieldsValue);                
        if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
            Object[] whereFieldsAndValues = LabPLANETArray.joinTwo1DArraysInOneOf1DString(diagnoses, whereFieldsValue, ":");
            errorCode = "Rdbms_NoRecordsFound";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, tableName);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldsAndValues));                                   
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
            return (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                                            
        }else{
            fieldIndex = Arrays.asList(specFieldName).indexOf("parameter");
            String parameter = (String) specFieldValue[fieldIndex];
            tableName = "analysis_method_params";
            whereFields = new String[]{"analysis", "method_name", "method_version", "param_name"};
            whereFieldsValue = new Object[] {analysis, methodName, methodVersion, parameter};            
            diagnoses = Rdbms.existsRecord(schemaName, tableName, whereFields, whereFieldsValue);      
            if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                Object[] whereFieldsAndValues = LabPLANETArray.joinTwo1DArraysInOneOf1DString(diagnoses, whereFieldsValue, ":");
                errorCode = "Rdbms_NoRecordsFound";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldsAndValues));                                   
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
                diagnoses =  (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                    
                diagnoses[5]="The parameter " + parameter + " was not found even though the method "+ methodName+" in its version " + methodVersion.toString()+" in the analysis " + analysis + " exists in the schema "+schemaName + "......... " + diagnoses[5].toString();                                             
                return diagnoses;}                   
        }
        try{
            diagnoses = Rdbms.insertRecordInTable(schemaName, "spec_limits", specFieldName, specFieldValue); 
            return diagnoses;
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ConfigSpecStructure.class.getName()).log(Level.SEVERE, null, ex);
        }                    
        errorCode = "UnhandledExceptionInCode";
        String params = "schemaPrefix: " + schemaPrefix+"specFieldName: "+Arrays.toString(specFieldName)+"specFieldValue: "+Arrays.toString(specFieldValue);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, params);
        diagnoses =  (String[]) LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                    
        return diagnoses;
    }
    
}
