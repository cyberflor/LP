/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sampleStructure;

//import com.sun.rowset.CachedRowSetImpl;
//import java.util.HashMap;
//import java.util.Map;
import LabPLANET.utilities.LPNulls;
import databases.Rdbms;
import functionalJava.analysis.UserMethod;
import functionalJava.audit.SampleAudit;
import functionalJava.materialSpec.DataSpec;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETDate;
import LabPLANET.utilities.LPPlatform;
import databases.DataDataIntegrity;
import functionalJava.ChangeOfCustody.ChangeOfCustody;
import functionalJava.parameter.Parameter;
import functionalJava.unitsOfMeasurement.UnitsOfMeasurement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class DataSampleBck {
    String classVersion = "0.1";
    String errorCode ="";
    Object[] errorDetailVariables= new Object[0];
    
    Rdbms rdbms;
    String lastError;
    Object[] diagnoses = new Object[7];

    String[] mandatoryFields = null;
    Object[] mandatoryFieldsValue = null;

    DataDataIntegrity labIntChecker = new DataDataIntegrity();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "sample"; 

    SampleAudit smpAudit = new SampleAudit();
    String grouperName = "";
    
    /**
     *
     * @param grouperName
     */
    public DataSampleBck(String grouperName){
        this.grouperName = grouperName;
    }
    
    /**
     *
     * @param grouperName
     */
    public void setSampleGrouper(String grouperName){this.grouperName=grouperName;}

    /**
     *
     * @return
     */
    public String getSampleGrouper(){return this.grouperName;}

    /**
     *
     */
    public void logSampleBySchedule(){
    // Not implemented yet
    }

    /**
     *
     * 
     * @param schemaPrefix
     * @param sampleTemplate
     * @param sampleTemplateVersion
     * @param sampleFieldName
     * @param sampleFieldValue
     * @param userName
     * @param userRole
     * @param appSessionId
     * @return
     */
    public Object[] logSampleDev( String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Integer appSessionId) {
        Object[] diag = logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, appSessionId, true);
    return diag;
}

    /**
     *
     * 
     * @param schemaPrefix
     * @param sampleTemplate
     * @param sampleTemplateVersion
     * @param sampleFieldName
     * @param sampleFieldValue
     * @param userName
     * @param userRole
     * @param appSessionId
     * @return
     */
    public Object[] logSample( String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Integer appSessionId) {
    Object[] diag = logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, appSessionId, false);
    return diag;
}

    /**
     *
     * @param schemaPrefix
     * @param sampleTemplate
     * @param sampleTemplateVersion
     * @param sampleFieldName
     * @param sampleFieldValue
     * @param userName
     * @param appSessionId
     * @param userRole
     * @param numSamplesToLog
     * @return
     */
    public Object[] logSample( String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Integer appSessionId, Integer numSamplesToLog) {
    Object[] diag = logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, appSessionId, false, numSamplesToLog);
    return diag;
}

Object[] logSample( String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Integer appSessionId, Boolean devMode) {
        
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "BEGIN";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }    
    
        String query = "";
        tableName = "sample";
        String actionName = "Insert";
        String auditActionName = "LOG_SAMPLE";
        
        String schemaDataName = "data";
        String schemaConfigName = "config";

        schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);    
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
        
        String sampleLevel = tableName;
//        if (this.getSampleGrouper()!=null){sampleLevel=this.getSampleGrouper()+"_"+sampleLevel;}

        mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, sampleLevel, actionName);
        
        String sampleStatusFirst = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), sampleLevel+"_statusFirst");     

        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "status");
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, sampleStatusFirst);
        // mandatoryFields = getSampleMandatoryFields();
        
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK sampleFieldName and sampleFieldValue match in length";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }    
    if (devMode==false){
        diagnoses = LabPLANETArray.checkTwoArraysSameLength(sampleFieldName, sampleFieldValue);
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
           errorCode = "DataSample_FieldArraysDifferentSize";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(sampleFieldName));
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(sampleFieldValue));
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }
    }    
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK sampleFieldName and sampleFieldValue match in length";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }    
    if (devMode==false){                
        if (LabPLANETArray.duplicates(sampleFieldName)){
           errorCode = "DataSample_FieldsDuplicated";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(sampleFieldName));
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                      
        }

        // spec is not mandatory but when any of the fields involved is added to the parameters 
        //  then it turns mandatory all the fields required for linking this entity.
        Integer fieldIndexSpecCode = Arrays.asList(sampleFieldName).indexOf("spec_code");
        Integer fieldIndexSpecCodeVersion = Arrays.asList(sampleFieldName).indexOf("spec_code_version");
        Integer fieldIndexSpecVariationName = Arrays.asList(sampleFieldName).indexOf("spec_variation_name");
        if ((fieldIndexSpecCode!=-1) || (fieldIndexSpecCodeVersion!=-1) || (fieldIndexSpecVariationName!=-1)){
            mandatoryFields = LabPLANETArray.addValueToArray1D(mandatoryFields, "spec_code");
            mandatoryFields = LabPLANETArray.addValueToArray1D(mandatoryFields, "spec_code_version");
            mandatoryFields = LabPLANETArray.addValueToArray1D(mandatoryFields, "spec_variation_name");
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
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);    
        }               
        
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, tableName, new String[]{"code","code_version"}, new Object[]{sampleTemplate, sampleTemplateVersion});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnosis[0].toString())){
           errorCode = "DataSample_MissingConfigCode";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleTemplate);
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleTemplateVersion);
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaConfigName);
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, diagnosis[5]);
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);    
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
                    Object specialFunctionReturn=null;      
                    try {
                        specialFunctionReturn = method.invoke(this, null, schemaPrefix, sampleTemplate, sampleTemplateVersion);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(DataSample.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (specialFunctionReturn.toString().contains("ERROR")){
                        errorCode = "DataSample_SpecialFunctionReturnedERROR";
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currField);
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aMethod);
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, specialFunctionReturn.toString());
                        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                            
                    } 
            }
        }
        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "sample_config_code");    
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, sampleTemplate);
        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "sample_config_code_version");    
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, sampleTemplateVersion); 
        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "logged_on");
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, Rdbms.getCurrentDate());    
        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "logged_by");
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, userName);           
        if (this.getSampleGrouper().length()>0){tableName=this.getSampleGrouper()+"_"+tableName;}               
        
        diagnoses = Rdbms.insertRecordInTable(schemaDataName, tableName, sampleFieldName, sampleFieldValue);
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
            errorCode = "DataSample_errorInsertingSampleRecord";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, diagnoses[5]);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }        

        Object[] fieldsOnLogSample = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, diagnoses[6]);

        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, tableName, Integer.parseInt(diagnoses[6].toString()), 
                Integer.parseInt(diagnoses[6].toString()), null, null, 
                fieldsOnLogSample, userName, userRole, appSessionId);

        return diagnoses;  
    }    
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "END";
        Integer specialFieldIndex = Arrays.asList(javaDocFields).indexOf("line_name");
        if (specialFieldIndex==-1){
            javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);         
        }else{    
            javaDocValues[specialFieldIndex] = javaDocLineName;             
        }
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }
    return diagnoses; 
}

Object[] logSample( String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Integer appSessionId, Boolean devMode, Integer numSamplesToLog) {
    
    
    
    LPPlatform labPlat = new LPPlatform();
    
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "BEGIN";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }    
    
        String query = "";
        tableName = "sample";
        String actionName = "Insert";
        String auditActionName = "LOG_SAMPLE";
        
        String schemaDataName = "data";
        String schemaConfigName = "config";

        schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);    
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
        
        String sampleLevel = tableName;
