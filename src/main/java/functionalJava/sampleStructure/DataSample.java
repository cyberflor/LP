/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sampleStructure;

//import com.sun.rowset.CachedRowSetImpl;
//import java.util.HashMap;
//import java.util.Map;
import databases.Rdbms;
import functionalJava.analysis.UserMethod;
import functionalJava.audit.SampleAudit;
import functionalJava.materialSpec.DataSpec;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;
import databases.DataDataIntegrity;
import functionalJava.unitsOfMeasurement.UnitsOfMeasurement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class DataSample {
    String classVersion = "0.1";
    String errorCode ="";
    Object[] errorDetailVariables= new Object[0];
    
    Rdbms rdbms;
    String lastError;
    Object[] diagnoses = new Object[7];

    String[] mandatoryFields = null;
    Object[] mandatoryFieldsValue = null;

    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    DataDataIntegrity labIntChecker = new DataDataIntegrity();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "sample"; 

    SampleAudit smpAudit = new SampleAudit();
    String grouperName = "";
    
    public DataSample(String grouperName){
        this.grouperName = grouperName;
    }
    
    public void setSampleGrouper(String grouperName){this.grouperName=grouperName;}
    public String getSampleGrouper(){return this.grouperName;}

public void logSampleBySchedule(){}

public Object[] logSampleDev(Rdbms rdbm, String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
    Object[] diag = logSample(rdbm, schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, true);
    return diag;
}

public Object[] logSample(Rdbms rdbm, String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
    Object[] diag = logSample(rdbm, schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, false);
    return diag;
}

Object[] logSample(Rdbms rdbm, String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Boolean devMode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{

    if (devMode==true){
        try {
            StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
            javaDocLineName = "BEGIN";
            javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);
            javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);
            labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
        } catch (SQLException ex) {
            Logger.getLogger(DataSample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
        String query = "";
        tableName = "sample";
        String actionName = "Insert";
        String auditActionName = "ADD_SAMPLE_ANALYSIS";
        
        String schemaDataName = "data";
        String schemaConfigName = "config";

        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);    
        schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName); 
        
        String sampleLevel = tableName;
//        if (this.getSampleGrouper()!=null){sampleLevel=this.getSampleGrouper()+"_"+sampleLevel;}

        mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, sampleLevel, actionName);
        
        String sampleStatusFirst = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), sampleLevel+"_statusFirst");     

        sampleFieldName = labArr.addValueToArray1D(sampleFieldName, "status");
        sampleFieldValue = labArr.addValueToArray1D(sampleFieldValue, sampleStatusFirst);
        // mandatoryFields = getSampleMandatoryFields();
        
    if (devMode==true){
        try {
            StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
            javaDocLineName = "CHECK sampleFieldName and sampleFieldValue match in length";
            javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);
            javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);
            labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
        } catch (SQLException ex) {
            Logger.getLogger(DataSample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    if (devMode==false){
        diagnoses = labArr.checkTwoArraysSameLength(sampleFieldName, sampleFieldValue);
        if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
           errorCode = "DataSample_FieldArraysDifferentSize";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(sampleFieldName));
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(sampleFieldValue));
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
        }
    }    
    if (devMode==true){
        try {
            StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
            javaDocLineName = "CHECK sampleFieldName and sampleFieldValue match in length";
            javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);
            javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);
            labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
        } catch (SQLException ex) {
            Logger.getLogger(DataSample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    if (devMode==false){        
        LabPLANETArray lpa = new LabPLANETArray();
        if (lpa.duplicates(sampleFieldName)){
           errorCode = "DataSample_FieldsDuplicated";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(sampleFieldName));
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                      
        }

        // spec is not mandatory but when any of the fields involved is added to the parameters 
        //  then it turns mandatory all the fields required for linking this entity.
        Integer fieldIndexSpecCode = Arrays.asList(sampleFieldName).indexOf("spec_code");
        Integer fieldIndexSpecCodeVersion = Arrays.asList(sampleFieldName).indexOf("spec_code_version");
        Integer fieldIndexSpecVariationName = Arrays.asList(sampleFieldName).indexOf("spec_variation_name");
        if ((fieldIndexSpecCode!=-1) || (fieldIndexSpecCodeVersion!=-1) || (fieldIndexSpecVariationName!=-1)){
            mandatoryFields = labArr.addValueToArray1D(mandatoryFields, "spec_code");
            mandatoryFields = labArr.addValueToArray1D(mandatoryFields, "spec_code_version");
            mandatoryFields = labArr.addValueToArray1D(mandatoryFields, "spec_variation_name");
        }

        mandatoryFieldsValue = new Object[mandatoryFields.length];
        String configCode = "";
        String mandatoryFieldsMissing = "";
        for (Integer inumLines=0;inumLines<mandatoryFields.length;inumLines++){
            String currField = mandatoryFields[inumLines];
            boolean contains = Arrays.asList(sampleFieldName).contains(currField.toLowerCase());
            if (!contains){
                Object[] sampleDefaultFieldValues = labIntChecker.getTableFieldsDefaulValues(schemaDataName, sampleLevel, actionName);                
                
                                
                if (mandatoryFieldsMissing.length()>0){mandatoryFieldsMissing = mandatoryFieldsMissing + ",";}
                mandatoryFieldsMissing = mandatoryFieldsMissing + currField;
            }else{
                Integer valuePosic = Arrays.asList(sampleFieldName).indexOf(currField);
                mandatoryFieldsValue[inumLines] = sampleFieldValue[valuePosic]; 
                if ("sample_config_code".equals(currField)){configCode = sampleFieldValue[Arrays.asList(sampleFieldName).indexOf(currField)].toString();}
            }        
        }            
        if (mandatoryFieldsMissing.length()>0){
           errorCode = "DataSample_MissingMandatoryFields";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);    
        }               
        
        Object[] diagnosis = rdbm.existsRecord(rdbm, schemaConfigName, tableName, new String[]{"code","code_version"}, new Object[]{sampleTemplate, sampleTemplateVersion});
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnosis[0].toString())){
           errorCode = "DataSample_MissingConfigCode";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleTemplate);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleTemplateVersion);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaConfigName);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, diagnosis[5]);
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);    
        }
        //String[] specialFields = getSpecialFields();
        //String[] specialFieldsFunction = getSpecialFieldsFunction();
        String[] specialFields = labIntChecker.getStructureSpecialFields(schemaDataName, sampleLevel+"_"+"sampleStructure", actionName);
        String[] specialFieldsFunction = labIntChecker.getStructureSpecialFieldsFunction(schemaDataName, sampleLevel+"_"+"sampleStructure", actionName);
        String specialFieldsCheck = "";
        Integer specialFieldIndex = -1;
        
        for (Integer inumLines=0;inumLines<sampleFieldName.length;inumLines++){
            String currField = tableName+"." + sampleFieldName[inumLines];
            String currFieldValue = sampleFieldValue[inumLines].toString();
            boolean contains = Arrays.asList(specialFields).contains(currField);
            if (contains){                    
                    specialFieldIndex = Arrays.asList(specialFields).indexOf(currField);
                    String aMethod = specialFieldsFunction[specialFieldIndex];
                    Method method = null;
                    try {
                        Class<?>[] paramTypes = {Rdbms.class, String[].class, String.class, String.class, Integer.class};
                        method = getClass().getDeclaredMethod(aMethod, paramTypes);
                    } catch (NoSuchMethodException | SecurityException ex) {
                    }
                    Object specialFunctionReturn = method.invoke(this, rdbm, null, schemaPrefix, sampleTemplate, sampleTemplateVersion);      
                    if (specialFunctionReturn.toString().contains("ERROR")){
                        errorCode = "DataSample_SpecialFunctionReturnedERROR";
                        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currField);
                        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, aMethod);
                        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, specialFunctionReturn.toString());
                        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                            
                    } 
            }
        }
        sampleFieldName = lpa.addValueToArray1D(sampleFieldName, "sample_config_code");    
        sampleFieldValue = lpa.addValueToArray1D(sampleFieldValue, sampleTemplate);
        sampleFieldName = lpa.addValueToArray1D(sampleFieldName, "sample_config_code_version");    
        sampleFieldValue = lpa.addValueToArray1D(sampleFieldValue, sampleTemplateVersion); 
        
        if (this.getSampleGrouper().length()>0){tableName=this.getSampleGrouper()+"_"+tableName;}               
        
        diagnoses = rdbm.insertRecordInTable(rdbm, schemaDataName, tableName, sampleFieldName, sampleFieldValue);
        if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
            errorCode = "DataSample_errorInsertingSampleRecord";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, diagnoses[5]);
            return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
        }        

        Object[] fieldsOnLogSample = labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");
        diagnoses = labArr.addValueToArray1D(diagnoses, diagnoses[6]);

        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, tableName, Integer.parseInt(diagnoses[6].toString()), Integer.parseInt(diagnoses[6].toString()), null, null, fieldsOnLogSample, userName, userRole);

        return diagnoses;  
    }    
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "END";
        Integer specialFieldIndex = Arrays.asList(javaDocFields).indexOf("line_name");
        if (specialFieldIndex==-1){
            javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);         
        }else{    
            javaDocValues[specialFieldIndex] = javaDocLineName;             
        }
        labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
    }
    return diagnoses; 
}

public Object[] sampleReception(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, String userRole) throws SQLException{

    String receptionStatus = "RECEIVED";
    String auditActionName = "SAMPLE_RECEPTION";
    
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName); 
    
    Object[][] currSampleStatus = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample", new String[]{"sample_id"}, 
                                                new Object[]{sampleId}, new String[]{"status", "received_by","received_on", "status"});
    if ("LABPLANET_FALSE"==currSampleStatus[0][0]){
        errorCode = "DataSample_SampleNotFound";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleId);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaPrefix);
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
    }
    if (currSampleStatus[0][1]!=null){ 
        errorCode = "DataSample_SampleAlreadyReceived";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleId.toString());
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currSampleStatus[0][2]);
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
    }
    String currentStatus = (String) currSampleStatus[0][0];
    
    String[] sampleFieldName = new String[4];
    Object[] sampleFieldValue = new Object[4];
    Integer iField = 0;

    sampleFieldName[iField] = "status";    
    sampleFieldValue[iField] = receptionStatus;
    iField++;

    sampleFieldName[iField] = "status_previous";    
    sampleFieldValue[iField] = currentStatus;
    iField++;    

    sampleFieldName[iField] = "received_by";    
    sampleFieldValue[iField] = userName;
    iField++;

    sampleFieldName[iField] = "received_on";    
    sampleFieldValue[iField] = (new Date(System.currentTimeMillis()));
    iField++;
    
    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, sampleFieldName, sampleFieldValue, 
                                            new String[] {"sample_id"}, new Object[]{sampleId});
    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
        String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, this.getSampleGrouper()+"_"+"sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
    }    
    return diagnoses;
}

public Object[] changeSamplingDate(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, Date newDate, String userRole) throws SQLException{

    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName); 
    
    String auditActionName = "SAMPLE_CHANGE_SAMPLING_DATE";

    String[] sampleFieldName = new String[1];
    Object[] sampleFieldValue = new Object[1];
    Integer iField = 0;

    sampleFieldName[iField] = "sampling_date";    
    sampleFieldValue[iField] = newDate;
    iField++;
    
    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {"sample_id"}, new Object[]{sampleId});
    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
        errorCode = "DataSample_SamplingDateChangedSuccessfully";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleId.toString());
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));        
        diagnoses = (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);

        String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
    }    
    return diagnoses;
}

