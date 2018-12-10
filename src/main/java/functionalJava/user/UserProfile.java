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
    

    public Object[] getAllUserProcedurePrefix (Rdbms rdbm, String userInfoId) {
            String tableName = "user_process";  
            
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
    
        public Object[] getAppUserProfileFieldValues (Rdbms rdbm, String userInfoId) {
            String[] filterFieldName = new String[3];
            Object[] filterFieldValue = new Object[2];
            String[] fieldsToReturn = new String[1];
            
            fieldsToReturn[0] = "proc_name";
            filterFieldName[0]="user_name";
            filterFieldValue[0]=userInfoId;
            filterFieldName[1]="active";
            filterFieldValue[1]=true;
            filterFieldName[2]="proc_name is not null";          
            String tableName = "user_profile";  
            LabPLANETArray labArr = new LabPLANETArray();                          
            
            Object[][] userProc =  rdbm.getRecordFieldsByFilter(rdbm, schemaConfigName, tableName, filterFieldName, filterFieldValue, fieldsToReturn);
            return labArr.array2dTo1d(userProc);                         
        }
        
        public Object[] getProcedureUserProfileFieldValues (Rdbms rdbm, String schemaPrefix, String userInfoId) {
            LabPLANETArray labArr = new LabPLANETArray();     
            String tableName = "person_profile";  
            String[] filterFieldName = new String[3];
            Object[] filterFieldValue = new Object[2];
            String[] fieldsToReturn = new String[1];
            fieldsToReturn[0] = "role_name";
            filterFieldName[0]="person_name";
            filterFieldValue[0]=userInfoId;
            filterFieldName[1]="active";
            filterFieldValue[1]=true;
            filterFieldName[2]="role_name is not null";          
             
            schemaPrefix = schemaPrefix + "-config";
            
            Object[][] userProc =  rdbm.getRecordFieldsByFilter(rdbm, schemaPrefix, tableName, 
                    filterFieldName, filterFieldValue, new String[] {"role_name"});
            return labArr.array2dTo1d(userProc);                         
        }
        public Object[] getProcedureUserProfileFieldValues (Rdbms rdbm, Object[] schemaPrefix, String userInfoId) {
            String tableName = "user_profile";  
            LabPLANETArray labArr = new LabPLANETArray();      
            Object[] totalProcUserProfiles  = new Object[0];          
            
            for (int iProcs=0; iProcs<schemaPrefix.length; iProcs++){
                String currProcPrefix = (String) schemaPrefix[iProcs];
                Object[] currProcUserProfiles =  getProcedureUserProfileFieldValues(rdbm, currProcPrefix, userInfoId);
                for (Object fn: currProcUserProfiles ){
                    totalProcUserProfiles = labArr.addValueToArray1D(totalProcUserProfiles, fn);}
                    //totalProcUserProfiles = labArr.addValueToArray1D(totalProcUserProfiles, currProcPrefix+" - "+fn);}
            }            
            return totalProcUserProfiles;                         
        }                
    // Should not return any role from config and data schemas as those are considered specials, not for business users.
    
}
