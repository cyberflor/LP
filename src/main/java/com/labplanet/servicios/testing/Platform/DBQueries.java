/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.Platform;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETNullValue;
import databases.Rdbms;
import functionalJava.materialSpec.ConfigSpecStructure;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class DBQueries extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
        response.setContentType("text/html;charset=UTF-8");
        //ConfigSpecStructure configSpec = new ConfigSpecStructure();
        
        String userName=null;
        Rdbms rdbm = new Rdbms();            
        boolean isConnected = false;
        try {
             isConnected = rdbm.startRdbms("labplanet", "LabPlanet");
        } catch (ClassNotFoundException|IllegalAccessException|InstantiationException|SQLException|NamingException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }


        LabPLANETArray labArr = new LabPLANETArray();
        Integer numTesting = 50;
        Integer inumTesting = 0;
        Object[][] configSpecTestingArray = new Object[numTesting][7];
        
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];             Object[] fieldValue=new Object[0];            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="sample-A-data";            String tableName="sample";
            //fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldName = labArr.addValueToArray1D(fieldName, "sampling_comment");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pHname comment");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=tableName;            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;                 configSpecTestingArray[inumTesting][4]=fieldValue;              configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            configSpecTestingArray[inumTesting][6]="INSERT";
            inumTesting++;
        }     
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];             Object[] fieldValue=new Object[0];            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="config";            String tableName="0db_queries_testing";
            //fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldName = labArr.addValueToArray1D(fieldName, "name");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pHname");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=tableName;            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;                 configSpecTestingArray[inumTesting][4]=fieldValue;              configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            configSpecTestingArray[inumTesting][6]="INSERT";
            inumTesting++;
        }                     
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];             Object[] fieldValue=new Object[0];            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="config";            String tableName="0db_queries_testing";
            //fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldName = labArr.addValueToArray1D(fieldName, "analysi1s");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=tableName;            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;                 configSpecTestingArray[inumTesting][4]=fieldValue;              configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            configSpecTestingArray[inumTesting][6]="INSERT";
            inumTesting++;
        }                
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];             Object[] fieldValue=new Object[0];            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="config";            String tableName="0db_queries_testing";
            //fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldName = labArr.addValueToArray1D(fieldName, "number");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=tableName;            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;                 configSpecTestingArray[inumTesting][4]=fieldValue;              configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            configSpecTestingArray[inumTesting][6]="INSERT";
            inumTesting++;
        }         
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];             Object[] fieldValue=new Object[0];            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="config";            String tableName="0db_queries_testing";
            //fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldName = labArr.addValueToArray1D(fieldName, "number");
            fieldValue = labArr.addValueToArray1D(fieldValue, "10");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=tableName;            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;                 configSpecTestingArray[inumTesting][4]=fieldValue;              configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            configSpecTestingArray[inumTesting][6]="INSERT";
            inumTesting++;
        }                 
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];             Object[] fieldValue=new Object[0];            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="config";            String tableName="analysis_method";
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldName = labArr.addValueToArray1D(fieldName, "analysi1s");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=tableName;            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;                 configSpecTestingArray[inumTesting][4]=fieldValue;              configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            configSpecTestingArray[inumTesting][6]="EXISTSRECORD";
            inumTesting++;
        }        
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];             Object[] fieldValue=new Object[0];            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="config";            String tableName="analysis_method";
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=tableName;            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;                 configSpecTestingArray[inumTesting][4]=fieldValue;              configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            configSpecTestingArray[inumTesting][6]="EXISTSRECORD";
            inumTesting++;
        }     
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];             Object[] fieldValue=new Object[0];            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="config";            String tableName="analysis_method_params";
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "param_name");
            fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=tableName;            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;                 configSpecTestingArray[inumTesting][4]=fieldValue;              configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            configSpecTestingArray[inumTesting][6]="EXISTSRECORD";
            inumTesting++;
        }          
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="config";
            String tableName="analysis_method";
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="config";
            String tableName="analysis_method";
            fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }

        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="zzzzzzanalysis_method_paramszzzzzz";
            fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
       if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "zzzzzanalysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
       if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "zzzzzanalysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }

        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
            fieldName = labArr.addValueToArray1D(fieldName, "method_name");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
            fieldName = labArr.addValueToArray1D(fieldName, "method_version");
            fieldValue = labArr.addValueToArray1D(fieldValue, 2);
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "method_name is null");
            fieldValue = labArr.addValueToArray1D(fieldValue, "");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "method_name is not null");
            fieldValue = labArr.addValueToArray1D(fieldValue, "");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "method_name is not null");
            fieldValue = labArr.addValueToArray1D(fieldValue, "");
            fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }        
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "method_name is not null");            
            fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }        
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "method_name like ");
            fieldValue = labArr.addValueToArray1D(fieldValue, "%met%");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }  
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "method_name like ");
            fieldValue = labArr.addValueToArray1D(fieldValue, "%met%");            
            fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "LOD");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }        
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "method_name like '%met%'");
            //fieldValue = labArr.addValueToArray1D(fieldValue, "%met%");
            fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            fieldValue = labArr.addValueToArray1D(fieldValue, "LOD");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }        
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = labArr.addValueToArray1D(fieldName, "method_name in|");
            fieldValue = labArr.addValueToArray1D(fieldValue, "pH|LOD");
            //fieldName = labArr.addValueToArray1D(fieldName, "analysis");
            //fieldValue = labArr.addValueToArray1D(fieldValue, "LOD");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = labArr.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }        

            String fileName = "C:\\home\\judas\\dbqueries.txt";            
            labArr.arrayToFile(null, configSpecTestingArray, fileName, ";");
    
    if (1==1){return;}
    
        /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet TestingDbQueries</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet TestingDbQueries at " + request.getContextPath() + "</h1>");
        out.println("</body>");
        out.println("</html>");                
        out.println("<table>");
        out.println("<th>Test#</th><th>Function Being Tested</th><th>Schema Prefix</th><th>Function Being Tested</th><th>Field Name</th><th>Field Value</th><th>Evaluation</th>");        
        
        for (Integer i=0;i<configSpecTestingArray.length;i++){
            //if (configSpecTestingArray[i][2]==null && configSpecTestingArray[i][3]==null){
            out.println("<tr>");

            userName=null;                
            String schemaPrefix=null;
            String tableName=null;
            String[] fieldName=null;    
            Object[] fieldValue=null;
            String[] fieldsToRetrieve=null;   
            String functionBeingTested="";
            String fileContent = "";
            Object[][] dataSample2D = null;

            if (configSpecTestingArray[i][0]!=null){schemaPrefix = (String) configSpecTestingArray[i][0];}
            if (configSpecTestingArray[i][1]!=null){tableName = (String) configSpecTestingArray[i][1];}
            if (configSpecTestingArray[i][2]!=null){userName = (String) configSpecTestingArray[i][2];}
            if (configSpecTestingArray[i][3]!=null){fieldName = (String[]) configSpecTestingArray[i][3];}else{fieldName = new String[0];}
            if (configSpecTestingArray[i][4]!=null){fieldValue = (Object[]) configSpecTestingArray[i][4];}else{fieldValue = new Object[0];}
            if (configSpecTestingArray[i][5]!=null){fieldsToRetrieve = (String[]) configSpecTestingArray[i][5];}else{fieldsToRetrieve = new String[0];}
            if (configSpecTestingArray[i][6]!=null){functionBeingTested = (String) configSpecTestingArray[i][6];}
            

            out.println("<td>"+i+"</td><td>"+functionBeingTested+"</td><td>"+schemaPrefix+"</td><td>"+tableName+"</td><td>"+Arrays.toString(fieldName)+"</td><td><b>"+Arrays.toString(fieldValue)+"</b></td>");
            
            switch (functionBeingTested.toUpperCase()){
                case "EXISTSRECORD":   
                    Object[] exRec =  rdbm.existsRecord(rdbm, schemaPrefix, tableName, fieldName, fieldValue);
                    dataSample2D = labArr.array1dTo2d(exRec, 6);
                    break;
                case "INSERT":                    
                    Object[] insRec = rdbm.insertRecordInTable(rdbm, schemaPrefix, tableName, fieldName, fieldValue);  
                    dataSample2D = labArr.array1dTo2d(insRec, 6);
                    break;
                default:
                    dataSample2D = rdbm.getRecordFieldsByFilter(rdbm, schemaPrefix, tableName, fieldName, fieldValue, fieldsToRetrieve);
                    break;
            }        
            //if (("LABPLANET_FALSE".equalsIgnoreCase(dataSample2D[0][0].toString()))){
                fileContent = fileContent + "<td>"+dataSample2D[0][0].toString();
                fileContent = fileContent + ". "+LabPLANETNullValue.replaceNull((String) dataSample2D[0][1]);
                if (dataSample2D[0].length>2){
                    fileContent = fileContent + ". "+LabPLANETNullValue.replaceNull((String) dataSample2D[0][2]);}
                if (dataSample2D[0].length>3){
                    fileContent = fileContent + ". "+LabPLANETNullValue.replaceNull((String) dataSample2D[0][3]);}
                if (dataSample2D[0].length>4){
                    fileContent = fileContent + ". "+LabPLANETNullValue.replaceNull((String) dataSample2D[0][4]);}                
                if (dataSample2D[0].length>5){
                    fileContent = fileContent + ". "+LabPLANETNullValue.replaceNull((String) dataSample2D[0][5])+"</td>";}                     
                 out.println(fileContent);                
            //}else{                 
            //     out.println("<td>"+"Records returned: " + dataSample2D.length+"</td>");  
            //}        
            
            //out.println("<td>"+dataSample[0].toString()+". "+dataSample[1].toString()+". "+dataSample[2].toString()+". "+dataSample[3].toString()+". "+dataSample[4].toString()+". "+dataSample[5].toString()+"</td>");
            out.println("</tr>");
        }

        out.println("</table>");        
        rdbm.closeRdbms();       

    }   
    catch (SQLException|IOException ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);                           
    }       
}
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
