/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

import LabPLANET.utilities.LPNulls;
import com.sun.rowset.CachedRowSetImpl;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPPlatform;
import functionalJava.testingScripts.LPTestingOutFormat;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
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
import java.util.Properties;
import org.json.simple.JSONObject;

/**
 *
 * @author Administrator
 */
public class Rdbms {
    String errorCode = "";

    private static Connection conn = null;
    private static Boolean isStarted = false;
    private static Integer timeout;
    private String lastError = "";
    Integer transactionId = 0;
    String savepointName;
    Savepoint savepoint=null;    
    
    private static Rdbms rdbms;
    private static final String SQLSELECT = "SELECT";
    public static final String TBL_NO_KEY="TABLE WITH NO KEY";
    public static final String TBL_KEY_NOT_FIRST_TABLEFLD="PRIMARY KEY NOT FIRST FIELD IN TABLE";
    
    private Rdbms() {}                
   
    /**
     *
     * @return
     */
    
    public synchronized static Rdbms getRdbms(){
        if (rdbms==null){
            rdbms= new Rdbms();
        }
        return rdbms;
    }
    
    public Boolean startRdbms(){
        return startRdbmsTomcat(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW);
    }

    public Boolean startRdbms(String us, String pw){
        return startRdbmsTomcat(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW);
        //return startRdbmsTomcat(us, pw);
    }    
    
    public Boolean startRdbmsTomcat(String user, String pass) {        
            ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");
            String url = prop.getString("dburl");
            String dbDriver = prop.getString("dbDriver");
            Integer conTimeOut = Integer.valueOf(prop.getString("dbtimeout"));
            
            String datasrc = prop.getString("datasource");
            
            try{
                Context ctx = new InitialContext();
                  //DataSource ds = (DataSource)ctx.lookup(datasrc);

                //Class.forName(dbDriver);
                Properties dbProps = new Properties();
                dbProps.setProperty("user", user);
                dbProps.setProperty("password", pass);
                dbProps.setProperty("Ssl", "false");
                //dbProps.setProperty("ssl", "true");
                dbProps.setProperty("ConnectTimeout", conTimeOut.toString());
                //dbProps.setProperty("ConnectTimeout", "conTimeOut");
                
                Connection getConnection = DriverManager.getConnection(url, dbProps);          
                setConnection(getConnection);
                //Connection setConnection = DriverManager.getConnection(url, user, pass);          
                setTimeout(conTimeOut);
                if(getConnection()!=null){
                  setIsStarted(Boolean.TRUE);                                                      
                  return Boolean.TRUE;
                }else{
                  setIsStarted(Boolean.FALSE);
                  return Boolean.FALSE;
                }                                 
            } catch (Exception e){
                String errMsg = e.getMessage();
                return false;
            }
            
    }
    /**
     *
     * @param user
     * @param pass
     * @return
     */
    public Boolean startRdbmsGlassfish(String user, String pass) {        
        
        try {        
            //Rdbms rdbms = new Rdbms();
            ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");
            String datasrc = prop.getString("datasource");
            Integer to = Integer.valueOf(prop.getString("dbtimeout"));
            
            //rdbms.timeout = to;
            setTimeout(to);

                  Context ctx = new InitialContext();
                  DataSource ds = (DataSource)ctx.lookup(datasrc);

                    try {                        
                        ds.setLoginTimeout(Rdbms.timeout);
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
                return Boolean.TRUE;
              }else{
                setIsStarted(Boolean.FALSE);
                return Boolean.FALSE;
              } 
        } catch (NamingException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
            return Boolean.FALSE;
        }
    //return getIsStarted();
    }
   
    public static void setTransactionId(String schemaName){
        
        schemaName = LPPlatform.buildSchemaName(schemaName, "");
        String qry = "select nextval('"+ schemaName + ".transaction_id')";
        Integer transactionIdNextVal = prepUpQuery(qry, null);
        if (transactionIdNextVal==-999){
            transactionIdNextVal=12;
        }
        rdbms.transactionId = transactionIdNextVal;
        
    }
    
    public static Integer getTransactionId(){
        return rdbms.transactionId;
    }
            
