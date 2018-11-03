package com.labplanet.servicios;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import LabPLANET.utilities.LabPLANETArray;
import databases.Rdbms;
import functionalJava.analysis.UserMethod;
import functionalJava.sampleStructure.DataSample;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import testing.functionalData.testingFileContentSections;

/**
 *
 * @author Administrator
 */
public class UT_moduleSample extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        Rdbms rdbm = new Rdbms();         
        String fileContent = "";    
        String csvFileName = "dataSampleStructure.txt"; String csvFileSeparator=";";
        String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName;         
        try (PrintWriter out = response.getWriter()) {
            
            response.setContentType("text/html;charset=UTF-8");
            UserMethod um = new UserMethod();
           
            boolean isConnected = false;
            isConnected = rdbm.startRdbms("labplanet", "LabPlanet");

 
            Object[][] dataSample2D = new Object[0][0];
        
            Integer numTesting = 1;
            Integer inumTesting = 0;
            Object[][] configSpecTestingArray = new Object[numTesting][6];
            LabPLANETArray labArr = new LabPLANETArray();
            String userName="1"; 
            String userRole="oil1plant_analyst";
            
            configSpecTestingArray = labArr.convertCSVinArray(csvPathName, csvFileSeparator);            
            
            /* TODO output your page here. You may use following sample code. */
 /*           out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet dataSampleStructure</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet dataSampleStructure at " + request.getContextPath() + "</h1>");
            out.println("<h1>Connected to the db? " + isConnected + "</h1>");
            out.println("</body>");
            out.println("</html>");
*/            
            //String fileContent="";
            fileContent = testingFileContentSections.getHtmlStyleHeader(this.getServletName());

            DataSample smp = new DataSample("");

            for (Integer j=0;j<configSpecTestingArray[0].length;j++){
                fileContent = fileContent + "<th>"+configSpecTestingArray[0][j]+"</th>";
            }            
        }
        fileContent = fileContent + "</table>";        
        out.println(fileContent);

        csvPathName = csvPathName.replace(".txt", ".html");
        File file = new File(csvPathName);
            try (FileWriter fileWriter = new FileWriter(file)) {
                if (file.exists()){ file.delete();}
                fileWriter.write(fileContent);
                fileWriter.flush();
            } 

        rdbm.closeRdbms();
        
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
