/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _functionalJava.batch;

import databases.Rdbms;
import labPLANET.utilities.LabPLANETArray;
import labPLANET.utilities.LabPLANETPlatform;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DataBatch {

    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "user_method"; 
    
    Object[] diagnoses = new Object[7];    
    
    public String dbCreateBatchArray(Rdbms rdbm, String schemaName, String transac, BatchArray batchArray)
    {
        String ermessage="";
        String functionResult = "Fail";
        String tableName = "batch_java";

        
        //Integer td[][]= {{4, 17, 28, 38, 43, 58, 69, 77, 83}, {4, 12, 24, 35, 48, 55, 62, 73, 87}, {11,15, 22, 36, 46, 60, 67, 80, 84}};
        List<String> singleDArray = new ArrayList<>();
        for (String[] array :batchArray.batchPosic) {         
              singleDArray.addAll(Arrays.asList(array));
        }       
        String[] sd = singleDArray.toArray(new String[singleDArray.size()]);       

        Object[] insertRecordInTable = rdbm.insertRecordInTable(rdbm, schemaName, tableName, 
                                                new String[]{"name, template, template_version, array_num_rows,"
                                                    + "array_num_cols, array_total_positions, array_total_objects"},
                                                new Object [] {batchArray.getBatchName(), batchArray.getBatchTemplate(), batchArray.getBatchTemplateVersion(), batchArray.numRows,
                                                    + batchArray.numCols, batchArray.numTotalPositions, batchArray.numTotalObjects});
                    
        functionResult = "Added to the database";
        return functionResult;
        
    }

    public String dbCreateBatchArray(Rdbms rdbm, String schemaName, BatchArray batchArray)
    {
        String ermessage="";
        String functionResult = "Fail";
        String tableName = "batch_java";
                 
        //Integer td[][]= {{4, 17, 28, 38, 43, 58, 69, 77, 83}, {4, 12, 24, 35, 48, 55, 62, 73, 87}, {11,15, 22, 36, 46, 60, 67, 80, 84}};
        List<String> singleDArray = new ArrayList<>();
        for (String[] array :batchArray.batchPosic) {         
              singleDArray.addAll(Arrays.asList(array));
        }       
        String[] sd = singleDArray.toArray(new String[singleDArray.size()]);       
        
        Object[] insertRecordInTable = rdbm.insertRecordInTable(rdbm, schemaName, tableName, 
                                                new String[]{"name, template, template_version, array_num_rows,"
                                                    + "array_num_cols, array_total_positions, array_total_objects"},
                                                new Object [] {batchArray.getBatchName(), batchArray.getBatchTemplate(), batchArray.getBatchTemplateVersion(), batchArray.numRows,
                                                    + batchArray.numCols, batchArray.numTotalPositions, batchArray.numTotalObjects});
        functionResult = "Added to the database";
        return functionResult;        
    }
    
    public Integer dbUpdateBatchArray(Rdbms rdbm, String schemaName, String batchName, String fieldName, String fieldValue) throws SQLException{
        String ermessage="";
        String functionResult = "Fail";  
        String tableName = "batch_java";
        Integer pk = 0;
       
        Object[] diagnoses = rdbm.updateRecordFieldsByFilter(rdbm, schemaName, tableName, 
                new String[]{fieldName}, new Object[]{fieldValue}, 
                new String[]{"name"}, new Object[]{batchName});
        pk = Integer.parseInt(diagnoses[6].toString());
        return pk; 
    }    
}