    /**
     *
     */
    public static void closeRdbms(){       
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
    private static void setTimeout(Integer tOut){ Rdbms.timeout = tOut;}
    
    public Integer getTimeout() { return timeout;}

    /**
     *
     * @param timeout
     */
    //public static void setTimeout(Integer timeout) { this.timeout = timeout;}

    private void setLastError(String txterror){ lastError = txterror;}
    
    /**
     *
     * @return
     */
    public String getLastError(){return lastError;}

    private static void setConnection(Connection con){ Rdbms.conn=con; }
    
    /**
     *
     * @return
     */
    public static Connection getConnection(){ return conn; }

    /**
     *
     * @return
     */
    public Boolean getIsStarted() { return isStarted;}
    
    private static void setIsStarted(Boolean isStart) { Rdbms.isStarted = isStart;}
    
    /**
     *
     * @param schemaName
     * @param tableName
     * @param keyFieldName
     * @param keyFieldValue
     * @return
     */
    public Object[] zzzexistsRecord(String schemaName, String tableName, String[] keyFieldName, Object keyFieldValue){
        String[] errorDetailVariables = new String[0];
        SqlStatement sql = new SqlStatement();        
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement(SQLSELECT, schemaName, tableName,
                keyFieldName, null, keyFieldName,  null, null,  null, null);          
            String query= hmQuery.keySet().iterator().next();   
            Object[] keyFieldValueNew = hmQuery.get(query);
        try{
            ResultSet res;
            res = Rdbms.prepRdQuery(query, keyFieldValueNew);
            res.last();

            if (res.getRow()>0){
                rdbms.errorCode = "Rbdms_existsRecord_RecordFound";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, keyFieldValue.toString());
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, rdbms.errorCode, errorDetailVariables);                
            }else{
                rdbms.errorCode = "Rbdms_existsRecord_RecordNotFound";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, keyFieldValue.toString());
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            rdbms.errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, query);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                                       
        }                    
    }

    /**
     *
     * @param schemaName
     * @param tableName
     * @param keyFieldNames
     * @param keyFieldValues
     * @return
     */
    public static Object[] existsRecord(String schemaName, String tableName, String[] keyFieldNames, Object[] keyFieldValues){
        String[] errorDetailVariables = new String[0];
        Object[] filteredValues = new Object[0];
        
        if (keyFieldNames.length==0){
           rdbms.errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);          
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
        }
        SqlStatement sql = new SqlStatement(); 
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement(SQLSELECT, schemaName, tableName,
                keyFieldNames, keyFieldValues, keyFieldNames,  null, null,  null, null);          
        String query= hmQuery.keySet().iterator().next();   
        Object[] keyFieldValueNew = hmQuery.get(query);
//        String query = sql.buildSqlStatement(SQLSELECT, schemaName, tableName,
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
                    LPPlatform labPlat = new LPPlatform();
                    
                    //Object[] decrypted = labPlat.decryptString("fS￵ￃﾺﾀ?p￦￠￝ﾅ\\ﾭﾡ￡");
                    //"fS￵ￃﾺﾀ?p￦￠￝ﾅ\ﾭﾡ￡"
                            
                    for (int ii=0; ii<fINlen; ii++){
                        String fldToEncrypt = fieldsIN[ii].toString();
                        Object[] fEnc = labPlat.encryptString(fldToEncrypt);
                        fieldsIN[ii] = fEnc[1];                        
                    }
                    keyFieldValuesSplitted = LPArray.addValueToArray1D(keyFieldValuesSplitted, fieldsIN);
                }else{
                    keyFieldValuesSplitted = LPArray.addValueToArray1D(keyFieldValuesSplitted, keyFieldValues[i]);
                }
            }            
            ResultSet res = rdbm.prepRdQuery(query, keyFieldValuesSplitted);
