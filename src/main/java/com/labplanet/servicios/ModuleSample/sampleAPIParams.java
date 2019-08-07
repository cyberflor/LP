/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleSample;

import LabPLANET.utilities.LPFrontEnd;
import com.labplanet.servicios.app.globalAPIsParams;
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
public class sampleAPIParams extends HttpServlet {

    public static final String API_ENDPOINT_INCUBATIONEND = "INCUBATIONEND";
    public static final String API_ENDPOINT_ENTERRESULT = "ENTERRESULT";
    public static final String API_ENDPOINT_UNREVIEWRESULT = "UNREVIEWRESULT";
    public static final String API_ENDPOINT_INCUBATIONSTART = "INCUBATIONSTART";
    public static final String API_ENDPOINT_SAMPLINGCOMMENTREMOVE = "SAMPLINGCOMMENTREMOVE";
    public static final String API_ENDPOINT_LOGSAMPLE = "LOGSAMPLE";
    public static final String API_ENDPOINT_UNCANCELRESULT = "UNCANCELRESULT";
    public static final String API_ENDPOINT_TESTASSIGNMENT = "ESTASSIGNMENT";
    public static final String API_ENDPOINT_COC_ABORTCHANGE = "COC_ABORTCHANGE";
    public static final String API_ENDPOINT_SAMPLINGCOMMENTADD = "SAMPLINGCOMMENTADD";
    public static final String API_ENDPOINT_SAMPLEANALYSISADD = "SAMPLEANALYSISADD";
    public static final String API_ENDPOINT_COC_STARTCHANGE = "COC_STARTCHANGE";
    public static final String API_ENDPOINT_CHANGESAMPLINGDATE = "CHANGESAMPLINGDATE";
    public static final String API_ENDPOINT_RECEIVESAMPLE = "RECEIVESAMPLE";
    public static final String API_ENDPOINT_LOGSUBALIQUOT = "LOGSUBALIQUOT";
    public static final String API_ENDPOINT_LOGALIQUOT = "LOGALIQUOT";
    public static final String API_ENDPOINT_CANCELRESULT = "CANCELRESULT";
    public static final String API_ENDPOINT_COC_CONFIRMCHANGE = "COC_CONFIRMCHANGE";
    public static final String API_ENDPOINT_REVIEWRESULT = "REVIEWRESULT";
    public static final String API_ENDPOINT_GETSAMPLEINFO = "GETSAMPLEINFO";

    public static final String MANDATORY_PARAMS_MAIN_SERVLET = "actionName|finalToken|schemaPrefix";
    public static final String MANDATORY_PARAMS_CASE_TOKEN_VALIDATE_ESIGN_PHRASE = "myToken|esignPhraseToCheck";
    public static final String MANDATORY_PARAMS_LOGSAMPLE="sampleTemplate|sampleTemplateVersion";
    public static final String MANDATORY_PARAMS_RECEIVESAMPLE="sampleId";
    public static final String MANDATORY_PARAMS_CHANGESAMPLINGDATE="sampleId|newDate";  
    public static final String MANDATORY_PARAMS_SAMPLINGCOMMENTADD="sampleId|sampleComment";  
    public static final String MANDATORY_PARAMS_SAMPLINGCOMMENTREMOVE="sampleId";  
    public static final String MANDATORY_PARAMS_INCUBATIONSTART="sampleId";  
    public static final String MANDATORY_PARAMS_INCUBATIONEND="sampleId";  
    public static final String MANDATORY_PARAMS_SAMPLEANALYSISADD="sampleId|fieldName|fieldValue";  
    public static final String MANDATORY_PARAMS_ENTERRESULT="sampleId|rawValueResult"; 
    public static final String MANDATORY_PARAMS_REVIEWRESULT="objectId|objectLevel"; 
    public static final String MANDATORY_PARAMS_CANCELRESULT="objectId|objectLevel"; 
    public static final String MANDATORY_PARAMS_UNREVIEWRESULT="objectId|objectLevel"; 
    public static final String MANDATORY_PARAMS_UNCANCELRESULT="objectId|objectLevel"; 
    public static final String MANDATORY_PARAMS_TESTASSIGNMENT="testId|newAnalyst"; 
    public static final String MANDATORY_PARAMS_GETSAMPLEINFO="sampleId|sampleFieldToRetrieve"; 
    public static final String MANDATORY_PARAMS_COC_STARTCHANGE="sampleId|custodianCandidate"; 
    public static final String MANDATORY_PARAMS_COC_CONFIRMCHANGE="sampleId|confirmChangeComment"; 
    public static final String MANDATORY_PARAMS_COC_ABORTCHANGE="sampleId|cancelChangeComment"; 
    public static final String MANDATORY_PARAMS_LOGALIQUOT="sampleId"; 
    public static final String MANDATORY_PARAMS_LOGSUBALIQUOT="aliquotId"; 
    
