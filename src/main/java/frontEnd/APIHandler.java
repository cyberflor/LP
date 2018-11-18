/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontEnd;

import LabPLANET.utilities.LabPLANETArray;
import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class APIHandler {
    
    public static  String[] actionNotRecognized(String[] errLog, String actionName, HttpServletResponse response){       
        LabPLANETArray labArr = new LabPLANETArray();
        
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);                
        errLog = labArr.addValueToArray1D(errLog, "Error Status Code: "+HttpServletResponse.SC_BAD_REQUEST);
        errLog = labArr.addValueToArray1D(errLog, actionName+" Not Declared in this API");                    
                 
        return errLog;
    }        
    
    public static void _handleNull (){
    }


}
