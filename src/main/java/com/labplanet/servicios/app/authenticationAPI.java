/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LPPlatform;
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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {
        response = LabPLANETRequest.responsePreparation(response);
        request = LabPLANETRequest.requestPreparation(request);               

        String language = "en";

        try (PrintWriter out = response.getWriter()) {            
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};            

            String[] mandatoryParamsAction = new String[]{"actionName"};            
            Object[] areMandatoryParamsInResponse = LabPLANETRequest.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                
                errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;                
            }                          
            String actionName = request.getParameter("actionName");
                                    
            switch (actionName.toUpperCase()){
                case "AUTHENTICATE":
                    mandatoryParamsAction = new String[]{"dbUserName"};
                    mandatoryParamsAction = LabPLANETArray.addValueToArray1D(mandatoryParamsAction, "dbUserPassword");
                    areMandatoryParamsInResponse = LabPLANETRequest.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                          
                    String dbUserName = request.getParameter("dbUserName");                   
                    String dbUserPassword = request.getParameter("dbUserPassword");       
                    
                    if (Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword)==null){                      
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);   
                        Rdbms.closeRdbms(); 
                        return ;                                 
                    }
                    Role rol = new Role();
                    Object[][] internalUser = rol.getInternalUser(dbUserName);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(internalUser[0][0].toString())){
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: Person does not exist or password incorrect.");                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                        Rdbms.closeRdbms(); 
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
                    Rdbms.closeRdbms();
                    return;
                case "GETUSERROLE":         
                    mandatoryParamsAction = new String[]{"dbUserName"};
                    mandatoryParamsAction = LabPLANETArray.addValueToArray1D(mandatoryParamsAction, "dbUserPassword");
                    mandatoryParamsAction = LabPLANETArray.addValueToArray1D(mandatoryParamsAction, "userInfoId");
                    areMandatoryParamsInResponse = LabPLANETRequest.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                              
                    dbUserName = request.getParameter("dbUserName");                   
                    dbUserPassword = request.getParameter("dbUserPassword");      
                    String userInfoId = request.getParameter("userInfoId");      
                    
                    if (Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword)==null){
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);      
                        Rdbms.closeRdbms(); 
                        return ;  
                    }                    
                    
                    UserProfile usProf = new UserProfile();
                    Object[] allUserProcedurePrefix = usProf.getAllUserProcedurePrefix(dbUserName);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                        Object[] errMsg = LabPLANETFrontEnd.responseError(allUserProcedurePrefix, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }                    
                    
                    Object[] allUserProcedureRoles = usProf.getProcedureUserProfileFieldValues(allUserProcedurePrefix, userInfoId);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedureRoles[0].toString())){
                        Object[] errMsg = LabPLANETFrontEnd.responseError(allUserProcedureRoles, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }                    
                    JSONArray jArray= new JSONArray();
                    jArray.addAll(Arrays.asList(allUserProcedureRoles));        
                    System.out.println(jArray);
                    response.getWriter().write(jArray.toJSONString()); 

                    Rdbms.closeRdbms();                     
                    if (1==1) return;
                    
                    Object[][] recordFieldsByFilter = Rdbms.getRecordFieldsByFilter("config", "user_profile", 
                                new String[]{"user_info_id"}, new Object[]{userInfoId}, new String[]{"role_id"});
                        //Object[] recordFieldsByFilter1D =  LabPLANETArray.array2dTo1d(recordFieldsByFilter);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(recordFieldsByFilter[0][0].toString())){
                        Object[] errMsg = LabPLANETFrontEnd.responseError(LabPLANETArray.array2dTo1d(recordFieldsByFilter), language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                        return;
                    }
                    Object[] recordsFieldsByFilter1D = LabPLANETArray.array2dTo1d(recordFieldsByFilter);                    
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
                    mandatoryParamsAction = LabPLANETArray.addValueToArray1D(mandatoryParamsAction, "userRole");
                    areMandatoryParamsInResponse = LabPLANETRequest.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                              
                    String firstToken = request.getParameter("myToken");                   
                    String userRole = request.getParameter("userRole");                      

                    token = new Token();
                    String[] tokenParams = token.tokenParamsList();
                    String[] tokenParamsValues = token.validateToken(firstToken, tokenParams);
                    
                    String userName = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "userDB")];
                    String password = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "userDBPassword")];
                    internalUserStr = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "internalUserID")];
                    //String sessionIdStr = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "appSessionId")];
                    //String appSessionStartedDate = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "appSessionStartedDate")];

String sessionIdStr = "";
if (1==1){
    sessionIdStr = "12";
}else{                    
                    String[] fieldsName = new String[]{"person", "role_name"};
                    Object[] fieldsValue = new Object[]{internalUserStr, userRole};
                    Object[] newAppSession = LabPLANETSession.newAppSession(fieldsName, fieldsValue);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(newAppSession[0].toString())){
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: App Session Id cannot be generated");                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                        Rdbms.closeRdbms(); 
                        return ;                                 
                    }
                    Integer sessionId = Integer.parseInt((String) newAppSession[newAppSession.length-1]);
                    if (sessionId==null){
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: App Session Id cannot be null");                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                        Rdbms.closeRdbms(); 
                        return ;                            
                    }
}                    
                    Date nowLocalDate = LabPLANETDate.getTimeStampLocalDate();
                    
//                    Object[][] userEsignInfo = Rdbms.getRecordFieldsByFilter(rdbm, "app", "users", new String[]{"user_name"}, new Object[]{userName}, new String[]{"e_sign"});
                    Object[][] userEsignInfo = new Object[1][1];
                    userEsignInfo[0][0]="mala";
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(userEsignInfo[0][0].toString())){
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: eSign Info not available");                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                        Rdbms.closeRdbms(); 
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
                    Rdbms.closeRdbms();
                    return;
                default:      
                    Rdbms.closeRdbms();
                    Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);                            
            }
        }catch(Exception e){            
            String exceptionMessage = e.getMessage();            
            Object[] errMsg = LabPLANETFrontEnd.responseError(new String[]{exceptionMessage}, language, null);
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
