/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleSample;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPHttp;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPPlatform;
import com.sun.rowset.CachedRowSetImpl;
import databases.Rdbms;
import databases.Token;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
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
public class sampleAPIfrontend extends HttpServlet {
    public static final String ERRORMSG_ERROR_STATUS_CODE="Error Status Code";
    public static final String ERRORMSG_MANDATORY_PARAMS_MISSING="API Error Message: There are mandatory params for this API method not being passed";

    public static final String PARAMETER_SAMPLE_ID="sampleId";
    public static final String PARAMETER_TEST_ID="testId";
    public static final String PARAMETER_RESULT_ID="resultId";
    
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
        String language = "en";

        try (PrintWriter out = response.getWriter()) {
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};            
            
            String finalToken = request.getParameter("finalToken");                      

            if (finalToken==null) {
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: finalToken is one mandatory param for this API");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;
            }                    
            Token token = new Token();
            String[] tokenParams = token.tokenParamsList();
            String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);
            
            String dbUserName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERDB)];
            String dbUserPassword = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERPW)];
//            String internalUserID = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_INTERNAL_USERID)];         
//            String userRole = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ROLE)];                     

            boolean isConnected = false;
            isConnected = Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword);
            if (!isConnected){
                Rdbms.closeRdbms();                
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;               
            }            
        
            String actionName = request.getParameter("actionName");
            if (actionName==null) {
                Rdbms.closeRdbms();                
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: actionName is one mandatory param for this API");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;
            }           
            String schemaPrefix = request.getParameter("schemaPrefix");
            if (schemaPrefix==null) {
                Rdbms.closeRdbms();                
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: schemaPrefix is one mandatory param for this API");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;
            }           
            
            switch (actionName.toUpperCase()){
            case "GET_SAMPLETEMPLATES":       
                String[] filterFieldName = new String[]{"json_definition is not null"};
                Object[] filterFieldValue = new Object[]{""};
/*                filterFieldName = LPArray.addValueToArray1D(filterFieldName, "code");
                if ("process-us".equalsIgnoreCase(schemaPrefix)){
                    filterFieldValue = LPArray.addValueToArray1D(filterFieldValue, "specSamples");
                }else{filterFieldValue = LPArray.addValueToArray1D(filterFieldValue, "sampleTemplate");}    */
                Object[][] datas = Rdbms.getRecordFieldsByFilter(schemaPrefix+"-config", "sample", 
                        filterFieldName, filterFieldValue, new String[] { "json_definition"});
                Rdbms.closeRdbms();
                JSONObject proceduresList = new JSONObject();
                JSONArray jArray = new JSONArray();
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(datas[0][0].toString())){  
                    Object[] errMsg = LPFrontEnd.responseError(LPArray.array2dTo1d(datas), language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    return;
                }else{
                   Response.ok().build();                   
                   jArray.addAll(Arrays.asList(LPArray.array2dTo1d(datas)));                                 
                }           
                response.getWriter().write(jArray.toString());                  
                return;
            case "UNRECEIVESAMPLES_LIST":            
                String[] sortFieldsNameArr = null;
                String sortFieldsName = request.getParameter("sortFieldsName"); 
                String sampleFieldToRetrieve = request.getParameter("sampleFieldToRetrieve"); 
                
                if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                    sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                }else{   sortFieldsNameArr=null;}             
                String[] sampleFieldToRetrieveArr = new String[]{"sample_id"};
                if (sampleFieldToRetrieve!=null){
                    sampleFieldToRetrieveArr=LPArray.addValueToArray1D(sampleFieldToRetrieveArr, sampleFieldToRetrieve.split("\\|"));
                }                
                
                String[] whereFieldsNameArr = null;
                Object[] whereFieldsValueArr = null;
                String whereFieldsName = request.getParameter("whereFieldsName"); 
                if (whereFieldsName==null){whereFieldsName="";}
                String whereFieldsValue = request.getParameter("whereFieldsValue"); 
                if (whereFieldsValue==null){whereFieldsValue="";}
                
                if ( (whereFieldsName=="") && (whereFieldsValue=="") ){
                    whereFieldsNameArr = LPArray.addValueToArray1D(whereFieldsNameArr, "received_by is null");
                    whereFieldsValueArr = LPArray.addValueToArray1D(whereFieldsValueArr, "");
                }else{
                    whereFieldsNameArr=LPArray.addValueToArray1D(whereFieldsNameArr, whereFieldsName.split("\\|"));
                    whereFieldsValueArr = LPArray.addValueToArray1D(whereFieldsValueArr, LPArray.convertStringWithDataTypeToObjectArray(whereFieldsValue.split("\\|")));                                          
                    for (int iFields=0; iFields<whereFieldsNameArr.length; iFields++){
                        if (LPPlatform.isEncryptedField(schemaPrefix+"-data", "sample", whereFieldsNameArr[iFields])){                
                            HashMap<String, String> hm = LPPlatform.encryptEncryptableFieldsAddBoth(whereFieldsNameArr[iFields], whereFieldsNameArr[iFields]);
                            whereFieldsNameArr[iFields]= hm.keySet().iterator().next();    
                            if ( hm.get(whereFieldsNameArr[iFields]).length()!=whereFieldsNameArr[iFields].length()){
                                String newWhereFieldValues = hm.get(whereFieldsNameArr[iFields]);
                                whereFieldsValueArr[iFields]=newWhereFieldValues;
                            }
                        }
                        String[] tokenFieldValue = LPPlatform.getTokenFieldValue(whereFieldsValueArr[iFields].toString(), finalToken);
                        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(tokenFieldValue[0])) 
                            whereFieldsValueArr[iFields]=tokenFieldValue[1];                                                    
                    }                                    
                }  
                
                String myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-data", "sample",
                        whereFieldsNameArr, whereFieldsValueArr,
                        sampleFieldToRetrieveArr, sortFieldsNameArr);
                Rdbms.closeRdbms();
                if (myData.contains(LPPlatform.LAB_FALSE)){ 
                    Response.ok().build();
                    response.getWriter().write("[]");  
                    /*Object[] errMsg = LPFrontEnd.responseError(new String[]{myData}, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    */
                }else{
                    Response.ok().build();
                    response.getWriter().write(myData);           
                }
                Rdbms.closeRdbms();
                return;
            case "SAMPLES_INPROGRESS_LIST":   
                whereFieldsName = request.getParameter("whereFieldsName"); 
                if (whereFieldsName==null){whereFieldsName="";}
                whereFieldsValue = request.getParameter("whereFieldsValue"); 

                sampleFieldToRetrieve = request.getParameter("sampleFieldToRetrieve"); 
                String testFieldToRetrieve = request.getParameter("testFieldToRetrieve"); 
                String sampleLastLevel = request.getParameter("sampleLastLevel");                                
                
                if (sampleLastLevel==null){
                    sampleLastLevel="SAMPLE";
                }                                
                sampleFieldToRetrieveArr = new String[]{"sample_id"};
                if (sampleFieldToRetrieve!=null){
                    sampleFieldToRetrieveArr=LPArray.addValueToArray1D(sampleFieldToRetrieveArr, sampleFieldToRetrieve.split("\\|"));
                }
                String[] testFieldToRetrieveArr = new String[]{"test_id"};
                if (testFieldToRetrieve!=null){
                    testFieldToRetrieveArr=LPArray.addValueToArray1D(testFieldToRetrieveArr, testFieldToRetrieve.split("\\|"));
                }                
                whereFieldsNameArr = null;
                whereFieldsValueArr = null;                
                if (!whereFieldsName.contains("status")){
                    whereFieldsNameArr = LPArray.addValueToArray1D(whereFieldsNameArr, "received_by is not null");
                    whereFieldsValueArr = LPArray.addValueToArray1D(whereFieldsValueArr, "");
                    Object[] recEncrypted = LPPlatform.encryptString("RECEIVED");
                    whereFieldsNameArr=LPArray.addValueToArray1D(whereFieldsNameArr, "status in|");                
                    whereFieldsValueArr=LPArray.addValueToArray1D(whereFieldsValueArr, "RECEIVED|"+recEncrypted[1]);                
                }
                if ( (whereFieldsName!=null) && (whereFieldsValue!=null) ){
                    whereFieldsNameArr=LPArray.addValueToArray1D(whereFieldsNameArr, whereFieldsName.split("\\|"));
                    whereFieldsValueArr = LPArray.addValueToArray1D(whereFieldsValueArr, LPArray.convertStringWithDataTypeToObjectArray(whereFieldsValue.split("\\|")));                                          
                    for (int iFields=0; iFields<whereFieldsNameArr.length; iFields++){
                        if (LPPlatform.isEncryptedField(schemaPrefix+"-data", "sample", whereFieldsNameArr[iFields])){                
                            HashMap<String, String> hm = LPPlatform.encryptEncryptableFieldsAddBoth(whereFieldsNameArr[iFields], whereFieldsNameArr[iFields]);
                            whereFieldsNameArr[iFields]= hm.keySet().iterator().next();    
                            if ( hm.get(whereFieldsNameArr[iFields]).length()!=whereFieldsNameArr[iFields].length()){
                                String newWhereFieldValues = hm.get(whereFieldsNameArr[iFields]);
                                whereFieldsValueArr[iFields]=newWhereFieldValues;
                            }
                        }
                        String[] tokenFieldValue = LPPlatform.getTokenFieldValue(whereFieldsValueArr[iFields].toString(), finalToken);
                        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(tokenFieldValue[0])) 
                            whereFieldsValueArr[iFields]=tokenFieldValue[1];                                                    
                    }                                    
                }            
