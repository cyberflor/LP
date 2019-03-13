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
    
  /*private LPParadigm() {
    throw new IllegalStateException("Utility class");
  }*/    
    
    
    public static Object[] fieldNameValueArrayChecker (String[] fName, Object[] fValue){
        Object[] diagnoses = null;
        String errorCode ="";
        Object[] errorDetailVariables= new Object[0];

        diagnoses = LabPLANETArray.checkTwoArraysSameLength(fName, fValue);
        if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
           errorCode = "DataSample_FieldArraysDifferentSize";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(fName));
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(fValue));
           return LPPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);
        }
        
        if (LabPLANETArray.duplicates(fName)){
           errorCode = "DataSample_FieldsDuplicated";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(fName));
           return LPPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                      
        }        
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "LABPLANET_TRUE");
        return diagnoses;
                
    }
    
}
