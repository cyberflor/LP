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
public class _UserSession {
    private String userId;
    private String dbUserId;
    private String userRole;
    private Boolean userSessionValidated=false;
    private String rdbm;
    private String token;

    /**
     *
     * @param dbUserId
     * @param rdbm
     */
    public _UserSession(String dbUserId, Rdbms rdbm) {
        this.dbUserId = dbUserId;
        this.rdbm = rdbm.toString();
        this.token = "myNameIs"+dbUserId;
    }

    /**
     *
     * @return
     */
    public String getDbUserId() {
        return dbUserId;
    }

    /**
     *
     * @param dbUserId
     */
    public void setUserId(String dbUserId) {
        this.dbUserId = dbUserId;
    }

    /**
     *
     * @return
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     *
     * @param userRole
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     *
     * @return
     */
    public Boolean getUserSessionValidated() {
        return userSessionValidated;
    }

    /**
     *
     * @param userSessionValidated
     */
    public void setUserSessionValidated(Boolean userSessionValidated) {
        this.userSessionValidated = userSessionValidated;
    }

    /**
     *
     * @return
     */
    public String getRdbm() {
        return this.rdbm;
    }

    /**
     *
     * @param rdbm
     */
    public void setRdbm(String rdbm) {
        this.rdbm = rdbm;
    }
    
    
    
}
