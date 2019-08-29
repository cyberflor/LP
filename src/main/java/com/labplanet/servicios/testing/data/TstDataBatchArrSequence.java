/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.testing.data;

import functionaljavaa.testingscripts.LPTestingOutFormat;
import lbplanet.utilities.LPArray;
import lbplanet.utilities.LPFrontEnd;
import databases.Rdbms;
import functionaljavaa.batch.BatchArray;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class TstDataBatchArrSequence extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings("empty-statement")
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)            throws ServletException, IOException {
        response = LPTestingOutFormat.responsePreparation(response);        
        String csvFileName = "tstDataBatchArray.txt"; 
        String fileContent = "";                          
        String csvPathName = LPTestingOutFormat.TESTING_FILES_PATH+csvFileName; 
//        String csvFileSeparator=LPTestingOutFormat.TESTING_FILES_FIELD_SEPARATOR;

        if (Rdbms.getRdbms().startRdbms(LPTestingOutFormat.TESTING_USER, LPTestingOutFormat.TESTING_PW)==null){
            fileContent = fileContent + LPTestingOutFormat.MSG_DB_CON_ERROR;
            LPTestingOutFormat.createLogFile(csvPathName, fileContent);
            return;
        }           

        try (PrintWriter out = response.getWriter()) {
            
            String currentUser = "Fran";
//            String currentUSerRole = "Analyst";
            
//            String schemaName = "oil-pl1-data"; 
//            String tableName = "batch_java";
            
            BatchArray[][] myBatchArray= new BatchArray[1][1];
            
            String[] fieldName = new String[6];
            Object[] fieldValue = new Object[6];
            String[] keyFieldName = new String[1];
            Object[] keyFieldValue = new Object[1];                 
                       


            String batchName = "Batch 22";

            keyFieldName[0] = "name";
            keyFieldValue[0] = batchName;             

//            dbObj = new DBTransac(rdbm, schemaName, currentUser, currentUSerRole, "Delete",tableName, null, null, keyFieldName, keyFieldValue);           
//            dbObj.DBAction(rdbm);
            
            myBatchArray[0][0] = new BatchArray("oil-pl1", "tmpA", 1, batchName, currentUser,30, 5);                        

            fieldName[0] = "name";
            fieldValue[0] = batchName;
            fieldName[1] = "template";
            fieldValue[1] = "tmpA";
            fieldName[2] = "template_version";
            fieldValue[2] = 1;
            fieldName[3] = "array_num_rows";
            fieldValue[3] = 2;
            fieldName[4] = "array_num_cols";
            fieldValue[4] = 2;
            fieldName[5] = "array_total_positions";
            fieldValue[5] = 4;
                                
            Object[][] batchContent = myBatchArray[0][0].getBatchContent();
            Object[] batchContent1d = LPArray.array2dTo1d(batchContent);
            
            fieldName[5] = "array_content";
            fieldValue[5] = batchContent1d;            
            
 //           dbObj = new DBTransac(rdbm, schemaName, currentUser, currentUSerRole, "Insert",tableName, fieldName, fieldValue, keyFieldName, keyFieldValue);
            
 //           dbObj.DBAction(rdbm);
            for (BatchArray mb: myBatchArray[0])
            {
                fieldName = new String[1];
                fieldValue = new Object[1];
                fieldName[0] = "operator";
                fieldValue[0] = currentUser;
                /*fieldName[1] = "template";
                fieldValue[1] = "tmpA";
                fieldName[2] = "template_version";
                fieldValue[2] = 1;*/
//                dbObj = new DBTransac(rdbm, schemaName, currentUser, currentUSerRole, "Update",tableName, fieldName, fieldValue, keyFieldName, keyFieldValue);                                                   
//                dbObj.DBAction(rdbm);
                

//                out.println("Saving in database: " + mb.getBatchName() + " "+ myBatchArray[0].dbCreateBatchArray(rdbm, schemaName) + "<br>");            

                myBatchArray[0][0].batchAssignOperator("OperA");
//                out.println("Saving in database the update operator: " + mb.getBatchOperator() + " "+ myBatchArray[0].dbUpdateBatchArray(rdbm, schemaName,mb.getBatchName(),"operator", mb.getBatchOperator()) + "<br>");            

                mb.batchCommentAdd("hello", mb.getBatchOperator());
                mb.batchAssignOperator("newOper");
                String comment = mb.batchCommentOpen("OperA", "");
                if (!comment.isEmpty())
                    comment = comment + " (Comment created by:" + mb.getBatchCommentAuthor() + ") <br>";
                
                out.println("Batch Position length is: " + mb.getNumTotalObjects() + " in a " + mb.getNumRows() + " x " + mb.getNumCols() + " array.<br>");
                
                //System.out.println(mb.name + " " + mb.getBatchOperator() + " " + comment);
                
                out.println("Adding Sample 1 in position 11x1: " + mb.batchArrayAddObjectInPosic(1, 1, "Sample 1") + ". Objects in the array: " + mb.getBatchTotalObjets() + " / " + mb.getNumTotalObjects() + "<br>");
                out.println("Adding Sample 2 in position 9x1: " + mb.batchArrayAddObjectInPosic(2, 1, "Sample 2") + ". Objects in the array: " + mb.getBatchTotalObjets() + " / " + mb.getNumTotalObjects() + "<br>");
                out.println("Adding Sample 3 in position 9x1: " + mb.batchArrayAddObjectInPosic(1, 2, "Sample 3") + ". Objects in the array: " + mb.getBatchTotalObjets() + " / " + mb.getNumTotalObjects() + "<br>");
                
                out.println("Adding Sample 4 in position 9x1 by override: " + mb.batchArrayAddObjectInPosicOverride(2, 2, "Sample 4") + ". Objects in the array: " + mb.getBatchTotalObjets() + " / " + mb.getNumTotalObjects() + "<br>");
                
                out.println("Adding Sample 5 in position 10x10 by override: " + mb.batchArrayAddObjectInPosicOverride(10, 10, "Sample 5") + ". Objects in the array: " + mb.getBatchTotalObjets() + " / " + mb.getNumTotalObjects() + "<br>");
                
                out.println("Adding Sample 5 in position 10x2 by override: " + mb.batchArrayAddObjectInPosicOverride(10, 2, "Sample 5") + ". Objects in the array: " + mb.getBatchTotalObjets() + " / " + mb.getNumTotalObjects() + "<br>");

                out.println("Adding Sample 6 in position 10x2 without override: " + mb.batchArrayAddObjectInPosic(10, 2, "Sample 6") + ". Objects in the array: " + mb.getBatchTotalObjets() + " / " + mb.getNumTotalObjects() + "<br>");
                                
                out.println("Adding Sample 6 in position 1x1 by override: " + mb.batchArrayAddObjectInPosicOverride(1, 1, "Sample 6") + ". Objects in the array: " + mb.getBatchTotalObjets() + " / " + mb.getNumTotalObjects() + "<br>");
                
                out.println("Adding Sample 7 in position 2x5 by override: " + mb.batchArrayAddObjectInPosicOverride(2, 5, "Sample 7") + ". Objects in the array: " + mb.getBatchTotalObjets() + " / " + mb.getNumTotalObjects() + "<br>");

                out.println("The object in position 9x1 is: " + mb.getBatchPositionContent(1, 1) + "<br>");
                
                out.println("The object in position 2x1 is: " + mb.getBatchPositionContent(2, 1) + "<br>");                
                
                out.println("The Sample 4 was found in " + mb.searchStringContent("Sample 4").size() + " positions." + "<br>");
                
                mb.searchStringContent("Sample 4").forEach((Object al) -> {
                    out.println("    " + al + "<br>");
                });
                
                out.println("The Sample 1 was found in " + mb.searchStringContent("Sample 1").size() + " positions." + "<br>");
                
                out.println("Adding Sample 4 again in position 1x2 by override: " + mb.batchArrayAddObjectInPosicOverride(1, 2, "Sample 4") + ". Objects in the array: " + mb.getBatchTotalObjets() + " / " + mb.getNumTotalObjects() + "<br>");
                
                out.println("The Sample 4 was found in " + mb.searchStringContent("Sample 4").size() + " positions." + "<br>");
                
                mb.searchStringContent("Sample 4").forEach(al -> {
                    out.println("    " + al + "<br>");
                });
                batchContent = myBatchArray[0][0].getBatchContent();
                
                batchContent1d = LPArray.array2dTo1d(batchContent);
//                Object[][] batchContent2d = LPArray.array1dTo2d(batchContent1d, mb.numCols);
                                
                fieldName = new String[5];
                fieldValue = new Object[5];
                fieldName[0] = "array_content";
                fieldValue[0] = batchContent1d;
//                dbObj = new DBTransac(rdbm, schemaName, currentUser, currentUSerRole, "Update",tableName, fieldName, fieldValue, keyFieldName, keyFieldValue);                                                   
//                dbObj.DBAction(rdbm);
                
                myBatchArray[0][0].setColumnsName(null);
                Object[] columnName = myBatchArray[0][0].getColumnsName();
                fieldName[1] = "array_columns_name";
                fieldValue[1] = columnName;                
//                dbObj = new DBTransac(rdbm, schemaName, currentUser, currentUSerRole, "Update",tableName, fieldName, fieldValue, keyFieldName, keyFieldValue);                                                                  
//                dbObj.DBAction(rdbm);

                myBatchArray[0][0].setLinesName(null);
                Object[] lineName = myBatchArray[0][0].getLinesName();
                fieldName[2] = "array_lines_name";
                fieldValue[2] = lineName;                
                fieldName[3] = "array_total_objects";
                fieldValue[3] = myBatchArray[0][0].getNumTotalObjects();                
                fieldName[4] = "array_total_positions";
                fieldValue[4] = myBatchArray[0][0].getNumTotalObjects();                
//                dbObj = new DBTransac(rdbm, schemaName, currentUser, currentUSerRole, "Update",tableName, fieldName, fieldValue, keyFieldName, keyFieldValue);                                                                  
//                dbObj.DBAction(rdbm);                                
            }
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