//        if (this.getSampleGrouper()!=null){sampleLevel=this.getSampleGrouper()+"_"+sampleLevel;}

        mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, sampleLevel, actionName);
        
        String sampleStatusFirst = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), sampleLevel+"_statusFirst");     

        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "status");
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, sampleStatusFirst);
        // mandatoryFields = getSampleMandatoryFields();
        
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK sampleFieldName and sampleFieldValue match in length";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }    
    if (devMode==false){
        diagnoses = LabPLANETArray.checkTwoArraysSameLength(sampleFieldName, sampleFieldValue);
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
           errorCode = "DataSample_FieldArraysDifferentSize";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(sampleFieldName));
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(sampleFieldValue));
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }
    }    
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK sampleFieldName and sampleFieldValue match in length";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }    
    if (devMode==false){        
        if (LabPLANETArray.duplicates(sampleFieldName)){
           errorCode = "DataSample_FieldsDuplicated";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(sampleFieldName));
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                      
        }

        // spec is not mandatory but when any of the fields involved is added to the parameters 
        //  then it turns mandatory all the fields required for linking this entity.
        Integer fieldIndexSpecCode = Arrays.asList(sampleFieldName).indexOf("spec_code");
        Integer fieldIndexSpecCodeVersion = Arrays.asList(sampleFieldName).indexOf("spec_code_version");
        Integer fieldIndexSpecVariationName = Arrays.asList(sampleFieldName).indexOf("spec_variation_name");
        if ((fieldIndexSpecCode!=-1) || (fieldIndexSpecCodeVersion!=-1) || (fieldIndexSpecVariationName!=-1)){
            mandatoryFields = LabPLANETArray.addValueToArray1D(mandatoryFields, "spec_code");
            mandatoryFields = LabPLANETArray.addValueToArray1D(mandatoryFields, "spec_code_version");
            mandatoryFields = LabPLANETArray.addValueToArray1D(mandatoryFields, "spec_variation_name");
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
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);    
        }               
        
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, tableName, new String[]{"code","code_version"}, new Object[]{sampleTemplate, sampleTemplateVersion});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnosis[0].toString())){
           errorCode = "DataSample_MissingConfigCode";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleTemplate);
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleTemplateVersion);
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaConfigName);
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, diagnosis[5]);
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);    
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
                    Object specialFunctionReturn=null;      
                    try {
                        specialFunctionReturn = method.invoke(this, null, schemaPrefix, sampleTemplate, sampleTemplateVersion);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(DataSample.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (specialFunctionReturn.toString().contains("ERROR")){
                        errorCode = "DataSample_SpecialFunctionReturnedERROR";
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currField);
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aMethod);
                        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, specialFunctionReturn.toString());
                        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                            
                    } 
            }
        }
        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "sample_config_code");    
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, sampleTemplate);
        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "sample_config_code_version");    
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, sampleTemplateVersion); 
        
        ChangeOfCustody coc = new ChangeOfCustody();
        Object[] changeOfCustodyEnable = coc.isChangeOfCustodyEnable(schemaDataName, tableName);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(changeOfCustodyEnable[0].toString())){
            sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "custodian");    
            sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, userName);             
        }
        
        
        if (numSamplesToLog==null){numSamplesToLog=1;}
        
        for (int iNumSamplesToLog=0; iNumSamplesToLog<numSamplesToLog; iNumSamplesToLog++ ){        
    
            tableName ="sample";
            if (this.getSampleGrouper().length()>0){tableName=this.getSampleGrouper()+"_"+tableName;}               

            diagnoses = Rdbms.insertRecordInTable(schemaDataName, tableName, sampleFieldName, sampleFieldValue);
            if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                errorCode = "DataSample_errorInsertingSampleRecord";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, diagnoses[diagnoses.length-2]);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
            }                                

            Object[] fieldsOnLogSample = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");
            diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, diagnoses[diagnoses.length-1]);

            Integer sampleId = Integer.parseInt(diagnoses[diagnoses.length-1].toString());
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, tableName, sampleId, 
                    sampleId, null, null, 
                    fieldsOnLogSample, userName, userRole, appSessionId);
            Integer transactionId = null;
            
            autoSampleAnalysisAdd(schemaPrefix, sampleId, userName, userRole, sampleFieldName, sampleFieldValue, "LOGGED", appSessionId, transactionId);
            
            autoSampleAliquoting(schemaPrefix, sampleId, userName, userRole, sampleFieldName, sampleFieldValue, "LOGGED", appSessionId, transactionId);
            
            
            
        }
        return diagnoses;  
    }    
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "END";
        Integer specialFieldIndex = Arrays.asList(javaDocFields).indexOf("line_name");
        if (specialFieldIndex==-1){
            javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);         
        }else{    
            javaDocValues[specialFieldIndex] = javaDocLineName;             
        }
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }
    return diagnoses; 
}

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param userRole
     * @param appSessionId
     * @return
     */
    public Object[] sampleReception( String schemaPrefix, String userName, Integer sampleId, String userRole, Integer appSessionId) {

    String receptionStatus = "RECEIVED";
    String auditActionName = "SAMPLE_RECEPTION";
    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName); 
    
    Object[][] currSampleStatus = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample", new String[]{"sample_id"}, 
                                                new Object[]{sampleId}, new String[]{"status", "received_by","received_on", "status"});
    if (LPPlatform.LAB_FALSE==currSampleStatus[0][0]){
        errorCode = "DataSample_SampleNotFound";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
    }
    if (currSampleStatus[0][1]!=null){ 
        errorCode = "DataSample_SampleAlreadyReceived";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currSampleStatus[0][2]);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
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
    
    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, 
                                            new String[] {"sample_id"}, new Object[]{sampleId});
    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
        String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, this.getSampleGrouper()+"_"+"sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole, appSessionId);
    }    
    return diagnoses;
}

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param newDate
     * @param userRole
     * @return
     * @throws SQLException
     */
    public Object[] changeSamplingDate( String schemaPrefix, String userName, Integer sampleId, Date newDate, String userRole) throws SQLException{

    
    LPPlatform labPlat = new LPPlatform();
    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName); 
    
    String auditActionName = "SAMPLE_CHANGE_SAMPLING_DATE";

    String[] sampleFieldName = new String[1];
    Object[] sampleFieldValue = new Object[1];
    Integer iField = 0;

    sampleFieldName[iField] = "sampling_date";    
    sampleFieldValue[iField] = newDate;
    iField++;
    
    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {"sample_id"}, new Object[]{sampleId});
    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
        errorCode = "DataSample_SamplingDateChangedSuccessfully";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));        
        diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);

        String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
    }    
    return diagnoses;
}

    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param comment
     * @param userRole
     * @return
     * @throws SQLException
     */
    public Object[] sampleReceptionCommentAdd( String schemaPrefix, String userName, Integer sampleId, String comment, String userRole) throws SQLException{

    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName); 
    
    String auditActionName = "SAMPLE_RECEPTION_COMMENT_ADD";

    String[] sampleFieldName = new String[1];
    Object[] sampleFieldValue = new Object[1];
    Integer iField = 0;

    sampleFieldName[iField] = "sampling_comment";    
    sampleFieldValue[iField] = comment;
    iField++;
    
    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {"sample_id"}, new Object[]{sampleId});
    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){        
        errorCode = "DataSample_SampleReceptionCommentAdd";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
        diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);                
        
        String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
    } 
    return diagnoses;
}

    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param comment
     * @param userRole
     * @return
     * @throws SQLException
     */
    public Object[] sampleReceptionCommentRemove( String schemaPrefix, String userName, Integer sampleId, String comment, String userRole) throws SQLException{
    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName); 

    String auditActionName = "SAMPLE_RECEPTION_COMMENT_REMOVE";
    
    String[] sampleFieldName = new String[1];
    Object[] sampleFieldValue = new Object[1];
    Integer iField = 0;

    sampleFieldName[iField] = "sampling_comment";    
    sampleFieldValue[iField] = "";
    iField++;
    
    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {"sample_id"}, new Object[]{sampleId});
    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
        errorCode = "DataSample_SampleReceptionCommentRemoved";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
        diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                

        String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
    } 
    return diagnoses;
}

    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param userRole
     * @return
     * @throws SQLException
     */
    public Object[] setSampleStartIncubationDateTime( String schemaPrefix, String userName, Integer sampleId, String userRole) throws SQLException{
        schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName); 

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

        diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {"sample_id"}, new Object[]{sampleId});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
            errorCode = "DataSample_SampleIncubationStartedSuccessfully";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
            diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);                

            String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
        } 
        return diagnoses;
    }

    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param userRole
     * @return
     * @throws SQLException
     */
    public Object[] setSampleEndIncubationDateTime( String schemaPrefix, String userName, Integer sampleId, String userRole) throws SQLException{
    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName); 
    
    String auditActionName = "SAMPLE_SET_INCUBATION_START";

    Object[][] incubationStart = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, 
            new String[]{"sample_id"}, new Object[]{sampleId}, new String[] {"incubation_start", "incubation_passed"});
    
    if (incubationStart[0][0]==null){        
        errorCode = "DataSample_SampleIncubationEnded_NotStartedYet";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
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

    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, sampleFieldName, sampleFieldValue, new String[] {"sample_id"}, new Object[]{sampleId});
    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
        errorCode = "DataSample_SampleIncubationEndedSuccessfully";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ", ")));
        diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);                

        String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, userName);
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);
    } 
    return diagnoses;
}

    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param testId
     * @param analyst
     * @param userRole
     */
    public void _sampleAssignAnalyst( String schemaPrefix, String userName, Integer testId, String analyst, String userRole){
    // Not implemented yet
    }

    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param testId
     * @param newAnalyst
     * @param userRole
     * @return
     * @throws SQLException
     */
    public Object[] sampleAnalysisAssignAnalyst( String schemaPrefix, String userName, Integer testId, String newAnalyst, String userRole) throws SQLException{

    tableName = "sample_analysis";
    String auditActionName = "SAMPLE_ANALYSIS_ANALYST_ASSIGNMENT";

    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);  
    schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 

    String testStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusReviewed");
    String testStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusCanceled");    
    
    Boolean assignTestAnalyst = false;
    
    String assignmentModes = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_analystAssigmentModes");
    
    Object[][] testData = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, new String[]{"test_id"}, new Object[]{testId}, new String[]{"sample_id", "status", "analyst", "analysis", "method_name", "method_version"});    
     if (LPPlatform.LAB_FALSE.equalsIgnoreCase(testData[0][0].toString())){
        errorCode = "DataSample_SampleAnalysisNotFound";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testId.toString());
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
    }   
     
    Integer sampleId = (Integer) testData[0][0];String testStatus = (String) testData[0][1];String testCurrAnalyst = (String) testData[0][2];
    String testAnalysis = (String) testData[0][3];String testMethodName = (String) testData[0][4];Integer testMethodVersion = (Integer) testData[0][5];
    
    if (testCurrAnalyst == null ? newAnalyst == null : testCurrAnalyst.equals(newAnalyst)){
        errorCode = "DataSample_SampleAnalysisAssignment_SameAnalyst";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testCurrAnalyst);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testId.toString());
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
    }
    
    // the test status cannot be reviewed or canceled, should be checked
    if (testCurrAnalyst != null){
        if (testStatus.equalsIgnoreCase(testStatusReviewed) || testStatus.equalsIgnoreCase(testStatusCanceled)){
            errorCode = "DataSample_SampleAnalysisAssignment_SameAnalyst";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testStatus);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, newAnalyst);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }    
    }       

    Object[][] sampleData = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample", new String[]{"sample_id"}, new Object[]{sampleId}, new String[]{"sample_config_code", "sample_config_code_version", "status", "status"});
    String sampleConfigCode = (String) sampleData[0][0]; Integer sampleConfigCodeVersion = (Integer) sampleData[0][1];
    String sampleStatus = (String) sampleData[0][2];
 
    Object[][] sampleRulesData = Rdbms.getRecordFieldsByFilter(schemaConfigName, "sample_rules", new String[]{"code", "code_version"}, new Object[]{sampleConfigCode, sampleConfigCodeVersion}, new String[]{"code", "code_version", "analyst_assignment_mode", "analyst_assignment_mode"});
    String testAssignmentMode = (String) sampleRulesData[0][2];
    if (testAssignmentMode==null){testAssignmentMode="null";}
    
    if (!assignmentModes.contains(testAssignmentMode)){
        errorCode = "DataSample_SampleAnalysisAssignment_SameAnalyst";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "analyst_assignment_mode");
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleConfigCode);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleConfigCodeVersion);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testAssignmentMode);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, assignmentModes);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testId.toString());
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, newAnalyst);              
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
    }
    if (testAssignmentMode.equalsIgnoreCase("DISABLE")){
        assignTestAnalyst = true;
    }else{            
        UserMethod ana = new UserMethod();
        String userMethodCertificationMode = ana.userMethodCertificationLevel(schemaPrefix, testAnalysis, testMethodName, testMethodVersion, newAnalyst);

        String userCertifiedModes = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_analystAssigmentMode"+testAssignmentMode);
        String[] userMethodModesArr = userCertifiedModes.split("\\|");                                    

        assignTestAnalyst = LabPLANETArray.valueInArray(userMethodModesArr, userMethodCertificationMode);        
        if (!assignTestAnalyst){                            
            errorCode = "DataSample_SampleAnalysisAssignment_AssignmentModeNotImplemented";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testAssignmentMode);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(userMethodModesArr));
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, userMethodCertificationMode);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }        
    }    

    if (assignTestAnalyst){
        String[] updateFieldName = new String[0];
        Object[] updateFieldValue = new Object[0];
        updateFieldName = LabPLANETArray.addValueToArray1D(updateFieldName, "analyst");
        updateFieldValue = LabPLANETArray.addValueToArray1D(updateFieldValue, newAnalyst);
        updateFieldName = LabPLANETArray.addValueToArray1D(updateFieldName, "analyst_assigned_on");
        updateFieldValue = LabPLANETArray.addValueToArray1D(updateFieldValue, LabPLANETDate.getTimeStampLocalDate());
        updateFieldName = LabPLANETArray.addValueToArray1D(updateFieldName, "analyst_assigned_by");
        updateFieldValue = LabPLANETArray.addValueToArray1D(updateFieldValue, userName);
        
        diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, updateFieldName, updateFieldValue, new String[]{"test_id"}, new Object[]{testId}); 
               
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){   
              errorCode = "DataSample_SampleAnalysisAssignment_Successfully";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, newAnalyst);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);          
            String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(updateFieldName, updateFieldValue, ":");            
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample_analysis", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
        }else{    
            errorCode = "DataSample_SampleAnalysisAssignment_databaseReturnedError";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, newAnalyst);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);               
        }   
    }

    errorCode = "DataSample_SampleAnalysisAssignment_EscapeByUnhandledException";        
    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, userName);
    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testId.toString());
    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, newAnalyst);
    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, userRole);
    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);           
}
        
    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param fieldName
     * @param fieldValue
     * @param userRole
     * @return
     */
    public Object[] sampleAnalysisAddtoSample( String schemaPrefix, String userName, Integer sampleId, String[] fieldName, Object[] fieldValue, String userRole) { // throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{

    tableName = "_analysis";
    String actionName = "Insert";
    String auditActionName = "ADD_SAMPLE_ANALYSIS";

    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);  
    schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
    
    String sampleLevel = "sample";
    if ((this.getSampleGrouper().length())>0){sampleLevel=this.getSampleGrouper()+"_"+sampleLevel;}
    String sampleTableName = "sample";    
    if (this.getSampleGrouper().length()>0){sampleTableName=this.getSampleGrouper()+"_"+sampleTableName;}
            
    mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, sampleLevel+tableName, actionName);

    if (fieldName.length!=fieldValue.length){
            errorCode = "DataSample_FieldArraysDifferentSize";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldName));
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldValue));
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
    }
    
    if (LabPLANETArray.duplicates(fieldName)){
           errorCode = "DataSample_FieldsDuplicated";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldName));
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);   
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
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(fieldName));
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaConfigName);
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);   
    }

// set first status. Begin    
    
    String firstStatus = "NOT_STARTED"; //Rdbms.getParameterBundleInConfigFile(schemaDataName, "sampleAnalysis_statusFirst");      
    Integer specialFieldIndex = Arrays.asList(fieldName).indexOf("status");
    if (specialFieldIndex==-1){
        fieldName = LabPLANETArray.addValueToArray1D(fieldName,"status");
        fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, firstStatus);
    }else{
        fieldValue[specialFieldIndex] = firstStatus; 
    }    
