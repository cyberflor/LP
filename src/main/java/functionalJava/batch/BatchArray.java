/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.batch;

import LabPLANET.utilities.LPNulls;
import LabPLANET.utilities.LPPlatform;
import databases.Rdbms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialArray;
import javax.sql.rowset.serial.SerialException;

/**
 *
 * @author Administrator
 */
public final class BatchArray extends Batch{
    private Integer numTotalPositions = 0;
    private Integer numRows = 0;
    private Integer numCols = 0; 
    private Integer numTotalObjects = 0;
    public String[][] batchPosic;    
    private Object[] linesName;
    private Object[] columnsName;

    /**
     *  Creates one BatchArray object in memory
     * @param batchTemplate 
     * @param batchTemplateVersion
     * @param batchName
     * @param creator
     * @param numRows
     * @param numCols
     * @param rowNames
     * @param colNames
     */
    public BatchArray(String batchTemplate, Integer batchTemplateVersion, String batchName, String creator, Integer numRows, Integer numCols, Object[] rowNames, Object[] colNames){
        super(batchTemplate, batchTemplateVersion, batchName, creator);
        
        this.numRows = numRows;
        this.numCols = numCols;
        this.numTotalPositions = numRows * numCols;
        this.numTotalObjects = 0;
        this.columnsName = colNames;
        this.linesName=rowNames;
            
        batchPosic = new String[numRows][numCols];            
    }        
    /**
     *  Creates one BatchArray object in memory
     * @param schemaName
     * @param batchTemplate 
     * @param batchTemplateVersion
     * @param batchName
     * @param creator
     * @param numRows
     * @param numCols
     */
    public BatchArray(String schemaName, String batchTemplate, Integer batchTemplateVersion, String batchName, String creator, Integer numRows, Integer numCols){
        super(batchTemplate, batchTemplateVersion, batchName, creator);
        
        this.numRows = numRows;
        this.numCols = numCols;
        this.numTotalPositions = numRows * numCols;
        this.numTotalObjects = 0;
            
        batchPosic = new String[numRows][numCols]; 
        linesName = new String[numRows];
        columnsName = new String[numCols];
        
        setLinesName(null);
        setColumnsName(null);
//        this.linesName=linesName;
//        this.columnsName=columnsName;
        
        dbCreateBatchArray(schemaName);
    }        
/*
    public BatchArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
*/
    /**
     * Give the total number of positions in one array. Example: 30x5 = 150.
     * @return Integer
     */
    public Integer getBatchPosicLen(){ return this.batchPosic.length;}
    
    /**
     * Return the number of objects contained in the Batch Array
     * @return Integer
     */
    public Integer getBatchTotalObjets(){return this.getNumTotalObjects();}
    
    /**
     * Return the batch content in a 2-dimensions way independently of being in use or free.
     * @return Object[][]
     */
    public Object[][] getBatchContent(){return this.batchPosic;}
    
    /**
     *  Get the given name for the lines
     * @return String[]
     */
    public Object[] getLinesName(){return this.linesName;}
    
    /**
     * given the name for the columns
     * @return String[]
     */
    public Object[] getColumnsName(){return this.columnsName;}
    
    /**
     * Set the lines name using the alphabet, then lines are named in the way of from a to the z.
     * @param names
     */
    public void setLinesName(String[] names){
        if (names==null){
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();            
            //Integer numLet=alphabet.length;
            Integer inumLet=0;
            Integer inumLetAlphabet=0;
            String currPrefix ="";
            while (inumLet<linesName.length){
                if (Objects.equals(inumLet, alphabet.length)){currPrefix=currPrefix+"A";inumLetAlphabet=0;}
                linesName[inumLet]=currPrefix+alphabet[inumLetAlphabet];
                inumLet++;
                inumLetAlphabet++;
            }            
        } else{
            if (this.linesName.length==names.length) linesName=names;
        }
        this.linesName=names;
    }

    /**
     * set the given name for the columns where columns are named in the way of from 1 to 100.
     * @param names
     */
    public void setColumnsName(String[] names){
        if (names==null){                                    
            Integer inumLet=1;
            while (inumLet<=columnsName.length){                
                columnsName[inumLet-1]=inumLet.toString();
                inumLet++;
            }
        } else{
            if (this.columnsName.length==names.length) columnsName=names;            
        }
        this.columnsName=names;
    }
    
    /**
     * This method adds one object in one given position unless the position is occupied <br>
     * or even out of the array dimensions.
     * @param row
     * @param col
     * @param objId
     * @return String
     */
    public String batchArrayAddObjectInPosic(Integer row, Integer col, String objId) {
        if ( (row<=this.getNumRows()) && (col<=this.getNumCols())) {                               
            if (batchPosic[row-1][col-1] == null) {
                ++this.numTotalObjects;
                batchPosic[row-1][col-1] = objId;
                return "Added Succesfully";
            } else { 
                return "The position is occupied";}
        } else {
            return "This position is out of the batch dimension";
        }
     }
    
