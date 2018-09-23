/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import databases.Rdbms;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * LabPLANETArray is a library for methods for building and modeling 1D and 2D arrays.
 * @author Fran Gomez
 * @version 0.1
 */

public class LabPLANETArray {
    
    String classVersion = "0.1";
    String schemaDataName = "data";
    String schemaConfigName = "config";    
    String errorCode = "";
    String[] errorDetailVariables = new String[0];
//    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    Rdbms rdbm = new Rdbms();           
    
    public boolean duplicates(String[] zipcodelist){
      Set<String> lump = new HashSet<>();
      for (String i : zipcodelist)
      {
        if (lump.contains(i)) return true;
        lump.add(i);
      }
      return false;
    }    
    
    
    public boolean duplicates(final int[] zipcodelist){
      Set<Integer> lump = new HashSet<>();
      for (int i : zipcodelist)
      {
        if (lump.contains(i)) return true;
        lump.add(i);
      }
      return false;
    }    

    public Object[] encryptTableFieldArray(String schemaName, String tableName, String[] fieldName, Object[] fieldValue){
        String key = "Bar12345Bar12345"; // 128 bit key
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        schemaDataName = labPlat.buildSchemaName(schemaName, schemaDataName);  
        Rdbms rdbm = new Rdbms();
        String fieldsEncrypted = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "encrypted_"+tableName);
        String[] fieldsEncryptedArr = fieldsEncrypted.split("\\|");
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
                catch(InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){}
            }
        }
        
        return fieldValue;        
    }  
    
    public Object[][] decryptTableFieldArray(String schemaName, String tableName, String[] fieldName, Object[][] fieldValue){
        String key = "Bar12345Bar12345"; // 128 bit key
        //LabPLANETPlatform labPlat = new LabPLANETPlatform();
        //schemaDataName = labPlat.buildSchemaName(schemaName, schemaDataName);  
        Rdbms rdbm = new Rdbms();
        String fieldsEncrypted = rdbm.getParameterBundle(schemaName.replace("\"", ""), "encrypted_"+tableName);
        String[] fieldsEncryptedArr = fieldsEncrypted.split("\\|");
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
                            System.err.println("decrypted:" + decrypted);
                            fieldValue1[iFields] = decrypted;
                        }    
                    }        
        }
                catch(InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){}
            }
        }
        
        return fieldValue;        
    }    

    public Object[] decryptTableFieldArray(String schemaName, String tableName, String[] fieldName, Object[] fieldValue){
        String key = "Bar12345Bar12345"; // 128 bit key
        //LabPLANETPlatform labPlat = new LabPLANETPlatform();
        //schemaDataName = labPlat.buildSchemaName(schemaName, schemaDataName);  
        Rdbms rdbm = new Rdbms();
        String fieldsEncrypted = rdbm.getParameterBundle(schemaName.replace("\"", ""), "encrypted_"+tableName);
        String[] fieldsEncryptedArr = fieldsEncrypted.split("\\|");
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
                    System.err.println("decrypted:" + decrypted);   
                    fieldValue[iFields] = decrypted;
                }
                catch(InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e){}
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
    public Object[] convertStringWithDataTypeToObjectArray(String[] myStringsArray){
        
        Object[] myObjectsArray = new Object[myStringsArray.length];
        
        for (Integer i=0;i<myStringsArray.length;i++){
            String[] rowParse = myStringsArray[i].split("\\*");
            if (rowParse.length!=2){
                myObjectsArray[i]=myStringsArray[i];
            }else{
                switch (rowParse[1].toUpperCase()){                                    
                    case "STRING":
                        myObjectsArray[i]=(String) rowParse[0];
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
                    default:
                        myObjectsArray[i]=(String) rowParse[0];
                        break;
                }        
            }                
        }        
        return myObjectsArray;
        
    }
    public String[][] convertCSVinArray(String xfileLocation, String csvSeparator){
        String[][] myArray = new String[0][0];
        String[] myArray1D = new String[0];
        Scanner scanIn = null;
        int rowc = 0; int row = 0;   int colc = 0; int col = 0; double xnum = 0; Integer columnsInCsv=0;
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
            myArray = new String[numLines][columnsInCsv];    
            for (Integer inumLines=0;inumLines<numLines;inumLines++){                
                String[] inArray = myArray1D[inumLines].split(String.valueOf(csvSeparator));
                System.arraycopy(inArray, 0, myArray[inumLines], 0, inArray.length);
            }            
//            myArray = array1dTo2d(myArray1D, columnsInCsv);
            return myArray;
            
        } catch (FileNotFoundException e){ 
            System.out.println(e);}
        
        return myArray;
    }
    
    public String[][] convertCSVinArrayHomogeneous(String xfileLocation, String csvSeparator){
        String[][] myArray = new String[0][0];
        String[] myArray1D = new String[0];
        Scanner scanIn = null;
        int rowc = 0; int row = 0;   int colc = 0; int col = 0; double xnum = 0; Integer columnsInCsv=0;
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
            myArray = array1dTo2d(myArray1D, columnsInCsv);
            return myArray;
            
        } catch (FileNotFoundException e){ 
            System.out.println(e);}
        
        return myArray;
    }
    
