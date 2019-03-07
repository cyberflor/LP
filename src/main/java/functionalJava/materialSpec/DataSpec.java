/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.materialSpec;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;
import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
public class DataSpec {

    String classVersion = "0.1";
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = ""; 
    
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
      //  LabPLANETPlatform labPlat = new LabPLANETPlatform();        
        Object[] isCorrectTheSpec = new Object[2];
        Object[] diagnoses = new Object[2];
        ConfigSpecRule matQualit = new ConfigSpecRule();
        LabPLANETArray labArr = new LabPLANETArray();
        Object [] errorVariables = new Object[0];        

        String errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";        
        if (result==null || "".equals(result)){
            errorVariables = labArr.addValueToArray1D(errorVariables, "Result");
            diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
            diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
            return diagnoses;}               
        if (specRule==null || "".equals(specRule)){
            errorVariables = labArr.addValueToArray1D(errorVariables, "specRule");            
            diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
            diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
            return diagnoses;}        
        if (values==null || "".equals(values)){
            errorVariables = labArr.addValueToArray1D(errorVariables, "values");            
            diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
            diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
            return diagnoses;}        
        
        isCorrectTheSpec = matQualit.specLimitIsCorrectQualitative(schemaName, specRule, values, separator);
        if ("LABPLANET_FALSE".equalsIgnoreCase(isCorrectTheSpec[0].toString())){
            return isCorrectTheSpec;}
        
        switch (specRule.toUpperCase()){
            case "EQUALTO": 
                if (result.equalsIgnoreCase(values)){
                    errorCode = "DataSpec_resultCheck_qualitativeIN";
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "IN");
                    return diagnoses;                    
                }else{
                    errorCode = "DataSpec_resultCheck_qualitativeEqualToOUT";
                    errorVariables = labArr.addValueToArray1D(errorVariables, result);
                    errorVariables = labArr.addValueToArray1D(errorVariables, values);
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT");
                    return diagnoses;                                                           
                }
                
