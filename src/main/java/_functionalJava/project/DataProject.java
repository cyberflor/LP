/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _functionalJava.project;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;
import databases.DataDataIntegrity;
import databases.Rdbms;
import functionalJava.audit.SampleAudit;
import java.util.Date;
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

    Object[] diagnoses = new Object[7];

    String[] mandatoryFields = null;
    Object[] mandatoryFieldsValue = null;

    String classVersion = "0.1";

    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";

    /**
     *
     * @param grouperName
     */
    public DataProject(String grouperName) {
        super(grouperName);
    }

    /**
     *
     * @param schemaPrefix
     * @param sampleTemplate
     * @param sampleTemplateVersion
     * @param sampleFieldName
     * @param sampleFieldValue
     * @param userName
     * @param userRole
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws SQLException
     */
    public Object[] createProjectDev( String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
    Object[] diag = createProject(schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, true);
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
     * @param userRole
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws SQLException
     */
    public Object[] createProject( String schemaPrefix, String sampleTemplate, Integer sampleTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
    Object[] diag = createProject(schemaPrefix, sampleTemplate, sampleTemplateVersion, sampleFieldName, sampleFieldValue, userName, userRole, false);
    return diag;
}

Object[] createProject( String schemaPrefix, String projectTemplate, Integer projectTemplateVersion, String[] sampleFieldName, Object[] sampleFieldValue, String userName, String userRole, Boolean devMode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException{
    DataDataIntegrity labIntChecker = new DataDataIntegrity();

    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "BEGIN";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);
        LabPLANETPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }    
    
        String query = "";
        String tableName = "project";
        String actionName = "Insert";
        String auditActionName = "ADD_SAMPLE_ANALYSIS";                        
        
        String schemaDataName = "data";
        String schemaConfigName = "config";
        
        schemaDataName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaDataName);    
        schemaConfigName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
        
        mandatoryFields = labIntChecker.getTableMandatoryFields(schemaDataName, this.getSampleGrouper()+"_"+tableName, actionName);
        
        
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK sampleFieldName and sampleFieldValue match in length";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);
        LabPLANETPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }    
    if (devMode==false){
        diagnoses = LabPLANETArray.checkTwoArraysSameLength(sampleFieldName, sampleFieldValue);
        if (sampleFieldName.length!=sampleFieldValue.length){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnoses[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnoses[1]= classVersion;
            diagnoses[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnoses[3]="LABPLANET_FALSE";
            diagnoses[4]="ERROR:Field names and values arrays with different length";
            diagnoses[5]="The values in FieldName are:"+ Arrays.toString(sampleFieldName)+". and in FieldValue are:"+Arrays.toString(sampleFieldValue);
            return diagnoses;
        }
    }    
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK sampleFieldName and sampleFieldValue match in length";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");
        javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);
        LabPLANETPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }    
    if (devMode==false){        
        if (LabPLANETArray.duplicates(sampleFieldName)){
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
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, tableName, new String[]{"config","config_version"}, new Object[]{projectTemplate, projectTemplateVersion});
        if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnosis[0].toString())){	
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
                    Object specialFunctionReturn = method.invoke(this, null, schemaPrefix, projectTemplate, projectTemplateVersion);      
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
        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "config");    
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, projectTemplate);
        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "config_version");    
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, projectTemplateVersion); 

        diagnoses = Rdbms.insertRecordInTable(schemaDataName, tableName, sampleFieldName, sampleFieldValue);

        Object[] fieldsOnLogSample = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");

        //smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample", Integer.parseInt(diagnoses[6]), Integer.parseInt(diagnoses[6]), null, null, fieldsOnLogSample, userName, userRole);

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
        LabPLANETPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }
    return diagnoses; 
}    

    /**
     *
     * @param schemaPrefix
     * @param projectTemplate
     * @param projectTemplateVersion
     * @param fieldName
     * @param fieldValue
     * @param userName
     * @param userRole
     * @param projectName
     * @param appSessionId
     * @return
     */
    public Object[] logProjectSample( String schemaPrefix, String projectTemplate, Integer projectTemplateVersion, String[] fieldName, Object[] fieldValue, String userName, String userRole, String projectName, Integer appSessionId){    
    Object[] newProjSample = new Object[0];
        try {
            DataSample ds = new DataSample("project");
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "project");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, projectName);
            newProjSample = ds.logSample(schemaPrefix, projectTemplate, projectTemplateVersion, fieldName, fieldValue, userName, userRole, appSessionId);
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
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DataProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return newProjSample;
    
}

    /**
     *
     */
    public void createProjectName(){
    DataSample prjSmp = new DataSample("project");
    
}

}

