/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.user;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class Role {

    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "role";  
    
    Object[] diagnoses = new Object[7];
    
    public Object[] createRole(Rdbms rdbm, String roleId) throws SQLException {        
                
        if (roleId.toUpperCase().contains("ALL")){            
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR ALL IS SPECIAL WORD";
            diagnoses[5]="The word ALL in roles is an special one, it means this privilege should be added to all the roles present in this procedure.";
            return diagnoses;
        }                
        diagnoses = rdbm.insertRecordInTable(rdbm, "config", "role", new String[]{"role_id", "active"}, new Object[]{roleId, true });
           if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){	
            String newEntry = " for role " + roleId + ". Success, The record is created.";
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="TRUE";
            diagnoses[4]="SUCCESS";
            diagnoses[5]=" for role " + roleId + ". Success, The record is created.";
            return diagnoses;            
        }else{
            String newEntry = " for role " + roleId + ". Success, The record is created.";
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR CREATING ROLE RECORD IN DB";
            diagnoses[5]=diagnoses[5];
            return diagnoses;        
        }
    }    
    
    public Object[] addPrivilegeToRole(Rdbms rdbm, String privilegeId, String roleId, String procName) throws SQLException {
        String methodName = "addPrivilegeToRole";
        Integer id;      
        Integer numRecords = 0;
//        ResultSet resRole = null;
        String newRoleId = "";
        Object[][] resRole = new Object[0][0];
        
        // ALL means assign the privilege to all the sopName present in this procedure.
        if (roleId.toUpperCase().contains("ALL")){   
            String tableName = "role";
            
            resRole = rdbm.getRecordFieldsByFilter(rdbm, procName, tableName, 
                    new String[]{tableName+"_id like %"}, new Object[]{procName}, new String[]{tableName+"_id"});
            numRecords = resRole.length;
/*            String queryRoles = "SELECT " + tableName + "_id from config." + tableName + " where " + tableName + "_id like ?";  
            procName = procName + "%";
            resRole = rdbm.prepRdQuery(queryRoles, new Object [] {procName});
            resRole.last();
            numRecords = resRole.getRow();
            resRole.first();
*/            
//            newRoleId = resRole.getString("role_id");
        }
        else{
            numRecords = 1;
            newRoleId = roleId;
        }
        for (Integer inumRecords=0; inumRecords<numRecords; inumRecords++){
            if (roleId.toUpperCase().contains("ALL")){newRoleId = resRole[inumRecords][0].toString();}
            Object[] diagnoses = rdbm.existsRecord(rdbm, schemaConfigName, "role_privilege", 
                    new String[]{"privilege_id"}, new Object[]{privilegeId + "," + roleId} );
            if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){      
                diagnoses = rdbm.insertRecordInTable(rdbm, "config", "role_privilege", new String[]{"role_id", "privilege_id"}, new Object[]{newRoleId, privilegeId });
                if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                    StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                    diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                    diagnoses[1]= classVersion;
                    diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
                    diagnoses[3]="TRUE";
                    diagnoses[4]="SUCCESS";
                    diagnoses[5]=" for role " + newRoleId + " and privilege " + privilegeId + ". The record created.";
                    //return diagnoses;                                    
                }else{
                    StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                    diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                    diagnoses[1]= classVersion;
                    diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
                    diagnoses[3]="FALSE";
                    diagnoses[4]="ERROR ROLE_PRIVILEGE RECORD CANNOT BE CREATED";
                    diagnoses[5]=diagnoses[5];
                    //return diagnoses;                
                }                        
            }               
//            if (roleId.toUpperCase().contains("ALL")){resRole.next();}            
        }    
    return diagnoses;
    }         

    public Object[] createPrivilege(Rdbms rdbm, String privilegeId) throws SQLException {
/*        String methodName = "createPrivilege";
        Integer id;                
*/        
        diagnoses = rdbm.insertRecordInTable(rdbm, "config", "privilege", new String[]{"privilege_id"}, new Object[]{privilegeId});
        if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="TRUE";
            diagnoses[4]="SUCCESS";
            diagnoses[5]=" for privilege " + privilegeId + ". Success, The record created.";
        }else{
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR PRIVILEGE RECORD NOT CREATED";
            diagnoses[5]=diagnoses[5];
        }
        return diagnoses;
    }   

    
    
}