// set first status. End        
// Spec Business Rule. Allow other analyses. Begin
    Object[][] sampleData = Rdbms.getRecordFieldsByFilter(schemaDataName, sampleTableName, new String[]{"sample_id"}, new Object[]{sampleId}, 
        new String[]{"sample_id","sample_id","sample_id", "status"});
    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleData[0][0].toString())){
           errorCode = "DataSample_SampleNotFound";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);    
    }   
    Object[][] sampleSpecData = Rdbms.getRecordFieldsByFilter(schemaDataName, sampleTableName, new String[]{"sample_id"}, new Object[]{sampleId}, 
        new String[]{"sample_id", "spec_code","spec_code_version","spec_variation_name", "status"});
    
    if ( (sampleSpecData[0][0]==null) || (!LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleSpecData[0][0].toString())) ){                
        String sampleSpecCode = (String) sampleSpecData[0][1];Integer sampleSpecCodeVersion = (Integer) sampleSpecData[0][2];
        String sampleSpecVariationName = (String) sampleSpecData[0][3];
        if (sampleSpecCode!=null){
            Object[][] specRules = Rdbms.getRecordFieldsByFilter(schemaConfigName, "spec_rules", new String[]{"code", "config_version"}, new Object[]{sampleSpecCode, sampleSpecCodeVersion}, 
            new String[]{"allow_other_analysis","allow_multi_spec","code", "config_version"});
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(specRules[0][0].toString())){
                errorCode = "DataSample_SpecRuleNotFound";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleSpecCode);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleSpecCodeVersion.toString());
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                    
            }    
            if ((Boolean) specRules[0][0]==false){
                String[] specAnalysisFieldName = new String[]{"analysis", "method_name", "method_version"};
                Object[] specAnalysisFieldValue = new Object[0];
                for (String iFieldN: specAnalysisFieldName){
                    specialFieldIndex = Arrays.asList(fieldName).indexOf(iFieldN);
                    if (specialFieldIndex==-1){specAnalysisFieldValue = LabPLANETArray.addValueToArray1D(fieldValue,null);} 
                    else{specAnalysisFieldValue = LabPLANETArray.addValueToArray1D(specAnalysisFieldValue, fieldValue[specialFieldIndex]);}                    
                }
                specAnalysisFieldName = LabPLANETArray.addValueToArray1D(specAnalysisFieldName,"code");
                specAnalysisFieldValue = LabPLANETArray.addValueToArray1D(specAnalysisFieldValue,sampleSpecCode);
                specAnalysisFieldName = LabPLANETArray.addValueToArray1D(specAnalysisFieldName,"config_version");
                specAnalysisFieldValue = LabPLANETArray.addValueToArray1D(specAnalysisFieldValue,sampleSpecCodeVersion);
                specAnalysisFieldName = LabPLANETArray.addValueToArray1D(specAnalysisFieldName,"variation_name");
                specAnalysisFieldValue = LabPLANETArray.addValueToArray1D(specAnalysisFieldValue,sampleSpecVariationName);  
                Object[] analysisInSpec = Rdbms.existsRecord(schemaConfigName, "spec_limits", specAnalysisFieldName, specAnalysisFieldValue);
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(analysisInSpec[0].toString())){
                    errorCode = "DataSample_SpecLimitNotFound";
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LabPLANETArray.joinTwo1DArraysInOneOf1DString(specAnalysisFieldName, specAnalysisFieldValue, ":")));
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                          
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
                Object specialFunctionReturn = null;      
                try {
                    specialFunctionReturn = method.invoke(this, schemaPrefix);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(DataSample.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (specialFunctionReturn.toString().contains("ERROR")){
                    errorCode = "DataSample_SpecialFunctionReturnedERROR";
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currField);
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aMethod);
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, specialFunctionReturn.toString());
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                                                  
                }
        }
    }    
    specialFieldIndex = 0;
    Object value = null;
    Object[] whereResultFieldValue = new Object[0];
    String[] whereResultFieldName = new String[0];
    String fieldNeed = "analysis";
    whereResultFieldName = LabPLANETArray.addValueToArray1D(whereResultFieldName, fieldNeed);
    specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(fieldNeed);
    if (specialFieldIndex==-1){
        specialFieldIndex = Arrays.asList(fieldName).indexOf(fieldNeed);       
        if (specialFieldIndex==-1){ 
            errorCode = "DataSample_sampleAnalaysisAddToSample_MissingMandatoryFields";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, fieldNeed);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(mandatoryFields));
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return  LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                          
        }        
        value = fieldValue[specialFieldIndex];            
    }else{value = mandatoryFieldsValue[specialFieldIndex];}        
    whereResultFieldValue = LabPLANETArray.addValueToArray1D(whereResultFieldValue, value);
    
    fieldNeed = "method_name";
    whereResultFieldName = LabPLANETArray.addValueToArray1D(whereResultFieldName, fieldNeed);
    specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(fieldNeed);
    if (specialFieldIndex==-1){
        specialFieldIndex = Arrays.asList(fieldName).indexOf(fieldNeed);
        if (specialFieldIndex==-1){ 
            errorCode = "DataSample_sampleAnalaysisAddToSample_MissingMandatoryFields";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, fieldNeed);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, mandatoryFields);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                          
        }        
        value = fieldValue[specialFieldIndex];    
    }else{value = mandatoryFieldsValue[specialFieldIndex];}        
    whereResultFieldValue = LabPLANETArray.addValueToArray1D(whereResultFieldValue, value);

    fieldNeed = "method_version";
    whereResultFieldName = LabPLANETArray.addValueToArray1D(whereResultFieldName, fieldNeed);
    specialFieldIndex = Arrays.asList(mandatoryFields).indexOf(fieldNeed);
    if (specialFieldIndex==-1){
        specialFieldIndex = Arrays.asList(fieldName).indexOf(fieldNeed);
        if (specialFieldIndex==-1){ 
            errorCode = "DataSample_sampleAnalaysisAddToSample_MissingMandatoryFields";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, fieldNeed);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, mandatoryFields);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                          
        }        
        value = fieldValue[specialFieldIndex];    
    }else{value = mandatoryFieldsValue[specialFieldIndex];}        
    whereResultFieldValue = LabPLANETArray.addValueToArray1D(whereResultFieldValue, value);

    String[] getResultFields = new String[0];    
    getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "param_name");
    getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "mandatory");
    getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "analysis");
    getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "param_type");
    getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "num_replicas");
    getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "uom");
    getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "uom_conversion_mode");
    
    Object[][] resultFieldRecords = Rdbms.getRecordFieldsByFilter(schemaConfigName, "analysis_method_params", whereResultFieldName, whereResultFieldValue, getResultFields);
 
    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(resultFieldRecords[0][0].toString())){ 
        errorCode = "DataSample_AnalysisMethodParamsNotFound";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(LabPLANETArray.joinTwo1DArraysInOneOf1DString(whereResultFieldName, whereResultFieldValue, ":")));
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                          
    }
    resultFieldRecords = LabPLANETArray.addColumnToArray2D(resultFieldRecords, sampleId);
    getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "sample_id");
    resultFieldRecords = LabPLANETArray.addColumnToArray2D(resultFieldRecords, 0);
    getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "test_id");    

// This is temporary !!!! ***************************************************************    
    specialFieldIndex = Arrays.asList(getResultFields).indexOf("status"); 
    if (specialFieldIndex == -1){        
        firstStatus = "BLANK"; //Rdbms.getParameterBundleInConfigFile(schemaDataName, "sampleAnalysis_statusFirst");    
        resultFieldRecords = LabPLANETArray.addColumnToArray2D(resultFieldRecords, firstStatus);
        getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "status");        
    }    
