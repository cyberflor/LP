/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleSample;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETFrontEnd;
import LabPLANET.utilities.LabPLANETPlatform;
import LabPLANET.utilities.LabPLANETRequest;
import databases.Rdbms;
import databases.Token;
import functionalJava.sampleStructure.DataSample;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
//import frontEnd.APIHandler;


/**
 *
 * @author Administrator
 */
public class sampleAPI extends HttpServlet {

    Status  responseOnERROR = Response.Status.BAD_REQUEST;

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

        String language = "es";
        String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};   
        LabPLANETArray labArr = new LabPLANETArray();
        LabPLANETFrontEnd labFrEnd = new LabPLANETFrontEnd();
        LabPLANETPlatform labPlat = new LabPLANETPlatform();        
        LabPLANETRequest labReq = new LabPLANETRequest();

        String[] mandatoryParams = new String[]{"schemaPrefix"};
        mandatoryParams = labArr.addValueToArray1D(mandatoryParams, "functionBeingTested");
        mandatoryParams = labArr.addValueToArray1D(mandatoryParams, "finalToken");
        Object[] areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParams);
        if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
            errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
            errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
            Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
            response.sendError((int) errMsg[0], (String) errMsg[1]);                
            return ;                
        }            

        String schemaPrefix = request.getParameter("schemaPrefix");            
        String functionBeingTested = request.getParameter("functionBeingTested");
        String finalToken = request.getParameter("finalToken");                   

        Token token = new Token();
        String[] tokenParams = token.tokenParamsList();
        String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

        String dbUserName = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDB")];
        String dbUserPassword = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDBPassword")];
        String internalUserID = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "internalUserID")];         
        String userRole = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userRole")];                     
        String appSessionIdStr = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "appSessionId")];
        String appSessionStartedDate = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "appSessionStartedDate")];                              
        
        Rdbms rdbm = new Rdbms();    
        boolean isConnected = false;
        
        isConnected = rdbm.startRdbms(dbUserName, dbUserPassword);
        if (!isConnected){
            errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
            errObject = labArr.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
            Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
            response.sendError((int) errMsg[0], (String) errMsg[1]);   
            rdbm.closeRdbms(); 
            return ;               
        }        
        Connection con = rdbm.createTransactionWithSavePoint();
        if (con==null){
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The Transaction cannot be created, the action should be aborted");
             return;
        }
        rdbm.setTransactionId(schemaPrefix);
        //ResponseEntity<String121> responsew;        
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
    
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */                     

            Object[] actionEnabled = labPlat.procActionEnabled(schemaPrefix, functionBeingTested);
            if ("LABPLANET_FALSE".equalsIgnoreCase(actionEnabled[0].toString())){
                Object[] errMsg = labFrEnd.responseError(actionEnabled, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                rdbm.closeRdbms(); 
                return ;               
            }            
            actionEnabled = labPlat.procUserRoleActionEnabled(schemaPrefix, userRole, functionBeingTested);
            if ("LABPLANET_FALSE".equalsIgnoreCase(actionEnabled[0].toString())){            
                Object[] errMsg = labFrEnd.responseError(actionEnabled, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                rdbm.closeRdbms(); 
                return ;                           
            }
            
            DataSample smp = new DataSample("");            
            Object[] dataSample = null;
            
            switch (functionBeingTested.toUpperCase()){
                case "LOGSAMPLE":
                    String[] mandatoryParamsAction = new String[]{"sampleTemplate"};
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParams, "sampleTemplateVersion");                    
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }            
                    
                    String sampleTemplate=request.getParameter("sampleTemplate");
                    String sampleTemplateVersionStr = request.getParameter("sampleTemplateVersion");                                  

                    Integer sampleTemplateVersion = Integer.parseInt(sampleTemplateVersionStr);                  
                    String fieldName=request.getParameter("fieldName");                                        
                    String fieldValue=request.getParameter("fieldValue");                    
                    String[] fieldNames=null;
                    Object[] fieldValues=null;
                    if (fieldName!=null) fieldNames = (String[]) fieldName.split("\\|");                                            
                    if (fieldValue!=null) fieldValues = (Object[]) labArr.convertStringWithDataTypeToObjectArray(fieldValue.split("\\|"));                                                            

                    String numSamplesToLogStr=request.getParameter("numSamplesToLog");       
                    Integer numSamplesToLog = Integer.parseInt(numSamplesToLogStr);  

                    if (numSamplesToLogStr==null){
                        dataSample = smp.logSample(rdbm, schemaPrefix, sampleTemplate, sampleTemplateVersion, fieldNames, fieldValues, internalUserID, userRole, Integer.parseInt(appSessionIdStr));
                    }else{
                        dataSample = smp.logSample(rdbm, schemaPrefix, sampleTemplate, sampleTemplateVersion, fieldNames, fieldValues, internalUserID, userRole, Integer.parseInt(appSessionIdStr), numSamplesToLog);
                    }
                    break;
                case "RECEIVESAMPLE":                                          
                    mandatoryParamsAction = new String[]{"sampleId"};
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                         
                    String sampleIdStr = request.getParameter("sampleId");                             

                    Integer sampleId = Integer.parseInt(sampleIdStr);      
//sampleId = 12312131;

                    dataSample = smp.sampleReception(rdbm, schemaPrefix, internalUserID, sampleId, userRole, Integer.parseInt(appSessionIdStr));
                    break;
                case "CHANGESAMPLINGDATE":
                    mandatoryParamsAction = new String[]{"sampleId"};
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParams, "newDate");                    
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }     
                    
                    sampleIdStr = request.getParameter("sampleId");                                     
                    sampleId = Integer.parseInt(sampleIdStr);      
                    Date newDate=Date.valueOf(request.getParameter("newDate"));

                    dataSample = smp.changeSamplingDate(rdbm, schemaPrefix, internalUserID, sampleId, newDate, userRole);
                    break;       
                case "SAMPLINGCOMMENTADD":
                    mandatoryParamsAction = new String[]{"sampleId"};
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                        
                    sampleIdStr = request.getParameter("sampleId");                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    String comment=null;                    
                    comment = request.getParameter("sampleComment"); 
                    dataSample = smp.sampleReceptionCommentAdd(rdbm, schemaPrefix, internalUserID, sampleId, comment, userRole);
                    break;       
                case "SAMPLINGCOMMENTREMOVE":
                    mandatoryParamsAction = new String[]{"sampleId"};
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                        
                    sampleIdStr = request.getParameter("sampleId");                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    comment = request.getParameter("sampleComment"); 
                    dataSample = smp.sampleReceptionCommentRemove(rdbm, schemaPrefix, internalUserID, sampleId, comment, userRole);
                    break;       
                case "INCUBATIONSTART":
                    mandatoryParamsAction = new String[]{"sampleId"};
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                        
                    sampleIdStr = request.getParameter("sampleId");                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    dataSample = smp.setSampleStartIncubationDateTime(rdbm, schemaPrefix, internalUserID, sampleId, userRole);
                    break;       
                case "INCUBATIONEND":
                    mandatoryParamsAction = new String[]{"sampleId"};
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                                            
                    sampleIdStr = request.getParameter("sampleId");                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    dataSample = smp.setSampleEndIncubationDateTime(rdbm, schemaPrefix, internalUserID, sampleId, userRole);
                    break;       
                case "SAMPLEANALYSISADD":
                    mandatoryParamsAction = new String[]{"sampleId"};
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParamsAction, "fieldName");
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParamsAction, "fieldValue");                    
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                                            
                    sampleIdStr = request.getParameter("sampleId");                             
                    sampleId = Integer.parseInt(sampleIdStr);       
                    String[] fieldNameArr = null;
                    Object[] fieldValueArr = null;
                    fieldName = request.getParameter("fieldName");
                    fieldNameArr = (String[]) fieldName.split("\\|");                                    
                    fieldValue = request.getParameter("fieldValue");
                    fieldValueArr = (String[]) fieldValue.split("\\|");                        
                    fieldValueArr = labArr.convertStringWithDataTypeToObjectArray((String[]) fieldValueArr);
                    dataSample = smp.sampleAnalysisAddtoSample(rdbm, schemaPrefix, internalUserID, sampleId, fieldNameArr, fieldValueArr, userRole);                    
                    break;              
                case "ENTERRESULT":
                    mandatoryParamsAction = new String[]{"resultId"};
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParamsAction, "rawValueResult");
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                                            
                    Integer resultId = 0;
                    String rawValueResult = "";
                    String resultIdStr = request.getParameter("resultId");
                    resultId = Integer.parseInt(resultIdStr);       
                    rawValueResult = request.getParameter("rawValueResult");
                    dataSample = smp.sampleAnalysisResultEntry(rdbm, schemaPrefix, internalUserID, resultId, rawValueResult, userRole);
                    break;              
                case "REVIEWRESULT":
                    mandatoryParamsAction = new String[]{"objectId"};
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParamsAction, "objectLevel");
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                                            
                    Integer objectId = 0;
                    String objectIdStr = request.getParameter("objectId");
                    objectId = Integer.parseInt(objectIdStr);     
                    String objectLevel = request.getParameter("objectLevel");
                    sampleId = null; Integer testId = null; resultId = null;
                    if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                    if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                    if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                    dataSample = smp.sampleResultReview(rdbm, schemaPrefix, internalUserID, sampleId, testId, resultId, userRole);
                    break;                       
                case "CANCELRESULT":
                    mandatoryParamsAction = new String[]{"objectId"};
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParamsAction, "objectLevel");
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                                            
                    objectId = 0;
                    objectIdStr = request.getParameter("objectId");
                    objectId = Integer.parseInt(objectIdStr);     
                    objectLevel = request.getParameter("objectLevel");
                        sampleId = null; testId = null; resultId = null;
                        if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultCancel(rdbm, schemaPrefix, internalUserID, sampleId, testId, resultId, userRole);
                    break;   
                case "UNREVIEWRESULT":   // No break then will take the same logic than the next one  
                case "UNCANCELRESULT":
                    mandatoryParamsAction = new String[]{"objectId"};
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParamsAction, "objectLevel");
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                                            
                    objectId = 0;
                    objectIdStr = request.getParameter("objectId");
                    objectId = Integer.parseInt(objectIdStr);     
                    objectLevel = request.getParameter("objectLevel");
                        sampleId = null; testId = null; resultId = null;
                        if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultUnCancel(rdbm, schemaPrefix, internalUserID, sampleId, testId, resultId, userRole);
                    break;       
