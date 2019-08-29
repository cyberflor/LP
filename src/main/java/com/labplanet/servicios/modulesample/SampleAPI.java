/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.modulesample;

import lbplanet.utilities.LPArray;
import lbplanet.utilities.LPFrontEnd;
import lbplanet.utilities.LPPlatform;
import lbplanet.utilities.LPHttp;
import com.labplanet.servicios.moduleenvmonit.EnvMonAPI;
import com.labplanet.servicios.app.GlobalAPIsParams;
import databases.Rdbms;
import databases.Token;
import functionaljavaa.changeofcustody.ChangeOfCustody;
import functionaljavaa.samplestructure.DataSample;
import functionaljavaa.samplestructure.DataSampleAnalysis;
import functionaljavaa.samplestructure.DataSampleAnalysisResult;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
/**
 *
 * @author Administrator
 */
public class SampleAPI extends HttpServlet {
    public static final String TABLE_NAME_SAMPLE="sample";
    public static final String FIELD_NAME_SAMPLE_ID="sample_id";      
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

        String[] mandatoryParams = new String[]{""};
        Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_MAIN_SERVLET.split("\\|"));                       
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
            LPFrontEnd.servletReturnResponseError(request, response, 
                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
            return;          
        }             
        String schemaPrefix = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SCHEMA_PREFIX);            
        String actionName = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_ACTION_NAME);
        String finalToken = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_FINAL_TOKEN);                   
        
        Token token = new Token(finalToken);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(token.getUserName())){
                LPFrontEnd.servletReturnResponseError(request, response, 
                        LPPlatform.API_ERRORTRAPING_INVALID_TOKEN, null, language);              
                return;                                
        }
        mandatoryParams = null;                        

         Object[] procActionRequiresUserConfirmation = LPPlatform.procActionRequiresUserConfirmation(schemaPrefix, actionName);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresUserConfirmation[0].toString())){     
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, GlobalAPIsParams.REQUEST_PARAM_USER_TO_CHECK);    
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, GlobalAPIsParams.REQUEST_PARAM_PASSWORD_TO_CHECK);    
        }
        Object[] procActionRequiresEsignConfirmation = LPPlatform.procActionRequiresEsignConfirmation(schemaPrefix, actionName);
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresEsignConfirmation[0].toString())){                                                      
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, GlobalAPIsParams.REQUEST_PARAM_ESIGN_TO_CHECK);    
        }        
        if (mandatoryParams!=null){
            areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParams);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                LPFrontEnd.servletReturnResponseError(request, response, 
                        LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                return;                  
            }     
        }
        if ( (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresUserConfirmation[0].toString())) &&     
             (!LPFrontEnd.servletUserToVerify(request, response, procActionRequiresUserConfirmation, token.getUserName(), token.getUsrPw())) ){return;}

        if ( (LPPlatform.LAB_TRUE.equalsIgnoreCase(procActionRequiresEsignConfirmation[0].toString())) &&    
             (!LPFrontEnd.servletEsignToVerify(request, response, procActionRequiresUserConfirmation, token.geteSign())) ){return;}
        if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}     
        
        Connection con = Rdbms.createTransactionWithSavePoint();        
 /*       if (con==null){
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The Transaction cannot be created, the action should be aborted");
             return;
        }
*/        
        try {
            con.rollback();
            con.setAutoCommit(true);    
        } catch (SQLException ex) {
            Logger.getLogger(EnvMonAPI.class.getName()).log(Level.SEVERE, null, ex);
        }        
