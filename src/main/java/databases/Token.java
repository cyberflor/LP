/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

import LabPLANET.utilities.LPPlatform;
import LabPLANET.utilities.LPArray;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.sql.Date;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.util.HashMap;
import java.util.Map;
//import org.apache.commons.net.util.Base64;
/**
 *
 * @author Administrator
 */
public final class Token {   
    private static final String KEY = "mi clave";
    private static final String ISSUER = "LabPLANETdestrangisInTheNight";
    
    private static final String TOKEN_PARAM_USERDB="userDB";
    private static final String TOKEN_PARAM_USERPW="userDBPassword";
    private static final String TOKEN_PARAM_INTERNAL_USERID="internalUserID";
    private static final String TOKEN_PARAM_USER_ROLE="userRole";
    private static final String TOKEN_PARAM_USER_ESIGN="eSign";
    private static final String TOKEN_PARAM_APP_SESSION_ID="appSessionId";
    private static final String TOKEN_PARAM_APP_SESSION_STARTED_DATE="appSessionStartedDate";
    
    private static final String TOKEN_PARAM_PREFIX = "TOKEN_";
    
    private String userName="";
    private String usrPw="";
    private String personName="";
    private String userRole="";
    private String eSign="";
    private String appSessionId="";
    private Date appSessionStartedDate= null;
    
