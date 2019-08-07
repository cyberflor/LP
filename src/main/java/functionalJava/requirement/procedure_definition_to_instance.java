/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.requirement;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPJson;
import LabPLANET.utilities.LPPlatform;
import databases.Rdbms;
import static functionalJava.requirement.RequirementLogFile.requirementsLogEntry;
import java.util.Arrays;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Administrator
 */
public class procedure_definition_to_instance {
    private procedure_definition_to_instance(){    throw new IllegalStateException("Utility class");}
    
    public static final String LABEL_FOR_NO = "No";
    public static final String LABEL_FOR_YES = "Yes";

    public static final String TABLE_NAME_APP_USERS = "users";
        public static final String FLD_NAME_APP_USERS_USER_NAME="user_name";
        public static final String FLD_NAME_APP_USERS_PERSON_NAME="person_name";
    public static final String TABLE_NAME_APP_USER_PROCESS = "user_process";
        public static final String FLD_NAME_APP_USER_PROCESS_USER_NAME="user_name";
        public static final String FLD_NAME_APP_USER_PROCESS_PROC_NAME="proc_name";
        public static final String FLD_NAME_APP_USER_PROCESS_ACTIVE="active";
    public static final String TABLE_NAME_PROCEDURE = "procedure_info";
        public static final String FLD_NAME_PROCEDURE_NAME="name";
        public static final String FLD_NAME_PROCEDURE_VERSION="version";
        public static final String FLD_NAME_PROCEDURE_SCHEMA_PREFIX="schema_prefix";
    public static final String TABLE_NAME_PROCEDURE_USER_ROLE_SOURCE = "procedure_user_role";    
        public static final String FLD_NAME_PROCEDURE_USER_ROLE_NAME="procedure_name";
        public static final String FLD_NAME_PROCEDURE_USER_ROLE_VERSION="procedure_version";
        public static final String FLD_NAME_PROCEDURE_USER_ROLE_SCHEMA_PREFIX="schema_prefix";
        public static final String FLD_NAME_PROCEDURE_USER_ROLE_USER_NAME="user_name";
        public static final String FLD_NAME_PROCEDURE_USER_ROLE_ROLE_NAME="role_name";
    public static final String TABLE_NAME_PROCEDURE_SOP_META_DATA = "procedure_sop_meta_data";  
        public static final String FLD_NAME_PROCEDURE_SOP_META_DATA_SOP_ID="sop_id";
        public static final String FLD_NAME_PROCEDURE_SOP_META_DATA_SOP_NAME="sop_name";
    public static final String TABLE_NAME_PROC_EVENT_DESTINATION = "procedure_events";   
        public static final String FLD_NAME_PROC_EVENT_NAME="name";
        public static final String FLD_NAME_PROC_EVENT_ROLE_NAME="role_name";
        public static final String FLD_NAME_PROC_EVENT_SOP_NAME="sop";
    public static final String TABLE_NAME_SOP_META_DATA_DESTINATION = "sop_meta_data";   
    public static final String TABLE_NAME_PERSON_PROFILE_DESTINATION = "person_profile";    
        public static final String FLD_NAME_PERSON_PROFILE_ROLE_NAME="role_name";
        public static final String FLD_NAME_PERSON_PROFILE_PERSON_NAME="person_name";
    public static final String SCHEMA_AUTHORIZATION_ROLE = "labplanet";
    
    public static final String FIELDS_TO_RETRIEVE_PROCEDURE_INFO_SOURCE="name|version|label_en|label_es";
    public static final String FIELDS_TO_RETRIEVE_PROCEDURE_USER_ROLE_SOURCE="user_name|role_name";
    public static final String FIELDS_TO_RETRIEVE_PROCEDURE_USER_ROLE_SORT="user_name";
    public static final String FIELDS_TO_INSERT_APP_USER_PROCESS=FLD_NAME_APP_USER_PROCESS_USER_NAME+"|"+FLD_NAME_APP_USER_PROCESS_PROC_NAME+"|"+FLD_NAME_APP_USER_PROCESS_ACTIVE;
    public static final String FIELDS_TO_INSERT_PROCEDURE_USER_ROLE_DESTINATION="person_name|role_name|active";
    //public static final String FIELDS_TO_INSERT_PROCEDURE_INFO_DESTINATION="name|version|label_en|label_es";
    public static final String FIELDS_TO_RETRIEVE_PROCEDURE_SOP_META_DATA_SOURCE="sop_id|sop_name|sop_version|sop_revision|current_status|expires|has_child|file_link|brief_summary";
    public static final String FIELDS_TO_RETRIEVE_PROCEDURE_SOP_META_DATA_SORT="sop_id";
    public static final String FIELDS_TO_INSERT_PROCEDURE_SOP_META_DATA_DESTINATION="person_name|role_name|active";
    public static final String FIELDS_TO_RETRIEVE_PROC_EVENT_DESTINATION="name|role_name|sop";

