/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETDate;
import LabPLANET.utilities.LabPLANETFrontEnd;
import LabPLANET.utilities.LabPLANETRequest;
import LabPLANET.utilities.LabPLANETSession;
import databases.Rdbms;
import databases.Token;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import functionalJava.user.Role;
import functionalJava.user.UserProfile;
import java.util.Date;
import java.util.ResourceBundle;
import org.json.simple.JSONArray;
//import com.google.gson.Gson;
/**
 *
 * @author Administrator
 */
public class authenticationAPI extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //public enum actionsList {    AUTHENTICATE,    GETUSERROLE,    LOW    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                request.setCharacterEncoding("UTF-8");

                ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");
                String frontendUrl = prop.getString("frontend_url");
      
                response.setHeader("Access-Control-Allow-Origin", frontendUrl);
                response.setHeader("Access-Control-Allow-Methods", "GET");        
      
        String language = "en";

                LabPLANETArray labArr = new LabPLANETArray();
                LabPLANETFrontEnd labFrEnd = new LabPLANETFrontEnd();
                LabPLANETRequest labReq = new  LabPLANETRequest();

                Rdbms rdbm = new Rdbms();     
    
        try (PrintWriter out = response.getWriter()) {            
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};            

            String[] mandatoryParamsAction = new String[]{"actionName"};            
            Object[] areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
            if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                rdbm.closeRdbms(); 
                return ;                
            }                          
            String actionName = request.getParameter("actionName");
                                    
            switch (actionName.toUpperCase()){
                case "AUTHENTICATE":
                    mandatoryParamsAction = new String[]{"dbUserName"};
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParamsAction, "dbUserPassword");
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                          
                    String dbUserName = request.getParameter("dbUserName");                   
                    String dbUserPassword = request.getParameter("dbUserPassword");       
                    
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
                    Role rol = new Role();
                    Object[][] internalUser = rol.getInternalUser(rdbm, dbUserName);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(internalUser[0][0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: Person does not exist or password incorrect.");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                        rdbm.closeRdbms(); 
                        return ;                                 
                    }
                    
                    Token token = new Token();
                    String internalUserStr = internalUser[0][0].toString();
                    String myToken = token.createToken(dbUserName, dbUserPassword, internalUserStr, "Admin", "", "", "");                    
                    
                    JsonObject json = Json.createObjectBuilder()
                            .add("userInfoId", internalUserStr)
                            .add("myToken", myToken)
                            .build();                                                
                    response.getWriter().write(json.toString());                                        
                    
                    Response.ok().build();    
                    rdbm.closeRdbms();
                    return;
                case "GETUSERROLE":         
                    mandatoryParamsAction = new String[]{"dbUserName"};
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParamsAction, "dbUserPassword");
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParamsAction, "userInfoId");
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                                              
                    dbUserName = request.getParameter("dbUserName");                   
                    dbUserPassword = request.getParameter("dbUserPassword");      
                    String userInfoId = request.getParameter("userInfoId");      
                    
                    isConnected = rdbm.startRdbms(dbUserName, dbUserPassword);                    
                    if (!isConnected){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);      
                        rdbm.closeRdbms(); 
                        return ;  
                    }                    
                    
                    UserProfile usProf = new UserProfile();
                    Object[] allUserProcedurePrefix = usProf.getAllUserProcedurePrefix(rdbm, dbUserName);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                        Object[] errMsg = labFrEnd.responseError(allUserProcedurePrefix, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }                    
                    
                    Object[] allUserProcedureRoles = usProf.getProcedureUserProfileFieldValues(rdbm, allUserProcedurePrefix, userInfoId);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(allUserProcedureRoles[0].toString())){
                        Object[] errMsg = labFrEnd.responseError(allUserProcedureRoles, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }                    
                    JSONArray jArray= new JSONArray();
                    jArray.addAll(Arrays.asList(allUserProcedureRoles));        
                    System.out.println(jArray);
                    response.getWriter().write(jArray.toJSONString()); 

                    rdbm.closeRdbms();                     
                    if (1==1) return;
                    
                    Object[][] recordFieldsByFilter = rdbm.getRecordFieldsByFilter(rdbm, "config", "user_profile", 
                                new String[]{"user_info_id"}, new Object[]{userInfoId}, new String[]{"role_id"});
                        //Object[] recordFieldsByFilter1D =  labArr.array2dTo1d(recordFieldsByFilter);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(recordFieldsByFilter[0][0].toString())){
                        Object[] errMsg = labFrEnd.responseError(labArr.array2dTo1d(recordFieldsByFilter), language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                        return;
                    }
                    Object[] recordsFieldsByFilter1D = labArr.array2dTo1d(recordFieldsByFilter);                    
                    System.out.println(Arrays.toString(recordsFieldsByFilter1D));
                    
                     jArray= new JSONArray();
                    for (Object[] recordFieldsByFilter1 : recordFieldsByFilter) {
                        for (int j = 0; j < recordFieldsByFilter[0].length; j++) {
                            jArray.add(recordFieldsByFilter1[j]);
                        }
                    }        
                    System.out.println(jArray);
                    response.getWriter().write(jArray.toJSONString());                                
                    return;                                
                case "FINALTOKEN":     
                    mandatoryParamsAction = new String[]{"myToken"};
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParamsAction, "userRole");
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                                              
                    String firstToken = request.getParameter("myToken");                   
                    String userRole = request.getParameter("userRole");                      

                    token = new Token();
                    String[] tokenParams = token.tokenParamsList();
                    String[] tokenParamsValues = token.validateToken(firstToken, tokenParams);
                    
                    String userName = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDB")];
                    String password = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDBPassword")];
                    internalUserStr = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "internalUserID")];
                    //String sessionIdStr = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "appSessionId")];
                    //String appSessionStartedDate = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "appSessionStartedDate")];