*/            
            ResultSet res = Rdbms.prepRdQuery(query, keyFieldValueNew);
            res.first();
            Integer numRows=res.getRow();
            if (numRows>0){
                rdbms.errorCode = "Rbdms_existsRecord_RecordFound";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(filteredValues));
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, rdbms.errorCode, errorDetailVariables);                
            }else{
                rdbms.errorCode = "Rbdms_existsRecord_RecordNotFound";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(filteredValues));
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            rdbms.errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, query);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
        }                    
    }

    /**
     *
     * @param schemaName
     * @param tableName
     * @param whereFieldNames
     * @param whereFieldValues
     * @param fieldsToRetrieve
     * @param fieldsSortBy
     * @return
     */
    public static String getRecordFieldsByFilterJSONString(String schemaName, String tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve, String[] fieldsSortBy){
        // Falta implementar que devuelva JSON
        return "";
    }
    public static String getRecordFieldsByFilterJSON(String schemaName, String tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve, String[] fieldsSortBy){
        String[] errorDetailVariables = new String[0];        
        schemaName = LPPlatform.buildSchemaName(schemaName, "");
        
        if (whereFieldNames.length==0){
           rdbms.errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);          
           LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
           return null;
           //return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);
        }
        SqlStatement sql = new SqlStatement(); 
