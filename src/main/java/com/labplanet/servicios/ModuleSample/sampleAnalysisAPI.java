/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleSample;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPHttp;
import databases.Rdbms;
import databases.Token;
import functionalJava.sampleStructure.DataSample;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

/**
 *
 * @author Administrator
 */
public class sampleAnalysisAPI extends HttpServlet {
    public static final String ERRORMSG_ERROR_STATUS_CODE="Error Status Code";
    public static final String ERRORMSG_MANDATORY_PARAMS_MISSING="API Error Message: There are mandatory params for this API method not being passed";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {
        request=LPHttp.requestPreparation(request);
        response=LPHttp.responsePreparation(response);

        String language = LPFrontEnd.setLanguage(request); 
        String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};   

        String[] mandatoryParams = new String[]{"schemaPrefix"};
        mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "actionName");
        mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "finalToken");
                
        Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParams);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
            errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
            errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
            Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
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
//        String appSessionIdStr = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_APP_SESSION_ID)];
//        String appSessionStartedDate = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_APP_SESSION_STARTED_DATE)];       
        String eSign = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ESIGN)];            
        
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
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
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
        
        if (!LPFrontEnd.servletStablishDBConection(request, response)){return;} 
        
        Connection con = Rdbms.createTransactionWithSavePoint();
        if (con==null){
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The Transaction cannot be created, the action should be aborted");
             return;
        }
        Rdbms.setTransactionId(schemaPrefix);
        //ResponseEntity<String121> responsew;        
    
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */                     

            Object[] actionEnabled = LPPlatform.procActionEnabled(schemaPrefix, actionName);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabled[0].toString())){
                Object[] errMsg = LPFrontEnd.responseError(actionEnabled, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;               
            }            
            actionEnabled = LPPlatform.procUserRoleActionEnabled(schemaPrefix, userRole, actionName);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabled[0].toString())){            
                Object[] errMsg = LPFrontEnd.responseError(actionEnabled, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;                           
            }            
            
            DataSample smp = new DataSample("");            
            Object[] dataSample = null;
            
            switch (actionName.toUpperCase()){
                case "SAMPLEANALYSISADD":
                    String[] mandatoryParamsAction = new String[]{"sampleId"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "fieldName");
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "fieldValue");                    
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    String sampleIdStr = request.getParameter("sampleId");                             
                    Integer sampleId = Integer.parseInt(sampleIdStr);       
                    String[] fieldNameArr = null;
                    Object[] fieldValueArr = null;
                    String fieldName = request.getParameter("fieldName");
                    fieldNameArr = (String[]) fieldName.split("\\|");                                    
                    String fieldValue = request.getParameter("fieldValue");
                    fieldValueArr = (String[]) fieldValue.split("\\|");                        
                    fieldValueArr = LPArray.convertStringWithDataTypeToObjectArray((String[]) fieldValueArr);                    
                    dataSample = smp.sampleAnalysisAddtoSample(schemaPrefix, internalUserID, sampleId, fieldNameArr, fieldValueArr, userRole);                    
                    break;              
                case "REVIEWRESULT":
                    mandatoryParamsAction = new String[]{"objectId"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "objectLevel");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    Integer objectId = 0;
                    Integer resultId = 0;
                    String objectIdStr = request.getParameter("objectId");
                    objectId = Integer.parseInt(objectIdStr);     
                    String objectLevel = request.getParameter("objectLevel");
                    sampleId = null; Integer testId = null; resultId = null;
                    if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                    if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                    if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                    dataSample = smp.sampleResultReview(schemaPrefix, internalUserID, sampleId, testId, resultId, userRole);
                    break;                       
                case "CANCELRESULT":
                    mandatoryParamsAction = new String[]{"objectId"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "objectLevel");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    objectId = 0;
                    objectIdStr = request.getParameter("objectId");
                    objectId = Integer.parseInt(objectIdStr);     
                    objectLevel = request.getParameter("objectLevel");
                        sampleId = null; testId = null; resultId = null;
                        if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultCancel(schemaPrefix, internalUserID, sampleId, testId, resultId, userRole);
                    break;   
                case "UNREVIEWRESULT":   // No break then will take the same logic than the next one  
                case "UNCANCELRESULT":
                    mandatoryParamsAction = new String[]{"objectId"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "objectLevel");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    objectId = 0;
                    objectIdStr = request.getParameter("objectId");
                    objectId = Integer.parseInt(objectIdStr);     
                    objectLevel = request.getParameter("objectLevel");
                        sampleId = null; testId = null; resultId = null;
                        if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultUnCancel(schemaPrefix, internalUserID, sampleId, testId, resultId, userRole);
                    break;       
                case "TESTASSIGNMENT": 
                    mandatoryParamsAction = new String[]{"testId"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "newAnalyst");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    objectIdStr = request.getParameter("testId");
                    testId = Integer.parseInt(objectIdStr);     
                    String newAnalyst = request.getParameter("newAnalyst");
                    try {
                        dataSample = smp.sampleAnalysisAssignAnalyst(schemaPrefix, internalUserID, testId, newAnalyst, userRole);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;                      
                default:      
                    //errObject = frontEnd.APIHandler.actionNotRecognized(errObject, actionName, response);
                    errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                    errObject = LPArray.addValueToArray1D(errObject, "API Error Message: actionName "+actionName+ " not recognized as an action by this API");                                                            
                    Object[] errMsg = LPFrontEnd.responseError(errObject, language, schemaPrefix);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    Rdbms.closeRdbms();                    
                    return;                    
            }    
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(dataSample[0].toString())){  
                Rdbms.rollbackWithSavePoint();
                con.rollback();
                con.setAutoCommit(true);
                Object[] errMsg = LPFrontEnd.responseError(dataSample, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
            }else{
                con.commit();
                con.setAutoCommit(true);
                response.getWriter().write(Arrays.toString(dataSample));      
                Response.ok().build();
            }            
            Rdbms.closeRdbms();
        }catch(Exception e){   
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(sampleAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
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