/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontEnd.api;

import LabPLANET.utilities.LabPLANETArray;
import com.labplanet.modelo.UserRole;
import databases.Rdbms;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import functionalJava.user.Role;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import org.json.simple.JSONArray;
//import com.google.gson.Gson;
/**
 *
 * @author Administrator
 */
public class authenticationAPI_refer extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public enum actionsList {    AUTHENTICATE,    GETUSERROLE,    LOW    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        LabPLANETArray labArr = new LabPLANETArray();        
        Rdbms rdbm = new Rdbms();     
    
        try (PrintWriter out = response.getWriter()) {            
            String[] errObject = new String[]{"Servlet sampleAPI at " + request.getServletPath()};            
            
            String actionName = null;
            actionName = request.getParameter("actionName");
            
            if (actionName==null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);           
                errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                errObject = labArr.addValueToArray1D(errObject, "API Error Message: actionName is one mandatory param and should be one integer value for this API");                    
                out.println(Arrays.toString(errObject));
                return ;
            }            
            
            
            switch (actionName.toUpperCase()){
                case "AUTHENTICATE":
                    String dbUserName = request.getParameter("dbUserName");                   
                    String dbUserPassword = request.getParameter("dbUserPassword");       
                    
                    if (dbUserName==null || dbUserPassword==null) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);           
                        errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
                        if (dbUserName==null) errObject = labArr.addValueToArray1D(errObject, "API Error Message: dbUserName is one mandatory param and should be one integer value for this API");                    
                        else errObject = labArr.addValueToArray1D(errObject, "API Error Message: dbUserPassword is one mandatory param and should be one integer value for this API");                    
                        out.println(Arrays.toString(errObject));
                        return ;
                    }                   
                    if (dbUserName.length()==0){
                        Response.status(Response.Status.NOT_ACCEPTABLE).build();
                        break;    
                    }                      
                    boolean isConnected = false;
                    isConnected = rdbm.startRdbms(dbUserName, dbUserPassword);           
                    if (!isConnected){     
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);                           
                        break;    
                    }
                    Role rol = new Role();
                    Object[][] internalUser = rol.getInternalUser(rdbm, dbUserName);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(internalUser[0][0].toString())){
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);           
                        //errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);                        
                        out.println(Arrays.toString(internalUser));                                       
                    }
                    String internalUserStr = internalUser[0][0].toString();
                    //UserSession usSess = new UserSession(usr, rdbm);         
                   // String myToken = rdbm.createToken(internalUserStr, dbUserName, dbUserPassword, "Admin");
                    //JsonObject json = Json.createObjectBuilder()
                    //        .add("JWT", myToken).build();
                    
                    JsonObject json = Json.createObjectBuilder()
                            .add("userInfoId", internalUserStr)
                            //.add("myToken", myToken)
                            .build();                                                
                    response.getWriter().write(json.toString());
                    Response.ok().build();                    
                    Response.status(Response.Status.CREATED).entity(json).build();                    
                    break;    
            //                HttpSession mySession = request.getSession(true);
            //                request.setAttribute("m_respuestaUsuarioValido", true);
            //                mySession.setAttribute("dhue8y7d8ue8", true);
            //               ResponseBuilder builder = Response.ok();
            //                return builder.build();
                case "GETUSERROLE":         
                    dbUserName = request.getParameter("dbUserName");                   
                    dbUserPassword = request.getParameter("dbUserPassword");      
                    String userInfoId = request.getParameter("userInfoId");      
                    
                    if (userInfoId==null) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);       
                        errObject = labArr.addValueToArray1D(errObject, "API Error Message: userInfoId is one mandatory param and should be one integer value for this API");                    
                        out.println(Arrays.toString(errObject));
                        return ;
                    }
                    isConnected = rdbm.startRdbms(dbUserName, dbUserPassword);
                    
                    if (!isConnected){
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);           
                            errObject = labArr.addValueToArray1D(errObject, "Error Status Code: "+"Connection not established");                        
                            out.println(Arrays.toString(errObject));              
                            return;
                    }
                    //Response.ok().build();
                    Object[][] recordFieldsByFilter = rdbm.getRecordFieldsByFilter(rdbm, "config", "user_profile", 
                                new String[]{"user_info_id"}, new Object[]{userInfoId}, new String[]{"role_id"});
                        //Object[] recordFieldsByFilter1D =  labArr.array2dTo1d(recordFieldsByFilter);
                    if ("LABPLANET_FALSE".equalsIgnoreCase(recordFieldsByFilter[0][0].toString())){
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        out.println("No user profile defined for internal user "+userInfoId);
                        response.getWriter().write(Arrays.toString(recordFieldsByFilter));                        
                        Response.ok(recordFieldsByFilter).build();
                        return;
                    }
                    Object[] recordsFieldsByFilter1D = labArr.array2dTo1d(recordFieldsByFilter);
                    
                    System.out.println(recordsFieldsByFilter1D);
                    //response.getWriter().write(recordsFieldsByFilter1D);       
