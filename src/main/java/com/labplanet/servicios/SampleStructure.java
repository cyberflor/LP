/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios;

import databases.Rdbms;
import functionalJava.analysis.UserMethod;
import functionalJava.sampleStructure.DataSample;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETNullValue;
import LabPLANET.utilities.LabPLANETPlatform;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import testing.functionalData.testingFileContentSections;

/**
 *
 * @author Administrator
 */
public class SampleStructure extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
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
            UserMethod um = new UserMethod();

            Rdbms rdbm = new Rdbms();            
            boolean isConnected = false;
            isConnected = rdbm.startRdbms("labplanet", "LabPlanet");
            
            String csvFileName = "dataSampleStructure.txt"; String csvFileSeparator=";";
            String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName; 
 
            Object[][] dataSample2D = new Object[0][0];
        
            Integer numTesting = 1;
            Integer inumTesting = 0;
            Object[][] configSpecTestingArray = new Object[numTesting][6];
            LabPLANETArray labArr = new LabPLANETArray();
            String userName="1"; 
            String userRole="oil1plant_analyst";
            
            configSpecTestingArray = labArr.convertCSVinArray(csvPathName, csvFileSeparator);

/*
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer testId=19;                                               
                String actionName="TESTASSIGNMENT";
                String newAnalyst = "84";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=testId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=newAnalyst;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer resultId=188;                                               
                String actionName="ENTERRESULT";
                String result = "2.52";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=resultId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
/*            
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "SAMPLE";
                Integer objectId = 3;                                               
                String actionName="CANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "TEST";
                Integer objectId = 52;                                               
                String actionName="UNCANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "RESULT";
                Integer objectId = 106;                                               
                String actionName="UNCANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "SAMPLE";
                Integer objectId = 4;                                               
                String actionName="UNCANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "TEST";
                Integer objectId = 57;                                               
                String actionName="UNCANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "RESULT";
                Integer objectId = 107;                                               
                String actionName="UNCANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "TEST";
                Integer objectId = 42;                                               
                String actionName="UNCANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "RESULT";
                Integer objectId = 65;                                               
                String actionName="UNCANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "SAMPLE";
                Integer objectId = 3;                                               
                String actionName="UNCANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "TEST";
                Integer objectId = 42;                                               
                String actionName="CANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "RESULT";
                Integer objectId = 65;                                               
                String actionName="CANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String objectLevel = "SAMPLE";
                Integer objectId = 3;                                               
                String actionName="CANCELRESULT";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=objectId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=objectLevel;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName = labArr.addValueToArray1D(fieldName, "status");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOGGED");
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code");
                fieldValue = labArr.addValueToArray1D(fieldValue, "spec");                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_variation_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "VARIATION1");                
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="sample-A";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName = labArr.addValueToArray1D(fieldName, "status");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOGGED");
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code");
                fieldValue = labArr.addValueToArray1D(fieldValue, "spec");                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_variation_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "VARIATION1");                
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="sample-A";
                String sampleTemplate="template1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName = labArr.addValueToArray1D(fieldName, "status");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOGGED");
                /*fieldName = labArr.addValueToArray1D(fieldName, "spec_code");
                fieldValue = labArr.addValueToArray1D(fieldValue, "spec");                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_variation_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "VARIATION1");                
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=4;                                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer resultId=106;                                               
                String actionName="ENTERRESULT";
                String result = "7";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=resultId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer resultId=108;                                               
                String actionName="ENTERRESULT";
                String result = "6.12";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=resultId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName = labArr.addValueToArray1D(fieldName, "status");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOGGED");
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code");
                fieldValue = labArr.addValueToArray1D(fieldValue, "spec");                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_variation_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "VARIATION1");                
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=3;                                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=3;                                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOD");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOD method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer resultId=89;                                               
                String actionName="ENTERRESULT";
                String result = "0.2";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=resultId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer resultId=90;                                               
                String actionName="ENTERRESULT";
                String result = "7";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=resultId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer resultId=91;                                               
                String actionName="ENTERRESULT";
                String result = "6.8";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=resultId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }            
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=2;                                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOD");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOD method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer resultId=72;                                               
                String actionName="ENTERRESULT";
                String result = "7.2";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=resultId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer resultId=73;                                               
                String actionName="ENTERRESULT";
                String result = "goodbye";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=resultId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName = labArr.addValueToArray1D(fieldName, "status");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOGGED");
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code");
                fieldValue = labArr.addValueToArray1D(fieldValue, "spec");                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_variation_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "VARIATION1");                
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=2;                                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer resultId=70;                                               
                String actionName="ENTERRESULT";
                String result = "ADIOSsa";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=resultId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=1;                                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=1;                                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOD");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOD method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
        
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer resultId=17;                                               
                String actionName="ENTERRESULT";
                String result = "3.1";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=resultId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer resultId=18;                
                               
                String actionName="ENTERRESULT";
                String result = "7";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=resultId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
//rdbm.transactionModeRollBack(con);
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=1;                                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=2;                
                               
                String actionName="ENTERRESULT";
                String result = "This is my first result for pH notes";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=1;                
                               
                String actionName="ENTERRESULT";
                String result = "1";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=1;                
                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=1;                
                String actionName="ENTERRESULT";
                String result = "1";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName = labArr.addValueToArray1D(fieldName, "status");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOGGED");
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code");
                fieldValue = labArr.addValueToArray1D(fieldValue, "spec");                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_variation_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "VARIATION1");                
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName = labArr.addValueToArray1D(fieldName, "status");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOGGED");
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code");
                fieldValue = labArr.addValueToArray1D(fieldValue, "spec");                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_variation_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "default");                
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName = labArr.addValueToArray1D(fieldName, "status");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOGGED");
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code");
                fieldValue = labArr.addValueToArray1D(fieldValue, "spec");                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 1);                
                fieldName = labArr.addValueToArray1D(fieldName, "spec_variation_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "default");                
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName = labArr.addValueToArray1D(fieldName, "status");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOGGED");
                fieldName = labArr.addValueToArray1D(fieldName, "spec_code_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, "LOGGED");                
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=3;                
                               
                String actionName="ENTERRESULT";
                String result = "1";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=result;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
          if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=3;                
                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
          if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=3;                
                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, null);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

          if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=3;                
                               
                String actionName="SAMPLEANALYSISADD";
                fieldName = labArr.addValueToArray1D(fieldName, "analysis");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH");
                fieldName = labArr.addValueToArray1D(fieldName, "method_name");
                fieldValue = labArr.addValueToArray1D(fieldValue, "pH method");
                fieldName = labArr.addValueToArray1D(fieldName, "method_version");
                fieldValue = labArr.addValueToArray1D(fieldValue, 2);
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

          if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                Integer sampleId=3;                
                               
                String actionName="SAMPLEANALYSISADD";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

          
          if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                Integer sampleId=3;                
                               
                String actionName="INCUBATIONEND";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

          if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                Integer sampleId=4;                
                               
                String actionName="INCUBATIONEND";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

          if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                Integer sampleId=3;                
                String actionName="INCUBATIONSTART";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

          if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                Integer sampleId=1;                
                String comment="My first comment on a sample";
                String actionName="SAMPLINGCOMMENTADD";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=comment;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

          if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                Integer sampleId=2;                
                String comment="My second comment on a sample";
                String actionName="SAMPLINGCOMMENTADD";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=comment;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

          if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                Integer sampleId=2;                
                String comment="My second comment on a sample";
                String actionName="SAMPLINGCOMMENTREMOVE";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=comment;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

          if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                Integer sampleId=1;                
                String comment="My first comment on a sample";
                String actionName="SAMPLINGCOMMENTADD";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=comment;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                Integer sampleId=1;                
                Date newDate=Date.valueOf("2017-11-22");
                String actionName="CHANGESAMPLINGDATE";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=newDate;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

           if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                Integer sampleId=1;                
                String actionName="RECEIVESAMPLE";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                Integer sampleId=1;                
                String actionName="RECEIVESAMPLE";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleId;
                configSpecTestingArray[inumTesting][2]=userName;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 2;
                String actionName="LOGSAMPLE";
                fieldName[0]="status";
                fieldValue[0]="LOGGED";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName[0]="status";
                fieldValue[0]="LOGGED";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }            
            if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName[0]="status";
                fieldValue[0]="LOGGED";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
                
            if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName[0]="status";
                fieldValue[0]="LOGGED2";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }

            if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName[0]="status";
                fieldValue[0]="RECEIVED2";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            
            if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName[0]="sample_config_code";
                fieldValue[0]="spec7";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            
            if (inumTesting<numTesting){
                String[] fieldName= new String[1];
                Object[] fieldValue=new Object[1];
                String schemaPrefix="oil-pl1";
                String sampleTemplate="oil1";
                Integer sampleTemplateVersion = 1;
                String actionName="LOGSAMPLE";
                fieldName[0]="code";
                fieldValue[0]="spec7";
                configSpecTestingArray[inumTesting][0]=schemaPrefix;
                configSpecTestingArray[inumTesting][1]=sampleTemplate;
                configSpecTestingArray[inumTesting][2]=sampleTemplateVersion;
                configSpecTestingArray[inumTesting][3]=fieldName;;
                configSpecTestingArray[inumTesting][4]=fieldValue;
                configSpecTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
*/

        String fileContent="";
        fileContent = testingFileContentSections.getHtmlStyleHeader(this.getServletName());
            
        DataSample smp = new DataSample("");
            
        for (Integer j=0;j<configSpecTestingArray[0].length;j++){
            fileContent = fileContent + "<th>"+configSpecTestingArray[0][j]+"</th>";
        }            

        for (Integer i=1;i<configSpecTestingArray.length;i++){
            //if (configSpecTestingArray[i][2]==null && configSpecTestingArray[i][3]==null){
            fileContent = fileContent + "<tr>";
            String[] fieldName=null;    
            Object[] fieldValue=null;
            String schemaPrefix=null;
            Integer sampleId=null;
            userName=null;                
            String functionBeingTested=null;
            Object[] dataSample = null;

            if (configSpecTestingArray[i][1]!=null){schemaPrefix = (String) configSpecTestingArray[i][1];}
            if (configSpecTestingArray[i][2]!=null){functionBeingTested = (String) configSpecTestingArray[i][2];}

            fileContent = fileContent + "<td>"+i+"</td><td>"+schemaPrefix+"</td><td>"+functionBeingTested+"</td>";

            switch (functionBeingTested.toUpperCase()){
                case "LOGSAMPLE":
                    String sampleTemplate=null;
                    Integer sampleTemplateVersion=null;
                    String[] sampleTemplateInfo = configSpecTestingArray[i][3].toString().split("\\|");
                    sampleTemplate = sampleTemplateInfo[0];
                    sampleTemplateVersion = Integer.parseInt(sampleTemplateInfo[1]);
                    if (configSpecTestingArray[i][3]!=null){fieldName = (String[]) configSpecTestingArray[i][4].toString().split("\\|");}              
                    if (configSpecTestingArray[i][4]!=null){fieldValue = (Object[]) configSpecTestingArray[i][5].toString().split("\\|");}    
                    fieldValue = labArr.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                    fileContent = fileContent + "<td>templateName, templateVersion, fieldNames, fieldValues</td>";
                    fileContent = fileContent + "<td>"+sampleTemplate+", "+sampleTemplateVersion.toString()+", "
                            +configSpecTestingArray[i][4].toString()+", "+configSpecTestingArray[i][5].toString()+"</td>";                        
                    try {
                        dataSample = smp.logSample(rdbm, schemaPrefix, sampleTemplate, sampleTemplateVersion, fieldName, fieldValue, userName, userRole);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case "RECEIVESAMPLE":  
                    if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    fileContent = fileContent + "<td>sampleId, receiver</td>";
                    fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName.toString()+"</td>";
                    dataSample = smp.sampleReception(rdbm, schemaPrefix, userName, sampleId, userRole);
                    break;       
                case "CHANGESAMPLINGDATE":
                    Date newDate=null;
                    if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){newDate =  Date.valueOf((String) configSpecTestingArray[i][5]);}
                    fileContent = fileContent + "<td>sampleId, userName, newDate</td>";
                    fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName.toString()+newDate.toString()+"</td>";
                    dataSample = smp.changeSamplingDate(rdbm, schemaPrefix, userName, sampleId, newDate, userRole);
                    break;       
                case "SAMPLINGCOMMENTADD":
                    String comment=null;
                    if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){comment = (String) configSpecTestingArray[i][5];}
                    fileContent = fileContent + "<td>sampleId, userName, comment</td>";
                    fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName.toString()+comment.toString()+"</td>";
                    dataSample = smp.sampleReceptionCommentAdd(rdbm, schemaPrefix, userName, sampleId, comment, userRole);
                    break;       
                case "SAMPLINGCOMMENTREMOVE":
                    comment=null;
                    if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){comment = (String) configSpecTestingArray[i][5];}
                    fileContent = fileContent + "<td>sampleId, userName, comment</td>";
                    fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName.toString()+comment.toString()+"</td>";
                    dataSample = smp.sampleReceptionCommentRemove(rdbm, schemaPrefix, userName, sampleId, comment, userRole);
                    break;       
                case "INCUBATIONSTART":
                    if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    fileContent = fileContent + "<td>sampleId, userName</td>";
                    fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName.toString()+"</td>";
                    dataSample = smp.setSampleStartIncubationDateTime(rdbm, schemaPrefix, userName, sampleId, userRole);
                    break;       
                case "INCUBATIONEND":
                    if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    fileContent = fileContent + "<td>sampleId, userName</td>";
                    fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName.toString()+"</td>";
                    dataSample = smp.setSampleEndIncubationDateTime(rdbm, schemaPrefix, userName, sampleId, userRole);
                    break;       
                case "SAMPLEANALYSISADD":
                    if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){fieldName = (String[]) configSpecTestingArray[i][5].toString().split("\\|");}              
                    if (configSpecTestingArray[i][6]!=null){fieldValue = (Object[]) configSpecTestingArray[i][6].toString().split("\\|");}   
                    fieldValue = labArr.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                    try {                        
                        fieldValue = labArr.convertStringWithDataTypeToObjectArray(configSpecTestingArray[i][6].toString().split("\\|"));
                        fileContent = fileContent + "<td>sampleId, userName, fieldNames, fieldValues</td>";
                        fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName.toString()+", "
                            +configSpecTestingArray[i][5].toString()+", "+configSpecTestingArray[i][6].toString()+"</td>";                            
                        dataSample = smp.sampleAnalysisAddtoSample(rdbm, schemaPrefix, userName, sampleId, fieldName, fieldValue, userRole);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;              
                case "ENTERRESULT":
                    Integer resultId = 0;
                    String rawValueResult = "";
                    if (configSpecTestingArray[i][3]!=null){resultId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){rawValueResult = (String) configSpecTestingArray[i][5];}   
                    fileContent = fileContent + "<td>resultId, userName, rawValueResult</td>";
                    fileContent = fileContent + "<td>"+resultId.toString()+", "+userName+", "+rawValueResult+"</td>";
                    try {
                        dataSample = smp.sampleAnalysisResultEntry(rdbm, schemaPrefix, userName, resultId, rawValueResult, userRole);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;              
                case "CANCELRESULT":
                    Integer objectId = 0;
                    String objectLevel = "";
                    rawValueResult = "";
                    if (configSpecTestingArray[i][3]!=null){objectId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){objectLevel = (String) configSpecTestingArray[i][5];}   
                    fileContent = fileContent + "<td>resultId, userName, objectLevel</td>";
                    fileContent = fileContent + "<td>"+objectId.toString()+", "+userName+", "+objectLevel+"</td>";
                    try {
                        sampleId = null; Integer testId = null; resultId = null;

                        if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultCancel(rdbm, schemaPrefix, userName, sampleId, testId, resultId, userRole);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;                            
                case "UNCANCELRESULT": 
                    objectId = 0;
                    objectLevel = "";
                    rawValueResult = "";
                    if (configSpecTestingArray[i][3]!=null){objectId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){objectLevel = (String) configSpecTestingArray[i][5];}                      
                    fileContent = fileContent + "<td>resultId, userName, objectLevel</td>";
                    fileContent = fileContent + "<td>"+objectId.toString()+", "+userName+", "+objectLevel+"</td>";
                    try {
                        sampleId = null; Integer testId = null; resultId = null;

                        if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultUnCancel(rdbm, schemaPrefix, userName, sampleId, testId, resultId, userRole);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;       
                case "TESTASSIGNMENT": 
                    Integer testId = 0;
                    String newAnalyst = "";
                    if (configSpecTestingArray[i][3]!=null){testId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){newAnalyst = (String) configSpecTestingArray[i][5];}                      
                    fileContent = fileContent + "<td>testId, userName, newAnalyst</td>";
                    fileContent = fileContent + "<td>"+testId.toString()+", "+userName+", "+newAnalyst+"</td>";
                    try {
                        dataSample = smp.sampleAnalysisAssignAnalyst(rdbm, schemaPrefix, userName, testId, newAnalyst, userRole);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;   
                case "GETSAMPLEINFO":
                    String schemaDataName = "data";
                    LabPLANETPlatform labPlat = new LabPLANETPlatform();
                    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);                     
                    if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    String[] fieldsToGet = (configSpecTestingArray[i][4].toString().split("\\|"));                    
                         fileContent = fileContent + "<td>"
                                 +configSpecTestingArray[i][4].toString()+"</td><td>"
                                 +sampleId.toString()+"</td>";                      
                         dataSample2D = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample", new String[]{"sample_id"}, new Object[]{sampleId}, fieldsToGet);
                    break;
                default:                
                    break;
            }
            LabPLANETNullValue labNull = new LabPLANETNullValue();
            if (functionBeingTested.equalsIgnoreCase("GETSAMPLEINFO")){
                fileContent = fileContent + "<td>"+dataSample2D[0][0].toString();
                fileContent = fileContent + ". "+labNull.replaceNull((String) dataSample2D[0][1]);
                fileContent = fileContent + ". "+labNull.replaceNull((String) dataSample2D[0][2]);
                fileContent = fileContent + ". "+labNull.replaceNull((String) dataSample2D[0][3])+"</td>";
                
            }else{
                fileContent = fileContent + "<td>"+dataSample[0].toString()+". "+dataSample[1].toString()+". "+dataSample[2].toString()+". "+dataSample[3].toString()+". "+dataSample[4].toString()+". "+dataSample[5].toString()+"</td>";
            }    
            fileContent = fileContent + "</tr>";
        }
        fileContent = fileContent + "</table>";        
        out.println(fileContent);

        csvPathName = csvPathName.replace(".txt", ".html");
        File file = new File(csvPathName);
        FileWriter fileWriter = new FileWriter(file);
        if (file.exists()){ file.delete();} 
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();   

        rdbm.closeRdbms();

        }   catch (SQLException|IOException ex) {
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
