/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontEnd.sampleAudit;

import labPLANET.utilities.LabPLANETArray;
import databases.Rdbms;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class treantTimeLine extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    @SuppressWarnings("empty-statement")
    private String GetSampleAudit(Rdbms rdbm, String schemaName, String tableName, Integer sn){
        String myJsonInString = "";
        
        LabPLANETArray labArr = new LabPLANETArray();
        
        String[] whereFieldNames = new String[0];
        whereFieldNames = labArr.addValueToArray1D(whereFieldNames, "sample_id");
        whereFieldNames = labArr.addValueToArray1D(whereFieldNames, "transaction_id is not null");

        Object[] whereFieldValues = new Object[0];
        whereFieldValues = labArr.addValueToArray1D(whereFieldValues, sn);

        String[] fieldsToRetrieve = new String[0];
        fieldsToRetrieve = labArr.addValueToArray1D(fieldsToRetrieve, "transaction_id");  
        fieldsToRetrieve = labArr.addValueToArray1D(fieldsToRetrieve, "audit_id"); 
        fieldsToRetrieve = labArr.addValueToArray1D(fieldsToRetrieve, "action_name");        
        fieldsToRetrieve = labArr.addValueToArray1D(fieldsToRetrieve, "sample_id");  
        fieldsToRetrieve = labArr.addValueToArray1D(fieldsToRetrieve, "test_id");  
        fieldsToRetrieve = labArr.addValueToArray1D(fieldsToRetrieve, "result_id");          

        String[] orderBy = new String[0];
        orderBy = labArr.addValueToArray1D(orderBy, "transaction_id");  
        orderBy = labArr.addValueToArray1D(orderBy, "audit_id");        
        
        Object[][] smpAuditRcd = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, whereFieldNames, whereFieldValues, fieldsToRetrieve, orderBy);
        
        myJsonInString = myJsonInString +" text: { name: \"Parent node\" },"
			+"children: ["; 
        
        Object lastSampleId = ""; Object lastTestId = ""; Object lastResultId = ""; Object lastTransactionId=0;
        Integer iRecords=0;
        while (iRecords<smpAuditRcd.length){
            
            Object transactionId = smpAuditRcd[iRecords][0];  Object auditId = smpAuditRcd[iRecords][1];  Object actionName = smpAuditRcd[iRecords][2];
            Object sampleId = smpAuditRcd[iRecords][3];       Object testId = smpAuditRcd[iRecords][4];   Object resultId = smpAuditRcd[iRecords][5];
            
            if (transactionId!=lastTransactionId){
            
                myJsonInString = myJsonInString +"{";
                myJsonInString = myJsonInString +"  HTMLclass: \"timeline main-date\",";
                myJsonInString = myJsonInString +"  text: { desc: \""+" "+"\"";
                    myJsonInString = myJsonInString +" , name: \""+transactionId.toString()+"\" },";            

                myJsonInString = myJsonInString +"  children: [";
                
                myJsonInString = myJsonInString +"{";
                myJsonInString = myJsonInString +" text: { name: \""+actionName.toString()+"\" }";

                lastSampleId = sampleId; lastTestId = testId; lastResultId = resultId; lastTransactionId = transactionId;
                iRecords++;
                transactionId = smpAuditRcd[iRecords][0];  auditId = smpAuditRcd[iRecords][1];  actionName = smpAuditRcd[iRecords][2];
                sampleId = smpAuditRcd[iRecords][3];       testId = smpAuditRcd[iRecords][4];   resultId = smpAuditRcd[iRecords][5];
                
                myJsonInString = myJsonInString +",  children: [";
                
                if (lastTransactionId!=transactionId){myJsonInString = myJsonInString +"]}"; iRecords--; }
                else{                

                    myJsonInString = myJsonInString +"{";
                    myJsonInString = myJsonInString +" text: { name: \""+auditId.toString()+"\"}";
                    myJsonInString = myJsonInString +"}";            
                    myJsonInString = myJsonInString +"            ] ";

                    myJsonInString = myJsonInString +"}";

                    myJsonInString = myJsonInString +"            ] ";
                    myJsonInString = myJsonInString +"},";
                }        
                lastSampleId = sampleId; lastTestId = testId; lastResultId = resultId; lastTransactionId = transactionId;
            
            }   
            iRecords++;
        }    
        myJsonInString = myJsonInString +"{";
        myJsonInString = myJsonInString +"}";

        myJsonInString = myJsonInString+"]";
        return myJsonInString;
    }
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            Rdbms rdbm = new Rdbms();            
            boolean isConnected = false;
            try {
                 isConnected = rdbm.startRdbms("labplanet", "LabPlanet");
            } catch (ClassNotFoundException|IllegalAccessException|InstantiationException|SQLException|NamingException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
            
            String schemaName = "\"oil-pl1-data-audit\"";
            String tableName = "sample";
            
            String csvFileName = "LabPLANETTimeLine.html"; String csvFileSeparator=";";
            String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\SourceCode\\treant-js\\"+csvFileName; 
            
            Scanner scanIn = new Scanner(new BufferedReader(new FileReader(csvPathName)));

            String myJsonInString = GetSampleAudit(rdbm, schemaName, tableName, 12);

            String fileContent = "";
            while (scanIn.hasNextLine()){
                String inputLine = scanIn.nextLine();
                fileContent=fileContent+inputLine.replace("##NodeStructure##", myJsonInString);
            }
            out.println(fileContent);
            
            try {
                rdbm.closeRdbms();
            } catch (SQLException ex) {
                Logger.getLogger(treantTimeLine.class.getName()).log(Level.SEVERE, null, ex);
            }
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
