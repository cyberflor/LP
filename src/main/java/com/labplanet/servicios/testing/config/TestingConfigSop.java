/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.config;

import LabPLANET.utilities.LabPLANETArray;
import functionalJava.sop.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import databases.Rdbms;
import functionalJava.user.UserProfile;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import testing.functionalData.testingFileContentSections;
/**
 *
 * @author Administrator
 */
public class TestingConfigSop extends HttpServlet {

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
            
            LabPLANETArray labArr = new LabPLANETArray();

            String csvFileName = "config-Sop.txt"; String csvFileSeparator=";";
            String csvPathName = "\\\\FRANCLOUD\\fran\\LabPlanet\\testingRepository\\"+csvFileName;     

            Object[][] configSpecTestingArray = labArr.convertCSVinArray(csvPathName, csvFileSeparator);                        
     
            String fileContent = testingFileContentSections.getHtmlStyleHeader(this.getServletName());
            fileContent = fileContent + "<table>";
            for (Integer j=0;j<configSpecTestingArray[0].length;j++){
                fileContent = fileContent + "<th>"+configSpecTestingArray[0][j]+"</th>";
            }             
            
            String schemaConfigName = "oil-pl1";
            String schemaDataName = "oil-pl1";
//            String schemaConfigName = "\"oil-pl1-config\"";
//            String schemaDataName = "\"oil-pl1-data\"";
            HttpSession miSession = request.getSession(false);         
            
            Rdbms rdbm = new Rdbms();
            //DBTransac dbObj = null;          
            boolean isConnected = false;  
            isConnected = rdbm.startRdbms("labplanet", "LabPlanet");
            
            String currentUser = "labplanet";
            Integer sopId = 1;
            
            UserProfile usProf = new UserProfile();
            UserSop usSop = new UserSop();
            String[] filterFieldName = new String[3];
            Object[] filterFieldValue = new Object[2];
            String[] fieldsToReturn = new String[2];
            
            fieldsToReturn[0] = "role_id";
            fieldsToReturn[1] = "schema_prefix";
            filterFieldName[0]="user_info_id";
            filterFieldValue[0]="1";
            filterFieldName[1]="active";
            filterFieldValue[1]=true;
            filterFieldName[2]="schema_prefix is not null";            
            
            Object[][] userProfileField = usProf.getUserProfileFieldValues(rdbm, filterFieldName, filterFieldValue, fieldsToReturn);  
            fileContent = fileContent + "<tr><td>"+"getUserProfileFieldValues"+"</td>";                                   
            fileContent = fileContent + "<td>"+"filterFieldName: "+Arrays.toString(filterFieldName)+"</td>";                                   
            fileContent = fileContent + "<td>"+"filterFieldValue: "+Arrays.toString(filterFieldValue)+"</td>";                                   
            fileContent = fileContent + "<td>"+"fieldsToReturn: "+Arrays.toString(fieldsToReturn)+"</td>";                                   
            fileContent = fileContent + "<td>"+Arrays.toString(labArr.array2dTo1d(userProfileField))+"</td></tr>";    
            
            String[] schemaPrefix = new String[userProfileField.length];
            for(Integer i=0;i<userProfileField.length;i++){
                schemaPrefix[i]=userProfileField[i][1].toString();
            }
            
            String[] userSchemas = usProf.getAllUserSchemaPrefix(rdbm, "1");
            fileContent = fileContent + "<tr><td>"+"getAllUserSchemaPrefix"+"</td>";  
            fileContent = fileContent + "<td>"+"UserInfoId: 1"+"</td>";                                               
            fileContent = fileContent + "<td>"+Arrays.toString(userSchemas)+"</td></tr>";    
            
            fieldsToReturn[0] = "sop_id";
            fieldsToReturn[1] = "sop_name";
            filterFieldName[0]="user_id";
            filterFieldValue[0]="1";
            filterFieldName[1]="user_id";
            filterFieldValue[1]="1";
            filterFieldName[2]="understood is null";            

