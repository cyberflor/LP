/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.user;

import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPArray;
import databases.Rdbms;

/**
 *
 * @author Administrator
 */
public class UserProfile {
    //String schemaDataName = "data";
    private static final String SCHEMANAMEAPP = LPPlatform.SCHEMA_APP;
    private static final String FIELDVALUE_ACTIVE="active";
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
            filterFieldName[1]=FIELDVALUE_ACTIVE;
            filterFieldValue[1]=true;
            filterFieldName[2]="proc_name is not null";            
            
            Object[][] userProc =  Rdbms.getRecordFieldsByFilter(SCHEMANAMEAPP, tableName, filterFieldName, filterFieldValue, fieldsToReturn);
            return LPArray.array2dTo1d(userProc);                         
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
            filterFieldName[1]=FIELDVALUE_ACTIVE;
            filterFieldValue[1]=true;
            filterFieldName[2]="proc_name is not null";          
            String tableName = "user_profile";                                  
            
            Object[][] userProc =  Rdbms.getRecordFieldsByFilter(SCHEMANAMEAPP, tableName, filterFieldName, filterFieldValue, fieldsToReturn);
            return LPArray.array2dTo1d(userProc);                         
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
            filterFieldName[1]=FIELDVALUE_ACTIVE;
            filterFieldValue[1]=true;
            filterFieldName[2]="role_name is not null";          
             
            schemaPrefix = schemaPrefix + "-config";
            
            Object[][] userProc =  Rdbms.getRecordFieldsByFilter(schemaPrefix, tableName, 
                    filterFieldName, filterFieldValue, new String[] {"role_name"});
            return LPArray.array2dTo1d(userProc);                         
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
                    totalProcUserProfiles = LPArray.addValueToArray1D(totalProcUserProfiles, fn);}
                    //totalProcUserProfiles = labArr.addValueToArray1D(totalProcUserProfiles, currProcPrefix+" - "+fn);}
            }            
            return totalProcUserProfiles;                         
        }                
    // Should not return any role from config and data schemas as those are considered specials, not for business users.
    
}
