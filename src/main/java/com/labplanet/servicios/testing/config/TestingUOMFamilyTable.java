/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;

import LabPLANET.utilities.LPPlatform;
import databases.Rdbms;
import functionalJava.unitsOfMeasurement.UnitsOfMeasurement;
import LabPLANET.utilities.LabPLANETArray;
import functionalJava.testingScripts.LPTestingOutFormat;
import functionalJava.testingScripts.TestingAssert;
import functionalJava.testingScripts.TestingAssertSummary;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Fran
 */
public class TestingUOMFamilyTable extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {
        TestingAssertSummary tstAssertSummary = new TestingAssertSummary();

        String csvFileName = "uom_familyTable.txt"; 
        response = LPTestingOutFormat.responsePreparation(response);        
                             
        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
        String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;
        Object[][] csvFileContent = LabPLANETArray.convertCSVinArray(csvPathName, csvFileSeparator); 

        String fileContent = LPTestingOutFormat.getHtmlStyleHeader(this.getClass().getSimpleName());

        if (Rdbms.getRdbms().startRdbms("labplanet", "avecesllegaelmomento")==null){fileContent=fileContent+"Connection to the database not established";return;}
        
        try (PrintWriter out = response.getWriter()) {
            HashMap<String, Object> csvHeaderTags = LPTestingOutFormat.getCSVHeader(LabPLANETArray.convertCSVinArray(csvPathName, "="));
            if (csvHeaderTags.containsKey(LPPlatform.LAB_FALSE)){
                fileContent=fileContent+"There are missing tags in the file header: "+csvHeaderTags.get(LPPlatform.LAB_FALSE);                        
                out.println(fileContent); 
                return;
            }            
            
            Integer numEvaluationArguments = Integer.valueOf(csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_numEvaluationArguments).toString());   
            Integer numHeaderLines = Integer.valueOf(csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_numHeaderLinesTagName).toString());   
            //numEvaluationArguments=numEvaluationArguments+1;
            String table1Header = csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_tableNameTagName+"1").toString();               
            String fileContentTable1 = LPTestingOutFormat.createTableWithHeader(table1Header, numEvaluationArguments);

            Integer iLines =numHeaderLines; 
            for (iLines=iLines;iLines<csvFileContent.length;iLines++){
                tstAssertSummary.increaseTotalTests();
                TestingAssert tstAssert = new TestingAssert(csvFileContent[iLines], numEvaluationArguments);

                String schemaPrefix = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments]);
                String familyName = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+1]);
                String[] fieldsToRetrieve = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+2]);
                
                UnitsOfMeasurement UOM = new UnitsOfMeasurement();

                Object[][] tableGet = UOM.getAllUnitsPerFamily(schemaPrefix, familyName, fieldsToRetrieve);    
                fileContentTable1 = fileContentTable1 +LPTestingOutFormat.tableStart();
                for (int iRows=0;iRows<tableGet.length;iRows++){
                   fileContentTable1 = fileContentTable1 +LPTestingOutFormat.rowStart(); 
                   fileContentTable1 = fileContentTable1 + LPTestingOutFormat.rowAddField(String.valueOf(iRows));     
                   for (int iColumns=0;iColumns<fieldsToRetrieve.length;iColumns++){
                        if ("LABPLANET_FALSE".equalsIgnoreCase(tableGet[0][0].toString())) {
                             fileContentTable1 = fileContentTable1+ 
                                     LPTestingOutFormat.rowAddField(String.valueOf(tableGet[0][3]))+
                                     LPTestingOutFormat.rowAddField(String.valueOf(tableGet[0][5]));
                        }else{                       
                           fileContentTable1 = fileContentTable1 + LPTestingOutFormat.rowAddField(String.valueOf(tableGet[iRows][iColumns]));     
                        }
                   }    
                   fileContentTable1 = fileContentTable1 +LPTestingOutFormat.rowEnd(); 
                   
                }    
                fileContentTable1 = fileContentTable1 +LPTestingOutFormat.tableEnd();
                
                if (numEvaluationArguments>0){                    
                    Object[] evaluate = tstAssert.evaluate(numEvaluationArguments, tstAssertSummary, new Object[0]);
                    fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(evaluate);                        
                }
                fileContentTable1 = fileContentTable1 +LPTestingOutFormat.rowEnd();                        
            }   
            tstAssertSummary.notifyResults();
            Rdbms.closeRdbms();
            fileContentTable1 = fileContentTable1 +LPTestingOutFormat.tableEnd();
            fileContent=fileContent+fileContentTable1;
            if (numEvaluationArguments>0){
                String fileContentSummary = LPTestingOutFormat.CreateSummaryTable(tstAssertSummary);
                fileContent=fileContent+fileContentSummary;
            }
            fileContent=fileContent+LPTestingOutFormat.bodyEnd()+LPTestingOutFormat.htmlEnd();
            out.println(fileContent);            
            LPTestingOutFormat.createLogFile(csvPathName, fileContent);
            //testingSummary=null; resChkSpec=null;
        }
        catch(IOException error){
            //testingSummary=null; resChkSpec=null;
            Rdbms.closeRdbms();
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
