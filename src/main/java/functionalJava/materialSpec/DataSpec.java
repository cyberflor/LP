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
        public static final String ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_RESULT="Result";
        public static final String ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_SPEC_RULE="specRule";
            public static final String ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_VALUES="values";
            public static final String ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_SEPARATOR="Separator";
                
        
    public static final String ERROR_TRAPPING_RESULT_CHECK_QUANT_OUT_MIN_CONTROL_IN_SPEC="DataSpec_resultCheck_quantitativeOutMinControlInSpec";
    
    public static final String QUALITATIVE_RULES_EQUAL_TO="EQUALTO";    
    public static final String QUALITATIVE_RULES_NOT_EQUAL_TO="NOTEQUALTO";
    public static final String QUALITATIVE_RULES_CONTAINS="CONTAINS";
    public static final String QUALITATIVE_RULES_NOT_CONTAINS="NOTCONTAINS";
    public static final String QUALITATIVE_RULES_IS_ONE_OF="ISONEOF";
    public static final String QUALITATIVE_RULES_IS_NOT_ONE_OF="ISNOTONEOF";
    
    
    
    public static final String EVALUATION_CODE_QUANTITATIVE_IN="DataSpec_resultCheck_quantitativeIn";
    public static final String EVALUATION_CODE_QUANTITATIVE_OUT_MIN_STRICT="DataSpec_resultCheck_quantitativeOutMinStrict";
    public static final String EVALUATION_CODE_QUANTITATIVE_OUT_MIN="DataSpec_resultCheck_quantitativeOutMin";
    public static final String EVALUATION_CODE_QUANTITATIVE_OUT_MAX_STRICT="DataSpec_resultCheck_quantitativeOutMaxStrict";
    public static final String EVALUATION_CODE_QUANTITATIVE_OUT_MAX="DataSpec_resultCheck_quantitativeOutMax";   
    public static final String EVALUATION_CODE_QUANTITATIVE_OUT_MAX_CONTROL_IN_SPEC="DataSpec_resultCheck_quantitativeOutMaxControlInSpec";   
    
    public static final String EVALUATION_CODE_QUANTITATIVE_OUT_ARGUMENT_MIN_SPEC_MIN_CONTROL="Min Spec and Min Control";
    public static final String EVALUATION_CODE_QUANTITATIVE_OUT_ARGUMENT_MAX_SPEC_MAX_CONTROL="Max Spec and Control";    
    
    
    public static final String EVALUATION_CODE_QUALITATIVE_IN="DataSpec_resultCheck_qualitativeIN";    
    public static final String EVALUATION_CODE_QUALITATIVE_OUT_EQUAL_TO="DataSpec_resultCheck_qualitativeEqualToOUT"; 
    public static final String EVALUATION_CODE_QUALITATIVE_OUT_NOT_EQUAL_TO="DataSpec_resultCheck_qualitativeNotEqualToOUT"; 
    public static final String EVALUATION_CODE_QUALITATIVE_OUT_CONTAINS="DataSpec_resultCheck_qualitativeContainsOUT"; 
    public static final String EVALUATION_CODE_QUALITATIVE_OUT_NOT_CONTAINS="DataSpec_resultCheck_qualitativeNotContainsOUT"; 
    public static final String EVALUATION_CODE_QUALITATIVE_OUT_IS_ONE_OF="DataSpec_resultCheck_qualitativeIsOneOfOUT";
    public static final String EVALUATION_CODE_QUALITATIVE_OUT_IS_NOT_ONE_OF="DataSpec_resultCheck_qualitativeIsNotOneOfOUT";
    
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

        String errorCode = "";        
        if (result==null || "".equals(result)){
            errorVariables = LPArray.addValueToArray1D(errorVariables, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_RESULT);
            Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD, errorVariables);
            diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
            return diagnoses;}               
        if (specRule==null || "".equals(specRule)){
            errorVariables = LPArray.addValueToArray1D(errorVariables, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_SPEC_RULE);            
            Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD, errorVariables);
            diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
            return diagnoses;}        
        if (values==null || "".equals(values)){
            errorVariables = LPArray.addValueToArray1D(errorVariables, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_VALUES);            
            Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD, errorVariables);
            diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
            return diagnoses;}        
        
        Object[] isCorrectTheSpec = matQualit.specLimitIsCorrectQualitative( specRule, values, separator);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(isCorrectTheSpec[0].toString())){
            return isCorrectTheSpec;}
        
        switch (specRule.toUpperCase()){
            case QUALITATIVE_RULES_EQUAL_TO: 
                if (result.equalsIgnoreCase(values)){
                    errorCode = EVALUATION_CODE_QUALITATIVE_IN;
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
                    return diagnoses;                    
                }else{
                    errorVariables = new Object[]{result, values};
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUALITATIVE_OUT_EQUAL_TO, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                    return diagnoses;                                                           
                }
                
            case QUALITATIVE_RULES_NOT_EQUAL_TO: 
                if (result.equalsIgnoreCase(values)){ 
                    errorVariables = new Object[]{result, values};
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, EVALUATION_CODE_QUALITATIVE_OUT_NOT_EQUAL_TO, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                    return diagnoses;                                                                               
                }else{                    
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUALITATIVE_IN, null);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
                    return diagnoses;                    
                }
            case QUALITATIVE_RULES_CONTAINS:                 
                if (values.toUpperCase().contains(result.toUpperCase())){
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUALITATIVE_IN, null);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
                    return diagnoses;                    
                }else{                    
                    errorVariables = new Object[]{result, values};
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, EVALUATION_CODE_QUALITATIVE_OUT_CONTAINS, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                    return diagnoses;                         
                }
            case QUALITATIVE_RULES_NOT_CONTAINS:                 
                if (values.toUpperCase().contains(result.toUpperCase())){
                    errorVariables = new Object[]{result, values};
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, EVALUATION_CODE_QUALITATIVE_OUT_NOT_CONTAINS, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                    return diagnoses;                                             
                }else{
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUALITATIVE_IN, null);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
                    return diagnoses;                    
                }

            case QUALITATIVE_RULES_IS_ONE_OF: 
                if ((separator==null) || (separator.length()==0)){
                    errorVariables = LPArray.addValueToArray1D(errorVariables, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_SEPARATOR);
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;
                }else{
                    String[] textSpecArray = values.split(separator);
                    for (Integer itextSpecArrayLen=0;itextSpecArrayLen<textSpecArray.length;itextSpecArrayLen++){
                        if (result.equalsIgnoreCase(textSpecArray[itextSpecArrayLen])){                            
                            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUALITATIVE_IN, null);
                            diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_IN);
                            return diagnoses;                    
                        }
                    }                
                    errorVariables = new Object[]{result, String.valueOf((Integer)textSpecArray.length+1), values};
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, EVALUATION_CODE_QUALITATIVE_OUT_IS_ONE_OF, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                    return diagnoses;                                                                 
                }
            case QUALITATIVE_RULES_IS_NOT_ONE_OF: 
                if ((separator==null) || (separator.length()==0)){                    
                    errorVariables = LPArray.addValueToArray1D(errorVariables, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_SEPARATOR);
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;
                }else{
                    String[] textSpecArray = values.split(separator);
                    if (!LPArray.valueInArray(textSpecArray, result)){
                        errorVariables = new Object[]{result, String.valueOf((Integer)textSpecArray.length+1), values};                        
                        Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, EVALUATION_CODE_QUALITATIVE_OUT_IS_NOT_ONE_OF, errorVariables);
                        diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT);
                        return diagnoses;
                    }
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUALITATIVE_IN, null);
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
            errorVariables = LPArray.addValueToArray1D(errorVariables, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_RESULT);
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
                    errorVariables = new Object[]{result.toString(), minSpec.toString()}; 
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, EVALUATION_CODE_QUANTITATIVE_OUT_MIN_STRICT, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT_MIN);
                    return diagnoses;
                }
            }else{
                if (result<minSpec){
                    errorVariables = new Object[]{result.toString(), minSpec.toString()}; 
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUANTITATIVE_OUT_MIN, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT_MIN);
                    return diagnoses;
                }
            }
        }                    
        if (maxSpec!=null){  
            if (maxStrict){
                if (result>=maxSpec){
                    errorVariables = new Object[]{result.toString(), maxSpec.toString()}; 
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUANTITATIVE_OUT_MAX_STRICT, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT_MAX);
                    return diagnoses;
                }
            }else{
                if (result>maxSpec){
                    errorVariables = new Object[]{result.toString(), maxSpec.toString()}; 
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, EVALUATION_CODE_QUANTITATIVE_OUT_MAX, errorVariables);
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
            errorVariables = LPArray.addValueToArray1D(errorVariables, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_RESULT);
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
                    errorVariables = new Object[]{result.toString(), minSpec.toString()}; 
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUANTITATIVE_OUT_MIN_STRICT, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_OUT_MIN);
                    return diagnoses;                
            }
        }                    
        if (maxSpec!=null){  
            int comparingMAX = result.compareTo(maxSpec);
            if ( (comparingMAX==1) || (comparingMAX==0 && maxStrict) ) {
                    errorVariables = new Object[]{result.toString(), maxSpec.toString()}; 
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUANTITATIVE_OUT_MAX_STRICT, errorVariables);
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
                errorVariables = LPArray.addValueToArray1D(errorVariables, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_RESULT);
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
                if (!minStrict || minStrict==null){
                    errorVariables = LPArray.addValueToArray1D(errorVariables, EVALUATION_CODE_QUANTITATIVE_OUT_ARGUMENT_MIN_SPEC_MIN_CONTROL);                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Min Strict  is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;
                }

                if (minStrict && minControlStrict){
                    errorVariables = LPArray.addValueToArray1D(errorVariables, EVALUATION_CODE_QUANTITATIVE_OUT_ARGUMENT_MIN_SPEC_MIN_CONTROL);                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "both, min Spec & Control Strict, set to true");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;                   
                }                    
            }    
    
            if (minControlStrict==null){minControlStrict=true;}
            if (minControlStrict){
                if (result<=minControl){
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, " < "+result.toString()+" < ");
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minControl.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, ERROR_TRAPPING_RESULT_CHECK_QUANT_OUT_MIN_CONTROL_IN_SPEC, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_CONTROL_MIN);
                    return diagnoses;                    
                }
            }
            else{
                if (result<minControl){
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, " <= "+result.toString()+" <= ");
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minControl.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, ERROR_TRAPPING_RESULT_CHECK_QUANT_OUT_MIN_CONTROL_IN_SPEC, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_CONTROL_MIN);
                    return diagnoses;                                   
                }
            }
                      
        }
        
        if (maxControl!=null){
            if (maxControl.equals(maxSpec)) {                
                if (!maxStrict || maxStrict==null){
                    errorVariables = LPArray.addValueToArray1D(errorVariables, EVALUATION_CODE_QUANTITATIVE_OUT_ARGUMENT_MAX_SPEC_MAX_CONTROL);                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "max Strict is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;                    
                }

                if (maxStrict && maxControlStrict){
                    errorVariables = LPArray.addValueToArray1D(errorVariables, EVALUATION_CODE_QUANTITATIVE_OUT_ARGUMENT_MAX_SPEC_MAX_CONTROL);                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "both, max Spec & Control Strict, set to true..");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;                    
                }                    
            }            
            if (maxControlStrict==null){maxControlStrict=true;}
            if (maxControlStrict){
                if (result>=maxControl){
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxControl.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, " > "+result.toString()+" > ");
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUANTITATIVE_OUT_MAX_CONTROL_IN_SPEC, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_CONTROL_MAX);
                    return diagnoses;                                         
                }
            }
            else{
                if (result>maxControl){
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxControl.toString());
                    errorVariables = LPArray.addValueToArray1D(errorVariables, " => "+result.toString()+" => ");
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, EVALUATION_CODE_QUANTITATIVE_OUT_MAX_CONTROL_IN_SPEC, errorVariables);
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
                errorVariables = LPArray.addValueToArray1D(errorVariables, ERROR_TRAPPING_RESULT_CHECK_NULL_MANDATORY_FIELD_ARGUMENT_RESULT);
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
                if (!minStrict || minStrict==null){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, EVALUATION_CODE_QUANTITATIVE_OUT_ARGUMENT_MIN_SPEC_MIN_CONTROL);                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "Min Strict  is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;
                }

                if (minStrict && minControlStrict){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, EVALUATION_CODE_QUANTITATIVE_OUT_ARGUMENT_MIN_SPEC_MIN_CONTROL);                   
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
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_QUANT_OUT_MIN_CONTROL_IN_SPEC;
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
                if (!maxStrict || maxStrict==null){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, EVALUATION_CODE_QUANTITATIVE_OUT_ARGUMENT_MAX_SPEC_MAX_CONTROL);                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "max Strict is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;                    
                }

                if (maxStrict && maxControlStrict){
                    errorCode = ERROR_TRAPPING_RESULT_CHECK_STRICT_DOES_NOT_ALLOW_EQUALS;
                    errorVariables = LPArray.addValueToArray1D(errorVariables, EVALUATION_CODE_QUANTITATIVE_OUT_ARGUMENT_MAX_SPEC_MAX_CONTROL);                   
                    errorVariables = LPArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LPArray.addValueToArray1D(errorVariables, "both, max Spec & Control Strict, set to true..");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LPArray.addValueToArray1D(diagnoses, EVALUATION_WRONG_RULE);
                    return diagnoses;                    
                }                    
            }            
            int comparingMAX = result.compareTo(maxSpec);
            if ( (comparingMAX==1) || (comparingMAX==0 && maxStrict) ) {
                errorCode = EVALUATION_CODE_QUANTITATIVE_OUT_MAX_CONTROL_IN_SPEC;
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
