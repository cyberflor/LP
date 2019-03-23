
package com.labplanet.servicios.testing.config;

import LabPLANET.utilities.LPPlatform;
import databases.Rdbms;
import functionalJava.unitsOfMeasurement.UnitsOfMeasurement;
import LabPLANET.utilities.LPArray;
import functionalJava.testingScripts.LPTestingOutFormat;
import functionalJava.testingScripts.TestingAssert;
import functionalJava.testingScripts.TestingAssertSummary;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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

        String fileContent = LPTestingOutFormat.getHtmlStyleHeader(this.getClass().getSimpleName());

        if (Rdbms.getRdbms().startRdbms("labplanet", "avecesllegaelmomento")==null){fileContent=fileContent+"Connection to the database not established";return;}
        
        try (PrintWriter out = response.getWriter()) {
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

            Integer iLines =numHeaderLines; 
            for (iLines=iLines;iLines<csvFileContent.length;iLines++){
                
                tstAssertSummary.increaseTotalTests();
                TestingAssert tstAssert = new TestingAssert(csvFileContent[iLines], numEvaluationArguments);

                fileContentTable1 = fileContentTable1 +LPTestingOutFormat.rowStart();
                Integer lineNumCols = csvFileContent[0].length-1;
                String schemaPrefix = null;
                if (lineNumCols>=numEvaluationArguments)                
                    schemaPrefix = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments]);
                String familyName = null;
                if (lineNumCols>=numEvaluationArguments+1)                
                    familyName = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+1]);
                String[] fieldsToRetrieve = null;
                if (lineNumCols>=numEvaluationArguments+2)                
                    fieldsToRetrieve = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+2]);
                BigDecimal baseValue = null;
                if (lineNumCols>=numEvaluationArguments+3)                
                    baseValue = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments+3]);
                
                UnitsOfMeasurement UOM = new UnitsOfMeasurement();
                             fileContentTable1 = fileContentTable1+ 
                                     LPTestingOutFormat.rowAddFields(new Object[]{iLines, schemaPrefix, familyName, Arrays.toString(fieldsToRetrieve), baseValue});
                String baseUnitName = UOM.getFamilyBaseUnitName(schemaPrefix, familyName);
                if (baseUnitName.length()==0){
                     fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddField(String.valueOf("Nothing to convert with no base unit defined"));
                }else{                    
                Object[][] tableGet = UOM.getAllUnitsPerFamily(schemaPrefix, familyName, fieldsToRetrieve);
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(tableGet[0][0].toString())) {
                             fileContentTable1 = fileContentTable1+ 
                                     LPTestingOutFormat.rowAddField(String.valueOf(tableGet[0][3]))+
                                     LPTestingOutFormat.rowAddField(String.valueOf(tableGet[0][5]));
                }else{
                    String tableConversions=LPTestingOutFormat.tableStart(); 
                    for (Object[] tableGet1 : tableGet) {
                        tableConversions = tableConversions +LPTestingOutFormat.rowStart();
                        Object[] newValue = UOM.convertValue(schemaPrefix, baseValue, baseUnitName, (String) tableGet1[0]);
                        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(newValue[0].toString())) {
                            tableConversions = tableConversions+
                                                                 LPTestingOutFormat.rowAddField("Not Converted");                            
                        }else{
                            tableConversions = tableConversions+
                                    LPTestingOutFormat.rowAddField("Value "+baseValue+" in "+baseUnitName+" is equal to "+newValue[newValue.length-2].toString()+" in "+newValue[newValue.length-1].toString()+" once converted.");
                        }
                        tableConversions = tableConversions +LPTestingOutFormat.rowEnd();
                    }                 
                    tableConversions = tableConversions+LPTestingOutFormat.tableEnd(); 
                    fileContentTable1 = fileContentTable1+
                                     LPTestingOutFormat.rowAddField("There are "+(tableGet.length)+" units in the family "+familyName+", the conversions are"+tableConversions);
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
            //String fileContentSummary = LPTestingOutFormat.CreateSummaryTable(testingSummary);
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
