/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleSample;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETFrontEnd;
import LabPLANET.utilities.LabPLANETPlatform;
import com.labplanet.dao.ProductoDAO;
import com.labplanet.modelo.Producto;
import databases.Rdbms;
import databases.Token;
import functionalJava.sampleStructure.DataSample;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

        String language = "en";

        LabPLANETArray labArr = new LabPLANETArray();
        LabPLANETFrontEnd labFrEnd = new LabPLANETFrontEnd();
        
        Rdbms rdbm = new Rdbms();            
        //ResponseEntity<String> responsew;
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};            
                        
            String functionBeingTested = request.getParameter("functionBeingTested");
                                
            String schemaPrefix = request.getParameter("schemaPrefix");
            String dbUserName = request.getParameter("dbUserName");
            String dbUserPassword = request.getParameter("dbUserPassword");
            String userName = request.getParameter("userName");
            String userRole = request.getParameter("userRole");

            if (schemaPrefix==null) {
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: schemaPrefix is one mandatory param for this API");                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);     
                return ;
            }        

            if (functionBeingTested==null) {
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: functionBeingTested is one mandatory param for this API");                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);     
                return ;
            }
                
            String finalToken = request.getParameter("finalToken");                   
            //String userRole = request.getParameter("userRole");                      

            if (finalToken==null) {
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: finalToken and userRole are mandatory params for this API");                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);     
                return ;
            }                    

            Token token = new Token();
            String[] tokenParams = token.tokenParamsList();
            String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);
                    
            dbUserName = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDB")];
            dbUserPassword = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDBPassword")];
            String internalUserID = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "internalUserID")];         
            userRole = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userRole")];                     
                                
            boolean isConnected = false;
            isConnected = rdbm.startRdbms(dbUserName, dbUserPassword);
            if (!isConnected){
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);                            
                return ;               
            }
            
            DataSample smp = new DataSample("");            
            Object[] dataSample = null;
            
            switch (functionBeingTested.toUpperCase()){
                case "LOGSAMPLE":
                    String sampleTemplate=request.getParameter("sampleTemplate");
                    if (sampleTemplate==null) {
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: sampleTemplate is one mandatory param for this API");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);     
                        return ;
                    }                 
                    String sampleTemplateVersionStr = request.getParameter("sampleTemplateVersion");                                  
                    if (sampleTemplateVersionStr==null) {
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "sampleTemplateVersion="+request.getParameter("sampleTemplateVersion"));
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: sampleTemplateVersion is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);     
                        return ;
                    }            
                    Integer sampleTemplateVersion = Integer.parseInt(sampleTemplateVersionStr);                  
                    String fieldName=request.getParameter("fieldName");                                        
                    String fieldValue=request.getParameter("fieldValue");
                    String[] fieldNames=null;
                    Object[] fieldValues=null;
                    if (fieldName!=null) fieldNames = (String[]) fieldName.split("\\|");                                            
                    if (fieldValue!=null) fieldValues = (Object[]) labArr.convertStringWithDataTypeToObjectArray(fieldValue.split("\\|"));                                                            
                    rdbm.closeRdbms();
                    if ("LABPLANET_FALSE".equalsIgnoreCase(dataSample[0].toString())){  
                        Object[] errMsg = labFrEnd.responseError(dataSample, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    }else{
                        Response.ok().build();
                        response.getWriter().write(Arrays.toString(dataSample));      
                    }  
                    return;
                case "RECEIVESAMPLE":                      
                    String sampleIdStr = request.getParameter("sampleId");                             
                    if (sampleIdStr==null) {
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "sampleId="+request.getParameter("sampleId"));
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);     
                        return ;
                    }                
                    Integer sampleId = Integer.parseInt(sampleIdStr);      
