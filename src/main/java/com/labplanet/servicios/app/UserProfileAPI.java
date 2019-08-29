/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import lbplanet.utilities.LPArray;
import lbplanet.utilities.LPFrontEnd;
import lbplanet.utilities.LPHttp;
import lbplanet.utilities.LPPlatform;
import com.labplanet.servicios.moduleenvmonit.EnvMonAPI;
import databases.Rdbms;
import databases.Token;
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
public class UserProfileAPI extends HttpServlet {
    public static final String MANDATORY_PARAMS_MAIN_SERVLET = "actionName|finalToken";
    
    public static final String API_ENDPOINT_USER_UPDATE_ESIGN="UPDATE_ESIGN";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request=LPHttp.requestPreparation(request);
        response=LPHttp.responsePreparation(response);

        String language = LPFrontEnd.setLanguage(request);         
        try (PrintWriter out = response.getWriter()) {
            Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, MANDATORY_PARAMS_MAIN_SERVLET.split("\\|"));                       
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                 LPFrontEnd.servletReturnResponseError(request, response, 
                         LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                 return;          
             }               
            String actionName = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_ACTION_NAME);
            String finalToken = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_FINAL_TOKEN);            
                           
            Token token = new Token(finalToken);
            String[] mandatoryParams = null;
           Object[] procActionRequiresUserConfirmation = LPPlatform.procActionRequiresUserConfirmation(LPPlatform.SCHEMA_APP, actionName);
           if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresUserConfirmation[0].toString())){     
               mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, GlobalAPIsParams.REQUEST_PARAM_USER_TO_CHECK);    
               mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, GlobalAPIsParams.REQUEST_PARAM_PASSWORD_TO_CHECK);    
           }
           Object[] procActionRequiresEsignConfirmation = LPPlatform.procActionRequiresEsignConfirmation(LPPlatform.SCHEMA_APP, actionName);
           if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresEsignConfirmation[0].toString())){                                                      
               mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, GlobalAPIsParams.REQUEST_PARAM_ESIGN_TO_CHECK);    
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
        Connection con = Rdbms.createTransactionWithSavePoint();        
 /*       if (con==null){
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The Transaction cannot be created, the action should be aborted");
             return;
        }
*/        
        try {
            con.rollback();
            con.setAutoCommit(true);    
        } catch (SQLException ex) {
            Logger.getLogger(EnvMonAPI.class.getName()).log(Level.SEVERE, null, ex);
        }        
           
            Object[] userActionDiagnostic = new Object[]{LPPlatform.LAB_FALSE};
            switch (actionName.toUpperCase()){
                case API_ENDPOINT_USER_UPDATE_ESIGN: 
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, new String[]{"newEsignPhrase"});
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                    
                    String newEsignPhrase = request.getParameter("newEsignPhrase"); 
                    userActionDiagnostic = Rdbms.updateRecordFieldsByFilter(LPPlatform.SCHEMA_APP, "users", 
                            new String[]{"e_sign"}, new Object[]{newEsignPhrase}, new String[]{"user_name"}, new Object[]{token.getUserName()});
                    
                    break;
                default:
                    LPFrontEnd.servletReturnResponseError(request, response, LPPlatform.API_ERRORTRAPING_PROPERTY_ENDPOINT_NOT_FOUND, new Object[]{actionName, this.getServletName()}, language);              
                    return;                                        
            }
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(userActionDiagnostic[0].toString())){  
                Rdbms.rollbackWithSavePoint();
                if (!con.getAutoCommit()){
                    con.rollback();
                    con.setAutoCommit(true);}                
                LPFrontEnd.servletReturnResponseErrorLPFalseDiagnostic(request, response, userActionDiagnostic);   
            }else{
                LPFrontEnd.servletReturnResponseErrorLPTrueDiagnostic(request, response, userActionDiagnostic);
            }                             
        } catch (SQLException ex) {
            Logger.getLogger(UserProfileAPI.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            processRequest(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(UserProfileAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try {
            processRequest(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(UserProfileAPI.class.getName()).log(Level.SEVERE, null, ex);
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
