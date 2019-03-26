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
public class LPFrontEnd {

    /**
     *
     * @param errorStructure
     * @return
     */
    public static Object[] responseError(Object[] errorStructure){
        Object[] responseObj = new Object[0];
        responseObj = LPArray.addValueToArray1D(responseObj, HttpServletResponse.SC_UNAUTHORIZED);
        responseObj = LPArray.addValueToArray1D(responseObj, errorStructure[errorStructure.length-1].toString());        
        return responseObj;
    }

    /**
     *
     * @param errorStructure
     * @param language
     * @param schemaPrefix
     * @return
     */
    public static Object[] responseError(Object[] errorStructure, String language, String schemaPrefix){
        Object[] responseObj = new Object[0];
        responseObj = LPArray.addValueToArray1D(responseObj, HttpServletResponse.SC_UNAUTHORIZED);
        
        responseObj = LPArray.addValueToArray1D(responseObj, errorStructure[errorStructure.length-1].toString());        
        return responseObj;
    }
}
