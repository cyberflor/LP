/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPDate;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPHttp;
import LabPLANET.utilities.LPSession;
import com.auth0.jwt.exceptions.JWTCreationException;
import databases.Rdbms;
import databases.Token;
import static databases.Token.TOKEN_PARAM_INTERNAL_USERID;
import static databases.Token.TOKEN_PARAM_USERDB;
import static databases.Token.TOKEN_PARAM_USERPW;
import static databases.Token.TOKEN_PARAM_USER_ESIGN;
import functionalJava.testingScripts.LPTestingOutFormat;
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
import functionalJava.user.UserSecurity;
import java.sql.SQLException;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import com.google.gson.Gson;
/**
 *
 * @author Administrator
 */
public class authenticationAPI extends HttpServlet {
    
    public static final String ERRORMSG_ERROR_STATUS_CODE="Error Status Code";
    public static final String ERRORMSG_MANDATORY_PARAMS_MISSING="API Error Message: There are mandatory params for this API method not being passed";
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
        request=LPHttp.requestPreparation(request);
        response=LPHttp.responsePreparation(response);

        String language = "en";
    
        try (PrintWriter out = response.getWriter()) {            
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};            

            String[] mandatoryParamsAction = new String[]{"actionName"};            
            Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;                
            }                          
            String actionName = request.getParameter("actionName");
                                    
            switch (actionName.toUpperCase()){
                case "AUTHENTICATE":
                    mandatoryParamsAction = new String[]{"dbUserName"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "dbUserPassword");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                          
                    String dbUserName = request.getParameter("dbUserName");                   
                    String dbUserPassword = request.getParameter("dbUserPassword");       
                    
                    boolean isConnected = false;
                    
                    //isConnected = Rdbms.getRdbms().startRdbmsTomcat(dbUserName, dbUserPassword);           
                    isConnected = Rdbms.getRdbms().startRdbms(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW);      
                    if (!isConnected){                            
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);   
                        Rdbms.closeRdbms(); 
                        return ;                                 
                    }
                    Role rol = new Role();
                    Object[][] internalUser = rol.getInternalUser(dbUserName);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(internalUser[0][0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, "API Error Message: Person does not exist or password incorrect.");                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                        Rdbms.closeRdbms(); 
                        return ;                                 
                    }
                    
                    UserSecurity usSec = new UserSecurity();
                    Object[] validUserPassword = usSec.isValidUserPassword(dbUserName, dbUserPassword);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(validUserPassword[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
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
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "dbUserPassword");
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "userInfoId");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                              
                    dbUserName = request.getParameter("dbUserName");                   
                    dbUserPassword = request.getParameter("dbUserPassword");      
                    String userInfoId = request.getParameter("userInfoId");      
                    
                    isConnected = Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword);                    
                    if (!isConnected){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);      
                        Rdbms.closeRdbms(); 
                        return ;  
                    }                    
                    
                    UserProfile usProf = new UserProfile();
                    Object[] allUserProcedurePrefix = usProf.getAllUserProcedurePrefix(dbUserName);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                        Object[] errMsg = LPFrontEnd.responseError(allUserProcedurePrefix, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }                    
                    
                    Object[] allUserProcedureRoles = usProf.getProcedureUserProfileFieldValues(allUserProcedurePrefix, userInfoId);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedureRoles[0].toString())){
                        Object[] errMsg = LPFrontEnd.responseError(allUserProcedureRoles, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }                    
                    JSONArray jArray= new JSONArray();
                    jArray.addAll(Arrays.asList(allUserProcedureRoles));        
                    response.getWriter().write(jArray.toJSONString()); 

                    Rdbms.closeRdbms();                     
                    if (1==1) return;
                    
                    Object[][] recordFieldsByFilter = Rdbms.getRecordFieldsByFilter("config", "user_profile", 
                                new String[]{"user_info_id"}, new Object[]{userInfoId}, new String[]{"role_id"});
                        //Object[] recordFieldsByFilter1D =  LPArray.array2dTo1d(recordFieldsByFilter);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(recordFieldsByFilter[0][0].toString())){
                        Object[] errMsg = LPFrontEnd.responseError(LPArray.array2dTo1d(recordFieldsByFilter), language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);         
                        Rdbms.closeRdbms();    
                        return;
                    }
                    Object[] recordsFieldsByFilter1D = LPArray.array2dTo1d(recordFieldsByFilter);                    
                    
                     jArray= new JSONArray();
                    for (Object[] recordFieldsByFilter1 : recordFieldsByFilter) {
                        for (int j = 0; j < recordFieldsByFilter[0].length; j++) {
                            jArray.add(recordFieldsByFilter1[j]);
                        }
                    }        
                    response.getWriter().write(jArray.toJSONString());   
                    Rdbms.closeRdbms();    
                    return;                                
                case "FINALTOKEN":     
                    mandatoryParamsAction = new String[]{"myToken"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "userRole");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                              
                    String firstToken = request.getParameter("myToken");                   
                    String userRole = request.getParameter("userRole");                      
 if (1!=1){
     JSONObject obj = new JSONObject();
     obj.put("appSessionId", "12");
     obj.put("appSessionStartDate", LPDate.getTimeStampLocalDate().toString());
     obj.put("finalToken", "eyJ1c2VyREIiOiJsYWJwbGFuZXQiLCJ0eXAiOiJKV1QiLCJ1c2VyUm9sZSI6ImNvb3JkaW5hdG9yIiwidXNlckRCUGFzc3dvcmQiOiJMYWJQbGFuZXQiLCJhbGciOiJIUzI1NiIsImludGVybmFsVXNlcklEIjoiMSJ9.eyJpc3MiOiJMYWJQTEFORVRkZXN0cmFuZ2lzSW5UaGVOaWdodCJ9.4_dqXo8ebPx6Oiyh6Ef3HxhFdmZG8qzZ0oyirgVG7zU");
     response.getWriter().write(obj.toString());
     return;
 }        
                    token = new Token();
                    String[] tokenParams = token.tokenParamsList();
                    String[] tokenParamsValues = token.validateToken(firstToken, tokenParams);
                    
                    String userName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USERDB)];
                    String password = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USERPW)];
                    internalUserStr = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_INTERNAL_USERID)];
                    //String sessionIdStr = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, "appSessionId")];
                    //String appSessionStartedDate = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, "appSessionStartedDate")];