    public static final String MANDATORY_PARAMS_FRONTEND_UNRECEIVESAMPLES_LIST="sortFieldsName|sampleFieldToRetrieve"; 
    public static final String MANDATORY_PARAMS_FRONTEND_UNRECEIVESAMPLES_LIST_SORT_FIELDS_NAME_DEFAULT_VALUE="";
    public static final String MANDATORY_PARAMS_FRONTEND_SAMPLES_INPROGRESS_LIST_SAMPLE_ANALYSIS_FIELD_RETRIEVE_DEFAULT_VALUE="test_id|status|analysis|method_name|method_version";
    public static final String MANDATORY_PARAMS_FRONTEND_SAMPLES_INPROGRESS_LIST_SAMPLE_ANALYSIS_RESULT_FIELD_RETRIEVE_DEFAULT_VALUE="result_id|status|param_name|raw_value|pretty_value";
    public static final String MANDATORY_PARAMS_FRONTEND_UNRECEIVESAMPLES_LIST_SAMPLE_FIELD_RETRIEVE_DEFAULT_VALUE="sample_id";
    public static final String MANDATORY_PARAMS_FRONTEND_GET_SAMPLE_ANALYSIS_LIST=globalAPIsParams.REQUEST_PARAM_SAMPLE_ID; 
    public static final String MANDATORY_PARAMS_FRONTEND_GET_SAMPLE_ANALYSIS_RESULT_LIST="sampleId"; 
    public static final String MANDATORY_PARAMS_FRONTEND_CHANGEOFCUSTODY_SAMPLE_HISTORY="sampleId"; 
    public static final String MANDATORY_PARAMS_FRONTEND_GET_SAMPLE_ANALYSIS_RESULT_SPEC="resultId"; 
    
    public static final String MANDATORY_FIELDS_FRONTEND_TO_RETRIEVE_ANALYSIS_ALL_LIST="code|method_name|method_version"; 
    public static final String MANDATORY_FIELDS_FRONTEND_TO_RETRIEVE_GET_SAMPLE_ANALYSIS_LIST="sample_id|test_id"; 
    public static final String MANDATORY_FIELDS_FRONTEND_TO_RETRIEVE_GET_SAMPLE_ANALYSIS_RESULT_LIST="sample_id|test_id|result_id|param_name"; 
    public static final String MANDATORY_FIELDS_FRONTEND_TO_RETRIEVE_CHANGEOFCUSTODY_SAMPLE_HISTORY="sample_id|custodian|custodian_name|custodian_candidate|candidate_name|coc_started_on|status|coc_confirmed_on"; 
    public static final String MANDATORY_FIELDS_FRONTEND_TO_RETRIEVE_CHANGEOFCUSTODY_USERS_LIST="user_name|person_name"; 
    
    public static final String MANDATORY_FIELDS_FRONTEND_WHEN_SORT_NULL_GET_SAMPLE_ANALYSIS_LIST="sample_id|test_id"; 
    public static final String MANDATORY_FIELDS_FRONTEND_WHEN_SORT_NULL_GET_SAMPLE_ANALYSIS_RESULT_LIST="sample_id|test_id|result_id"; 
    public static final String MANDATORY_FIELDS_FRONTEND_WHEN_SORT_NULL_CHANGEOFCUSTODY_SAMPLE_HISTORY="sample_id|coc_started_on"; 
    public static final String MANDATORY_FIELDS_FRONTEND_WHEN_SORT_NULL_CHANGEOFCUSTODY_USERS_LIST="user_name|person_name"; 
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
            out.println("<title>Servlet sampleAPIParams</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet sampleAPIParams at " + request.getContextPath() + "</h1>");
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