/*        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SampleAPI.class.getName()).log(Level.SEVERE, null, ex);        
/*        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SampleAPI.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);    
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);    
        Rdbms.setTransactionId(schemaConfigName);
        try (PrintWriter out = response.getWriter()) {

            Object[] actionEnabled = LPPlatform.procActionEnabled(schemaPrefix, actionName);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabled[0].toString())){
                LPFrontEnd.servletReturnResponseErrorLPFalseDiagnostic(request, response, actionEnabled);
                return ;               
            }            
            actionEnabled = LPPlatform.procUserRoleActionEnabled(schemaPrefix, token.getUserRole(), actionName);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabled[0].toString())){       
                LPFrontEnd.servletReturnResponseErrorLPFalseDiagnostic(request, response, actionEnabled);
                return ;                           
            }            
            
            DataSample smp = new DataSample();    
            DataSampleAnalysis smpAna = new DataSampleAnalysis();   
            DataSampleAnalysisResult smpAnaRes = new DataSampleAnalysisResult();   
            Object[] dataSample = null;
            
            switch (actionName.toUpperCase()){
                case SampleAPIParams.API_ENDPOINT_LOGSAMPLE: 
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_LOGSAMPLE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                     
                    String sampleTemplate=request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_TEMPLATE);
                    String sampleTemplateVersionStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_TEMPLATE_VERSION);                                  

                    Integer sampleTemplateVersion = Integer.parseInt(sampleTemplateVersionStr);                  
                    String fieldName=request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_FIELD_NAME);                                        
                    String fieldValue=request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_FIELD_VALUE);                    
                    String[] fieldNames=null;
                    Object[] fieldValues=null;
                    if (fieldName!=null) fieldNames = fieldName.split("\\|");                                            
                    if (fieldValue!=null) fieldValues = LPArray.convertStringWithDataTypeToObjectArray(fieldValue.split("\\|"));                                                            

                    Integer numSamplesToLog = 1;
                    String numSamplesToLogStr=request.getParameter(GlobalAPIsParams.REQUEST_PARAM_NUM_SAMPLES_TO_LOG);    
                    if (numSamplesToLogStr!=null){numSamplesToLog = Integer.parseInt(numSamplesToLogStr);}

                    if (numSamplesToLogStr==null){
                        dataSample = smp.logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, fieldNames, fieldValues, token.getPersonName(), token.getUserRole(), Integer.parseInt(token.getAppSessionId()));
                    }else{
                        dataSample = smp.logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, fieldNames, fieldValues, token.getPersonName(), token.getUserRole(), Integer.parseInt(token.getAppSessionId()), numSamplesToLog);
                    }
                    break;
                case SampleAPIParams.API_ENDPOINT_RECEIVESAMPLE:   
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_RECEIVESAMPLE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                            
                    String sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                             
                    Integer sampleId = Integer.parseInt(sampleIdStr);      
                    dataSample = smp.sampleReception(schemaPrefix, token.getPersonName(), sampleId, token.getUserRole(), Integer.parseInt(token.getAppSessionId()));
                    break;
                case SampleAPIParams.API_ENDPOINT_CHANGESAMPLINGDATE:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_CHANGESAMPLINGDATE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                     
                    sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                                     
                    sampleId = Integer.parseInt(sampleIdStr);      
                    Date newDate=Date.valueOf(request.getParameter(GlobalAPIsParams.REQUEST_PARAM_NEW_DATE));

                    dataSample = smp.changeSamplingDate(schemaPrefix, token.getPersonName(), sampleId, newDate, token.getUserRole());
                    break;       
                case SampleAPIParams.API_ENDPOINT_SAMPLINGCOMMENTADD:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_SAMPLINGCOMMENTADD.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                            
                    sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    String comment=null;                    
                    comment = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_COMMENT); 
                    dataSample = smp.sampleReceptionCommentAdd(schemaPrefix, token.getPersonName(), sampleId, comment, token.getUserRole());
                    break;       
                case SampleAPIParams.API_ENDPOINT_SAMPLINGCOMMENTREMOVE:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.API_ENDPOINT_SAMPLINGCOMMENTREMOVE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                        
                    sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    comment = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_COMMENT); 
                    dataSample = smp.sampleReceptionCommentRemove(schemaPrefix, token.getPersonName(), sampleId, comment, token.getUserRole());
                    break;       
                case SampleAPIParams.API_ENDPOINT_INCUBATIONSTART:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_INCUBATIONSTART.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                    
                    sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    dataSample = smp.setSampleStartIncubationDateTime(schemaPrefix, token.getPersonName(), sampleId, token.getUserRole());
                    break;       
                case SampleAPIParams.API_ENDPOINT_INCUBATIONEND:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_INCUBATIONEND.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                    
                    sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);      
                    dataSample = smp.setSampleEndIncubationDateTime(schemaPrefix, token.getPersonName(), sampleId, token.getUserRole());
                    break;       
                case SampleAPIParams.API_ENDPOINT_SAMPLEANALYSISADD:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_SAMPLEANALYSISADD.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                                                
                    sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);       
                    String[] fieldNameArr = null;
                    Object[] fieldValueArr = null;
                    fieldName = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_FIELD_NAME);
                    fieldNameArr =fieldName.split("\\|");                                    
                    fieldValue = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_FIELD_VALUE);
                    fieldValueArr = fieldValue.split("\\|");                        
                    fieldValueArr = LPArray.convertStringWithDataTypeToObjectArray((String[]) fieldValueArr);
                    dataSample = smpAna.sampleAnalysisAddtoSample(schemaPrefix, token.getPersonName(), sampleId, fieldNameArr, fieldValueArr, token.getUserRole());  
                    break;              
                case SampleAPIParams.API_ENDPOINT_ENTERRESULT:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_ENTERRESULT.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                                              
                    Integer resultId = 0;
                    String rawValueResult = "";
                    String resultIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_RESULT_ID);
                    resultId = Integer.parseInt(resultIdStr);       
                    rawValueResult = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_RAW_VALUE_RESULT);
                    dataSample = smpAnaRes.sampleAnalysisResultEntry(schemaPrefix, token.getPersonName(), resultId, rawValueResult, token.getUserRole(), smp);
                    break;              
                case SampleAPIParams.API_ENDPOINT_REVIEWRESULT:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_REVIEWRESULT.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                                                 
                    Integer objectId = 0;
                    String objectIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_OBJECT_ID);
                    objectId = Integer.parseInt(objectIdStr);     
                    String objectLevel = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL);
                    sampleId = null; Integer testId = null; resultId = null;
                    if (objectLevel.equalsIgnoreCase(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL_SAMPLE)){sampleId = objectId;}
                    if (objectLevel.equalsIgnoreCase(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL_TEST)){testId = objectId;}
                    if (objectLevel.equalsIgnoreCase(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL_RESULT)){resultId = objectId;}
                    dataSample = smp.sampleResultReview(schemaPrefix, token.getPersonName(), sampleId, testId, resultId, token.getUserRole());
                    break;                       
                case SampleAPIParams.API_ENDPOINT_CANCELRESULT:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_CANCELRESULT.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                                              
                    objectId = 0;
                    objectIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_OBJECT_ID);
                    objectId = Integer.parseInt(objectIdStr);     
                    objectLevel = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL);
                        sampleId = null; testId = null; resultId = null;
                        if (objectLevel.equalsIgnoreCase(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL_SAMPLE)){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL_TEST)){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL_RESULT)){resultId = objectId;}
                        dataSample = smpAnaRes.sampleAnalysisResultCancel(schemaPrefix, token.getPersonName(), sampleId, testId, resultId, token.getUserRole(),smp);
                    break;   
                case SampleAPIParams.API_ENDPOINT_UNREVIEWRESULT:   // No break then will take the same logic than the next one  
                case SampleAPIParams.API_ENDPOINT_UNCANCELRESULT:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_UNCANCELRESULT.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                                              
                    objectId = 0;
                    objectIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_OBJECT_ID);
                    objectId = Integer.parseInt(objectIdStr);     
                    objectLevel = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL);
                        sampleId = null; testId = null; resultId = null;
                        if (objectLevel.equalsIgnoreCase(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL_SAMPLE)){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL_TEST)){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase(GlobalAPIsParams.REQUEST_PARAM_OBJECT_LEVEL_RESULT)){resultId = objectId;}
                        dataSample = smpAnaRes.sampleAnalysisResultUnCancel(schemaPrefix, token.getPersonName(), sampleId, testId, resultId, token.getUserRole(), smp);
                    break;       
                case SampleAPIParams.API_ENDPOINT_TESTASSIGNMENT: 
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_TESTASSIGNMENT.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                                               
                    objectIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_TEST_ID);
                    testId = Integer.parseInt(objectIdStr);     
                    String newAnalyst = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_NEW_ANALYST);
                    dataSample = smpAna.sampleAnalysisAssignAnalyst(schemaPrefix, token.getPersonName(), testId, newAnalyst, token.getUserRole(), smp);
 
                    break;                       
                case SampleAPIParams.API_ENDPOINT_GETSAMPLEINFO:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_GETSAMPLEINFO.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                         
                    sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                             
                    sampleId = Integer.parseInt(sampleIdStr);                                               
                    String sampleFieldToRetrieve = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_FIELD_TO_RETRIEVE);                                                                                     

                    String[] sampleFieldToRetrieveArr =sampleFieldToRetrieve.split("\\|");                           
                    schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);              

                    String[] sortFieldsNameArr = null;
                    String sortFieldsName = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SORT_FIELDS_NAME); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                    }else{   sortFieldsNameArr=null;}  
                    
                    String dataSampleStr = Rdbms.getRecordFieldsByFilterJSON(schemaDataName, TABLE_NAME_SAMPLE, 
                            new String[]{FIELD_NAME_SAMPLE_ID}, new Object[]{sampleId}, sampleFieldToRetrieveArr, sortFieldsNameArr);
                   if (dataSampleStr.contains(LPPlatform.LAB_FALSE)){                                 
                        Object[] errMsg = LPFrontEnd.responseError(dataSampleStr.split("\\|"), language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);        
                    }else{
                        LPFrontEnd.servletReturnSuccess(request, response, dataSampleStr);
                    }                  
                    return;        
                case SampleAPIParams.API_ENDPOINT_COC_STARTCHANGE:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_COC_STARTCHANGE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                                                                    
                    sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                             
                    objectId = Integer.valueOf(sampleIdStr);
                    String custodianCandidate = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_CUSTODIAN_CANDIDATE);                             
                    ChangeOfCustody coc = new ChangeOfCustody();
                    Integer appSessionId=null;
                    if (token.getAppSessionId()!=null){appSessionId=Integer.valueOf(token.getAppSessionId());}
                    dataSample = coc.cocStartChange(schemaPrefix, TABLE_NAME_SAMPLE, FIELD_NAME_SAMPLE_ID, objectId, token.getPersonName(), custodianCandidate, token.getUserRole(), appSessionId);
                    break;
                case SampleAPIParams.API_ENDPOINT_COC_CONFIRMCHANGE:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_COC_CONFIRMCHANGE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                                                                   
                    sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                             
                    sampleId = Integer.valueOf(sampleIdStr);
                    String confirmChangeComment = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_CONFIRM_CHANGE_COMMENT);                             
                    coc =  new ChangeOfCustody();
                    dataSample = coc.cocConfirmedChange(schemaPrefix, TABLE_NAME_SAMPLE, FIELD_NAME_SAMPLE_ID, sampleId, token.getPersonName(), 
                            confirmChangeComment, token.getUserRole(), null);
                    break;
                case SampleAPIParams.API_ENDPOINT_COC_ABORTCHANGE:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_COC_ABORTCHANGE.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                                             
                    sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                             
                    sampleId = Integer.valueOf(sampleIdStr);
                    String cancelChangeComment = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_CANCEL_CHANGE_COMMENT);                             
                    coc =  new ChangeOfCustody();
                    dataSample = coc.cocAbortedChange(schemaPrefix, TABLE_NAME_SAMPLE, FIELD_NAME_SAMPLE_ID, sampleId, token.getPersonName(), 
                            cancelChangeComment, token.getUserRole(), null);
                    break;                    
                case SampleAPIParams.API_ENDPOINT_LOGALIQUOT:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_LOGALIQUOT.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                                               
                    sampleIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_ID);              
                    sampleId = Integer.valueOf(sampleIdStr);                    
                    fieldName=request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_FIELD_NAME);                                        
                    fieldValue=request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_FIELD_VALUE);                    
                    fieldNames=null;
                    fieldValues=null;
                    if (fieldName!=null) fieldNames = fieldName.split("\\|");                                            
                    if (fieldValue!=null) fieldValues = LPArray.convertStringWithDataTypeToObjectArray(fieldValue.split("\\|"));                                                                                
                    dataSample = smp.logSampleAliquot(schemaPrefix, sampleId, 
                                // sampleTemplate, sampleTemplateVersion, 
                                fieldNames, fieldValues, token.getPersonName(), token.getUserRole(), Integer.parseInt(token.getAppSessionId()));                                                                
                    break;                     
                case SampleAPIParams.API_ENDPOINT_LOGSUBALIQUOT:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, SampleAPIParams.MANDATORY_PARAMS_LOGSUBALIQUOT.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                                                             
                    String aliquotIdStr = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_ALIQUOT_ID);              
                    Integer aliquotId = Integer.valueOf(aliquotIdStr);
                    fieldName=request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_FIELD_NAME);                                        
                    fieldValue=request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SAMPLE_FIELD_VALUE);                    
                    fieldNames=null;
                    fieldValues=null;
                    if (fieldName!=null) fieldNames =  fieldName.split("\\|");                                            
                    if (fieldValue!=null) fieldValues = LPArray.convertStringWithDataTypeToObjectArray(fieldValue.split("\\|"));                                                                                
                    dataSample = smp.logSampleSubAliquot(schemaPrefix, aliquotId, 
                                // sampleTemplate, sampleTemplateVersion, 
                                fieldNames, fieldValues, token.getPersonName(), token.getUserRole(), Integer.parseInt(token.getAppSessionId()));                                                                
                    break;                                  
                default:      
                    LPFrontEnd.servletReturnResponseError(request, response, LPPlatform.API_ERRORTRAPING_PROPERTY_ENDPOINT_NOT_FOUND, new Object[]{actionName, this.getServletName()}, language);              
                    return;                    
            }    
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(dataSample[0].toString())){  
                Rdbms.rollbackWithSavePoint();
                if (!con.getAutoCommit()){
                    con.rollback();
                    con.setAutoCommit(true);}                
                LPFrontEnd.servletReturnResponseErrorLPFalseDiagnostic(request, response, dataSample);   
            }else{
                JSONObject dataSampleJSONMsg = LPFrontEnd.responseJSONDiagnosticLPTrue(dataSample);
                LPFrontEnd.servletReturnSuccess(request, response, dataSampleJSONMsg);
            }            
        }catch(Exception e){   
            LPFrontEnd.servletReturnResponseError(request, response, LPPlatform.API_ERRORTRAPING_EXCEPTION_RAISED, new Object[]{e.getMessage(), this.getServletName()}, language);                   
 /*           try {
                con.rollback();
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(SampleAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
*/            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlLPFrontEnd on the + sign on the left to edit the code.">
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