/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import databases.Rdbms;
import java.util.Date;

/**
 * Create one new app session
 * @author Administrator
 */
public class LPSession {
    private LPSession(){    throw new IllegalStateException("Utility class");}    
    
    public static final String FIELDNAME_SESSION_ID = "session_id";
    
    public static Object[] newAppSession( String[] fieldsName2, Object[] fieldsValue2){        
        Date nowLocalDate = LPDate.getTimeStampLocalDate();
        String schemaAppName = "app";
        String tableName = "app_session";
        
        String[] fieldsName = null;     
        Object[] fieldsValue = null;
        
        fieldsName = LPArray.addValueToArray1D(fieldsName, "date_started");
        fieldsValue = LPArray.addValueToArray1D(fieldsValue, nowLocalDate);

        fieldsName = LPArray.addValueToArray1D(fieldsName, "person");
        fieldsValue = LPArray.addValueToArray1D(fieldsValue, "1111");        
        return Rdbms.insertRecordInTable(schemaAppName, tableName, fieldsName, fieldsValue);            
    }
    
    /**
     *  get App Session and get record field values by appSessionId
     * @param appSessionId
     * @param fieldsToRetrieve
     * @return
     */
    public static Object[] getAppSession( Integer appSessionId, String[] fieldsToRetrieve){
        String schemaAppName = "app";
        String tableName = "app_session";
        if (fieldsToRetrieve==null){
            fieldsToRetrieve = LPArray.addValueToArray1D(fieldsToRetrieve, FIELDNAME_SESSION_ID);
            fieldsToRetrieve = LPArray.addValueToArray1D(fieldsToRetrieve, "date_started");
        }
        
        Object[][] recordFieldsBySessionId = Rdbms.getRecordFieldsByFilter(schemaAppName, tableName, 
                new String[]{FIELDNAME_SESSION_ID}, new Object[]{appSessionId}, fieldsToRetrieve);
        return LPArray.array2dTo1d(recordFieldsBySessionId);
    }
    
    /**
     * PendingList! - Trap errorsInDatabase for DataIntegrity
     * IdeaList!       - Ler the AppSession know in which procedures any action was performed by adding one field to concatenat the procedureNames
     * When the user authenticates then one appSession is created but no ProcessSessions yet due to no action performed yet.<br>
     * This function will replicate to the ProcessSession the session once one action is audited in order to let that any action
     * on this procedure was performed as part of this given appSession.
     * @param processName
     * @param appSessionId
     * @param fieldsNamesToInsert
     * @return
     */
    public static Object[] addProcessSession( String processName, Integer appSessionId, String[] fieldsNamesToInsert){
        String tableName = "session";
        String schemaAuditName = LPPlatform.buildSchemaName(processName, "data-audit");       
        
        Object[][] recordFieldsBySessionId = Rdbms.getRecordFieldsByFilter(schemaAuditName, tableName, 
                new String[]{FIELDNAME_SESSION_ID}, new Object[]{appSessionId}, fieldsNamesToInsert);
        if ("LABPLANET_FALSE".equalsIgnoreCase(recordFieldsBySessionId[0][0].toString())){
            Object[] appSession = getAppSession(appSessionId, fieldsNamesToInsert);
            if (!LPArray.valueInArray(fieldsNamesToInsert, FIELDNAME_SESSION_ID)){
                fieldsNamesToInsert = LPArray.addValueToArray1D(fieldsNamesToInsert, FIELDNAME_SESSION_ID);
                appSession = LPArray.addValueToArray1D(appSession, appSessionId);
            }
            return Rdbms.insertRecordInTable(schemaAuditName, tableName, fieldsNamesToInsert, appSession);
        }
        return LPArray.array2dTo1d(recordFieldsBySessionId);
    }
    
    
}
