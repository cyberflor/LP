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
import functionalJava.testingScripts.LPTestingOutFormat;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import functionalJava.user.UserProfile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import functionalJava.sop.UserSop;
/**
 *
 * @author Administrator
 */
public class sopUserAPI extends HttpServlet {
    public static final String ERRORMSG_ERROR_STATUS_CODE="Error Status Code";
    public static final String ERRORMSG_MANDATORY_PARAMS_MISSING="API Error Message: There are mandatory params for this API method not being passed";

    public static final String FIELDNAME_SOP_ID="sop_id";
    public static final String FIELDNAME_SOP_NAME="sop_name";

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
        
        //ResponseEntity<String> responsew;
        
        try (PrintWriter out = response.getWriter()) {
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};                        
            
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
//            String userRole = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ROLE)];                     
                        
             if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}   
             
            String actionName = request.getParameter("actionName");
            if (actionName==null) {
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: actionName is one mandatory param for this API");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;
            }           
/*            String schemaPrefix = request.getParameter("schemaPrefix");
            if (actionName==null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: schemaPrefix is one mandatory param for this API");                    
                out.println(Arrays.toString(errObject));
                return ;
            }           
*/            
            switch (actionName.toUpperCase()){
            case "ALL_MY_SOPS":    
                UserProfile usProf = new UserProfile();
                String[] allUserProcedurePrefix = LPArray.convertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(dbUserName));
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0])){
                    Object[] errMsg = LPFrontEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    Rdbms.closeRdbms(); 
                    return;
                }
                String[] fieldsToRetrieve = new String[]{FIELDNAME_SOP_ID, FIELDNAME_SOP_NAME};
                String sopFieldsToRetrieve = request.getParameter("sopFieldsToRetrieve");
                if (sopFieldsToRetrieve!=null) {                
                    String[] sopFieldsToRetrieveArr = sopFieldsToRetrieve.split("\\|");
                    for (String fv: sopFieldsToRetrieveArr){
                        fieldsToRetrieve = LPArray.addValueToArray1D(fieldsToRetrieve, fv);
                    }
                }
                
                UserSop userSop = new UserSop();                               
                
                Object[][] userSops = userSop.getUserProfileFieldValues( 
                        new String[]{"user_id"}, new Object[]{internalUserID}, fieldsToRetrieve, allUserProcedurePrefix);
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
                            columns.put("column_"+String.valueOf(yProc), fieldsToRetrieve[yProc]);
                        }                       
                    }
                    columnsCreated=true;
                    mySops.add(sop);
                }    
                columnNames.add(columns);
                mySopsList.put("columns_names", columnNames);
                //mySopsListArr.add(mySopsList);
                //mySopsList.clear();
                mySopsList.put("my_sops", mySops);
                mySopsListArr.add(mySopsList);
                LPFrontEnd.servletReturnSuccess(request, response, mySopsListArr);
                return;
            case "MY_PENDING_SOPS":    
                usProf = new UserProfile();
                allUserProcedurePrefix = LPArray.convertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(dbUserName));
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0])){
                    Object[] errMsg = LPFrontEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);   
                    Rdbms.closeRdbms();
                    return;
                }
                fieldsToRetrieve = new String[]{FIELDNAME_SOP_ID, FIELDNAME_SOP_NAME};
                sopFieldsToRetrieve = request.getParameter("sopFieldsToRetrieve");
                if (sopFieldsToRetrieve!=null) {                
                    String[] sopFieldsToRetrieveArr = sopFieldsToRetrieve.split("\\|");
                    for (String fv: sopFieldsToRetrieveArr){
                        fieldsToRetrieve = LPArray.addValueToArray1D(fieldsToRetrieve, fv);
                    }
                }
                JSONArray  myPendingSopsByProc = new JSONArray();                 
                userSop = new UserSop();      
                for (String currProc: allUserProcedurePrefix) {                   
                    
                    Object[][] userProcSops = userSop.getNotCompletedUserSOP(internalUserID, currProc, fieldsToRetrieve);
                    //userSops = userSop.getUserProfileFieldValues(rdbm, 
                    //        new String[]{"user_id"}, new Object[]{internalUserID}, fieldsToRetrieve, allUserProcedurePrefix);
                    if (userProcSops.length>0){
                        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(Arrays.toString(userProcSops[0]))){
                            Object[] errMsg = LPFrontEnd.responseError(userProcSops, language, null);
                            response.sendError((int) errMsg[0], (String) errMsg[1]);    
                            Rdbms.closeRdbms();
                            return; 
                        }
                        mySops = new JSONArray(); 
                        mySopsList = new JSONObject();

                        //for (Object curProcSop: userProcSops){
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
                allUserProcedurePrefix = LPArray.convertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(dbUserName));
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0])){
                    Object[] errMsg = LPFrontEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    Rdbms.closeRdbms();
                    return;
                }
                fieldsToRetrieve = new String[]{FIELDNAME_SOP_ID, FIELDNAME_SOP_NAME};
                sopFieldsToRetrieve = request.getParameter("sopFieldsToRetrieve");
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
                    //Object[][] procSops = userSop.getNotCompletedUserSOP(rdbm, internalUserID, currProc, fieldsToRetrieve);
                    //userSops = userSop.getUserProfileFieldValues(rdbm, 
                    //        new String[]{"user_id"}, new Object[]{internalUserID}, fieldsToRetrieve, allUserProcedurePrefix);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(Arrays.toString(procSops[0]))){
                        Object[] errMsg = LPFrontEnd.responseError(procSops, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms();
                        return;
                    }
                    mySops = new JSONArray(); 
                    mySopsList = new JSONObject();
                    if (procSops.length>0){
                        if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(procSops[0][0].toString())){
                            //for (Object curProcSop: userProcSops){
                            for (Object[] procSop : procSops) {                                                
                                JSONObject sop = new JSONObject();
                                for (int yProc = 0; yProc<procSops[0].length; yProc++) {
                                    sop.put(fieldsToRetrieve[yProc], procSop[yProc]);
                                }
                                mySops.add(sop);
                            }    
                        }
                    }
                    mySopsList.put("procedure_sops", mySops);
                    mySopsList.put("procedure_name", currProc);
                    myPendingSopsByProc.add(mySopsList);
                }                
                response.getWriter().write(myPendingSopsByProc.toString());                    
                LPFrontEnd.servletReturnSuccess(request, response, myPendingSopsByProc);
                return;
            default:                
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "No action linked to actionName "+actionName+" in this API");                     
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms();                                    
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