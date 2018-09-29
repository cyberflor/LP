/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labPLANET.utilities;

/**
 * LabPLANETNullValue is a library for functions linked to the null db concept
 * @author Fran Gomez
 */
public class LabPLANETNullValue {
/**
 * Ensure it is a value or a blank value instead of a null value.
 * @param input String
 * @return String
 */    
    public static String replaceNull(String input) {
      return input == null ? "" : input;
    }     
}
