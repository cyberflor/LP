/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;

import databases.Rdbms;
import functionalJava.materialSpec.ConfigSpecStructure;
import functionalJava.unitsOfMeasurement.UnitsOfMeasurement;
import LabPLANET.utilities.LabPLANETArray;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Fran
 */
public class TestingUOMFamilyTable extends HttpServlet {

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
    try (PrintWriter out = response.getWriter()) {
        response.setContentType("text/html;charset=UTF-8");
        ConfigSpecStructure configSpec = new ConfigSpecStructure();
        String csvFileName = "uom_familyTable.txt"; String csvFileSeparator=";";
        String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName; 
        
        String userName=null;

        if (Rdbms.getRdbms().startRdbms("labplanet", "avecesllegaelmomento")==null){out.println("Connection to the database not established");return;}

        Integer numTesting = 20;
        Integer inumTesting = 0;
        Object[][] configSpecTestingArray = new Object[numTesting][6]; 
                
        configSpecTestingArray = LabPLANETArray.convertCSVinArray(csvPathName, csvFileSeparator);

        /* TODO output your page here. You may use following sample code. */
        String fileContent = "";
        fileContent = fileContent + "<!DOCTYPE html><html><head>" + "";
        fileContent = fileContent + "<style>";
            ResourceBundle prop = ResourceBundle.getBundle("parameter.config.labtimus");        
            fileContent = fileContent+prop.getString("testingTableStyle1");                
            fileContent = fileContent+prop.getString("testingTableStyle2");                
            fileContent = fileContent+prop.getString("testingTableStyle3");                
            fileContent = fileContent+prop.getString("testingTableStyle4");                
            fileContent = fileContent+prop.getString("testingTableStyle5");                
        fileContent = fileContent + "</style>";        
        fileContent = fileContent + "<title>Servlet TestingUnitConversion_ConversionTable</title>" + "";
        fileContent = fileContent + "</head>" + "";
        fileContent = fileContent + "<body>" + "\n";
        fileContent = fileContent + "<h1>Servlet TestingUnitConversion at " + request.getContextPath() + "</h1>" + "";
        fileContent = fileContent + "</body>" + "";
        fileContent = fileContent + "</html>" + "";
        fileContent = fileContent + "<table id=\"scriptTable\">";        
        
        for (Integer i=0;i<configSpecTestingArray.length;i++){
            
            if (i==0){
                fileContent = fileContent + "<th>Test#</th><th>"+configSpecTestingArray[i][0]+"</th>"
                                          + "<th>family</th><th>"+configSpecTestingArray[i][1]+"</th>"
                                          + "<th>Number of units</th><th>"+configSpecTestingArray[i][2]+"</th>";                       
            }else{    
                //if (configSpecTestingArray[i][2]==null && configSpecTestingArray[i][3]==null){
                fileContent = fileContent + "<tr>";

                userName=null;                
                String schemaPrefix=null;
                String familyName=null;    
                String[] fieldsToRetrieve=null;

                Object[][] dataSample = null;

                if (configSpecTestingArray[i][0]!=null){schemaPrefix = (String) configSpecTestingArray[i][0];}
                if (configSpecTestingArray[i][1]!=null){familyName = (String) configSpecTestingArray[i][1];}
                if (configSpecTestingArray[i][2]!=null){fieldsToRetrieve = (String[]) configSpecTestingArray[i][2].toString().split("\\|");}            

                UnitsOfMeasurement UOM = new UnitsOfMeasurement();

                Object[][] tableGet = UOM.getAllUnitsPerFamily(schemaPrefix, familyName, fieldsToRetrieve);

                fileContent = fileContent + "<td>"+i+"</td><td>"+schemaPrefix+"</td><td>"+familyName+"</td><td>"+Arrays.toString(fieldsToRetrieve)+"</td><td><b>"+tableGet.length+"</b></td>";
                if ("LABPLANET_FALSE".equalsIgnoreCase(tableGet[0][0].toString())) {
                     fileContent = fileContent + "<td>"+tableGet[0][3].toString()+": "+tableGet[0][5].toString()+"</td>";                
                }else{

                     fileContent = fileContent + "<td>The units are:";  
                     fileContent = fileContent + "<table id=\"scriptTable2\">"; 
                     fileContent = fileContent + "<tr><th>"+"#"+"</th>";
                    for (String fieldsToRetrieve1 : fieldsToRetrieve) {
                        fileContent = fileContent + "<th>" + fieldsToRetrieve1 + "</th>";
                    }
                     fileContent = fileContent + "</tr>"; 
                     for (int iRows=0;iRows<tableGet.length;iRows++){
                        fileContent = fileContent + "<tr>"; 
                        fileContent = fileContent + "<td>"+iRows+"</td>";     
                        for (int iColumns=0;iColumns<fieldsToRetrieve.length;iColumns++){
                            fileContent = fileContent + "<td>"+tableGet[iRows][iColumns]+"</td>";                               
                        }    
                        fileContent = fileContent + "</tr>"; 
                     }                 

                     fileContent = fileContent + "</table>"; 

                }                   
                //out.println("<td>"+dataSample[0].toString()+". "+dataSample[1].toString()+". "+dataSample[2].toString()+". "+dataSample[3].toString()+". "+dataSample[4].toString()+". "+dataSample[5].toString()+"</td>");
                fileContent = fileContent + "</tr>";
            }    
        }

        fileContent = fileContent + "</table>";   
        out.println(fileContent);  
        
        csvPathName = csvPathName.replace(".txt", ".html");
        File file = new File(csvPathName);
        try (FileWriter fileWriter = new FileWriter(file)){
            if (file.exists()){ file.delete();} 
            fileWriter.write(fileContent);
            fileWriter.flush();
            fileWriter.close();        
        }
        
        Rdbms.closeRdbms();       
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
