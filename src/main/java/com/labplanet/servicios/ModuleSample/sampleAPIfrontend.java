/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios.ModuleSample;

import LabPLANET.utilities.LabPLANETArray;
import databases.Rdbms;
import databases.Token;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
public class sampleAPIfrontend extends HttpServlet {

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

        LabPLANETArray labArr = new LabPLANETArray();
        Rdbms rdbm = new Rdbms();                

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};            
            
            String finalToken = request.getParameter("finalToken");                      

            if (finalToken==null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);       
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: finalToken is one mandatory param for this API");                    
                out.println(Arrays.toString(errObject));
                return ;
            }                    

            Token token = new Token();
            String[] tokenParams = token.tokenParamsList();
            String[] tokenParamsValues = token.validateToken(finalToken, tokenParams);

            String dbUserName = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDB")];
            String dbUserPassword = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userDBPassword")];         
            String internalUserID = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "internalUserID")];         
            String userRole = tokenParamsValues[labArr.valuePosicInArray(tokenParams, "userRole")];         
                        
            boolean isConnected = false;
            isConnected = rdbm.startRdbms(dbUserName, dbUserPassword);
            if (!isConnected){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: db User Name and Password not correct, connection to the database is not possible");                    
                out.println(Arrays.toString(errObject));
                return ;               
            }            
        
            String actionName = request.getParameter("actionName");
            if (actionName==null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: actionName is one mandatory param for this API");                    
                out.println(Arrays.toString(errObject));
                return ;
            }           
            String schemaPrefix = request.getParameter("schemaPrefix");
            if (actionName==null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: schemaPrefix is one mandatory param for this API");                    
                out.println(Arrays.toString(errObject));
                return ;
            }           
            
            switch (actionName.toUpperCase()){
            case "GET_SAMPLETEMPLATES":       
                Object[][] Datas = rdbm.getRecordFieldsByFilter(rdbm, schemaPrefix+"-config", "sample", 
                //String  myData = rdbm.getRecordFieldsByFilterJSON(rdbm, schemaPrefix+"-config", "sample",
                        new String[] {"code"}, new Object[]{"sampleTemplate"},
                        new String[] { "json_definition"});
                rdbm.closeRdbms();
                JSONObject proceduresList = new JSONObject();
                JSONArray jArray = new JSONArray();
                if ("LABPLANET_FALSE".equalsIgnoreCase(Datas[0][0].toString())){  
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);         
                    response.getWriter().write(Arrays.toString(labArr.array2dTo1d(Datas)));      
                }else{
                   Response.ok().build();                   
                   for (Object fv: labArr.array2dTo1d(Datas)){
                       jArray.add(fv);
                       //proceduresList.put("procedures", fv);                          
                   }                                 
//                   response.getWriter().write(myData);           
                }           
                //response.getWriter().write(proceduresList.toString());                  
                response.getWriter().write(jArray.toString());                  
                return;
            case "UNRECEIVESAMPLES_LIST":            
                String myData = rdbm.getRecordFieldsByFilterJSON(rdbm, schemaPrefix+"-data", "sample",
                        new String[] {"received_by is null"}, new Object[]{""},
                        //new String[] {"sample_id"}, new Object[]{5},
                        new String[] { "sample_id", "sample_config_code", "sampling_comment"});
                rdbm.closeRdbms();
                if (myData.contains("LABPLANET_FALSE")){  
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);         
                    response.getWriter().write(myData);      
                }else{
                    Response.ok().build();
                    response.getWriter().write(myData);           
                }
                    //Response.serverError().entity(myData).build();
                rdbm.closeRdbms();
                    //return Response.ok(myData).build();            
                    return;
            case "SAMPLES_INPROGRESS_LIST":   
                String whereFieldsName = request.getParameter("whereFieldsName"); 
                String whereFieldsValue = request.getParameter("whereFieldsValue"); 
                String sampleFieldToRetrieve = request.getParameter("sampleFieldToRetrieve"); 
                
                String[] sampleFieldToRetrieveArr = new String[]{"sample_id"};
                if (sampleFieldToRetrieve!=null){
                    sampleFieldToRetrieveArr=labArr.addValueToArray1D(sampleFieldToRetrieveArr, sampleFieldToRetrieve.split("\\|"));
                }
                
                String[] whereFieldsNameArr = new String[]{"status"};
                if (whereFieldsName!=null){
                    whereFieldsNameArr=labArr.addValueToArray1D(whereFieldsNameArr, whereFieldsName.split("\\|"));
                }            
                Object[] whereFieldsValueArr = new Object[]{"RECEIVED"};
                if (whereFieldsValue!=null){
                    whereFieldsValueArr=labArr.addValueToArray1D(whereFieldsValueArr, whereFieldsValue.split("\\|"));
                }                 
 /*               whereFieldsNameArr = labArr.addValueToArray1D(whereFieldsNameArr, "status");
                whereFieldsNameArr = labArr.addValueToArray1D(whereFieldsNameArr, "RECEIVED");
                
                String[] whereFieldsNameArr=whereFieldsName.split("\\|");                
                Object[] whereFieldsValueArr = labArr.convertStringWithDataTypeToObjectArray(whereFieldsValue.split("\\|"));
  */              

                myData = rdbm.getRecordFieldsByFilterJSON(rdbm, schemaPrefix+"-data", "sample",
                        whereFieldsNameArr, whereFieldsValueArr,                        
                        sampleFieldToRetrieveArr);
                rdbm.closeRdbms();
                if (myData.contains("LABPLANET_FALSE")){  
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);         
                    response.getWriter().write(myData);      
                }else{
                    Response.ok().build();
                    response.getWriter().write(myData);           
                }
                    //Response.serverError().entity(myData).build();
                rdbm.closeRdbms();
                    //return Response.ok(myData).build();            
                    return;                    
            default:      
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
                response.getWriter().write(Arrays.toString(errObject));
                out.println(Arrays.toString(errObject));   
                rdbm.closeRdbms();                    
                //return;                    
                break;
            }               
/*            rdbm.closeRdbms();
            if ("LABPLANET_TRUE".equalsIgnoreCase(dataSample[0].toString())){                                
                //out.println(Arrays.toString(dataSample));
                response.getWriter().write(Arrays.toString(dataSample));
                Response.serverError().entity(errObject).build();
                Response.ok().build();
            }else{
               // response.getWriter().write(Arrays.toString(dataSample));                          
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
                out.println(Arrays.toString(dataSample));                    
            }      
*/            
            return;
        }catch(Exception e){      
            rdbm.closeRdbms();        
            
            String[] errObject = new String[]{e.getMessage()};
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
            response.getWriter().write(Arrays.toString(errObject));
            Response.serverError().entity(errObject).build();
            
            //out.println(Arrays.toString(errObject));                    
            //Response.serverError();
            //Response.status(responseOnERROR).build();            
            return;                 
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