    /**
     * Add one object in a given position and in case the position is occupied then it moves out the
     *  object in there and moving this new object to the given position unless it is out of the array dimensions.
     * @param row
     * @param col
     * @param objId
     * @return String
     */
    public String batchArrayAddObjectInPosicOverride(Integer row, Integer col, String objId) {
        String addType = ""; 
        if ( (row<=this.getNumRows()) && (col<=this.getNumCols())) {                               
            if (batchPosic[row-1][col-1] == null){
                ++this.numTotalObjects;
                addType = "Added Succesfully";
            } else {
                addType = "Content overrided";}
            batchPosic[row-1][col-1] = objId;
            return addType;
        } else {
            return "This position is out of the batch dimension"; }
     }

    /**
     * Return the name of the object contained in this given position otherwise "Empty"
     * @param row
     * @param col
     * @return String
     */
    public String getBatchPositionContent(Integer row, Integer col) {
        if (batchPosic[row-1][col-1] == null){
            //throw new RuntimeException("Empty position");
            return "Empty";
        }                
        return batchPosic[row-1][col-1];
    }        
    
    /**
     * Will return one list with the many places where this string is in use into the array
     * In case there is not match then it returns null.
     * @param searchPattern
     * @return ArrayList
     */
    public ArrayList searchStringContent(String searchPattern) {  
        ArrayList foundPosic = new ArrayList();            
//    Arrays.sort(batchPosic);        
        for(int i = 0; i < this.getNumRows(); i++){
          for(int j = 0; j < this.getNumCols(); j++){              
            if ((batchPosic[i][j] != null) && (batchPosic[i][j] == null ? searchPattern == null : batchPosic[i][j].equals(searchPattern)) ){
                int posicI = i + 1; 
                int posicJ = j + 1;
                foundPosic.add(posicI + ", " + posicJ);}                   
          }
        }
        return foundPosic; //If not found, return null             
        }     
    public Object[] dbCreateBatchArray(String schemaName)
    {
        String tableName = "batch_java";
                 
        //Integer td[][]= {{4, 17, 28, 38, 43, 58, 69, 77, 83}, {4, 12, 24, 35, 48, 55, 62, 73, 87}, {11,15, 22, 36, 46, 60, 67, 80, 84}};
        List<String> singleDArray = new ArrayList<>();
        for (String[] array :this.batchPosic) {         
              singleDArray.addAll(Arrays.asList(array));
        }       
        schemaName = LPPlatform.buildSchemaName(schemaName, LPPlatform.SCHEMA_DATA);
        
        return Rdbms.insertRecordInTable(schemaName, tableName, 
                                                new String[]{"name", "template", "template_version", "array_num_rows",
                                                     "array_num_cols", "array_total_positions", "array_total_objects",
                                                    "array_lines_name", "array_columns_name"},
                                                new Object [] {this.getBatchName(), this.getBatchTemplate(), this.getBatchTemplateVersion(), this.getNumRows(), this.getNumCols(), this.numTotalPositions, this.getNumTotalObjects(),
                                                    this.linesName, this.columnsName});
    }   
    public static BatchArray dbGetBatchArray(String schemaName, String batchName){
        schemaName = LPPlatform.buildSchemaName(schemaName, LPPlatform.SCHEMA_DATA);
        String tableName = "batch_java";
        Object[][] recordFieldsByFilter = Rdbms.getRecordFieldsByFilter(schemaName, tableName, 
                new String[]{"name"}, new Object[]{batchName}, 
                new String[]{"name", "template", "template_version", "operator" , "array_num_rows",
                    "array_num_cols", "array_total_positions", "array_total_objects",
                    "array_lines_name", "array_columns_name"}
        );
        if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(recordFieldsByFilter[0][0].toString())){                    
            try {
                SerialArray rowNames = (SerialArray) recordFieldsByFilter[0][8];
                Object[] rowNamesArr = (Object[]) rowNames.getArray();
                SerialArray colNames = (SerialArray) recordFieldsByFilter[0][8];
                Object[] colNamesArr = (Object[]) colNames.getArray();                
                
                return new BatchArray(
                        LPNulls.replaceNull(recordFieldsByFilter[0][1].toString()),
                        (Integer) recordFieldsByFilter[0][2],  batchName,
                        "",
                        //(String) recordFieldsByFilter[0][3]==null ? "" : recordFieldsByFilter[0][3].toString(),
                        //Integer.valueOf(LPNulls.replaceNull((String)recordFieldsByFilter[0][4])),
                        (Integer) recordFieldsByFilter[0][4],
                        (Integer) recordFieldsByFilter[0][5],
                        (Object[]) rowNamesArr,
                        (Object[]) colNamesArr );
            } catch (SerialException ex) {
                Logger.getLogger(BatchArray.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * @return the numRows
     */
    public Integer getNumRows() {
        return numRows;
    }

    /**
     * @return the numCols
     */
    public Integer getNumCols() {
        return numCols;
    }

    /**
     * @return the numTotalObjects
     */
    public Integer getNumTotalObjects() {
        return numTotalObjects;
    }
}