// This is temporary !!!! ***************************************************************

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
                resultFieldRecords = LabPLANETArray.addColumnToArray2D(resultFieldRecords, currFieldValue);
                getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, currField);                
            }    
        }
        else{
            Integer valuePosic = Arrays.asList(fieldName).indexOf(currField);            
//            resultMandatoryFields[inumLines] = fieldValue[valuePosic]; 
        }
    }                
    
    if (resultMandatoryFieldsMissing.length()>0){
           errorCode = "DataSample_MissingMandatoryFields";
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, mandatoryFieldsMissing);
           errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
           return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);   
    }
    
        
    fieldName = LabPLANETArray.addValueToArray1D(fieldName, "sample_id");
    fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, sampleId);    
    fieldName = LabPLANETArray.addValueToArray1D(fieldName, "added_on");
    fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, Rdbms.getCurrentDate());    
    fieldName = LabPLANETArray.addValueToArray1D(fieldName, "added_by");
    fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, userName);   
    String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(fieldName, fieldValue, ":");
    diagnoses = Rdbms.insertRecordInTable(schemaDataName, sampleLevel+"_analysis", fieldName, fieldValue);
    
    Integer testId = Integer.parseInt(diagnoses[diagnoses.length-1].toString());
    
    smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, sampleLevel+"_analysis", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
    
    Integer valuePosic = Arrays.asList(getResultFields).indexOf("test_id");
    if (valuePosic==-1){
        resultFieldRecords = LabPLANETArray.addColumnToArray2D(resultFieldRecords, testId);
        getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "test_id");        
    }else{    
        resultFieldRecords = LabPLANETArray.setColumnValueToArray2D(resultFieldRecords, valuePosic, testId);
    }    
    valuePosic = Arrays.asList(getResultFields).indexOf("method_name");
    if (valuePosic==-1){        
        resultFieldRecords = LabPLANETArray.addColumnToArray2D(resultFieldRecords, fieldValue[Arrays.asList(fieldName).indexOf("method_name")]);
        getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "method_name");        
    }
    valuePosic = Arrays.asList(getResultFields).indexOf("method_version");
    if (valuePosic==-1){        
        resultFieldRecords = LabPLANETArray.addColumnToArray2D(resultFieldRecords, fieldValue[Arrays.asList(fieldName).indexOf("method_version")]);
        getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, "method_version");        
    }
    for (Object[] resultFieldRecord : resultFieldRecords) {
        Object[] fieldVal = new Object[0];
        for (int col = 0; col < resultFieldRecords[0].length; col++) {
            fieldVal = LabPLANETArray.addValueToArray1D(fieldVal, resultFieldRecord[col]);
        }
        valuePosic = Arrays.asList(getResultFields).indexOf("num_replicas");
        Integer numReplicas = 1;
        String resultReplicaFieldName = "replica";
        if (valuePosic==-1){
            valuePosic = Arrays.asList(getResultFields).indexOf("replica");
            if (valuePosic==-1){
                getResultFields = LabPLANETArray.addValueToArray1D(getResultFields, resultReplicaFieldName);
                fieldVal = LabPLANETArray.addValueToArray1D(fieldVal, numReplicas);
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
            diagnoses = Rdbms.insertRecordInTable(schemaDataName, sampleLevel+"_analysis_result", getResultFields, fieldVal);

            fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(getResultFields, fieldVal, ":");
            Integer resultId = Integer.parseInt(diagnoses[diagnoses.length-1].toString());
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, sampleLevel+"_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
        }
    } 
    
    //String[] diagnoses2 = sampleAnalysisEvaluateStatus(schemaPrefix, userName, sampleId, testId);
    Object[] diagnoses3 = sampleEvaluateStatus(schemaPrefix, userName, sampleId, auditActionName, userRole);
    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses3[0].toString())){
        return diagnoses3;
    }
    errorCode = "DataSample_SampleAnalysisAddedSuccessfully";
    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "");
    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testId.toString());
    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
    return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, errorCode, errorDetailVariables);       
}

    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param parentAuditAction
     * @param userRole
     * @return
     */
    public Object[] sampleEvaluateStatus( String schemaPrefix, String userName, Integer sampleId, String parentAuditAction, String userRole){ 
    
    String auditActionName = "SAMPLE_EVALUATE_STATUS";
    if (parentAuditAction!=null){auditActionName = parentAuditAction + ":"+auditActionName;}

    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);  
    schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
    tableName = "sample_analysis";      
            
    String sampleStatusIncomplete = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusIncomplete");
    String sampleStatusComplete = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusComplete");
    
    Object[] diagnoses = null;
    String smpPrevStatus=""; String smpAnaNewStatus="";
    
    diagnoses =  Rdbms.existsRecord(schemaDataName, "sample_analysis", 
                                        new String[]{"sample_id","status in|"}, 
                                        new Object[]{sampleId, "NOT_STARTED|STARTED|INCOMPLETE"});
    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){smpAnaNewStatus=sampleStatusIncomplete;}
    else{smpAnaNewStatus=sampleStatusComplete;}
    
    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample", 
            new String[]{"status"}, 
            new Object[]{smpAnaNewStatus},
            new String[]{"sample_id"},
            new Object[]{sampleId});
    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
        String[] fieldsForAudit = new String[0];
        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+smpAnaNewStatus);
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample", sampleId, sampleId, null, null, fieldsForAudit, userName, userRole);        
    }      
    return diagnoses;
}

    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param testId
     * @param parentAuditAction
     * @param userRole
     * @return
     */
    public Object[] sampleAnalysisEvaluateStatus( String schemaPrefix, String userName, Integer sampleId, Integer testId, String parentAuditAction, String userRole){
    
    schemaDataName = "data";
    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);  
    
    tableName = "sample_analysis_result";      
    
    String auditActionName = "SAMPLE_ANALYSIS_EVALUATE_STATUS";

    if (parentAuditAction!=null){auditActionName = parentAuditAction + ":"+auditActionName;}
    
    String sampleAnalysisStatusIncomplete = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusIncomplete");
    String sampleAnalysisStatusComplete = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusComplete");
   
    Object[] diagnoses = null;
    String smpAnaPrevStatus=""; String smpAnaNewStatus="";
    
    diagnoses = Rdbms.existsRecord(schemaDataName, tableName, 
                                        new String[]{"test_id","status","mandatory"}, 
                                        new Object[]{testId, "BLANK", true});
    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){smpAnaNewStatus=sampleAnalysisStatusIncomplete;}
    else{smpAnaNewStatus=sampleAnalysisStatusComplete;}
    
    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample_analysis", 
            new String[]{"status"}, 
            new Object[]{smpAnaNewStatus},
            new String[]{"test_id"},
            new Object[]{testId});
    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
        String[] fieldsForAudit = new String[0];
        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+smpAnaNewStatus);
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample_analysis", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);        
    }    
    Object[] diagnoses2 = sampleEvaluateStatus(schemaPrefix, userName, sampleId, parentAuditAction, userRole);
    return diagnoses;
}

    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param resultId
     * @param resultValue
     * @param userRole
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Object[] sampleAnalysisResultEntry( String schemaPrefix, String userName, Integer resultId, Object resultValue, String userRole) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{

    tableName = "sample_analysis_result";  
    String actionName = "Insert";
    String auditActionName = "SAMPLE_ANALYSIS_RESULT_ENTRY";
    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);  
    schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
    
    String specEvalNoSpec = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusSpecEvalNoSpec");
    String specEvalNoSpecParamLimit = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusSpecEvalNoSpecParamLimit");
    String resultStatusEntered = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusEntered");
    String resultStatusUpdated = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusUpdated");
    String resultStatusDefault = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusFirst");
    String resultStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusReviewed");
    String resultStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusCanceled");    

    String specArgumentsSeparator = "\\*";
    
    Boolean specMinSpecStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_minStrictSpecWhenNotSpecified"));
    Boolean specMinControlStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_minStrictControlWhenNotSpecified"));
    Boolean specMaxControlStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_maxStrictControlWhenNotSpecified"));
    Boolean specMaxSpecStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_maxStrictSpecWhenNotSpecified"));
               
    mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, this.getSampleGrouper()+"_"+"sample_analysis", actionName);

    String[] fieldsName = new String[0];
    Object[] fieldsValue = new Object[0];    
    fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "raw_value");
    fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, resultValue);

     Object[][] resultData = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample_analysis_result", 
        new String[]{"result_id"}, 
        new Object[]{resultId}, 
        new String[]{"sample_id","test_id", "analysis", "method_name", "method_version","param_name", "status", "raw_value", "uom", "uom_conversion_mode"});
    if (LPPlatform.LAB_FALSE.equals(resultData[0][0].toString())){
        errorCode = "DataSample_SampleAnalysisResultNotFound";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);    
    }
    Integer sampleId = (Integer)resultData[0][0];Integer testId = (Integer)resultData[0][1];
    String analysis = (String)resultData[0][2];String methodName = (String)resultData[0][3];Integer methodVersion = (Integer)resultData[0][4];
    String paramName = (String)resultData[0][5];String currResultStatus = (String)resultData[0][6];String currRawValue = (String)resultData[0][7];
    String resultUomName = (String)resultData[0][8];String resultUomConversionMode = (String)resultData[0][9];
    
    if (resultStatusReviewed.equalsIgnoreCase(currResultStatus) || resultStatusCanceled.equalsIgnoreCase(currResultStatus)){
        errorCode = "DataSample_SampleAnalysisResultLocked";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currResultStatus);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaConfigName);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);         
    }
    
    if ( (currRawValue!=null) && (currRawValue.equalsIgnoreCase(resultValue.toString())) ){
        errorCode = "DataSample_SampleAnalysisResultSampleValue";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currRawValue);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);         
    }
    
    Object[][] sampleData = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample", new String[]{"sample_id"}, new Object[]{sampleId}, 
        new String[]{"sample_id","sample_id","sample_id", "status", "sample_config_code", "sample_config_code_version"});
    if (LPPlatform.LAB_FALSE.equals(sampleData[0][0].toString())){
        errorCode = "DataSample_SampleNotFound";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);   
    }  
    String sampleConfigCode = (String) sampleData[0][4];Integer sampleConfigCodeVersion = (Integer) sampleData[0][5];
    
    Object[][] sampleSpecData = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample", new String[]{"sample_id"}, new Object[]{sampleId}, 
        new String[]{"spec_code","spec_code_version","spec_variation_name", "status", "sample_config_code", "sample_config_code_version"});
    String sampleSpecCode = null; Integer sampleSpecCodeVersion = null;
    String sampleSpecVariationName = null; String status = null; 
    if (sampleSpecData[0][0]!=null){
        if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleSpecData[0][0].toString())){
            sampleSpecCode = sampleSpecData[0][0].toString(); 
            sampleSpecCodeVersion = Integer.valueOf(sampleSpecData[0][1].toString());
            sampleSpecVariationName = sampleSpecData[0][2].toString(); status = sampleSpecData[0][3].toString();        
        }
    }
    Object[][] sampleRulesData = Rdbms.getRecordFieldsByFilter(schemaConfigName, "sample_rules", new String[]{"code", "code_version"}, new Object[]{sampleConfigCode, sampleConfigCodeVersion}, 
        new String[]{"test_analyst_required"});
    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleRulesData[0][0].toString())){    
        errorCode = "DataSample_SampleRulesNotFound";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "analyst_assignment_mode");
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleConfigCode);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleConfigCodeVersion);
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaConfigName);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);        
    }    
    Boolean analystRequired = (Boolean) sampleRulesData[0][0];
    if (analystRequired){
        Object[][] testData = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample_analysis", new String[]{"test_id"}, new Object[]{testId}, 
            new String[]{"test_id", "analyst", "analyst_assigned_on", "analyst_assigned_by"});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleRulesData[0][0].toString())){
            errorCode = "DataSample_SampleAnalysisNotFound";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);          
        }    
        String testAnalyst = (String) testData[0][1];String testAnalystBy = (String) testData[0][3];
        if (testAnalyst==null){
            errorCode = "DataSample_SampleAnalysisRuleAnalystNotAssigned";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleConfigCode);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleConfigCodeVersion.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                     
        }
        if (!testAnalyst.equalsIgnoreCase(userName)){
            errorCode = "DataSample_SampleAnalysisRuleOtherAnalystEnterResult";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, testAnalyst);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, userName);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                     
        }
    }
    String newResultStatus = currResultStatus;
    
    if (currResultStatus==null){newResultStatus = resultStatusDefault;}
    
    if (newResultStatus.equalsIgnoreCase(resultStatusDefault)){
        newResultStatus = "ENTERED";
    }else{   
        newResultStatus = "RE-ENTERED";
    }
    if (sampleSpecCode==null){
        fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "spec_eval");
        fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, specEvalNoSpec);
        fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "entered_by");
        fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, userName);
        fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "entered_on");
        fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, Rdbms.getCurrentDate());
        fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "status");
        fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, newResultStatus);
        diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, fieldsName, fieldsValue, new String[] {"result_id"} , new Object[] {resultId});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
            String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
        }
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
            Object[] diagnoses2 = sampleAnalysisEvaluateStatus(schemaPrefix, userName, sampleId,testId, auditActionName, userRole);
        }    
        String[] whereFields = new String[]{"user_id", "analysis", "method_name", "method_version"};
        Object[] whereFieldsValue = new Object[]{userName, analysis, methodName, methodVersion};
        String[] updFields = new String[]{"last_analysis_on", "last_sample", "last_sample_analysis"};
        Object[] updFieldsValue = new Object[]{Rdbms.getLocalDate(), sampleId, testId};            
        Object[][] userMethodInfo;
        userMethodInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "user_method", 
                                                whereFields,
                                                whereFieldsValue,
                                                new String[]{"user_method_id", "user_id", "analysis", "method_name", "method_version"});
        if (!(LPPlatform.LAB_FALSE.equalsIgnoreCase(userMethodInfo[0][0].toString())) ){ 
            diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "user_method", 
                                                updFields, updFieldsValue, whereFields, whereFieldsValue);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                updFields = LabPLANETArray.addValueToArray1D(updFields, whereFields);
                updFieldsValue = LabPLANETArray.addValueToArray1D(updFieldsValue, whereFieldsValue);
                String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(updFields, updFieldsValue, ":");
                auditActionName = "UPDATE LAST ANALYSIS USER METHOD";
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "user_method", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
            }    
        }         
        return diagnoses;
    }

    Object[][] specLimits = Rdbms.getRecordFieldsByFilter(schemaConfigName, "spec_limits", 
        new String[]{"code", "config_version", "variation_name", "analysis", "method_name","method_version","parameter"}, 
        new Object[]{sampleSpecCode, sampleSpecCodeVersion, sampleSpecVariationName, analysis, methodName, methodVersion, paramName}, 
        new String[]{"limit_id","rule_type","rule_variables", "limit_id", "uom", "uom_conversion_mode"});
