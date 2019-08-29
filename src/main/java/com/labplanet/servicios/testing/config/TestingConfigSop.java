/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;

import functionaljavaa.sop.Sop;
import functionaljavaa.sop.UserSop;
import functionaljavaa.sop.SopList;
import functionaljavaa.testingscripts.LPTestingOutFormat;
import lbplanet.utilities.LPArray;
import lbplanet.utilities.LPFrontEnd;
import lbplanet.utilities.LPPlatform;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import databases.Rdbms;
import functionaljavaa.user.UserProfile;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
/**
 *
 * @author Administrator
 */
public class TestingConfigSop extends HttpServlet {
    public static final String FIELDNAME_SOP_ID="sop_id";
    public static final String FIELDNAME_SOP_NAME="sop_name";
    public static final String LBL_MSG_SCHEMACONFIGNAME="schemaConfigName";
    public static final String LBL_MSG_SCHEMAPREFIX="schemaPrefix";
    public static final String LBL_MSG_USERINFOID="UserInfoId";
    
    public static final String ACTION_NAME_ADD_SOP_TO_SOPLIST="addSopToSopList";
    
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
            response = LPTestingOutFormat.responsePreparation(response);        
            
            String processName="oil-pl1";
            
            String csvFileName = "config-Sop.txt"; 
            String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
            String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;

            Object[][] configSpecTestingArray = LPArray.convertCSVinArray(csvPathName, csvFileSeparator);                        
            StringBuilder fileContentBuilder = new StringBuilder();
            fileContentBuilder.append(LPTestingOutFormat.getHtmlStyleHeader(this.getServletName()));
            fileContentBuilder.append("<table>");
            for (Integer j=0;j<configSpecTestingArray[0].length;j++){               
                fileContentBuilder.append(LPTestingOutFormat.rowAddField(configSpecTestingArray[0][j].toString()));
            }             
            
            String schemaConfigName = processName;
            String schemaDataName = processName;
            
            if (!LPFrontEnd.servletStablishDBConection(request, response)){return;}   
        
            String currentUser = "labplanet";
            Integer sopId = 1;
            
            UserProfile usProf = new UserProfile();
            UserSop usSop = new UserSop();
            String[] filterFieldName = new String[3];
            Object[] filterFieldValue = new Object[2];
            String[] fieldsToReturn = new String[2];     

