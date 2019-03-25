/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleSample;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LabPLANETFrontEnd;
import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPHttp;
import databases.Rdbms;
import databases.Token;
import functionalJava.ChangeOfCustody.ChangeOfCustody;
import functionalJava.sampleStructure.DataSample;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;


/**
 *
 * @author Administrator
 */
public class sampleAPI extends HttpServlet {

    public static final String ERRORMSG_ERROR_STATUS_CODE="Error Status Code";
    public static final String ERRORMSG_MANDATORY_PARAMS_MISSING="API Error Message: There are mandatory params for this API method not being passed";

    public static final String PARAMETER_SAMPLE_ID="sampleId";
    public static final String PARAMETER_TEST_ID="testId";
    public static final String PARAMETER_RESULT_ID="resultId";
    public static final String PARAMETER_SAMPLE_TEMPLATE="sampleTemplate";
    public static final String PARAMETER_SAMPLE_TEMPLATE_VERSION="sampleTemplateVersion";
    public static final String PARAMETER_NUM_SAMPLES_TO_LOG="numSamplesToLog";
    public static final String PARAMETER_SAMPLE_FIELD_NAME="fieldName";
    public static final String PARAMETER_SAMPLE_FIELD_VALUE="fieldValue";
    public static final String PARAMETER_SAMPLE_COMMENT="sampleComment";
    public static final String PARAMETER_OBJECT_ID="objectId";
    public static final String PARAMETER_OBJECT_LEVEL="objectLevel";
    public static final String PARAMETER_OBJECT_LEVEL_SAMPLE="SAMPLE";
    public static final String PARAMETER_OBJECT_LEVEL_TEST="TEST";
    public static final String PARAMETER_OBJECT_LEVEL_RESULT="RESULT";

    public static final String TABLE_NAME_SAMPLE="sample";
    public static final String FIELD_NAME_SAMPLE_ID="sample_id";    
    
    
    //Status  responseOnERROR = Response.Status.BAD_REQUEST;

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

        String language = "es";
        String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};   

        String[] mandatoryParams = new String[]{"schemaPrefix"};
        mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "functionBeingTested");
        mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "finalToken");
                
        Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParams);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
            errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
            errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
            Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
            response.sendError((int) errMsg[0], (String) errMsg[1]);                
            return ;                
        }            

        String schemaPrefix = request.getParameter("schemaPrefix");            
        String functionBeingTested = request.getParameter("functionBeingTested");
        String finalToken = request.getParameter("finalToken");                   
        
        Token token = new Token();
        String[] tokenParams = token.tokenParamsList();
        String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

        String dbUserName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERDB)];
        String dbUserPassword = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERPW)];
        String internalUserID = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_INTERNAL_USERID)];         
        String userRole = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ROLE)];                     
        String appSessionIdStr = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_APP_SESSION_ID)];
//        String appSessionStartedDate = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_APP_SESSION_STARTED_DATE)];       
        String eSign = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ESIGN)];            
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(dbUserName)){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: The token is not valid");                    
                Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                response.sendError((int) errMsg[0], (String) errMsg[1]);                
                return ;                            
        }
        mandatoryParams = null;                        

        Object[] procActionRequiresUserConfirmation = LPPlatform.procActionRequiresUserConfirmation(schemaPrefix, functionBeingTested);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresUserConfirmation[0].toString())){     
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "userToVerify");    
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "passwordToVerify");    
        }

        Object[] procActionRequiresEsignConfirmation = LPPlatform.procActionRequiresEsignConfirmation(schemaPrefix, functionBeingTested);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresEsignConfirmation[0].toString())){                                                      
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "eSignToVerify");    
        }        
        if (mandatoryParams!=null){
            areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParams);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                response.sendError((int) errMsg[0], (String) errMsg[1]);                
                return ;                
            }     
        }
        
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresUserConfirmation[0].toString())){    
            String userToVerify = request.getParameter("userToVerify");                   
            String passwordToVerify = request.getParameter("passwordToVerify");    
            if ( (!userToVerify.equalsIgnoreCase(dbUserName)) || (!passwordToVerify.equalsIgnoreCase(dbUserPassword)) ){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: User Verification returned error, the user or the password are not correct.");                    
                Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, "");
                response.sendError((int) errMsg[0], (String) errMsg[1]);                
                return ;                                
            }
        }
        
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresEsignConfirmation[0].toString())){                                                      
            String eSignToVerify = request.getParameter("eSignToVerify");                   
            if (!eSignToVerify.equalsIgnoreCase(eSign)) {
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: eSign Verification returned error, the value is not correct.");                    
                Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, "");
                response.sendError((int) errMsg[0], (String) errMsg[1]);                
                return ;                                                
            }
        }
        
        boolean isConnected = false;
        
        isConnected = Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword);
        if (!isConnected){
            errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
            errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
            Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, schemaPrefix);
            response.sendError((int) errMsg[0], (String) errMsg[1]);   
            Rdbms.closeRdbms(); 
            return ;               
        }        
        
        Connection con = Rdbms.createTransactionWithSavePoint();        
        if (con==null){
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The Transaction cannot be created, the action should be aborted");
             return;
        }
        
