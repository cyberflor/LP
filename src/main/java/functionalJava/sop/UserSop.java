/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functionalJava.sop;

import databases.Rdbms;
import functionalJava.user.UserProfile;
import LabPLANET.utilities.LPArray;
import LabPLANET.utilities.LPPlatform;
import functionalJava.parameter.Parameter;
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

    public static final String FIELDNAME_SOP_ID="sop_id";
    public static final String FIELDNAME_SOP_NAME="sop_name";
    
    private static final String SOP_ENABLE_CODE="SOP_ENABLE";
    private static final String SOP_ENABLE_CODE_ICON="xf272@FontAwesome";
    private static final String SOP_DISABLE_CODE="SOP_DISABLE";
    private static final String SOP_DISABLE_CODE_ICON="xf133@FontAwesome";
    private static final String SOP_CERTIF_EXPIRED_CODE="SOP_CERTIF_EXPIRED";
    private static final String SOP_CERTIF_EXPIRED_CODE_ICON="xf06a@FontAwesome";
    private static final String SOP_PASS_CODE="PASS";
    private static final String SOP_PASS_CODE_ICON="xf046@FontAwesome";
    private static final String SOP_NOTPASS_CODE="NOTPASS";
    private static final String SOP_NOTPASS_CODE_ICON="xf05e@FontAwesome";

    /**
     *
     * @param schemaPrefixName
     * @param userInfoId
     * @param sopName
     * @return
     * @throws SQLException
     */
    public Object[] userSopCertifiedBySopName( String schemaPrefixName, String userInfoId, String sopName ) throws SQLException{    
        return userSopCertifiedBySopInternalLogic(schemaPrefixName, userInfoId, FIELDNAME_SOP_NAME, sopName);        
        }

    /**
     *
     * @param schemaPrefixName
     * @param userInfoId
     * @param sopId
     * @return
     * @throws SQLException
     */
    public Object[] userSopCertifiedBySopId( String schemaPrefixName, String userInfoId, String sopId ) throws SQLException{
        return userSopCertifiedBySopInternalLogic(schemaPrefixName, userInfoId, FIELDNAME_SOP_ID, sopId);        
    }
    
    private Object[] userSopCertifiedBySopInternalLogic( String schemaPrefixName, String userInfoId, String sopIdFieldName, String sopIdFieldValue ) {
                
        String schemaConfigName = "config";
        schemaConfigName = LPPlatform.buildSchemaName(schemaPrefixName, schemaConfigName);
        String actionEnabledUserSopCertification = Parameter.getParameterBundle(schemaConfigName, "actionEnabledUserSopCertification"); 
        
        UserProfile usProf = new UserProfile();
        Object[] userSchemas = (Object[]) usProf.getAllUserProcedurePrefix(userInfoId);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(userSchemas[0].toString())){
            return LPArray.array1dTo2d(userSchemas, userSchemas.length);
        }        
        Boolean schemaIsCorrect = false;
        for (String us: (String[]) userSchemas){
            if (us.equalsIgnoreCase(schemaPrefixName)){schemaIsCorrect=true;break;}            
        }
        if (!schemaIsCorrect){
            String errorCode = "UserSop_UserWithNoRolesForThisGivenSchema";
            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, new Object[]{userInfoId, schemaPrefixName});
            diagnoses = LPArray.addValueToArray1D(diagnoses, "ERROR");
            diagnoses = LPArray.addValueToArray1D(diagnoses, Parameter.getParameterBundle(schemaConfigName, "userSopCertificationLevelImage_ERROR"));
            return diagnoses;
        }
        String[] userSchema = new String[1];
        userSchema[0]=schemaPrefixName;
        
        String[] filterFieldName = new String[2];
        Object[] filterFieldValue = new Object[2];
        String[] fieldsToReturn = new String[4];

        fieldsToReturn[0] = FIELDNAME_SOP_ID;
        fieldsToReturn[1] = FIELDNAME_SOP_NAME;
        fieldsToReturn[2] = "status";
        fieldsToReturn[3] = "light";
        filterFieldName[0]="user_id";
        filterFieldValue[0]=userInfoId;        
        filterFieldName[1]=sopIdFieldName;
        filterFieldValue[1]=sopIdFieldValue;                
        Object[][] getUserProfileFieldValues = getUserProfileFieldValues(filterFieldName, filterFieldValue, fieldsToReturn, userSchema);   
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(getUserProfileFieldValues[0][0].toString())){
            Object[] diagnoses = LPArray.array2dTo1d(getUserProfileFieldValues);
            diagnoses = LPArray.addValueToArray1D(diagnoses, "ERROR");
            return diagnoses;
        }
        if (getUserProfileFieldValues.length<=0){
            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "UserSop_SopNotAssignedToThisUser", new Object[]{sopIdFieldValue, userInfoId, schemaPrefixName});
            diagnoses = LPArray.addValueToArray1D(diagnoses, "ERROR");
            diagnoses = LPArray.addValueToArray1D(diagnoses, Parameter.getParameterBundle(schemaConfigName, "userSopCertificationLevelImage_NotAssigned"));
            return diagnoses;
        }
        if (getUserProfileFieldValues[0][3].toString().contains("GREEN")){
            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, "UserSop_SopNotAssignedToThisUser", 
                    new Object[]{userInfoId, sopIdFieldValue, schemaPrefixName, "current status is "+getUserProfileFieldValues[0][2].toString()+" and the light is "+getUserProfileFieldValues[0][3].toString()});
            diagnoses = LPArray.addValueToArray1D(diagnoses, SOP_PASS_CODE);
            diagnoses = LPArray.addValueToArray1D(diagnoses, Parameter.getParameterBundle(schemaConfigName, "userSopCertificationLevelImage_Certified"));
            return diagnoses;
        }
        else{
            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "UserSop_UserNotCertifiedForSop", new Object[]{userInfoId, sopIdFieldValue, schemaPrefixName});
            diagnoses = LPArray.addValueToArray1D(diagnoses, SOP_NOTPASS_CODE);
            diagnoses = LPArray.addValueToArray1D(diagnoses, Parameter.getParameterBundle(schemaConfigName, "userSopCertificationLevelImage_NotCertified"));
            return diagnoses;
        }               
    }

    /**
     *
     * @param userInfoId
     * @param schemaPrefixName
     * @param fieldsToRetrieve
     * @return
     */
    public Object[][] getNotCompletedUserSOP( String userInfoId, String schemaPrefixName, String[] fieldsToRetrieve) {
        Object[] userSchemas = null;
        if (schemaPrefixName.contains("ALL")){
            UserProfile usProf = new UserProfile();
            userSchemas = (Object[]) usProf.getAllUserProcedurePrefix(userInfoId);
        }
        else{
            userSchemas = new String[1];
            userSchemas[0]=schemaPrefixName;
        }

        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(userSchemas[0].toString())){
            return LPArray.array1dTo2d(userSchemas, userSchemas.length);
        }
        String[] filterFieldName = new String[2];
        Object[] filterFieldValue = new Object[2];
        String[] fieldsToReturn = new String[0];

        filterFieldName[0]="user_id";
        filterFieldValue[0]=userInfoId;
        filterFieldName[1]="light";
        filterFieldValue[1]="RED";
        if (fieldsToRetrieve!=null){            
            for (String fv: fieldsToRetrieve){
                fieldsToReturn = LPArray.addValueToArray1D(fieldsToReturn, fv);
            }
        }else{
            fieldsToReturn = LPArray.addValueToArray1D(fieldsToReturn, FIELDNAME_SOP_ID);
            fieldsToReturn = LPArray.addValueToArray1D(fieldsToReturn, FIELDNAME_SOP_NAME);
        }
                
        Object[][] getUserProfileFieldValues = getUserProfileFieldValues(filterFieldName, filterFieldValue, fieldsToReturn, (String[]) userSchemas);   
        return getUserProfileFieldValues;
