/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

import com.sun.rowset.CachedRowSetImpl;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.sql.DataSource;


/**
 *
 * @author Administrator
 */
public class _RdbmsNEW {

    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "user_method"; 
    
    String[] diagnoses = new String[7];    

    private Connection conn = null;
    private Boolean isStarted = false;
    private Integer timeout;
    private String lastError = "";

    public _RdbmsNEW() {
        //default query timeout
        this.timeout = 5;        
    }    


    
    public Boolean startRdbms(String user, String pass) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, NamingException{
        ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");
        String datasrc = prop.getString("datasource");
        Integer to = Integer.valueOf(prop.getString("dbtimeout"));
        setTimeout(to);
                
              Context ctx = new InitialContext();
              ctx = new InitialDirContext();
              DataSource ds = (DataSource)ctx.lookup(datasrc);
          
              ds.setLoginTimeout(getTimeout());
              setConnection(ds.getConnection(user, pass));
              
//              String url = prop.getString("dburl");
//              Properties props = new Properties();
//              
//                props.setProperty("user",user);
//                props.setProperty("password",pass);
//                props.setProperty("ssl","true");
//                Connection conn = DriverManager.getConnection(url, props);
              
          if(getConnection()!=null){
          setIsStarted(Boolean.TRUE);
          }
    
    return getIsStarted();
    }

    public void closeRdbms() throws SQLException{
        if(getConnection()!=null){
            conn.close();
            setIsStarted(Boolean.FALSE);
            }
    }  
    
    public Integer getTimeout() { return timeout;}

    public void setTimeout(Integer timeout) { this.timeout = timeout;}

    public String getLastError(){return lastError;}

    private void setConnection(Connection con){ conn=con; }
    
    public Connection getConnection(){ return conn; }

    public Boolean getIsStarted() { return isStarted;}
    
    private void setIsStarted(Boolean isStart) { this.isStarted = isStart;}

