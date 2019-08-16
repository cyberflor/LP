/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.audit;

import databases.Rdbms;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPSession;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.stream.JsonGenerator;
import functionalJava.requirement.Requirement;

/**
 * 
 * @author Fran Gomez
 * @version 0.1
 */
public class SampleAudit {
    String classVersion = "0.1";
    public static final String TABLE_NAME_DATA_AUDIT_SAMPLE="sample";
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_PROCEDURE="procedure";
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_PROCEDURE_VERSION="procedure_version";
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_ACTION_NAME="action_name";
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_NAME="table_name";
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_ID="table_id";
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_SAMPLE_ID="sample_id";
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_TEST_ID="test_id";
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_RESULT_ID="result_id";        
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_FIELDS_UPDATED="fields_updated";
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_USER_ROLE="user_role";
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_PERSON="person";
        public static final String FIELD_NAME_DATA_AUDIT_SAMPLE_TRANSACTION_ID="transaction_id";
        

/**
 * Add one record in the audit table when altering any of the levels belonging to the sample structure when not linked to any other statement.
 * @param schemaPrefix String - Procedure Name
 * @param action String - Action being performed
 * @param tableName String - table where the action was performed into the Sample structure
 * @param tableId Integer - Id for the object where the action was performed.
 * @param sampleId Integer - sampleId
 * @param testId Integer - testId
 * @param resultId Integer - resultId
 * @param auditlog Object[] - All data that should be stored in the audit as part of the action being performed
 * @param userName String - User who performed the action.
 * @param appSessionId
 * @param userRole String - User Role in use when the action was performed. 
 */    
    public void sampleAuditAdd(String schemaPrefix, String action, String tableName, Integer tableId, 
                        Integer sampleId, Integer testId, Integer resultId, Object[] auditlog, String userName, String userRole, Integer appSessionId) {
        
        String auditTableName = TABLE_NAME_DATA_AUDIT_SAMPLE;
        String schemaName = LPPlatform.SCHEMA_DATA_AUDIT;                
        
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);                
        
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        Object[][] procedureInfo = Requirement.getProcedureBySchemaPrefix(schemaPrefix);
        if (!(procedureInfo[0][3].toString().equalsIgnoreCase(LPPlatform.LAB_FALSE))){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PROCEDURE);
            fieldValues = LPArray.addValueToArray1D(fieldValues, procedureInfo[0][0]);
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PROCEDURE_VERSION);
            fieldValues = LPArray.addValueToArray1D(fieldValues, procedureInfo[0][1]);        
        }        
        
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_ACTION_NAME);
        fieldValues = LPArray.addValueToArray1D(fieldValues, action);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_NAME);
        fieldValues = LPArray.addValueToArray1D(fieldValues, tableName);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_ID);
        fieldValues = LPArray.addValueToArray1D(fieldValues, tableId);
        if (sampleId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_SAMPLE_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, sampleId);
        }    
        if (testId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TEST_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, testId);
        }    
        if (resultId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_RESULT_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, resultId);
        }    
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_FIELDS_UPDATED);
        fieldValues = LPArray.addValueToArray1D(fieldValues, Arrays.toString(auditlog));
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_USER_ROLE);
        fieldValues = LPArray.addValueToArray1D(fieldValues, userRole);

        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PERSON);
        fieldValues = LPArray.addValueToArray1D(fieldValues, userName);
        if (appSessionId!=null){
            Object[] appSession = LPSession.addProcessSession(schemaName, appSessionId, new String[]{"date_started"});
        
    //        Object[] appSession = labSession.getAppSession(appSessionId, new String[]{"date_started"});
            if ("LABPLANET_FALSE".equalsIgnoreCase(appSession[0].toString())){
                return;
            }else{

                fieldNames = LPArray.addValueToArray1D(fieldNames, "app_session_id");
                fieldValues = LPArray.addValueToArray1D(fieldValues, appSessionId);            
            }
        }
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TRANSACTION_ID);
        fieldValues = LPArray.addValueToArray1D(fieldValues, Rdbms.getTransactionId());            
        
