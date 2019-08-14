/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.materialSpec;

import LabPLANET.utilities.LPNulls;
import databases.Rdbms;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPPlatform;
import static LabPLANET.utilities.LPPlatform.trapErrorMessage;
import databases.dbObjectsConfigTables;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private static final String DIAGNOSES_SUCCESS = "SUCCESS";
    private static final String DIAGNOSES_ERROR = "ERROR";
    
    private static final String ERROR_TRAPING_DATA_SAMPLE_SPECIAL_FUNCTION_RETURN_ERROR="DataSample_SpecialFunctionReturnedERROR";
    

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
        return new String[]{dbObjectsConfigTables.FLD_CONFIG_SPEC_CODE, 
                                      dbObjectsConfigTables.FLD_CONFIG_SPEC_CONFIG_VERSION};
    }
    
    private String[] getSpecLimitsMandatoryFields(){
        String[] myMandatoryFields = new String[9];
        myMandatoryFields[0] = dbObjectsConfigTables.FLD_CONFIG_SPEC_LIMITS_VARIATION_NAME;
        myMandatoryFields[1] = dbObjectsConfigTables.FLD_CONFIG_SPEC_LIMITS_ANALYSIS;
        myMandatoryFields[2] = dbObjectsConfigTables.FLD_CONFIG_SPEC_LIMITS_METHOD_NAME;
        myMandatoryFields[3] = dbObjectsConfigTables.FLD_CONFIG_SPEC_LIMITS_METHOD_VERSION;
        myMandatoryFields[4] = dbObjectsConfigTables.FLD_CONFIG_SPEC_LIMITS_PARAMETER;
        myMandatoryFields[5] = dbObjectsConfigTables.FLD_CONFIG_SPEC_LIMITS_RULE_TYPE;
        myMandatoryFields[6] = dbObjectsConfigTables.FLD_CONFIG_SPEC_LIMITS_RULE_VARIABLES;
        myMandatoryFields[7] = dbObjectsConfigTables.FLD_CONFIG_SPEC_LIMITS_CODE;
        myMandatoryFields[8] = dbObjectsConfigTables.FLD_CONFIG_SPEC_LIMITS_CONFIG_VERSION;
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
        
        String schemaName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);

        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("code");        
        String specCode = (String) mandatoryFieldValue[specialFieldIndex];

        String variationNameExist = "";

