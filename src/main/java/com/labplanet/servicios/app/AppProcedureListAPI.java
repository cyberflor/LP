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
import LabPLANET.utilities.LPJson;
import databases.Rdbms;
import databases.Token;
import functionalJava.user.UserProfile;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import functionalJava.sop.UserSop;

/**
 *
 * @author Administrator
 */
public class AppProcedureListAPI extends HttpServlet {

    public static final String MANDATORY_PARAMS_MAIN_SERVLET = "finalToken";
    
    public static final String LABEL_ARRAY_PROCEDURES="procedures";
    public static final String LABEL_ARRAY_PROC_EVENTS ="definition";
    public static final String LABEL_ARRAY_SOPS="sops";
    public static final String LABEL_ARRAY_SOP_LIST="sop_list";

    public static final String LABEL_SOPS_PASSED="sops_passed";
    public static final String LABEL_SOP_TOTAL="sop_total";
    public static final String LABEL_SOP_NAME="sop_name";
    public static final String LABEL_SOP_CERTIFICATION="SopCertification";
    public static final String LABEL_VALUE_SOP_CERTIFICATION_DISABLE="Disabled";
    public static final String LABEL_SOP_TOTAL_COMPLETED="sop_total_completed";
    public static final String LABEL_SOP_TOTAL_NOT_COMPLETED="sop_total_not_completed";
    public static final String LABEL_ARRAY_SOP_LIST_INFO="sop_list_info";
    public static final String LABEL_SOP_TOTAL_NO_SOPS="There are no SOPS for this form";
    
    
    public static final String LABEL_PROC_SCHEMA="schemaPrefix";
    
    public static final String FIELD_NAME_SOP="sop";
    
    public static final String PROC_FLD_NAME="name|version|label_en|label_es";
    public static final String PROC_EVENT_FLD_NAME="name|label_en|label_es|branch_level|type|mode|esign_required|lp_frontend_page_name|sop";
    
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
            
        try (PrintWriter out = response.getWriter()) {
            Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, MANDATORY_PARAMS_MAIN_SERVLET.split("\\|"));                       
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                 LPFrontEnd.servletReturnResponseError(request, response, 
                         LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                 return;          
             }               
            String finalToken = request.getParameter(globalAPIsParams.REQUEST_PARAM_FINAL_TOKEN);    
                           
            Token token = new Token(finalToken);
                        