public Object[] sampleReceptionCommentAdd(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, String comment, String userRole) throws SQLException{
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName); 
    
    String auditActionName = "SAMPLE_RECEPTION_COMMENT_ADD";

    String[] sampleFieldName = new String[1];
    Object[] sampleFieldValue = new Object[1];
    Integer iField = 0;

    sampleFieldName[iField] = "sampling_comment";    
    sampleFieldValue[iField] = comment;
    iField++;
    
    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {"sample_id"}, new Object[]{sampleId});
    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){        
        errorCode = "DataSample_SampleReceptionCommentAdd";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleId);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
        diagnoses = (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                
        
        String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
    } 
    return diagnoses;
}

public Object[] sampleReceptionCommentRemove(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, String comment, String userRole) throws SQLException{
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName); 

    String auditActionName = "SAMPLE_RECEPTION_COMMENT_REMOVE";
    
    String[] sampleFieldName = new String[1];
    Object[] sampleFieldValue = new Object[1];
    Integer iField = 0;

    sampleFieldName[iField] = "sampling_comment";    
    sampleFieldValue[iField] = "";
    iField++;
    
    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {"sample_id"}, new Object[]{sampleId});
    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
        errorCode = "DataSample_SampleReceptionCommentRemoved";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleId);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
        diagnoses = (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                

        String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
    } 
    return diagnoses;
}

public Object[] setSampleStartIncubationDateTime(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, String userRole) throws SQLException{

    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName); 
    
    String auditActionName = "SAMPLE_SET_INCUBATION_START";

    String[] sampleFieldName = new String[2];
    Object[] sampleFieldValue = new Object[2];
    Integer iField = 0;

    sampleFieldName[iField] = "incubation_start";    
    sampleFieldValue[iField] = (new Date(System.currentTimeMillis()));
    iField++;

    sampleFieldName[iField] = "incubation_passed";    
    sampleFieldValue[iField] = false;
    iField++;
        
    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {"sample_id"}, new Object[]{sampleId});
    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
        errorCode = "DataSample_SampleIncubationStartedSuccessfully";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleId);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
        diagnoses = (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);                
        
        String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
    } 
    return diagnoses;
}

public Object[] setSampleEndIncubationDateTime(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, String userRole) throws SQLException{

    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName); 
    
    String auditActionName = "SAMPLE_SET_INCUBATION_START";

    Object[][] incubationStart = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, tableName, 
            new String[]{"sample_id"}, new Object[]{sampleId}, new String[] {"incubation_start", "incubation_passed"});
    
    if (incubationStart[0][0]==null){        
        errorCode = "DataSample_SampleIncubationEnded_NotStartedYet";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleId);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
    }
    String[] sampleFieldName = new String[2];
    Object[] sampleFieldValue = new Object[2];
    Integer iField = 0;

    sampleFieldName[iField] = "incubation_end";    
    sampleFieldValue[iField] = (new Date(System.currentTimeMillis()));
    iField++;
    
    sampleFieldName[iField] = "incubation_passed";    
    sampleFieldValue[iField] = true;
    iField++;

    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {"sample_id"}, new Object[]{sampleId});
    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
        errorCode = "DataSample_SampleIncubationEndedSuccessfully";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleId);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
        diagnoses = (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);                

        String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
    } 
    return diagnoses;
}

public void _sampleAssignAnalyst(Rdbms rdbm, String schemaPrefix, String userName, Integer testId, String analyst, String userRole){

}

public String[] sampleAnalysisAssignAnalyst(Rdbms rdbm, String schemaPrefix, String userName, Integer testId, String newAnalyst, String userRole) throws SQLException{

    tableName = "sample_analysis";
    String auditActionName = "SAMPLE_ANALYSIS_ANALYST_ASSIGNMENT";

    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);  
    schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName); 

    String testStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusReviewed");
    String testStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusCanceled");    
    
    Boolean assignTestAnalyst = false;
    
    String assignmentModes = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_analystAssigmentModes");
    
    Object[][] testData = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, tableName, new String[]{"test_id"}, new Object[]{testId}, new String[]{"sample_id", "status", "analyst", "analysis", "method_name", "method_version"});    
     if ("LABPLANET_FALSE".equalsIgnoreCase((String) testData[0][0])){
        errorCode = "DataSample_SampleAnalysisNotFound";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testId.toString());
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaPrefix);
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
    }   
 
     
    Integer sampleId = (Integer) testData[0][0];String testStatus = (String) testData[0][1];String testCurrAnalyst = (String) testData[0][2];
    String testAnalysis = (String) testData[0][3];String testMethodName = (String) testData[0][4];Integer testMethodVersion = (Integer) testData[0][5];
    
    if (testCurrAnalyst == null ? newAnalyst == null : testCurrAnalyst.equals(newAnalyst)){
        errorCode = "DataSample_SampleAnalysisAssignment_SameAnalyst";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testCurrAnalyst);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testId.toString());
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaPrefix);
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
    }
    
    // the test status cannot be reviewed or canceled, should be checked
    if (testCurrAnalyst != null){
        if (testStatus.equalsIgnoreCase(testStatusReviewed) || testStatus.equalsIgnoreCase(testStatusCanceled)){
            errorCode = "DataSample_SampleAnalysisAssignment_SameAnalyst";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testStatus);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, newAnalyst);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
        }    
    }       

    Object[][] sampleData = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample", new String[]{"sample_id"}, new Object[]{sampleId}, new String[]{"sample_config_code", "sample_config_code_version", "status", "status"});
    String sampleConfigCode = (String) sampleData[0][0]; Integer sampleConfigCodeVersion = (Integer) sampleData[0][1];
    String sampleStatus = (String) sampleData[0][2];
 
    Object[][] sampleRulesData = rdbm.getRecordFieldsByFilter(rdbm, schemaConfigName, "sample_rules", new String[]{"code", "code_version"}, new Object[]{sampleConfigCode, sampleConfigCodeVersion}, new String[]{"code", "code_version", "analyst_assignment_mode", "analyst_assignment_mode"});
    String testAssignmentMode = (String) sampleRulesData[0][2];
    if (testAssignmentMode==null){testAssignmentMode="null";}
    
    if (!assignmentModes.contains(testAssignmentMode)){
        errorCode = "DataSample_SampleAnalysisAssignment_SameAnalyst";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, "analyst_assignment_mode");
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleConfigCode);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleConfigCodeVersion);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testAssignmentMode);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, assignmentModes);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaPrefix);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testId.toString());
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, newAnalyst);              
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
    }
    if (testAssignmentMode.equalsIgnoreCase("DISABLE")){
        assignTestAnalyst = true;
    }else{            
        UserMethod ana = new UserMethod();
        String userMethodCertificationMode = ana.userMethodCertificationLevel(rdbm, schemaPrefix, testAnalysis, testMethodName, testMethodVersion, newAnalyst);

        String userCertifiedModes = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_analystAssigmentMode"+testAssignmentMode);
        String[] userMethodModesArr = userCertifiedModes.split("\\|");                                    

        assignTestAnalyst = labArr.valueInArray(userMethodModesArr, userMethodCertificationMode);        
        if (!assignTestAnalyst){                            
            errorCode = "DataSample_SampleAnalysisAssignment_AssignmentModeNotImplemented";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testAssignmentMode);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(userMethodModesArr));
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, userMethodCertificationMode);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
            return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
        }        
    }    

    if (assignTestAnalyst){
        String[] updateFieldName = new String[0];
        Object[] updateFieldValue = new Object[0];
        updateFieldName = labArr.addValueToArray1D(updateFieldName, "analyst");
        updateFieldValue = labArr.addValueToArray1D(updateFieldValue, newAnalyst);
//        updateFieldName = labArr.addValueToArray1D(updateFieldName, "analyst_assigned_on");
//        updateFieldValue = labArr.addValueToArray1D(updateFieldValue, rdbm.getCurrentDate());
        updateFieldName = labArr.addValueToArray1D(updateFieldName, "analyst_assigned_by");
        updateFieldValue = labArr.addValueToArray1D(updateFieldValue, userName);
        
        diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, updateFieldName, updateFieldValue, new String[]{"test_id"}, new Object[]{testId}); 
               
        if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){   
              errorCode = "DataSample_SampleAnalysisAssignment_Successfully";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, newAnalyst);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
            diagnoses = (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_TRUE", classVersion, errorCode, errorDetailVariables);          
            String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(updateFieldName, updateFieldValue, ":");            
            smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample_analysis", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
        }else{    
            errorCode = "DataSample_SampleAnalysisAssignment_databaseReturnedError";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, newAnalyst);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
            return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);               
        }   
    }

    errorCode = "DataSample_SampleAnalysisAssignment_EscapeByUnhandledException";        
    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaPrefix);
    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, userName);
    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testId.toString());
    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, newAnalyst);
    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, userRole);
    return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);           
}
        
public String[] sampleAnalysisAddtoSample(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, String[] fieldName, Object[] fieldValue, String userRole) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{

    tableName = "_analysis";
    String actionName = "Insert";
    String auditActionName = "ADD_SAMPLE_ANALYSIS";
    
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);  
    schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName); 
    
    String sampleLevel = "sample";
    if ((this.getSampleGrouper().length())>0){sampleLevel=this.getSampleGrouper()+"_"+sampleLevel;}
    String sampleTableName = "sample";    
    if (this.getSampleGrouper().length()>0){sampleTableName=this.getSampleGrouper()+"_"+sampleTableName;}
            
    mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, sampleLevel+tableName, actionName);

    if (fieldName.length!=fieldValue.length){
            errorCode = "DataSample_FieldArraysDifferentSize";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldName));
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldValue));
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
    }
    LabPLANETArray lpa = new LabPLANETArray();
    if (lpa.duplicates(fieldName)){
           errorCode = "DataSample_FieldsDuplicated";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldName));
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);   
    }

    mandatoryFieldsValue = new Object[mandatoryFields.length];
    String mandatoryFieldsMissing = "";
    for (Integer inumLines=0;inumLines<mandatoryFields.length;inumLines++){
        String currField = mandatoryFields[inumLines];
        boolean contains = Arrays.asList(fieldName).contains(currField.toLowerCase());
        if (!contains){
            if (mandatoryFieldsMissing.length()>0){mandatoryFieldsMissing = mandatoryFieldsMissing + ",";}
            mandatoryFieldsMissing = mandatoryFieldsMissing + currField;
        }
        else{
            Integer valuePosic = Arrays.asList(fieldName).indexOf(currField);            
            mandatoryFieldsValue[inumLines] = fieldValue[valuePosic]; 
        }
    }            
    if (mandatoryFieldsMissing.length()>0){
           errorCode = "DataSample_sampleAnalaysisAddToSample_MissingMandatoryFields";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldName));
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaConfigName);
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);   
    }

// set first status. Begin    
    
    String firstStatus = "NOT_STARTED"; //rdbm.getParameterBundle(schemaDataName, "sampleAnalysis_statusFirst");      
    Integer specialFieldIndex = Arrays.asList(fieldName).indexOf("status");
    if (specialFieldIndex==-1){
        fieldName = labArr.addValueToArray1D(fieldName,"status");
        fieldValue = labArr.addValueToArray1D(fieldValue, firstStatus);
    }else{
        fieldValue[specialFieldIndex] = firstStatus; 
    }    
