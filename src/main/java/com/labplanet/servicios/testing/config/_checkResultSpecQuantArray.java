/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;

import LabPLANET.utilities.LPPlatform;
import java.math.BigDecimal;

/**
 *
 * @author Administrator
 */
public class _checkResultSpecQuantArray {
    
    public static Object[][] givenArray(){
            Integer numTesting = 5;
            Integer inumTesting = 0;
            Object[][] QuantitSpecTestingArray = new Object[numTesting][11];

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=LPPlatform.LAB_FALSE;
                QuantitSpecTestingArray[inumTesting][1]="specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec";
                QuantitSpecTestingArray[inumTesting][2]=BigDecimal.valueOf(Double.valueOf("4.000000000000000000001"));            
                QuantitSpecTestingArray[inumTesting][3]=BigDecimal.valueOf(Double.valueOf("4"));  
                QuantitSpecTestingArray[inumTesting][4]=BigDecimal.valueOf(Double.valueOf("4.9"));
                QuantitSpecTestingArray[inumTesting][5]=null;
                QuantitSpecTestingArray[inumTesting][6]=null;
                inumTesting++;}           

            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=LPPlatform.LAB_TRUE;
                QuantitSpecTestingArray[inumTesting][1]="specLimits_quantitativeMinSpecMaxSpec_MinSpecGreaterOrEqualToMaxSpec";                
                QuantitSpecTestingArray[inumTesting][2]=null;
                QuantitSpecTestingArray[inumTesting][3]=null;
                QuantitSpecTestingArray[inumTesting][4]=null;
                QuantitSpecTestingArray[inumTesting][5]=null;
                QuantitSpecTestingArray[inumTesting][6]=null;
                inumTesting++;}
              
            if (inumTesting<numTesting){
                QuantitSpecTestingArray[inumTesting][0]=LPPlatform.LAB_FALSE;
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
                QuantitSpecTestingArray[inumTesting][0]=LPPlatform.LAB_FALSE;
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
            
            return QuantitSpecTestingArray;
    }
    
}
