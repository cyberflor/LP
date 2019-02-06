/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.materialSpec;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import static LabPLANET.utilities.LabPLANETMath.nthroot;
import LabPLANET.utilities.LabPLANETPlatform;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class _ConfigSamplingPlanForSpec {
    
    Object[] diagnoses = new Object[6];
    String classVersion = "Class Version=0.1";
    String javaDocLineName = "";
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    
    String[] mandatoryFields = new String[1];
    String tableName = "";
    String schemaDataName = "data";
    String schemaConfigName = "config";
        
    String mandatoryFieldsMissing = ""; 
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    LabPLANETArray labArr = new LabPLANETArray();

    /**
     *
     * @param rdbm
     * @param schemaPrefix
     * @param userName
     * @param userRole
     * @param fieldsName
     * @param fieldsValue
     * @return
     * @throws SQLException
     */
    public Object[] newSamplingPlanDetailRecord(Rdbms rdbm, String schemaPrefix, String userName, String userRole, String[] fieldsName, Object[] fieldsValue) throws SQLException{
        diagnoses = newSamplingPlanDetailRecordDev(rdbm, schemaPrefix, userName, userRole, fieldsName, fieldsValue, false);
        return diagnoses;
    }

    /**
     *
     * @param rdbm
     * @param schemaPrefix
     * @param userName
     * @param userRole
     * @param fieldsName
     * @param fieldsValue
     * @param devMode
     * @return
     * @throws SQLException
     */
    public Object[] newSamplingPlanDetailRecord(Rdbms rdbm, String schemaPrefix, String userName, String userRole, String[] fieldsName, Object[] fieldsValue, Boolean devMode) throws SQLException{
        diagnoses = newSamplingPlanDetailRecordDev(rdbm, schemaPrefix, userName, userRole, fieldsName, fieldsValue, devMode);
        return diagnoses;
    }
        
    /**
     *
     * @param rdbm
     * @param schemaPrefix
     * @param userName
     * @param userRole
     * @param fieldsName
     * @param fieldsValue
     * @param devMode
     * @return
     * @throws SQLException
     */
    public Object[] newSamplingPlanDetailRecordDev(Rdbms rdbm, String schemaPrefix, String userName, String userRole, String[] fieldsName, Object[] fieldsValue, Boolean devMode) throws SQLException{
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "BEGIN";
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);         
        labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
    }  
        String actionName = "Insert";
        tableName = "project";
        String auditActionName = "CREATE SAMPLING PLAN DETAIL RECORD";
        schemaConfigName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK fieldsName and fieldsValue match in length";
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);         
        labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
    }    
    if (devMode==false){
        diagnoses = labArr.checkTwoArraysSameLength(fieldsName, fieldsValue);
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnoses[0].toString())){ return diagnoses;}
    } 
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK MANDATORY FIELDS";
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);         
        labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
    }  
    if (devMode==false){
        schemaDataName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaDataName);         
        Object[][] mandatoryFieldsCheck = (Object[][]) labPlat.mandatoryFieldsCheck(schemaDataName, fieldsName, fieldsValue, tableName, actionName);                
        if ("LABPLANET_FALSE".equalsIgnoreCase(mandatoryFieldsCheck[0][0].toString())){ return mandatoryFieldsCheck;}
        for (Integer i=0;i<mandatoryFieldsCheck[1].length;i++){
            if (mandatoryFieldsCheck[1][i]!=null){
                String value = (String) mandatoryFieldsCheck[1][i];
                Integer fieldPosic = Arrays.asList(fieldsName).indexOf(value);
                if (fieldPosic==-1){                
                    fieldsName = labArr.addValueToArray1D(fieldsName, (String) mandatoryFieldsCheck[1][i]);
                    fieldsValue = labArr.addValueToArray1D(fieldsValue, mandatoryFieldsCheck[2][i]);    
                }else{
                    //fieldsName[i] = (String) mandatoryFieldsCheck[1][i];
                    fieldsValue[fieldPosic] = mandatoryFieldsCheck[2][i];
                }        
            }
        }   
    }  
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK CONFIG OBJECT EXISTS";
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);         
        labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
    } 
    if (devMode==false){
        diagnoses = labPlat.configObjectExists(rdbm, schemaConfigName, fieldsName, fieldsValue, tableName);
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnoses[0].toString())){return diagnoses;}
    }
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK SPECIAL FIELDS";
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);         
        labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
    }  
    if (devMode==false){
        diagnoses = labPlat.specialFieldsCheck(rdbm, schemaDataName, fieldsName, fieldsValue, tableName, actionName);
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnoses[0].toString())){return diagnoses;}
    }
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "INSERT RECORD IN PROJECT TABLE";
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);         
        labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
    }  
    if (devMode==false){
        diagnoses = rdbm.insertRecordInTable(rdbm, schemaDataName, tableName, fieldsName, fieldsValue);    
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnoses[0].toString())){return diagnoses;}
    }    
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "END";
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = labArr.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = labArr.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = labArr.addValueToArray1D(javaDocValues, classVersion);         
        labPlat.addJavaClassDoc(rdbm, javaDocFields, javaDocValues, elementsDev);
    }      
        return diagnoses;
    }

    private Integer samplingAlgorithmFix(Integer val){
        return val;
    }
    
    private Integer samplingAlgorithmRootNplus1(Integer n){
	double nthRoot =  nthroot(2, n, .001);      
        nthRoot = nthRoot++;
        return Integer.getInteger(String.valueOf(nthRoot));        
    }
    
    private Integer samplingAlgorithmEachContainer(Integer val){
        return val;
    }

    private Integer samplingAlgorithmQuantityTable(Integer val){
        return val;
    }
    
    private Integer samplingAlgorithmAQL(Integer val){
        return val;
    }    
    
    private Integer samplingAlgorithmQuantityDisable(Integer val){
        return 0;
    }    
        
}