            case "NOTEQUALTO": 
                if (result.equalsIgnoreCase(values)){
                    errorCode = "DataSpec_resultCheck_qualitativeNotEqualToOUT";
                    errorVariables = labArr.addValueToArray1D(errorVariables, result);
                    errorVariables = labArr.addValueToArray1D(errorVariables, values);
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT");
                    return diagnoses;                                                                               
                }else{
                    errorCode = "DataSpec_resultCheck_qualitativeIN";
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "IN");
                    return diagnoses;                    
                }

            case "CONTAINS":                 
                if (values.toUpperCase().contains(result.toUpperCase())){
                    errorCode = "DataSpec_resultCheck_qualitativeIN";
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "IN");
                    return diagnoses;                    
                }else{
                    errorCode = "DataSpec_resultCheck_qualitativeContainsOUT";
                    errorVariables = labArr.addValueToArray1D(errorVariables, result);
                    errorVariables = labArr.addValueToArray1D(errorVariables, values);
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT");
                    return diagnoses;                         
                }

            case "NOTCONTAINS":                 
                if (values.toUpperCase().contains(result.toUpperCase())){
                    errorCode = "DataSpec_resultCheck_qualitativeNotContainsOUT";
                    errorVariables = labArr.addValueToArray1D(errorVariables, result);
                    errorVariables = labArr.addValueToArray1D(errorVariables, values);
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT");
                    return diagnoses;                                             
                }else{
                    errorCode = "DataSpec_resultCheck_qualitativeIN";
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "IN");
                    return diagnoses;                    
                }

            case "ISONEOF": 
                if ((separator==null) || (separator.length()==0)){
                    errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Separator");
                    diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;
                }else{
                    String[] textSpecArray = values.split(separator);
                    for (Integer itextSpecArrayLen=0;itextSpecArrayLen<textSpecArray.length;itextSpecArrayLen++){
                        if (result.equalsIgnoreCase(textSpecArray[itextSpecArrayLen])){
                            errorCode = "DataSpec_resultCheck_qualitativeIN";
                            diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                            diagnoses = labArr.addValueToArray1D(diagnoses, "IN");
                            return diagnoses;                    
                        }
                    }
                    errorCode = "DataSpec_resultCheck_qualitativeIsOneOfOUT";
                    errorVariables = labArr.addValueToArray1D(errorVariables, result);
                    errorVariables = labArr.addValueToArray1D(errorVariables, String.valueOf((Integer)textSpecArray.length+1));
                    errorVariables = labArr.addValueToArray1D(errorVariables, values);
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT");
                    return diagnoses;                                                                 
                }
            case "ISNOTONEOF": 
                if ((separator==null) || (separator.length()==0)){
                    errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Separator");
                    diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;
                }else{
                    String[] textSpecArray = values.split(separator);
                    for (Integer itextSpecArrayLen=0;itextSpecArrayLen<textSpecArray.length;itextSpecArrayLen++){
                        if (result.equalsIgnoreCase(textSpecArray[itextSpecArrayLen])){
                            errorCode = "DataSpec_resultCheck_qualitativeIsNotOneOfOUT";
                            errorVariables = labArr.addValueToArray1D(errorVariables, result);
                            errorVariables = labArr.addValueToArray1D(errorVariables, String.valueOf((Integer)textSpecArray.length+1));
                            errorVariables = labArr.addValueToArray1D(errorVariables, values);
                            diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                            diagnoses = labArr.addValueToArray1D(diagnoses, "OUT");
                            return diagnoses;
                        }
                    }
                    errorCode = "DataSpec_resultCheck_qualitativeIN";
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "IN");
                    return diagnoses;                    
                }
            default:
                errorCode = "DataSpec_resultCheck_UnhandledException";
                String params = "Schema Name: "+schemaName+", result: "+result+", Spec Rule: "+specRule+", values: "+values+", separator: "+separator+", listName: "+listName;
                errorVariables = labArr.addValueToArray1D(errorVariables, params);
                diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
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
        
        Object[] isCorrectMinMaxSpec = new Object[2];
        Object[] diagnoses = new Object[7];
        ConfigSpecRule matQuant = new ConfigSpecRule();
        LabPLANETArray labArr = new LabPLANETArray();

        Object [] errorVariables = new Object[0];        
        
        if (result==null){
            String errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
            errorVariables = labArr.addValueToArray1D(errorVariables, "Result");
            diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
            diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
            return diagnoses;
        }
        isCorrectMinMaxSpec = matQuant.specLimitIsCorrectQuantitative(minSpec, maxSpec);
        if ("LABPLANET_FALSE".equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;}
                
        if (minStrict==null){minStrict=true;}
        if (maxStrict==null){maxStrict=true;}
        //&& (maxSpec!=null)){
        if (minSpec!=null){  
            if (minStrict){
                if (result<=minSpec){                
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMinStrict";
                    errorVariables = labArr.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = labArr.addValueToArray1D(errorVariables, minSpec.toString());
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_MIN");
                    return diagnoses;
                }
            }else{
                if (result<minSpec){
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMin";
                    errorVariables = labArr.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = labArr.addValueToArray1D(errorVariables, minSpec.toString());
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_MIN");
                    return diagnoses;
                }
            }
        }                    
        if (maxSpec!=null){  
            if (maxStrict){
                if (result>=maxSpec){
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMaxStrict";
                    errorVariables = labArr.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = labArr.addValueToArray1D(errorVariables, maxSpec.toString());
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_MAX");
                    return diagnoses;
                }
            }else{
                if (result>maxSpec){
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMax";
                    errorVariables = labArr.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = labArr.addValueToArray1D(errorVariables, maxSpec.toString());
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_MAX");
                    return diagnoses;
                }
            }
        }        
        String errorCode = "DataSpec_resultCheck_quantitativeIn";
        diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
        diagnoses = labArr.addValueToArray1D(diagnoses, "IN");
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
        
        Object[] isCorrectMinMaxSpec = new Object[2];
        Object[] diagnoses = new Object[7];
        ConfigSpecRule matQuant = new ConfigSpecRule();
        LabPLANETArray labArr = new LabPLANETArray();

        Object [] errorVariables = new Object[0];        
        
        if (result==null){
            String errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
            errorVariables = labArr.addValueToArray1D(errorVariables, "Result");
            diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
            diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
            return diagnoses;
        }
        isCorrectMinMaxSpec = matQuant.specLimitIsCorrectQuantitative(minSpec, maxSpec);
        if ("LABPLANET_FALSE".equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;}
                
        if (minStrict==null){minStrict=true;}
        if (maxStrict==null){maxStrict=true;}

        if (minSpec!=null){  
            int compareMIN = minSpec.compareTo(result);
            if ( (compareMIN==1) || (compareMIN==0 && minStrict) ) {
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMinStrict";
                    errorVariables = labArr.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = labArr.addValueToArray1D(errorVariables, minSpec.toString());
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_MIN");
                    return diagnoses;                
            }
        }                    
        if (maxSpec!=null){  
            int compareMAX = result.compareTo(maxSpec);
            if ( (compareMAX==1) || (compareMAX==0 && maxStrict) ) {
                    String errorCode = "DataSpec_resultCheck_quantitativeOutMaxStrict";
                    errorVariables = labArr.addValueToArray1D(errorVariables, result.toString());
                    errorVariables = labArr.addValueToArray1D(errorVariables, maxSpec.toString());
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_MAX");
                    return diagnoses;            
            }            
        }    
        String errorCode = "DataSpec_resultCheck_quantitativeIn";
        diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
        diagnoses = labArr.addValueToArray1D(diagnoses, "IN");
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
        
        Object[] isCorrectMinMaxSpec = new Object[2];
        Object[] diagnoses = new Object[2];
        ConfigSpecRule matQuant = new ConfigSpecRule();
        String errorCode = "";
        Object [] errorVariables = new Object[0]; 
        LabPLANETArray labArr = new LabPLANETArray();

        if (result==null){
                errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
                errorVariables = labArr.addValueToArray1D(errorVariables, "Result");
                diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                return diagnoses;
        }
        
        isCorrectMinMaxSpec = this.resultCheck(schemaName, result,minSpec,maxSpec, minStrict, maxStrict);
        
        if ("LABPLANET_FALSE".equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;
        }

        if (isCorrectMinMaxSpec[isCorrectMinMaxSpec.length-1].toString().contains("OUT")){
            return isCorrectMinMaxSpec;
        }

        if (minControl!=null){
            if (minControl.equals(minSpec)) {                
                if (minStrict==false || minStrict==null){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = labArr.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Min Strict  is set to false.");                   
                    diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;
                }

                if (minStrict==true && minControlStrict==true){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = labArr.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = labArr.addValueToArray1D(errorVariables, "both, min Spec & Control Strict, set to true");                   
                    diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                   
                }                    
            }    
    
            if (minControlStrict==null){minControlStrict=true;}
            if (minControlStrict){
                if (result<=minControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMinControlInSpec";
                    errorVariables = labArr.addValueToArray1D(errorVariables, minSpec.toString());
                    errorVariables = labArr.addValueToArray1D(errorVariables, " < "+result.toString()+" < ");
                    errorVariables = labArr.addValueToArray1D(errorVariables, minControl.toString());
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "CONTROL_MIN");
                    return diagnoses;                    
                }
            }
            else{
                if (result<minControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMinControlInSpec";
                    errorVariables = labArr.addValueToArray1D(errorVariables, minSpec.toString());
                    errorVariables = labArr.addValueToArray1D(errorVariables, " <= "+result.toString()+" <= ");
                    errorVariables = labArr.addValueToArray1D(errorVariables, minControl.toString());
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "CONTROL_MIN");
                    return diagnoses;                                   
                }
            }
                      
        }
        
        if (maxControl!=null){
            if (maxControl.equals(maxSpec)) {                
                if (maxStrict==false || maxStrict==null){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = labArr.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = labArr.addValueToArray1D(errorVariables, "max Strict is set to false.");                   
                    diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                    
                }

                if (maxStrict==true && maxControlStrict==true){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = labArr.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = labArr.addValueToArray1D(errorVariables, "both, max Spec & Control Strict, set to true..");                   
                    diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                    
                }                    
            }            
            if (maxControlStrict==null){maxControlStrict=true;}
            if (maxControlStrict){
                if (result>=maxControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMaxControlInSpec";
                    errorVariables = labArr.addValueToArray1D(errorVariables, maxControl.toString());
                    errorVariables = labArr.addValueToArray1D(errorVariables, " > "+result.toString()+" > ");
                    errorVariables = labArr.addValueToArray1D(errorVariables, maxSpec.toString());
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "CONTROL_MAX");
                    return diagnoses;                                         
                }
            }
            else{
                if (result>maxControl){
                    errorCode = "DataSpec_resultCheck_quantitativeOutMaxControlInSpec";
                    errorVariables = labArr.addValueToArray1D(errorVariables, maxControl.toString());
                    errorVariables = labArr.addValueToArray1D(errorVariables, " => "+result.toString()+" => ");
                    errorVariables = labArr.addValueToArray1D(errorVariables, maxSpec.toString());
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "CONTROL_MAX");
                    return diagnoses;                                         
                }
            }                        
        }
        
        errorCode = "DataSpec_resultCheck_quantitativeIn";
        diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
        diagnoses = labArr.addValueToArray1D(diagnoses, "IN");
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
        
        Object[] isCorrectMinMaxSpec = new Object[2];
        Object[] diagnoses = new Object[2];
        ConfigSpecRule matQuant = new ConfigSpecRule();
        String errorCode = "";
        Object [] errorVariables = new Object[0]; 
        LabPLANETArray labArr = new LabPLANETArray();

        if (result==null){
                errorCode = "DataSpec_resultCheck_mandatoryFieldIsNull";
                errorVariables = labArr.addValueToArray1D(errorVariables, "Result");
                diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                return diagnoses;
        }
        
        isCorrectMinMaxSpec = this.resultCheck(schemaName, result,minSpec,maxSpec, minStrict, maxStrict);
        
        if ("OUT".equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;
        }

        if ("LABPLANET_FALSE".equalsIgnoreCase(isCorrectMinMaxSpec[0].toString())){
            return isCorrectMinMaxSpec;
        }

        if (minControl!=null){
            if (minControl.equals(minSpec)) {                
                if (minStrict==false || minStrict==null){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = labArr.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Min Strict  is set to false.");                   
                    diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;
                }

                if (minStrict==true && minControlStrict==true){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Min Spec and Min Control");                   
                    errorVariables = labArr.addValueToArray1D(errorVariables, minSpec);
                    errorVariables = labArr.addValueToArray1D(errorVariables, "both, min Spec & Control Strict, set to true");                   
                    diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                   
                }                    
            }    
    
            if (minControlStrict==null){minControlStrict=true;}
            
            int compareMIN = minControl.compareTo(result);
            if ( (compareMIN==1) || (compareMIN==0 && minControlStrict) ) {
                    errorCode = "DataSpec_resultCheck_quantitativeOutMinControlInSpec";
                    errorVariables = labArr.addValueToArray1D(errorVariables, minSpec.toString());
                    errorVariables = labArr.addValueToArray1D(errorVariables, " < "+result.toString()+" < ");
                    errorVariables = labArr.addValueToArray1D(errorVariables, minControl.toString());
                    diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "CONTROL_MIN");
                    return diagnoses;                    
            }                      
        }
        
        if (maxControl!=null){
            
            if (maxControl.equals(maxSpec)) {                
                if (maxStrict==false || maxStrict==null){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = labArr.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = labArr.addValueToArray1D(errorVariables, "max Strict is set to false.");                   
                    diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                    
                }

                if (maxStrict==true && maxControlStrict==true){
                    errorCode = "DataSpec_resultCheck_StrictDoesNotAllowPairOfSameValue";
                    errorVariables = labArr.addValueToArray1D(errorVariables, "Max spec and control");                   
                    errorVariables = labArr.addValueToArray1D(errorVariables, maxSpec);
                    errorVariables = labArr.addValueToArray1D(errorVariables, "both, max Spec & Control Strict, set to true..");                   
                    diagnoses =  LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_FALSE", classVersion, errorCode, errorVariables);
                    diagnoses = labArr.addValueToArray1D(diagnoses, "OUT_WRONG_RULE");
                    return diagnoses;                    
                }                    
            }            
            int compareMAX = result.compareTo(maxSpec);
            if ( (compareMAX==1) || (compareMAX==0 && maxStrict) ) {
                errorCode = "DataSpec_resultCheck_quantitativeOutMaxControlInSpec";
                errorVariables = labArr.addValueToArray1D(errorVariables, maxControl.toString());
                errorVariables = labArr.addValueToArray1D(errorVariables, " > "+result.toString()+" > ");
                errorVariables = labArr.addValueToArray1D(errorVariables, maxSpec.toString());
                diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, errorVariables);
                diagnoses = labArr.addValueToArray1D(diagnoses, "CONTROL_MAX");
                return diagnoses;                                         
            }
        }
        
        errorCode = "DataSpec_resultCheck_quantitativeIn";
        diagnoses = LabPLANETPlatform.trapErrorMessage(null, "LABPLANET_TRUE", classVersion, errorCode, null);
        diagnoses = labArr.addValueToArray1D(diagnoses, "IN");
        return diagnoses;            
    }
    

}
