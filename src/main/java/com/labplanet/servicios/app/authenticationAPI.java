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
import databases.dbObjectsAppTables;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import functionalJava.user.UserAndRolesViews;
import functionalJava.user.UserProfile;
import java.util.Date;
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
                        LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                return;          
            }                        
            if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}

            String actionName = request.getParameter(globalAPIsParams.REQUEST_PARAM_ACTION_NAME);                                    
            switch (actionName.toUpperCase()){
                case authenticationAPIParams.API_ENDPOINT_AUTHENTICATE:                         
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, authenticationAPIParams.MANDATORY_PARAMS_CASE_AUTHENTICATE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                                    
                    }     
                    String dbUserName = request.getParameter(globalAPIsParams.REQUEST_PARAM_DB_USERNAME);                   
                    String dbUserPassword = request.getParameter(globalAPIsParams.REQUEST_PARAM_DB_PSSWD);  
                    String personName = UserAndRolesViews.getPersonByUser(dbUserName);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(personName)){               
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_PROPERTY_PERSON_NOT_FOUND, null, language);              
                        return;                                                          
                    }                    
                    Object[] validUserPassword = UserAndRolesViews.isValidUserPassword(dbUserName, dbUserPassword);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(validUserPassword[0].toString())){
                        validUserPassword = UserAndRolesViews.isValidUserPassword(dbUserName, dbUserPassword);
                        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(validUserPassword[0].toString())){     
                            LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_PROPERTY_INVALID_USER_PSSWD, null, language);              
                            return;                               
                        }
                    }                                                          
                    Token token = new Token("");                    
                    String myToken = token.createToken(dbUserName, dbUserPassword, personName, "Admin", "", "", "");                    

                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put(authenticationAPIParams.RESPONSE_JSON_TAG_USER_INFO_ID, personName);
                    jsonObj.put(authenticationAPIParams.RESPONSE_JSON_TAG_MY_TOKEN, myToken);
                    LPFrontEnd.servletReturnSuccess(request, response, jsonObj);
                    return;
                case authenticationAPIParams.API_ENDPOINT_GET_USER_ROLE:                                                 
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, authenticationAPIParams.MANDATORY_PARAMS_CASE_GETUSERROLE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[] {areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                          
                    }                                             
                    String firstToken = request.getParameter(globalAPIsParams.REQUEST_PARAM_MY_TOKEN);                   
                    
                    token = new Token(firstToken);
                    
                    UserProfile usProf = new UserProfile();
                    Object[] allUserProcedurePrefix = usProf.getAllUserProcedurePrefix(token.getUserName());
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(allUserProcedurePrefix[0].toString())){
                        LPFrontEnd.servletReturnResponseErrorLPFalseDiagnostic(request, response, allUserProcedurePrefix);
                        return;                                             
                    }                    
                    
                    Object[] allUserProcedureRoles = usProf.getProcedureUserProfileFieldValues(allUserProcedurePrefix, token.getPersonName());
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
                                new String[]{"user_info_id"}, new Object[]{personName}, new String[]{"role_id"});
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
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[] {areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                                               
                    }                                                     
                    firstToken = request.getParameter(globalAPIsParams.REQUEST_PARAM_MY_TOKEN);                   
                    String userRole = request.getParameter(globalAPIsParams.REQUEST_PARAM_USER_ROLE);                     

                    token = new Token(firstToken);

                    String[] fieldsName = new String[]{"person", "role_name"};
                    Object[] fieldsValue = new Object[]{token.getPersonName(), userRole};
                    Object[] newAppSession = LPSession.newAppSession(fieldsName, fieldsValue);                    
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(newAppSession[0].toString())){   
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_PROPERTY_SESSION_ID_NOT_GENERATED, null, language);              
                        return;                                                         
                    }                    
                    Integer sessionId = Integer.parseInt((String) newAppSession[newAppSession.length-1]);
                    String sessionIdStr = sessionId.toString();
                    if (sessionId==null){
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_PROPERTY_SESSION_ID_NULL_NOT_ALLOWED, null, language);          
                        return;                                                   
                    }
                    Date nowLocalDate =LPDate.getTimeStampLocalDate();
                    
                   Object[][] userEsignInfo = Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_APP, dbObjectsAppTables.TABLE_NAME_APP_USERS, 
                            new String[]{dbObjectsAppTables.FIELD_NAME_APP_USERS_USER_NAME}, new Object[]{token.getUserName()}, 
                            new String[]{dbObjectsAppTables.FIELD_NAME_APP_USERS_ESIGN});
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(userEsignInfo[0][0].toString())){  
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_PROPERTY_ESIGN_INFO_NOT_AVAILABLE, null, language);       
                        return;                                                                                
                    }                               
                    String myFinalToken = token.createToken(token.getUserName(), token.getUsrPw(), token.getPersonName(), 
                            userRole, sessionIdStr, nowLocalDate.toString(), userEsignInfo[0][0].toString());
                    Rdbms.closeRdbms();                    
                    jsonObj = new JSONObject();
                    jsonObj.put(authenticationAPIParams.RESPONSE_JSON_TAG_FINAL_TOKEN, myFinalToken);
                    jsonObj.put(authenticationAPIParams.RESPONSE_JSON_TAG_APP_SESSION_ID, sessionIdStr);
                    jsonObj.put(authenticationAPIParams.RESPONSE_JSON_TAG_APP_SESSION_DATE, nowLocalDate.toString());
                    LPFrontEnd.servletReturnSuccess(request, response, jsonObj);
                    return;                                   
                case authenticationAPIParams.API_ENDPOINT_TOKEN_VALIDATE_ESIGN_PHRASE:     
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, authenticationAPIParams.MANDATORY_PARAMS_CASE_TOKEN_VALIDATE_ESIGN_PHRASE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[] {areMandatoryParamsInResponse[1].toString()}, language);  
                        return;                                                                                          
                    }                                               
                    myToken = request.getParameter(globalAPIsParams.REQUEST_PARAM_MY_TOKEN);                   
                    String esignPhraseToCheck = request.getParameter(globalAPIsParams.REQUEST_PARAM_ESIGN_TO_CHECK);                      

                    token = new Token(myToken);
                    
                    if(esignPhraseToCheck.equals(token.geteSign())){   
                        LPFrontEnd.servletReturnSuccess(request, response);
                        return;                                             
                    }else{               
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_API_ERRORTRAPING_PROPERTY_ESIGN_TO_CHECK_INVALID, new Object[]{esignPhraseToCheck}, language);
                        return;                             
                    }
                    
                case authenticationAPIParams.API_ENDPOINT_TOKEN_VALIDATE_USER_CREDENTIALS:     
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, authenticationAPIParams.MANDATORY_PARAMS_CASE_TOKEN_VALIDATE_USER_CREDENTIALS.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[] {areMandatoryParamsInResponse[1].toString()}, language);
                        return;                                          
                    }                                                                                            
                    myToken = request.getParameter(globalAPIsParams.REQUEST_PARAM_MY_TOKEN);                   
                    String userToCheck = request.getParameter(globalAPIsParams.REQUEST_PARAM_USER_TO_CHECK);                      
                    String passwordToCheck = request.getParameter(globalAPIsParams.REQUEST_PARAM_PASSWORD_TO_CHECK);                      
                    
                    token = new Token(myToken);
                    if ( (userToCheck.equals(token.getUserName())) && (passwordToCheck.equals(token.getUsrPw())) ){
                        LPFrontEnd.servletReturnSuccess(request, response);
                    }else{                        
                        LPFrontEnd.servletReturnResponseError(request, response, authenticationAPIParams.ERROR_API_ERRORTRAPING_PROPERTY_USER_PSSWD_TO_CHECK_INVALID, new Object[]{userToCheck}, language);              
                    }    
                    break;
                default:      
                    LPFrontEnd.servletReturnResponseError(request, response, LPPlatform.API_ERRORTRAPING_PROPERTY_ENDPOINT_NOT_FOUND, new Object[]{actionName, this.getServletName()}, language);              
                    break;
            }
        }catch(IOException | JWTCreationException e){            
            String exceptionMessage = e.getMessage();            
            LPFrontEnd.servletReturnResponseError(request, response, exceptionMessage, null, null);  
        }catch(@SuppressWarnings("FieldNameHidesFieldInSuperclass") Exception e){            
            String exceptionMessage = e.getMessage();     
            LPFrontEnd.servletReturnResponseError(request, response, exceptionMessage, null, null);                    
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