//    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(specLimits[0][0].toString())){
    if ( (LPPlatform.LAB_FALSE.equalsIgnoreCase(specLimits[0][0].toString())) && (!"Rdbms_NoRecordsFound".equalsIgnoreCase(specLimits[0][4].toString())) ){
        return LabPLANETArray.array2dTo1d(specLimits);
    }
    if ( (LPPlatform.LAB_FALSE.equalsIgnoreCase(specLimits[0][0].toString())) && ("Rdbms_NoRecordsFound".equalsIgnoreCase(specLimits[0][4].toString())) ){
        fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "spec_eval");
        fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, specEvalNoSpecParamLimit);
        fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "entered_by");
        fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, userName);
        fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "entered_on");
        fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, Rdbms.getCurrentDate());
        fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "status");
        fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, newResultStatus);
        diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, fieldsName, fieldsValue, new String[] {"result_id"} , new Object[] {resultId});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){            String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
            smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
        }        
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){            Object[] diagnoses2 = sampleAnalysisEvaluateStatus(schemaPrefix, userName, sampleId,testId, auditActionName, userRole);
        }
        String[] whereFields = new String[]{"user_id", "analysis", "method_name", "method_version"};
        Object[] whereFieldsValue = new Object[]{userName, analysis, methodName, methodVersion};
        String[] updFields = new String[]{"last_analysis_on", "last_sample", "last_sample_analysis"};
        Object[] updFieldsValue = new Object[]{Rdbms.getLocalDate(), sampleId, testId};            
        Object[][] userMethodInfo;
        userMethodInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "user_method", 
                                                whereFields,
                                                whereFieldsValue,
                                                new String[]{"user_method_id", "user_id", "analysis", "method_name", "method_version"});
        if (!(LPPlatform.LAB_FALSE.equalsIgnoreCase(userMethodInfo[0][0].toString())) ){ 
            diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "user_method", 
                                                updFields, updFieldsValue, whereFields, whereFieldsValue);
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){                updFields = LabPLANETArray.addValueToArray1D(updFields, whereFields);
                updFieldsValue = LabPLANETArray.addValueToArray1D(updFieldsValue, whereFieldsValue);
                String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(updFields, updFieldsValue, ":");
                auditActionName = "UPDATE LAST ANALYSIS USER METHOD";
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "user_method", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
            }    
        }         
        return diagnoses;          
    }    
    Integer limitId = (Integer) specLimits[0][0];String ruleType = (String) specLimits[0][1];
    String ruleVariables = (String) specLimits[0][2]; 
    String specUomName = (String) specLimits[0][4]; String specUomConversionMode = (String) specLimits[0][5];

    Boolean requiresUnitsConversion = false;    
    BigDecimal resultConverted = null;
    if (resultUomName!=null){
        if (!resultUomName.equalsIgnoreCase(specUomName)){
            if (specUomConversionMode==null || specUomConversionMode.equalsIgnoreCase("DISABLED") || ((!specUomConversionMode.contains(resultUomName)) && !specUomConversionMode.equalsIgnoreCase("ALL")) ){
                errorCode = "DataSample_SampleAnalysisResult_ConversionNotAllowed";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, specUomConversionMode);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, specUomName);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultUomName);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, limitId.toString());
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                      
            }    
           
            requiresUnitsConversion=true;
            UnitsOfMeasurement UOM = new UnitsOfMeasurement();            
            Object[] convDiagnoses = UOM.convertValue(schemaPrefix, new BigDecimal(resultValue.toString()), resultUomName, specUomName);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(convDiagnoses[0].toString())) {
                errorCode = "DataSample_SampleAnalysisResult_ConverterFALSE";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, convDiagnoses[3].toString());
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                  
            }
            resultConverted = (BigDecimal) convDiagnoses[1];
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
            
            resSpecEvaluation = resChkSpec.resultCheck(schemaDataName, (String) resultValue, specRuleType, specValues, specSeparator, specListName);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(resSpecEvaluation[0].toString())){
                return resSpecEvaluation;
               //errorCode = "DataSample_SampleAnalysisResult_QualitativeSpecNotRecognized";
               // errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
               // errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, ruleVariables);
               // errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
               // return labPlat.trapErrorMessage(LPPlatform.LAB_FALSE, classVersion, errorCode, errorDetailVariables);                  
            }
            fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "spec_eval");
            fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, resSpecEvaluation[resSpecEvaluation.length-1]);
            fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "spec_eval_detail");
            fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, resSpecEvaluation[resSpecEvaluation.length-2]);
            fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "entered_by");
            fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, userName);
            fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "entered_on");
            fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, Rdbms.getCurrentDate());
            fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "status");
            fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, newResultStatus);
            diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, fieldsName, fieldsValue, new String[] {"result_id"} , new Object[] {resultId});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
            }  
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                Object[] diagnoses2 = sampleAnalysisEvaluateStatus(schemaPrefix, userName, sampleId,testId, auditActionName, userRole);
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
                    resSpecEvaluation = resChkSpec.resultCheck(schemaDataName, resultConverted.floatValue(), minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);
                }else {
                    resSpecEvaluation = resChkSpec.resultCheck(schemaDataName, (Float) resultValue, minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);                            
                }    
            }else{
                if (requiresUnitsConversion){
                    resSpecEvaluation = resChkSpec.resultCheck(schemaDataName, resultConverted.floatValue(), minSpec, maxSpec, minStrict, maxStrict);
                }else {
                    resSpecEvaluation = resChkSpec.resultCheck(schemaDataName, (Float) resultValue, minSpec, maxSpec, minStrict, maxStrict);
                }    
//                resSpecEvaluation = resChkSpec.resultCheck((Float) resultValue, (Float) minSpec, (Float) maxSpec, (Boolean) minStrict, (Boolean) maxStrict);
            } 

            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(resSpecEvaluation[0].toString())){        
                return resSpecEvaluation;
                //errorCode = "DataSample_SampleAnalysisResult_QuantitativeSpecNotRecognized";
                //errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
                //errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, ruleVariables);
                //errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                //return labPlat.trapErrorMessage(LPPlatform.LAB_FALSE, classVersion, errorCode, errorDetailVariables);                  
            }
            String specEval = (String) resSpecEvaluation[resSpecEvaluation.length-1];      String specEvalDetail = (String) resSpecEvaluation[resSpecEvaluation.length-2];
            if (requiresUnitsConversion==true){specEvalDetail=specEvalDetail+ " in "+specUomName;}
            
            fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "spec_eval");
            fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, specEval);
            fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "spec_eval_detail");
            fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, specEvalDetail);
            fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "entered_by");
            fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, userName);
            fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "entered_on");
            fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, Rdbms.getCurrentDate());
            fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, "status");
            fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, newResultStatus);
            diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, fieldsName, fieldsValue, new String[] {"result_id"} , new Object[] {resultId});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(fieldsName, fieldsValue, ":");
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
            }
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                Object[] diagnoses2 = sampleAnalysisEvaluateStatus(schemaPrefix, userName, sampleId, testId, auditActionName, userRole);
            }
            String[] whereFields = new String[]{"user_id", "analysis", "method_name", "method_version"};
            Object[] whereFieldsValue = new Object[]{userName, analysis, methodName, methodVersion};
            String[] updFields = new String[]{"last_analysis_on", "last_sample", "last_sample_analysis"};
            Object[] updFieldsValue = new Object[]{Rdbms.getLocalDate(), sampleId, testId};            
            Object[][] userMethodInfo;
            userMethodInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "user_method", 
                                                    whereFields,
                                                    whereFieldsValue,
                                                    new String[]{"user_method_id", "user_id", "analysis", "method_name", "method_version"});
            if (!(LPPlatform.LAB_FALSE.equalsIgnoreCase(userMethodInfo[0][0].toString())) ){ 
                diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "user_method", 
                                                    updFields, updFieldsValue, whereFields, whereFieldsValue);
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                    updFields = LabPLANETArray.addValueToArray1D(updFields, whereFields);
                    updFieldsValue = LabPLANETArray.addValueToArray1D(updFieldsValue, whereFieldsValue);
                    String[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(updFields, updFieldsValue, ":");
                    auditActionName = auditActionName+":"+"UPDATE USER METHOD RECORD";
                    smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, "user_method", testId, sampleId, testId, null, fieldsForAudit, userName, userRole);
                }    
            }    

            return diagnoses;   
            
        default:
                errorCode = "DataSample_SampleAnalysisResult_SpecRuleNotImplemented";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, ruleType);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                  
    }
}

    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param testId
     * @param resultId
     * @param userRole
     * @return
     * @throws SQLException
     */
    public Object[] sampleResultReview( String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole) throws SQLException{
        
        tableName = "sample_analysis_result";  

        schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, "data");  
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, "config"); 
            
        String sampleAnalysisResultStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusCanceled");
        String sampleAnalysisResultStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusReviewed");
        String sampleAnalysisStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusCanceled");
        String sampleAnalysisStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusReviewed");
        String sampleStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusCanceled");
        String sampleStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusReviewed");
        
        Object[] samplesToReview = new Object[0];
        Object[] testsToReview = new Object[0];
        Object[] testsSampleToReview = new Object[0];
        
        String cancelScope = ""; Integer cancelScopeId = 0;
        if (sampleId!=null ){cancelScope = "sample_id"; cancelScopeId=sampleId;}
        if (testId!=null ){cancelScope = "test_id"; cancelScopeId=testId;}
        if (resultId!=null){cancelScope = "result_id"; cancelScopeId=resultId;}
        Object[][] objectInfo = null;
        objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, 
                                                            new String[]{cancelScope},
                                                            new Object[]{cancelScopeId},
                                                            new String[]{"status","result_id","test_id", "sample_id"});
        if (objectInfo.length==0){
            errorCode = "DataSample_SampleAnalysisResultNotFound";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                  
        }else{
            for (Integer iResToCancel=0;iResToCancel<objectInfo.length;iResToCancel++){
                String currStatus = (String) objectInfo[iResToCancel][0];
                if (!(sampleAnalysisResultStatusCanceled.equalsIgnoreCase(currStatus))){    
                resultId = (Integer) objectInfo[iResToCancel][1];
                testId = (Integer) objectInfo[iResToCancel][2];
                sampleId = (Integer) objectInfo[iResToCancel][3];  
                if (!(sampleAnalysisResultStatusReviewed.equalsIgnoreCase(currStatus))){    
                    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample_analysis_result", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisResultStatusReviewed, currStatus}, 
                                                                        new String[]{"result_id", "status"}, new Object[]{resultId, "<>"+sampleAnalysisResultStatusCanceled});
                    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisResultStatusReviewed);
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                        smpAudit.sampleAuditAdd(schemaPrefix, "REVIEW_RESULT", "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);        
                    }else{
                        return diagnoses;
                    }                        
                }else{
                    errorCode = "DataSample_SampleAnalysisResultNotReviable";
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleAnalysisResultStatusReviewed);                    
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                  
                     }    
                }
                if ((cancelScope.equalsIgnoreCase("sample_id")) && (!LabPLANETArray.valueInArray(samplesToReview, sampleId)))
                        {samplesToReview = LabPLANETArray.addValueToArray1D(samplesToReview, sampleId);}
                if ((cancelScope.equalsIgnoreCase("sample_id") || cancelScope.equalsIgnoreCase("test_id")) && (!LabPLANETArray.valueInArray(testsToReview, testId)))
                    {testsToReview = LabPLANETArray.addValueToArray1D(testsToReview, testId);
                     testsSampleToReview = LabPLANETArray.addValueToArray1D(testsSampleToReview, sampleId);
                    }
            }    
        }    
        for (Integer iTstToCancel=0;iTstToCancel<testsToReview.length;iTstToCancel++){
                Integer currTest = (Integer) testsToReview[iTstToCancel];                
                objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample_analysis", 
                                                                    new String[]{"test_id"},
                                                                    new Object[]{currTest},
                                                                    new String[]{"status","status_previous","test_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                if ( (!(sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleAnalysisStatusReviewed.equalsIgnoreCase(currStatus))) && (currTest!=null) ) {                    
                    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample_analysis", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisStatusReviewed, currStatus}, 
                                                                        new String[]{"test_id"}, new Object[]{currTest});      
                    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisStatusCanceled);
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                        smpAudit.sampleAuditAdd(schemaPrefix, "REVIEW_RESULT", "sample_analysis", currTest, sampleId, currTest, null, fieldsForAudit, userName, userRole);        
                    }                        
                }else{
                    errorCode = "DataSample_SampleAnalysisResultNotReviewable";
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currTest.toString());
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currStatus);                    
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                                  
                }
        }
        
        for (Integer iSmpToCancel=0;iSmpToCancel<samplesToReview.length;iSmpToCancel++){
                Integer currSample = (Integer) samplesToReview[iSmpToCancel];
                objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample", 
                                                                    new String[]{"sample_id"},
                                                                    new Object[]{currSample},
                                                                    new String[]{"status","status_previous","sample_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                if ( (!(sampleStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleStatusReviewed.equalsIgnoreCase(currStatus))) && (currSample!=null) ){
                    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleStatusReviewed, currStatus}, 
                                                                        new String[]{"sample_id"}, new Object[]{currSample});                                                        
                    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+sampleStatusCanceled);
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                        smpAudit.sampleAuditAdd(schemaPrefix, "REVIEW_RESULT", "sample", currSample, currSample, null, null, fieldsForAudit, userName, userRole);                            
                    }                        
                }else{
                    errorCode = "DataSample_SampleNotReviewable";
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currSample.toString());
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currStatus);                    
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                       
                }
        }
        return diagnoses;
    }
    
    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param resultId
     */
    public void _sampleAnalysisResultReview( String schemaPrefix, String userName, Integer resultId){
    // Not implemented yet
    }

    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param resultId
     */
    public void _sampleAnalysisReview( String schemaPrefix, String userName, Integer resultId){
    // Not implemented yet
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param resultId
     */
    public void _sampleAnalysisResult( String schemaPrefix, String userName, Integer resultId){
    // Not implemented yet
    }
    
    /**
     *
     */
    public void _groupSampleCreate(){
    // Not implemented yet
    }
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

    /**
     *
     * @param parameters
     * @param schemaPrefix
     * @param template
     * @param templateVersion
     * @return
     */
    
    public String specialFieldCheckSampleStatus( String[] parameters, String schemaPrefix, String template, Integer templateVersion){ //, String schemaPrefix, String analysisList){                        

        String myDiagnoses = "";        
        String schemaConfigName = "config";
        
        LPPlatform labPlat = new LPPlatform();       
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
        
        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("status");
        String status = mandatoryFieldsValue[specialFieldIndex].toString();     
        if (status.length()==0){myDiagnoses = "ERROR: The parameter status cannot be null"; return myDiagnoses;}
        
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, "sample_rules", new String[] {"code", "code_version"}, new Object[] {template, templateVersion});
        if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnosis[0].toString())){
            myDiagnoses = "ERROR: The sample_rule record for "+template+" does not exist in schema"+schemaConfigName+". ERROR: "+diagnosis[5];}
        else{    
            String[] fieldNames = new String[1];
            Object[] fieldValues = new Object[1];

            fieldNames[0]="code";
            fieldValues[0]=template;
            
            String[] fieldFilter = new String[] {"code", "code_version", "statuses", "default_status"};
            
            Object[][] records = Rdbms.getRecordFieldsByFilter(schemaConfigName, "sample_rules", fieldNames, fieldValues, fieldFilter);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(records[0][0].toString())){
                myDiagnoses = "ERROR: Problem on getting sample rules for " + template + " exists but the rule record is missing in the schema "+schemaConfigName;            
                return myDiagnoses;
            }
            
            
            String statuses = records[0][2].toString();
            
            if (LabPLANETArray.valueInArray(statuses.split("\\|", -1), status)){
                myDiagnoses = "SUCCESS";                            
            }else{
                myDiagnoses = "ERROR: The status " + status + " is not of one the defined status (" + statuses + " for the template " + template + " exists but the rule record is missing in the schema "+schemaConfigName;                                            
            }            
        }        
        return myDiagnoses;
    }
    
    /**
     *
     * @param schemaPrefix
     * @return
     */
    public String specialFieldCheckSampleAnalysisMethod( String schemaPrefix){ //, String schemaPrefix, String analysisList){                        

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
        
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, "analysis_method", fieldNames, fieldValues);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnosis[0].toString())){
            myDiagnoses = "SUCCESS";        }
        else{    
            diagnosis = Rdbms.existsRecord(schemaConfigName, "analysis", new String[]{"code"},  new Object[]{analysis});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnosis[0].toString())){
                myDiagnoses = "ERROR: The analysis " + analysis + " exists but the method " + methodName +" with version "+ methodVersion+ " was not found in the schema "+schemaPrefix;            
            }
            else{
                myDiagnoses = "ERROR: The analysis " + analysis + " is not found in the schema "+schemaPrefix;            
            }
        }        
        return myDiagnoses;
    }

    /**
     *
     * 
     * @param parameters
     * @param schemaPrefix
     * @param template
     * @param templateVersion
     * @return
     */
    public String specialFieldCheckSampleAnalysisAnalyst( String[] parameters, String schemaPrefix, String template, Integer templateVersion){ //, String schemaPrefix, String analysisList){                        

        String myDiagnoses = "";        
        String schemaConfigName = "config";
        
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
        
        if ( 1==1){
            myDiagnoses = "ERROR: specialFieldCheckSampleAnalysisAnalyst not implemented yet.";
            return myDiagnoses;
        }
        
        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("status");
        String status = mandatoryFieldsValue[specialFieldIndex].toString();     
        if (status.length()==0){myDiagnoses = "ERROR: The parameter status cannot be null"; return myDiagnoses;}
        
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, "sample_rules", new String[] {"code", "code_version"}, new Object[] {template, templateVersion});
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnosis[0].toString())){
            myDiagnoses = "ERROR: The sample_rule record for "+template+" does not exist in schema"+schemaConfigName+". ERROR: "+diagnosis[5];}
        else{    
            String[] fieldNames = new String[1];
            Object[] fieldValues = new Object[1];

            fieldNames[0]="code";
            fieldValues[0]=template;
            
            String[] fieldFilter = new String[] {"code", "code_version", "statuses", "default_status"};
            
            Object[][] records = Rdbms.getRecordFieldsByFilter(schemaConfigName, "sample_rules", fieldNames, fieldValues, fieldFilter);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(records[0][0].toString())){
                myDiagnoses = "ERROR: Problem on getting sample rules for " + template + " exists but the rule record is missing in the schema "+schemaConfigName;            
                return myDiagnoses;
            }
            
            
            String statuses = records[0][2].toString();
            
            if (LabPLANETArray.valueInArray(statuses.split("\\|", -1), status)){
                myDiagnoses = "SUCCESS";                            
            }else{
                myDiagnoses = "ERROR: The status " + status + " is not of one the defined status (" + statuses + " for the template " + template + " exists but the rule record is missing in the schema "+schemaConfigName;                                            
            }
            
        }        
        return myDiagnoses;
    }

    /**
     *
     * @param parameters
     * @param schemaPrefix
     * @param template
     * @param templateVersion
     * @return
     */
    public String specialFieldCheckSampleSpecCode( String[] parameters, String schemaPrefix, String template, Integer templateVersion){ //, String schemaPrefix, String analysisList){                        

        String myDiagnoses = "";        
        String schemaConfigName = "config";
        
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 

        Integer specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("spec_code");
        String specCode = (String) mandatoryFieldsValue[specialFieldIndex];     
        if (specCode.length()==0){myDiagnoses = "ERROR: The parameter spec_code cannot be null"; return myDiagnoses;}

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("spec_code_version");
        Integer specCodeVersion = (Integer) mandatoryFieldsValue[specialFieldIndex];     
        if (specCodeVersion==null){myDiagnoses = "ERROR: The parameter spec_code_version cannot be null"; return myDiagnoses;}

        specialFieldIndex = Arrays.asList(mandatoryFields).indexOf("spec_variation_name");
        String specVariationName = (String) mandatoryFieldsValue[specialFieldIndex];     
        if (specVariationName.length()==0){myDiagnoses = "ERROR: The parameter spec_variation_name cannot be null"; return myDiagnoses;}
                
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, "spec_limits", 
                new String[] {"code", "config_version", "variation_name"}, 
                new Object[] {specCode, specCodeVersion, specVariationName});
        
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnosis[0].toString())){
            myDiagnoses = "ERROR: The sample_rule record for "+template+" does not exist in schema"+schemaConfigName+". ERROR: "+diagnosis[5];
            return myDiagnoses;}
        
        myDiagnoses = "SUCCESS"; //: Spec "+specCode+" with version "+specCodeVersion.toString()+" and variation "+specVariationName+" does not exist in schema"+schemaConfigName+". ERROR: "+diagnosis[5];
        return myDiagnoses;}
    
    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param testId
     * @param resultId
     * @param userRole
     * @return
     * @throws SQLException
     */
    public Object[] sampleAnalysisResultCancel( String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole) throws SQLException{
        
        tableName = "sample_analysis_result";  
        schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, "data");  
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, "config"); 
            
        String sampleAnalysisResultStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusCanceled");
        String sampleAnalysisResultStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusReviewed");
        String sampleAnalysisStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusCanceled");
        String sampleAnalysisStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusReviewed");
        String sampleStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusCanceled");
        String sampleStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusReviewed");
        
        Object[] samplesToCancel = new Object[0];
        Object[] testsToCancel = new Object[0];
        Object[] testsSampleToCancel = new Object[0];
        
        String cancelScope = ""; Integer cancelScopeId = 0;
        if (sampleId!=null ){cancelScope = "sample_id"; cancelScopeId=sampleId;}
        if (testId!=null ){cancelScope = "test_id"; cancelScopeId=testId;}
        if (resultId!=null){cancelScope = "result_id"; cancelScopeId=resultId;}
        Object[][] objectInfo = null;
        objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, 
                                                            new String[]{cancelScope},
                                                            new Object[]{cancelScopeId},
                                                            new String[]{"status","result_id","test_id", "sample_id"});
        if (objectInfo.length==0){
            String[] filter = new String[]{"sample_id:"+sampleId.toString()+" test_id:"+testId.toString()+" result_id:"+resultId.toString()};
            errorCode = "DataSample_SampleNotFound";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(filter));
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
        }else{
            for (Integer iResToCancel=0;iResToCancel<objectInfo.length;iResToCancel++){
                String currStatus = (String) objectInfo[iResToCancel][0];
                if (!(sampleAnalysisResultStatusCanceled.equalsIgnoreCase(currStatus))){    
                resultId = (Integer) objectInfo[iResToCancel][1];
                testId = (Integer) objectInfo[iResToCancel][2];
                sampleId = (Integer) objectInfo[iResToCancel][3];  
                if (!(sampleAnalysisResultStatusReviewed.equalsIgnoreCase(currStatus))){    
                    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample_analysis_result", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisResultStatusCanceled, currStatus}, 
                                                                        new String[]{"result_id"}, new Object[]{resultId});
                    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisResultStatusCanceled);
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                        smpAudit.sampleAuditAdd(schemaPrefix, "CANCEL_RESULT", "sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);        
                    }                        
                }else{
                    errorCode = "DataSample_SampleAnalysisResultCancelation_StatusNotExpected";
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currStatus);
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                    return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
                }    
                }
                if ((cancelScope.equalsIgnoreCase("sample_id")) && (!LabPLANETArray.valueInArray(samplesToCancel, sampleId)))
                        {samplesToCancel = LabPLANETArray.addValueToArray1D(samplesToCancel, sampleId);}
                if ((cancelScope.equalsIgnoreCase("sample_id") || cancelScope.equalsIgnoreCase("test_id")) && (!LabPLANETArray.valueInArray(testsToCancel, testId)))
                    {testsToCancel = LabPLANETArray.addValueToArray1D(testsToCancel, testId);
                     testsSampleToCancel = LabPLANETArray.addValueToArray1D(testsSampleToCancel, sampleId);
                    }
            }    
        }    
        for (Integer iTstToCancel=0;iTstToCancel<testsToCancel.length;iTstToCancel++){
                Integer currTest = (Integer) testsToCancel[iTstToCancel];                
                objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample_analysis", 
                                                                    new String[]{"test_id"},
                                                                    new Object[]{currTest},
                                                                    new String[]{"status","status_previous","test_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                if ( (!(sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleAnalysisStatusReviewed.equalsIgnoreCase(currStatus))) && (currTest!=null) ) {                    
                    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample_analysis", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisStatusCanceled, currStatus}, 
                                                                        new String[]{"test_id"}, new Object[]{currTest});      
                    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisStatusCanceled);
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                        smpAudit.sampleAuditAdd(schemaPrefix, "CANCEL_RESULT", "sample_analysis", currTest, sampleId, currTest, null, fieldsForAudit, userName, userRole);        
                    }                        
                }else{
                    errorCode = "DataSample_SampleAnalysisCancelation_StatusNotExpected";
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currTest.toString());
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currStatus);
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
                }
        }
        
        for (Integer iSmpToCancel=0;iSmpToCancel<samplesToCancel.length;iSmpToCancel++){
                Integer currSample = (Integer) samplesToCancel[iSmpToCancel];
                objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample", 
                                                                    new String[]{"sample_id"},
                                                                    new Object[]{currSample},
                                                                    new String[]{"status","status_previous","sample_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                if ( (!(sampleStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleStatusReviewed.equalsIgnoreCase(currStatus))) && (currSample!=null) ){
                    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleStatusCanceled, currStatus}, 
                                                                        new String[]{"sample_id"}, new Object[]{currSample});                                                        
                    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+sampleStatusCanceled);
                    fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                    smpAudit.sampleAuditAdd(schemaPrefix, "CANCEL_RESULT", "sample", currSample, currSample, null, null, fieldsForAudit, userName, userRole);        
                }                        
                }else{
                    errorCode = "DataSample_SampleAnalysisCancelation_StatusNotExpected";
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currSample.toString());
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currStatus);
                    errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                    diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
                }
        }
        return diagnoses;
    }
    
    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param testId
     * @param resultId
     * @param userRole
     * @return
     * @throws SQLException
     */
    public Object[] sampleAnalysisResultUnCancel( String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole) throws SQLException{
        
        tableName = "sample_analysis_result";  
       
        String auditActionName = "SAMPLE_ANALYSIS_RESULT_UNCANCELING";
        schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);  
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 

        String sampleAnalysisResultStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusCanceled");
        String sampleAnalysisResultStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusReviewed");
        String sampleAnalysisStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusCanceled");
        String sampleAnalysisStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusReviewed");
        String sampleStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusCanceled");
        String sampleStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusReviewed");
        
        String cancelScope = ""; Integer cancelScopeId = 0;
        if (sampleId!=null ){cancelScope = "sample_id"; cancelScopeId=sampleId;}
        if (testId!=null ){cancelScope = "test_id"; cancelScopeId=testId;}
        if (resultId!=null){cancelScope = "result_id"; cancelScopeId=resultId;}
        Object[][] resultInfo = null;
        resultInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, 
                                                            new String[]{cancelScope},
                                                            new Object[]{cancelScopeId},
                                                            new String[]{"status","status_previous", "result_id","test_id", "sample_id"});
        if (resultInfo.length==0){
            String[] filter = new String[]{"sample_id:"+sampleId.toString()+" test_id:"+testId.toString()+" result_id:"+resultId.toString()};
            errorCode = "DataSample_SampleNotFound";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(filter));
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
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
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultInfo[0][0].toString());
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleAnalysisResultStatusCanceled);
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
                diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
                diagPerResult = LabPLANETArray.addValueToArray1D(diagPerResult, "Result "+ resultId.toString() + " not uncanceled because current status is "+ currResultStatus);
            }else{    
            resultId = (Integer) resultInfo[iResToCancel][2];
            diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, 
                                                                new String[]{"status_previous", "status"}, 
                                                                new Object[]{sampleAnalysisResultStatusCanceled, statusPrevious}, 
                                                                new String[]{"result_id"}, new Object[]{resultId});
            if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                String[] fieldsForAudit = new String[0];
                fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+sampleAnalysisResultStatusCanceled);
                fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+statusPrevious);
                smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, this.getSampleGrouper()+"_"+"sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
            }
            diagPerResult = LabPLANETArray.addValueToArray1D(diagPerResult, "Result "+ resultId.toString() + " UNCANCELED ");            
            }
            if ((cancelScope.equalsIgnoreCase("sample_id")) && (!LabPLANETArray.valueInArray(samplesToUnCancel, sampleId)))
                    {samplesToUnCancel = LabPLANETArray.addValueToArray1D(samplesToUnCancel, sampleId);}
            if ((cancelScope.equalsIgnoreCase("sample_id") || cancelScope.equalsIgnoreCase("test_id")) && (!LabPLANETArray.valueInArray(testsToUnCancel, testId)))
                {testsToUnCancel = LabPLANETArray.addValueToArray1D(testsToUnCancel, testId);
                }            
        }           
        for (Integer iTstToUnCancel=0;iTstToUnCancel<testsToUnCancel.length;iTstToUnCancel++){
                Integer currTest = (Integer) testsToUnCancel[iTstToUnCancel];                
                Object[][] objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample_analysis", 
                                                                new String[]{"test_id"},
                                                                new Object[]{currTest},
                                                                new String[]{"status","status_previous","test_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                String currPrevStatus = (String) objectInfo[0][1];               
                if ( ((sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus))) && (currTest!=null) ) {                    
                    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample_analysis", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{currPrevStatus, sampleAnalysisResultStatusCanceled}, 
                                                                        new String[]{"test_id"}, new Object[]{currTest});      
                    if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                        String[] fieldsForAudit = new String[0];
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+sampleAnalysisResultStatusCanceled);
                        fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+currPrevStatus);
                        smpAudit.sampleAuditAdd(schemaPrefix, "UNCANCEL_RESULT", this.getSampleGrouper()+"_"+"sample_analysis", currTest, sampleId, currTest, null, fieldsForAudit, userName, userRole);        
                    }                        
                }else{    
                    diagnoses[5]="The test "+currTest.toString()+" has status "+currStatus+" then cannot be canceled in schema "+schemaDataName;                    
                }
        }        
        for (Integer iSmpToUnCancel=0;iSmpToUnCancel<samplesToUnCancel.length;iSmpToUnCancel++){
                Integer currSample = (Integer) samplesToUnCancel[iSmpToUnCancel];
                Object[][] objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample", 
                                                                    new String[]{"sample_id"},
                                                                    new Object[]{currSample},
                                                                    new String[]{"status","status_previous","sample_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];    
                String currPrevStatus = (String) objectInfo[0][1];  
                if ( ((sampleStatusCanceled.equalsIgnoreCase(currStatus))) && (currSample!=null) ){
                    diagnoses = Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{currPrevStatus, sampleAnalysisResultStatusCanceled}, 
                                                                        new String[]{"sample_id"}, new Object[]{currSample});                                                        
                if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+sampleAnalysisResultStatusCanceled);
                    fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+currPrevStatus);
                    smpAudit.sampleAuditAdd(schemaPrefix, "UNCANCEL_RESULT", "sample", currSample, currSample, null, null, fieldsForAudit, userName, userRole);        
                }                        
                }else{    
                    diagnoses[5]="The sample "+currSample.toString()+" has status "+currStatus+" then cannot be canceled in schema "+schemaDataName;                    
                }
        }        
        diagnoses[5] = Arrays.toString(diagPerResult);
        return diagnoses;
    }    
        
    /**
     *
     * 
     * @param schemaPrefix
     * @param userName
     * @param sampleId
     * @param testId
     * @param resultId
     * @param userRole
     * @return
     * @throws SQLException
     */
    public Object[] sampleAnalysisResultCancelBack( String schemaPrefix, String userName, Integer sampleId, Integer testId, Integer resultId, String userRole) throws SQLException{
        
        tableName = "sample_analysis_result";  
        schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, "data");  
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, "config"); 
        String[] diagnoses = new String[6];
            
        String sampleAnalysisResultStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusCanceled");
        String sampleAnalysisResultStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysisResult_statusReviewed");
        String sampleAnalysisStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusCanceled");
        String sampleAnalysisStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sampleAnalysis_statusReviewed");
        String sampleStatusCanceled = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusCanceled");
        String sampleStatusReviewed = Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "sample_statusReviewed");
        
        Object[] samplesToCancel = new Object[0];
        Object[] testsToCancel = new Object[0];
        Object[] testsSampleToCancel = new Object[0];
        
        String cancelScope = ""; Integer cancelScopeId = 0;
        if (sampleId!=null ){cancelScope = "sample_id"; cancelScopeId=sampleId;}
        if (testId!=null ){cancelScope = "test_id"; cancelScopeId=testId;}
        if (resultId!=null){cancelScope = "result_id"; cancelScopeId=resultId;}
        Object[][] objectInfo = null;
        objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, 
                                                            new String[]{cancelScope},
                                                            new Object[]{cancelScopeId},
                                                            new String[]{"status","result_id","test_id", "sample_id","status_previous"});
        if (objectInfo.length==0){
            String[] filter = new String[]{"sample_id:"+sampleId.toString()+" test_id:"+testId.toString()+" result_id:"+resultId.toString()};
            errorCode = "DataSample_SampleAnalysisResultNotFound";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(filter));
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                    
        }else{
            for (Integer iResToCancel=0;iResToCancel<objectInfo.length;iResToCancel++){
                String currStatus = (String) objectInfo[iResToCancel][0];
                String currStatusPrevious = (String) objectInfo[iResToCancel][4];
                if (!(sampleAnalysisResultStatusCanceled.equalsIgnoreCase(currStatus))){    
                    resultId = (Integer) objectInfo[iResToCancel][1];
                    testId = (Integer) objectInfo[iResToCancel][2];
                    sampleId = (Integer) objectInfo[iResToCancel][3];  
                    if (!(sampleAnalysisResultStatusReviewed.equalsIgnoreCase(currStatus))){    
                        diagnoses = (String[]) Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample_analysis_result", 
                                                                            new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisResultStatusCanceled, currStatus}, 
                                                                            new String[]{"result_id"}, new Object[]{resultId});
                        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0])){
                            String[] fieldsForAudit = new String[0];
                            fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisResultStatusCanceled);
                            fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                            smpAudit.sampleAuditAdd(schemaPrefix, "CANCEL_RESULT", this.getSampleGrouper()+"_"+"sample_analysis_result", resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);        
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
                
                if ((cancelScope.equalsIgnoreCase("sample_id")) && (!LabPLANETArray.valueInArray(samplesToCancel, sampleId)))
                        {samplesToCancel = LabPLANETArray.addValueToArray1D(samplesToCancel, sampleId);}
                if ((cancelScope.equalsIgnoreCase("sample_id") || cancelScope.equalsIgnoreCase("test_id")) && (!LabPLANETArray.valueInArray(testsToCancel, testId)))
                    {testsToCancel = LabPLANETArray.addValueToArray1D(testsToCancel, testId);
                     testsSampleToCancel = LabPLANETArray.addValueToArray1D(testsSampleToCancel, sampleId);
                    }
            }    
        }    
        for (Integer iTstToCancel=0;iTstToCancel<testsToCancel.length;iTstToCancel++){
                Integer currTest = (Integer) testsToCancel[iTstToCancel];    
                if (currTest!=null){
                    objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample_analysis", 
                                                                        new String[]{"test_id"},
                                                                        new Object[]{currTest},
                                                                        new String[]{"status","status_previous","test_id", "sample_id"});
                    String currStatus = (String) objectInfo[0][0];               
                    if ( (!(sampleAnalysisStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleAnalysisStatusReviewed.equalsIgnoreCase(currStatus))) && (currTest!=null) ) {                    
                        diagnoses = (String[]) Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample_analysis", 
                                                                            new String[]{"status", "status_previous"}, new Object[]{sampleAnalysisStatusCanceled, currStatus}, 
                                                                            new String[]{"test_id"}, new Object[]{currTest});      
                        if ("TRUE".equalsIgnoreCase(diagnoses[3])){
                            String[] fieldsForAudit = new String[0];
                            fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+sampleAnalysisStatusCanceled);
                            fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                            smpAudit.sampleAuditAdd(schemaPrefix, "CANCEL_RESULT", this.getSampleGrouper()+"_"+"sample_analysis", currTest, sampleId, currTest, null, fieldsForAudit, userName, userRole);        
                        }                        
                    }else{    
                        diagnoses[5]="The test "+currTest.toString()+" has status "+currStatus+" then cannot be canceled in schema "+schemaDataName;                    
                    }    
                }
        }
        
        for (Integer iSmpToCancel=0;iSmpToCancel<samplesToCancel.length;iSmpToCancel++){
                Integer currSample = (Integer) samplesToCancel[iSmpToCancel];
                objectInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample", 
                                                                    new String[]{"sample_id"},
                                                                    new Object[]{currSample},
                                                                    new String[]{"status","status_previous","sample_id", "sample_id"});
                String currStatus = (String) objectInfo[0][0];               
                if ( (!(sampleStatusCanceled.equalsIgnoreCase(currStatus))) && (!(sampleStatusReviewed.equalsIgnoreCase(currStatus))) && (currSample!=null) ){
                    diagnoses = (String[]) Rdbms.updateRecordFieldsByFilter(schemaDataName, "sample", 
                                                                        new String[]{"status", "status_previous"}, new Object[]{sampleStatusCanceled, currStatus}, 
                                                                        new String[]{"sample_id"}, new Object[]{currSample});                                                        
                if ("TRUE".equalsIgnoreCase(diagnoses[3])){
                    String[] fieldsForAudit = new String[0];
                    fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status:"+sampleStatusCanceled);
                    fieldsForAudit = LabPLANETArray.addValueToArray1D(fieldsForAudit, "status_previous:"+currStatus);
                    smpAudit.sampleAuditAdd(schemaPrefix, "CANCEL_RESULT", this.getSampleGrouper()+"_"+"sample", currSample, currSample, null, null, fieldsForAudit, userName, userRole);        
                }                        
                }else{    
                    diagnoses[5]="The sample "+currSample.toString()+" has status "+currStatus+" then cannot be canceled in schema "+schemaDataName;                    
                }
        }
        return diagnoses;
    }

    /**
     *  Automate the sample analysis assignment as to be triggered by any sample action.<br>
     *      Assigned to the actions: LOGSAMPLE.
     * 
     * @param schemaPrefix
     * @param sampleId
     * @param userName
     * @param sampleFieldName
     * @param userRole
     * @param sampleFieldValue
     * @param eventName
     * @param appSessionId
     * @param transactionId
     */
    public void autoSampleAnalysisAdd( String schemaPrefix, Integer sampleId, String userName, String userRole, String[] sampleFieldName, Object[] sampleFieldValue, String eventName, Integer appSessionId, Integer transactionId){
        
        Object[][] anaName = new Object[2][3];
        anaName[0][0] = "pH"; anaName[0][1] = "pH method"; anaName[0][2] = 1;
        anaName[1][0] = "LOD"; anaName[1][1] = "LOD Method"; anaName[1][2] = 1;
        
        for (Object[] anaName1 : anaName) {
            String[] fieldsName= new String[]{"analysis", "method_name", "method_version"};
            Object[] fieldsValue= new Object[]{(String) anaName1[0], (String) anaName1[1], (Integer) anaName1[2]};
            
            Object[] sampleAnalysisAddtoSample = sampleAnalysisAddtoSample(schemaPrefix, userName, sampleId, fieldsName, fieldsValue, userRole);
            Integer i=0;        
        } 

    }
    
    public void autoSampleAliquoting( String schemaPrefix, Integer sampleId, String userName, String userRole, String[] sampleFieldName, Object[] sampleFieldValue, String eventName, Integer appSessionId, Integer transactionId){
        // Not implemented yet
    }
    
    public Object[] sarChangeUOM( String schemaPrefix, Integer resultId, String newUOM, String userName, String userRole){
        Object[] diagnoses = new Object[0];
        String auditActionName = "CHANGE_UOM";
        schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, "data");
        tableName="sample_analysis_result";
        
        Object[][] resultInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, tableName, 
                new String[]{"result_id" },
                new Object[]{ resultId}, 
                new String[]{"result_id", "status", "param_name", "uom", "raw_value", "test_id", "sample_id", "uom_conversion_mode"});
        if ("LabPLANET_FALSE".equalsIgnoreCase(resultInfo[0][0].toString())){
            return LabPLANETArray.array2dTo1d(resultInfo);
        }
        String paramName = resultInfo[0][2].toString();
        String currUOM = resultInfo[0][3].toString();
        String currValue = resultInfo[0][4].toString();
        Integer testId = Integer.valueOf(resultInfo[0][5].toString()); Integer sampleId = Integer.valueOf(resultInfo[0][6].toString());
        String specUomConversionMode = resultInfo[0][7].toString();

        if (specUomConversionMode==null || specUomConversionMode.equalsIgnoreCase("DISABLED") || ((!specUomConversionMode.contains(newUOM)) && !specUomConversionMode.equalsIgnoreCase("ALL")) ){
            errorCode = "DataSample_SampleAnalysisResult_ConversionNotAllowed";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, specUomConversionMode);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, newUOM);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, currUOM);
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                      
        }    
        
        UnitsOfMeasurement uom = new UnitsOfMeasurement();
        Object[] convDiagnoses = uom.convertValue(schemaPrefix, new BigDecimal(currValue), currUOM, newUOM);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(convDiagnoses[0].toString())) {
            errorCode = "DataSample_SampleAnalysisResult_ConverterFALSE";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, resultId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, convDiagnoses[3].toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaDataName);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                  
        }
        BigDecimal resultConverted = (BigDecimal) convDiagnoses[convDiagnoses.length-2];
        
        String[] updFieldNames = new String[]{"raw_value", "uom"};
        Object[] updFieldValues = new Object[]{resultConverted.toString(), newUOM};
        
        Object[] updateRecordFieldsByFilter = Rdbms.updateRecordFieldsByFilter(schemaDataName, tableName, 
                updFieldNames, updFieldValues,
                new String[]{"result_id"}, new Object[]{resultId});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(updateRecordFieldsByFilter[0].toString())) {
            return updateRecordFieldsByFilter;}
        
        SampleAudit smpAudit = new SampleAudit();
        auditActionName = auditActionName+" FOR "+paramName;
        Object[] fieldsForAudit = LabPLANETArray.joinTwo1DArraysInOneOf1DString(updFieldNames, updFieldValues, ":");
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, tableName, resultId, sampleId, testId, resultId, fieldsForAudit, userName, userRole);
        
        return updateRecordFieldsByFilter;
    }
    
