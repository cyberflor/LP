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
    
    public Object[] cocStartChange(Rdbms rdbm, String schemaPrefix, String objectTable, String ObjectFieldName, Object objectId, String currCustodian, String custodianCandidate, String userRole, Integer appSessionId) {
        
        String cocTableName = objectTable.toLowerCase()+"_coc";
        String auditActionName = "START_CHAIN_OF_CUSTODY";
        LabPLANETArray labArr = new LabPLANETArray();
        Object[] errorDetailVariables = null;
        String schemaName=LabPLANETPlatform.buildSchemaName(schemaPrefix, "data");

        if ((custodianCandidate==null) || (custodianCandidate.length()==0) ) {
                String errorCode = "ChainOfCustody_noCustodian";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectId);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectTable);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
                return LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                              
        }        
        
        if (currCustodian.equalsIgnoreCase(custodianCandidate)){
                String errorCode = "ChainOfCustody_sameCustodian";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currCustodian);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectId);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectTable);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
                return LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                              
        }        

        Object[] changeOfCustodyEnable = isChangeOfCustodyEnable(schemaName, objectTable);
        if ("LABPLANET_FALSE".equalsIgnoreCase(changeOfCustodyEnable[0].toString())){
            return changeOfCustodyEnable;}
        
        Object[] existsRecord = rdbm.existsRecord(rdbm, schemaName, cocTableName, 
                new String[]{ObjectFieldName, "status"}, 
                new Object[]{objectId, cocStartChangeStatus});
        if ("LABPLANET_TRUE".equalsIgnoreCase(existsRecord[0].toString())){
                String errorCode = "ChainOfCustody_requestAlreadyInCourse";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectId);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectTable);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
                return LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                  
        }
        Object[] updateRecordFieldsByFilter = rdbm.updateRecordFieldsByFilter(rdbm, schemaName, objectTable.toLowerCase(), 
                new String[]{"coc_requested_on", "custodian_candidate"},                 
                new Object[]{LabPLANETDate.getTimeStampLocalDate(), custodianCandidate}, 
                new String[]{ObjectFieldName}, new Object[]{objectId});
        if ("LABPLANET_FALSE".equalsIgnoreCase(updateRecordFieldsByFilter[0].toString())){
            return updateRecordFieldsByFilter;}
        
        String[] sampleFieldName = new String[]{ObjectFieldName, "custodian", "custodian_candidate", "coc_started_on", "status"};
        Object[] sampleFieldValue = new Object[]{objectId, currCustodian, custodianCandidate, LabPLANETDate.getTimeStampLocalDate(), cocStartChangeStatus};
        
        Object[] insertRecordInTable = rdbm.insertRecordInTable(rdbm, schemaName, cocTableName, 
                sampleFieldName, sampleFieldValue);        
        if ("LABPLANET_FALSE".equalsIgnoreCase(insertRecordInTable[0].toString())){
            return insertRecordInTable;}
        
        switch (objectTable.toLowerCase()){
            case "sample":
                Object[] fieldsOnLogSample = labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, objectTable, Integer.valueOf(objectId.toString()), 
                        Integer.valueOf(objectId.toString()), null, null, 
                        fieldsOnLogSample, currCustodian, userRole, appSessionId);                
                break;
            default:
                break;
        }        
        String errorCode = "ChainOfCustody_requestStarted";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectId);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectTable);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
        return LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);                  
    }

    public Object[] cocConfirmedChange(Rdbms rdbm, String schemaName, String objectTable, String ObjectFieldName, Object objectId, String userName, String comment, String userRole, Integer appSessionId) {
        return cocCompleteChange(rdbm, schemaName, objectTable, ObjectFieldName, objectId, userName, comment, cocConfirmedChangeStatus, userRole, appSessionId);
    }
    
    public Object[] cocAbortedChange(Rdbms rdbm, String schemaName, String objectTable, String ObjectFieldName, Object objectId, String userName, String comment, String userRole, Integer appSessionId) {
        return cocCompleteChange(rdbm, schemaName, objectTable, ObjectFieldName, objectId, userName, comment, cocAbortedChangeStatus, userRole, appSessionId);
    }
    
    private Object[] cocCompleteChange(Rdbms rdbm, String schemaPrefix, String objectTable, String ObjectFieldName, Object objectId, String userName, String comment, String actionName, String userRole, Integer appSessionId) {

        String auditActionName = actionName.toUpperCase()+"_CHAIN_OF_CUSTODY";       
        LabPLANETArray labArr = new LabPLANETArray();
        Object[] errorDetailVariables = null;        

        String schemaName=LabPLANETPlatform.buildSchemaName(schemaPrefix, "data");
        String cocTableName = objectTable.toLowerCase()+"_coc";

        Object[] changeOfCustodyEnable = isChangeOfCustodyEnable(schemaName, objectTable);
        if ("LABPLANET_FALSE".equalsIgnoreCase(changeOfCustodyEnable[0].toString())){
            return changeOfCustodyEnable;}

        Object[][] startedProcessData = rdbm.getRecordFieldsByFilter(rdbm, schemaName, cocTableName, 
                new String[]{ObjectFieldName, "status"},
                new Object[]{objectId, "STARTED"},
                new String[]{"id", "status", "custodian_candidate"});
        if ("LABPLANET_FALSE".equalsIgnoreCase(startedProcessData[0][0].toString())){            
            String errorCode = "ChainOfCustody_noChangeInProgress";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectId);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectTable);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
            return LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}                                       
        
        String custodianCandidate = "";
        Integer recordId=null;
        if (startedProcessData[0][2]!=null) {
            recordId = (Integer) startedProcessData[0][0];       
            custodianCandidate = startedProcessData[0][2].toString();}

        if ( (startedProcessData[0][2]==null) || (!userName.equalsIgnoreCase(custodianCandidate)) ){
            String errorCode = "ChainOfCustody_noCustodianCandidate";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectId);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectTable);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
            return LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);}                                                   
        
        
        String[] sampleFieldName=new String[]{"status", "coc_confirmed_on" };
        Object[] sampleFieldValue=new Object[]{actionName,LabPLANETDate.getTimeStampLocalDate()};
        if (comment!=null){            
            sampleFieldName = labArr.addValueToArray1D(sampleFieldName, "coc_new_custodian_notes");
            sampleFieldValue = labArr.addValueToArray1D(sampleFieldValue, comment);
        }
        Object[] updateRecordInTable = rdbm.updateRecordFieldsByFilter(rdbm, schemaName, cocTableName, 
                sampleFieldName, sampleFieldValue,
                new String[]{"id"}, new Object[]{recordId});
        if ("LABPLANET_FALSE".equalsIgnoreCase(updateRecordInTable[0].toString())){
            return updateRecordInTable;}        
        
         String[] updSampleTblFlds=new String[]{"coc_confirmed_on", "custodian_candidate"}; // , "coc_requested_on"
         Object[] updSampleTblVls=new Object[]{LabPLANETDate.getTimeStampLocalDate(), "null*String"}; // , "null*Date"
         if (actionName.equalsIgnoreCase(cocConfirmedChangeStatus)){
            updSampleTblFlds = labArr.addValueToArray1D(updSampleTblFlds, "custodian");
            updSampleTblVls = labArr.addValueToArray1D(updSampleTblVls, userName);
         }
         Object[] updateRecordFieldsByFilter = rdbm.updateRecordFieldsByFilter(rdbm, schemaName, objectTable.toLowerCase(), 
                updSampleTblFlds, updSampleTblVls,                
                new String[]{ObjectFieldName}, new Object[]{objectId});
        if ("LABPLANET_FALSE".equalsIgnoreCase(updateRecordFieldsByFilter[0].toString())){
            return updateRecordFieldsByFilter;}
                
        switch (objectTable.toLowerCase()){
            case "sample":
                Object[] fieldsOnLogSample = labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, objectTable, Integer.valueOf(objectId.toString()), 
                        Integer.valueOf(objectId.toString()), null, null, 
                        fieldsOnLogSample, userName, userRole, appSessionId);                
                break;
            default:
                break;
        }      
        String errorCode = "ChainOfCustody_requestCompleted";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectTable);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, objectId);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, actionName.toLowerCase());
        return LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);                          
    }
    
    public Object[] isChangeOfCustodyEnable(String schemaName, String objectTable){
            String procBusinessRuleName = "changeOfCustodyObjects";
            return new Object[]{"LABPLANET_TRUE"};
    }
    
}
