/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleEnvMonit;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPHttp;
import LabPLANET.utilities.LPPlatform;
import static com.labplanet.servicios.ModuleSample.sampleAPI.ERRORMSG_ERROR_STATUS_CODE;
import static com.labplanet.servicios.ModuleSample.sampleAPI.ERRORMSG_MANDATORY_PARAMS_MISSING;
import static com.labplanet.servicios.ModuleSample.sampleAPI.FIELD_NAME_SAMPLE_ID;
import static com.labplanet.servicios.ModuleSample.sampleAPI.PARAMETER_SAMPLE_ID;
import static com.labplanet.servicios.ModuleSample.sampleAPI.TABLE_NAME_SAMPLE;
import databases.Rdbms;
import databases.Token;
import functionalJava.environmentalMonitoring.DataProgramSample;
import functionalJava.testingScripts.LPTestingOutFormat;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author Administrator
 */
public class envMonAPI extends HttpServlet {
    
    public static final String PARAMETER_PROGRAM_SAMPLE_TEMPLATE="sampleTemplate";
    public static final String PARAMETER_PROGRAM_SAMPLE_TEMPLATE_VERSION="sampleTemplateVersion";       
    public static final String PARAMETER_NUM_SAMPLES_TO_LOG="numSamplesToLog";
    public static final String PARAMETER_PROGRAM_FIELD_NAME="fieldName";
    public static final String PARAMETER_PROGRAM_FIELD_VALUE="fieldValue";    
    public static final String PARAMETER_PROGRAM_SAMPLE_PROGRAM_FIELD="programName"; 
    
    public static final String TABLE_SAMPLE_PROGRAM_FIELD="program"; 
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

        String language = "es";
        String[] errObject = new String[]{"Servlet programAPI at " + request.getServletPath()};   

        String[] mandatoryParams = new String[]{"schemaPrefix"};
        mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "actionName");
        mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "finalToken");
                
        Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParams);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
            errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
            errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
            Object[] errMsg =  LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
            response.sendError((int) errMsg[0], (String) errMsg[1]);                
            return ;                
        }            
        String schemaPrefix = request.getParameter("schemaPrefix");            
        String actionName = request.getParameter("actionName");
        String finalToken = request.getParameter("finalToken");                   
        
        Token token = new Token();
        String[] tokenParams = token.tokenParamsList();
        String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

        String dbUserName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERDB)];
        String dbUserPassword = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERPW)];
        String internalUserID = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_INTERNAL_USERID)];         
        String userRole = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ROLE)];                     
        String appSessionIdStr = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_APP_SESSION_ID)];
//        String appSessionStartedDate = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_APP_SESSION_STARTED_DATE)];       
        String eSign = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ESIGN)];            
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(dbUserName)){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: The token is not valid");                    
                Object[] errMsg =  LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                response.sendError((int) errMsg[0], (String) errMsg[1]);                
                return ;                            
        }
        mandatoryParams = null;                        

        Object[] procActionRequiresUserConfirmation = LPPlatform.procActionRequiresUserConfirmation(schemaPrefix, actionName);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresUserConfirmation[0].toString())){     
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "userToVerify");    
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "passwordToVerify");    
        }

        Object[] procActionRequiresEsignConfirmation = LPPlatform.procActionRequiresEsignConfirmation(schemaPrefix, actionName);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresEsignConfirmation[0].toString())){                                                      
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "eSignToVerify");    
        }        
        if (mandatoryParams!=null){
            areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParams);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+
                        areMandatoryParamsInResponse[1].toString());                    
                Object[] errMsg =  LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                response.sendError((int) errMsg[0], (String) errMsg[1]);                
                return ;                
            }     
        }
        
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresUserConfirmation[0].toString())){    
            String userToVerify = request.getParameter("userToVerify");                   
            String passwordToVerify = request.getParameter("passwordToVerify");    
            if ( (!userToVerify.equalsIgnoreCase(dbUserName)) || (!passwordToVerify.equalsIgnoreCase(dbUserPassword)) ){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: User Verification returned error, the user or the password are not correct.");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, "");
                response.sendError((int) errMsg[0], (String) errMsg[1]);                
                return ;                                
            }
        }
        
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresEsignConfirmation[0].toString())){                                                      
            String eSignToVerify = request.getParameter("eSignToVerify");                   
            if (!eSignToVerify.equalsIgnoreCase(eSign)) {
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: eSign Verification returned error, the value is not correct.");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, "");
                response.sendError((int) errMsg[0], (String) errMsg[1]);                
                return ;                                                
            }
        }
        
            if (Rdbms.getRdbms().startRdbms(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW)==null){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, "");
                response.sendError((int) errMsg[0], (String) errMsg[1]);   
                Rdbms.closeRdbms(); 
                return ;               
            }
            if (!Rdbms.getRdbms().startRdbms(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW)){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, "");
                response.sendError((int) errMsg[0], (String) errMsg[1]);   
                Rdbms.closeRdbms(); 
                return ;               
            }