/*
        Integer numLines=getUserProfileFieldValues.length;

        String[]UserSchemas=new String[numLines];
        for (Integer inumLines=0;inumLines<numLines;inumLines++){                
            UserSchemas[inumLines]=getUserProfileFieldValues[inumLines][0].toString();
        }
        return UserSchemas;           
*/        
    }
    
    /**
     *
     * @param schemaPrefixName
     * @param userInfoId
     * @param sopName
     * @param procedure
     * @param procVersion
     * @return
     * @throws SQLException
     */
    public Object[] _NotRequireduserSopCertifiedBySopName( String schemaPrefixName, String userInfoId, String sopName, String procedure, Integer procVersion ) throws SQLException{
        return _NotRequireduserSopCertifiedBySopInternalLogic(schemaPrefixName, userInfoId, FIELDNAME_SOP_NAME, sopName, procedure, procVersion);        
    }

    /**
     *
     * @param schemaPrefixName
     * @param userInfoId
     * @param sopId
     * @param procedure
     * @param procVersion
     * @return
     * @throws SQLException
     */
    public Object[] _NotRequireduserSopCertifiedBySopId( String schemaPrefixName, String userInfoId, String sopId, String procedure, Integer procVersion ) throws SQLException{
        return _NotRequireduserSopCertifiedBySopInternalLogic(schemaPrefixName, userInfoId, FIELDNAME_SOP_ID, sopId, procedure, procVersion);
    }    
    private Object[] _NotRequireduserSopCertifiedBySopInternalLogic( String schemaPrefixName, String userInfoId, String sopIdFieldName, String sopIdFieldValue, String procedure, Integer procVersion ) {
        
        Object[] diagnoses = new Object[0];
        String sopMode = "";
        Boolean certifyManagement = false;
        Boolean enableRecertification = false;
        
        Object[][] procBusinessRule = Rdbms.getRecordFieldsByFilter("requirements", "procedure_business_rule", 
                                                        new String[]{"procedure", "version"}, new Object[]{procedure, procVersion}, 
                                                        new String[]{"sop_mode", "certify_management", "enable_recertification", "procedure"});        
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(procBusinessRule[0][0].toString())){return diagnoses;}
        
        sopMode = (String) procBusinessRule[0][0];
        certifyManagement = (Boolean) procBusinessRule[0][1];
        enableRecertification = (Boolean) procBusinessRule[0][2];

        if (!sopMode.equalsIgnoreCase("ALL")){
            diagnoses[0]=SOP_DISABLE_CODE;            diagnoses[1]="SOP disabled.";
            diagnoses[2]=SOP_DISABLE_CODE_ICON;  diagnoses[3]="SOPs disabled";
            return diagnoses;
        }        
        if (!certifyManagement){
            diagnoses[0]=SOP_ENABLE_CODE;            diagnoses[1]="SOP enable but Certifications disabled, SOP merely info";
            diagnoses[2]=SOP_ENABLE_CODE_ICON;  diagnoses[3]="SOP enable but Certifications disabled, SOP merely info";
            return diagnoses;
        }       
        UserProfile usProf = new UserProfile();
        String[] userSchemas = (String[]) usProf.getAllUserProcedurePrefix(userInfoId);
        Boolean schemaIsCorrect = false;
        for (String us: userSchemas){
            if (us.equalsIgnoreCase(schemaPrefixName)){schemaIsCorrect=true;break;}            
        }
        if (!schemaIsCorrect){
            diagnoses[0]="ERROR";  diagnoses[1]="The user "+userInfoId+" has no roles assigned for working on schema"+schemaPrefixName;
            diagnoses[2]="";            diagnoses[3]="";
            return diagnoses;
        }
        Object[][] getUserProfileFieldValues = getUserProfileFieldValues(
                new String[]{"user_id", sopIdFieldName}, new Object[]{userInfoId, sopIdFieldValue}, 
                new String[]{"light", "expiration_date"}, new String[]{schemaPrefixName});   
        if (getUserProfileFieldValues.length<=0){
            diagnoses[0]="ERROR";
            diagnoses[1]="The user "+userInfoId+" has no the sop "+sopIdFieldValue+ " assigned to.";
            return diagnoses;
        }
        String usrProfLight=getUserProfileFieldValues[0][0].toString();
        Date usrProfExpDate=(Date) getUserProfileFieldValues[0][1];    
        
        if (!usrProfLight.contains("GREEN")){            
            diagnoses[0]=SOP_NOTPASS_CODE;              diagnoses[1]="The user "; //+userInfoId+" has the sop "+replaceNull(getUserProfileFieldValues[0][1].toString())+ " assigned to which current status is "+replaceNull(getUserProfileFieldValues[0][2].toString())+" and the light is "+replaceNull(getUserProfileFieldValues[0][3].toString());
            diagnoses[2]=SOP_NOTPASS_CODE_ICON;    diagnoses[3]="The user "+userInfoId+" is currently NOT certified for the sop "+sopIdFieldValue;
            return diagnoses;
        }else{
            Date now = new Date();
            if ( (certifyManagement && usrProfExpDate!=null) && (now.after(usrProfExpDate)) ){                
                diagnoses[0]=SOP_CERTIF_EXPIRED_CODE;              diagnoses[1]="The user "+userInfoId+" was certified for the sop "+sopIdFieldValue+" but it expired on "+usrProfExpDate.toString();
                diagnoses[2]=SOP_CERTIF_EXPIRED_CODE_ICON;    diagnoses[3]="The user "+userInfoId+" was certified for the sop "+sopIdFieldValue+" but it expired on "+usrProfExpDate.toString();
                return diagnoses;
            }
            diagnoses[0]=SOP_PASS_CODE;              diagnoses[1]="";  
            diagnoses[2]=SOP_PASS_CODE_ICON;    diagnoses[3]="The user "+userInfoId+" is currently certified for the sop "+sopIdFieldValue;
            //xf24e --> Certification near expire
            return diagnoses;
        }               
    }
       
    // This function cannot be replaced by a single query through the rdbm because it run the query through the many procedures
    //      the user is involved on if so ....

    /**
     *
     * @param filterFieldName
     * @param filterFieldValue
     * @param fieldsToReturn
     * @param schemaPrefix
     * @return
     */
        
    public Object[][] getUserProfileFieldValues(String[] filterFieldName, Object[] filterFieldValue, String[] fieldsToReturn, String[] schemaPrefix){                
        String tableName = "user_sop";
        
        if (fieldsToReturn.length<=0){
            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "Rdbms_NotFilterSpecified", new Object[]{tableName, schemaPrefix});
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
                
        String query = "";
        for(String currSchemaPrefix: schemaPrefix){                    
            query = query+"(select ";
            for(String fRet: fieldsToReturn){
                query = query+" "+fRet+","; 
            }
            query=query.substring(0, query.length()-1);
            if (currSchemaPrefix.contains("data")){query = query+" from \""+ currSchemaPrefix+"\"."+tableName+" where 1=1";}
            else{query = query+" from \""+ currSchemaPrefix+"-data\"."+tableName+" where 1=1";}
            for(String fFN: filterFieldName){
                query = query+" and "+fFN;
                if (!fFN.contains("null")){query=query+"= ?";}
            }
            query = query+") union ";
        }       
        query=query.substring(0, query.length()-6);
        
        Object[] filterFieldValueAllSchemas = new Object[filterFieldValue.length*schemaPrefix.length];
        Integer iFldValue=0;
        for(String sPref: schemaPrefix){
            for(Object fVal: filterFieldValue){
                filterFieldValueAllSchemas[iFldValue]=fVal;    
                iFldValue++;
            }
        }               
        try{
            ResultSet res = Rdbms.prepRdQuery(query, filterFieldValueAllSchemas);         
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
        }catch(SQLException ex){
            String errorCode = "LabPLANETPlatform_SpecialFunctionReturnedEXCEPTION";
            String[] errorDetailVariables = new String[0];
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, ex.getMessage());
            Object[] trpErr=LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, errorCode, errorDetailVariables);
            return LPArray.array1dTo2d(trpErr, trpErr.length);            
        }
    }
    
    Integer  _notRequireddbGetUserSopBySopId( String schemaName, String userId, Integer sopId) {
        String schemaDataName = "data";
        schemaName = LPPlatform.buildSchemaName(schemaDataName, schemaName);
        
        String query = "";
        schemaName = "\""+schemaName+"\"";
        query = "select user_sop_id from " + schemaName + ".user_sop "        
            + "   where user_id =? and sop_id = ? ";
        try{     
            ResultSet res = Rdbms.prepRdQuery(query, new Object [] {userId, sopId.toString()});          
            res.last();            

            if (res.getRow()>0) return res.getInt("user_sop_id");

            return null;
        }catch (SQLException ex) {
            Logger.getLogger(query).log(Level.SEVERE, null, ex);
            return null;
        }
            
    }

    /**
     *
     * @param schemaName
     * @param userInfoId
     * @param sopId
     * @return
     */
    public Object[] addSopToUserById( String schemaName, String userInfoId, Integer sopId){
        return addSopToUserInternalLogic(schemaName, userInfoId, FIELDNAME_SOP_ID, sopId);
    }   

    /**
     *
     * @param schemaName
     * @param userInfoId
     * @param sopId
     * @return
     */
    public Object[] addSopToUserById( String schemaName, String userInfoId, String sopId){
        return addSopToUserInternalLogic(schemaName, userInfoId, FIELDNAME_SOP_ID, sopId);
    }   

    /**
     *
     * @param schemaName
     * @param userInfoId
     * @param sopName
     * @return
     */
    public Object[] addSopToUserByName( String schemaName, String userInfoId, String sopName){
        return addSopToUserInternalLogic(schemaName, userInfoId, FIELDNAME_SOP_ID, sopName);
    }    

    /**
     *
     * @param schemaName
     * @param userInfoId
     * @param sopIdFieldName
     * @param sopIdFieldValue
     * @return
     */
    public Object[] addSopToUserInternalLogic( String schemaName, String userInfoId, String sopIdFieldName, Object sopIdFieldValue){
        
        String schemaDataName = "data";
        schemaName = LPPlatform.buildSchemaName(schemaName, schemaDataName);
        String diagnoses = "";
        Sop s = null;
        String tableName = "user_sop";
        Object[] exists = Rdbms.existsRecord(schemaName, tableName, new String[]{"user_id", sopIdFieldName}, new Object[]{userInfoId, sopIdFieldValue});
                
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(exists[0].toString())){
            String messageCode = "UserSop_sopAlreadyAssignToUser";
            Object[] errorDetailVariables = new Object[0] ;
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sopIdFieldValue);          
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, userInfoId);          
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);          
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, messageCode, errorDetailVariables);
        }
        
        Object[] diagnosis = Rdbms.insertRecordInTable(schemaName, "user_sop", new String[]{"user_id", sopIdFieldName}, new Object[]{userInfoId, sopIdFieldValue});
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnosis[0].toString())){
            return diagnosis;
        }else{
            String messageCode = "UserSop_sopAddedToUser";
            Object[] errorDetailVariables = new Object[0] ;
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sopIdFieldValue);          
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, userInfoId);          
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);          
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, messageCode, errorDetailVariables);
        }
    }
    
    /**
     *
     * @param userInfoId
     * @return
     */
    public String[] _notRequiredgetUserSopFilter(String userInfoId){
        String[] theSops = null;        
        return theSops;
    }
    
    public boolean isProcedureSopEnable(String procedureName){
        String sopCertificationLevel = Parameter.getParameterBundle("config", procedureName, "procedure", "actionEnabledUserSopCertification", null);
        if ("DISABLE".equalsIgnoreCase(sopCertificationLevel)) return false;
        if ("DISABLED".equalsIgnoreCase(sopCertificationLevel)) return false;
        if ("OFF".equalsIgnoreCase(sopCertificationLevel)) return false;
        return !"".equalsIgnoreCase(sopCertificationLevel);
    }
    
}
