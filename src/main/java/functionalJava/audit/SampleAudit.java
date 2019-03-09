/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.audit;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LabPLANETSession;
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
//    
//    LPPlatform labPlat = new LPPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "sample"; 

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
        
        String auditTableName = "sample";
        String schemaName = "data-audit";                
        
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);                
        
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        Requirement req = new Requirement();
        Object[][] procedureInfo = req.getProcedureBySchemaPrefix(schemaPrefix);
        if (!(procedureInfo[0][3].toString().equalsIgnoreCase("FALSE"))){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "procedure");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, procedureInfo[0][0]);
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "procedure_version");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, procedureInfo[0][1]);        
        }        
        
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "action_name");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, action);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "table_name");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, tableName);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "table_id");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, tableId);
        if (sampleId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "sample_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, sampleId);
        }    
        if (testId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "test_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, testId);
        }    
        if (resultId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "result_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, resultId);
        }    
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "fields_updated");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, Arrays.toString(auditlog));
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "user_role");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userRole);

        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "person");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userName);
        if (appSessionId!=null){
            Object[] appSession = LabPLANETSession.addProcessSession(schemaName, appSessionId, new String[]{"date_started"});
        
    //        Object[] appSession = labSession.getAppSession(appSessionId, new String[]{"date_started"});
            if ("LABPLANET_FALSE".equalsIgnoreCase(appSession[0].toString())){

            }else{

                fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "app_session_id");
                fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, appSessionId);            
            }
        }
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "transaction_id");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, Rdbms.getTransactionId());            
        
        String jsonString = null;
/*        jsonString = sampleJsonString(schemaPrefix+"-data", sampleId);
        if ((jsonString!=null)){
        //if (!jsonString.isEmpty()){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "picture_after");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, jsonString);            
        }
*/        
//        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "user");
//        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userName);        

        Object[] diagnoses = Rdbms.insertRecordInTable(schemaName, auditTableName, fieldNames, fieldValues);
        String veredict= diagnoses[0].toString();
    }

    public void sampleAuditAdd(String schemaPrefix, String action, String tableName, Integer tableId, Integer aliquotId, Integer sampleId, Integer testId, Integer resultId, Object[] auditlog, String userName, String userRole, Integer sessionId) {
        String auditTableName = "sample";
        String schemaName = "data-audit";                
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);                
        
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        Requirement req = new Requirement();
        Object[][] procedureInfo = req.getProcedureBySchemaPrefix(schemaPrefix);
        if (!(procedureInfo[0][3].toString().equalsIgnoreCase("FALSE"))){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "procedure");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, procedureInfo[0][0]);
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "procedure_version");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, procedureInfo[0][1]);        
        }        
        
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "action_name");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, action);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "table_name");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, tableName);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "table_id");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, tableId);
        if (sessionId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "session_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, sessionId);
        }    
        if (aliquotId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "aliquot_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, aliquotId);
        }    
        if (sampleId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "sample_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, sampleId);
        }    
        if (testId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "test_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, testId);
        }    
        if (resultId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "result_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, resultId);
        }    
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "fields_updated");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, Arrays.toString(auditlog));
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "user_role");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userRole);

        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "person");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userName);
        
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "transaction_id");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, Rdbms.getTransactionId());            
                
        String jsonString = null;
