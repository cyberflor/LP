/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPHttp;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPPlatform;
import databases.Rdbms;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import databases.Token;
import databases.dbObjectsConfigTables;
import org.json.simple.JSONObject;

/**
 *
 * @author Administrator
 */
public class appHeaderAPI extends HttpServlet {
    public static final String ENDPOINT_NAME_GETAPPHEADER="GETAPPHEADER";
    
    public static final String MANDATORY_PARAMS_MAIN_SERVLET="actionName|finalToken";
    
    public static final String MANDATORY_PARAMS_FRONTEND_GETAPPHEADER_PERSONFIELDSNAME_DEFAULT_VALUE="first_name|last_name|photo";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {
        
        response=LPHttp.responsePreparation(response);
        
        request=LPHttp.requestPreparation(request);
        String language = LPFrontEnd.setLanguage(request); 
   
        try (PrintWriter out = response.getWriter()) {            

            Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, MANDATORY_PARAMS_MAIN_SERVLET.split("\\|"));                       
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                LPFrontEnd.servletReturnResponseError(request, response, 
                    LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                return;          
            }                  
            String actionName = request.getParameter(globalAPIsParams.REQUEST_PARAM_ACTION_NAME);
            String finalToken = request.getParameter(globalAPIsParams.REQUEST_PARAM_FINAL_TOKEN);

            JSONObject personInfoJsonObj = new JSONObject();
            switch (actionName.toUpperCase()){
                case ENDPOINT_NAME_GETAPPHEADER:          
                    String personFieldsName = request.getParameter(globalAPIsParams.REQUEST_PARAM_PERSON_FIELDS_NAME);
                    String[] personFieldsNameArr = new String[0];
                    if ( personFieldsName==null || personFieldsName.length()==0){
                        personFieldsNameArr = MANDATORY_PARAMS_FRONTEND_GETAPPHEADER_PERSONFIELDSNAME_DEFAULT_VALUE.split("\\|");                            
                    }else{
                        personFieldsNameArr = personFieldsName.split("\\|");
                    }    
                    if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}   
                    Token token = new Token(finalToken);
                    Object[][] personInfoArr = Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_CONFIG, dbObjectsConfigTables.TABLE_CONFIG_PERSON, 
                         new String[]{dbObjectsConfigTables.FLD_CONFIG_PERSON_PERSON_ID}, new String[]{token.getPersonName()}, personFieldsNameArr);             
                    if (LPPlatform.LAB_FALSE.equals(personInfoArr[0][0].toString())){                                                                                                                                                   
                        Object[] errMsg = LPFrontEnd.responseError(LPArray.array2dTo1d(personInfoArr), language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);   
                        Rdbms.closeRdbms();    
                        return;
                    }
                    for (int iFields=0; iFields<personFieldsNameArr.length; iFields++ ){
                        personInfoJsonObj.put(personFieldsNameArr[iFields], personInfoArr[0][iFields]);
                    }                                 
                    token=null;
                    LPFrontEnd.servletReturnSuccess(request, response, personInfoJsonObj);
                    return;  
                default:      
                    LPFrontEnd.servletReturnResponseError(request, response, LPPlatform.API_ERRORTRAPING_PROPERTY_ENDPOINT_NOT_FOUND, new Object[]{actionName, this.getServletName()}, language);                                                      
            }            
        }catch(Exception e){            
            String exceptionMessage = e.getMessage();           
            Object[] errMsg = LPFrontEnd.responseError(new String[]{exceptionMessage}, language, null);
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