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
import functionalJava.user.UserAndRolesViews;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


/**
 *
 * @author Administrator
 */
public class UserSop {
    String classVersion = "0.1";

    public static final String TABLENAME_DATA_USER_SOP="user_sop";
        public static final String FIELDNAME_SOP_ID="sop_id";
        public static final String FIELDNAME_SOP_LIGHT="light";   
        public static final String FIELDNAME_SOP_NAME="sop_name";
        public static final String FIELDNAME_SOP_READ_COMPLETED="read_completed";   
        public static final String FIELDNAME_SOP_STATUS="status";    
        public static final String FIELDNAME_SOP_USER_ID="user_id";   
        public static final String FIELDNAME_SOP_USER_NAME="user_name";  
        
    
        public static final String SOP_ENABLE_CODE="SOP_ENABLE";
        public static final String SOP_ENABLE_CODE_ICON="xf272@FontAwesome";
        public static final String SOP_DISABLE_CODE="SOP_DISABLE";
        public static final String SOP_DISABLE_CODE_ICON="xf133@FontAwesome";
        public static final String SOP_CERTIF_EXPIRED_CODE="SOP_CERTIF_EXPIRED";
        public static final String SOP_CERTIF_EXPIRED_CODE_ICON="xf06a@FontAwesome";
        public static final String SOP_PASS_CODE="PASS";
        public static final String SOP_PASS_CODE_ICON="xf046@FontAwesome";
        public static final String SOP_PASS_LIGHT_CODE="GREEN";
        public static final String SOP_NOTPASS_CODE="NOTPASS";
        public static final String SOP_NOTPASS_CODE_ICON="xf05e@FontAwesome";
        public static final String SOP_NOT_PASS_LIGHT_CODE="RED";

    private static final String ERROR_TRAPING_SOP_MARKEDASCOMPLETED_NOT_PENDING="sopMarkedAsCompletedNotPending";
     private static final String ERROR_TRAPING_SOP_NOT_ASSIGNED_TO_THIS_USER="UserSop_SopNotAssignedToThisUser";

    private static final String DIAGNOSES_ERROR_CODE="ERROR";
    
    public static final Object[][] getUserSop(String schemaPrefix, String userName, String sopName ){
        UserProfile usProf = new UserProfile();
        Object[] userSchemas = (Object[]) usProf.getAllUserProcedurePrefix(userName);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(userSchemas[0].toString())){
            return LPArray.array1dTo2d(userSchemas, userSchemas.length);
        }    
        
