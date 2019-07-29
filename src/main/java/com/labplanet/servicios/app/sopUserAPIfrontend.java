/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPHttp;
import databases.Rdbms;
import databases.Token;
import functionalJava.sop.UserSop;
import functionalJava.testingScripts.LPTestingOutFormat;
import functionalJava.user.UserProfile;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Administrator
 */
public class sopUserAPIfrontend extends HttpServlet {

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

        UserSop userSop = new UserSop();        
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

                String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};     

                String[] mandatoryParams = new String[]{"finalToken"};
                mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "finalToken");
                Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParams);                        
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                    errObject = LPArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                    errObject = LPArray.addValueToArray1D(errObject, "API Error Message: Mandatory fields missing");                    
                    Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);   
                    Rdbms.closeRdbms(); 
                    return ;  
                }
                String finalToken = request.getParameter("finalToken");                   

                Token token = new Token();
                String[] tokenParams = token.tokenParamsList();
                String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

                String dbUserName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERDB)];
                String dbUserPassword = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERPW)];
                String internalUserID = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_INTERNAL_USERID)];         
//                String userRole = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ROLE)];                     

                if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}   
               
                UserProfile usProf = new UserProfile();
                String[] allUserProcedurePrefix = LPArray.convertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(dbUserName));
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0])){
                    Object[] errMsg = LPFrontEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);   
                    Rdbms.closeRdbms();
                    return;
                }     
                Integer numPendingSOPs = 0;
                String[] fieldsToRetrieve = new String[]{"sop_id"};
                for (String curProc: allUserProcedurePrefix){
                    Object[][] userProcSops = userSop.getNotCompletedUserSOP(internalUserID, curProc, fieldsToRetrieve);       
                    if (userProcSops.length>0){
                        if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(userProcSops[0][0].toString())){
                            numPendingSOPs=numPendingSOPs+userProcSops.length;}                            
                        }
                }
                   JSONArray sopOptions = new JSONArray(); 
                    
                    //JSONObject sopElement = new JSONObject();
                    
                    JSONObject sopOption = new JSONObject();
                    sopOption.put("name", "AllMySOPs");
                    sopOption.put("label_en", "All my SOPs");
                    sopOption.put("label_es", "Todos Mis PNTs");
                    sopOption.put("window_url", "Modulo1/home.js");
                    sopOption.put("mode", "edit");
                    sopOption.put("branch_level", "level1");
                    sopOption.put("type", "tree-list");
                    sopOptions.add(sopOption);
                    
                    sopOption = new JSONObject();
                    sopOption.put("name", "MyPendingSOPs");
                    sopOption.put("label_en", "My Pending SOPs");
                    sopOption.put("label_es", "Mis PNT Pendientes");
                    sopOption.put("window_url", "Modulo1/home.js");
                    sopOption.put("mode", "edit");
                    sopOption.put("branch_level", "level1");
                    sopOption.put("badge", numPendingSOPs);
                    sopOption.put("type", "tree-list");
                    sopOptions.add(sopOption);
                    
                    sopOption = new JSONObject();
                    sopOption.put("name", "ProcSOPs");
                    sopOption.put("label_en", "Procedure SOPs");
                    sopOption.put("label_es", "PNTs del proceso");
                    sopOption.put("window_url", "Modulo1/home.js");
                    sopOption.put("mode", "edit");
                    sopOption.put("branch_level", "level1");
                    sopOption.put("type", "tree-list");
                    sopOptions.add(sopOption);
                    
                    JSONObject sopElement = new JSONObject();
                    sopElement.put("definition", sopOptions);
                    sopElement.put("name", "SOP");
                    sopElement.put("version", "1");
                    sopElement.put("label_en", "SOPs");
                    sopElement.put("label_es", "P.N.T.");
                    sopElement.put("schemaPrefix", "process-us");
                    //SopElement.add(sopOption);
                    
                    JSONArray arrFinal = new JSONArray();
                    arrFinal.add(sopElement);                    
                    LPFrontEnd.servletReturnSuccess(request, response, arrFinal);
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