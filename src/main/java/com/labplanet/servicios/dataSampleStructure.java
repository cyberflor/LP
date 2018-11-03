/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios;

import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETNullValue;
import LabPLANET.utilities.LabPLANETPlatform;
import databases.Rdbms;
import functionalJava.analysis.UserMethod;
import functionalJava.sampleStructure.DataSample;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.SQLException;
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
import testing.functionalData.testingFileContentSections;

/**
 *
 * @author Administrator
 */
public class dataSampleStructure extends HttpServlet {

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
        Rdbms rdbm = new Rdbms();           
        boolean isConnected = false;

        String fileContent = "";        
        try (PrintWriter out = response.getWriter()) {
            
            response.setContentType("text/html;charset=UTF-8");
            UserMethod um = new UserMethod();

            isConnected = rdbm.startRdbms("labplanet", "LabPlanet");

            String csvFileName = "dataSampleStructure.txt"; String csvFileSeparator=";";
            String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName; 
 
            Object[][] dataSample2D = new Object[1][6];
        
            Integer numTesting = 1;
            Integer inumTesting = 0;
            Object[][] configSpecTestingArray = new Object[numTesting][6];
            LabPLANETArray labArr = new LabPLANETArray();
            
            configSpecTestingArray = labArr.convertCSVinArray(csvPathName, csvFileSeparator);            
            
        fileContent = testingFileContentSections.getHtmlStyleHeader(this.getServletName());
            
        DataSample smp = new DataSample("");
            
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
            String functionBeingTested=null;
            Object[] dataSample = new Object[6];
            String userName=null; 
            String userRole=null;
            LabPLANETPlatform LabPlat = new LabPLANETPlatform();
            
            out.println("Line "+i.toString());

            if (configSpecTestingArray[i][1]!=null){schemaPrefix = (String) configSpecTestingArray[i][1];}
            if (configSpecTestingArray[i][2]!=null){userName = (String) configSpecTestingArray[i][2];}
            if (configSpecTestingArray[i][3]!=null){userRole = (String) configSpecTestingArray[i][3];}
            if (configSpecTestingArray[i][4]!=null){functionBeingTested = (String) configSpecTestingArray[i][4];}
                        
            fileContent = fileContent + "<td>"+i+"</td><td>"+schemaPrefix+"</td><td>"+userName+"</td><td>"+userRole+"</td><td>"+functionBeingTested+"</td>";
            Object[] actionEnabled = LabPlat.procActionEnabled(schemaPrefix, functionBeingTested);
            if ("LABPLANET_FALSE".equalsIgnoreCase(actionEnabled[0].toString())){
                if ("GETSAMPLEINFO".equalsIgnoreCase(functionBeingTested)){                
                        dataSample2D[0][0] = (String) actionEnabled[0];
                        dataSample2D[0][1] = actionEnabled[1]; dataSample2D[0][2] = actionEnabled[2]; 
                        dataSample2D[0][3] = actionEnabled[3]; dataSample2D[0][4] = actionEnabled[4]; 
                        dataSample2D[0][5] = actionEnabled[5]; 
                }else{        
                        dataSample[0] = (String) actionEnabled[0]; dataSample[1] = actionEnabled[1]; dataSample[2] = actionEnabled[2];
                        dataSample[3] = actionEnabled[3]; dataSample[4] = actionEnabled[4]; dataSample[5] = actionEnabled[5]; 
                }        
                //fileContent = fileContent + "<td>Action not allowed for the procedure "+schemaPrefix+"</td></tr>";
            }else{            

                Object[] actionEnabledForRole = LabPlat.procUserRoleActionEnabled(schemaPrefix, userRole, functionBeingTested);
                if ("LABPLANET_FALSE".equalsIgnoreCase(actionEnabledForRole[0].toString())){
                    LabPLANETPlatform labPlat = new LabPLANETPlatform();
                    //StackTraceElement[] elementsDev = Thread.currentThread().getStackTrace();
                    if ("GETSAMPLEINFO".equalsIgnoreCase(functionBeingTested)){                
                            dataSample2D[0][0] = (String) actionEnabledForRole[0];
                            dataSample2D[0][1] = actionEnabledForRole[1]; dataSample2D[0][2] = actionEnabledForRole[2]; 
                            dataSample2D[0][3] = actionEnabledForRole[3]; dataSample2D[0][4] = actionEnabledForRole[4]; 
                            dataSample2D[0][5] = actionEnabledForRole[5]; 
                    }else{        
                            dataSample[0] = (String) actionEnabledForRole[0]; dataSample[1] = actionEnabledForRole[1]; dataSample[2] = actionEnabledForRole[2];
                            dataSample[3] = actionEnabledForRole[3]; dataSample[4] = actionEnabledForRole[4]; dataSample[5] = actionEnabledForRole[5]; 
                    }                      
                }else{                
                    switch (functionBeingTested.toUpperCase()){
                        case "LOGSAMPLE":
                            String sampleTemplate=null;
                            Integer sampleTemplateVersion=null;
                            String[] sampleTemplateInfo = configSpecTestingArray[i][5].toString().split("\\|");
                            sampleTemplate = sampleTemplateInfo[0];
                            sampleTemplateVersion = Integer.parseInt(sampleTemplateInfo[1]);
                            if (configSpecTestingArray[i][6]!=null){
                                fieldName = (String[]) configSpecTestingArray[i][6].toString().split("\\|");                        
                            }              
                            if (configSpecTestingArray[i][7]!=null){
                                fieldValue = (Object[]) configSpecTestingArray[i][7].toString().split("\\|");
                                fieldValue = labArr.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                            }    
                            fileContent = fileContent + "<td>templateName, templateVersion, fieldNames, fieldValues</td>";
                            fileContent = fileContent + "<td>"+sampleTemplate+", "+sampleTemplateVersion.toString()+", ";
                            if (configSpecTestingArray[i][6]!=null)fileContent = fileContent + configSpecTestingArray[i][6].toString();
                            fileContent = fileContent +", ";
                            if (configSpecTestingArray[i][7]!=null)fileContent = fileContent + configSpecTestingArray[i][7].toString();
                            try {
                                dataSample = smp.logSample(rdbm, schemaPrefix, sampleTemplate, sampleTemplateVersion, fieldName, fieldValue, userName, userRole);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                        case "RECEIVESAMPLE":  
                            if (configSpecTestingArray[i][5]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            fileContent = fileContent + "<td>sampleId, receiver</td>";
                            fileContent = fileContent + "<td>"+sampleId.toString()+"</td>";
                            dataSample = smp.sampleReception(rdbm, schemaPrefix, userName, sampleId, userRole);
                            break;       
                        case "CHANGESAMPLINGDATE":
                            Date newDate=null;
                            if (configSpecTestingArray[i][5]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            if (configSpecTestingArray[i][6]!=null){newDate =  Date.valueOf((String) configSpecTestingArray[i][6]);}
                            fileContent = fileContent + "<td>sampleId, userName, newDate</td>";
                            fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName+newDate.toString()+"</td>";
                            dataSample = smp.changeSamplingDate(rdbm, schemaPrefix, userName, sampleId, newDate, userRole);
                            break;       
                        case "SAMPLINGCOMMENTADD":
                            String comment=null;
                            if (configSpecTestingArray[i][5]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            if (configSpecTestingArray[i][6]!=null){comment = (String) configSpecTestingArray[i][6];}
                            fileContent = fileContent + "<td>sampleId, userName, comment</td>";
                            fileContent = fileContent + "<td>"+sampleId+", "+userName+comment+"</td>";
                            dataSample = smp.sampleReceptionCommentAdd(rdbm, schemaPrefix, userName, sampleId, comment, userRole);
                            break;       
                        case "SAMPLINGCOMMENTREMOVE":
                            comment=null;
                            if (configSpecTestingArray[i][4]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            if (configSpecTestingArray[i][6]!=null){comment = (String) configSpecTestingArray[i][6];}
                            fileContent = fileContent + "<td>sampleId, userName, comment</td>";
                            fileContent = fileContent + "<td>"+sampleId+", "+userName+comment+"</td>";
                            dataSample = smp.sampleReceptionCommentRemove(rdbm, schemaPrefix, userName, sampleId, comment, userRole);
                            break;       
                        case "INCUBATIONSTART":
                            if (configSpecTestingArray[i][5]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            fileContent = fileContent + "<td>sampleId, userName</td>";
                            fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName+"</td>";
                            dataSample = smp.setSampleStartIncubationDateTime(rdbm, schemaPrefix, userName, sampleId, userRole);
                            break;       
                        case "INCUBATIONEND":
                            if (configSpecTestingArray[i][5]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            fileContent = fileContent + "<td>sampleId, userName</td>";
                            fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName+"</td>";
                            dataSample = smp.setSampleEndIncubationDateTime(rdbm, schemaPrefix, userName, sampleId, userRole);
                            break;       
                        case "SAMPLEANALYSISADD":
                            if (configSpecTestingArray[i][5]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            if (configSpecTestingArray[i][6]!=null){fieldName = (String[]) configSpecTestingArray[i][6].toString().split("\\|");}              
                            if (configSpecTestingArray[i][7]!=null){fieldValue = (Object[]) configSpecTestingArray[i][7].toString().split("\\|");}   
                            fieldValue = labArr.convertStringWithDataTypeToObjectArray((String[]) fieldValue);
                            try {                        
                                fieldValue = labArr.convertStringWithDataTypeToObjectArray(configSpecTestingArray[i][7].toString().split("\\|"));
                                fileContent = fileContent + "<td>sampleId, userName, fieldNames, fieldValues</td>";
                                fileContent = fileContent + "<td>"+sampleId.toString()+", "+userName+", "
                                    +configSpecTestingArray[i][6].toString()+", "+configSpecTestingArray[i][7].toString()+"</td>";                            
                                dataSample = smp.sampleAnalysisAddtoSample(rdbm, schemaPrefix, userName, sampleId, fieldName, fieldValue, userRole);
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                            }
                            break;              
                        case "ENTERRESULT":
                            Integer resultId = 0;
                            String rawValueResult = "";
                            if (configSpecTestingArray[i][5]!=null){resultId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            if (configSpecTestingArray[i][6]!=null){rawValueResult = (String) configSpecTestingArray[i][6];}   
                            fileContent = fileContent + "<td>resultId, userName, rawValueResult</td>";
                            fileContent = fileContent + "<td>"+resultId.toString()+", "+userName+", "+rawValueResult+"</td>";
                            try {
                                dataSample = smp.sampleAnalysisResultEntry(rdbm, schemaPrefix, userName, resultId, rawValueResult, userRole);
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                            }
                            break;  
                        case "REVIEWRESULT":
                            Integer objectId = 0;
                            String objectLevel = "";
                            rawValueResult = "";
                            if (configSpecTestingArray[i][5]!=null){objectId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            if (configSpecTestingArray[i][6]!=null){objectLevel = (String) configSpecTestingArray[i][6];}   
                            fileContent = fileContent + "<td>resultId, userName, objectLevel</td>";
                            fileContent = fileContent + "<td>"+objectId.toString()+", "+userName+", "+objectLevel+"</td>";
                            try {
                                sampleId = null; Integer testId = null; resultId = null;

                                if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                                if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                                if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                                dataSample = smp.sampleResultReview(rdbm, schemaPrefix, userName, sampleId, testId, resultId, userRole);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                            }
                            break;                                     
                        case "CANCELRESULT":
                            objectId = 0;
                            objectLevel = "";
                            rawValueResult = "";
                            if (configSpecTestingArray[i][5]!=null){objectId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            if (configSpecTestingArray[i][6]!=null){objectLevel = (String) configSpecTestingArray[i][6];}   
                            fileContent = fileContent + "<td>resultId, userName, objectLevel</td>";
                            fileContent = fileContent + "<td>"+objectId.toString()+", "+userName+", "+objectLevel+"</td>";
                            try {
                                sampleId = null; Integer testId = null; resultId = null;

                                if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                                if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                                if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                                dataSample = smp.sampleAnalysisResultCancel(rdbm, schemaPrefix, userName, sampleId, testId, resultId, userRole);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                            }
                            break;                            
                        case "UNCANCELRESULT": 
                            objectId = 0;
                            objectLevel = "";
                            rawValueResult = "";
                            if (configSpecTestingArray[i][5]!=null){objectId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            if (configSpecTestingArray[i][6]!=null){objectLevel = (String) configSpecTestingArray[i][6];}                      
                            fileContent = fileContent + "<td>resultId, userName, objectLevel</td>";
                            fileContent = fileContent + "<td>"+objectId.toString()+", "+userName+", "+objectLevel+"</td>";
                            try {
                                sampleId = null; Integer testId = null; resultId = null;

                                if (objectLevel.equalsIgnoreCase("SAMPLE")){sampleId = objectId;}
                                if (objectLevel.equalsIgnoreCase("TEST")){testId = objectId;}
                                if (objectLevel.equalsIgnoreCase("RESULT")){resultId = objectId;}
                                dataSample = smp.sampleAnalysisResultUnCancel(rdbm, schemaPrefix, userName, sampleId, testId, resultId, userRole);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                            }
                            break;       
                        case "TESTASSIGNMENT": 
                            Integer testId = 0;
                            String newAnalyst = "";
                            if (configSpecTestingArray[i][5]!=null){testId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            if (configSpecTestingArray[i][6]!=null){newAnalyst = (String) configSpecTestingArray[i][6];}                      
                            fileContent = fileContent + "<td>testId, userName, newAnalyst</td>";
                            fileContent = fileContent + "<td>"+testId.toString()+", "+userName+", "+newAnalyst+"</td>";
                            try {
                                dataSample = smp.sampleAnalysisAssignAnalyst(rdbm, schemaPrefix, userName, testId, newAnalyst, userRole);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                            }
                            break;   
                        case "GETSAMPLEINFO":
                            String schemaDataName = "data";
                            LabPLANETPlatform labPlat = new LabPLANETPlatform();
                            schemaDataName = labPlat.buildSchemaName(schemaPrefix, schemaDataName);                     
                            if (configSpecTestingArray[i][5]!=null){sampleId = Integer.parseInt( (String) configSpecTestingArray[i][5]);}
                            String[] fieldsToGet = (configSpecTestingArray[i][6].toString().split("\\|"));                    
                                 fileContent = fileContent + "<td>"
                                         +configSpecTestingArray[i][6].toString()+"</td><td>"
                                         +sampleId.toString()+"</td>";                      
                                 dataSample2D = rdbm.getRecordFieldsByFilter(rdbm, schemaDataName, "sample", new String[]{"sample_id"}, new Object[]{sampleId}, fieldsToGet);
                            break;
                        case "ENTERRESULT_LOD":
                            ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                            try {
                                try {
                                    engine.eval(new FileReader("C:\\home\\judas\\myResult.js"));
                                } catch (ScriptException ex) {
                                    Logger.getLogger(dataSampleStructure.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Invocable invocable = (Invocable) engine;
                                Object result;
                                result = invocable.invokeFunction("lossOnDrying", 10, 7);
                                System.out.println(result);
                                System.out.println(result.getClass());
                            } catch (FileNotFoundException | NoSuchMethodException | ScriptException e) {
                            }
                            break;
                        default:                       
                            dataSample[0] = (String) "function "+functionBeingTested+" not recognized";
                            dataSample[1] = ""; dataSample[2] = ""; dataSample[3] = ""; dataSample[4] = ""; dataSample[5] = ""; 

                            break;
                    }        
                }    
            }
            LabPLANETNullValue labNull = new LabPLANETNullValue();
            if (functionBeingTested.equalsIgnoreCase("GETSAMPLEINFO")){
                fileContent = fileContent + "<td>"+dataSample2D[0][0].toString();
                fileContent = fileContent + ". "+LabPLANETNullValue.replaceNull((String) dataSample2D[0][1]);
                if (dataSample2D[0].length>2){
                    fileContent = fileContent + ". "+LabPLANETNullValue.replaceNull((String) dataSample2D[0][2]);}
                if (dataSample2D[0].length>3){
                    fileContent = fileContent + ". "+LabPLANETNullValue.replaceNull((String) dataSample2D[0][3]);}
                if (dataSample2D[0].length>4){
                    fileContent = fileContent + ". "+LabPLANETNullValue.replaceNull((String) dataSample2D[0][4]);}                
                if (dataSample2D[0].length>5){
                    fileContent = fileContent + ". "+LabPLANETNullValue.replaceNull((String) dataSample2D[0][5])+"</td>";}
                
            }else{
                fileContent = fileContent + "<td>"+dataSample[0].toString()+". "+dataSample[1].toString()+". "+dataSample[2].toString()+". "+dataSample[3].toString()+". "+dataSample[4].toString()+". "+dataSample[5].toString()+"</td>";
            }    
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

        }   catch (SQLException|IOException ex) {
            if (isConnected){rdbm.closeRdbms();}            
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);   
                fileContent = fileContent + "</table>";        
                out.println(fileContent);                     
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
