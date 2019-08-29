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
public class DbObjects {
    
    public Object[] createPlatformSchemas(String schemaPrefix){
    
        Rdbms.stablishDBConection();
 /*       Rdbms.
            CREATE SCHEMA "process-us-config"
            AUTHORIZATION labplanet;

            GRANT USAGE ON SCHEMA "process-us-config" TO dzambrana;

            GRANT ALL ON SCHEMA "process-us-config" TO labplanet;*/
        return new Object[0];
    }
    
}
