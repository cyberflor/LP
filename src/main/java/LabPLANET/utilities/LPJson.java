/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import java.util.Arrays;
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

   


    
}
