/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Administrator
 */
public class LabPLANETRequest {

    public static Object[] areMandatoryParamsInApiRequest(HttpServletRequest request, String[] paramNames){        
        LabPLANETArray labArr = new LabPLANETArray();
        Object [] diagnoses = null;        
        String paramsNotPresent = "";
        for (String curParam: paramNames){
            Boolean notPresent = false;
            String curParamValue = request.getParameter(curParam);
            if (curParamValue==null){notPresent=true;}
            if ("undefined".equals(curParamValue)){notPresent=true;}
            if ("".equals(curParamValue)){notPresent=true;}
            if (notPresent){
                paramsNotPresent=paramsNotPresent+curParam+", ";
            }
        }
        if (paramsNotPresent.length()>0){
            diagnoses = labArr.addValueToArray1D(diagnoses, "LABPLANET_FALSE");
            diagnoses = labArr.addValueToArray1D(diagnoses, paramsNotPresent);
            return diagnoses;
        }else{
            return new Object[]{"LABPLANET_TRUE"};           
        }
    }
    
    public static void sendResponseMissingMandatories(){
        
    }
}
