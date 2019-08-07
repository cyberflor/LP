/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.user;

import databases.Rdbms;
import LabPLANET.utilities.LPPlatform;
/**
 *
 * @author Administrator
 */
public class Role {

    
    /**
     *
     * @param userName
     * @return
     */
    public static final Object[][] getInternalUser( String userName) {
        return Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_APP, "users", new String[]{"user_name"}, new Object[]{userName},
                new String[]{"person_name"});        
    }
    
    
}
