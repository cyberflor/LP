/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleSample;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETFrontEnd;
import databases.Rdbms;
import databases.Token;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Arrays;
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

        String language = "en";

        LabPLANETArray labArr = new LabPLANETArray();
        LabPLANETFrontEnd labFrEnd = new LabPLANETFrontEnd();

        Rdbms rdbm = new Rdbms();                

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};            
            
            String finalToken = request.getParameter("finalToken");                      

            if (finalToken==null) {
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: finalToken is one mandatory param for this API");                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;
            }                    
            Token token = new Token();
            String[] tokenParams = token.tokenParamsList();
            String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

            String dbUserName = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDB")];
            String dbUserPassword = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDBPassword")];         
            String internalUserID = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "internalUserID")];         
            String userRole = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userRole")];         
                        
            boolean isConnected = false;
            isConnected = rdbm.startRdbms(dbUserName, dbUserPassword);
            if (!isConnected){
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;               
            }            
        
            String actionName = request.getParameter("actionName");
            if (actionName==null) {
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: actionName is one mandatory param for this API");                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;
            }           
            String schemaPrefix = request.getParameter("schemaPrefix");
            if (schemaPrefix==null) {
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: schemaPrefix is one mandatory param for this API");                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                return ;
            }           
            
            switch (actionName.toUpperCase()){
            case "GET_SAMPLETEMPLATES":       
                Object[][] Datas = rdbm.getRecordFieldsByFilter(rdbm, schemaPrefix+"-config", "sample", 
                        new String[] {"code"}, new Object[]{"sampleTemplate"}, new String[] { "json_definition"});
                rdbm.closeRdbms();
                JSONObject proceduresList = new JSONObject();
                JSONArray jArray = new JSONArray();
                if ("LABPLANET_FALSE".equalsIgnoreCase(Datas[0][0].toString())){  
                    Object[] errMsg = labFrEnd.responseError(labArr.array2dTo1d(Datas), language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    return;
                }else{
                   Response.ok().build();                   
                   for (Object fv: labArr.array2dTo1d(Datas)){
                       jArray.add(fv);
                   }                                 
                }           
                response.getWriter().write(jArray.toString());                  
                return;
            case "UNRECEIVESAMPLES_LIST":            
                String myData = rdbm.getRecordFieldsByFilterJSON(rdbm, schemaPrefix+"-data", "sample",
                        new String[] {"received_by is null"}, new Object[]{""},
                        new String[] { "sample_id", "sample_config_code", "sampling_comment"});
                rdbm.closeRdbms();
                if (myData.contains("LABPLANET_FALSE")){  
                    Object[] errMsg = labFrEnd.responseError(new String[]{myData}, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                }else{
                    Response.ok().build();
                    response.getWriter().write(myData);           
                }
                rdbm.closeRdbms();
                return;
            case "SAMPLES_INPROGRESS_LIST":   
                String whereFieldsName = request.getParameter("whereFieldsName"); 
                String whereFieldsValue = request.getParameter("whereFieldsValue"); 
                String sampleFieldToRetrieve = request.getParameter("sampleFieldToRetrieve"); 
                String testFieldToRetrieve = request.getParameter("testFieldToRetrieve"); 
                String sampleLastLevel = request.getParameter("sampleLastLevel");                 
                if (sampleLastLevel==null){
                    sampleLastLevel="SAMPLE";
                }                                
                String[] sampleFieldToRetrieveArr = new String[]{"sample_id"};
                if (sampleFieldToRetrieve!=null){
                    sampleFieldToRetrieveArr=labArr.addValueToArray1D(sampleFieldToRetrieveArr, sampleFieldToRetrieve.split("\\|"));
                }
                String[] testFieldToRetrieveArr = new String[]{"test_id"};
                if (testFieldToRetrieve!=null){
                    testFieldToRetrieveArr=labArr.addValueToArray1D(testFieldToRetrieveArr, testFieldToRetrieve.split("\\|"));
                }                                
                String[] whereFieldsNameArr = new String[]{"status"};
                if (whereFieldsName!=null){
                    whereFieldsNameArr=labArr.addValueToArray1D(whereFieldsNameArr, whereFieldsName.split("\\|"));
                }            
                Object[] whereFieldsValueArr = new Object[]{"RECEIVED"};
                if (whereFieldsValue!=null){
                    whereFieldsValueArr=labArr.addValueToArray1D(whereFieldsValueArr, whereFieldsValue.split("\\|"));
                }                 
 /*               whereFieldsNameArr = labArr.addValueToArray1D(whereFieldsNameArr, "status");
                whereFieldsNameArr = labArr.addValueToArray1D(whereFieldsNameArr, "RECEIVED");
                
                String[] whereFieldsNameArr=whereFieldsName.split("\\|");                
                Object[] whereFieldsValueArr = labArr.convertStringWithDataTypeToObjectArray(whereFieldsValue.split("\\|"));
  */              
                if ("SAMPLE".equals(sampleLastLevel)){
                    myData = rdbm.getRecordFieldsByFilterJSON(rdbm, schemaPrefix+"-data", "sample",
                            whereFieldsNameArr, whereFieldsValueArr, sampleFieldToRetrieveArr);
                    if (myData.contains("LABPLANET_FALSE")){  
                        Object[] errMsg = labFrEnd.responseError(new String[]{myData}, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                    }else{
                        Response.ok().build();
                        response.getWriter().write(myData);           
                    }
                }else{                    
                    whereFieldsNameArr = labArr.addValueToArray1D(whereFieldsNameArr, "sample_id");
                    whereFieldsValueArr = labArr.addValueToArray1D(whereFieldsValueArr, 2);
                    JSONArray samplesArray = new JSONArray();    
                    JSONArray sampleArray = new JSONArray();    
                    Object[][] mySamples = rdbm.getRecordFieldsByFilter(rdbm, schemaPrefix+"-data", "sample",
                            whereFieldsNameArr, whereFieldsValueArr, sampleFieldToRetrieveArr);
                    if ( "LABPLANET_FALSE".equalsIgnoreCase(mySamples[0][0].toString()) ){
                        Object[] errMsg = labFrEnd.responseError(labArr.array2dTo1d(mySamples), language, null);
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
                            Object[][] mySampleAnalysis = rdbm.getRecordFieldsByFilter(rdbm, schemaPrefix+"-data", "sample_analysis",
                                    testWhereFieldsNameArr, testWhereFieldsValueArr, testFieldToRetrieveArr);          
                            for (int xSmpAna=0; xSmpAna<mySampleAnalysis.length; xSmpAna++){
                                JSONObject testObj = new JSONObject();
                                Integer testId = Integer.valueOf(mySampleAnalysis[xSmpAna][0].toString());
                                for (int ySmpAna=0; ySmpAna<mySampleAnalysis[0].length; ySmpAna++){         
                                    if (mySampleAnalysis[xSmpAna][ySmpAna] instanceof Timestamp){
                                        testObj.put(testFieldToRetrieveArr[ySmpAna], mySampleAnalysis[xSmpAna][ySmpAna].toString());
                                    }else{                                    
                                        testObj.put(testFieldToRetrieveArr[ySmpAna], mySampleAnalysis[xSmpAna][ySmpAna]);
                                    }
                                }      
                                sampleArray.add(testObj);
                            }
                            sampleObj.put("sample_analysis", sampleArray);
                        }
                        sampleArray.add(sampleObj);                        
                    }
                    rdbm.closeRdbms();
                    samplesArray.add(sampleArray);
                    JSONObject JsonObj = new JSONObject();
                    JsonObj.put("samples", samplesArray);                    
                    Response.ok().build();
                    response.getWriter().write(JsonObj.toString());               
                }
                    //Response.serverError().entity(myData).build();
                    //return Response.ok(myData).build();            
                    return;          
                case "ANALYSIS_ALL_LIST":          
                String fieldToRetrieve = request.getParameter("fieldToRetrieve"); 
                    
                String[] fieldToRetrieveArr = new String[0];
                if ( (fieldToRetrieve==null) || (fieldToRetrieve.length()==0) ){
                    fieldToRetrieveArr=labArr.addValueToArray1D(fieldToRetrieveArr, "code");
                    fieldToRetrieveArr=labArr.addValueToArray1D(fieldToRetrieveArr, "method_name");                           
                    fieldToRetrieveArr=labArr.addValueToArray1D(fieldToRetrieveArr, "method_version");                           
                }else{
                    fieldToRetrieveArr=labArr.addValueToArray1D(fieldToRetrieveArr, fieldToRetrieve.split("\\|"));
                    if (labArr.valuePosicInArray(fieldToRetrieveArr, "code")==-1){
                        fieldToRetrieveArr=labArr.addValueToArray1D(fieldToRetrieveArr, "code");     
                        fieldToRetrieveArr=labArr.addValueToArray1D(fieldToRetrieveArr, "method_name");                           
                        fieldToRetrieveArr=labArr.addValueToArray1D(fieldToRetrieveArr, "method_version");                           
                    }                    
                }                

                myData = rdbm.getRecordFieldsByFilterJSON(rdbm, schemaPrefix+"-config", "analysis_methods_view",
                        new String[]{"code is not null"},new Object[]{true}, fieldToRetrieveArr);
                rdbm.closeRdbms();
                if (myData.contains("LABPLANET_FALSE")){  
                    Object[] errMsg = labFrEnd.responseError(new String[] {myData}, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                }else{
                    Response.ok().build();
                    response.getWriter().write(myData);           
                }
                rdbm.closeRdbms();
                return;                          
            default:      
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: actionName "+actionName+ " not recognized as an action by this API");                                                            
                Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                rdbm.closeRdbms();                    
                return;
            }               
/*            rdbm.closeRdbms();
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
            rdbm.closeRdbms();                   
            String[] errObject = new String[]{e.getMessage()};
            Object[] errMsg = labFrEnd.responseError(errObject, language, null);
            response.sendError((int) errMsg[0], (String) errMsg[1]);           
            return;                  
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
