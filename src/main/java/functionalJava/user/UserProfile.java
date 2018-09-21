/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.user;

import databases.Rdbms;
import LabPLANET.utilities.LabPLANETArray;
import LabPLANET.utilities.LabPLANETPlatform;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author Administrator
 */
public class UserProfile {

    String classVersion = "0.1";
    LabPLANETArray labArr = new LabPLANETArray();
    LabPLANETPlatform labPlat = new LabPLANETPlatform();
    String[] javaDocFields = new String[0];
    Object[] javaDocValues = new Object[0];
    String javaDocLineName = "";

    String schemaDataName = "data";
    String schemaConfigName = "config";
    String tableName = "user_profile";  

    public String[] getAllUserSchemaPrefix (Rdbms rdbm, String userInfoId) throws SQLException{

            String[] filterFieldName = new String[3];
            Object[] filterFieldValue = new Object[2];
            String[] fieldsToReturn = new String[1];
                        
            fieldsToReturn[0] = "schema_prefix";
            filterFieldName[0]="user_info_id";
            filterFieldValue[0]=userInfoId;
            filterFieldName[1]="active";
            filterFieldValue[1]=true;
            filterFieldName[2]="schema_prefix is not null";            
            
            Object[][] userProfileField = getUserProfileFieldValues(rdbm, filterFieldName, filterFieldValue, fieldsToReturn);   
            Integer numLines=userProfileField.length;
            
            String[]UserSchemas=new String[numLines];
            for (Integer inumLines=0;inumLines<numLines;inumLines++){                
                UserSchemas[inumLines]=userProfileField[inumLines][0].toString();
            }
            return UserSchemas;                                
    }
    
    public Object[][] getUserProfileFieldValues(Rdbms rdbm, String[] filterFieldName, Object[] filterFieldValue, String[] fieldsToReturn) throws SQLException{
                
        if (fieldsToReturn.length<=0){
            String[][] getUserProfileNEW = new String[1][2];
            getUserProfileNEW[0][0]="ERROR";
            getUserProfileNEW[0][1]="No fields specified for fieldsToReturn";
            return getUserProfileNEW;}
                    
        if ((filterFieldName==null) || (filterFieldValue==null)){
            String[][] getUserProfileNEW = new String[1][4];
            getUserProfileNEW[0][0]="ERROR";
            getUserProfileNEW[0][1]="filterFieldName and/or filterFieldValue are null and this is not expected";
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
        
        String query = "select ";
        for(String fRet: fieldsToReturn){
            query = query+" "+fRet+","; 
        }
        query=query.substring(0, query.length()-1);
        query = query+" from config.user_profile where 1=1";
        //Integer i =0;
        for(String fFN: filterFieldName){
            query = query+" and "+fFN;
            if (!fFN.contains("null")){query=query+"= ?";}
        //    i++;
            //query = query+" and "+fFN+"=?";
        }
        ResultSet res = rdbm.prepRdQuery(query, filterFieldValue); 
        
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
    
}
