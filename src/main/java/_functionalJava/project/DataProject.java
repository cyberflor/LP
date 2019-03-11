/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _functionalJava.project;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LPPlatform;
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

    private Object[] diagnosesProj = new Object[7];

    private String[] mandatoryFieldsProj = null;
    private Object[] mandatoryFieldsValueProj = null;

    private String classVersionProj = "0.1";

    private String[] javaDocFieldsProj = new String[0];
    private Object[] javaDocValuesProj = new Object[0];
    private String javaDocLineNameProj = "";
    
    private final String schemaDataNameProj = "data";
    private final String schemaConfigNameProj = "config";

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
        javaDocLineNameProj = "BEGIN";
        javaDocFieldsProj = LabPLANETArray.addValueToArray1D(javaDocFieldsProj, "line_name");
        javaDocValuesProj = LabPLANETArray.addValueToArray1D(javaDocValuesProj, javaDocLineNameProj);
        javaDocFieldsProj = LabPLANETArray.addValueToArray1D(javaDocFieldsProj, "class_version");
        javaDocValuesProj = LabPLANETArray.addValueToArray1D(javaDocValuesProj, classVersionProj);
        LPPlatform.addJavaClassDoc(javaDocFieldsProj, javaDocValuesProj, elementsDev);
    }    
    
        String query = "";
        String tableName = "project";
        String actionName = "Insert";
        String auditActionName = "ADD_SAMPLE_ANALYSIS";                        
        
        String schemaDataName = "data";
        String schemaConfigName = "config";
        
        schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);    
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName); 
        
        mandatoryFieldsProj = labIntChecker.getTableMandatoryFields(schemaDataName, this.getSampleGrouper()+"_"+tableName, actionName);
        
        
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineNameProj = "CHECK sampleFieldName and sampleFieldValue match in length";
        javaDocFieldsProj = LabPLANETArray.addValueToArray1D(javaDocFieldsProj, "line_name");
        javaDocValuesProj = LabPLANETArray.addValueToArray1D(javaDocValuesProj, javaDocLineNameProj);
        javaDocFieldsProj = LabPLANETArray.addValueToArray1D(javaDocFieldsProj, "class_version");
        javaDocValuesProj = LabPLANETArray.addValueToArray1D(javaDocValuesProj, classVersionProj);
        LPPlatform.addJavaClassDoc(javaDocFieldsProj, javaDocValuesProj, elementsDev);
    }    
    if (devMode==false){
        diagnosesProj = LabPLANETArray.checkTwoArraysSameLength(sampleFieldName, sampleFieldValue);
        if (sampleFieldName.length!=sampleFieldValue.length){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnosesProj[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnosesProj[1]= classVersionProj;
            diagnosesProj[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());   
            diagnosesProj[3]="LABPLANET_FALSE";
            diagnosesProj[4]="ERROR:Field names and values arrays with different length";
            diagnosesProj[5]="The values in FieldName are:"+ Arrays.toString(sampleFieldName)+". and in FieldValue are:"+Arrays.toString(sampleFieldValue);
            return diagnosesProj;
        }
    }    
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineNameProj = "CHECK sampleFieldName and sampleFieldValue match in length";
        javaDocFieldsProj = LabPLANETArray.addValueToArray1D(javaDocFieldsProj, "line_name");
        javaDocValuesProj = LabPLANETArray.addValueToArray1D(javaDocValuesProj, javaDocLineNameProj);
        javaDocFieldsProj = LabPLANETArray.addValueToArray1D(javaDocFieldsProj, "class_version");
        javaDocValuesProj = LabPLANETArray.addValueToArray1D(javaDocValuesProj, classVersionProj);
        LPPlatform.addJavaClassDoc(javaDocFieldsProj, javaDocValuesProj, elementsDev);
    }    
    if (devMode==false){        
        if (LabPLANETArray.duplicates(sampleFieldName)){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnosesProj[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnosesProj[1]= classVersionProj;
            diagnosesProj[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnosesProj[3]="FALSE";
            diagnosesProj[4]="ERROR:Fields duplicated";
            diagnosesProj[5]="Detected any field duplicated in FieldName, the values are:"+(char) 10 + Arrays.toString(sampleFieldName);
            return diagnosesProj;
        }

        // spec is not mandatory but when any of the fields involved is added to the parameters 
        //  then it turns mandatory all the fields required for linking this entity.
        Integer fieldIndexSpecCode = Arrays.asList(sampleFieldName).indexOf("spec_code");
        Integer fieldIndexSpecCodeVersion = Arrays.asList(sampleFieldName).indexOf("spec_code_version");
        Integer fieldIndexSpecVariationName = Arrays.asList(sampleFieldName).indexOf("spec_variation_name");
        if ((fieldIndexSpecCode!=-1) || (fieldIndexSpecCodeVersion!=-1) || (fieldIndexSpecVariationName!=-1)){
            mandatoryFieldsProj = LabPLANETArray.addValueToArray1D(mandatoryFieldsProj, "spec_code");
            mandatoryFieldsProj = LabPLANETArray.addValueToArray1D(mandatoryFieldsProj, "spec_code_version");
            mandatoryFieldsProj = LabPLANETArray.addValueToArray1D(mandatoryFieldsProj, "spec_variation_name");
        }

        mandatoryFieldsValueProj = new Object[mandatoryFieldsProj.length];
        String configCode = "";
        String mandatoryFieldsMissing = "";
        for (Integer inumLines=0;inumLines<mandatoryFieldsProj.length;inumLines++){
            String currField = mandatoryFieldsProj[inumLines];
            boolean contains = Arrays.asList(sampleFieldName).contains(currField.toLowerCase());
            if (!contains){
                
                Object[] sampleDefaultFieldValues = labIntChecker.getTableFieldsDefaulValues(schemaDataName, this.getSampleGrouper()+"_"+tableName, actionName); 
                
                
                if (mandatoryFieldsMissing.length()>0){mandatoryFieldsMissing = mandatoryFieldsMissing + ",";}
                mandatoryFieldsMissing = mandatoryFieldsMissing + currField;
            }else{
                Integer valuePosic = Arrays.asList(sampleFieldName).indexOf(currField);
                mandatoryFieldsValueProj[inumLines] = sampleFieldValue[valuePosic]; 
                if ("config_code".equals(currField)){configCode = sampleFieldValue[Arrays.asList(sampleFieldName).indexOf(currField)].toString();}
            }        
        }            
        if (mandatoryFieldsMissing.length()>0){
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnosesProj[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnosesProj[1]= classVersionProj;
            diagnosesProj[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnosesProj[3]="FALSE";
            diagnosesProj[4]="ERROR:Missing Mandatory Fields";
            diagnosesProj[5]="Mandatory fields not found: "+mandatoryFieldsMissing;
            return diagnosesProj;
        }        
        Object[] diagnosis = Rdbms.existsRecord(schemaConfigName, tableName, new String[]{"config","config_version"}, new Object[]{projectTemplate, projectTemplateVersion});
        if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnosis[0].toString())){	
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            diagnosesProj[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
            diagnosesProj[1]= classVersionProj;
            diagnosesProj[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
            diagnosesProj[3]="FALSE";
            diagnosesProj[4]="ERROR:Sample Config Code NOT FOUND";
            diagnosesProj[5]="The sample config code "+projectTemplate+" in its version "+projectTemplateVersion+" was not found in the schema "+schemaConfigName+". Detail:"+diagnosis[5];
            return diagnosesProj;
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
                        diagnosesProj[0]= elements[1].getClassName() + "." + elements[1].getMethodName();
                        diagnosesProj[1]= classVersionProj;
                        diagnosesProj[2]= "Code Line " + String.valueOf(elements[1].getLineNumber());
                        diagnosesProj[3]="FALSE";
                        diagnosesProj[4]=specialFunctionReturn.toString();
                        diagnosesProj[5]="The field " + currField + " is considered special and its checker (" + aMethod + ") returned the Error above";
                        return diagnosesProj;                            
                    }                
            }
        }
        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "config");    
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, projectTemplate);
        sampleFieldName = LabPLANETArray.addValueToArray1D(sampleFieldName, "config_version");    
        sampleFieldValue = LabPLANETArray.addValueToArray1D(sampleFieldValue, projectTemplateVersion); 

        diagnosesProj = Rdbms.insertRecordInTable(schemaDataName, tableName, sampleFieldName, sampleFieldValue);

        Object[] fieldsOnLogSample = LabPLANETArray.joinTwo1DArraysInOneOf1DString(sampleFieldName, sampleFieldValue, ":");

        //smpAudit.sampleAuditAdd(rdbm, schemaPrefix, auditActionName, "sample", Integer.parseInt(diagnosesProj[6]), Integer.parseInt(diagnosesProj[6]), null, null, fieldsOnLogSample, userName, userRole);

        return diagnosesProj;  
    }    
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineNameProj = "END";
        Integer specialFieldIndex = Arrays.asList(javaDocFieldsProj).indexOf("line_name");
        if (specialFieldIndex==-1){
            javaDocFieldsProj = LabPLANETArray.addValueToArray1D(javaDocFieldsProj, "line_name");         javaDocValuesProj = LabPLANETArray.addValueToArray1D(javaDocValuesProj, javaDocLineNameProj);         
        }else{    
            javaDocValuesProj[specialFieldIndex] = javaDocLineNameProj;             
        }
        LPPlatform.addJavaClassDoc(javaDocFieldsProj, javaDocValuesProj, elementsDev);
    }
    return diagnosesProj; 
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
                String schemaDataNameProj = "data";
                String schemaConfigNameProj = "config";

                LPPlatform labPlat = new LPPlatform();
                schemaDataNameProj = labPlat.buildSchemaName(schemaPrefix, schemaDataNameProj);    
                schemaConfigNameProj = labPlat.buildSchemaName(schemaPrefix, schemaConfigNameProj);                 
                
                newProjSample = rdbm.updateRecordFieldsByFilter(rdbm, schemaDataNameProj, "project_sample", 
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