String sessionIdStr = "";
if (1==1){
    sessionIdStr = "12";
}else{                    
                    String[] fieldsName = new String[]{"person", "role_name"};
                    Object[] fieldsValue = new Object[]{internalUserStr, userRole};
                    Object[] newAppSession = LPSession.newAppSession(fieldsName, fieldsValue);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(newAppSession[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, "API Error Message: App Session Id cannot be generated");                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                        Rdbms.closeRdbms(); 
                        return ;                                 
                    }
                    Integer sessionId = Integer.parseInt((String) newAppSession[newAppSession.length-1]);
                    if (sessionId==null){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, "API Error Message: App Session Id cannot be null");                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                        Rdbms.closeRdbms(); 
                        return ;                            
                    }
}                    
                    Date nowLocalDate = LPDate.getTimeStampLocalDate();
                    
//                    Object[][] userEsignInfo = Rdbms.getRecordFieldsByFilter("app", "users", new String[]{"user_name"}, new Object[]{userName}, new String[]{"e_sign"});
                    Object[][] userEsignInfo = new Object[1][1];
                    userEsignInfo[0][0]="mala";
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(userEsignInfo[0][0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, "API Error Message: eSign Info not available");                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
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
                case "TOKEN_VALIDATE_ESIGN_PHRASE":     
                    mandatoryParamsAction = new String[]{"myToken"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "esignPhraseToCheck");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                              
                    myToken = request.getParameter("myToken");                   
                    String esignPhraseToCheck = request.getParameter("esignPhraseToCheck");                      

                    token = new Token();
                    tokenParams = token.tokenParamsList();
                    tokenParamsValues = token.validateToken(myToken, tokenParams);
                    
                    String tokenEsign = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USER_ESIGN)];
                    if(esignPhraseToCheck.equals(tokenEsign)){
                        Response.ok().build();                                        
                    }else{
                        Object[] errMsg = LPFrontEnd.responseError(new Object[]{"The phrase provided, "+esignPhraseToCheck+", does not match the one contained in this Token."}, language, "");
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }
                    return;
                case "TOKEN_VALIDATE_USER_CREDENTIALS":     
                    mandatoryParamsAction = new String[]{"myToken"};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "userToCheck");
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "passwordToCheck");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                              
                    myToken = request.getParameter("myToken");                   
                    String userToCheck = request.getParameter("userToCheck");                      
                    String passwordToCheck = request.getParameter("passwordToCheck");                      
                    
                    token = new Token();
                    tokenParams = token.tokenParamsList();
                    tokenParamsValues = token.validateToken(myToken, tokenParams);
                    
                    String tokenUser = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USERDB)];
                    String tokenPassword = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USERPW)];
                    if ( (userToCheck.equals(tokenUser)) && (passwordToCheck.equals(tokenPassword)) ){
                        Response.ok().build();                                        
                    }else{
                        Object[] errMsg = LPFrontEnd.responseError(new Object[]{"The user provided, "+userToCheck+", or the password does not match the one contained in this Token."}, language, "");
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }
                    return;                    
                default:      
                    Rdbms.closeRdbms();
                    Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);                            
            }
        }catch(IOException | JWTCreationException e){            
            String exceptionMessage = e.getMessage();            
            Object[] errMsg = LPFrontEnd.responseError(new String[]{exceptionMessage}, language, null);
            response.sendError((int) errMsg[0], (String) errMsg[1]);                                        
            Rdbms.closeRdbms();        
        }catch(Exception e){            
            String exceptionMessage = e.getMessage();            
            Object[] errMsg = LPFrontEnd.responseError(new String[]{exceptionMessage}, language, null);
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