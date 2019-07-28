/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.Platform;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPHttp;
import LabPLANET.utilities.LPNulls;
import LabPLANET.utilities.LPPlatform;
import functionalJava.testingScripts.LPTestingOutFormat;
import databases.Rdbms;
import functionalJava.testingScripts.TestingAssert;
import functionalJava.testingScripts.TestingAssertSummary;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class DBActions extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {
        request=LPHttp.requestPreparation(request);
        response=LPHttp.responsePreparation(response);

        String language = LPFrontEnd.setLanguage(request); 

        Object[][] dataSample2D = new Object[1][6];
        Object[] dataSample2Din1D = new Object[0];
        
        String csvFileName = "dbActions.txt"; 
        TestingAssertSummary tstAssertSummary = new TestingAssertSummary();

        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
        String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;
        
        Object[][] csvFileContent = LPArray.convertCSVinArray(csvPathName, csvFileSeparator); 
                
        try (PrintWriter out = response.getWriter()) {
if (1==1)            {
            out.println("Before connection to DB");
            }            
            String fileContent = LPTestingOutFormat.getHtmlStyleHeader(this.getClass().getSimpleName());
            HashMap<String, Object> csvHeaderTags = LPTestingOutFormat.getCSVHeader(LPArray.convertCSVinArray(csvPathName, "="));
            if (csvHeaderTags.containsKey(LPPlatform.LAB_FALSE)){
                fileContent=fileContent+"There are missing tags in the file header: "+csvHeaderTags.get(LPPlatform.LAB_FALSE);                        
                out.println(fileContent); 
                return;
            }            
if (1==1)            {
            out.println("Before connection to DB");
            }
            if (Rdbms.getRdbms().startRdbms(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW)==null){fileContent=fileContent+"Connection to the database not established";out.println(fileContent);return;}
            if (!Rdbms.getRdbms().startRdbms(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW)){fileContent=fileContent+"Connection to the database not established";out.println(fileContent);return;}
if (1==1)            {
            out.println("connected to DB");
            }
                
            Integer numEvaluationArguments = Integer.valueOf(csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_NUM_EVALUATION_ARGUMENTS).toString());   
            Integer numHeaderLines = Integer.valueOf(csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_NUM_HEADER_LINES_TAG_NAME).toString());   
            //numEvaluationArguments=numEvaluationArguments+1;
            String table1Header = csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_TABLE_NAME_TAG_NAME+"1").toString();               
            String fileContentTable1 = LPTestingOutFormat.createTableWithHeader(table1Header, numEvaluationArguments);

            Integer iLines =numHeaderLines; 
            for (iLines=iLines;iLines<csvFileContent.length;iLines++){
                tstAssertSummary.increaseTotalTests();
                TestingAssert tstAssert = new TestingAssert(csvFileContent[iLines], numEvaluationArguments);
                
                Integer lineNumCols = csvFileContent[0].length-1;
                String actionName = null;
                if (lineNumCols>=numEvaluationArguments)
                    {actionName=LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments]);}
                String schemaPrefix = null;
                if (lineNumCols>=numEvaluationArguments+1)
                    schemaPrefix = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+1]);
                String tableName = null;
                if (lineNumCols>=numEvaluationArguments+2)
                    tableName = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+2]);
                String[] fieldName = null;
                if (lineNumCols>=numEvaluationArguments+3)                
                    fieldName = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+3]);
                String[] fieldValue = null;
                if (lineNumCols>=numEvaluationArguments+4)
                     fieldValue = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+4]);
                String[] fieldsToRetrieve = null;
                if (lineNumCols>=numEvaluationArguments+5)
                     fieldsToRetrieve = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+5]);
                String[] setFieldName = null;
                if (lineNumCols>=numEvaluationArguments+6)
                     setFieldName = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+6]);
                String[] setFieldValue = null;
                if (lineNumCols>=numEvaluationArguments+7)
                    {setFieldValue=LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+7]);}
                String[] orderBy = null;
                if (lineNumCols>=numEvaluationArguments+8)
                    {orderBy=LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+8]);}
                String[] groupBy = null;
                if (lineNumCols>=numEvaluationArguments+9)
                    {groupBy=LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+9]);}
                
                Object[] fieldValues = LPArray.convertStringWithDataTypeToObjectArray(fieldValue);
                if ( (fieldName!=null) && (fieldValue!=null) ){
                    for (int iFields=0; iFields<fieldName.length; iFields++){
                        if (LPPlatform.isEncryptedField(schemaPrefix, "sample", fieldName[iFields])){                
                            HashMap<String, String> hm = LPPlatform.encryptEncryptableFieldsAddBoth(fieldName[iFields], fieldValues[iFields].toString());
                            fieldName[iFields]= hm.keySet().iterator().next();    
                            if ( hm.get(fieldName[iFields]).length()!=fieldValues[iFields].toString().length()){
                                String newWhereFieldValues = hm.get(fieldName[iFields]);
                                fieldValues[iFields]=newWhereFieldValues;
                            }
                        }
                    }                                    
                }     
                Object[] setFieldValues = null;
                if (setFieldValue!=null){
                    setFieldValues = LPArray.convertStringWithDataTypeToObjectArray(setFieldValue);}
                fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddField(iLines.toString())+LPTestingOutFormat.rowAddField(actionName)
                        +LPTestingOutFormat.rowAddField(schemaPrefix)+LPTestingOutFormat.rowAddField(tableName)
                        +LPTestingOutFormat.rowAddField(Arrays.toString(fieldName))+LPTestingOutFormat.rowAddField(Arrays.toString(fieldValue))
                        +LPTestingOutFormat.rowAddField(Arrays.toString(fieldsToRetrieve))
                        +LPTestingOutFormat.rowAddField(Arrays.toString(setFieldName))+LPTestingOutFormat.rowAddField(Arrays.toString(setFieldValue))
                        +LPTestingOutFormat.rowAddField(Arrays.toString(orderBy))+LPTestingOutFormat.rowAddField(Arrays.toString(groupBy));

                Object[] allFunctionsBeingTested = new Object[0];                
                allFunctionsBeingTested = LPArray.addValueToArray1D(allFunctionsBeingTested, "EXISTSRECORD");
                allFunctionsBeingTested = LPArray.addValueToArray1D(allFunctionsBeingTested, "INSERT");
                allFunctionsBeingTested = LPArray.addValueToArray1D(allFunctionsBeingTested, "GETRECORDFIELDSBYFILTER");
                allFunctionsBeingTested = LPArray.addValueToArray1D(allFunctionsBeingTested, "UPDATE");

