/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.functionalData;

import databases.Rdbms;
import _functionalJava.project.DataProject;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPNulls;
import functionalJava.testingScripts.LPTestingOutFormat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
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

            if (!LPFrontEnd.servletStablishDBConection(request, response)){return;} 
            
            String csvFileName = "dataProjectStructure.txt"; 
            String csvFileSeparator=";";
            String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName; 
 
            Object[][] dataSample2D = new Object[0][0];
        
            Integer numTesting = 1;
            Integer inumTesting = 0;
            Object[][] configSpecTestingArray = new Object[numTesting][6];
            String userName="1"; 
            String userRole="oil1plant_analyst";
            
            configSpecTestingArray = LPArray.convertCSVinArray(csvPathName, csvFileSeparator);

            String fileContent="";
            fileContent = LPTestingOutFormat.getHtmlStyleHeader(this.getServletName());

            DataProject prj = new DataProject("project");
            
            for (Integer j=0;j<configSpecTestingArray[0].length;j++){
                fileContent = fileContent + "<th>"+configSpecTestingArray[0][j]+"</th>";
            }            

            for (Integer i=1;i<configSpecTestingArray.length;i++){
                fileContent = fileContent + LPTestingOutFormat.rowStart();
                String[] fieldName=null;    
                Object[] fieldValue=null;
                String schemaPrefix=null;
                Integer sampleId=null;
                userName=null;                
                String actionName="<<<";
                Object[] dataProject = null;

                if (configSpecTestingArray[i][1]!=null){schemaPrefix = (String) configSpecTestingArray[i][1];}
                if (configSpecTestingArray[i][2]!=null){actionName = (String) configSpecTestingArray[i][2];}

                fileContent = fileContent + LPTestingOutFormat.fieldStart()+i+"</td><td>"+schemaPrefix+"</td><td>"+actionName+LPTestingOutFormat.fieldEnd();

                switch (actionName.toUpperCase()){
                    case "CREATEPROJECT":
                        String projectTemplate=null;
                        Integer projectTemplateVersion=null;
                        String[] sampleTemplateInfo = configSpecTestingArray[i][3].toString().split("\\|");
                        projectTemplate = sampleTemplateInfo[0];
                        projectTemplateVersion = Integer.parseInt(sampleTemplateInfo[1]);
                        if (configSpecTestingArray[i][3]!=null){fieldName = configSpecTestingArray[i][4].toString().split("\\|");}              
                        if (configSpecTestingArray[i][4]!=null){fieldValue = configSpecTestingArray[i][5].toString().split("\\|");} 
                        fieldValue = LPArray.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                        fileContent = fileContent + "<td>templateName, templateVersion, fieldNames, fieldValues</td>";
                        fileContent = fileContent + LPTestingOutFormat.fieldStart()+projectTemplate+", "+projectTemplateVersion.toString()+", "
                                +configSpecTestingArray[i][4].toString()+", "+configSpecTestingArray[i][5].toString()+LPTestingOutFormat.fieldEnd();                        
                        dataProject = prj.createProject(schemaPrefix, projectTemplate, projectTemplateVersion, fieldName, fieldValue, userName, userRole);
                        break;
                    case "LOGPROJECTSAMPLE":
                        String projectName=null;
                        projectTemplate=null;
                        projectTemplateVersion=null;
                        sampleTemplateInfo = configSpecTestingArray[i][3].toString().split("\\|");
                        projectTemplate = sampleTemplateInfo[0];
                        projectTemplateVersion = Integer.parseInt(sampleTemplateInfo[1]);
                        if (configSpecTestingArray[i][3]!=null){fieldName = configSpecTestingArray[i][4].toString().split("\\|");}              
                        if (configSpecTestingArray[i][4]!=null){fieldValue = configSpecTestingArray[i][5].toString().split("\\|");}   
                        fieldValue = LPArray.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                        if (configSpecTestingArray[i][5]!=null){projectName = (String) configSpecTestingArray[i][6];}                           
                        fileContent = fileContent + "<td>projectName, templateName, templateVersion, fieldNames, fieldValues</td>";
                        fileContent = fileContent + LPTestingOutFormat.fieldStart()+projectName+", "+projectTemplate+", "+projectTemplateVersion.toString()+", "
                                +configSpecTestingArray[i][4].toString()+", "+configSpecTestingArray[i][5].toString()+LPTestingOutFormat.fieldEnd();                        
                        dataProject = prj.logProjectSample(schemaPrefix, projectTemplate, projectTemplateVersion, fieldName, fieldValue, userName, userRole, projectName, null);
                        break;   
                    case "SAMPLEANALYSISADD":
                        if (configSpecTestingArray[i][3]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][3]);}
                        if (configSpecTestingArray[i][4]!=null){userName = (String) configSpecTestingArray[i][4];}
                        if (configSpecTestingArray[i][5]!=null){fieldName = configSpecTestingArray[i][5].toString().split("\\|");}              
                        if (configSpecTestingArray[i][6]!=null){fieldValue = configSpecTestingArray[i][6].toString().split("\\|");}
                        fieldValue = LPArray.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                        fieldValue = LPArray.convertStringWithDataTypeToObjectArray(configSpecTestingArray[i][6].toString().split("\\|"));
                        fileContent = fileContent + "<td>sampleId, userName, fieldNames, fieldValues</td>";
                        if (sampleId==null){sampleId=-9;}
                        fileContent = fileContent + LPTestingOutFormat.fieldStart()+sampleId.toString()+", "+userName+", "
                            +configSpecTestingArray[i][5].toString()+", "+configSpecTestingArray[i][6].toString()+LPTestingOutFormat.fieldEnd();                             
                        dataProject = prj.sampleAnalysisAddtoSample(schemaPrefix, userName, sampleId, fieldName, fieldValue, userRole);
                        break;                           
                    default:                
                        break;
                }
                if (actionName.equalsIgnoreCase("GETSAMPLEINFO")){
                    fileContent = fileContent + LPTestingOutFormat.fieldStart()+dataSample2D[0][0].toString();
                    fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][1]);
                    fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][2]);
                    fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][3])+LPTestingOutFormat.fieldEnd();

                }else{
                    if (dataProject!=null){
                        fileContent = fileContent + LPTestingOutFormat.fieldStart()+dataProject[0].toString()+". "+dataProject[1].toString()+". "+dataProject[2].toString()+". "+dataProject[3].toString()+". "+dataProject[4].toString()+". "+dataProject[5].toString()+LPTestingOutFormat.fieldEnd();}
                }    
                fileContent = fileContent + LPTestingOutFormat.rowEnd();
            }
            fileContent = fileContent + "</table>";        
            out.println(fileContent);

            csvPathName = csvPathName.replace(".txt", ".html");
            File file = new File(csvPathName);
            FileWriter fileWriter = new FileWriter(file);
            Files.deleteIfExists(file.toPath());
            fileWriter.write(fileContent);
            fileWriter.flush();
            fileWriter.close();   

            Rdbms.closeRdbms();

            }   catch (IOException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);   

            }        
    }




    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        try{
        processRequest(request, response);
        }catch(ServletException|IOException e){
            LPFrontEnd.servletReturnResponseError(request, response, e.getMessage(), new Object[]{}, null);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        try{
        processRequest(request, response);
        }catch(ServletException|IOException e){
            LPFrontEnd.servletReturnResponseError(request, response, e.getMessage(), new Object[]{}, null);
        }
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