// set first status. End        
// Spec Business Rule. Allow other analyses. Begin
    Object[][] sampleData = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, sampleTableName, new String[]{"sample_id"}, new Object[]{sampleId}, 
        new String[]{"sample_id","sample_id","sample_id", "status"});
    if ("LABPLANET_FALSE".equalsIgnoreCase(sampleData[0][0].toString())){
           errorCode = "DataSample_SampleNotFound";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleId.toString());
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);    
    }   
    Object[][] sampleSpecData = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, sampleTableName, new String[]{"sample_id"}, new Object[]{sampleId}, 
        new String[]{"spec_code","spec_code_version","spec_variation_name", "status"});
    
    if (!"LABPLANET_FALSE".equalsIgnoreCase(sampleSpecData[0][0].toString())){                
        String sampleSpecCode = (String) sampleSpecData[0][0];Integer sampleSpecCodeVersion = (Integer) sampleSpecData[0][1];
        String sampleSpecVariationName = (String) sampleSpecData[0][2];
        if (sampleSpecCode!=null){
            Object[][] specRules = rdbm.getRecordFieldsByFilter(rdbm, schemaConfigName, "spec_rules", new String[]{"code", "config_version"}, new Object[]{sampleSpecCode, sampleSpecCodeVersion}, 
            new String[]{"allow_other_analysis","allow_multi_spec","code", "config_version"});
            if ("LABPLANET_FALSE".equalsIgnoreCase(specRules[0][0].toString())){
                errorCode = "DataSample_SpecRuleNotFound";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleSpecCode);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleSpecCodeVersion.toString());
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                    
            }    
            if ((Boolean) specRules[0][0]==false){
                String[] specAnalysisFieldName = new String[]{"analysis", "method_name", "method_version"};
                Object[] specAnalysisFieldValue = new Object[0];
                for (String iFieldN: specAnalysisFieldName){
                    specialFieldIndex = Arrays.asList(fieldName).indexOf(iFieldN);
                    if (specialFieldIndex==-1){specAnalysisFieldValue = labArr.addValueToArray1D(fieldValue,null);} 
                    else{specAnalysisFieldValue = labArr.addValueToArray1D(specAnalysisFieldValue, fieldValue[specialFieldIndex]);}                    
                }
                specAnalysisFieldName = labArr.addValueToArray1D(specAnalysisFieldName,"code");
                specAnalysisFieldValue = labArr.addValueToArray1D(specAnalysisFieldValue,sampleSpecCode);
                specAnalysisFieldName = labArr.addValueToArray1D(specAnalysisFieldName,"config_version");
                specAnalysisFieldValue = labArr.addValueToArray1D(specAnalysisFieldValue,sampleSpecCodeVersion);
                specAnalysisFieldName = labArr.addValueToArray1D(specAnalysisFieldName,"variation_name");
                specAnalysisFieldValue = labArr.addValueToArray1D(specAnalysisFieldValue,sampleSpecVariationName);  
                Object[] analysisInSpec = rdbm.existsRecord(rdbm, schemaConfigName, "spec_limits", specAnalysisFieldName, specAnalysisFieldValue);
                if ("LABPLANET_FALSE".equalsIgnoreCase(analysisInSpec[0].toString())){
                    errorCode = "DataSample_SpecLimitNotFound";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(labArr.joinTwo1DArraysInOneOf1DString(specAnalysisFieldName, specAnalysisFieldValue, ":")));
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                    return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                          
                }
            }
        }
    }    
// Spec Business Rule. Allow other analyses. End    
    
    
//    String[] specialFields = getSpecialFields();
//    String[] specialFieldsFunction = getSpecialFieldsFunction();

    String[] specialFields = labIntChecker.getStructureSpecialFields(schemaDataName, sampleLevel+"Structure", actionName);
    String[] specialFieldsFunction = labIntChecker.getStructureSpecialFieldsFunction(schemaDataName, sampleLevel+"Structure", actionName);
    
    String specialFieldsCheck = "";
    for (Integer inumLines=0;inumLines<fieldName.length;inumLines++){
        String currField = tableName+"." + fieldName[inumLines];
        String currFieldValue = fieldValue[inumLines].toString();
        boolean contains = Arrays.asList(specialFields).contains(currField);
        if (contains){                    
                specialFieldIndex = Arrays.asList(specialFields).indexOf(currField);
                String aMethod = specialFieldsFunction[specialFieldIndex];
                Method method = null;
                try {
                    Class<?>[] paramTypes = {Rdbms.class, String.class};
                    method = getClass().getDeclaredMethod(aMethod, paramTypes);
                } catch (NoSuchMethodException | SecurityException ex) {
//                    Logger.getLogger(configSpecStructure.class.getName()).log(Level.SEVERE, null, ex);
                }
                String[] parameters = new String[3];
/*                parameters[0]=schemaDataName;
                parameters[1]=currFieldValue;
                parameters[2]=newCode;*/
                Object specialFunctionReturn = method.invoke(this, rdbm, schemaPrefix);      
                if (specialFunctionReturn.toString().contains("ERROR")){
                    errorCode = "DataSample_SpecialFunctionReturnedERROR";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currField);
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, aMethod);
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, specialFunctionReturn.toString());
                    return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                                                  
                }
        }
    }    
    specialFieldIndex = 0;
    Object value = null;
    Object[] whereResultFieldValue = new Object[0];
    String[] whereResultFieldName = new String[0];
    String fieldNeed = "analysis";
    whereResultFieldName = labArr.addValueToArray1D(whereResultFieldName, fieldNeed);
    specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(fieldNeed);
    if (specialFieldIndex==-1){
        specialFieldIndex = Arrays.asList(fieldName).indexOf(fieldNeed);
        value = fieldValue[specialFieldIndex];    
    }else{value = mandatoryFieldsValue[specialFieldIndex];}        
    whereResultFieldValue = labArr.addValueToArray1D(whereResultFieldValue, value);
    
    fieldNeed = "method_name";
    whereResultFieldName = labArr.addValueToArray1D(whereResultFieldName, fieldNeed);
    specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(fieldNeed);
    if (specialFieldIndex==-1){
        specialFieldIndex = Arrays.asList(fieldName).indexOf(fieldNeed);
        value = fieldValue[specialFieldIndex];    
    }else{value = mandatoryFieldsValue[specialFieldIndex];}        
    whereResultFieldValue = labArr.addValueToArray1D(whereResultFieldValue, value);

    fieldNeed = "method_version";
    whereResultFieldName = labArr.addValueToArray1D(whereResultFieldName, fieldNeed);
    specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(fieldNeed);
    if (specialFieldIndex==-1){
        specialFieldIndex = Arrays.asList(fieldName).indexOf(fieldNeed);
        value = fieldValue[specialFieldIndex];    
    }else{value = mandatoryFieldsValue[specialFieldIndex];}        
    whereResultFieldValue = labArr.addValueToArray1D(whereResultFieldValue, value);

    String[] getResultFields = new String[0];    
    getResultFields = labArr.addValueToArray1D(getResultFields, "param_name");
    getResultFields = labArr.addValueToArray1D(getResultFields, "mandatory");
    getResultFields = labArr.addValueToArray1D(getResultFields, "analysis");
    getResultFields = labArr.addValueToArray1D(getResultFields, "param_type");
    getResultFields = labArr.addValueToArray1D(getResultFields, "num_replicas");
    
    Object[][] resultFieldRecords = rdbm.getRecordFieldsByFilter(rdbm, schemaConfigName, "analysis_method_params", whereResultFieldName, whereResultFieldValue, getResultFields);
 
    if ("LABPLANET_FALSE".equalsIgnoreCase(resultFieldRecords[0][0].toString())){ 
        errorCode = "DataSample_AnalysisMethodParamsNotFound";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(labArr.joinTwo1DArraysInOneOf1DString(whereResultFieldName, whereResultFieldValue, ":")));
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                          
    }
    resultFieldRecords = labArr.addColumnToArray2D(resultFieldRecords, sampleId);
    getResultFields = labArr.addValueToArray1D(getResultFields, "sample_id");
    resultFieldRecords = labArr.addColumnToArray2D(resultFieldRecords, 0);
    getResultFields = labArr.addValueToArray1D(getResultFields, "test_id");    
    
    String[] resultMandatoryFields = mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, sampleLevel, actionName);
    String[] resultDefaulFields = labIntChecker.getTableFieldsDefaulValues(schemaDataName, tableName, actionName);
    Object[] resultDefaulFieldValue = labIntChecker.getTableFieldsDefaulValues(schemaDataName, tableName, actionName);
    
    Object[] resultMandatoryFieldsValue = new Object[resultMandatoryFields.length];
    String resultMandatoryFieldsMissing = "";
    for (Integer inumLines=0;inumLines<resultMandatoryFieldsValue.length;inumLines++){
        String currField = resultMandatoryFields[inumLines];
        boolean contains = Arrays.asList(getResultFields).contains(currField.toLowerCase());
        if (!contains){     
            Integer valuePosic = Arrays.asList(resultDefaulFields).indexOf(currField.toLowerCase());
            if (valuePosic==-1){
                if (resultMandatoryFieldsMissing.length()>0){resultMandatoryFieldsMissing = resultMandatoryFieldsMissing + ",";}
                resultMandatoryFieldsMissing = resultMandatoryFieldsMissing + currField;
            }else{
                Object currFieldValue = resultDefaulFieldValue[valuePosic];    
                resultFieldRecords = labArr.addColumnToArray2D(resultFieldRecords, currFieldValue);
                getResultFields = labArr.addValueToArray1D(getResultFields, currField);                
            }    
        }
        else{
            Integer valuePosic = Arrays.asList(fieldName).indexOf(currField);            
//            resultMandatoryFields[inumLines] = fieldValue[valuePosic]; 
        }
    }            
    if (resultMandatoryFieldsMissing.length()>0){
           errorCode = "DataSample_MissingMandatoryFields";
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
           return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);   
    }
    
    
    LabPLANETArray labArr = new LabPLANETArray();
    fieldName = labArr.addValueToArray1D(fieldName, "sample_id");
    fieldValue = labArr.addValueToArray1D(fieldValue, sampleId);    
//    fieldName = labArr.addValueToArray1D(fieldName, "assigned_on");
//    fieldValue = labArr.addValueToArray1D(fieldValue, rdbm.getCurrentDate());    
    fieldName = labArr.addValueToArray1D(fieldName, "assigned_by");
    fieldValue = labArr.addValueToArray1D(fieldValue, userName);   
    String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(fieldName, fieldValue, ":");
    diagnoses = rdbm.insertRecordInTable(rdbm, schemaDataName, sampleLevel+"_analysis", fieldName, fieldValue);
    
    Integer testId = Integer.parseInt(diagnoses[6].toString());
    
    smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, sampleLevel+"_analysis", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
    
    Integer valuePosic = Arrays.asList(getResultFields).indexOf("test_id");
    if (valuePosic==-1){
        resultFieldRecords = labArr.addColumnToArray2D(resultFieldRecords, testId);
        getResultFields = labArr.addValueToArray1D(getResultFields, "test_id");        
    }else{    
        resultFieldRecords = labArr.setColumnValueToArray2D(resultFieldRecords, valuePosic, testId);
    }    
    valuePosic = Arrays.asList(getResultFields).indexOf("method_name");
    if (valuePosic==-1){        
        resultFieldRecords = labArr.addColumnToArray2D(resultFieldRecords, fieldValue[Arrays.asList(fieldName).indexOf("method_name")]);
        getResultFields = labArr.addValueToArray1D(getResultFields, "method_name");        
    }
    valuePosic = Arrays.asList(getResultFields).indexOf("method_version");
    if (valuePosic==-1){        
        resultFieldRecords = labArr.addColumnToArray2D(resultFieldRecords, fieldValue[Arrays.asList(fieldName).indexOf("method_version")]);
        getResultFields = labArr.addValueToArray1D(getResultFields, "method_version");        
    }
    for (Object[] resultFieldRecord : resultFieldRecords) {
        Object[] fieldVal = new Object[0];
        for (int col = 0; col < resultFieldRecords[0].length; col++) {
            fieldVal = labArr.addValueToArray1D(fieldVal, resultFieldRecord[col]);
        }
        valuePosic = Arrays.asList(getResultFields).indexOf("num_replicas");
        Integer numReplicas = 1;
        String resultReplicaFieldName = "replica";
        if (valuePosic==-1){
            valuePosic = Arrays.asList(getResultFields).indexOf("replica");
            if (valuePosic==-1){
                getResultFields = labArr.addValueToArray1D(getResultFields, resultReplicaFieldName);
                fieldVal = labArr.addValueToArray1D(fieldVal, numReplicas);
                valuePosic = fieldVal.length-1;
            }
        }else{
            numReplicas = (Integer) fieldVal[valuePosic];
            getResultFields[valuePosic] = resultReplicaFieldName;
            if ((numReplicas==null) || (numReplicas==0)){
                numReplicas = 1;
                fieldVal[valuePosic] = 1;
            }
        }   for ( Integer iNumReps = 1;iNumReps<=numReplicas;iNumReps++){
            fieldVal[valuePosic] = iNumReps;
            diagnoses = rdbm.insertRecordInTable(rdbm, schemaDataName, sampleLevel+"_analysis_result", getResultFields, fieldVal);

            fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(getResultFields, fieldVal, ":");
            Integer resultId = Integer.parseInt(diagnoses[6].toString());
            smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, sampleLevel+"_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
        }
    } 
    
    //String[] diagnoses2 = sampleAnalysisEvaluateStatus(rdbm, schemaPrefix, userName, sampleId, testId);
    Object[] diagnoses3 = sampleEvaluateStatus(rdbm, schemaPrefix, userName, sampleId, auditActionName, userRole);

    errorCode = "DataSample_SampleAnalysisAddedSuccessfully";
    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, "");
    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testId.toString());
    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
    return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);       
}

