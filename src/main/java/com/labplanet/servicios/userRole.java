/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios;

import databases.Rdbms;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author Administrator
 */
@Path("/userRole")
public class userRole {
    
   @GET
   @Path("/{user}-{pass}")
   @Produces("application/xml")       
    public Response login(@PathParam("user")String usr, @PathParam("pass")String pw){
        Rdbms rdbm = new Rdbms();        
        
        if (usr.length()==0){return Response.status(Response.Status.NOT_ACCEPTABLE).build();}

        boolean isConnected = false;
        try {
            isConnected = rdbm.startRdbms(usr, pw);           
            //isConnected = rdbm.startRdbms(us, pw);           
            if (isConnected){return Response.ok().build();}
            else return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ClassNotFoundException|IllegalAccessException|InstantiationException|SQLException|NamingException ex) {

        Object[][] recordFieldsByFilter = rdbm.getRecordFieldsByFilter(rdbm, "", "config", new String[]{"user_info_id"}, new Object[]{}, new String[]{"role_id"});
        
        
        return Response.status(Response.Status.NOT_FOUND).build();}                
    }     
    
}
