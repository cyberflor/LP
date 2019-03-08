/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.user;

import LabPLANET.utilities.LabPLANETArray;
import databases.Rdbms;

/**
 *
 * @author Administrator
 */
public class UserProfile {

    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    //String schemaDataName = "data";
    String schemaAppName = "app";
    
    /**
     *
     * @param userInfoId
     * @return
     */
    public Object[] getAllUserProcedurePrefix ( String userInfoId) {
            String tableName = "user_process";  
                        
            String[] filterFieldName = new String[3];
            Object[] filterFieldValue = new Object[2];
            String[] fieldsToReturn = new String[1];
                        
            fieldsToReturn[0] = "proc_name";
            filterFieldName[0]="user_name";
            filterFieldValue[0]=userInfoId;
            filterFieldName[1]="active";
            filterFieldValue[1]=true;
            filterFieldName[2]="proc_name is not null";            
            
            Object[][] userProc =  Rdbms.getRecordFieldsByFilter(schemaAppName, tableName, filterFieldName, filterFieldValue, fieldsToReturn);
            return LabPLANETArray.array2dTo1d(userProc);                         
    }
    
    /**
     *
     * @param userInfoId
     * @return
     */
    public Object[] getAppUserProfileFieldValues ( String userInfoId) {
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
            
            Object[][] userProc =  Rdbms.getRecordFieldsByFilter(schemaAppName, tableName, filterFieldName, filterFieldValue, fieldsToReturn);
            return LabPLANETArray.array2dTo1d(userProc);                         
        }
        
    /**
     *
     * @param schemaPrefix
     * @param userInfoId
     * @return
     */
    public Object[] getProcedureUserProfileFieldValues ( String schemaPrefix, String userInfoId) {
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
            
            Object[][] userProc =  Rdbms.getRecordFieldsByFilter(schemaPrefix, tableName, 
                    filterFieldName, filterFieldValue, new String[] {"role_name"});
            return LabPLANETArray.array2dTo1d(userProc);                         
        }

    /**
     *
     * @param schemaPrefix
     * @param userInfoId
     * @return
     */
    public Object[] getProcedureUserProfileFieldValues ( Object[] schemaPrefix, String userInfoId) {
            Object[] totalProcUserProfiles  = new Object[0];          
            
            for (int iProcs=0; iProcs<schemaPrefix.length; iProcs++){
                String currProcPrefix = (String) schemaPrefix[iProcs];
                Object[] currProcUserProfiles =  getProcedureUserProfileFieldValues(currProcPrefix, userInfoId);
                for (Object fn: currProcUserProfiles ){
                    totalProcUserProfiles = LabPLANETArray.addValueToArray1D(totalProcUserProfiles, fn);}
                    //totalProcUserProfiles = labArr.addValueToArray1D(totalProcUserProfiles, currProcPrefix+" - "+fn);}
            }            
            return totalProcUserProfiles;                         
        }                
    // Should not return any role from config and data schemas as those are considered specials, not for business users.
    
}
