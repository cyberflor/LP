/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.materialSpec;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPPlatform;
import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
public class DataSpec {
    String classVersion = "0.1";
    
    public static final String ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS="DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
    public static final String ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD="DataSpec_resultCheck_mandatoryFieldIsNull";
    
    
    
    
    public static final String EVALUATION_CODE_QUANTITATIVE_IN="DataSpec_resultCheck_quantitativeIn";
    public static final String EVALUATION_CODE_QUALITATIVE_IN="DataSpec_resultCheck_qualitativeIN";
    
    public static final String EVALUATION_CONTROL_MIN="CONTROL_MIN";
    public static final String EVALUATION_CONTROL_MAX="CONTROL_MAX";
    public static final String EVALUATION_WRONG_RULE= "OUT_WRONG_RULE";
    public static final String EVALUATION_OUT= "OUT";
    public static final String EVALUATION_OUT_MIN= "OUT_MIN";
    public static final String EVALUATION_OUT_MAX= "OUT_MAX";
    
    public static final String EVALUATION_IN= "IN";
    
   
/**
 * There are some behaviors for interpret what the spec limits means.<br>
 * Case 1: The most restrictive one is the one where the spec is the mechanism to decide which are the analysis that should be added to the samples
 * and it means that it is expected that the sample contains all the analysis defined in its spec, nothing more and nothing less (SPEC_LIMIT_DEFINITION).<BR>
 * Case 2: Other analysis than the ones added to the spec are not allowed but not all of them should be present at the spec limit level due to many reasons like
 * , for example, simply there are no ranges defined yet for those analysis or even they are just analysis to reinforce the result or testing for internal purposes
 * (ANALYSES_SPEC_LIST).<br>
 * Case 3. Analysis has not to be declared in any level of the spec, let any analysis be added to the sample (OPEN)<br>
 * Then the three cases that this method cover are: OPEN|ANALYSES_SPEC_LIST|SPEC_LIMIT_DEFINITION 
 * LABPLANET_TRUE means the result can be checked against the rule even when the check returns one Out result
 * LABPLANET_FALSE means the evaluation cannot be performed due to any deficiency<br>
 * @return 
 */
    public Object[] specAllowSampleAnalysisAddition(){
        Object[] diagnoses = new Object[2];
        diagnoses[0]=false;
        return diagnoses;
    }

    /**
     *
     * @param result
     * @param specRule
     * @param values
     * @param separator
     * @param listName
     * @return
     */
    public Object[] resultCheck(String result, String specRule, String values, String separator, String listName){
        ConfigSpecRule matQualit = new ConfigSpecRule();
        Object [] errorVariables = new Object[0];        

        String errorCode = ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD;        
        if (result==null || "".equals(result)){
            errorVariables = LPArray.addValueToArray1D(errorVariables, "Result");
            Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
            diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
            return diagnoses;}               
        if (specRule==null || "".equals(specRule)){
            errorVariables = LPArray.addValueToArray1D(errorVariables, "specRule");            
            Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
            diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
            return diagnoses;}        
        if (values==null || "".equals(values)){
            errorVariables = LPArray.addValueToArray1D(errorVariables, "values");            
            Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
            diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
            return diagnoses;}        
        
        Object[] isCorrectTheSpec = matQualit.specLimitIsCorrectQualitative( specRule, values, separator);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(isCorrectTheSpec[0].toString())){
            return isCorrectTheSpec;}
        
