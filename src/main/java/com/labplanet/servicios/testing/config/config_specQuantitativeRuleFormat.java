/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;

import LabPLANET.utilities.LabPLANETArray;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import functionalJava.materialSpec.ConfigSpecRule;
import java.util.Arrays;
/**
 *
 * @author Administrator
 */
public class config_specQuantitativeRuleFormat extends HttpServlet {

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
            ConfigSpecRule mSpec = new ConfigSpecRule();
            //MaterialQuantitativeSpec mSpec = new MaterialQuantitativeSpec();            
            
            Integer numTesting = 30;
            Integer inumTesting = 0;
            Float[][] QuantitSpecTestingArray = new Float[numTesting][4];
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-2.51");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-2.4999");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-2.51");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-2.4999");
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("4.9");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("4.9");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("5.00000");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("5.00001");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-4.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-1.112");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-4.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-1.112");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-4.444449");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-1.1111118");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-4.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-1.112");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-4.500001");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-1.1111118");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-4.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-1.1122");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-4.444449");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-1.1124");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-4.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-1.1122");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-4.444449");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-1.1111");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("4");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("3");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("4");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("3");
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("2.9");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("5.1");
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("5");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-2.5001");
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=Float.valueOf("-2.4999");
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("6");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-6");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-5.0");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5.1");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-5.1");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5.009");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5.009");
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                inumTesting++;}
//            String fileName = "C:\\home\\judas\\"+this.getServletName()+".txt";             
//            LabPLANETArray labArr = new LabPLANETArray();
            
//            String[] QuantitSpecTestingArrayStr = Arrays.copyOf(QuantitSpecTestingArray, QuantitSpecTestingArray.length, String[].class);            
//            labArr.arrayToFile(null, QuantitSpecTestingArrayStr, fileName, ";");
    
//    if (1==1){return;}
    
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestingQuantitativeSpecifications</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestingQuantitativeSpecifications at " + request.getContextPath() + "</h1>");            
            out.println("<table>");
            out.println("<th>Min Spec</th><th>Max Spec</th><th>Evaluation</th>");
            for (Integer i=0;i<QuantitSpecTestingArray.length;i++){
                if (QuantitSpecTestingArray[i][2]==null && QuantitSpecTestingArray[i][3]==null){
                    out.println("<tr>");
                    String minSpecText = "";
                    String maxSpecText = "";
                    if (QuantitSpecTestingArray[i][0]!=null){minSpecText = QuantitSpecTestingArray[i][0].toString();}
                    if (QuantitSpecTestingArray[i][1]!=null){maxSpecText = QuantitSpecTestingArray[i][1].toString();}               
                
                    out.println("<td>"+minSpecText+"</td><td>"+maxSpecText+"</td>");
                    Object[] isCorrect = mSpec.specLimitIsCorrectQuantitative(QuantitSpecTestingArray[i][0], QuantitSpecTestingArray[i][1]);
                    out.println("<td>"+Arrays.toString(isCorrect)+"</td>");
                    out.println("</tr>");
                }
            }                
            out.println("</table>");

            out.println("<table>");
            out.println("<th>Min Spec</th><th>Min Control</th><th>Max Control</th><th>Max Spec</th><th>Evaluation</th>");
            for (Integer i=0;i<QuantitSpecTestingArray.length;i++){
                out.println("<tr>");
                String minSpecText = "";
                if (QuantitSpecTestingArray[i][0]!=null){minSpecText = QuantitSpecTestingArray[i][0].toString();}
                String minControlText = "";
                if (QuantitSpecTestingArray[i][2]!=null){minControlText = QuantitSpecTestingArray[i][2].toString();}
                
                String maxSpecText = "";                
                if (QuantitSpecTestingArray[i][1]!=null){maxSpecText = QuantitSpecTestingArray[i][1].toString();}               
                String maxControlText = "";                
                if (QuantitSpecTestingArray[i][3]!=null){maxControlText = QuantitSpecTestingArray[i][3].toString();}               
                
                out.println("<td>"+minSpecText+"</td><td>"+minControlText+"</td>");
                out.println("<td>"+maxControlText+"</td><td>"+maxSpecText+"</td>");
                Object[] isCorrect = mSpec.specLimitIsCorrectQuantitative(QuantitSpecTestingArray[i][0], QuantitSpecTestingArray[i][1],QuantitSpecTestingArray[i][2],QuantitSpecTestingArray[i][3]);
                out.println("<td>"+Arrays.toString(isCorrect)+"</td>");
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
