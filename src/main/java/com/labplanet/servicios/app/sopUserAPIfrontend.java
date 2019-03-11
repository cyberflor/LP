/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETFrontEnd;
import LabPLANET.utilities.LabPLANETRequest;
import databases.Rdbms;
import databases.Token;
import functionalJava.sop.UserSop;
import functionalJava.user.UserProfile;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
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
        response = LabPLANETRequest.responsePreparation(response);
        request = LabPLANETRequest.requestPreparation(request);      
        
         UserSop userSop = new UserSop();
         
         String language = "es";
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

                String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};     

                String[] mandatoryParams = new String[]{"finalToken"};
                mandatoryParams = LabPLANETArray.addValueToArray1D(mandatoryParams, "finalToken");
                Object[] areMandatoryParamsInResponse = LabPLANETRequest.areMandatoryParamsInApiRequest(request, mandatoryParams);                        
                
                String finalToken = request.getParameter("finalToken");                   

                Token token = new Token();
                String[] tokenParams = token.tokenParamsList();
                String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

                String dbUserName = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "userDB")];
                String dbUserPassword = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "userDBPassword")];
                String internalUserID = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "internalUserID")];         
                String userRole = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "userRole")];                     

                if (Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword)==null){
                    errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                    errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                    Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);   
                    Rdbms.closeRdbms(); 
                    return ;               
                }
               
                UserProfile usProf = new UserProfile();
                String[] allUserProcedurePrefix = LabPLANETArray.ConvertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(dbUserName));
                if ("LABPLANET_FALSE".equalsIgnoreCase(allUserProcedurePrefix[0])){
                    Object[] errMsg = LabPLANETFrontEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);   
                    Rdbms.closeRdbms();
                    return;
                }     
                Integer numPendingSOPs = 0;
                String[] fieldsToRetrieve = new String[]{"sop_id"};
                for (String curProc: allUserProcedurePrefix){
                    Object[][] userProcSops = userSop.getNotCompletedUserSOP(internalUserID, curProc, fieldsToRetrieve);       
                    if (!"LABPLANET_FALSE".equalsIgnoreCase(userProcSops[0][0].toString())){numPendingSOPs=numPendingSOPs+userProcSops.length;}
                }
                   JSONArray SopOptions = new JSONArray(); 
                    
                    //JSONObject sopElement = new JSONObject();
                    
                    JSONObject SopOption = new JSONObject();
                    SopOption.put("name", "AllMySOPs");
                    SopOption.put("label_en", "All my SOPs");
                    SopOption.put("label_es", "Todos Mis PNTs");
                    SopOption.put("window_url", "Modulo1/home.js");
                    SopOption.put("mode", "edit");
                    SopOption.put("branch_level", "level1");
                    SopOption.put("type", "tree-list");
                    SopOptions.add(SopOption);
                    
                    SopOption = new JSONObject();
                    SopOption.put("name", "MyPendingSOPs");
                    SopOption.put("label_en", "My Pending SOPs");
                    SopOption.put("label_es", "Mis PNT Pendientes");
                    SopOption.put("window_url", "Modulo1/home.js");
                    SopOption.put("mode", "edit");
                    SopOption.put("branch_level", "level1");
                    SopOption.put("badge", numPendingSOPs);
                    SopOption.put("type", "tree-list");
                    SopOptions.add(SopOption);
                    
                    SopOption = new JSONObject();
                    SopOption.put("name", "ProcSOPs");
                    SopOption.put("label_en", "Procedure SOPs");
                    SopOption.put("label_es", "PNTs del proceso");
                    SopOption.put("window_url", "Modulo1/home.js");
                    SopOption.put("mode", "edit");
                    SopOption.put("branch_level", "level1");
                    SopOption.put("type", "tree-list");
                    SopOptions.add(SopOption);
                    
                    JSONObject sopElement = new JSONObject();
                    sopElement.put("definition", SopOptions);
                    sopElement.put("name", "SOP");
                    sopElement.put("version", "1");
                    sopElement.put("label_en", "SOPs");
                    sopElement.put("label_es", "P.N.T.");
                    sopElement.put("schemaPrefix", "module1");
                    //SopElement.add(SopOption);
                    
                    JSONArray arrFinal = new JSONArray();
                    arrFinal.add(sopElement);
                    
                    Response.ok().build();
                    response.getWriter().write(arrFinal.toString());                               
                    
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
