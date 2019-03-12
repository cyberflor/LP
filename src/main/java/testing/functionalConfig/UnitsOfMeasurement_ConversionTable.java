/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.functionalConfig;

import databases.Rdbms;
import functionalJava.materialSpec.ConfigSpecStructure;
import functionalJava.unitsOfMeasurement.UnitsOfMeasurement;
import LabPLANET.utilities.LabPLANETArray;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
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
public class UnitsOfMeasurement_ConversionTable extends HttpServlet {

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
        String csvFileName = "uom_familyConversionTable.txt"; String csvFileSeparator=";";
        String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName;         
        
        String userName=null;

        if (Rdbms.getRdbms().startRdbms("labplanet", "avecesllegaelmomento")==null){out.println("Connection to the database not established");return;}

        Integer numTesting = 20;
        Integer inumTesting = 0;
        Object[][] configSpecTestingArray = new Object[numTesting][6]; 
        BigDecimal baseValue=BigDecimal.ZERO;

        configSpecTestingArray = LabPLANETArray.convertCSVinArray(csvPathName, csvFileSeparator);        
                
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
        
        for (Integer j=0;j<configSpecTestingArray[0].length;j++){
            fileContent = fileContent + "<th>"+configSpecTestingArray[0][j]+"</th>";
        }            
        
        for (Integer i=1;i<configSpecTestingArray.length;i++){
            //if (configSpecTestingArray[i][2]==null && configSpecTestingArray[i][3]==null){
            fileContent = fileContent + "<tr>";

            userName=null;                
            String schemaPrefix=null;
            String familyName=null;    
            String[] fieldsToRetrieve=null;
            baseValue=BigDecimal.ONE;            
            Object[][] dataSample = null;

            if (configSpecTestingArray[i][1]!=null){schemaPrefix = (String) configSpecTestingArray[i][1];}
            if (configSpecTestingArray[i][2]!=null){familyName = (String) configSpecTestingArray[i][2];}
            if (configSpecTestingArray[i][3]!=null){fieldsToRetrieve = (String[]) configSpecTestingArray[i][3].toString().split("\\|");}          
            if (configSpecTestingArray[i][4]!=null){baseValue = new BigDecimal(configSpecTestingArray[i][4].toString());}    
            
            fileContent = fileContent + "<td>"+i+"</td><td>"+schemaPrefix+"</td><td>"+familyName+"</td><td>"+Arrays.toString(fieldsToRetrieve)+"</td><td>"+baseValue+"</td>";
            
            
            UnitsOfMeasurement UOM = new UnitsOfMeasurement();
            
            String baseUnitName = UOM.getFamilyBaseUnitName(schemaPrefix, familyName);
            if (baseUnitName.length()==0){
                 fileContent = fileContent + "<td>"+"Nothing to convert with no units"+"</td>";                                
            }else{
                Object[][] tableGet = UOM.getAllUnitsPerFamily(schemaPrefix, familyName, fieldsToRetrieve);
                //fileContent = fileContent + "<td>"+i+"</td><td>"+schemaPrefix+"</td><td>"+familyName+"</td><td>"+Arrays.toString(fieldsToRetrieve)+"</td><td><b>"+baseValue+"</b></td>";
                if ("LABPLANET_FALSE".equalsIgnoreCase(tableGet[0][0].toString())) {
                     fileContent = fileContent + "<td>"+tableGet[0][3].toString()+": "+tableGet[0][5].toString()+"</td>";                
                }else{
                     fileContent = fileContent + "<td><b>There are "+(tableGet.length)+" units in the family "+familyName+", the conversions are <b></b>";  
                     fileContent = fileContent + "<table id=\"scriptTable2\">"; 
                    for (Object[] tableGet1 : tableGet) {
                        fileContent = fileContent + "<tr>";
                        Object[] newValue = UOM.convertValue(schemaPrefix, baseValue, baseUnitName, (String) tableGet1[0]);
                        if ("LABPLANET_FALSE".equalsIgnoreCase(newValue[0].toString())) {
                            fileContent = fileContent + "<td>Not Converted</td>"; 
                        }else{
                            fileContent = fileContent + "<td>"+"Value "+baseValue+" in "+baseUnitName+" is equal to "+newValue[newValue.length-2].toString()+" in "+newValue[newValue.length-1].toString()+" once converted.</td>";                            
                        }
                        fileContent = fileContent + "</tr>";
                    }                 
                     fileContent = fileContent + "</table>";
                }                   
            }    
            fileContent = fileContent + "</tr>";
        }
        fileContent = fileContent + "</table>";        
        out.println(fileContent);

        csvPathName = csvPathName.replace(".txt", ".html");        
        File file = new File(csvPathName);
        
        try (FileWriter fileWriter = new FileWriter(file)){                
            Files.deleteIfExists(file.toPath());
            fileWriter.write(fileContent);
            fileWriter.flush();
//            fileWriter.close();   
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