/*                case "TESTASSIGNMENT": 
                    Integer testId = 0;
                    String newAnalyst = "";
                    if (configSpecTestingArray[i][3]!=null){testId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){newAnalyst = (String) configSpecTestingArray[i][5];}                      
                    fileContent = fileContent + "<td>testId, internalUserID, newAnalyst</td>";
                    fileContent = fileContent + "<td>"+testId.toString()+", "+userName+", "+newAnalyst+"</td>";
                    try {
                        dataSample = smp.sampleAnalysisAssignAnalyst(rdbm, schemaPrefix, internalUserID, testId, newAnalyst, userRole);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;   
*/                    
                case "GETSAMPLEINFO":
                    mandatoryParamsAction = new String[]{"sampleId"};
                    mandatoryParamsAction = labArr.addValueToArray1D(mandatoryParamsAction, "sampleFieldToRetrieve");
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        rdbm.closeRdbms(); 
                        return ;                
                    }                                            
                    
                    sampleIdStr = request.getParameter("sampleId");                             
                    sampleId = Integer.parseInt(sampleIdStr);                                               
                    String sampleFieldToRetrieve = request.getParameter("sampleFieldToRetrieve");                                                                                     

                    String[] sampleFieldToRetrieveArr = (String[]) sampleFieldToRetrieve.split("\\|");                           
                    String schemaDataName = "data";
                    schemaDataName = LabPLANETPlatform.buildSchemaName(schemaPrefix, schemaDataName);              

                    String[] sortFieldsNameArr = null;
                    String sortFieldsName = request.getParameter("sortFieldsName"); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = (String[]) sortFieldsName.split("\\|");                                    
                    }else{   sortFieldsNameArr=null;}  
                    
                    String dataSampleStr = rdbm.getRecordFieldsByFilterJSON(rdbm, schemaDataName, "sample", 
                            new String[]{"sample_id"}, new Object[]{sampleId}, sampleFieldToRetrieveArr, sortFieldsNameArr);
                   if (dataSampleStr.contains("LABPLANET_FALSE")){                                 
                        Object[] errMsg = labFrEnd.responseError(dataSampleStr.split("\\|"), language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                     
                    }else{
                        response.getWriter().write(dataSampleStr);
                        Response.ok().build();
                    }  
                    rdbm.closeRdbms();                    
                    return;                   
                default:      
                    //errObject = frontEnd.APIHandler.actionNotRecognized(errObject, functionBeingTested, response);
                    errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                    errObject = labArr.addValueToArray1D(errObject, "API Error Message: actionName "+functionBeingTested+ " not recognized as an action by this API");                                                            
                    Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    rdbm.closeRdbms();                    
                    return;                    
            }    
            if ("LABPLANET_FALSE".equalsIgnoreCase(dataSample[0].toString())){  
                rdbm.rollbackWithSavePoint();
                con.rollback();
                con.setAutoCommit(true);
                Object[] errMsg = labFrEnd.responseError(dataSample, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
            }else{
                con.commit();
                con.setAutoCommit(true);
                Response.ok().build();
                response.getWriter().write(Arrays.toString(dataSample));      
            }            
            rdbm.closeRdbms();
        }catch(Exception e){   
            try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(sampleAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
            rdbm.closeRdbms();                   
            errObject = new String[]{e.getMessage()};
            Object[] errMsg = labFrEnd.responseError(errObject, language, null);
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