/*                Object[] whereFieldsValueArr = new Object[]{"RECEIVED"};
                LPPlatform labPlat = new LPPlatform();
                
                Object[] wordDecripted = labPlat.decryptString("fS￵ￃﾺﾀ?p￦￠￝ﾅ\\ﾭﾡ￡");
                Object[] recEncrypted = labPlat.encryptString("RECEIVED");
                whereFieldsValueArr=LPArray.addValueToArray1D(whereFieldsValueArr, recEncrypted[1]);
                recEncrypted = labPlat.encryptString("LOGGED");
                whereFieldsValueArr=LPArray.addValueToArray1D(whereFieldsValueArr, recEncrypted[1]);
*/                
                
/*                LPPlatform labPlat = new LPPlatform();
                Object[] recEncrypted = labPlat.encryptString("LOGGED");
                Object[] whereFieldsValueArr = new Object[]{recEncrypted[1]};
                if (whereFieldsValue!=null){
                    whereFieldsValueArr=LPArray.addValueToArray1D(whereFieldsValueArr, whereFieldsValue.split("\\|"));
                }                 
*/                
 /*               whereFieldsNameArr = LPArray.addValueToArray1D(whereFieldsNameArr, "status");
                whereFieldsNameArr = LPArray.addValueToArray1D(whereFieldsNameArr, "RECEIVED");
                
                String[] whereFieldsNameArr=whereFieldsName.split("\\|");                
                Object[] whereFieldsValueArr = LPArray.convertStringWithDataTypeToObjectArray(whereFieldsValue.split("\\|"));
  */              
                sortFieldsNameArr = null;
                sortFieldsName = request.getParameter("sortFieldsName"); 
                if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                    sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                }else{   sortFieldsNameArr=null;}  
                
                if ("SAMPLE".equals(sampleLastLevel)){
                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-data", "sample",
                            whereFieldsNameArr, whereFieldsValueArr, sampleFieldToRetrieveArr, sortFieldsNameArr);
                    if (myData==null){
                        Rdbms.closeRdbms(); 
                        Response.ok().build();
                        response.getWriter().write("[]");  
                        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No info found or error running the query for this sample "); 
                        return;
                    }
                    if ( myData.contains(LPPlatform.LAB_FALSE)) {  
                        Response.ok().build();
                        response.getWriter().write("[]");  
                        //Object[] errMsg = LPFrontEnd.responseError(new String[]{myData}, language, null);
                        //response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }
                }else{                    
                    whereFieldsNameArr = LPArray.addValueToArray1D(whereFieldsNameArr, "sample_id");
                    whereFieldsValueArr = LPArray.addValueToArray1D(whereFieldsValueArr, 2);
                    JSONArray samplesArray = new JSONArray();    
                    JSONArray sampleArray = new JSONArray();    
                    Object[][] mySamples = Rdbms.getRecordFieldsByFilter(schemaPrefix+"-data", "sample",
                            whereFieldsNameArr, whereFieldsValueArr, sampleFieldToRetrieveArr);
                    if ( LPPlatform.LAB_FALSE.equalsIgnoreCase(mySamples[0][0].toString()) ){
                        Rdbms.closeRdbms(); 
                        Object[] errMsg = LPFrontEnd.responseError(LPArray.array2dTo1d(mySamples), language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                        return;
                    }
                    for (int xProc=0; xProc<mySamples.length; xProc++){
                        JSONObject sampleObj = new JSONObject();
                        Integer sampleId = Integer.valueOf(mySamples[xProc][0].toString());
                        for (int yProc=0; yProc<mySamples[0].length; yProc++){
                            if (mySamples[xProc][yProc] instanceof Timestamp){
                                sampleObj.put(sampleFieldToRetrieveArr[yProc], mySamples[xProc][yProc].toString());
                            }
                            sampleObj.put(sampleFieldToRetrieveArr[yProc], mySamples[xProc][yProc]);
                        }
                        if ( ("TEST".equals(sampleLastLevel)) || ("RESULT".equals(sampleLastLevel)) ) {
                            String[] testWhereFieldsNameArr = new String[]{"sample_id"};
                            Object[] testWhereFieldsValueArr = new Object[]{sampleId};
                            Object[][] mySampleAnalysis = Rdbms.getRecordFieldsByFilter(schemaPrefix+"-data", "sample_analysis",
                                    testWhereFieldsNameArr, testWhereFieldsValueArr, testFieldToRetrieveArr);          
                            for (Object[] mySampleAnalysi : mySampleAnalysis) {
                                JSONObject testObj = new JSONObject();
                                for (int ySmpAna = 0; ySmpAna<mySampleAnalysis[0].length; ySmpAna++) {
                                    if (mySampleAnalysi[ySmpAna] instanceof Timestamp) {
                                        testObj.put(testFieldToRetrieveArr[ySmpAna], mySampleAnalysi[ySmpAna].toString());
                                    } else {
                                        testObj.put(testFieldToRetrieveArr[ySmpAna], mySampleAnalysi[ySmpAna]);
                                    }
                                }      
                                sampleArray.add(testObj);
                            }
                            sampleObj.put("sample_analysis", sampleArray);
                        }
                        sampleArray.add(sampleObj);                        
                    }
                    Rdbms.closeRdbms();
                    samplesArray.add(sampleArray);
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("samples", samplesArray);                    
                    Response.ok().build();
                    response.getWriter().write(jsonObj.toString());               
                }
                    //Response.serverError().entity(myData).build();
                    //return Response.ok(myData).build();     
                    Rdbms.closeRdbms(); 
                    return;                                        
                case "ANALYSIS_ALL_LIST":          
                    String fieldToRetrieve = request.getParameter("fieldToRetrieve"); 

                    String[] fieldToRetrieveArr = new String[0];
                    if ( (fieldToRetrieve==null) || (fieldToRetrieve.length()==0) ){
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "code");
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "method_name");                           
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "method_version");                           
                    }else{
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, fieldToRetrieve.split("\\|"));
                        if (LPArray.valuePosicInArray(fieldToRetrieveArr, "code")==-1){
                            fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "code");     
                            fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "method_name");                           
                            fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "method_version");                           
                        }                    
                    }                
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter("sortFieldsName"); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                    }else{   sortFieldsNameArr=null;}  

                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-config", "analysis_methods_view",
                            new String[]{"code is not null"},new Object[]{true}, fieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains(LPPlatform.LAB_FALSE)){  
                        Object[] errMsg = LPFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }
                    Rdbms.closeRdbms();
                    return;         
                case "GET_SAMPLE_ANALYSIS_LIST":    
                    String[] sampleAnalysisFixFieldToRetrieveArr = new String[]{"sample_id", "test_id", };
                    String[] mandatoryParamsAction = new String[]{PARAMETER_SAMPLE_ID};
                    Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParamsAction);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        Rdbms.closeRdbms(); 
                        return ;                
                    }                                          
                    String sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                                                      
                    Integer sampleId = Integer.parseInt(sampleIdStr);       
                    
                    String[] sampleAnalysisFieldToRetrieveArr = new String[0];
                    String sampleAnalysisFieldToRetrieve = request.getParameter("sampleAnalysisFieldToRetrieve");  
                    if (! ((sampleAnalysisFieldToRetrieve==null) || (sampleAnalysisFieldToRetrieve.contains("undefined"))) ) {
                         sampleAnalysisFieldToRetrieveArr=  sampleAnalysisFieldToRetrieve.split("\\|");                             
                    }    
                    sampleAnalysisFieldToRetrieveArr = LPArray.addValueToArray1D(sampleAnalysisFieldToRetrieveArr, sampleAnalysisFixFieldToRetrieveArr);
                    
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter("sortFieldsName"); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr =  sortFieldsName.split("\\|");                                    
                    }else{   
                        sortFieldsNameArr = LPArray.addValueToArray1D(sortFieldsNameArr, "sample_id");
                        sortFieldsNameArr = LPArray.addValueToArray1D(sortFieldsNameArr, "test_id");                        
                    }  
                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-data", "sample_analysis",
                            new String[]{"sample_id"},new Object[]{sampleId}, sampleAnalysisFieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains(LPPlatform.LAB_FALSE)){  
                        Object[] errMsg = LPFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }
                    
                    Rdbms.closeRdbms();
                    return;                                            
                case "GET_SAMPLE_ANALYSIS_RESULT_LIST":
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             
                    if ( (sampleIdStr==null) || (sampleIdStr.contains("undefined")) ) {
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, "sampleId="+request.getParameter(PARAMETER_SAMPLE_ID));
                        errObject = LPArray.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);   
                        Rdbms.closeRdbms(); 
                        return ;
                    }                              
                    sampleId = Integer.parseInt(sampleIdStr);                           
                    String resultFieldToRetrieve = request.getParameter("resultFieldToRetrieve");  
                    String[] resultFieldToRetrieveArr=  resultFieldToRetrieve.split("\\|");
                    String fieldName = "result_id";
                    if (LPArray.valuePosicInArray(resultFieldToRetrieveArr, fieldName)==-1){
                        resultFieldToRetrieveArr=LPArray.addValueToArray1D(resultFieldToRetrieveArr, fieldName);}     
                    fieldName = "param_name";
                    if (LPArray.valuePosicInArray(resultFieldToRetrieveArr, fieldName)==-1){
                        resultFieldToRetrieveArr=LPArray.addValueToArray1D(resultFieldToRetrieveArr, fieldName);}     
                    
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter("sortFieldsName"); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                    }else{   
                        sortFieldsNameArr = LPArray.addValueToArray1D(sortFieldsNameArr, "sample_id");
                        sortFieldsNameArr = LPArray.addValueToArray1D(sortFieldsNameArr, "test_id");
                        sortFieldsNameArr = LPArray.addValueToArray1D(sortFieldsNameArr, "result_id");
                    }  
                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-data", "sample_analysis_result",
                            new String[]{"sample_id"},new Object[]{sampleId}, resultFieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains(LPPlatform.LAB_FALSE)){  
                        Object[] errMsg = LPFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }                    
                    Rdbms.closeRdbms();
                    return;  
                case "CHANGEOFCUSTODY_SAMPLE_HISTORY":     
                    sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);                             
                    if ( (sampleIdStr==null) || (sampleIdStr.contains("undefined")) ) {
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, "sampleId="+request.getParameter(PARAMETER_SAMPLE_ID));
                        errObject = LPArray.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);   
                        Rdbms.closeRdbms(); 
                        return ;
                    }                              
                    sampleId = Integer.parseInt(sampleIdStr);      

                    fieldToRetrieve = request.getParameter("fieldToRetrieve");                    
                    sortFieldsName = request.getParameter("sortFieldsName");
                    
                    fieldToRetrieveArr = new String[0];
                    if ( (fieldToRetrieve==null) || (fieldToRetrieve.length()==0) ){
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "sample_id");
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "custodian");
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "custodian_candidate");                           
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "coc_started_on");                           
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "status");                           
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "coc_confirmed_on");                           
                    }else{
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, fieldToRetrieve.split("\\|"));
                        if (LPArray.valuePosicInArray(fieldToRetrieveArr, "custodian")==-1){
                            fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "sample_id");
                            fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "custodian");
                            fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "custodian_candidate");                           
                            fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "coc_started_on");                           
                            fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "status");                           
                            fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "coc_confirmed_on");                           
                        }                    
                    }                       
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter("sortFieldsName"); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                    }else{   
                        sortFieldsNameArr = LPArray.addValueToArray1D(sortFieldsNameArr, "sample_id");
                        sortFieldsNameArr = LPArray.addValueToArray1D(sortFieldsNameArr, "coc_started_on");                        
                    }  
                    
                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-data", "sample_coc",
                            new String[]{"sample_id"},new Object[]{sampleId}, fieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains(LPPlatform.LAB_FALSE)){  
                        Object[] errMsg = LPFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }                             
                    Rdbms.closeRdbms();
                    return;                      
                case "CHANGEOFCUSTODY_USERS_LIST":     

                    fieldToRetrieve = request.getParameter("fieldToRetrieve");                    
                    sortFieldsName = request.getParameter("sortFieldsName");
                    
                    fieldToRetrieveArr = new String[0];
                    if ( (fieldToRetrieve==null) || (fieldToRetrieve.length()==0) ){
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "user_name");
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "person_name");
                    }else{
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, fieldToRetrieve.split("\\|"));
                        if (LPArray.valuePosicInArray(fieldToRetrieveArr, "person_name")==-1){
                            fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, "person_name");
                        }                    
                    }                       
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter("sortFieldsName"); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                    }else{   
                        sortFieldsNameArr = LPArray.addValueToArray1D(sortFieldsNameArr, "user_name");
                        sortFieldsNameArr = LPArray.addValueToArray1D(sortFieldsNameArr, "person_name");                        
                    }  
                    
                    myData = Rdbms.getRecordFieldsByFilterJSON("app", "users",
                            new String[]{"user_name NOT IN|"},new Object[]{"0"}, fieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains(LPPlatform.LAB_FALSE)){  
                        Object[] errMsg = LPFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }                             
                    Rdbms.closeRdbms();
                    return;                      
                case "GET_SAMPLE_ANALYSIS_RESULT_SPEC":
                    String resultIdStr = request.getParameter("result_id");
                    if ( (resultIdStr==null) || (resultIdStr.contains("undefined")) ) {
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, "sampleId="+request.getParameter(PARAMETER_SAMPLE_ID));
                        errObject = LPArray.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);   
                        Rdbms.closeRdbms(); 
                        return ;
                    }
