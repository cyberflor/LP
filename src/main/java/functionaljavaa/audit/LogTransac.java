/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionaljavaa.audit;

/**
 * UNDER DEVELOPMENT
 * @author Fran Gomez
 */
import lbplanet.utilities.LPArray;

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
        Object[][] calledFunct = null;
        this.mainMethodName = methodName;
        this.mainInputArguments = mainInputArguments;
        this.mainOutput = mainOutput;
        this.calledFunctions = calledFunct;
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
        
        Object[] newCalledFunctions = new Object[1];
//        JSONArray mJSONArray;
//        mJSONArray = new JSONArray(mainInputArguments);
        
        newCalledFunctions[0] = methodName; newCalledFunctions[1] = mainInputArguments; newCalledFunctions[2] = mainOutput;
        
        this.calledFunctions = LPArray.addColumnToArray2D(this.calledFunctions, newCalledFunctions);
    }

/**
 * UNDER DEVELOPMENT
 * @author Fran Gomez
     * @param methodName
     * @param mainInputArguments
     * @param mainOutput
 */
    public void addCall(String methodName, String mainInputArguments, Object mainOutput){        
        
        Object[] newCalledFunctions = new Object[1];
//        JSONArray mJSONArray;
//        mJSONArray = new JSONArray(mainInputArguments);
        
        newCalledFunctions[0] = methodName; newCalledFunctions[1] = mainInputArguments; newCalledFunctions[2] = mainOutput;
        
        this.calledFunctions = LPArray.addColumnToArray2D(calledFunctions, newCalledFunctions);
    }
/**
 * UNDER DEVELOPMENT
 * @author Fran Gomez
     * @return 
 */        
    public Integer getNumberOfChildren(){
        Integer childCount = 0;
        childCount = this.calledFunctions.length;
        return childCount;
    }
    
}
