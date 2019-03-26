/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.ChangeOfCustody;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPDate;
import LabPLANET.utilities.LPPlatform;
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
    public static final String FIELDNAME_STATUS = "status";
    
    public Object[] cocStartChange(String schemaPrefix, String objectTable, String objectFieldName, Object objectId, String currCustodian, String custodianCandidate, String userRole, Integer appSessionId) {
        
        String cocTableName = objectTable.toLowerCase()+"_coc";
        String auditActionName = "START_CHAIN_OF_CUSTODY";
        
        Object[] errorDetailVariables = null;
        String schemaName=LPPlatform.buildSchemaName(schemaPrefix, "data");

        if ((custodianCandidate==null) || (custodianCandidate.length()==0) ) {
                String errorCode = "ChainOfCustody_noCustodian";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectId);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectTable);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                              
        }        
        
        if (currCustodian.equalsIgnoreCase(custodianCandidate)){
                String errorCode = "ChainOfCustody_sameCustodian";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, currCustodian);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectId);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectTable);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                              
        }        

        Object[] changeOfCustodyEnable = isChangeOfCustodyEnable(schemaName, objectTable);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(changeOfCustodyEnable[0].toString())){
            return changeOfCustodyEnable;}
        
        Object[] existsRecord = Rdbms.existsRecord( schemaName, cocTableName, 
                new String[]{objectFieldName, FIELDNAME_STATUS}, 
                new Object[]{objectId, cocStartChangeStatus});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(existsRecord[0].toString())){
                String errorCode = "ChainOfCustody_requestAlreadyInCourse";
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectId.toString());
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectTable);
                errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                  
        }
        Object[] updateRecordFieldsByFilter = Rdbms.updateRecordFieldsByFilter(schemaName, objectTable.toLowerCase(), 
                new String[]{"coc_requested_on", "custodian_candidate"},                 
                new Object[]{LPDate.getTimeStampLocalDate(), custodianCandidate}, 
                new String[]{objectFieldName}, new Object[]{objectId});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(updateRecordFieldsByFilter[0].toString())){
            return updateRecordFieldsByFilter;}
        
        String[] sampleFieldName = new String[]{objectFieldName, "custodian", "custodian_candidate", "coc_started_on", FIELDNAME_STATUS};
        Object[] sampleFieldValue = new Object[]{objectId, currCustodian, custodianCandidate, LPDate.getTimeStampLocalDate(), cocStartChangeStatus};
        
        Object[] insertRecordInTable = Rdbms.insertRecordInTable( schemaName, cocTableName, 
                sampleFieldName, sampleFieldValue);        
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(insertRecordInTable[0].toString())){
            return insertRecordInTable;}
        
        switch (objectTable.toLowerCase()){
            case "sample":
                Object[] fieldsOnLogSample = LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, objectTable, Integer.valueOf(objectId.toString()), 
                        Integer.valueOf(objectId.toString()), null, null, 
                        fieldsOnLogSample, currCustodian, userRole, appSessionId);                
                break;
            default:
                break;
        }        
        String errorCode = "ChainOfCustody_requestStarted";
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectId);
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectTable);
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);                  
    }

    public Object[] cocConfirmedChange( String schemaName, String objectTable, String objectFieldName, Object objectId, String userName, String comment, String userRole, Integer appSessionId) {
        return cocCompleteChange( schemaName, objectTable, objectFieldName, objectId, userName, comment, cocConfirmedChangeStatus, userRole, appSessionId);
    }
    
    public Object[] cocAbortedChange( String schemaName, String objectTable, String objectFieldName, Object objectId, String userName, String comment, String userRole, Integer appSessionId) {
        return cocCompleteChange( schemaName, objectTable, objectFieldName, objectId, userName, comment, cocAbortedChangeStatus, userRole, appSessionId);
    }
    
    private Object[] cocCompleteChange( String schemaPrefix, String objectTable, String objectFieldName, Object objectId, String userName, String comment, String actionName, String userRole, Integer appSessionId) {

        String auditActionName = actionName.toUpperCase()+"_CHAIN_OF_CUSTODY";       
        
        Object[] errorDetailVariables = null;        

        String schemaName=LPPlatform.buildSchemaName(schemaPrefix, "data");
        String cocTableName = objectTable.toLowerCase()+"_coc";

        Object[] changeOfCustodyEnable = isChangeOfCustodyEnable(schemaName, objectTable);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(changeOfCustodyEnable[0].toString())){
            return changeOfCustodyEnable;}

        Object[][] startedProcessData = Rdbms.getRecordFieldsByFilter( schemaName, cocTableName, 
                new String[]{objectFieldName, FIELDNAME_STATUS},
                new Object[]{objectId, "STARTED"},
                new String[]{"id", FIELDNAME_STATUS, "custodian_candidate"});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(startedProcessData[0][0].toString())){            
            String errorCode = "ChainOfCustody_noChangeInProgress";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectId);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectTable);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                                       
        
        String custodianCandidate = "";
        Integer recordId=null;
        if (startedProcessData[0][2]!=null) {
            recordId = (Integer) startedProcessData[0][0];       
            custodianCandidate = startedProcessData[0][2].toString();}

        if ( (startedProcessData[0][2]==null) || (!userName.equalsIgnoreCase(custodianCandidate)) ){
            String errorCode = "ChainOfCustody_noCustodianCandidate";
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectId);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectTable);
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);}                                                   
        
        
        String[] sampleFieldName=new String[]{FIELDNAME_STATUS, "coc_confirmed_on" };
        Object[] sampleFieldValue=new Object[]{actionName,LPDate.getTimeStampLocalDate()};
        if (comment!=null){            
            sampleFieldName = LPArray.addValueToArray1D(sampleFieldName, "coc_new_custodian_notes");
            sampleFieldValue = LPArray.addValueToArray1D(sampleFieldValue, comment);
        }
        Object[] updateRecordInTable = Rdbms.updateRecordFieldsByFilter( schemaName, cocTableName, 
                sampleFieldName, sampleFieldValue,
                new String[]{"id"}, new Object[]{recordId});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(updateRecordInTable[0].toString())){
            return updateRecordInTable;}        
        
         String[] updSampleTblFlds=new String[]{"coc_confirmed_on", "custodian_candidate"}; // , "coc_requested_on"
         Object[] updSampleTblVls=new Object[]{LPDate.getTimeStampLocalDate(), "null*String"}; // , "null*Date"
         if (actionName.equalsIgnoreCase(cocConfirmedChangeStatus)){
            updSampleTblFlds = LPArray.addValueToArray1D(updSampleTblFlds, "custodian");
            updSampleTblVls = LPArray.addValueToArray1D(updSampleTblVls, userName);
         }
         Object[] updateRecordFieldsByFilter = Rdbms.updateRecordFieldsByFilter( schemaName, objectTable.toLowerCase(), 
                updSampleTblFlds, updSampleTblVls,                
                new String[]{objectFieldName}, new Object[]{objectId});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(updateRecordFieldsByFilter[0].toString())){
            return updateRecordFieldsByFilter;}
                
        switch (objectTable.toLowerCase()){
            case "sample":
                Object[] fieldsOnLogSample = LPArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");
                SampleAudit smpAudit = new SampleAudit();
                smpAudit.sampleAuditAdd( schemaPrefix, auditActionName, objectTable, Integer.valueOf(objectId.toString()), 
                        Integer.valueOf(objectId.toString()), null, null, 
                        fieldsOnLogSample, userName, userRole, appSessionId);                
                break;
            default:
                break;
        }      
        String errorCode = "ChainOfCustody_requestCompleted";
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectTable);
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, objectId);
        errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, actionName.toLowerCase());
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);                          
    }
    
    public Object[] isChangeOfCustodyEnable(String schemaName, String objectTable){
            String procBusinessRuleName = "changeOfCustodyObjects";
            return new Object[]{LPPlatform.LAB_TRUE};
    }
    
}
