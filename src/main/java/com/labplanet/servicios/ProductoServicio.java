/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.servicios;

import com.labplanet.dao.ProductoDAO;
import com.labplanet.modelo.Producto;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PathParam;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
        Producto fr = new Producto();
        pr.setId(id);           
        if (lista.contains(pr)){    
            for (Producto obj: lista) 
                if (obj.getId()==id) {
                     List<Producto> lista2 = new ArrayList<>();
                    lista2.add(obj);
                    //return Response.ok(lista2).build();} // Si envío el objeto dentro de una lista entonces sí funciona                    
                    return Response.ok(obj).build();} // Si lo envío de esta manera me devuelve un error 500 Internal Server Error
        }
        return Response.status(Response.Status.NOT_FOUND).build();        
    }

    //@Path("/nuevo")
    //@Path("add")
    @POST    
    @Consumes("application/json")
    @Produces("application/json")
    //public Response guardarProducto(){
    public Response guardarProducto(Producto producto){
    //public Response guardarProduct(String s){
        // En este caso he comentado la logica que debe hacer el metodo post.
        // Usando la primera cabecera, sin argumentos, al menos devuelve el response OK aunque al no recibir nada pues no hace nada, pero funciona.
        // Usando la segunda cabecera, con argumento, devuelve error 500 internal server error.
        
        //Producto pr = new Producto();
        //pr.setId(10); pr.setNombre(s); pr.setPrecio(0);
//request.getMethod();
        lista.add(producto);
        //return Response.status(Response.Status.CREATED).entity(lista).build();
       // System.out.println(Response.ok("si").build());
        return Response.ok(lista).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response borrarProducto(@PathParam("id") int id){
        Producto pr = new Producto();
        pr.setId(id);
        if (lista.contains(pr)){
            lista.remove(pr);
           return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();        
    }

}
