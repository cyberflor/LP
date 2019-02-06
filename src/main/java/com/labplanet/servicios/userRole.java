/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios;

import LabPLANET.utilities.LabPLANETArray;
import com.labplanet.modelo.UserRole;
import databases.Rdbms;
import java.util.ArrayList;
import java.util.List;
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
    
    /**
     *
     * @param usr
     * @param pw
     * @param userId
     * @return
     */
    @GET
   @Path("/roleList/{user}-{pass}-{userId}")
   @Produces("application/json")       
    public Response userRoleRoleList(@PathParam("user")String usr, @PathParam("pass")String pw, @PathParam("userId")String userId){
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

    /**
     *
     * @param usr
     * @param pw
     * @return
     */
    @GET
   @Path("/userProfile/{user}-{pass}")
   @Produces("application/json")       
    public Response userProfile(@PathParam("user")String usr, @PathParam("pass")String pw){
        LabPLANETArray labArr = new LabPLANETArray();
        String[] userProfileData = new String[0];        
        String userId = "\"UserId\""+":"+"1";
        //UserSession usSes = new UserSession(usr, pw);
        userProfileData = labArr.addValueToArray1D(userProfileData, userId);
        
        if (1==1) return Response.ok(userProfileData).build();   
        
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