            String userInfoId = "1"; 
            Object[] userProfileField = usProf.getProcedureUserProfileFieldValues(schemaDataName, userInfoId);  
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{"getUserProfileFieldValues", "filterFieldName: "+Arrays.toString(filterFieldName),"filterFieldValue: "+Arrays.toString(filterFieldValue),
                "fieldsToReturn: "+Arrays.toString(fieldsToReturn), Arrays.toString(userProfileField)}));
            
            String[] schemaPrefix = new String[userProfileField.length];
            for(Integer i=0;i<userProfileField.length;i++){
                schemaPrefix[i]=userProfileField[i].toString();
            }
            
            Object[] userSchemas = usProf.getAllUserProcedurePrefix( "1");
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{"getAllUserSchemaPrefix", LBL_MSG_USERINFOID+": 1", Arrays.toString(userSchemas)}));
            
            fieldsToReturn[0] = FIELDNAME_SOP_ID;
            fieldsToReturn[1] = FIELDNAME_SOP_NAME;
            filterFieldName[0]="user_id";
            filterFieldValue[0]="1";
            filterFieldName[1]="user_id";
            filterFieldValue[1]="1";
            filterFieldName[2]="understood is null";            
            
            Object[][] userSOP = UserSop.getUserProfileFieldValues(filterFieldName, filterFieldValue, fieldsToReturn, schemaPrefix);
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{"getUserProfileFieldValues", "filterFieldName: "+Arrays.toString(filterFieldName), "filterFieldValue: "+Arrays.toString(filterFieldValue),
                "fieldsToReturn: "+Arrays.toString(fieldsToReturn), LBL_MSG_SCHEMAPREFIX+": "+Arrays.toString(schemaPrefix), Arrays.toString(LPArray.array2dTo1d(userSOP))}));

            Object[][] userPendingSOPs = usSop.getNotCompletedUserSOP("1", "ALL", null);
            Object[] userPendingSOPs1D = LPArray.array2dTo1d(userPendingSOPs);
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{"getNotCompletedUserSOP", LBL_MSG_USERINFOID+": 1", "schemaPrefix: ALL", Arrays.toString(userPendingSOPs1D)}));
            
            Object[] certificationStatus = usSop.userSopCertifiedBySopId(processName+"1", "1", "58");
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{"userSopCertifiedBySopId", LBL_MSG_SCHEMAPREFIX+": "+"oil-pl11", LBL_MSG_USERINFOID+": 1", "SopId: 58", Arrays.toString(certificationStatus)}));

            certificationStatus = usSop.userSopCertifiedBySopId(processName, "1", "58");
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{"userSopCertifiedBySopId", LBL_MSG_SCHEMAPREFIX+": "+"oil-pl11", LBL_MSG_USERINFOID+": 1", "SopId: 58", Arrays.toString(certificationStatus)}));
            
            String sopName = "Demo UAT";
            
            Sop s = new Sop(sopId, sopName, 1, 1, "ACTIVE", "READ");
            Object[][] dbGetSopIdByName = s.dbGetSopObjByName(schemaConfigName, sopName,new String[]{FIELDNAME_SOP_ID, FIELDNAME_SOP_NAME});            
            if (LPPlatform.LAB_FALSE.equalsIgnoreCase(dbGetSopIdByName[0][0].toString())){
                Object[] recordCreated = s.dbInsertSopId(schemaConfigName, sopName);
                fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{"dbInsertSopId", LBL_MSG_SCHEMACONFIGNAME+": "+schemaConfigName, FIELDNAME_SOP_NAME+":"+sopName, 
                    "[sop_id, sop_name]", Arrays.toString(recordCreated)}));
            }
            dbGetSopIdByName = s.dbGetSopObjByName(schemaConfigName, sopName,new String[]{FIELDNAME_SOP_ID, FIELDNAME_SOP_NAME});
            fileContentBuilder.append(LPTestingOutFormat.rowStart()).append(LPTestingOutFormat.rowAddFields(new String[]{"", LBL_MSG_SCHEMACONFIGNAME+": "+schemaConfigName,
                FIELDNAME_SOP_NAME+":"+sopName, "["+FIELDNAME_SOP_ID+", "+FIELDNAME_SOP_NAME+"]"})).append(LPTestingOutFormat.rowEnd());        
            Object[] dbGetSopIdByName1D = LPArray.array2dTo1d(dbGetSopIdByName);
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{"dbGetSopObjByName", LBL_MSG_SCHEMACONFIGNAME+": "+schemaConfigName, FIELDNAME_SOP_NAME+":"+sopName, 
                "[sop_id, sop_name]", Arrays.toString(dbGetSopIdByName1D)}));

            UserSop userSop = new UserSop();
            Object[] addSopToUserById = userSop.addSopToUserById(schemaDataName, currentUser, sopId);
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{"addSopToUserById", "schemaDataName: "+schemaDataName, "currentUser:"+currentUser, 
                FIELDNAME_SOP_ID+":"+sopId, Arrays.toString(addSopToUserById)}));
            
            Integer sopListId = 1;
            String sopListName = "My first SOP List";
            Integer sopListVersion = 1;
            Integer sopListRevision = 1;
            String sopListStatus = "DRAFT";            
            SopList sList = new SopList(sopListId, sopListName, sopListVersion, sopListRevision, sopListStatus, null);
            sList.dbInsertSopList(schemaConfigName, currentUser);

            sopListId = 1;
            sopListName = "My second SOP List";
            sopListVersion = 1;
            sopListRevision = 1;
            sopListStatus = "DRAFT";            
            sList = new SopList(sopListId, sopListName, sopListVersion, sopListRevision, sopListStatus, null);
            
            Object[] addSopToSopList = sList.addSopToSopList(sopName);
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{ACTION_NAME_ADD_SOP_TO_SOPLIST, FIELDNAME_SOP_NAME+": "+sopName, Arrays.toString(addSopToSopList)}));
            
            Object[] addSopToSopList1 = sList.addSopToSopList("SOP-DEMO3");
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{ACTION_NAME_ADD_SOP_TO_SOPLIST, FIELDNAME_SOP_NAME+": "+"SOP-DEMO3", Arrays.toString(addSopToSopList1)}));
            
            Object[] addSopToSopList2 = sList.addSopToSopList(sopName);
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{ACTION_NAME_ADD_SOP_TO_SOPLIST, FIELDNAME_SOP_NAME+": "+sopName, Arrays.toString(addSopToSopList2)}));

            sList.dbInsertSopList(schemaConfigName, currentUser);

            Object[] dbUpdateSopListSopAssigned = sList.dbUpdateSopListSopAssigned(schemaConfigName, sList.getSopListSopAssigned());
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{"dbUpdateSopListSopAssigned", LBL_MSG_SCHEMACONFIGNAME+": "+schemaConfigName, 
                "getSopListSopAssigned: "+Arrays.toString(sList.getSopListSopAssigned()), Arrays.toString(dbUpdateSopListSopAssigned)}));

            dbUpdateSopListSopAssigned = sList.dbUpdateSopListSopAssigned(schemaConfigName, sList.getSopListSopAssigned());
            fileContentBuilder.append(LPTestingOutFormat.rowAddFields(new Object[]{"dbUpdateSopListSopAssigned", LBL_MSG_SCHEMACONFIGNAME+": "+schemaConfigName, 
                "getSopListSopAssigned: "+Arrays.toString(sList.getSopListSopAssigned()), Arrays.toString(dbUpdateSopListSopAssigned)}));
            
            fileContentBuilder.append("</table>");
            out.println(fileContentBuilder.toString());

            csvPathName = csvPathName.replace(".txt", ".html");
            File file = new File(csvPathName);
                try (FileWriter fileWriter = new FileWriter(file)) {
                    Files.deleteIfExists(file.toPath());
                    fileWriter.write(fileContentBuilder.toString());
                    fileWriter.flush();
                } 
            Rdbms.closeRdbms();
          
        } catch (Exception ex) {
            Logger.getLogger(TestingConfigSop.class.getName()).log(Level.SEVERE, null, ex);
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
