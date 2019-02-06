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
    
    /**
     *
     */
    public UserRole(){
    
    }

    /**
     *
     * @param id
     * @param nombre
     */
    public UserRole(int id, String nombre) {
        this.UserId = id;
        this.UserRole = nombre;        
    }

    /**
     *
     * @return
     */
    public int getId() {
        return UserId;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return UserRole;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.UserId = id;
    }

    /**
     *
     * @param nombre
     */
    public void setUserRole(String nombre) {
        this.UserRole = nombre;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.UserId;
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
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
