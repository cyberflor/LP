/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.platform;

import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPQualityAssurance;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ResourceBundle;
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
public class QualityAssurance_requirements_doc extends HttpServlet {

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
        
        String csvFileName = "QualityAssurance_requirements_doc_checker.txt"; 
        String csvFileSeparator=";";
        String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName; 
        
        try (PrintWriter out = response.getWriter()) {
            String fileContent = "";
            fileContent = fileContent + "<!DOCTYPE html>" + "";
            fileContent = fileContent + "<html>" + "";
            fileContent = fileContent + "<head>" + "";
            fileContent = fileContent + "<style>";
                ResourceBundle prop = ResourceBundle.getBundle("parameter.config.labtimus");        
                fileContent = fileContent+prop.getString("testingTableStyle1");                
                fileContent = fileContent+prop.getString("testingTableStyle2");                
                fileContent = fileContent+prop.getString("testingTableStyle3");                
                fileContent = fileContent+prop.getString("testingTableStyle4");                
                fileContent = fileContent+prop.getString("testingTableStyle5");                
                fileContent = fileContent+prop.getString("testingTableStyle6");  
            fileContent = fileContent + "</style>";

            fileContent = fileContent + "<title>Servlet " + this.getServletName()+"</title>" + "";
            fileContent = fileContent + "</head>" + "";
            fileContent = fileContent + "<body>" + "\n";
            fileContent = fileContent + "<h1>Servlet " + this.getServletName()+" at " + request.getContextPath() + "</h1>" + "";
            fileContent = fileContent + "</body>" + "";
            fileContent = fileContent + "</html>" + "";
            fileContent = fileContent + "<table id=\"scriptTable\">";  
            
            LPQualityAssurance labQA = new LPQualityAssurance();
            
            try {
                Object[][] methodsList = LPQualityAssurance.javaDocChecker();
                out.println("Number of Methods: "+methodsList.length);
                
                for (Integer j=0;j<methodsList[0].length;j++){
                    fileContent = fileContent + "<th>"+methodsList[0][j]+"</th>";
                }                    

                for (Integer i=1;i<methodsList.length;i++){
                    fileContent = fileContent + "<tr>" + "";
                    for (Integer j=0;j<methodsList[0].length;j++){
                        if (methodsList[i][j].toString().startsWith("ERROR:")){
                            fileContent = fileContent + "<td class\"textInRed\">";
                        }else{
                        fileContent = fileContent + "<td>";
                        }
                        fileContent = fileContent + methodsList[i][j]+"</td>";
                    }  
                    fileContent = fileContent + "</tr>" + "";
                }                    
                
                
                out.println("</table>");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(QualityAssurance_requirements_doc.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            fileContent = fileContent + "</table>";        
            out.println(fileContent);

            csvPathName = csvPathName.replace(".txt", ".html");
            File file = new File(csvPathName);
            try (FileWriter fileWriter = new FileWriter(file)){
                Files.deleteIfExists(file.toPath());
                fileWriter.write(fileContent);
                fileWriter.flush();
                fileWriter.close();   
            }
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
