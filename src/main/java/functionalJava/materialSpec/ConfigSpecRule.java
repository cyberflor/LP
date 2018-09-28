/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.materialSpec;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class ConfigSpecRule {

    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = ""; 
/**
 * This method verify that the parameters provided to build one qualitative spec limit are coherent accordingly to the different options:<br>
 * EqualTo - The value should match strictly this wording.(Separator not required)<br>
 * NotEqualTo - Any value except the wording.(Separator not required)<br>
 * Contains - The value is contained. (Separator not required)<br>
 * NotContains - The opposite to Contains. (Separator not required)<br>
 * IsOneOf - The value will be one of the those. (Separator is mandatory)<br>
 * IsNotOneOf - The value cannot be one of those. (Separator is mandatory)<br> 
 * Bundle parameters:
 *          config-specLimits_ruleMandatoryArgumentNull, specLimits_textSpecMandatoryArgumentNull, specLimits_separatorMandatoryArgumentNull<br>
 *          specLimits_equalTo_Successfully, specLimits_notEqualTo_Successfully, specLimits_contains_Successfully, specLimits_notContains_Successfully<br>
 *          specLimits_isOneOf_Successfully, specLimits_isNotOneOf_Successfully<br>
 *          specLimits_qualitativeRuleNotRecognized
 * @param rule String - Rule Type
 * @param textSpec String - The value for building the spec
 * @param separator String - For those that requires separator for the many option that should be part of the range.
 * @return Object[] position 0 is a boolean to determine if the arguments are correct, when set to false then position 1 provides detail about the deficiency 
 */    
    public Object[] specLimitIsCorrectQualitative(String schemaPrefix, String rule, String textSpec, String separator){
                
        String schemaConfigName = "config";
        String errorCode = "";
        Object[]  errorDetailVariables= new Object[0];
        
        String[] expectedRules = new String[6];
        expectedRules[0] = "EQUALTO";
        expectedRules[1] = "NOTEQUALTO";
        expectedRules[2] = "CONTAINS";
        expectedRules[3] = "NOTCONTAINS";
        expectedRules[4] = "ISONEOF";
        expectedRules[5] = "ISNOTONEOF";
        
        String schemaName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName);
        
        if ((rule==null) || (rule.length()==0)){
           errorCode = "specLimits_ruleMandatoryArgumentNull";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
           return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}                                                
        if ((textSpec==null) || (textSpec.length()==0)){
            errorCode = "specLimits_textSpecMandatoryArgumentNull";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
            return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}                                                
        switch (rule.toUpperCase()){
            case "EQUALTO":  errorCode = "specLimits_equalTo_Successfully"; return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);                          
            case "NOTEQUALTO": errorCode = "specLimits_notEqualTo_Successfully"; return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);
            case "CONTAINS": errorCode = "specLimits_contains_Successfully"; return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);          
            case "NOTCONTAINS": errorCode = "specLimits_notContains_Successfully"; return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);          
            case "ISONEOF": 
                if ((separator==null) || (separator.length()==0)){
                    errorCode = "specLimits_separatorMandatoryArgumentNull";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, rule.toUpperCase());          
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
                    return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}                       
                else{
                    String[] textSpecArray = textSpec.split(separator);
                    errorCode = "specLimits_isOneOf_Successfully";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, textSpecArray.length);          
                    return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);}                       
            case "ISNOTONEOF": 
                if ((separator==null) || (separator.length()==0)){
                    errorCode = "specLimits_separatorMandatoryArgumentNull";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, rule.toUpperCase());          
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
                    return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}    
                else{
                    String[] textSpecArray = textSpec.split(separator);
                    errorCode = "specLimits_isNotOneOf_Successfully";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, textSpecArray.length);          
                    return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);}                          
            default: 
                errorCode = "specLimits_qualitativeRuleNotRecognized";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, rule);          
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(expectedRules));          
                return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);    
        }
    }
/**
 * This method verify that the parameters provided to build one quantitative spec limit apply just one range are coherent accordingly to the different options:<br>
 * Basically when both are not null then cannot be the same value even min cannot be greater than max.
 * @param minSpec Float - The minimum value
 * @param maxSpec Float - The maximum value
 * Bundle parameters:
 *          config-specLimits_MinAndMaxSpecBothMandatory, specLimits_quantitativeMinSpecSuccessfully, specLimits_quantitativeMaxSpecSuccessfully<br>
 *          specLimits_quantitativeMinSpecMaxSpec_Successfully, specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec
 * @return Object[] position 0 is a boolean to determine if the arguments are correct, when set to false then position 1 provides detail about the deficiency 
 */
    public Object[] specLimitIsCorrectQuantitative(Float minSpec, Float maxSpec){
        String errorCode = "";
        Object[]  errorDetailVariables= new Object[0];        
                
        Object[] diagnoses = new Object[2];
        
        if ((minSpec==null) && (maxSpec==null)){
            errorCode = "specLimits_MinAndMaxSpecBothMandatory"; return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}                                               
        if ((minSpec!=null) && (maxSpec==null)){
            errorCode = "specLimits_quantitativeMinSpecSuccessfully"; return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);}                                    
        if ((minSpec==null) && (maxSpec!=null)){
            errorCode = "specLimits_quantitativeMaxSpecSuccessfully"; return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);}                                           
        if (minSpec<maxSpec){
            errorCode = "specLimits_quantitativeMinSpecMaxSpec_Successfully"; return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);}                                    
        
        errorCode = "specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec"; 
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, minSpec.toString());        
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, maxSpec.toString());
        return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                                    
    }

