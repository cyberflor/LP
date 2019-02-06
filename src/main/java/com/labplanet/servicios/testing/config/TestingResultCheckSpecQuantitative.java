/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;

import functionalJava.materialSpec.DataSpec;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ResourceBundle;
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
                        
            Integer numTesting = 2;
            Integer inumTesting = 0;
            Object[][] QuantitSpecTestingArray = new Object[numTesting][11];

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]="LABPLANET_FALSE";
                QuantitSpecTestingArray[inumTesting][1]="specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec";
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Double.valueOf("4.000000000000000000001"));            
                QuantitSpecTestingArray[inumTesting][3]=BigDecimal.valueOf(Double.valueOf("4"));  
                QuantitSpecTestingArray[inumTesting][4]=BigDecimal.valueOf(Double.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][5]=null;
                QuantitSpecTestingArray[inumTesting][6]=null;
                inumTesting++;}           

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]="LABPLANET_TRUE";
                QuantitSpecTestingArray[inumTesting][1]="specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec";                
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                QuantitSpecTestingArray[inumTesting][5]=null;
                QuantitSpecTestingArray[inumTesting][6]=null;
                inumTesting++;}
              
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]="LABPLANET_FALSE";
                QuantitSpecTestingArray[inumTesting][1]="specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec";
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Double.valueOf("4.188888888888888888888887"));            
                QuantitSpecTestingArray[inumTesting][3]=null;  
                QuantitSpecTestingArray[inumTesting][4]=BigDecimal.valueOf(Double.valueOf("4.1888888888888888888888889"));
                QuantitSpecTestingArray[inumTesting][5]=null;
                QuantitSpecTestingArray[inumTesting][6]=null;
                inumTesting++;}           
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=null;
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("0")); 
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("0.9")); 
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Double.valueOf("1"));             
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("1.1")); 
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Double.valueOf("1"));             
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("0.9")); 
                QuantitSpecTestingArray[inumTesting][1]=null;       
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Double.valueOf("1"));      
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("1.1")); 
                QuantitSpecTestingArray[inumTesting][1]=null;    
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Double.valueOf("1"));         
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("1.188888888888888888888887")); 
                QuantitSpecTestingArray[inumTesting][1]=null;    
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Double.valueOf("1.1888888888888888888888889"));         
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("-2.4999")); 
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Double.valueOf("-2.5"));             
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("-2.501"));     
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Double.valueOf("-2.5"));               
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("-2.5"));     
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Double.valueOf("-2.5"));               
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("-2.5"));     
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Double.valueOf("-2.5"));               
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("-2.5"));            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Double.valueOf("-2.51"));
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Double.valueOf("-2.5"));            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Double.valueOf("-2.4999"));
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("-2.5"));            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("-2.5"));
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=false;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("-2.5"));            
                QuantitSpecTestingArray[inumTesting][1]=null;
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("-2.5"));
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=true;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("5"));            
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Float.valueOf("5"));
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("0"));
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Float.valueOf("-5"));
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("5"));
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=true;
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("-5"));
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Float.valueOf("-5"));
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("5"));
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
                QuantitSpecTestingArray[inumTesting][0]="LABPLANET_FALSE";
                QuantitSpecTestingArray[inumTesting][1]="specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec";                
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("4.51"));        
                QuantitSpecTestingArray[inumTesting][3]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][4]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][5]=false;
                QuantitSpecTestingArray[inumTesting][6]=null;
                QuantitSpecTestingArray[inumTesting][7]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][8]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][9]=true;
                QuantitSpecTestingArray[inumTesting][10]=null;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]="LABPLANET_TRUE";
                QuantitSpecTestingArray[inumTesting][1]="IN";                          
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("4.52"));            
                QuantitSpecTestingArray[inumTesting][3]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][4]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][5]=true;
                QuantitSpecTestingArray[inumTesting][6]=false;
                QuantitSpecTestingArray[inumTesting][7]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][8]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][9]=true;
                QuantitSpecTestingArray[inumTesting][10]=null;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("4.53"));            
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][6]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=null;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("4.54"));           
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=true;
                QuantitSpecTestingArray[inumTesting][5]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][6]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=true;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("4.55"));           
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=true;
                QuantitSpecTestingArray[inumTesting][5]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][6]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("4.0"));            
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][3]=true;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=BigDecimal.valueOf(Float.valueOf("4.1"));
                QuantitSpecTestingArray[inumTesting][6]=BigDecimal.valueOf(Float.valueOf("4.8"));
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("4.0"));            
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=BigDecimal.valueOf(Float.valueOf("4.1"));
                QuantitSpecTestingArray[inumTesting][6]=BigDecimal.valueOf(Float.valueOf("4.8"));
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("4.05"));            
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=BigDecimal.valueOf(Float.valueOf("4.1"));
                QuantitSpecTestingArray[inumTesting][6]=BigDecimal.valueOf(Float.valueOf("4.8"));
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("4.1"));            
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=BigDecimal.valueOf(Float.valueOf("4.1"));
                QuantitSpecTestingArray[inumTesting][6]=BigDecimal.valueOf(Float.valueOf("4.8"));
                QuantitSpecTestingArray[inumTesting][7]=false;
                QuantitSpecTestingArray[inumTesting][8]=false;                
                inumTesting++;}
            
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=BigDecimal.valueOf(Float.valueOf("4.1"));            
                QuantitSpecTestingArray[inumTesting][1]=BigDecimal.valueOf(Float.valueOf("4"));
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Float.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][3]=false;
                QuantitSpecTestingArray[inumTesting][4]=false;
                QuantitSpecTestingArray[inumTesting][5]=BigDecimal.valueOf(Float.valueOf("4.1"));
                QuantitSpecTestingArray[inumTesting][6]=BigDecimal.valueOf(Float.valueOf("4.8"));
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
            
        String fileContent = "";
        fileContent = fileContent + "<!DOCTYPE html><html><head>" + "";
        fileContent = fileContent + "<style>";
            ResourceBundle prop = ResourceBundle.getBundle("parameter.config.labtimus");        
            fileContent = fileContent+prop.getString("testingTableStyle1");                
            fileContent = fileContent+prop.getString("testingTableStyle2");                
            fileContent = fileContent+prop.getString("testingTableStyle3");                
            fileContent = fileContent+prop.getString("testingTableStyle4");                
            fileContent = fileContent+prop.getString("testingTableStyle5");       
            
            String booleanMatch =prop.getString("labPLANET_booleanMatch");
            String booleanUnMatch =prop.getString("labPLANET_booleanUnMatch");
            String booleanUndefined =prop.getString("labPLANET_booleanUndefined");
            String errorCodeMatch =prop.getString("labPLANET_errorCodeMatch");
            String errorCodeUnMatch =prop.getString("labPLANET_errorCodeUnMatch");
            String errorCodeUndefined =prop.getString("labPLANET_errorCodeUndefined");            

            
        fileContent = fileContent + "</style>";        
        fileContent = fileContent + "<title>Servlet TestingUnitConversion_ConversionTable</title>" + "";
        fileContent = fileContent + "</head>" + "";
        fileContent = fileContent + "<body>" + "\n";
        fileContent = fileContent + "<h1>Servlet TestingUnitConversion at " + request.getContextPath() + "</h1>" + "";
        fileContent = fileContent + "</body>" + "";
        fileContent = fileContent + "</html>" + "";
        fileContent = fileContent + "<table id=\"scriptTable\">";        
        
            Integer totalTests=0;
            Integer totalLabPlanetBooleanMatch=0;
            Integer totalLabPlanetBooleanUnMatch=0;
            Integer totalLabPlanetBooleanUndefined=0;
            Integer totalLabPlanetErrorCodeMatch=0;
            Integer totalLabPlanetErrorCodeUnMatch=0;
            Integer totalLabPlanetErrorCodeUndefined=0;
            
            fileContent = fileContent +"<th>Test#</th><th>Min Spec</th><th>Min Strict</th><th>Result</th><th>Max Spec</th><th>Max Strict</th><th>Syntaxis</th><th>Code</th><th>Evaluation</th>";
            
            for (Integer i=0;i<QuantitSpecTestingArray.length;i++){
                //if (QuantitSpecTestingArray[i][2]==null && QuantitSpecTestingArray[i][3]==null){
                totalTests++;
                String sintaxisIcon = ""; String codeIcon="";                
                
                    fileContent = fileContent +"<tr>";
                    BigDecimal result = null;
                    BigDecimal minSpec = null;
                    BigDecimal maxSpec = null;
                    Boolean minStrict = null;
                    Boolean maxStrict = null;

                    BigDecimal minControl = null;
                    BigDecimal maxControl = null;
                    Boolean minControlStrict = null;
                    Boolean maxControlStrict = null;

                    String labPlanetBoolean = "";
                    String labPlanetErrorCode="";      
                    
                    if (QuantitSpecTestingArray[i][0]!=null){labPlanetBoolean = (String) QuantitSpecTestingArray[i][0];}else{totalLabPlanetBooleanUndefined++;}
                    if (QuantitSpecTestingArray[i][1]!=null){labPlanetErrorCode = (String) QuantitSpecTestingArray[i][1];}else{totalLabPlanetErrorCodeUndefined++;}

                    if (QuantitSpecTestingArray[i][2]!=null){result = (BigDecimal) QuantitSpecTestingArray[i][2];}
                    if (QuantitSpecTestingArray[i][3]!=null){minSpec = (BigDecimal) QuantitSpecTestingArray[i][3];}              
                    if (QuantitSpecTestingArray[i][4]!=null){maxSpec = (BigDecimal) QuantitSpecTestingArray[i][4];}
                    if (QuantitSpecTestingArray[i][5]!=null){minStrict = (Boolean) QuantitSpecTestingArray[i][5];}
                    if (QuantitSpecTestingArray[i][6]!=null){maxStrict = (Boolean) QuantitSpecTestingArray[i][6];}

                    if (QuantitSpecTestingArray[i][7]!=null){minControl = (BigDecimal) QuantitSpecTestingArray[i][7];}              
                    if (QuantitSpecTestingArray[i][8]!=null){maxControl = (BigDecimal) QuantitSpecTestingArray[i][8];}
                    if (QuantitSpecTestingArray[i][9]!=null){minControlStrict = (Boolean) QuantitSpecTestingArray[i][9];}
                    if (QuantitSpecTestingArray[i][10]!=null){maxControlStrict = (Boolean) QuantitSpecTestingArray[i][10];}
                    String schemaName = "";
                    if (minControl==null){
                    fileContent = fileContent +"<td>"+i+"</td><td>"+minSpec+"</td><td>"+minStrict+"</td><td><b>"+result+"</b></td><td>"+maxSpec+"</td><td>"+maxStrict+"</td>";
                    Object[] resSpecEvaluation = resChkSpec.resultCheck(schemaName, result, minSpec, maxSpec, minStrict, maxStrict);
                    //Object[] specEvaluation = resChkSpec.resultCheck((Float) QuantitSpecTestingArray[i][0], (Float) QuantitSpecTestingArray[i][1], +
                    //                                                (Float) QuantitSpecTestingArray[i][2], (Boolean) QuantitSpecTestingArray[i][3], (Boolean) QuantitSpecTestingArray[i][4]);
                    //Object[] specEvaluation = resChkSpec.resultCheck(, QuantitSpecTestingArray[i][1], QuantitSpecTestingArray[i][2], QuantitSpecTestingArray[i][3], QuantitSpecTestingArray[i][4]);
                    
                    if (labPlanetBoolean.equalsIgnoreCase(resSpecEvaluation[0].toString())){
                        totalLabPlanetBooleanMatch++; sintaxisIcon=booleanMatch;}else{totalLabPlanetBooleanUnMatch++; sintaxisIcon=booleanUnMatch;}
                    if (labPlanetErrorCode.equalsIgnoreCase(resSpecEvaluation[0].toString())){
                        totalLabPlanetErrorCodeMatch++; codeIcon=errorCodeMatch;}else{totalLabPlanetErrorCodeUnMatch++; codeIcon=errorCodeUnMatch;}
                    
                    fileContent = fileContent +"<td>"+sintaxisIcon+"</td>"; 
                    fileContent = fileContent +"<td>"+codeIcon+"</td>"; 
                    
                    fileContent = fileContent +"<td>"+Arrays.toString(resSpecEvaluation)+"</td>";  
                    fileContent = fileContent +"</tr>";
                    }
                //}
            }                
            fileContent = fileContent +"</table>";

            fileContent = fileContent +"<table>";
            fileContent = fileContent +"<th>Test#</th><th>Min Spec</th><th>Min Strict</th><th>Min Control</th><th>Min Control Strict</th><th>Result</th><th>Max Cotnrol</th><th>Max Control Strict</th><th>Max Spec</th><th>Max Strict</th><th>Syntaxis</th><th>Code</th><th>Evaluation</th>";
            for (Integer i=0;i<QuantitSpecTestingArray.length;i++){
                //if (QuantitSpecTestingArray[i][2]==null && QuantitSpecTestingArray[i][3]==null){
                
                totalTests++;
                String sintaxisIcon = ""; String codeIcon="";
                
                    fileContent = fileContent +"<tr>";
                    BigDecimal result = null;
                    BigDecimal minSpec = null;
                    BigDecimal maxSpec = null;
                    Boolean minStrict = null;
                    Boolean maxStrict = null;

                    BigDecimal minControl = null;
                    BigDecimal maxControl = null;
                    Boolean minControlStrict = null;
                    Boolean maxControlStrict = null;
                    
                    String labPlanetBoolean = "";
                    String labPlanetErrorCode="";
                    
                    if (QuantitSpecTestingArray[i][0]!=null){labPlanetBoolean = (String) QuantitSpecTestingArray[i][0];}
                    else{
                        totalLabPlanetBooleanUndefined++; sintaxisIcon=booleanUndefined;}
                    if (QuantitSpecTestingArray[i][1]!=null){labPlanetErrorCode = (String) QuantitSpecTestingArray[i][1];}
                    else{
                        totalLabPlanetErrorCodeUndefined++; codeIcon=errorCodeUndefined;}
                    
                    if (QuantitSpecTestingArray[i][2]!=null){result = (BigDecimal) QuantitSpecTestingArray[i][2];}
                    if (QuantitSpecTestingArray[i][3]!=null){minSpec = (BigDecimal) QuantitSpecTestingArray[i][3];}              
                    if (QuantitSpecTestingArray[i][4]!=null){maxSpec = (BigDecimal) QuantitSpecTestingArray[i][4];}
                    if (QuantitSpecTestingArray[i][5]!=null){minStrict = (Boolean) QuantitSpecTestingArray[i][5];}
                    if (QuantitSpecTestingArray[i][6]!=null){maxStrict = (Boolean) QuantitSpecTestingArray[i][6];}

                    if (QuantitSpecTestingArray[i][7]!=null){minControl = (BigDecimal) QuantitSpecTestingArray[i][7];}              
                    if (QuantitSpecTestingArray[i][8]!=null){maxControl = (BigDecimal) QuantitSpecTestingArray[i][8];}
                    if (QuantitSpecTestingArray[i][9]!=null){minControlStrict = (Boolean) QuantitSpecTestingArray[i][9];}
                    if (QuantitSpecTestingArray[i][10]!=null){maxControlStrict = (Boolean) QuantitSpecTestingArray[i][10];}

                    String schemaName = "";
                    fileContent = fileContent +"<td>"+i+"</td><<td>"+minSpec+"</td><td>"+minStrict+"</td><td>"+minControl+"</td><td>"+minControlStrict+"</td><td><b>"+result+"</b></td><td>"+maxControl+"</td><td>"+maxControlStrict+"</td><td>"+maxSpec+"</td><td>"+maxStrict+"</td>";
                    Object[] resSpecEvaluation = resChkSpec.resultCheck(schemaName, result, minSpec, maxSpec, minStrict, maxStrict, minControl, maxControl, minControlStrict, maxControlStrict);
                    //Object[] specEvaluation = resChkSpec.resultCheck((Float) QuantitSpecTestingArray[i][0], (Float) QuantitSpecTestingArray[i][1], +
                    //                                                (Float) QuantitSpecTestingArray[i][2], (Boolean) QuantitSpecTestingArray[i][3], (Boolean) QuantitSpecTestingArray[i][4]);
                    //Object[] specEvaluation = resChkSpec.resultCheck(, QuantitSpecTestingArray[i][1], QuantitSpecTestingArray[i][2], QuantitSpecTestingArray[i][3], QuantitSpecTestingArray[i][4]);

                    if (labPlanetBoolean.equalsIgnoreCase(resSpecEvaluation[0].toString())){
                        totalLabPlanetBooleanMatch++; sintaxisIcon=booleanMatch;}else{totalLabPlanetBooleanUnMatch++; sintaxisIcon=booleanUnMatch;}
                    if (labPlanetErrorCode.equalsIgnoreCase(resSpecEvaluation[0].toString())){
                        totalLabPlanetErrorCodeMatch++; codeIcon=errorCodeMatch;}else{totalLabPlanetErrorCodeUnMatch++; codeIcon=errorCodeUnMatch;}
                    
                    fileContent = fileContent +"<td>"+sintaxisIcon+"</td>"; 
                    fileContent = fileContent +"<td>"+codeIcon+"</td>"; 
                    fileContent = fileContent +"<td>"+Arrays.toString(resSpecEvaluation)+"</td>";  
                    fileContent = fileContent +"</tr>";
                    
                //}
            }                
            fileContent = fileContent +"</table>";
            fileContent = fileContent +"<table>";
            fileContent = fileContent +"<th>Total Tests</th><th>Total Match Boolean</th><th>Total Boolean Undefined</th><th>Total Boolean Unmatch</th>"
                                                       + "<th>Total Match ErrorCode</th><th>Total ErrorCode Undefined</th><th>Total ErrorCode Unmatch</th>";            
            fileContent = fileContent +"</tr>";
                fileContent = fileContent +"<td>"+totalTests.toString()+booleanMatch+"</td>";  
                fileContent = fileContent +"<td>"+totalLabPlanetBooleanMatch.toString()+"</td>";  
                fileContent = fileContent +"<td>"+totalLabPlanetBooleanUndefined.toString()+"</td>";  
                fileContent = fileContent +"<td>"+totalLabPlanetBooleanUnMatch.toString()+"</td>";  
                fileContent = fileContent +"<td>"+totalLabPlanetErrorCodeMatch.toString()+"</td>";  
                fileContent = fileContent +"<td>"+totalLabPlanetErrorCodeUndefined.toString()+"</td>";  
                fileContent = fileContent +"<td>"+totalLabPlanetErrorCodeUnMatch.toString()+"</td>";  
            fileContent = fileContent +"<//tr>";
            fileContent = fileContent +"</table>";

            out.println(fileContent);  
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
