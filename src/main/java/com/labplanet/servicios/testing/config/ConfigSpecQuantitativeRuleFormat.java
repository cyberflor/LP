/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;

import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LabPLANETArray;
import functionalJava.testingScripts.LPTestingOutFormat;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import functionalJava.materialSpec.ConfigSpecRule;
import functionalJava.testingScripts.TestingAssert;
import functionalJava.testingScripts.TestingAssertSummary;
import java.util.Arrays;
import java.util.HashMap;
/**
 *
 * @author Administrator
 */
public class ConfigSpecQuantitativeRuleFormat extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {
        ConfigSpecRule mSpec = new ConfigSpecRule();
        
        TestingAssertSummary tstAssertSummary = new TestingAssertSummary();

        String csvFileName = "Config_SpecQuantitativeRuleGeneratorChecker.txt"; 
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
                String schemaName = "";
                    
                TestingAssert tstAssert = new TestingAssert(csvFileContent[iLines], numEvaluationArguments);
                
                if (csvFileContent[iLines][0]==null){tstAssertSummary.increasetotalLabPlanetBooleanUndefined();}
                if (csvFileContent[iLines][1]==null){tstAssertSummary.increasetotalLabPlanetErrorCodeUndefined();}
                
                Float minSpec = LPTestingOutFormat.csvExtractFieldValueFloat(csvFileContent[iLines][numEvaluationArguments]);
                Float minControl = LPTestingOutFormat.csvExtractFieldValueFloat(csvFileContent[iLines][numEvaluationArguments+1]);
                Float maxControl = LPTestingOutFormat.csvExtractFieldValueFloat(csvFileContent[iLines][numEvaluationArguments+2]);
                Float maxSpec = LPTestingOutFormat.csvExtractFieldValueFloat(csvFileContent[iLines][numEvaluationArguments+3]);
                    
                Object[] resSpecEvaluation = new Object[0];                
                if (minControl==null){
                    fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                            new Object[]{iLines, minSpec, maxSpec});
                    resSpecEvaluation = mSpec.specLimitIsCorrectQuantitative(minSpec,maxSpec, minControl, maxControl);
                }else{
                    fileContentTable2=fileContentTable2+LPTestingOutFormat.rowAddFields(
                            new Object[]{iLines, minSpec, minControl, maxControl, maxSpec});
                    resSpecEvaluation = mSpec.specLimitIsCorrectQuantitative(minSpec,maxSpec, minControl, maxControl);
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
                        fileContentTable2=fileContentTable2+LPTestingOutFormat.rowEnd();                                                                        
                    }
                }
            }    
            tstAssertSummary.notifyResults();
            fileContentTable1 = fileContentTable1 +LPTestingOutFormat.tableEnd();
            fileContentTable2 = fileContentTable2 +LPTestingOutFormat.tableEnd();         
            if (numEvaluationArguments>0){
                String fileContentSummary = LPTestingOutFormat.CreateSummaryTable(tstAssertSummary);
                fileContent=fileContent+fileContentSummary+fileContentTable1+fileContentTable2;
            }
            fileContent=fileContent+LPTestingOutFormat.bodyEnd()+LPTestingOutFormat.htmlEnd();
            out.println(fileContent);            
            LPTestingOutFormat.createLogFile(csvPathName, fileContent);
            tstAssertSummary=null; mSpec=null;
        }
        catch(IOException error){
            tstAssertSummary=null; mSpec=null;
        }        
    }
