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
    private String dbUserId;
    private String userRole;
    private Boolean userSessionValidated=false;
    private String rdbm;
    private String token;

    public UserSession(String dbUserId, Rdbms rdbm) {
        this.dbUserId = dbUserId;
        this.rdbm = rdbm.toString();
        this.token = "myNameIs"+dbUserId;
    }

    public String getDbUserId() {
        return dbUserId;
    }

    public void setUserId(String dbUserId) {
        this.dbUserId = dbUserId;
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

    public String getRdbm() {
        return this.rdbm;
    }

    public void setRdbm(String rdbm) {
        this.rdbm = rdbm;
    }
    
    
    
}
