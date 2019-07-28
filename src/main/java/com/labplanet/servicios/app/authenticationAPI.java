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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import functionalJava.user.Role;
import functionalJava.user.UserProfile;
import functionalJava.user.UserSecurity;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
        request=LPHttp.requestPreparation(request);
        response=LPHttp.responsePreparation(response);

        String language = LPFrontEnd.setLanguage(request); 
        
        try (PrintWriter out = response.getWriter()) {            
            
            Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, authenticationAPIParams.MANDATORY_PARAMS_MAIN_SERVLET.split("\\|"));                       
           if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                LPFrontEnd.servletReturnResponseError(request, response, 
                        LPPlatform.API_ERRORTRAPING__MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                return;          
            }                        
            String actionName = request.getParameter(authenticationAPIParams.REQUEST_PARAM_ACTION_NAME);
                                    
            switch (actionName.toUpperCase()){
                case authenticationAPIParams.API_ENDPOINT_AUTHENTICATE:                         
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, authenticationAPIParams.MANDATORY_PARAMS_CASE_AUTHENTICATE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING__MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                                    
                    }     
                    String dbUserName = request.getParameter(authenticationAPIParams.REQUEST_PARAM_DB_USERNAME);                   
                    String dbUserPassword = request.getParameter(authenticationAPIParams.REQUEST_PARAM_DB_PASSWORD);  
                    
                    if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}
                   
                    Role rol = new Role();
                    Object[][] internalUser = rol.getInternalUser(dbUserName);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(internalUser[0][0].toString())){               
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_PROPERTY_PERSON_NOT_FOUND, null, language);              
                        return;                                                          
                    }                    
                    UserSecurity usSec = new UserSecurity();
                    Object[] validUserPassword = usSec.isValidUserPassword(dbUserName, dbUserPassword);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(validUserPassword[0].toString())){
                        if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}
                        validUserPassword = usSec.isValidUserPassword(dbUserName, dbUserPassword);
                        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(validUserPassword[0].toString())){     
                            LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_PROPERTY_INVALID_USER_PASSWORD, null, language);              
                            return;                               
                        }
                    }                                                          
                    Token token = new Token();
                    String internalUserStr = internalUser[0][0].toString();
                    String myToken = token.createToken(dbUserName, dbUserPassword, internalUserStr, "Admin", "", "", "");                    

                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put(authenticationAPIParams.RESPONSE_JSON_TAG_USER_INFO_ID, internalUserStr);
                    jsonObj.put(authenticationAPIParams.RESPONSE_JSON_TAG_MY_TOKEN, myToken);
                    Rdbms.closeRdbms();      
                    request.setAttribute(LPPlatform.SERVLETS_REPONSE_SUCCESS_ATTRIBUTE_NAME, jsonObj.toString());
                    RequestDispatcher rd = request.getRequestDispatcher(LPPlatform.SERVLETS_REPONSE_SUCCESS_SERVLET_NAME);
                    rd.forward(request,response);   
                    return;
                case authenticationAPIParams.API_ENDPOINT_GET_USER_ROLE:                                                 
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, authenticationAPIParams.MANDATORY_PARAMS_CASE_GETUSERROLE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING__MANDATORY_PARAMS_MISSING, new Object[] {areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                          
                    }                                             
                    String firstToken = request.getParameter(authenticationAPIParams.REQUEST_PARAM_MY_TOKEN);                   
                    
                    token = new Token();
                    String[] tokenParams = token.tokenParamsList();
                    String[] tokenParamsValues = token.validateToken(firstToken, tokenParams);
                    
                    String userName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USERDB)];
                    String password = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USERPW)];
                    internalUserStr = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_INTERNAL_USERID)];                    
                    dbUserPassword = request.getParameter(authenticationAPIParams.REQUEST_PARAM_DB_PASSWORD);      
                    String userInfoId = request.getParameter(authenticationAPIParams.REQUEST_PARAM_USER_INFO);      
                    
                    if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}             
                    
                    UserProfile usProf = new UserProfile();
                    Object[] allUserProcedurePrefix = usProf.getAllUserProcedurePrefix(userName);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                        LPFrontEnd.servletReturnResponseErrorLPFalseDiagnostic(request, response, allUserProcedurePrefix);
                        return;                                             
                    }                    
                    
                    Object[] allUserProcedureRoles = usProf.getProcedureUserProfileFieldValues(allUserProcedurePrefix, internalUserStr);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedureRoles[0].toString())){            
                        LPFrontEnd.servletReturnResponseErrorLPFalseDiagnostic(request, response, allUserProcedureRoles);
                        return;                                                            
                    }                    
                    JSONArray jArray= new JSONArray();
                    jArray.addAll(Arrays.asList(allUserProcedureRoles));        
                    response.getWriter().write(jArray.toJSONString()); 

                    Rdbms.closeRdbms();                     
                    if (1==1) return;
                    
                    Object[][] recordFieldsByFilter = Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_CONFIG, "user_profile", 
                                new String[]{"user_info_id"}, new Object[]{internalUserStr}, new String[]{"role_id"});
                        //Object[] recordFieldsByFilter1D =  LPArray.array2dTo1d(recordFieldsByFilter);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(recordFieldsByFilter[0][0].toString())){
                        LPFrontEnd.servletReturnResponseErrorLPFalseDiagnostic(request, response, LPArray.array2dTo1d(recordFieldsByFilter));
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
                case authenticationAPIParams.API_ENDPOINT_FINAL_TOKEN:     
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, authenticationAPIParams.MANDATORY_PARAMS_CASE_FINALTOKEN.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING__MANDATORY_PARAMS_MISSING, new Object[] {areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                                               
                    }                                                     
                    firstToken = request.getParameter(authenticationAPIParams.REQUEST_PARAM_MY_TOKEN);                   
                    String userRole = request.getParameter(authenticationAPIParams.REQUEST_PARAM_USER_ROLE);                      
 if (1!=1){
     JSONObject obj = new JSONObject();
     obj.put(   authenticationAPIParams.RESPONSE_JSON_TAG_APP_SESSION_ID, "12");
     obj.put(   authenticationAPIParams.RESPONSE_JSON_TAG_APP_SESSION_DATE, LPDate.getTimeStampLocalDate().toString());
     obj.put(   authenticationAPIParams.RESPONSE_JSON_TAG_FINAL_TOKEN, "eyJ1c2VyREIiOiJsYWJwbGFuZXQiLCJ0eXAiOiJKV1QiLCJ1c2VyUm9sZSI6ImNvb3JkaW5hdG9yIiwidXNlckRCUGFzc3dvcmQiOiJMYWJQbGFuZXQiLCJhbGciOiJIUzI1NiIsImludGVybmFsVXNlcklEIjoiMSJ9.eyJpc3MiOiJMYWJQTEFORVRkZXN0cmFuZ2lzSW5UaGVOaWdodCJ9.4_dqXo8ebPx6Oiyh6Ef3HxhFdmZG8qzZ0oyirgVG7zU");
     response.getWriter().write(obj.toString());
     return;
 }        
                    token = new Token();
                    tokenParams = token.tokenParamsList();
                    tokenParamsValues = token.validateToken(firstToken, tokenParams);
                    
                    userName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USERDB)];
                    password = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USERPW)];
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
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_PROPERTY_SESSION_ID_NOT_GENERATED, null, language);              
                        return;                                                         
                    }
                    
                    Integer sessionId = Integer.parseInt((String) newAppSession[newAppSession.length-1]);
                    if (sessionId==null){
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_PROPERTY_SESSION_ID_NULL_NOT_ALLOWED, null, language);          
                        return;                                                   
                    }
}                    
                    Date nowLocalDate = LPDate.getTimeStampLocalDate();
                    
