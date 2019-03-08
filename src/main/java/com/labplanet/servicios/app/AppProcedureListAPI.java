/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETFrontEnd;
import LabPLANET.utilities.LabPLANETJson;
import LabPLANET.utilities.LabPLANETPlatform;
import databases.Rdbms;
import databases.Token;
import functionalJava.user.UserProfile;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
//import org.codehaus.jettison.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import functionalJava.sop.UserSop;
import java.util.ResourceBundle;

/**
 *
 * @author Administrator
 */
public class AppProcedureListAPI extends HttpServlet {

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
                response.setContentType("application/json");
                response.setCharacterEncoding(LabPLANETPlatform.LAB_ENCODER_UTF8);
                request.setCharacterEncoding(LabPLANETPlatform.LAB_ENCODER_UTF8);
                
                ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");
                String frontendUrl = prop.getString("frontend_url");
      
                response.setHeader("Access-Control-Allow-Origin", frontendUrl);
                response.setHeader("Access-Control-Allow-Methods", "GET");        

            String language = "en";
            String sopFieldName = "sop";
            
            LabPLANETJson labJson = new LabPLANETJson();
            response.setContentType("application/json");        response.setCharacterEncoding(LabPLANETPlatform.LAB_ENCODER_UTF8);

        try (PrintWriter out = response.getWriter()) {
            String[] errObject = new String[]{"Servlet AppProcedureList at " + request.getServletPath()};      
            
            String finalToken = request.getParameter("finalToken");                      

            if (finalToken==null) {                  
                errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: finalToken is one mandatory param for this API");                    
                Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;
            }                    

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
            
            String rolName = userRole;
            UserProfile usProf = new UserProfile();
            Object[] allUserProcedurePrefix = usProf.getAllUserProcedurePrefix(dbUserName);
            if ("LABPLANET_FALSE".equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                Object[] errMsg = LabPLANETFrontEnd.responseError(allUserProcedurePrefix, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);                    
            }
            String[] procFldNameArray = new String[0];
            procFldNameArray = LabPLANETArray.addValueToArray1D(procFldNameArray, "name");
            procFldNameArray = LabPLANETArray.addValueToArray1D(procFldNameArray, "version");
            procFldNameArray = LabPLANETArray.addValueToArray1D(procFldNameArray, "label_en");
            procFldNameArray = LabPLANETArray.addValueToArray1D(procFldNameArray, "label_es");
            //procFldNameArray = LabPLANETArray.addValueToArray1D(procFldNameArray, "schemaPrefix");
            
            String[] procEventFldNameArray = new String[0];
            procEventFldNameArray = LabPLANETArray.addValueToArray1D(procEventFldNameArray, "name");
            procEventFldNameArray = LabPLANETArray.addValueToArray1D(procEventFldNameArray, "label_en");
            procEventFldNameArray = LabPLANETArray.addValueToArray1D(procEventFldNameArray, "label_es");
            procEventFldNameArray = LabPLANETArray.addValueToArray1D(procEventFldNameArray, "branch_level");
            procEventFldNameArray = LabPLANETArray.addValueToArray1D(procEventFldNameArray, "type");
            procEventFldNameArray = LabPLANETArray.addValueToArray1D(procEventFldNameArray, "mode");
            
            Object[][] notCompletedUserSOP = null;
            Object[] notCompletedUserSOP1D = null;
                    
            UserSop userSop = new UserSop();
            //Object[] procArray = new Object[0];
            JSONArray procedures = new JSONArray();     
            for (Object curProc: allUserProcedurePrefix){
                JSONObject procedure = new JSONObject();
                //JSONArray procEventsJson = new JSONArray();
                Object[][] procInfo = Rdbms.getRecordFieldsByFilter(curProc.toString()+"-config", "procedure_info", 
                        new String[]{"name is not null"}, null, procFldNameArray);
                if ("LABPLANET_FALSE".equalsIgnoreCase(procInfo[0][0].toString())){
                    Object[] errMsg = LabPLANETFrontEnd.responseError(procInfo, language,  curProc.toString());
                    response.sendError((int) errMsg[0], (String) errMsg[1]);                    
                }                       
                for (int yProc=0; yProc<procInfo[0].length; yProc++){              
                    //procArray = LabPLANETArray.addValueToArray1D(procArray, procInfo[0][yProc]);
                    procedure.put(procFldNameArray[yProc], procInfo[0][yProc]);
                }
                //procArray = LabPLANETArray.addValueToArray1D(procArray, curProc);
                procedure.put("schemaPrefix", curProc);
                
                Boolean isProcedureSopEnable = userSop.isProcedureSopEnable((String) curProc);
                if (!isProcedureSopEnable) procedure.put("SopCertification", "Disabled");                 
                if (isProcedureSopEnable){
                    notCompletedUserSOP = userSop.getNotCompletedUserSOP(internalUserID, curProc.toString(), new String[]{"sop_name"});
                    notCompletedUserSOP1D = LabPLANETArray.array2dTo1d(notCompletedUserSOP);
                    procEventFldNameArray = LabPLANETArray.addValueToArray1D(procEventFldNameArray, sopFieldName);
                }
                
                Object[][] procEvent = Rdbms.getRecordFieldsByFilter(curProc.toString()+"-config", "procedure_events", 
                        new String[]{"role_name"}, new String[]{rolName}, 
                        procEventFldNameArray, new String[]{"order_number"});
                if (!"LABPLANET_FALSE".equalsIgnoreCase(procEvent[0][0].toString())){                                                
                    
                    JSONArray procEvents = new JSONArray(); 
                    for (Object[] procEvent1 : procEvent) {
                        JSONObject procEventJson = new JSONObject();
                        for (int yProcEv = 0; yProcEv<procEvent[0].length; yProcEv++) {
                            //procArray = LabPLANETArray.addValueToArray1D(procArray, procEvent[xProcEv][yProcEv]);
                            procEventJson.put(procEventFldNameArray[yProcEv], procEvent1[yProcEv]);
                        }
                        Boolean userHasNotCompletedSOP = false;
                        JSONObject procEventSopDetail = new JSONObject();
                        String procEventSops = (String) procEvent1[LabPLANETArray.valuePosicInArray(procEventFldNameArray, sopFieldName)];
                        if (procEventSops==null){
                            userHasNotCompletedSOP = false;
                            procEventJson.put("sops_passed", true);
                            procEventSopDetail.put("sop_list", "NO_SOPS");
                            procEventSopDetail.put("sop_total", 0);
                            procEventSopDetail.put("sop_total_completed", 0);
                            procEventSopDetail.put("sop_total_not_completed", 0);
                        }else{
                            if ("".equals(procEventSops)){
                                procEventJson.put("sops_passed", true);
                                userHasNotCompletedSOP = false;
                                procEventSopDetail.put("sop_list", "NO_SOPS");
                                procEventSopDetail.put("sop_total", 0);
                                procEventSopDetail.put("sop_total_completed", 0);
                                procEventSopDetail.put("sop_total_not_completed", 0);
                            }else{
                                Object[] procEventSopsArr = procEventSops.split("\\|");
                                String sopListStr = "";
                                Integer sopTotalNotCompleted = 0;
                                Integer sopTotalCompleted = 0;
                                Integer sopTotal = 0;
                                JSONObject procEventSopSummary = new JSONObject();   
                                for (Object curProcEvSop: procEventSopsArr){
                                    JSONObject procEventSopDetailJson = new JSONObject();   
                                    sopTotal++;
                                    procEventSopDetailJson.put("sop_name", curProcEvSop);
                                    if (LabPLANETArray.valuePosicInArray(notCompletedUserSOP1D, curProcEvSop)==-1) {
                                        sopTotalNotCompleted++;
                                        sopListStr=sopListStr+curProcEvSop.toString()+"*NO, ";
                                        userHasNotCompletedSOP = true;
                                        procEventSopDetailJson.put("sop_completed", false);
                                    }else{
                                        sopTotalCompleted++;
                                        procEventSopDetailJson.put("sop_completed", true);
                                    }
                                    procEventSopSummary.put(sopTotal, procEventSopDetailJson);
                                }
                                procEventJson.put("sops_passed", !userHasNotCompletedSOP);
                                procEventSopDetail.put("sop_total", sopTotal);
                                procEventSopDetail.put("sop_total_completed", sopTotalCompleted);
                                procEventSopDetail.put("sop_total_not_completed", sopTotalNotCompleted);
                                procEventSopDetail.put("sop_list", procEventSopSummary);
                            }
                        }procEventJson.put("sops", procEventSopDetail);
                        procEvents.add(procEventJson);
                    }
                    procedure.put("definition", procEvents);
                }        
                procedures.add(procedure);
            }
            procFldNameArray = LabPLANETArray.addValueToArray1D(procFldNameArray, "schemaPrefix");
            //Object[][] procArray2d = LabPLANETArray.array1dTo2d(procArray, procFldNameArray.length);              
            JSONObject proceduresList = new JSONObject();
            proceduresList.put("procedures", procedures);

            Response.ok().build();
            response.getWriter().write(proceduresList.toString());               
            Rdbms.closeRdbms();
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