/*        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(sampleAPI.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);    
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);    
        Rdbms.setTransactionId(schemaConfigName);
        //ResponseEntity<String121> responsew;        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */                     

            Object[] actionEnabled = LPPlatform.procActionEnabled(schemaPrefix, functionBeingTested);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabled[0].toString())){
                Object[] errMsg = LabPLANETFrontEnd.responseError(actionEnabled, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;               
            }            
            actionEnabled = LPPlatform.procUserRoleActionEnabled(schemaPrefix, userRole, functionBeingTested);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabled[0].toString())){            
                Object[] errMsg = LabPLANETFrontEnd.responseError(actionEnabled, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                Rdbms.closeRdbms(); 
                return ;                           
            }            
            
            DataSample smp = new DataSample("");            
            Object[] dataSample = null;
            
            switch (functionBeingTested.toUpperCase()){
                case "LOGSAMPLE":
                    String[] mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_TEMPLATE};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParams, PARAMETER_SAMPLE_TEMPLATE_VERSION);                    
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                
                    String sampleTemplate=request.getParameter(PARAMETER_SAMPLE_TEMPLATE);
                    String sampleTemplateVersionStr = request.getParameter(PARAMETER_SAMPLE_TEMPLATE_VERSION);                                  

                    Integer sampleTemplateVersion = Integer.parseInt(sampleTemplateVersionStr);                  
                    String fieldName=request.getParameter(PARAMETER_SAMPLE_FIELD_NAME);                                        
                    String fieldValue=request.getParameter(PARAMETER_SAMPLE_FIELD_VALUE);                    
                    String[] fieldNames=null;
                    Object[] fieldValues=null;
                    if (fieldName!=null) fieldNames = fieldName.split("\\|");                                            
                    if (fieldValue!=null) fieldValues = LPArray.convertStringWithDataTypeToObjectArray(fieldValue.split("\\|"));                                                            

                    String numSamplesToLogStr=request.getParameter(PARAMETER_NUM_SAMPLES_TO_LOG);       
                    Integer numSamplesToLog = Integer.parseInt(numSamplesToLogStr);  

                    if (numSamplesToLogStr==null){
                        dataSample = smp.logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, fieldNames, fieldValues, internalUserID, userRole, Integer.parseInt(appSessionIdStr));
                    }else{
                        dataSample = smp.logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, fieldNames, fieldValues, internalUserID, userRole, Integer.parseInt(appSessionIdStr), numSamplesToLog);
                    }
                    break;
                case "RECEIVESAMPLE":                                          
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                         
                    String sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             

                    Integer sampleId = Integer.parseInt(sampleIdStr);      
