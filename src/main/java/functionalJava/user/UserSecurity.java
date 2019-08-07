/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.user;

import LabPLANET.utilities.LPPlatform;
import databases.Rdbms;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Administrator
 */
public class UserSecurity {
    
    /**
     *
     */
    public UserSecurity(){
    // Not implemented yet
    }

    /**
     *
     * @param user
     * @param pass
     * @param newEsign
     * @return
     */
    public Object[] _setUserEsigWithLabtimus( String user, String pass, String newEsign){
        
        Object[] diagnoses = new Object[2];
        diagnoses[0] = false;
        String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
        String tableName = "user_info";
        if (user==null || "null".equalsIgnoreCase(user)){diagnoses[1]="User Name cannot be set to null"; return diagnoses;}
        if (pass==null || "null".equalsIgnoreCase(pass)){diagnoses[1]="password cannot be set to null, user has to be validated"; return diagnoses;}
        if (newEsign==null || "null".equalsIgnoreCase(newEsign)){diagnoses[1]="new word for eSign cannot be set to null"; return diagnoses;}
        Object[] validUserPassword = isValidUserPassword(user, pass);
        if (!(Boolean) validUserPassword[0]){
            diagnoses[1] = "Invalid password for the user "+user;
        }
        Object[] userExists = Rdbms.existsRecord(schemaConfigName, tableName, new String[]{"user_info_name"}, new String[]{user});
        String diagn = userExists[0].toString();
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagn)){
            diagnoses[1] = userExists[5];
            return diagnoses;
        }    
        Object[] encrypted = LPPlatform.encryptString(newEsign);
        if (!(Boolean) encrypted[0]){
            diagnoses[1] = encrypted[1];
            return diagnoses;
        }
        Object[] updateRecordFieldsByFilter = Rdbms.updateRecordFieldsByFilter(schemaConfigName, tableName,
                new String[]{"esign_value"}, new Object[]{encrypted[1]},
                new String[]{"user_info_name"}, new String[]{user});
        if ("LABPLANET_FALSE".equalsIgnoreCase(updateRecordFieldsByFilter[0].toString())){
            diagnoses[1] = updateRecordFieldsByFilter[5];
            return diagnoses;
        }
        diagnoses[0] = true;
        diagnoses[1] = "new eSign set for the user "+user;
        return diagnoses;
    }
    
    /**
     *
     * @param user
     * @param eSign
     * @return
     */
    public Object[] isValidESign( String user, String eSign){
        Object[] diagnoses = new Object[2];  
        diagnoses[0]=false;
        String schemaConfigName = LPPlatform.SCHEMA_CONFIG;
        String tableName = "user_info";
            
        if (user==null || "null".equalsIgnoreCase(user)){diagnoses[1]="User Name cannot be set to null"; return diagnoses;}
        if (eSign==null || "null".equalsIgnoreCase(eSign)){diagnoses[1]="new word for eSign cannot be set to null"; return diagnoses;}

        
        Object[][] userExists;
        userExists = Rdbms.getRecordFieldsByFilter(schemaConfigName, tableName, new String[]{"user_info_name"}, new String[]{user}, new String[]{"user_info_name", "user_info_name", "user_info_name", "esign_value"});
        String diagn = (String) userExists[0][3];
        if (diagn.equalsIgnoreCase("FALSE")) {
            
            diagnoses[1] = userExists[0][5];           
            return diagnoses;
        }
        Object[] decrypted = LPPlatform.decryptString((String) userExists[0][3]);
        if (!(Boolean) decrypted[0]){
            diagnoses[1] = decrypted[1];           
            return diagnoses;                
        }
        if (eSign.equalsIgnoreCase((String) decrypted[1])){
            diagnoses[0] = true; diagnoses[1] = "The eSign password is correct"; return diagnoses;                       
        }else{
            diagnoses[0] = false; diagnoses[1]="eSign incorrect for the user "+user; return diagnoses;
        }
    }

    /**
     *
     * @param user
     * @param pass
     * @param eSign
     * @return
     */
    public Object[] isValidESign( String user, String pass, String eSign){
        Object[] diagnoses = new Object[2];
        diagnoses[0]=false;
        Object[] validUserPassword = isValidUserPassword(user, pass);
        if (!(Boolean) validUserPassword[0]){
            diagnoses[1] = "Invalid password for the user "+user;   
        }            
        return isValidESign(user, eSign);
    }
    
    /**
     * This method makes no sense once the Rdbms instance is created once by singleton pattern <br>
     * This method would be replaced by checking user and password against the info in the  token
     * @param user
     * @param pass
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws NamingException
     */
    public Object[] isValidUserPassword(String user, String pass) {            
        //Object[][] recordFieldsByFilter = Rdbms.getRecordFieldsByFilter(, new String[]{"user_name"});
        return Rdbms.existsRecord("app", "users", new String[]{"user_name", "password"}, new Object[]{user, pass});
    }    
    
}