String sessionIdStr = "";
if (1==1){
    sessionIdStr = "12";
}else{                    
                    LabPLANETSession labSession = new LabPLANETSession();
                    String[] fieldsName = new String[]{"person", "role_name"};
                    Object[] fieldsValue = new Object[]{internalUserStr, userRole};
                    Object[] newAppSession = labSession.newAppSession(rdbm, fieldsName, fieldsValue);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(newAppSession[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: App Session Id cannot be generated");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                        rdbm.closeRdbms(); 
                        return ;                                 
                    }
                    Integer sessionId = Integer.parseInt((String) newAppSession[newAppSession.length-1]);
                    if (sessionId==null){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: App Session Id cannot be null");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                        rdbm.closeRdbms(); 
                        return ;                            
                    }
}                    
                    LabPLANETDate labDate = new LabPLANETDate();
                    Date nowLocalDate = LabPLANETDate.getTimeStampLocalDate();
                    
//                    Object[][] userEsignInfo = rdbm.getRecordFieldsByFilter(rdbm, "app", "users", new String[]{"user_name"}, new Object[]{userName}, new String[]{"e_sign"});
                    Object[][] userEsignInfo = new Object[1][1];
                    userEsignInfo[0][0]="mala";
                    if ("LABPLANET_FALSE".equalsIgnoreCase(userEsignInfo[0][0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: eSign Info not available");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                        rdbm.closeRdbms(); 
                        return ;                                                    
                    }
                    
                    String myFinalToken = token.createToken(userName, password, internalUserStr, userRole, sessionIdStr, nowLocalDate.toString(), userEsignInfo[0][0].toString());
                    
                    json = Json.createObjectBuilder()
                            .add("finalToken", myFinalToken)
                            .add("appSessionId", sessionIdStr)
                            .add("appSessionStartDate", nowLocalDate.toString())
                            .build();                                                
                    response.getWriter().write(json.toString());
                    Response.ok().build();                    
                    Response.status(Response.Status.CREATED).entity(json).build();   
                    rdbm.closeRdbms();
                    return;
                default:      
                    rdbm.closeRdbms();
                    Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);                            
            }
        }catch(Exception e){            
            String exceptionMessage = e.getMessage();            
            Object[] errMsg = labFrEnd.responseError(new String[]{exceptionMessage}, language, null);
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
