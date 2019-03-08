/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sop;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;
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

    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String tableName = "sop_meta_data"; 

    /**
     *
     */
    public Sop(){}
    
    /**
     *
     * @param sopName
     */
    public Sop(String sopName){this.sopName=sopName;}
            
    /**
     *
     * @param sopId
     * @param sopName
     * @param sopVersion
     * @param sopRevision
     * @param currentStatus
     * @param mandatoryLevel
     */
    public Sop (Integer sopId, String sopName, Integer sopVersion, Integer sopRevision, String currentStatus, String mandatoryLevel){
        this.sopId = sopId;
        this.sopName=sopName;
        this.sopVersion = sopVersion;
        this.sopRevision = sopRevision;
        this.currentStatus = currentStatus;
        this.mandatoryLevel = mandatoryLevel;               
    }

    /**
     *
     * @param schemaPrefix
     * @param userInfoId
     * @return
     * @throws SQLException
     */
    public Object[] dbInsertSopId( String schemaPrefix, String userInfoId) throws SQLException{
         LabPLANETArray labArr = new LabPLANETArray();
         String schemaConfigName = "config";
         schemaConfigName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
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

        Object[][] dbGetSopObjByName = this.dbGetSopObjByName(schemaPrefix, this.sopName, fieldNames);
        if ("LABPLANET_FALSE".equalsIgnoreCase(dbGetSopObjByName[0][0].toString())){        
            Object[] diagnoses = Rdbms.insertRecordInTable(schemaConfigName, tableName, fieldNames, fieldValues);
            return diagnoses;
        }else{
            Object[] diagnoses = LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", "Sop_SopAlreadyExists", new Object[]{this.sopName, schemaPrefix});
            return diagnoses;
        }
    }
    
    /**
     *
     * @param schemaPrefix
     * @param sopId
     * @return
     * @throws SQLException
     */
    public Integer dbGetSopIdById( String schemaPrefix, Integer sopId) throws SQLException{     
        String schemaConfigName = "config";
        schemaConfigName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
        Object[][] sopInfo = Rdbms.getRecordFieldsByFilter(schemaConfigName, tableName, 
                                                                new String[]{"sop_id"}, new Object[]{sopId}, new String[]{"sop_id"});
        Integer getSopId = (Integer) sopInfo[0][0];
        return getSopId;
    }                

    /**
     *
     * @param schemaPrefix
     * @param sopName
     * @return
     * @throws SQLException
     */
    public Integer dbGetSopIdByName( String schemaPrefix, String sopName) throws SQLException{
        String schemaConfigName = "config";
        schemaConfigName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
        Object[][] sopInfo = Rdbms.getRecordFieldsByFilter(schemaConfigName, tableName, 
                                                                new String[]{"sop_name"}, new Object[]{sopName}, new String[]{"sop_id"});
        Integer getSopId = (Integer) sopInfo[0][0];
        return getSopId;
        
    }    

    /**
     *
     * @param schemaPrefix
     * @param sopName
     * @param fields
     * @return
     * @throws SQLException
     */
    public Object[][] dbGetSopObjByName( String schemaPrefix, String sopName, String[] fields) throws SQLException{
        String schemaConfigName = "config";
        schemaConfigName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
        Object[][] sopInfo = Rdbms.getRecordFieldsByFilter(schemaConfigName, tableName, 
                                                                new String[]{"sop_name"}, new Object[]{sopName}, fields);
        return sopInfo;
    }

    /**
     *
     * @param schemaPrefix
     * @param sopName
     * @return
     * @throws SQLException
     */
    public Object[] createSop( String schemaPrefix, String sopName) throws SQLException {
        String schemaConfigName = "config";
        schemaConfigName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
        LabPLANETArray labArr = new LabPLANETArray();
        String errorCode = "";        
        Object[] diagnoses = Rdbms.insertRecordInTable(schemaConfigName, "sop_meta_data", 
                            new String[]{"sop_name", "sop_version", "sop_revision"},
                            new Object[]{sopName, 1, 1});
        if ("LABPLANET_FALSE".equals(diagnoses[0].toString() )){
            errorCode = "Sop_SopMetaData_recordNotCreated";
            String[] fieldForInserting = labArr.joinTwo1DArraysInOneOf1DString(new String[]{"sop_name", "sop_version", "sop_revision"}, new Object[]{sopName, 1, 1}, ":");
            LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, new Object[]{fieldForInserting, schemaConfigName} );
            return diagnoses;            
        }else{           
            return diagnoses;                        
        }
    }   
        
    /**
     *
     * @param schemaName
     * @param schemaPrefix
     * @param fieldName
     * @param fieldValue
     * @param fieldType
     * @return
     * @throws SQLException
     */
    public Object[] updateSop( String schemaName, String schemaPrefix, String fieldName, String fieldValue, String fieldType) throws SQLException {
        String schemaConfigName = "config";
        schemaConfigName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
        Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaConfigName, "sop_meta_data", 
                                        new String[]{fieldName}, new Object[]{fieldValue}, new String[]{"sop_name"}, new Object[]{sopName});
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnoses[0].toString())){
            String errorCode = "Sop_SopMetaData_recordNotUpdated";
            LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, new Object[]{fieldName, fieldValue, sopName, schemaConfigName} );
            return diagnoses;            
        }else{
            return diagnoses;                        
        }        
    }   
}
