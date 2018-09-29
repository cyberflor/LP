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
public class SopList {

    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "sop_list";    

    Integer sopListId = null;
    String sopListName = "";
    Integer sopListVersion = 0;
    Integer sopListRevision = 0;    
    String sopListStatus = "";
    String[] sopListSopAssigned = null;
    
    public SopList (Integer sopListId, String sopListName, Integer sopListVersion, Integer sopListRevision, String sopListStatus, String[] sopListSopAssigned){
        this.sopListId = sopListId;
        this.sopListName = sopListName;
        this.sopListVersion = sopListVersion;
        this.sopListRevision = sopListRevision;                
        this.sopListStatus = sopListStatus;
        if (sopListSopAssigned!=null){this.sopListSopAssigned = sopListSopAssigned;}
        else{
            String[] newStr = new String[0];
            this.sopListSopAssigned = newStr;
        }
    }

    public void setSopListId(Integer sopListId){ this.sopListId=sopListId;}
    
    public void setSopListName(String sopListName){ this.sopListName=sopListName;}
    
    public void setSopListVersion(Integer sopListVersion){ this.sopListVersion=sopListVersion;}
    
    public void setSopListRevision(Integer sopListRevision){ this.sopListRevision=sopListRevision;}   
    
    public void setSopListSopAssigned(String[] sopListSopAssigned){ this.sopListSopAssigned=sopListSopAssigned;} 
    
    public Integer getSopListId(){ return this.sopListId;}
    
    public String getSopListName(){ return this.sopListName;}
    
    public Integer getSopListVersion(){ return this.sopListVersion;}
    
    public Integer getSopListRevision(){ return this.sopListRevision;}
    
    public String[] getSopListSopAssigned(){ return this.sopListSopAssigned;}
    
    public Object[] dbInsertSopList(Rdbms rdbm, String schemaPrefix, String userInfoId){
    
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);
//        schemaPrefix = "\""+schemaPrefix+"\"";
        //requires added_on
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        fieldNames = labArr.addValueToArray1D(fieldNames, "name");
        fieldValues = labArr.addValueToArray1D(fieldValues, this.sopListName);
        fieldNames = labArr.addValueToArray1D(fieldNames, "version");
        fieldValues = labArr.addValueToArray1D(fieldValues, this.sopListVersion);
        fieldNames = labArr.addValueToArray1D(fieldNames, "revision");
        fieldValues = labArr.addValueToArray1D(fieldValues, this.sopListRevision);
        fieldNames = labArr.addValueToArray1D(fieldNames, "status");
        fieldValues = labArr.addValueToArray1D(fieldValues, this.sopListStatus);
        fieldNames = labArr.addValueToArray1D(fieldNames, "added_by");
        fieldValues = labArr.addValueToArray1D(fieldValues, userInfoId);
        
        //requires added_on        
        Object[] diagnoses = rdbm.insertRecordInTable(rdbm, schemaPrefix, tableName, fieldNames, fieldValues);
        
        return diagnoses;
        
    }
    
    public Object[] dbUpdateSopListSopAssigned(Rdbms rdbm, String schemaPrefix, String[] sopAssigned) throws SQLException{
    
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);
        Object[] diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, 
                                        new String[]{"sop_assigned"}, new Object[]{this.sopListId}, 
                                        new String[]{"sop_list_id"}, new Object[]{sopAssigned});        
        return diagnoses;
    }
    

    public Integer sopPositionIntoSopList(String sopId){
        Integer diagnoses = -1;
        String[] currSopAssignedValue = getSopListSopAssigned();
        Integer arrayPosic = currSopAssignedValue.length;
        for (Integer i=0;i<arrayPosic;i++){
            if (currSopAssignedValue[i] == null ? sopId == null : currSopAssignedValue[i].equals(sopId)){ return i; } 
        }
        return diagnoses;
    }
    
    public String addSopToSopList(String sopId){
        String diagnoses = "";
        
        String[] currSopAssignedValue = getSopListSopAssigned();
        Integer arrayPosic = currSopAssignedValue.length;
        if (sopPositionIntoSopList(sopId)==-1){
            String[] newArray = new String[arrayPosic+1];
            for (Integer i=0;i<arrayPosic;i++){
                newArray[i] = currSopAssignedValue[i];                
            }
            newArray[arrayPosic++] = sopId;
            setSopListSopAssigned(newArray);
        }    
        return diagnoses;
    }
     
}
