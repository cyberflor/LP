/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import static LabPLANET.utilities.LPPlatform.trapErrorMessage;
import functionalJava.parameter.Parameter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LPArray is a library for methods for building and modeling 1D and 2D arrays.
 * @author Fran Gomez
 * @version 0.1
 */

public class  LPArray {
        
    private static final String ENCRYPTION_KEY = "Bar12345Bar12345";
    private static final String ENCRYPTED_PREFIX = "encrypted_";
    private static final String ERRORTRAPPING_EXCEPTION= "LabPLANETPlatform_SpecialFunctionReturnedEXCEPTION";
    /**
     *
     * @param zipcodelist
     * @return
     */
    public static boolean duplicates(String[] zipcodelist){
      Set<String> lump = new HashSet<>();
      for (String i : zipcodelist)
      {
        if (lump.contains(i)) return true;
        lump.add(i);
      }
      return false;
    }    
    
    /**
     *
     * @param arrayHeader
     * @param array
     * @param filename
     * @param fieldsSeparator
     * @return
     * @throws IOException
     */
    public static Object[] arrayToFile (String[] arrayHeader, String[] array, String filename, String fieldsSeparator) throws IOException{
        Object[] diagnosis = new Object[0];
        
        BufferedWriter outputWriter = null;
        try{        
            if (arrayHeader!=null){
                for (String arrayHeader1 : arrayHeader) {
                    outputWriter.write(arrayHeader1 + fieldsSeparator);
                    outputWriter.newLine();
                }
            }
            outputWriter = new BufferedWriter(new FileWriter(filename));
            for (String array1 : array) {
                outputWriter.write(array1);
                outputWriter.newLine();
            }
            outputWriter.flush();  
            outputWriter.close();          
            return diagnosis;        
        }catch(IOException e){
            outputWriter.close();
        }
        return diagnosis;  
    }
    
    /**
     *
     * @param schemaName
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public static Object[] encryptTableFieldArray(String schemaName, String tableName, String[] fieldName, Object[] fieldValue){
         String key = ENCRYPTION_KEY; // 128 bit key
        //? Should be by schemaPrefix? config or data???
        String fieldsEncrypted = Parameter.getParameterBundle(schemaName.replace("\"", ""), ENCRYPTED_PREFIX+tableName);        
        for (int iFields=0;iFields<fieldName.length;iFields++){
            if (fieldsEncrypted.contains(fieldName[iFields])){
                try{
                    String text = fieldValue[iFields].toString();
                    // Create key and cipher
                    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
                    Cipher cipher = Cipher.getInstance("AES");
                    // encrypt the text
                    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                    byte[] encrypted = cipher.doFinal(text.getBytes());

                    StringBuilder sb = new StringBuilder();
                    for (byte b: encrypted) {
                        sb.append((char)b);
                    }

                    // the encrypted String
                    String enc = sb.toString();
                    fieldValue[iFields] = enc;

                    // decrypt the text
    //                cipher.init(Cipher.DECRYPT_MODE, aesKey);
    //                String decrypted = new String(cipher.doFinal(bb));
    //                System.err.println("decrypted:" + decrypted);                
        }
                catch(InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){
                    String errorCode = ERRORTRAPPING_EXCEPTION;
                    Object[] errorDetailVariables = new Object[0];
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, e.getMessage());
                    return trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
                }
            }
        }
        
        return fieldValue;        
    }  
    
    /**
     *
     * @param schemaName
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public static Object[][] decryptTableFieldArray(String schemaName, String tableName, String[] fieldName, Object[][] fieldValue){
        String key = ENCRYPTION_KEY; //"Bar12345Bar12345"; // 128 bit key
        String fieldsEncrypted = Parameter.getParameterBundle(schemaName.replace("\"", ""), ENCRYPTED_PREFIX+tableName);
        for (int iFields=0;iFields<fieldName.length;iFields++){
            if (fieldsEncrypted.contains(fieldName[iFields])){
                try{                    
                    for (Object[] fieldValue1 : fieldValue) {
                        String enc = fieldValue1[iFields].toString();
                        if (enc==null){
                            fieldValue1[iFields] = "";                            
                        }else{
                            // Create key and cipher
                            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
                            Cipher cipher = Cipher.getInstance("AES");
                            // for decryption
                            byte[] bb = new byte[enc.length()];
                            for (int i=0; i<enc.length(); i++) {
                                bb[i] = (byte) enc.charAt(i);
                            }
                            // decrypt the text
                            cipher.init(Cipher.DECRYPT_MODE, aesKey);
                            String decrypted = new String(cipher.doFinal(bb));
                            fieldValue1[iFields] = decrypted;
                        }    
                    }        
        }
                catch(InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){
                    String errorCode = ERRORTRAPPING_EXCEPTION;
                    Object[] errorDetailVariables = new Object[0];
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, e.getMessage());
                    return LPArray.array1dTo2d(
                            trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables), errorDetailVariables.length);
                }
            }
        }
        
        return fieldValue;        
    }    

    /**
     *
     * @param schemaName
     * @param tableName
     * @param fieldName
     * @param fieldValue
     * @return
     */
    public static Object[] decryptTableFieldArray(String schemaName, String tableName, String[] fieldName, Object[] fieldValue){
        String key = ENCRYPTION_KEY;
        String fieldsEncrypted = Parameter.getParameterBundle(schemaName.replace("\"", ""), ENCRYPTED_PREFIX+tableName);
        for (int iFields=0;iFields<fieldName.length;iFields++){
            if (fieldsEncrypted.contains(fieldName[iFields])){
                try{                                        
                    String enc = fieldValue[iFields].toString();
                    // Create key and cipher
                    Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
                    Cipher cipher = Cipher.getInstance("AES");
                    // for decryption
                    byte[] bb = new byte[enc.length()];
                    for (int i=0; i<enc.length(); i++) {
                        bb[i] = (byte) enc.charAt(i);
                    }
                    // decrypt the text
                    cipher.init(Cipher.DECRYPT_MODE, aesKey);
                    String decrypted = new String(cipher.doFinal(bb));
                    fieldValue[iFields] = decrypted;
                }
                catch(InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){
                    String errorCode = ERRORTRAPPING_EXCEPTION;
                    Object[] errorDetailVariables = new Object[0];
                    errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, e.getMessage());
                    return trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
                }
            }
        }
        
