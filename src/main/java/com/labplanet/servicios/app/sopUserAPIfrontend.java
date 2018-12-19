/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.app;

import java.io.IOException;
import java.io.PrintWriter;
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
public class sopUserAPIfrontend extends HttpServlet {

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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

                    JSONArray SopOptions = new JSONArray(); 
                    
                    //JSONObject sopElement = new JSONObject();
                    
                    JSONObject SopOption = new JSONObject();
                    SopOption.put("name", "AllMySOPs");
                    SopOption.put("label_en", "All my SOPs");
                    SopOption.put("label_es", "Todos Mis PNTs");
                    SopOption.put("window_url", "Modulo1/home.js");
                    SopOption.put("mode", "edit");
                    SopOption.put("branch_level", "level1");
                    SopOption.put("type", "tree-list");
                    SopOptions.add(SopOption);
                    
                    SopOption = new JSONObject();
                    SopOption.put("name", "MyPendingSOPs");
                    SopOption.put("label_en", "My Pending SOPs");
                    SopOption.put("label_es", "Mis PNT Pendientes");
                    SopOption.put("window_url", "Modulo1/home.js");
                    SopOption.put("mode", "edit");
                    SopOption.put("branch_level", "level1");
                    SopOption.put("type", "tree-list");
                    SopOptions.add(SopOption);
                    
                    SopOption = new JSONObject();
                    SopOption.put("name", "ProcSOPs");
                    SopOption.put("label_en", "Procedure SOPs");
                    SopOption.put("label_es", "PNTs del proceso");
                    SopOption.put("window_url", "Modulo1/home.js");
                    SopOption.put("mode", "edit");
                    SopOption.put("branch_level", "level1");
                    SopOption.put("type", "tree-list");
                    SopOptions.add(SopOption);
                    
                    JSONObject sopElement = new JSONObject();
                    sopElement.put("definition", SopOptions);
                    sopElement.put("name", "SOP");
                    sopElement.put("version", "1");
                    sopElement.put("label_en", "SOPs");
                    sopElement.put("label_es", "P.N.T.");
                    sopElement.put("schemaPrefix", "module1");
                    //SopElement.add(SopOption);
                    
                    JSONArray arrFinal = new JSONArray();
                    arrFinal.add(sopElement);
                    
                    Response.ok().build();
                    response.getWriter().write(arrFinal.toString());                               
                    
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
