/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.audit;

/**
 * UNDER DEVELOPMENT
 * @author Fran Gomez
 */
import LabPLANET.utilities.LabPLANETArray;

/**
 * UNDER DEVELOPMENT
 * @author Fran Gomez
 */
public class LogTransac {
  
    String mainMethodName = "";
    Object[] mainInputArguments;
    Object[] mainOutput;
    Object[][] calledFunctions;
    
/**
 * UNDER DEVELOPMENT
 * @author Fran Gomez
     * @param methodName
     * @param mainInputArguments
     * @param mainOutput
 */
    public LogTransac(String methodName, Object[] mainInputArguments, Object[] mainOutput){
        Object[][] calledFunctions = null;
        this.mainMethodName = methodName;
        this.mainInputArguments = mainInputArguments;
        this.mainOutput = mainOutput;
        this.calledFunctions = calledFunctions;
    }        

/**
 * UNDER DEVELOPMENT
 * @author Fran Gomez
     * @param methodName
     * @param mainInputArguments
     * @param mainOutput
     * @param level
 */
    public void addCall(String methodName, String[] mainInputArguments, Object[] mainOutput, Integer level){        
        LabPLANETArray labArr = new LabPLANETArray();
        Object[] newCalledFunctions = new Object[1];
//        JSONArray mJSONArray;
//        mJSONArray = new JSONArray(mainInputArguments);
        
        newCalledFunctions[0] = methodName; newCalledFunctions[1] = mainInputArguments; newCalledFunctions[2] = mainOutput;
        
        this.calledFunctions = labArr.addColumnToArray2D(this.calledFunctions, newCalledFunctions);
    }

/**
 * UNDER DEVELOPMENT
 * @author Fran Gomez
     * @param methodName
     * @param mainInputArguments
     * @param mainOutput
 */
    public void addCall(String methodName, String mainInputArguments, Object mainOutput){        
        LabPLANETArray labArr = new LabPLANETArray();
        Object[] newCalledFunctions = new Object[1];
//        JSONArray mJSONArray;
//        mJSONArray = new JSONArray(mainInputArguments);
        
        newCalledFunctions[0] = methodName; newCalledFunctions[1] = mainInputArguments; newCalledFunctions[2] = mainOutput;
        
        this.calledFunctions = labArr.addColumnToArray2D(calledFunctions, newCalledFunctions);
    }
/**
 * UNDER DEVELOPMENT
 * @author Fran Gomez
     * @return 
 */        
    public Integer getNumberOfChilds(){
        Integer ChildCount = 0;
        ChildCount = this.calledFunctions.length;
        return ChildCount;
    }
    
}
