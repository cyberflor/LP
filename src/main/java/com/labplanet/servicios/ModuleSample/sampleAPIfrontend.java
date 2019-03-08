/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleSample;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETFrontEnd;
import LabPLANET.utilities.LabPLANETPlatform;
import com.sun.rowset.CachedRowSetImpl;
import databases.Rdbms;
import databases.SqlStatement;
import databases.Token;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
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

        ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");
        String frontendUrl = prop.getString("frontend_url");

        response.setHeader("Access-Control-Allow-Origin", frontendUrl);
        response.setHeader("Access-Control-Allow-Methods", "GET");        

        String language = "en";


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};            
            
            String finalToken = request.getParameter("finalToken");                      

            if (finalToken==null) {
                errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: finalToken is one mandatory param for this API");                    
                Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;
            }                    
            Token token = new Token();
            String[] tokenParams = token.tokenParamsList();
            String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

            String dbUserName = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "userDB")];
            String dbUserPassword = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "userDBPassword")];         
            String internalUserID = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "internalUserID")];         
            String userRole = tokenParamsValues[LabPLANETArray.valuePosicInArray(tokenParams, "userRole")];         
                        
            if (Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword)==null){
                Rdbms.closeRdbms();                
                errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;               
            }            
        
            String actionName = request.getParameter("actionName");
            if (actionName==null) {
                Rdbms.closeRdbms();                
                errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: actionName is one mandatory param for this API");                    
                Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;
            }           
            String schemaPrefix = request.getParameter("schemaPrefix");
            if (schemaPrefix==null) {
                Rdbms.closeRdbms();                
                errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: schemaPrefix is one mandatory param for this API");                    
                Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;
            }           
            
            switch (actionName.toUpperCase()){
            case "GET_SAMPLETEMPLATES":       
                Object[][] Datas = Rdbms.getRecordFieldsByFilter(schemaPrefix+"-config", "sample", 
                        new String[] {"code"}, new Object[]{"specSamples"}, new String[] { "json_definition"});
                Rdbms.closeRdbms();
                JSONObject proceduresList = new JSONObject();
                JSONArray jArray = new JSONArray();
                if ("LABPLANET_FALSE".equalsIgnoreCase(Datas[0][0].toString())){  
                    Object[] errMsg = LabPLANETFrontEnd.responseError(LabPLANETArray.array2dTo1d(Datas), language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    return;
                }else{
                   Response.ok().build();                   
                   jArray.addAll(Arrays.asList(LabPLANETArray.array2dTo1d(Datas)));                                 
                }           
                response.getWriter().write(jArray.toString());                  
                return;
            case "UNRECEIVESAMPLES_LIST":            
                String[] sortFieldsNameArr = null;
                String sortFieldsName = request.getParameter("sortFieldsName"); 
                String sampleFieldToRetrieve = request.getParameter("sampleFieldToRetrieve"); 
                
                if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                    sortFieldsNameArr = (String[]) sortFieldsName.split("\\|");                                    
                }else{   sortFieldsNameArr=null;}             
                String[] sampleFieldToRetrieveArr = new String[]{"sample_id"};
                if (sampleFieldToRetrieve!=null){
                    sampleFieldToRetrieveArr=LabPLANETArray.addValueToArray1D(sampleFieldToRetrieveArr, sampleFieldToRetrieve.split("\\|"));
                }                
                String myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-data", "sample",
                        new String[] {"received_by is null"}, new Object[]{""},
                        sampleFieldToRetrieveArr, sortFieldsNameArr);
                Rdbms.closeRdbms();
                if (myData.contains("LABPLANET_FALSE")){  
                    Object[] errMsg = LabPLANETFrontEnd.responseError(new String[]{myData}, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                }else{
                    Response.ok().build();
                    response.getWriter().write(myData);           
                }
                Rdbms.closeRdbms();
                return;
            case "SAMPLES_INPROGRESS_LIST":   
                String whereFieldsName = request.getParameter("whereFieldsName"); 
                if (whereFieldsName==null){whereFieldsName="";}
                String whereFieldsValue = request.getParameter("whereFieldsValue"); 

                sampleFieldToRetrieve = request.getParameter("sampleFieldToRetrieve"); 
                String testFieldToRetrieve = request.getParameter("testFieldToRetrieve"); 
                String sampleLastLevel = request.getParameter("sampleLastLevel");                 
                
                String[] whereFieldsNameArr = null;
                Object[] whereFieldsValueArr = null;
                
                if (sampleLastLevel==null){
                    sampleLastLevel="SAMPLE";
                }                                
                sampleFieldToRetrieveArr = new String[]{"sample_id"};
                if (sampleFieldToRetrieve!=null){
                    sampleFieldToRetrieveArr=LabPLANETArray.addValueToArray1D(sampleFieldToRetrieveArr, sampleFieldToRetrieve.split("\\|"));
                }
                String[] testFieldToRetrieveArr = new String[]{"test_id"};
                if (testFieldToRetrieve!=null){
                    testFieldToRetrieveArr=LabPLANETArray.addValueToArray1D(testFieldToRetrieveArr, testFieldToRetrieve.split("\\|"));
                }                
                
                if (!whereFieldsName.contains("status")){
                    Object[] recEncrypted = LabPLANETPlatform.encryptString("RECEIVED");
                    whereFieldsNameArr=LabPLANETArray.addValueToArray1D(whereFieldsNameArr, "status in|");                
                    whereFieldsValueArr=LabPLANETArray.addValueToArray1D(whereFieldsValueArr, "RECEIVED|"+recEncrypted[1]);                
                }
                if ( (whereFieldsName!=null) && (whereFieldsValue!=null) ){
                    whereFieldsNameArr=LabPLANETArray.addValueToArray1D(whereFieldsNameArr, whereFieldsName.split("\\|"));
                    whereFieldsValueArr = LabPLANETArray.addValueToArray1D(whereFieldsValueArr, LabPLANETArray.convertStringWithDataTypeToObjectArray(whereFieldsValue.split("\\|")));                                          
                    for (int iFields=0; iFields<whereFieldsNameArr.length; iFields++){
                        if (LabPLANETPlatform.isEncryptedField(schemaPrefix+"-data", "sample", whereFieldsNameArr[iFields])){                
                            HashMap<String, String> hm = LabPLANETPlatform.encryptEncryptableFieldsAddBoth(whereFieldsNameArr[iFields], whereFieldsNameArr[iFields]);
                            whereFieldsNameArr[iFields]= hm.keySet().iterator().next();    
                            SqlStatement sql = new SqlStatement();
                            String separator = sql.inSeparator(whereFieldsNameArr[iFields]);
                            if ( hm.get(whereFieldsNameArr[iFields]).length()!=whereFieldsNameArr[iFields].length()){
                                String newWhereFieldValues = hm.get(whereFieldsNameArr[iFields]);
                                whereFieldsValueArr[iFields]=newWhereFieldValues;
                            }
                        }
                    }                                    
                }            
/*                Object[] whereFieldsValueArr = new Object[]{"RECEIVED"};
                LabPLANETPlatform LabPLANETPlatform = new LabPLANETPlatform();
                
                Object[] wordDecripted = LabPLANETPlatform.decryptString("fS￵ￃﾺﾀ?p￦￠￝ﾅ\\ﾭﾡ￡");
                Object[] recEncrypted = LabPLANETPlatform.encryptString("RECEIVED");
                whereFieldsValueArr=LabPLANETArray.addValueToArray1D(whereFieldsValueArr, recEncrypted[1]);
                recEncrypted = LabPLANETPlatform.encryptString("LOGGED");
                whereFieldsValueArr=LabPLANETArray.addValueToArray1D(whereFieldsValueArr, recEncrypted[1]);
*/                
                
/*                LabPLANETPlatform LabPLANETPlatform = new LabPLANETPlatform();
                Object[] recEncrypted = LabPLANETPlatform.encryptString("LOGGED");
                Object[] whereFieldsValueArr = new Object[]{recEncrypted[1]};
                if (whereFieldsValue!=null){
                    whereFieldsValueArr=LabPLANETArray.addValueToArray1D(whereFieldsValueArr, whereFieldsValue.split("\\|"));
                }                 
*/                
 /*               whereFieldsNameArr = LabPLANETArray.addValueToArray1D(whereFieldsNameArr, "status");
                whereFieldsNameArr = LabPLANETArray.addValueToArray1D(whereFieldsNameArr, "RECEIVED");
                
                String[] whereFieldsNameArr=whereFieldsName.split("\\|");                
                Object[] whereFieldsValueArr = LabPLANETArray.convertStringWithDataTypeToObjectArray(whereFieldsValue.split("\\|"));
  */              
                sortFieldsNameArr = null;
                sortFieldsName = request.getParameter("sortFieldsName"); 
                if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                    sortFieldsNameArr = (String[]) sortFieldsName.split("\\|");                                    
                }else{   sortFieldsNameArr=null;}  
                
                if ("SAMPLE".equals(sampleLastLevel)){
                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-data", "sample",
                            whereFieldsNameArr, whereFieldsValueArr, sampleFieldToRetrieveArr, sortFieldsNameArr);
                    if (myData==null){
                        Rdbms.closeRdbms(); 
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No info found or error running the query for this sample "); 
                        return;
                    }
                    if ( myData.contains("LABPLANET_FALSE")) {  
                        Object[] errMsg = LabPLANETFrontEnd.responseError(new String[]{myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }
                }else{                    
                    whereFieldsNameArr = LabPLANETArray.addValueToArray1D(whereFieldsNameArr, "sample_id");
                    whereFieldsValueArr = LabPLANETArray.addValueToArray1D(whereFieldsValueArr, 2);
                    JSONArray samplesArray = new JSONArray();    
                    JSONArray sampleArray = new JSONArray();    
                    Object[][] mySamples = Rdbms.getRecordFieldsByFilter(schemaPrefix+"-data", "sample",
                            whereFieldsNameArr, whereFieldsValueArr, sampleFieldToRetrieveArr);
                    if ( "LABPLANET_FALSE".equalsIgnoreCase(mySamples[0][0].toString()) ){
                        Rdbms.closeRdbms(); 
                        Object[] errMsg = LabPLANETFrontEnd.responseError(LabPLANETArray.array2dTo1d(mySamples), language, null);
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
                                Integer testId = Integer.valueOf(mySampleAnalysi[0].toString());
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
                    JSONObject JsonObj = new JSONObject();
                    JsonObj.put("samples", samplesArray);                    
                    Response.ok().build();
                    response.getWriter().write(JsonObj.toString());               
                }
                    //Response.serverError().entity(myData).build();
                    //return Response.ok(myData).build();     
                    Rdbms.closeRdbms(); 
                    return;                                        
                case "ANALYSIS_ALL_LIST":          
                    String fieldToRetrieve = request.getParameter("fieldToRetrieve"); 

                    String[] fieldToRetrieveArr = new String[0];
                    if ( (fieldToRetrieve==null) || (fieldToRetrieve.length()==0) ){
                        fieldToRetrieveArr=LabPLANETArray.addValueToArray1D(fieldToRetrieveArr, "code");
                        fieldToRetrieveArr=LabPLANETArray.addValueToArray1D(fieldToRetrieveArr, "method_name");                           
                        fieldToRetrieveArr=LabPLANETArray.addValueToArray1D(fieldToRetrieveArr, "method_version");                           
                    }else{
                        fieldToRetrieveArr=LabPLANETArray.addValueToArray1D(fieldToRetrieveArr, fieldToRetrieve.split("\\|"));
                        if (LabPLANETArray.valuePosicInArray(fieldToRetrieveArr, "code")==-1){
                            fieldToRetrieveArr=LabPLANETArray.addValueToArray1D(fieldToRetrieveArr, "code");     
                            fieldToRetrieveArr=LabPLANETArray.addValueToArray1D(fieldToRetrieveArr, "method_name");                           
                            fieldToRetrieveArr=LabPLANETArray.addValueToArray1D(fieldToRetrieveArr, "method_version");                           
                        }                    
                    }                
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter("sortFieldsName"); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = (String[]) sortFieldsName.split("\\|");                                    
                    }else{   sortFieldsNameArr=null;}  

                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-config", "analysis_methods_view",
                            new String[]{"code is not null"},new Object[]{true}, fieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains("LABPLANET_FALSE")){  
                        Object[] errMsg = LabPLANETFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }
                    Rdbms.closeRdbms();
                    return;         
                case "GET_SAMPLE_ANALYSIS_LIST":
                    String sampleIdStr = request.getParameter("sampleId");                             
                    if ( (sampleIdStr==null) || (sampleIdStr.contains("undefined")) ) {
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "sampleId="+request.getParameter("sampleId"));
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);   
                        Rdbms.closeRdbms(); 
                        return ;
                    }                              
                    Integer sampleId = Integer.parseInt(sampleIdStr);       
                    
                    String sampleAnalysisFieldToRetrieve = request.getParameter("sampleAnalysisFieldToRetrieve");  
                    String[] sampleAnalysisFieldToRetrieveArr=  sampleAnalysisFieldToRetrieve.split("\\|");
                    
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter("sortFieldsName"); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = (String[]) sortFieldsName.split("\\|");                                    
                    }else{   
                        sortFieldsNameArr = LabPLANETArray.addValueToArray1D(sortFieldsNameArr, "sample_id");
                        sortFieldsNameArr = LabPLANETArray.addValueToArray1D(sortFieldsNameArr, "test_id");                        
                    }  
                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-data", "sample_analysis",
                            new String[]{"sample_id"},new Object[]{sampleId}, sampleAnalysisFieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains("LABPLANET_FALSE")){  
                        Object[] errMsg = LabPLANETFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }
                    
                    Rdbms.closeRdbms();
                    return;                      
                case "GET_SAMPLE_ANALYSIS_RESULT_LIST":
                    sampleIdStr = request.getParameter("sampleId");                             
                    if ( (sampleIdStr==null) || (sampleIdStr.contains("undefined")) ) {
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "sampleId="+request.getParameter("sampleId"));
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);   
                        Rdbms.closeRdbms(); 
                        return ;
                    }                              
                    sampleId = Integer.parseInt(sampleIdStr);                           
                    String resultFieldToRetrieve = request.getParameter("resultFieldToRetrieve");  
                    String[] resultFieldToRetrieveArr=  resultFieldToRetrieve.split("\\|");
                    String fieldName = "result_id";
                    if (LabPLANETArray.valuePosicInArray(resultFieldToRetrieveArr, fieldName)==-1){
                        resultFieldToRetrieveArr=LabPLANETArray.addValueToArray1D(resultFieldToRetrieveArr, fieldName);}     
                    fieldName = "param_name";
                    if (LabPLANETArray.valuePosicInArray(resultFieldToRetrieveArr, fieldName)==-1){
                        resultFieldToRetrieveArr=LabPLANETArray.addValueToArray1D(resultFieldToRetrieveArr, fieldName);}     
                    
                    sortFieldsNameArr = null;
                    sortFieldsName = request.getParameter("sortFieldsName"); 
                    if (! ((sortFieldsName==null) || (sortFieldsName.contains("undefined"))) ) {
                        sortFieldsNameArr = (String[]) sortFieldsName.split("\\|");                                    
                    }else{   
                        sortFieldsNameArr = LabPLANETArray.addValueToArray1D(sortFieldsNameArr, "sample_id");
                        sortFieldsNameArr = LabPLANETArray.addValueToArray1D(sortFieldsNameArr, "test_id");
                        sortFieldsNameArr = LabPLANETArray.addValueToArray1D(sortFieldsNameArr, "result_id");
                    }  
                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-data", "sample_analysis_result",
                            new String[]{"sample_id"},new Object[]{sampleId}, resultFieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains("LABPLANET_FALSE")){  
                        Object[] errMsg = LabPLANETFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }                    
                    Rdbms.closeRdbms();
                    return;  
                case "GET_SAMPLE_ANALYSIS_RESULT_SPECK":
                    String resultIdStr = request.getParameter("result_id");
                    if ( (resultIdStr==null) || (resultIdStr.contains("undefined")) ) {
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "sampleId="+request.getParameter("sampleId"));
                        errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);   
                        Rdbms.closeRdbms(); 
                        return ;
                    }
/*                    myData = Rdbms.getRecordFieldsByFilterJSON(schemaPrefix+"-data", "sample_analysis_result",
                            new String[]{"sample_id"},new Object[]{resultIdStr}, resultFieldToRetrieveArr, sortFieldsNameArr);
                    Rdbms.closeRdbms();
                    if (myData.contains("LABPLANET_FALSE")){  
                        Object[] errMsg = LabPLANETFrontEnd.responseError(new String[] {myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }
  */                  
                    Rdbms.closeRdbms();
                    return;  
                case "SAMPLE_ENTIRE_STRUCTURE":
                   sampleIdStr = request.getParameter("sampleId");        
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
                    errObject = LabPLANETArray.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                    errObject = LabPLANETArray.addValueToArray1D(errObject, "API Error Message: actionName "+actionName+ " not recognized as an action by this API");                                                            
                    Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, schemaPrefix);
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