//sampleId = 12312131;

                    dataSample = smp.sampleReception(schemaPrefix, internalUserID, sampleId, userRole, Integer.parseInt(appSessionIdStr));
                    break;
                case "CHANGESAMPLINGDATE":
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParams, "newDate");                    
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }     
                    
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                                     
                    sampleId = Integer.parseInt(sampleIdStr);      
                    Date newDate=Date.valueOf(request.getParameter("newDate"));

                    dataSample = smp.changeSamplingDate(schemaPrefix, internalUserID, sampleId, newDate, userRole);
                    break;       
                case "SAMPLINGCOMMENTADD":
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID, PARAMETER_SAMPLE_COMMENT};
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                        
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    String comment=null;                    
                    comment = request.getParameter(PARAMETER_SAMPLE_COMMENT); 
                    dataSample = smp.sampleReceptionCommentAdd(schemaPrefix, internalUserID, sampleId, comment, userRole);
                    break;       
                case "SAMPLINGCOMMENTREMOVE":
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                        
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    comment = request.getParameter(PARAMETER_SAMPLE_COMMENT); 
                    dataSample = smp.sampleReceptionCommentRemove(schemaPrefix, internalUserID, sampleId, comment, userRole);
                    break;       
                case "INCUBATIONSTART":
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                        
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    dataSample = smp.setSampleStartIncubationDateTime(schemaPrefix, internalUserID, sampleId, userRole);
                    break;       
                case "INCUBATIONEND":
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    dataSample = smp.setSampleEndIncubationDateTime(schemaPrefix, internalUserID, sampleId, userRole);
                    break;       
                case "SAMPLEANALYSISADD":
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, PARAMETER_SAMPLE_FIELD_NAME);
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, PARAMETER_SAMPLE_FIELD_VALUE);                    
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);       
                    String[] fieldNameArr = null;
                    Object[] fieldValueArr = null;
                    fieldName = request.getParameter(PARAMETER_SAMPLE_FIELD_NAME);
                    fieldNameArr =fieldName.split("\\|");                                    
                    fieldValue = request.getParameter(PARAMETER_SAMPLE_FIELD_VALUE);
                    fieldValueArr = fieldValue.split("\\|");                        
                    fieldValueArr = LPArray.convertStringWithDataTypeToObjectArray((String[]) fieldValueArr);
                    dataSample = smp.sampleAnalysisAddtoSample(schemaPrefix, internalUserID, sampleId, fieldNameArr, fieldValueArr, userRole);                    
                    break;              
                case "ENTERRESULT":
                    mandatoryParamsAction = new String[]{PARAMETER_RESULT_ID};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "rawValueResult");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    Integer resultId = 0;
                    String rawValueResult = "";
                    String resultIdStr = request.getParameter(PARAMETER_RESULT_ID);
                    resultId = Integer.parseInt(resultIdStr);       
                    rawValueResult = request.getParameter("rawValueResult");
                    dataSample = smp.sampleAnalysisResultEntry(schemaPrefix, internalUserID, resultId, rawValueResult, userRole);
                    break;              
                case "REVIEWRESULT":
                    mandatoryParamsAction = new String[]{PARAMETER_OBJECT_ID};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, PARAMETER_OBJECT_LEVEL);
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    Integer objectId = 0;
                    String objectIdStr = request.getParameter(PARAMETER_OBJECT_ID);
                    objectId = Integer.parseInt(objectIdStr);     
                    String objectLevel = request.getParameter(PARAMETER_OBJECT_LEVEL);
                    sampleId = null; Integer testId = null; resultId = null;
                    if (objectLevel.equalsIgnoreCase(PARAMETER_OBJECT_LEVEL_SAMPLE)){sampleId = objectId;}
                    if (objectLevel.equalsIgnoreCase(PARAMETER_OBJECT_LEVEL_TEST)){testId = objectId;}
                    if (objectLevel.equalsIgnoreCase(PARAMETER_OBJECT_LEVEL_RESULT)){resultId = objectId;}
                    dataSample = smp.sampleResultReview(schemaPrefix, internalUserID, sampleId, testId, resultId, userRole);
                    break;                       
                case "CANCELRESULT":
                    mandatoryParamsAction = new String[]{PARAMETER_OBJECT_ID};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, PARAMETER_OBJECT_LEVEL);
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    objectId = 0;
                    objectIdStr = request.getParameter(PARAMETER_OBJECT_ID);
                    objectId = Integer.parseInt(objectIdStr);     
                    objectLevel = request.getParameter(PARAMETER_OBJECT_LEVEL);
                        sampleId = null; testId = null; resultId = null;
                        if (objectLevel.equalsIgnoreCase(PARAMETER_OBJECT_LEVEL_SAMPLE)){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase(PARAMETER_OBJECT_LEVEL_TEST)){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase(PARAMETER_OBJECT_LEVEL_RESULT)){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultCancel(schemaPrefix, internalUserID, sampleId, testId, resultId, userRole);
                    break;   
                case "UNREVIEWRESULT":   // No break then will take the same logic than the next one  
                case "UNCANCELRESULT":
                    mandatoryParamsAction = new String[]{PARAMETER_OBJECT_ID};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, PARAMETER_OBJECT_LEVEL);
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    objectId = 0;
                    objectIdStr = request.getParameter(PARAMETER_OBJECT_ID);
                    objectId = Integer.parseInt(objectIdStr);     
                    objectLevel = request.getParameter(PARAMETER_OBJECT_LEVEL);
                        sampleId = null; testId = null; resultId = null;
                        if (objectLevel.equalsIgnoreCase(PARAMETER_OBJECT_LEVEL_SAMPLE)){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase(PARAMETER_OBJECT_LEVEL_TEST)){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase(PARAMETER_OBJECT_LEVEL_RESULT)){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultUnCancel(schemaPrefix, internalUserID, sampleId, testId, resultId, userRole);
                    break;       
                case "TESTASSIGNMENT": 
                    mandatoryParamsAction = new String[]{PARAMETER_TEST_ID};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "newAnalyst");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    objectIdStr = request.getParameter(PARAMETER_TEST_ID);
                    testId = Integer.parseInt(objectIdStr);     
                    String newAnalyst = request.getParameter("newAnalyst");
                    try {
                        dataSample = smp.sampleAnalysisAssignAnalyst(schemaPrefix, internalUserID, testId, newAnalyst, userRole);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;   
                    
                case "GETSAMPLEINFO":
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "sampleFieldToRetrieve");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);                                               
                    String sampleFieldToRetrieve = request.getParameter("sampleFieldToRetrieve");                                                                                     

                    String[] sampleFieldToRetrieveArr =sampleFieldToRetrieve.split("\\|");                           
                    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);              

                    String[] sortFieldsNameArr = null;
                    String sortFieldsName = request.getParameter("sortFieldsName"); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                    }else{   sortFieldsNameArr=null;}  
                    
                    String dataSampleStr = Rdbms.getRecordFieldsByFilterJSON(schemaDataName, TABLE_NAME_SAMPLE, 
                            new String[]{FIELD_NAME_SAMPLE_ID}, new Object[]{sampleId}, sampleFieldToRetrieveArr, sortFieldsNameArr);
                   if (dataSampleStr.contains(LPPlatform.LAB_FALSE)){                                 
                        Object[] errMsg = LabPLANETFrontEnd.responseError(dataSampleStr.split("\\|"), language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                     
                    }else{
                        response.getWriter().write(dataSampleStr);
                        Response.ok().build();
                    }  
                    Rdbms.closeRdbms();                    
                    return;        
                case "COC_STARTCHANGE":
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "custodianCandidate");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                            
                    
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             
                    objectId = Integer.valueOf(sampleIdStr);
                    String custodianCandidate = request.getParameter("custodianCandidate");                             
                    ChangeOfCustody coc = new ChangeOfCustody();
                    Integer appSessionId=null;
                    if (appSessionIdStr!=null){appSessionId=Integer.valueOf(appSessionIdStr);}
                    dataSample = coc.cocStartChange(schemaPrefix, TABLE_NAME_SAMPLE, FIELD_NAME_SAMPLE_ID, objectId, internalUserID, custodianCandidate, userRole, appSessionId);
                    break;
                case "COC_CONFIRMCHANGE":
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "confirmChangeComment");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                                                
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             
                    sampleId = Integer.valueOf(sampleIdStr);
                    String confirmChangeComment = request.getParameter("confirmChangeComment");                             
                    coc =  new ChangeOfCustody();
                    dataSample = coc.cocConfirmedChange(schemaPrefix, TABLE_NAME_SAMPLE, FIELD_NAME_SAMPLE_ID, sampleId, internalUserID, 
                            confirmChangeComment, userRole, null);
                    break;
                case "COC_ABORTCHANGE":
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "cancelChangeComment");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                                                
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             
                    sampleId = Integer.valueOf(sampleIdStr);
                    String cancelChangeComment = request.getParameter("cancelChangeComment");                             
                    coc =  new ChangeOfCustody();
                    dataSample = coc.cocAbortedChange(schemaPrefix, TABLE_NAME_SAMPLE, FIELD_NAME_SAMPLE_ID, sampleId, internalUserID, 
                            cancelChangeComment, userRole, null);
                    break;                    
                case "LOGALIQUOT":
                    mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
