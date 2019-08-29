/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import lbplanet.utilities.LPArray;
import lbplanet.utilities.LPFrontEnd;
import lbplanet.utilities.LPHttp;
import lbplanet.utilities.LPMath;
import lbplanet.utilities.LPPlatform;
import databases.Rdbms;
import databases.Token;
import databases.DbObjectsAppTables.SchemaApppTableAppSession;
import functionaljavaa.parameter.Parameter;
import functionaljavaa.samplestructure.DataSampleUtilities;
import functionaljavaa.testingscripts.LPTestingOutFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
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
public class TestingServer extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request=LPHttp.requestPreparation(request);
        response = LPTestingOutFormat.responsePreparation(response);     

        String language = LPFrontEnd.setLanguage(request);         
        StringBuilder fileContentBuilder = new StringBuilder();
        String procName="process-us";
        
        String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};          
        boolean isConnected = false;
        //isConnected = Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword);
        isConnected = Rdbms.getRdbms().startRdbms(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW);      
        if (!isConnected){
            errObject = LPArray.addValueToArray1D(errObject, HttpServletResponse.SC_UNAUTHORIZED+": "+HttpServletResponse.SC_BAD_REQUEST);
            errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
            Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
            response.sendError((int) errMsg[0], (String) errMsg[1]);  
            Rdbms.closeRdbms(); 
            return ;               
        }  
        Token token = new Token("");
        String myToken = token.createToken(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW, "3", "Admin", "", "", "");        
        
        
        try (PrintWriter out = response.getWriter()) {
            fileContentBuilder.append(LPTestingOutFormat.getHtmlStyleHeader(this.getClass().getSimpleName()));
            fileContentBuilder.append(LPTestingOutFormat.tableEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableStart());
                fileContentBuilder.append(LPTestingOutFormat.headerStart()).append(LPTestingOutFormat.headerAddField("Algunos parámetros")).append(LPTestingOutFormat.headerEnd());
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField("Current Testing Files Path is "+LPTestingOutFormat.TESTING_FILES_PATH)).append(LPTestingOutFormat.rowEnd());
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField("Current Process is "+procName)).append(LPTestingOutFormat.rowEnd());
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField("Connection to the database stablished? "+isConnected)).append(LPTestingOutFormat.rowEnd());
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField("Token created: "+myToken)).append(LPTestingOutFormat.rowEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableStart());
                fileContentBuilder.append(LPTestingOutFormat.headerStart()).append(LPTestingOutFormat.headerAddField("Testeando la extracción de volumen")).append(LPTestingOutFormat.headerEnd());
                String msgStr="Extract portion, extraer 5 sobre una cantidad de 4: "+Arrays.toString(LPMath.extractPortion(procName, 
                            BigDecimal.valueOf(4), "MG",1, BigDecimal.valueOf(5), "MG", 2));
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());

                msgStr="Extract portion, extraer 4 sobre una cantidad de 4: "+Arrays.toString(LPMath.extractPortion(procName, 
                            BigDecimal.valueOf(4), "MG",1, BigDecimal.valueOf(4), "MG", 2));
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableStart());
                fileContentBuilder.append(LPTestingOutFormat.headerStart()).append(LPTestingOutFormat.headerAddField("Testings About getParameterBundler")).append(LPTestingOutFormat.headerEnd());                        
                msgStr="Statuses en inglés: "+Arrays.toString(DataSampleUtilities.getSchemaSampleStatusList(procName));
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
                msgStr="Statuses en castellano: "+Arrays.toString(DataSampleUtilities.getSchemaSampleStatusList(procName, "es"));     
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());                
                msgStr="First sample analysis status for process-us is: "+Parameter.getParameterBundle("process-us-data", "sampleAnalysis_statusFirst");
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableStart());
            fileContentBuilder.append(LPTestingOutFormat.headerStart()).append(LPTestingOutFormat.headerAddField("Testings About encrypting")).append(LPTestingOutFormat.headerEnd());
                String fieldName="status";
                String fieldValue="RECEIVED";
                HashMap<String, String> hmEncryptOverride = LPPlatform.encryptEncryptableFieldsOverride(fieldName, fieldValue);    
                    String query= hmEncryptOverride.keySet().iterator().next();   
                    String encryptedValueOverride = hmEncryptOverride.get(query);            
                msgStr="The value "+fieldValue+" encrypted is"+encryptedValueOverride+" by overriding.";
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
                Object[] decryptingEncryptedValue=LPPlatform.decryptString(encryptedValueOverride);
                msgStr="The encrypted value "+encryptedValueOverride+" is decrypted as "+Arrays.toString(decryptingEncryptedValue);
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
                HashMap<String, String> hmEncryptAddingBoth = LPPlatform.encryptEncryptableFieldsAddBoth(fieldName, fieldValue);          
                    query= hmEncryptAddingBoth.keySet().iterator().next();   
                    String encryptedValueAddingBoth = hmEncryptAddingBoth.get(query);            
                msgStr="The value "+fieldValue+" encrypted is"+encryptedValueAddingBoth+" by adding both.";
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
                decryptingEncryptedValue=LPPlatform.decryptString(encryptedValueAddingBoth);
                msgStr="The encrypted value "+encryptedValueAddingBoth+" is decrypted as "+Arrays.toString(decryptingEncryptedValue);
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableStart());
               fileContentBuilder.append(LPTestingOutFormat.headerStart()).append(LPTestingOutFormat.headerAddField("Playing with arrays")).append(LPTestingOutFormat.headerEnd());            
                Object[] firstArray=new Object[]{"A", "B"};
                Object[] secondArray=new Object[]{"1", "2", 3};
                String[] myJoinedArray=LPArray.joinTwo1DArraysInOneOf1DString(firstArray, secondArray, ":");
                msgStr="joining two arrays of "+Arrays.toString(firstArray)+" and "+Arrays.toString(secondArray)+" with the separator "+":"+" I obtained "+Arrays.toString(myJoinedArray);
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
            firstArray=new Object[]{"A", "B","c", "Z"};
                myJoinedArray=LPArray.joinTwo1DArraysInOneOf1DString(firstArray, secondArray, ":");
                msgStr="joining two arrays of "+Arrays.toString(firstArray)+" and "+Arrays.toString(secondArray)+" with the separator "+":"+" I obtained "+Arrays.toString(myJoinedArray);
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableStart());
                fileContentBuilder.append(LPTestingOutFormat.headerStart()).append(LPTestingOutFormat.headerAddField("Table Enums")).append(LPTestingOutFormat.headerEnd());                   
                msgStr="The name for the table Session in db is "+ SchemaApppTableAppSession.valueOf("TABLE_NAME").getName();
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
                msgStr="The name for the field Session_id in db is "+SchemaApppTableAppSession.valueOf("FIELD_SESSION_ID").getName();
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableStart());            
                fileContentBuilder.append(LPTestingOutFormat.headerStart()).append(LPTestingOutFormat.headerAddField("Reading files")).append(LPTestingOutFormat.headerEnd());                   
                    String exampleUrl = "http://51.75.202.142:8888/myfiles/txtfile.txt";
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField("Web txt file name: "+exampleUrl));
                    final URL url = new URL(exampleUrl);
                    final StringBuilder sb = new StringBuilder();
                    final char[] buf = new char[4096];
                    final CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
                            .onMalformedInput(CodingErrorAction.REPORT);
                    try (
                        final InputStream in = url.openStream();
                        final InputStreamReader reader = new InputStreamReader(in, decoder);
                    ) {
                        int nrChars;
                        while ((nrChars = reader.read(buf)) != -1)
                            sb.append(buf, 0, nrChars);
                    }
                    final String test = sb.toString();
                    msgStr=test;
                fileContentBuilder.append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
                    String csvFileName = "noDBSchema_config_specQuantitative_resultCheck.txt";                              
                fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddField("CSVF File name: "+csvFileName));
                    String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
                    String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;
        
                    Object[][] csvFileContent = LPArray.convertCSVinArray(csvPathName, csvFileSeparator);
                    msgStr=LPTestingOutFormat.convertArrayInHtmlTable(csvFileContent);
                fileContentBuilder.append(LPTestingOutFormat.rowAddField(msgStr)).append(LPTestingOutFormat.rowEnd());
            fileContentBuilder.append(LPTestingOutFormat.tableEnd());
            
        fileContentBuilder.append(LPTestingOutFormat.htmlEnd());
        out.println(fileContentBuilder.toString()); 

        
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
