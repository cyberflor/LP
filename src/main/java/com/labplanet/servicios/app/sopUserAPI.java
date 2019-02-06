/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETFrontEnd;
import databases.Rdbms;
import databases.Token;
import functionalJava.sop.UserSop;
import functionalJava.user.UserProfile;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
public class sopUserAPI extends HttpServlet {

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

        String language = "en";
        
        LabPLANETFrontEnd labFrEnd = new LabPLANETFrontEnd();
        LabPLANETArray labArr = new LabPLANETArray();
        Rdbms rdbm = new Rdbms();            
        //ResponseEntity<String> responsew;
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");        
    
        try (PrintWriter out = response.getWriter()) {
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};                        
            
            String finalToken = request.getParameter("finalToken");                      

            if (finalToken==null) {
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: finalToken is one mandatory param for this API");                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);   
                rdbm.closeRdbms(); 
                return ;
            }                    

            Token token = new Token();
            String[] tokenParams = token.tokenParamsList();
            String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

            String dbUserName = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDB")];
            String dbUserPassword = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDBPassword")];         
            String internalUserID = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "internalUserID")];         
            String userRole = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userRole")];         
                        
            boolean isConnected = false;
            isConnected = rdbm.startRdbms(dbUserName, dbUserPassword);
            if (!isConnected){
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);  
                rdbm.closeRdbms(); 
                return ;               
            }            
        
            String actionName = request.getParameter("actionName");
            if (actionName==null) {
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: actionName is one mandatory param for this API");                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                rdbm.closeRdbms(); 
                return ;
            }           
