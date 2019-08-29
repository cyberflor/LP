/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config.db;

import lbplanet.utilities.LPArray;
import lbplanet.utilities.LPPlatform;
import databases.Rdbms;
import functionaljavaa.materialspec.DataSpec;
import functionaljavaa.parameter.Parameter;
import static functionaljavaa.samplestructure.DataSample.FIELDNAME_ANALYSIS;
import static functionaljavaa.samplestructure.DataSample.FIELDNAME_CODE;
import static functionaljavaa.samplestructure.DataSample.FIELDNAME_VARIATION_NAME;
import functionaljavaa.samplestructure.DataSampleAnalysis;
import functionaljavaa.samplestructure.DataSampleAnalysisResult;
import functionaljavaa.testingscripts.LPTestingOutFormat;
import functionaljavaa.testingscripts.TestingAssert;
import functionaljavaa.testingscripts.TestingAssertSummary;
import functionaljavaa.unitsofmeasurement.UnitsOfMeasurement;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class TestingQuantitativeLimitAndResult extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response = LPTestingOutFormat.responsePreparation(response);        
        DataSpec resChkSpec = new DataSpec();   

        TestingAssertSummary tstAssertSummary = new TestingAssertSummary();

        String csvFileName = "dbSchema_config_specQuantitative_resultCheck.txt"; 
                             
        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
        String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;
        
        Object[][] csvFileContent = LPArray.convertCSVinArray(csvPathName, csvFileSeparator); 
        StringBuilder fileContentBuilder = new StringBuilder();
        try (PrintWriter out = response.getWriter()) {
            fileContentBuilder.append(LPTestingOutFormat.getHtmlStyleHeader(this.getClass().getSimpleName()));
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
            Integer totalLines =csvFileContent.length;
            numHeaderLines=13;
            for (Integer iLines=numHeaderLines;iLines<totalLines;iLines++){
                tstAssertSummary.increaseTotalTests();
                TestingAssert tstAssert = new TestingAssert(csvFileContent[iLines], numEvaluationArguments);
                
                Integer lineNumCols = csvFileContent[0].length-1;
                BigDecimal resultValue = null;
                BigDecimal minSpec = null;
                BigDecimal maxSpec = null;
                BigDecimal minControl = null;
                BigDecimal maxControl = null;
                
                String schemaName="";
                String specCode="";
                Integer specCodeVersion=null;
                String variation="";
                String analysis="";
                String methodName="";
                Integer methodVersion=null;
                String parameterName="";
                String resultUomName=null; 
                if (lineNumCols>=numEvaluationArguments)
                    {schemaName = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments]);}
                if (lineNumCols>=numEvaluationArguments+1)
                    {specCode = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+1]);}
                if (lineNumCols>=numEvaluationArguments+2)
                    {specCodeVersion = LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+2]);}
                if (lineNumCols>=numEvaluationArguments+3)
                    {variation = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+3]);}
                if (lineNumCols>=numEvaluationArguments+4)
                    { analysis = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+4]);}
                if (lineNumCols>=numEvaluationArguments+5)
                    { methodName = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);}
                if (lineNumCols>=numEvaluationArguments+6)
                    { methodVersion = LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+6]);}
                if (lineNumCols>=numEvaluationArguments+7)
                    {parameterName = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+7]);}
                if (lineNumCols>=numEvaluationArguments+8)
                    {resultValue = LPTestingOutFormat.csvExtractFieldValueBigDecimal(csvFileContent[iLines][numEvaluationArguments+8]);}
                if (lineNumCols>=numEvaluationArguments+9)
                    {resultUomName = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+9]);}
                
                String schemaConfigName=LPPlatform.buildSchemaName(schemaName, LPPlatform.SCHEMA_CONFIG);
                String schemaDataName=LPPlatform.buildSchemaName(schemaName, LPPlatform.SCHEMA_DATA);
                String specArgumentsSeparator = "\\*";
                Boolean specMinSpecStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_minStrictSpecWhenNotSpecified"));
                Boolean specMinControlStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_minStrictControlWhenNotSpecified"));
                Boolean specMaxControlStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_maxStrictControlWhenNotSpecified"));
                Boolean specMaxSpecStrictDefault = Boolean.getBoolean(Parameter.getParameterBundle(schemaDataName.replace("\"", ""), "specLimit_maxStrictSpecWhenNotSpecified"));

                Rdbms.stablishDBConection();
                Object[] resSpecEvaluation = null;                
                Object[][] specLimits = Rdbms.getRecordFieldsByFilter(schemaConfigName, "spec_limits", 
                    new String[]{FIELDNAME_CODE, "config_version", FIELDNAME_VARIATION_NAME, FIELDNAME_ANALYSIS, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_NAME, DataSampleAnalysis.FIELDNAME_SAMPLE_ANALYSIS_METHOD_VERSION,"parameter"}, 
                    new Object[]{specCode, specCodeVersion, variation, analysis, methodName, methodVersion, parameterName}, 
                    new String[]{"limit_id","rule_type","rule_variables", "limit_id", DataSampleAnalysisResult.FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM, DataSampleAnalysisResult.FIELDNAME_DATA_SAMPLE_ANALYSIS_RESULT_UOM_CONVERSION_MODE});
                if ( (LPPlatform.LAB_FALSE.equalsIgnoreCase(specLimits[0][0].toString())) && (!"Rdbms_NoRecordsFound".equalsIgnoreCase(specLimits[0][4].toString())) ){
                    fileContentTable1Builder.append(LPTestingOutFormat.rowAddField(Arrays.toString(resSpecEvaluation)));
                }else{
                    Integer limitId = (Integer) specLimits[0][0];
                    String ruleType = (String) specLimits[0][1];
                    String ruleVariables = (String) specLimits[0][2]; 
                    String specUomName = (String) specLimits[0][4]; 
                    
                    String[] quantiSpecTestingArray = ruleVariables.split(specArgumentsSeparator);
                    Boolean minStrict = specMinSpecStrictDefault;
                    Boolean maxStrict = specMaxSpecStrictDefault;   
                    Boolean minControlStrict = specMinControlStrictDefault;
                    Boolean maxControlStrict = specMaxControlStrictDefault;                  
                    for (Integer iField=0; iField<quantiSpecTestingArray.length;iField++){
                        String curParam = quantiSpecTestingArray[iField];

                        if (curParam.toUpperCase().contains("MINSPECSTRICT")){
                                curParam = curParam.replace("MINSPECSTRICT", "");       
                                minSpec = BigDecimal.valueOf(Long.valueOf(curParam));   minStrict=true;
                        }        
                        if (curParam.toUpperCase().contains("MINSPEC")){
                                curParam = curParam.replace("MINSPEC", "");             minSpec = BigDecimal.valueOf(Long.valueOf(curParam));
                        }        
                        if (curParam.toUpperCase().contains("MINCONTROLSTRICT")){
                                curParam = curParam.replace("MINCONTROLSTRICT", "");       minControl = BigDecimal.valueOf(Long.valueOf(curParam)); minControlStrict=true;
                        }        
                        if (curParam.toUpperCase().contains("MINCONTROL")){
                                curParam = curParam.replace("MINCONTROL", "");          minControl = BigDecimal.valueOf(Long.valueOf(curParam));
                        }        
                        if (curParam.toUpperCase().contains("MAXCONTROLTRICT")){
                                curParam = curParam.replace("MAXCONTROLSTRICT", "");       maxControl = BigDecimal.valueOf(Long.valueOf(curParam));     maxControlStrict=true;
                        }        
                        if (curParam.toUpperCase().contains("MAXCONTROL")){
                                curParam = curParam.replace("MAXCONTROL", "");          maxControl = BigDecimal.valueOf(Long.valueOf(curParam));
                        }        
                        if (curParam.toUpperCase().contains("MAXSPECSTRICT")){
                                curParam = curParam.replace("MAXSPECSTRICT", "");       maxSpec = BigDecimal.valueOf(Long.valueOf(curParam));    maxStrict=true;
                        }        
                        if (curParam.toUpperCase().contains("MAXSPEC")){
                                curParam = curParam.replace("MAXSPEC", "");              maxSpec =BigDecimal.valueOf(Long.valueOf(curParam));
                        }        
                    }       
                    Boolean requiresUnitsConversion=true;
                    BigDecimal resultConverted =  null;
                    UnitsOfMeasurement uom = new UnitsOfMeasurement();     
                    if (resultUomName == null ? specUomName == null : resultUomName.equals(specUomName)){requiresUnitsConversion=false;}
                    if (requiresUnitsConversion){
                        Object[] convDiagnoses = uom.convertValue(schemaName, new BigDecimal(resultValue.toString()), resultUomName, specUomName);
                        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(convDiagnoses[0].toString())) {
                            resSpecEvaluation=LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "DataSample_SampleAnalysisResult_ConverterFALSE", new Object[]{limitId.toString(), convDiagnoses[3].toString(), schemaDataName});                  
                        }
                        resultConverted = (BigDecimal) convDiagnoses[1];        
                    }
                    ruleVariables = (String) specLimits[0][2];
                     if (ruleVariables.contains("CONTROL")){
                        if (requiresUnitsConversion){
                            resSpecEvaluation = resChkSpec.resultCheck(resultConverted, minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);
                        }else {
                            resSpecEvaluation = resChkSpec.resultCheck(resultValue, minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);                            
                        }    
                    }else{
                        if (requiresUnitsConversion){
                            resSpecEvaluation = resChkSpec.resultCheck(resultConverted, minSpec, maxSpec, minStrict, maxStrict);
                        }else {
                            resSpecEvaluation = resChkSpec.resultCheck(resultValue, minSpec, maxSpec, minStrict, maxStrict);
                        }    

                    
                }
                fileContentTable1Builder.append(LPTestingOutFormat.rowAddFields(new Object[]{iLines, schemaName, specCode, specCodeVersion, variation, analysis, methodName, methodVersion, parameterName, resultValue, resultUomName}));
                if (numEvaluationArguments>0){                    
                    Object[] evaluate = tstAssert.evaluate(numEvaluationArguments, tstAssertSummary, resSpecEvaluation);
                    if (minControl==null){
                        fileContentTable1Builder.append(LPTestingOutFormat.rowAddFields(evaluate)).append(LPTestingOutFormat.rowEnd());
                    }
                }
            }
        }       
        tstAssertSummary.notifyResults();
        fileContentTable1Builder.append(LPTestingOutFormat.tableEnd());
        String fileContentSummary = LPTestingOutFormat.createSummaryTable(tstAssertSummary);
        fileContentBuilder.append(fileContentSummary).append(fileContentTable1Builder.toString());
        out.println(fileContentBuilder.toString());            
        LPTestingOutFormat.createLogFile(csvPathName, fileContentBuilder.toString());
        tstAssertSummary=null; resChkSpec=null;
        }
        catch(Exception error){
            PrintWriter out = response.getWriter();
            out.println(error.getMessage());
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response){            
        try {
            processRequest(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(TestingQuantitativeLimitAndResult.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try {
            processRequest(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(TestingQuantitativeLimitAndResult.class.getName()).log(Level.SEVERE, null, ex);
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
