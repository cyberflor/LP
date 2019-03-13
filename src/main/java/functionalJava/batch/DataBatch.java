/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.batch;

import databases.Rdbms;
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
    
    /**
     *
     * @param schemaName
     * @param transac
     * @param batchArray
     * @return
     */
    public String dbCreateBatchArray( String schemaName, String transac, BatchArray batchArray){
        String ermessage="";
        String functionResult = "Fail";
        String tableName = "batch_java";

        //Integer td[][]= {{4, 17, 28, 38, 43, 58, 69, 77, 83}, {4, 12, 24, 35, 48, 55, 62, 73, 87}, {11,15, 22, 36, 46, 60, 67, 80, 84}};
        List<String> singleDArray = new ArrayList<>();
        for (String[] array :batchArray.batchPosic) {         
              singleDArray.addAll(Arrays.asList(array));
        }       
        Object[] insertRecordInTable = Rdbms.insertRecordInTable(schemaName, tableName, 
                                                new String[]{"name, template, template_version, array_num_rows,"
                                                    + "array_num_cols, array_total_positions, array_total_objects"},
                                                new Object [] {batchArray.getBatchName(), batchArray.getBatchTemplate(), batchArray.getBatchTemplateVersion(), batchArray.numRows,
                                                    + batchArray.numCols, batchArray.numTotalPositions, batchArray.numTotalObjects});
                    
        functionResult = "Added to the database";
        return functionResult;
        
    }

    /**
     *
     * @param schemaName
     * @param batchArray
     * @return
     */
    public Object[] dbCreateBatchArray( String schemaName, BatchArray batchArray)
    {
        String ermessage="";
        String functionResult = "Fail";
        String tableName = "batch_java";
                 
        //Integer td[][]= {{4, 17, 28, 38, 43, 58, 69, 77, 83}, {4, 12, 24, 35, 48, 55, 62, 73, 87}, {11,15, 22, 36, 46, 60, 67, 80, 84}};
        List<String> singleDArray = new ArrayList<>();
        for (String[] array :batchArray.batchPosic) {         
              singleDArray.addAll(Arrays.asList(array));
        }       
        Object[] insertRecordInTable = Rdbms.insertRecordInTable(schemaName, tableName, 
                                                new String[]{"name, template, template_version, array_num_rows,"
                                                    + "array_num_cols, array_total_positions, array_total_objects"},
                                                new Object [] {batchArray.getBatchName(), batchArray.getBatchTemplate(), batchArray.getBatchTemplateVersion(), batchArray.numRows,
                                                    + batchArray.numCols, batchArray.numTotalPositions, batchArray.numTotalObjects});
        //functionResult = "Added to the database";
        return insertRecordInTable;        
    }
    
    /**
     *
     * @param schemaName
     * @param batchName
     * @param fieldName
     * @param fieldValue
     * @return
     * @throws SQLException
     */
    public Integer dbUpdateBatchArray( String schemaName, String batchName, String fieldName, String fieldValue) throws SQLException{
        String ermessage="";
        String functionResult = "Fail";  
        String tableName = "batch_java";
        Integer pk = 0;
       
        Object[] updateRecordFieldsByFilter = Rdbms.updateRecordFieldsByFilter(schemaName, tableName, 
                new String[]{fieldName}, new Object[]{fieldValue}, 
                new String[]{"name"}, new Object[]{batchName});
        pk = Integer.parseInt(updateRecordFieldsByFilter[6].toString());
        return pk; 
    }    
}
