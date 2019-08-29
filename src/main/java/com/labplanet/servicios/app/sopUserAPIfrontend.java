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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import functionalJava.user.UserProfile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import functionalJava.sop.UserSop;
import java.util.logging.Logger;
/**
 *
 * @author Administrator
 */
public class sopUserAPIfrontend extends HttpServlet {
    public static final String MANDATORY_PARAMS_MAIN_SERVLET="actionName|finalToken";
    public static final String ERRORMSG_ERROR_STATUS_CODE="Error Status Code";
    public static final String ERRORMSG_MANDATORY_PARAMS_MISSING="API Error Message: There are mandatory params for this API method not being passed";

    public static final String FIELDNAME_SOP_ID="sop_id";
    public static final String FIELDNAME_SOP_NAME="sop_name";
    
    public static final String JSON_TAG_NAME="name";
    public static final String JSON_TAG_LABEL_EN="label_en";
    public static final String JSON_TAG_LABEL_ES="label_es";
    public static final String JSON_TAG_WINDOWS_URL="window_url";
    public static final String JSON_TAG_MODE="mode";
    public static final String JSON_TAG_BRANCH_LEVEL="branch_level";
    public static final String JSON_TAG_TYPE="type";
    public static final String JSON_TAG_BADGE="badge";
    public static final String JSON_TAG_DEFINITION="definition";
    public static final String JSON_TAG_VERSION="version";
    public static final String JSON_TAG_SCHEMA_PREFIX="schemaPrefix";
    public static final String JSON_TAG_VALUE_TYPE_TREE_LIST="tree-list";
    public static final String JSON_TAG_VALUE_BRANCH_LEVEL_LEVEL_1="level1";
    public static final String JSON_TAG_VALUE_WINDOWS_URL_HOME="Modulo1/home.js";
     
                           

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
            String actionName = request.getParameter(globalAPIsParams.REQUEST_PARAM_ACTION_NAME);
            String finalToken = request.getParameter(globalAPIsParams.REQUEST_PARAM_FINAL_TOKEN);
            
            Token token = new Token(finalToken);
                        
