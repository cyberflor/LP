/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.data;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LPNulls;
import LabPLANET.utilities.LabPLANETPlatform;
import databases.Rdbms;
import functionalJava.analysis.UserMethod;
import functionalJava.batch.DataBatch;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import testing.functionalData.testingFileContentSections;

/**
 *
 * @author Administrator
 */
public class tstDataBatchArray extends HttpServlet {
    String classVersion = "0.1";
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
        
        String fileContent = "";        
        Rdbms rdbm = new Rdbms();          
        DataBatch batch = new DataBatch();
        try (PrintWriter out = response.getWriter()) {
            
            response.setContentType("text/html;charset=UTF-8");
            UserMethod um = new UserMethod();
      
            boolean isConnected = false;
            isConnected = rdbm.startRdbms("labplanet", "LabPlanet");

            String csvFileName = "tstDataBatchArray.txt"; String csvFileSeparator=";";
            String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName; 
 
         
            Object[][] dataSample2D = new Object[1][6];
        
            Integer numTesting = 1;
            Integer inumTesting = 0;
            Object[][] configSpecTestingArray = new Object[numTesting][9];
            LabPLANETArray labArr = new LabPLANETArray();
            
            configSpecTestingArray = labArr.convertCSVinArray(csvPathName, csvFileSeparator);                        
     
            fileContent = testingFileContentSections.getHtmlStyleHeader(this.getServletName());

            for (Integer j=0;j<configSpecTestingArray[0].length;j++){
                fileContent = fileContent + "<th>"+configSpecTestingArray[0][j]+"</th>";
            }            

            for (Integer i=1;i<configSpecTestingArray.length;i++){
                //if (configSpecTestingArray[i][2]==null && configSpecTestingArray[i][3]==null){
                
                out.println("Line "+i.toString());

                fileContent = fileContent + "<tr>";                
                String userName=null;                
                String schemaPrefix=null;
                String tableName=null;
                String[] fieldName=null;                    String[] fieldValue=null;
                String[] setFieldName=null;                    String[] setFieldValue=null;
                String[] orderBy=null;                    String[] groupBy=null;
                String[] fieldsToRetrieve=null;   
                String functionBeingTested="";                     
                LabPLANETPlatform LabPlat = new LabPLANETPlatform();
                Object[] dataSample2Din1D = new Object[0];

                if (configSpecTestingArray[i][1]!=null){functionBeingTested = (String) configSpecTestingArray[i][1];}
                if (configSpecTestingArray[i][2]!=null){schemaPrefix = (String) configSpecTestingArray[i][2];}
                if (configSpecTestingArray[i][3]!=null){tableName = (String) configSpecTestingArray[i][3];}
                if (configSpecTestingArray[i][4]!=null){fieldName = (String[]) configSpecTestingArray[i][4].toString().split("\\|");}else{fieldName = new String[0];}              
                if (configSpecTestingArray[i][5]!=null){fieldValue = (String[]) configSpecTestingArray[i][5].toString().split("\\|");}else{fieldValue = new String[0];}
                if (configSpecTestingArray[i][6]!=null){fieldsToRetrieve = (String[]) configSpecTestingArray[i][6].toString().split("\\|");}else{fieldsToRetrieve = new String[0];}                  
                if (configSpecTestingArray[i][7]!=null){setFieldName = (String[]) configSpecTestingArray[i][7].toString().split("\\|");}else{setFieldName = new String[0];}              
                if (configSpecTestingArray[i][8]!=null){setFieldValue = (String[]) configSpecTestingArray[i][8].toString().split("\\|");}else{setFieldValue = new String[0];}
                if (configSpecTestingArray[i][9]!=null){orderBy = (String[]) configSpecTestingArray[i][9].toString().split("\\|");}else{orderBy = new String[0];}
                if (configSpecTestingArray[i][10]!=null){groupBy = (String[]) configSpecTestingArray[i][10].toString().split("\\|");}else{groupBy = new String[0];}
                
                LabPLANETPlatform labPlat = new LabPLANETPlatform();                   
/*                String[] whereFieldsNameArr = new String[]{"status in|"};
                Object[] whereFieldsValueArr = null;
                Object[] recEncrypted = labPlat.encryptString("RECEIVED");
                whereFieldsValueArr=labArr.addValueToArray1D(whereFieldsValueArr, "RECEIVED|"+recEncrypted[1]);                
*/                
                Object[] fieldValues = labArr.convertStringWithDataTypeToObjectArray(fieldValue);
                if ( (fieldName!=null) && (fieldValue!=null) ){
                    //whereFieldsNameArr=labArr.addValueToArray1D(whereFieldsNameArr, whereFieldsName.split("\\|"));
                    //whereFieldsValueArr = labArr.addValueToArray1D(whereFieldsValueArr, labArr.convertStringWithDataTypeToObjectArray(whereFieldsValue.split("\\|")));                                                              
                }                         
                //Object[] fieldValues = labArr.convertStringWithDataTypeToObjectArray(fieldValue);
                Object[] setFieldValues = labArr.convertStringWithDataTypeToObjectArray(setFieldValue);
                
                fileContent = fileContent + "<td>"+i+"</td><td>"+functionBeingTested+"</td><td>"+schemaPrefix+"</td><td>"+tableName
                        +"</td><td>"+Arrays.toString(fieldName)+"</td><td><b>"+Arrays.toString(fieldValue)+"</b></td><td>"+Arrays.toString(fieldsToRetrieve)
                        +"</td><td>"+Arrays.toString(setFieldName)+"</td><td><b>"+Arrays.toString(setFieldValue)+"</b></td>"
                        +"</td><td>"+Arrays.toString(orderBy)+"</td><td><b>"+Arrays.toString(groupBy)+"</b></td>";
                
                Object[] allFunctionsBeingTested = new Object[0];                
                allFunctionsBeingTested = labArr.addValueToArray1D(allFunctionsBeingTested, "CREATEBATCHARRAY");
                allFunctionsBeingTested = labArr.addValueToArray1D(allFunctionsBeingTested, "INSERT");
                allFunctionsBeingTested = labArr.addValueToArray1D(allFunctionsBeingTested, "GETRECORDFIELDSBYFILTER");
                allFunctionsBeingTested = labArr.addValueToArray1D(allFunctionsBeingTested, "UPDATE");
                
                switch (functionBeingTested.toUpperCase()){
                    case "CREATEBATCHARRAY":   
                        //batch.dbCreateBatchArray(rdbm, );
                        Object[] exRec =  rdbm.existsRecord(rdbm, schemaPrefix, tableName, fieldName, fieldValues);
                        dataSample2D = labArr.array1dTo2d(exRec, exRec.length);
                        break;
                    case "INSERT":                    
                        Object[] insRec = rdbm.insertRecordInTable(rdbm, schemaPrefix, tableName, fieldName, fieldValues);  
                        dataSample2D = labArr.array1dTo2d(insRec, insRec.length);
                        break;
                    case "GETRECORDFIELDSBYFILTER":              
                        if (orderBy.length>0){
                            dataSample2D = rdbm.getRecordFieldsByFilter(rdbm, schemaPrefix, tableName, fieldName, fieldValues, fieldsToRetrieve, orderBy);
                        }else{
                            dataSample2D = rdbm.getRecordFieldsByFilter(rdbm, schemaPrefix, tableName, fieldName, fieldValues, fieldsToRetrieve);
                        }
                        if (!"LABPLANET_FALSE".equalsIgnoreCase(dataSample2D[0][0].toString())){
                            dataSample2Din1D =  labArr.array2dTo1d(dataSample2D);
                        }    
                        break;
                    case "UPDATE":                    
                        Object[] updRec = rdbm.updateRecordFieldsByFilter(rdbm, schemaPrefix, tableName, setFieldName, setFieldValues, fieldName, fieldValues);  
                        dataSample2D = labArr.array1dTo2d(updRec, updRec.length);
                        break;                        
                    default:
                        String errorCode = "ERROR: FUNCTION NOT RECOGNIZED";
                        Object[] errorDetail = new Object [1];
                        errorDetail[0]="The function <*1*> is not one of the declared ones therefore nothing can be performed for it. Functions are: <*2*>";
                        errorDetail = labArr.addValueToArray1D(errorDetail, functionBeingTested);
                        errorDetail = labArr.addValueToArray1D(errorDetail, Arrays.toString(allFunctionsBeingTested));
                        Object[] trapErrorMessage = LabPLANETPlatform.trapErrorMessage(rdbm, "LABPLANET_FALSE", classVersion, errorCode, errorDetail);            
                        dataSample2D = labArr.array1dTo2d(trapErrorMessage, trapErrorMessage.length);
                        break;
                }        
                LPNulls labNull = new LPNulls();
                if (dataSample2D[0].length==0){fileContent = fileContent + "<td>No content in the array dataSample2D returned for function "+functionBeingTested;}
                if (dataSample2D[0].length>0){fileContent = fileContent + "<td>"+dataSample2D[0][0].toString();}
                if (dataSample2D[0].length>1){fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][1]);}
                if (dataSample2D[0].length>2){fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][2]);}
                if (dataSample2D[0].length>3){fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][3]);}
                if (dataSample2D[0].length>4){fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][4]);}                
                if (dataSample2D[0].length>5){fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][5]);}
                if ( ("GETRECORDFIELDSBYFILTER".equalsIgnoreCase(functionBeingTested)) && (!"LABPLANET_FALSE".equalsIgnoreCase(dataSample2D[0][0].toString())) ){
                    fileContent = fileContent + "</td><td>"+Arrays.toString(dataSample2Din1D);
                }

                fileContent = fileContent + "</td>";
                fileContent = fileContent + "</tr>";
            }
            fileContent = fileContent + "</table>";        
            out.println(fileContent);

            csvPathName = csvPathName.replace(".txt", ".html");
            File file = new File(csvPathName);
                try (FileWriter fileWriter = new FileWriter(file)) {
                    if (file.exists()){ file.delete();}
                    fileWriter.write(fileContent);
                    fileWriter.flush();
                } 
            rdbm.closeRdbms();
            }   catch (IOException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);   
                fileContent = fileContent + "</table>";        
                out.println(fileContent);        
                rdbm.closeRdbms();
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
