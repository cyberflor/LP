/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.materialSpec;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LPPlatform;
import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
public class DataSpec {
    String classVersion = "0.1";
    
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
     * @param schemaName
     * @param result
     * @param specRule
     * @param values
     * @param separator
     * @param listName
     * @return
     */
    public Object[] resultCheck(String schemaName, String result, String specRule, String values, String separator, String listName){
        Object[] isCorrectTheSpec = new Object[2];
        Object[] diagnoses = new Object[2];
        ConfigSpecRule matQualit = new ConfigSpecRule();
        Object [] errorVariables = new Object[0];        

        String errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";        
        if (result==null || "".equals(result)){
            errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Result");
            diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
            diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
            return diagnoses;}               
        if (specRule==null || "".equals(specRule)){
            errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "specRule");            
            diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
            diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
            return diagnoses;}        
        if (values==null || "".equals(values)){
            errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "values");            
            diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
            diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
            return diagnoses;}        
        
        isCorrectTheSpec = matQualit.specLimitIsCorrectQualitative(schemaName, specRule, values, separator);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(isCorrectTheSpec[0].toString())){
            return isCorrectTheSpec;}
        
        switch (specRule.toUpperCase()){
            case "EQUALTO": 
                if (result.equalsIgnoreCase(values)){
                    errorCode = "DataSpec_resultCheck_qualitativeIN";
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "IN");
                    return diagnoses;                    
                }else{
                    errorCode = "DataSpec_resultCheck_qualitativeEqualToOUT";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, values);
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT");
                    return diagnoses;                                                           
                }
                
            case "NOTEQUALTO": 
                if (result.equalsIgnoreCase(values)){
                    errorCode = "DataSpec_resultCheck_qualitativeNotEqualToOUT";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, values);
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT");
                    return diagnoses;                                                                               
                }else{
                    errorCode = "DataSpec_resultCheck_qualitativeIN";
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "IN");
                    return diagnoses;                    
                }

            case "CONTAINS":                 
                if (values.toUpperCase().contains(result.toUpperCase())){
                    errorCode = "DataSpec_resultCheck_qualitativeIN";
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "IN");
                    return diagnoses;                    
                }else{
                    errorCode = "DataSpec_resultCheck_qualitativeContainsOUT";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, values);
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT");
                    return diagnoses;                         
                }

            case "NOTCONTAINS":                 
                if (values.toUpperCase().contains(result.toUpperCase())){
                    errorCode = "DataSpec_resultCheck_qualitativeNotContainsOUT";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, values);
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT");
                    return diagnoses;                                             
                }else{
                    errorCode = "DataSpec_resultCheck_qualitativeIN";
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "IN");
                    return diagnoses;                    
                }

            case "ISONEOF": 
                if ((separator==null) || (separator.length()==0)){
                    errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Separator");
                    diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;
                }else{
                    String[] textSpecArray = values.split(separator);
                    for (Integer itextSpecArrayLen=0;itextSpecArrayLen<textSpecArray.length;itextSpecArrayLen++){
                        if (result.equalsIgnoreCase(textSpecArray[itextSpecArrayLen])){
                            errorCode = "DataSpec_resultCheck_qualitativeIN";
                            diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
                            diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "IN");
                            return diagnoses;                    
                        }
                    }
                    errorCode = "DataSpec_resultCheck_qualitativeIsOneOfOUT";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, String.valueOf((Integer)textSpecArray.length+1));
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, values);
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT");
                    return diagnoses;                                                                 
                }
            case "ISNOTONEOF": 
                if ((separator==null) || (separator.length()==0)){
                    errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Separator");
                    diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;
                }else{
                    String[] textSpecArray = values.split(separator);
                    if (!LabPLANETArray.valueInArray(textSpecArray, result)){
                        errorCode = "DataSpec_resultCheck_qualitativeIsNotOneOfOUT";
                        errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result);
                        errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, String.valueOf((Integer)textSpecArray.length+1));
                        errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, values);
                        diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT");
                        return diagnoses;
                    }
                    errorCode = "DataSpec_resultCheck_qualitativeIN";
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, null);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "IN");
                    return diagnoses;                    
                }
            default:
                errorCode = "DataSpec_resultCheck_UnhandledException";
                String params = "Schema Name: "+schemaName+", result: "+result+", Spec Rule: "+specRule+", values: "+values+", separator: "+separator+", listName: "+listName;
                errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, params);
                diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                return diagnoses;               
        }
    }

    /**
     *
     * @param schemaName
     * @param result
     * @param minSpec
     * @param maxSpec
     * @param minStrict
     * @param maxStrict
     * @return
     */
    public Object[] resultCheck(String schemaName, Float result, Float minSpec, Float maxSpec, Boolean minStrict, Boolean maxStrict){
        
        ConfigSpecRule matQuant = new ConfigSpecRule();
        Object[] errorVariables = new Object[0];        
        
        if (result==null){
            String errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
            errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Result");
            Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
            diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
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
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_MIN");
                    return diagnoses;
                }
            }else{
                if (result<minSpec){
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMin";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_MIN");
                    return diagnoses;
                }
            }
        }                    
        if (maxSpec!=null){  
            if (maxStrict){
                if (result>=maxSpec){
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMaxStrict";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_MAX");
                    return diagnoses;
                }
            }else{
                if (result>maxSpec){
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMax";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_MAX");
                    return diagnoses;
                }
            }
        }        
        String errorCode = "DataSpec_resultCheck_quantitativeIn";
        Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "IN");
        return diagnoses;        
    }

    /**
     *
     * @param schemaName
     * @param result
     * @param minSpec
     * @param maxSpec
     * @param minStrict
     * @param maxStrict
     * @return
     */
    public Object[] resultCheck(String schemaName, BigDecimal result, BigDecimal minSpec, BigDecimal maxSpec, Boolean minStrict, Boolean maxStrict){
        
        ConfigSpecRule matQuant = new ConfigSpecRule();

        Object [] errorVariables = new Object[0];        
        
        if (result==null){
            String errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
            errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Result");
            Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
            diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
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
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_MIN");
                    return diagnoses;                
            }
        }                    
        if (maxSpec!=null){  
            int comparingMAX = result.compareTo(maxSpec);
            if ( (comparingMAX==1) || (comparingMAX==0 && maxStrict) ) {
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMaxStrict";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_MAX");
                    return diagnoses;            
            }            
        }    
        String errorCode = "DataSpec_resultCheck_quantitativeIn";
        Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "IN");
        return diagnoses;        
    }
    
    /**
     *
     * @param schemaName
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
    public Object[] resultCheck(String schemaName, Float result, Float minSpec, Float maxSpec, Boolean minStrict, Boolean maxStrict, Float minControl, Float maxControl, Boolean minControlStrict, Boolean maxControlStrict){
        
        String errorCode = "";
        Object [] errorVariables = new Object[0]; 

        if (result==null){
                errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
                errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Result");
                Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                return diagnoses;
        }
        
        Object[] isCorrectMinMaxSpec = this.resultCheck(schemaName, result,minSpec,maxSpec, minStrict, maxStrict);
        
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;
        }

        if (isCorrectMinMaxSpec[isCorrectMinMaxSpec.length-1].toString().contains("OUT")){
            return isCorrectMinMaxSpec;
        }

        if (minControl!=null){
            if (minControl.equals(minSpec)) {                
                if (minStrict==false || minStrict==null){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Min Strict  is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;
                }

                if (minStrict==true && minControlStrict==true){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "both, min Spec & Control Strict, set to true");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                   
                }                    
            }    
    
            if (minControlStrict==null){minControlStrict=true;}
            if (minControlStrict){
                if (result<=minControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMinControlInSpec";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minSpec.toString());
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, " < "+result.toString()+" < ");
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minControl.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "CONTROL_MIN");
                    return diagnoses;                    
                }
            }
            else{
                if (result<minControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMinControlInSpec";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minSpec.toString());
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, " <= "+result.toString()+" <= ");
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minControl.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "CONTROL_MIN");
                    return diagnoses;                                   
                }
            }
                      
        }
        
        if (maxControl!=null){
            if (maxControl.equals(maxSpec)) {                
                if (maxStrict==false || maxStrict==null){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "max Strict is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                    
                }

                if (maxStrict==true && maxControlStrict==true){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "both, max Spec & Control Strict, set to true..");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                    
                }                    
            }            
            if (maxControlStrict==null){maxControlStrict=true;}
            if (maxControlStrict){
                if (result>=maxControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMaxControlInSpec";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxControl.toString());
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, " > "+result.toString()+" > ");
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "CONTROL_MAX");
                    return diagnoses;                                         
                }
            }
            else{
                if (result>maxControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMaxControlInSpec";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxControl.toString());
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, " => "+result.toString()+" => ");
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxSpec.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "CONTROL_MAX");
                    return diagnoses;                                         
                }
            }                        
        }
        
        errorCode = "DataSpec_resultCheck_quantitativeIn";
        Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "IN");
        return diagnoses;            
    }
    
    /**
     *
     * @param schemaName
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
    public Object[] resultCheck(String schemaName, BigDecimal result, BigDecimal minSpec, BigDecimal maxSpec, Boolean minStrict, Boolean maxStrict, BigDecimal minControl, BigDecimal maxControl, Boolean minControlStrict, Boolean maxControlStrict){
        
        String errorCode = "";
        Object [] errorVariables = new Object[0]; 

        if (result==null){
                errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
                errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Result");
                Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                return diagnoses;
        }
        
        Object[] isCorrectMinMaxSpec = this.resultCheck(schemaName, result,minSpec,maxSpec, minStrict, maxStrict);
        
        if ("OUT".equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;
        }

        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;
        }

        if (minControl!=null){
            if (minControl.equals(minSpec)) {                
                if (minStrict==false || minStrict==null){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Min Strict  is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;
                }

                if (minStrict==true && minControlStrict==true){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "both, min Spec & Control Strict, set to true");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                   
                }                    
            }    
    
            if (minControlStrict==null){minControlStrict=true;}
            
            int comparingMIN = minControl.compareTo(result);
            if ( (comparingMIN==1) || (comparingMIN==0 && minControlStrict) ) {
                    errorCode = "DataSpec_resultCheck_quantitativeOutMinControlInSpec";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minSpec.toString());
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, " < "+result.toString()+" < ");
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, minControl.toString());
                    Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "CONTROL_MIN");
                    return diagnoses;                    
            }                      
        }
        
        if (maxControl!=null){
            
            if (maxControl.equals(maxSpec)) {                
                if (maxStrict==false || maxStrict==null){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "max Strict is set to false.");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                    
                }

                if (maxStrict==true && maxControlStrict==true){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, "both, max Spec & Control Strict, set to true..");                   
                    Object[] diagnoses =  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorVariables);
                    diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                    
                }                    
            }            
            int comparingMAX = result.compareTo(maxSpec);
            if ( (comparingMAX==1) || (comparingMAX==0 && maxStrict) ) {
                errorCode = "DataSpec_resultCheck_quantitativeOutMaxControlInSpec";
                errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxControl.toString());
                errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, " > "+result.toString()+" > ");
                errorVariables = LabPLANETArray.addValueToArray1D(errorVariables, maxSpec.toString());
                Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorVariables);
                diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "CONTROL_MAX");
                return diagnoses;                                         
            }
        }
        
        errorCode = "DataSpec_resultCheck_quantitativeIn";
        Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, null);
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "IN");
        return diagnoses;            
    }
    

}