           if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}               
         
            String rolName = token.getUserRole();
            UserProfile usProf = new UserProfile();
            Object[] allUserProcedurePrefix = usProf.getAllUserProcedurePrefix(token.getUserName());
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                LPFrontEnd.servletReturnResponseErrorLPFalseDiagnostic(request, response, allUserProcedurePrefix);
                return;
            }
            String[] procFldNameArray = PROC_FLD_NAME.split("\\|");
            String[] procEventFldNameArray = PROC_EVENT_FLD_NAME.split("\\|");            

            JSONArray procedures = new JSONArray();     
            for (Object curProc: allUserProcedurePrefix){
                JSONObject procedure = new JSONObject();
                String schemaName=LPPlatform.buildSchemaName(curProc.toString(), LPPlatform.SCHEMA_CONFIG);

                if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}           

                Object[][] procInfo = Rdbms.getRecordFieldsByFilter(schemaName, "procedure_info", 
                        new String[]{"name is not null"}, null, PROC_FLD_NAME.split("\\|"));
                if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(procInfo[0][0].toString())){
                    procedure = LPJson.convertArrayRowToJSONObject(procFldNameArray, procInfo[0]);
                    procedure.put(LABEL_PROC_SCHEMA, curProc);

                    if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}      
                    
                    Object[][] procEvent = Rdbms.getRecordFieldsByFilter(curProc.toString()+"-config", "procedure_events", 
                            new String[]{"role_name"}, new String[]{rolName}, 
                            procEventFldNameArray, new String[]{"branch_level", "order_number"});
                    if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(procEvent[0][0].toString())){ 
                        JSONObject procEventJson = new JSONObject();
                        procEventJson.put("Error on get procedure_events records", procEvent[0][procEvent.length-1].toString());
                        procedure.put(LABEL_ARRAY_PROC_EVENTS, procEventJson);
                    }
                    
                    if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(procEvent[0][0].toString())){                                                
                        JSONArray procEvents = new JSONArray(); 
                        for (Object[] procEvent1 : procEvent) {
                            JSONObject procEventJson = new JSONObject();
                            procEventJson = LPJson.convertArrayRowToJSONObject(procEventFldNameArray, procEvent1);

                            JSONObject procEventSopDetail = procEventSops(token.getPersonName(), curProc.toString(), procedure, procEventJson, procEventFldNameArray, procEvent1);

                            procEventJson.put(LABEL_ARRAY_SOPS, procEventSopDetail);
                            procEvents.add(procEventJson);
                        }
                        procedure.put(LABEL_ARRAY_PROC_EVENTS, procEvents);
                    } 
                }
                procedures.add(procedure);
            }
            procFldNameArray = LPArray.addValueToArray1D(procFldNameArray, LABEL_PROC_SCHEMA);
            //Object[][] procArray2d = LPArray.array1dTo2d(procArray, procFldNameArray.length);              
            JSONObject proceduresList = new JSONObject();
            proceduresList.put(LABEL_ARRAY_PROCEDURES, procedures);
            LPFrontEnd.servletReturnSuccess(request, response, proceduresList);
        }
    }
    
    public JSONObject procEventSops(String internalUserID, String curProc, JSONObject procedure, JSONObject procEventJson, String[] procEventFldNameArray, Object[] procEvent1){
        Object[][] notCompletedUserSOP = null;
        Object[] notCompletedUserSOP1D = null;
                    
        UserSop userSop = new UserSop();
        Boolean userHasNotCompletedSOP = false;

        Boolean isProcedureSopEnable = userSop.isProcedureSopEnable((String) curProc);
        if (!isProcedureSopEnable) procedure.put(LABEL_SOP_CERTIFICATION, LABEL_VALUE_SOP_CERTIFICATION_DISABLE);                 
        if (isProcedureSopEnable){
            notCompletedUserSOP = userSop.getNotCompletedUserSOP(internalUserID, curProc, new String[]{"sop_name"});
            notCompletedUserSOP1D = LPArray.array2dTo1d(notCompletedUserSOP);
        }        
        JSONObject procEventSopDetail = new JSONObject();
        String procEventSops = null;
        Integer sopFieldposic = LPArray.valuePosicInArray(procEventFldNameArray, FIELD_NAME_SOP);
        if (sopFieldposic>-1){
            procEventSops = (String) procEvent1[sopFieldposic];}
        
        if ( (procEventSops==null) || ( (procEventSops!=null) && ("".equals(procEventSops)) ) ){
            procEventJson.put(LABEL_SOPS_PASSED, true);
            userHasNotCompletedSOP = false;
            if ( (procEventSops==null) ) {
                userHasNotCompletedSOP = false;
                procEventJson.put(LABEL_SOPS_PASSED, true);
            }
            procEventSopDetail.put(LABEL_ARRAY_SOP_LIST, new JSONArray());
            procEventSopDetail.put(LABEL_ARRAY_SOP_LIST_INFO, LABEL_SOP_TOTAL_NO_SOPS);
            procEventSopDetail.put(LABEL_SOP_TOTAL, 0);
            procEventSopDetail.put(LABEL_SOP_TOTAL_COMPLETED, 0);
            procEventSopDetail.put(LABEL_SOP_TOTAL_NOT_COMPLETED, 0);
        }else{
            Object[] procEventSopsArr = procEventSops.split("\\|");
            String sopListStr = "";
            Integer sopTotalNotCompleted = 0;                Integer sopTotalCompleted = 0;                
            JSONArray procEventSopSummary = new JSONArray();   
            for (Object curProcEvSop: procEventSopsArr){
                JSONObject procEventSopDetailJson = new JSONObject();   
                procEventSopDetailJson.put(LABEL_SOP_NAME, curProcEvSop);
                if (LPArray.valuePosicInArray(notCompletedUserSOP1D, curProcEvSop)==-1) {
                    sopTotalNotCompleted++;
                    sopListStr=sopListStr+curProcEvSop.toString()+"*NO, ";
                    userHasNotCompletedSOP = true;
                    procEventSopDetailJson.put(LABEL_SOP_TOTAL_COMPLETED, false);
                }else{
                    sopTotalCompleted++;
                    procEventSopDetailJson.put(LABEL_SOP_TOTAL_COMPLETED, true);
                }
                procEventSopSummary.add(procEventSopDetailJson);
            }
            procEventJson.put(LABEL_SOPS_PASSED, !userHasNotCompletedSOP);
            procEventSopDetail.put(LABEL_SOP_TOTAL, procEventSopsArr.length);
            procEventSopDetail.put(LABEL_SOP_TOTAL_COMPLETED, sopTotalCompleted);
            procEventSopDetail.put(LABEL_SOP_TOTAL_NOT_COMPLETED, sopTotalNotCompleted);
            procEventSopDetail.put(LABEL_ARRAY_SOP_LIST, procEventSopSummary);
        }        
        return procEventSopDetail;
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
