/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios;

import databases.Rdbms;
import functionalJava.sampleStructure.DataSample;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Administrator
 */
@Path("unReceivedSamples")
public class SampleReception {
    
    //Integer sampleId = 0;
    
    //public SampleReception(Integer sId){    
    //    this.sampleId=sId;
    //}
    
    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    public Response getSampleReception(){        
            Object[][] myObj = new Object[2][3];
            myObj[0][0] = "hola"; myObj[0][1] = 1; myObj[0][2] = "bueno";
            myObj[1][0] = "hola"; myObj[1][1] = "adios"; myObj[1][2] = true;
              
            Rdbms rdbm = new Rdbms();
            Boolean isConnected = rdbm.startRdbms("labplanet", "LabPlanet");
            String  myData = rdbm.getRecordFieldsByFilterJSON(rdbm, "sample-A-data", "sample",
                    new String[] {"received_by is null"}, new Object[]{""},
                    //new String[] {"sample_id"}, new Object[]{5},
                    new String[] { "sample_id", "sample_config_code", "sampling_comment"}, null);
            rdbm.closeRdbms();
            return Response.ok(myData).build();            
    }  
    
    @PUT
    @Path("/{schema}/{user}/{userRole}/{sampleId}")
    //@Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.APPLICATION_JSON)
    public Response receiveSample(@PathParam(
            "schema") String schemaPrefix, @PathParam("user") String userName, @PathParam("userRole") String userRole, @PathParam("sampleId") int sampleId){  
        DataSample smp = new DataSample("");
        Rdbms rdbm = new Rdbms();
        Boolean isConnected = rdbm.startRdbms("labplanet", "LabPlanet");        
        Object[] sampleReceptionLog = smp.sampleReception(rdbm, schemaPrefix, userName, sampleId, userRole);        
        if ("LABPLANET_FALSE".equalsIgnoreCase(sampleReceptionLog[0].toString()))
            return Response.ok(sampleReceptionLog).build();
        else
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
            //return Response.ok().build();
            
    }
    
}