/**
 * This method verify that the parameters provided to build one quantitative spec limit apply one double level range are coherent accordingly to the different options:<br>
 * Basically when both peers, min-max, are not null then cannot be the same value even min cannot be greater than max. At the same time
 * The control range should be included or part of the spec range that should be broader.
 * @param minSpec Float - The minimum value
 * @param maxSpec Float - The maximum value
 * @param minControl1 Float - The minimum control
 * @param maxControl1 Float - The maximum control
 * Bundle parameters:
 *          config-specLimits_quantitativeMinSpecMaxSpec_Successfully, specLimits_MinControlPresent_MinSpecMandatory, specLimits_MaxControlPresent_MaxSpecMandatory<br>
 *          specLimits_minControlGreaterOrEqualToMaxControl, specLimits_minControlGreaterOrEqualToMaxSpec, specLimits_MaxControlLessThanOrEqualToMinSpec <br>
 *          specLimits_MinControlLessThanOrEqualToMinSpec, specLimits_quantitativeMinSpecMinControlMaxSpec_Successfully, specLimits_MaxControlGreaterThanOrEqualToMaxSpec <br>
 *          specLimits_quantitativeMinSpecMinControlMaxControlMaxSpec_Successfully, specLimits_MinControlAndMaxControlOutOfLogicControl
 * @return Object[] position 0 is a boolean to determine if the arguments are correct, when set to false then position 1 provides detail about the deficiency 
 */    
    public Object[] specLimitIsCorrectQuantitative(Float minSpec, Float maxSpec, Float minControl1, Float maxControl1){
        String errorCode = "";
        Object[]  errorDetailVariables= new Object[0];        
        Object[] diagnoses = new Object[2];
        
        Object[] isCorrectMinMaxSpec = this.specLimitIsCorrectQuantitative(minSpec, maxSpec);
        if ("LABPLANET_FALSE".equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;}
                
        if ((minControl1==null) && (maxControl1==null)){
            errorCode = "specLimits_quantitativeMinSpecMaxSpec_Successfully"; return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);}                                            
        if ((minControl1!=null) && (minSpec==null)){
            errorCode = "specLimits_MinControlPresent_MinSpecMandatory"; 
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, minControl1.toString());        
            return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}                                           
        if ((maxControl1!=null) && (maxSpec==null)){
            errorCode = "specLimits_MaxControlPresent_MaxSpecMandatory"; 
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, maxControl1.toString());      
            return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}                                    
        if (((minControl1!=null) && (maxControl1!=null)) && (minControl1>=maxControl1)){
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, minControl1.toString());        
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, maxControl1.toString());    
            errorCode = "specLimits_minControlGreaterOrEqualToMaxControl"; 
            return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}                                    
        if (((minControl1!=null) && (maxSpec!=null)) && (minControl1>=maxSpec)){
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, minControl1.toString());        
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, maxSpec.toString());    
            errorCode = "specLimits_minControlGreaterOrEqualToMaxSpec"; 
            return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}                      
        if (((maxControl1!=null) && (minSpec!=null)) && (maxControl1<=minSpec)){
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, maxControl1.toString());        
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, minSpec.toString());    
            errorCode = "specLimits_MaxControlLessThanOrEqualToMinSpec"; 
            return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}                      
        if (minControl1!=null){                        
            if (minControl1.compareTo(minSpec)<=0){
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, minControl1.toString());        
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, minSpec.toString());    
                errorCode = "specLimits_MinControlLessThanOrEqualToMinSpec"; 
                return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                                      
            }else{
                errorCode = "specLimits_quantitativeMinSpecMinControlMaxSpec_Successfully"; 
                return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);   
            }
        }                      
        if ((maxControl1!=null)){
            int comparison = 0;
            if (maxControl1.compareTo(maxSpec)>=0){
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, maxControl1.toString());        
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, maxSpec.toString()); 
                errorCode = "specLimits_MaxControlGreaterThanOrEqualToMaxSpec"; 
                return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                                   
            }else{
                errorCode = "specLimits_quantitativeMinSpecMinControlMaxControlMaxSpec_Successfully"; 
                return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);        
            }    
        }
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, minControl1.toString());        
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, maxControl1.toString()); 
        errorCode = "specLimits_MinControlAndMaxControlOutOfLogicControl"; 
        return LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);              
    }    
} 