//        String query = sql.buildSqlStatement(SQLSELECT, schemaName, tableName,
//                whereFieldNames, whereFieldValues,
//                fieldsToRetrieve,  null, null, fieldsSortBy, null);        
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement(SQLSELECT, schemaName, tableName,
                whereFieldNames, whereFieldValues,
                fieldsToRetrieve,  null, null, fieldsSortBy, null);       
        String query= hmQuery.keySet().iterator().next();    
        Object[] keyFieldValueNew = hmQuery.get(query);        
            try{
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
            res = Rdbms.prepRdQuery(query, keyFieldValueNew);
            res.last();
            if (res.getRow()>0){
                return res.getString(1);
            
/*                if (1==1){                       
                          return LabPLANETJson.convertToJSON(res);}
                
             Integer totalLines = res.getRow();
             res.first();
             Integer icurrLine = 0;   
             
             Object[][] diagnoses2 = new Object[totalLines][fieldsToRetrieve.length];
             while(icurrLine<=totalLines-1) {
                //fieldValues = LPArray.encryptTableFieldArray(schemaName, tableName, fieldNames, fieldValues);                 
                for (Integer icurrCol=0;icurrCol<fieldsToRetrieve.length;icurrCol++){
                    Object currValue = res.getObject(icurrCol+1);
                    diagnoses2[icurrLine][icurrCol] =  currValue;
                }        
                res.next();
                icurrLine++;
             }         
             //diagnoses2 = LPArray.decryptTableFieldArray(schemaName, tableName, fieldsToRetrieve, (Object[][]) diagnoses2); 
//                diagnoses2 = LPArray.decryptTableFieldArray(schemaName, tableName, fieldsToRetrieve, diagnoses2);
                return null;
                //return diagnoses2;
*/            
            }else{
                
                rdbms.errorCode = "Rdbms_NoRecordsFound";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues) );
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
                return null;
                //return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            rdbms.errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, query);
            LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
            return null;
            //return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);
        }                    
    }
    
    /**
     *
     * @param schemaName
     * @param tableName
     * @param whereFieldNames
     * @param whereFieldValues
     * @param fieldsToRetrieve
     * @return
     */
    public static Object[][] getRecordFieldsByFilter(String schemaName, 
            String tableName, 
            String[] whereFieldNames, 
            Object[] whereFieldValues, 
            String[] fieldsToRetrieve){
        
        String[] errorDetailVariables = new String[0];        
        schemaName = LPPlatform.buildSchemaName(schemaName, "");
        
        if (whereFieldNames.length==0){
           Rdbms.rdbms.errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);          
           Object[] diagnosesError = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, Rdbms.rdbms.errorCode, errorDetailVariables);                         
           return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);
        }        
        
        SqlStatement sql = new SqlStatement(); 
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement(SQLSELECT, schemaName, tableName,
                whereFieldNames, whereFieldValues,
                fieldsToRetrieve,  null, null, null, null);           
        String query= hmQuery.keySet().iterator().next();   
        Object[] keyFieldValueNew = hmQuery.get(query);
        
        try{
            ResultSet res = null;
            res = Rdbms.prepRdQuery(query, keyFieldValueNew);
            res.last();
        if (res.getRow()>0){
         Integer totalLines = res.getRow();
         res.first();
         Integer icurrLine = 0;   
             
             Object[][] diagnoses2 = new Object[totalLines][fieldsToRetrieve.length];
             while(icurrLine<=totalLines-1) {
                //fieldValues = LPArray.encryptTableFieldArray(schemaName, tableName, fieldNames, fieldValues);                 
                for (Integer icurrCol=0;icurrCol<fieldsToRetrieve.length;icurrCol++){
                    Object currValue = res.getObject(icurrCol+1);
                    diagnoses2[icurrLine][icurrCol] =  currValue;
                }        
                res.next();
                icurrLine++;
             }         
             //diagnoses2 = LPArray.decryptTableFieldArray(schemaName, tableName, fieldsToRetrieve, (Object[][]) diagnoses2); 
//                diagnoses2 = LPArray.decryptTableFieldArray(schemaName, tableName, fieldsToRetrieve, diagnoses2);
                return diagnoses2;
            }else{
                rdbms.errorCode = "Rdbms_NoRecordsFound";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, query);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues) );
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                Object[] diagnosesError = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
                return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            rdbms.errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, query);
            Object[] diagnosesError = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
            return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);
        }                    
    }

    /**
     *
     * @param schemaName
     * @param tableName
     * @param whereFieldNames
     * @param whereFieldValues
     * @param fieldsToRetrieve
     * @return
     */
    public static Object[][] getRecordFieldsByFilter(String schemaName, String[] tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve){
        String[] errorDetailVariables = new String[0];        
        
        if (whereFieldNames.length==0){
           rdbms.errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);          
           Object[] diagnosesError = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
           return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);               
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
            ResultSet res = Rdbms.prepRdQuery(query, whereFieldValues);
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
                diagnoses2 = LPArray.decryptTableFieldArray(schemaName, tableName[0], fieldsToRetrieve, diagnoses2);
                return diagnoses2;
            }else{
                rdbms.errorCode = "Rbdms_existsRecord_RecordNotFound";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues));
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                Object[] diagnosesError = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
                return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);               
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            rdbms.errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, query);
            Object[] diagnosesError = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
            return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);               
        }                    
    }

    /**
     *
     * @param schemaName
     * @param tableName
     * @param whereFieldNames
     * @param whereFieldValues
     * @param fieldsToRetrieve
     * @param orderBy
     * @return
     */
    public static Object[][] getRecordFieldsByFilter(String schemaName, String tableName, String[] whereFieldNames, Object[] whereFieldValues, String[] fieldsToRetrieve, String[] orderBy){
        String[] errorDetailVariables = new String[0];        
        
        if (whereFieldNames.length==0){
           rdbms.errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);          
           Object[] diagnosesError = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
           return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);               
        }
        SqlStatement sql = new SqlStatement(); 
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement(SQLSELECT, schemaName, tableName,
                whereFieldNames, whereFieldValues,
                fieldsToRetrieve,  null, null, orderBy, null);            
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
            ResultSet res = Rdbms.prepRdQuery(query, keyFieldValueNew);
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
                diagnoses2 = LPArray.decryptTableFieldArray(schemaName, tableName, fieldsToRetrieve, diagnoses2);
                return diagnoses2;
            }else{
                rdbms.errorCode = "Rdbms_NoRecordsFound";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, query);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues) );
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);                
                
                Object[] diagnosesError = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
                return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);                
            }
        }catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            rdbms.errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, query);
            Object[] diagnosesError = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
            return LPArray.array1dTo2d(diagnosesError, diagnosesError.length);             
        }                    
    }

    /**
     *
     * @param schemaName
     * @param tableName
     * @param fieldNames
     * @param fieldValues
     * @return
     */
    public static Object[] insertRecordInTable(String schemaName, String tableName, String[] fieldNames, Object[] fieldValues){
        // fieldValues = LPArray.encryptTableFieldArray(schemaName, tableName, fieldNames, fieldValues);
        String[] errorDetailVariables = new String[0];        

        if (fieldNames.length==0){
           rdbms.errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);          
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
        }
        if (fieldNames.length!=fieldValues.length){
           rdbms.errorCode = "DataSample_FieldArraysDifferentSize";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldNames));
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldValues));
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);
        }
        SqlStatement sql = new SqlStatement(); 
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement("INSERT", schemaName, tableName,
                null, null, null, fieldNames, fieldValues,
                null, null);              
        String query= hmQuery.keySet().iterator().next();   
        //Object[] keyFieldValueNew = hmQuery.get(query);        
        try {                        
            fieldValues = LPArray.encryptTableFieldArray(schemaName, tableName, fieldNames, fieldValues); 
            String keyValueNewRecord = Rdbms.prepUpQueryK(query, fieldValues, 1);
            fieldValues = LPArray.decryptTableFieldArray(schemaName, tableName, fieldNames, (Object[]) fieldValues); 
            
            rdbms.errorCode = "Rdbms_RecordCreated";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, String.valueOf(keyValueNewRecord));
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, query);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldValues));            
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);                
            Object[] diagnosis =  LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, rdbms.errorCode, errorDetailVariables);                         
            diagnosis = LPArray.addValueToArray1D(diagnosis, keyValueNewRecord);
            return diagnosis;
        } catch (SQLException er) {
            String ermessage=er.getLocalizedMessage()+er.getCause();
            Logger.getLogger(query).log(Level.SEVERE, null, er);     
            rdbms.errorCode = "Rdbms_dtSQLException";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, query);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
        }
    }
    
    /**
     *
     * @param schemaName
     * @param tableName
     * @param updateFieldNames
     * @param updateFieldValues
     * @param whereFieldNames
     * @param whereFieldValues
     * @return
     */
    public static Object[] updateRecordFieldsByFilter(String schemaName, String tableName, String[] updateFieldNames, Object[] updateFieldValues, String[] whereFieldNames, Object[] whereFieldValues) {
        updateFieldValues = LPArray.decryptTableFieldArray(schemaName, tableName, updateFieldNames, (Object[]) updateFieldValues);        
        String[] errorDetailVariables = new String[0];        
       
        if (whereFieldNames.length==0){
           rdbms.errorCode = "Rdbms_NotFilterSpecified";
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
           errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);          
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
        }
        SqlStatement sql = new SqlStatement();       