/*            String schemaPrefix = request.getParameter("schemaPrefix");
            if (actionName==null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: schemaPrefix is one mandatory param for this API");                    
                out.println(Arrays.toString(errObject));
                return ;
            }           
*/            
            switch (actionName.toUpperCase()){
            case "MY_SOPS":    
                UserProfile usProf = new UserProfile();
                String[] allUserProcedurePrefix = labArr.ConvertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(rdbm, dbUserName));
                if ("LABPLANET_FALSE".equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                    Object[] errMsg = labFrEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    rdbm.closeRdbms(); 
                    return;
                }
                String[] fieldsToRetrieve = new String[]{"sop_id", "sop_name"};
                String sopFieldsToRetrieve = request.getParameter("sopFieldsToRetrieve");
                if (sopFieldsToRetrieve!=null) {                
                    String[] sopFieldsToRetrieveArr = sopFieldsToRetrieve.split("\\|");
                    for (String fv: sopFieldsToRetrieveArr){
                        fieldsToRetrieve = labArr.addValueToArray1D(fieldsToRetrieve, fv);
                    }
                }
                
                UserSop userSop = new UserSop();                               
                
                Object[][] userSops = userSop.getUserProfileFieldValues(rdbm, 
                        new String[]{"user_id"}, new Object[]{internalUserID}, fieldsToRetrieve, allUserProcedurePrefix);
                if ("LABPLANET_FALSE".equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                    Object[] errMsg = labFrEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    rdbm.closeRdbms();
                    return;
                }
                JSONArray columnNames = new JSONArray(); 
                JSONArray mySops = new JSONArray(); 
                JSONObject mySopsList = new JSONObject();
                
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
                mySopsList.put("my_sops", mySops);

                Response.ok().build();
                response.getWriter().write(mySopsList.toString());      
                rdbm.closeRdbms(); 
                return;
            case "MY_PENDING_SOPS":    
                usProf = new UserProfile();
                allUserProcedurePrefix = labArr.ConvertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(rdbm, dbUserName));
                if ("LABPLANET_FALSE".equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                    Object[] errMsg = labFrEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);   
                    rdbm.closeRdbms();
                    return;
                }
                fieldsToRetrieve = new String[]{"sop_id", "sop_name"};
                sopFieldsToRetrieve = request.getParameter("sopFieldsToRetrieve");
                if (sopFieldsToRetrieve!=null) {                
                    String[] sopFieldsToRetrieveArr = sopFieldsToRetrieve.split("\\|");
                    for (String fv: sopFieldsToRetrieveArr){
                        fieldsToRetrieve = labArr.addValueToArray1D(fieldsToRetrieve, fv);
                    }
                }
                JSONArray  myPendingSopsByProc = new JSONArray();                 
                userSop = new UserSop();      
                for (String currProc: allUserProcedurePrefix) {                   
                    
                    Object[][] userProcSops = userSop.getNotCompletedUserSOP(rdbm, internalUserID, currProc, fieldsToRetrieve);
                    //userSops = userSop.getUserProfileFieldValues(rdbm, 
                    //        new String[]{"user_id"}, new Object[]{internalUserID}, fieldsToRetrieve, allUserProcedurePrefix);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(userProcSops[0].toString())){
                        Object[] errMsg = labFrEnd.responseError(userProcSops, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms();
                        return; 
                    }
                    mySops = new JSONArray(); 
                    mySopsList = new JSONObject();

                    //for (Object curProcSop: userProcSops){
                    for (int xProc=0; xProc<userProcSops.length; xProc++){                                                
                        JSONObject sop = new JSONObject();
                        for (int yProc=0; yProc<userProcSops[0].length; yProc++){
                            sop.put(fieldsToRetrieve[yProc], userProcSops[xProc][yProc]);
                        }
                        mySops.add(sop);
                    }    
                    mySopsList.put("pending_sops", mySops);
                    mySopsList.put("procedure_name", currProc);
                    myPendingSopsByProc.add(mySopsList);
                }                
                rdbm.closeRdbms();
                Response.ok().build();
                response.getWriter().write(myPendingSopsByProc.toString());                    
                return;
            case "PROCEDURE_SOPS":    
                usProf = new UserProfile();
                allUserProcedurePrefix = labArr.ConvertObjectArrayToStringArray(usProf.getAllUserProcedurePrefix(rdbm, dbUserName));
                if ("LABPLANET_FALSE".equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                    Object[] errMsg = labFrEnd.responseError(allUserProcedurePrefix, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    rdbm.closeRdbms();
                    return;
                }
                fieldsToRetrieve = new String[]{"sop_id", "sop_name"};
                sopFieldsToRetrieve = request.getParameter("sopFieldsToRetrieve");
                if (sopFieldsToRetrieve!=null) {                
                    String[] sopFieldsToRetrieveArr = sopFieldsToRetrieve.split("\\|");
                    for (String fv: sopFieldsToRetrieveArr){
                        fieldsToRetrieve = labArr.addValueToArray1D(fieldsToRetrieve, fv);
                    }
                }
                myPendingSopsByProc = new JSONArray();                 
                userSop = new UserSop();      
                for (String currProc: allUserProcedurePrefix) {                   
                    Object[][] procSops = rdbm.getRecordFieldsByFilter(rdbm, currProc+"-config", "sop_meta_data", 
                            new String[]{"sop_id is not null"}, null, fieldsToRetrieve);
                    //Object[][] procSops = userSop.getNotCompletedUserSOP(rdbm, internalUserID, currProc, fieldsToRetrieve);
                    //userSops = userSop.getUserProfileFieldValues(rdbm, 
                    //        new String[]{"user_id"}, new Object[]{internalUserID}, fieldsToRetrieve, allUserProcedurePrefix);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(procSops[0].toString())){
                        Object[] errMsg = labFrEnd.responseError(procSops, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms();
                        return;
                    }
                    mySops = new JSONArray(); 
                    mySopsList = new JSONObject();

                    //for (Object curProcSop: userProcSops){
                    for (int xProc=0; xProc<procSops.length; xProc++){                                                
                        JSONObject sop = new JSONObject();
                        for (int yProc=0; yProc<procSops[0].length; yProc++){
                            sop.put(fieldsToRetrieve[yProc], procSops[xProc][yProc]);
                        }
                        mySops.add(sop);
                    }    
                    mySopsList.put("procedure_sops", mySops);
                    mySopsList.put("procedure_name", currProc);
                    myPendingSopsByProc.add(mySopsList);
                }                
                Response.ok().build();
                response.getWriter().write(myPendingSopsByProc.toString());                    
                rdbm.closeRdbms();
                return;
            default:                
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "No action linked to actionName "+actionName+" in this API");                     
                Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                rdbm.closeRdbms();                    
                return;                   
                
            }
        }catch(Exception e){
            String errMessage = e.getMessage();
            String[] errObject = new String[0];
            errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
            errObject = labArr.addValueToArray1D(errObject, "This call raised one unhandled exception. Error:"+errMessage);     
            Object[] errMsg = labFrEnd.responseError(errObject, language, null);
            response.sendError((int) errMsg[0], (String) errMsg[1]);    
            rdbm.closeRdbms();        
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
