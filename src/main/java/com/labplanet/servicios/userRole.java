/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios;

import LabPLANET.utilities.LabPLANETArray;
import com.labplanet.modelo.Producto;
import com.labplanet.modelo.UserRole;
import databases.Rdbms;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
   @Path("/{user}-{pass}-{userId}")
   @Produces("application/json")       
    public Response login(@PathParam("user")String usr, @PathParam("pass")String pw, @PathParam("userId")String userId){
        LabPLANETArray labArr = new LabPLANETArray();
        Rdbms rdbm = new Rdbms();        
        String myUserRoleJSON = "";
        //if (usr.length()==0){return Response.status(Response.Status.NOT_ACCEPTABLE).build();}        
        boolean isConnected = false;
        /*if (!isConnected){            
            myUserRoleJSON = "'{ }'";
            return Response.ok(myUserRoleJSON).build();      
        }*/
        isConnected = rdbm.startRdbms(usr, pw);           
        if (isConnected){
            Response.ok().build();
            Object[][] recordFieldsByFilter = rdbm.getRecordFieldsByFilter(rdbm, "config", "user_profile", new String[]{"user_info_id"}, new Object[]{userId}, new String[]{"user_info_id", "role_id"});
            //Object[] recordFieldsByFilter1D =  labArr.array2dTo1d(recordFieldsByFilter);
            List<UserRole> lista = new ArrayList();
            for (int i=0; i<recordFieldsByFilter.length;i++){
                UserRole ur = new UserRole((int) recordFieldsByFilter[i][0].hashCode(), (String) recordFieldsByFilter[i][1]);
                lista.add(ur);
            }
            return Response.ok(lista).build();                  
           // Object[] myJson = JSON.stringify({ x: 5, y: 6 }) ;
            
        }else{return Response.status(Response.Status.NOT_FOUND).build();}

    }
}