public Object[] sampleEvaluateStatus(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, String parentAuditAction, String userRole) throws SQLException{
    
    String auditActionName = "SAMPLE_EVALUATE_STATUS";
    if (parentAuditAction!=null){auditActionName = parentAuditAction + ":"+auditActionName;}
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);  
    schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName); 
    tableName = "sample_analysis";      
            
    String sampleStatusIncomplete = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusIncomplete");
    String sampleStatusComplete = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusComplete");
    
    String[] diagnoses = null;
    String smpPrevStatus=""; String smpAnaNewStatus="";
    
    diagnoses = (String[]) rdbm.existsRecord(rdbm, schemaDataName, "sample_analysis", 
                                        new String[]{"sample_id","status in ('NOT_STARTED','STARTED','INCOMPLETE')"}, 
                                        new Object[]{sampleId, "IN()"});
    if (diagnoses[3].equalsIgnoreCase("LABPLANET_TRUE")){smpAnaNewStatus=sampleStatusIncomplete;}
    else{smpAnaNewStatus=sampleStatusComplete;}
    
    diagnoses = (String[]) rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample", 
            new String[]{"status"}, 
            new Object[]{smpAnaNewStatus},
            new String[]{"sample_id"},
            new Object[]{sampleId});
    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0])){
        String[] fieldsForAudit = new String[0];
        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+smpAnaNewStatus);
        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);        
    }      
    return diagnoses;
}

public Object[] sampleAnalysisEvaluateStatus(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, Integer testId, String parentAuditAction, String userRole) throws SQLException{
    
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    schemaDataName = "data";
    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);  
    
    tableName = "sample_analysis_result";      
    
    String auditActionName = "SAMPLE_ANALYSIS_EVALUATE_STATUS";

    if (parentAuditAction!=null){auditActionName = parentAuditAction + ":"+auditActionName;}
    
    ResourceBundle prop = rdbm.getParameterBundle(schemaDataName.replace("\"", ""));
    String sampleAnalysisStatusIncomplete = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusIncomplete");
    String sampleAnalysisStatusComplete = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusComplete");
   
    Object[] diagnoses = null;
    String smpAnaPrevStatus=""; String smpAnaNewStatus="";
    
    diagnoses = rdbm.existsRecord(rdbm, schemaDataName, tableName, 
                                        new String[]{"test_id","status","mandatory"}, 
                                        new Object[]{testId, "BLANK", true});
    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){smpAnaNewStatus=sampleAnalysisStatusIncomplete;}
    else{smpAnaNewStatus=sampleAnalysisStatusComplete;}
    
    diagnoses = (String[]) rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis", 
            new String[]{"status"}, 
            new Object[]{smpAnaNewStatus},
            new String[]{"test_id"},
            new Object[]{testId});
    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
        String[] fieldsForAudit = new String[0];
        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+smpAnaNewStatus);
        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample_analysis", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);        
    }    
    Object[] diagnoses2 = sampleEvaluateStatus(rdbm, schemaPrefix, userName, sampleId, parentAuditAction, userRole);
    return diagnoses;
}