//public Object[] logSampleAliquot( String schemaPrefix, Integer sampleId, String sampleTemplate, Integer sampleTemplateVersion, String[] smpAliqFieldName, Object[] smpAliqFieldValue, String userName, String userRole, Integer appSessionId, Boolean devMode) {
public Object[] logSampleAliquot( String schemaPrefix, Integer sampleId, String[] smpAliqFieldName, Object[] smpAliqFieldValue, String userName, String userRole, Integer appSessionId, Boolean devMode) {    
    Object[] diagnoses = new Object[]{LPPlatform.LAB_FALSE};
    
    LPPlatform labPlat = new LPPlatform();
        
    String query = "";
    String parentTableName = "sample";
    tableName = "sample_aliq";
    String actionName = "Insert";
    String auditActionName = "LOG_SAMPLE_ALIQUOT";

    String schemaDataName = "data";
    String schemaConfigName = "config";

    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);    
    schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 

    BigDecimal aliqVolume = BigDecimal.ZERO;
    String aliqVolumeUOM = "";
    
    String actionEnabledSampleAliquot_volumeRequired = Parameter.getParameterBundle(schemaPrefix.replace("\"", "")+"-data", "sampleAliquot_volumeRequired");   
    if (actionEnabledSampleAliquot_volumeRequired.toUpperCase().contains("ENABLE")){
        String[] mandatorySampleFields = new String[]{"volume_for_aliq", "volume_for_aliq_uom"};
        String[] mandatorySampleAliqFields = new String[]{"volume", "volume_uom"};
        Object[][] sampleInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, parentTableName, new String[] {"sample_id"}, new Object[]{sampleId}, mandatorySampleFields);
        if ( (sampleInfo[0][0]!=null) && (LPPlatform.LAB_FALSE.equalsIgnoreCase(sampleInfo[0][0].toString())) ){
            return LabPLANETArray.array2dTo1d(sampleInfo);}    
        for (String fv: mandatorySampleAliqFields){
            if (LabPLANETArray.valuePosicInArray(smpAliqFieldName, fv) == -1){
                errorCode = "DataSample_sampleAliquoting_volumeAndUomMandatory";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "sampleAliquot_volumeRequired");
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(smpAliqFieldName));
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                
            }
        }      
        if (aliqVolume.compareTo(BigDecimal.ZERO)==-1) {
            errorCode = "DataSample_sampleAliquoting_volumeCannotBeNegativeorZero";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aliqVolume.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                 
        }        

        if (sampleInfo[0][0]==null) {
            errorCode = "DataSample_sampleAliquoting_volumeCannotBeNegativeorZero";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "null");
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                 
        }        
        

        BigDecimal smpVolume = new BigDecimal(sampleInfo[0][0].toString()); 
        String smpVolumeUOM = (String) LPNulls.replaceNull(sampleInfo[0][1]);
        
        aliqVolume = new BigDecimal(smpAliqFieldValue[LabPLANETArray.valuePosicInArray(smpAliqFieldName, mandatorySampleAliqFields[0])].toString());        
        aliqVolumeUOM = smpAliqFieldValue[LabPLANETArray.valuePosicInArray(smpAliqFieldName, mandatorySampleAliqFields[1])].toString();
        if (sampleInfo[0][1]!=null){
            UnitsOfMeasurement uom = new UnitsOfMeasurement();
            if (aliqVolumeUOM == null ? smpVolumeUOM != null : !aliqVolumeUOM.equals(smpVolumeUOM)){
                Object[] valueConverted = uom.convertValue(schemaPrefix, aliqVolume, aliqVolumeUOM, smpVolumeUOM);
                aliqVolume = (BigDecimal) valueConverted[1];
            }
        }
        if ( smpVolume.compareTo(BigDecimal.ZERO)==-1) {
            errorCode = "DataSample_sampleAliquoting_notEnoughVolumeToAliquoting";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "sample "+sampleId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, smpVolume.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "aliquoting");
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aliqVolume.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                     
        }
        
        smpVolume = smpVolume.add(aliqVolume.negate());
        String[] smpVolFldName = new String[]{"volume_for_aliq"};
        Object[] smpVolFldValue = new Object[]{smpVolume};
        Object[] updateSampleVolume = Rdbms.updateRecordFieldsByFilter(schemaDataName, parentTableName, 
                smpVolFldName, smpVolFldValue, new String[]{"sample_id"}, new Object[]{sampleId});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(updateSampleVolume[0].toString())){
            return updateSampleVolume;}    
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, parentTableName, sampleId, 
                sampleId, null, null, 
                LabPLANETArray.joinTwo1DArraysInOneOf1DString(smpVolFldName, smpVolFldValue, ":"), userName, userRole, appSessionId);        
    }
    String sampleLevel = tableName;

    if (this.getSampleGrouper().length()>0){tableName=this.getSampleGrouper()+"_"+tableName;}            
    
    smpAliqFieldName = LabPLANETArray.addValueToArray1D(smpAliqFieldName, "sample_id");
    smpAliqFieldValue = LabPLANETArray.addValueToArray1D(smpAliqFieldValue, sampleId);
    smpAliqFieldName = LabPLANETArray.addValueToArray1D(smpAliqFieldName, "volume_for_aliq");
    smpAliqFieldValue = LabPLANETArray.addValueToArray1D(smpAliqFieldValue, aliqVolume);
    smpAliqFieldName = LabPLANETArray.addValueToArray1D(smpAliqFieldName, "volume_for_aliq_uom");
    smpAliqFieldValue = LabPLANETArray.addValueToArray1D(smpAliqFieldValue, aliqVolumeUOM);
    smpAliqFieldName = LabPLANETArray.addValueToArray1D(smpAliqFieldName, "created_by");
    smpAliqFieldValue = LabPLANETArray.addValueToArray1D(smpAliqFieldValue, userName);
    smpAliqFieldName = LabPLANETArray.addValueToArray1D(smpAliqFieldName, "created_on");  
    smpAliqFieldValue = LabPLANETArray.addValueToArray1D(smpAliqFieldValue, LabPLANETDate.getTimeStampLocalDate());

    diagnoses = Rdbms.insertRecordInTable(schemaDataName, tableName, smpAliqFieldName, smpAliqFieldValue);
    if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
        errorCode = "DataSample_errorInsertingSampleRecord";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, diagnoses[diagnoses.length-2]);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
    }
    Integer aliquotId = Integer.parseInt(diagnoses[diagnoses.length-1].toString());
    Object[] fieldsOnLogSample = LabPLANETArray.joinTwo1DArraysInOneOf1DString(smpAliqFieldName, smpAliqFieldValue, ":");
    //diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, diagnoses[diagnoses.length-1]);

    smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, tableName, aliquotId, aliquotId,
            sampleId, null, null, 
            fieldsOnLogSample, userName, userRole, appSessionId);
    Integer transactionId = null;

    return diagnoses;
    }

