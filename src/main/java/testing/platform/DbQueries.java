/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.platform;

import databases.Rdbms;
import functionalJava.materialSpec.ConfigSpecStructure;
import LabPLANET.utilities.LabPLANETArray;
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
public class DbQueries extends HttpServlet {

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
    try (PrintWriter out = response.getWriter()) {
        response.setContentType("text/html;charset=UTF-8");
        ConfigSpecStructure configSpec = new ConfigSpecStructure();
        
        String userName=null;

        if (Rdbms.getRdbms().startRdbms("labplanet", "avecesllegaelmomento")==null){out.println("Connection to the database not established");return;}

        Integer numTesting = 20;
        Integer inumTesting = 0;
        Object[][] configSpecTestingArray = new Object[numTesting][6];
        
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="config";
            String tableName="analysis_method";
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="config";
            String tableName="analysis_method";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "analysis");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }

        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "analysis");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="zzzzzzanalysis_method_paramszzzzzz";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "analysis");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
       if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "zzzzzanalysis");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
       if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "analysis");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "zzzzzanalysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }

        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "analysis");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "pH");
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "method_name");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "pH method");
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "method_version");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, 2);
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "method_name is null");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "method_name is not null");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "method_name is not null");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "");
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "analysis");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }        
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "method_name is not null");            
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "analysis");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "pH");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }        
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "method_name like ");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "%met%");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }  
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "method_name like ");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "%met%");            
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "analysis");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "LOD");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }        
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "method_name like '%met%'");
            //fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "%met%");
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "analysis");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "LOD");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }        
        if (inumTesting<numTesting){
            String[] fieldName= new String[0];
            Object[] fieldValue=new Object[0];
            String[] fieldToRetrieve= new String[0];
            String schemaPrefix="oil-pl1-config";
            String tableName="analysis_method_params";
            fieldName = LabPLANETArray.addValueToArray1D(fieldName, "method_name in|");
            fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "pH|LOD");
            //fieldName = LabPLANETArray.addValueToArray1D(fieldName, "analysis");
            //fieldValue = LabPLANETArray.addValueToArray1D(fieldValue, "LOD");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");
            fieldToRetrieve = LabPLANETArray.addValueToArray1D(fieldToRetrieve, "analysis");            
            configSpecTestingArray[inumTesting][0]=schemaPrefix;
            configSpecTestingArray[inumTesting][1]=tableName;
            configSpecTestingArray[inumTesting][2]=userName;
            configSpecTestingArray[inumTesting][3]=fieldName;
            configSpecTestingArray[inumTesting][4]=fieldValue;
            configSpecTestingArray[inumTesting][5]=fieldToRetrieve;
            inumTesting++;
        }        
            
        /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet TestingDbQueries</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet TestingDbQueries at " + request.getContextPath() + "</h1>");
        out.println("</body>");
        out.println("</html>");                
        out.println("<table>");
        out.println("<th>Test#</th><th>Schema Prefix</th><th>Function Being Tested</th><th>Field Name</th><th>Field Value</th><th>Evaluation</th>");        
        
        for (Integer i=0;i<configSpecTestingArray.length;i++){
            //if (configSpecTestingArray[i][2]==null && configSpecTestingArray[i][3]==null){
            out.println("<tr>");

            userName=null;                
            String schemaPrefix=null;
            String tableName=null;
            String[] fieldName=null;    
            Object[] fieldValue=null;
            String[] fieldsToRetrieve=null;    
            
            Object[][] dataSample = null;

            if (configSpecTestingArray[i][0]!=null){schemaPrefix = (String) configSpecTestingArray[i][0];}
            if (configSpecTestingArray[i][1]!=null){tableName = (String) configSpecTestingArray[i][1];}
            if (configSpecTestingArray[i][2]!=null){userName = (String) configSpecTestingArray[i][2];}
            if (configSpecTestingArray[i][3]!=null){fieldName = (String[]) configSpecTestingArray[i][3];}
            if (configSpecTestingArray[i][4]!=null){fieldValue = (Object[]) configSpecTestingArray[i][4];}
            if (configSpecTestingArray[i][5]!=null){fieldsToRetrieve = (String[]) configSpecTestingArray[i][5];}

            out.println("<td>"+i+"</td><td>"+schemaPrefix+"</td><td>"+tableName+"</td><td>"+Arrays.toString(fieldName)+"</td><td><b>"+Arrays.toString(fieldValue)+"</b></td>");

            
            dataSample = Rdbms.getRecordFieldsByFilter(schemaPrefix, tableName, fieldName, fieldValue, fieldsToRetrieve);
            
            if ((dataSample[0][3].toString().equalsIgnoreCase("FALSE"))){
                 out.println("<td>"+dataSample[0][3].toString()+": "+dataSample[0][5].toString()+"</td>");                
            }else{
                 
                 out.println("<td>"+"Records returned: " + dataSample.length+"</td>");  
            }        
            
            //out.println("<td>"+dataSample[0].toString()+". "+dataSample[1].toString()+". "+dataSample[2].toString()+". "+dataSample[3].toString()+". "+dataSample[4].toString()+". "+dataSample[5].toString()+"</td>");
            out.println("</tr>");
        }

        out.println("</table>");        
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
