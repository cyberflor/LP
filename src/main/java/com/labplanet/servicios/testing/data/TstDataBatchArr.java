/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.data;

import LabPLANET.utilities.LPNulls;
import LabPLANET.utilities.LPPlatform;
import functionalJava.testingScripts.LPTestingOutFormat;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import databases.Rdbms;
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

/**
 *
 * @author Administrator
 */
public class TstDataBatchArr extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {
        response = LPTestingOutFormat.responsePreparation(response);        
        String csvFileName = "tstDataBatchArray.txt"; 
        String fileContent = "";                          
        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
        String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;

        if (Rdbms.getRdbms().startRdbms(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW)==null){
            fileContent = fileContent + LPTestingOutFormat.MSG_DB_CON_ERROR;
            LPTestingOutFormat.createLogFile(csvPathName, fileContent);
            return;
        }           
  
        try (PrintWriter out = response.getWriter()) {
            
            Object[][] dataSample2D = new Object[1][6];
            Integer numTesting = 1;
            
            String[][] configSpecTestingArray = LPArray.convertCSVinArray(csvPathName, csvFileSeparator);                        
     
            fileContent = LPTestingOutFormat.getHtmlStyleHeader(this.getServletName());
            for (Integer j=0;j<configSpecTestingArray[0].length;j++){
                fileContent = fileContent + LPTestingOutFormat.headerAddFields(configSpecTestingArray[0]);
            }            

            for (Integer i=1;i<configSpecTestingArray.length;i++){
                out.println("Line "+i.toString());

                fileContent = fileContent + "<tr>";                
//                String userName=null;                
                String schemaPrefix=null;
                String tableName=null;
                String[] fieldName=null;                    
                String[] fieldValue=null;
                String[] setFieldName=null;                    
                String[] setFieldValue=null;
                String[] orderBy=null;                    
                String[] groupBy=null;
                String[] fieldsToRetrieve=null;   
                String actionName="";                     
                Object[] dataSample2Din1D = new Object[0];

                if (configSpecTestingArray[i][1]!=null){actionName = (String) configSpecTestingArray[i][1];}
                if (configSpecTestingArray[i][2]!=null){schemaPrefix = (String) configSpecTestingArray[i][2];}
                if (configSpecTestingArray[i][3]!=null){tableName = (String) configSpecTestingArray[i][3];}
                if (configSpecTestingArray[i][4]!=null){fieldName = configSpecTestingArray[i][4].split("\\|");}else{fieldName = new String[0];}              
                if (configSpecTestingArray[i][5]!=null){fieldValue = configSpecTestingArray[i][5].split("\\|");}else{fieldValue = new String[0];}
                if (configSpecTestingArray[i][6]!=null){fieldsToRetrieve = configSpecTestingArray[i][6].split("\\|");}else{fieldsToRetrieve = new String[0];}                  
                if (configSpecTestingArray[i][7]!=null){setFieldName = configSpecTestingArray[i][7].split("\\|");}else{setFieldName = new String[0];}              
                if (configSpecTestingArray[i][8]!=null){setFieldValue = configSpecTestingArray[i][8].split("\\|");}else{setFieldValue = new String[0];}
                if (configSpecTestingArray[i][9]!=null){orderBy = configSpecTestingArray[i][9].split("\\|");}else{orderBy = new String[0];}
                if (configSpecTestingArray[i][10]!=null){groupBy = configSpecTestingArray[i][10].split("\\|");}else{groupBy = new String[0];}
                
/*                String[] whereFieldsNameArr = new String[]{"status in|"};
                Object[] whereFieldsValueArr = null;
                Object[] recEncrypted = labPlat.encryptString("RECEIVED");
                whereFieldsValueArr=labArr.addValueToArray1D(whereFieldsValueArr, "RECEIVED|"+recEncrypted[1]);                
*/                
                Object[] fieldValues = LPArray.convertStringWithDataTypeToObjectArray(fieldValue);
                if ( (fieldName!=null) && (fieldValue!=null) ){
                    //whereFieldsNameArr=labArr.addValueToArray1D(whereFieldsNameArr, whereFieldsName.split("\\|"));
                    //whereFieldsValueArr = labArr.addValueToArray1D(whereFieldsValueArr, labArr.convertStringWithDataTypeToObjectArray(whereFieldsValue.split("\\|")));                                                              
                }                         
                //Object[] fieldValues = labArr.convertStringWithDataTypeToObjectArray(fieldValue);
                Object[] setFieldValues = LPArray.convertStringWithDataTypeToObjectArray(setFieldValue);
                
                fileContent = fileContent + "<td>"+i+LPTestingOutFormat.fieldEnd()+LPTestingOutFormat.fieldStart()
                        +actionName+LPTestingOutFormat.fieldEnd()+LPTestingOutFormat.fieldStart()+schemaPrefix+LPTestingOutFormat.fieldEnd()+LPTestingOutFormat.fieldStart()+tableName
                        +LPTestingOutFormat.fieldEnd()+LPTestingOutFormat.fieldStart()+Arrays.toString(fieldName)+LPTestingOutFormat.fieldEnd()+LPTestingOutFormat.fieldStart()+"<b>"+Arrays.toString(fieldValue)+"</b>"+LPTestingOutFormat.fieldEnd()+LPTestingOutFormat.fieldStart()+Arrays.toString(fieldsToRetrieve)
                        +LPTestingOutFormat.fieldEnd()+LPTestingOutFormat.fieldStart()+Arrays.toString(setFieldName)+LPTestingOutFormat.fieldEnd()+LPTestingOutFormat.fieldStart()+"<b>"+Arrays.toString(setFieldValue)+"</b>"+"</td>"
                        +LPTestingOutFormat.fieldEnd()+LPTestingOutFormat.fieldStart()+Arrays.toString(orderBy)+LPTestingOutFormat.fieldEnd()+LPTestingOutFormat.fieldStart()+"<b>"+Arrays.toString(groupBy)+"</b></td>";
                
                Object[] allFunctionsBeingTested = new Object[0];                
                allFunctionsBeingTested = LPArray.addValueToArray1D(allFunctionsBeingTested, "CREATEBATCHARRAY");
                allFunctionsBeingTested = LPArray.addValueToArray1D(allFunctionsBeingTested, "INSERT");
                allFunctionsBeingTested = LPArray.addValueToArray1D(allFunctionsBeingTested, "GETRECORDFIELDSBYFILTER");
                allFunctionsBeingTested = LPArray.addValueToArray1D(allFunctionsBeingTested, "UPDATE");
                
                switch (actionName.toUpperCase()){
                    case "CREATEBATCHARRAY":   
                        //batch.dbCreateBatchArray();
                        Object[] exRec =  Rdbms.existsRecord(schemaPrefix, tableName, fieldName, fieldValues);
                        dataSample2D = LPArray.array1dTo2d(exRec, exRec.length);
                        break;
                    case "INSERT":                    
                        Object[] insRec = Rdbms.insertRecordInTable(schemaPrefix, tableName, fieldName, fieldValues);  
                        dataSample2D = LPArray.array1dTo2d(insRec, insRec.length);
                        break;
                    case "GETRECORDFIELDSBYFILTER":              
                        if (orderBy.length>0){
                            dataSample2D = Rdbms.getRecordFieldsByFilter(schemaPrefix, tableName, fieldName, fieldValues, fieldsToRetrieve, orderBy);
                        }else{
                            dataSample2D = Rdbms.getRecordFieldsByFilter(schemaPrefix, tableName, fieldName, fieldValues, fieldsToRetrieve);
                        }
                        if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(dataSample2D[0][0].toString())){
                            dataSample2Din1D =  LPArray.array2dTo1d(dataSample2D);
                        }    
                        break;
                    case "UPDATE":                    
                        Object[] updRec = Rdbms.updateRecordFieldsByFilter(schemaPrefix, tableName, setFieldName, setFieldValues, fieldName, fieldValues);  
                        dataSample2D = LPArray.array1dTo2d(updRec, updRec.length);
                        break;                        
                    default:
                        String errorCode = "ERROR: FUNCTION NOT RECOGNIZED";
                        Object[] errorDetail = new Object [1];
                        errorDetail[0]="The function <*1*> is not one of the declared ones therefore nothing can be performed for it. Functions are: <*2*>";
                        errorDetail = LPArray.addValueToArray1D(errorDetail, actionName);
                        errorDetail = LPArray.addValueToArray1D(errorDetail, Arrays.toString(allFunctionsBeingTested));
                        Object[] trapErrorMessage = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetail);            
                        dataSample2D = LPArray.array1dTo2d(trapErrorMessage, trapErrorMessage.length);
                        break;
                }        
                if (dataSample2D[0].length==0){fileContent = fileContent + "<td>No content in the array dataSample2D returned for function "+actionName;}
                if (dataSample2D[0].length>0){fileContent = fileContent + "<td>"+dataSample2D[0][0].toString();}
                if (dataSample2D[0].length>1){fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][1]);}
                if (dataSample2D[0].length>2){fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][2]);}
                if (dataSample2D[0].length>3){fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][3]);}
                if (dataSample2D[0].length>4){fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][4]);}                
                if (dataSample2D[0].length>5){fileContent = fileContent + ". "+LPNulls.replaceNull((String) dataSample2D[0][5]);}
                if ( ("GETRECORDFIELDSBYFILTER".equalsIgnoreCase(actionName)) && (!LPPlatform.LAB_FALSE.equalsIgnoreCase(dataSample2D[0][0].toString())) ){
                    fileContent = fileContent + LPTestingOutFormat.fieldEnd()+LPTestingOutFormat.fieldStart()+Arrays.toString(dataSample2Din1D);
                }

                fileContent = fileContent + LPTestingOutFormat.fieldEnd();
                fileContent = fileContent +LPTestingOutFormat.rowEnd();
            }
            fileContent = fileContent + LPTestingOutFormat.tableEnd();        
            out.println(fileContent);

            csvPathName = csvPathName.replace(".txt", ".html");
            LPTestingOutFormat.createLogFile(csvPathName, fileContent);
            Rdbms.closeRdbms();
            }   catch (IOException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);   
                fileContent = fileContent + "</table>";        
                out.println(fileContent);        
                Rdbms.closeRdbms();
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