/*        String jsonString = null;
        jsonString = sampleJsonString(schemaPrefix+"-data", sampleId);
        if ((jsonString!=null)){
        //if (!jsonString.isEmpty()){
            fieldNames = LPArray.addValueToArray1D(fieldNames, "picture_after");
            fieldValues = LPArray.addValueToArray1D(fieldValues, jsonString);            
        }
*/        
//        fieldNames = LPArray.addValueToArray1D(fieldNames, "user");
//        fieldValues = LPArray.addValueToArray1D(fieldValues, userName);        

           Rdbms.insertRecordInTable(schemaName, auditTableName, fieldNames, fieldValues);
    }

    public void sampleAuditAdd(String schemaPrefix, String action, String tableName, Integer tableId, Integer aliquotId, Integer sampleId, Integer testId, Integer resultId, Object[] auditlog, String userName, String userRole, Integer sessionId) {
        String auditTableName = TABLE_NAME_DATA_AUDIT_SAMPLE;        
        String schemaName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA_AUDIT);                
        
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        Object[][] procedureInfo = Requirement.getProcedureBySchemaPrefix(schemaPrefix);
        if (!(procedureInfo[0][3].toString().equalsIgnoreCase(LPPlatform.LAB_FALSE))){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PROCEDURE);
            fieldValues = LPArray.addValueToArray1D(fieldValues, procedureInfo[0][0]);
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PROCEDURE_VERSION);
            fieldValues = LPArray.addValueToArray1D(fieldValues, procedureInfo[0][1]);        
        }        
        
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_ACTION_NAME);
        fieldValues = LPArray.addValueToArray1D(fieldValues, action);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_NAME);
        fieldValues = LPArray.addValueToArray1D(fieldValues, tableName);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_ID);
        fieldValues = LPArray.addValueToArray1D(fieldValues, tableId);
        if (sessionId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, "session_id");
            fieldValues = LPArray.addValueToArray1D(fieldValues, sessionId);
        }    
        if (aliquotId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, "aliquot_id");
            fieldValues = LPArray.addValueToArray1D(fieldValues, aliquotId);
        }    
        if (sampleId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_SAMPLE_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, sampleId);
        }    
        if (testId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TEST_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, testId);
        }    
        if (resultId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_RESULT_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, resultId);
        }    
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_FIELDS_UPDATED);
        fieldValues = LPArray.addValueToArray1D(fieldValues, Arrays.toString(auditlog));
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_USER_ROLE);
        fieldValues = LPArray.addValueToArray1D(fieldValues, userRole);

        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PERSON);
        fieldValues = LPArray.addValueToArray1D(fieldValues, userName);
        
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TRANSACTION_ID);
        fieldValues = LPArray.addValueToArray1D(fieldValues, Rdbms.getTransactionId());            
                
