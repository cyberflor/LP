/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class LPParadigm {
    
    
    public static Object[] fieldNameValueArrayChecker (String[] fName, Object[] fValue){
        String classVersion = "0.1";
        Object[] diagnoses = null;
        String errorCode ="";
        Object[] errorDetailVariables= new Object[0];

        LabPLANETArray labArr = new LabPLANETArray();
        diagnoses = labArr.checkTwoArraysSameLength(fName, fValue);
        if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
           errorCode = "DataSample_FieldArraysDifferentSize";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(fName));
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(fValue));
           return LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);
        }
        
        if (labArr.duplicates(fName)){
           errorCode = "DataSample_FieldsDuplicated";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(fName));
           return LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                      
        }        
        
        labArr=null;
        diagnoses = labArr.addValueToArray1D(diagnoses, "LABPLANET_TRUE");
        return diagnoses;
                
    }
    
}
