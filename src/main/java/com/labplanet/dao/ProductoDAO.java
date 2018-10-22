/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.dao;

import com.labplanet.modelo.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class ProductoDAO {
    
    public static List<Producto> getProductos(){
        ArrayList<Producto> lista = new ArrayList<>();
        Producto pr = new Producto(1, "HARINA", 500);
        Producto pr2 = new Producto(2, "PASTA", 500);
        Producto pr3 = new Producto(3, "SARDINAS", 100);
        Producto pr4 = new Producto(4, "MACARRONES", 500);
        Producto pr5 = new Producto(5, "ACEITE", 600);
        lista.add(pr);lista.add(pr2);lista.add(pr3);lista.add(pr4);lista.add(pr5);        
        return lista;
    }
    
}
