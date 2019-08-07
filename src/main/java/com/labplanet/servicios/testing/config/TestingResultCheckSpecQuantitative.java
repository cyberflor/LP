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
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
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
        response = LPTestingOutFormat.responsePreparation(response);        
        DataSpec resChkSpec = new DataSpec();   
        TestingAssertSummary tstAssertSummary = new TestingAssertSummary();

        String csvFileName = "noDBSchema_config_specQuantitative_resultCheck.txt"; 
                             
        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
        String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;
        
        Object[][] csvFileContent = LPArray.convertCSVinArray(csvPathName, csvFileSeparator); 
                
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
            //numEvaluationArguments=numEvaluationArguments+1;
            String table1Header = csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_TABLE_NAME_TAG_NAME+"1").toString();               
            String fileContentTable1 = LPTestingOutFormat.createTableWithHeader(table1Header, numEvaluationArguments);

            String table2Header = csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_TABLE_NAME_TAG_NAME+"2").toString();            
            String fileContentTable2 = LPTestingOutFormat.createTableWithHeader(table2Header, numEvaluationArguments);

            for (Integer iLines=numHeaderLines;iLines<csvFileContent.length;iLines++){
                tstAssertSummary.increaseTotalTests();
                TestingAssert tstAssert = new TestingAssert(csvFileContent[iLines], numEvaluationArguments);
                String schemaName = "";
                
                Integer lineNumCols = csvFileContent[0].length-1;
                BigDecimal result = null;
                if (lineNumCols>=numEvaluationArguments)
                    {result = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments]);}
                BigDecimal minSpec = null;
                if (lineNumCols>=numEvaluationArguments+1)
                    {minSpec = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments+1]);}
                Boolean minStrict = null;
                if (lineNumCols>=numEvaluationArguments+2)
                    {minStrict = LPTestingOutFormat.csvExtractFieldValueBoolean(csvFileContent[iLines][numEvaluationArguments+2]);}
                BigDecimal maxSpec = null;
                if (lineNumCols>=numEvaluationArguments+3)
                    {maxSpec = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments+3]);}
                Boolean maxStrict = null;
                if (lineNumCols>=numEvaluationArguments+4)
                    { maxStrict = LPTestingOutFormat.csvExtractFieldValueBoolean(csvFileContent[iLines][numEvaluationArguments+4]);}
                BigDecimal minControl = null;
                if (lineNumCols>=numEvaluationArguments+5)
                    { minControl = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments+5]);}
                Boolean minControlStrict = null;
                if (lineNumCols>=numEvaluationArguments+6)
                    { minControlStrict = LPTestingOutFormat.csvExtractFieldValueBoolean(csvFileContent[iLines][numEvaluationArguments+6]);}
                BigDecimal maxControl = null;
                if (lineNumCols>=numEvaluationArguments+7)
                    {maxControl = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments+7]);}
                Boolean maxControlStrict = null;
                if (lineNumCols>=numEvaluationArguments+8)
                    {maxControlStrict = LPTestingOutFormat.csvExtractFieldValueBoolean(csvFileContent[iLines][numEvaluationArguments+8]);}
                    
                Object[] resSpecEvaluation = new Object[0];
                if (minControl==null){
                    fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                            new Object[]{iLines, result, minSpec, minStrict, maxSpec, maxStrict});
                    resSpecEvaluation = resChkSpec.resultCheck(result, minSpec, maxSpec, minStrict, maxStrict);
                }else{
                    fileContentTable2=fileContentTable2+LPTestingOutFormat.rowAddFields(
                            new Object[]{iLines, minSpec, minStrict, minControl, minControlStrict, result, maxControl, maxControlStrict, maxSpec, maxStrict});
                    resSpecEvaluation = resChkSpec.resultCheck(
                            result, minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);
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
            String fileContentSummary = LPTestingOutFormat.createSummaryTable(tstAssertSummary);
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
