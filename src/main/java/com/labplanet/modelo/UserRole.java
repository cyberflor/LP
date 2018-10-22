/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.modelo;

/**
 *
 * @author Administrator
 */
public class UserRole {
    
    private int UserId;
    private String UserRole;
    
    public UserRole(){
    
    }

    public UserRole(int id, String nombre) {
        this.UserId = id;
        this.UserRole = nombre;        
    }

    public int getId() {
        return UserId;
    }

    public String getNombre() {
        return UserRole;
    }


    public void setId(int id) {
        this.UserId = id;
    }

    public void setUserRole(String nombre) {
        this.UserRole = nombre;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.UserId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserRole other = (UserRole) obj;
        if (this.UserId != other.UserId) {
            return false;
        }
        return true;
    }
            
    
}
