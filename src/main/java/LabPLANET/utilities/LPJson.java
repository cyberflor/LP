/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

/**
 * LabPLANETJSon is a library of utilities for working with json objects
 * @author Fran Gomez
 */  

public class LPJson {
 /**
 * classVersion
 * {@value}
 */   
    String classVersion = "0.1";

    /**
     *
     * @param header
     * @param row
     * @return
     */
    public static JSONObject convertArrayRowToJSONObject(String[] header, Object[] row){
        JSONObject jObj = new JSONObject();    
        if (header.length==0){return jObj;}
        for (int iField=0; iField<header.length; iField++){     
            //if (!"sampling_date".equalsIgnoreCase(header[iField])){                            
                if (row[iField]==null){
                    jObj.put(header[iField], "");
                }else{
                    String clase = row[iField].getClass().toString();
                    if (clase.toUpperCase().equalsIgnoreCase("class java.sql.Date")){
                        jObj.put(header[iField], row[iField].toString());
                    }else{
                        jObj.put(header[iField], row[iField]);
                    }
                }
            //}
        }                    
        return jObj;
    }
    public static String convertToJSON(Object[] diagn) {
        StringBuilder jsonStr = new StringBuilder().append("{");
        
        for(int diagnItem = 0; diagnItem<diagn.length;diagnItem++){            
            jsonStr=jsonStr.append("diagn").append(diagnItem).append(":").append(diagn[diagnItem].toString());
        }
        jsonStr=jsonStr.append("}");
        return jsonStr.toString();
    }

    /**
     *
     * @param normalArray
     * @return
     */
    public static JSONArray convertToJSON(String[] normalArray) {
        JSONArray jsonArray= new JSONArray();
        jsonArray.addAll(Arrays.asList(normalArray));
        return jsonArray;
    }

    /**
     *
     * @param res
     * @return
     */
    public static String convertToJSON(ResultSet res) {
        try {
            String jsonarrayf;
            res.first();
            String finalString = "";
            
            while (!res.isAfterLast()){
                jsonarrayf = res.getString(1);

                if (jsonarrayf==null){jsonarrayf="";}
                    Integer numchars = jsonarrayf.length();

                    if (numchars>2){

                        String str1 = jsonarrayf.substring(2, numchars - 2);
                        String [] strarr0 = str1.split("[}],[{]");

                        Set<String> mySet = new HashSet(Arrays.asList(strarr0));
                        String[] strarr = Arrays.copyOf(mySet.toArray(), mySet.toArray().length, String[].class);

                        int index = 0;

                        while(index < strarr.length)
                        {
                            String fieldar = strarr[index];
                            String fieldarm = "";

                            if (!fieldar.startsWith("{")){
                                fieldarm = "{"+fieldar;
                            }

                            if (!fieldar.endsWith("}")){
                                fieldarm += "},";
                            }
                            fieldarm =   fieldar; 
                            fieldarm = fieldarm.replace("\\\"", "\"");

                            strarr[index] =  fieldarm;
                            finalString = finalString + "{"+fieldarm+"}";
                            if (index < strarr.length-1){finalString=finalString+",";}
                            index++;
                        }
                    }
                    res.next();
                }      
            finalString="["+finalString+"]";
            return finalString;
        } catch (SQLException ex) {
            Logger.getLogger(LPJson.class.getName()).log(Level.SEVERE, null, ex);
            return ex.getMessage();
        }
    }

    /**
     *
     * @param resultSet
     * @return
     */
    public static JSONArray convertToJSON2(ResultSet resultSet) {
        try {
            JSONArray jsonArray = new JSONArray();
            Integer totalLines = resultSet.getRow();
            Integer icurrLine = 0;   
            resultSet.first();
            
            while(icurrLine<=totalLines-1) {
                int totalRows = resultSet.getMetaData().getColumnCount();
                for (int i = 0; i < totalRows; i++) {
                    JSONObject obj = new JSONObject();
                        if (resultSet.getObject(i+1)==null){
                            obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                                    .toLowerCase(), "");                            
                        }else{
                        obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                                .toLowerCase(), resultSet.getObject(i + 1));
                        }
                    jsonArray.add(obj);
                }
                resultSet.next();
                icurrLine++;                
            }
            return jsonArray;
        } catch (SQLException ex) {
            Logger.getLogger(LPJson.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }    
/**
 * 
 * @param table String - tableName
 * @param fields String[] - fieldsToBeAdded to the json structure
 * @param fieldPrefix String - ???
 * @return - String[] - Contains the data in a json structure way.
 * @throws SQLException -
 */   
    public static String [] _getJsonArrayFields(String table, String[] fields,String fieldPrefix) {
        return table.split("\\.");
    }

    
}
