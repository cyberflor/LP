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
import LabPLANET.utilities.LPJson;
import com.labplanet.servicios.app.globalAPIsParams;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Administrator
 */
public class sampleAPIfrontend extends HttpServlet {
    public static final String API_ENDPOINT_GET_SAMPLETEMPLATES="GET_SAMPLETEMPLATES";
    public static final String API_ENDPOINT_UNRECEIVESAMPLES_LIST="UNRECEIVESAMPLES_LIST";
    public static final String API_ENDPOINT_SAMPLES_INPROGRESS_LIST="SAMPLES_INPROGRESS_LIST";
    public static final String API_ENDPOINT_ANALYSIS_ALL_LIST="ANALYSIS_ALL_LIST";
    public static final String API_ENDPOINT_GET_SAMPLE_ANALYSIS_LIST="GET_SAMPLE_ANALYSIS_LIST";
    public static final String API_ENDPOINT_GET_SAMPLE_ANALYSIS_RESULT_LIST="GET_SAMPLE_ANALYSIS_RESULT_LIST";
    public static final String API_ENDPOINT_CHANGEOFCUSTODY_SAMPLE_HISTORY="CHANGEOFCUSTODY_SAMPLE_HISTORY";
    public static final String API_ENDPOINT_CHANGEOFCUSTODY_USERS_LIST="CHANGEOFCUSTODY_USERS_LIST";
    public static final String API_ENDPOINT_GET_SAMPLE_ANALYSIS_RESULT_SPEC="GET_SAMPLE_ANALYSIS_RESULT_SPEC";
    public static final String API_ENDPOINT_SAMPLE_ENTIRE_STRUCTURE="SAMPLE_ENTIRE_STRUCTURE";
    
    public static final String MANDATORY_PARAMS_MAIN_SERVLET="actionName|finalToken|schemaPrefix";       
    
    public static final String TABLE_NAME_USERS="users";
    public static final String TABLE_NAME_SAMPLE="sample";
    public static final String TABLE_NAME_SAMPLE_ANALYSIS="sample_analysis";
    public static final String TABLE_NAME_SAMPLE_ANALYSIS_RESULT="sample_analysis_result";
    
    public static final String VIEW_NAME_ANALYSIS_METHOD="analysis_methods_view";
    public static final String VIEW_NAME_SAMPLE_COC_NAMES="sample_coc_names";
    
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

        try (PrintWriter out = response.getWriter()) {

            Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, MANDATORY_PARAMS_MAIN_SERVLET.split("\\|"));                       
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                LPFrontEnd.servletReturnResponseError(request, response, 
                    LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                return;          
            }             
            String schemaPrefix = request.getParameter(globalAPIsParams.REQUEST_PARAM_SCHEMA_PREFIX);            
            String actionName = request.getParameter(globalAPIsParams.REQUEST_PARAM_ACTION_NAME);
            String finalToken = request.getParameter(globalAPIsParams.REQUEST_PARAM_FINAL_TOKEN);                   

