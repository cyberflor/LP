/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.user;

import labPLANET.utilities.LabPLANETPlatform;
import databases.Rdbms;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author Administrator
 */
public class UserSecurity {
    
    public UserSecurity(){}

    public Object[] setUserEsig(Rdbms rdbm, String user, String pass, String newEsign){
        
        try {
            Object[] diagnoses = new Object[2];
            LabPLANETPlatform plat = new LabPLANETPlatform();
            diagnoses[0] = false;
            String schemaName = "config";
            String tableName = "user_info";
            
            if (user==null || "null".equalsIgnoreCase(user)){diagnoses[1]="User Name cannot be set to null"; return diagnoses;}
            if (pass==null || "null".equalsIgnoreCase(pass)){diagnoses[1]="password cannot be set to null, user has to be validated"; return diagnoses;}
            if (newEsign==null || "null".equalsIgnoreCase(newEsign)){diagnoses[1]="new word for eSign cannot be set to null"; return diagnoses;}
            
            Object[] validUserPassword = isValidUserPassword(user, pass);
            if (!(Boolean) validUserPassword[0]){
                diagnoses[1] = "Invalid password for the user "+user;   
            }

            Object[] userExists = rdbm.existsRecord(rdbm, schemaName, tableName, new String[]{"user_info_name"}, new String[]{user});
            String diagn = userExists[0].toString();
            if ("LABPLANET_FALSE".equalsIgnoreCase(diagn)){
                diagnoses[1] = userExists[5];           
                return diagnoses;
            }
            Object[] encrypted = plat.encryptString(newEsign);
            if (!(Boolean) encrypted[0]){
                diagnoses[1] = encrypted[1];           
                return diagnoses;                
            }
            Object[] updateRecordFieldsByFilter = rdbm.updateRecordFieldsByFilter(rdbm, schemaName, tableName, 
                    new String[]{"esign_value"}, new Object[]{encrypted[1]}, 
                    new String[]{"user_info_name"}, new String[]{user});
            if ("LABPLANET_FALSE".equalsIgnoreCase(updateRecordFieldsByFilter[0].toString())){            
                diagnoses[1] = updateRecordFieldsByFilter[5];           
                return diagnoses;
            }
            diagnoses[0] = true;
            diagnoses[1] = "new eSign set for the user "+user;
            return diagnoses;                                                   
        } catch (SQLException|ClassNotFoundException|IllegalAccessException|InstantiationException|NamingException ex) {
            Logger.getLogger(UserSecurity.class.getName()).log(Level.SEVERE, null, ex);
            Object[] diagnoses = new Object[2];
            diagnoses[0] = false;
            diagnoses[1] = ex.getMessage();
            return diagnoses;
        }    
    }
    
    public Object[] isValidESign(Rdbms rdbm, String user, String eSign){
        Object[] diagnoses = new Object[2];  
        diagnoses[0]=false;
        LabPLANETPlatform plat = new LabPLANETPlatform();
        String schemaName = "config";
        String tableName = "user_info";
            
        if (user==null || "null".equalsIgnoreCase(user)){diagnoses[1]="User Name cannot be set to null"; return diagnoses;}
        if (eSign==null || "null".equalsIgnoreCase(eSign)){diagnoses[1]="new word for eSign cannot be set to null"; return diagnoses;}

        
        Object[][] userExists;
        userExists = rdbm.getRecordFieldsByFilter(rdbm, schemaName, tableName, new String[]{"user_info_name"}, new String[]{user}, new String[]{"user_info_name", "user_info_name", "user_info_name", "esign_value"});
        String diagn = (String) userExists[0][3];
        if (diagn.equalsIgnoreCase("FALSE")) {
            
            diagnoses[1] = userExists[0][5];           
            return diagnoses;
        }
        Object[] decrypted = plat.decryptString((String) userExists[0][3]);
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

    public Object[] isValidESign(Rdbms rdbm, String user, String pass, String eSign){
        try {
            Object[] diagnoses = new Object[2];
            diagnoses[0]=false;
            Object[] validUserPassword = isValidUserPassword(user, pass);
            if (!(Boolean) validUserPassword[0]){
                diagnoses[1] = "Invalid password for the user "+user;   
            }            
            return isValidESign(rdbm, user, eSign);
            
        } catch (ClassNotFoundException|IllegalAccessException|InstantiationException|SQLException|NamingException ex) {
            Logger.getLogger(UserSecurity.class.getName()).log(Level.SEVERE, null, ex);
            Object[] diagnoses = new Object[2];
            diagnoses[0]=false;
            diagnoses[1]= ex.getMessage();
            return diagnoses;
        }
    }
    
    public Object[] isValidUserPassword(String user, String pass) throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, NamingException {            
        Object[] diagnoses = new Object[2];
        diagnoses[0]=false;
        Rdbms rdbms2 = new Rdbms();
        
        Boolean startRdbms = rdbms2.startRdbms( user, pass);        
        if(startRdbms){
            diagnoses[0] = true; diagnoses[1] = "The eSign password is correct"; 
        }else{
            diagnoses[0] = false; diagnoses[1]="eSign incorrect for the user "+user;               
        }    
        Connection connection = rdbms2.getConnection();
        connection.close();
        rdbms2.closeRdbms();        
        return diagnoses;
        
    }    
    
}
