/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import com.sun.rowset.CachedRowSetImpl;
import databases.Rdbms;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * LabPLANETJSon is a library of utilities for working with json objects
 * @author Fran Gomez
 */  

public class LabPLANETJson {
 /**
 * classVersion
 * {@value}
 */   
    String classVersion = "0.1";
      
/**
 * 
 * @param rdbm Rdbms - Pass the current db connection for running queries
 * @param table String - tableName
 * @param fields String[] - fieldsToBeAdded to the json structure
 * @param fieldPrefix String - ???
 * @return - String[] - Contains the data in a json structure way.
 * @throws SQLException -
 */   
    public String [] _getJsonArrayFields(Rdbms rdbm, String table, String[] fields,String fieldPrefix) throws SQLException {
        String [] itt = table.split("\\.");
        String tbl = itt[1];

       String tableColumns = ""; //" and column_name = 'description' ";
       
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
