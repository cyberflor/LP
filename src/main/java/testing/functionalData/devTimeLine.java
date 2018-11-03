/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.functionalData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class devTimeLine extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            String csvFileName = "LabPLANETTimeLine.html"; String csvFileSeparator=";";
            String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\SourceCode\\"+csvFileName; 
            
            Scanner scanIn = new Scanner(new BufferedReader(new FileReader(csvPathName)));

            String myJsonInString = "";
            myJsonInString = myJsonInString +" text: { name: \"Parent node\" },"
			+"children: ["
+"				{	"
+"					HTMLclass: \"timeline main-date\","
+"					text: { desc: \"\", name: \"01.01.2014\" },"
+"					children: ["
+"						{"
+"							text: { name: \"Event 1\" },"
+"						},"
+"						{"
+"							text: { name: \"Event 23\" }"
+"						}"
+"					]"
+"				},"
+"				{	"
+"					HTMLclass: \"main-date\","
+"					text: { name: \"12.02.2014\" },"
+"					collapsed: true,"
+"					children: ["
+"						{"
+"							text: { name: \"Event 1\" }"
+"						},"
+"						{"
+"							text: { name: \"Event 222\" }"
+"						}"
+"					]"
+"				},"
+"				{"
+"					HTMLclass: \"main-date\","
+"					text: { name: \"23.02.2014\" },"
+"					children: ["
+"						{"
+"							text: { name: \"Event 1\" }"
+"						},"
+"						{"
+"							text: { name: \"Event 2\" }"
+"						}"
+"					]"
+"				},"
+"				{"
+"					HTMLclass: \"main-date\","
+"					text: { name: \"03.06.2014\" }"
+"				}"
+"			]";

            String fileContent = "";
            while (scanIn.hasNextLine()){
                String inputLine = scanIn.nextLine();
                fileContent=fileContent+inputLine.replace("##NodeStructure##", myJsonInString);
            }
            out.println(fileContent);
            
            /* TODO output your page here. You may use following sample code.
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet devTimeLine</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet devTimeLine at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");*/
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
