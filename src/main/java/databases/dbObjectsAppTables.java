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

    public enum SchemaApppTableAppSession{
        TABLE_NAME("app_session", "CREATE TABLE app.app_session (#FLDS) WITH (" +
                "    OIDS = FALSE" +
                ")" +
                "TABLESPACE pg_default;" +
                "" +
                "ALTER TABLE app.app_session" +
                "    OWNER to labplanet;")
        , FIELD_SESSION_ID("session_id", "integer NOT NULL DEFAULT nextval('app.app_session_session_id_seq1'::regclass)")
        , FIELD_DATA_STARTED("data_started", "date")
        , FIELD_PERSON("person", "character varying COLLATE pg_catalog.\"default\"")
        , FIELD_ROLE_NAME("role_name", "character varying COLLATE pg_catalog.\"default\"");
        
        private SchemaApppTableAppSession(String dbObjName, String dbObjType){
            this.dbObjName=dbObjName;
            this.dbObjType=dbObjType;
        }

        public String getName(){
            return this.dbObjName;
        }
        private String[] getDbFieldDefinition(){
            return new String[]{this.dbObjName, this.dbObjType};
        }
        private final String dbObjName;             
        private final String dbObjType;             
        
    }
        
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

    
}
