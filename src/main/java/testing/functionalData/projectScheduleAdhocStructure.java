/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.functionalData;

import databases.Rdbms;
import functionalJava.analysis.UserMethod;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LPNulls;
import _functionalJava.project.DataProjectScheduleAdhoc;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class projectScheduleAdhocStructure extends HttpServlet {

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
            
            response.setContentType("text/html;charset=UTF-8");
            UserMethod um = new UserMethod();

            Rdbms rdbm = new Rdbms();            
            boolean isConnected = false;
            isConnected = rdbm.startRdbms("labplanet", "LabPlanet");
            if (!isConnected){out.println("Connection to the database not established");return;}
            
            String csvFileName = "dataProjectScheduleAdhocStructure.txt"; String csvFileSeparator=";";
            String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName; 
            //String csvPathName = "c:\\testingRepository\\"+csvFileName; 
             
            Object[][] dataSample2D = new Object[0][0];
        
            Integer numTesting = 1;
            Integer inumTesting = 0;
            Object[][] configSpecTestingArray = new Object[numTesting][6];
            LabPLANETArray labArr = new LabPLANETArray();
            String userName="1"; 
            String userRole="oil1plant_analyst";
            
            configSpecTestingArray = labArr.convertCSVinArray(csvPathName, csvFileSeparator);

            String fileContent="";
            fileContent = testingFileContentSections.getHtmlStyleHeader(this.getServletName());
            
            DataProjectScheduleAdhoc prjSched = new DataProjectScheduleAdhoc();
            
            for (Integer j=0;j<configSpecTestingArray[0].length;j++){
                fileContent = fileContent + "<th>"+configSpecTestingArray[0][j]+"</th>";
            }            

            for (Integer i=1;i<configSpecTestingArray.length;i++){
                //if (configSpecTestingArray[i][2]==null && configSpecTestingArray[i][3]==null){
                fileContent = fileContent + "<tr>";
                String[] fieldName=null;    
                Object[] fieldValue=null;
                String schemaPrefix=null;
                Integer sampleId=null;
                userName=null;                
                String functionBeingTested=null;
                Object[] dataProject = new Object[6];

                if (configSpecTestingArray[i][1]!=null){schemaPrefix = (String) configSpecTestingArray[i][1];}
                if (configSpecTestingArray[i][2]!=null){functionBeingTested = (String) configSpecTestingArray[i][2];}

                fileContent = fileContent + "<td>"+i+"</td><td>"+schemaPrefix+"</td><td>"+functionBeingTested+"</td>";

                switch (functionBeingTested.toUpperCase()){
                    case "ADDRECURSIVESCHEDULEPOINT":
                        String projectName=null;
                        Integer projSchedId=null;
                        String[] rules;
                        if (configSpecTestingArray[i][3]!=null){projectName = (String) configSpecTestingArray[i][3].toString();}              
                        if (configSpecTestingArray[i][4]!=null){projSchedId = (Integer) Integer.parseInt(configSpecTestingArray[i][4].toString());}  
                        if (configSpecTestingArray[i][5]!=null){fieldName = (String[]) configSpecTestingArray[i][5].toString().split("\\|");} 
                        if (configSpecTestingArray[i][6]!=null){fieldValue = (String[]) configSpecTestingArray[i][6].toString().split("\\|");} 
                        Object[] fieldValues = labArr.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                        //fileContent = fileContent + "<td>projectName, projectScheduleId, fieldNames, fieldValues</td>";
                        fileContent = fileContent + "<td>"+projectName+"</td><td>"+projSchedId.toString()+"</td><td>"
                                +configSpecTestingArray[i][5].toString()+"</td><td>"+configSpecTestingArray[i][6].toString()+"</td>";                        
                        try {
                            dataProject = prjSched.addRecursiveSchedulePoint(rdbm, schemaPrefix, projectName, projSchedId, fieldName, fieldValues);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "IMPORTHOLIDAYSCALENDAR":
                        projectName="";
                        projSchedId=-1;
                        String calendarName="";
                        if (configSpecTestingArray[i][3]!=null){projectName = (String) configSpecTestingArray[i][3].toString();}              
                        if (configSpecTestingArray[i][4]!=null){projSchedId = (Integer) Integer.parseInt(configSpecTestingArray[i][4].toString());}  
                        if (configSpecTestingArray[i][5]!=null){calendarName = (String) configSpecTestingArray[i][5];} 
                        //if (configSpecTestingArray[i][6]!=null){fieldValue = (String[]) configSpecTestingArray[i][6].toString().split("\\|");} 
                        
                        //fileContent = fileContent + "<td>projectName, projectScheduleId, fieldNames, fieldValues</td>";
                        fileContent = fileContent + "<td>"+projectName+"</td><td>"+projSchedId.toString()+"</td><td>"
                                +configSpecTestingArray[i][5].toString()+"</td><td>"+configSpecTestingArray[i][6].toString()+"</td>";                        
                        try {
                            dataProject = prjSched.importHolidaysCalendarSchedule(rdbm, schemaPrefix, calendarName, projectName, projSchedId);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    default:    
                        dataProject = new String[6];
                        dataProject[0]=functionBeingTested;
                        dataProject[1]="Action not recognizned or not implemented yet";
                        dataProject[2]="";dataProject[3]="";dataProject[4]="";dataProject[5]="";
                        break;
                }
                LPNulls labNull = new LPNulls();
                if (functionBeingTested.equalsIgnoreCase("GETSAMPLEINFO")){
                    fileContent = fileContent + "<td>"+dataSample2D[0][0].toString();
                    fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][1]);
                    fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][2]);
                    fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][3])+"</td>";

                }else{
                    fileContent = fileContent + "<td>"+dataProject[3].toString()+": "+dataProject[0].toString()+". "+dataProject[1].toString()+". "+dataProject[2].toString()+". "+dataProject[3].toString()+". "+dataProject[4].toString()+". "+dataProject[5].toString()+"</td>";
                }    
                fileContent = fileContent + "</tr>";
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

            rdbm.closeRdbms();

            }   catch (SQLException|IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);   

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
