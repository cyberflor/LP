/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.user;

import LabPLANET.utilities.LPPlatform;
import databases.Rdbms;

/**
 *
 * @author Administrator
 */
public class UserAndRolesViews {
    private UserAndRolesViews(){    throw new IllegalStateException("Utility class");}    
    
    public static final String getUserByPerson(String person){
        Object[][] userByPerson = Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_APP, "users", 
                new String[]{"person_name"}, new String[]{person}, new String[]{"user_name"}, new String[]{"user_name"});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(userByPerson[0][0].toString())){return LPPlatform.LAB_FALSE;}
        return userByPerson[0][0].toString();
    }
    
}
