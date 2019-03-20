/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.testingScripts;

import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class TestingAssert {
    
    String evalBoolean="";
    String errorCode="";    
    
    public TestingAssert(Object[] line, Integer numArgs){
        switch (numArgs){                    
            case 1:
                this.evalBoolean=(String) line[0];
            case 2:
                this.evalBoolean=(String) line[0];
                this.errorCode=(String) line[1];
            default:                
        }        
    }    
    private TestingAssert(String bool, String errCode){
        this.evalBoolean=bool; 
        this.errorCode=errCode;
    }
    
    public Object[] evaluate(Integer numEvaluationArguments, TestingAssertSummary tstAssertSummary, Object[] diagnoses){
        String sintaxisIcon = ""; String codeIcon = "";
        if (numEvaluationArguments>0){
            if (this.evalBoolean.equalsIgnoreCase(diagnoses[0].toString())){
                tstAssertSummary.increasetotalLabPlanetBooleanMatch(); sintaxisIcon=LPTestingOutFormat.TST_BOOLEANMATCH;
            }else{tstAssertSummary.increasetotalLabPlanetBooleanUnMatch(); sintaxisIcon=LPTestingOutFormat.TST_BOOLEANUNMATCH;}
            if (this.errorCode.equalsIgnoreCase(diagnoses[4].toString())){
                tstAssertSummary.increasetotalLabPlanetErrorCodeMatch(); codeIcon=LPTestingOutFormat.TST_ERRORCODEMATCH;
            }else{tstAssertSummary.increasetotalLabPlanetErrorCodeUnMatch(); codeIcon=LPTestingOutFormat.TST_ERRORCODEUNMATCH;}
        }    
        return new Object[] {sintaxisIcon + " ("+this.evalBoolean+") ", 
            codeIcon + "<h8>("+this.errorCode+")</h8> ", 
            Arrays.toString(diagnoses)};
}
    
}
