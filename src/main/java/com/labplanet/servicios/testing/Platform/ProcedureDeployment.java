/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.Platform;

import LabPLANET.utilities.LPFrontEnd;
import databases.Rdbms;
import functionalJava.requirement.RequirementDeployment;
import functionalJava.testingScripts.LPTestingOutFormat;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ProcedureDeployment extends HttpServlet {
        private static final Boolean PROC_DEPLOYMENT_ENTIRE_PROCEDURE=false;
        private static final Boolean PROC_DEPLOYMENT_ASSIGN_USER_SOPS=false;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        

        
        String procName = "process-us"; Integer procVersion = 1;
        
        response=LPTestingOutFormat.responsePreparation(response);
        String fileContent = LPTestingOutFormat.getHtmlStyleHeader(this.getClass().getSimpleName());
        try (PrintWriter out = response.getWriter()) {
             if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}   
        
            RequirementDeployment reqDep = new RequirementDeployment();
        
            try {
                if (PROC_DEPLOYMENT_ENTIRE_PROCEDURE){reqDep.procedureDeployment(procName, procVersion);}

                if (PROC_DEPLOYMENT_ASSIGN_USER_SOPS){reqDep.procedureDeployment(procName, procVersion);}
                
            } catch (SQLException ex) {
                Logger.getLogger(ProcedureDeployment.class.getName()).log(Level.SEVERE, null, ex);
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