/*        String jsonString = null;
        jsonString = sampleJsonString(schemaPrefix+"-data", sampleId);
        if ((jsonString!=null)){
        //if (!jsonString.isEmpty()){
            fieldNames = LPArray.addValueToArray1D(fieldNames, "picture_after");
            fieldValues = LPArray.addValueToArray1D(fieldValues, jsonString);            
        }
*/        
//        fieldNames = LPArray.addValueToArray1D(fieldNames, "user");
//        fieldValues = LPArray.addValueToArray1D(fieldValues, userName);        
           Rdbms.insertRecordInTable(schemaName, auditTableName, fieldNames, fieldValues);
    }

    public void sampleAuditAdd( String schemaPrefix, String action, String tableName, Integer tableId, Integer subaliquotId, Integer aliquotId, Integer sampleId, Integer testId, Integer resultId, Object[] auditlog, String userName, String userRole, Integer sessionId) {
        String auditTableName = TABLE_NAME_DATA_AUDIT_SAMPLE;
        String schemaName = LPPlatform.SCHEMA_DATA_AUDIT;                
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);                
        
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        Object[][] procedureInfo = Requirement.getProcedureBySchemaPrefix(schemaPrefix);
        if (!(procedureInfo[0][3].toString().equalsIgnoreCase(LPPlatform.LAB_FALSE))){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PROCEDURE);
            fieldValues = LPArray.addValueToArray1D(fieldValues, procedureInfo[0][0]);
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PROCEDURE_VERSION);
            fieldValues = LPArray.addValueToArray1D(fieldValues, procedureInfo[0][1]);        
        }        
        
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_ACTION_NAME);
        fieldValues = LPArray.addValueToArray1D(fieldValues, action);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_NAME);
        fieldValues = LPArray.addValueToArray1D(fieldValues, tableName);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_ID);
        fieldValues = LPArray.addValueToArray1D(fieldValues, tableId);
        if (sessionId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, "session_id");
            fieldValues = LPArray.addValueToArray1D(fieldValues, sessionId);
        }    
        if (subaliquotId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, "subaliquot_id");
            fieldValues = LPArray.addValueToArray1D(fieldValues, subaliquotId);
        }    
        if (aliquotId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, "aliquot_id");
            fieldValues = LPArray.addValueToArray1D(fieldValues, aliquotId);
        }    
        if (sampleId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_SAMPLE_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, sampleId);
        }    
        if (testId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TEST_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, testId);
        }    
        if (resultId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_RESULT_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, resultId);
        }    
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_FIELDS_UPDATED);
        fieldValues = LPArray.addValueToArray1D(fieldValues, Arrays.toString(auditlog));
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_USER_ROLE);
        fieldValues = LPArray.addValueToArray1D(fieldValues, userRole);

        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PERSON);
        fieldValues = LPArray.addValueToArray1D(fieldValues, userName);
        
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TRANSACTION_ID);
        fieldValues = LPArray.addValueToArray1D(fieldValues, Rdbms.getTransactionId());            
                
/*        String jsonString = null;
        jsonString = sampleJsonString(schemaPrefix+"-data", sampleId);
        if ((jsonString!=null)){
        //if (!jsonString.isEmpty()){
            fieldNames = LPArray.addValueToArray1D(fieldNames, "picture_after");
            fieldValues = LPArray.addValueToArray1D(fieldValues, jsonString);            
        }
*/        
//        fieldNames = LPArray.addValueToArray1D(fieldNames, "user");
//        fieldValues = LPArray.addValueToArray1D(fieldValues, userName);        
           Rdbms.insertRecordInTable(schemaName, auditTableName, fieldNames, fieldValues);
    }
    
    
    public void sampleAuditAdd( String schemaPrefix, String action, String tableName, Integer tableId, Integer sampleId, Integer testId, Integer resultId, Object[] auditlog, String userName, String userRole) {
        String auditTableName = TABLE_NAME_DATA_AUDIT_SAMPLE;
        String schemaName = LPPlatform.SCHEMA_DATA_AUDIT;                
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);                
        
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        Object[][] procedureInfo = Requirement.getProcedureBySchemaPrefix(schemaPrefix);
        if (!(procedureInfo[0][3].toString().equalsIgnoreCase(LPPlatform.LAB_FALSE))){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PROCEDURE);
            fieldValues = LPArray.addValueToArray1D(fieldValues, procedureInfo[0][0]);
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PROCEDURE_VERSION);
            fieldValues = LPArray.addValueToArray1D(fieldValues, procedureInfo[0][1]);        
        }        
        
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_ACTION_NAME);
        fieldValues = LPArray.addValueToArray1D(fieldValues, action);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_NAME);
        fieldValues = LPArray.addValueToArray1D(fieldValues, tableName);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_ID);
        fieldValues = LPArray.addValueToArray1D(fieldValues, tableId);
        if (sampleId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_SAMPLE_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, sampleId);
        }    
        if (testId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TEST_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, testId);
        }    
        if (resultId!=null){
            fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_RESULT_ID);
            fieldValues = LPArray.addValueToArray1D(fieldValues, resultId);
        }    
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_FIELDS_UPDATED);
        fieldValues = LPArray.addValueToArray1D(fieldValues, Arrays.toString(auditlog));
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_USER_ROLE);
        fieldValues = LPArray.addValueToArray1D(fieldValues, userRole);

        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_PERSON);
        fieldValues = LPArray.addValueToArray1D(fieldValues, userName);
        
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TRANSACTION_ID);
        fieldValues = LPArray.addValueToArray1D(fieldValues, Rdbms.getTransactionId());            
                
