/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.functionalData;

import databases.Rdbms;
import functionalJava.analysis.UserMethod;
import _functionalJava.project.DataProject;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LPNulls;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
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
public class projectStructure extends HttpServlet {

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

        if (Rdbms.getRdbms().startRdbms("labplanet", "avecesllegaelmomento")==null){out.println("Connection to the database not established");return;}

            String csvFileName = "dataProjectStructure.txt"; String csvFileSeparator=";";
            String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName; 
 
            Object[][] dataSample2D = new Object[0][0];
        
            Integer numTesting = 1;
            Integer inumTesting = 0;
            Object[][] configSpecTestingArray = new Object[numTesting][6];
            String userName="1"; 
            String userRole="oil1plant_analyst";
            
            configSpecTestingArray = LabPLANETArray.convertCSVinArray(csvPathName, csvFileSeparator);

            String fileContent="";
            fileContent = testingFileContentSections.getHtmlStyleHeader(this.getServletName());

            DataProject prj = new DataProject("project");
            
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
                Object[] dataProject = null;

                if (configSpecTestingArray[i][1]!=null){schemaPrefix = (String) configSpecTestingArray[i][1];}
                if (configSpecTestingArray[i][2]!=null){functionBeingTested = (String) configSpecTestingArray[i][2];}

                fileContent = fileContent + "<td>"+i+"</td><td>"+schemaPrefix+"</td><td>"+functionBeingTested+"</td>";

                switch (functionBeingTested.toUpperCase()){
                    case "CREATEPROJECT":
                        String projectTemplate=null;
                        Integer projectTemplateVersion=null;
                        String[] sampleTemplateInfo = configSpecTestingArray[i][3].toString().split("\\|");
                        projectTemplate = sampleTemplateInfo[0];
                        projectTemplateVersion = Integer.parseInt(sampleTemplateInfo[1]);
                        if (configSpecTestingArray[i][3]!=null){fieldName = (String[]) configSpecTestingArray[i][4].toString().split("\\|");}              
                        if (configSpecTestingArray[i][4]!=null){fieldValue = (Object[]) configSpecTestingArray[i][5].toString().split("\\|");} 
                        fieldValue = LabPLANETArray.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                        fileContent = fileContent + "<td>templateName, templateVersion, fieldNames, fieldValues</td>";
                        fileContent = fileContent + "<td>"+projectTemplate+", "+projectTemplateVersion.toString()+", "
                                +configSpecTestingArray[i][4].toString()+", "+configSpecTestingArray[i][5].toString()+"</td>";                        
                        try {
                            dataProject = prj.createProject(schemaPrefix, projectTemplate, projectTemplateVersion, fieldName, fieldValue, userName, userRole);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "LOGPROJECTSAMPLE":
                        String projectName=null;
                        projectTemplate=null;
                        projectTemplateVersion=null;
                        sampleTemplateInfo = configSpecTestingArray[i][3].toString().split("\\|");
                        projectTemplate = sampleTemplateInfo[0];
                        projectTemplateVersion = Integer.parseInt(sampleTemplateInfo[1]);
                        if (configSpecTestingArray[i][3]!=null){fieldName = (String[]) configSpecTestingArray[i][4].toString().split("\\|");}              
                        if (configSpecTestingArray[i][4]!=null){fieldValue = (Object[]) configSpecTestingArray[i][5].toString().split("\\|");}   
                        fieldValue = LabPLANETArray.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                        if (configSpecTestingArray[i][5]!=null){projectName = (String) configSpecTestingArray[i][6];}                           
                        fileContent = fileContent + "<td>projectName, templateName, templateVersion, fieldNames, fieldValues</td>";
                        fileContent = fileContent + "<td>"+projectName+", "+projectTemplate+", "+projectTemplateVersion.toString()+", "
                                +configSpecTestingArray[i][4].toString()+", "+configSpecTestingArray[i][5].toString()+"</td>";                        
                        try {
                            dataProject = prj.logProjectSample(schemaPrefix, projectTemplate, projectTemplateVersion, fieldName, fieldValue, userName, userRole, projectName, null);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                        }
                        break;   
                    case "SAMPLEANALYSISADD":
                        if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                        if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                        if (configSpecTestingArray[i][5]!=null){fieldName = (String[]) configSpecTestingArray[i][5].toString().split("\\|");}              
                        if (configSpecTestingArray[i][6]!=null){fieldValue = (Object[]) configSpecTestingArray[i][6].toString().split("\\|");}
                        fieldValue = LabPLANETArray.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                        try {                        
                            fieldValue = LabPLANETArray.convertStringWithDataTypeToObjectArray(configSpecTestingArray[i][6].toString().split("\\|"));
                            fileContent = fileContent + "<td>sampleId, userName, fieldNames, fieldValues</td>";
                            fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName+", "
                                +configSpecTestingArray[i][5].toString()+", "+configSpecTestingArray[i][6].toString()+"</td>";                            
                            dataProject = prj.sampleAnalysisAddtoSample(schemaPrefix, userName, sampleId, fieldName, fieldValue, userRole);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                        }
                        break;                           
                    default:                
                        break;
                }
                if (functionBeingTested.equalsIgnoreCase("GETSAMPLEINFO")){
                    fileContent = fileContent + "<td>"+dataSample2D[0][0].toString();
                    fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][1]);
                    fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][2]);
                    fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][3])+"</td>";

                }else{
                    fileContent = fileContent + "<td>"+dataProject[0].toString()+". "+dataProject[1].toString()+". "+dataProject[2].toString()+". "+dataProject[3].toString()+". "+dataProject[4].toString()+". "+dataProject[5].toString()+"</td>";
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

            Rdbms.closeRdbms();

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