        return fieldValue;        
    }    
    
/**
 * Sometimes values from different nature/type are concatenated in same string, in this case concatenating the type by using *<br>
 * Example: 1*String will be treat as text but 1*Integer as numeric.<br>
 * Data Types supported: STRING, INTEGER, FLOAT, BOOLEAN
 * @param myStringsArray String[] - String containing the peers values*type.
 * @return Object[] - The same values expressed in the proper type.
 */
    public static Object[] convertStringWithDataTypeToObjectArray(String[] myStringsArray){
        
        Object[] myObjectsArray = new Object[myStringsArray.length];
        
        for (Integer i=0;i<myStringsArray.length;i++){
            String[] rowParse = myStringsArray[i].split("\\*");
            if (rowParse.length!=2){
                myObjectsArray[i]=myStringsArray[i];
            }else{
                switch (rowParse[1].toUpperCase()){                                    
                    case "STRING":
                        myObjectsArray[i]=rowParse[0];
                        break;
                    case "INTEGER":
                        myObjectsArray[i]=Integer.parseInt((String) rowParse[0]);
                        break;
                    case "FLOAT":               
                        myObjectsArray[i]=Float.parseFloat((String) rowParse[0]);
                        break;
                    case "BOOLEAN":               
                        myObjectsArray[i]=Boolean.valueOf((String) rowParse[0]);
                        break;               
                    case "DATE":        
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        {
                            try {
                                myObjectsArray[i]= format.parse((String) rowParse[0]);
                            } catch (ParseException ex) {
                                Logger.getLogger(LPArray.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;                                                            
                    default:
                        myObjectsArray[i]=rowParse[0];
                        break;
                }        
            }                
        }        
        return myObjectsArray;
        
    }

    /**
     *
     * @param xfileLocation
     * @param csvSeparator
     * @return
     */
    public static String[][] convertCSVinArray(String xfileLocation, String csvSeparator){
        String[][] myArray = new String[0][0];
        String[] myArray1D = new String[0];
        Scanner scanIn = null;
        Integer columnsInCsv=0;
        String inputLine = ""; 
        
        try{
            scanIn = new Scanner(new BufferedReader(new FileReader(xfileLocation)));
            Integer numLines = 0;
            while (scanIn.hasNextLine()){
                inputLine = scanIn.nextLine();
                numLines++;
                myArray1D = addValueToArray1D(myArray1D, inputLine );                
                String[] inArray = inputLine.split(String.valueOf(csvSeparator));
                if (inArray.length>columnsInCsv) {columnsInCsv = inArray.length;}
            }
            scanIn.close();
            myArray = new String[numLines][columnsInCsv];    
            for (Integer inumLines=0;inumLines<numLines;inumLines++){                
                String[] inArray = myArray1D[inumLines].split(String.valueOf(csvSeparator));
                System.arraycopy(inArray, 0, myArray[inumLines], 0, inArray.length);
            }            
//            myArray = array1dTo2d(myArray1D, columnsInCsv);
            return myArray;            
        } catch (FileNotFoundException e){               
            myArray1D = addValueToArray1D(myArray1D, e.getMessage());
            return array1dTo2d(myArray1D, 1);
            
        }
    }
    
    /**
     *
     * @param xfileLocation
     * @param csvSeparator
     * @return
     */
    public static String[][] convertCSVinArrayHomogeneous(String xfileLocation, String csvSeparator){
        String[][] myArray = new String[0][0];
        String[] myArray1D = new String[0];
        Scanner scanIn = null;
        Integer columnsInCsv=0;
        String inputLine = ""; 
        try{
            scanIn = new Scanner(new BufferedReader(new FileReader(xfileLocation)));
     
            while (scanIn.hasNextLine()){
                inputLine = scanIn.nextLine();
                String[] inArray = inputLine.split(String.valueOf(csvSeparator));
                if (inArray.length>columnsInCsv) {columnsInCsv = inArray.length;}
                if (inArray.length == columnsInCsv ){
                    for (String inArray1 : inArray) {
                        myArray1D = addValueToArray1D(myArray1D, inArray1);
                    }    
                }
            }
            scanIn.close();
            if (columnsInCsv==0){columnsInCsv=1;}
            myArray = array1dTo2d(myArray1D, columnsInCsv);
            return myArray;
            
        } catch (FileNotFoundException e){}        
        return myArray;
    }
    
/**
 * Converts one 2-Dimensional in a 1-Dimensional array
 * @param array2d String[][]
 * @return String[]
 */
    public static String[] array2dTo1d(String[][] array2d){
        List<String> list;
        list = new ArrayList<>();
        for (String[] array2d1 : array2d) {
            list.addAll(Arrays.asList(array2d1)); // java.util.Arrays
        }
        String[] array1d = new String[list.size()];
        array1d = list.toArray(array1d);
        
       return array1d;
    }
/**
 * Converts one 2-Dimensional in a 1-Dimensional array.
 * @param array2d Object[][]
 * @return Object[]
 */ 
    public static Object[] array2dTo1d(Object[][] array2d){
        //Object[] array1d = new Object[1];
        //Integer numLines = 
        //String[][] my2Darr = .....(something)......
        List<Object> list;
        list = new ArrayList<>();
        for (Object[] array2d1 : array2d) {
            list.addAll(Arrays.asList(array2d1)); // java.util.Arrays
        }
        Object[] array1d = new Object[list.size()];
        array1d = list.toArray(array1d);
        
       return array1d;
    }
    
/**
 * Converts one 2-Dimensional in a 1-Dimensional array just extracting the values from the given column
 * @param array2d Object[][]
 * @param colNum Integer
 * @return Object[]
 */
    public static Object[] array2dTo1d(Object[][] array2d, Integer colNum){
        Object[] array1d = new Object[0];
        for (Integer iLine=0;iLine<array2d.length;iLine++) {
           array1d = addValueToArray1D(array1d, array2d[iLine][colNum]); 
        }        
       return array1d;
    }
        
/**
 * Converts one 1-Dimensional in a 2-Dimensional array where numColumns determines the size per line or row.
 * @param array1d String[][]
 * @param numColumns Integer
 * @return String[][]
 */    
    public static String[][] array1dTo2d(String[] array1d, Integer numColumns){
        
        Integer numLines = array1d.length/numColumns;
        String[][] array2d = new String[numLines][numColumns];        
        int inumLines = 0;    
        int iTotal = 0;
        while ( iTotal < array1d.length) {
            for (int inumColumns=0; inumColumns<numColumns;inumColumns++){
                array2d[inumLines][inumColumns]=array1d[iTotal];
                if (inumColumns+1==numColumns){inumLines++;}
                iTotal++;
            }                        
        }                
       return array2d;
    }

    /**
     *
     * @param array1d
     * @param numColumns
     * @return
     */
    public static Object[][] array1dTo2d(Object[] array1d, Integer numColumns){
        
        Integer numLines = array1d.length/numColumns;
        Object[][] array2d = new Object[numLines][numColumns];        
        int inumLines = 0;    
        int iTotal = 0;
        while ( iTotal < array1d.length) {
            for (int inumColumns=0; inumColumns<numColumns;inumColumns++){
                array2d[inumLines][inumColumns]=array1d[iTotal];
                if (inumColumns+1==numColumns){inumLines++;}
                iTotal++;
            }                        
        }                
       return array2d;
    }    
/**
 * Determines is the given value is present in the array.
 * @param array Object[]
 * @param value Object
 * @return boolean
 */
    public static boolean valueInArray(Object[] array, Object value){
        boolean diagnoses = false;
        Integer specialFieldIndex = Arrays.asList(array).indexOf(value);
        if (specialFieldIndex!=-1){return true;}
        return diagnoses;
    }

/**
 * Determines is the given value is present in the array.
 * @param array Object[]
 * @param value Object
 * @return boolean
 */
    public static Integer valuePosicInArray(Object[] array, Object value){        
        Integer specialFieldIndex = Arrays.asList(array).indexOf(value);
        if (specialFieldIndex!=-1){return specialFieldIndex;}
        return -1;
    }

 /**
 * Add one new position to the array at the bottom for the new incoming value
 * @param array Object[] 
 * @param newValue Object
 * @return Object[] 
 */    
    public static Object[] addValueToArray1D(Object[] array, Object newValue){
        Integer arrayLen = 0;
        if (array==null){
            arrayLen = 0;
        }else{
            arrayLen = array.length;
        }
        Object[] newArray = new Object[arrayLen+1];
        if (array!=null){        
            for (Integer i=0;i<array.length;i++){
                newArray[i]=array[i];
            }
        }    
        newArray[newArray.length-1]= newValue;
        return newArray;
    }

 /**
 * Add one new position to the Calendar array at the bottom for the new incoming Calendar value
 * @param array Calendar[] 
 * @param newValue Calendar
 * @return Calendar[] 
 */    
    public static Calendar[] addCalendarValueToCalendarArray1D(Calendar[] array, Calendar newValue){
        Calendar[] newArray = new Calendar[array.length+1];
        
        for (Integer i=0;i<array.length;i++){
            newArray[i]=array[i];
        }
        newArray[newArray.length-1]= newValue;
        return newArray;
    }

 /**
 * Add new positions to the array at the bottom for the new incoming values at once
 * @param array Object[] 
 * @param newValues Object[]
 * @return Object[] 
 */    
    public static Object[] addValueToArray1D(Object[] array, Object[] newValues){
        Integer arrayLen = 0;
        if (array==null){
            arrayLen = 0;
        }else{
            arrayLen = array.length;
        }
        Object[] newArray = new Object[arrayLen];

        if (array!=null){     
            for (Integer i=0;i<array.length;i++){
                newArray[i]=array[i];
            }    
        }

        for (Integer i=0;i<newValues.length;i++){
            newArray = addValueToArray1D(newArray, newValues[i]);
        }
               
        return newArray;
    }

/**
 * Add new positions to the array at the bottom for the new incoming values at once
 * @param array String[] 
 * @param newValues String[]
 * @return String[] 
 */ 
    public static String[] addValueToArray1D(String[] array, String[] newValues){
        Integer arrayLen = 0;
        if (array==null){
            arrayLen = 0;
        }else{
            arrayLen = array.length;
        }
        String[] newArray = new String[arrayLen];
        
        if (array!=null){
            for (Integer i=0;i<array.length;i++){
                newArray[i]=array[i];
            }    
        }

        for (Integer i=0;i<newValues.length;i++){
            newArray = addValueToArray1D(newArray, newValues[i]);
        }
               
        return newArray;
    }
    
/**
 * Add one new position to the array at the bottom for the new incoming value
 * @param array String[] 
 * @param newValue String
 * @return String[] 
 */  
    public static String[] addValueToArray1D(String[] array, String newValue){
        Integer arrayLen = 0;
        if (array==null){
            arrayLen = 0;
        }else{
            arrayLen = array.length;
        }
        String[] newArray = new String[arrayLen+1];
        
        if (array!=null){
            for (Integer i=0;i<array.length;i++){
                newArray[i]=array[i];
            }
        }    
        newArray[newArray.length-1]= newValue;
        return newArray;
    }
/**
 * Set the same value for all rows in a given column
 * @param array Object[][]
 * @param col Integer
 * @param newValue Object
 * @return Object[][]  
 */
    public static Object[][] setColumnValueToArray2D(Object[][] array, Integer col, Object newValue){
        
        //Object[][] newArray = new Object[array.length][array[0].length];
        for (Object[] array1 : array) {
            array1[col] = newValue;
        }
                
        return array;
    }

/**
 * Add one new column by the end and set it to the same value for all rows.
 * @param array Object[][]
 * @param newValue Object
 * @return  Object[][]
 */    
    public static Object[][] addColumnToArray2D(Object[][] array, Object newValue){
        
        Object[][] newArray = new Object[array.length][array[0].length+1];
        
        for (int row = 0; row < array.length; row++){
            System.arraycopy(array[row], 0, newArray[row], 0, array[0].length);
            newArray[row][newArray[0].length-1]= newValue;
        }
                
        return newArray;
    }
/**
 * Build one 1-Dimensional array with the bigger size of both passed as arguments
 * and where the value in each position will be the result of concatenate the value in both arrays 
 * separated by the separator specified in the third argument.
 * @param arrayOne Object[]
 * @param arrayTwo Object[]
 * @param separator String
 * @return String[]
 */
    public static String[] joinTwo1DArraysInOneOf1DString (Object[] arrayOne, Object[] arrayTwo, String separator){
        String[] newArray = new String[0];        
        Integer arrLength = arrayOne.length;
        
        if (arrayTwo.length>arrLength){arrLength=arrayTwo.length;}
        
            String currValueA ="";
            String currValueB ="";        
            for (Integer iarrLength = 0;iarrLength<arrLength;iarrLength++){            
                if (arrayOne.length < iarrLength) {currValueA ="";}
                if (arrayOne[iarrLength]==null){currValueA ="";}
                else{currValueA = arrayOne[iarrLength].toString();}
            
                if (arrayTwo.length < iarrLength) {currValueB ="";}
                if (arrayTwo[iarrLength]==null){currValueB ="";}
                else{currValueB = arrayTwo[iarrLength].toString();}
            
                currValueA = LPNulls.replaceNull(currValueA); currValueB = LPNulls.replaceNull(currValueB);
                
                String newValue = currValueA + separator + currValueB;
                newArray = addValueToArray1D(newArray, newValue);
            }
        return newArray;
    }

/**
 * Verify whether two arrays having the same size
 * @param arrayA Object[]
 * @param arrayB Object[]
 * @return String[6]. Position 3 FALSE/TRUE is the diagnostic.
 */    
    public static String[] checkTwoArraysSameLength(Object[] arrayA, Object[] arrayB){
    String errorCode = "";
    String[] errorDetailVariables = new String[0];

        String[] diagnoses = new String[6];
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName() + " called from " + elements[2].getMethodName();
        if (arrayA.length!=arrayB.length){
           errorCode = "DataSample_FieldArraysDifferentSize";
           errorDetailVariables = addValueToArray1D(errorDetailVariables, Arrays.toString(arrayA));
           errorDetailVariables = addValueToArray1D(errorDetailVariables, Arrays.toString(arrayB));
           return (String[]) LPPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);           
        }else{
            diagnoses[0]="LABPLANET_TRUE";
        }    
        return diagnoses;
    }

/**
 * Build one 1-Dimensional array for the given column from the 2-Dimensional array passed by argument
 * @param array Object[][]
 * @param colNum Integer
 * @return Object[]. Position 3 set to FALSE when not possible. 
 */    
    public static Object[] getColumnFromArray2D(Object[][] array, Integer colNum){
        Object[] diagnoses = new Object[0];
    String errorCode = "";
    String[] errorDetailVariables = new String[0];
        
        if (colNum>array[0].length){
           errorCode = "LabPLANETArray_getColumnFromArray2D_ColNotFound";
           errorDetailVariables = (String[]) addValueToArray1D(errorDetailVariables, array[0].length);
           errorDetailVariables = addValueToArray1D(errorDetailVariables, colNum.toString());
           return LPPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);           
        }       
        for (Integer i=0;i<array.length;i++){
            diagnoses=addValueToArray1D(diagnoses, array[colNum][i]);
        }
        
        return diagnoses;
    }

/**
 * Creates one array of Strings from the given object. 
 * 
 * {@link java.util.Arrays#copyOf}
 * @param obj Object
 * @return String[]
 */    
    public static String[] getStringArray(Object obj) {
        Object [] arrobj = (Object [])obj;
        String[] data = Arrays.copyOf(arrobj, arrobj.length, String[].class);
        return data;
    }    

/**
 * Converts one Object[] to String[]
 * 
 * {@link java.util.Arrays#copyOf}
     * @param objArray
 * @return String[]
 */    
    public static String[] convertObjectArrayToStringArray(Object[] objArray) {
        String[] strArray = new String[objArray.length];
        for (int i = 0; i < objArray.length; i++)
            strArray[i] = String.valueOf(objArray[i]);
        return strArray;
    }    

}