            Object[][] userSOP = usSop.getUserProfileFieldValues(rdbm, filterFieldName, filterFieldValue, fieldsToReturn, schemaPrefix);
            fileContent = fileContent + "<tr><td>"+"getUserProfileFieldValues"+"</td>";          
            fileContent = fileContent + "<td>"+"filterFieldName: "+Arrays.toString(filterFieldName)+"</td>";                                   
            fileContent = fileContent + "<td>"+"filterFieldValue: "+Arrays.toString(filterFieldValue)+"</td>";                                   
            fileContent = fileContent + "<td>"+"fieldsToReturn: "+Arrays.toString(fieldsToReturn)+"</td>";                                   
            fileContent = fileContent + "<td>"+"schemaPrefix: "+Arrays.toString(schemaPrefix)+"</td>";   
            fileContent = fileContent + "<td>"+Arrays.toString(labArr.array2dTo1d(userSOP))+"</td></tr>";  
            
            
//            if ("LABPLANET_FALSE".equalsIgnoreCase(userSOP[0][0].toString())){
//                out.println(Arrays.toString(userSOP));
//            }

            String[] userPendingSOPs = usSop.getNotCompletedUserSOP(rdbm, "1", "ALL");
            fileContent = fileContent + "<tr><td>"+"getNotCompletedUserSOP"+"</td>";                                   
            fileContent = fileContent + "<td>"+"UserInfoId: 1"+"</td>";      
            fileContent = fileContent + "<td>"+"schemaPrefix: ALL"+"</td>";                
            fileContent = fileContent + "<td>"+Arrays.toString(userPendingSOPs)+"</td></tr>";                
            
            Object[] certificationStatus = usSop.userSopCertifiedBySopId(rdbm, "oil-pl11", "1", "58");
            fileContent = fileContent + "<tr><td>"+"userSopCertifiedBySopId"+"</td>";                                   
            fileContent = fileContent + "<td>"+"schemaPrefix: "+"oil-pl11"+"</td>";  
            fileContent = fileContent + "<td>"+"UserInfoId: 1"+"</td>";      
            fileContent = fileContent + "<td>"+"SopId: 58"+"</td>";      
            fileContent = fileContent + "<td>"+Arrays.toString(certificationStatus)+"</td></tr>";           

            certificationStatus = usSop.userSopCertifiedBySopId(rdbm, "oil-pl1", "1", "58");
            fileContent = fileContent + "<tr><td>"+"userSopCertifiedBySopId"+"</td>";                                   
            fileContent = fileContent + "<td>"+"schemaPrefix: "+"oil-pl1"+"</td>";  
            fileContent = fileContent + "<td>"+"UserInfoId: 1"+"</td>";      
            fileContent = fileContent + "<td>"+"SopId: 58"+"</td>";      
            fileContent = fileContent + "<td>"+Arrays.toString(certificationStatus)+"</td></tr>";               
            
//            if (1==1)return;
//            System.exit(1);
            String sopName = "Demo UAT";
            
            Sop s = new Sop(sopId, sopName, 1, 1, "ACTIVE", "READ");
            Object[][] dbGetSopIdByName = s.dbGetSopObjByName(rdbm, schemaConfigName, sopName,new String[]{"sop_id", "sop_name"});            
            if ("LABPLANET_FALSE".equalsIgnoreCase(dbGetSopIdByName[0][0].toString())){
                Object[] recordCreated = s.dbInsertSopId(rdbm, schemaConfigName, sopName);
                fileContent = fileContent + "<tr><td>"+"dbInsertSopId"+"</td>";                                   
                fileContent = fileContent + "<td>"+"schemaConfigName: "+schemaConfigName+"</td>";  
                fileContent = fileContent + "<td>"+"sopName:"+sopName+"</td>";      
                fileContent = fileContent + "<td>"+"[sop_id, sop_name]"+"</td>";      
                fileContent = fileContent + "<td>"+Arrays.toString(recordCreated)+"</td></tr>";                  
            }
            dbGetSopIdByName = s.dbGetSopObjByName(rdbm, schemaConfigName, sopName,new String[]{"sop_id", "sop_name"});
            fileContent = fileContent + "<tr><td>"+"dbGetSopObjByName"+"</td>";                                   
            fileContent = fileContent + "<td>"+"schemaConfigName: "+schemaConfigName+"</td>";  
            fileContent = fileContent + "<td>"+"sopName:"+sopName+"</td>";      
            fileContent = fileContent + "<td>"+"[sop_id, sop_name]"+"</td>";      
            Object[] dbGetSopIdByName1D = labArr.array2dTo1d(dbGetSopIdByName);
            fileContent = fileContent + "<td>"+Arrays.toString(dbGetSopIdByName1D)+"</td></tr>";                  

