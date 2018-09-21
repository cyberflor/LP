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
 * @param rule String - Rule Type
 * @param textSpec String - The value for building the spec
 * @param separator String - For those that requires separator for the many option that should be part of the range.
 * @return Object[] position 0 is a boolean to determine if the arguments are correct, when set to false then position 1 provides detail about the deficiency 
 */    
    public Object[] specLimitIsCorrectQualitative(String rule, String textSpec, String separator){
                
        Object[] diagnoses = new Object[2];
        String[] expectedRules = new String[6];
        
        expectedRules[0] = "EQUALTO";
        expectedRules[1] = "NOTEQUALTO";
        expectedRules[2] = "CONTAINS";
        expectedRules[3] = "NOTCONTAINS";
        expectedRules[4] = "ISONEOF";
        expectedRules[5] = "ISNOTONEOF";
        
        if ((rule==null) || (rule.length()==0)){diagnoses[0]=false; diagnoses[1]="The rule is one mandatory field and cannot be null"; return diagnoses;}
        if ((textSpec==null) || (textSpec.length()==0)){diagnoses[0]=false; diagnoses[1]="The Text Spec is one mandatory field and cannot be null"; return diagnoses;}
        switch (rule.toUpperCase()){
            case "EQUALTO": diagnoses[0]=true; diagnoses[1]=""; return diagnoses;

            case "NOTEQUALTO": diagnoses[0]=true; diagnoses[1]=""; return diagnoses;

            case "CONTAINS": diagnoses[0]=true; diagnoses[1]=""; return diagnoses;
            
            case "NOTCONTAINS": diagnoses[0]=true; diagnoses[1]=""; return diagnoses;

            case "ISONEOF": 
                if ((separator==null) || (separator.length()==0)){diagnoses[0]=false; diagnoses[1]="The separator is one mandatory field and cannot be null"; return diagnoses;}                
                else{
                    String[] textSpecArray = textSpec.split(separator);
                    diagnoses[0]=true; diagnoses[1]="This Text Spec has "+textSpecArray.length+" entries."; return diagnoses;}

            case "ISNOTONEOF": 
                if ((separator==null) || (separator.length()==0)){diagnoses[0]=false; diagnoses[1]="The separator is one mandatory field and cannot be null"; return diagnoses;}
                else{
                    String[] textSpecArray = textSpec.split(separator);
                    diagnoses[0]=true; diagnoses[1]="This Text Spec has "+textSpecArray.length+" entries."; return diagnoses;}

            default: diagnoses[0]=false; diagnoses[1]="The rule "+rule+" is not recognized as one of the expected ones. The expected values are: "+Arrays.toString(expectedRules); return diagnoses;
                //break;
        }
    }
/**
 * This method verify that the parameters provided to build one quantitative spec limit apply just one range are coherent accordingly to the different options:<br>
 * Basically when both are not null then cannot be the same value even min cannot be greater than max.
 * @param minSpec Float - The minimum value
 * @param maxSpec Float - The maximum value
 * @return Object[] position 0 is a boolean to determine if the arguments are correct, when set to false then position 1 provides detail about the deficiency 
 */
    public Object[] specLimitIsCorrectQuantitative(Float minSpec, Float maxSpec){
                
        Object[] diagnoses = new Object[2];
        
        if ((minSpec==null) && (maxSpec==null)){diagnoses[0]=false; diagnoses[1]="Min and Max Spec values are null, both cannot be set to null"; return diagnoses;}
        if ((minSpec!=null) && (maxSpec==null)){diagnoses[0]=true; diagnoses[1]=""; return diagnoses;}
        if ((minSpec==null) && (maxSpec!=null)){diagnoses[0]=true; diagnoses[1]=""; return diagnoses;}
        
        if (minSpec<maxSpec){diagnoses[0]=true; diagnoses[1]=""; return diagnoses;}
        
        diagnoses[0]=false; diagnoses[1]=minSpec.toString()+" is >= "+maxSpec.toString();
        return diagnoses;
    }

