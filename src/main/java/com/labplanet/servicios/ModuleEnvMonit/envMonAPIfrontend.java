/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleEnvMonit;

import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPFrontEnd;
import LabPLANET.utilities.LPHttp;
import LabPLANET.utilities.LPPlatform;
import databases.Rdbms;
import databases.Token;
import functionalJava.sampleStructure.DataSampleUtilities;
import functionalJava.testingScripts.LPTestingOutFormat;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Administrator
 */
public class envMonAPIfrontend extends HttpServlet {
    public static final String ERRORMSG_ERROR_STATUS_CODE="Error Status Code";
    public static final String ERRORMSG_MANDATORY_PARAMS_MISSING="API Error Message: There are mandatory params for this API method not being passed";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request=LPHttp.requestPreparation(request);
        response=LPHttp.responsePreparation(response);

        String language = LPFrontEnd.setLanguage(request); 

        try (PrintWriter out = response.getWriter()) {
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};            
            
            String[] mandatoryParams = new String[]{"schemaPrefix"};
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "actionName");
            mandatoryParams = LPArray.addValueToArray1D(mandatoryParams, "finalToken");

            Object[] areMandatoryParamsInResponse = LPHttp.areMandatoryParamsInApiRequest(request, mandatoryParams);
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(areMandatoryParamsInResponse[0].toString())){
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_ERROR_STATUS_CODE+": "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = LPArray.addValueToArray1D(errObject, ERRORMSG_MANDATORY_PARAMS_MISSING+": "+areMandatoryParamsInResponse[1].toString());                    
                Object[] errMsg =  LPFrontEnd.responseError(errObject, language, areMandatoryParamsInResponse[1].toString());
                response.sendError((int) errMsg[0], (String) errMsg[1]);                
                return ;                
            }            

            String schemaPrefix = request.getParameter("schemaPrefix");            
            String actionName = request.getParameter("actionName");
            String finalToken = request.getParameter("finalToken");                           
            
            Token token = new Token();
            String[] tokenParams = token.tokenParamsList();
            String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);
            
            String dbUserName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERDB)];
            String dbUserPassword = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERPW)];
