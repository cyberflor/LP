/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleEnvMonit;

import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPHttp;
import LabPLANET.utilities.LPPlatform;
import com.labplanet.servicios.app.globalAPIsParams;
import databases.Rdbms;
import databases.Token;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class envMonitSampleAPIfrontend extends HttpServlet {
    public static final String MANDATORY_PARAMS_MAIN_SERVLET="actionName|finalToken";

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
        request=LPHttp.requestPreparation(request);
        response=LPHttp.responsePreparation(response);

        String language = LPFrontEnd.setLanguage(request); 

        try (PrintWriter out = response.getWriter()) {
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};            
            
            String[] mandatoryParams = new String[]{""};
            Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, MANDATORY_PARAMS_MAIN_SERVLET.split("\\|"));                       
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                LPFrontEnd.servletReturnResponseError(request, response, 
                    LPPlatform.API_ERRORTRAPING_MANDATORY_PARAMS_MISSING, new Object[]{areMandatoryParamsInResponse[1].toString()}, language);              
                return;          
            }             
            String schemaPrefix = request.getParameter(globalAPIsParams.REQUEST_PARAM_SCHEMA_PREFIX);            
            String actionName = request.getParameter(globalAPIsParams.REQUEST_PARAM_ACTION_NAME);
            String finalToken = request.getParameter(globalAPIsParams.REQUEST_PARAM_FINAL_TOKEN);                   
           
            Token token = new Token(finalToken);

            if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}              
            
            switch (actionName.toUpperCase()){
                case "UNRECEIVESAMPLES_LIST":
                    RequestDispatcher rd = request.getRequestDispatcher("/frontEnd/sampleAPIfrontEnd");
                    rd.forward(request,response);   
                    return;                    
                default:      
                    LPFrontEnd.servletReturnResponseError(request, response, LPPlatform.API_ERRORTRAPING_PROPERTY_ENDPOINT_NOT_FOUND, new Object[]{actionName, this.getServletName()}, language);              
            }
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