/**
 * This method verify that the parameters provided to build one quantitative spec limit apply one double level range are coherent accordingly to the different options:<br>
 * Basically when both peers, min-max, are not null then cannot be the same value even min cannot be greater than max. At the same time
 * The control range should be included or part of the spec range that should be broader.
 * @param minSpec Float - The minimum value
 * @param maxSpec Float - The maximum value
 * @param minControl1 Float - The minimum control
 * @param maxControl1 Float - The maximum control
 * @return Object[] position 0 is a boolean to determine if the arguments are correct, when set to false then position 1 provides detail about the deficiency 
 */    
    public Object[] specLimitIsCorrectQuantitative(Float minSpec, Float maxSpec, Float minControl1, Float maxControl1){
        
        Object[] isCorrectMinMaxSpec = new Object[2];
        Object[] diagnoses = new Object[2];
        
        isCorrectMinMaxSpec = this.specLimitIsCorrectQuantitative(minSpec, maxSpec);
        if ((Boolean) isCorrectMinMaxSpec[0]==false){
            return isCorrectMinMaxSpec;}
                
        if ((minControl1==null) && (maxControl1==null)){
/*            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="TRUE";
            diagnoses[4]="SUCCESS: SPEC UPDATED";
            diagnoses[5]="no limits, result is ok";            */
            diagnoses[0]=true; diagnoses[1]="";
            return diagnoses;}
        if ((minControl1!=null) && (minSpec==null)){diagnoses[0]=false; diagnoses[1]="Min Control is "+minControl1.toString()+" but min Spec is null, this is not possible"; return diagnoses;}
        if ((maxControl1!=null) && (maxSpec==null)){diagnoses[0]=false; diagnoses[1]="Max Control is "+maxControl1.toString()+" but max Spec is null, this is not possible"; return diagnoses;}
        
        if (((minControl1!=null) && (maxControl1!=null)) && (minControl1>=maxControl1)){diagnoses[0]=false; diagnoses[1]="Min Control is "+minControl1.toString()+" and cannot be greater than or equal to Max Control that is "+maxControl1.toString(); return diagnoses;}

        if (((minControl1!=null) && (maxSpec!=null)) && (minControl1>=maxSpec)){diagnoses[0]=false; diagnoses[1]="Min Control is "+minControl1.toString()+" and cannot be greater than or equal to Max Spec that is "+maxSpec.toString(); return diagnoses;}        
        if (((maxControl1!=null) && (minSpec!=null)) && (maxControl1<=minSpec)){diagnoses[0]=false; diagnoses[1]="Max Control is "+maxControl1.toString()+" and cannot be less than or equal to Min Spec that is "+minSpec.toString(); return diagnoses;}        
        
        if (minControl1!=null){                        
            if (minControl1.compareTo(minSpec)<=0){diagnoses[0]=false; diagnoses[1]="Min Control is "+minControl1.toString()+ " and cannot be lesser than or equal to the Min Spec value that is "+minSpec.toString(); return diagnoses;            
            }else{diagnoses[0]=true; diagnoses[1]=""; return diagnoses;}              
        }                 
        
        if ((maxControl1!=null)){
            int comparison = 0;
            if (maxControl1.compareTo(maxSpec)>=0){diagnoses[0]=false; diagnoses[1]="Max Control is "+maxControl1.toString()+ " and cannot be greater than or equal to the Max Spec value that is "+maxSpec.toString(); return diagnoses;
            }else{diagnoses[0]=true; diagnoses[1]=""; return diagnoses;}                
        }
        
        diagnoses[0]=false; diagnoses[1]="For "+minControl1.toString()+"(Min) and "+maxControl1.toString()+"(Max) pair control are out the logic control.";
        return diagnoses;
    }
     
} 

