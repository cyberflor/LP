/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPHttp;
import LabPLANET.utilities.LPMath;
import databases.Rdbms;
import databases.Token;
import databases.dbObjectsAppTables.SchemaApppTableAppSession;
import functionalJava.parameter.Parameter;
import functionalJava.sampleStructure.DataSampleUtilities;
import functionalJava.testingScripts.LPTestingOutFormat;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class testingServer extends HttpServlet {

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
        response=LPHttp.responsePreparation(response);

        String language = LPFrontEnd.setLanguage(request);         
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet testingServer</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet testingServer at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            
            out.println("Testing Files Path: "+LPTestingOutFormat.TESTING_FILES_PATH);
            out.println("Adios");
            String procName="process-us";
            out.println("Extract portion, extraer 5 sobre una cantidad de 4: "+Arrays.toString(LPMath.extractPortion(procName, 
                    BigDecimal.valueOf(4), "MG",1, BigDecimal.valueOf(5), "MG", 2)));
            out.println("Extract portion, extraer 4 sobre una cantidad de 4: "+Arrays.toString(LPMath.extractPortion(procName, 
                    BigDecimal.valueOf(4), "MG",1, BigDecimal.valueOf(4), "MG", 2)));
            
            out.println("Statuses en inglés: "+Arrays.toString(DataSampleUtilities.getSchemaSampleStatusList(procName)));
            out.println("Statuses en castellano: "+Arrays.toString(DataSampleUtilities.getSchemaSampleStatusList(procName, "es")));
            
            out.println("First sample analysis status for process-us is: "+Parameter.getParameterBundle("process-us-data", "sampleAnalysis_statusFirst"));
            
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
            if (isConnected){out.println("Connected to the db :)");
            }else{out.println("NOT Connected to the db :(");}
            
            Object[] firstArray=new Object[]{"A", "B"};
            Object[] secondArray=new Object[]{"1", "2", 3};
                String[] myJoinedArray=LPArray.joinTwo1DArraysInOneOf1DString(firstArray, secondArray, ":");
                out.println("joining two arrays of "+Arrays.toString(firstArray)+" and "+Arrays.toString(secondArray)+" with the separator "+":"+" I obtained "+Arrays.toString(myJoinedArray));
            firstArray=new Object[]{"A", "B","c", "Z"};
                myJoinedArray=LPArray.joinTwo1DArraysInOneOf1DString(firstArray, secondArray, ":");
                out.println("joining two arrays of "+Arrays.toString(firstArray)+" and "+Arrays.toString(secondArray)+" with the separator "+":"+" I obtained "+Arrays.toString(myJoinedArray));
            
            out.println("The name for the table Session in db is "+ SchemaApppTableAppSession.valueOf("TABLE_NAME").getName());
            out.println("The name for the field Session_id in db is "+SchemaApppTableAppSession.valueOf("FIELD_SESSION_ID").getName());
            
            //String appSessionTbl =Enum.valueOf(dbObjectsAppTables.SchemaApppTableAppSession, TABLE_NAME); 
            
            out.println("Before creating the token");
            Token token = new Token("");
            String myToken = token.createToken(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW, "3", "Admin", "", "", "");
            out.println("Token created: "+myToken);
            
            out.println("Reading web text file");
            String exampleUrl = "http://51.75.202.142:8888/myfiles/txtfile.txt";
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
            out.println("Test File Content: <br>"+test);
            
        String csvFileName = "noDBSchema_config_SpecQualitativeRuleGeneratorChecker.txt"; 
                             
        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
        String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;
        
        Object[][] csvFileContent = LPArray.convertCSVinArray(csvPathName, csvFileSeparator);
        out.println("csv File Content: <br>"+LPTestingOutFormat.convertArrayInHtmlTable(csvFileContent));
        
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
