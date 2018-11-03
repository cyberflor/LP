/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.frontend;

import databases.Rdbms;
import functionalJava.sampleStructure.DataSample;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETJson;

/**
 *
 * @author Administrator
 */
public class logSample extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            LabPLANETArray labArr = new LabPLANETArray();
            Enumeration<String> parameterNames = request.getParameterNames();
            String schemaPrefix = request.getParameter("schemaPrefix");
            String sampleTemplate = request.getParameter("sampleTemplate");
            String sampleTemplateVersion = request.getParameter("sampleTemplateVersion");
            String userName = request.getParameter("userName");
            String userRole = request.getParameter("userRole");
            String samplingComment = request.getParameter("samplingComment");

            String fieldNameParams = request.getParameter("fieldName");
            String fieldValueParams = request.getParameter("fieldValue");
            
            String[] fieldName = null; Object[] fieldValue = null;
            
            if (fieldNameParams!=null){
                     fieldName = (String[]) fieldNameParams.toString().split("\\|");                        
                 }              
                 if (fieldValueParams!=null){
                     String[] fieldValueParamsArr = (String[]) fieldValueParams.toString().split("\\|");
                     fieldValue = labArr.convertStringWithDataTypeToObjectArray((String[]) fieldValueParamsArr);
                 }                
            
            int templateVersion = Integer.parseInt(sampleTemplateVersion);
            //String[] parameterValues = request.getParameterValues("sampleTemplate");
            //String smpConfigCode = parameterValues[0].toString();
            Rdbms rdbm = new Rdbms();
            rdbm.startRdbms("labplanet", "LabPlanet");
            DataSample smp = new DataSample("");
                Object[]  dataSample = smp.logSample(rdbm, schemaPrefix, sampleTemplate, templateVersion,
                        fieldName, fieldValue,  userName,  userRole);
                response.getWriter().write(LabPLANETJson.convertToJSON(dataSample));
                if ("LABPLANET_TRUE".equalsIgnoreCase(dataSample[0].toString()))
                    Response.ok("Logged sample "+dataSample[dataSample.length-1].toString()).build();
                else
                    Response.notModified(dataSample[dataSample.length-1].toString()).build();
                    //Response.status(Response.Status.NOT_ACCEPTABLE).build();
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