/**
 * Converts one 2-Dimensional in a 1-Dimensional array
 * @param array2d String[][]
 * @return String[]
 */
    public String[] array2dTo1d(String[][] array2d){
        //Object[] array1d = new Object[1];
        //Integer numLines = 
        //String[][] my2Darr = .....(something)......
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
    public Object[] array2dTo1d(Object[][] array2d){
        //Object[] array1d = new Object[1];
        //Integer numLines = 
        //String[][] my2Darr = .....(something)......
        List<Object> list;
        list = new ArrayList<>();
        for (Object[] array2d1 : array2d) {
            list.addAll(Arrays.asList(array2d1)); // java.util.Arrays
        }
        String[] array1d = new String[list.size()];
        array1d = list.toArray(array1d);
        
       return array1d;
    }
    
/**
 * Converts one 2-Dimensional in a 1-Dimensional array just extracting the values from the given column
 * @param array2d Object[][]
 * @param colNum Integer
 * @return Object[]
 */
    public Object[] array2dTo1d(Object[][] array2d, Integer colNum){
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
    public String[][] array1dTo2d(String[] array1d, Integer numColumns){
        
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
    public Object[][] array1dTo2d(Object[] array1d, Integer numColumns){
        
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
    public boolean valueInArray(Object[] array, Object value){
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
    public Integer valuePosicInArray(Object[] array, Object value){        
        boolean diagnoses = false;
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
    public Object[] addValueToArray1D(Object[] array, Object newValue){
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
    public Calendar[] addCalendarValueToCalendarArray1D(Calendar[] array, Calendar newValue){
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
    public Object[] addValueToArray1D(Object[] array, Object[] newValues){
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
    public String[] addValueToArray1D(String[] array, String[] newValues){
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
    public String[] addValueToArray1D(String[] array, String newValue){
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
    public Object[][] setColumnValueToArray2D(Object[][] array, Integer col, Object newValue){
        
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
    public Object[][] addColumnToArray2D(Object[][] array, Object newValue){
        
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
    public String[] joinTwo1DArraysInOneOf1DString (Object[] arrayOne, Object[] arrayTwo, String separator){
        String[] newArray = new String[0];
        
        Integer arrOneLength = arrayOne.length;
        Integer arrTwoLength = arrayTwo.length;
        Integer arrLength = arrayOne.length;
        if (arrayTwo.length>arrLength){arrLength=arrayTwo.length;}
        
        String currValueA ="";
        String currValueB ="";
        
        for (Integer iarrLength = 0;iarrLength<arrLength;iarrLength++){
            
            if (arrOneLength < iarrLength) {currValueA ="";}
            else{currValueA = arrayOne[iarrLength].toString();}
            
            if (arrTwoLength < iarrLength) {currValueB ="";}
            else{currValueB = arrayTwo[iarrLength].toString();}
            
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
    public String[] checkTwoArraysSameLength(Object[] arrayA, Object[] arrayB){
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        String[] diagnoses = new String[6];
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName() + " called from " + elements[2].getMethodName();
        if (arrayA.length!=arrayB.length){
           errorCode = "DataSample_FieldArraysDifferentSize";
           errorDetailVariables = addValueToArray1D(errorDetailVariables, Arrays.toString(arrayA));
           errorDetailVariables = addValueToArray1D(errorDetailVariables, Arrays.toString(arrayB));
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);           
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
    public Object[] getColumnFromArray2D(Object[][] array, Integer colNum){
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        Object[] diagnoses = new Object[0];
        
        if (colNum>array[0].length){
           errorCode = "LabPLANETArray_getColumnFromArray2D_ColNotFound";
           errorDetailVariables = (String[]) addValueToArray1D(errorDetailVariables, array[0].length);
           errorDetailVariables = addValueToArray1D(errorDetailVariables, colNum.toString());
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);           
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
}
