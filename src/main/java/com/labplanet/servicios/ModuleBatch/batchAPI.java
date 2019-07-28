/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleBatch;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPHttp;
import databases.Rdbms;
import databases.Token;
import functionalJava.batch.BatchArray;
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
    public static final String ERRORMSG_ERROR_STATUS_CODE="Error Status Code";
    public static final String ERRORMSG_MANDATORY_PARAMS_MISSING="API Error Message: There are mandatory params for this API method not being passed";

    public static final String PARAMS_SCHEMA_PREFIX="schemaPrefix"; 
    public static final String PARAMS_ACTION_NAME="actionName"; 
    public static final String PARAMS_FINAL_TOKEN="finalToken"; 

    public static final String PARAMS_BATCH_NAME="batchName"; 
    public static final String PARAMS_BATCH_TEMPLATE="batchTemplate"; 
    public static final String PARAMS_BATCH_TEMPLATE_VERSION="batchTemplateVersion";
    public static final String PARAMS_BATCH_NUM_ROWS="numRows";
    public static final String PARAMS_BATCH_NUM_COLS="numCols";

    
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

        Connection con = null;
        

            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};   

            String[] mandatoryParams = new String[]{PARAMS_SCHEMA_PREFIX};
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, PARAMS_ACTION_NAME);
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, PARAMS_FINAL_TOKEN);
            Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParams);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                response.sendError((int) errMsg[0], (String) errMsg[1]);                
                return ;                
            }            
            String schemaPrefix = request.getParameter(PARAMS_SCHEMA_PREFIX);            
            String actionName = request.getParameter(PARAMS_ACTION_NAME);
            String finalToken = request.getParameter(PARAMS_FINAL_TOKEN);                   

            Token token = new Token();
            String[] tokenParams = token.tokenParamsList();
            String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

            String dbUserName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERDB)];
            String dbUserPassword = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERPW)];
            String internalUserID = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_INTERNAL_USERID)];         
            String userRole = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ROLE)];                     
//            String appSessionIdStr = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, "appSessionId")];
//            String appSessionStartedDate = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, "appSessionStartedDate")];                              

            boolean isConnected = Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword);
            if (!isConnected){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, schemaPrefix);
                response.sendError((int) errMsg[0], (String) errMsg[1]);   
                Rdbms.closeRdbms(); 
                return ;               
            }                
            con = Rdbms.createTransactionWithSavePoint();
            if (con==null){
                 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The Transaction cannot be created, the action should be aborted");
                 return;
            }
            Rdbms.setTransactionId(schemaPrefix);
            try (PrintWriter out = response.getWriter()) {
                Object[] actionEnabled = LPPlatform.procActionEnabled(schemaPrefix, actionName);
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabled[0].toString())){
                    Object[] errMsg =  LPFrontEnd.responseError(actionEnabled, language, schemaPrefix);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    Rdbms.closeRdbms(); 
                    return ;               
                }            
                actionEnabled = LPPlatform.procUserRoleActionEnabled(schemaPrefix, userRole, actionName);
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabled[0].toString())){            
                    Object[] errMsg = LPFrontEnd.responseError(actionEnabled, language, schemaPrefix);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                    Rdbms.closeRdbms(); 
                    return ;                           
                }
                Object[] dataSample = null;            

                switch (actionName.toUpperCase()){
                    case "CREATEBATCHARRAY":   
                        mandatoryParams = new String[]{PARAMS_BATCH_NAME};
                        mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, PARAMS_BATCH_TEMPLATE);
                        mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, PARAMS_BATCH_TEMPLATE_VERSION);
                        mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, PARAMS_BATCH_NUM_ROWS);
                        mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, PARAMS_BATCH_NUM_COLS);
                        areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParams);
                        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                            errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                            errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                            Object[] errMsg = LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                            response.sendError((int) errMsg[0], (String) errMsg[1]);                
                            return ;                
                        }            

                        String batchName = request.getParameter(PARAMS_BATCH_NAME);                        
                        String batchTemplate = request.getParameter(PARAMS_BATCH_TEMPLATE);                        
                        String batchTemplateVersionStr = request.getParameter(PARAMS_BATCH_TEMPLATE_VERSION);                        
                        String numRowsStr = request.getParameter(PARAMS_BATCH_NUM_ROWS);                        
                        String numColsStr = request.getParameter(PARAMS_BATCH_NUM_COLS);       
    //                    public BatchArray(Rdbms rdbm, String schemaName, String batchTemplate, Integer batchTemplateVersion, 
    //                    String batchName, String creator, Integer numRows, Integer numCols){

                        BatchArray bArray = new BatchArray(schemaPrefix,  batchTemplate,  Integer.valueOf(batchTemplateVersionStr),  batchName,  
                                internalUserID,  Integer.valueOf(numRowsStr),  Integer.valueOf(numColsStr));
                        break;
                    case "LOADBATCHARRAY":
                            mandatoryParams = new String[]{PARAMS_BATCH_NAME};
                            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                                Object[] errMsg = LPFrontEnd.responseError(errObject, language, Arrays.toString(mandatoryParams));
                                response.sendError((int) errMsg[0], (String) errMsg[1]);                
                                return ;                
                            }            
                            batchName = request.getParameter(PARAMS_BATCH_NAME);                          
                            bArray = BatchArray.dbGetBatchArray(schemaPrefix, batchName);

                            break;
                    default:
                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                        errObject = LPArray.addValueToArray1D(errObject, "API Error Message: actionName "+actionName+ " not recognized as an action by this API");                                                            
                        Object[] errMsg = LPFrontEnd.responseError(errObject, language, "");
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        Rdbms.closeRdbms();                    
                        return;                    
                }    
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(dataSample[0].toString())){  
                    Rdbms.rollbackWithSavePoint();
                    con.rollback();
                    con.setAutoCommit(true);
                    Object[] errMsg = LPFrontEnd.responseError(dataSample, language, "");
                    Rdbms.closeRdbms();
                    response.sendError((int) errMsg[0], (String) errMsg[1]);    
                }else{
                    con.commit();
                    con.setAutoCommit(true);
                    response.getWriter().write(Arrays.toString(dataSample));      
                    Rdbms.closeRdbms();
                    Response.ok().build();
                }            
            }catch(SQLException e){   
                try {
                    con.rollback();
                    con.setAutoCommit(true);
                } catch (SQLException ex) {
                    Logger.getLogger(batchAPI.class.getName()).log(Level.SEVERE, null, ex);
                }
                Rdbms.closeRdbms();                   
                errObject = new String[]{e.getMessage()};
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);           
            }finally{try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(batchAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
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