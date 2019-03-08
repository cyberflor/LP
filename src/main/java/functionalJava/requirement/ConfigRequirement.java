/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.requirement;

/**
 *
 * @author Administrator
 */
public class ConfigRequirement {
    
    /**
     *
     * @param procedure
     * @param pVersion
     */
    public void _getConfigObject2( String procedure, Integer pVersion){
    }
   /*
        try {
            String methodName = "RequirementConfigObjects-ProcessRequest";
            
            String id ="";           
            Requirement req = new Requirement();
            
            String query = "SELECT schema_name, table_name, object_name, field_name_1, field_value_1, field_name_2, field_value_2 "
                    + " FROM requirements.procedure_config_object"
                    + " where procedure =? and version=? "
                    + " and active=? and ready=? "
                    + " order by object_id ";
            
            ResultSet res = rdbm.prepRdQuery(query, new Object [] {procedure, pVersion, true, true});
            res.last();
            Integer i;
            i = res.getRow();
            
            String newEntry = " query returns " + i+1 + " records.";
//            try {req.requirementsLogEntry(methodName, newEntry,1);
//            } catch (IOException ex) {
//                Logger.getLogger(RequirementConfigObjects.class.getName()).log(Level.SEVERE, null, ex);}
//            
            
            out.println(i.toString());
            // Create the root node for the procedure being deployed.
            res.first();
            for (Integer j=0; j<i;j++){
                String schemaName = res.getString("schema_name");
                String tableName = res.getString("table_name");
                String objectName = res.getString("object_name");
                String fieldName1 = res.getString("field_name_1");
                String fieldValue1 = res.getString("field_value_1");
                String fieldName2 = res.getString("field_name_2");
                String fieldValue2 = res.getString("field_value_2");                                
                
                newEntry = j.toString()+"/"+i.toString()+ " get object "+objectName;
//                try {req.requirementsLogEntry(methodName, newEntry,2);
//                } catch (IOException ex) {
//                    Logger.getLogger(RequirementConfigObjects.class.getName()).log(Level.SEVERE, null, ex);}
//                
                String foreignTableName = "user_info";
                Object[] diagnoses = rdbm.existsRecord(rdbm, "config", foreignTableName, 
                        new String[]{fieldName1}, new Object[]{fieldValue1});
                if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                    schemaName = "\"" + schemaName + "\"";
                    query = "Insert into " + schemaName + "."+foreignTableName+" (user_info_id) values (?)";
                    try{        
                        id = rdbm.prepUpQueryK(query, new Object[]{fieldValue1}, 1);
                    }
                    catch(SQLException er){String ermessage=er.getErrorCode()+er.getLocalizedMessage()+er.getCause();} catch (Throwable ex) {            
                        Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);
                        String ermessage = "";                     
                        newEntry = " for "+foreignTableName+"  " + fieldValue1 + ". Error detected: " + ermessage;
                    }    
                }
                else{newEntry = " The "+foreignTableName+" " + fieldValue1 + " already exist";}   
//                try {
//                    req.requirementsLogEntry(methodName, newEntry,3);
//                } catch (IOException ex1) {
//                    Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex1);
//                }
//
                foreignTableName = "role";
                diagnoses = rdbm.existsRecord(rdbm, "config", foreignTableName, 
                        new String[]{fieldName2}, new Object[]{fieldValue2});
                if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){
                    schemaName = "\"" + schemaName + "\"";
                    query = "Insert into " + schemaName + "."+foreignTableName+" (user_info_id) values (?)";
                    try{        
                        id = rdbm.prepUpQueryK(query, new Object[]{fieldValue2}, 1);
                    }
                    catch(SQLException er){String ermessage=er.getErrorCode()+er.getLocalizedMessage()+er.getCause();} catch (Throwable ex) {            
                        Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);
                        String ermessage = "";                     
                        newEntry = " for "+foreignTableName+"  " + fieldValue2 + ". Error detected: " + ermessage;
                    }    
                }
                else{newEntry = " The "+foreignTableName+" " + fieldValue2 + " already exist";}   
//                try {
//                    req.requirementsLogEntry(methodName, newEntry,3);
//                } catch (IOException ex1) {
//                    Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex1);
//                }
//                
                //user role    
                Integer userRoleCount = 0;
                diagnoses = rdbm.existsRecord(rdbm, "config", tableName, new String[]{fieldName1, fieldName2}, new Object[]{fieldValue1, fieldValue2});
                if (!"LABPLANET_TRUE".equalsIgnoreCase(diagnoses[0].toString())){                              
                    schemaName = "\"" + schemaName + "\"";
                    if ( userRoleCount==0){
                        query="select count(role_id) as cont from config.user_profile";                        
                        res = rdbm.prepRdQuery(query, new Object [] {});
                        res.first();
                        userRoleCount = res.getInt("cont");
                        }
                    userRoleCount++;
                    query = "Insert into " + schemaName + "."+tableName+" ("+fieldName1+", "+fieldName2+", user_profile_id) values (?,?,?)";
                    try{        
                        id = rdbm.prepUpQueryK(query, new Object[]{fieldValue1, fieldValue2, userRoleCount}, 1);
                        userRoleCount++;
                    }
                    catch(SQLException er){String ermessage=er.getErrorCode()+er.getLocalizedMessage()+er.getCause();} catch (Throwable ex) {            
                        Logger.getLogger(Requirement.class.getName()).log(Level.SEVERE, null, ex);
                        String ermessage = "";                     
                        newEntry = " for "+tableName+"  " + fieldValue1 + " "+fieldValue2+". Error detected: " + ermessage;
                    }     
                }    
                res.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        
    }
      */      
    
}
