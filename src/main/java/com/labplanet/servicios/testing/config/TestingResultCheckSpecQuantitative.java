package com.labplanet.servicios.testing.config;

import functionalJava.materialSpec.DataSpec;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import LabPLANET.utilities.LPPlatform;
import functionalJava.testingScripts.LPTestingOutFormat;
import LabPLANET.utilities.LabPLANETArray;
import functionalJava.testingScripts.TestingAssert;
import functionalJava.testingScripts.TestingAssertSummary;
import java.util.HashMap;
/**
 *
 * @author Administrator
 */
public class TestingResultCheckSpecQuantitative extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {        
        DataSpec resChkSpec = new DataSpec();   
        TestingAssertSummary tstAssertSummary = new TestingAssertSummary();

        String csvFileName = "noDBSchema_config_specQuantitative_resultCheck.txt"; 
        response = LPTestingOutFormat.responsePreparation(response);        
                             
        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
        String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;
        
        Object[][] csvFileContent = LabPLANETArray.convertCSVinArray(csvPathName, csvFileSeparator); 
                
        try (PrintWriter out = response.getWriter()) {
            String fileContent = LPTestingOutFormat.getHtmlStyleHeader(this.getClass().getSimpleName());
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

            String table2Header = csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_tableNameTagName+"2").toString();            
            String fileContentTable2 = LPTestingOutFormat.createTableWithHeader(table2Header, numEvaluationArguments);

            Integer iLines =numHeaderLines; 
            for (iLines=iLines;iLines<csvFileContent.length;iLines++){
                tstAssertSummary.increaseTotalTests();
                TestingAssert tstAssert = new TestingAssert(csvFileContent[iLines], numEvaluationArguments);
                String schemaName = "";
                
                BigDecimal result = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments]);
                BigDecimal minSpec = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments+1]);
                Boolean minStrict = LPTestingOutFormat.csvExtractFieldValueBoolean(csvFileContent[iLines][numEvaluationArguments+2]);
                BigDecimal maxSpec = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments+3]);
                Boolean maxStrict = LPTestingOutFormat.csvExtractFieldValueBoolean(csvFileContent[iLines][numEvaluationArguments+4]);
                BigDecimal minControl = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments+5]);
                Boolean minControlStrict = LPTestingOutFormat.csvExtractFieldValueBoolean(csvFileContent[iLines][numEvaluationArguments+6]);
                BigDecimal maxControl = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments+7]);
                Boolean maxControlStrict = LPTestingOutFormat.csvExtractFieldValueBoolean(csvFileContent[iLines][numEvaluationArguments+8]);
                    
                Object[] resSpecEvaluation = new Object[0];
                if (minControl==null){
                    fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                            new Object[]{iLines, result, minSpec, minStrict, maxSpec, maxStrict});
                    resSpecEvaluation = resChkSpec.resultCheck(schemaName, result, minSpec, maxSpec, minStrict, maxStrict);
                }else{
                    fileContentTable2=fileContentTable2+LPTestingOutFormat.rowAddFields(
                            new Object[]{iLines, minSpec, minStrict, minControl, minControlStrict, result, maxControl, maxControlStrict, maxSpec, maxStrict});
                    resSpecEvaluation = resChkSpec.resultCheck(schemaName, result, minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);
                }               
                if (numEvaluationArguments==0){                    
                    if (minControl==null){
                        fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddField(Arrays.toString(resSpecEvaluation));                     
                    }else{
                        fileContentTable2=fileContentTable2+LPTestingOutFormat.rowAddField(Arrays.toString(resSpecEvaluation));                     
                    }
                }                
                if (numEvaluationArguments>0){                    
                    Object[] evaluate = tstAssert.evaluate(numEvaluationArguments, tstAssertSummary, resSpecEvaluation);
                    if (minControl==null){
                        fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(evaluate);                        
                        fileContentTable1=fileContentTable1+LPTestingOutFormat.rowEnd();                                                
                    }else{
                        fileContentTable2=fileContentTable2+LPTestingOutFormat.rowAddFields(evaluate);                                                
                        fileContentTable2 = fileContentTable2+LPTestingOutFormat.rowEnd();                                                
                    }
                }
            }       
            tstAssertSummary.notifyResults();
            fileContentTable1 = fileContentTable1 +LPTestingOutFormat.tableEnd();
            fileContentTable2 = fileContentTable2 +LPTestingOutFormat.tableEnd();             
            String fileContentSummary = LPTestingOutFormat.CreateSummaryTable(tstAssertSummary);
            fileContent=fileContent+fileContentSummary+fileContentTable1+fileContentTable2;
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
