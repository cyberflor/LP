/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPHttp;
import LabPLANET.utilities.LabPLANETFrontEnd;
import databases.Rdbms;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import databases.Token;
import java.util.ResourceBundle;
import org.json.simple.JSONObject;

/**
 *
 * @author Administrator
 */
public class appHeaderAPI extends HttpServlet {
    public static final String ERRORMSG_ERROR_STATUS_CODE="Error Status Code";
    public static final String ERRORMSG_MANDATORY_PARAMS_MISSING="API Error Message: There are mandatory params for this API method not being passed";

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
            
            String actionName = request.getParameter("actionName");
            String finalToken = request.getParameter("finalToken");
            if (actionName==null && finalToken==null) {
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, "API Error Message: actionName and finalToken are mandatory params for this API");                    
                Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);   
                Rdbms.closeRdbms(); 
                return ;
            }                     
            switch (actionName.toUpperCase()){
                case "GETAPPHEADER":          
                    
                    String personFieldsName = request.getParameter("personFieldsName");
                    
                    Token token = new Token();
                    String[] tokenParams = token.tokenParamsList();
                    String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

                    String dbUserName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERDB)];
                    String dbUserPassword = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERDB)];
                    String internalUserID = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_INTERNAL_USERID)];         
                    String userRole = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ROLE)];                     
                    
/*                    JsonObject json = Json.createObjectBuilder()
                            .add("DBUser", dbUserName)
                            .add("userRole", userRole).build();
*/
                    JSONObject personInfoJsonObj = new JSONObject();

                    if ( personFieldsName!=null){
                        String[] personFieldsNameArr = personFieldsName.split("\\|");
                        
                        if (!Rdbms.getRdbms().startRdbms(dbUserName, dbUserPassword)) {
                                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);           
                                        errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+"Connection not established");                        
                                        out.println(Arrays.toString(errObject));              
                                        return;                
                        }
                        Object[][] personInfoArr = Rdbms.getRecordFieldsByFilter("config", "person", 
                             new String[]{"person_id"}, new String[]{internalUserID}, personFieldsNameArr);             
                        if ("LABPLANET_FALSE".equals(personInfoArr[0][0].toString())){                                                                                                                                                   
                            Object[] errMsg = LabPLANETFrontEnd.responseError(LPArray.array2dTo1d(personInfoArr), language, null);
                            response.sendError((int) errMsg[0], (String) errMsg[1]);   
                            return;
                        }
                        for (int iFields=0; iFields<personFieldsNameArr.length; iFields++ ){
                            personInfoJsonObj.put(personFieldsNameArr[iFields], personInfoArr[0][iFields]);
                        }
                    }             
                    response.getWriter().write(personInfoJsonObj.toString());                                                                                                                           
                    Response.ok().build();                     
                    Rdbms.closeRdbms();
                    return;
                default:      
                    errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                    errObject = LPArray.addValueToArray1D(errObject, "API Error Message: actionName "+actionName+ " not recognized as an action by this API");                                        
                    Object[] errMsg = LabPLANETFrontEnd.responseError(errObject, language, null);
                    response.sendError((int) errMsg[0], (String) errMsg[1]);                   
                    Rdbms.closeRdbms();
            }            
        }catch(Exception e){            
            String exceptionMessage = e.getMessage();           
            Object[] errMsg = LabPLANETFrontEnd.responseError(new String[]{exceptionMessage}, language, null);
            response.sendError((int) errMsg[0], (String) errMsg[1]); 
            Rdbms.closeRdbms(); 
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