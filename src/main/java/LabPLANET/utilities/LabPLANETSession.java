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
public class LabPLANETSession {
    
    public static Object[] newAppSession( String[] fieldsName2, Object[] fieldsValue2){        
        LabPLANETArray labArr = new LabPLANETArray();
        LabPLANETDate labDate = new LabPLANETDate();
        Date nowLocalDate = labDate.getTimeStampLocalDate();
        String schemaAppName = "app";
        String tableName = "app_session";
        
        String[] fieldsName = null;     Object[] fieldsValue = null;
        
        fieldsName = labArr.addValueToArray1D(fieldsName, "date_started");
        fieldsValue = labArr.addValueToArray1D(fieldsValue, nowLocalDate);

        fieldsName = labArr.addValueToArray1D(fieldsName, "person");
        fieldsValue = labArr.addValueToArray1D(fieldsValue, "1111");        
        return Rdbms.insertRecordInTable(schemaAppName, tableName, fieldsName, fieldsValue);            

//return new Object[]{1};
    }
    
    /**
     *  get App Session and get record field values by appSessionId
     * @param rdbm
     * @param appSessionId
     * @param fieldsToRetrieve
     * @return
     */
    public static Object[] getAppSession( Integer appSessionId, String[] fieldsToRetrieve){
        LabPLANETArray labArr = new LabPLANETArray();
        String schemaAppName = "app";
        String tableName = "app_session";
        if (fieldsToRetrieve==null){
            fieldsToRetrieve = labArr.addValueToArray1D(fieldsToRetrieve, "session_id");
            fieldsToRetrieve = labArr.addValueToArray1D(fieldsToRetrieve, "date_started");
        }
        
        Object[][] recordFieldsBySessionId = Rdbms.getRecordFieldsByFilter(schemaAppName, tableName, 
                new String[]{"session_id"}, new Object[]{appSessionId}, fieldsToRetrieve);
        return labArr.array2dTo1d(recordFieldsBySessionId);
    }
    
    /**
     * PendingList! - Trap errorsInDatabase for DataIntegrity
     * IdeaList!       - Ler the AppSession know in which procedures any action was performed by adding one field to concatenat the procedureNames
     * When the user authenticates then one appSession is created but no ProcessSessions yet due to no action performed yet.<br>
     * This function will replicate to the ProcessSession the session once one action is audited in order to let that any action
     * on this procedure was performed as part of this given appSession.
     * @param rdbm
     * @param processName
     * @param appSessionId
     * @param fieldsNamesToInsert
     * @return
     */
    public static Object[] addProcessSession( String processName, Integer appSessionId, String[] fieldsNamesToInsert){
        LabPLANETArray labArr = new LabPLANETArray();
        LabPLANETPlatform labPlat = new LabPLANETPlatform();                
                
        String schemaAuditName = LabPLANETPlatform.buildSchemaName(processName, "data-audit");
        
        String tableName = "session";
        String[] fieldsToRetrieve = new String[]{"session_id"};
        
        Object[][] recordFieldsBySessionId = Rdbms.getRecordFieldsByFilter(schemaAuditName, tableName, 
                new String[]{"session_id"}, new Object[]{appSessionId}, fieldsNamesToInsert);
        if ("LABPLANET_FALSE".equalsIgnoreCase(recordFieldsBySessionId[0][0].toString())){
            Object[] appSession = getAppSession(appSessionId, fieldsNamesToInsert);
            if (!labArr.valueInArray(fieldsNamesToInsert, "session_id")){
                fieldsNamesToInsert = labArr.addValueToArray1D(fieldsNamesToInsert, "session_id");
                appSession = labArr.addValueToArray1D(appSession, appSessionId);
            }
            return Rdbms.insertRecordInTable(schemaAuditName, tableName, fieldsNamesToInsert, appSession);
        }
        return labArr.array2dTo1d(recordFieldsBySessionId);
    }
    
    
}
