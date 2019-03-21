/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sop;

import databases.Rdbms;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPPlatform;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class Sop {

    public static final String FIELDNAME_SOP_ID="sop_id";
    public static final String FIELDNAME_SOP_NAME="sop_name";
    public static final String FIELDNAME_SOP_VERSION="sop_version";
    public static final String FIELDNAME_SOP_REVISION="sop_revision";
    
    
    Integer sopId = null;
    String sopName = "";
    Integer sopVersion = 0;
    Integer sopRevision = 0;
    String currentStatus = "";
    String mandatoryLevel = "READ";
    
    String classVersion = "0.1";

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
         String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
         schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
//        schemaPrefix = "\""+schemaPrefix+"\"";
        //requires added_on
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELDNAME_SOP_NAME);
        fieldValues = LPArray.addValueToArray1D(fieldValues, this.sopName);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELDNAME_SOP_VERSION);
        fieldValues = LPArray.addValueToArray1D(fieldValues, this.sopVersion);
        fieldNames = LPArray.addValueToArray1D(fieldNames, FIELDNAME_SOP_REVISION);
        fieldValues = LPArray.addValueToArray1D(fieldValues, this.sopRevision);
        fieldNames = LPArray.addValueToArray1D(fieldNames, "current_status");
        fieldValues = LPArray.addValueToArray1D(fieldValues, this.currentStatus);
        fieldNames = LPArray.addValueToArray1D(fieldNames, "added_by");
        fieldValues = LPArray.addValueToArray1D(fieldValues, userInfoId);

        Object[][] dbGetSopObjByName = this.dbGetSopObjByName(schemaPrefix, this.sopName, fieldNames);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(dbGetSopObjByName[0][0].toString())){        
            Object[] diagnoses = Rdbms.insertRecordInTable(schemaConfigName, tableName, fieldNames, fieldValues);
            return diagnoses;
        }else{
            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "Sop_SopAlreadyExists", new Object[]{this.sopName, schemaPrefix});
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
        String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
        Object[][] sopInfo = Rdbms.getRecordFieldsByFilter(schemaConfigName, tableName, 
                                                                new String[]{FIELDNAME_SOP_ID}, new Object[]{sopId}, new String[]{FIELDNAME_SOP_ID});
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
        String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
        Object[][] sopInfo = Rdbms.getRecordFieldsByFilter(schemaConfigName, tableName, 
                                                                new String[]{FIELDNAME_SOP_NAME}, new Object[]{sopName}, new String[]{FIELDNAME_SOP_ID});
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
        String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
        Object[][] sopInfo = Rdbms.getRecordFieldsByFilter(schemaConfigName, tableName, 
                                                                new String[]{FIELDNAME_SOP_NAME}, new Object[]{sopName}, fields);
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
        String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
        String errorCode = "";        
        Object[] diagnoses = Rdbms.insertRecordInTable(schemaConfigName, "sop_meta_data", 
                            new String[]{FIELDNAME_SOP_NAME, FIELDNAME_SOP_VERSION, FIELDNAME_SOP_REVISION},
                            new Object[]{sopName, 1, 1});
        if (LPPlatform.LAB_FALSE.equals(diagnoses[0].toString() )){
            errorCode = "Sop_SopMetaData_recordNotCreated";
            String[] fieldForInserting = LPArray.joinTwo1DArraysInOneOf1DString(new String[]{FIELDNAME_SOP_NAME, FIELDNAME_SOP_VERSION, FIELDNAME_SOP_REVISION}, new Object[]{sopName, 1, 1}, ":");
            LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, new Object[]{fieldForInserting, schemaConfigName} );
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
        String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
        Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaConfigName, "sop_meta_data", 
                                        new String[]{fieldName}, new Object[]{fieldValue}, new String[]{FIELDNAME_SOP_NAME}, new Object[]{sopName});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses[0].toString())){
            String errorCode = "Sop_SopMetaData_recordNotUpdated";
            LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, new Object[]{fieldName, fieldValue, sopName, schemaConfigName} );
            return diagnoses;            
        }else{
            return diagnoses;                        
        }        
    }   
}
