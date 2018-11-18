/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontEnd.api;

import LabPLANET.utilities.LabPLANETArray;
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

/**
 *
 * @author Administrator
 */
public class authenticationAPI extends HttpServlet {

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
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        LabPLANETArray labArr = new LabPLANETArray();
    
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
                   Rdbms rdbm = new Rdbms();        

                    boolean isConnected = false;
                    isConnected = rdbm.startRdbms(dbUserName, dbUserPassword);           
                    if (isConnected){      
                        //UserSession usSess = new UserSession(usr, rdbm);         
                        String myToken = rdbm.createToken("1", dbUserName, "Admin");
                        JsonObject json = Json.createObjectBuilder()
                                .add("JWT", myToken).build();
                        Response.status(Response.Status.CREATED).entity(json).build();
                        break;    
            //                HttpSession mySession = request.getSession(true);
            //                request.setAttribute("m_respuestaUsuarioValido", true);
            //                mySession.setAttribute("dhue8y7d8ue8", true);
            //               ResponseBuilder builder = Response.ok();
            //                return builder.build();
                    } else{ 
                        Response.status(Response.Status.NOT_FOUND).build();
                        break;    
                    }                                                
                default:      
                    errObject = frontEnd.APIHandler.actionNotRecognized(errObject, actionName, response);
                    out.println(Arrays.toString(errObject));                   
                    return;                           
            }
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