/*        jsonString = sampleJsonString(schemaPrefix+"-data", sampleId);
        if ((jsonString!=null)){
        //if (!jsonString.isEmpty()){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "picture_after");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, jsonString);            
        }
*/        
//        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "user");
//        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userName);        
        Object[] diagnoses = Rdbms.insertRecordInTable(schemaName, auditTableName, fieldNames, fieldValues);
        String veredict= diagnoses[0].toString();
    }

    public void sampleAuditAdd( String schemaPrefix, String action, String tableName, Integer tableId, Integer subaliquotId, Integer aliquotId, Integer sampleId, Integer testId, Integer resultId, Object[] auditlog, String userName, String userRole, Integer sessionId) {
        String auditTableName = "sample";
        String schemaName = "data-audit";                
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);                
        
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        Requirement req = new Requirement();
        Object[][] procedureInfo = req.getProcedureBySchemaPrefix(schemaPrefix);
        if (!(procedureInfo[0][3].toString().equalsIgnoreCase("FALSE"))){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "procedure");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, procedureInfo[0][0]);
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "procedure_version");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, procedureInfo[0][1]);        
        }        
        
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "action_name");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, action);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "table_name");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, tableName);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "table_id");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, tableId);
        if (sessionId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "session_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, sessionId);
        }    
        if (subaliquotId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "subaliquot_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, subaliquotId);
        }    
        if (aliquotId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "aliquot_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, aliquotId);
        }    
        if (sampleId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "sample_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, sampleId);
        }    
        if (testId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "test_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, testId);
        }    
        if (resultId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "result_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, resultId);
        }    
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "fields_updated");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, Arrays.toString(auditlog));
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "user_role");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userRole);

        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "person");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userName);
        
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "transaction_id");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, Rdbms.getTransactionId());            
                
        String jsonString = null;
/*        jsonString = sampleJsonString(schemaPrefix+"-data", sampleId);
        if ((jsonString!=null)){
        //if (!jsonString.isEmpty()){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "picture_after");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, jsonString);            
        }
*/        
//        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "user");
//        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userName);        
        Object[] diagnoses = Rdbms.insertRecordInTable(schemaName, auditTableName, fieldNames, fieldValues);
        String veredict= diagnoses[0].toString();
    }
    
    
    public void sampleAuditAdd( String schemaPrefix, String action, String tableName, Integer tableId, Integer sampleId, Integer testId, Integer resultId, Object[] auditlog, String userName, String userRole) {
        String auditTableName = "sample";
        String schemaName = "data-audit";                
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);                
        
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        Requirement req = new Requirement();
        Object[][] procedureInfo = req.getProcedureBySchemaPrefix(schemaPrefix);
        if (!(procedureInfo[0][3].toString().equalsIgnoreCase("FALSE"))){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "procedure");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, procedureInfo[0][0]);
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "procedure_version");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, procedureInfo[0][1]);        
        }        
        
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "action_name");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, action);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "table_name");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, tableName);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "table_id");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, tableId);
        if (sampleId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "sample_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, sampleId);
        }    
        if (testId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "test_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, testId);
        }    
        if (resultId!=null){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "result_id");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, resultId);
        }    
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "fields_updated");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, Arrays.toString(auditlog));
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "user_role");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userRole);

        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "person");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userName);
        
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "transaction_id");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, Rdbms.getTransactionId());            
                
        String jsonString = null;
/*        jsonString = sampleJsonString(schemaPrefix+"-data", sampleId);
        if ((jsonString!=null)){
        //if (!jsonString.isEmpty()){
            fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "picture_after");
            fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, jsonString);            
        }
*/        
//        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "user");
//        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userName);        
        Object[] diagnoses = Rdbms.insertRecordInTable(schemaName, auditTableName, fieldNames, fieldValues);
        String veredict= diagnoses[0].toString();
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
        String auditTableName = "sample";
        String schemaName = "data-audit";
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);                
        
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "action_name");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, action);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "table_name");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, tableName);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "table_id");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, tableId);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "sampleId");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, tableId);     
        
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "transaction_id");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, Rdbms.getTransactionId());            
        
//        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "user");
//        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userName);        
        Object[] diagnoses = Rdbms.insertRecordInTable(schemaName, auditTableName, fieldNames, fieldValues);
    }

