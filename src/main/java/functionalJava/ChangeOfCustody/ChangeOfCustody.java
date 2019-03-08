/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.ChangeOfCustody;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETDate;
import LabPLANET.utilities.LabPLANETPlatform;
import databases.Rdbms;
import functionalJava.audit.SampleAudit;
    
/**
 *
 * @author Administrator
 */
public class ChangeOfCustody {
    String classVersion = "0.1";
    String cocStartChangeStatus = "STARTED";
    String cocConfirmedChangeStatus = "CONFIRMED";
    String cocAbortedChangeStatus = "ABORTED";
    
    public Object[] cocStartChange(String schemaPrefix, String objectTable, String ObjectFieldName, Object objectId, String currCustodian, String custodianCandidate, String userRole, Integer appSessionId) {
        
        String cocTableName = objectTable.toLowerCase()+"_coc";
        String auditActionName = "START_CHAIN_OF_CUSTODY";
        
        Object[] errorDetailVariables = null;
        String schemaName=LabPLANETPlatform.buildSchemaName(schemaPrefix, "data");

        if ((custodianCandidate==null) || (custodianCandidate.length()==0) ) {
                String errorCode = "ChainOfCustody_noCustodian";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectId);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectTable);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
                return LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                              
        }        
        
        if (currCustodian.equalsIgnoreCase(custodianCandidate)){
                String errorCode = "ChainOfCustody_sameCustodian";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currCustodian);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectId);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectTable);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
                return LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                              
        }        

        Object[] changeOfCustodyEnable = isChangeOfCustodyEnable(schemaName, objectTable);
        if ("LABPLANET_FALSE".equalsIgnoreCase(changeOfCustodyEnable[0].toString())){
            return changeOfCustodyEnable;}
        
        Object[] existsRecord = Rdbms.existsRecord( schemaName, cocTableName, 
                new String[]{ObjectFieldName, "status"}, 
                new Object[]{objectId, cocStartChangeStatus});
        if ("LABPLANET_TRUE".equalsIgnoreCase(existsRecord[0].toString())){
                String errorCode = "ChainOfCustody_requestAlreadyInCourse";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectId);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectTable);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
                return LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);                  
        }
        Object[] updateRecordFieldsByFilter = Rdbms.updateRecordFieldsByFilter( schemaName, objectTable.toLowerCase(), 
                new String[]{"coc_requested_on", "custodian_candidate"},                 
                new Object[]{LabPLANETDate.getTimeStampLocalDate(), custodianCandidate}, 
                new String[]{ObjectFieldName}, new Object[]{objectId});
        if ("LABPLANET_FALSE".equalsIgnoreCase(updateRecordFieldsByFilter[0].toString())){
            return updateRecordFieldsByFilter;}
        
        String[] sampleFieldName = new String[]{ObjectFieldName, "custodian", "custodian_candidate", "coc_started_on", "status"};
        Object[] sampleFieldValue = new Object[]{objectId, currCustodian, custodianCandidate, LabPLANETDate.getTimeStampLocalDate(), cocStartChangeStatus};
        
        Object[] insertRecordInTable = Rdbms.insertRecordInTable( schemaName, cocTableName, 
                sampleFieldName, sampleFieldValue);        
        if ("LABPLANET_FALSE".equalsIgnoreCase(insertRecordInTable[0].toString())){
            return insertRecordInTable;}
        
        switch (objectTable.toLowerCase()){
            case "sample":
                Object[] fieldsOnLogSample = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, objectTable, Integer.valueOf(objectId.toString()), 
                        Integer.valueOf(objectId.toString()), null, null, 
                        fieldsOnLogSample, currCustodian, userRole, appSessionId);                
                break;
            default:
                break;
        }        
        String errorCode = "ChainOfCustody_requestStarted";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectId);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectTable);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
        return LabPLANETPlatform.trapErrorMessage("LABPLANET_TRUE", errorCode, errorDetailVariables);                  
    }

    public Object[] cocConfirmedChange( String schemaName, String objectTable, String ObjectFieldName, Object objectId, String userName, String comment, String userRole, Integer appSessionId) {
        return cocCompleteChange( schemaName, objectTable, ObjectFieldName, objectId, userName, comment, cocConfirmedChangeStatus, userRole, appSessionId);
    }
    
    public Object[] cocAbortedChange( String schemaName, String objectTable, String ObjectFieldName, Object objectId, String userName, String comment, String userRole, Integer appSessionId) {
        return cocCompleteChange( schemaName, objectTable, ObjectFieldName, objectId, userName, comment, cocAbortedChangeStatus, userRole, appSessionId);
    }
    
    private Object[] cocCompleteChange( String schemaPrefix, String objectTable, String ObjectFieldName, Object objectId, String userName, String comment, String actionName, String userRole, Integer appSessionId) {

        String auditActionName = actionName.toUpperCase()+"_CHAIN_OF_CUSTODY";       
        
        Object[] errorDetailVariables = null;        

        String schemaName=LabPLANETPlatform.buildSchemaName(schemaPrefix, "data");
        String cocTableName = objectTable.toLowerCase()+"_coc";

        Object[] changeOfCustodyEnable = isChangeOfCustodyEnable(schemaName, objectTable);
        if ("LABPLANET_FALSE".equalsIgnoreCase(changeOfCustodyEnable[0].toString())){
            return changeOfCustodyEnable;}

        Object[][] startedProcessData = Rdbms.getRecordFieldsByFilter( schemaName, cocTableName, 
                new String[]{ObjectFieldName, "status"},
                new Object[]{objectId, "STARTED"},
                new String[]{"id", "status", "custodian_candidate"});
        if ("LABPLANET_FALSE".equalsIgnoreCase(startedProcessData[0][0].toString())){            
            String errorCode = "ChainOfCustody_noChangeInProgress";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectId);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectTable);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
            return LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);}                                       
        
        String custodianCandidate = "";
        Integer recordId=null;
        if (startedProcessData[0][2]!=null) {
            recordId = (Integer) startedProcessData[0][0];       
            custodianCandidate = startedProcessData[0][2].toString();}

        if ( (startedProcessData[0][2]==null) || (!userName.equalsIgnoreCase(custodianCandidate)) ){
            String errorCode = "ChainOfCustody_noCustodianCandidate";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectId);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectTable);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
            return LabPLANETPlatform.trapErrorMessage("LABPLANET_FALSE", errorCode, errorDetailVariables);}                                                   
        
        
        String[] sampleFieldName=new String[]{"status", "coc_confirmed_on" };
        Object[] sampleFieldValue=new Object[]{actionName,LabPLANETDate.getTimeStampLocalDate()};
        if (comment!=null){            
            sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "coc_new_custodian_notes");
            sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, comment);
        }
        Object[] updateRecordInTable = Rdbms.updateRecordFieldsByFilter( schemaName, cocTableName, 
                sampleFieldName, sampleFieldValue,
                new String[]{"id"}, new Object[]{recordId});
        if ("LABPLANET_FALSE".equalsIgnoreCase(updateRecordInTable[0].toString())){
            return updateRecordInTable;}        
        
         String[] updSampleTblFlds=new String[]{"coc_confirmed_on", "custodian_candidate"}; // , "coc_requested_on"
         Object[] updSampleTblVls=new Object[]{LabPLANETDate.getTimeStampLocalDate(), "null*String"}; // , "null*Date"
         if (actionName.equalsIgnoreCase(cocConfirmedChangeStatus)){
            updSampleTblFlds = LabPLANETArray.addValueToArray1D(updSampleTblFlds, "custodian");
            updSampleTblVls = LabPLANETArray.addValueToArray1D(updSampleTblVls, userName);
         }
         Object[] updateRecordFieldsByFilter = Rdbms.updateRecordFieldsByFilter( schemaName, objectTable.toLowerCase(), 
                updSampleTblFlds, updSampleTblVls,                
                new String[]{ObjectFieldName}, new Object[]{objectId});
        if ("LABPLANET_FALSE".equalsIgnoreCase(updateRecordFieldsByFilter[0].toString())){
            return updateRecordFieldsByFilter;}
                
        switch (objectTable.toLowerCase()){
            case "sample":
                Object[] fieldsOnLogSample = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd( schemaPrefix, auditActionName, objectTable, Integer.valueOf(objectId.toString()), 
                        Integer.valueOf(objectId.toString()), null, null, 
                        fieldsOnLogSample, userName, userRole, appSessionId);                
                break;
            default:
                break;
        }      
        String errorCode = "ChainOfCustody_requestCompleted";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaName);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectTable);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, objectId);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, actionName.toLowerCase());
        return LabPLANETPlatform.trapErrorMessage("LABPLANET_TRUE", errorCode, errorDetailVariables);                          
    }
    
    public Object[] isChangeOfCustodyEnable(String schemaName, String objectTable){
            String procBusinessRuleName = "changeOfCustodyObjects";
            return new Object[]{"LABPLANET_TRUE"};
    }
    
}
