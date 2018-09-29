/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sop;

import databases.Rdbms;
import labPLANET.utilities.LabPLANETArray;
import labPLANET.utilities.LabPLANETPlatform;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class Sop {

    Integer sopId = null;
    String sopName = "";
    Integer sopVersion = 0;
    Integer sopRevision = 0;
    String currentStatus = "";
    String mandatoryLevel = "READ";
    
    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "sop_meta_data"; 

    public Sop(){}
    
    public Sop(String sopName){this.sopName=sopName;}
            
    public Sop (Integer sopId, String sopName, Integer sopVersion, Integer sopRevision, String currentStatus, String mandatoryLevel){
        this.sopId = sopId;
        this.sopName=sopName;
        this.sopVersion = sopVersion;
        this.sopRevision = sopRevision;
        this.currentStatus = currentStatus;
        this.mandatoryLevel = mandatoryLevel;               
    }

    public Object[] dbInsertSopId(Rdbms rdbm, String schemaPrefix, String userInfoId) throws SQLException{
    
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);
//        schemaPrefix = "\""+schemaPrefix+"\"";
        //requires added_on
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        fieldNames = labArr.addValueToArray1D(fieldNames, "sop_name");
        fieldValues = labArr.addValueToArray1D(fieldValues, this.sopName);
        fieldNames = labArr.addValueToArray1D(fieldNames, "sop_version");
        fieldValues = labArr.addValueToArray1D(fieldValues, this.sopVersion);
        fieldNames = labArr.addValueToArray1D(fieldNames, "sop_revision");
        fieldValues = labArr.addValueToArray1D(fieldValues, this.sopRevision);
        fieldNames = labArr.addValueToArray1D(fieldNames, "current_status");
        fieldValues = labArr.addValueToArray1D(fieldValues, this.currentStatus);
        fieldNames = labArr.addValueToArray1D(fieldNames, "added_by");
        fieldValues = labArr.addValueToArray1D(fieldValues, userInfoId);
        
        Object[] diagnoses = rdbm.insertRecordInTable(rdbm, schemaPrefix, tableName, fieldNames, fieldValues);
        
        return diagnoses;
    }
    
    public Integer dbGetSopIdById(Rdbms rdbm, String schemaPrefix, Integer sopId) throws SQLException{
                        
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);
        Object[][] sopInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, tableName, 
                                                                new String[]{"sop_id"}, new Object[]{sopId}, new String[]{"sop_id"});
        Integer getSopId = (Integer) sopInfo[0][0];
        return getSopId;
    }                

    public Integer dbGetSopIdByName(Rdbms rdbm, String schemaPrefix, String sopName) throws SQLException{

        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);
        Object[][] sopInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, tableName, 
                                                                new String[]{"sop_name"}, new Object[]{sopName}, new String[]{"sop_id"});
        Integer getSopId = (Integer) sopInfo[0][0];
        return getSopId;
        
    }    

    public Object[][] dbGetSopObjByName(Rdbms rdbm, String schemaPrefix, String sopName, String[] fields) throws SQLException{
                
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);
        Object[][] sopInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, tableName, 
                                                                new String[]{"sop_name"}, new Object[]{sopName}, fields);
        return sopInfo;
    }

    public Object[] createSop(Rdbms rdbm, String schemaPrefix, String sopName) throws SQLException {
        
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);
        Object[] diagnoses = rdbm.insertRecordInTable(rdbm, schemaDataName, "sop_meta_data", 
                            new String[]{"sop_name", "sop_version", "sop_revision"},
                            new Object[]{sopName, 1, 1});
        if ("FALSE".equals(diagnoses[3])){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR SOP_META_DATA RECORD CANNOT BE CREATED";
            //diagnoses[5]=diagnoses[5];
            return diagnoses;            
        }else{
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="TRUE";
            diagnoses[4]="SUCCESS";
            diagnoses[5]="The sop_meta_data "+sopName+" record was created in schema "+schemaDataName;
            return diagnoses;                        
        }
    }   
        
    public Object[] updateSop(Rdbms rdbm, String schemaName, String schemaPrefix, String fieldName, String fieldValue, String fieldType) throws SQLException {
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);
        Object[] diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sop_meta_data", 
                                        new String[]{fieldName}, new Object[]{fieldValue}, new String[]{"sop_name"}, new Object[]{sopName});
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnoses[0].toString())){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR SOP_META_DATA RECORD CANNOT BE UPDATED";
            //diagnoses[5]=diagnoses[5];
            return diagnoses;            
        }else{
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="TRUE";
            diagnoses[4]="SUCCESS";
            diagnoses[5]="The sop_meta_data "+sopName+" record was updated successfully in schema "+schemaDataName;
            return diagnoses;                        
        }
        
    }   



}
