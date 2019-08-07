/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;;

import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import functionalJava.materialSpec.DataSpec;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import functionalJava.testingScripts.LPTestingOutFormat;
import functionalJava.testingScripts.TestingAssert;
import functionalJava.testingScripts.TestingAssertSummary;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class TestingResultCheckSpecQualitative extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {
        response = LPTestingOutFormat.responsePreparation(response);        
        DataSpec resChkSpec = new DataSpec();   
        TestingAssertSummary tstAssertSummary = new TestingAssertSummary();

        String csvFileName = "noDBSchema_config_specQualitative_resultCheck.txt";                              
        Object[][] csvFileContent = LPTestingOutFormat.getCSVFileContent(csvFileName); 
        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
        
        try (PrintWriter out = response.getWriter()) {
            String fileContent = LPTestingOutFormat.getHtmlStyleHeader(this.getClass().getSimpleName());
            HashMap<String, Object> csvHeaderTags = LPTestingOutFormat.getCSVHeader(LPArray.convertCSVinArray(csvPathName, "="));
            if (csvHeaderTags.containsKey(LPPlatform.LAB_FALSE)){
                fileContent=fileContent+"There are missing tags in the file header: "+csvHeaderTags.get(LPPlatform.LAB_FALSE);                        
                out.println(fileContent); 
                return;
            }                        
            Integer numEvaluationArguments = Integer.valueOf(csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_NUM_EVALUATION_ARGUMENTS).toString());   
            Integer numHeaderLines = Integer.valueOf(csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_NUM_HEADER_LINES_TAG_NAME).toString());   
            String table1Header = csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_TABLE_NAME_TAG_NAME+"1").toString();               
            String fileContentTable1 = LPTestingOutFormat.createTableWithHeader(table1Header, numEvaluationArguments);

            for (Integer iLines=numHeaderLines;iLines<csvFileContent.length;iLines++){
                tstAssertSummary.increaseTotalTests();
                TestingAssert tstAssert = new TestingAssert(csvFileContent[iLines], numEvaluationArguments);                
                
                Integer lineNumCols = csvFileContent[0].length-1;
                String result = null;
                if (lineNumCols>=numEvaluationArguments)                               
                     result = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments]);
                String ruleType = null;
                if (lineNumCols>=numEvaluationArguments+1)                               
                     ruleType = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+1]);
                String values = null;
                if (lineNumCols>=numEvaluationArguments+2)                               
                     values = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+2]);
                String separator = null;
                if (lineNumCols>=numEvaluationArguments+3)                               
                     separator = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+3]);
                String listName = null;
                if (lineNumCols>=numEvaluationArguments+4)                               
                     listName = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+4]);

                fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                        new Object[]{iLines, result, ruleType, values, separator, listName});
                    
                Object[] resSpecEvaluation = resChkSpec.resultCheck(result, ruleType, values, separator, listName);
                if (numEvaluationArguments==0){                    
                    fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddField(Arrays.toString(resSpecEvaluation));                     
                }
                if (numEvaluationArguments>0){                    
                    Object[] evaluate = tstAssert.evaluate(numEvaluationArguments, tstAssertSummary, resSpecEvaluation);
                    fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(evaluate);                        
                }
                fileContentTable1=fileContentTable1+LPTestingOutFormat.rowEnd();                                                
            }                          
            tstAssertSummary.notifyResults();
            fileContentTable1 = fileContentTable1 +LPTestingOutFormat.tableEnd();
            String fileContentSummary = LPTestingOutFormat.createSummaryTable(tstAssertSummary);
            fileContent=fileContent+fileContentSummary+fileContentTable1;
            fileContent=fileContent+LPTestingOutFormat.bodyEnd()+LPTestingOutFormat.htmlEnd();
            out.println(fileContent);            
            LPTestingOutFormat.createLogFile(csvPathName, fileContent);
            tstAssertSummary=null; resChkSpec=null;
        }
        catch(IOException error){
            tstAssertSummary=null; resChkSpec=null;
        }        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        try{
        processRequest(request, response);
        }catch(ServletException|IOException e){
            LPFrontEnd.servletReturnResponseError(request, response, e.getMessage(), new Object[]{}, null);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        try{
        processRequest(request, response);
        }catch(ServletException|IOException e){
            LPFrontEnd.servletReturnResponseError(request, response, e.getMessage(), new Object[]{}, null);
        }
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
