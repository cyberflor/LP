/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPHttp;
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

/**
 *
 * @author Administrator
 */
public class AppProcedureListAPI extends HttpServlet {

    public static final String LABEL_SOP_LIST="sop_list";

    public static final String LABEL_SOPS_PASSED="sops_passed";
    public static final String LABEL_SOP_TOTAL="sop_total";
    public static final String LABEL_SOP_TOTAL_COMPLETED="sop_total_completed";
    public static final String LABEL_SOP_TOTAL_NOT_COMPLETED="sop_total_not_completed";
    public static final String LABEL_SOP_TOTAL_NO_SOPS="NO_SOPS";
    
/**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {   
        response = LPHttp.responsePreparation(response);
        request = LPHttp.requestPreparation(request);      

        String language = "en";
        String sopFieldName = "sop";
            
        try (PrintWriter out = response.getWriter()) {
            String[] errObject = new String[]{"Servlet AppProcedureList at " + request.getServletPath()};                  
            String finalToken = request.getParameter("finalToken");                      
            if (finalToken==null) {                  
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: finalToken is one mandatory param for this API");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;
            }                    
            Token token = new Token();
            String[] tokenParams = token.tokenParamsList();
            String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

            String dbUserName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERDB)];
            String dbUserPassword = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERPW)];
            String internalUserID = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_INTERNAL_USERID)];         
            String userRole = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ROLE)];                     
                        
            if (Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword)==null){
                errObject = LPArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;               
            }               
            String rolName = userRole;
            UserProfile usProf = new UserProfile();
            Object[] allUserProcedurePrefix = usProf.getAllUserProcedurePrefix(dbUserName);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                Object[] errMsg = LPFrontEnd.responseError(allUserProcedurePrefix, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);          
                return;
            }
            String[] procFldNameArray = new String[0];
            procFldNameArray = LPArray.addValueToArray1D(procFldNameArray, "name");
            procFldNameArray = LPArray.addValueToArray1D(procFldNameArray, "version");
            procFldNameArray = LPArray.addValueToArray1D(procFldNameArray, "label_en");
            procFldNameArray = LPArray.addValueToArray1D(procFldNameArray, "label_es");
            //procFldNameArray = LPArray.addValueToArray1D(procFldNameArray, "schemaPrefix");
            
            String[] procEventFldNameArray = new String[0];
            procEventFldNameArray = LPArray.addValueToArray1D(procEventFldNameArray, "name");
            procEventFldNameArray = LPArray.addValueToArray1D(procEventFldNameArray, "label_en");
            procEventFldNameArray = LPArray.addValueToArray1D(procEventFldNameArray, "label_es");
            procEventFldNameArray = LPArray.addValueToArray1D(procEventFldNameArray, "branch_level");
            procEventFldNameArray = LPArray.addValueToArray1D(procEventFldNameArray, "type");
            procEventFldNameArray = LPArray.addValueToArray1D(procEventFldNameArray, "mode");
            
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
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(procInfo[0][0].toString())){
                    Object[] errMsg = LPFrontEnd.responseError(procInfo, language,  curProc.toString());
                    response.sendError((int) errMsg[0], (String) errMsg[1]);                    
                }                       
                for (int yProc=0; yProc<procInfo[0].length; yProc++){              
                    //procArray = LPArray.addValueToArray1D(procArray, procInfo[0][yProc]);
                    procedure.put(procFldNameArray[yProc], procInfo[0][yProc]);
                }
                //procArray = LPArray.addValueToArray1D(procArray, curProc);
                procedure.put("schemaPrefix", curProc);
                
                Boolean isProcedureSopEnable = userSop.isProcedureSopEnable((String) curProc);
                if (!isProcedureSopEnable) procedure.put("SopCertification", "Disabled");                 
                if (isProcedureSopEnable){
                    notCompletedUserSOP = userSop.getNotCompletedUserSOP(internalUserID, curProc.toString(), new String[]{"sop_name"});
                    notCompletedUserSOP1D = LPArray.array2dTo1d(notCompletedUserSOP);
                    procEventFldNameArray = LPArray.addValueToArray1D(procEventFldNameArray, sopFieldName);
                }
                
                Object[][] procEvent = Rdbms.getRecordFieldsByFilter(curProc.toString()+"-config", "procedure_events", 
                        new String[]{"role_name"}, new String[]{rolName}, 
                        procEventFldNameArray, new String[]{"order_number"});
                if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(procEvent[0][0].toString())){                                                
                    
                    JSONArray procEvents = new JSONArray(); 
                    for (Object[] procEvent1 : procEvent) {
                        JSONObject procEventJson = new JSONObject();
                        for (int yProcEv = 0; yProcEv<procEvent[0].length; yProcEv++) {
                            //procArray = LPArray.addValueToArray1D(procArray, procEvent[xProcEv][yProcEv]);
                            procEventJson.put(procEventFldNameArray[yProcEv], procEvent1[yProcEv]);
                        }
                        Boolean userHasNotCompletedSOP = false;
                        JSONObject procEventSopDetail = new JSONObject();
                        String procEventSops = (String) procEvent1[LPArray.valuePosicInArray(procEventFldNameArray, sopFieldName)];
                        if (procEventSops==null){
                            userHasNotCompletedSOP = false;
                            procEventJson.put(LABEL_SOPS_PASSED, true);
                            procEventSopDetail.put(LABEL_SOP_LIST, LABEL_SOP_TOTAL_NO_SOPS);
                            procEventSopDetail.put(LABEL_SOP_TOTAL, 0);
                            procEventSopDetail.put(LABEL_SOP_TOTAL_COMPLETED, 0);
                            procEventSopDetail.put(LABEL_SOP_TOTAL_NOT_COMPLETED, 0);
                        }else{
                            if ("".equals(procEventSops)){
                                procEventJson.put(LABEL_SOPS_PASSED, true);
                                userHasNotCompletedSOP = false;
                                procEventSopDetail.put(LABEL_SOP_LIST, LABEL_SOP_TOTAL_NO_SOPS);
                                procEventSopDetail.put(LABEL_SOP_TOTAL, 0);
                                procEventSopDetail.put(LABEL_SOP_TOTAL_COMPLETED, 0);
                                procEventSopDetail.put(LABEL_SOP_TOTAL_NOT_COMPLETED, 0);
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
                                    if (LPArray.valuePosicInArray(notCompletedUserSOP1D, curProcEvSop)==-1) {
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
                                procEventJson.put(LABEL_SOPS_PASSED, !userHasNotCompletedSOP);
                                procEventSopDetail.put(LABEL_SOP_TOTAL, sopTotal);
                                procEventSopDetail.put(LABEL_SOP_TOTAL_COMPLETED, sopTotalCompleted);
                                procEventSopDetail.put(LABEL_SOP_TOTAL_NOT_COMPLETED, sopTotalNotCompleted);
                                procEventSopDetail.put(LABEL_SOP_LIST, procEventSopSummary);
                            }
                        }procEventJson.put("sops", procEventSopDetail);
                        procEvents.add(procEventJson);
                    }
                    procedure.put("definition", procEvents);
                }        
                procedures.add(procedure);
            }
            procFldNameArray = LPArray.addValueToArray1D(procFldNameArray, "schemaPrefix");
            //Object[][] procArray2d = LPArray.array1dTo2d(procArray, procFldNameArray.length);              
            JSONObject proceduresList = new JSONObject();
            proceduresList.put("procedures", procedures);

            Response.ok().build();
            response.getWriter().write(proceduresList.toString());               
            return;
//            Rdbms.closeRdbms();
/*
if (1==1){            
    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "allUserProcedurePrefix"+ Arrays.toString(allUserProcedurePrefix)); 
    return;
} */           
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
