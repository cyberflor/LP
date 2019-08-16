/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

/**
 *
 * @author Administrator
 */
public class dbObjectsAppTables {
    
    public static final String TABLE_NAME_APP_USERS = "users";
        public static final String FIELD_NAME_APP_USERS_ESIGN = "e_sign";
        public static final String FIELD_NAME_APP_USERS_PASSWORD = "password";
        public static final String FIELD_NAME_APP_USERS_PERSON_NAME = "person_name";
        public static final String FIELD_NAME_APP_USERS_USER_NAME = "user_name";
    private Object[][] tableAppUsersScript(String schemaPrefix){
        //Falta implementarlo
        String tableName = TABLE_NAME_APP_USERS;
        return new Object[0][0];
    } 
    public static final String TABLE_APP_APP_SESSION="app_session";
        public static final String FLD_APP_APP_SESSION_SESSION_ID="session_id";
        public static final String FLD_APP_APP_SESSION_DATE_STARTED="data_started";
        public static final String FLD_APP_APP_SESSION_PERSON="person";
        public static final String FLD_APP_APP_SESSION_ROLE_NAME="role_name";
    private Object[][] tableAppAppSessionScript(String schemaPrefix){
        //Falta implementarlo
        String tableName = TABLE_APP_APP_SESSION;
        return new Object[0][0];
    } 
    
}
