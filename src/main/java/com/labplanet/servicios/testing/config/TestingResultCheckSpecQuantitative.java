/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;

import functionalJava.materialSpec.DataSpec;
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
public class TestingResultCheckSpecQuantitative extends HttpServlet {

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
            DataSpec resChkSpec = new DataSpec();
                        
            Integer numTesting = 38;
            Integer inumTesting = 0;
            Object[][] QuantitSpecTestingArray = new Object[numTesting][9];
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("0");
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.4999");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.501");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-2.51");
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-2.4999");
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=false;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-2.5");            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("-2.5");
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=true;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("5");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("0");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=true;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=true;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("-5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=true;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("-5");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("5");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.900001");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.8999900001");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.00000000001");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.51");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=null;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][7]=true;
                QuantitSpecTestingArray[inumTesting][8]=null;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.52");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][7]=true;
                QuantitSpecTestingArray[inumTesting][8]=null;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.53");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=null;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.54");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=true;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=true;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.55");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=true;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.0");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.0");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.05");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.1");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.1");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=true;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.15");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.799");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.8");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.8");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=true;                
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.81");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.9");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=Float.valueOf("4.9");            
                QuantitSpecTestingArray[inumTesting][1]=Float.valueOf("4");
                QuantitSpecTestingArray[inumTesting][2]=Float.valueOf("4.9");
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=true;
                QuantitSpecTestingArray[inumTesting][5]=Float.valueOf("4.1");
                QuantitSpecTestingArray[inumTesting][6]=Float.valueOf("4.8");
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestingQuantitativeSpecifications</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestingQuantitativeSpecifications at " + request.getContextPath() + "</h1>");            
            out.println("<table>");
            out.println("<th>Test#</th><th>Min Spec</th><th>Min Strict</th><th>Result</th><th>Max Spec</th><th>Max Strict</th><th>Evaluation</th>");
            for (Integer i=0;i<QuantitSpecTestingArray.length;i++){
                //if (QuantitSpecTestingArray[i][2]==null && QuantitSpecTestingArray[i][3]==null){
                    out.println("<tr>");
                    Float result = null;
                    Float minSpec = null;
                    Float maxSpec = null;
                    Boolean minStrict = null;
                    Boolean maxStrict = null;

                    Float minControl = null;
                    Float maxControl = null;
                    Boolean minControlStrict = null;
                    Boolean maxControlStrict = null;

                    if (QuantitSpecTestingArray[i][0]!=null){result = (Float) QuantitSpecTestingArray[i][0];}
                    if (QuantitSpecTestingArray[i][1]!=null){minSpec = (Float) QuantitSpecTestingArray[i][1];}              
                    if (QuantitSpecTestingArray[i][2]!=null){maxSpec = (Float) QuantitSpecTestingArray[i][2];}
                    if (QuantitSpecTestingArray[i][3]!=null){minStrict = (Boolean) QuantitSpecTestingArray[i][3];}
                    if (QuantitSpecTestingArray[i][4]!=null){maxStrict = (Boolean) QuantitSpecTestingArray[i][4];}

                    if (QuantitSpecTestingArray[i][5]!=null){minControl = (Float) QuantitSpecTestingArray[i][5];}              
                    if (QuantitSpecTestingArray[i][6]!=null){maxControl = (Float) QuantitSpecTestingArray[i][6];}
                    if (QuantitSpecTestingArray[i][7]!=null){minControlStrict = (Boolean) QuantitSpecTestingArray[i][7];}
                    if (QuantitSpecTestingArray[i][8]!=null){maxControlStrict = (Boolean) QuantitSpecTestingArray[i][8];}
                    String schemaName = "";
                    if (minControl==null){
                    out.println("<td>"+i+"</td><td>"+minSpec+"</td><td>"+minStrict+"</td><td><b>"+result+"</b></td><td>"+maxSpec+"</td><td>"+maxStrict+"</td>");
                    Object[] resSpecEvaluation = resChkSpec.resultCheck(schemaName, result, minSpec, maxSpec, minStrict, maxStrict);
                    //Object[] specEvaluation = resChkSpec.resultCheck((Float) QuantitSpecTestingArray[i][0], (Float) QuantitSpecTestingArray[i][1], +
                    //                                                (Float) QuantitSpecTestingArray[i][2], (Boolean) QuantitSpecTestingArray[i][3], (Boolean) QuantitSpecTestingArray[i][4]);
                    //Object[] specEvaluation = resChkSpec.resultCheck(, QuantitSpecTestingArray[i][1], QuantitSpecTestingArray[i][2], QuantitSpecTestingArray[i][3], QuantitSpecTestingArray[i][4]);
                    out.println("<td>"+Arrays.toString(resSpecEvaluation)+"</td>");  
                    out.println("</tr>");
                    }
                //}
            }                
            out.println("</table>");

            out.println("<table>");
            out.println("<th>Test#</th><th>Min Spec</th><th>Min Strict</th><th>Min Control</th><th>Min Control Strict</th><th>Result</th><th>Max Cotnrol</th><th>Max Control Strict</th><th>Max Spec</th><th>Max Strict</th><th>Evaluation</th>");
            for (Integer i=0;i<QuantitSpecTestingArray.length;i++){
                //if (QuantitSpecTestingArray[i][2]==null && QuantitSpecTestingArray[i][3]==null){
                    out.println("<tr>");
                    Float result = null;
                    Float minSpec = null;
                    Float maxSpec = null;
                    Boolean minStrict = null;
                    Boolean maxStrict = null;

                    Float minControl = null;
                    Float maxControl = null;
                    Boolean minControlStrict = null;
                    Boolean maxControlStrict = null;

                    if (QuantitSpecTestingArray[i][0]!=null){result = (Float) QuantitSpecTestingArray[i][0];}
                    if (QuantitSpecTestingArray[i][1]!=null){minSpec = (Float) QuantitSpecTestingArray[i][1];}              
                    if (QuantitSpecTestingArray[i][2]!=null){maxSpec = (Float) QuantitSpecTestingArray[i][2];}
                    if (QuantitSpecTestingArray[i][3]!=null){minStrict = (Boolean) QuantitSpecTestingArray[i][3];}
                    if (QuantitSpecTestingArray[i][4]!=null){maxStrict = (Boolean) QuantitSpecTestingArray[i][4];}

                    if (QuantitSpecTestingArray[i][5]!=null){minControl = (Float) QuantitSpecTestingArray[i][5];}              
                    if (QuantitSpecTestingArray[i][6]!=null){maxControl = (Float) QuantitSpecTestingArray[i][6];}
                    if (QuantitSpecTestingArray[i][7]!=null){minControlStrict = (Boolean) QuantitSpecTestingArray[i][7];}
                    if (QuantitSpecTestingArray[i][8]!=null){maxControlStrict = (Boolean) QuantitSpecTestingArray[i][8];}

                    String schemaName = "";
                    out.println("<td>"+i+"</td><<td>"+minSpec+"</td><td>"+minStrict+"</td><td>"+minControl+"</td><td>"+minControlStrict+"</td><td><b>"+result+"</b></td><td>"+maxControl+"</td><td>"+maxControlStrict+"</td><td>"+maxSpec+"</td><td>"+maxStrict+"</td>");
                    Object[] resSpecEvaluation = resChkSpec.resultCheck(schemaName, result, minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);
                    //Object[] specEvaluation = resChkSpec.resultCheck((Float) QuantitSpecTestingArray[i][0], (Float) QuantitSpecTestingArray[i][1], +
                    //                                                (Float) QuantitSpecTestingArray[i][2], (Boolean) QuantitSpecTestingArray[i][3], (Boolean) QuantitSpecTestingArray[i][4]);
                    //Object[] specEvaluation = resChkSpec.resultCheck(, QuantitSpecTestingArray[i][1], QuantitSpecTestingArray[i][2], QuantitSpecTestingArray[i][3], QuantitSpecTestingArray[i][4]);
                    out.println("<td>"+Arrays.toString(resSpecEvaluation)+"</td>");  
                    out.println("</tr>");
                    
                //}
            }                
            out.println("</table>");

/*            out.println("<table>");
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
                Object[] specEvaluation = resChkSpec.resultCheck((Float) QuantitSpecTestingArray[i][0], (Float) QuantitSpecTestingArray[i][1], +
                                                                (Float) QuantitSpecTestingArray[i][2], (Boolean) QuantitSpecTestingArray[i][3], (Boolean) QuantitSpecTestingArray[i][4]);
                out.println("<td>"+specEvaluation[0].toString()+". "+specEvaluation[1].toString()+"</td>");
                out.println("</tr>");
            }                
            out.println("</table>");
*/
            out.println("</body>");
            out.println("</html>");
        }
        catch(IOException error){
//            out.println("</body>");
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
