/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing.functionalConfig;

import databases.Rdbms;
import functionalJava.materialSpec.ConfigSpecStructure;
import functionalJava.unitsOfMeasurement.UnitsOfMeasurement;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class UnitsOfMeasurementConversion extends HttpServlet {

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

        
        String userName=null;

        boolean isConnected = false;

        if (Rdbms.getRdbms().startRdbms("labplanet", "avecesllegaelmomento")==null){out.println("Connection to the database not established");return;}

        Integer numTesting = 20;
        Integer inumTesting = 0;
        Object[][] configSpecTestingArray = new Object[numTesting][6];
  
        if (inumTesting<numTesting){
            String schemaPrefix="config";            BigDecimal currentValue=new BigDecimal(11);
            String currentUOM="GR";                  String newUOM=null;                
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String schemaPrefix="config";            BigDecimal currentValue=new BigDecimal(11);
            String currentUOM=null;                  String newUOM="GR";                
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String schemaPrefix="config";           BigDecimal currentValue=new BigDecimal(11);
            String currentUOM="GR";                  String newUOM="GR";                
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String schemaPrefix="config";           BigDecimal currentValue=new BigDecimal(11);
            String currentUOM="GR";                  String newUOM="KG";                
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String schemaPrefix="config";          BigDecimal currentValue=new BigDecimal(11);
            String currentUOM="G";                   String newUOM="L";                
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String schemaPrefix="config";            BigDecimal currentValue=new BigDecimal(11);
            String currentUOM="PCT-MASS_VOLUME-5C";  String newUOM="G";                
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String schemaPrefix="config";            BigDecimal currentValue=new BigDecimal(11);
            String currentUOM="PCT-MASS_VOLUME-5C";  String newUOM="MG-N_L";                
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }        
        if (inumTesting<numTesting){
            String schemaPrefix="config";           BigDecimal currentValue=new BigDecimal(11);
            String currentUOM="KG";                  String newUOM="G";
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }       
        if (inumTesting<numTesting){
            String schemaPrefix="config";         BigDecimal currentValue=new BigDecimal(11);
            String currentUOM="G";                  String newUOM="KG";
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }          
        if (inumTesting<numTesting){
            String schemaPrefix="config";        BigDecimal currentValue=new BigDecimal(1);
            String currentUOM="K";                  String newUOM="DEG-C";
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }  
        if (inumTesting<numTesting){
            String schemaPrefix="config";        BigDecimal currentValue=new BigDecimal(1);
            String currentUOM="K";                  String newUOM="DEG-N";
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }    
        if (inumTesting<numTesting){
            String schemaPrefix="config";            BigDecimal currentValue=new BigDecimal(1);
            String currentUOM="SECOND";                  String newUOM="DAY";
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }
        if (inumTesting<numTesting){
            String schemaPrefix="oil-pl1-config";         BigDecimal currentValue=new BigDecimal(1);
            String currentUOM="SECOND";                  String newUOM="DAY";
            configSpecTestingArray[inumTesting][0]=schemaPrefix;            configSpecTestingArray[inumTesting][1]=currentValue;
            configSpecTestingArray[inumTesting][2]=currentUOM;              configSpecTestingArray[inumTesting][3]=newUOM;
            inumTesting++;
        }           
        /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Servlet TestingUnitConversion</title>");            
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Servlet TestingUnitConversion at " + request.getContextPath() + "</h1>");
        out.println("</body>");
        out.println("</html>");                
        out.println("<table>");
        out.println("<th>Test#</th><th>Schema Name</th><th>Current Value</th><th>Current UOM</th><th>New UOM</th><th>New Value</th><th>Evaluation</th>");        
        
        for (Integer i=0;i<configSpecTestingArray.length;i++){
            //if (configSpecTestingArray[i][2]==null && configSpecTestingArray[i][3]==null){
            out.println("<tr>");

            userName=null;                
            String schemaPrefix=null;
            BigDecimal currentValue=BigDecimal.ZERO;    
            String currentUOM=null;
            String newUOM=null;    
            
            Object[][] dataSample = null;

            if (configSpecTestingArray[i][0]!=null){schemaPrefix = (String) configSpecTestingArray[i][0];}
            if (configSpecTestingArray[i][1]!=null){currentValue = (BigDecimal) configSpecTestingArray[i][1];}
            if (configSpecTestingArray[i][2]!=null){currentUOM = (String) configSpecTestingArray[i][2];}
            if (configSpecTestingArray[i][3]!=null){newUOM = (String) configSpecTestingArray[i][3];}

            out.println("<td>"+i+"</td><td>"+schemaPrefix+"</td><td>"+currentValue+"</td><td>"+currentUOM+"</td><td><b>"+newUOM+"</b></td>");

            UnitsOfMeasurement UOM = new UnitsOfMeasurement();
            
            Object[] convDiagnoses = UOM.convertValue(schemaPrefix, currentValue, currentUOM, newUOM);
            if ("LABPLANET_FALSE".equalsIgnoreCase(convDiagnoses[0].toString())) {
                 out.println("<td>"+convDiagnoses[0].toString()+": "+convDiagnoses[3].toString()+"</td>");                
            }else{
                 
                 out.println("<td>"+convDiagnoses[0].toString()+":The new value is "+convDiagnoses[1].toString()+"</td>");                

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