    public String[] existsRecord(_RdbmsNEW rdbm, String schemaName, String tableName, String keyFieldName, Object keyFieldValue){
        
        String[] diagnoses = new String[6];
        
        String query = "";
        query = "select " + keyFieldName + " from " + schemaName + "." + tableName
                + "   where " + keyFieldName + "=? ";
               
        try{
            ResultSet res;
            res = rdbm.prepRdQuery(query, new Object[] {keyFieldValue});
            res.last();

            if (res.getRow()>0){
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                diagnoses[1]= classVersion;
                diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
                diagnoses[3]="TRUE";
                diagnoses[4]="SUCCESS: " + tableName + " FOUND";
                diagnoses[5]="The " + tableName +": "+keyFieldValue+ " already exists in schema "+schemaName;
                return diagnoses;                
            }else{
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                diagnoses[1]= classVersion;
                diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
                diagnoses[3]="FALSE";
                diagnoses[4]="ERROR: " + tableName + " NOT FOUND";
                diagnoses[5]="The " + tableName +": "+keyFieldValue+ " does not exist in schema "+schemaName;
                return diagnoses;
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR:DB RETURNED ERROR";
            diagnoses[5]="The database returned error: "+ermessage+ " Query: "+query;
            return diagnoses;                
        }                    
    }

    public String[] existsRecord(_RdbmsNEW rdbm, String schemaName, String tableName, String[] keyFieldNames, Object[] keyFieldValues){
        
        String[] diagnoses = new String[6];
        
        Object[] filteredValues = new Object[0];
        
        if (keyFieldNames.length==0){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR: NO FILTER SPECIFIED";
            diagnoses[5]="Any filter is mandatory to run the query on " + tableName +" in schema "+schemaName;
            return diagnoses;     
        }
        
        String query = "";
        query = "select " + keyFieldNames[0] + " from " + schemaName + "." + tableName
                + "   where " ;
        
        for (Integer iFv=0;iFv<keyFieldValues.length;iFv++){
            if (iFv>0){query = query + " and ";}
            query = query + keyFieldNames[iFv];

            Boolean addToFilter = true;
            if (keyFieldValues[iFv].toString().equalsIgnoreCase("IN()")){addToFilter=false;}
            if (keyFieldValues[iFv].toString().equalsIgnoreCase("IS NULL")){addToFilter=false;}
            if (keyFieldValues[iFv].toString().equalsIgnoreCase("IS NOE NULL")){addToFilter=false;}
            if (addToFilter){
                filteredValues = labArr.addValueToArray1D(filteredValues, keyFieldValues[iFv]);
                query = query + "=?";
            }
        }        
        try{
            ResultSet res = rdbm.prepRdQuery(query, filteredValues);
            res.last();

            if (res.getRow()>0){
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                diagnoses[1]= classVersion;
                diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
                diagnoses[3]="TRUE";
                diagnoses[4]="SUCCESS: " + tableName + " FOUND";
                diagnoses[5]="Found " + res.getRow() + tableName + " where the query is "+query+" and the filter is "+ Arrays.toString(keyFieldValues) + " already exists in schema "+schemaName;
                return diagnoses;                
            }else{
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                diagnoses[1]= classVersion;
                diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
                diagnoses[3]="FALSE";
                diagnoses[4]="ERROR: " + tableName + " NOT FOUND";
                diagnoses[5]="No records founds in " + tableName + " where the query is "+query+" and the filter is "+ Arrays.toString(keyFieldValues) + " already exists in schema "+schemaName;
                return diagnoses;
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR:DB RETURNED ERROR";
            diagnoses[5]="The database returned error: "+ermessage+ " Query: "+query+" and the filter is "+ Arrays.toString(keyFieldValues);
            return diagnoses;                
        }                    
    }

    public Object[][] getRecordFieldsByFilter(_RdbmsNEW rdbm, String schemaName, String tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve){
        
        Object[][] diagnoses = new Object[1][6];        
        
        LabPLANETPlatform labPlat = new LabPLANETPlatform();   
        
        schemaName = labPlat.buildSchemaName(schemaName, "");
        
        if (whereFieldNames.length==0){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0][0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[0][1]= classVersion;
            diagnoses[0][2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[0][3]="FALSE";
            diagnoses[0][4]="ERROR: NO FILTER SPECIFIED";
            diagnoses[0][5]="Any filter is mandatory to run the query on " + tableName +" in schema "+schemaName;
            return diagnoses;     
        }
        
        String query = "";
        String fieldsToRetrieveStr = "";
        for (String fn: fieldsToRetrieve){fieldsToRetrieveStr = fieldsToRetrieveStr + fn + ", ";}
        fieldsToRetrieveStr = fieldsToRetrieveStr.substring(0, fieldsToRetrieveStr.length()-2);
        query = "select " + fieldsToRetrieveStr + " from " + schemaName + "." + tableName
                + "   where " ;
        Integer i=1;
        Boolean containsInClause = false;
        Object[] whereFieldValuesNew = new Object[0];
        
        for (String fn: whereFieldNames){
                if (i>1){query = query + " and ";}
                
                if ( fn.toUpperCase().contains("NULL")){ query = query + fn;}
                else if (fn.toUpperCase().contains("LIKE")){ query = query + fn + " ? ";} 
                else if (fn.toUpperCase().contains("IN")){ 
                    Integer posicINClause = fn.toUpperCase().indexOf("IN");
                    String separator = fn;
                    separator = separator.substring(posicINClause+2, posicINClause+3);
                    separator = separator.trim();
                    separator = separator.replace("IN", "");  
                    containsInClause = true;
                    String textSpecs = (String) whereFieldValues[i-1];
                    String[] textSpecArray = textSpecs.toString().split(separator);
                    query = query + fn.replace(separator, "") + "(" ;
                    for (Integer iNew=0;iNew<i-1;iNew++){
                        whereFieldValuesNew[iNew] = whereFieldValues[i];                        
                    }
                    for (int j = 0; j < textSpecArray.length; j++) {
						query = query + "?,";
                        whereFieldValuesNew = labArr.addValueToArray1D(whereFieldValuesNew, textSpecArray);                        
                        i++;
					}
                    for (Integer j=i;j<=whereFieldValues.length;j++){
                        whereFieldValuesNew = labArr.addValueToArray1D(whereFieldValuesNew, whereFieldValues[j]);  
                    }
                    query = query.substring(0, query.length()-1);
                    query = query + ")" ;
                    whereFieldValues = whereFieldValuesNew;
                }                 
                else {query = query + fn + "=? ";}
                
                i++;
        }        
        try{
            ResultSet res = null;
            if ( containsInClause ){
                res = rdbm.prepRdQuery(query, whereFieldValues);
                res.last();
            }else{
                res = rdbm.prepRdQuery(query, whereFieldValues);
                res.last();
            }
            

            if (res.getRow()>0){
             Integer totalLines = res.getRow();
             res.first();
             Integer icurrLine = 0;   
             
             Object[][] diagnoses2 = new Object[totalLines][fieldsToRetrieve.length];
             while(icurrLine<=totalLines-1) {
                //fieldValues = labArr.encryptTableFieldArray(schemaName, tableName, fieldNames, fieldValues);                 
                for (Integer icurrCol=0;icurrCol<fieldsToRetrieve.length;icurrCol++){
                    Object currValue = res.getObject(icurrCol+1);
                    diagnoses2[icurrLine][icurrCol] =  currValue;
                }        
                res.next();
                icurrLine++;
             }              
                diagnoses2 = labArr.decryptTableFieldArray(schemaName, tableName, fieldsToRetrieve, diagnoses2);
                return diagnoses2;
            }else{
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                diagnoses[0][0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                diagnoses[0][1]= classVersion;
                diagnoses[0][2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
                diagnoses[0][3]="FALSE";
                diagnoses[0][4]="ERROR: " + tableName + " NOT FOUND";
                diagnoses[0][5]="No records founds in " + tableName + " by the filter "+ Arrays.toString(whereFieldValues) + " in schema "+schemaName;
                return diagnoses;
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0][0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[0][1]= classVersion;
            diagnoses[0][2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[0][3]="FALSE";
            diagnoses[0][4]="ERROR:DB RETURNED ERROR";
            diagnoses[0][5]="The database returned error: "+ermessage+ " Query: "+query;
            return diagnoses;                
        }                    
    }

    public Object[][] getRecordFieldsByFilter(_RdbmsNEW rdbm, String schemaName, String[] tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve){
        
        Object[][] diagnoses = new Object[1][6];        
        
        if (whereFieldNames.length==0){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0][0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[0][1]= classVersion;
            diagnoses[0][2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[0][3]="FALSE";
            diagnoses[0][4]="ERROR: NO FILTER SPECIFIED";
            diagnoses[0][5]="Any filter is mandatory to run the query on " + tableName +" in schema "+schemaName;
            return diagnoses;     
        }
        
        String query = "";
        String fieldsToRetrieveStr = "";
        for (String fn: fieldsToRetrieve){fieldsToRetrieveStr = fieldsToRetrieveStr + fn + ", ";}
        fieldsToRetrieveStr = fieldsToRetrieveStr.substring(0, fieldsToRetrieveStr.length()-2);
        query = "select " + fieldsToRetrieveStr + " from ";
        Integer i=1;
        for (String tbl: tableName){
            if (i>1){query = query + " , ";}
            query = query + " " + schemaName + "." + tbl;
            i++;
        }    
        query = query + "   where " ;
        i=1;
        for (String fn: whereFieldNames){
                if (i>1){query = query + " and ";}
                
                if ( (fn.toUpperCase().contains("NULL")) || (fn.toUpperCase().contains("LIKE")) ){
                    query = query + fn;
                }else {query = query + fn + "=? ";}
                
                i++;
        }        
        try{
            ResultSet res = rdbm.prepRdQuery(query, whereFieldValues);
            res.last();

            if (res.getRow()>0){
             Integer totalLines = res.getRow();
             res.first();
             Integer icurrLine = 0;   
             
             Object[][] diagnoses2 = new Object[totalLines][fieldsToRetrieve.length];
             while(icurrLine<=totalLines-1) {
                for (Integer icurrCol=0;icurrCol<fieldsToRetrieve.length;icurrCol++){
                    Object currValue = res.getObject(icurrCol+1);
                    diagnoses2[icurrLine][icurrCol] =  currValue;
                }        
                res.next();
                icurrLine++;
             }
                diagnoses2 = labArr.decryptTableFieldArray(schemaName, tableName[0], fieldsToRetrieve, diagnoses2);
                return diagnoses2;
            }else{
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                diagnoses[0][0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                diagnoses[0][1]= classVersion;
                diagnoses[0][2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
                diagnoses[0][3]="FALSE";
                diagnoses[0][4]="ERROR: " + tableName + " NOT FOUND";
                diagnoses[0][5]="No records founds in " + tableName + " by the filter "+ Arrays.toString(whereFieldValues) + " in schema "+schemaName;
                return diagnoses;
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0][0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[0][1]= classVersion;
            diagnoses[0][2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[0][3]="FALSE";
            diagnoses[0][4]="ERROR:DB RETURNED ERROR";
            diagnoses[0][5]="The database returned error: "+ermessage+ " Query: "+query;
            return diagnoses;                
        }                    
    }

    public Object[][] getRecordFieldsByFilter(_RdbmsNEW rdbm, String schemaName, String tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve, String[] orderBy){
        
        Object[][] diagnoses = new Object[1][6];        
        
        if (whereFieldNames.length==0){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0][0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[0][1]= classVersion;
            diagnoses[0][2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[0][3]="FALSE";
            diagnoses[0][4]="ERROR: NO FILTER SPECIFIED";
            diagnoses[0][5]="Any filter is mandatory to run the query on " + tableName +" in schema "+schemaName;
            return diagnoses;     
        }
        
        String query = "";
        String fieldsToRetrieveStr = "";
        for (String fn: fieldsToRetrieve){fieldsToRetrieveStr = fieldsToRetrieveStr + fn + ", ";}
        fieldsToRetrieveStr = fieldsToRetrieveStr.substring(0, fieldsToRetrieveStr.length()-2);
        query = "select " + fieldsToRetrieveStr + " from " + schemaName + "." + tableName
                + "   where " ;
        Integer i=1;
        for (String fn: whereFieldNames){
                if (i>1){query = query + " and ";}
                
                if ( (fn.toUpperCase().contains("NULL")) || (fn.toUpperCase().contains("LIKE")) ){
                    query = query + fn;
                }else {query = query + fn + "=? ";}
                
                i++;
        }    
        i=1;
        query = query + " order by ";
        for (String sortFld: orderBy){
                if (i>1){query = query + ", ";}                
                query = query + sortFld;               
                i++;
        }         
        try{
            ResultSet res = rdbm.prepRdQuery(query, whereFieldValues);
            res.last();

            if (res.getRow()>0){
             Integer totalLines = res.getRow();
             res.first();
             Integer icurrLine = 0;   
             
             Object[][] diagnoses2 = new Object[totalLines][fieldsToRetrieve.length];
             while(icurrLine<=totalLines-1) {
                for (Integer icurrCol=0;icurrCol<fieldsToRetrieve.length;icurrCol++){
                    Object currValue = res.getObject(icurrCol+1);
                    diagnoses2[icurrLine][icurrCol] =  currValue;
                }        
                res.next();
                icurrLine++;
             }
                diagnoses2 = labArr.decryptTableFieldArray(schemaName, tableName, fieldsToRetrieve, diagnoses2);
                return diagnoses2;
            }else{
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                diagnoses[0][0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                diagnoses[0][1]= classVersion;
                diagnoses[0][2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
                diagnoses[0][3]="FALSE";
                diagnoses[0][4]="ERROR: " + tableName + " NOT FOUND";
                diagnoses[0][5]="No records founds in " + tableName + " by the filter "+ Arrays.toString(whereFieldValues) + " in schema "+schemaName;
                return diagnoses;
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0][0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[0][1]= classVersion;
            diagnoses[0][2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[0][3]="FALSE";
            diagnoses[0][4]="ERROR:DB RETURNED ERROR";
            diagnoses[0][5]="The database returned error: "+ermessage+ " Query: "+query;
            return diagnoses;                
        }                    
    }

    public String[] insertRecordInTable(_RdbmsNEW rdbm, String schemaName, String tableName, String[] fieldNames, Object[] fieldValues){
        try {
            
            // fieldValues = labArr.encryptTableFieldArray(schemaName, tableName, fieldNames, fieldValues);
            
            String[] diagnoses = new String[7];
            
            if (fieldNames.length==0){
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                diagnoses[1]= classVersion;
                diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
                diagnoses[3]="FALSE";
                diagnoses[4]="ERROR: NO FILTER SPECIFIED";
                diagnoses[5]="Any filter is mandatory to run the query on " + tableName +" in schema "+schemaName;
                return diagnoses;
            }
            
            if (fieldNames.length!=fieldValues.length){
                StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                diagnoses[1]= classVersion;
                diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
                diagnoses[3]="FALSE";
                diagnoses[4]="ERROR:Field names and values arrays with different length";
                diagnoses[5]="The values in FieldName are:"+ Arrays.toString(fieldNames)+". and in FieldValue are:"+Arrays.toString(fieldValues);
                return diagnoses;
            }
            String query = "";
            String fieldNamesStr = "";
            for (String fn: fieldNames){fieldNamesStr = fieldNamesStr + fn + ", ";}
            fieldNamesStr = fieldNamesStr.substring(0, fieldNamesStr.length()-2);
            query = "Insert into " + schemaName + "." + tableName + " (" + fieldNamesStr + ") values ( " ;
            Integer i=1;
            for (int j = 0; j < fieldNames.length; j++) {
				if (i==1){query = query + "? ";i++;}
                else{query = query + ", ? ";}
                i++;
			}
            query = query + ") ";
                        
            int numr = rdbm.prepUpQueryK(query, fieldValues, 1);
            
            //ResultSet res = rdbm.prepRdQuery(query, fieldValues);
            //res.last();
            //if (numr>0){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="TRUE";
            diagnoses[4]="SUCCESS: RECORD CREATED";
            diagnoses[5]="New record created using the query "+ query+" for values "+Arrays.toString(fieldValues) + " in schema " + schemaName ;
            diagnoses[6]=String.valueOf(numr);
            return diagnoses;

        } catch (SQLException ex) {
            Logger.getLogger(_RdbmsNEW.class.getName()).log(Level.SEVERE, null, ex);
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR:DATABASE RETURNS ERROR";
            diagnoses[5]="The database returned this error on execute the SQL statement:"+ex.getMessage()+" The query is "+ex.getSQLState()+". and in FieldValue are:"+Arrays.toString(fieldValues);
            return diagnoses;            
        }
    }
    
    public String[] updateRecordFieldsByFilter(_RdbmsNEW rdbm, String schemaName, String tableName, String[] updateFieldNames, Object[] updateFieldValues, String[] whereFieldNames, Object[] whereFieldValues) throws SQLException{
        
        String[] diagnoses = new String[6];        
        
        updateFieldValues = labArr.decryptTableFieldArray(schemaName, tableName, updateFieldNames, (Object[]) updateFieldValues);        
        
        if (whereFieldNames.length==0){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR: NO FILTER SPECIFIED";
            diagnoses[5]="Any filter is mandatory to run the query on " + tableName +" in schema "+schemaName;
            return diagnoses;     
        }
        
        String query = "";
        String updateFieldNamesStr = " set ";
        for (String fn: updateFieldNames){updateFieldNamesStr = updateFieldNamesStr + fn + "=?, ";}
        updateFieldNamesStr = updateFieldNamesStr.substring(0, updateFieldNamesStr.length()-2);
        query = "update " + schemaName + "." + tableName + updateFieldNamesStr
                + "   where " ;
        Integer i=1;
        for (String fn: whereFieldNames){
                if (i==1){query = query + fn + "=? ";i++;}
                else{query = query + " and "+ fn + "=? ";}
        }       i++;
        for (Object fn: whereFieldValues){
            updateFieldValues = labArr.addValueToArray1D(updateFieldValues, fn);}

        updateFieldValues = labArr.encryptTableFieldArray(schemaName, tableName, updateFieldNames, (Object[]) updateFieldValues); 
        
        Integer numr = rdbm.prepUpQuery(query, updateFieldValues);
        if (numr>0){     
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[3]="TRUE";
            diagnoses[4]="SUCCESS: " + tableName + " RECORD UPDATED";
            diagnoses[5]="Updated " + tableName + " records by the filter "+ Arrays.toString(whereFieldValues) + " already exists in schema "+schemaName;
        }else{
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR: " + tableName + " NOT FOUND";
            diagnoses[5]="No records founds in " + tableName + " by the filter "+ Arrays.toString(whereFieldValues) + " already exists in schema "+schemaName+". Query="+query;
        }
        return diagnoses;                    
        
        /*
        res.last();
        
        if (res.getRow()>0){
        Integer totalLines = res.getRow();
        res.first();
        Integer icurrLine = 0;
        
        Object[][] diagnoses2 = new Object[res.getRow()][updateFieldNames.length];
        while(icurrLine<totalLines-1) {
        for (Integer icurrCol=0;icurrCol<updateFieldNames.length;icurrCol++){
        diagnoses2[icurrLine][icurrCol] =  (Object) res.getObject(icurrCol+1);
        }
        res.next();
        icurrLine++;
        }
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
        diagnoses[1]= classVersion;
        diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
        diagnoses[3]="FALSE";
        diagnoses[4]="ERROR: " + tableName + " NOT FOUND";
        diagnoses[5]="No records founds in " + tableName + " by the filter "+ whereFieldValues.toString() + " already exists in schema "+schemaName;
        
        return diagnoses;
        }else{
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
        diagnoses[1]= classVersion;
        diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
        diagnoses[3]="FALSE";
        diagnoses[4]="ERROR: " + tableName + " NOT FOUND";
        diagnoses[5]="No records founds in " + tableName + " by the filter "+ whereFieldValues.toString() + " already exists in schema "+schemaName;
        return diagnoses;
        }*/
    }    

    public CachedRowSetImpl prepRdQuery(String consultaconinterrogaciones, Object [] valoresinterrogaciones) throws SQLException{
    //prepare statement para evitar sql injection
     Object[] filteredValoresConInterrogaciones = new Object[0];     
     
     PreparedStatement prep=getConnection().prepareStatement(consultaconinterrogaciones);
        prep.setQueryTimeout(getTimeout());
        
        try{
            for (Integer i=0;i<valoresinterrogaciones.length;i++){
                Boolean addToFilter = true;
                if (valoresinterrogaciones[i].toString().equalsIgnoreCase("IN()")){addToFilter=false;}
                if (valoresinterrogaciones[i].toString().equalsIgnoreCase("IS NULL")){addToFilter=false;}
                if (valoresinterrogaciones[i].toString().equalsIgnoreCase("IS NOE NULL")){addToFilter=false;}
                if (addToFilter){
                    filteredValoresConInterrogaciones = labArr.addValueToArray1D(filteredValoresConInterrogaciones, valoresinterrogaciones[i]);}
            }
                
            buildPreparedStatement(filteredValoresConInterrogaciones, prep, null); 
        }catch(Exception er){//cuando se envia un array 

        }
        
    ResultSet res = prep.executeQuery();
    CachedRowSetImpl crs = new CachedRowSetImpl();
    crs.populate(res);
    
    return crs; 
    }
    

    public Integer prepUpQuery(String consultaconinterrogaciones, Object [] valoresinterrogaciones) throws SQLException{
        Integer reg =  prepUpQuery(consultaconinterrogaciones, valoresinterrogaciones, null);    
        return reg;
    }
    
    public Integer prepUpQuery(String consultaconinterrogaciones, Object [] valoresinterrogaciones, Integer [] fieldtypes) throws SQLException{
        PreparedStatement prep=getConnection().prepareStatement(consultaconinterrogaciones);

        setTimeout(getTimeout());

        if (valoresinterrogaciones != null){
            buildPreparedStatement(valoresinterrogaciones, prep, fieldtypes); 
        }

        Integer res=prep.executeUpdate();
        return res; 
    }
    
    public Integer prepUpQueryK(String consultaconinterrogaciones, Object [] valoresinterrogaciones, Integer indexposition) throws SQLException{
        Integer pk = 0;
        PreparedStatement prep=getConnection().prepareStatement(consultaconinterrogaciones, Statement.RETURN_GENERATED_KEYS);

        setTimeout(getTimeout());

        buildPreparedStatement(valoresinterrogaciones, prep, null); 
        ResultSet rs = prep.getGeneratedKeys();

        if (rs.next()) {
          int newId = rs.getInt(indexposition);
          pk = newId;
        }

        return pk; 
    }
    
    public String [] getTableFieldsArrayEj(String schema, String table) throws SQLException{
        String sq = "select array(SELECT column_name || ''  FROM information_schema.columns WHERE table_schema = ? AND table_name   = ?) fields";
        CachedRowSetImpl res = prepRdQuery(sq, new Object[]{schema, table});
        
        String [] items = res.next() ? LabPLANETArray.getStringArray(res.getArray("fields").getArray()) : null ;
        
        return items;
    }
    
    public String getTableFieldsArrayEj(String schema, String table, String separator, Boolean addTableName) throws SQLException{
        String sq = "select array(SELECT column_name || ''  FROM information_schema.columns WHERE table_schema = ? AND table_name   = ?) fields";
        CachedRowSetImpl res = prepRdQuery(sq, new Object[]{schema, table});
        
        String [] items = res.next() ? LabPLANETArray.getStringArray(res.getArray("fields").getArray()) : null ;
        
        String tableFields = "";
        
        for (String f: items){
            if (tableFields.length()>0){tableFields=tableFields+separator;}
            if (addTableName){tableFields = tableFields+table+"."+f;            
            }else{tableFields = tableFields+f;}
        }
        return tableFields;
    }
        
    private void buildPreparedStatement(Object [] valoStrings, PreparedStatement prepsta, Integer [] fieldtypes) throws SQLException{
        Integer numfields = valoStrings.length;
        Integer indexval = 1;

        for(Integer numi=0;numi<numfields;numi++){
             Object obj = valoStrings[numi];

             String clase;

             if (obj != null){
             clase = obj.getClass().toString();
             }else{
             clase = "null";    
             }

               switch(clase){
               case "class java.lang.Integer":
               prepsta.setInt(indexval, (Integer)obj);
               break;
               case "class java.lang.Boolean":
               prepsta.setBoolean(indexval, (Boolean)obj);
               break;
               case "class java.sql.Date":
               prepsta.setDate(indexval, (java.sql.Date) obj);
               break;
               case "null":
               prepsta.setNull(indexval, fieldtypes[numi]);
               break; 
               case "class json.Na"://to skip fields
               break;  
               case "class [Ljava.lang.String;":
               Array array = conn.createArrayOf("VARCHAR", (Object []) obj);
               prepsta.setArray(indexval, array);
               break;
               default:
               prepsta.setString(indexval, (String) obj);
               break; 
           }
           
           if (!clase.equals("class json.Na")){
               indexval++;
           }
        }     
    }

    public ResourceBundle getParameterBundle(String configFile){
        ResourceBundle prop = ResourceBundle.getBundle("parameter.config."+configFile);
        return prop;
    }
    
    public String getParameterBundle(String configFile, String parameterName, String language){
        
        ResourceBundle prop = ResourceBundle.getBundle("parameter.config."+configFile+"_"+language); 
        if (!prop.containsKey(parameterName)){  
            return "";
        }else{    
            String paramValue = prop.getString(parameterName);
            return paramValue;
        }    
    }        

    public String getParameterBundle(String configFile, String parameterName){
        

        try {
            ResourceBundle prop = ResourceBundle.getBundle("parameter.config."+configFile); 
            if (!prop.containsKey(parameterName)){  
                return "";
            }else{    
                String paramValue = prop.getString(parameterName);
                return paramValue;
            }
        }catch (Exception e){    
            return "";
        }    
    }        

    public Date getLocalDate(){
        Date de = new java.sql.Date(System.currentTimeMillis());        
        return de;}

    public Date getCurrentDate(){        
        Date de = new java.sql.Date(System.currentTimeMillis());        
        return de;}    
    
    public CachedRowSetImpl readQuery(String consulta) throws SQLException{
    Statement sta=getConnection().createStatement();
    sta.setQueryTimeout(getTimeout());
    
    ResultSet res=null;
            
        if(!"".equals(consulta)){
        res = sta.executeQuery(consulta);    
        }
        
    CachedRowSetImpl crs = new CachedRowSetImpl();
    crs.populate(res);
    
    return crs;    
    }
    
}