public Object[] sampleAnalysisResultEntry(Rdbms rdbm, String schemaPrefix, String userName, Integer resultId, Object resultValue, String userRole) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{

    tableName = "sample_analysis_result";  
    String actionName = "Insert";
    String auditActionName = "SAMPLE_ANALYSIS_RESULT_ENTRY";
    
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);  
    schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName); 
    
    String specEvalNoSpec = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusSpecEvalNoSpec");
    String specEvalNoSpecParamLimit = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusSpecEvalNoSpecParamLimit");
    String resultStatusEntered = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusEntered");
    String resultStatusUpdated = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusUpdated");
    String resultStatusDefault = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusFirst");
    String resultStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusReviewed");
    String resultStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusCanceled");    

    String specArgumentsSeparator = "\\*";
    
    Boolean specMinSpecStrictDefault = Boolean.getBoolean(rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_minStrictSpecWhenNotSpecified"));
    Boolean specMinControlStrictDefault = Boolean.getBoolean(rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_minStrictControlWhenNotSpecified"));
    Boolean specMaxControlStrictDefault = Boolean.getBoolean(rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_maxStrictControlWhenNotSpecified"));
    Boolean specMaxSpecStrictDefault = Boolean.getBoolean(rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_maxStrictSpecWhenNotSpecified"));
               
    mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, this.getSampleGrouper()+"_"+"sample_analysis", actionName);

    String[] fieldsName = new String[0];
    Object[] fieldsValue = new Object[0];    
    fieldsName = labArr.addValueToArray1D(fieldsName, "raw_value");
    fieldsValue = labArr.addValueToArray1D(fieldsValue, resultValue);

     Object[][] resultData = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis_result", 
        new String[]{"result_id"}, 
        new Object[]{resultId}, 
        new String[]{"sample_id","test_id", "analysis", "method_name", "method_version","param_name", "status", "raw_value", "uom", "uom_conversion_mode"});
    if ("LABPLANET_FALSE".equals(resultData[0][0].toString())){
        errorCode = "DataSample_SampleAnalysisResultNotFound";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultId.toString());
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);    
    }
    Integer sampleId = (Integer)resultData[0][0];Integer testId = (Integer)resultData[0][1];
    String analysis = (String)resultData[0][2];String methodName = (String)resultData[0][3];Integer methodVersion = (Integer)resultData[0][4];
    String paramName = (String)resultData[0][5];String currResultStatus = (String)resultData[0][6];String currRawValue = (String)resultData[0][7];
    String resultUomName = (String)resultData[0][8];String resultUomConversionMode = (String)resultData[0][9];
    
    if (resultStatusReviewed.equalsIgnoreCase(currResultStatus) || resultStatusCanceled.equalsIgnoreCase(currResultStatus)){
        errorCode = "DataSample_SampleAnalysisResultLocked";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currResultStatus);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultId.toString());
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaConfigName);
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);         
    }
    
    if ( (currRawValue!=null) && (currRawValue.equalsIgnoreCase(resultValue.toString())) ){
        errorCode = "DataSample_SampleAnalysisResultSampleValue";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultId.toString());
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currRawValue);
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);         
    }
    
    Object[][] sampleData = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample", new String[]{"sample_id"}, new Object[]{sampleId}, 
        new String[]{"sample_id","sample_id","sample_id", "status", "sample_config_code", "sample_config_code_version"});
    if ("LABPLANET_FALSE".equals(sampleData[0][0].toString())){
        errorCode = "DataSample_SampleNotFound";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleId.toString());
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);   
    }  
    String sampleConfigCode = (String) sampleData[0][4];Integer sampleConfigCodeVersion = (Integer) sampleData[0][5];
    
    Object[][] sampleSpecData = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample", new String[]{"sample_id"}, new Object[]{sampleId}, 
        new String[]{"spec_code","spec_code_version","spec_variation_name", "status", "sample_config_code", "sample_config_code_version"});
    String sampleSpecCode = null; Integer sampleSpecCodeVersion = null;
    String sampleSpecVariationName = null; String status = null; 
    if (!"LABPLANET_FALSE".equalsIgnoreCase(sampleSpecData[0][0].toString())){
        sampleSpecCode = (String) sampleData[0][0]; sampleSpecCodeVersion = (Integer) sampleData[0][1];
        sampleSpecVariationName = (String) sampleData[0][2]; status = (String) sampleData[0][3];        
    }
    Object[][] sampleRulesData = rdbm.getRecordFieldsByFilter(rdbm, schemaConfigName, "sample_rules", new String[]{"code", "code_version"}, new Object[]{sampleConfigCode, sampleConfigCodeVersion}, 
        new String[]{"test_analyst_required", "test_analyst_required", "test_analyst_required", "test_analyst_required"});
    if ("LABPLANET_FALSE".equalsIgnoreCase(sampleRulesData[0][0].toString())){    
        errorCode = "DataSample_SampleRulesNotFound";
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, "analyst_assignment_mode");
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleConfigCode);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleConfigCodeVersion);
        errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaConfigName);
        return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);        
    }    
    Boolean analystRequired = (Boolean) sampleRulesData[0][1];
    if (analystRequired){
        Object[][] testData = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis", new String[]{"test_id"}, new Object[]{testId}, 
            new String[]{"test_id", "analyst", "analyst_assigned_on", "analyst_assigned_by"});
        if ("LABPLANET_FALSE".equalsIgnoreCase(sampleRulesData[0][0].toString())){
            errorCode = "DataSample_SampleAnalysisNotFound";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
            return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);          
        }    
        String testAnalyst = (String) testData[0][1];String testAnalystBy = (String) testData[0][3];
        if (testAnalyst==null){
            errorCode = "DataSample_SampleAnalysisRuleAnalystNotAssigned";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleConfigCode);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleConfigCodeVersion.toString());
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
            return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                     
        }
        if (!testAnalyst.equalsIgnoreCase(userName)){
            errorCode = "DataSample_SampleAnalysisRuleOtherAnalystEnterResult";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, testAnalyst);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, userName);
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
            return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                     
        }
    }
    String newResultStatus = currResultStatus;
    if (newResultStatus.equalsIgnoreCase(resultStatusDefault)){
        newResultStatus = "ENTERED";
    }else{   
        newResultStatus = "RE-ENTERED";
    }
    if (sampleSpecCode==null){
        fieldsName = labArr.addValueToArray1D(fieldsName, "spec_eval");
        fieldsValue = labArr.addValueToArray1D(fieldsValue, specEvalNoSpec);
        fieldsName = labArr.addValueToArray1D(fieldsName, "entered_by");
        fieldsValue = labArr.addValueToArray1D(fieldsValue, userName);
        fieldsName = labArr.addValueToArray1D(fieldsName, "entered_on");
        fieldsValue = labArr.addValueToArray1D(fieldsValue, rdbm.getCurrentDate());
        fieldsName = labArr.addValueToArray1D(fieldsName, "status");
        fieldsValue = labArr.addValueToArray1D(fieldsValue, newResultStatus);
        diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, fieldsName, fieldsValue, new String[] {"result_id"} , new Object[] {resultId});
        if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
            String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
            smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
        }
        if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
            Object[] diagnoses2 = sampleAnalysisEvaluateStatus(rdbm, schemaPrefix, userName, sampleId,testId, auditActionName, userRole);
        }    
        String[] whereFields = new String[]{"user_id", "analysis", "method_name", "method_version"};
        Object[] whereFieldsValue = new Object[]{userName, analysis, methodName, methodVersion};
        String[] updFields = new String[]{"last_analysis_on", "last_sample", "last_sample_analysis"};
        Object[] updFieldsValue = new Object[]{rdbm.getLocalDate(), sampleId, testId};            
        Object[][] userMethodInfo;
        userMethodInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "user_method", 
                                                whereFields,
                                                whereFieldsValue,
                                                new String[]{"user_method_id", "user_id", "analysis", "method_name", "method_version"});
        if (!("LABPLANET_FALSE".equalsIgnoreCase(userMethodInfo[0][0].toString())) ){ 
            diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "user_method", 
                                                updFields, updFieldsValue, whereFields, whereFieldsValue);
        if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                updFields = labArr.addValueToArray1D(updFields, whereFields);
                updFieldsValue = labArr.addValueToArray1D(updFieldsValue, whereFieldsValue);
                String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(updFields, updFieldsValue, ":");
                auditActionName = "UPDATE LAST ANALYSIS USER METHOD";
                smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "user_method", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
            }    
        }         
        return diagnoses;
    }

    Object[][] specLimits = rdbm.getRecordFieldsByFilter(rdbm, schemaConfigName, "spec_limits", 
        new String[]{"code", "config_version", "variation_name", "analysis", "method_name","method_version","parameter"}, 
        new Object[]{sampleSpecCode, sampleSpecCodeVersion, sampleSpecVariationName, analysis, methodName, methodVersion, paramName}, 
        new String[]{"limit_id","rule_type","rule_variables", "limit_id", "uom", "uom_conversion_mode"});
    if (!"LABPLANET_FALSE".equalsIgnoreCase(specLimits[0][0].toString())){
        fieldsName = labArr.addValueToArray1D(fieldsName, "spec_eval");
        fieldsValue = labArr.addValueToArray1D(fieldsValue, specEvalNoSpecParamLimit);
        fieldsName = labArr.addValueToArray1D(fieldsName, "entered_by");
        fieldsValue = labArr.addValueToArray1D(fieldsValue, userName);
        fieldsName = labArr.addValueToArray1D(fieldsName, "entered_on");
        fieldsValue = labArr.addValueToArray1D(fieldsValue, rdbm.getCurrentDate());
        fieldsName = labArr.addValueToArray1D(fieldsName, "status");
        fieldsValue = labArr.addValueToArray1D(fieldsValue, newResultStatus);
        diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, fieldsName, fieldsValue, new String[] {"result_id"} , new Object[] {resultId});
        if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){            String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
            smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
        }        
        if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){            Object[] diagnoses2 = sampleAnalysisEvaluateStatus(rdbm, schemaPrefix, userName, sampleId,testId, auditActionName, userRole);
        }
        String[] whereFields = new String[]{"user_id", "analysis", "method_name", "method_version"};
        Object[] whereFieldsValue = new Object[]{userName, analysis, methodName, methodVersion};
        String[] updFields = new String[]{"last_analysis_on", "last_sample", "last_sample_analysis"};
        Object[] updFieldsValue = new Object[]{rdbm.getLocalDate(), sampleId, testId};            
        Object[][] userMethodInfo;
        userMethodInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "user_method", 
                                                whereFields,
                                                whereFieldsValue,
                                                new String[]{"user_method_id", "user_id", "analysis", "method_name", "method_version"});
        if (!("LABPLANET_FALSE".equalsIgnoreCase(userMethodInfo[0][0].toString())) ){ 
            diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "user_method", 
                                                updFields, updFieldsValue, whereFields, whereFieldsValue);
            if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){                updFields = labArr.addValueToArray1D(updFields, whereFields);
                updFieldsValue = labArr.addValueToArray1D(updFieldsValue, whereFieldsValue);
                String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(updFields, updFieldsValue, ":");
                auditActionName = "UPDATE LAST ANALYSIS USER METHOD";
                smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "user_method", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
            }    
        }         
        return diagnoses;          
    }    
    Integer limitId = (Integer) specLimits[0][0];String ruleType = (String) specLimits[0][1];
    String ruleVariables = (String) specLimits[0][2]; 
    String specUomName = (String) specLimits[0][4]; String specUomConversionMode = (String) specLimits[0][5];

    Boolean requiresUnitsConversion = false;    
    Float resultConverted = null;
    if (resultUomName!=null){
        if (!resultUomName.equalsIgnoreCase(specUomName)){
            if (specUomConversionMode==null || specUomConversionMode.equalsIgnoreCase("DISABLED") || ((!specUomConversionMode.contains(resultUomName)) && !specUomConversionMode.equalsIgnoreCase("ALL")) ){
                errorCode = "DataSample_SampleAnalysisResult_ConversionNotAllowed";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, specUomConversionMode);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, specUomName);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultUomName);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, limitId.toString());
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                      
            }    
           
            requiresUnitsConversion=true;
            UnitsOfMeasurement UOM = new UnitsOfMeasurement();            
            Object[] convDiagnoses = UOM.convertValue(rdbm, schemaPrefix, Float.parseFloat(resultValue.toString()), resultUomName, specUomName);
            if ((Boolean) convDiagnoses[0] == false){
                errorCode = "DataSample_SampleAnalysisResult_ConverterFALSE";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultId.toString());
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, convDiagnoses[3].toString());
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                  
            }
            resultConverted = (Float) convDiagnoses[1];
        }
    }
    
    DataSpec resChkSpec = new DataSpec();
    Object[] resSpecEvaluation = null;
    switch (ruleType.toLowerCase()){
        case "qualitative":            
            String[] QualitSpecTestingArray = ruleVariables.split(specArgumentsSeparator);
            String specRuleType = QualitSpecTestingArray[0]; 
            String specValues = QualitSpecTestingArray[1]; 
            String specSeparator = null;            
            if (QualitSpecTestingArray.length==3){specSeparator = QualitSpecTestingArray[2];}
            String specListName = null;
            
            resSpecEvaluation = resChkSpec.resultCheck((String) resultValue, specRuleType, specValues, specSeparator, specListName);
            if ("LABPLANET_FALSE".equalsIgnoreCase(resSpecEvaluation[0].toString())){
               errorCode = "DataSample_SampleAnalysisResult_QualitativeSpecNotRecognized";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultId.toString());
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, ruleVariables);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                  
            }
            fieldsName = labArr.addValueToArray1D(fieldsName, "spec_eval");
            fieldsValue = labArr.addValueToArray1D(fieldsValue, resSpecEvaluation[0]);
            fieldsName = labArr.addValueToArray1D(fieldsName, "spec_eval_detail");
            fieldsValue = labArr.addValueToArray1D(fieldsValue, resSpecEvaluation[1]);
            fieldsName = labArr.addValueToArray1D(fieldsName, "entered_by");
            fieldsValue = labArr.addValueToArray1D(fieldsValue, userName);
            fieldsName = labArr.addValueToArray1D(fieldsName, "entered_on");
            fieldsValue = labArr.addValueToArray1D(fieldsValue, rdbm.getCurrentDate());
            fieldsName = labArr.addValueToArray1D(fieldsName, "status");
            fieldsValue = labArr.addValueToArray1D(fieldsValue, newResultStatus);
            diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, fieldsName, fieldsValue, new String[] {"result_id"} , new Object[] {resultId});
            if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
                smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
            }  
            if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                Object[] diagnoses2 = sampleAnalysisEvaluateStatus(rdbm, schemaPrefix, userName, sampleId,testId, auditActionName, userRole);
            }
            return diagnoses;                
            
        case "quantitative":
            resultValue = Float.parseFloat(resultValue.toString());
            String[] QuantiSpecTestingArray = ruleVariables.split(specArgumentsSeparator);
            Float minSpec = null;Boolean minStrict = specMinSpecStrictDefault;Float maxSpec = null;Boolean maxStrict = specMaxSpecStrictDefault;   
            Float minControl = null;Boolean minControlStrict = specMinControlStrictDefault;Float maxControl = null;Boolean maxControlStrict = specMaxControlStrictDefault;                  
            for (Integer iField=0; iField<QuantiSpecTestingArray.length;iField++){
                String curParam = QuantiSpecTestingArray[iField];
                
                if (curParam.toUpperCase().contains("MINSPEC")){
                        curParam = curParam.replace("MINSPEC", "");             minSpec = Float.parseFloat(curParam);
                }        
                if (curParam.toUpperCase().contains("MINSPECSTRICT")){
                        curParam = curParam.replace("MINSPECSTRICT", "");       minStrict = Boolean.getBoolean(curParam);
                }        
                if (curParam.toUpperCase().contains("MINCONTROL")){
                        curParam = curParam.replace("MINCONTROL", "");          minControl = Float.parseFloat(curParam);
                }        
                if (curParam.toUpperCase().contains("MINCONTROLSTRICT")){
                        curParam = curParam.replace("MINCONTROLSTRICT", "");       minControlStrict = Boolean.getBoolean(curParam);
                }        
                if (curParam.toUpperCase().contains("MAXCONTROL")){
                        curParam = curParam.replace("MAXCONTROL", "");          maxControl = Float.parseFloat(curParam);
                }        
                if (curParam.toUpperCase().contains("MAXCONTROLTRICT")){
                        curParam = curParam.replace("MAXCONTROLSTRICT", "");       maxControlStrict = Boolean.getBoolean(curParam);
                }        
                if (curParam.toUpperCase().contains("MAXSPEC")){
                        curParam = curParam.replace("MAXSPEC", "");              maxSpec = Float.parseFloat(curParam);
                }        
                if (curParam.toUpperCase().contains("MAXSPECSTRICT")){
                        curParam = curParam.replace("MAXSPECSTRICT", "");       maxStrict = Boolean.getBoolean(curParam);
                }        
            }       
            if (ruleVariables.contains("CONTROL")){
                if (requiresUnitsConversion){
                    resSpecEvaluation = resChkSpec.resultCheck(resultConverted, minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);
                }else {
                    resSpecEvaluation = resChkSpec.resultCheck((Float) resultValue, minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);                            
                }    
            }else{
                if (requiresUnitsConversion){
                    resSpecEvaluation = resChkSpec.resultCheck(resultConverted, minSpec, maxSpec, minStrict, maxStrict);
                }else {
                    resSpecEvaluation = resChkSpec.resultCheck((Float) resultValue, minSpec, maxSpec, minStrict, maxStrict);
                }    
//                resSpecEvaluation = resChkSpec.resultCheck((Float) resultValue, (Float) minSpec, (Float) maxSpec, (Boolean) minStrict, (Boolean) maxStrict);
            } 

            if ("LABPLANET_FALSE".equalsIgnoreCase(resSpecEvaluation[0].toString())){                
                errorCode = "DataSample_SampleAnalysisResult_QuantitativeSpecNotRecognized";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultId.toString());
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, ruleVariables);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                  
            }
            String specEval = (String) resSpecEvaluation[0];      String specEvalDetail = (String) resSpecEvaluation[1];
            if (requiresUnitsConversion=true){specEvalDetail=specEvalDetail+ " in "+specUomName;}
            
            fieldsName = labArr.addValueToArray1D(fieldsName, "spec_eval");
            fieldsValue = labArr.addValueToArray1D(fieldsValue, specEval);
            fieldsName = labArr.addValueToArray1D(fieldsName, "spec_eval_detail");
            fieldsValue = labArr.addValueToArray1D(fieldsValue, specEvalDetail);
            fieldsName = labArr.addValueToArray1D(fieldsName, "entered_by");
            fieldsValue = labArr.addValueToArray1D(fieldsValue, userName);
            fieldsName = labArr.addValueToArray1D(fieldsName, "entered_on");
            fieldsValue = labArr.addValueToArray1D(fieldsValue, rdbm.getCurrentDate());
            fieldsName = labArr.addValueToArray1D(fieldsName, "status");
            fieldsValue = labArr.addValueToArray1D(fieldsValue, newResultStatus);
            diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, fieldsName, fieldsValue, new String[] {"result_id"} , new Object[] {resultId});
            if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
                smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
            }
            if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                Object[] diagnoses2 = sampleAnalysisEvaluateStatus(rdbm, schemaPrefix, userName, sampleId, testId, auditActionName, userRole);
            }
            String[] whereFields = new String[]{"user_id", "analysis", "method_name", "method_version"};
            Object[] whereFieldsValue = new Object[]{userName, analysis, methodName, methodVersion};
            String[] updFields = new String[]{"last_analysis_on", "last_sample", "last_sample_analysis"};
            Object[] updFieldsValue = new Object[]{rdbm.getLocalDate(), sampleId, testId};            
            Object[][] userMethodInfo;
            userMethodInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "user_method", 
                                                    whereFields,
                                                    whereFieldsValue,
                                                    new String[]{"user_method_id", "user_id", "analysis", "method_name", "method_version"});
            if (!("LABPLANET_FALSE".equalsIgnoreCase(userMethodInfo[0][0].toString())) ){ 
                diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "user_method", 
                                                    updFields, updFieldsValue, whereFields, whereFieldsValue);
                if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                    updFields = labArr.addValueToArray1D(updFields, whereFields);
                    updFieldsValue = labArr.addValueToArray1D(updFieldsValue, whereFieldsValue);
                    String[] fieldsForAudit = labArr.joinTwo1DArraysInOneOf1DString(updFields, updFieldsValue, ":");
                    auditActionName = auditActionName+":"+"UPDATE USER METHOD RECORD";
                    smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "user_method", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
                }    
            }    

            return diagnoses;   
            
        default:
                errorCode = "DataSample_SampleAnalysisResult_SpecRuleNotImplemented";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultId.toString());
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, ruleType);
                return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                  
    }
}

       
    public Object[] sampleResultReview(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole) throws SQLException{
        
        tableName = "sample_analysis_result";  
        
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, "data");  
        schemaConfigName = labPlat.buildSchemaName(schemaPrefix, "config"); 
            
        ResourceBundle prop = rdbm.getParameterBundle(schemaDataName.replace("\"", ""));
        String sampleAnalysisResultStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusCanceled");
        String sampleAnalysisResultStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusReviewed");
        String sampleAnalysisStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusCanceled");
        String sampleAnalysisStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusReviewed");
        String sampleStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusCanceled");
        String sampleStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusReviewed");
        
        Object[] samplesToReview = new Object[0];
        Object[] testsToReview = new Object[0];
        Object[] testsSampleToReview = new Object[0];
        
        String cancelScope = ""; Integer cancelScopeId = 0;
        if (sampleId!=null ){cancelScope = "sample_id"; cancelScopeId=sampleId;}
        if (testId!=null ){cancelScope = "test_id"; cancelScopeId=testId;}
        if (resultId!=null){cancelScope = "result_id"; cancelScopeId=resultId;}
        Object[][] objectInfo = null;
        objectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, tableName, 
                                                            new String[]{cancelScope},
                                                            new Object[]{cancelScopeId},
                                                            new String[]{"status","result_id","test_id", "sample_id"});
        if (objectInfo.length==0){
            errorCode = "DataSample_SampleAnalysisResultNotFound";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultId.toString());
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
            return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                  
        }else{
            for (Integer iResToCancel=0;iResToCancel<objectInfo.length;iResToCancel++){
                String currStatus = (String) objectInfo[iResToCancel][0];
                if (!(sampleAnalysisResultStatusCanceled.equalsIgnoreCase(currStatus))){    
                resultId = (Integer) objectInfo[iResToCancel][1];
                testId = (Integer) objectInfo[iResToCancel][2];
                sampleId = (Integer) objectInfo[iResToCancel][3];  
                if (!(sampleAnalysisResultStatusReviewed.equalsIgnoreCase(currStatus))){    
                    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis_result", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisResultStatusReviewed, currStatus}, 
                                                                        new String[]{"result_id", "status"}, new Object[]{resultId, "<>"+sampleAnalysisResultStatusCanceled});
                    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisResultStatusReviewed);
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, "REVIEW_RESULT", "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);        
                    }else{
                        return diagnoses;
                    }                        
                }else{
                    errorCode = "DataSample_SampleAnalysisResultNotReviable";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultId.toString());
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleAnalysisResultStatusReviewed);                    
                    return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                  
                     }    
                }
                if ((cancelScope.equalsIgnoreCase("sample_id")) && (!labArr.valueInArray(samplesToReview, sampleId)))
                        {samplesToReview = labArr.addValueToArray1D(samplesToReview, sampleId);}
                if ((cancelScope.equalsIgnoreCase("sample_id") || cancelScope.equalsIgnoreCase("test_id")) && (!labArr.valueInArray(testsToReview, testId)))
                    {testsToReview = labArr.addValueToArray1D(testsToReview, testId);
                     testsSampleToReview = labArr.addValueToArray1D(testsSampleToReview, sampleId);
                    }
            }    
        }    
        for (Integer iTstToCancel=0;iTstToCancel<testsToReview.length;iTstToCancel++){
                Integer currTest = (Integer) testsToReview[iTstToCancel];                
                objectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis", 
                                                                    new String[]{"test_id"},
                                                                    new Object[]{currTest},
                                                                    new String[]{"status","status_previous","test_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                if ( (!(sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleAnalysisStatusReviewed.equalsIgnoreCase(currStatus))) && (currTest!=null) ) {                    
                    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisStatusReviewed, currStatus}, 
                                                                        new String[]{"test_id"}, new Object[]{currTest});      
                    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisStatusCanceled);
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, "REVIEW_RESULT", "sample_analysis", currTest, sampleId, currTest, null, fieldsForAudit, userName, userRole);        
                    }                        
                }else{
                    errorCode = "DataSample_SampleAnalysisResultNotReviewable";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currTest.toString());
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currStatus);                    
                    return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                                  
                }
        }
        
        for (Integer iSmpToCancel=0;iSmpToCancel<samplesToReview.length;iSmpToCancel++){
                Integer currSample = (Integer) samplesToReview[iSmpToCancel];
                objectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample", 
                                                                    new String[]{"sample_id"},
                                                                    new Object[]{currSample},
                                                                    new String[]{"status","status_previous","sample_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                if ( (!(sampleStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleStatusReviewed.equalsIgnoreCase(currStatus))) && (currSample!=null) ){
                    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleStatusReviewed, currStatus}, 
                                                                        new String[]{"sample_id"}, new Object[]{currSample});                                                        
                    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+sampleStatusCanceled);
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, "REVIEW_RESULT", "sample", currSample, currSample, null, null, fieldsForAudit, userName, userRole);                            
                    }                        
                }else{
                    errorCode = "DataSample_SampleNotReviewable";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currSample.toString());
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currStatus);                    
                    diagnoses = (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                       
                }
        }
        return diagnoses;
    }
    