    //public Token(){}
    public Token(String tokenString){
        String[] tokenParams = tokenParamsList();
        String[] tokenParamsValues = getTokenParamValue(tokenString, tokenParams);

        this.userName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERDB)];
        this.usrPw = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USERPW)];
        this.personName = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_INTERNAL_USERID)];         
        this.userRole = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ROLE)];                             
        this.eSign = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_USER_ESIGN)];     
        this.appSessionId = tokenParamsValues[LPArray.valuePosicInArray(tokenParams, Token.TOKEN_PARAM_APP_SESSION_ID)];     
    }

    /**
     *
     * @return
     */
    private String[] tokenParamsList(){
        String[] diagnoses = new String[0];        
        diagnoses = LPArray.addValueToArray1D(diagnoses, TOKEN_PARAM_USERDB);
        diagnoses = LPArray.addValueToArray1D(diagnoses, TOKEN_PARAM_USERPW);
        diagnoses = LPArray.addValueToArray1D(diagnoses, TOKEN_PARAM_INTERNAL_USERID);
        diagnoses = LPArray.addValueToArray1D(diagnoses, TOKEN_PARAM_USER_ROLE);
        diagnoses = LPArray.addValueToArray1D(diagnoses, TOKEN_PARAM_APP_SESSION_ID);
        diagnoses = LPArray.addValueToArray1D(diagnoses, TOKEN_PARAM_APP_SESSION_STARTED_DATE);
        diagnoses = LPArray.addValueToArray1D(diagnoses, TOKEN_PARAM_USER_ESIGN);
        return diagnoses;
    }  
    
    private Object[] isValidToken(String token){
        Object[] diagnoses = new Object[0];
        try {
            
            Algorithm algorithm = Algorithm.HMAC256(KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build(); //Reusable verifier instance
            DecodedJWT decode = JWT.decode(token); // This is need for the check that should be implemented
            DecodedJWT jwt = verifier.verify(token);            
            
            // Check that the fields in the header are present, not just verify that the token construction is ok.
            
            diagnoses = LPArray.addValueToArray1D(diagnoses, true);
            diagnoses = LPArray.addValueToArray1D(diagnoses, jwt);
            return diagnoses;
            
        } catch (JWTVerificationException exception){
            diagnoses = LPArray.addValueToArray1D(diagnoses, false);
            return diagnoses;
        }       
    }
    
    /**
     *
     * @param token
     * @return
     */
    public String validateToken(String token){
        return isValidToken(token)[0].toString();
    }
    
    /**
     *
     * @param token
     * @param paramName
     * @return
     */
    public String[] getTokenParamValue(String token, String[] paramName){
        String[] infoFromToken = new String[0];
        String[] tokenParams = tokenParamsList();
        
        for (String pn: paramName){
            String paramValue = getTokenParamValue(token, pn);
            infoFromToken = LPArray.addValueToArray1D(infoFromToken, paramValue);
        }
        return infoFromToken;            
    }    

    /**
     *
     * @param userDBId
     * @param userDBPassword
     * @param userId
     * @param userRole
     * @param appSessionId
     * @param appSessionStartedDate
     * @param eSign
     * @return
     */
    public String  createToken(String userDBId, String userDBPassword, String userId, String userRole, String appSessionId, String appSessionStartedDate, String eSign){        
        Algorithm algorithm = Algorithm.HMAC256(KEY); /*long tiempo = System.currentTimeMillis();
        String vwt = Jwts.builder()
        .signWith(SignatureAlgorithm.HS256, KEY)
        .setSubject(userId)
        .setHeaderParams(myParams)
        //.setHeaderParam("", )
        //.setHeaderParam("", )
        //.setHeaderParam("", )
        .setIssuer(ISSUER)
        .setIssuedAt(new Date(tiempo))
        .setExpiration(new Date(tiempo+900000))
        .claim("email", "mymail@gmail.com")
        .compact();
        return vwt;*/
        Map <String, Object> myParams = new HashMap<>();
        myParams.put(TOKEN_PARAM_USERDB, userDBId);
        myParams.put(TOKEN_PARAM_USERPW, userDBPassword);
        myParams.put(TOKEN_PARAM_INTERNAL_USERID, userId);
        myParams.put(TOKEN_PARAM_USER_ROLE, userRole);
        myParams.put(TOKEN_PARAM_APP_SESSION_ID, appSessionId);
        myParams.put(TOKEN_PARAM_APP_SESSION_STARTED_DATE, appSessionStartedDate);
        myParams.put(TOKEN_PARAM_USER_ESIGN, eSign);
        
        try{
            return JWT.create()
                    .withHeader(myParams)
                    .withIssuer(ISSUER)                    
                    .sign(algorithm);
       } catch (JWTCreationException exception){
            return "ERROR: You need to enable Algorithm.HMAC256";        
        }
        
        
        /*long tiempo = System.currentTimeMillis();
        String vwt = Jwts.builder()
        .signWith(SignatureAlgorithm.HS256, KEY)
        .setSubject(userId)
        .setHeaderParams(myParams)
        //.setHeaderParam("", )
        //.setHeaderParam("", )
        //.setHeaderParam("", )
        .setIssuer(ISSUER)
        .setIssuedAt(new Date(tiempo))
        .setExpiration(new Date(tiempo+900000))
        .claim("email", "mymail@gmail.com")
        .compact();
        return vwt;*/
    }    
        
    public String getTokenParamValue(String token, String paramName){
       Object[] tokenObj = isValidToken(token);
        
       if (!Boolean.valueOf(tokenObj[0].toString())) return LPPlatform.LAB_FALSE;

       DecodedJWT jwt = (DecodedJWT) tokenObj[1];
       Claim header1 = jwt.getHeaderClaim(paramName);            
       return header1.asString();                    
    }
    /**
     * The fieldName should include one prefix that is "TOKEN_" otherwise it will not be interpreted as a correct param.
     * @param token
     * @param fieldName
     * @return
     */
    public static String[] getTokenFieldValue(String fieldName, String token) {
        if (fieldName == null) {
            return new String[]{LPPlatform.LAB_FALSE, ""};
        }
        if (!fieldName.toUpperCase().contains(TOKEN_PARAM_PREFIX)) {
            return new String[]{LPPlatform.LAB_FALSE, ""};
        }
        Token tokenObj = new Token(token);
        String tokenParamValue = tokenObj.getTokenParamValue(token, fieldName.replace(TOKEN_PARAM_PREFIX, ""));
        if (tokenParamValue != null) {
            return new String[]{LPPlatform.LAB_TRUE, tokenParamValue};
        } else {
            return new String[]{LPPlatform.LAB_FALSE, ""};
        }
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the userDBPassword
     */
    public String getUsrPw() {
        return usrPw;
    }

    /**
     * @return the personName
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * @return the userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * @return the eSign
     */
    public String geteSign() {
        return eSign;
    }

    /**
     * @return the appSessionId
     */
    public String getAppSessionId() {
        return appSessionId;
    }

    /**
     * @return the appSessionStartedDate
     */
    public Date getAppSessionStartedDate() {
        return appSessionStartedDate;
    }
    
}
