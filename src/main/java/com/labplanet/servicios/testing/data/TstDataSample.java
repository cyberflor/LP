/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.data;

import LabPLANET.utilities.LPPlatform;
import functionalJava.testingScripts.LPTestingOutFormat;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPNulls;
import databases.Rdbms;
import functionalJava.ChangeOfCustody.ChangeOfCustody;
import functionalJava.sampleStructure.DataSample;
import functionalJava.testingScripts.TestingAssert;
import functionalJava.testingScripts.TestingAssertSummary;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class TstDataSample extends HttpServlet {

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
                
        String csvFileName = "dataSampleStructure.txt";      
        Object[][] dataSample2D = new Object[1][6];
        Object[] dataSample = new Object[6];
        DataSample smp = new DataSample("");   
        Integer appSessionId = null;
        
        TestingAssertSummary tstAssertSummary = new TestingAssertSummary();

        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
        String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;
        
        Object[][] csvFileContent = LPArray.convertCSVinArray(csvPathName, csvFileSeparator); 
                
        try (PrintWriter out = response.getWriter()) {
            String fileContent = LPTestingOutFormat.getHtmlStyleHeader(this.getClass().getSimpleName());
            HashMap<String, Object> csvHeaderTags = LPTestingOutFormat.getCSVHeader(LPArray.convertCSVinArray(csvPathName, "="));
            if (csvHeaderTags.containsKey(LPPlatform.LAB_FALSE)){
                fileContent=fileContent+"There are missing tags in the file header: "+csvHeaderTags.get(LPPlatform.LAB_FALSE);                        
                out.println(fileContent); 
                return;
            }            
            if (Rdbms.getRdbms().startRdbms(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW)==null){fileContent=fileContent+"Connection to the database not established";return;}
                
            Integer numEvaluationArguments = Integer.valueOf(csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_NUM_EVALUATION_ARGUMENTS).toString());   
            Integer numHeaderLines = Integer.valueOf(csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_NUM_HEADER_LINES_TAG_NAME).toString());   
            //numEvaluationArguments=numEvaluationArguments+1;
            String table1Header = csvHeaderTags.get(LPTestingOutFormat.FILEHEADER_TABLE_NAME_TAG_NAME+"1").toString();               
            String fileContentTable1 = LPTestingOutFormat.createTableWithHeader(table1Header, numEvaluationArguments);
            
            for (Integer iLines=numHeaderLines;iLines<csvFileContent.length;iLines++){
                tstAssertSummary.increaseTotalTests();
                TestingAssert tstAssert = new TestingAssert(csvFileContent[iLines], numEvaluationArguments);
                
                Integer lineNumCols = csvFileContent[0].length-1;                                
                String schemaPrefix = null;
                if (lineNumCols>=numEvaluationArguments)
                    {schemaPrefix=LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments]);}
                String userName = null;
                if (lineNumCols>=numEvaluationArguments+1)
                    userName = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+1]);
                String userRole = null;
                if (lineNumCols>=numEvaluationArguments+2)
                    userRole = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+2]);
                String actionName = null;
                if (lineNumCols>=numEvaluationArguments+3)                
                    actionName = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+3]);

                fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddField(iLines.toString())+LPTestingOutFormat.rowAddField(schemaPrefix)
                        +LPTestingOutFormat.rowAddField(userName)+LPTestingOutFormat.rowAddField(userRole)
                        +LPTestingOutFormat.rowAddField(actionName);

                Object[] actionEnabledForRole = LPPlatform.procUserRoleActionEnabled(schemaPrefix, userRole, actionName);
                if (LPPlatform.LAB_FALSE.equalsIgnoreCase(actionEnabledForRole[0].toString())){
                    //StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
                    if ("GETSAMPLEINFO".equalsIgnoreCase(actionName)){                
                            dataSample2D[0][0] = actionEnabledForRole[0];
                            dataSample2D[0][1] = actionEnabledForRole[1]; dataSample2D[0][2] = actionEnabledForRole[2]; 
                            dataSample2D[0][3] = actionEnabledForRole[3]; dataSample2D[0][4] = actionEnabledForRole[4]; 
                            dataSample2D[0][5] = actionEnabledForRole[5]; 
                    }else{        
                            dataSample[0] = actionEnabledForRole[0]; dataSample[1] = actionEnabledForRole[1]; dataSample[2] = actionEnabledForRole[2];
                            dataSample[3] = actionEnabledForRole[3]; dataSample[4] = actionEnabledForRole[4]; dataSample[5] = actionEnabledForRole[5]; 
                    }                      
                }else{                
                    switch (actionName.toUpperCase()){
                        case "LOGSAMPLE":                            
                            String sampleTemplate=null;
                            Integer sampleTemplateVersion=null;
                            String[] sampleTemplateInfo = new String[0];
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleTemplateInfo = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+4]);
                            sampleTemplate = sampleTemplateInfo[0];
                            sampleTemplateVersion = Integer.parseInt(sampleTemplateInfo[1]);
                            String[] fieldName = null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                fieldName = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+5]);
                            String[] fieldValue = null;
                            if (lineNumCols>=numEvaluationArguments+6)                
                                fieldValue = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+6]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"templateName, templateVersion, fieldNames, fieldValues", 
                                    sampleTemplate+", "+sampleTemplateVersion.toString()+", "+Arrays.toString(fieldName)+", "+Arrays.toString(fieldValue)});                              
                            dataSample = smp.logSample(schemaPrefix, sampleTemplate, sampleTemplateVersion, fieldName, fieldValue, userName, userRole, null, null);
                            break;
                        case "RECEIVESAMPLE":  
                            Integer sampleId = null;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId = LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"sampleId, receiver", 
                                    LPNulls.replaceNull(sampleId).toString()+", "+userName});                              
                            dataSample = smp.sampleReception(schemaPrefix, userName, sampleId, userRole, null);
                            break;       
                        case "CHANGESAMPLINGDATE":
                            sampleId = null;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId = LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            Date newDate = null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                newDate = LPTestingOutFormat.csvExtractFieldValueDate(csvFileContent[iLines][numEvaluationArguments+5]);                            
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"sampleId, userName, newDate", 
                                    LPNulls.replaceNull(sampleId).toString()+", "+userName+", "+LPNulls.replaceNull(newDate).toString()});                              
                            dataSample = smp.changeSamplingDate(schemaPrefix, userName, sampleId, newDate, userRole);
                            break;       
                        case "SAMPLINGCOMMENTADD":
                            sampleId = null;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId = LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            String comment=null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                comment = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"sampleId, userName, comment", 
                                    LPNulls.replaceNull(sampleId).toString()+", "+userName+", "+comment});                              
                            dataSample = smp.sampleReceptionCommentAdd(schemaPrefix, userName, sampleId, comment, userRole);
                            break;       
                        case "SAMPLINGCOMMENTREMOVE":
                            sampleId = null;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId = LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            comment=null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                comment = LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"sampleId, userName, comment", 
                                    LPNulls.replaceNull(sampleId).toString()+", "+userName+", "+comment});                              
                            dataSample = smp.sampleReceptionCommentRemove(schemaPrefix, userName, sampleId, comment, userRole);
                            break;       
                        case "INCUBATIONSTART":
                            sampleId = null;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId = LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"sampleId, userName", 
                                    LPNulls.replaceNull(sampleId).toString()+", "+userName});                              
                            dataSample = smp.setSampleStartIncubationDateTime(schemaPrefix, userName, sampleId, userRole);
                            break;       
                        case "INCUBATIONEND":
                            sampleId = null;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId = LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"sampleId, userName", 
                                    LPNulls.replaceNull(sampleId).toString()+", "+userName});                              
                            dataSample = smp.setSampleEndIncubationDateTime(schemaPrefix, userName, sampleId, userRole);
                            break;       
                        case "SAMPLEANALYSISADD":
                            sampleId=null;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId = LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            fieldName=null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                fieldName = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+5]);
                            String[] fieldValueStrArr=null;
                            if (lineNumCols>=numEvaluationArguments+6)
                                 fieldValueStrArr = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+6]);
                            Object[] fieldValueObjArr=LPArray.convertStringWithDataTypeToObjectArray(fieldValueStrArr);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"sampleId, userName, fieldNames, fieldValues", 
                                    LPNulls.replaceNull(sampleId).toString()+", "+userName+", "+Arrays.toString(fieldName)+", "+Arrays.toString(fieldValueObjArr)});                              
                            dataSample = smp.sampleAnalysisAddtoSample(schemaPrefix, userName, sampleId, fieldName, fieldValueObjArr, userRole);
                            break;              
                        case "ENTERRESULT":
                            Integer resultId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            String rawValueResult=null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                rawValueResult=LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"resultId, userName, fieldNames, rawValueResult", 
                                    resultId.toString()+", "+userName+", "+rawValueResult});                              
                            dataSample = smp.sampleAnalysisResultEntry(schemaPrefix, userName, resultId, rawValueResult, userRole);
                            break;  
                        case "REVIEWRESULT":
                            Integer objectId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                objectId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            String objectLevel="";
                            if (lineNumCols>=numEvaluationArguments+5)                
                                objectLevel=LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"userName, fieldNames, objectLevel, ObjectId", 
                                    userName+", "+objectLevel+", "+objectId.toString()});                              
                            sampleId = null; Integer testId = null; resultId = null;
                            if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId=objectId;}
                            if (objectLevel.equalsIgnoreCase("TEST")){testId=objectId;}
                            if (objectLevel.equalsIgnoreCase("RESULT")){resultId=objectId;}
                            dataSample = smp.sampleResultReview(schemaPrefix, userName, sampleId, testId, resultId, userRole);
                            break;                                     
                        case "CANCELRESULT":
                            objectId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                objectId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            objectLevel="";
                            if (lineNumCols>=numEvaluationArguments+5)                
                                objectLevel=LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"userName, fieldNames, objectLevel, ObjectId", 
                                    userName+", "+objectLevel+", "+objectId.toString()});                              
                            sampleId = null; testId = null; resultId = null;
                            if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                            if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                            if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                            dataSample = smp.sampleAnalysisResultCancel(schemaPrefix, userName, sampleId, testId, resultId, userRole);
                            break;                            
                        case "UNCANCELRESULT": 
                            objectId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                objectId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            objectLevel="";
                            if (lineNumCols>=numEvaluationArguments+5)                
                                objectLevel=LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"userName, fieldNames, objectLevel, ObjectId", 
                                    userName+", "+objectLevel+", "+objectId.toString()});                              
                            sampleId = null; testId = null; resultId = null;

                            if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                            if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                            if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                            dataSample = smp.sampleAnalysisResultUnCancel(schemaPrefix, userName, sampleId, testId, resultId, userRole);
                            break;       
                        case "TESTASSIGNMENT": 
                            testId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                testId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            String newAnalyst=null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                newAnalyst=LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"testId, userName, newAnalyst", 
                                    testId.toString()+", "+userName+", "+newAnalyst});                              
                            dataSample = smp.sampleAnalysisAssignAnalyst(schemaPrefix, userName, testId, newAnalyst, userRole);
                            break;   
                        case "GETSAMPLEINFO":
                            String schemaDataName = "data";
                            schemaDataName = LPPlatform.buildSchemaName(schemaPrefix, schemaDataName);                     
                            sampleId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            String[] fieldsToGet=null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                fieldsToGet=LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddField(Arrays.toString(fieldsToGet));
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddField(sampleId.toString());
                            dataSample2D = Rdbms.getRecordFieldsByFilter(schemaDataName, "sample", new String[]{"sample_id"}, new Object[]{sampleId}, fieldsToGet);
                            break;
                        case "ENTERRESULT_LOD":
                            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                            try {
                                try {
                                    engine.eval(new FileReader("C:\\home\\judas\\myResult.js"));
                                } catch (ScriptException ex) {
                                    Logger.getLogger(TstDataSample.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Invocable invocable = (Invocable) engine;
                                Object result;
                                result = invocable.invokeFunction("lossOnDrying", 10, 7);
                                System.out.println(result);
                                System.out.println(result.getClass());
                            } catch (FileNotFoundException | NoSuchMethodException | ScriptException e) {
                                return;
                            }
                            break;
                        case "COC_STARTCHANGE":
                            String custodianCandidate=null;
                            sampleId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            custodianCandidate = null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                custodianCandidate=LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"sampleId, custodianCandidate", 
                                    sampleId.toString()+", "+custodianCandidate});                              
                            ChangeOfCustody coc =  new ChangeOfCustody();
                            dataSample = coc.cocStartChange(schemaPrefix, "sample", "sample_id", sampleId, userName, 
                                    custodianCandidate, userRole, null);
                            break;
                        case "COC_CONFIRMCHANGE":
                            sampleId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            comment = null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                comment=LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"sampleId, comment", 
                                    sampleId.toString()+", "+comment});                                                          
                            coc =  new ChangeOfCustody();
                            dataSample = coc.cocConfirmedChange(schemaPrefix, "sample", "sample_id", sampleId, userName, 
                                    comment, userRole, null);
                            break;
                        case "COC_ABORTCHANGE":
                            sampleId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            comment = null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                comment=LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"sampleId, comment", 
                                    sampleId.toString()+", "+comment});                                                          
                            coc =  new ChangeOfCustody();
                            dataSample = coc.cocAbortedChange(schemaPrefix, "sample", "sample_id", sampleId, userName, 
                                    comment, userRole, null);
                            break;
                        case "RESULT_CHANGE_UOM":
                            resultId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                resultId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            String newUOM = null;
                            if (lineNumCols>=numEvaluationArguments+5)                
                                newUOM=LPTestingOutFormat.csvExtractFieldValueString(csvFileContent[iLines][numEvaluationArguments+5]);
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"resultId, newUOM", 
                                    resultId.toString()+", "+newUOM});                                  
                            dataSample = smp.sarChangeUom(schemaPrefix, resultId, newUOM, userName, userRole);
                            break;
                        case "LOGALIQUOT":
                            sampleId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                sampleId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            //sampleTemplate=null;
                            //sampleTemplateVersion=null;
                            //sampleTemplateInfo = configSpecTestingArray[i][6].toString().split("\\|");
                            //sampleTemplate = sampleTemplateInfo[0];
                            //sampleTemplateVersion = Integer.parseInt(sampleTemplateInfo[1]);
                            fieldName=null;
                            if (lineNumCols>=numEvaluationArguments+6)                
                                fieldName = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+6]);
                            fieldValueStrArr=null;
                            if (lineNumCols>=numEvaluationArguments+7)
                                 fieldValueStrArr = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+7]);
                            fieldValueObjArr=LPArray.convertStringWithDataTypeToObjectArray(fieldValueStrArr);
                            
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"sample_id, fieldNames, fieldValues", 
                                    sampleId.toString()+", "+Arrays.toString(fieldName)+", "+Arrays.toString(fieldValueStrArr)});                                                                                      
                            dataSample = smp.logSampleAliquot(schemaPrefix, sampleId, 
                                    // sampleTemplate, sampleTemplateVersion, 
                                    fieldName, fieldValueObjArr, userName, userRole, appSessionId);                                                                
                            break;                     
                        case "LOGSUBALIQUOT":
                            Integer aliquotId = 0;
                            if (lineNumCols>=numEvaluationArguments+4)                
                                aliquotId=LPTestingOutFormat.csvExtractFieldValueInteger(csvFileContent[iLines][numEvaluationArguments+4]);
                            //sampleTemplate=null;
                            //sampleTemplateVersion=null;
                            //sampleTemplateInfo = configSpecTestingArray[i][6].toString().split("\\|");
                            //sampleTemplate = sampleTemplateInfo[0];
                            //sampleTemplateVersion = Integer.parseInt(sampleTemplateInfo[1]);
                            fieldName=null;
                            if (lineNumCols>=numEvaluationArguments+6)                
                                fieldName = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+6]);
                            fieldValueStrArr=null;
                            if (lineNumCols>=numEvaluationArguments+7)
                                 fieldValueStrArr = LPTestingOutFormat.csvExtractFieldValueStringArr(csvFileContent[iLines][numEvaluationArguments+7]);
                            fieldValueObjArr=LPArray.convertStringWithDataTypeToObjectArray(fieldValueStrArr);
                            
                            fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(
                                new Object[]{"aliquot_id, fieldNames, fieldValues", 
                                    aliquotId.toString()+", "+Arrays.toString(fieldName)+", "+Arrays.toString(fieldValueStrArr)});                                                                                                                  
                            dataSample = smp.logSampleSubAliquot(schemaPrefix, aliquotId, 
                                    // sampleTemplate, sampleTemplateVersion, 
                                    fieldName, fieldValueObjArr, userName, userRole, appSessionId);                                                                
                            break;                     
                        default:                       
                            dataSample[0] = "function "+actionName+" not recognized";
                            dataSample[1] = ""; dataSample[2] = ""; dataSample[3] = ""; dataSample[4] = ""; dataSample[5] = ""; 

                            break;
                    }        
                }                
