/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import functionalJava.unitsOfMeasurement.UnitsOfMeasurement;
import java.math.BigDecimal;

/**
 * LPMath is a library for adding extra maths to the standard ones.
 * @author Fran Gomez
 * @version 0.1
 */
public class LPMath {
    private LPMath(){    throw new IllegalStateException("Utility class");}    
/**
 * 
 * Calc the nth root. Common application on sampling plans.
 * @param n int - The nth root 
 * @param a double - for this value
 * @param p double 
 * @return double. returns 0 if A = 0, returns -1 in case of error.
 */    
    public static double nthroot(int n, double a, double p) {
        if(a < 0) {
            return -1;
        }else if(a == 0) {
            return 0;
        }
        double xPrev = a;
        double x = a / n;  // starting "guessed" value...
        while(Math.abs(x - xPrev) > p) {
                xPrev = x;
                x = ((n - 1.0) * x + a / Math.pow(x, n - 1.0)) / n;
        }
        return x;
    }  
    
    public static Object[] extractPortion(String schemaPrefix, BigDecimal volume, String volumeUOM, Integer volumeObjectId, BigDecimal portion, String portionUOM, Integer portionObjectId){
        volumeUOM = volumeUOM == null ? "" : volumeUOM;
        String errorCode="";
        Object[] errorDetailVariables = new Object[0];
        
        if (volume==null){
            errorCode = "DataSample_sampleAliquoting_volumeCannotBeNegativeorZero";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "");
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "");
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                 
        }
        UnitsOfMeasurement uom = new UnitsOfMeasurement();
        if (portionUOM == null ? volumeUOM != null : !portionUOM.equals(volumeUOM)){
            Object[] valueConverted = uom.convertValue(schemaPrefix, portion, portionUOM, volumeUOM);
            portion = (BigDecimal) valueConverted[valueConverted.length-2];
        }
        
        if ( portion.compareTo(BigDecimal.ZERO)<1) {
            errorCode = "DataSample_sampleAliquoting_volumeCannotBeNegativeorZero";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, portion.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, portionObjectId.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                 
        }        
       volume = volume.add(portion.negate());        
       if ( volume.compareTo(BigDecimal.ZERO)<0) {
            errorCode = "DataSample_sampleAliquoting_notEnoughVolumeToAliquoting";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "aliquot  "+volumeObjectId.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, volume.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "subaliquoting");
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, portion.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                          
        }        
       
       String conclusionMsg = "It is possible to extract ";
       if (volumeUOM.equalsIgnoreCase(portionUOM)){
           conclusionMsg=conclusionMsg+portion.toString()+" from "+volume.toString()+" expressed in "+volumeUOM;
       }else{
           conclusionMsg=conclusionMsg+portion.toString()+" of "+portionUOM+" from "+volume.toString()+" of "+volumeUOM;
       }
        return new Object[]{LPPlatform.LAB_TRUE, conclusionMsg, portion};
    }
    
} // end class
