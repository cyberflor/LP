/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

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
public class globalAPIsParams extends HttpServlet {

    // Por favor, agregar ordenado alfabeticamente para evitar duplicados
    public static final String REQUEST_PARAM_ACTION_NAME = "actionName";
    public static final String REQUEST_PARAM_ADD_SAMPLE_ANALYSIS = "addSampleAnalysis";
    public static final String REQUEST_PARAM_ADD_SAMPLE_ANALYSIS_FIELD_TO_RETRIEVE = "addSampleAnalysisFieldToRetrieve";
    public static final String REQUEST_PARAM_ADD_SAMPLE_ANALYSIS_RESULT = "addSampleAnalysisResult";
    public static final String REQUEST_PARAM_ADD_SAMPLE_ANALYSIS_RESULT_FIELD_TO_RETRIEVE = "addSampleAnalysisResultFieldToRetrieve";    
    
    public static final String REQUEST_PARAM_ALIQUOT_ID = "aliquotId";
    public static final String REQUEST_PARAM_CANCEL_CHANGE_COMMENT = "cancelChangeComment"; 
    public static final String REQUEST_PARAM_CONFIRM_CHANGE_COMMENT = "confirmChangeComment"; 
    
    public static final String REQUEST_PARAM_CUSTODIAN_CANDIDATE = "custodianCandidate";
    
    public static final String REQUEST_PARAM_DB_USERNAME = "dbUserName";
    public static final String REQUEST_PARAM_DB_PASSWORD = "dbUserPassword";    
    public static final String REQUEST_PARAM_ESIGN_TO_CHECK = "esignPhraseToCheck"; 
    public static final String REQUEST_PARAM_FIELD_TO_RETRIEVE = "fieldToRetrieve"; 
    public static final String REQUEST_PARAM_FINAL_TOKEN = "finalToken";    
    public static final String REQUEST_PARAM_MY_TOKEN = "myToken";
    public static final String REQUEST_PARAM_NEW_ANALYST = "newAnalyst";
    public static final String REQUEST_PARAM_NEW_DATE = "newDate";
    public static final String REQUEST_PARAM_NUM_SAMPLES_TO_LOG = "numSamplesToLog";
    public static final String REQUEST_PARAM_OBJECT_ID = "objectId";
    public static final String REQUEST_PARAM_OBJECT_LEVEL = "objectLevel";
    public static final String REQUEST_PARAM_OBJECT_LEVEL_RESULT = "RESULT";
    public static final String REQUEST_PARAM_OBJECT_LEVEL_SAMPLE = "SAMPLE";
    public static final String REQUEST_PARAM_OBJECT_LEVEL_TEST = "TEST";
    public static final String REQUEST_PARAM_PASSWORD_TO_CHECK = "passwordToCheck";
    public static final String REQUEST_PARAM_RAW_VALUE_RESULT = "rawValueResult";
    public static final String REQUEST_PARAM_RESULT_ID = "resultId";
    public static final String REQUEST_PARAM_SAMPLE_COMMENT = "sampleComment";
    public static final String REQUEST_PARAM_SAMPLE_ID = "sampleId";
    public static final String REQUEST_PARAM_SAMPLE_FIELD_NAME = "fieldName";
    public static final String REQUEST_PARAM_SAMPLE_FIELD_VALUE = "fieldValue";
    public static final String REQUEST_PARAM_SAMPLE_FIELD_TO_RETRIEVE = "sampleFieldToRetrieve";
    public static final String REQUEST_PARAM_SAMPLE_LAST_LEVEL = "sampleLastLevel";
    public static final String REQUEST_PARAM_SAMPLE_TEMPLATE = "sampleTemplate";
    public static final String REQUEST_PARAM_SAMPLE_TEMPLATE_VERSION = "sampleTemplateVersion";
    public static final String REQUEST_PARAM_SAMPLE_ANALYSIS_FIELD_TO_RETRIEVE = "sampleAnalysisFieldToRetrieve";
    public static final String REQUEST_PARAM_SAMPLE_ANALYSIS_RESULT_FIELD_TO_RETRIEVE = "sampleAnalysisResultFieldToRetrieve";
    public static final String REQUEST_PARAM_SORT_FIELDS_NAME = "sortFieldsName";
    
    public static final String REQUEST_PARAM_SCHEMA_PREFIX = "schemaPrefix";    
    public static final String REQUEST_PARAM_TEST_ID = "testId";
    public static final String REQUEST_PARAM_TEST_FIELD_TO_RETRIEVE = "testFieldToRetrieve";
    public static final String REQUEST_PARAM_USER_ROLE = "userRole";
    public static final String REQUEST_PARAM_USER_TO_CHECK = "userToCheck";
    public static final String REQUEST_PARAM_USER_INFO = "userInfoId";
    public static final String REQUEST_PARAM_WHERE_FIELDS_NAME = "whereFieldsName"; 
    public static final String REQUEST_PARAM_WHERE_FIELDS_VALUE = "whereFieldsValue";
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
            out.println("<title>Servlet globalAPIsParams</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet globalAPIsParams at " + request.getContextPath() + "</h1>");
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