public Object[] logSampleSubAliquot( String schemaPrefix, Integer aliquotId, String[] smpSubAliqFieldName, Object[] smpSubAliqFieldValue, String userName, String userRole, Integer appSessionId, Boolean devMode) {
    
    Object[] diagnoses = new Object[]{LPPlatform.LAB_FALSE};
    
    LPPlatform labPlat = new LPPlatform();
        
    String query = "";
    String parentTableName = "sample_aliq";
    tableName = "sample_aliq_sub";
    String actionName = "Insert";
    String auditActionName = "LOG_SAMPLE_SUBALIQUOT";

    String schemaDataName = "data";
    String schemaConfigName = "config";

    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);    
    schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 

    Integer sampleId = 0;
    String[] mandatoryAliquotFields = new String[]{"sample_id"};
    String actionEnabledSampleSubAliquot_volumeRequired = Parameter.getParameterBundle(schemaPrefix.replace("\"", "")+"-data", "sampleSubAliquot_volumeRequired");             

    if (actionEnabledSampleSubAliquot_volumeRequired.toUpperCase().contains("ENABLE")){
        mandatoryAliquotFields = LabPLANETArray.addValueToArray1D(mandatoryAliquotFields, "volume_for_aliq");
        mandatoryAliquotFields = LabPLANETArray.addValueToArray1D(mandatoryAliquotFields, "volume_for_aliq_uom");
        
        String[] mandatorySampleSubAliqFields = new String[]{"volume", "volume_uom"};
        Object[][] aliquotInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, parentTableName, new String[] {"aliquot_id"}, new Object[]{aliquotId}, mandatoryAliquotFields);
         if ( (aliquotInfo[0][0]!=null) && (LPPlatform.LAB_FALSE.equalsIgnoreCase(aliquotInfo[0][0].toString())) ){
            return LabPLANETArray.array2dTo1d(aliquotInfo);}    
        for (String fv: mandatorySampleSubAliqFields){
            if (LabPLANETArray.valuePosicInArray(smpSubAliqFieldName, fv) == -1){
                errorCode = "DataSample_sampleSubAliquoting_volumeAndUomMandatory";
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "sampleAliquot_volumeRequired");
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, Arrays.toString(smpSubAliqFieldName));
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aliquotId.toString());
                errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
                return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                
            }
        }
        if (aliquotInfo[0][1]==null) {
            errorCode = "DataSample_sampleAliquoting_volumeCannotBeNegativeorZero";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "null");
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, sampleId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                 
        }          
        sampleId = (Integer) aliquotInfo[0][0];
        BigDecimal aliqVolume = new BigDecimal(aliquotInfo[0][1].toString());           
        String aliqVolumeUOM = (String) aliquotInfo[0][2];        
        BigDecimal subAliqVolume = new BigDecimal(smpSubAliqFieldValue[LabPLANETArray.valuePosicInArray(smpSubAliqFieldName, smpSubAliqFieldName[0])].toString());         
        String subAliqVolumeUOM = (String) smpSubAliqFieldValue[LabPLANETArray.valuePosicInArray(mandatorySampleSubAliqFields, smpSubAliqFieldName[1])];
        if (aliquotInfo[0][1]!=null){
            UnitsOfMeasurement uom = new UnitsOfMeasurement();
            if (subAliqVolumeUOM == null ? aliqVolumeUOM != null : !subAliqVolumeUOM.equals(aliqVolumeUOM)){
                Object[] valueConverted = uom.convertValue(schemaPrefix, subAliqVolume, subAliqVolumeUOM, aliqVolumeUOM);
                subAliqVolume = (BigDecimal) valueConverted[1];
            }
        }
        if ( subAliqVolume.compareTo(BigDecimal.ZERO)==-1) {
            errorCode = "DataSample_sampleAliquoting_volumeCannotBeNegativeorZero";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, subAliqVolume.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aliquotId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                 
        }        
        
       if ( aliqVolume.compareTo(BigDecimal.ZERO)==-1) {
            errorCode = "DataSample_sampleAliquoting_notEnoughVolumeToAliquoting";
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "aliquot  "+sampleId.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, aliqVolume.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, "subaliquoting");
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, subAliqVolume.toString());
            errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, schemaPrefix);
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);                          
        }
        
        aliqVolume = aliqVolume.add(subAliqVolume.negate());
        String[] smpVolFldName = new String[]{"volume_for_aliq"};
        Object[] smpVolFldValue = new Object[]{aliqVolume};
        Object[] updateSampleVolume = Rdbms.updateRecordFieldsByFilter(schemaDataName, parentTableName, 
                smpVolFldName, smpVolFldValue, new String[]{"aliquot_id"}, new Object[]{aliquotId});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(updateSampleVolume[0].toString())){
            return updateSampleVolume;}    
        smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, parentTableName, aliquotId, aliquotId, 
                sampleId, null, null, 
                LabPLANETArray.joinTwo1DArraysInOneOf1DString(smpVolFldName, smpVolFldValue, ":"), userName, userRole, appSessionId);        
    }
    if (!actionEnabledSampleSubAliquot_volumeRequired.toUpperCase().contains("ENABLE")){
        Object[][] aliquotInfo = Rdbms.getRecordFieldsByFilter(schemaDataName, parentTableName, new String[] {"aliquot_id"}, new Object[]{aliquotId}, mandatoryAliquotFields);
        sampleId = (Integer) aliquotInfo[0][0];
    }
    String sampleLevel = tableName;

    if (this.getSampleGrouper().length()>0){tableName=this.getSampleGrouper()+"_"+tableName;}            
    
    smpSubAliqFieldName = LabPLANETArray.addValueToArray1D(smpSubAliqFieldName, "sample_id");
    smpSubAliqFieldValue = LabPLANETArray.addValueToArray1D(smpSubAliqFieldValue, sampleId);
    smpSubAliqFieldName = LabPLANETArray.addValueToArray1D(smpSubAliqFieldName, "aliquot_id");
    smpSubAliqFieldValue = LabPLANETArray.addValueToArray1D(smpSubAliqFieldValue, aliquotId);
    smpSubAliqFieldName = LabPLANETArray.addValueToArray1D(smpSubAliqFieldName, "created_by");
    smpSubAliqFieldValue = LabPLANETArray.addValueToArray1D(smpSubAliqFieldValue, userName);
    smpSubAliqFieldName = LabPLANETArray.addValueToArray1D(smpSubAliqFieldName, "created_on");  
    smpSubAliqFieldValue = LabPLANETArray.addValueToArray1D(smpSubAliqFieldValue, LabPLANETDate.getTimeStampLocalDate());
    
    diagnoses = Rdbms.insertRecordInTable(schemaDataName, tableName, smpSubAliqFieldName, smpSubAliqFieldValue);
    if (!LPPlatform.LAB_TRUE.equalsIgnoreCase(diagnoses[0].toString())){
        errorCode = "DataSample_errorInsertingSampleRecord";
        errorDetailVariables = LabPLANETArray.addValueToArray1D(errorDetailVariables, diagnoses[diagnoses.length-2]);
        return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
    }
    Integer subaliquotId = Integer.parseInt(diagnoses[diagnoses.length-1].toString());
    Object[] fieldsOnLogSample = LabPLANETArray.joinTwo1DArraysInOneOf1DString(smpSubAliqFieldName, smpSubAliqFieldValue, ":");
    //diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, diagnoses[diagnoses.length-1]);

    smpAudit.sampleAuditAdd(schemaPrefix, auditActionName, tableName, subaliquotId, subaliquotId, aliquotId,
            sampleId, null, null, 
            fieldsOnLogSample, userName, userRole, appSessionId);
    Integer transactionId = null;

    return diagnoses;
    }

}
