/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;

import functionalJava.materialSpec.ConfigSpecRule;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class specQualitative extends HttpServlet {

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
                       
            Integer numTesting = 30;
            Integer inumTesting = 0;
            String[][] QualitSpecTestingArray = new String[numTesting][3];
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]=null;
                QualitSpecTestingArray[inumTesting][1]=null;
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="";            
                QualitSpecTestingArray[inumTesting][1]="";
                QualitSpecTestingArray[inumTesting][2]="";
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Rule";            
                QualitSpecTestingArray[inumTesting][1]=null;
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]=null;            
                QualitSpecTestingArray[inumTesting][1]="Text Spec";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]=null;            
                QualitSpecTestingArray[inumTesting][1]=null;
                QualitSpecTestingArray[inumTesting][2]="Separator";
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="Rule";            
                QualitSpecTestingArray[inumTesting][1]="Text Spec";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="equalTo";            
                QualitSpecTestingArray[inumTesting][1]="Text";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="EQUALTO";            
                QualitSpecTestingArray[inumTesting][1]="Text";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="EQUALTO";            
                QualitSpecTestingArray[inumTesting][1]="";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="NOTEQUALTO";            
                QualitSpecTestingArray[inumTesting][1]=null;
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="NOTEQUALTO";            
                QualitSpecTestingArray[inumTesting][1]="Text";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="CONTAINS";            
                QualitSpecTestingArray[inumTesting][1]="";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="CONTAINS";            
                QualitSpecTestingArray[inumTesting][1]="Text";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="NOTCONTAINS";            
                QualitSpecTestingArray[inumTesting][1]=null;
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="NOTCONTAINS";            
                QualitSpecTestingArray[inumTesting][1]=null;
                QualitSpecTestingArray[inumTesting][2]=",";
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="NOTCONTAINS";            
                QualitSpecTestingArray[inumTesting][1]="Text";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISONEOF";            
                QualitSpecTestingArray[inumTesting][1]="";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISONEOF";            
                QualitSpecTestingArray[inumTesting][1]="Text";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISONEOF";            
                QualitSpecTestingArray[inumTesting][1]="Text";
                QualitSpecTestingArray[inumTesting][2]=",";
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISNOTONEOF";            
                QualitSpecTestingArray[inumTesting][1]="";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISNOTONEOF";            
                QualitSpecTestingArray[inumTesting][1]="Text";
                QualitSpecTestingArray[inumTesting][2]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISNOTONEOF";            
                QualitSpecTestingArray[inumTesting][1]="Text";
                QualitSpecTestingArray[inumTesting][2]=",";
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISONEOF";            
                QualitSpecTestingArray[inumTesting][1]="TextA, TextB, TextC,TextD with something else";
                QualitSpecTestingArray[inumTesting][2]=",";
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISONEOF";            
                QualitSpecTestingArray[inumTesting][1]="TextA, TextB, TextC,TextD with something else";
                QualitSpecTestingArray[inumTesting][2]=" ";
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISONEOF";            
                QualitSpecTestingArray[inumTesting][1]="TextA // TextB // TextC // TextD /with/..";
                QualitSpecTestingArray[inumTesting][2]="//";
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISNOTONEOF";            
                QualitSpecTestingArray[inumTesting][1]="TextA, TextB, TextC,TextD with something else";
                QualitSpecTestingArray[inumTesting][2]=",";
                inumTesting++;}

            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISNOTONEOF";            
                QualitSpecTestingArray[inumTesting][1]="TextA, TextB, TextC,TextD with something else";
                QualitSpecTestingArray[inumTesting][2]=" ";
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QualitSpecTestingArray[inumTesting][0]="ISNOTONEOF";            
                QualitSpecTestingArray[inumTesting][1]="TextA // TextB // TextC // TextD /with/..";
                QualitSpecTestingArray[inumTesting][2]="//";
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
            out.println("<th>Test#</th><th>Rule</th><th>Text Spec</th><th>Separator</th><th>Evaluation</th>");
            for (Integer i=0;i<QualitSpecTestingArray.length;i++){
                    out.println("<tr>");
                    String rule = "";
                    String specText = "";
                    String separator = "";
                    if (QualitSpecTestingArray[i][0]!=null){rule = QualitSpecTestingArray[i][0].toString();}
                    if (QualitSpecTestingArray[i][1]!=null){specText = QualitSpecTestingArray[i][1].toString();}               
                    if (QualitSpecTestingArray[i][2]!=null){separator = QualitSpecTestingArray[i][2].toString();}               
                
                    out.println("<td>"+i+"</td><td>"+rule+"</td><td>"+specText+"</td><td>"+separator+"</td>");    
                    String schemaName = "config";
                    Object[] isCorrect = mSpec.specLimitIsCorrectQualitative(schemaName, rule, specText, separator);
                    //Object[] isCorrect = mSpec.specLimitIsCorrectQualitative(QualitSpecTestingArray[i][0], QualitSpecTestingArray[i][1], QualitSpecTestingArray[i][2]);
                    out.println("<td>"+Arrays.toString(isCorrect)+"</td>");
                    out.println("</tr>");
            }                
            out.println("</table>");

            out.println("</body>");
            out.println("</html>");        }
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