public void _sampleAnalysisResultReview(Rdbms rdbm, String schemaPrefix, String userName, Integer resultId){}

public void _sampleAnalysisReview(Rdbms rdbm, String schemaPrefix, String userName, Integer resultId){}

public void _sampleAnalysisResult(Rdbms rdbm, String schemaPrefix, String userName, Integer resultId){}
    
public void _groupSampleCreate(){}
/*
private Map getDefaultValuesTemplate(String schema, String tsample, String template) throws SQLException {
    
    String q = "SELECT column_name,\n" +
    "\n" +
    "(SELECT CASE\n" +
    "          WHEN isc.column_default IS NOT NULL THEN isc.column_default\n" +
    "          WHEN tsd.value IS NOT NULL THEN tsd.value\n" +
    "          ELSE null\n" +
    "       END) \n" +
    "\n" +
    "fielDefault\n" +
    "FROM information_schema.columns isc\n" +
    "left join template.sample ts on (isc.table_schema=ts.schema)\n" +
    "left join template.sample_default tsd on (isc.column_name = tsd.field)\n" +
    "WHERE isc.table_name = '"+tsample+"' and isc.table_schema='"+schema+"' and ts.sample_name = '"+template+"'\n" +
    "ORDER BY ordinal_position;";
    
    Map<String, String> myMap = new HashMap<>();
        
    try{
    CachedRowSetImpl res = rdbms.prepRdQuery(q, null);
    
        while (res.next()) {
            String col_name = res.getString("column_name");
            String col_value = res.getString("fielDefault");
            myMap.put(col_name, col_value);
        }
    
    }catch(SQLException ex){
        
    }
    
    return  myMap;
    
    }
*/    
    public String specialFieldCheckSampleStatus(Rdbms rdbm, String[] parameters, String schemaPrefix, String template, Integer templateVersion){ //, String schemaPrefix, String analysisList){                        

        String myDiagnoses = "";        
        String schemaConfigName = "config";
        
        LabPLANETPlatform labPlat = new LabPLANETPlatform();       
        schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName); 
        
        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("status");
        String status = mandatoryFieldsValue[specialFieldIndex].toString();     
        if (status.length()==0){myDiagnoses = "ERROR: The parameter status cannot be null"; return myDiagnoses;}
        
        Object[] diagnosis = rdbm.existsRecord(rdbm, schemaConfigName, "sample_rules", new String[] {"code", "code_version"}, new Object[] {template, templateVersion});
        if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnosis[0].toString())){
            myDiagnoses = "ERROR: The sample_rule record for "+template+" does not exist in schema"+schemaConfigName+". ERROR: "+diagnosis[5];}
        else{    
            String[] fieldNames = new String[1];
            Object[] fieldValues = new Object[1];

            fieldNames[0]="code";
            fieldValues[0]=template;
            
            String[] fieldFilter = new String[] {"code", "code_version", "statuses", "default_status"};
            
            Object[][] records = rdbm.getRecordFieldsByFilter(rdbm, schemaConfigName, "sample_rules", fieldNames, fieldValues, fieldFilter);
            if ("LABPLANET_FALSE".equalsIgnoreCase(records[0][0].toString())){
                myDiagnoses = "ERROR: Problem on getting sample rules for " + template + " exists but the rule record is missing in the schema "+schemaConfigName;            
                return myDiagnoses;
            }
            
            LabPLANETArray labArr = new LabPLANETArray();
            String statuses = records[0][2].toString();
            
            if (labArr.valueInArray(statuses.split("\\|", -1), status)){
                myDiagnoses = "SUCCESS";                            
            }else{
                myDiagnoses = "ERROR: The status " + status + " is not of one the defined status (" + statuses + " for the template " + template + " exists but the rule record is missing in the schema "+schemaConfigName;                                            
            }            
        }        
        return myDiagnoses;
    }
    
    public String specialFieldCheckSampleAnalysisMethod(Rdbms rdbm, String schemaPrefix){ //, String schemaPrefix, String analysisList){                        

        String myDiagnoses = "";       
                

        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("analysis");
        String analysis = (String) mandatoryFieldsValue[specialFieldIndex];     
        if (analysis.length()==0){myDiagnoses = "ERROR: The parameter analysis cannot be null"; return myDiagnoses;}

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("method_name");
        String methodName = (String) mandatoryFieldsValue[specialFieldIndex];     
        if (methodName.length()==0){myDiagnoses = "ERROR: The parameter method_name cannot be null"; return myDiagnoses;}

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("method_version");        
        Integer methodVersion = (Integer) mandatoryFieldsValue[specialFieldIndex];     
        if (methodVersion==null){myDiagnoses = "ERROR: The parameter method_version cannot be null"; return myDiagnoses;}
                        
        String[] fieldNames = new String[3];
        Object[] fieldValues = new Object[3];
                
        fieldNames[0]="analysis";
        fieldValues[0]=analysis;
        fieldNames[1]="method_name";
        fieldValues[1]=methodName;
        fieldNames[2]="method_version";        
        fieldValues[2]=methodVersion;                            
        
        Object[] diagnosis = rdbm.existsRecord(rdbm, schemaConfigName, "analysis_method", fieldNames, fieldValues);
        if ("LABPLANET_TRUE".equalsIgnoreCase(diagnosis[0].toString())){
            myDiagnoses = "SUCCESS";        }
        else{    
            diagnosis = rdbm.existsRecord(rdbm, schemaConfigName, "analysis", new String[]{"code"}, analysis);
            if ("LABPLANET_TRUE".equalsIgnoreCase(diagnosis[0].toString())){
                myDiagnoses = "ERROR: The analysis " + analysis + " exists but the method " + methodName +" with version "+ methodVersion+ " was not found in the schema "+schemaPrefix;            
            }
            else{
                myDiagnoses = "ERROR: The analysis " + analysis + " is not found in the schema "+schemaPrefix;            
            }
        }        
        return myDiagnoses;
    }

    public String specialFieldCheckSampleAnalysisAnalyst(Rdbms rdbm, String[] parameters, String schemaPrefix, String template, Integer templateVersion){ //, String schemaPrefix, String analysisList){                        

        String myDiagnoses = "";        
        String schemaConfigName = "config";
        
        LabPLANETPlatform labPlat = new LabPLANETPlatform();       
        schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName); 
        
        if ( 1==1){
            myDiagnoses = "ERROR: specialFieldCheckSampleAnalysisAnalyst not implemented yet.";
            return myDiagnoses;
        }
        
        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("status");
        String status = mandatoryFieldsValue[specialFieldIndex].toString();     
        if (status.length()==0){myDiagnoses = "ERROR: The parameter status cannot be null"; return myDiagnoses;}
        
        Object[] diagnosis = rdbm.existsRecord(rdbm, schemaConfigName, "sample_rules", new String[] {"code", "code_version"}, new Object[] {template, templateVersion});
        if ("LABPLANET_TRUE".equalsIgnoreCase(diagnosis[0].toString())){
            myDiagnoses = "ERROR: The sample_rule record for "+template+" does not exist in schema"+schemaConfigName+". ERROR: "+diagnosis[5];}
        else{    
            String[] fieldNames = new String[1];
            Object[] fieldValues = new Object[1];

            fieldNames[0]="code";
            fieldValues[0]=template;
            
            String[] fieldFilter = new String[] {"code", "code_version", "statuses", "default_status"};
            
            Object[][] records = rdbm.getRecordFieldsByFilter(rdbm, schemaConfigName, "sample_rules", fieldNames, fieldValues, fieldFilter);
            if ("LABPLANET_FALSE".equalsIgnoreCase(records[0][0].toString())){
                myDiagnoses = "ERROR: Problem on getting sample rules for " + template + " exists but the rule record is missing in the schema "+schemaConfigName;            
                return myDiagnoses;
            }
            
            LabPLANETArray labArr = new LabPLANETArray();
            String statuses = records[0][2].toString();
            
            if (labArr.valueInArray(statuses.split("\\|", -1), status)){
                myDiagnoses = "SUCCESS";                            
            }else{
                myDiagnoses = "ERROR: The status " + status + " is not of one the defined status (" + statuses + " for the template " + template + " exists but the rule record is missing in the schema "+schemaConfigName;                                            
            }
            
        }        
        return myDiagnoses;
    }

    public String specialFieldCheckSampleSpecCode(Rdbms rdbm, String[] parameters, String schemaPrefix, String template, Integer templateVersion){ //, String schemaPrefix, String analysisList){                        

        String myDiagnoses = "";        
        String schemaConfigName = "config";
        
        LabPLANETPlatform labPlat = new LabPLANETPlatform();       
        schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName); 

        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("spec_code");
        String specCode = (String) mandatoryFieldsValue[specialFieldIndex];     
        if (specCode.length()==0){myDiagnoses = "ERROR: The parameter spec_code cannot be null"; return myDiagnoses;}

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("spec_code_version");
        Integer specCodeVersion = (Integer) mandatoryFieldsValue[specialFieldIndex];     
        if (specCodeVersion==null){myDiagnoses = "ERROR: The parameter spec_code_version cannot be null"; return myDiagnoses;}

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("spec_variation_name");
        String specVariationName = (String) mandatoryFieldsValue[specialFieldIndex];     
        if (specVariationName.length()==0){myDiagnoses = "ERROR: The parameter spec_variation_name cannot be null"; return myDiagnoses;}
                
        Object[] diagnosis = rdbm.existsRecord(rdbm, schemaConfigName, "spec_limits", 
                new String[] {"code", "config_version", "variation_name"}, 
                new Object[] {specCode, specCodeVersion, specVariationName});
        
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnosis[0].toString())){
            myDiagnoses = "ERROR: The sample_rule record for "+template+" does not exist in schema"+schemaConfigName+". ERROR: "+diagnosis[5];
            return myDiagnoses;}
        
        myDiagnoses = "SUCCESS"; //: Spec "+specCode+" with version "+specCodeVersion.toString()+" and variation "+specVariationName+" does not exist in schema"+schemaConfigName+". ERROR: "+diagnosis[5];
        return myDiagnoses;}
    
    public Object[] sampleAnalysisResultCancel(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole) throws SQLException{
        
        tableName = "sample_analysis_result";  
        
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, "data");  
        schemaConfigName = labPlat.buildSchemaName(schemaPrefix, "config"); 
            
        ResourceBundle prop = rdbm.getParameterBundle(schemaDataName.replace("\"", ""));
        String sampleAnalysisResultStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusCanceled");
        String sampleAnalysisResultStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusReviewed");
        String sampleAnalysisStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusCanceled");
        String sampleAnalysisStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusReviewed");
        String sampleStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusCanceled");
        String sampleStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusReviewed");
        
        Object[] samplesToCancel = new Object[0];
        Object[] testsToCancel = new Object[0];
        Object[] testsSampleToCancel = new Object[0];
        
        String cancelScope = ""; Integer cancelScopeId = 0;
        if (sampleId!=null ){cancelScope = "sample_id"; cancelScopeId=sampleId;}
        if (testId!=null ){cancelScope = "test_id"; cancelScopeId=testId;}
        if (resultId!=null){cancelScope = "result_id"; cancelScopeId=resultId;}
        Object[][] objectInfo = null;
        objectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, tableName, 
                                                            new String[]{cancelScope},
                                                            new Object[]{cancelScopeId},
                                                            new String[]{"status","result_id","test_id", "sample_id"});
        if (objectInfo.length==0){
            String[] filter = new String[]{"sample_id:"+sampleId.toString()+" test_id:"+testId.toString()+" result_id:"+resultId.toString()};
            errorCode = "DataSample_SampleNotFound";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(filter));
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
            return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
        }else{
            for (Integer iResToCancel=0;iResToCancel<objectInfo.length;iResToCancel++){
                String currStatus = (String) objectInfo[iResToCancel][0];
                if (!(sampleAnalysisResultStatusCanceled.equalsIgnoreCase(currStatus))){    
                resultId = (Integer) objectInfo[iResToCancel][1];
                testId = (Integer) objectInfo[iResToCancel][2];
                sampleId = (Integer) objectInfo[iResToCancel][3];  
                if (!(sampleAnalysisResultStatusReviewed.equalsIgnoreCase(currStatus))){    
                    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis_result", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisResultStatusCanceled, currStatus}, 
                                                                        new String[]{"result_id"}, new Object[]{resultId});
                    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisResultStatusCanceled);
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, "CANCEL_RESULT", "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);        
                    }                        
                }else{
                    errorCode = "DataSample_SampleAnalysisResultCancelation_StatusNotExpected";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultId.toString());
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currStatus);
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                    return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
                }    
                }
                if ((cancelScope.equalsIgnoreCase("sample_id")) && (!labArr.valueInArray(samplesToCancel, sampleId)))
                        {samplesToCancel = labArr.addValueToArray1D(samplesToCancel, sampleId);}
                if ((cancelScope.equalsIgnoreCase("sample_id") || cancelScope.equalsIgnoreCase("test_id")) && (!labArr.valueInArray(testsToCancel, testId)))
                    {testsToCancel = labArr.addValueToArray1D(testsToCancel, testId);
                     testsSampleToCancel = labArr.addValueToArray1D(testsSampleToCancel, sampleId);
                    }
            }    
        }    
        for (Integer iTstToCancel=0;iTstToCancel<testsToCancel.length;iTstToCancel++){
                Integer currTest = (Integer) testsToCancel[iTstToCancel];                
                objectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis", 
                                                                    new String[]{"test_id"},
                                                                    new Object[]{currTest},
                                                                    new String[]{"status","status_previous","test_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                if ( (!(sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleAnalysisStatusReviewed.equalsIgnoreCase(currStatus))) && (currTest!=null) ) {                    
                    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisStatusCanceled, currStatus}, 
                                                                        new String[]{"test_id"}, new Object[]{currTest});      
                    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisStatusCanceled);
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, "CANCEL_RESULT", "sample_analysis", currTest, sampleId, currTest, null, fieldsForAudit, userName, userRole);        
                    }                        
                }else{
                    errorCode = "DataSample_SampleAnalysisCancelation_StatusNotExpected";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currTest.toString());
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currStatus);
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                    diagnoses = (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
                }
        }
        
        for (Integer iSmpToCancel=0;iSmpToCancel<samplesToCancel.length;iSmpToCancel++){
                Integer currSample = (Integer) samplesToCancel[iSmpToCancel];
                objectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample", 
                                                                    new String[]{"sample_id"},
                                                                    new Object[]{currSample},
                                                                    new String[]{"status","status_previous","sample_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                if ( (!(sampleStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleStatusReviewed.equalsIgnoreCase(currStatus))) && (currSample!=null) ){
                    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleStatusCanceled, currStatus}, 
                                                                        new String[]{"sample_id"}, new Object[]{currSample});                                                        
                    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+sampleStatusCanceled);
                    fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                    smpAudit.sampleAuditAdd(rdbm, schemaPrefix, "CANCEL_RESULT", "sample", currSample, currSample, null, null, fieldsForAudit, userName, userRole);        
                }                        
                }else{
                    errorCode = "DataSample_SampleAnalysisCancelation_StatusNotExpected";
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currSample.toString());
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, currStatus);
                    errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                    diagnoses = (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
                }
        }
        return diagnoses;
    }
    
    public Object[] sampleAnalysisResultUnCancel(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole) throws SQLException{
        
        tableName = "sample_analysis_result";  
       
        String auditActionName = "SAMPLE_ANALYSIS_RESULT_UNCANCELING";
        
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);  
        schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName); 
            

        ResourceBundle prop = rdbm.getParameterBundle(schemaDataName.replace("\"", ""));
        String sampleAnalysisResultStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusCanceled");
        String sampleAnalysisResultStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusReviewed");
        String sampleAnalysisStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusCanceled");
        String sampleAnalysisStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusReviewed");
        String sampleStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusCanceled");
        String sampleStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusReviewed");
        
        String cancelScope = ""; Integer cancelScopeId = 0;
        if (sampleId!=null ){cancelScope = "sample_id"; cancelScopeId=sampleId;}
        if (testId!=null ){cancelScope = "test_id"; cancelScopeId=testId;}
        if (resultId!=null){cancelScope = "result_id"; cancelScopeId=resultId;}
        Object[][] resultInfo = null;
        resultInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, tableName, 
                                                            new String[]{cancelScope},
                                                            new Object[]{cancelScopeId},
                                                            new String[]{"status","status_previous", "result_id","test_id", "sample_id"});
        if (resultInfo.length==0){
            String[] filter = new String[]{"sample_id:"+sampleId.toString()+" test_id:"+testId.toString()+" result_id:"+resultId.toString()};
            errorCode = "DataSample_SampleNotFound";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(filter));
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
            return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
        }
        Object[] samplesToUnCancel = new Object[0];
        Object[] testsToUnCancel = new Object[0];       
        String[] diagPerResult = new String[0];
        for (Integer iResToCancel=0;iResToCancel<resultInfo.length;iResToCancel++){
            String currResultStatus = (String) resultInfo[iResToCancel][0];
            String statusPrevious = (String) resultInfo[iResToCancel][1];
            resultId = (Integer) resultInfo[iResToCancel][2];
            testId = (Integer) resultInfo[iResToCancel][3];
            sampleId = (Integer) resultInfo[iResToCancel][4];
            if (!(sampleAnalysisResultStatusCanceled.equalsIgnoreCase(currResultStatus))){        
                String[] filter = new String[]{"sample_id:"+sampleId.toString()+" test_id:"+testId.toString()+" result_id:"+resultId.toString()};
                errorCode = "DataSample_SampleUnCancel_StatusNotExpected";
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, resultInfo[0][0].toString());
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sampleAnalysisResultStatusCanceled);
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
                diagnoses = (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);
                diagPerResult = labArr.addValueToArray1D(diagPerResult, "Result "+ resultId.toString() + " not uncanceled because current status is "+ currResultStatus);
            }else{    
            resultId = (Integer) resultInfo[iResToCancel][2];
            diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, tableName, 
                                                                new String[]{"status_previous", "status"}, 
                                                                new Object[]{sampleAnalysisResultStatusCanceled, statusPrevious}, 
                                                                new String[]{"result_id"}, new Object[]{resultId});
            if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                String[] fieldsForAudit = new String[0];
                fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+sampleAnalysisResultStatusCanceled);
                fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+statusPrevious);
                smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, this.getSampleGrouper()+"_"+"sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
            }
            diagPerResult = labArr.addValueToArray1D(diagPerResult, "Result "+ resultId.toString() + " UNCANCELED ");            
            }
            if ((cancelScope.equalsIgnoreCase("sample_id")) && (!labArr.valueInArray(samplesToUnCancel, sampleId)))
                    {samplesToUnCancel = labArr.addValueToArray1D(samplesToUnCancel, sampleId);}
            if ((cancelScope.equalsIgnoreCase("sample_id") || cancelScope.equalsIgnoreCase("test_id")) && (!labArr.valueInArray(testsToUnCancel, testId)))
                {testsToUnCancel = labArr.addValueToArray1D(testsToUnCancel, testId);
                }            
        }           
        for (Integer iTstToUnCancel=0;iTstToUnCancel<testsToUnCancel.length;iTstToUnCancel++){
                Integer currTest = (Integer) testsToUnCancel[iTstToUnCancel];                
                Object[][] objectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis", 
                                                                new String[]{"test_id"},
                                                                new Object[]{currTest},
                                                                new String[]{"status","status_previous","test_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                String currPrevStatus = (String) objectInfo[0][1];               
                if ( ((sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus))) && (currTest!=null) ) {                    
                    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{currPrevStatus, sampleAnalysisResultStatusCanceled}, 
                                                                        new String[]{"test_id"}, new Object[]{currTest});      
                    if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+sampleAnalysisResultStatusCanceled);
                        fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+currPrevStatus);
                        smpAudit.sampleAuditAdd(rdbm, schemaPrefix, "UNCANCEL_RESULT", this.getSampleGrouper()+"_"+"sample_analysis", currTest, sampleId, currTest, null, fieldsForAudit, userName, userRole);        
                    }                        
                }else{    
                    diagnoses[5]="The test "+currTest.toString()+" has status "+currStatus+" then cannot be canceled in schema "+schemaDataName;                    
                }
        }        
        for (Integer iSmpToUnCancel=0;iSmpToUnCancel<samplesToUnCancel.length;iSmpToUnCancel++){
                Integer currSample = (Integer) samplesToUnCancel[iSmpToUnCancel];
                Object[][] objectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample", 
                                                                    new String[]{"sample_id"},
                                                                    new Object[]{currSample},
                                                                    new String[]{"status","status_previous","sample_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];    
                String currPrevStatus = (String) objectInfo[0][1];  
                if ( ((sampleStatusCanceled.equalsIgnoreCase(currStatus))) && (currSample!=null) ){
                    diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{currPrevStatus, sampleAnalysisResultStatusCanceled}, 
                                                                        new String[]{"sample_id"}, new Object[]{currSample});                                                        
                if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+sampleAnalysisResultStatusCanceled);
                    fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+currPrevStatus);
                    smpAudit.sampleAuditAdd(rdbm, schemaPrefix, "UNCANCEL_RESULT", "sample", currSample, currSample, null, null, fieldsForAudit, userName, userRole);        
                }                        
                }else{    
                    diagnoses[5]="The sample "+currSample.toString()+" has status "+currStatus+" then cannot be canceled in schema "+schemaDataName;                    
                }
        }        
        diagnoses[5] = Arrays.toString(diagPerResult);
        return diagnoses;
    }    
        
    public String[] sampleAnalysisResultCancelBack(Rdbms rdbm, String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole) throws SQLException{
        
        tableName = "sample_analysis_result";  
        
        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, "data");  
        schemaConfigName = labPlat.buildSchemaName(schemaPrefix, "config"); 
        String[] diagnoses = new String[6];
            
        ResourceBundle prop = rdbm.getParameterBundle(schemaDataName.replace("\"", ""));
        String sampleAnalysisResultStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusCanceled");
        String sampleAnalysisResultStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusReviewed");
        String sampleAnalysisStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusCanceled");
        String sampleAnalysisStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusReviewed");
        String sampleStatusCanceled = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusCanceled");
        String sampleStatusReviewed = rdbm.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusReviewed");
        
        Object[] samplesToCancel = new Object[0];
        Object[] testsToCancel = new Object[0];
        Object[] testsSampleToCancel = new Object[0];
        
        String cancelScope = ""; Integer cancelScopeId = 0;
        if (sampleId!=null ){cancelScope = "sample_id"; cancelScopeId=sampleId;}
        if (testId!=null ){cancelScope = "test_id"; cancelScopeId=testId;}
        if (resultId!=null){cancelScope = "result_id"; cancelScopeId=resultId;}
        Object[][] objectInfo = null;
        objectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, tableName, 
                                                            new String[]{cancelScope},
                                                            new Object[]{cancelScopeId},
                                                            new String[]{"status","result_id","test_id", "sample_id","status_previous"});
        if (objectInfo.length==0){
            String[] filter = new String[]{"sample_id:"+sampleId.toString()+" test_id:"+testId.toString()+" result_id:"+resultId.toString()};
            errorCode = "DataSample_SampleAnalysisResultNotFound";
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, Arrays.toString(filter));
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaDataName);
            return (String[]) labPlat.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetailVariables);                    
        }else{
            for (Integer iResToCancel=0;iResToCancel<objectInfo.length;iResToCancel++){
                String currStatus = (String) objectInfo[iResToCancel][0];
                String currStatusPrevious = (String) objectInfo[iResToCancel][4];
                if (!(sampleAnalysisResultStatusCanceled.equalsIgnoreCase(currStatus))){    
                    resultId = (Integer) objectInfo[iResToCancel][1];
                    testId = (Integer) objectInfo[iResToCancel][2];
                    sampleId = (Integer) objectInfo[iResToCancel][3];  
                    if (!(sampleAnalysisResultStatusReviewed.equalsIgnoreCase(currStatus))){    
                        diagnoses = (String[]) rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis_result", 
                                                                            new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisResultStatusCanceled, currStatus}, 
                                                                            new String[]{"result_id"}, new Object[]{resultId});
                        if ("LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0])){
                            String[] fieldsForAudit = new String[0];
                            fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisResultStatusCanceled);
                            fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                            smpAudit.sampleAuditAdd(rdbm, schemaPrefix, "CANCEL_RESULT", this.getSampleGrouper()+"_"+"sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);        
                        }    

                    }else{
                    StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                    diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                    diagnoses[1]= classVersion;
                    diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());                    
                    diagnoses[3]="FALSE";    
                    diagnoses[4]="RESULT ALREADY CANCELED";
                    diagnoses[5]="The result "+resultId.toString()+" has status "+currStatus+" then cannot be canceled in schema "+schemaDataName;                    
                    }
                }    
                
                if ((cancelScope.equalsIgnoreCase("sample_id")) && (!labArr.valueInArray(samplesToCancel, sampleId)))
                        {samplesToCancel = labArr.addValueToArray1D(samplesToCancel, sampleId);}
                if ((cancelScope.equalsIgnoreCase("sample_id") || cancelScope.equalsIgnoreCase("test_id")) && (!labArr.valueInArray(testsToCancel, testId)))
                    {testsToCancel = labArr.addValueToArray1D(testsToCancel, testId);
                     testsSampleToCancel = labArr.addValueToArray1D(testsSampleToCancel, sampleId);
                    }
            }    
        }    
        for (Integer iTstToCancel=0;iTstToCancel<testsToCancel.length;iTstToCancel++){
                Integer currTest = (Integer) testsToCancel[iTstToCancel];    
                if (currTest!=null){
                    objectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis", 
                                                                        new String[]{"test_id"},
                                                                        new Object[]{currTest},
                                                                        new String[]{"status","status_previous","test_id", "sample_id"});
                    String currStatus = (String) objectInfo[0][0];               
                    if ( (!(sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleAnalysisStatusReviewed.equalsIgnoreCase(currStatus))) && (currTest!=null) ) {                    
                        diagnoses = (String[]) rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample_analysis", 
                                                                            new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisStatusCanceled, currStatus}, 
                                                                            new String[]{"test_id"}, new Object[]{currTest});      
                        if ("TRUE".equalsIgnoreCase(diagnoses[3])){
                            String[] fieldsForAudit = new String[0];
                            fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisStatusCanceled);
                            fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                            smpAudit.sampleAuditAdd(rdbm, schemaPrefix, "CANCEL_RESULT", this.getSampleGrouper()+"_"+"sample_analysis", currTest, sampleId, currTest, null, fieldsForAudit, userName, userRole);        
                        }                        
                    }else{    
                        diagnoses[5]="The test "+currTest.toString()+" has status "+currStatus+" then cannot be canceled in schema "+schemaDataName;                    
                    }    
                }
        }
        
        for (Integer iSmpToCancel=0;iSmpToCancel<samplesToCancel.length;iSmpToCancel++){
                Integer currSample = (Integer) samplesToCancel[iSmpToCancel];
                objectInfo = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample", 
                                                                    new String[]{"sample_id"},
                                                                    new Object[]{currSample},
                                                                    new String[]{"status","status_previous","sample_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                if ( (!(sampleStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleStatusReviewed.equalsIgnoreCase(currStatus))) && (currSample!=null) ){
                    diagnoses = (String[]) rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "sample", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleStatusCanceled, currStatus}, 
                                                                        new String[]{"sample_id"}, new Object[]{currSample});                                                        
                if ("TRUE".equalsIgnoreCase(diagnoses[3])){
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status:"+sampleStatusCanceled);
                    fieldsForAudit = labArr.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                    smpAudit.sampleAuditAdd(rdbm, schemaPrefix, "CANCEL_RESULT", this.getSampleGrouper()+"_"+"sample", currSample, currSample, null, null, fieldsForAudit, userName, userRole);        
                }                        
                }else{    
                    diagnoses[5]="The sample "+currSample.toString()+" has status "+currStatus+" then cannot be canceled in schema "+schemaDataName;                    
                }
        }
        return diagnoses;
    }


}