/*
        String csvFileName = "Config_SpecQuantitativeRuleGeneratorChecker.txt"; 

        response = LPTestingOutFormat.responsePreparation(response);        
        String fileContent = "";                         
        try (PrintWriter out = response.getWriter()) {
            String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
            String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;
            
            ConfigSpecRule mSpec = new ConfigSpecRule();
            
            Integer numTesting = 30;
            Integer inumTesting = 0;
            Float[][] QuantitSpecTestingArray = new Float[numTesting][4];
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-2.51");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-2.4999");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-2.51");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-2.4999");
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("4.9");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("4.9");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("5.00000");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("5.00001");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-4.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-1.112");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-4.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-1.112");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-4.444449");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-1.1111118");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-4.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-1.112");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-4.500001");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-1.1111118");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-4.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-1.1122");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-4.444449");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-1.1124");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-4.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-1.1122");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-4.444449");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-1.1111");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("4");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("3");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("4");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("3");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("2.9");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("5.1");
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("5");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-2.5001");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-2.4999");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("6");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-6");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-5.0");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5.1");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-5.1");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5.009");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5.009");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}
            
//            String fileName = "C:\\home\\judas\\"+this.getServletName()+".txt";             
//            LabPLANETArray labArr = new LabPLANETArray();
            
//            String[] QuantitSpecTestingArrayStr = Arrays.copyOf(QuantitSpecTestingArray, QuantitSpecTestingArray.length, String[].class);            
//            labArr.arrayToFile(null, QuantitSpecTestingArrayStr, fileName, ";");
    
//    if (1==1){return;}
    
           
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestingQuantitativeSpecifications</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestingQuantitativeSpecifications at " + request.getContextPath() + "</h1>");            
            out.println("<table>");
            out.println("<th>Min Spec</th><th>Max Spec</th><th>Evaluation</th>");
            for (Integer i=0;i<QuantitSpecTestingArray.length;i++){
                if (QuantitSpecTestingArray[i][2]==null && QuantitSpecTestingArray[i][3]==null){
                    out.println("<tr>");
                    String minSpecText = "";
                    String maxSpecText = "";
                    if (QuantitSpecTestingArray[i][0]!=null){minSpecText = QuantitSpecTestingArray[i][0].toString();}
                    if (QuantitSpecTestingArray[i][1]!=null){maxSpecText = QuantitSpecTestingArray[i][1].toString();}               
                
                    out.println("<td>"+minSpecText+"</td><td>"+maxSpecText+"</td>");
                    Object[] isCorrect = mSpec.specLimitIsCorrectQuantitative(QuantitSpecTestingArray[i][0], QuantitSpecTestingArray[i][1]);
                    out.println("<td>"+Arrays.toString(isCorrect)+"</td>");
                    out.println("</tr>");
                }
            }                
            out.println("</table>");

            out.println("<table>");
            out.println("<th>Min Spec</th><th>Min Control</th><th>Max Control</th><th>Max Spec</th><th>Evaluation</th>");
            for (Integer i=0;i<QuantitSpecTestingArray.length;i++){
                out.println("<tr>");
                String minSpecText = "";
                if (QuantitSpecTestingArray[i][0]!=null){minSpecText = QuantitSpecTestingArray[i][0].toString();}
                String minControlText = "";
                if (QuantitSpecTestingArray[i][2]!=null){minControlText = QuantitSpecTestingArray[i][2].toString();}
                
                String maxSpecText = "";                
                if (QuantitSpecTestingArray[i][1]!=null){maxSpecText = QuantitSpecTestingArray[i][1].toString();}               
                String maxControlText = "";                
                if (QuantitSpecTestingArray[i][3]!=null){maxControlText = QuantitSpecTestingArray[i][3].toString();}               
                
                out.println("<td>"+minSpecText+"</td><td>"+minControlText+"</td>");
                out.println("<td>"+maxControlText+"</td><td>"+maxSpecText+"</td>");
                Object[] isCorrect = mSpec.specLimitIsCorrectQuantitative(QuantitSpecTestingArray[i][0], QuantitSpecTestingArray[i][1],QuantitSpecTestingArray[i][2],QuantitSpecTestingArray[i][3]);
                out.println("<td>"+Arrays.toString(isCorrect)+"</td>");
                out.println("</tr>");
            }                
            out.println("</table>");

            out.println("</body>");
            out.println("</html>");
        }
    }
*/
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
