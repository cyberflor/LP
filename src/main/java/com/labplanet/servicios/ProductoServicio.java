/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios;

import com.labplanet.dao.ProductoDAO;
import com.labplanet.modelo.Producto;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Administrator
 */
@Path("productos")
public class ProductoServicio {
    
    private static List<Producto> lista = ProductoDAO.getProductos();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductos(){
        return Response.ok(lista).build();        
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducto(@PathParam("id") int id){
        Producto pr = new Producto();
        pr.setId(id);
        if (lista.contains(pr)){
            for (Producto obj: lista)
                if (obj.getId()==id)
                    return Response.ok(obj).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
        
    }
            
}
