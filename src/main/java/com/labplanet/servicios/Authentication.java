/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios;

import LabPLANET.utilities.LabPLANETWebToken;
import com.labplanet.dao.ProductoDAO;
import com.labplanet.modelo.Producto;
import databases.Rdbms;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Administrator
 */

@Path("/auth")
public class Authentication {
    
    //private static List<Producto> lista = ProductoDAO.getProductos();

   @GET
   @Path("/{user}-{pass}")
   @Produces("application/json")       
    public Response login(@PathParam("user")String usr, @PathParam("pass")String pw){
        
        Rdbms rdbm = new Rdbms();        
       //String createJWT = LabPLANETWebToken.createJWT("hola", "que tal", "adios", 0);
        if (usr.length()==0){return Response.status(Response.Status.NOT_ACCEPTABLE).build();}
        boolean isConnected = false;
        isConnected = rdbm.startRdbms(usr, pw);           
        if (isConnected){                    
//                HttpSession mySession = request.getSession(true);
//                request.setAttribute("m_respuestaUsuarioValido", true);
//                mySession.setAttribute("dhue8y7d8ue8", true);
            return Response.ok().build();
        } else return Response.status(Response.Status.NOT_FOUND).build();} 
        
    @GET
   //@Path("/{user}-{pass}")
    //@consumes("application/json")
   @Produces("application/json")       
    public Response login(String[] args){
        String usr = args[0]; //request.getParameter("userName");
        String pw = args[1]; //request.getParameter("password");
        return Response.ok(usr).build();
   /*     
        Rdbms rdbm = new Rdbms();        
       //String createJWT = LabPLANETWebToken.createJWT("hola", "que tal", "adios", 0);
        if (usr.length()==0){return Response.status(Response.Status.NOT_ACCEPTABLE).build();}
        boolean isConnected = false;
        isConnected = rdbm.startRdbms(usr, pw);           
        if (isConnected){                    
//                HttpSession mySession = request.getSession(true);
//                request.setAttribute("m_respuestaUsuarioValido", true);
//                mySession.setAttribute("dhue8y7d8ue8", true);
            return Response.ok().build();
        } else return Response.status(Response.Status.NOT_FOUND).build();
        */
        } 
         

/*    @GET    
    //@Path("{userName}")   
    //public Response login(@PathParam("userName") String us, @PathParam("userPassword") String pw){
        public Response login(){
        Rdbms rdbm = new Rdbms();
        
        String userName=null;

        boolean isConnected = false;
        try {
            isConnected = rdbm.startRdbms("labplanet", "LabPlanet");           
            //isConnected = rdbm.startRdbms(us, pw);           
            if (isConnected){return Response.ok().build();}
            else return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ClassNotFoundException|IllegalAccessException|InstantiationException|SQLException|NamingException ex) {
            
            return Response.status(Response.Status.NOT_FOUND).build();}
        
        
    }    
*/
    
}