    public static final JSONObject createDBProcedureInfo(String procedure,  Integer procVersion, String schemaPrefix){
        JSONObject jsonObj = new JSONObject();
        String schemaNameDestination=LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);
         Object[][] procInfoRecordsSource = Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_REQUIREMENTS, TABLE_NAME_PROCEDURE, 
                new String[]{FLD_NAME_PROCEDURE_NAME, FLD_NAME_PROCEDURE_VERSION,FLD_NAME_PROCEDURE_SCHEMA_PREFIX}, new Object[]{procedure, procVersion, schemaPrefix}, 
                FIELDS_TO_RETRIEVE_PROCEDURE_INFO_SOURCE.split("\\|"), null);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(procInfoRecordsSource[0][0].toString())){
          jsonObj.put("Error", LPJson.convertToJSON(procInfoRecordsSource));
        }else{
            jsonObj.put("Num Records in definition", procInfoRecordsSource.length);
            for (Object[] curRow: procInfoRecordsSource){
                Object[][] procInfoRecordsDestination = Rdbms.getRecordFieldsByFilter(schemaNameDestination, TABLE_NAME_PROCEDURE, 
                       new String[]{FLD_NAME_PROCEDURE_NAME, FLD_NAME_PROCEDURE_VERSION}, new Object[]{procedure, procVersion}, 
                       FIELDS_TO_RETRIEVE_PROCEDURE_INFO_SOURCE.split("\\|"), null);
                if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(procInfoRecordsDestination[0][0].toString())){
                    jsonObj.put("Record in the instance", "Already exists");
                }else{
                    jsonObj.put("Record in instance", "Not exists");
                    Object[] insertRecordInTable = Rdbms.insertRecordInTable(schemaNameDestination, TABLE_NAME_PROCEDURE, FIELDS_TO_RETRIEVE_PROCEDURE_INFO_SOURCE.split("\\|"), curRow);
                    jsonObj.put("Record in the instance inserted?", insertRecordInTable[0].toString());
                    //if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(insertRecordInTable[0].toString())){}
                }
            }
        }
        return jsonObj;
    }    
    public static final  JSONObject createDBProcedureEvents(String procedure,  Integer procVersion, String schemaPrefix){
        return new JSONObject();
/*        String schemaNameDestination=LPPlatform.buildSchemaName(LPPlatform.SCHEMA_APP, LPPlatform.SCHEMA_CONFIG);
         Object[][] procInfoRecordsSource = Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_REQUIREMENTS, TABLE_NAME_PROCEDURE, 
                new String[]{FLD_NAME_PROCEDURE_NAME, FLD_NAME_PROCEDURE_VERSION,FLD_NAME_PROCEDURE_SCHEMA_PREFIX}, new Object[]{procedure, procVersion, schemaPrefix}, 
                FIELDS_TO_RETRIEVE_PROCEDURE_INFO_SOURCE.split("\\|"), null);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(procInfoRecordsSource[0][0].toString())){
          jsonObj.put("Error", LPJson.convertToJSON(procInfoRecordsSource));
        }else{
            
        }    */            
    }
    public static final  JSONObject createDBPersonProfiles(String procedure,  Integer procVersion, String schemaPrefix){
        JSONObject jsonObj = new JSONObject();
        String schemaNameDestination=LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);
         Object[][] procUserRolesRecordsSource = Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_REQUIREMENTS, TABLE_NAME_PROCEDURE_USER_ROLE_SOURCE, 
                new String[]{FLD_NAME_PROCEDURE_USER_ROLE_NAME, FLD_NAME_PROCEDURE_USER_ROLE_VERSION,FLD_NAME_PROCEDURE_USER_ROLE_SCHEMA_PREFIX}, new Object[]{procedure, procVersion, schemaPrefix}, 
                FIELDS_TO_RETRIEVE_PROCEDURE_USER_ROLE_SOURCE.split("\\|"), FIELDS_TO_RETRIEVE_PROCEDURE_USER_ROLE_SORT.split("\\|"));
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(procUserRolesRecordsSource[0][0].toString())){
          jsonObj.put("Error", LPJson.convertToJSON(procUserRolesRecordsSource));
          return jsonObj;
        }
        jsonObj.put("Num Records in definition", procUserRolesRecordsSource.length);    
        for (Object[] curRow: procUserRolesRecordsSource){
            Object curUserName = curRow[LPArray.valuePosicInArray(FIELDS_TO_RETRIEVE_PROCEDURE_USER_ROLE_SOURCE.split("\\|"), FLD_NAME_PROCEDURE_USER_ROLE_USER_NAME)];
            Object curRoleName = curRow[LPArray.valuePosicInArray(FIELDS_TO_RETRIEVE_PROCEDURE_USER_ROLE_SOURCE.split("\\|"), FLD_NAME_PROCEDURE_USER_ROLE_ROLE_NAME)];
            JSONArray jsArr = new JSONArray(); JSONObject jsUserRoleObj = new JSONObject();
            jsUserRoleObj.put("User", curUserName); jsUserRoleObj.put("Role", curRoleName);

            Object[][] existsAppUser = Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_APP, TABLE_NAME_APP_USERS, 
                    new String[]{FLD_NAME_APP_USERS_USER_NAME}, new Object[]{curUserName.toString()}, new String[]{FLD_NAME_APP_USERS_PERSON_NAME}, null);
            String diagnosesForLog = (LPPlatform.LAB_FALSE.equalsIgnoreCase(existsAppUser[0][0].toString())) ? LABEL_FOR_NO : LABEL_FOR_YES;
            jsUserRoleObj.put("User exists in the app?", diagnosesForLog); 
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(existsAppUser[0][0].toString())){
                // Place to create the user
            }                
            if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(Arrays.toString(existsAppUser[0]))){
                Object[] existsAppUserProcess = Rdbms.existsRecord(LPPlatform.SCHEMA_APP, TABLE_NAME_APP_USER_PROCESS, 
                        new String[]{FLD_NAME_APP_USER_PROCESS_USER_NAME,FLD_NAME_APP_USER_PROCESS_PROC_NAME}, new Object[]{curUserName.toString(), schemaPrefix});
                jsonObj.put("User was added to the Process at the App level?", existsAppUserProcess[0].toString());  
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(existsAppUserProcess[0].toString())){
                    Object[] insertRecordInTable = Rdbms.insertRecordInTable(LPPlatform.SCHEMA_APP, TABLE_NAME_APP_USER_PROCESS, 
                            FIELDS_TO_INSERT_APP_USER_PROCESS.split("\\|"), new Object[]{curUserName.toString(), schemaPrefix, true});
                    jsonObj.put("Added the User to the Process at the App level by running this utility?", insertRecordInTable[0].toString());                                                                
                }
            }
                    //
            Object curPersonName = existsAppUser[0][0];                
            if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(existsAppUser[0][0].toString())){
                Object[] insertRecordInTable = Rdbms.insertRecordInTable(schemaNameDestination, TABLE_NAME_PERSON_PROFILE_DESTINATION, 
                        FIELDS_TO_INSERT_PROCEDURE_USER_ROLE_DESTINATION.split("\\|"), new Object[]{curPersonName.toString(), curRoleName.toString(), true});
                jsonObj.put("User Role inserted in the instance?", insertRecordInTable[0].toString());                    
            }
            jsArr.add(jsUserRoleObj);
            jsonObj.put("User "+curUserName+ " & Role "+curRoleName, jsArr);
        }                            
        return jsonObj;
    }
    public static final  JSONObject createDBSopMetaDataAndUserSop(String procedure,  Integer procVersion, String schemaPrefix){
        JSONObject jsonObj = new JSONObject();
        String schemaNameDestination=LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);
        
         Object[][] procSopMetaDataRecordsSource = Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_REQUIREMENTS, TABLE_NAME_PROCEDURE_SOP_META_DATA, 
                new String[]{FLD_NAME_PROCEDURE_USER_ROLE_NAME, FLD_NAME_PROCEDURE_USER_ROLE_VERSION,FLD_NAME_PROCEDURE_USER_ROLE_SCHEMA_PREFIX}, new Object[]{procedure, procVersion, schemaPrefix}, 
                FIELDS_TO_RETRIEVE_PROCEDURE_SOP_META_DATA_SOURCE.split("\\|"), FIELDS_TO_RETRIEVE_PROCEDURE_SOP_META_DATA_SORT.split("\\|"));
                //new String[]{"*"}, new String[]{"sop_id"});
        
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(procSopMetaDataRecordsSource[0][0].toString())){
          jsonObj.put("Error", LPJson.convertToJSON(procSopMetaDataRecordsSource));
          return jsonObj;
        }
        jsonObj.put("Num Records in definition", procSopMetaDataRecordsSource.length);        
        for (Object[] curSopMetaData: procSopMetaDataRecordsSource){
            Object curSopId = curSopMetaData[LPArray.valuePosicInArray(FIELDS_TO_RETRIEVE_PROCEDURE_SOP_META_DATA_SOURCE.split("\\|"), FLD_NAME_PROCEDURE_SOP_META_DATA_SOP_ID)];
            Object curSopName = curSopMetaData[LPArray.valuePosicInArray(FIELDS_TO_RETRIEVE_PROCEDURE_SOP_META_DATA_SOURCE.split("\\|"), FLD_NAME_PROCEDURE_SOP_META_DATA_SOP_NAME)];
            JSONArray jsArr = new JSONArray(); JSONObject jsUserRoleObj = new JSONObject();
            jsUserRoleObj.put("SOP Id", curSopId); jsUserRoleObj.put("SOP Name", curSopName);

            Object[][] existsAppUser = Rdbms.getRecordFieldsByFilter(schemaNameDestination, TABLE_NAME_SOP_META_DATA_DESTINATION, 
                    new String[]{FLD_NAME_PROCEDURE_SOP_META_DATA_SOP_NAME}, new Object[]{curSopName.toString()}, new String[]{FLD_NAME_PROCEDURE_SOP_META_DATA_SOP_NAME}, null);
            String diagnosesForLog = (LPPlatform.LAB_FALSE.equalsIgnoreCase(existsAppUser[0][0].toString())) ? LABEL_FOR_NO : LABEL_FOR_YES;
            jsUserRoleObj.put("SOP exists in the procedure?", diagnosesForLog); 
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(existsAppUser[0][0].toString())){
                Object[] insertRecordInTable = Rdbms.insertRecordInTable(schemaNameDestination, TABLE_NAME_SOP_META_DATA_DESTINATION, 
                        FIELDS_TO_RETRIEVE_PROCEDURE_SOP_META_DATA_SOURCE.split("\\|"), curSopMetaData);
                diagnosesForLog = (LPPlatform.LAB_FALSE.equalsIgnoreCase(insertRecordInTable[0].toString())) ? LABEL_FOR_NO : LABEL_FOR_YES;
                jsonObj.put("SOP inserted in the instance?", diagnosesForLog);
                //if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(insertRecordInTable[0].toString())){}
            }                         
            jsArr.add(jsUserRoleObj);
            jsonObj.put("SOP Id "+curSopId+ " & SOP Name "+curSopName, jsArr);            
        }        
        return jsonObj;
    }
    public static final  JSONObject addProcedureSOPtoUsers(String procedure,  Integer procVersion, String schemaPrefix){
        JSONObject jsonObj = new JSONObject();
        String schemaNameDestination=LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);
        Object[][] procEventSopsRecordsSource = Rdbms.getRecordFieldsByFilter(schemaNameDestination, TABLE_NAME_PROC_EVENT_DESTINATION, 
                new String[]{FLD_NAME_PROC_EVENT_SOP_NAME+" is not null"}, new Object[]{""}, 
                FIELDS_TO_RETRIEVE_PROC_EVENT_DESTINATION.split("\\|"), new String[]{"sop"});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(procEventSopsRecordsSource[0][0].toString())){
          jsonObj.put("Error", LPJson.convertToJSON(procEventSopsRecordsSource));
          return jsonObj;
        }
        jsonObj.put("Num Records in definition", procEventSopsRecordsSource.length);  
        
        String[] existingSopRole = new String[0];
        for (Object[] curProcEventSops: procEventSopsRecordsSource){
            Object curProcEventName = curProcEventSops[LPArray.valuePosicInArray(FIELDS_TO_RETRIEVE_PROC_EVENT_DESTINATION.split("\\|"), FLD_NAME_PROC_EVENT_NAME)];
            Object curSops = curProcEventSops[LPArray.valuePosicInArray(FIELDS_TO_RETRIEVE_PROC_EVENT_DESTINATION.split("\\|"), FLD_NAME_PROC_EVENT_SOP_NAME)];
            Object curRoleName = curProcEventSops[LPArray.valuePosicInArray(FIELDS_TO_RETRIEVE_PROC_EVENT_DESTINATION.split("\\|"), FLD_NAME_PROC_EVENT_ROLE_NAME)];
            JSONArray jsArr = new JSONArray(); JSONObject jsUserRoleObj = new JSONObject();
            jsUserRoleObj.put("Procedure Event", curProcEventName); jsUserRoleObj.put("SOP Name", curSops); jsUserRoleObj.put("Role Name", curRoleName);
            
            String[] curSopsArr = curSops.toString().split("\\|"); 
            String[] curRoleNameArr = curRoleName.toString().split("\\|"); 
            JSONArray jsEventArr = new JSONArray();
            for (String sopFromArr: curSopsArr){         
                JSONArray jsSopRoleArr = new JSONArray();
                for (String roleFromArr: curRoleNameArr){
                    
                    JSONObject jsSopRoleObj = new JSONObject();
                    
                    String sopRoleValue=sopFromArr+"*"+roleFromArr;
                    Integer sopRolePosic = LPArray.valuePosicInArray(existingSopRole, sopRoleValue);
                    String diagnosesForLog = (sopRolePosic==-1) ? LABEL_FOR_NO : LABEL_FOR_YES;
                    jsSopRoleObj.put("SOP "+sopFromArr+" exists for role "+roleFromArr+" ?", diagnosesForLog);
                    if (sopRolePosic==-1){
                        ProcedureDefinitionToInstanceUtility.procedureAddSopToUsersByRole(procedure, procVersion, schemaPrefix, 
                                roleFromArr, sopFromArr, null, null);                        
                    }
                    jsSopRoleArr.add(jsSopRoleObj);
                    existingSopRole=LPArray.addValueToArray1D(existingSopRole, sopRoleValue);
                }
                jsEventArr.add(jsSopRoleArr);
                jsUserRoleObj.put("Event SOPs Log", jsEventArr);
            }
            jsArr.add(jsUserRoleObj); 
            jsonObj.put("Procedure Event "+curProcEventName+ " & SOP Name "+curSops+ " & Role Name "+curRoleName, jsArr);   
        }       
        return jsonObj;
    }
    
    
    public static final  JSONObject createDBSchemas(String schemaNamePrefix){
        JSONObject jsonObj = new JSONObject();
        
        String methodName = "createDataBaseSchemas";       
        String newEntry = "";
        String[] schemaNames = new String[]{LPPlatform.SCHEMA_CONFIG, LPPlatform.SCHEMA_DATA, LPPlatform.SCHEMA_DATA_AUDIT};
         jsonObj.put("Num Records in definition", schemaNames.length);     
        for (String fn:schemaNames){
            JSONArray jsSchemaArr = new JSONArray();
            String configSchemaName = schemaNamePrefix+"-"+fn;
            jsSchemaArr.add(configSchemaName);
            requirementsLogEntry("", methodName, configSchemaName,2);
            
            configSchemaName = LPPlatform.buildSchemaName(configSchemaName, fn);
            String configSchemaScript = "CREATE SCHEMA "+configSchemaName+"  AUTHORIZATION "+SCHEMA_AUTHORIZATION_ROLE+";";     
            Integer prepUpQuery = Rdbms.prepUpQuery(configSchemaScript, null);
            String diagnosesForLog = (prepUpQuery==-1) ? LABEL_FOR_NO : LABEL_FOR_YES;
            jsonObj.put("Schema Created?", diagnosesForLog);
            
            jsonObj.put(configSchemaName, jsSchemaArr);
        }
        return jsonObj;
    }    
    
}