        String[] fieldsToReturn = new String[]{FIELDNAME_SOP_ID, FIELDNAME_SOP_NAME, FIELDNAME_SOP_STATUS, FIELDNAME_SOP_LIGHT};
        String[] filterFieldName =new String[]{FIELDNAME_SOP_NAME, FIELDNAME_SOP_USER_NAME};
        Object[] filterFieldValue =new Object[]{sopName, userName};        
        Object[][] getUserProfileFieldValues = getUserProfileFieldValues(filterFieldName, filterFieldValue, fieldsToReturn, new String[]{schemaPrefix});   
        if (getUserProfileFieldValues.length<=0){
            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPING_SOP_NOT_ASSIGNED_TO_THIS_USER, new Object[]{sopName, userName, schemaPrefix});
            //diagnoses = LPArray.addValueToArray1D(diagnoses, DIAGNOSES_ERROR_CODE);
            //diagnoses = LPArray.addValueToArray1D(diagnoses, Parameter.getParameterBundle(schemaConfigName, "userSopCertificationLevelImage_NotAssigned"));
            return LPArray.array1dTo2d(diagnoses, diagnoses.length);
        }        
        return getUserProfileFieldValues;
    }
    /**
     *
     * @param schemaPrefixName
     * @param userInfoId
     * @param sopName
     * @return
     */
    public Object[] userSopCertifiedBySopName( String schemaPrefixName, String userInfoId, String sopName ) {    
        return userSopCertifiedBySopInternalLogic(schemaPrefixName, userInfoId, FIELDNAME_SOP_NAME, sopName);        
        }

    /**
     *
     * @param schemaPrefixName
     * @param userInfoId
     * @param sopId
     * @return
     */        
    public Object[] userSopCertifiedBySopId( String schemaPrefixName, String userInfoId, String sopId ) {
        return userSopCertifiedBySopInternalLogic(schemaPrefixName, userInfoId, FIELDNAME_SOP_ID, sopId);        
    }        
    
    private Object[] userSopCertifiedBySopInternalLogic( String schemaPrefixName, String userInfoId, String sopIdFieldName, String sopIdFieldValue ) {
                        
        String schemaConfigName = LPPlatform.buildSchemaName(schemaPrefixName, LPPlatform.SCHEMA_CONFIG);
//        String actionEnabledUserSopCertification = Parameter.getParameterBundle(LPPlatform.SCHEMA_CONFIG, "actionEnabledUserSopCertification"); 
        
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
            diagnoses = LPArray.addValueToArray1D(diagnoses, DIAGNOSES_ERROR_CODE);
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
        fieldsToReturn[2] = FIELDNAME_SOP_STATUS;
        fieldsToReturn[3] = FIELDNAME_SOP_LIGHT;
        filterFieldName[0]=FIELDNAME_SOP_USER_ID;
        filterFieldValue[0]=userInfoId;        
        filterFieldName[1]=sopIdFieldName;
        filterFieldValue[1]=sopIdFieldValue;                
        Object[][] getUserProfileFieldValues = getUserProfileFieldValues(filterFieldName, filterFieldValue, fieldsToReturn, userSchema);   
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(getUserProfileFieldValues[0][0].toString())){
            Object[] diagnoses = LPArray.array2dTo1d(getUserProfileFieldValues);
            diagnoses = LPArray.addValueToArray1D(diagnoses, DIAGNOSES_ERROR_CODE);
            return diagnoses;
        }
        if (getUserProfileFieldValues.length<=0){
            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPING_SOP_NOT_ASSIGNED_TO_THIS_USER, new Object[]{sopIdFieldValue, userInfoId, schemaPrefixName});
            diagnoses = LPArray.addValueToArray1D(diagnoses, DIAGNOSES_ERROR_CODE);
            diagnoses = LPArray.addValueToArray1D(diagnoses, Parameter.getParameterBundle(schemaConfigName, "userSopCertificationLevelImage_NotAssigned"));
            return diagnoses;
        }
        if (getUserProfileFieldValues[0][3].toString().contains(SOP_PASS_LIGHT_CODE)){
            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, ERROR_TRAPING_SOP_NOT_ASSIGNED_TO_THIS_USER, 
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

        filterFieldName[0]=FIELDNAME_SOP_USER_ID;
        filterFieldValue[0]=userInfoId;
        filterFieldName[1]=FIELDNAME_SOP_LIGHT;
        filterFieldValue[1]=SOP_NOT_PASS_LIGHT_CODE;
        if (fieldsToRetrieve!=null){            
            for (String fv: fieldsToRetrieve){
                fieldsToReturn = LPArray.addValueToArray1D(fieldsToReturn, fv);
            }
        }else{
            fieldsToReturn = LPArray.addValueToArray1D(fieldsToReturn, FIELDNAME_SOP_ID);
            fieldsToReturn = LPArray.addValueToArray1D(fieldsToReturn, FIELDNAME_SOP_NAME);
        }
        return getUserProfileFieldValues(filterFieldName, filterFieldValue, fieldsToReturn, (String[]) userSchemas);   
/*
        Integer numLines=getUserProfileFieldValues.length;

        String[]UserSchemas=new String[numLines];
        for (Integer inumLines=0;inumLines<numLines;inumLines++){                
            UserSchemas[inumLines]=getUserProfileFieldValues[inumLines][0].toString();
        }
        return UserSchemas;           
*/        
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
        
    public static final Object[][] getUserProfileFieldValues(String[] filterFieldName, Object[] filterFieldValue, String[] fieldsToReturn, String[] schemaPrefix){                
        String tableName = "user_and_meta_data_sop_vw"; //user_sop";
        
        if (fieldsToReturn.length<=0){
//            Object[] diagnoses = LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, "Rdbms_NotFilterSpecified", new Object[]{tableName, schemaPrefix});
            String[][] getUserProfileNEW = new String[1][2];
            getUserProfileNEW[0][0]=DIAGNOSES_ERROR_CODE;
            getUserProfileNEW[0][1]="No fields specified for fieldsToReturn";
            return getUserProfileNEW;}
                    
        if ((filterFieldName==null) || (filterFieldValue==null) || (schemaPrefix==null)){
            String[][] getUserProfileNEW = new String[1][4];
            getUserProfileNEW[0][0]=DIAGNOSES_ERROR_CODE;
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
            if (currSchemaPrefix.contains(LPPlatform.SCHEMA_DATA)){query = query+" from \""+ currSchemaPrefix+"\"."+tableName+" where 1=1";}
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
        return addSopToUserInternalLogic(schemaName, userInfoId, FIELDNAME_SOP_NAME, sopName);
    }    

    /**
     *
     * @param schemaName
     * @param personName
     * @param sopIdFieldName
     * @param sopIdFieldValue
     * @return
     */
    private Object[] addSopToUserInternalLogic( String schemaPrefix, String personName, String sopIdFieldName, Object sopIdFieldValue){
                
        String schemaName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        String diagnoses = "";
        Sop s = null;
        Object[] exists = Rdbms.existsRecord(schemaName, TABLENAME_DATA_USER_SOP, new String[]{FIELDNAME_SOP_USER_ID, sopIdFieldName}, new Object[]{personName, sopIdFieldValue});
                
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(exists[0].toString())){
            String messageCode = "UserSop_sopAlreadyAssignToUser";
            Object[] errorDetailVariables = new Object[0] ;
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sopIdFieldValue);          
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, personName);          
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);          
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, messageCode, errorDetailVariables);
        }        
        String userSopInitialStatus = Parameter.getParameterBundle(schemaPrefix.replace("\"", "")+LPPlatform.CONFIG_PROC_FILE_NAME, "userSopInitialStatus");
        String userSopInitialLight = Parameter.getParameterBundle(schemaPrefix.replace("\"", "")+LPPlatform.CONFIG_PROC_FILE_NAME, "userSopInitialLight");
        
        String[] insertFieldNames=new String[]{FIELDNAME_SOP_USER_ID, sopIdFieldName, FIELDNAME_SOP_STATUS, FIELDNAME_SOP_LIGHT};
        Object[] insertFieldValues=new Object[]{personName, sopIdFieldValue, userSopInitialStatus, userSopInitialLight};
        if (Sop.FIELDNAME_SOP_NAME.equalsIgnoreCase(sopIdFieldName)){
            insertFieldNames=LPArray.addValueToArray1D(insertFieldNames, Sop.FIELDNAME_SOP_NAME); 
            insertFieldValues=LPArray.addValueToArray1D(insertFieldValues, Sop.dbGetSopIdByName(schemaPrefix, sopIdFieldValue.toString()));
        }
        if (Sop.FIELDNAME_SOP_ID.equalsIgnoreCase(sopIdFieldName)){
            insertFieldNames=LPArray.addValueToArray1D(insertFieldNames, Sop.FIELDNAME_SOP_ID); 
            insertFieldValues=LPArray.addValueToArray1D(insertFieldValues, Sop.dbGetSopNameById(schemaPrefix, sopIdFieldValue));
        }        
        insertFieldNames=LPArray.addValueToArray1D(insertFieldNames, FIELDNAME_SOP_USER_NAME); 
        insertFieldValues=LPArray.addValueToArray1D(insertFieldValues, UserAndRolesViews.getUserByPerson(personName));        
        
        Object[] diagnosis = Rdbms.insertRecordInTable(schemaName, TABLENAME_DATA_USER_SOP, insertFieldNames, insertFieldValues);
        if (LPPlatform.LAB_FALSE.equalsIgnoreCase(diagnosis[0].toString())){
            return diagnosis;
        }else{
            String messageCode = "UserSop_sopAddedToUser";
            Object[] errorDetailVariables = new Object[0] ;
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, sopIdFieldValue);          
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, personName);          
            errorDetailVariables = LPArray.addValueToArray1D(errorDetailVariables, schemaName);          
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_TRUE, messageCode, errorDetailVariables);
        }
    }    
    
    
    public boolean isProcedureSopEnable(String procedureName){
        String sopCertificationLevel = Parameter.getParameterBundle("config", procedureName, "procedure", "actionEnabledUserSopCertification", null);
        if ("DISABLE".equalsIgnoreCase(sopCertificationLevel)) return false;
        if ("DISABLED".equalsIgnoreCase(sopCertificationLevel)) return false;
        if ("OFF".equalsIgnoreCase(sopCertificationLevel)) return false;
        return !"".equalsIgnoreCase(sopCertificationLevel);
    }

    public static final Object[] userSopMarkedAsCompletedByUser( String schemaPrefix, String userName, String sopName ) {
        String schemaName = LPPlatform.buildSchemaName(schemaPrefix, LPPlatform.SCHEMA_DATA);
        Object[][] sopInfo = getUserSop(schemaPrefix, userName, sopName);
        if(LPPlatform.LAB_FALSE.equalsIgnoreCase(sopInfo[0][0].toString())){return LPArray.array2dTo1d(sopInfo);}
        if (SOP_PASS_LIGHT_CODE.equalsIgnoreCase(sopInfo[0][3].toString())){
            return LPPlatform.trapErrorMessage(LPPlatform.LAB_FALSE, ERROR_TRAPING_SOP_MARKEDASCOMPLETED_NOT_PENDING, new Object[]{sopName, schemaPrefix});
        }
        Object[] userSopDiagnositc=Rdbms.updateRecordFieldsByFilter(schemaName, TABLENAME_DATA_USER_SOP, 
                new String[]{FIELDNAME_SOP_READ_COMPLETED, FIELDNAME_SOP_STATUS, FIELDNAME_SOP_LIGHT}, new Object[]{true, SOP_PASS_CODE, SOP_PASS_LIGHT_CODE},
                new String[]{FIELDNAME_SOP_NAME, FIELDNAME_SOP_USER_NAME}, new Object[]{sopName, userName} );
        if (LPPlatform.LAB_TRUE.equalsIgnoreCase(userSopDiagnositc[0].toString())){
            userSopDiagnositc[userSopDiagnositc.length]="Sop assigned";
        }
        return userSopDiagnositc; 
    }
    
}