/*        boolean isConnected = false;
        
        isConnected = Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword);
        if (!isConnected){
            errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
            errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
            Object[] errMsg = LPFrontEnd.responseError(errObject, language, "");
            response.sendError((int) errMsg[0], (String) errMsg[1]);   
            Rdbms.closeRdbms(); 
            return ;               
        }        
*/        
        Connection con = Rdbms.createTransactionWithSavePoint();        
        if (con==null){
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The Transaction cannot be created, the action should be aborted");
             return;
        }
        try {
            con.rollback();
            con.setAutoCommit(true);    
        } catch (SQLException ex) {
            Logger.getLogger(envMonAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
/*        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(sampleAPI.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);    
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);    
        Rdbms.setTransactionId(schemaConfigName);
        //ResponseEntity<String121> responsew;        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */                     

            Object[] actionEnabled = LPPlatform.procActionEnabled(schemaPrefix, actionName);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabled[0].toString())){
                Object[] errMsg = LPFrontEnd.responseError(actionEnabled, language, "");
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;               
            }            
            actionEnabled = LPPlatform.procUserRoleActionEnabled(schemaPrefix, userRole, actionName);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabled[0].toString())){            
                Object[] errMsg = LPFrontEnd.responseError(actionEnabled, language, "");
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;                           
            }            
            
            DataProgramSample prgSmp = new DataProgramSample("");            
            Object[] dataSample = null;
            
            switch (actionName.toUpperCase()){
                case "LOGSAMPLExxxxxxx":
//                    String programName=request.getParameter("programName");
//                    String programLocation=request.getParameter("programLocation");

                    RequestDispatcher rd2 = request.getRequestDispatcher("/moduleSample/sampleAPI");
                    rd2.forward(request,response); 
                    break;     
/*                    String[] mandatoryParamsAction = new String[]{PARAMETER_PROGRAM_SAMPLE_TEMPLATE};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParams, PARAMETER_PROGRAM_SAMPLE_TEMPLATE_VERSION);  
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParams, PARAMETER_PROGRAM_SAMPLE_PROGRAM_FIELD);   
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                   
                    String programName =request.getParameter(PARAMETER_PROGRAM_SAMPLE_PROGRAM_FIELD);
                    String sampleTemplate=request.getParameter(PARAMETER_PROGRAM_SAMPLE_TEMPLATE);
                    String sampleTemplateVersionStr = request.getParameter(PARAMETER_PROGRAM_SAMPLE_TEMPLATE_VERSION);                                  

                    Integer sampleTemplateVersion = Integer.parseInt(sampleTemplateVersionStr);                  
                    String fieldName=request.getParameter(PARAMETER_PROGRAM_FIELD_NAME);                                        
                    String fieldValue=request.getParameter(PARAMETER_PROGRAM_FIELD_VALUE);                    
                    String[] fieldNames=null;
                    Object[] fieldValues=null;
                    if (fieldName!=null) fieldNames = fieldName.split("\\|");                                            
                    if (fieldValue!=null) fieldValues = LPArray.convertStringWithDataTypeToObjectArray(fieldValue.split("\\|"));    
                    
                    fieldNames = LPArray.addValueToArray1D(fieldNames, TABLE_SAMPLE_PROGRAM_FIELD);
                    fieldValues = LPArray.addValueToArray1D(fieldValues, programName);

                    Integer numSamplesToLog = 1;
                    String numSamplesToLogStr=request.getParameter(PARAMETER_NUM_SAMPLES_TO_LOG);    
                    if (numSamplesToLogStr!=null){numSamplesToLog = Integer.parseInt(numSamplesToLogStr);}

                    if (numSamplesToLogStr==null){
                        dataSample = prgSmp.logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, fieldNames, fieldValues, internalUserID, userRole, Integer.parseInt(appSessionIdStr));
                    }else{
                        dataSample = prgSmp.logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, fieldNames, fieldValues, internalUserID, userRole, Integer.parseInt(appSessionIdStr), numSamplesToLog);
                    }
                    break;
*/
                case "GETSAMPLEINFO2":
                    RequestDispatcher rd3 = request.getRequestDispatcher("/moduleSample/sampleAPI");
                    rd3.forward(request,response);         
                    return;
                default:    
                    Rdbms.closeRdbms(); 
                    RequestDispatcher rd = request.getRequestDispatcher("/moduleSample/sampleAPI");
                    rd.forward(request,response);   
                    return;
                    //errObject = frontEnd.APIHandler.actionNotRecognized(errObject, actionName, response);
/*                    errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                    errObject = LPArray.addValueToArray1D(errObject, "API Error Message: actionName "+actionName+ " not recognized as an action by this API");                                                            
                    Object[] errMsg = LPFrontEnd.responseError(errObject, language, schemaPrefix);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    Rdbms.closeRdbms();
                    return;                    */
            }    
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(dataSample[0].toString())){  
                Rdbms.rollbackWithSavePoint();
                // con.rollback();
                // con.setAutoCommit(true);
                
                Object[] errMsg = LPFrontEnd.responseError(dataSample, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
            }else{
                // con.commit();
                // con.setAutoCommit(true);
                
                response.getWriter().write(Arrays.toString(dataSample));      
                Response.ok().build();
            }            
            Rdbms.closeRdbms();
        }catch(Exception e){   
 /*           try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(sampleAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
*/            
            Rdbms.closeRdbms();                   
            errObject = new String[]{e.getMessage()};
            Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
            response.sendError((int) errMsg[0], (String) errMsg[1]);           
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
