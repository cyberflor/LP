/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.user;

import LabPLANET.utilities.LabPLANETArray;
import databases.Rdbms;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class UserProfile {

    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    //String schemaDataName = "data";
    String schemaConfigName = "app";
    String tableName = "user_process";  

    public Object[] getAllUserProcedurePrefix (Rdbms rdbm, String userInfoId) {

            LabPLANETArray labArr = new LabPLANETArray();
            String[] filterFieldName = new String[3];
            Object[] filterFieldValue = new Object[2];
            String[] fieldsToReturn = new String[1];
                        
            fieldsToReturn[0] = "proc_name";
            filterFieldName[0]="user_name";
            filterFieldValue[0]=userInfoId;
            filterFieldName[1]="active";
            filterFieldValue[1]=true;
            filterFieldName[2]="proc_name is not null";            
            
            Object[][] userProc =  rdbm.getRecordFieldsByFilter(rdbm, schemaConfigName, tableName, filterFieldName, filterFieldValue, fieldsToReturn);
            return labArr.array2dTo1d(userProc);                         
    }
    
        public Object[] getAppUserProfileFieldValues (Rdbms rdbm, String[] filterFieldName, Object[] filterFieldValue, String[] fieldsToReturn) {

            LabPLANETArray labArr = new LabPLANETArray();                          
            
            Object[][] userProc =  rdbm.getRecordFieldsByFilter(rdbm, schemaConfigName, tableName, filterFieldName, filterFieldValue, fieldsToReturn);
            return labArr.array2dTo1d(userProc);                         
        }
        
        public Object[] getProcedureUserProfileFieldValues (Rdbms rdbm, String schemaPrefix, String[] filterFieldName, Object[] filterFieldValue, String[] fieldsToReturn) {

            LabPLANETArray labArr = new LabPLANETArray();      
            schemaPrefix = schemaPrefix + "-config";
            
            Object[][] userProc =  rdbm.getRecordFieldsByFilter(rdbm, schemaPrefix, tableName, filterFieldName, filterFieldValue, fieldsToReturn);
            return labArr.array2dTo1d(userProc);                         
        }        
    // Should not return any role from config and data schemas as those are considered specials, not for business users.
    
}
