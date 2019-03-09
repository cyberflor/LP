/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sop;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LPPlatform;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class SopList {

    String classVersion = "0.1";
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String tableName = "sop_list";    

    Integer sopListId = null;
    String sopListName = "";
    Integer sopListVersion = 0;
    Integer sopListRevision = 0;    
    String sopListStatus = "";
    String[] sopListSopAssigned = null;
    
    /**
     *
     * @param sopListId
     * @param sopListName
     * @param sopListVersion
     * @param sopListRevision
     * @param sopListStatus
     * @param sopListSopAssigned
     */
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

    /**
     *
     * @param sopListId
     */
    public void setSopListId(Integer sopListId){ this.sopListId=sopListId;}
    
    /**
     *
     * @param sopListName
     */
    public void setSopListName(String sopListName){ this.sopListName=sopListName;}
    
    /**
     *
     * @param sopListVersion
     */
    public void setSopListVersion(Integer sopListVersion){ this.sopListVersion=sopListVersion;}
    
    /**
     *
     * @param sopListRevision
     */
    public void setSopListRevision(Integer sopListRevision){ this.sopListRevision=sopListRevision;}   
    
    /**
     *
     * @param sopListSopAssigned
     */
    public void setSopListSopAssigned(String[] sopListSopAssigned){ this.sopListSopAssigned=sopListSopAssigned;} 
    
    /**
     *
     * @return
     */
    public Integer getSopListId(){ return this.sopListId;}
    
    /**
     *
     * @return
     */
    public String getSopListName(){ return this.sopListName;}
    
    /**
     *
     * @return
     */
    public Integer getSopListVersion(){ return this.sopListVersion;}
    
    /**
     *
     * @return
     */
    public Integer getSopListRevision(){ return this.sopListRevision;}
    
    /**
     *
     * @return
     */
    public String[] getSopListSopAssigned(){ return this.sopListSopAssigned;}
    
    /**
     *
     * @param schemaPrefix
     * @param userInfoId
     * @return
     */
    public Object[] dbInsertSopList( String schemaPrefix, String userInfoId){
        String schemaConfigName = "config";     
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName);

        //requires added_on
        String[] fieldNames = new String[0];
        Object[] fieldValues = new Object[0];
        
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "name");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, this.sopListName);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "version");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, this.sopListVersion);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "revision");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, this.sopListRevision);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "status");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, this.sopListStatus);
        fieldNames = LabPLANETArray.addValueToArray1D(fieldNames, "added_by");
        fieldValues = LabPLANETArray.addValueToArray1D(fieldValues, userInfoId);
        
        //requires added_on        
        Object[] diagnoses = Rdbms.insertRecordInTable(schemaConfigName, tableName, fieldNames, fieldValues);
        
        return diagnoses;
        
    }
    
    /**
     *
     * @param schemaPrefix
     * @param sopAssigned
     * @return
     * @throws SQLException
     */
    public Object[] dbUpdateSopListSopAssigned( String schemaPrefix, String[] sopAssigned) throws SQLException{    
        String schemaConfigName = "config";     
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
        Object[] diagnoses = Rdbms.updateRecordFieldsByFilter(schemaConfigName, tableName, 
                                        new String[]{"sop_assigned"}, new Object[]{this.sopListId}, 
                                        new String[]{"sop_list_id"}, new Object[]{sopAssigned});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses[0].toString())) return diagnoses;
        String errorCode = "SopList_SopAssignedToSopList";
        LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, new Object[]{sopAssigned, this.sopListId, schemaConfigName} );
        return diagnoses;        
    }   
    
    /**
     *
     * @param sopId
     * @return
     */
    public Integer sopPositionIntoSopList(String sopId){
        Integer diagnoses = -1;
        String[] currSopAssignedValue = getSopListSopAssigned();
        Integer arrayPosic = currSopAssignedValue.length;
        for (Integer i=0;i<arrayPosic;i++){
            if (currSopAssignedValue[i] == null ? sopId == null : currSopAssignedValue[i].equals(sopId)){ return i; } 
        }
        return diagnoses;
    }

    /**
     *
     * @param sopId
     * @return
     */
    public Object[] sopPositionIntoSopListLabPLANET(String sopId){
        Object[] diagnoses = new Object[0];
        String[] currSopAssignedValue = getSopListSopAssigned();
        Integer arrayPosic = currSopAssignedValue.length;
        for (Integer i=0;i<arrayPosic;i++){
            if (currSopAssignedValue[i] == null ? sopId == null : currSopAssignedValue[i].equals(sopId)){ 
                diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, "SOP FOUND IN SOP LIST", 
                        new Object[]{"SOP <*1*> found in SOP List <*2*> in position <*3>", sopId, currSopAssignedValue, i});
                diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, i);
                return diagnoses;
            }
        }
        diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, "SOP NOT FOUND IN SOP LIST", 
                new Object[]{"SOP <*1*> NOT found in SOP List <*2*>", sopId, currSopAssignedValue});
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, -1);
        return diagnoses;
    }    
    
    /**
     *
     * @param sopId
     * @return
     */
    public Object[]  addSopToSopList(String sopId){
        Object[] diagnoses = new Object[0];
        
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
        diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, "SopList_SopAssignedToSopList", 
                new Object[]{sopId, Arrays.toString(currSopAssignedValue),""});
        return diagnoses;
    }
     
}