//                fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
//                        new Object[]{iLines, result, ruleType, values, separator, listName});
                switch (actionName.toUpperCase()){
                    case "EXISTSRECORD":   
                        Object[] exRec =  Rdbms.existsRecord(schemaPrefix, tableName, fieldName, fieldValues);
                        dataSample2D = LPArray.array1dTo2d(exRec, exRec.length);
                        break;
                    case "INSERT":                    
                        Object[] insRec = Rdbms.insertRecordInTable(schemaPrefix, tableName, fieldName, fieldValues);  
                        dataSample2D = LPArray.array1dTo2d(insRec, insRec.length);
                        break;
                    case "GETRECORDFIELDSBYFILTER":              
                        if (orderBy!=null && orderBy.length>0){
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
                
                if (dataSample2D[0].length==0){
                    fileContentTable1 = fileContentTable1 +LPTestingOutFormat.rowAddField("No content in the array dataSample2D returned for function");                     
                }else{
                    if ( ("GETRECORDFIELDSBYFILTER".equalsIgnoreCase(actionName)) && (!LPPlatform.LAB_FALSE.equalsIgnoreCase(dataSample2D[0][0].toString())) ){
                        if (numEvaluationArguments==0){                    
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddField(Arrays.toString(dataSample2Din1D));                     
                        }
                        if (numEvaluationArguments>0){                    
                            Object[] evaluate = tstAssert.evaluate(numEvaluationArguments, tstAssertSummary, dataSample2Din1D);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(evaluate);                        
                        }
                        //fileContent = fileContent +LPTestingOutFormat.rowAddField(Arrays.toString(dataSample2Din1D));
                    }
                    if ( (!"GETRECORDFIELDSBYFILTER".equalsIgnoreCase(actionName)) && (!LPPlatform.LAB_FALSE.equalsIgnoreCase(dataSample2D[0][0].toString())) ){                        
                        String dataSampleFldOut = "";
                        for (int iFields=0; iFields<dataSample2D[0].length;iFields++){
                            dataSampleFldOut=dataSampleFldOut+LPNulls.replaceNull((String) dataSample2D[0][iFields])+ ". ";
                        }
                        if (numEvaluationArguments==0){                    
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddField(dataSampleFldOut);
                        }
                        if (numEvaluationArguments>0){                    
                            Object[] evaluate = tstAssert.evaluate(numEvaluationArguments, tstAssertSummary, new Object[]{dataSampleFldOut});
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(evaluate);                        
                        }
                        //fileContent = fileContent +LPTestingOutFormat.rowAddField(dataSampleFldOut);
                    }
                }
                fileContentTable1=fileContentTable1+LPTestingOutFormat.rowEnd();                                                
            }                          
            tstAssertSummary.notifyResults();
            fileContentTable1 = fileContentTable1 +LPTestingOutFormat.tableEnd();
            String fileContentSummary = LPTestingOutFormat.createSummaryTable(tstAssertSummary);
            fileContent=fileContent+fileContentSummary+fileContentTable1;
            fileContent=fileContent+LPTestingOutFormat.bodyEnd()+LPTestingOutFormat.htmlEnd();
            out.println(fileContent);            
            LPTestingOutFormat.createLogFile(csvPathName, fileContent);
            Rdbms.closeRdbms();
            tstAssertSummary=null; 
        }
        catch(IOException error){
            Rdbms.closeRdbms();
            tstAssertSummary=null; 
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