sampleId = 12312131;

                    dataSample = smp.sampleReception(rdbm, schemaPrefix, internalUserID, sampleId, userRole);
                    rdbm.closeRdbms();
                    if ("LABPLANET_FALSE".equalsIgnoreCase(dataSample[0].toString())){  
                        Object[] errMsg = labFrEnd.responseError(dataSample, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    }else{
                        Response.ok().build();
                        response.getWriter().write(Arrays.toString(dataSample));      
                    }            
                    rdbm.closeRdbms();
                    return;                         
                case "CHANGESAMPLINGDATE":
                    sampleIdStr = request.getParameter("sampleId");                             
                    if ( (sampleIdStr==null) || (sampleIdStr.contains("undefined")) ) {
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "sampleId="+request.getParameter("sampleId"));
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);     
                        return ;
                    }                
                    sampleId = Integer.parseInt(sampleIdStr);      
                    Date newDate=Date.valueOf(request.getParameter("newDate"));
                    if (newDate==null) {
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "sampleTemplateVersion="+request.getParameter("sampleTemplateVersion"));
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: newDate is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                             
                        return ;
                    }                     
                    dataSample = smp.changeSamplingDate(rdbm, schemaPrefix, internalUserID, sampleId, newDate, userRole);
                    break;       
                case "SAMPLINGCOMMENTADD":
                    sampleIdStr = request.getParameter("sampleId");                             
                    if ( (sampleIdStr==null) || (sampleIdStr.contains("undefined")) ) {
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "sampleId="+request.getParameter("sampleId"));
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);     
                        return;
                    }                
                    sampleId = Integer.parseInt(sampleIdStr);      
                    String comment=null;                    
                    comment = request.getParameter("sampleComment"); 
                    dataSample = smp.sampleReceptionCommentAdd(rdbm, schemaPrefix, internalUserID, sampleId, comment, userRole);
                    break;       
                case "SAMPLINGCOMMENTREMOVE":
                    sampleIdStr = request.getParameter("sampleId");                             
                    if ( (sampleIdStr==null) || (sampleIdStr.contains("undefined")) ) {
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "sampleId="+request.getParameter("sampleId"));
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);     
                        return ;
                    }                
                    sampleId = Integer.parseInt(sampleIdStr);      
                    comment=null;        
                    comment = request.getParameter("sampleComment"); 
                    dataSample = smp.sampleReceptionCommentRemove(rdbm, schemaPrefix, internalUserID, sampleId, comment, userRole);
                    break;       
                case "INCUBATIONSTART":
                    sampleIdStr = request.getParameter("sampleId");                             
                    if ( (sampleIdStr==null) || (sampleIdStr.contains("undefined")) ) {
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "sampleId="+request.getParameter("sampleId"));
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);     
                        return ;
                    }                
                    sampleId = Integer.parseInt(sampleIdStr);      
                    dataSample = smp.setSampleStartIncubationDateTime(rdbm, schemaPrefix, userName, sampleId, userRole);
                    break;       
                case "INCUBATIONEND":
                    sampleIdStr = request.getParameter("sampleId");                             
                    if ( (sampleIdStr==null) || (sampleIdStr.contains("undefined")) ) {
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "sampleId="+request.getParameter("sampleId"));
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: sampleId is one mandatory param and should be one integer value for this API");                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);     
                        return ;
                    }                
                    sampleId = Integer.parseInt(sampleIdStr);      
                    dataSample = smp.setSampleEndIncubationDateTime(rdbm, schemaPrefix, userName, sampleId, userRole);
                    break;       
 /*               case "SAMPLEANALYSISADD":
                    if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){fieldName = (String[]) configSpecTestingArray[i][5].toString().split("\\|");}              
                    if (configSpecTestingArray[i][6]!=null){fieldValue = (Object[]) configSpecTestingArray[i][6].toString().split("\\|");}   
                    fieldValue = labArr.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                    try {                        
                        fieldValue = labArr.convertStringWithDataTypeToObjectArray(configSpecTestingArray[i][6].toString().split("\\|"));
                        fileContent = fileContent + "<td>sampleId, userName, fieldNames, fieldValues</td>";
                        fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName.toString()+", "
                            +configSpecTestingArray[i][5].toString()+", "+configSpecTestingArray[i][6].toString()+"</td>";                            
                        dataSample = smp.sampleAnalysisAddtoSample(rdbm, schemaPrefix, userName, sampleId, fieldName, fieldValue, userRole);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;              
                case "ENTERRESULT":
                    Integer resultId = 0;
                    String rawValueResult = "";
                    if (configSpecTestingArray[i][3]!=null){resultId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){rawValueResult = (String) configSpecTestingArray[i][5];}   
                    fileContent = fileContent + "<td>resultId, userName, rawValueResult</td>";
                    fileContent = fileContent + "<td>"+resultId.toString()+", "+userName+", "+rawValueResult+"</td>";
                    try {
                        dataSample = smp.sampleAnalysisResultEntry(rdbm, schemaPrefix, userName, resultId, rawValueResult, userRole);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;              
                case "CANCELRESULT":
                    Integer objectId = 0;
                    String objectLevel = "";
                    rawValueResult = "";
                    if (configSpecTestingArray[i][3]!=null){objectId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){objectLevel = (String) configSpecTestingArray[i][5];}   
                    fileContent = fileContent + "<td>resultId, userName, objectLevel</td>";
                    fileContent = fileContent + "<td>"+objectId.toString()+", "+userName+", "+objectLevel+"</td>";
                    try {
                        sampleId = null; Integer testId = null; resultId = null;

                        if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultCancel(rdbm, schemaPrefix, userName, sampleId, testId, resultId, userRole);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;                            
                case "UNCANCELRESULT": 
                    objectId = 0;
                    objectLevel = "";
                    rawValueResult = "";
                    if (configSpecTestingArray[i][3]!=null){objectId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){objectLevel = (String) configSpecTestingArray[i][5];}                      
                    fileContent = fileContent + "<td>resultId, userName, objectLevel</td>";
                    fileContent = fileContent + "<td>"+objectId.toString()+", "+userName+", "+objectLevel+"</td>";
                    try {
                        sampleId = null; Integer testId = null; resultId = null;

                        if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                        if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                        if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                        dataSample = smp.sampleAnalysisResultUnCancel(rdbm, schemaPrefix, userName, sampleId, testId, resultId, userRole);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;       
                case "TESTASSIGNMENT": 
                    Integer testId = 0;
                    String newAnalyst = "";
                    if (configSpecTestingArray[i][3]!=null){testId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                    if (configSpecTestingArray[i][5]!=null){newAnalyst = (String) configSpecTestingArray[i][5];}                      
                    fileContent = fileContent + "<td>testId, userName, newAnalyst</td>";
                    fileContent = fileContent + "<td>"+testId.toString()+", "+userName+", "+newAnalyst+"</td>";
                    try {
                        dataSample = smp.sampleAnalysisAssignAnalyst(rdbm, schemaPrefix, userName, testId, newAnalyst, userRole);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;   
                    
                case "GETSAMPLEINFO":
                    String schemaDataName = "data";
                    LabPLANETPlatform labPlat = new LabPLANETPlatform();
                    schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);                     
                    if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                    String[] fieldsToGet = (configSpecTestingArray[i][4].toString().split("\\|"));                    
                         fileContent = fileContent + "<td>"
                                 +configSpecTestingArray[i][4].toString()+"</td><td>"
                                 +sampleId.toString()+"</td>";                      
                         dataSample2D = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample", new String[]{"sample_id"}, new Object[]{sampleId}, fieldsToGet);
                    break;
*/                    
                default:      
                    //errObject = frontEnd.APIHandler.actionNotRecognized(errObject, functionBeingTested, response);
                    errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                    errObject = labArr.addValueToArray1D(errObject, "API Error Message: actionName "+functionBeingTested+ " not recognized as an action by this API");                                                            
                    Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    rdbm.closeRdbms();                    
                    return;                    
            }    
            rdbm.closeRdbms();
            if ("LABPLANET_TRUE".equalsIgnoreCase(dataSample[0].toString())){                                
                //out.println(Arrays.toString(dataSample));
                response.getWriter().write(Arrays.toString(dataSample));
                Response.ok().build();
            }else{
                Object[] errMsg = labFrEnd.responseError(errObject, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);                     
            }            
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