        switch (specRule.toUpperCase()){
            case "EQUALTO": 
                if (result.equalsIgnoreCase(values)){
                    errorCode = EVALUATION_CODE_QUALITATIVE_IN;
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
                    return diagnoses;                    
                }else{
                    errorCode = "DataSpec_resultCheck_qualitativeEqualToOUT";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, result);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, values);
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                    return diagnoses;                                                           
                }
                
            case "NOTEQUALTO": 
                if (result.equalsIgnoreCase(values)){
                    errorCode = "DataSpec_resultCheck_qualitativeNotEqualToOUT";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, result);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, values);
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                    return diagnoses;                                                                               
                }else{
                    errorCode = EVALUATION_CODE_QUALITATIVE_IN;
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
                    return diagnoses;                    
                }

            case "CONTAINS":                 
                if (values.toUpperCase().contains(result.toUpperCase())){
                    errorCode = EVALUATION_CODE_QUALITATIVE_IN;
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
                    return diagnoses;                    
                }else{
                    errorCode = "DataSpec_resultCheck_qualitativeContainsOUT";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, result);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, values);
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                    return diagnoses;                         
                }

            case "NOTCONTAINS":                 
                if (values.toUpperCase().contains(result.toUpperCase())){
                    errorCode = "DataSpec_resultCheck_qualitativeNotContainsOUT";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, result);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, values);
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                    return diagnoses;                                             
                }else{
                    errorCode = EVALUATION_CODE_QUALITATIVE_IN;
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
                    return diagnoses;                    
                }

            case "ISONEOF": 
                if ((separator==null) || (separator.length()==0)){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Separator");
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;
                }else{
                    String[] textSpecArray = values.split(separator);
                    for (Integer itextSpecArrayLen=0;itextSpecArrayLen<textSpecArray.length;itextSpecArrayLen++){
                        if (result.equalsIgnoreCase(textSpecArray[itextSpecArrayLen])){
                            errorCode = EVALUATION_CODE_QUALITATIVE_IN;
                            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
                            diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
                            return diagnoses;                    
                        }
                    }
                    errorCode = "DataSpec_resultCheck_qualitativeIsOneOfOUT";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, result);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, String.valueOf((Integer)textSpecArray.length+1));
                    errorVariables = LPArray.addValueToArray1D(errorVariables, values);
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                    return diagnoses;                                                                 
                }
            case "ISNOTONEOF": 
                if ((separator==null) || (separator.length()==0)){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Separator");
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;
                }else{
                    String[] textSpecArray = values.split(separator);
                    if (!LPArray.valueInArray(textSpecArray, result)){
                        errorCode = "DataSpec_resultCheck_qualitativeIsNotOneOfOUT";
                        errorVariables = LPArray.addValueToArray1D(errorVariables, result);
                        errorVariables = LPArray.addValueToArray1D(errorVariables, String.valueOf((Integer)textSpecArray.length+1));
                        errorVariables = LPArray.addValueToArray1D(errorVariables, values);
                        Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                        diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                        return diagnoses;
                    }
                    errorCode = EVALUATION_CODE_QUALITATIVE_IN;
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, null);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
                    return diagnoses;                    
                }
            default:
                errorCode = "DataSpec_resultCheck_UnhandledException";
                String params = "Result: "+result+", Spec Rule: "+specRule+", values: "+values+", separator: "+separator+", listName: "+listName;
                errorVariables = LPArray.addValueToArray1D(errorVariables, params);
                Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                return diagnoses;               
        }
    }

    /**
     *
     * @param result
     * @param minSpec
     * @param maxSpec
     * @param minStrict
     * @param maxStrict
     * @return
     */
    public Object[] resultCheck(Float result, Float minSpec, Float maxSpec, Boolean minStrict, Boolean maxStrict){
        
        ConfigSpecRule matQuant = new ConfigSpecRule();
        Object[] errorVariables = new Object[0];        
        
        if (result==null){
            String errorCode = ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD;
            errorVariables = LPArray.addValueToArray1D(errorVariables, "Result");
            Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
            diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
            return diagnoses;
        }
        Object[] isCorrectMinMaxSpec = matQuant.specLimitIsCorrectQuantitative(minSpec, maxSpec);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;}
                
        if (minStrict==null){minStrict=true;}
        if (maxStrict==null){maxStrict=true;}
        //&& (maxSpec!=null)){
        if (minSpec!=null){  
            if (minStrict){
                if (result<=minSpec){                
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMinStrict";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT_MIN);
                    return diagnoses;
                }
            }else{
                if (result<minSpec){
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMin";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT_MIN);
                    return diagnoses;
                }
            }
        }                    
        if (maxSpec!=null){  
            if (maxStrict){
                if (result>=maxSpec){
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMaxStrict";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT_MAX);
                    return diagnoses;
                }
            }else{
                if (result>maxSpec){
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMax";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT_MAX);
                    return diagnoses;
                }
            }
        }        
        String errorCode = EVALUATION_CODE_QUANTITATIVE_IN;
        Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
        diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
        return diagnoses;        
    }

    /**
     *
     * @param result
     * @param minSpec
     * @param maxSpec
     * @param minStrict
     * @param maxStrict
     * @return
     */
    public Object[] resultCheck(BigDecimal result, BigDecimal minSpec, BigDecimal maxSpec, Boolean minStrict, Boolean maxStrict){
        
        ConfigSpecRule matQuant = new ConfigSpecRule();

        Object [] errorVariables = new Object[0];        
        
        if (result==null){
            String errorCode = ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD;
            errorVariables = LPArray.addValueToArray1D(errorVariables, "Result");
            Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
            diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
            return diagnoses;
        }
        Object[] isCorrectMinMaxSpec = matQuant.specLimitIsCorrectQuantitative(minSpec, maxSpec);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;}
                
        if (minStrict==null){minStrict=true;}
        if (maxStrict==null){maxStrict=true;}

        if (minSpec!=null){  
            int comparingMIN = minSpec.compareTo(result);
            if ( (comparingMIN==1) || (comparingMIN==0 && minStrict) ) {
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMinStrict";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT_MIN);
                    return diagnoses;                
            }
        }                    
        if (maxSpec!=null){  
            int comparingMAX = result.compareTo(maxSpec);
            if ( (comparingMAX==1) || (comparingMAX==0 && maxStrict) ) {
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMaxStrict";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT_MAX);
                    return diagnoses;            
            }            
        }    
        String errorCode = EVALUATION_CODE_QUANTITATIVE_IN;
        Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
        diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
        return diagnoses;        
    }
    
    /**
     *
     * @param result
     * @param minSpec
     * @param maxSpec
     * @param minStrict
     * @param maxStrict
     * @param minControl
     * @param maxControl
     * @param minControlStrict
     * @param maxControlStrict
     * @return
     */
    public Object[] resultCheck(Float result, Float minSpec, Float maxSpec, Boolean minStrict, Boolean maxStrict, Float minControl, Float maxControl, Boolean minControlStrict, Boolean maxControlStrict){
        
        String errorCode = "";
        Object [] errorVariables = new Object[0]; 

        if (result==null){
                errorCode = ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD;
                errorVariables = LPArray.addValueToArray1D(errorVariables, "Result");
                Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                return diagnoses;
        }
        
        Object[] isCorrectMinMaxSpec = this.resultCheck(result,minSpec,maxSpec, minStrict, maxStrict);
        
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;
        }

        if (isCorrectMinMaxSpec[isCorrectMinMaxSpec.length-1].toString().contains(EVALUATION_OUT)){
            return isCorrectMinMaxSpec;
        }

        if (minControl!=null){
            if (minControl.equals(minSpec)) {                
                if (minStrict==false || minStrict==null){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Min Strict  is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;
                }

                if (minStrict==true && minControlStrict==true){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "both, min Spec & Control Strict, set to true");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;                   
                }                    
            }    
    
            if (minControlStrict==null){minControlStrict=true;}
            if (minControlStrict){
                if (result<=minControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMinControlInSpec";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, " < "+result.toString()+" < ");
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minControl.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_CONTROL_MIN);
                    return diagnoses;                    
                }
            }
            else{
                if (result<minControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMinControlInSpec";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, " <= "+result.toString()+" <= ");
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minControl.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_CONTROL_MIN);
                    return diagnoses;                                   
                }
            }
                      
        }
        
        if (maxControl!=null){
            if (maxControl.equals(maxSpec)) {                
                if (maxStrict==false || maxStrict==null){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "max Strict is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;                    
                }

                if (maxStrict==true && maxControlStrict==true){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "both, max Spec & Control Strict, set to true..");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;                    
                }                    
            }            
            if (maxControlStrict==null){maxControlStrict=true;}
            if (maxControlStrict){
                if (result>=maxControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMaxControlInSpec";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxControl.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, " > "+result.toString()+" > ");
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_CONTROL_MAX);
                    return diagnoses;                                         
                }
            }
            else{
                if (result>maxControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMaxControlInSpec";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxControl.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, " => "+result.toString()+" => ");
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_CONTROL_MAX);
                    return diagnoses;                                         
                }
            }                        
        }
        
        errorCode = EVALUATION_CODE_QUANTITATIVE_IN;
        Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
        diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
        return diagnoses;            
    }
    
    /**
     *
     * @param result
     * @param minSpec
     * @param maxSpec
     * @param minStrict
     * @param maxStrict
     * @param minControl
     * @param maxControl
     * @param minControlStrict
     * @param maxControlStrict
     * @return
     */
    public Object[] resultCheck(BigDecimal result, BigDecimal minSpec, BigDecimal maxSpec, Boolean minStrict, Boolean maxStrict, BigDecimal minControl, BigDecimal maxControl, Boolean minControlStrict, Boolean maxControlStrict){
        
        String errorCode = "";
        Object [] errorVariables = new Object[0]; 

        if (result==null){
                errorCode = ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD;
                errorVariables = LPArray.addValueToArray1D(errorVariables, "Result");
                Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                return diagnoses;
        }
        
        Object[] isCorrectMinMaxSpec = this.resultCheck(result,minSpec,maxSpec, minStrict, maxStrict);
        
        if (EVALUATION_OUT.equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;
        }

        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;
        }

        if (minControl!=null){
            if (minControl.equals(minSpec)) {                
                if (minStrict==false || minStrict==null){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Min Strict  is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;
                }

                if (minStrict==true && minControlStrict==true){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "both, min Spec & Control Strict, set to true");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;                   
                }                    
            }    
    
            if (minControlStrict==null){minControlStrict=true;}
            
            int comparingMIN = minControl.compareTo(result);
            if ( (comparingMIN==1) || (comparingMIN==0 && minControlStrict) ) {
                    errorCode = "DataSpec_resultCheck_quantitativeOutMinControlInSpec";
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, " < "+result.toString()+" < ");
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minControl.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_CONTROL_MIN);
                    return diagnoses;                    
            }                      
        }
        
        if (maxControl!=null){
            
            if (maxControl.equals(maxSpec)) {                
                if (maxStrict==false || maxStrict==null){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "max Strict is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;                    
                }

                if (maxStrict==true && maxControlStrict==true){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "both, max Spec & Control Strict, set to true..");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;                    
                }                    
            }            
            int comparingMAX = result.compareTo(maxSpec);
            if ( (comparingMAX==1) || (comparingMAX==0 && maxStrict) ) {
                errorCode = "DataSpec_resultCheck_quantitativeOutMaxControlInSpec";
                errorVariables = LPArray.addValueToArray1D(errorVariables, maxControl.toString());
                errorVariables = LPArray.addValueToArray1D(errorVariables, " > "+result.toString()+" > ");
                errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec.toString());
                Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_CONTROL_MAX);
                return diagnoses;                                         
            }
        }
        
        errorCode = EVALUATION_CODE_QUANTITATIVE_IN;
        Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
        diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
        return diagnoses;            
    }
    

}