if (1==1){myDiagnoses="SUCCESS, but not implemeneted yet"; return myDiagnoses;}
        
        //String[] variationNameArray = variationNames.split("\\|", -1);
        
        Object[] variationNameDiagnosticArray = specVariationGetNamesList(schemaPrefix, specCode);
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(variationNameDiagnosticArray[0].toString())){
            return DIAGNOSES_SUCCESS;
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
            return "ERROR: Those variations (" +variationNameExist+") are part of the spec "+specCode+ " and cannot be removed from the variations name by this method";
        }else{    
            return DIAGNOSES_SUCCESS;
        }        
        
    }
            
    /**
     *
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
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(variationNameDiagnosticArray[0].toString())){
            return DIAGNOSES_SUCCESS;
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
            return "ERROR: Those variations (" +variationNameExist+") are part of the spec "+specCode+ " and cannot be removed from the variations name by this method";
        }else{    
            return DIAGNOSES_SUCCESS;
        }        
    }

    /**
     *
     * @param schemaPrefix
     * @return
     */
    public String specialFieldCheckSpecLimitsVariationName(String schemaPrefix){ //, String schemaPrefix, String analysisList){                        
                
        String analysesMissing = "";
        String myDiagnoses = "";        
        String specVariations = "";
        String schemaName = LPPlatform.SCHEMA_CONFIG;
        
        mandatoryFields = getSpecLimitsMandatoryFields();

        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);

        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(dbObjectsConfigTables.FLD_CONFIG_SPEC_VARIATION_NAME);
        String varationName = (String) mandatoryFieldValue[specialFieldIndex];

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(dbObjectsConfigTables.FLD_CONFIG_SPEC_CODE);
        String specCode = (String) mandatoryFieldValue[specialFieldIndex];

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(dbObjectsConfigTables.FLD_CONFIG_SPEC_CONFIG_VERSION);
        Integer specCodeVersion = (Integer) mandatoryFieldValue[specialFieldIndex];

        Object[][] recordFieldsByFilter = Rdbms.getRecordFieldsByFilter(schemaName, dbObjectsConfigTables.TABLE_CONFIG_SPEC, 
                new String[]{dbObjectsConfigTables.FLD_CONFIG_SPEC_CODE, dbObjectsConfigTables.FLD_CONFIG_SPEC_CONFIG_VERSION}, 
                new Object[]{specCode, specCodeVersion}, 
                new String[]{"variation_names",dbObjectsConfigTables.FLD_CONFIG_SPEC_CODE,dbObjectsConfigTables.FLD_CONFIG_SPEC_CONFIG_VERSION,dbObjectsConfigTables.FLD_CONFIG_SPEC_CODE});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(recordFieldsByFilter[0][0].toString())){
            myDiagnoses = "ERROR: "+ recordFieldsByFilter[0][3]; return myDiagnoses;
        }              
        
        specVariations = recordFieldsByFilter[0][0].toString();
        String[] strArray = specVariations.split("\\|", -1);
        
        if (Arrays.asList(strArray).indexOf(varationName)==-1){
            myDiagnoses = "ERROR: The variation " + varationName + " is not one of the variations ("+ specVariations.replace("|", ", ") + ") on spec "+specCode+"  in the schema "+schemaPrefix+". Missed analysis="+analysesMissing;
        }else{    
            myDiagnoses = DIAGNOSES_SUCCESS;
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
        String schemaName = LPPlatform.SCHEMA_CONFIG;
        
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);

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
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnosis[0].toString())){
            return DIAGNOSES_SUCCESS;        }
        else{    
            diagnosis = Rdbms.existsRecord(schemaName, "analysis", 
                    new String[]{"code"}, new Object[]{analysis});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                return "ERROR: The analysis " + analysis + " exists but the method " + methodName +" with version "+ methodVersion+ " was not found in the schema "+schemaPrefix;            
            }
            else{
                return "ERROR: The analysis " + analysis + " is not found in the schema "+schemaPrefix;            
            }
        }        
    }
    
    /**
     *bm
     * @param schemaPrefix
     * @return
     */
    public String specialFieldCheckSpecLimitsRuleType(String schemaPrefix){ //, String schemaPrefix, String analysisList){                        
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
                if (ruleVariablesArr.length==2){isCorrect = qualSpec.specLimitIsCorrectQualitative(ruleVariablesArr[0], ruleVariablesArr[1], null);}                
                else{isCorrect = qualSpec.specLimitIsCorrectQualitative(ruleVariablesArr[0], ruleVariablesArr[1], ruleVariablesArr[2]);}
                if ((Boolean) isCorrect[0]==true){myDiagnoses=DIAGNOSES_SUCCESS;}
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
                if (ruleVariablesArr.length!=4){
                    myDiagnoses="ERROR: Qualitative rule type requires 4 or 4 parameters and the string ("+ruleVariables+") contains "+ruleVariablesArr.length+ " parameters";
                    return myDiagnoses;
                }                
                ConfigSpecRule quantSpec2 = new ConfigSpecRule();
                isCorrect = quantSpec2.specLimitIsCorrectQuantitative(minSpec, maxSpec, minControl, maxControl);                
                if ((Boolean) isCorrect[0]==true){myDiagnoses=DIAGNOSES_SUCCESS;}
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
     */
    public Object[] specUpdate( String schemaPrefix, String specCode, Integer specCodeVersion, String[] specFieldName, Object[] specFieldValue) {
        
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);
        String errorCode = ERROR_TRAPING_DATA_SAMPLE_SPECIAL_FUNCTION_RETURN_ERROR;
        Object[] errorDetailVariables = new Object[0];
            
        diagnoses = Rdbms.existsRecord(schemaConfigName, dbObjectsConfigTables.TABLE_CONFIG_SPEC, 
                new String[]{dbObjectsConfigTables.FLD_CONFIG_SPEC_CODE, dbObjectsConfigTables.FLD_CONFIG_SPEC_CONFIG_VERSION}, new Object[] {specCode, specCodeVersion});        
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses[0].toString())){return diagnoses;}
        
        String[] specialFields = getSpecialFields();
        String[] specialFieldsFunction = getSpecialFieldsFunction();
        
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
                parameters[0]=schemaConfigName;                parameters[1]=currFieldValue;                parameters[2]=specCode;
                Object specialFunctionReturn = DIAGNOSES_ERROR;
                try {                        
                    if (method!=null){ specialFunctionReturn = method.invoke(this, (Object[]) parameters);}
                } catch (IllegalAccessException | NullPointerException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(ConfigSpecStructure.class.getName()).log(Level.SEVERE, null, ex);
                }
                if ( (specialFunctionReturn.toString().contains(DIAGNOSES_ERROR)) ){
                    errorCode = ERROR_TRAPING_DATA_SAMPLE_SPECIAL_FUNCTION_RETURN_ERROR;
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, currField);
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, aMethod);
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, LPNulls.replaceNull(specialFunctionReturn));
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                                                
                }
            }
        }      
        try{
            String[] whereFieldNames = new String[]{dbObjectsConfigTables.FLD_CONFIG_SPEC_CODE, dbObjectsConfigTables.FLD_CONFIG_SPEC_CONFIG_VERSION};
            Object[] whereFieldValues = new Object[0];
            whereFieldValues = LPArray.addValueToArray1D(whereFieldValues, specCode);
            whereFieldValues = LPArray.addValueToArray1D(whereFieldValues, specCodeVersion);            
            diagnoses = Rdbms.updateRecordFieldsByFilter(schemaConfigName, dbObjectsConfigTables.TABLE_CONFIG_SPEC, specFieldName, specFieldValue, whereFieldNames, whereFieldValues);

           if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
               diagnoses = Rdbms.insertRecordInTable(schemaConfigName, dbObjectsConfigTables.TABLE_CONFIG_SPEC_RULES, 
                       new String[]{dbObjectsConfigTables.FLD_CONFIG_SPEC_RULES_CODE, dbObjectsConfigTables.FLD_CONFIG_SPEC_RULES_CONFIG_VERSION, 
                           dbObjectsConfigTables.FLD_CONFIG_SPEC_RULES_ALLOW_OTHER_ANALYSIS, dbObjectsConfigTables.FLD_CONFIG_SPEC_RULES_ALLOW_MULTI_SPEC}, 
                       new Object[] {specCode, 1, false, false});
           }
           return diagnoses;
       } catch (IllegalArgumentException ex) {
           Logger.getLogger(ConfigSpecStructure.class.getName()).log(Level.SEVERE, null, ex);
       }  
        errorCode = "UnhandledExceptionInCode";
        String params = "SchemaPrefix: "+schemaPrefix+"specCode"+specCode+"specCodeVersion"+specCodeVersion.toString()
                +"specFieldName"+Arrays.toString(specFieldName)+"specFieldValue"+Arrays.toString(specFieldValue);
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, params);        
        return trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
    }

    /**
     *
     * @param schemaPrefix
     * @param specFieldName
     * @param specFieldValue
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Object[] specNew( String schemaPrefix, String[] specFieldName, Object[] specFieldValue ) throws IllegalAccessException, InvocationTargetException{                          
        String newCode = "";
        String errorCode = "";
        String[] errorDetailVariables = new String[0];
        
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);

        mandatoryFields = getSpecMandatoryFields();
        
        String[] checkTwoArraysSameLength = LPArray.checkTwoArraysSameLength(specFieldName, specFieldValue);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(checkTwoArraysSameLength[0])){return checkTwoArraysSameLength;}

        if (LPArray.duplicates(specFieldName)){
           errorCode = "DataSample_FieldsDuplicated";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(specFieldName));
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                      
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
                mandatoryFieldValue = LPArray.addValueToArray1D(mandatoryFieldValue, currFieldValue);
            }
            
        }            
        if (mandatoryFieldsMissing.length()>0){
           errorCode = "DataSample_MissingMandatoryFields";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                
        }

        Integer fieldIndex = Arrays.asList(specFieldName).indexOf(dbObjectsConfigTables.FLD_CONFIG_SPEC_CODE);
        newCode = specFieldValue[fieldIndex].toString();
        fieldIndex = Arrays.asList(specFieldName).indexOf(dbObjectsConfigTables.FLD_CONFIG_SPEC_CONFIG_VERSION);
        Integer newCodeVersion = (Integer) specFieldValue[fieldIndex];

        String[] specialFields = getSpecialFields();
        String[] specialFieldsFunction = getSpecialFieldsFunction();
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
                    parameters[0]=schemaConfigName;
                    parameters[1]=currFieldValue;
                    parameters[2]=newCode;
                    Object specialFunctionReturn = DIAGNOSES_ERROR;
                    if (method!=null){ specialFunctionReturn = method.invoke(this, (Object[]) parameters); }     
                    if (specialFunctionReturn.toString().contains(DIAGNOSES_ERROR)){
                        errorCode = ERROR_TRAPING_DATA_SAMPLE_SPECIAL_FUNCTION_RETURN_ERROR;
                        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, currField);
                        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, aMethod);
                        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, specialFunctionReturn.toString());
                        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                            
                    }
                    //String specialFunctionReturnStatus = String.valueOf(specialFunctionReturn);
            }
        }
        diagnoses = Rdbms.existsRecord(schemaConfigName, dbObjectsConfigTables.TABLE_CONFIG_SPEC, 
                new String[]{dbObjectsConfigTables.FLD_CONFIG_SPEC_CODE, dbObjectsConfigTables.FLD_CONFIG_SPEC_CONFIG_VERSION}, 
                new Object[] {newCode, newCodeVersion});        
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses[0].toString())){
            errorCode = "specRecord_AlreadyExists";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, newCode);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, newCodeVersion.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaConfigName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);           
        }
        try{
            diagnoses = Rdbms.insertRecordInTable(schemaConfigName, dbObjectsConfigTables.TABLE_CONFIG_SPEC, specFieldName, specFieldValue);            
            
            diagnoses = Rdbms.insertRecordInTable(schemaConfigName, dbObjectsConfigTables.TABLE_CONFIG_SPEC_RULES, 
                    new String[]{dbObjectsConfigTables.FLD_CONFIG_SPEC_RULES_CODE, dbObjectsConfigTables.FLD_CONFIG_SPEC_RULES_CONFIG_VERSION, dbObjectsConfigTables.FLD_CONFIG_SPEC_RULES_ALLOW_OTHER_ANALYSIS, dbObjectsConfigTables.FLD_CONFIG_SPEC_RULES_ALLOW_MULTI_SPEC}, 
                    new Object[]{newCode, newCodeVersion, false, false});       
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                errorCode = "specRecord_createdSuccessfully";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, newCode);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaConfigName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);                   
            }    
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ConfigSpecStructure.class.getName()).log(Level.SEVERE, null, ex);
        }                    
        errorCode = "UnhandledExceptionInCode";
        String params = "schemaPrefix: " + schemaPrefix+"specFieldName: "+Arrays.toString(specFieldName)+"specFieldValue: "+Arrays.toString(specFieldValue);
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, params);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                  
    }
    
    /**
     * @param schemaPrefix
     * @param specCode
     * @return
     */
    public Object[] specVariationGetNamesList( String schemaPrefix, String specCode){

        String schemaName = LPPlatform.SCHEMA_CONFIG;
        String variationList = "";
        String errorCode ="";
        
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);
        
        Object[][] variationListArray = Rdbms.getRecordFieldsByFilter(schemaName, dbObjectsConfigTables.TABLE_CONFIG_SPEC_LIMITS, 
                new String[]{dbObjectsConfigTables.FLD_CONFIG_SPEC_LIMITS_CODE}, new Object[]{specCode}, 
                new String[]{dbObjectsConfigTables.FLD_CONFIG_SPEC_LIMITS_NAME});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(variationListArray[0][0].toString())){            
            return LPArray.array2dTo1d(variationListArray);
        }else{
            for (int i=0;i<=variationListArray.length;i++){
                 if (variationList.length()>0){variationList=variationList+"|";}
                 variationList=variationList+variationListArray[i][0].toString();
             }
            errorCode = "specVariationGetNamesList_successfully";
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, new String[]{variationList});            
        }
