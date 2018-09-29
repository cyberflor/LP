/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sop;

import databases.Rdbms;
import functionalJava.user.UserProfile;
import labPLANET.utilities.LabPLANETArray;
import labPLANET.utilities.LabPLANETPlatform;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class UserSop {
    
    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "user_sop";  
    
    public void UserSop(){}

    public String[] userSopCertifiedBySopId(Rdbms rdbm, String schemaPrefixName, String userInfoId, String sopId ) throws SQLException{
        String[] diagnoses = new String[3];
        
        UserProfile usProf = new UserProfile();
        String[] userSchemas = usProf.getAllUserSchemaPrefix(rdbm, userInfoId);
        Boolean schemaIsCorrect = false;
        for (String us: userSchemas){
            if (us.equalsIgnoreCase(schemaPrefixName)){schemaIsCorrect=true;break;}            
        }
        if (!schemaIsCorrect){
            diagnoses[0]="ERROR";
            diagnoses[1]="The user "+userInfoId+" has no roles assigned for working on schema"+schemaPrefixName;
            return diagnoses;
        }
        String[] userSchema = new String[1];
        userSchema[0]=schemaPrefixName;
        
        String[] filterFieldName = new String[2];
        Object[] filterFieldValue = new Object[2];
        String[] fieldsToReturn = new String[4];

        fieldsToReturn[0] = "sop_id";
        fieldsToReturn[1] = "sop_name";
        fieldsToReturn[2] = "status";
        fieldsToReturn[3] = "light";
        filterFieldName[0]="user_id";
        filterFieldValue[0]=userInfoId;        
        filterFieldName[1]="sop_id";
        filterFieldValue[1]=sopId;                
        Object[][] getUserProfileFieldValues = getUserProfileFieldValues(rdbm, filterFieldName, filterFieldValue, fieldsToReturn, userSchema);   
        if (getUserProfileFieldValues.length<=0){
            diagnoses[0]="ERROR";
            diagnoses[1]="The user "+userInfoId+" has no the sop "+sopId+ " assigned to.";
            return diagnoses;
        }
        if (getUserProfileFieldValues[0][3].toString().contains("GREEN")){
            diagnoses[0]="PASS";   
            diagnoses[2]="xf209@FontAwesome";            
            return diagnoses;
        }
        else{
            diagnoses[0]="NOTPASS";
            diagnoses[1]="The user "+userInfoId+" has the sop "+getUserProfileFieldValues[0][1].toString()+ " assigned to which current status is "+getUserProfileFieldValues[0][2].toString()+" and the light is "+getUserProfileFieldValues[0][3].toString();
            diagnoses[2]="xf209@FontAwesome"; 
            return diagnoses;
        }               
    }

    public String[] userSopCertifiedBySopName(Rdbms rdbm, String schemaPrefixName, String userInfoId, String sopName ) throws SQLException{
        String[] diagnoses = new String[3];
        
        UserProfile usProf = new UserProfile();
        String[] userSchemas = usProf.getAllUserSchemaPrefix(rdbm, userInfoId);
        Boolean schemaIsCorrect = false;
        for (String us: userSchemas){
            if (us.equalsIgnoreCase(schemaPrefixName)){schemaIsCorrect=true;break;}            
        }
        if (!schemaIsCorrect){
            diagnoses[0]="ERROR";
            diagnoses[1]="The user "+userInfoId+" has no roles assigned for working on schema"+schemaPrefixName;
            return diagnoses;
        }
        String[] userSchema = new String[1];
        userSchema[0]=schemaPrefixName;
        
        String[] filterFieldName = new String[2];
        Object[] filterFieldValue = new Object[2];
        String[] fieldsToReturn = new String[4];

        fieldsToReturn[0] = "sop_id";
        fieldsToReturn[1] = "sop_name";
        fieldsToReturn[2] = "status";
        fieldsToReturn[3] = "light";
        filterFieldName[0]="user_id";
        filterFieldValue[0]=userInfoId;
        filterFieldName[1]="sop_name";
        filterFieldValue[1]=sopName;                
        Object[][] getUserProfileFieldValues = getUserProfileFieldValues(rdbm, filterFieldName, filterFieldValue, fieldsToReturn, userSchema);   
        if (getUserProfileFieldValues.length<=0){
            diagnoses[0]="ERROR";
            diagnoses[1]="The user "+userInfoId+" has no the sop "+sopName+ " assigned to.";
            return diagnoses;
        }
        if (getUserProfileFieldValues[0][3].toString().contains("GREEN")){
            diagnoses[0]="PASS";  
            diagnoses[2]="xf046@FontAwesome"; 
            
            //xf24e --> Certification near expire
            
            return diagnoses;
        }
        else{
            diagnoses[0]="NOTPASS";            
            diagnoses[1]="The user "; //+userInfoId+" has the sop "+replaceNull(getUserProfileFieldValues[0][1].toString())+ " assigned to which current status is "+replaceNull(getUserProfileFieldValues[0][2].toString())+" and the light is "+replaceNull(getUserProfileFieldValues[0][3].toString());
            diagnoses[2]="xf05e@FontAwesome"; 
            return diagnoses;
        }               
    }

    public String[] userSopCertifiedBySopName(Rdbms rdbm, String schemaPrefixName, String userInfoId, String sopName, String procedure, Integer procVersion ) throws SQLException{
        
        String[] diagnoses = new String[4];
        String sopMode = "";
        Boolean certifyManagement = false;
        Boolean enableRecertification = false;
        
        Object[][] procBusinessRule = rdbm.getRecordFieldsByFilter(rdbm, "requirements", "procedure_business_rule", 
                                                        new String[]{"procedure", "version"}, new Object[]{procedure, procVersion}, 
                                                        new String[]{"sop_mode", "certify_management", "enable_recertification", "procedure"});
        
        if (procBusinessRule[0][3].toString().equalsIgnoreCase("FALSE")){return diagnoses;}
        
        sopMode = (String) procBusinessRule[0][0];
        certifyManagement = (Boolean) procBusinessRule[0][1];
        enableRecertification = (Boolean) procBusinessRule[0][2];

        if (!sopMode.equalsIgnoreCase("ALL")){
            diagnoses[0]="SOP_DISABLE";
            diagnoses[1]="SOP disabled.";
            diagnoses[2]="xf133@FontAwesome";
            diagnoses[3]="SOPs disabled";
            return diagnoses;
        }
        
        if (!certifyManagement){
            diagnoses[0]="SOP_ENABLE_";
            diagnoses[1]="SOP enable but Certifications disabled, SOP merely info";
            diagnoses[2]="xf272@FontAwesome";
            diagnoses[3]="SOP enable but Certifications disabled, SOP merely info";
            return diagnoses;
        }
        
        UserProfile usProf = new UserProfile();
        String[] userSchemas = usProf.getAllUserSchemaPrefix(rdbm, userInfoId);
        Boolean schemaIsCorrect = false;
        for (String us: userSchemas){
            if (us.equalsIgnoreCase(schemaPrefixName)){schemaIsCorrect=true;break;}            
        }
        if (!schemaIsCorrect){
            diagnoses[0]="ERROR";
            diagnoses[1]="The user "+userInfoId+" has no roles assigned for working on schema"+schemaPrefixName;
            diagnoses[2]="";
            diagnoses[3]="";
            return diagnoses;
        }
        String[] userSchema = new String[1];
        userSchema[0]=schemaPrefixName;
        
        String[] filterFieldName = new String[2];
        Object[] filterFieldValue = new Object[2];
        String[] fieldsToReturn = new String[5];

        fieldsToReturn[0] = "sop_id";
        fieldsToReturn[1] = "sop_name";
        fieldsToReturn[2] = "status";
        fieldsToReturn[3] = "light";
        fieldsToReturn[4] = "expiration_date";
        filterFieldName[0]="user_id";
        filterFieldValue[0]=userInfoId;
        filterFieldName[1]="sop_name";
        filterFieldValue[1]=sopName;                
        Object[][] getUserProfileFieldValues = getUserProfileFieldValues(rdbm, filterFieldName, filterFieldValue, fieldsToReturn, userSchema);   
        if (getUserProfileFieldValues.length<=0){
            diagnoses[0]="ERROR";
            diagnoses[1]="The user "+userInfoId+" has no the sop "+sopName+ " assigned to.";
            return diagnoses;
        }
                
        if (getUserProfileFieldValues[0][3].toString().contains("GREEN")){
            
            if (certifyManagement && getUserProfileFieldValues[0][4]!=null){                
                Date now = new Date();
                if (now.after((Date) getUserProfileFieldValues[0][4])){
                    diagnoses[0]="SOP_CERTIF_EXPIRED";
                    diagnoses[1]="The user "+userInfoId+" was certified for the sop "+sopName+" but it expired on "+getUserProfileFieldValues[0][4].toString();
                    diagnoses[2]="xf06a@FontAwesome";
                    diagnoses[3]="The user "+userInfoId+" was certified for the sop "+sopName+" but it expired on "+getUserProfileFieldValues[0][4].toString();
                    return diagnoses;
                }
            }
            
            diagnoses[0]="PASS";  
            diagnoses[2]="xf046@FontAwesome"; 
            diagnoses[3]="The user "+userInfoId+" is currently certified for the sop "+sopName;
            //xf24e --> Certification near expire
            
            return diagnoses;
        }
        else{
            diagnoses[0]="NOTPASS";            
            diagnoses[1]="The user "; //+userInfoId+" has the sop "+replaceNull(getUserProfileFieldValues[0][1].toString())+ " assigned to which current status is "+replaceNull(getUserProfileFieldValues[0][2].toString())+" and the light is "+replaceNull(getUserProfileFieldValues[0][3].toString());
            diagnoses[2]="xf05e@FontAwesome"; 
            diagnoses[3]="The user "+userInfoId+" is currently NOT certified for the sop "+sopName;
            return diagnoses;
        }               
    }

    
    public String[] getNotCompletedUserSOP(Rdbms rdbm, String userInfoId, String schemapPrefixName) throws SQLException{

        String[] userSchemas = null;
        if (schemapPrefixName.contains("ALL")){
            UserProfile usProf = new UserProfile();
            userSchemas = usProf.getAllUserSchemaPrefix(rdbm, userInfoId);
        }
        else{
            userSchemas = new String[1];
            userSchemas[0]=schemapPrefixName;
        }

        String[] filterFieldName = new String[2];
        Object[] filterFieldValue = new Object[2];
        String[] fieldsToReturn = new String[2];

        fieldsToReturn[0] = "sop_id";
        fieldsToReturn[1] = "sop_name";
        filterFieldName[0]="user_id";
        filterFieldValue[0]=userInfoId;
        filterFieldName[1]="light";
        filterFieldValue[1]="RED";
                
        Object[][] getUserProfileFieldValues = getUserProfileFieldValues(rdbm, filterFieldName, filterFieldValue, fieldsToReturn, userSchemas);   
        Integer numLines=getUserProfileFieldValues.length;

        String[]UserSchemas=new String[numLines];
        for (Integer inumLines=0;inumLines<numLines;inumLines++){                
            UserSchemas[inumLines]=getUserProfileFieldValues[inumLines][0].toString();
        }
        return UserSchemas;           
    }
    
    public Object[][] getUserProfileFieldValues(Rdbms rdbm, String[] filterFieldName, Object[] filterFieldValue, String[] fieldsToReturn, String[] schemaPrefix) throws SQLException{                
        if (fieldsToReturn.length<=0){
            String[][] getUserProfileNEW = new String[1][2];
            getUserProfileNEW[0][0]="ERROR";
            getUserProfileNEW[0][1]="No fields specified for fieldsToReturn";
            return getUserProfileNEW;}
                    
        if ((filterFieldName==null) || (filterFieldValue==null) || (schemaPrefix==null)){
            String[][] getUserProfileNEW = new String[1][4];
            getUserProfileNEW[0][0]="ERROR";
            getUserProfileNEW[0][1]="filterFieldName and/or filterFieldValue and/or schemaPrefix are null and this is not expected";
            if (filterFieldName==null){getUserProfileNEW[0][2]="filterFieldName is null";}else{getUserProfileNEW[0][2]="filterFieldName="+Arrays.toString(filterFieldName);}
            if (filterFieldValue==null){getUserProfileNEW[0][3]="filterFieldValue is null";}else{getUserProfileNEW[0][3]="filterFieldValue="+Arrays.toString(filterFieldValue);}
            return getUserProfileNEW;}       
        
        /*if (filterFieldName.length!=filterFieldValue.length){
            Object[][] getUserProfileNEW = new Object[1][4];
            getUserProfileNEW[0][0]="ERROR";
            getUserProfileNEW[0][1]="Number of fields and values mistmatch.filterFieldName contains "+filterFieldName.length+" fields and filterFieldValue contains "+filterFieldValue.length+" fields";
            getUserProfileNEW[0][2]=filterFieldName.toString();
            getUserProfileNEW[0][3]=filterFieldValue.toString();
            return getUserProfileNEW;}*/
        
        String query = "";
        for(String sPref: schemaPrefix){
        
            query = query+"(select ";
            for(String fRet: fieldsToReturn){
                query = query+" "+fRet+","; 
            }
            query=query.substring(0, query.length()-1);
            if (sPref.contains("data")){query = query+" from \""+ sPref+"\".user_sop where 1=1";}
            else{query = query+" from \""+ sPref+"-data\".user_sop where 1=1";}
            for(String fFN: filterFieldName){
                query = query+" and "+fFN;
                if (!fFN.contains("null")){query=query+"= ?";}
            }
            query = query+") union ";
        }
        
        query=query.substring(0, query.length()-6);
        
        Object[] filterFieldValueAllSchemas = new Object[filterFieldValue.length*schemaPrefix.length];
        Integer i=0;
        for(String sPref: schemaPrefix){
            for(Object fVal: filterFieldValue){
                filterFieldValueAllSchemas[i]=fVal;    
                i++;
            }
        }        
        
        ResultSet res = rdbm.prepRdQuery(query, filterFieldValueAllSchemas); 
        
        res.last();
        Integer numLines=res.getRow();
        Integer numColumns=fieldsToReturn.length;
        res.first();
        Object[][] getUserProfileNEW=new Object[numLines][numColumns];
        for (Integer inumLines=0;inumLines<numLines;inumLines++){
            for (Integer inumColumns=0;inumColumns<numColumns;inumColumns++){
                getUserProfileNEW[inumLines][inumColumns]=res.getObject(inumColumns+1);
            }
            res.next();
        }
        return getUserProfileNEW;            
    }
    
    Integer dbGetUserSopBySopId(Rdbms rdbm, String schemaName, String UserId, Integer sopId) throws SQLException{
                
        String query = "";
        schemaName = "\""+schemaName+"\"";
        query = "select user_sop_id from " + schemaName + ".user_sop "        
            + "   where user_id =? and sop_id = ? ";
        try{     
            ResultSet res = rdbm.prepRdQuery(query, new Object [] {UserId, sopId.toString()});          
            res.last();            

            if (res.getRow()>0) return res.getInt("user_sop_id");

            return null;
        }catch (SQLException ex) {
            Logger.getLogger(query).log(Level.SEVERE, null, ex);
            return null;
        }
            
    }

    public Object[] addSopToUser(Rdbms rdbm, String schemaName, String userInfoId, Integer sopId) throws SQLException{
        String diagnoses = "";
        Sop s = null;
        //Integer exists = s.dbGetSopIdById(rdbm, schemaName, sopId);        
        //if (exists==null) return "ZSop " + sopId + " not found.";
                
        Integer exists = dbGetUserSopBySopId(rdbm, schemaName, userInfoId, sopId);
        if (exists!=null){
            String messageCode = "UserSop_sopAlreadyAssignToUser";
            Object[] errorDetailVariables = new Object[0] ;
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sopId);          
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, userInfoId);          
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
            return LabPLANETPlatform.trapErrorMessage(rdbm, classVersion, classVersion, diagnoses, javaDocValues);
        }
        
        Object[] diagnosis = rdbm.insertRecordInTable(rdbm, schemaName, "user_sop", new String[]{"user_id", "sop_id"}, new Object[]{userInfoId, sopId});
        if ("LABPLANET_FALSE".equalsIgnoreCase(diagnosis[0].toString())){
            return diagnosis;
        }else{
            String messageCode = "UserSop_sopAddedToUser";
            Object[] errorDetailVariables = new Object[0] ;
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sopId);          
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, userInfoId);          
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
            return LabPLANETPlatform.trapErrorMessage(rdbm, classVersion, classVersion, diagnoses, javaDocValues);
        }
    }
    
    public Object[] addSopToUser(Rdbms rdbm, String schemaName, String userInfoId, String sopName) throws SQLException{
        String diagnoses = "";
        Sop s = new Sop(sopName);
        
        Integer sopId = s.dbGetSopIdByName(rdbm, schemaName, sopName);        
        if (sopId!=null) {        
            Integer exists = dbGetUserSopBySopId(rdbm, schemaName, userInfoId, sopId);
            if (exists!=null){
                String messageCode = "UserSop_sopAlreadyAssignToUser";
                Object[] errorDetailVariables = new Object[0] ;
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sopId);          
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, userInfoId);          
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
                return LabPLANETPlatform.trapErrorMessage(rdbm, classVersion, classVersion, diagnoses, javaDocValues);
            }
            Object[] diagnosis = rdbm.insertRecordInTable(rdbm, schemaName, "user_sop", new String[]{"user_id", "sop_id", "sop_name"}, new Object[]{userInfoId, sopId, sopName});
            if ("LABPLANET_FALSE".equalsIgnoreCase(diagnosis[0].toString())){
                return diagnosis;
            }else{
                String messageCode = "UserSop_sopAddedToUser";
                Object[] errorDetailVariables = new Object[0] ;
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, sopName);          
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, userInfoId);          
                errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
                return LabPLANETPlatform.trapErrorMessage(rdbm, classVersion, classVersion, diagnoses, javaDocValues);
            }            
        }else{
            String messageCode = "Rdbms_NoRecordsFound";
            Object[] errorDetailVariables = new Object[0] ;
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, "user_sop");          
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, "schemaName: "+schemaName+", sopName: "+sopName);          
            errorDetailVariables = labArr.addValueToArray1D(errorDetailVariables, schemaName);          
            return LabPLANETPlatform.trapErrorMessage(rdbm, classVersion, classVersion, diagnoses, javaDocValues);            
        }            
    }

    
    public String[] getUserSopFilter(String userInfoId){
        String[] theSops = null;
        
        return theSops;
    }
    
}