//                    Response.ok(recordsFieldsByFilter1D).build(); 
                    
                    JSONArray jArray= new JSONArray();
                    for (int i = 0; i < recordFieldsByFilter.length; i++) {     
                        for (int j = 0; j < recordFieldsByFilter[0].length; j++) {     
                            jArray.add(recordFieldsByFilter[i][j]);
                        }
                    }        
                    System.out.println(jArray);
                    response.getWriter().write(jArray.toJSONString());                    
                        
//                    int[][] data = {{1, 2, 3}, {3, 4, 5}, {4, 5, 6}};
//                    String json = gson.toJson(data);
                    //System.out.println("Data = " + json);                        
                        
                    //System.out.println(jArray);
//                    response.getWriter().write(jArray.toJSONString());                                
/*            String  myData = rdbm.getRecordFieldsByFilterJSON(rdbm, "sample-A-data", "sample",
                    new String[] {"received_by is null"}, new Object[]{""},
                    //new String[] {"sample_id"}, new Object[]{5},
                    new String[] { "sample_id", "sample_config_code", "sampling_comment"});
                  JSONArray jArray= new JSONArray();
                    for (int i = 0; i < recordFieldsByFilter.length; i++) {     
                        jArray.add(recordFieldsByFilter[i][0]);
                       //jArray.put(dataSample[i]);
                    }        
                    System.out.println(jArray);
                    response.getWriter().write(jArray.toJSONString());            
                    Response.ok(myData).build();  
            rdbm.closeRdbms();*/
                    
/*                    String[] dataSample = new String[0];
                    dataSample= labArr.addValueToArray1D(dataSample, "LABPLANET_TRUE");
                    dataSample= labArr.addValueToArray1D(dataSample, "eXAMPLE");
                    dataSample= labArr.addValueToArray1D(dataSample, userInfoId.toString());
                    //dataSample[1]="";
                  JSONArray jArray= new JSONArray();
                    for (int i = 0; i < dataSample.length; i++) {     
                        jArray.add(dataSample[i]);
                       //jArray.put(dataSample[i]);
                    }        


                    //dataSample[1]="";
                  JSONArray jArray= new JSONArray();
                    for (int i = 0; i < dataSample.length; i++) {     
                        jArray.add(dataSample[i]);
                       //jArray.put(dataSample[i]);
                    }        
*/                    
//                    System.out.println(jArray);
//                    response.getWriter().write(jArray.toJSONString());            
            //Response.ok(myData).build();   
            
            return;
                    
//                    Response.status(Response.Status.CREATED).entity(recordFieldsByFilter).build();    
//                    return;
                        /*List<UserRole> lista = new ArrayList();
                        for (int i=0; i<recordFieldsByFilter.length;i++){
                            UserRole ur = new UserRole((int) recordFieldsByFilter[i][0].hashCode(), (String) recordFieldsByFilter[i][1]);
                            lista.add(ur);
                        }
                        return Response.ok(lista).build();                  */
                       // Object[] myJson = JSON.stringify({ x: 5, y: 6 }) ;                   
                default:      
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
                    response.getWriter().write(Arrays.toString(errObject));       
                    return;                           
            }
            return;
        }catch(Exception e){            
            String exceptionMessage = e.getMessage();
            
            Response.serverError();
            Response.status(Response.Status.BAD_REQUEST).build();
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
