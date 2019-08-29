/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.materialSpec;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPPlatform;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class ConfigSpecRule {
    String classVersion = "0.1";
    public static String MESSAGE_CODE_QUANT_MINSPEC_MAXSPEC_SUCCESS="specLimits_quantitativeMinSpecMaxSpec_Successfully";
    /**
     *
     * @param rule
     * @param textSpec
     * @param separator
     * @return
     */
    public Object[] specLimitIsCorrectQualitative(String rule, String textSpec, String separator){
        String errorCode = "";
        Object[]  errorDetailVariables= new Object[0];
        
        String[] expectedRules = new String[6];
        expectedRules[0] = "EQUALTO";
        expectedRules[1] = "NOTEQUALTO";
        expectedRules[2] = "CONTAINS";
        expectedRules[3] = "NOTCONTAINS";
        expectedRules[4] = "ISONEOF";
        expectedRules[5] = "ISNOTONEOF";
                
        if ((rule==null) || (rule.length()==0)){
           errorCode = "specLimits_ruleMandatoryArgumentNull";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "");          
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                                                
        if ((textSpec==null) || (textSpec.length()==0)){
            errorCode = "specLimits_textSpecMandatoryArgumentNull";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "");          
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                                                
        switch (rule.toUpperCase()){
            case "EQUALTO":  errorCode = "specLimits_equalTo_Successfully"; return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);                          
            case "NOTEQUALTO": errorCode = "specLimits_notEqualTo_Successfully"; return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);
            case "CONTAINS": errorCode = "specLimits_contains_Successfully"; return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);          
            case "NOTCONTAINS": errorCode = "specLimits_notContains_Successfully"; return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);          
            case "ISONEOF": 
                if ((separator==null) || (separator.length()==0)){
                    errorCode = "specLimits_separatorMandatoryArgumentNull";
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, rule.toUpperCase());          
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "");          
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                       
                else{
                    String[] textSpecArray = textSpec.split(separator);
                    errorCode = "specLimits_isOneOf_Successfully";
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, textSpecArray.length);          
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);}                       
            case "ISNOTONEOF": 
                if ((separator==null) || (separator.length()==0)){
                    errorCode = "specLimits_separatorMandatoryArgumentNull";
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, rule.toUpperCase());          
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "");          
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}    
                else{
                    String[] textSpecArray = textSpec.split(separator);
                    errorCode = "specLimits_isNotOneOf_Successfully";
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, textSpecArray.length);          
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);}                          
            default: 
                errorCode = "specLimits_qualitativeRuleNotRecognized";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, rule);          
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(expectedRules));          
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);    
        }
    }
/**
 * This method verify that the parameters provided to build one quantitative spec limit apply just one range are coherent accordingly to the different options:<br>
 * Basically when both are not null then cannot be the same value even min cannot be greater than max.
 * @param minSpec Float - The minimum value
 * @param maxSpec Float - The maximum value
 * Bundle parameters:
 *          config-specLimits_MinAndMaxSpecBothMandatory, specLimits_quantitativeMinSpecSuccessfully, specLimits_quantitativeMaxSpecSuccessfully<br>
 *          MESSAGE_CODE_QUANT_MINSPEC_MAXSPEC_SUCCESS, specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec
 * @return Object[] position 0 is a boolean to determine if the arguments are correct, when set to false then position 1 provides detail about the deficiency 
 */
    public Object[] specLimitIsCorrectQuantitative(Float minSpec, Float maxSpec){
        String errorCode = "";
        Object[]  errorDetailVariables= new Object[0];        
        
        if ((minSpec==null) && (maxSpec==null)){
            errorCode = "specLimits_MinAndMaxSpecBothMandatory"; return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                                               
        if ((minSpec!=null) && (maxSpec==null)){
            errorCode = "specLimits_quantitativeMinSpecSuccessfully"; return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);}                                    
        if ((minSpec==null) && (maxSpec!=null)){
            errorCode = "specLimits_quantitativeMaxSpecSuccessfully"; return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);}                                           
        if (minSpec<maxSpec){
            errorCode = MESSAGE_CODE_QUANT_MINSPEC_MAXSPEC_SUCCESS; return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);}                                    
        
        errorCode = "specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec"; 
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, minSpec.toString());        
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, maxSpec.toString());
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                                    
    }