/*        String query = ""; //distinct on (name)
        query = "select name from " + schemaConfigName + ".spec_limits "
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
                diagnosis[3]=LPPlatform.LAB_TRUE;
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
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Object[] specLimitNew( String schemaPrefix, String[] specFieldName, Object[] specFieldValue ) throws IllegalAccessException, InvocationTargetException{
                          
        String code = "";
        String errorCode="";
        Object[]  errorDetailVariables= new Object[0];

        String schemaName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);
        mandatoryFields = getSpecLimitsMandatoryFields();

        String[] checkTwoArraysSameLength = LPArray.checkTwoArraysSameLength(specFieldName, specFieldValue);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(checkTwoArraysSameLength[0])){return checkTwoArraysSameLength;}

        if (LPArray.duplicates(specFieldName)){
           errorCode = "DataSample_FieldsDuplicated";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(specFieldName));
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                      
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
                mandatoryFieldValue = LPArray.addValueToArray1D(mandatoryFieldValue, currFieldValue);
            }
        }                    
        Integer fieldIndex = Arrays.asList(specFieldName).indexOf(dbObjectsConfigTables.FLD_CONFIG_SPEC_CODE);
        code = specFieldValue[fieldIndex].toString();
        fieldIndex = Arrays.asList(specFieldName).indexOf(dbObjectsConfigTables.FLD_CONFIG_SPEC_CONFIG_VERSION);
        Integer codeVersion = (Integer) specFieldValue[fieldIndex];

        diagnoses = Rdbms.existsRecord(schemaName, dbObjectsConfigTables.TABLE_CONFIG_SPEC, 
                new String[]{dbObjectsConfigTables.FLD_CONFIG_SPEC_CODE, dbObjectsConfigTables.FLD_CONFIG_SPEC_CONFIG_VERSION}, 
                new Object[] {code, codeVersion});        
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){                       
            return diagnoses;
        }
        
        if (mandatoryFieldsMissing.length()>0){
           errorCode = "DataSample_MissingMandatoryFields";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);    
        }
        
        String[] specialFields = getSpecialFields();
        String[] specialFieldsFunction = getSpecialFieldsFunction();
        for (Integer inumLines=0;inumLines<specFieldName.length;inumLines++){
            String currField = "spec_limits." + specFieldName[inumLines];
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
                    Object specialFunctionReturn = DIAGNOSES_ERROR;
                    if (method!=null){ specialFunctionReturn = method.invoke(this, schemaName); }
                    if (specialFunctionReturn.toString().contains(DIAGNOSES_ERROR)){
                        errorCode = ERROR_TRAPING_DATA_SAMPLE_SPECIAL_FUNCTION_RETURN_ERROR;
                        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, currField);
                        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, aMethod);
                        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, specialFunctionReturn.toString());
                        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                            
                    }
                }
                catch(InvocationTargetException ite){
                    errorCode = "LabPLANETPlatform_SpecialFunctionCausedException";
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, aMethod);
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ite.getMessage());                        
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "Spec Limits");
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                         
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
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
            Object[] whereFieldsAndValues = LPArray.joinTwo1DArraysInOneOf1DString(diagnoses, whereFieldsValue, ":");
            errorCode = "Rdbms_NoRecordsFound";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldsAndValues));                                   
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                                            
        }else{
            fieldIndex = Arrays.asList(specFieldName).indexOf("parameter");
            String parameter = (String) specFieldValue[fieldIndex];
            tableName = "analysis_method_params";
            whereFields = new String[]{"analysis", "method_name", "method_version", "param_name"};
            whereFieldsValue = new Object[] {analysis, methodName, methodVersion, parameter};            
            diagnoses = Rdbms.existsRecord(schemaName, tableName, whereFields, whereFieldsValue);      
            if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                Object[] whereFieldsAndValues = LPArray.joinTwo1DArraysInOneOf1DString(diagnoses, whereFieldsValue, ":");
                errorCode = "Rdbms_NoRecordsFound";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldsAndValues));                                   
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                    
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
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, params);
        diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                    
        return diagnoses;
    }
    
}
