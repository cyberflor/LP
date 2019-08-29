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
    private static final String FIELDVALUE_ACTIVE="active";
    /**
     *
     * @param userName
     * @return
     */
    public Object[] getAllUserProcedurePrefix ( String userName) {
            String tableName = "user_process";  
                        
            String[] filterFieldName = new String[3];
            Object[] filterFieldValue = new Object[2];
            String[] fieldsToReturn = new String[1];
                        
            fieldsToReturn[0] = "proc_name";
            filterFieldName[0]="user_name";
            filterFieldValue[0]=userName;
            filterFieldName[1]=FIELDVALUE_ACTIVE;
            filterFieldValue[1]=true;
            filterFieldName[2]="proc_name is not null";            
            if (!Rdbms.stablishDBConection()){return new Object[0];}   
            Object[][] userProc =  Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_APP, tableName, filterFieldName, filterFieldValue, fieldsToReturn);            
            return LPArray.array2dTo1d(userProc);                         
    }
    
    /**
     *
     * @param userName
     * @return
     */
    public Object[] getAppUserProfileFieldValues ( String userName) {
            String[] filterFieldName = new String[3];
            Object[] filterFieldValue = new Object[2];
            String[] fieldsToReturn = new String[1];
            
            fieldsToReturn[0] = "proc_name";
            filterFieldName[0]="user_name";
            filterFieldValue[0]=userName;
            filterFieldName[1]=FIELDVALUE_ACTIVE;
            filterFieldValue[1]=true;
            filterFieldName[2]="proc_name is not null";          
            String tableName = "user_profile";                                  
            
            Object[][] userProc =  Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_APP, tableName, filterFieldName, filterFieldValue, fieldsToReturn);
            return LPArray.array2dTo1d(userProc);                         
        }
        
    /**
     *
     * @param schemaPrefix
     * @param personName
     * @return
     */
    public Object[] getProcedureUserProfileFieldValues ( String schemaPrefix, String personName) {
            String tableName = "person_profile";  
            String[] filterFieldName = new String[3];
            Object[] filterFieldValue = new Object[2];
            String[] fieldsToReturn = new String[1];
            fieldsToReturn[0] = "role_name";
            filterFieldName[0]="person_name";
            filterFieldValue[0]=personName;
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
     * @param personName
     * @return
     */
    public Object[] getProcedureUserProfileFieldValues ( Object[] schemaPrefix, String personName) {
            Object[] totalProcUserProfiles  = new Object[0];          
            
        for (Object schemaPrefix1 : schemaPrefix) {
            String currProcPrefix = schemaPrefix1.toString();
            Object[] currProcUserProfiles =  getProcedureUserProfileFieldValues(currProcPrefix, personName);
            for (Object fn: currProcUserProfiles ){
                totalProcUserProfiles = LPArray.addValueToArray1D(totalProcUserProfiles, fn);}
        }            
            return totalProcUserProfiles;                         
        }                
    // Should not return any role from config and data schemas as those are considered specials, not for business users.
    
}
