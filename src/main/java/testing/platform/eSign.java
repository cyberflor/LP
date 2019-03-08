/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.platform;

import LabPLANET.utilities.LabPLANETArray;
import databases.Rdbms;
import functionalJava.materialSpec.ConfigSpecStructure;
import functionalJava.user.UserSecurity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
public class eSign extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        ConfigSpecStructure configSpec = new ConfigSpecStructure();
        
        Integer numTesting = 1;
        Integer inumTesting = 0;
        Object[][] configSpecTestingArray = new Object[numTesting][4];
        String userName="1"; 
        String userRole="oil1plant_analyst";    
        
        String csvFileName = "userSecurity.txt"; String csvFileSeparator=";";
        String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName; 
        
        configSpecTestingArray = LabPLANETArray.convertCSVinArray(csvPathName, csvFileSeparator);

        if (Rdbms.getRdbms().startRdbms("labplanet", "avecesllegaelmomento")==null){out.println("Connection to the database not established");return;}
               
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
        fileContent = fileContent + "</style>";

        fileContent = fileContent + "<title>Servlet " + this.getServletName()+"</title>" + "";
        fileContent = fileContent + "</head>" + "";
        fileContent = fileContent + "<body>" + "\n";
        fileContent = fileContent + "<h1>Servlet " + this.getServletName()+" at " + request.getContextPath() + "</h1>" + "";
        fileContent = fileContent + "</body>" + "";
        fileContent = fileContent + "</html>" + "";
        fileContent = fileContent + "<table id=\"scriptTable\">";  
        
        UserSecurity usrSec = new UserSecurity();
            
        for (Integer j=0;j<configSpecTestingArray[0].length;j++){
            fileContent = fileContent + "<th>"+configSpecTestingArray[0][j]+"</th>";
        }            

        for (Integer i=1;i<configSpecTestingArray.length;i++){
            //if (configSpecTestingArray[i][2]==null && configSpecTestingArray[i][3]==null){
            fileContent = fileContent + "<tr>";
            String functionBeingTested=null;
            userName=null;                
            String userPass=null;                
            String userEsign=null;                            

            Object[] usrSecDiag = null;

            if (configSpecTestingArray[i][0]!=null){functionBeingTested = (String) configSpecTestingArray[i][0];}
            if (configSpecTestingArray[i][1]!=null){userName = (String) configSpecTestingArray[i][1];}
            if (configSpecTestingArray[i][2]!=null){userPass = (String) configSpecTestingArray[i][2];}
            if (configSpecTestingArray[i][3]!=null){userEsign = (String) configSpecTestingArray[i][3];}

            fileContent = fileContent + "<td>"+i+"</td><td>"+functionBeingTested+"</td>";

            switch (functionBeingTested.toUpperCase()){
                case "SETUSERESIGN":
                    //fileContent = fileContent + "<td>User Name</td><td>Password</td>";
                    fileContent = fileContent + "<td>"+userName+"</td><td>"+userPass+"</td><td>"+userEsign+"</td>";
                    try {
                        usrSecDiag = usrSec.setUserEsig(userName, userPass, userEsign);
                        fileContent = fileContent + "<td>"+usrSecDiag[0].toString()+". "+usrSecDiag[1].toString();
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;       
                case "ISVALIDESIGN":
                    //fileContent = fileContent + "<td>User Name</td><td>Password</td>";
                    fileContent = fileContent + "<td>"+userName+"</td><td>"+userPass+"</td><td>"+userEsign+"</td>";
                    try {
                        usrSecDiag = usrSec.isValidESign(userName, userEsign);
                        fileContent = fileContent + "<td>"+usrSecDiag[0].toString()+". "+usrSecDiag[1].toString();
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;      
                case "ISVALIDUSERANDESIGN":
                    //fileContent = fileContent + "<td>User Name</td><td>Password</td>";
                    fileContent = fileContent + "<td>"+userName+"</td><td>"+userPass+"</td><td>"+userEsign+"</td>";
                    try {
                        usrSecDiag = usrSec.isValidESign(userName, userPass, userEsign);
                        fileContent = fileContent + "<td>"+usrSecDiag[0].toString()+". "+usrSecDiag[1].toString();
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                    }
                    break;                       
               /* LabPLANETNullValue labNull = new LabPLANETNullValue();
                if (functionBeingTested.equalsIgnoreCase("GETSAMPLEINFO")){
                    fileContent = fileContent + "<td>"+usrSecDiag[0].toString();
                    fileContent = fileContent + ". "+labNull.replaceNull((String) usrSecDiag[1]);
                    fileContent = fileContent + ". "+labNull.replaceNull((String) usrSecDiag[2]);
                    fileContent = fileContent + ". "+labNull.replaceNull((String) usrSecDiag[3])+"</td>";

                }else{
                    fileContent = fileContent + "<td>"+usrSecDiag[0].toString()+". "+usrSecDiag[1].toString()+". "+usrSecDiag[2].toString()+". "+usrSecDiag[3].toString()+". "+usrSecDiag[4].toString()+". "+usrSecDiag[5].toString()+"</td>";
                }*/    
                //fileContent = fileContent + "</tr>";
            default:
                fileContent = fileContent + "<td>"+functionBeingTested.toString().toUpperCase()+" not recognized by this API"+"</td>";                    
            }
        }    
        fileContent = fileContent + "</table>";        
        out.println(fileContent);

        csvPathName = csvPathName.replace(".txt", ".html");
        File file = new File(csvPathName);
        FileWriter fileWriter = new FileWriter(file);
        if (file.exists()){ file.delete();} 
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();   

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
