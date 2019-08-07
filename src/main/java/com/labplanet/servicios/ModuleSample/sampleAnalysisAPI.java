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
import com.labplanet.servicios.app.globalAPIsParams;
import databases.Rdbms;
import databases.Token;
import functionalJava.sampleStructure.DataSample;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
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
public class sampleAnalysisAPI extends HttpServlet {
    public static final String MANDATORY_PARAMS_MAIN_SERVLET="actionName|finalToken|schemaPrefix";

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

        String[] mandatoryParams = new String[]{""};
        Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, MANDATORY_PARAMS_MAIN_SERVLET.split("\\|"));                       
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
            LPFrontEnd.servletReturnResponseError(request, response, 
                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
            return;          
        }             
        String schemaPrefix = request.getParameter(globalAPIsParams.REQUEST_PARAM_SCHEMA_PREFIX);            
        String actionName = request.getParameter(globalAPIsParams.REQUEST_PARAM_ACTION_NAME);
        String finalToken = request.getParameter(globalAPIsParams.REQUEST_PARAM_FINAL_TOKEN);                   

        
        Token token = new Token(finalToken);
        
        mandatoryParams = null;                        

        Object[] procActionRequiresUserConfirmation = LPPlatform.procActionRequiresUserConfirmation(schemaPrefix, actionName);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresUserConfirmation[0].toString())){     
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, globalAPIsParams.REQUEST_PARAM_USER_TO_CHECK);    
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, globalAPIsParams.REQUEST_PARAM_PASSWORD_TO_CHECK);    
        }
        Object[] procActionRequiresEsignConfirmation = LPPlatform.procActionRequiresEsignConfirmation(schemaPrefix, actionName);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresEsignConfirmation[0].toString())){                                                      
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, globalAPIsParams.REQUEST_PARAM_ESIGN_TO_CHECK);    
        }    
        if (mandatoryParams!=null){
            areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParams);                       
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                LPFrontEnd.servletReturnResponseError(request, response, 
                    LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                return;          
            }             
        }
        
        if ( (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresUserConfirmation[0].toString())) &&     
             (!LPFrontEnd.servletUserToVerify(request, response, procActionRequiresUserConfirmation, token.getUserName(), token.getUsrPw())) ){return;}

        if ( (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresEsignConfirmation[0].toString())) &&    
             (!LPFrontEnd.servletEsignToVerify(request, response, procActionRequiresUserConfirmation, token.geteSign())) ){return;}
        if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}     
        
        if (!LPFrontEnd.servletStablishDBConection(request, response)){return;} 
        
        Connection con = Rdbms.createTransactionWithSavePoint();
        if (con==null){
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The Transaction cannot be created, the action should be aborted");
             return;
        }
        Rdbms.setTransactionId(schemaPrefix);
        //ResponseEntity<String121> responsew;        
    
        try (PrintWriter out = response.getWriter()) {
            Object[] actionEnabled = LPPlatform.procActionEnabled(schemaPrefix, actionName);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabled[0].toString())){
                Object[] errMsg = LPFrontEnd.responseError(actionEnabled, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;               
            }            
            actionEnabled = LPPlatform.procUserRoleActionEnabled(schemaPrefix, token.getUserRole(), actionName);
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
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
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
                    dataSample = smp.sampleAnalysisAddtoSample(schemaPrefix, token.getPersonName(), sampleId, fieldNameArr, fieldValueArr, token.getUserRole());                    
                    break;              
                case "REVIEWRESULT":
                    mandatoryParamsAction = new String[]{"objectId"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "objectLevel");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
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
                    dataSample = smp.sampleResultReview(schemaPrefix, token.getPersonName(), sampleId, testId, resultId, token.getUserRole());
                    break;                       
                case "CANCELRESULT":
                    mandatoryParamsAction = new String[]{"objectId"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "objectLevel");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }     
                    objectId = 0;
                    objectIdStr = request.getParameter("objectId");
                    objectId = Integer.parseInt(objectIdStr);     
                    objectLevel = request.getParameter("objectLevel");
                        sampleId = null; testId = null; resultId = null;
                        if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultCancel(schemaPrefix, token.getPersonName(), sampleId, testId, resultId, token.getUserRole());
                    break;   
                case "UNREVIEWRESULT":   // No break then will take the same logic than the next one  
                case "UNCANCELRESULT":
                    mandatoryParamsAction = new String[]{"objectId"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "objectLevel");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }     
                    objectId = 0;
                    objectIdStr = request.getParameter("objectId");
                    objectId = Integer.parseInt(objectIdStr);     
                    objectLevel = request.getParameter("objectLevel");
                        sampleId = null; testId = null; resultId = null;
                        if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultUnCancel(schemaPrefix, token.getPersonName(), sampleId, testId, resultId, token.getUserRole());
                    break;       
                case "TESTASSIGNMENT": 
                    mandatoryParamsAction = new String[]{"testId"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "newAnalyst");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }     
                    objectIdStr = request.getParameter("testId");
                    testId = Integer.parseInt(objectIdStr);     
                    String newAnalyst = request.getParameter("newAnalyst");
                    try {
                        dataSample = smp.sampleAnalysisAssignAnalyst(schemaPrefix, token.getPersonName(), testId, newAnalyst, token.getUserRole());
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;                      
                default:      
                    LPFrontEnd.servletReturnResponseError(request, response, LPPlatform.API_ERRORTRAPING_PROPERTY_ENDPOINT_NOT_FOUND, new Object[]{actionName, this.getServletName()}, language);              
                    return;                    
            }    
            if ( (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresUserConfirmation[0].toString())) &&     
                 (!LPFrontEnd.servletUserToVerify(request, response, procActionRequiresUserConfirmation, token.getUserName(), token.getUsrPw())) ){return;}

            if ( (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresEsignConfirmation[0].toString())) &&    
                 (!LPFrontEnd.servletEsignToVerify(request, response, procActionRequiresUserConfirmation, token.geteSign())) ){return;}

           //Connection con = Rdbms.createTransactionWithSavePoint();        
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