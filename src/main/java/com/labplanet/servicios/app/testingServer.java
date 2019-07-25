/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import static com.labplanet.servicios.app.sopUserAPI.ERRORMSG_ERROR_STATUS_CODE;
import databases.Rdbms;
import databases.Token;
import functionalJava.testingScripts.LPTestingOutFormat;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Response;

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet testingServer</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet testingServer at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            String language = "en";
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};          
            boolean isConnected = false;
            //isConnected = Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword);
            isConnected = Rdbms.getRdbms().startRdbms(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW);      
            if (!isConnected){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);  
                Rdbms.closeRdbms(); 
                return ;               
            }              
            if (isConnected){
                out.println("Connected to the db :)");
            }else{
                out.println("NOT Connected to the db :(");
            }
            
            out.println("Before creating the token");
            Token token = new Token();
            String myToken = token.createToken(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW, "3", "Admin", "", "", "");
            out.println("Token created: "+myToken);
            
 //           response.sendError(Response.SC_OK, "SC_OK response");
 //           if (1==1){                return;            }
            response.getWriter().write("Unauthorized response zzzzzzzzzzzz");
            response.setStatus(Response.SC_UNAUTHORIZED);
            //response.sendError(Response.SC_UNAUTHORIZED, "Unauthorized response");
            
            out.println("Reading web text file");
            //String exampleUrl = "https://www.w3.org/TR/PNG/iso_8859-1.txt";
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
            out.println("Test content: "+test);
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