/*                fileContentTable1 = fileContentTable1+LPTestingOutFormat.rowAddField(dataSample[0].toString()+". "
                        +dataSample[1].toString()+". "+dataSample[2].toString()+". "+dataSample[3].toString()+". "+dataSample[4].toString()
                        +". "+dataSample[dataSample.length-1].toString());

                if (actionName.equalsIgnoreCase("GETSAMPLEINFO")){
                    String value = "";
                    value = value + dataSample2D[0][0].toString();
                    value = value + ". "+LPNulls.replaceNull((String) dataSample2D[0][1]);
                    if (dataSample2D[0].length>2){
                        value = value + ". "+LPNulls.replaceNull((String) dataSample2D[0][2]);}
                    if (dataSample2D[0].length>3){
                        value = value + ". "+LPNulls.replaceNull((String) dataSample2D[0][3]);}
                    if (dataSample2D[0].length>4){
                        value = value + ". "+LPNulls.replaceNull((String) dataSample2D[0][4]);}                
                    if (dataSample2D[0].length>5){
                        value = value + ". "+LPNulls.replaceNull((String) dataSample2D[0][5]);}
                    fileContentTable1 = fileContentTable1 + LPTestingOutFormat.rowAddField(value);
                }else{
                    fileContentTable1 = fileContentTable1+LPTestingOutFormat.rowAddField(dataSample[0].toString()+". "
                            +dataSample[1].toString()+". "+dataSample[2].toString()+". "+dataSample[3].toString()+". "+dataSample[4].toString()
                            +". "+dataSample[dataSample.length-1].toString());
                }    
*/                
                if ("GETSAMPLEINFO".equalsIgnoreCase(actionName))  dataSample = LPArray.array2dTo1d(dataSample2D);

                if (numEvaluationArguments==0){                    
                    fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddField(Arrays.toString(dataSample));                     
                }
                if (numEvaluationArguments>0){                    
                    Object[] evaluate = tstAssert.evaluate(numEvaluationArguments, tstAssertSummary, dataSample);
                    fileContentTable1=fileContentTable1+LPTestingOutFormat.rowAddFields(evaluate);                        
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
        catch(IOException|IllegalArgumentException|InvocationTargetException error){
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