/*                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-data", "sample_analysis_result",
                            new String[]{"sample_id"},new Object[]{resultIdStr}, resultFieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains(LPPlatform.LAB_FALSE)){  
                        Object[] errMsg = LPFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }
  */                  
                    Rdbms.closeRdbms();
                    return;  
                case "SAMPLE_ENTIRE_STRUCTURE":
                   sampleIdStr = request.getParameter(PARAMETER_SAMPLE_ID);        
                   sampleId = Integer.parseInt(sampleIdStr);     
                    String qry = "";                    
                    qry = qry  + "select row_to_json(sQry)from "
                                    +" ( select s.sample_id, s.status, "
                                    +" ( select COALESCE(array_to_json(array_agg(row_to_json(saQry))),'[]') from  "
                                    +"( select sa.test_id, sa.analysis, "

                                    +"( select COALESCE(array_to_json(array_agg(row_to_json(sarQry))),'[]') from "
                                    +"( select sar.result_id, sar.raw_value from \"process-us-data\".sample_analysis_result sar where sar.test_id=sa.test_id "        
                                    +"order by sar.test_id asc      ) sarQry    ) as sample_analysis_result "          
          
                                    +"from \"process-us-data\".sample_analysis sa where sa.sample_id=s.sample_id "         
                                    +"order by sa.test_id asc      ) saQry    ) as sample_analysis "
                                    +"from \"process-us-data\".sample s where s.sample_id = 146 ) sQry   ";
                
            CachedRowSetImpl prepRdQuery = Rdbms.prepRdQuery(qry, new Object[]{sampleId});
            Response.ok().build();            
            
            boolean first = prepRdQuery.first();
            String finalString = "";
            
            String jsonarrayf = prepRdQuery.getString(1);
                    
            response.getWriter().write(jsonarrayf);                    
            Rdbms.closeRdbms();    
return;
                default:      
                    errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                    errObject = LPArray.addValueToArray1D(errObject, "API Error Message: actionName "+actionName+ " not recognized as an action by this API");                                                            
                    Object[] errMsg = LPFrontEnd.responseError(errObject, language, schemaPrefix);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    Rdbms.closeRdbms();                    
                }               
/*            Rdbms.closeRdbms();
            if ("LABPLANET_TRUE".equalsIgnoreCase(dataSample[0].toString())){                                
                //out.println(Arrays.toString(dataSample));
                response.getWriter().write(Arrays.toString(dataSample));
                Response.serverError().entity(errObject).build();
                Response.ok().build();
            }else{
               // response.getWriter().write(Arrays.toString(dataSample));                          
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
                out.println(Arrays.toString(dataSample));                    
            }      
*/            
        }catch(Exception e){      
            Rdbms.closeRdbms();                   
            String[] errObject = new String[]{e.getMessage()};
            Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
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