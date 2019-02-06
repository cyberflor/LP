/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.batch;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Administrator
 */
public class BatchArray extends Batch{
    public Integer numTotalPositions = 0;
    public Integer numRows = 0;
    public Integer numCols = 0; 
    public Integer numTotalObjects = 0;
    public String[][] batchPosic;    
    public String[] linesName;
    public String[] columnsName;

    /**
     *  Creates one BatchArray object in memory
     * @param batchTemplate 
     * @param batchTemplateVersion
     * @param batchName
     * @param creator
     * @param numRows
     * @param numCols
     */
    public BatchArray(String batchTemplate, Integer batchTemplateVersion, String batchName, String creator, Integer numRows, Integer numCols){
        super(batchTemplate, batchTemplateVersion, batchName, creator);
        
        this.numRows = numRows;
        this.numCols = numCols;
        this.numTotalPositions = numRows * numCols;
        this.numTotalObjects = 0;
            
        batchPosic = new String[numRows][numCols]; 
        linesName = new String[numRows];
        columnsName = new String[numCols];
    }        

    /**
     * Give the total number of positions in one array. Example: 30x5 = 150.
     * @return Integer
     */
    public Integer getBatchPosicLen(){ return this.batchPosic.length;}
    
    /**
     * Return the number of objects contained in the Batch Array
     * @return Integer
     */
    public Integer getBatchTotalObjets(){return this.numTotalObjects;}
    
    /**
     * Return the batch content in a 2-dimensions way independently of being in use or free.
     * @return Object[][]
     */
    public Object[][] getBatchContent(){return this.batchPosic;}
    
    /**
     *  Get the given name for the lines
     * @return String[]
     */
    public String[] getLinesName(){return this.linesName;}
    
    /**
     * given the name for the columns
     * @return String[]
     */
    public String[] getColumnsName(){return this.columnsName;}
    
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
            String currLet = "";
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
    }

    /**
     * set the given name for the columns where columns are named in the way of from 1 to 100.
     * @param names
     */
    public void setColumnsName(String[] names){
        if (names==null){                                    
            Integer inumLet=1;
            String currLet = "";
            String currPrefix ="";
            while (inumLet<=columnsName.length){                
                columnsName[inumLet-1]=inumLet.toString();
                inumLet++;
            }
        } else{
            if (this.linesName.length==names.length) linesName=names;            
        }
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
        if ( (row<=this.numRows) && (col<=this.numCols)) {                               
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
        if ( (row<=this.numRows) && (col<=this.numCols)) {                               
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
        String posicContent = batchPosic[row-1][col-1];
        return posicContent;                
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
        for(int i = 0; i < this.numRows; i++)
          for(int j = 0; j < this.numCols; j++)              
            if (batchPosic[i][j] != null){    
                if(batchPosic[i][j] == null ? searchPattern == null : batchPosic[i][j].equals(searchPattern)){
                    foundPosic.add(++i + ", " + ++j);
                }           
            }            
        return foundPosic; //If not found, return null             
    }     
                
}
