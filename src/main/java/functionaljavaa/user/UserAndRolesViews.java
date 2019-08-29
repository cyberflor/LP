/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionaljavaa.user;

import lbplanet.utilities.LPPlatform;
import databases.Rdbms;
import databases.DbObjectsAppTables;

/**
 *
 * @author Administrator
 */
public class UserAndRolesViews {
    private UserAndRolesViews(){    throw new IllegalStateException("Utility class");}             
         
    public static final String getUserByPerson(String person){
        Object[][] userByPerson = Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_APP, DbObjectsAppTables.TABLE_NAME_APP_USERS, 
                new String[]{DbObjectsAppTables.FIELD_NAME_APP_USERS_PERSON_NAME}, new String[]{person}, new String[]{DbObjectsAppTables.FIELD_NAME_APP_USERS_USER_NAME}, new String[]{DbObjectsAppTables.FIELD_NAME_APP_USERS_USER_NAME});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(userByPerson[0][0].toString())){return LPPlatform.LAB_FALSE;}
        return userByPerson[0][0].toString();
    }

    public static final String getPersonByUser(String userName){
        Object[][] personByUser = Rdbms.getRecordFieldsByFilter(LPPlatform.SCHEMA_APP, DbObjectsAppTables.TABLE_NAME_APP_USERS, 
                new String[]{DbObjectsAppTables.FIELD_NAME_APP_USERS_USER_NAME}, new String[]{userName}, new String[]{DbObjectsAppTables.FIELD_NAME_APP_USERS_PERSON_NAME}, new String[]{DbObjectsAppTables.FIELD_NAME_APP_USERS_PERSON_NAME});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(personByUser[0][0].toString())){return LPPlatform.LAB_FALSE;}
        return personByUser[0][0].toString();
    }
    

    /**
     * This method makes no sense once the Rdbms instance is created once by singleton pattern <br>
     * This method would be replaced by checking user and password against the info in the  token
     * @param user
     * @param pass
     * @return
     */
    public static final Object[] isValidUserPassword(String user, String pass) {
        return Rdbms.existsRecord(LPPlatform.SCHEMA_APP, DbObjectsAppTables.TABLE_NAME_APP_USERS, 
                new String[]{DbObjectsAppTables.FIELD_NAME_APP_USERS_USER_NAME, DbObjectsAppTables.FIELD_NAME_APP_USERS_PASSWORD}, new Object[]{user, pass});
    }
    
    
    
}
