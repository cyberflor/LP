/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config.noDB;

import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import functionalJava.materialSpec.ConfigSpecRule;
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
 * @author Administrator
 */
public class TestingConfig_specQualitative_ruleFormat extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {
        response = LPTestingOutFormat.responsePreparation(response);        
        ConfigSpecRule mSpec = new ConfigSpecRule();
        
        TestingAssertSummary tstAssertSummary = new TestingAssertSummary();

        String csvFileName = "noDBSchema_config_SpecQualitativeRuleGeneratorChecker.txt"; 
                             
        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
        String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;
        
        Object[][] csvFileContent = LPArray.convertCSVinArray(csvPathName, csvFileSeparator); 
        StringBuilder fileContentBuilder = new StringBuilder();
        fileContentBuilder.append(LPTestingOutFormat.getHtmlStyleHeader(this.getClass().getSimpleName()));
                
        try (PrintWriter out = response.getWriter()) {
            String[][] headerInfo = LPArray.convertCSVinArray(csvPathName, "=");
            HashMap<String, Object> csvHeaderTags = LPTestingOutFormat.getCSVHeader(headerInfo);
            if (csvHeaderTags.containsKey(LPPlatform.LAB_FALSE)){
                fileContentBuilder.append("There are missing tags in the file header: ").append(csvHeaderTags.get(LPPlatform.LAB_FALSE));
                out.println(fileContentBuilder.toString()); 
                return;
            }            
            
            Integer numEvaluationArguments = Integer.valueOf(csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_NUM_EVALUATION_ARGUMENTS).toString());   
            Integer numHeaderLines = Integer.valueOf(csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_NUM_HEADER_LINES_TAG_NAME).toString());   
            
            String table1Header = csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_TABLE_NAME_TAG_NAME+"1").toString();               
            StringBuilder fileContentTable1Builder = new StringBuilder();
            fileContentTable1Builder.append(LPTestingOutFormat.createTableWithHeader(table1Header, numEvaluationArguments));
            
            for ( Integer iLines =numHeaderLines;iLines<csvFileContent.length;iLines++){
                tstAssertSummary.increaseTotalTests();
                    
                TestingAssert tstAssert = new TestingAssert(csvFileContent[iLines], numEvaluationArguments);
                
                if (csvFileContent[iLines][0]==null){tstAssertSummary.increasetotalLabPlanetBooleanUndefined();}
                if (csvFileContent[iLines][1]==null){tstAssertSummary.increasetotalLabPlanetErrorCodeUndefined();}

                Integer lineNumCols = csvFileContent[0].length-1;
                String ruleType = null;
                if (lineNumCols>=numEvaluationArguments)                               
                     ruleType = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments]);
                String specText = null;
                if (lineNumCols>=numEvaluationArguments+1)                               
                     specText = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+1]);
                String separator = null;
                if (lineNumCols>=numEvaluationArguments+2)                               
                     separator = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+2]);

                fileContentTable1Builder.append(LPTestingOutFormat.rowAddFields(new Object[]{iLines, ruleType, specText, separator}));
                    
                Object[] resSpecEvaluation = mSpec.specLimitIsCorrectQualitative(ruleType, specText, separator);
                    
                if (numEvaluationArguments==0){                    
                    fileContentTable1Builder.append(LPTestingOutFormat.rowAddField(Arrays.toString(resSpecEvaluation)));                     
                }                                
                if (numEvaluationArguments>0){                    
                    Object[] evaluate = tstAssert.evaluate(numEvaluationArguments, tstAssertSummary, resSpecEvaluation);
                    fileContentTable1Builder.append(LPTestingOutFormat.rowAddFields(evaluate));                        
                    fileContentTable1Builder.append(LPTestingOutFormat.rowEnd());                                                
                }
            }    
            tstAssertSummary.notifyResults();
            fileContentTable1Builder.append(LPTestingOutFormat.tableEnd());
            if (numEvaluationArguments>0){
                String fileContentSummary = LPTestingOutFormat.createSummaryTable(tstAssertSummary);
                fileContentBuilder.append(fileContentSummary).append(fileContentTable1Builder);
            }
            fileContentBuilder.append(LPTestingOutFormat.bodyEnd()).append(LPTestingOutFormat.htmlEnd());
            out.println(fileContentBuilder.toString());            
            LPTestingOutFormat.createLogFile(csvPathName, fileContentBuilder.toString());
            tstAssertSummary=null; mSpec=null;
        }
        catch(IOException error){
            tstAssertSummary=null; mSpec=null;
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