//                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "confirmChangeComment");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                                                
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);              
                    sampleId = Integer.valueOf(sampleIdStr);                    
                    //sampleTemplate=null;
                    //sampleTemplateVersion=null;
                    //sampleTemplateInfo = configSpecTestingArray[i][6].toString().split("\\|");
                    //sampleTemplate = sampleTemplateInfo[0];
                    //sampleTemplateVersion = Integer.parseInt(sampleTemplateInfo[1]);
                    fieldName=request.getParameter(PARAMETER_SAMPLE_FIELD_NAME);                                        
                    fieldValue=request.getParameter(PARAMETER_SAMPLE_FIELD_VALUE);                    
                    fieldNames=null;
                    fieldValues=null;
                    if (fieldName!=null) fieldNames = fieldName.split("\\|");                                            
                    if (fieldValue!=null) fieldValues = LPArray.convertStringWithDataTypeToObjectArray(fieldValue.split("\\|"));                                                                                
                    try {
                        dataSample = smp.logSampleAliquot(schemaPrefix, sampleId, 
                                // sampleTemplate, sampleTemplateVersion, 
                                fieldNames, fieldValues, internalUserID, userRole, Integer.parseInt(appSessionIdStr));                                                                
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;                     
                case "LOGSUBALIQUOT":
                    mandatoryParamsAction = new String[]{"aliquotId"};
//                    mandatoryParamsAction = LPArray.addValueToArray1D(mandatoryParamsAction, "confirmChangeComment");
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                                                
                    String aliquotIdStr = request.getParameter("aliquotId");              
                    Integer aliquotId = Integer.valueOf(aliquotIdStr);
                    //sampleTemplate=null;
                    //sampleTemplateVersion=null;
                    //sampleTemplateInfo = configSpecTestingArray[i][6].toString().split("\\|");
                    //sampleTemplate = sampleTemplateInfo[0];
                    //sampleTemplateVersion = Integer.parseInt(sampleTemplateInfo[1]);
                    fieldName=request.getParameter(PARAMETER_SAMPLE_FIELD_NAME);                                        
                    fieldValue=request.getParameter(PARAMETER_SAMPLE_FIELD_VALUE);                    
                    fieldNames=null;
                    fieldValues=null;
                    if (fieldName!=null) fieldNames =  fieldName.split("\\|");                                            
                    if (fieldValue!=null) fieldValues = LPArray.convertStringWithDataTypeToObjectArray(fieldValue.split("\\|"));                                                                                
                    try {
                        dataSample = smp.logSampleSubAliquot(schemaPrefix, aliquotId, 
                                // sampleTemplate, sampleTemplateVersion, 
                                fieldNames, fieldValues, internalUserID, userRole, Integer.parseInt(appSessionIdStr));                                                                
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;                                  
                default:      
                    //errObject = frontEnd.APIHandler.actionNotRecognized(errObject, functionBeingTested, response);
                    errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                    errObject = LPArray.addValueToArray1D(errObject, "API Error Message: actionName "+functionBeingTested+ " not recognized as an action by this API");                                                            
                    Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, schemaPrefix);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    Rdbms.closeRdbms();                    
                    return;                    
            }    
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(dataSample[0].toString())){  
                Rdbms.rollbackWithSavePoint();
                con.rollback();
                con.setAutoCommit(true);
                
                Object[] errMsg = LabPLANETFrontEnd.responseError(dataSample, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
            }else{
                con.commit();
                con.setAutoCommit(true);
                
                Response.ok().build();
                response.getWriter().write(Arrays.toString(dataSample));      
            }            
            Rdbms.closeRdbms();
        }catch(Exception e){   
 /*           try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(sampleAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
*/            
            Rdbms.closeRdbms();                   
            errObject = new String[]{e.getMessage()};
            Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
            response.sendError((int) errMsg[0], (String) errMsg[1]);           
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