//        for (Object fn: whereFieldValues){
//            updateFieldValues = LPArray.addValueToArray1D(updateFieldValues, fn);}

        updateFieldValues = LPArray.encryptTableFieldArray(schemaName, tableName, updateFieldNames, (Object[]) updateFieldValues); 
        HashMap<String, Object[]> hmQuery = sql.buildSqlStatement("UPDATE", schemaName, tableName,
                whereFieldNames, whereFieldValues, null, updateFieldNames, updateFieldValues,
                null, null);         
        String query= hmQuery.keySet().iterator().next();   
        Object[] keyFieldValueNew = hmQuery.get(query);                     
        Integer numr = Rdbms.prepUpQuery(query, keyFieldValueNew);
        if (numr>0){     
            rdbms.errorCode = "Rdbms_RecordUpdated";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues));
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);     
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, rdbms.errorCode, errorDetailVariables);   
        }else if(numr==-999){
            rdbms.errorCode = "Rdbms_dtSQLException";
            String ermessage="The database cannot perform this sql statement: Schema: "+schemaName+". Table: "+tableName+". Query: "+query+", By the values "+ Arrays.toString(keyFieldValueNew);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ermessage);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, query);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);   
        }else{   
            rdbms.errorCode = "Rdbms_NoRecordsFound";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, tableName);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, Arrays.toString(whereFieldValues) );
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, rdbms.errorCode, errorDetailVariables);                         
        }
    }    

    /**
     *
     * @param consultaconinterrogaciones
     * @param valoresinterrogaciones
     * @return
     * @throws SQLException
     */
    public  static CachedRowSetImpl prepRdQuery(String consultaconinterrogaciones, Object [] valoresinterrogaciones) throws SQLException, NullPointerException{
        CachedRowSetImpl crs =new CachedRowSetImpl();
        try{
        Object[] filteredValoresConInterrogaciones = new Object[0];     
        PreparedStatement prepareStatement = conn.prepareStatement(consultaconinterrogaciones);
            prepareStatement.setQueryTimeout(rdbms.getTimeout());
            if (valoresinterrogaciones!=null){
                for (Object curVal: valoresinterrogaciones){
                    Boolean addToFilter = true;
                    if (curVal.toString().equalsIgnoreCase("IN()")){addToFilter=false;}
                    if (curVal.toString().equalsIgnoreCase("IS NULL")){addToFilter=false;}
                    if (curVal.toString().equalsIgnoreCase("IS NOT NULL")){addToFilter=false;}
                    if (addToFilter){
                        filteredValoresConInterrogaciones = LPArray.addValueToArray1D(filteredValoresConInterrogaciones, curVal);}                    
                }
            }                
            buildPreparedStatement(filteredValoresConInterrogaciones, prepareStatement, null); 
            ResultSet res = prepareStatement.executeQuery();
            crs.populate(res);
            return crs;             
        }catch(SQLException er){
            return crs;
        }//finally{crs.close();conn.close();}
    }
  

    public static Integer prepUpQuery(String consultaconinterrogaciones, Object [] valoresinterrogaciones) {
        Integer reg;    
        try {
            reg = prepUpQuery(consultaconinterrogaciones, valoresinterrogaciones, null);
            return reg;
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }        
    }
    
    private static Integer prepUpQuery(String consultaconinterrogaciones, Object [] valoresinterrogaciones, Integer [] fieldtypes) throws SQLException{
        PreparedStatement prep=getConnection().prepareStatement(consultaconinterrogaciones);

        setTimeout(rdbms.getTimeout());

        try{
            if (valoresinterrogaciones != null){
                buildPreparedStatement(valoresinterrogaciones, prep, fieldtypes); 
            }            
            Integer res=prep.executeUpdate();
            return res; 
        }catch (SQLException er){
            return -999;
        }//finally{            prep.close();        }
    }
    
    private static String prepUpQueryK(String consultaconinterrogaciones, Object [] valoresinterrogaciones, Integer indexposition) throws SQLException, SQLFeatureNotSupportedException{
        String pkValue = "";
        PreparedStatement prep=getConnection().prepareStatement(consultaconinterrogaciones, Statement.RETURN_GENERATED_KEYS);            
        setTimeout(rdbms.getTimeout());
        buildPreparedStatement(valoresinterrogaciones, prep, null);         
        try{
//            PreparedStatement prep=getConnection().prepareStatement(consultaconinterrogaciones);            
            prep.executeUpdate();        
            ResultSet rs = prep.getGeneratedKeys();
        // When the table has no numeric field as single query then no key is generated so nothing to return
        //if (prep.NO_GENERATED_KEYS==2){return 0;}
            if (rs.next()) {
              int newId = rs.getInt(indexposition);
              if (newId==0){
                  return TBL_KEY_NOT_FIRST_TABLEFLD; //"PRIMARY KEY NOT FIRST FIELD IN TABLE";
              }else{
                  return String.valueOf(newId);              
              }
            }
        }catch (SQLException er){
            return TBL_NO_KEY; //"TABLE WITH NO KEY";
        }//finally{rs.close();}
        return pkValue;
    }
    
    /**
     *
     * @param schema
     * @param table
     * @return
     */
    public static String [] getTableFieldsArrayEj(String schema, String table) {
        String sq = "select array(SELECT column_name || ''  FROM information_schema.columns WHERE table_schema = ? AND table_name   = ?) fields";
        CachedRowSetImpl res;
        try {
            res = prepRdQuery(sq, new Object[]{schema, table});
            String [] items ;
            items = res.next() ? LPArray.getStringArray(res.getArray("fields").getArray()) : null;
            return items;
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
            return new String[0];
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
    public static String getTableFieldsArrayEj(String schema, String table, String separator, Boolean addTableName) {
        String sq = "select array(SELECT column_name || ''  FROM information_schema.columns WHERE table_schema = ? AND table_name   = ?) fields";
        CachedRowSetImpl res;
        try {
            res = prepRdQuery(sq, new Object[]{schema, table});
            String [] items ;
            items = res.next() ? LPArray.getStringArray(res.getArray("fields").getArray()) : null;
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
        
    private static void buildPreparedStatement(Object [] valoStrings, PreparedStatement prepsta, Integer [] fieldtypes) throws SQLException{
        Integer indexval = 1;
        for(Integer numi=0;numi<valoStrings.length;numi++){
            Object obj = valoStrings[numi];
            String clase;
            if (obj != null){
               clase = obj.getClass().toString();
            }else{
               clase = "null";    
            }
            obj = LPNulls.replaceNull(obj);
            if (obj.toString().toLowerCase().contains("null")){                
                String[] split = obj.toString().split("\\*");
                clase = split[1];
                switch(clase.toUpperCase()){
                    case "INTEGER":
                        clase = "class java.sql.Date";
                        prepsta.setNull(indexval, Types.INTEGER);
                        break;
                    case "BIGDECIMAL":
                        clase = "class java.sql.Date";
                        prepsta.setNull(indexval, Types.NUMERIC);
                        break;                        
                    case "DATE":
                        clase = "class java.sql.Date";
                        prepsta.setNull(indexval, Types.DATE);
                        break;
                    case "STRING":
                        clase = "class java.sql.Date";
                        prepsta.setNull(indexval, Types.VARCHAR);
                        break;
                    case "BOOLEAN":
                        clase = "class java.sql.Date";
                        prepsta.setNull(indexval, Types.BOOLEAN);
                        break;
                    case "FLOAT":
                        clase = "class java.sql.Date";
                        prepsta.setNull(indexval, Types.FLOAT);
                        break;                        
                    default:
                        break;
                }
            }else{
                    switch(clase){
                       case "class java.lang.Integer":
                            prepsta.setInt(indexval, (Integer)obj);
                            break;
                       case "class java.math.BigDecimal":
                            prepsta.setObject(indexval, (java.math.BigDecimal) obj, java.sql.Types.NUMERIC);                            
                            break;                           
                       case "class java.lang.Float":                           
                            prepsta.setFloat(indexval, (Float)obj);
                            break;
                       case "class java.lang.Boolean":
                            prepsta.setBoolean(indexval, (Boolean)obj);
                            break;
                       case "class java.sql.Date":
                            prepsta.setDate(indexval, (java.sql.Date) obj);
                            break;
                       case "class java.util.Date":
                           Date dt = (Date) obj;
                           java.sql.Date sqlDate = null;
                           if (obj!=null){                   
                               sqlDate = new java.sql.Date(dt.getTime());
                               prepsta.setDate(indexval, (java.sql.Date) sqlDate);
                           }else{
                               prepsta.setNull(indexval, Types.DATE);
                           }                   
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
    public static Date getLocalDate(){
        Date de = new java.sql.Date(System.currentTimeMillis());        
        return de;}

    /**
     *
     * @return
     */
    public static Date getCurrentDate(){        
        //By now this method returns the same value than the getLocalDate one.
        //Date de = new java.sql.Date(System.currentTimeMillis());        
        return getLocalDate();}    
    
    private static CachedRowSetImpl readQuery(String consulta) throws SQLException, NullPointerException{
        Statement sta=null;
        
        try (CachedRowSetImpl crs = new CachedRowSetImpl()){
            sta=getConnection().createStatement();
            sta.setQueryTimeout(rdbms.getTimeout());    
            ResultSet res=null;            
            if(!"".equals(consulta)){
                res = sta.executeQuery(consulta);    
            }
        crs.populate(res);    
        return crs;    
        }//finally{            sta.close();        }        
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
    
    public void commit(){
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

     public static Connection createTransactionWithSavePoint(){        
        try {
            conn.setAutoCommit(false);
            rdbms.savepoint = conn.setSavepoint();
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return conn;        
    }
     public Savepoint getConnectionSavePoint(){
         return this.savepoint;
     }
     public static void rollbackWithSavePoint(){
        try {
            conn.rollback(rdbms.savepoint);
        } catch (SQLException ex) {
            Logger.getLogger(Rdbms.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

}
