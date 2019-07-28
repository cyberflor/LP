/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import com.labplanet.servicios.app.authenticationAPIParams;
import databases.Rdbms;
import functionalJava.parameter.Parameter;
import functionalJava.testingScripts.LPTestingOutFormat;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Administrator
 */
public class LPFrontEnd {
    public static final String PROPERTY_NAME = "error_code";
    public static final String PROPERTY_VALUE = "error_value";
    
    public static final String RESPONSE_JSON_TAG_DIAGNOSTIC= "diagnostic"; 
    //public static final String RESPONSE_JSON_TAG_ERROR_MESSAGE= "error_value"; 
    
    /**
     *
     * @param errorStructure
     * @return
     */
    public static Object[] responseError(Object[] errorStructure){
        Object[] responseObj = new Object[0];
        responseObj = LPArray.addValueToArray1D(responseObj, HttpServletResponse.SC_UNAUTHORIZED);
        responseObj = LPArray.addValueToArray1D(responseObj, errorStructure[errorStructure.length-1].toString());        
        return responseObj;
    }
    
    public static String setLanguage(HttpServletRequest request){
        String language = request.getParameter(LPPlatform.REQUEST_PARAM_LANGUAGE);
        if (language == null){language = LPPlatform.REQUEST_PARAM_LANGUAGE_DEFAULT_VALUE;}
        return language;
    }

    public static JSONObject responseJSONDiagnosticLPFalse(Object[] lpFalseStructure){
        JSONObject errJsObj = new JSONObject();
        errJsObj.put(RESPONSE_JSON_TAG_DIAGNOSTIC, lpFalseStructure[0]);
        errJsObj.put(PROPERTY_VALUE+"_es", lpFalseStructure[lpFalseStructure.length-1]);
        errJsObj.put(PROPERTY_VALUE+"_en", lpFalseStructure[lpFalseStructure.length-1]);
        return errJsObj;
    }
    public static JSONObject responseJSONError(String errorPropertyName, Object[] errorPropertyValue, String language){
        JSONObject errJsObj = new JSONObject();
        errJsObj.put(PROPERTY_NAME, errorPropertyName);
        String errorTextEn = Parameter.getParameterBundle(LPPlatform.CONFIG_FILES_FOLDER, LPPlatform.CONFIG_FILES_API_ERRORTRAPING, null, errorPropertyName, null);
        if (errorPropertyValue!=null){
            for (int iVarValue=1; iVarValue<=errorPropertyValue.length; iVarValue++){
                errorTextEn = errorTextEn.replace("<*"+iVarValue+"*>", errorPropertyValue[iVarValue-1].toString());
            }        
        }
        errJsObj.put(PROPERTY_VALUE+"_en", errorTextEn);
        String errorTextEs = Parameter.getParameterBundle(LPPlatform.CONFIG_FILES_FOLDER, LPPlatform.CONFIG_FILES_API_ERRORTRAPING, null, errorPropertyName, "es");
        if (errorPropertyValue!=null){
            for (int iVarValue=1; iVarValue<=errorPropertyValue.length; iVarValue++){
                errorTextEs = errorTextEs.replace("<*"+iVarValue+"*>", errorPropertyValue[iVarValue-1].toString());
            }         
        }
        errJsObj.put(PROPERTY_VALUE+"_es", errorTextEs);
        errJsObj.put(RESPONSE_JSON_TAG_DIAGNOSTIC, LPPlatform.LAB_FALSE);        
        return errJsObj;
    }
    /**
     *
     * @param errorStructure
     * @param language
     * @param schemaPrefix
     * @return
     */
    public static Object[] responseError(Object[] errorStructure, String language, String schemaPrefix){
        Object[] responseObj = new Object[0];
        responseObj = LPArray.addValueToArray1D(responseObj, HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
        if (errorStructure.length>0){
            responseObj = LPArray.addValueToArray1D(responseObj, errorStructure[errorStructure.length-1].toString());        
        }else{
            responseObj = LPArray.addValueToArray1D(responseObj, Thread.currentThread().getStackTrace()[CLIENT_CODE_STACK_INDEX].getMethodName());        
        }
        return responseObj;
    }
    private static final int CLIENT_CODE_STACK_INDEX;    
    static{
        int i = 0;
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()){
            i++;
            if (ste.getClassName().equals(LPPlatform.class.getName())){
                break;
            }
        }
        CLIENT_CODE_STACK_INDEX = i;
    }   
    
    public static final Boolean servletStablishDBConection(HttpServletRequest request, HttpServletResponse response){
        String dbUserName = request.getParameter(authenticationAPIParams.REQUEST_PARAM_DB_USERNAME);                   
        String dbUserPassword = request.getParameter(authenticationAPIParams.REQUEST_PARAM_DB_PASSWORD);     

        //isConnected = Rdbms.getRdbms().startRdbmsTomcat(dbUserName, dbUserPassword);
        boolean isConnected = false;                               
        isConnected = Rdbms.getRdbms().startRdbms(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW);      
        if (!isConnected){      
            LPFrontEnd.servletReturnResponseError(request, response, 
                    LPPlatform.API_ERRORTRAPING_PROPERTY_DATABASE_NOT_CONNECTED, null, null);                                                                
        }  
        return isConnected;
    }
    
    private static void servetInvokeResponseErrorServlet(HttpServletRequest request, HttpServletResponse response){
        Rdbms.closeRdbms();      
        RequestDispatcher rd = request.getRequestDispatcher(LPPlatform.SERVLETS_REPONSE_ERROR_SERVLET_NAME);
        try {   
            rd.forward(request,response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(LPFrontEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void servetInvokeResponseSuccessServlet(HttpServletRequest request, HttpServletResponse response){
        Rdbms.closeRdbms();      
        
        RequestDispatcher rd = request.getRequestDispatcher(LPPlatform.SERVLETS_REPONSE_SUCCESS_SERVLET_NAME);
        try {           
            rd.forward(request,response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(LPFrontEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static final void servletReturnResponseError(HttpServletRequest request, HttpServletResponse response, String errorCode, Object[] errorCodeVars, String language){  
        JSONObject errJSONMsg = LPFrontEnd.responseJSONError(errorCode,errorCodeVars, language);
        request.setAttribute(LPPlatform.SERVLETS_REPONSE_ERROR_ATTRIBUTE_NAME, errJSONMsg.toString());
        servetInvokeResponseErrorServlet(request, response);
    }
    public static final void servletReturnSuccess(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObj){  
        if (jsonObj==null){request.setAttribute(LPPlatform.SERVLETS_REPONSE_SUCCESS_ATTRIBUTE_NAME,"");}
        else{request.setAttribute(LPPlatform.SERVLETS_REPONSE_SUCCESS_ATTRIBUTE_NAME, jsonObj.toString());}
        servetInvokeResponseSuccessServlet(request, response);
    }    
    public static final void servletReturnResponseErrorLPFalseDiagnostic(HttpServletRequest request, HttpServletResponse response, Object[] LPFalseObject){       
        JSONObject errJSONMsg = LPFrontEnd.responseJSONDiagnosticLPFalse(LPFalseObject);
        request.setAttribute(LPPlatform.SERVLETS_REPONSE_ERROR_ATTRIBUTE_NAME, errJSONMsg.toString());        
        servetInvokeResponseErrorServlet(request, response);
    }                    
}