             if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}   
             
            switch (actionName.toUpperCase()){
            case "ALL_MY_SOPS":    
                UserProfile usProf = new UserProfile();
                String[] allUserProcedurePrefix = LPArray.convertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(token.getUserName()));
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0])){
                    Object[] errMsg = LPFrontEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    Rdbms.closeRdbms(); 
                    return;
                }
                String[] fieldsToRetrieve = new String[]{FIELDNAME_SOP_ID, FIELDNAME_SOP_NAME};
                String sopFieldsToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_SOP_FIELDS_TO_RETRIEVE);
                if (sopFieldsToRetrieve!=null) {                
                    String[] sopFieldsToRetrieveArr = sopFieldsToRetrieve.split("\\|");
                    for (String fv: sopFieldsToRetrieveArr){
                        fieldsToRetrieve = LPArray.addValueToArray1D(fieldsToRetrieve, fv);
                    }
                }
                
                UserSop userSop = new UserSop();                               
                
                Object[][] userSops = UserSop.getUserProfileFieldValues( 
                        new String[]{"user_id"}, new Object[]{token.getPersonName()}, fieldsToRetrieve, allUserProcedurePrefix);
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0])){
                    Object[] errMsg = LPFrontEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    Rdbms.closeRdbms();
                    return;
                }
                JSONArray columnNames = new JSONArray(); 
                JSONArray mySops = new JSONArray(); 
                JSONObject mySopsList = new JSONObject();
                JSONArray mySopsListArr = new JSONArray();
                
                JSONObject columns = new JSONObject();
                for (Object[] curSop: userSops){
                    JSONObject sop = new JSONObject();
                    Boolean columnsCreated =false;
                    for (int yProc=0; yProc<userSops[0].length; yProc++){
                        sop.put(fieldsToRetrieve[yProc], curSop[yProc]);
                        if (!columnsCreated){
                            columns.put("column_"+yProc, fieldsToRetrieve[yProc]);
                        }                       
                    }
                    columnsCreated=true;
                    mySops.add(sop);
                }    
                columnNames.add(columns);
                mySopsList.put("columns_names", columnNames);
                mySopsList.put("my_sops", mySops);
                mySopsListArr.add(mySopsList);
                LPFrontEnd.servletReturnSuccess(request, response, mySopsListArr);
                return;
            case "MY_PENDING_SOPS":    
                usProf = new UserProfile();
                allUserProcedurePrefix = LPArray.convertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(token.getUserName()));
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0])){
                    Object[] errMsg = LPFrontEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);   
                    Rdbms.closeRdbms();
                    return;
                }
                fieldsToRetrieve = new String[]{FIELDNAME_SOP_ID, FIELDNAME_SOP_NAME};
                sopFieldsToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_SOP_FIELDS_TO_RETRIEVE);
                if (sopFieldsToRetrieve!=null) {                
                    String[] sopFieldsToRetrieveArr = sopFieldsToRetrieve.split("\\|");
                    for (String fv: sopFieldsToRetrieveArr){
                        fieldsToRetrieve = LPArray.addValueToArray1D(fieldsToRetrieve, fv);
                    }
                }
                JSONArray  myPendingSopsByProc = new JSONArray();                 
                userSop = new UserSop();      
                for (String currProc: allUserProcedurePrefix) {                   
                    
                    Object[][] userProcSops = userSop.getNotCompletedUserSOP(token.getPersonName(), currProc, fieldsToRetrieve);
                    if (userProcSops.length>0){
                        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(Arrays.toString(userProcSops[0]))){
                            Object[] errMsg = LPFrontEnd.responseError(userProcSops, language, null);
                            response.sendError((int) errMsg[0], (String) errMsg[1]);    
                            Rdbms.closeRdbms();
                            return; 
                        }
                        mySops = new JSONArray(); 
                        mySopsList = new JSONObject();

                        for (Object[] userProcSop : userProcSops) {                                                
                            JSONObject sop = new JSONObject();
                            for (int yProc = 0; yProc<userProcSops[0].length; yProc++) {
                                sop.put(fieldsToRetrieve[yProc], userProcSop[yProc]);
                            }
                            mySops.add(sop);
                        }    
                        mySopsList.put("pending_sops", mySops);
                        mySopsList.put("procedure_name", currProc);
                        myPendingSopsByProc.add(mySopsList);
                    }
                }                
                LPFrontEnd.servletReturnSuccess(request, response, myPendingSopsByProc);
                return;
            case "PROCEDURE_SOPS":    
                usProf = new UserProfile();
                allUserProcedurePrefix = LPArray.convertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(token.getUserName()));
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0])){
                    Object[] errMsg = LPFrontEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    Rdbms.closeRdbms();
                    return;
                }
                fieldsToRetrieve = new String[]{FIELDNAME_SOP_ID, FIELDNAME_SOP_NAME};
                sopFieldsToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_SOP_FIELDS_TO_RETRIEVE);
                if (sopFieldsToRetrieve!=null) {                
                    String[] sopFieldsToRetrieveArr = sopFieldsToRetrieve.split("\\|");
                    for (String fv: sopFieldsToRetrieveArr){
                        fieldsToRetrieve = LPArray.addValueToArray1D(fieldsToRetrieve, fv);
                    }
                }
                myPendingSopsByProc = new JSONArray();                 
                userSop = new UserSop();      
                for (String currProc: allUserProcedurePrefix) {                   
                    Object[][] procSops = Rdbms.getRecordFieldsByFilter(currProc+"-config", "sop_meta_data", 
                            new String[]{"sop_id is not null"}, null, fieldsToRetrieve);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(Arrays.toString(procSops[0]))){
                        Object[] errMsg = LPFrontEnd.responseError(procSops, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms();
                        return;
                    }
                    mySops = new JSONArray(); 
                    mySopsList = new JSONObject();
                    if ( (procSops.length>0) &&
                         (!LPPlatform.LAB_FALSE.equalsIgnoreCase(procSops[0][0].toString())) ){
                            for (Object[] procSop : procSops) {                                                
                                JSONObject sop = new JSONObject();
                                for (int yProc = 0; yProc<procSops[0].length; yProc++) {
                                    sop.put(fieldsToRetrieve[yProc], procSop[yProc]);
                                }
                                mySops.add(sop);
                            }    
                    }
                    mySopsList.put("procedure_sops", mySops);
                    mySopsList.put("procedure_name", currProc);
                    myPendingSopsByProc.add(mySopsList);
                }                
                response.getWriter().write(myPendingSopsByProc.toString());                    
                LPFrontEnd.servletReturnSuccess(request, response, myPendingSopsByProc);
                return;
            case "SOP_TREE_LIST_ELEMENT":
                usProf = new UserProfile();
                allUserProcedurePrefix = LPArray.convertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(token.getUserName()));
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0])){
                    Object[] errMsg = LPFrontEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);   
                    Rdbms.closeRdbms();
                    return;
                }     
                Integer numPendingSOPs = 0;
                fieldsToRetrieve = new String[]{FIELDNAME_SOP_ID};
                for (String curProc: allUserProcedurePrefix){
                    userSop = new UserSop();  
                    Object[][] userProcSops = userSop.getNotCompletedUserSOP(token.getPersonName(), curProc, fieldsToRetrieve);       
                    if ( (userProcSops.length>0) &&
                       (!LPPlatform.LAB_FALSE.equalsIgnoreCase(userProcSops[0][0].toString())) ){
                            numPendingSOPs=numPendingSOPs+userProcSops.length;}                                                    
                }
                   JSONArray sopOptions = new JSONArray(); 
                    
                    JSONObject sopOption = new JSONObject();
                    sopOption.put(JSON_TAG_NAME, "AllMySOPs");
                    sopOption.put(JSON_TAG_LABEL_EN, "All my SOPs");
                    sopOption.put(JSON_TAG_LABEL_ES, "Todos Mis PNTs");
                    sopOption.put(JSON_TAG_WINDOWS_URL, JSON_TAG_VALUE_WINDOWS_URL_HOME);
                    sopOption.put(JSON_TAG_MODE, "edit");
                    sopOption.put(JSON_TAG_BRANCH_LEVEL, JSON_TAG_VALUE_BRANCH_LEVEL_LEVEL_1); 
                    sopOption.put(JSON_TAG_TYPE, JSON_TAG_VALUE_TYPE_TREE_LIST);
                    sopOptions.add(sopOption);
                   
                    sopOption = new JSONObject();
                    sopOption.put(JSON_TAG_NAME, "MyPendingSOPs");
                    sopOption.put(JSON_TAG_LABEL_EN, "My Pending SOPs");
                    sopOption.put(JSON_TAG_LABEL_ES, "Mis PNT Pendientes");
                    sopOption.put(JSON_TAG_WINDOWS_URL, JSON_TAG_VALUE_WINDOWS_URL_HOME);
                    sopOption.put(JSON_TAG_MODE, "edit");
                    sopOption.put(JSON_TAG_BRANCH_LEVEL, JSON_TAG_VALUE_BRANCH_LEVEL_LEVEL_1);
                    sopOption.put(JSON_TAG_BADGE, numPendingSOPs);
                    sopOption.put(JSON_TAG_TYPE, JSON_TAG_VALUE_TYPE_TREE_LIST);
                    sopOptions.add(sopOption);
                    
                    sopOption = new JSONObject();
                    sopOption.put(JSON_TAG_NAME, "ProcSOPs");
                    sopOption.put(JSON_TAG_LABEL_EN, "Procedure SOPs");
                    sopOption.put(JSON_TAG_LABEL_ES, "PNTs del proceso");
                    sopOption.put(JSON_TAG_WINDOWS_URL, JSON_TAG_VALUE_WINDOWS_URL_HOME);
                    sopOption.put(JSON_TAG_MODE, "edit");
                    sopOption.put(JSON_TAG_BRANCH_LEVEL, JSON_TAG_VALUE_BRANCH_LEVEL_LEVEL_1);
                    sopOption.put(JSON_TAG_TYPE, JSON_TAG_VALUE_TYPE_TREE_LIST);
                    sopOptions.add(sopOption);
                    
                    JSONObject sopElement = new JSONObject();
                    sopElement.put(JSON_TAG_DEFINITION, sopOptions);
                    sopElement.put(JSON_TAG_NAME, "SOP");
                    sopElement.put(JSON_TAG_VERSION, "1");
                    sopElement.put(JSON_TAG_LABEL_EN, "SOPs");
                    sopElement.put(JSON_TAG_LABEL_ES, "P.N.T.");
                    sopElement.put(JSON_TAG_SCHEMA_PREFIX, "process-us");
                    
                    JSONArray arrFinal = new JSONArray();
                    arrFinal.add(sopElement);                    
                    LPFrontEnd.servletReturnSuccess(request, response, arrFinal);
                    return;
            default:                
                    LPFrontEnd.servletReturnResponseError(request, response, LPPlatform.API_ERRORTRAPING_PROPERTY_ENDPOINT_NOT_FOUND, new Object[]{actionName, this.getServletName()}, language);              
            }
        }catch(Exception e){
            String errMessage = e.getMessage();
            String[] errObject = new String[0];
            errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
            errObject = LPArray.addValueToArray1D(errObject, "This call raised one unhandled exception. Error:"+errMessage);     
            Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
            response.sendError((int) errMsg[0], (String) errMsg[1]);    
            Rdbms.closeRdbms();        
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
         }catch(ServletException|IOException e){Logger.getLogger(e.getMessage());}
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