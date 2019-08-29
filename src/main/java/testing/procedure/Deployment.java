/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.procedure;

import lbplanet.utilities.LPFrontEnd;
import databases.Rdbms;
import functionaljavaa.requirement.RequirementDeployment;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class Deployment extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Deployment</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Deployment at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        
        if (Rdbms.getRdbms().startRdbms("labplanet", "avecesllegaelmomento")==null){out.println("Connection to the database not established");return;}

            RequirementDeployment reqDep = new RequirementDeployment();
            String procedure = "sampleA"; 
            Integer procVersion = 1;
            String log = reqDep.procedureDeployment(procedure, procVersion);
            out.println(log);
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