            String schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);    
            String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_CONFIG);  
        
            Token token = new Token(finalToken);

            if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}   
        
      
            
            switch (actionName.toUpperCase()){
            case API_ENDPOINT_GET_SAMPLETEMPLATES:       
                String[] filterFieldName = new String[]{"json_definition is not null"};
                Object[] filterFieldValue = new Object[]{""};
/*                filterFieldName = LPArray.addValueToArray1D(filterFieldName, "code");
                if ("process-us".equalsIgnoreCase(schemaPrefix)){
                    filterFieldValue = LPArray.addValueToArray1D(filterFieldValue, "specSamples");
                }else{filterFieldValue = LPArray.addValueToArray1D(filterFieldValue, "sampleTemplate");}    */
                Object[][] datas = Rdbms.getRecordFieldsByFilter(schemaConfigName,TABLE_NAME_SAMPLE, 
                        filterFieldName, filterFieldValue, new String[] { "json_definition"});
                Rdbms.closeRdbms();
                JSONObject proceduresList = new JSONObject();
                JSONArray jArray = new JSONArray();
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(datas[0][0].toString())){  
                    Object[] errMsg = LPFrontEnd.responseError(LPArray.array2dTo1d(datas), language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    return;
                }else{                   
                   jArray.addAll(Arrays.asList(LPArray.array2dTo1d(datas)));    
                }           
                LPFrontEnd.servletReturnSuccess(request, response, jArray);
                return;
            case API_ENDPOINT_UNRECEIVESAMPLES_LIST:   
                areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, sampleAPIParams.MANDATORY_PARAMS_FRONTEND_UNRECEIVESAMPLES_LIST.split("\\|"));
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                    LPFrontEnd.servletReturnResponseError(request, response, 
                            LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                    return;                  
                }                                  
                String[] sortFieldsNameArr = null;
                String[] sampleFieldToRetrieveArr = null;
                String sortFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_SORT_FIELDS_NAME); 
                String sampleFieldToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_SAMPLE_FIELD_TO_RETRIEVE); 
                
                if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                    sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                }else{  sortFieldsNameArr = sampleAPIParams.MANDATORY_PARAMS_FRONTEND_UNRECEIVESAMPLES_LIST_SORT_FIELDS_NAME_DEFAULT_VALUE.split("\\|");}             
                if (sampleFieldToRetrieve!=null){
                    sampleFieldToRetrieveArr=LPArray.addValueToArray1D(sampleFieldToRetrieveArr, sampleFieldToRetrieve.split("\\|"));
                }else{
                    sampleFieldToRetrieveArr=sampleAPIParams.MANDATORY_PARAMS_FRONTEND_UNRECEIVESAMPLES_LIST_SAMPLE_FIELD_RETRIEVE_DEFAULT_VALUE.split("\\|");
                }                
                
                String[] whereFieldsNameArr = null;
                Object[] whereFieldsValueArr = null;
                String whereFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_WHERE_FIELDS_NAME); 
                if (whereFieldsName==null){whereFieldsName="";}
                String whereFieldsValue = request.getParameter(globalAPIsParams.REQUEST_PARAM_WHERE_FIELDS_VALUE); 
                if (whereFieldsValue==null){whereFieldsValue="";}
                
                if ( ("".equals(whereFieldsName)) && ("".equals(whereFieldsValue)) ){
                    whereFieldsNameArr = LPArray.addValueToArray1D(whereFieldsNameArr, "received_by is null");
                    whereFieldsValueArr = LPArray.addValueToArray1D(whereFieldsValueArr, "");
                }else{
                    whereFieldsNameArr=LPArray.addValueToArray1D(whereFieldsNameArr, whereFieldsName.split("\\|"));
                    whereFieldsValueArr = LPArray.addValueToArray1D(whereFieldsValueArr, LPArray.convertStringWithDataTypeToObjectArray(whereFieldsValue.split("\\|")));                                          
                    for (int iFields=0; iFields<whereFieldsNameArr.length; iFields++){
                        if (LPPlatform.isEncryptedField(schemaDataName, "sample", whereFieldsNameArr[iFields])){                
                            HashMap<String, String> hm = LPPlatform.encryptEncryptableFieldsAddBoth(whereFieldsNameArr[iFields], whereFieldsNameArr[iFields]);
                            whereFieldsNameArr[iFields]= hm.keySet().iterator().next();    
                            if ( hm.get(whereFieldsNameArr[iFields]).length()!=whereFieldsNameArr[iFields].length()){
                                String newWhereFieldValues = hm.get(whereFieldsNameArr[iFields]);
                                whereFieldsValueArr[iFields]=newWhereFieldValues;
                            }
                        }
                        String[] tokenFieldValue = Token.getTokenFieldValue(whereFieldsValueArr[iFields].toString(), finalToken);
                        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(tokenFieldValue[0])) 
                            whereFieldsValueArr[iFields]=tokenFieldValue[1];                                                    
                    } 
                    whereFieldsNameArr = LPArray.addValueToArray1D(whereFieldsNameArr, "received_by is null");
                    whereFieldsValueArr = LPArray.addValueToArray1D(whereFieldsValueArr, "");
                }  
                
                String myData = Rdbms.getRecordFieldsByFilterJSON(schemaDataName, TABLE_NAME_SAMPLE,
                        whereFieldsNameArr, whereFieldsValueArr,
                        sampleFieldToRetrieveArr, sortFieldsNameArr);
                Rdbms.closeRdbms();
                if (myData.contains(LPPlatform.LAB_FALSE)){       
                    LPFrontEnd.servletReturnSuccess(request, response);     
                }else{
                    LPFrontEnd.servletReturnSuccess(request, response, myData);       
                }
                return;
            case API_ENDPOINT_SAMPLES_INPROGRESS_LIST:   
                whereFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_WHERE_FIELDS_NAME); 
                if (whereFieldsName==null){whereFieldsName="";}
                whereFieldsValue = request.getParameter(globalAPIsParams.REQUEST_PARAM_WHERE_FIELDS_VALUE); 

                sampleFieldToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_SAMPLE_FIELD_TO_RETRIEVE); 
                String testFieldToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_TEST_FIELD_TO_RETRIEVE); 
                String sampleLastLevel = request.getParameter(globalAPIsParams.REQUEST_PARAM_SAMPLE_LAST_LEVEL);                 
                
                String addSampleAnalysis = request.getParameter(globalAPIsParams.REQUEST_PARAM_ADD_SAMPLE_ANALYSIS); 
                if (addSampleAnalysis==null){addSampleAnalysis="false";}
                String addSampleAnalysisFieldToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_ADD_SAMPLE_ANALYSIS_FIELD_TO_RETRIEVE); 
                String[] addSampleAnalysisFieldToRetrieveArr = sampleAPIParams.MANDATORY_PARAMS_FRONTEND_SAMPLES_INPROGRESS_LIST_SAMPLE_ANALYSIS_FIELD_RETRIEVE_DEFAULT_VALUE.split("\\|");
                if ( (addSampleAnalysisFieldToRetrieve!=null) && (addSampleAnalysisFieldToRetrieve.length()>0) ) {
                    addSampleAnalysisFieldToRetrieveArr=LPArray.addValueToArray1D(addSampleAnalysisFieldToRetrieveArr, addSampleAnalysisFieldToRetrieve.split("\\|"));
                }                                

                String addSampleAnalysisResult = request.getParameter(globalAPIsParams.REQUEST_PARAM_ADD_SAMPLE_ANALYSIS_RESULT); 
                if (addSampleAnalysisResult==null){addSampleAnalysisResult="false";}
                String addSampleAnalysisResultFieldToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_ADD_SAMPLE_ANALYSIS_RESULT_FIELD_TO_RETRIEVE); 
                String[] addSampleAnalysisResultFieldToRetrieveArr = sampleAPIParams.MANDATORY_PARAMS_FRONTEND_SAMPLES_INPROGRESS_LIST_SAMPLE_ANALYSIS_RESULT_FIELD_RETRIEVE_DEFAULT_VALUE.split("\\|");
                if ( (addSampleAnalysisResultFieldToRetrieve!=null) && (addSampleAnalysisResultFieldToRetrieve.length()>0) ) {
                    addSampleAnalysisResultFieldToRetrieveArr=LPArray.addValueToArray1D(addSampleAnalysisResultFieldToRetrieveArr, addSampleAnalysisResultFieldToRetrieve.split("\\|"));
                }                                
                
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
                        if (LPPlatform.isEncryptedField(schemaDataName, "sample", whereFieldsNameArr[iFields])){                
                            HashMap<String, String> hm = LPPlatform.encryptEncryptableFieldsAddBoth(whereFieldsNameArr[iFields], whereFieldsNameArr[iFields]);
                            whereFieldsNameArr[iFields]= hm.keySet().iterator().next();    
                            if ( hm.get(whereFieldsNameArr[iFields]).length()!=whereFieldsNameArr[iFields].length()){
                                String newWhereFieldValues = hm.get(whereFieldsNameArr[iFields]);
                                whereFieldsValueArr[iFields]=newWhereFieldValues;
                            }
                        }
                        String[] tokenFieldValue = Token.getTokenFieldValue(whereFieldsValueArr[iFields].toString(), finalToken);
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
                sortFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_SORT_FIELDS_NAME); 
                if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                    sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                }else{   sortFieldsNameArr=null;}  
                
                if ("SAMPLE".equals(sampleLastLevel)){
                    Object[][] mySamples = Rdbms.getRecordFieldsByFilter(schemaDataName, TABLE_NAME_SAMPLE,
                        whereFieldsNameArr, whereFieldsValueArr, sampleFieldToRetrieveArr, sortFieldsNameArr);
                    if (mySamples==null){
                        LPFrontEnd.servletReturnSuccess(request, response);       
                        return;
                    }
                    if ( LPPlatform.LAB_FALSE.equalsIgnoreCase(mySamples[0][0].toString())) {  
                        LPFrontEnd.servletReturnSuccess(request, response);       
                        return;
                    }else{
                        
                        JSONArray mySamplesJSArr = new JSONArray();
                        Integer iLines = 0;
                        for (Object[] mySample : mySamples) {
                            iLines++;
                            JSONObject mySampleJSObj = LPJson.convertArrayRowToJSONObject(sampleFieldToRetrieveArr, mySample);                
                            if ("TRUE".equalsIgnoreCase(addSampleAnalysis)){
                                String[] testWhereFieldsNameArr = new String[]{"sample_id"};
                                Integer sampleIdPosicInArray = LPArray.valuePosicInArray(sampleFieldToRetrieveArr, "sample_id");
                                Object[] testWhereFieldsValueArr = new Object[]{Integer.parseInt(mySample[sampleIdPosicInArray].toString())};
                                Object[][] mySampleAnalysis = Rdbms.getRecordFieldsByFilter(schemaDataName, TABLE_NAME_SAMPLE_ANALYSIS,
                                        testWhereFieldsNameArr, testWhereFieldsValueArr, addSampleAnalysisFieldToRetrieveArr);          
                                JSONArray mySamplesAnaJSArr = new JSONArray();
                                if ( LPPlatform.LAB_FALSE.equalsIgnoreCase(mySampleAnalysis[0][0].toString()) ){
                                    mySampleJSObj.put("sample_analysis", mySamplesAnaJSArr);
                                }else{                                    
                                    for (Object[] mySampleAnalysi : mySampleAnalysis) {
                                        JSONObject mySampleAnaJSObj = LPJson.convertArrayRowToJSONObject(addSampleAnalysisFieldToRetrieveArr, mySampleAnalysi);

                                        if ("TRUE".equalsIgnoreCase(addSampleAnalysisResult)){
                                            String[] sarWhereFieldsNameArr = new String[]{"test_id"};
                                            Integer testIdPosicInArray = LPArray.valuePosicInArray(addSampleAnalysisFieldToRetrieveArr, "test_id");
                                            Object[] sarWhereFieldsValueArr = new Object[]{Integer.parseInt(mySampleAnalysi[testIdPosicInArray].toString())};
                                            Object[][] mySampleAnalysisResults = Rdbms.getRecordFieldsByFilter(schemaDataName, TABLE_NAME_SAMPLE_ANALYSIS_RESULT,
                                                    sarWhereFieldsNameArr, sarWhereFieldsValueArr, addSampleAnalysisResultFieldToRetrieveArr);          
                                            JSONArray mySamplesAnaResJSArr = new JSONArray();
                                            if ( LPPlatform.LAB_FALSE.equalsIgnoreCase(mySampleAnalysisResults[0][0].toString()) ){
                                                mySampleAnaJSObj.put("sample_analysis_result", mySamplesAnaResJSArr);                                        
                                            }
                                            JSONObject mySampleAnaResJSObj = new JSONObject();
                                            for (Object[] mySampleAnalysisResult : mySampleAnalysisResults) {
                                                mySampleAnaResJSObj = LPJson.convertArrayRowToJSONObject(addSampleAnalysisResultFieldToRetrieveArr, mySampleAnalysisResult);
                                                mySamplesAnaResJSArr.add(mySampleAnaResJSObj);
                                            }
                                            mySampleAnaJSObj.put("sample_analysis_result", mySamplesAnaResJSArr);  
                                            //mySamplesAnaResJSArr.add(mySampleAnaResJSObj);
                                        }

                                        mySamplesAnaJSArr.add(mySampleAnaJSObj);
                                    }        
                                    mySampleJSObj.put("sample_analysis", mySamplesAnaJSArr);
                                }
                            }                            
                            mySamplesJSArr.add(mySampleJSObj);
                        }
                        LPFrontEnd.servletReturnSuccess(request, response, mySamplesJSArr);                                   
                        return;
                    }
                }else{                    
                    whereFieldsNameArr = LPArray.addValueToArray1D(whereFieldsNameArr, "sample_id is not null");
                    whereFieldsValueArr = LPArray.addValueToArray1D(whereFieldsValueArr, "");
                    JSONArray samplesArray = new JSONArray();    
                    JSONArray sampleArray = new JSONArray();    
                    Object[][] mySamples = Rdbms.getRecordFieldsByFilter(schemaDataName, TABLE_NAME_SAMPLE,
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
                            Object[][] mySampleAnalysis = Rdbms.getRecordFieldsByFilter(schemaDataName, TABLE_NAME_SAMPLE_ANALYSIS,
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
                    LPFrontEnd.servletReturnSuccess(request, response, jsonObj);                    
                }
                    //Response.serverError().entity(myData).build();
                    //return Response.ok(myData).build();     
                    Rdbms.closeRdbms(); 
                    return;                                        
                case API_ENDPOINT_ANALYSIS_ALL_LIST:          
                    String fieldToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_FIELD_TO_RETRIEVE); 

                    String[] fieldToRetrieveArr = new String[0];
                    if ( (fieldToRetrieve==null) || (fieldToRetrieve.length()==0) ){
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, sampleAPIParams.MANDATORY_FIELDS_FRONTEND_TO_RETRIEVE_ANALYSIS_ALL_LIST.split("\\|"));
                    }else{
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, fieldToRetrieve.split("\\|"));                        
                            fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, sampleAPIParams.MANDATORY_FIELDS_FRONTEND_TO_RETRIEVE_ANALYSIS_ALL_LIST.split("\\|"));                       
                    }                
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_SORT_FIELDS_NAME); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                    }else{   sortFieldsNameArr=null;}  

                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaConfigName, VIEW_NAME_ANALYSIS_METHOD,
                            new String[]{"code is not null"},new Object[]{true}, fieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains(LPPlatform.LAB_FALSE)){  
                        Object[] errMsg = LPFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        LPFrontEnd.servletReturnSuccess(request, response, myData);
                    }
                    return;         
                case API_ENDPOINT_GET_SAMPLE_ANALYSIS_LIST:    
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, sampleAPIParams.MANDATORY_PARAMS_FRONTEND_GET_SAMPLE_ANALYSIS_LIST.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                      
                    String[] sampleAnalysisFixFieldToRetrieveArr = sampleAPIParams.MANDATORY_FIELDS_FRONTEND_TO_RETRIEVE_GET_SAMPLE_ANALYSIS_LIST.split("\\|");                                        
                    String sampleIdStr = request.getParameter(globalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                                                      
                    Integer sampleId = Integer.parseInt(sampleIdStr);       
                    
                    String[] sampleAnalysisFieldToRetrieveArr = new String[0];
                    String sampleAnalysisFieldToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_SAMPLE_ANALYSIS_FIELD_TO_RETRIEVE);  
                    if (! ((sampleAnalysisFieldToRetrieve==null) || (sampleAnalysisFieldToRetrieve.contains("undefined"))) ) {
                         sampleAnalysisFieldToRetrieveArr=  sampleAnalysisFieldToRetrieve.split("\\|");                             
                    }    
                    sampleAnalysisFieldToRetrieveArr = LPArray.addValueToArray1D(sampleAnalysisFieldToRetrieveArr, sampleAnalysisFixFieldToRetrieveArr);
                    
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_SORT_FIELDS_NAME); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr =  sortFieldsName.split("\\|");                                    
                    }else{   
                        sortFieldsNameArr = sampleAPIParams.MANDATORY_FIELDS_FRONTEND_WHEN_SORT_NULL_GET_SAMPLE_ANALYSIS_LIST.split("\\|");                     
                    }  
                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaDataName, TABLE_NAME_SAMPLE_ANALYSIS,
                            new String[]{"sample_id"},new Object[]{sampleId}, sampleAnalysisFieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains(LPPlatform.LAB_FALSE)){  
//                        LPFrontEnd.servletReturnResponseErrorLPFalseDiagnostic(request, response, myData);
                        Object[] errMsg = LPFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        LPFrontEnd.servletReturnSuccess(request, response, myData);
                    }
                    return;                                            
                case API_ENDPOINT_GET_SAMPLE_ANALYSIS_RESULT_LIST:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, sampleAPIParams.MANDATORY_PARAMS_FRONTEND_GET_SAMPLE_ANALYSIS_RESULT_LIST.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                      
                    sampleIdStr = request.getParameter(globalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                                                      
                    sampleId = Integer.parseInt(sampleIdStr);                           
                    String resultFieldToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_SAMPLE_ANALYSIS_RESULT_FIELD_TO_RETRIEVE);
                    String[] resultFieldToRetrieveArr=null;
                    if (resultFieldToRetrieve!=null){resultFieldToRetrieveArr=  resultFieldToRetrieve.split("\\|");}
                    resultFieldToRetrieveArr = LPArray.addValueToArray1D(resultFieldToRetrieveArr, sampleAPIParams.MANDATORY_FIELDS_FRONTEND_TO_RETRIEVE_GET_SAMPLE_ANALYSIS_RESULT_LIST.split("\\|"));
                    
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_SORT_FIELDS_NAME); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                    }else{   
                        sortFieldsNameArr = sampleAPIParams.MANDATORY_FIELDS_FRONTEND_WHEN_SORT_NULL_GET_SAMPLE_ANALYSIS_RESULT_LIST.split("\\|");     
                    }  
                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaDataName, TABLE_NAME_SAMPLE_ANALYSIS_RESULT,
                            new String[]{"sample_id"},new Object[]{sampleId}, resultFieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains(LPPlatform.LAB_FALSE)){  
                        Object[] errMsg = LPFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        LPFrontEnd.servletReturnSuccess(request, response, myData);
                    }                    
                    return;  
                case API_ENDPOINT_CHANGEOFCUSTODY_SAMPLE_HISTORY:     
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, sampleAPIParams.MANDATORY_PARAMS_FRONTEND_CHANGEOFCUSTODY_SAMPLE_HISTORY.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                      
                    sampleIdStr = request.getParameter(globalAPIsParams.REQUEST_PARAM_SAMPLE_ID);                                                         
                    sampleId = Integer.parseInt(sampleIdStr);      

                    fieldToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_FIELD_TO_RETRIEVE);                    
                    sortFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_SORT_FIELDS_NAME);
                    
                    fieldToRetrieveArr = new String[0];
                    if (!( (fieldToRetrieve==null) || (fieldToRetrieve.length()==0) )){                      
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, fieldToRetrieve.split("\\|"));
                    }  
                    fieldToRetrieveArr = LPArray.addValueToArray1D(fieldToRetrieveArr, sampleAPIParams.MANDATORY_FIELDS_FRONTEND_TO_RETRIEVE_CHANGEOFCUSTODY_SAMPLE_HISTORY.split("\\|"));
                    
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_SORT_FIELDS_NAME); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                    }else{   
                        sortFieldsNameArr = sampleAPIParams.MANDATORY_FIELDS_FRONTEND_WHEN_SORT_NULL_CHANGEOFCUSTODY_SAMPLE_HISTORY.split("\\|");                    
                    }                      
                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaDataName, VIEW_NAME_SAMPLE_COC_NAMES,
                            new String[]{"sample_id"},new Object[]{sampleId}, fieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains(LPPlatform.LAB_FALSE)){  
                        Object[] errMsg = LPFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        LPFrontEnd.servletReturnSuccess(request, response, myData);
                    }                             
                    return;                      
                case API_ENDPOINT_CHANGEOFCUSTODY_USERS_LIST:

                    fieldToRetrieve = request.getParameter(globalAPIsParams.REQUEST_PARAM_FIELD_TO_RETRIEVE);                    
                    sortFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_SORT_FIELDS_NAME);
                    
                    fieldToRetrieveArr = new String[0];
                    if (!( (fieldToRetrieve==null) || (fieldToRetrieve.length()==0) )){
                        fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, fieldToRetrieve.split("\\|"));                
                    }   
                    fieldToRetrieveArr=LPArray.addValueToArray1D(fieldToRetrieveArr, sampleAPIParams.MANDATORY_FIELDS_FRONTEND_TO_RETRIEVE_CHANGEOFCUSTODY_USERS_LIST.split("\\|"));
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_SORT_FIELDS_NAME); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = sortFieldsName.split("\\|");                                    
                    }else{   
                        sortFieldsNameArr=sampleAPIParams.MANDATORY_FIELDS_FRONTEND_WHEN_SORT_NULL_CHANGEOFCUSTODY_USERS_LIST.split("\\|"); 
                    }  
                    
                    myData = Rdbms.getRecordFieldsByFilterJSON(LPPlatform.SCHEMA_APP, TABLE_NAME_USERS,
                            new String[]{"user_name NOT IN|"},new Object[]{"0"}, fieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains(LPPlatform.LAB_FALSE)){  
                        Object[] errMsg = LPFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        LPFrontEnd.servletReturnSuccess(request, response, myData);
                    }                             

                    return;                      
                case API_ENDPOINT_GET_SAMPLE_ANALYSIS_RESULT_SPEC:
                    areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, sampleAPIParams.MANDATORY_PARAMS_FRONTEND_GET_SAMPLE_ANALYSIS_RESULT_SPEC.split("\\|"));
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        LPFrontEnd.servletReturnResponseError(request, response, 
                                LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                        return;                  
                    }                      
                    String resultIdStr = request.getParameter(globalAPIsParams.REQUEST_PARAM_RESULT_ID);
                    // No implementado aun
                    Rdbms.closeRdbms();
                    return;  
                case API_ENDPOINT_SAMPLE_ENTIRE_STRUCTURE:
                   sampleIdStr = request.getParameter(globalAPIsParams.REQUEST_PARAM_SAMPLE_ID);        
                   sampleId = Integer.parseInt(sampleIdStr);    
                   //schemaDataName
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
                                    +"from \"process-us-data\".sample s where s.sample_id = 13649 ) sQry   ";
                
                    CachedRowSetImpl prepRdQuery = Rdbms.prepRdQuery(qry, new Object[]{sampleId});


                    boolean first = prepRdQuery.first();
                    String finalString = "";
                    if (prepRdQuery.getString(1)==null){
                        LPFrontEnd.servletReturnResponseError(request, response, "NOTHING_TO_RETURN", new Object[0], language);
                    }
                    String jsonarrayf = prepRdQuery.getString(1);
                    LPFrontEnd.servletReturnSuccess(request, response, jsonarrayf);
                    return;
                default:      
                    LPFrontEnd.servletReturnResponseError(request, response, LPPlatform.API_ERRORTRAPING_PROPERTY_ENDPOINT_NOT_FOUND, new Object[]{actionName, this.getServletName()}, language);                              
                }                     
        }catch(Exception e){      
            String exceptionMessage =e.getMessage();
            if (exceptionMessage==null){exceptionMessage="null exception";}
            response.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);     
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