/**
 * This method verify that the parameters provided to build one quantitative spec limit apply just one range are coherent accordingly to the different options:<br>
 * Basically when both are not null then cannot be the same value even min cannot be greater than max.
 * @param minSpec BigDecimal - The minimum value
 * @param maxSpec BigDecimal - The maximum value
 * Bundle parameters:
 *          config-specLimits_MinAndMaxSpecBothMandatory, specLimits_quantitativeMinSpecSuccessfully, specLimits_quantitativeMaxSpecSuccessfully<br>
 *          specLimits_quantitativeMinSpecMaxSpec_Successfully, specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec
 * @return Object[] position 0 is a boolean to determine if the arguments are correct, when set to false then position 1 provides detail about the deficiency 
 */
    public Object[] specLimitIsCorrectQuantitative(BigDecimal minSpec, BigDecimal maxSpec){
        String errorCode = "";
        Object[]  errorDetailVariables= new Object[0];        
                
        if ((minSpec==null) && (maxSpec==null)){
            errorCode = "specLimits_MinAndMaxSpecBothMandatory"; return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                                               
        if ((minSpec!=null) && (maxSpec==null)){
            errorCode = "specLimits_quantitativeMinSpecSuccessfully"; return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);}                                    
        if ((minSpec==null) && (maxSpec!=null)){
            errorCode = "specLimits_quantitativeMaxSpecSuccessfully"; return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);}                                           
        int comparsion = minSpec.compareTo(maxSpec);
         if (comparsion!=1){
            errorCode = MESSAGE_CODE_QUANT_MINSPEC_MAXSPEC_SUCCESS; return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);}                                    
        
        errorCode = "specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec"; 
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, minSpec.toString());        
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, maxSpec.toString());
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                                    
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
        Object[] isCorrectMinMaxSpec = this.specLimitIsCorrectQuantitative(minSpec, maxSpec);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;}
                
        if ((minControl1==null) && (maxControl1==null)){
            errorCode = MESSAGE_CODE_QUANT_MINSPEC_MAXSPEC_SUCCESS; return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);}                                            
        if ((minControl1!=null) && (minSpec==null)){
            errorCode = "specLimits_MinControlPresent_MinSpecMandatory"; 
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, minControl1.toString());        
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                                           
        if ((maxControl1!=null) && (maxSpec==null)){
            errorCode = "specLimits_MaxControlPresent_MaxSpecMandatory"; 
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, maxControl1.toString());      
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                                    
        if (((minControl1!=null) && (maxControl1!=null)) && (minControl1>=maxControl1)){
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, minControl1.toString());        
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, maxControl1.toString());    
            errorCode = "specLimits_minControlGreaterOrEqualToMaxControl"; 
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                                    
        if (((minControl1!=null) && (maxSpec!=null)) && (minControl1>=maxSpec)){
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, minControl1.toString());        
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, maxSpec.toString());    
            errorCode = "specLimits_minControlGreaterOrEqualToMaxSpec"; 
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                      
        if (((maxControl1!=null) && (minSpec!=null)) && (maxControl1<=minSpec)){
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, maxControl1.toString());        
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, minSpec.toString());    
            errorCode = "specLimits_MaxControlLessThanOrEqualToMinSpec"; 
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                      
        if (minControl1!=null){                        
            if (minControl1.compareTo(minSpec)<=0){
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, minControl1.toString());        
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, minSpec.toString());    
                errorCode = "specLimits_MinControlLessThanOrEqualToMinSpec"; 
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                                      
            }else{
                errorCode = "specLimits_quantitativeMinSpecMinControlMaxSpec_Successfully"; 
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);   
            }
        }                      
        if ((maxControl1!=null)){
            if (maxControl1.compareTo(maxSpec)>=0){
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, maxControl1.toString());        
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, maxSpec.toString()); 
                errorCode = "specLimits_MaxControlGreaterThanOrEqualToMaxSpec"; 
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                                   
            }else{
                errorCode = "specLimits_quantitativeMinSpecMinControlMaxControlMaxSpec_Successfully"; 
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);        
            }    
        }
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, minControl1.toString());        
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, maxControl1.toString()); 
        errorCode = "specLimits_MinControlAndMaxControlOutOfLogicControl"; 
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);              
    }    
} 
