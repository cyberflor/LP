/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class LabPLANETFrontEnd {

    /**
     *
     * @param errorStructure
     * @return
     */
    public Object[] responseError(Object[] errorStructure){
        LabPLANETArray labArr = new LabPLANETArray();
        Object[] response = new Object[0];
        response = labArr.addValueToArray1D(response, HttpServletResponse.SC_UNAUTHORIZED);
        response = labArr.addValueToArray1D(response, errorStructure[errorStructure.length-1].toString());        
        return response;
    }

    /**
     *
     * @param errorStructure
     * @param language
     * @param schemaPrefix
     * @return
     */
    public Object[] responseError(Object[] errorStructure, String language, String schemaPrefix){
        LabPLANETArray labArr = new LabPLANETArray();
        Object[] response = new Object[0];
        response = labArr.addValueToArray1D(response, HttpServletResponse.SC_UNAUTHORIZED);
        
        response = labArr.addValueToArray1D(response, errorStructure[errorStructure.length-1].toString());        
        return response;
    }
}
