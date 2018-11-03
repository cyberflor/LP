/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import result.specifications.MaterialQualitativeSpec;
import functionalJava.materialSpec.DataSpec;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class TestingResultCheckSpecQualitative extends HttpServlet {

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
            DataSpec resChkSpec = new DataSpec();
                       
            Integer numTesting = 44;
            Integer inumTesting = 0;
            String[][] QualitSpecTestingArray = new String[numTesting][5];
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]=null;
                QualitSpecTestingArray[inumTesting][1]=null;
                QualitSpecTestingArray[inumTesting][2]=null;
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]=null;
                QualitSpecTestingArray[inumTesting][2]=null;
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="Equal";
                QualitSpecTestingArray[inumTesting][2]=null;
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]=null;
                QualitSpecTestingArray[inumTesting][2]="Hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]=null;
                QualitSpecTestingArray[inumTesting][1]="Hello";
                QualitSpecTestingArray[inumTesting][2]="Hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="Hello";
                QualitSpecTestingArray[inumTesting][2]="Hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="Equal To";
                QualitSpecTestingArray[inumTesting][2]="Hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="EqualTo";
                QualitSpecTestingArray[inumTesting][2]="Hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="EqualTo";
                QualitSpecTestingArray[inumTesting][2]="Hello hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="Not Equal To";
                QualitSpecTestingArray[inumTesting][2]="Hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="NotEqualTo";
                QualitSpecTestingArray[inumTesting][2]="Hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="NotEqualTo";
                QualitSpecTestingArray[inumTesting][2]="Hello hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="contains";
                QualitSpecTestingArray[inumTesting][2]="Hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="Contains";
                QualitSpecTestingArray[inumTesting][2]="hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="contains";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="CONTAINS";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="CONTAINS";
                QualitSpecTestingArray[inumTesting][2]="GoodbyehelloGoodbye";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="e Hello g";
                QualitSpecTestingArray[inumTesting][1]="CONTAINS";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="e Hellog";
                QualitSpecTestingArray[inumTesting][1]="CONTAINS";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello goob";
                QualitSpecTestingArray[inumTesting][1]="CONTAINS";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello.";
                QualitSpecTestingArray[inumTesting][1]="CONTAINS";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello. Goodbye";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello.";
                QualitSpecTestingArray[inumTesting][1]="CONTAINS";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello. Goodbye";
                QualitSpecTestingArray[inumTesting][3]=".";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello.";
                QualitSpecTestingArray[inumTesting][1]="CONTAINS";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello. Goodbye";
                QualitSpecTestingArray[inumTesting][3]=",";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="Not contains";
                QualitSpecTestingArray[inumTesting][2]="Hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="NotContains";
                QualitSpecTestingArray[inumTesting][2]="hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="Notcontains";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello hello";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="NotCONTAINS";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="Not CONTAINS";
                QualitSpecTestingArray[inumTesting][2]="GoodbyehelloGoodbye";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="e Hello g";
                QualitSpecTestingArray[inumTesting][1]="NotCONTAINS";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="e Hellog";
                QualitSpecTestingArray[inumTesting][1]="NotCONTAINS";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello goob";
                QualitSpecTestingArray[inumTesting][1]="NotCONTAINS";
                QualitSpecTestingArray[inumTesting][2]="Goodbye Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=null;
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="ISONEOF";
                QualitSpecTestingArray[inumTesting][2]="Goodbye,Hello,Goodbye";
                QualitSpecTestingArray[inumTesting][3]=",";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="ISONEOF";
                QualitSpecTestingArray[inumTesting][2]="Goodbye,Hello,Goodbye";
                QualitSpecTestingArray[inumTesting][3]=".";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="ISONEOF";
                QualitSpecTestingArray[inumTesting][2]="Goodbye,Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=",";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hell";
                QualitSpecTestingArray[inumTesting][1]="ISONEOF";
                QualitSpecTestingArray[inumTesting][2]="Goodbye,Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=",";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="ISONEOF";
                QualitSpecTestingArray[inumTesting][2]="Goodbye,Hell, o, Goodbye";
                QualitSpecTestingArray[inumTesting][3]=",";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hel,lo";
                QualitSpecTestingArray[inumTesting][1]="ISONEOF";
                QualitSpecTestingArray[inumTesting][2]="Hel,lo";
                QualitSpecTestingArray[inumTesting][3]=",";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="ISNOTONEOF";
                QualitSpecTestingArray[inumTesting][2]="Goodbye,Hello,Goodbye";
                QualitSpecTestingArray[inumTesting][3]=",";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="ISNOTONEOF";
                QualitSpecTestingArray[inumTesting][2]="Goodbye,Hello,Goodbye";
                QualitSpecTestingArray[inumTesting][3]=".";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="ISNOTONEOF";
                QualitSpecTestingArray[inumTesting][2]="Goodbye,Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=",";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hell";
                QualitSpecTestingArray[inumTesting][1]="ISNOTONEOF";
                QualitSpecTestingArray[inumTesting][2]="Goodbye,Hello Goodbye";
                QualitSpecTestingArray[inumTesting][3]=",";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hello";
                QualitSpecTestingArray[inumTesting][1]="ISNOTONEOF";
                QualitSpecTestingArray[inumTesting][2]="Goodbye,Hell, o, Goodbye";
                QualitSpecTestingArray[inumTesting][3]=",";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Hel,lo";
                QualitSpecTestingArray[inumTesting][1]="ISNOTONEOF";
                QualitSpecTestingArray[inumTesting][2]="Hel,lo";
                QualitSpecTestingArray[inumTesting][3]=",";
                QualitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestingQualitativeSpecifications</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Unit Testing for Qualitative Specifications at " + request.getContextPath() + "</h1>");            
            out.println("<table>");
            out.println("<th>Test#</th><th>Result</th><th>Rule Type</th><th>Values</th><th>Separator</th><th>List Name</th><th>Evaluation</th>");
            for (Integer i=0;i<QualitSpecTestingArray.length;i++){
                    out.println("<tr>");
                    String schemaName = "oil-pl1-data";
                    String result = "";
                    String ruleType = "";
                    String values = "";
                    String separator = "";
                    String listName = "";
                    if (QualitSpecTestingArray[i][0]!=null){result = QualitSpecTestingArray[i][0];}
                    if (QualitSpecTestingArray[i][1]!=null){ruleType = QualitSpecTestingArray[i][1];}               
                    if (QualitSpecTestingArray[i][2]!=null){values = QualitSpecTestingArray[i][2];}               
                    if (QualitSpecTestingArray[i][3]!=null){separator = QualitSpecTestingArray[i][3];}               
                    if (QualitSpecTestingArray[i][4]!=null){listName = QualitSpecTestingArray[i][4];}               
                    
                    out.println("<td>"+i+"</td><td>"+result+"</td><td>"+ruleType+"</td><td>"+values+"</td><td>"+separator+"</td><td>"+listName+"</td>");

                    Object[] resSpecEvaluation = resChkSpec.resultCheck(schemaName, result, ruleType, values, separator, listName);
                    out.println("<td>"+Arrays.toString(resSpecEvaluation)+"</td>");                    
                    out.println("</tr>");
            }                
            out.println("</table>");

            out.println("</body>");
            out.println("</html>");        
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
