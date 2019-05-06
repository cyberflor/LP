/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class LPHttp {

    public static HttpServletRequest requestPreparation(HttpServletRequest request){
        try {
            request.setCharacterEncoding(LPPlatform.LAB_ENCODER_UTF8);                    
            return request;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LPHttp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return request;
    }

    public static HttpServletResponse responsePreparation(HttpServletResponse response){
        response.setContentType("application/json");
        response.setCharacterEncoding(LPPlatform.LAB_ENCODER_UTF8);

        ResourceBundle prop = ResourceBundle.getBundle("parameter.config.config");
        String frontendUrl = prop.getString("frontend_url");
        response.setHeader("CORS_ORIGIN_ALLOW_ALL", "True");                
        response.setHeader("CORS_ALLOW_CREDENTIALS", "False");                
        response.setHeader("Access-Control-Allow-Origin", frontendUrl);
        response.setHeader("Access-Control-Allow-Methods", "GET");                
        return response;
    }    
    
    public static Object[] areMandatoryParamsInApiRequest(HttpServletRequest request, String[] paramNames){        
        Object [] diagnoses = null;        
        StringBuilder paramsNotPresent = new StringBuilder(); 
        for (String curParam: paramNames){
            Boolean notPresent = false;
            String curParamValue = request.getParameter(curParam);
            if (curParamValue==null){notPresent=true;}
            if ("undefined".equals(curParamValue)){notPresent=true;}
            if ("".equals(curParamValue)){notPresent=true;}
            if (notPresent){
                paramsNotPresent.append(curParam).append(", ");
            }
        }
        if (paramsNotPresent.length()>0){
            diagnoses = LPArray.addValueToArray1D(diagnoses, LPPlatform.LAB_FALSE);
            diagnoses = LPArray.addValueToArray1D(diagnoses, paramsNotPresent);
            return diagnoses;
        }else{
            return new Object[]{LPPlatform.LAB_TRUE};           
        }
    }
    
    public static void sendResponseMissingMandatories(){
        // Not implemented yet
    }
}
