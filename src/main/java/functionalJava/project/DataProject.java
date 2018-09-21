/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.project;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;
import databases.DataDataIntegrity;
import databases.Rdbms;
import functionalJava.audit.SampleAudit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import functionalJava.sampleStructure.DataSample;

/**
 *
 * @author Administrator
 */
public class DataProject extends DataSample{

    Rdbms rdbms;
    String lastError;
    String[] diagnoses = new String[7];

    String[] mandatoryFields = null;
    Object[] mandatoryFieldsValue = null;

    String classVersion = "0.1";
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

    public DataProject(String grouperName) {
        super(grouperName);
    }

public String[] createProjectDev(Rdbms rdbm, String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
    String[] diag = createProject(rdbm, schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, true);
    return diag;
}
public String[] createProject(Rdbms rdbm, String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
    String[] diag = createProject(rdbm, schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, false);
    return diag;
}

String[] createProject(Rdbms rdbm, String schemaPrefix, String projectTemplate, Integer projectTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Boolean devMode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{

    if (devMode==true){
        try {
            StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
            javaDocLineName = "BEGIN";
            javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);
            javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);
            labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
        } catch (SQLException ex) {
            Logger.getLogger(DataProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
        String query = "";
        tableName = "project";
        String actionName = "Insert";
        String auditActionName = "ADD_SAMPLE_ANALYSIS";                        
        
        String schemaDataName = "data";
        String schemaConfigName = "config";

        LabPLANETPlatform labPlat = new LabPLANETPlatform();
        schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);    
        schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName); 
        
        mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, this.getSampleGrouper()+"_"+tableName, actionName);
        
        
    if (devMode==true){
        try {
            StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
            javaDocLineName = "CHECK sampleFieldName and sampleFieldValue match in length";
            javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);
            javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);
            labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
        } catch (SQLException ex) {
            Logger.getLogger(DataProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    if (devMode==false){
        diagnoses = labArr.checkTwoArraysSameLength(sampleFieldName, sampleFieldValue);
        if (sampleFieldName.length!=sampleFieldValue.length){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR:Field names and values arrays with different length";
            diagnoses[5]="The values in FieldName are:"+ Arrays.toString(sampleFieldName)+". and in FieldValue are:"+Arrays.toString(sampleFieldValue);
            return diagnoses;
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
            Logger.getLogger(DataProject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    if (devMode==false){        
        LabPLANETArray lpa = new LabPLANETArray();
        if (lpa.duplicates(sampleFieldName)){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR:Fields duplicated";
            diagnoses[5]="Detected any field duplicated in FieldName, the values are:"+(char) 10 + Arrays.toString(sampleFieldName);
            return diagnoses;
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
                
                Object[] sampleDefaultFieldValues = labIntChecker.getTableFieldsDefaulValues(schemaDataName, this.getSampleGrouper()+"_"+tableName, actionName); 
                
                
                if (mandatoryFieldsMissing.length()>0){mandatoryFieldsMissing = mandatoryFieldsMissing + ",";}
                mandatoryFieldsMissing = mandatoryFieldsMissing + currField;
            }else{
                Integer valuePosic = Arrays.asList(sampleFieldName).indexOf(currField);
                mandatoryFieldsValue[inumLines] = sampleFieldValue[valuePosic]; 
                if ("config_code".equals(currField)){configCode = sampleFieldValue[Arrays.asList(sampleFieldName).indexOf(currField)].toString();}
            }        
        }            
        if (mandatoryFieldsMissing.length()>0){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR:Missing Mandatory Fields";
            diagnoses[5]="Mandatory fields not found: "+mandatoryFieldsMissing;
            return diagnoses;
        }        
        String[] diagnosis = rdbm.existsRecord(rdbm, schemaConfigName, tableName, new String[]{"config","config_version"}, new Object[]{projectTemplate, projectTemplateVersion});
        if (!diagnosis[3].equalsIgnoreCase("TRUE")){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnoses[3]="FALSE";
            diagnoses[4]="ERROR:Sample Config Code NOT FOUND";
            diagnoses[5]="The sample config code "+projectTemplate+" in its version "+projectTemplateVersion+" was not found in the schema "+schemaConfigName+". Detail:"+diagnosis[5];
            return diagnoses;
        }

        String[] specialFields = labIntChecker.getStructureSpecialFields(schemaDataName, this.getSampleGrouper()+"_"+"projectStructure", actionName);
        String[] specialFieldsFunction = labIntChecker.getStructureSpecialFieldsFunction(schemaDataName, this.getSampleGrouper()+"_"+"projectStructure", actionName);
        
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
                    Object specialFunctionReturn = method.invoke(this, rdbm, null, schemaPrefix, projectTemplate, projectTemplateVersion);      
                    if (specialFunctionReturn.toString().contains("ERROR")){
                        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
                        diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                        diagnoses[1]= classVersion;
                        diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
                        diagnoses[3]="FALSE";
                        diagnoses[4]=specialFunctionReturn.toString();
                        diagnoses[5]="The field " + currField + " is considered special and its checker (" + aMethod + ") returned the Error above";
                        return diagnoses;                            
                    }                
            }
        }
        sampleFieldName = lpa.addValueToArray1D(sampleFieldName, "config");    
        sampleFieldValue = lpa.addValueToArray1D(sampleFieldValue, projectTemplate);
        sampleFieldName = lpa.addValueToArray1D(sampleFieldName, "config_version");    
        sampleFieldValue = lpa.addValueToArray1D(sampleFieldValue, projectTemplateVersion); 

        diagnoses = rdbm.insertRecordInTable(rdbm, schemaDataName, tableName, sampleFieldName, sampleFieldValue);

        Object[] fieldsOnLogSample = labArr.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");

        //smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample", Integer.parseInt(diagnoses[6]), Integer.parseInt(diagnoses[6]), null, null, fieldsOnLogSample, userName, userRole);

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

public String[] logProjectSample(Rdbms rdbm, String schemaPrefix, String projectTemplate, Integer projectTemplateVersion, String[] fieldName, Object[] fieldValue, String userName, String userRole, String projectName){
    
    String[] newProjSample = new String[0];
        try {
            DataSample ds = new DataSample("project");
            fieldName = labArr.addValueToArray1D(fieldName, "project");
            fieldValue = labArr.addValueToArray1D(fieldValue, projectName);
            newProjSample = ds.logSample(rdbm, schemaPrefix, projectTemplate, projectTemplateVersion, fieldName, fieldValue, userName, userRole);
            /*if (!newProjSample[3].equalsIgnoreCase("FALSE")){
                String schemaDataName = "data";
                String schemaConfigName = "config";

                LabPLANETPlatform labPlat = new LabPLANETPlatform();
                schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);    
                schemaConfigName = labPlat.buildSchemaName(schemaPrefix, schemaConfigName);                 
                
                newProjSample = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataName, "project_sample", 
                        new String[]{"project"}, new Object[]{projectName}, 
                        new String[]{"sample_id"}, new Object[]{Integer.parseInt(newProjSample[newProjSample.length-1])});
            }*/
        } catch (IllegalAccessException|IllegalArgumentException|InvocationTargetException|SQLException ex) {
            Logger.getLogger(DataProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return newProjSample;
    
}


public void createProjectName(){
    DataSample prjSmp = new DataSample("project");
    
}

}

