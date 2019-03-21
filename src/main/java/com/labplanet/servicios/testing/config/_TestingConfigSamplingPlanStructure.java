/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;

import LabPLANET.utilities.LPNulls;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import LabPLANET.utilities.LPArray;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import databases.Rdbms;
import functionalJava.materialSpec._ConfigSamplingPlanForSpec;
import functionalJava.testingScripts.LPTestingOutFormat;

/**
 *
 * @author Administrator
 */
public class _TestingConfigSamplingPlanStructure extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {
        response = LPTestingOutFormat.responsePreparation(response);        
        try (PrintWriter out = response.getWriter()) {
            _ConfigSamplingPlanForSpec smpPlan = new _ConfigSamplingPlanForSpec();

            if (Rdbms.getRdbms().startRdbms("labplanet", "avecesllegaelmomento")==null){out.println("Connection to the database not established");return;}
                
            Integer numTesting = 1;
            Integer inumTesting = 0;
            Object[][] configSamplingPlanTestingArray = new Object[numTesting][6];
            String userName="1"; 
            String userRole="oil1plant_analyst";

            if (inumTesting<numTesting){
                String[] fieldName= new String[0];
                Object[] fieldValue=new Object[0];
                String schemaPrefix="oil-pl1";
                String actionName="NEWSAMPLINGDETAIL";
                String samplingPlan = "84";
                fieldName = LPArray.addValueToArray1D(fieldName, "analysis");
                fieldValue = LPArray.addValueToArray1D(fieldValue, "pH");
                fieldName = LPArray.addValueToArray1D(fieldName, "method_name");
                fieldValue = LPArray.addValueToArray1D(fieldValue, "pH method");
                fieldName = LPArray.addValueToArray1D(fieldName, "method_version");
                fieldValue = LPArray.addValueToArray1D(fieldValue, 2);
                configSamplingPlanTestingArray[inumTesting][0]=schemaPrefix;
                configSamplingPlanTestingArray[inumTesting][1]=samplingPlan;
                configSamplingPlanTestingArray[inumTesting][2]=userName;
                configSamplingPlanTestingArray[inumTesting][3]=fieldName;
                configSamplingPlanTestingArray[inumTesting][4]=fieldValue;
                configSamplingPlanTestingArray[inumTesting][5]=actionName;
                inumTesting++;
            }
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestingConfigSamplingPlanStructure</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestingConfigSamplingPlanStructure at " + request.getContextPath() + "</h1>");
            out.println("<table>");
            out.println("<th>Test#</th><th>Schema Prefix</th><th>Function Being Tested</th><th>Field Name</th><th>Field Value</th><th>Evaluation</th>");           
            
            for (Integer i=0;i<configSamplingPlanTestingArray.length;i++){
                //if (configSamplingPlanTestingArray[i][2]==null && configSamplingPlanTestingArray[i][3]==null){
                out.println("<tr>");
                String[] fieldName=null;    
                Object[] fieldValue=null;
                String schemaPrefix=null;
                userName=null;                
                String functionBeingTested=null;
                Object[] dataSample = null;

                if (configSamplingPlanTestingArray[i][0]!=null){schemaPrefix = (String) configSamplingPlanTestingArray[i][0];}
                if (configSamplingPlanTestingArray[i][5]!=null){functionBeingTested = (String) configSamplingPlanTestingArray[i][5];}
                    
                out.println(LPTestingOutFormat.fieldStart()+i+LPTestingOutFormat.fieldStart()+LPTestingOutFormat.fieldEnd()+schemaPrefix+LPTestingOutFormat.fieldStart()+LPTestingOutFormat.fieldEnd()+functionBeingTested+LPTestingOutFormat.fieldStart()+LPTestingOutFormat.fieldEnd()+Arrays.toString(fieldName)+LPTestingOutFormat.fieldStart()+LPTestingOutFormat.fieldEnd()+"<b>"+Arrays.toString(fieldValue)+"</b>"+LPTestingOutFormat.fieldEnd());

                switch (LPNulls.replaceNull((String) functionBeingTested).toUpperCase()){
                    case "NEWSAMPLINGDETAIL":
                        String sampleTemplate=null;
                        Integer sampleTemplateVersion=null;
                        if (configSamplingPlanTestingArray[i][1]!=null){schemaPrefix = (String) configSamplingPlanTestingArray[i][0];}
                        if (configSamplingPlanTestingArray[i][2]!=null){String samplingPlan = (String) configSamplingPlanTestingArray[i][1];}
                        if (configSamplingPlanTestingArray[i][1]!=null){userName = (String) configSamplingPlanTestingArray[i][2];}
                        if (configSamplingPlanTestingArray[i][3]!=null){fieldName = (String[]) configSamplingPlanTestingArray[i][3];}              
                        if (configSamplingPlanTestingArray[i][4]!=null){fieldValue = (Object[]) configSamplingPlanTestingArray[i][4];}                         
                        try {
                            dataSample = smpPlan.newSamplingPlanDetailRecord(schemaPrefix, userName, userRole, fieldName, fieldValue);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(_TestingConfigSamplingPlanStructure.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    default:                
                        break;
                }
                out.println("<td>"+dataSample[0].toString()+". "+dataSample[1].toString()+". "+dataSample[2].toString()+". "+dataSample[3].toString()+". "+dataSample[4].toString()+". "+dataSample[5].toString()+"</td>");
                out.println("</tr>");
            }
            out.println("</table>");        
            out.println("</body>");
            out.println("</html>");
            Rdbms.closeRdbms();
        }   catch (SQLException|IOException ex) {
                Logger.getLogger(_TestingConfigSamplingPlanStructure.class.getName()).log(Level.SEVERE, null, ex);                           
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