//                    Object[][] userEsignInfo = Rdbms.getRecordFieldsByFilter("app", "users", new String[]{"user_name"}, new Object[]{userName}, new String[]{"e_sign"});
                    Object[][] userEsignInfo = new Object[1][1];
                    userEsignInfo[0][0]="mala";
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(userEsignInfo[0][0].toString())){  
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_PROPERTY_ESIGN_INFO_NOT_AVAILABLE, null, language);       
                        return;                                                                                
                    }
                               
                    String myFinalToken = token.createToken(userName, password, internalUserStr, userRole, sessionIdStr, nowLocalDate.toString(), userEsignInfo[0][0].toString());

                    Rdbms.closeRdbms();                    
                    jsonObj = new JSONObject();
                    jsonObj.put(authenticationAPIParams.RESPONSE_JSON_TAG_FINAL_TOKEN, myFinalToken);
                    jsonObj.put(authenticationAPIParams.RESPONSE_JSON_TAG_APP_SESSION_ID, sessionIdStr);
                    jsonObj.put(authenticationAPIParams.RESPONSE_JSON_TAG_APP_SESSION_DATE, nowLocalDate.toString());
                    Rdbms.closeRdbms();      
                    request.setAttribute(LPPlatform.SERVLETS_REPONSE_SUCCESS_ATTRIBUTE_NAME, jsonObj.toString());
                    rd = request.getRequestDispatcher(LPPlatform.SERVLETS_REPONSE_SUCCESS_SERVLET_NAME);
                    rd.forward(request,response);   
                    return;                    
                case authenticationAPIParams.API_ENDPOINT_TOKEN_VALIDATE_ESIGN_PHRASE:     
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, authenticationAPIParams.MANDATORY_PARAMS_CASE_TOKEN_VALIDATE_ESIGN_PHRASE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING__MANDATORY_PARAMS_MISSING, new Object[] {areMandatoryParamsInResponse[1].toString()}, language);  
                        return;                                                                                          
                    }                                               
                    myToken = request.getParameter(authenticationAPIParams.REQUEST_PARAM_MY_TOKEN);                   
                    String esignPhraseToCheck = request.getParameter(authenticationAPIParams.REQUEST_PARAM_ESIGN_TO_CHECK);                      

                    token = new Token();
                    tokenParams = token.tokenParamsList();
                    tokenParamsValues = token.validateToken(myToken, tokenParams);
                    
                    String tokenEsign = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USER_ESIGN)];
                    if(esignPhraseToCheck.equals(tokenEsign)){                         
                        Rdbms.closeRdbms();      
                        request.setAttribute(LPPlatform.SERVLETS_REPONSE_SUCCESS_ATTRIBUTE_NAME,"");
                        rd = request.getRequestDispatcher(LPPlatform.SERVLETS_REPONSE_SUCCESS_SERVLET_NAME);
                        rd.forward(request,response);   
                        return;                        
                    }else{               
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_API_ERRORTRAPING_PROPERTY_ESIGN_TO_CHECK_INVALID, new Object[]{esignPhraseToCheck}, language);
                        return;                             
                    }
                    
                case authenticationAPIParams.API_ENDPOINT_TOKEN_VALIDATE_USER_CREDENTIALS:     
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, authenticationAPIParams.MANDATORY_PARAMS_CASE_TOKEN_VALIDATE_USER_CREDENTIALS.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING__MANDATORY_PARAMS_MISSING, new Object[] {areMandatoryParamsInResponse[1].toString()}, language);
                        return;                                          
                    }                                                                                            
                    myToken = request.getParameter(authenticationAPIParams.REQUEST_PARAM_MY_TOKEN);                   
                    String userToCheck = request.getParameter(authenticationAPIParams.REQUEST_PARAM_USER_TO_CHECK);                      
                    String passwordToCheck = request.getParameter(authenticationAPIParams.REQUEST_PARAM_PASSWORD_TO_CHECK);                      
                    
                    token = new Token();
                    tokenParams = token.tokenParamsList();
                    tokenParamsValues = token.validateToken(myToken, tokenParams);
                    
                    String tokenUser = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USERDB)];
                    String tokenPassword = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, TOKEN_PARAM_USERPW)];
                    if ( (userToCheck.equals(tokenUser)) && (passwordToCheck.equals(tokenPassword)) ){
                        Rdbms.closeRdbms();      
                        request.setAttribute(LPPlatform.SERVLETS_REPONSE_SUCCESS_ATTRIBUTE_NAME,"");
                        rd = request.getRequestDispatcher(LPPlatform.SERVLETS_REPONSE_SUCCESS_SERVLET_NAME);
                        rd.forward(request,response);   
                        return;                        
                    }else{                        
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_API_ERRORTRAPING_PROPERTY_USER_PASSWORD_TO_CHECK_INVALID, new Object[]{userToCheck}, language);              
                    }                                      
                default:      
                    LPFrontEnd.servletReturnResponseError(request, response, LPPlatform.API_ERRORTRAPING_PROPERTY_ENDPOINT_NOT_FOUND, new Object[]{actionName, this.getServletName()}, language);              
            }
        }catch(IOException | JWTCreationException e){            
            String exceptionMessage = e.getMessage();            
            Object[] errMsg = LPFrontEnd.responseError(new String[]{exceptionMessage}, language, null);
            //response.sendError((int) errMsg[0], (String) errMsg[1]);                                        
            Rdbms.closeRdbms();        
        }catch(@SuppressWarnings("FieldNameHidesFieldInSuperclass") Exception e){            
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