//            String internalUserID = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_INTERNAL_USERID)];         
//            String userRole = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ROLE)];                     

            if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}
        
            switch (actionName.toUpperCase()){
                case "xxx":
                    break;
                case "PROGRAMS_LIST":
                    String schemaName=LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
                    String programTableName = "program";
                    String programFldNameList = request.getParameter("programFldNameList");   
                    if (programFldNameList==null){
                        programFldNameList = "name|program_config_id|program_config_version|description_en|description_es"
                                + "|sample_config_code|sample_config_code_version|map_image";}                     
                    String[] programFldNameArray = LPTestingOutFormat.csvExtractFieldValueStringArr(programFldNameList);

                    String programFldSortList = request.getParameter("programFldSortList");   
                    if (programFldSortList==null){
                        programFldSortList = "name";}                     
                    String[] programFldSortArray = LPTestingOutFormat.csvExtractFieldValueStringArr(programFldSortList);
                    
                    String programLocationFldNameList = request.getParameter("programLocationFldNameList");   
                    if (programLocationFldNameList==null){
                        programLocationFldNameList = "program_name|location_name|description_en|description_es|map_icon|map_icon_h|map_icon_w|map_icon_top|map_icon_left";}                     
                    String[] programLocationFldNameArray = LPTestingOutFormat.csvExtractFieldValueStringArr(programLocationFldNameList);
                    if (LPArray.valuePosicInArray(programLocationFldNameArray, "program_name")==-1){
                        programLocationFldNameArray = LPArray.addValueToArray1D(programLocationFldNameArray, "program_name");
                    }                    
                    if (LPArray.valuePosicInArray(programLocationFldNameArray, "location_name")==-1){
                        programLocationFldNameArray = LPArray.addValueToArray1D(programLocationFldNameArray, "location_name");
                    }
                    
                    String programLocationFldSortList = request.getParameter("programLocationFldSortList");   
                    if (programLocationFldSortList==null){
                        programLocationFldSortList = "order_number|location_name";}                     
                    String[] programLocationFldSortArray = LPTestingOutFormat.csvExtractFieldValueStringArr(programLocationFldSortList);
                    
                    String programLocationCardInfoFldNameList = request.getParameter("programLocationCardInfoFldNameList");   
                    if (programLocationCardInfoFldNameList==null){
                        programLocationCardInfoFldNameList = "program_name|location_name|description_en";}                     
                    String[] programLocationCardInfoFldNameArray = LPTestingOutFormat.csvExtractFieldValueStringArr(programLocationCardInfoFldNameList);

                    if (LPArray.valuePosicInArray(programLocationCardInfoFldNameArray, "program_name")==-1){
                        programLocationCardInfoFldNameArray = LPArray.addValueToArray1D(programLocationCardInfoFldNameArray, "program_name");
                    }                    
                    if (LPArray.valuePosicInArray(programLocationCardInfoFldNameArray, "location_name")==-1){
                        programLocationCardInfoFldNameArray = LPArray.addValueToArray1D(programLocationCardInfoFldNameArray, "location_name");
                    }
                    String programLocationCardInfoFldSortList = request.getParameter("programLocationCardInfoFldSortList");   
                    if (programLocationCardInfoFldSortList==null){
                        programLocationCardInfoFldSortList = "order_number|location_name";}                     
                    String[] programLocationCardInfoFldSortArray = LPTestingOutFormat.csvExtractFieldValueStringArr(programLocationCardInfoFldSortList);

                    Object[] statusList = DataSampleUtilities.getSchemaSampleStatusList(schemaPrefix);
                    Object[] statusListEn = DataSampleUtilities.getSchemaSampleStatusList(schemaPrefix, "en");
                    Object[] statusListEs = DataSampleUtilities.getSchemaSampleStatusList(schemaPrefix, "es");
                            
                    Object[][] programInfo = Rdbms.getRecordFieldsByFilter(schemaName, programTableName, 
                        new String[]{"active"}, new Object[]{true}, programFldNameArray, programFldSortArray);
                    if (LPPlatform.LAB_FALSE.equalsIgnoreCase(programInfo[0][0].toString())){
                        Rdbms.closeRdbms();                                           
                        Object[] errMsg = LPFrontEnd.responseError(programInfo, language, null);
                        response.sendError((int) errMsg[0], (String) errMsg[1]);    
                        return;
                    }
                    
                    JSONArray programsJsonArr = new JSONArray();     
                    for (Object[] curProc: programInfo){
                        JSONObject programJsonObj = new JSONObject();  
                        String currProgram = curProc[0].toString();

                        String[] programSampleSummaryFldNameArray = new String[]{"status", "location_name"};
                        String[] programSampleSummaryFldSortArray = new String[]{"status"};
                        Object[][] programSampleSummary = Rdbms.getRecordFieldsByFilter(schemaName, "sample", 
                                new String[]{"program_name", }, new String[]{currProgram}, programSampleSummaryFldNameArray, programSampleSummaryFldSortArray);

                        for (int yProc=0; yProc<programInfo[0].length; yProc++){              
                            //procArray = LPArray.addValueToArray1D(procArray, procInfo[0][yProc]);
                            programJsonObj.put(programFldNameArray[yProc], curProc[yProc]);
                        }
                        programJsonObj.put("type", "tree-list"); 
                        programJsonObj.put("total", programSampleSummary.length); 
                        
                        // Program Location subStructure. Begin
                        Object[][] programLocations = Rdbms.getRecordFieldsByFilter(schemaName, "program_location", 
                                new String[]{"program_name"}, new String[]{currProgram}, 
                                programLocationFldNameArray, programLocationFldSortArray);
                        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(programLocations[0][0].toString())){
//                            programJsonObj.put("program_location_error", programLocations[0][programLocations[0].length-1]);        
                            if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}                           
                            programLocations = Rdbms.getRecordFieldsByFilter(schemaName, "program_location", 
                                                           new String[]{"program_name"}, new String[]{currProgram}, 
                                                           programLocationFldNameArray, programLocationFldSortArray);                            
                        }
                        if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(programLocations[0][0].toString())){     
                            JSONArray programLocationsJsonArray = new JSONArray();                              
                            for (Object[] programLocations1 : programLocations) {
                                String locationName = programLocations1[LPArray.valuePosicInArray(programLocationFldNameArray, "location_name")].toString();

                                JSONObject programLocationJsonObj = new JSONObject();     
                                for (int yProcEv = 0; yProcEv<programLocations[0].length; yProcEv++) {
                                    //procArray = LPArray.addValueToArray1D(procArray, procEvent[xProcEv][yProcEv]);
                                    programLocationJsonObj.put(programLocationFldNameArray[yProcEv], programLocations1[yProcEv]);
                                }
//                                JSONObject programLocationsJsonObj = new JSONObject(); 
                            
                            // Program Location Card Info subStructure. Begin
                                Object[][] programLocationCardInfo = Rdbms.getRecordFieldsByFilter(schemaName, "program_location", 
                                        new String[]{"program_name", "location_name"}, new String[]{currProgram, locationName}, 
                                        programLocationCardInfoFldNameArray, programLocationCardInfoFldSortArray);
                                JSONArray programLocationCardInfoJsonArr = new JSONArray(); 
                                
                                JSONArray programLocationCardInfoJsonArray = new JSONArray();
                                JSONObject programLocationCardInfoJsonObj = new JSONObject();  
                                for (int xProc=0; xProc<programLocationCardInfo.length; xProc++){   
                                    for (int yProc=0; yProc<programLocationCardInfo[0].length; yProc++){              
                                        //procArray = LPArray.addValueToArray1D(procArray, procInfo[0][yProc]);
                                        //programLocationCardInfoJsonObj.put(programFldNameArray[yProc], curProc[yProc]);
                                        programLocationCardInfoJsonObj = new JSONObject();
                                        //procArray = LPArray.addValueToArray1D(procArray, procEvent[xProcEv][yProcEv]);                                    
                                        programLocationCardInfoJsonObj.put("name", programLocationCardInfoFldNameArray[yProc]);
                                        programLocationCardInfoJsonObj.put("label_en", programLocationCardInfoFldNameArray[yProc]);
                                        programLocationCardInfoJsonObj.put("label_es", programLocationCardInfoFldNameArray[yProc]);
                                        programLocationCardInfoJsonObj.put("value", programLocationCardInfo[xProc][yProc]);
                                        programLocationCardInfoJsonObj.put("type", "text");
                                        programLocationCardInfoJsonObj.put("password", "false");
                                        // programLocationCardInfoJsonObj.put(programLocationCardInfoFldNameArray[yProcEv], "test"); //programLocations1[yProcEv]);
                                        programLocationCardInfoJsonArr.add(programLocationCardInfoJsonObj);                                    
                                    }    
                                }
                                programLocationJsonObj.put("card_info", programLocationCardInfoJsonArr);  

                                Object[] samplesStatusCounter = new Object[0];
                                for (Object statusList1 : statusList) {
                                    String currStatus = statusList1.toString();
                                    Integer contSmpStatus=0;
                                    for (Object[] smpStatus: programSampleSummary){
                                        if (currStatus.equalsIgnoreCase(smpStatus[0].toString()) && 
                                                ( smpStatus[1]!=null) && locationName.equalsIgnoreCase(smpStatus[1].toString()) ){contSmpStatus++;}
                                    }
                                    samplesStatusCounter = LPArray.addValueToArray1D(samplesStatusCounter, contSmpStatus);
                                }
                                JSONArray programSampleSummaryJsonArray = new JSONArray();  
                                for (int iStatuses=0; iStatuses < statusList.length; iStatuses++){
                                    JSONObject programSampleSummaryJsonObj = new JSONObject();  
                                    programSampleSummaryJsonObj.put("name", statusList[iStatuses]);
                                    programSampleSummaryJsonObj.put("label_en", statusListEn[iStatuses]);
                                    programSampleSummaryJsonObj.put("label_es", statusListEs[iStatuses]);
                                    programSampleSummaryJsonObj.put("value", samplesStatusCounter[iStatuses]);
                                    programSampleSummaryJsonObj.put("type", "text");
                                    programSampleSummaryJsonObj.put("password", "false");

                                    programSampleSummaryJsonArray.add(programSampleSummaryJsonObj);
                                }

                                programLocationJsonObj.put("samples_summary", programSampleSummaryJsonArray);                             
                                

                        // Program Location subStructure. Begin
                                programLocationsJsonArray.add(programLocationJsonObj);
                            }                    
                            // Program Location Card Info subStructure. End

                                programJsonObj.put("sample_points", programLocationsJsonArray);   
                                
                        }
                            
                            //programLocationCardInfoJsonArr.add(programLocationCardInfoJsonObj);                                 
                        if (!LPPlatform.LAB_FALSE.equalsIgnoreCase(programLocations[0][0].toString())){     
                            JSONArray programSampleSummaryJsonArray = new JSONArray();   

                            Object[] samplesStatusCounter = new Object[0];
                            for (Object statusList1 : statusList) {
                                String currStatus = statusList1.toString();
                                Integer contSmpStatus=0;
                                for (Object[] smpStatus: programSampleSummary){
                                    if (currStatus.equalsIgnoreCase(smpStatus[0].toString())){contSmpStatus++;}
                                }
                                samplesStatusCounter = LPArray.addValueToArray1D(samplesStatusCounter, contSmpStatus);
                            }
                            for (int iStatuses=0; iStatuses < statusList.length; iStatuses++){
                                JSONObject programSampleSummaryJsonObj = new JSONObject();  
                                programSampleSummaryJsonObj.put("name", statusList[iStatuses]);
                                programSampleSummaryJsonObj.put("label_en", statusListEn[iStatuses]);
                                programSampleSummaryJsonObj.put("label_es", statusListEs[iStatuses]);
                                programSampleSummaryJsonObj.put("value", samplesStatusCounter[iStatuses]);
                                programSampleSummaryJsonObj.put("type", "text");
                                programSampleSummaryJsonObj.put("password", "false");
                            
                                programSampleSummaryJsonArray.add(programSampleSummaryJsonObj);
                            }
                            programJsonObj.put("samples_summary", programSampleSummaryJsonArray);                             
       
                        }   
                        // Program Location subStructure. End                         
                        programsJsonArr.add(programJsonObj);
                    }          
                    //Object[][] procArray2d = LPArray.array1dTo2d(procArray, procFldNameArray.length);              
                    JSONObject proceduresList = new JSONObject();
                    proceduresList.put("programsList", programsJsonArr);

                    response.getWriter().write(proceduresList.toString());
                    Rdbms.closeRdbms();                    
                    Response.ok().build();
                    return;                    
                default:      
                    Rdbms.closeRdbms(); 
                    RequestDispatcher rd = request.getRequestDispatcher("/frontEnd/sampleAPIfrontEnd");
                    rd.forward(request,response);   
                    return;
            }
            }catch(Exception e){      
                Rdbms.closeRdbms();                   
                String[] errObject = new String[]{e.getMessage()};
                Object[] errMsg = LPFrontEnd.responseError(errObject, language, null);
                response.sendError((int) errMsg[0], (String) errMsg[1]);           
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
