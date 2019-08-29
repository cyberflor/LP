/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LPFrontEnd;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class authenticationAPIParams extends HttpServlet {

    public static final String API_ENDPOINT_GET_USER_ROLE = "GETUSERROLE";
    public static final String API_ENDPOINT_TOKEN_VALIDATE_USER_CREDENTIALS = "TOKEN_VALIDATE_USER_CREDENTIALS";
    public static final String API_ENDPOINT_FINAL_TOKEN = "FINALTOKEN";
    public static final String API_ENDPOINT_AUTHENTICATE = "AUTHENTICATE";
    public static final String API_ENDPOINT_TOKEN_VALIDATE_ESIGN_PHRASE = "TOKEN_VALIDATE_ESIGN_PHRASE";
        
    public static final String RESPONSE_JSON_TAG_USER_INFO_ID = "userInfoId";
    public static final String RESPONSE_JSON_TAG_FINAL_TOKEN = "finalToken";
    public static final String RESPONSE_JSON_TAG_MY_TOKEN = "myToken";
    public static final String RESPONSE_JSON_TAG_APP_SESSION_ID = "appSessionId";
    public static final String RESPONSE_JSON_TAG_APP_SESSION_DATE = "appSessionStartDate";

    public static final String ERROR_PROPERTY_INVALID_USER_PSSWD = "authenticationAPI_invalidUserPsswd";
    public static final String ERROR_PROPERTY_SESSION_ID_NULL_NOT_ALLOWED = "authenticationAPI_sessionIdNullNotAllowed";
    public static final String ERROR_PROPERTY_ESIGN_INFO_NOT_AVAILABLE = "authenticationAPI_esignInfoNotAvailable";
    public static final String ERROR_PROPERTY_PERSON_NOT_FOUND = "authenticationAPI_personNotFound";
    public static final String ERROR_PROPERTY_SESSION_ID_NOT_GENERATED = "authenticationAPI_sessionIdNotGenerated";
    public static final String ERROR_API_ERRORTRAPING_PROPERTY_USER_PSSWD_TO_CHECK_INVALID = "authenticationAPI_userPsswdToCheckInvalid";
    public static final String ERROR_API_ERRORTRAPING_PROPERTY_ALL_USER_PROCEDURE_PREFIX = "allUserProcedurePrefixReturnedFalse";
    public static final String ERROR_API_ERRORTRAPING_PROPERTY_ESIGN_TO_CHECK_INVALID = "authenticationAPI_esignToCheckInvalid";
    
    public static final String MANDATORY_PARAMS_MAIN_SERVLET = "actionName";
    public static final String MANDATORY_PARAMS_CASE_TOKEN_VALIDATE_ESIGN_PHRASE = "myToken|esignPhraseToCheck";
    public static final String MANDATORY_PARAMS_CASE_AUTHENTICATE = "dbUserName|dbUserPassword";
    public static final String MANDATORY_PARAMS_CASE_FINALTOKEN = "myToken|userRole";
    public static final String MANDATORY_PARAMS_CASE_GETUSERROLE = "myToken";
    public static final String MANDATORY_PARAMS_CASE_TOKEN_VALIDATE_USER_CREDENTIALS = "myToken|userToCheck|passwordToCheck";
   
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet authenticationAPIParams</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet authenticationAPIParams at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
