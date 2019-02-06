/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

import com.sun.rowset.CachedRowSetImpl;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETJson;
import LabPLANET.utilities.LabPLANETPlatform;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class Rdbms {
    String classVersion = "0.1";
    String errorCode = "";

    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "user_method"; 
    

    private Connection conn = null;
    private Boolean isStarted = false;
    private Integer timeout;
    private String lastError = "";
    Integer transactionId = 0;
    String savepointName;
    Savepoint savepoint=null;
    
   
    /**
     *
     * @param user
     * @param pass
     * @return
     */
    public Boolean startRdbms(String user, String pass) {
        try {        
            ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");
            String datasrc = prop.getString("datasource");
            Integer to = Integer.valueOf(prop.getString("dbtimeout"));
            setTimeout(to);

                  Context ctx = new InitialContext();
                  DataSource ds = (DataSource)ctx.lookup(datasrc);

                    try {
                        ds.setLoginTimeout(getTimeout());
                        setConnection(ds.getConnection(user, pass));
                    } catch (SQLException ex) {
                        Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);                        
                    }

    //              String url = prop.getString("dburl");
    //              Properties props = new Properties();
    //              
    //                props.setProperty("user",user);
    //                props.setProperty("password",pass);
    //                props.setProperty("ssl","true");
    //                Connection conn = DriverManager.getConnection(url, props);

              if(getConnection()!=null){
                setIsStarted(Boolean.TRUE);                                                      
                return true;
              }else{
                setIsStarted(Boolean.FALSE);
                return false;
              } 
        } catch (NamingException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    //return getIsStarted();
    }
   
    public void setTransactionId(String schemaName){
        
        schemaName = LabPLANETPlatform.buildSchemaName(schemaName, "");
        String qry = "select nextval('"+ schemaName + ".transaction_id')";
        Integer transactionIdNextVal = prepUpQuery(qry, null);
        
        this.transactionId = transactionIdNextVal;
        
    }
    
    public Integer getTransactionId(){
        return this.transactionId;
    }
            
    /**
     *
     */
    public void closeRdbms(){       
        if(getConnection()!=null){
            try{                
                conn.close();
                setIsStarted(Boolean.FALSE);
            } catch (SQLException ex) {
                Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);                
            }
        }
    }  
    
    /**
     *
     * @return
     */
    public Integer getTimeout() { return timeout;}

    /**
     *
     * @param timeout
     */
    public void setTimeout(Integer timeout) { this.timeout = timeout;}

    private void setLastError(String txterror){ lastError = txterror;}
    
    /**
     *
     * @return
     */
    public String getLastError(){return lastError;}

    private void setConnection(Connection con){ conn=con; }
    
    /**
     *
     * @return
     */
    public Connection getConnection(){ return conn; }

    /**
     *
     * @return
     */
    public Boolean getIsStarted() { return isStarted;}
    
    private void setIsStarted(Boolean isStart) { this.isStarted = isStart;}
    
    /**
     *
     * @param rdbm
     * @param schemaName
     * @param tableName
     * @param keyFieldName
     * @param keyFieldValue
     * @return
     */
    public Object[] zzzexistsRecord(Rdbms rdbm, String schemaName, String tableName, String[] keyFieldName, Object keyFieldValue){
        
        String[] diagnoses = new String[6];
        LabPLANETArray labArr = new LabPLANETArray();        
        String[] errorDetailVariables = new String[0];
        SqlStatement sql = new SqlStatement();        
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement("SELECT", schemaName, tableName,
                keyFieldName, null, keyFieldName,  null, null,  null, null);          
            String query= hmQuery.keySet().iterator().next();   
            Object[] keyFieldValueNew = hmQuery.get(query);
        try{
            ResultSet res;
            res = rdbm.prepRdQuery(query, keyFieldValueNew);
            res.last();

            if (res.getRow()>0){
                errorCode = "Rbdms_existsRecord_RecordFound";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, keyFieldValue.toString());
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
                return (String[]) LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);                
            }else{
                errorCode = "Rbdms_existsRecord_RecordNotFound";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, keyFieldValue.toString());
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
                return (String[]) LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, query);
            return (Object[]) LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                                       
        }                    
    }

    /**
     *
     * @param rdbm
     * @param schemaName
     * @param tableName
     * @param keyFieldNames
     * @param keyFieldValues
     * @return
     */
    public Object[] existsRecord(Rdbms rdbm, String schemaName, String tableName, String[] keyFieldNames, Object[] keyFieldValues){
        
        String[] diagnoses = new String[6];
        LabPLANETArray labArr = new LabPLANETArray();       
        String[] errorDetailVariables = new String[0];
        
        Object[] filteredValues = new Object[0];
        
        if (keyFieldNames.length==0){
           errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
           return (Object[]) LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
        }
        SqlStatement sql = new SqlStatement(); 
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement("SELECT", schemaName, tableName,
                keyFieldNames, keyFieldValues, keyFieldNames,  null, null,  null, null);          
        String query= hmQuery.keySet().iterator().next();   
        Object[] keyFieldValueNew = hmQuery.get(query);
//        String query = sql.buildSqlStatement("SELECT", schemaName, tableName,
//                keyFieldNames, keyFieldValues, keyFieldNames,  null, null,  null, null);             
        try{
/*            
            Object[] keyFieldValuesSplitted = new Object[0];
            for (int i=0; i< keyFieldValues.length; i++){                    
                //Boolean containsInClause = false;
                String separator = "";
                String fn = keyFieldNames[i];
                if (fn.toUpperCase().contains(" IN")){ 
                    Integer posicINClause = fn.toUpperCase().indexOf("IN");
                    separator = fn;
                    separator = separator.substring(posicINClause+2, posicINClause+3);
                    separator = separator.trim();
                    separator = separator.replace(" IN", "");  
                    if (separator.length()==0){ separator="\\|";} 
                 //containsInClause = true;
                }
                //String currFieldValue = keyFieldValues[i].toString();                
                if(keyFieldValues[i] instanceof String){                    
                    Object[] fieldsIN = keyFieldValues[i].toString().split(separator);
                    int fINlen=fieldsIN.length;
                    LabPLANETPlatform labPlat = new LabPLANETPlatform();
                    
                    //Object[] decrypted = labPlat.decryptString("fS￵ￃﾺﾀ?p￦￠￝ﾅ\\ﾭﾡ￡");
                    //"fS￵ￃﾺﾀ?p￦￠￝ﾅ\ﾭﾡ￡"
                            
                    for (int ii=0; ii<fINlen; ii++){
                        String fldToEncrypt = fieldsIN[ii].toString();
                        Object[] fEnc = labPlat.encryptString(fldToEncrypt);
                        fieldsIN[ii] = fEnc[1];                        
                    }
                    keyFieldValuesSplitted = labArr.addValueToArray1D(keyFieldValuesSplitted, fieldsIN);
                }else{
                    keyFieldValuesSplitted = labArr.addValueToArray1D(keyFieldValuesSplitted, keyFieldValues[i]);
                }
            }            
            ResultSet res = rdbm.prepRdQuery(query, keyFieldValuesSplitted);
*/            
            ResultSet res = rdbm.prepRdQuery(query, keyFieldValueNew);
            res.first();
            Integer numRows=res.getRow();
            if (numRows>0){
                errorCode = "Rbdms_existsRecord_RecordFound";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(filteredValues));
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
                return LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);                
            }else{
                errorCode = "Rbdms_existsRecord_RecordNotFound";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(filteredValues));
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
                return LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, query);
            return (Object[]) LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
        }                    
    }

    /**
     *
     * @param rdbm
     * @param schemaName
     * @param tableName
     * @param whereFieldNames
     * @param whereFieldValues
     * @param fieldsToRetrieve
     * @param fieldsSortBy
     * @return
     */
    public String getRecordFieldsByFilterJSON(Rdbms rdbm, String schemaName, String tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve, String[] fieldsSortBy){
        
        Object[][] diagnoses = new Object[1][6];                
        LabPLANETArray labArr = new LabPLANETArray();                 
        String[] errorDetailVariables = new String[0];        
        schemaName = LabPLANETPlatform.buildSchemaName(schemaName, "");
        
        if (whereFieldNames.length==0){
           errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
           Object[] diagnosesError = (Object[]) LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
           return null;
           //return labArr.array1dTo2d(diagnosesError, diagnosesError.length);
        }
        SqlStatement sql = new SqlStatement(); 
//        String query = sql.buildSqlStatement("SELECT", schemaName, tableName,
//                whereFieldNames, whereFieldValues,
//                fieldsToRetrieve,  null, null, fieldsSortBy, null);        
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement("SELECT", schemaName, tableName,
                whereFieldNames, whereFieldValues,
                fieldsToRetrieve,  null, null, fieldsSortBy, null);       
        String query= hmQuery.keySet().iterator().next();    
        Object[] keyFieldValueNew = hmQuery.get(query);        
            try{
                Boolean containsInClause = false;
                ResultSet res = null;
                query = "select array_to_json(array_agg(row_to_json(t))) from (" + query +") t";
                
/*                if (fieldsSortBy!=null){
                    query=query+" Order By ";
                    for (String fn: fieldsSortBy){
                        query=query+" "+fn+" , ";
                    }
                    query=query.substring(0, query.length()-2);
                } 
*/                
                //query = "select row_to_json(t) from (" + query +") t";
                if ( containsInClause ){
                    res = rdbm.prepRdQuery(query, keyFieldValueNew);
                    res.last();
                }else{
                    res = rdbm.prepRdQuery(query, keyFieldValueNew);
                    res.last();
                }                
            if (res.getRow()>0){
                
                boolean first = res.first();
                String finalString = "";
            
                return res.getString(1);
            
/*                if (1==1){                       
                          return LabPLANETJson.convertToJSON(res);}
                
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
             //diagnoses2 = labArr.decryptTableFieldArray(schemaName, tableName, fieldsToRetrieve, (Object[][]) diagnoses2); 
//                diagnoses2 = labArr.decryptTableFieldArray(schemaName, tableName, fieldsToRetrieve, diagnoses2);
                return null;
                //return diagnoses2;
*/            
            }else{
                errorCode = "Rdbms_NoRecordsFound";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues) );
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
                Object[] diagnosesError = (Object[]) LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
                return null;
                //return labArr.array1dTo2d(diagnosesError, diagnosesError.length);
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, query);
            Object[] diagnosesError = (Object[]) LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
            return null;
            //return labArr.array1dTo2d(diagnosesError, diagnosesError.length);
        }                    
    }
    
    /**
     *
     * @param rdbm
     * @param schemaName
     * @param tableName
     * @param whereFieldNames
     * @param whereFieldValues
     * @param fieldsToRetrieve
     * @return
     */
    public Object[][] getRecordFieldsByFilter(Rdbms rdbm, String schemaName, String tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve){
        
        Object[][] diagnoses = new Object[1][7];                
        LabPLANETArray labArr = new LabPLANETArray();                 
        String[] errorDetailVariables = new String[0];        
        schemaName = LabPLANETPlatform.buildSchemaName(schemaName, "");
        
        if (whereFieldNames.length==0){
           errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
           Object[] diagnosesError = (Object[]) LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
           return labArr.array1dTo2d(diagnosesError, diagnosesError.length);
        }        
        
        SqlStatement sql = new SqlStatement(); 
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement("SELECT", schemaName, tableName,
                whereFieldNames, whereFieldValues,
                fieldsToRetrieve,  null, null, null, null);           
        String query= hmQuery.keySet().iterator().next();   
        Object[] keyFieldValueNew = hmQuery.get(query);
        
        try{
            Boolean containsInClause = false;
            ResultSet res = null;
            if ( containsInClause ){
                res = rdbm.prepRdQuery(query, keyFieldValueNew);
                res.last();
            }else{
                res = rdbm.prepRdQuery(query, keyFieldValueNew);
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
             //diagnoses2 = labArr.decryptTableFieldArray(schemaName, tableName, fieldsToRetrieve, (Object[][]) diagnoses2); 
//                diagnoses2 = labArr.decryptTableFieldArray(schemaName, tableName, fieldsToRetrieve, diagnoses2);
                return diagnoses2;
            }else{
                errorCode = "Rdbms_NoRecordsFound";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues) );
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
                Object[] diagnosesError = (Object[]) LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
                return labArr.array1dTo2d(diagnosesError, diagnosesError.length);
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, query);
            Object[] diagnosesError = (Object[]) LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
            return labArr.array1dTo2d(diagnosesError, diagnosesError.length);
        }                    
    }

    /**
     *
     * @param rdbm
     * @param schemaName
     * @param tableName
     * @param whereFieldNames
     * @param whereFieldValues
     * @param fieldsToRetrieve
     * @return
     */
    public Object[][] getRecordFieldsByFilter(Rdbms rdbm, String schemaName, String[] tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve){
        
        Object[][] diagnoses = new Object[1][6];        
        LabPLANETArray labArr = new LabPLANETArray();       
        LabPLANETPlatform labPlat = new LabPLANETPlatform();   
        String[] errorDetailVariables = new String[0];        
        
        if (whereFieldNames.length==0){
           errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
           Object[] diagnosesError = (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
           return labArr.array1dTo2d(diagnosesError, diagnosesError.length);               
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
                errorCode = "Rbdms_existsRecord_RecordNotFound";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues));
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
                Object[] diagnosesError = (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
                return labArr.array1dTo2d(diagnosesError, diagnosesError.length);               
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, query);
            Object[] diagnosesError = (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
            return labArr.array1dTo2d(diagnosesError, diagnosesError.length);               
        }                    
    }

    /**
     *
     * @param rdbm
     * @param schemaName
     * @param tableName
     * @param whereFieldNames
     * @param whereFieldValues
     * @param fieldsToRetrieve
     * @param orderBy
     * @return
     */
    public Object[][] getRecordFieldsByFilter(Rdbms rdbm, String schemaName, String tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve, String[] orderBy){
        
        Object[][] diagnoses = new Object[1][6];        
        LabPLANETArray labArr = new LabPLANETArray();       
        LabPLANETPlatform labPlat = new LabPLANETPlatform();   
        String[] errorDetailVariables = new String[0];        
        
        if (whereFieldNames.length==0){
           errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
           Object[] diagnosesError = (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
           return labArr.array1dTo2d(diagnosesError, diagnosesError.length);               
        }
        SqlStatement sql = new SqlStatement(); 
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement("SELECT", schemaName, tableName,
                whereFieldNames, whereFieldValues,
                fieldsToRetrieve,  orderBy, null, null, null);            
        String query= hmQuery.keySet().iterator().next();   
        Object[] keyFieldValueNew = hmQuery.get(query);
        
/*        
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
*/        
        try{
            ResultSet res = rdbm.prepRdQuery(query, keyFieldValueNew);
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
                errorCode = "Rdbms_NoRecordsFound";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues) );
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
                Object[] diagnosesError = (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
                return labArr.array1dTo2d(diagnosesError, diagnosesError.length);                
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, query);
            Object[] diagnosesError = (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
            return labArr.array1dTo2d(diagnosesError, diagnosesError.length);             
        }                    
    }

    /**
     *
     * @param rdbm
     * @param schemaName
     * @param tableName
     * @param fieldNames
     * @param fieldValues
     * @return
     */
    public Object[] insertRecordInTable(Rdbms rdbm, String schemaName, String tableName, String[] fieldNames, Object[] fieldValues){
        // fieldValues = labArr.encryptTableFieldArray(schemaName, tableName, fieldNames, fieldValues);
        String[] diagnoses = new String[7];
        LabPLANETArray labArr = new LabPLANETArray();       
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        LabPLANETArray labArray = new LabPLANETArray();
        String[] errorDetailVariables = new String[0];        

        if (fieldNames.length==0){
           errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
           return (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
        }
        if (fieldNames.length!=fieldValues.length){
           errorCode = "DataSample_FieldArraysDifferentSize";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldNames));
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldValues));
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
        }
        SqlStatement sql = new SqlStatement(); 
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement("INSERT", schemaName, tableName,
                null, null, null, fieldNames, fieldValues,
                null, null);              
        String query= hmQuery.keySet().iterator().next();   
        Object[] keyFieldValueNew = hmQuery.get(query);        
 /*       String query = "";
        String fieldNamesStr = "";
        for (String fn: fieldNames){fieldNamesStr = fieldNamesStr + fn + ", ";}
        fieldNamesStr = fieldNamesStr.substring(0, fieldNamesStr.length()-2);
        query = "Insert into " + schemaName + "." + tableName + " (" + fieldNamesStr + ") values ( " ;
        Integer i=1;
        for (String fn: fieldNames){
            if (i==1){query = query + "? ";i++;}
            else{query = query + ", ? ";}
            i++;
        }
        query = query + ") ";
*/        
        try {                        
            fieldValues = labArr.encryptTableFieldArray(schemaName, tableName, fieldNames, (Object[]) fieldValues); 
            String keyValueNewRecord = rdbm.prepUpQueryK(query, fieldValues, 1);
            fieldValues = labArr.decryptTableFieldArray(schemaName, tableName, fieldNames, (Object[]) fieldValues); 
            
            errorCode = "Rdbms_RecordCreated";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, String.valueOf(keyValueNewRecord));
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, query);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldValues));            
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);                
            Object[] diagnosis =  LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);                         
            diagnosis = labArr.addValueToArray1D(diagnosis, keyValueNewRecord);
            return diagnosis;
        } catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, query);
            return (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
        }
    }
    
    /**
     *
     * @param rdbm
     * @param schemaName
     * @param tableName
     * @param updateFieldNames
     * @param updateFieldValues
     * @param whereFieldNames
     * @param whereFieldValues
     * @return
     */
    public Object[] updateRecordFieldsByFilter(Rdbms rdbm, String schemaName, String tableName, String[] updateFieldNames, Object[] updateFieldValues, String[] whereFieldNames, Object[] whereFieldValues) {
        
        String[] diagnoses = new String[6];        
        LabPLANETArray labArr = new LabPLANETArray();       
        LabPLANETPlatform labPlat = new LabPLANETPlatform();  
        updateFieldValues = labArr.decryptTableFieldArray(schemaName, tableName, updateFieldNames, (Object[]) updateFieldValues);        
        String[] errorDetailVariables = new String[0];        
       
        if (whereFieldNames.length==0){
           errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
           return (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
        }
        SqlStatement sql = new SqlStatement();       
//        for (Object fn: whereFieldValues){
//            updateFieldValues = labArr.addValueToArray1D(updateFieldValues, fn);}

        updateFieldValues = labArr.encryptTableFieldArray(schemaName, tableName, updateFieldNames, (Object[]) updateFieldValues); 
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement("UPDATE", schemaName, tableName,
                whereFieldNames, whereFieldValues, null, updateFieldNames, updateFieldValues,
                null, null);         
        String query= hmQuery.keySet().iterator().next();   
        Object[] keyFieldValueNew = hmQuery.get(query);                     
        Integer numr = rdbm.prepUpQuery(query, keyFieldValueNew);
        if (numr>0){     
            errorCode = "Rdbms_RecordUpdated";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues));
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);     
            return (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);   
        }else if(numr==-999){
            errorCode = "Rdbms_dtSQLException";
            String ermessage="The database cannot perform this sql statement: Schema: "+schemaName+". Table: "+tableName+". Query: "+query+", By the filter "+ Arrays.toString(whereFieldValues);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, query);
            return (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);   
        }else{   
            errorCode = "Rdbms_NoRecordsFound";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, tableName);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues) );
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
            return (Object[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                         
        }
    }    

    /**
     *
     * @param consultaconinterrogaciones
     * @param valoresinterrogaciones
     * @return
     * @throws SQLException
     */
    public  CachedRowSetImpl prepRdQuery(String consultaconinterrogaciones, Object [] valoresinterrogaciones) throws SQLException{
    //prepare statement para evitar sql injection
    LabPLANETArray labArr = new LabPLANETArray();        
    Object[] filteredValoresConInterrogaciones = new Object[0];     
     
     PreparedStatement prep=getConnection().prepareStatement(consultaconinterrogaciones);
        prep.setQueryTimeout(getTimeout());
        
        try{
            if (valoresinterrogaciones!=null){
                for (Integer i=0;i<valoresinterrogaciones.length;i++){
                    Boolean addToFilter = true;
                    if (valoresinterrogaciones[i].toString().equalsIgnoreCase("IN()")){addToFilter=false;}
                    if (valoresinterrogaciones[i].toString().equalsIgnoreCase("IS NULL")){addToFilter=false;}
                    if (valoresinterrogaciones[i].toString().equalsIgnoreCase("IS NOT NULL")){addToFilter=false;}
                    if (addToFilter){
                        filteredValoresConInterrogaciones = labArr.addValueToArray1D(filteredValoresConInterrogaciones, valoresinterrogaciones[i]);}
                }
            }
                
            buildPreparedStatement(filteredValoresConInterrogaciones, prep, null); 
        }catch(SQLException er){//cuando se envia un array 

        }
        
    ResultSet res = prep.executeQuery();
    CachedRowSetImpl crs = new CachedRowSetImpl();
    crs.populate(res);
    
    return crs; 
    }
    

    private Integer prepUpQuery(String consultaconinterrogaciones, Object [] valoresinterrogaciones) {
        Integer reg;    
        try {
            reg = prepUpQuery(consultaconinterrogaciones, valoresinterrogaciones, null);
            return reg;
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }        
    }
    
    private Integer prepUpQuery(String consultaconinterrogaciones, Object [] valoresinterrogaciones, Integer [] fieldtypes) throws SQLException{
        PreparedStatement prep=getConnection().prepareStatement(consultaconinterrogaciones);

        setTimeout(getTimeout());

        try{
            if (valoresinterrogaciones != null){
                buildPreparedStatement(valoresinterrogaciones, prep, fieldtypes); 
            }            
            Integer res=prep.executeUpdate();
            return res; 
        }catch (SQLException er){
            return -999;
        }
    }
    
    private String prepUpQueryK(String consultaconinterrogaciones, Object [] valoresinterrogaciones, Integer indexposition) throws SQLException, SQLFeatureNotSupportedException{
        String pkValue = "";

        try{
        
//            PreparedStatement prep=getConnection().prepareStatement(consultaconinterrogaciones);            

            PreparedStatement prep=getConnection().prepareStatement(consultaconinterrogaciones, Statement.RETURN_GENERATED_KEYS);            
            setTimeout(getTimeout());
            buildPreparedStatement(valoresinterrogaciones, prep, null);         
            Integer res = prep.executeUpdate();
        
        // When the table has no numeric field as single query then no key is generated so nothing to return
        //if (prep.NO_GENERATED_KEYS==2){return 0;}
            ResultSet rs = prep.getGeneratedKeys();

            if (rs.next()) {
              int newId = rs.getInt(indexposition);
              if (newId==0){
                  pkValue = "PRIMARY KEY NOT FIRST FIELD IN TABLE";
              }else{
                  pkValue = String.valueOf(newId);              
              }
            }
        }catch (SQLException er){
            pkValue = "TABLE WITH NO KEY";
        }
        return pkValue; 
    }
    
    /**
     *
     * @param schema
     * @param table
     * @return
     */
    public String [] getTableFieldsArrayEj(String schema, String table) {
        String sq = "select array(SELECT column_name || ''  FROM information_schema.columns WHERE table_schema = ? AND table_name   = ?) fields";
        CachedRowSetImpl res;
        try {
            res = prepRdQuery(sq, new Object[]{schema, table});
            String [] items ;
            items = res.next() ? LabPLANETArray.getStringArray(res.getArray("fields").getArray()) : null;
            return items;
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     *
     * @param schema
     * @param table
     * @param separator
     * @param addTableName
     * @return
     */
    public String getTableFieldsArrayEj(String schema, String table, String separator, Boolean addTableName) {
        String sq = "select array(SELECT column_name || ''  FROM information_schema.columns WHERE table_schema = ? AND table_name   = ?) fields";
        CachedRowSetImpl res;
        try {
            res = prepRdQuery(sq, new Object[]{schema, table});
            String [] items ;
            items = res.next() ? LabPLANETArray.getStringArray(res.getArray("fields").getArray()) : null;
            String tableFields = "";

            for (String f: items){
                if (tableFields.length()>0){tableFields=tableFields+separator;}
                if (addTableName){tableFields = tableFields+table+"."+f;            
                }else{tableFields = tableFields+f;}
            }
            return tableFields;
            
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }                
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
               case "class java.util.Date":
                   Date dt = (Date) obj;
                   java.sql.Date sqlDate = new java.sql.Date(dt.getTime());                   
                   prepsta.setDate(indexval, (java.sql.Date) sqlDate);
                   break;
               case "null":
                   //prepsta.setNull(indexval, fieldtypes[numi]);
                   prepsta.setNull(indexval, Types.VARCHAR);
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
    
    /**
     *
     * @return
     */
    public Date getLocalDate(){
        Date de = new java.sql.Date(System.currentTimeMillis());        
        return de;}

    /**
     *
     * @return
     */
    public Date getCurrentDate(){        
        Date de = new java.sql.Date(System.currentTimeMillis());        
        return de;}    
    
    private CachedRowSetImpl readQuery(String consulta) throws SQLException{
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
    
    public Connection createTransaction(){
        try {
            conn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return conn;        
    }
    
    public void Commit(){
        try {
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
     public void rollback(){
        try {
            conn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

     public Connection createTransactionWithSavePoint(){
        //conn.setAutoCommit(false);
        try {
                this.savepoint = conn.setSavepoint();
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;        
    }
     public Savepoint getConnectionSavePoint(){
         return this.savepoint;
     }
     public void rollbackWithSavePoint(){
        try {
            conn.rollback(this.savepoint);
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

}
