/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.materialSpec;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;

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
 * @return 
 */
    public Object[] specAllowSampleAnalysisAddition(){
        Object[] diagnoses = new Object[2];
        diagnoses[0]=false;
        return diagnoses;
    }
    public Object[] resultCheck(Float result, Float minSpec, Float maxSpec, Boolean minStrict, Boolean maxStrict){
        
        Object[] isCorrectMinMaxSpec = new Object[2];
        Object[] diagnoses = new Object[2];
        ConfigSpecRule matQuant = new ConfigSpecRule();
        
        if (result==null){diagnoses[0]="ERROR";diagnoses[1]="Result cannot be null";return diagnoses;}
        
        isCorrectMinMaxSpec = matQuant.specLimitIsCorrectQuantitative(minSpec, maxSpec);
        if ((Boolean) isCorrectMinMaxSpec[0]==false){
            return isCorrectMinMaxSpec;}
                
        if (minStrict==null){minStrict=true;}
        if (maxStrict==null){maxStrict=true;}
        //&& (maxSpec!=null)){
        if (minSpec!=null){  
            if (minStrict){
                if (result<minSpec){diagnoses[0]="OUT_MIN";diagnoses[1]=result+ " < " + minSpec.toString();return diagnoses;}
            }else{
                if (result<=minSpec){diagnoses[0]="OUT_MIN";diagnoses[1]=result+ " <= " + minSpec.toString();return diagnoses;}
            }
        }            
        
        if (maxSpec!=null){  
            if (maxStrict){
                if (result>maxSpec){diagnoses[0]="OUT_MAX";diagnoses[1]=result+ " > " + maxSpec.toString();return diagnoses;}
            }else{
                if (result>=maxSpec){diagnoses[0]="OUT_MAX";diagnoses[1]=result+ " >= " + maxSpec.toString();return diagnoses;}
            }
        }    
        
        diagnoses[0]="IN";diagnoses[1]="";return diagnoses;       
    }

    public Object[] resultCheck(String schemaName, String result, String specRule, String values, String separator, String listName){
        LabPLANETArray labArr = new LabPLANETArray();
        LabPLANETPlatform labPlat = new LabPLANETPlatform();        
        Object[] isCorrectTheSpec = new Object[2];
        Object[] diagnoses = new Object[2];
        ConfigSpecRule matQualit = new ConfigSpecRule();
        
        if (result==null || "".equals(result)){diagnoses[0]="ERROR";diagnoses[1]="Result cannot be null";return diagnoses;}
        if (specRule==null || "".equals(specRule)){diagnoses[0]="ERROR";diagnoses[1]="specRule cannot be null";return diagnoses;}
        if (values==null || "".equals(values)){diagnoses[0]="ERROR";diagnoses[1]="values cannot be null";return diagnoses;}
        
        isCorrectTheSpec = matQualit.specLimitIsCorrectQualitative(schemaName, specRule, values, separator);
        if ((Boolean) isCorrectTheSpec[0]==false){
            return isCorrectTheSpec;}
        
        switch (specRule.toUpperCase()){
            case "EQUALTO": 
                if (result.equalsIgnoreCase(values)){
                    diagnoses[0]="IN"; diagnoses[1]=""; return diagnoses;}
                else{
                    diagnoses[0]="OUT"; diagnoses[1]="The result "+"\""+result+"\""+" mismatch the spec "+values; return diagnoses;}
                
            case "NOTEQUALTO": 
                if (result.equalsIgnoreCase(values)){
                    diagnoses[0]="OUT"; diagnoses[1]="The result "+"\""+result+"\""+" matches the spec "+values; return diagnoses;}
                else{
                    diagnoses[0]="IN"; diagnoses[1]=""; return diagnoses;}

            case "CONTAINS":                 
                if (values.toUpperCase().contains(result.toUpperCase())){
                    diagnoses[0]="IN"; diagnoses[1]=""; return diagnoses;}
                else{
                    diagnoses[0]="OUT"; diagnoses[1]="The result "+"\""+result+"\""+" is not part of the spec value "+values; return diagnoses;}

            case "NOTCONTAINS":                 
                if (values.toUpperCase().contains(result.toUpperCase())){
                    diagnoses[0]="OUT"; diagnoses[1]="The result "+"\""+result+"\""+" is part of the spec value "+values; return diagnoses;}
                else{
                    diagnoses[0]="IN"; diagnoses[1]=""; return diagnoses;}

            case "ISONEOF": 
                if ((separator==null) || (separator.length()==0)){diagnoses[0]=false; diagnoses[1]="The separator is one mandatory field and cannot be null"; return diagnoses;}                
                else{
                    String[] textSpecArray = values.split(separator);
                    for (Integer itextSpecArrayLen=0;itextSpecArrayLen<textSpecArray.length;itextSpecArrayLen++){
                        if (result.equalsIgnoreCase(textSpecArray[itextSpecArrayLen])){
                        diagnoses[0]="IN"; diagnoses[1]=""; return diagnoses;}
                    }
                    
                    diagnoses[0]="OUT"; diagnoses[1]="The result "+"\""+result+"\""+" is not any of the "+((Integer)textSpecArray.length+1)+" entries in the spec "+values; return diagnoses;}

            case "ISNOTONEOF": 
                if ((separator==null) || (separator.length()==0)){diagnoses[0]=false; diagnoses[1]="The separator is one mandatory field and cannot be null"; return diagnoses;}
                else{
                    String[] textSpecArray = values.split(separator);
                    for (Integer itextSpecArrayLen=0;itextSpecArrayLen<textSpecArray.length;itextSpecArrayLen++){
                        if (result.equalsIgnoreCase(textSpecArray[itextSpecArrayLen])){
                            diagnoses[0]="OUT"; diagnoses[1]="The result "+result+" is one of the "+((Integer)textSpecArray.length+1)+" entries in the spec "+values; return diagnoses;}
                    }
                    diagnoses[0]="IN"; diagnoses[1]=""; return diagnoses;}
                    

            default: diagnoses[0]=false; diagnoses[1]="This combination of input argument is not expected."; return diagnoses;
                //break;
        }        
        
    }
    
    public Object[] resultCheck(Float result, Float minSpec, Float maxSpec, Boolean minStrict, Boolean maxStrict, Float minControl, Float maxControl, Boolean minControlStrict, Boolean maxControlStrict){
        
        Object[] isCorrectMinMaxSpec = new Object[2];
        Object[] diagnoses = new Object[2];
        ConfigSpecRule matQuant = new ConfigSpecRule();

        if (result==null){diagnoses[0]="ERROR";diagnoses[1]="Result cannot be null";return diagnoses;}
        
        isCorrectMinMaxSpec = this.resultCheck(result,minSpec,maxSpec, minStrict, maxStrict);
        
        if (isCorrectMinMaxSpec[0].toString().toUpperCase().contains("OUT")){
            return isCorrectMinMaxSpec;
        }

        if (isCorrectMinMaxSpec[0].toString().toUpperCase().contains("FALSE")){
            return isCorrectMinMaxSpec;
        }

        if (minControl!=null){
            if (minControl.equals(minSpec)) {                
                if (minStrict==false || minStrict==null){
                    diagnoses[0]="ERROR";diagnoses[1]="Min spec and control cannot be the same ("+minSpec+") when min Strict is set to false.";return diagnoses;}

                if (minStrict==true && minControlStrict==true){
                    diagnoses[0]="ERROR";diagnoses[1]="Min spec and control cannot be the same ("+minSpec+") and both, min Spec & Control Strict set to true.";return diagnoses;}                    
            }    
    
            if (minControlStrict==null){minControlStrict=true;}
            if (minControlStrict){
                if (result<=minControl){diagnoses[0]="CONTROL_MIN";diagnoses[1]=result+ " < " + minControl.toString();return diagnoses;}
            }
            else{if (result<minControl){diagnoses[0]="CONTROL_MIN";diagnoses[1]=result+ " <= " + minControl.toString();return diagnoses;}}
                      
        }
        
        if (maxControl!=null){
            if (maxControl.equals(maxSpec)) {                
                if (maxStrict==false || maxStrict==null){
                    diagnoses[0]="ERROR";diagnoses[1]="Max spec and control cannot be the same ("+maxSpec+") when max Strict is set to false.";return diagnoses;}

                if (maxStrict==true && maxControlStrict==true){
                    diagnoses[0]="ERROR";diagnoses[1]="Max spec and control cannot be the same ("+maxSpec+") and both, max Spec & Control Strict set to true.";return diagnoses;}                    
            }            
            if (maxControlStrict==null){maxControlStrict=true;}
            if (maxControlStrict){
                if (result>=maxControl){diagnoses[0]="CONTROL_MAX";diagnoses[1]=result+ " < " + maxControl.toString();return diagnoses;}
            }
            else{if (result>maxControl){diagnoses[0]="CONTROL_MAX";diagnoses[1]=result+ " <= " + maxControl.toString();return diagnoses;}}
                        
        }

        diagnoses[0]="IN";diagnoses[1]="";return diagnoses;   

    }
    
}
