/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import functionalJava.unitsOfMeasurement.UnitsOfMeasurement;
import java.math.BigDecimal;

/**
 * LabPLANETMath is a library for adding extra maths to the standard ones.
 * @author Fran Gomez
 * @version 0.1
 */
public class LabPLANETMath {
    
/**
 * 
 * Calc the nth root. Common application on sampling plans.
 * @param n int - The nth root 
 * @param A double - for this value
 * @param p double 
 * @return double. returns 0 if A = 0, returns -1 in case of error.
 */    
    public static double nthroot(int n, double A, double p) {
	if(A < 0) {
		System.err.println("A < 0");// we handle only real positive numbers
		return -1;
	} else if(A == 0) {
		return 0;
	}
	double x_prev = A;
	double x = A / n;  // starting "guessed" value...
	while(Math.abs(x - x_prev) > p) {
		x_prev = x;
		x = ((n - 1.0) * x + A / Math.pow(x, n - 1.0)) / n;
	}
	return x;
    }  
    
    public static Object[] extractPortion(String schemaPrefix, BigDecimal volume, String volumeUOM, Integer volumeObjectId, BigDecimal portion, String portionUOM, Integer portionObjectId){
        
        String errorCode="";
        Object[] errorDetailVariables = new Object[0];
        
        if (volume!=null){
            UnitsOfMeasurement uom = new UnitsOfMeasurement();
            if (portionUOM == null ? volumeUOM != null : !portionUOM.equals(volumeUOM)){
                Object[] valueConverted = uom.convertValue(schemaPrefix, portion, portionUOM, volumeUOM);
                portion = (BigDecimal) valueConverted[valueConverted.length-2];
            }
        }
        if ( portion.compareTo(BigDecimal.ZERO)==-1) {
            errorCode = "DataSample_sampleAliquoting_volumeCannotBeNegativeorZero";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, portion.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, portionObjectId.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                 
        }        
       volume = volume.add(portion.negate());        
       if ( volume.compareTo(BigDecimal.ZERO)==-1) {
            errorCode = "DataSample_sampleAliquoting_notEnoughVolumeToAliquoting";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "aliquot  "+volumeObjectId.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, volume.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, "subaliquoting");
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, portion.toString());
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                          
        }        
       if (!(volume.compareTo(portion)==1) ) {
            errorCode = "DataSample_sampleAliquoting_notEnoughVolumeForExtraction";
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