/**
 * Creates the structure representation in a way of sample-analysis-results.
 * @param schemaPrefix String - Procedure Name
 * @param sampleId Integer - sampleId
 * @return 
 */    
    public String sampleJsonString( String schemaPrefix, Integer sampleId) {
        String jsonStructure = null;
        String query = "";
        String schemaName = "data";                
    
    LPPlatform labPlat = new LPPlatform();
                
        schemaName = LPPlatform.buildSchemaName(schemaPrefix, schemaName);  
        
        String[] sampleTblFldsArr = Rdbms.getTableFieldsArrayEj(schemaName.replace("\"", ""), "sample");
        
        String sampleTblFlds = Rdbms.getTableFieldsArrayEj(schemaName.replace("\"", ""), "sample", ",", true);
        String sampleAnalysisTblFlds = Rdbms.getTableFieldsArrayEj(schemaName.replace("\"", ""), "sample_analysis", ",", true);
        String sampleAnalysisResultTblFlds = Rdbms.getTableFieldsArrayEj(schemaName.replace("\"", ""), "sample_analysis_result", ",", true);
        
        FileWriter writer = null;
        try {
                writer = new FileWriter("C:\\home\\judas\\logs\\sample.txt");
            } catch (IOException e) {
            // TODO Auto-generated catch block

            }
        try (JsonGenerator gen = Json.createGenerator(writer)) {
            query = " select " + sampleTblFlds + " from " + schemaName + ".sample where sample_id="+sampleId;            
            Object[][] recordFieldsByFilter = Rdbms.getRecordFieldsByFilter(schemaName, "sample", new String[]{"sample_id"}, new Object[]{sampleId}, sampleTblFldsArr);
            //gen.writeStartObject().write("sample", sampleId.toString());
            // gen.writeStartArray("Sample Info");
            gen.writeStartObject();
            for (Integer iFields=0; iFields< sampleTblFldsArr.length;iFields++){
                String currValue = "y";            
                //if (recordFieldsByFilter[0][iFields]==null){currValue = "null";}
                //else{currValue=recordFieldsByFilter[0][iFields].toString();}
                //gen.writeStartObject().write("sample", sampleId.toString()).write(sampleTblFldsArr[iFields],"X");
                try{
                    gen.writeStartArray().write(sampleTblFldsArr[iFields], currValue);
                }catch (JsonException ex){
                    String m = ex.getMessage();
                }
                //gen.write(sampleTblFldsArr[iFields], (String) recordFieldsByFilter[0][iFields]);
            }
            gen.writeEnd();
//            gen.writeStartObject().write("sample_id", sampleId.toString());
//            gen.writeEnd();
        }   
    
/*        query = "  select " + sampleTblFlds + ", " + sampleAnalysisTblFlds + ", " + sampleAnalysisResultTblFlds
                + "  from " + schemaName + ".sample "
                +       "left outer join " + schemaName + ".sample_analysis on sample_analysis.sample_id=sample.sample_id "
                +       "left outer join " + schemaName + ".sample_analysis_result on sample_analysis_result.test_id=sample_analysis.test_id "
                + " where sample.sample_id="+sampleId;

        JSONObject object = new JSONObject();
object.element("name", "sample");
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
    public void jsonToFile(){
        FileWriter writer = null;
            try {
                writer = new FileWriter("C:\\home\\judas\\logs\\sample.txt");
            } catch (IOException e) {
            // TODO Auto-generated catch block

            }
        try (JsonGenerator gen = Json.createGenerator(writer)) {
            gen.writeStartObject("Sample").write("sample_id", "Sample Audit Info")
                    .writeStartArray("def");
            
            gen.writeStartObject().write("name", "sample")
                    .writeStartArray("def")
                    .writeStartObject().write("setId", 1)
                    .writeStartArray("setDef")
                    .writeStartObject().write("name", "ABC").write("type", "STRING")
                    .writeEnd()
                    .writeStartObject().write("name", "XYZ").write("type", "STRING")
                    .writeEnd()
                    .writeEnd()
                    .writeEnd()
                    .writeStartObject().write("setId", 2)
                    .writeStartArray("setDef")
                    .writeStartObject().write("name", "abc").write("type", "STRING")
                    .writeEnd()
                    .writeStartObject().write("name", "xyz").write("type", "STRING")
                    .writeEnd()
                    .writeEnd()
                    .writeEnd()    
                    .writeEnd()
                    .writeEnd();
        }
        }        
}