/*        String jsonString = null;
        jsonString = sampleJsonString(schemaPrefix+"-data", sampleId);
        if ((jsonString!=null)){
        //if (!jsonString.isEmpty()){
            fieldNames = LPArray.addValueToArray1D(fieldNames, "picture_after");
            fieldValues = LPArray.addValueToArray1D(fieldValues, jsonString);            
        }
*/        
//        fieldNames = LPArray.addValueToArray1D(fieldNames, "user");
//        fieldValues = LPArray.addValueToArray1D(fieldValues, userName);        
           Rdbms.insertRecordInTable(schemaName, auditTableName, fieldNames, fieldValues);
    }

 /**
 * Not recommended. reduced version of
 * @param schemaPrefix String - Procedure Name
 * @param action String - Action being performed
 * @param tableName String - table where the action was performed into the Sample structure
 * @param tableId Integer - Id for the object where the action was performed.
 * @param auditlog Object[] - All data that should be stored in the audit as part of the action being performed
 * @param userName String - User who performed the action.
 */    
    public void sampleAuditAdd( String schemaPrefix, String action, String tableName, Integer tableId, Object[] auditlog, String userName){
        String auditTableName = TABLE_NAME_DATA_AUDIT_SAMPLE;
        String schemaName = LPPlatform.SCHEMA_DATA_AUDIT;
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);                
        
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_ACTION_NAME);
        fieldValues = LPArray.addValueToArray1D(fieldValues, action);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_NAME);
        fieldValues = LPArray.addValueToArray1D(fieldValues, tableName);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TABLE_ID);
        fieldValues = LPArray.addValueToArray1D(fieldValues, tableId);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_SAMPLE_ID);
        fieldValues = LPArray.addValueToArray1D(fieldValues, tableId);     
        
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELD_NAME_DATA_AUDIT_SAMPLE_TRANSACTION_ID);
        fieldValues = LPArray.addValueToArray1D(fieldValues, Rdbms.getTransactionId());            
        
//        fieldNames = LPArray.addValueToArray1D(fieldNames, "user");
//        fieldValues = LPArray.addValueToArray1D(fieldValues, userName);        
           Rdbms.insertRecordInTable(schemaName, auditTableName, fieldNames, fieldValues);
    }

/**
 * Creates the structure representation in a way of sample-analysis-results.
 * @param schemaPrefix String - Procedure Name
 * @param sampleId Integer - sampleId
 * @return 
 */    
    public String _sampleJsonString( String schemaPrefix, Integer sampleId) {
        // Not implemented yet
        String jsonStructure = null;
               
        String schemaName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);  
        String[] sampleTblFldsArr = Rdbms.getTableFieldsArrayEj(schemaName.replace("\"", ""), TABLE_NAME_DATA_AUDIT_SAMPLE);
        String sampleTblFlds = Rdbms.getTableFieldsArrayEj(schemaName.replace("\"", ""), TABLE_NAME_DATA_AUDIT_SAMPLE, ",", true);
//        String sampleAnalysisTblFlds = Rdbms.getTableFieldsArrayEj(schemaName.replace("\"", ""), "sample_analysis", ",", true);
//        String sampleAnalysisResultTblFlds = Rdbms.getTableFieldsArrayEj(schemaName.replace("\"", ""), "sample_analysis_result", ",", true);
        
        FileWriter writer = null;
        try {
                writer = new FileWriter("C:\\home\\judas\\logs\\sample.txt");
            } catch (IOException e) { return ""; }
        try (JsonGenerator gen = Json.createGenerator(writer)) {
//            String query = " select " + sampleTblFlds + " from " + schemaName + ".sample where sample_id="+sampleId;            
//            Object[][] recordFieldsByFilter = Rdbms.getRecordFieldsByFilter(schemaName, TABLE_NAME_DATA_AUDIT_SAMPLE new String[]{FIELD_NAME_DATA_AUDIT_SAMPLE_SAMPLE_ID}, new Object[]{sampleId}, sampleTblFldsArr);
            //gen.writeStartObject().write(TABLE_NAME_DATA_AUDIT_SAMPLE, sampleId.toString());
            // gen.writeStartArray("Sample Info");
            gen.writeStartObject();
            for (Integer iFields=0; iFields< sampleTblFldsArr.length;iFields++){
                String currValue = "y";            
                //if (recordFieldsByFilter[0][iFields]==null){currValue = "null";}
                //else{currValue=recordFieldsByFilter[0][iFields].toString();}
                //gen.writeStartObject().write(TABLE_NAME_DATA_AUDIT_SAMPLE, sampleId.toString()).write(sampleTblFldsArr[iFields],"X");
                try{
                    gen.writeStartArray().write(sampleTblFldsArr[iFields], currValue);
                }catch (JsonException ex){
//                    String m = ex.getMessage();
                }
                //gen.write(sampleTblFldsArr[iFields], (String) recordFieldsByFilter[0][iFields]);
            }
            gen.writeEnd();
//            gen.writeStartObject().write(FIELD_NAME_DATA_AUDIT_SAMPLE_SAMPLE_ID, sampleId.toString());
//            gen.writeEnd();
        }   
    