            UserSop userSop = new UserSop();
            Object[] addSopToUserById = userSop.addSopToUserById(rdbm, schemaDataName, currentUser, sopId);
            fileContent = fileContent + "<tr><td>"+"addSopToUserById"+"</td>";                                   
            fileContent = fileContent + "<td>"+"schemaDataName: "+schemaDataName+"</td>";  
            fileContent = fileContent + "<td>"+"currentUser:"+currentUser+"</td>";      
            fileContent = fileContent + "<td>"+"sopId:"+sopId+"</td>";      
            fileContent = fileContent + "<td>"+Arrays.toString(addSopToUserById)+"</td></tr>";                             
            
            Integer sopListId = 1;
            String sopListName = "My first SOP List";
            Integer sopListVersion = 1;
            Integer sopListRevision = 1;
            String sopListStatus = "DRAFT";            
            SopList sList = new SopList(sopListId, sopListName, sopListVersion, sopListRevision, sopListStatus, null);
            sList.dbInsertSopList(rdbm, schemaConfigName, currentUser);

            sopListId = 1;
            sopListName = "My second SOP List";
            sopListVersion = 1;
            sopListRevision = 1;
            sopListStatus = "DRAFT";            
            sList = new SopList(sopListId, sopListName, sopListVersion, sopListRevision, sopListStatus, null);
            
            Integer sopsInSopList = sList.getSopListSopAssigned().length;
            Object[] addSopToSopList = sList.addSopToSopList(sopName);
            fileContent = fileContent + "<tr><td>"+"addSopToSopList"+"</td>";                                   
            fileContent = fileContent + "<td>"+"sopName:"+sopName+"</td>";      
            fileContent = fileContent + "<td>"+Arrays.toString(addSopToSopList)+"</td></tr>";        
            
            sopsInSopList = sList.getSopListSopAssigned().length;          
            Object[] addSopToSopList1 = sList.addSopToSopList("SOP-DEMO3");
            fileContent = fileContent + "<tr><td>"+"addSopToSopList"+"</td>";                                   
            fileContent = fileContent + "<td>"+"sopName:"+"SOP-DEMO3"+"</td>";      
            fileContent = fileContent + "<td>"+Arrays.toString(addSopToSopList1)+"</td></tr>";        
            
            sopsInSopList = sList.getSopListSopAssigned().length;          
            Object[] addSopToSopList2 = sList.addSopToSopList(sopName);
            fileContent = fileContent + "<tr><td>"+"addSopToSopList"+"</td>";                                   
            fileContent = fileContent + "<td>"+"sopName:"+sopName+"</td>";      
            fileContent = fileContent + "<td>"+Arrays.toString(addSopToSopList2)+"</td></tr>";     

            sList.dbInsertSopList(rdbm, schemaConfigName, currentUser);

            sopsInSopList = sList.getSopListSopAssigned().length;  
            Object[] dbUpdateSopListSopAssigned = sList.dbUpdateSopListSopAssigned(rdbm, schemaConfigName, sList.getSopListSopAssigned());
            fileContent = fileContent + "<tr><td>"+"dbUpdateSopListSopAssigned"+"</td>";                                   
            fileContent = fileContent + "<td>"+"schemaConfigName: "+schemaConfigName+"</td>";  
            fileContent = fileContent + "<td>"+"getSopListSopAssigned: "+Arrays.toString(sList.getSopListSopAssigned())+"</td>";              
            fileContent = fileContent + "<td>"+Arrays.toString(dbUpdateSopListSopAssigned)+"</td></tr>";        

            sopsInSopList = sList.getSopListSopAssigned().length;  
            dbUpdateSopListSopAssigned = sList.dbUpdateSopListSopAssigned(rdbm, schemaConfigName, sList.getSopListSopAssigned());
            fileContent = fileContent + "<tr><td>"+"dbUpdateSopListSopAssigned"+"</td>";                                   
            fileContent = fileContent + "<td>"+"schemaConfigName: "+schemaConfigName+"</td>";  
            fileContent = fileContent + "<td>"+"getSopListSopAssigned: "+Arrays.toString(sList.getSopListSopAssigned())+"</td>";              
            fileContent = fileContent + "<td>"+Arrays.toString(dbUpdateSopListSopAssigned)+"</td></tr>";        
            
            sopsInSopList++;

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
          
        } catch (SQLException ex) {
            Logger.getLogger(TestingConfigSop.class.getName()).log(Level.SEVERE, null, ex);
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
