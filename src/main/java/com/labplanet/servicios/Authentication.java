/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios;

import com.labplanet.modelo.UserSession;
import databases.Rdbms;
import java.math.BigDecimal;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;


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
    public Response login(@PathParam("user") String usr, @PathParam("pass") String pw){
        
        Rdbms rdbm = new Rdbms();        
       //String createJWT = LabPLANETWebToken.createJWT("hola", "que tal", "adios", 0);
        if (usr.length()==0){return Response.status(Response.Status.NOT_ACCEPTABLE).build();}
        boolean isConnected = false;
        isConnected = rdbm.startRdbms(usr, pw);           
        if (isConnected){      
            //UserSession usSess = new UserSession(usr, rdbm);         
            String myToken = rdbm.createToken("1", usr, "Admin");
            JsonObject json = Json.createObjectBuilder()
                    .add("JWT", myToken).build();
            return Response.status(Response.Status.CREATED).entity(json).build();
//                HttpSession mySession = request.getSession(true);
//                request.setAttribute("m_respuestaUsuarioValido", true);
//                mySession.setAttribute("dhue8y7d8ue8", true);
//               ResponseBuilder builder = Response.ok();
//                return builder.build();
        } else return Response.status(Response.Status.NOT_FOUND).build();} 
        
    //1@GET
   //@Path("/{user}-{pass}")
    //@consumes("application/json")
   //@Produces("application/json")       
    //1public Response login(String[] args){
    //1    String usr = args[0]; //request.getParameter("userName");
    //1    String pw = args[1]; //request.getParameter("password");
    //1    return Response.ok().build();
        //return Response.ok(usr).build();
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
    //1    } 
         

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
