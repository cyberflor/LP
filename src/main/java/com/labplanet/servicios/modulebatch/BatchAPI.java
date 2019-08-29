/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.modulebatch;

import lbplanet.utilities.LPFrontEnd;
import lbplanet.utilities.LPPlatform;
import lbplanet.utilities.LPHttp;
import com.labplanet.servicios.app.GlobalAPIsParams;
import databases.Rdbms;
import databases.Token;
import functionaljavaa.batch.BatchArray;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class BatchAPI extends HttpServlet {
    
    public static final String API_ENDPOINT_CREATEBATCHARRAY= "CREATEBATCHARRAY";
    public static final String API_ENDPOINT_LOADBATCHARRAY = "LOADBATCHARRAY";
    
    public static final String MANDATORY_PARAMS_MAIN_SERVLET = "actionName|finalToken|schemaPrefix";
    public static final String ERRORMSG_ERROR_STATUS_CODE="Error Status Code";
    public static final String ERRORMSG_MANDATORY_PARAMS_MISSING="API Error Message: There are mandatory params for this API method not being passed";

    public static final String MANDATORY_PARAMS_CREATEBATCHARRAY="batchName|batchTemplate|batchTemplateVersion|numRows|numCols"; 
    public static final String MANDATORY_PARAMS_LOADBATCHARRAY="batchName"; 
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

            Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, MANDATORY_PARAMS_MAIN_SERVLET.split("\\|"));                       
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                 LPFrontEnd.servletReturnResponseError(request, response, 
                         LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                 return;          
             }              
            String schemaPrefix = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_SCHEMA_PREFIX);            
            String actionName = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_ACTION_NAME);
            String finalToken = request.getParameter(GlobalAPIsParams.REQUEST_PARAM_FINAL_TOKEN);                   
                
            Token token = new Token(finalToken);

           if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}      
           
            con = Rdbms.createTransactionWithSavePoint();
            if (con==null){
                 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The Transaction cannot be created, the action should be aborted");
                 return;
            }
            Rdbms.setTransactionId(schemaPrefix);
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
                switch (actionName.toUpperCase()){
                    case API_ENDPOINT_CREATEBATCHARRAY:   
                        areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, MANDATORY_PARAMS_CREATEBATCHARRAY.split("\\|"));                       
                        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                             LPFrontEnd.servletReturnResponseError(request, response, 
                                     LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                             return;          
                         }                                     
                        String batchName = request.getParameter(PARAMS_BATCH_NAME);                        
                        String batchTemplate = request.getParameter(PARAMS_BATCH_TEMPLATE);                        
                        String batchTemplateVersionStr = request.getParameter(PARAMS_BATCH_TEMPLATE_VERSION);                        
                        String numRowsStr = request.getParameter(PARAMS_BATCH_NUM_ROWS);                        
                        String numColsStr = request.getParameter(PARAMS_BATCH_NUM_COLS);       

                        BatchArray bArray = new BatchArray(schemaPrefix,  batchTemplate,  Integer.valueOf(batchTemplateVersionStr),  batchName,  
                                token.getPersonName(),  Integer.valueOf(numRowsStr),  Integer.valueOf(numColsStr));
                        break;
                    case API_ENDPOINT_LOADBATCHARRAY:                        
                        areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, MANDATORY_PARAMS_LOADBATCHARRAY.split("\\|"));                       
                        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                             LPFrontEnd.servletReturnResponseError(request, response, 
                                     LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                             return;          
                         }                                     
                        batchName = request.getParameter(PARAMS_BATCH_NAME);                          
                        bArray = BatchArray.dbGetBatchArray(schemaPrefix, batchName);
                        break;
                    default:
                        LPFrontEnd.servletReturnResponseError(request, response, LPPlatform.API_ERRORTRAPING_PROPERTY_ENDPOINT_NOT_FOUND, new Object[]{actionName, this.getServletName()}, language);              
                }    
/*                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(dataSample[0].toString())){  
                    Rdbms.rollbackWithSavePoint();
                    if (!con.getAutoCommit()){
                        con.rollback();
                        con.setAutoCommit(true);}                
                    LPFrontEnd.servletReturnResponseErrorLPFalseDiagnostic(request, response, dataSample);   
                }else{
                    LPFrontEnd.servletReturnResponseErrorLPTrueDiagnostic(request, response, dataSample);
                }       
*/                
            }finally{try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(BatchAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
}
/*                try {
        con.rollback();
        con.setAutoCommit(true);
        }
        catch (SQLException ex) {
        Logger.getLogger(BatchAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
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