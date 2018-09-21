/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labplanet.modelo;

import databases.Rdbms;

/**
 *
 * @author Administrator
 */
public class UserSession {
    private String userId;
    private String userRole;
    private Boolean userSessionValidated=false;
    private Rdbms rdbm;

    public UserSession(String userId, Rdbms rdbm) {
        this.userId = userId;
        this.rdbm = rdbm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Boolean getUserSessionValidated() {
        return userSessionValidated;
    }

    public void setUserSessionValidated(Boolean userSessionValidated) {
        this.userSessionValidated = userSessionValidated;
    }

    public Rdbms getRdbm() {
        return rdbm;
    }

    public void setRdbm(Rdbms rdbm) {
        this.rdbm = rdbm;
    }
    
    
    
}
