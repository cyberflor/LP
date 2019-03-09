/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.materialSpec;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import static LabPLANET.utilities.LabPLANETMath.nthroot;
import LabPLANET.utilities.LPPlatform;
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

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param userRole
     * @param fieldsName
     * @param fieldsValue
     * @return
     * @throws SQLException
     */
    public Object[] newSamplingPlanDetailRecord( String schemaPrefix, String userName, String userRole, String[] fieldsName, Object[] fieldsValue) throws SQLException{
        diagnoses = newSamplingPlanDetailRecordDev(schemaPrefix, userName, userRole, fieldsName, fieldsValue, false);
        return diagnoses;
    }

    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param userRole
     * @param fieldsName
     * @param fieldsValue
     * @param devMode
     * @return
     * @throws SQLException
     */
    public Object[] newSamplingPlanDetailRecord( String schemaPrefix, String userName, String userRole, String[] fieldsName, Object[] fieldsValue, Boolean devMode) throws SQLException{
        diagnoses = newSamplingPlanDetailRecordDev(schemaPrefix, userName, userRole, fieldsName, fieldsValue, devMode);
        return diagnoses;
    }
        
    /**
     *
     * @param schemaPrefix
     * @param userName
     * @param userRole
     * @param fieldsName
     * @param fieldsValue
     * @param devMode
     * @return
     * @throws SQLException
     */
    public Object[] newSamplingPlanDetailRecordDev( String schemaPrefix, String userName, String userRole, String[] fieldsName, Object[] fieldsValue, Boolean devMode) throws SQLException{
    if (devMode==true){
        LPPlatform labPlat = new LPPlatform();
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "BEGIN";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);         
        labPlat.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }  
        String actionName = "Insert";
        tableName = "project";
        String auditActionName = "CREATE SAMPLING PLAN DETAIL RECORD";
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, schemaConfigName);
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK fieldsName and fieldsValue match in length";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);         
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }    
    if (devMode==false){
        diagnoses = LabPLANETArray.checkTwoArraysSameLength(fieldsName, fieldsValue);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses[0].toString())){ return diagnoses;}
    } 
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK MANDATORY FIELDS";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);         
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }  
    if (devMode==false){
        schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);         
        Object[][] mandatoryFieldsCheck = (Object[][]) LPPlatform.mandatoryFieldsCheck(schemaDataName, fieldsName, fieldsValue, tableName, actionName);                
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(mandatoryFieldsCheck[0][0].toString())){ return mandatoryFieldsCheck;}
        for (Integer i=0;i<mandatoryFieldsCheck[1].length;i++){
            if (mandatoryFieldsCheck[1][i]!=null){
                String value = (String) mandatoryFieldsCheck[1][i];
                Integer fieldPosic = Arrays.asList(fieldsName).indexOf(value);
                if (fieldPosic==-1){                
                    fieldsName = LabPLANETArray.addValueToArray1D(fieldsName, (String) mandatoryFieldsCheck[1][i]);
                    fieldsValue = LabPLANETArray.addValueToArray1D(fieldsValue, mandatoryFieldsCheck[2][i]);    
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
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);         
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    } 
    if (devMode==false){
        diagnoses = LPPlatform.configObjectExists(schemaConfigName, fieldsName, fieldsValue, tableName);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses[0].toString())){return diagnoses;}
    }
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "CHECK SPECIAL FIELDS";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);         
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }  
    if (devMode==false){
        LPPlatform labPlat = new LPPlatform();
        diagnoses = labPlat.specialFieldsCheck(schemaDataName, fieldsName, fieldsValue, tableName, actionName);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses[0].toString())){return diagnoses;}
    }
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "INSERT RECORD IN PROJECT TABLE";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);         
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }  
    if (devMode==false){
        diagnoses = Rdbms.insertRecordInTable(schemaDataName, tableName, fieldsName, fieldsValue);    
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnoses[0].toString())){return diagnoses;}
    }    
    if (devMode==true){
        StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
        javaDocLineName = "END";
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "line_name");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, javaDocLineName);         
        javaDocFields = LabPLANETArray.addValueToArray1D(javaDocFields, "class_version");         javaDocValues = LabPLANETArray.addValueToArray1D(javaDocValues, classVersion);         
        LPPlatform.addJavaClassDoc(javaDocFields, javaDocValues, elementsDev);
    }      
        return diagnoses;
    }

    private Integer samplingAlgorithmFix(Integer val){
        return val;
    }
    
    private Integer samplingAlgorithmRootNplus1(Integer n){
	double nthRoot =  nthroot(2, n, .001);      
        //nthRoot = nthRoot++;
        return Integer.getInteger(String.valueOf(nthRoot++));        
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
