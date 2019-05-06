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
//import org.json.simple.;

//import org.glassfish.jersey.jackson.internal.FilteringJacksonJaxbJsonProvider;
//import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;

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
     * @param diagn
     * @return
     */
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
        for (String normalArray1 : normalArray) {
            jsonArray.add(normalArray1);
            //jsonArray.put(normalArray1);
        }
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
                    //try {
                        if (resultSet.getObject(i+1)==null){
                            obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                                    .toLowerCase(), "");                            
                        }else{
                        obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                                .toLowerCase(), resultSet.getObject(i + 1));
                        }
                    /*} catch (JSONException ex) {
                        Logger.getLogger(LPJson.class.getName()).log(Level.SEVERE, null, ex);
                        try {
                            obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                                    .toLowerCase(), "");
                        } catch (JSONException ex1) {
                            Logger.getLogger(LPJson.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }*/
                    jsonArray.add(obj);
                    //jsonArray.put(obj);
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
    public static String [] _getJsonArrayFields(String table, String[] fields,String fieldPrefix) throws SQLException {
        String [] itt = table.split("\\.");
//       String tableColumns = ""; //" and column_name = 'description' ";      
        //Convert this method by using the official Rdbms for queries
        return itt;       
    }
/*       
       if (fields!=null)
       {   
           tableColumns = " and column_name in ( ";
            for (String field : fields) {
                tableColumns = tableColumns + "'" + field + "',";
            }            
           tableColumns = tableColumns.substring(1, tableColumns.length()-1) + " ) ";
       }


        String q = "select array_to_json(array_agg(row_to_json(t))) jsonr from (select distinct       " +
       " CASE WHEN data_type='character varing' or data_type='character' or data_type='char' THEN 'textfield' " +
       " WHEN data_type='boolean' THEN 'checkboxfield' " +
       " WHEN data_type='integer' or data_type='smallint' or data_type='bigint' or data_type='decimal' or data_type='numeric' or data_type='real' or data_type='double precision' or data_type='smallserial' or data_type='serial' or data_type='bigserial' THEN 'numberfield' " +
       " WHEN data_type='date' or data_type='timestamp' THEN 'datefield' " +
       " WHEN data_type='text' THEN 'htmleditor' ELSE 'textfield' END as xtype " +
       ", column_name \"name\", 'gTr(''paramView'',''" + fieldPrefix +  "_' || column_name || ''')' \"fieldLabel\" from INFORMATION_SCHEMA.COLUMNS where table_name = '"+tbl+"' " + 
       tableColumns + 
       ") t;";    

       CachedRowSetImpl res ;
        res = rdbm.readQuery(q);
       String jsonarrayf;
        jsonarrayf = null;
       String obj [] = null;

       if (res.next()){
           jsonarrayf = res.getString(1);

           Integer numchars = jsonarrayf.length();
           String [] objarr = null;

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
                   fieldarm += "}";
               }

               fieldarm = fieldarm.replace("\"", "'");   
               fieldarm = fieldarm.replace("'gTr(", "gTr(");   
               fieldarm = fieldarm.replace(")'", ")");

           strarr[index] =  fieldarm;
           index++;
           }

           objarr = strarr;
           }

           obj = objarr;
       }

       return obj;
    }
*/

    
}
