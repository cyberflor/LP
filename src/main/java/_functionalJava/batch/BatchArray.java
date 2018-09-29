/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _functionalJava.batch;

import databases.Rdbms;
import labPLANET.utilities.LabPLANETArray;
import labPLANET.utilities.LabPLANETPlatform;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class BatchArray extends Batch{

    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "user_method"; 
    
    String[] diagnoses = new String[7];    
    
    public Integer numTotalPositions = 0;
    public Integer numRows = 0;
    public Integer numCols = 0; 
    public Integer numTotalObjects = 0;
    public String[][] batchPosic;    
    public String[] linesName;
    public String[] columnsName;

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

    public Integer getBatchPosicLen(){ return this.batchPosic.length;}
    
    public Integer getBatchTotalObjets(){return this.numTotalObjects;}
    
    public String[][] getBatchContent(){return this.batchPosic;}
    
    public String[] getLinesName(){return this.linesName;}
    
    public String[] getColumnsName(){return this.columnsName;}
    
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
        }
        else{
            if (this.linesName.length==names.length){
                linesName=names;
            }
        }
    }

    public void setColumnsName(String[] names){
        if (names==null){
                                    
            Integer inumLet=1;

            String currLet = "";
            String currPrefix ="";
            while (inumLet<=columnsName.length){                
                columnsName[inumLet-1]=inumLet.toString();
                inumLet++;

            }
        }
        else{
            if (this.linesName.length==names.length){
                linesName=names;
            }
        }
    }
    
    public String batchArrayAddObjectInPosic(Integer row, Integer col, String objId)
     {
        if ( (row<=this.numRows) && (col<=this.numCols))         
            {                   
            
            if (batchPosic[row-1][col-1] == null)
                {++this.numTotalObjects;
                batchPosic[row-1][col-1] = objId;
                return "Added Succesfully";
                }
            else
            { return "The position is occupied";}
            }
        else
        {   return "This position is out of the batch dimension";
        }
     }
    
    public String batchArrayAddObjectInPosicOverride(Integer row, Integer col, String objId)
     {
        String addType = ""; 
        if ( (row<=this.numRows) && (col<=this.numCols))         
            {                   
            
            if (batchPosic[row-1][col-1] == null)
                {++this.numTotalObjects;
                addType = "Added Succesfully";
                }
            else
                {addType = "Content overrided";}
            batchPosic[row-1][col-1] = objId;
            return addType;
//                }
//            else
//            { return "The position is occupied";}
            }
        else
        {   return "This position is out of the batch dimension";
        }
     }

    public String getBatchPositionContent(Integer row, Integer col)
    {
        if (batchPosic[row-1][col-1] == null)
        {
            //throw new RuntimeException("Empty position");
            return "Empty";
        }
                
        String posicContent = batchPosic[row-1][col-1];
        return posicContent;        
        
    }        
    
    public ArrayList searchStringContent(String searchPattern) {
  
        ArrayList foundPosic = new ArrayList();        
    
//    Arrays.sort(batchPosic);
        
        for(int i = 0; i < this.numRows; i++)
          for(int j = 0; j < this.numCols; j++)
              
            if (batchPosic[i][j] != null)  
            {    
                if(batchPosic[i][j] == searchPattern) 
                {
                    foundPosic.add(++i + ", " + ++j);
                }           
            }            
        return foundPosic; //If not found, return null             
    }     
                
}
