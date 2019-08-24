
package com.labplanet.servicios.testing.config;

import LabPLANET.utilities.LPPlatform;
import databases.Rdbms;
import functionalJava.unitsOfMeasurement.UnitsOfMeasurement;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import functionalJava.testingScripts.LPTestingOutFormat;
import functionalJava.testingScripts.TestingAssert;
import functionalJava.testingScripts.TestingAssertSummary;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Administrator
 */
public class TestingUOMConversionTable extends HttpServlet {

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
        TestingAssertSummary tstAssertSummary = new TestingAssertSummary();

        String csvFileName = "uom_familyConversionTable.txt"; 
                             
        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
        String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;
        Object[][] csvFileContent = LPArray.convertCSVinArray(csvPathName, csvFileSeparator); 
        StringBuilder fileContentBuilder = new StringBuilder();
        fileContentBuilder.append(LPTestingOutFormat.getHtmlStyleHeader(this.getClass().getSimpleName()));

        if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}   
        
        try (PrintWriter out = response.getWriter()) {
            HashMap<String, Object> csvHeaderTags = LPTestingOutFormat.getCSVHeader(LPArray.convertCSVinArray(csvPathName, "="));
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

            for (Integer iLines=numHeaderLines;iLines<csvFileContent.length;iLines++){
                
                tstAssertSummary.increaseTotalTests();
                TestingAssert tstAssert = new TestingAssert(csvFileContent[iLines], numEvaluationArguments);

                String schemaPrefix = null;
                String familyName = null;
                String[] fieldsToRetrieve = null;
                BigDecimal baseValue = null;
                Integer lineNumCols = csvFileContent[0].length-1;
                if (lineNumCols>=numEvaluationArguments)                
                    schemaPrefix = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments]);
                if (lineNumCols>=numEvaluationArguments+1)                
                    familyName = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+1]);
                if (lineNumCols>=numEvaluationArguments+2)                
                    fieldsToRetrieve = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+2]);
                if (lineNumCols>=numEvaluationArguments+3)                
                    baseValue = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments+3]);
                fileContentTable1Builder.append(LPTestingOutFormat.rowAddFields(new Object[]{iLines, schemaPrefix, familyName, fieldsToRetrieve, baseValue}));
                UnitsOfMeasurement uom = new UnitsOfMeasurement();
                String baseUnitName = uom.getFamilyBaseUnitName(schemaPrefix, familyName);
                if (baseUnitName.length()==0){
                    fileContentTable1Builder.append(LPTestingOutFormat.rowAddField(String.valueOf("Nothing to convert with no base unit defined")));
                }else{                    
                Object[][] tableGet = uom.getAllUnitsPerFamily(schemaPrefix, familyName, fieldsToRetrieve);
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(tableGet[0][0].toString())) {
                    fileContentTable1Builder.append(LPTestingOutFormat.rowAddField(tableGet[0][3].toString()))
                            .append(tableGet[0][5].toString());
                }else{
                    StringBuilder tableConversionsBuilder = new StringBuilder();
                    for (Object[] tableGet1 : tableGet) {
                        tableConversionsBuilder.append(LPTestingOutFormat.rowStart());
                        Object[] newValue = uom.convertValue(schemaPrefix, baseValue, baseUnitName, (String) tableGet1[0]);
                        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(newValue[0].toString())) {
                            tableConversionsBuilder.append(LPTestingOutFormat.rowAddField("Not Converted"));
                        }else{
                            tableConversionsBuilder.append(LPTestingOutFormat.rowAddField("Value "+baseValue+" in "+baseUnitName+" is equal to "+newValue[newValue.length-2].toString()+" in "+newValue[newValue.length-1].toString()+" once converted."));
                        }
                        tableConversionsBuilder.append(LPTestingOutFormat.rowEnd());
                    }                 
                    tableConversionsBuilder.append(LPTestingOutFormat.tableEnd());
                    fileContentTable1Builder.append(LPTestingOutFormat.rowAddField("There are "+(tableGet.length)+" units in the family "+familyName+", the conversions are"+tableConversionsBuilder.toString()));
                }    
                fileContentTable1Builder.append(LPTestingOutFormat.rowEnd());
            }    
            fileContentTable1Builder.append(LPTestingOutFormat.tableEnd());
            if (numEvaluationArguments>0){                    
                Object[] evaluate = tstAssert.evaluate(numEvaluationArguments, tstAssertSummary, new Object[0]);
                fileContentTable1Builder.append(LPTestingOutFormat.rowAddFields(evaluate));
            }
            fileContentTable1Builder.append(LPTestingOutFormat.rowEnd());
            }      
            tstAssertSummary.notifyResults();
            Rdbms.closeRdbms();
            fileContentTable1Builder.append(LPTestingOutFormat.tableEnd());
            fileContentBuilder.append(fileContentTable1Builder.toString()).append(LPTestingOutFormat.bodyEnd()).append(LPTestingOutFormat.htmlEnd());            
            out.println(fileContentBuilder.toString());            
            LPTestingOutFormat.createLogFile(csvPathName, fileContentBuilder.toString());
        }
        catch(IOException error){
            Rdbms.closeRdbms();
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