/*        query = "  select " + sampleTblFlds + ", " + sampleAnalysisTblFlds + ", " + sampleAnalysisResultTblFlds
                + "  from " + schemaName + ".sample "
                +       "left outer join " + schemaName + ".sample_analysis on sample_analysis.sample_id=sample.sample_id "
                +       "left outer join " + schemaName + ".sample_analysis_result on sample_analysis_result.test_id=sample_analysis.test_id "
                + " where sample.sample_id="+sampleId;

        JSONObject object = new JSONObject();
object.element("name", TABLE_NAME_DATA_AUDIT_SAMPLE);
JSONArray array = new JSONArray();

JSONObject arrayElementOne = new JSONObject();
arrayElementOne.element("setId", 1);
JSONArray arrayElementOneArray = new JSONArray();

JSONObject arrayElementOneArrayElementOne = new JSONObject();
arrayElementOneArrayElementOne.element("name", "ABC");
arrayElementOneArrayElementOne.element("type", "STRING");

JSONObject arrayElementOneArrayElementTwo = new JSONObject();
arrayElementOneArrayElementTwo.element("name", "XYZ");
arrayElementOneArrayElementTwo.element("type", "STRING");

arrayElementOneArray.add(arrayElementOneArrayElementOne);
arrayElementOneArray.add(arrayElementOneArrayElementTwo);

arrayElementOne.element("setDef", arrayElementOneArray);
object.element("def", array);


        jsonStructure = Rdbms.querytoJSON(query);
*/
    return jsonStructure;
            
    }
/**
 * Under development!!!! Example on how to build a json structure using JsonGenerator.
 */    
    public void _jsonToFile(){
        // Not implemented yet
        String typeString = "STRING";
        FileWriter writer = null;
            try {
                writer = new FileWriter("C:\\home\\judas\\logs\\sample.txt");
            } catch (IOException e) {     return;       }
        try (JsonGenerator gen = Json.createGenerator(writer)) {
            gen.writeStartObject(TABLE_NAME_DATA_AUDIT_SAMPLE).write(FIELD_NAME_DATA_AUDIT_SAMPLE_SAMPLE_ID, "Sample Audit Info")
                    .writeStartArray("def");
            
            gen.writeStartObject().write("name", TABLE_NAME_DATA_AUDIT_SAMPLE)
                    .writeStartArray("def")
                    .writeStartObject().write("setId", 1)
                    .writeStartArray("setDef")
                    .writeStartObject().write("name", "ABC").write("type", typeString)
                    .writeEnd()
                    .writeStartObject().write("name", "XYZ").write("type", typeString)
                    .writeEnd()
                    .writeEnd()
                    .writeEnd()
                    .writeStartObject().write("setId", 2)
                    .writeStartArray("setDef")
                    .writeStartObject().write("name", "abc").write("type", typeString)
                    .writeEnd()
                    .writeStartObject().write("name", "xyz").write("type", typeString)
                    .writeEnd()
                    .writeEnd()
                    .writeEnd()    
                    .writeEnd()
                    .writeEnd();
        }
        }        
}
