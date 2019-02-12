/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleBatch;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETFrontEnd;
import LabPLANET.utilities.LabPLANETPlatform;
import LabPLANET.utilities.LabPLANETRequest;
import databases.Rdbms;
import databases.Token;
import functionalJava.batch.BatchArray;
import functionalJava.sampleStructure.DataSample;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
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
public class batchAPI extends HttpServlet {

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
        mandatoryParams = labArr.addValueToArray1D(mandatoryParams, "actionName");
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
        String actionName = request.getParameter("actionName");
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

            Object[] actionEnabled = labPlat.procActionEnabled(schemaPrefix, actionName);
            if ("LABPLANET_FALSE".equalsIgnoreCase(actionEnabled[0].toString())){
                Object[] errMsg = labFrEnd.responseError(actionEnabled, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                rdbm.closeRdbms(); 
                return ;               
            }            
            actionEnabled = labPlat.procUserRoleActionEnabled(schemaPrefix, userRole, actionName);
            if ("LABPLANET_FALSE".equalsIgnoreCase(actionEnabled[0].toString())){            
                Object[] errMsg = labFrEnd.responseError(actionEnabled, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);    
                rdbm.closeRdbms(); 
                return ;                           
            }
            
            DataSample smp = new DataSample("");            
            Object[] dataSample = null;            
            
            switch (actionName.toUpperCase()){
                case "CREATEBATCHARRAY":   
                    mandatoryParams = new String[]{"batchName"};
                    mandatoryParams = labArr.addValueToArray1D(mandatoryParams, "batchTemplate");
                    mandatoryParams = labArr.addValueToArray1D(mandatoryParams, "batchTemplateVersion");
                    mandatoryParams = labArr.addValueToArray1D(mandatoryParams, "numRows");
                    mandatoryParams = labArr.addValueToArray1D(mandatoryParams, "numCols");
                    areMandatoryParamsInResponse = labReq.areMandatoryParamsInApiRequest(request, mandatoryParams);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                        Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                        response.sendError((int) errMsg[0], (String) errMsg[1]);                
                        return ;                
                    }            

                    String batchName = request.getParameter("batchName");                        
                    String batchTemplate = request.getParameter("batchTemplate");                        
                    String batchTemplateVersionStr = request.getParameter("batchTemplateVersion");                        
                    String numRowsStr = request.getParameter("numRows");                        
                    String numColsStr = request.getParameter("numCols");       
//                    public BatchArray(Rdbms rdbm, String schemaName, String batchTemplate, Integer batchTemplateVersion, 
//                    String batchName, String creator, Integer numRows, Integer numCols){
                                
                    BatchArray bArray = new BatchArray(rdbm,  schemaPrefix,  batchTemplate,  Integer.valueOf(batchTemplateVersionStr),  batchName,  
                            internalUserID,  Integer.valueOf(numRowsStr),  Integer.valueOf(numColsStr));
                    break;
                case "LOADBATCHARRAY":
                        mandatoryParams = new String[]{"batchName"};
                        if ("LABPLANET_FALSE".equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                            errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                            errObject = labArr.addValueToArray1D(errObject, "API Error Message: There are mandatory params for this API method not being passed: "+areMandatoryParamsInResponse[1].toString());                    
                            Object[] errMsg = labFrEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                            response.sendError((int) errMsg[0], (String) errMsg[1]);                
                            return ;                
                        }            
                        batchName = request.getParameter("batchName");                          
                        bArray = BatchArray.dbGetBatchArray(rdbm, schemaPrefix, batchName);
                        
                        break;
                default:
                    errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                    errObject = labArr.addValueToArray1D(errObject, "API Error Message: actionName "+actionName+ " not recognized as an action by this API");                                                            
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
                Logger.getLogger(batchAPI.class.getName()).log(Level.SEVERE, null, ex);
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
