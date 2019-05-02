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
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class Token {
    
    String userDB;
    String userDBPassword;
    String personId;
    String userRole;
    String userEsign;
    String appSessionId;
    Date appSessionStartDate;
    
    String KEY = "mi clave";
    String ISSUER = "LabPLANETdestrangisInTheNight";
    
    public static final String TOKEN_PARAM_USERDB="userDB";
    public static final String TOKEN_PARAM_USERPW="userDBPassword";
    public static final String TOKEN_PARAM_INTERNAL_USERID="internalUserID";
    public static final String TOKEN_PARAM_USER_ROLE="userRole";
    public static final String TOKEN_PARAM_APP_SESSION_ID="appSessionId";
    public static final String TOKEN_PARAM_APP_SESSION_STARTED_DATE="appSessionStartedDate";
    public static final String TOKEN_PARAM_USER_ESIGN="eSign";
    
    /**
     *
     * @return
     */
    public String[] tokenParamsList(){
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
            //DecodedJWT decode = JWT.decode(token);
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
    public String validateToken(String token, String paramName){       
       Object[] tokenObj = isValidToken(token);
        
       if ((Boolean) tokenObj[0]==false) return LPPlatform.LAB_FALSE;

       DecodedJWT jwt = (DecodedJWT) tokenObj[1];
       Claim header1 = jwt.getHeaderClaim(paramName);            
       return header1.asString();            
    }    

    /**
     *
     * @param token
     * @param paramName
     * @return
     */
    public String[] validateToken(String token, String[] paramName){
        String[] infoFromToken = new String[0];
        
        for (String pn: paramName){
            String paramValue = validateToken(token, pn);
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
        Algorithm algorithm = Algorithm.HMAC256(KEY);
        Map <String, Object> myParams = new HashMap<>();
        myParams.put(TOKEN_PARAM_USERDB, userDBId);                   myParams.put(TOKEN_PARAM_USERPW, userDBPassword);
        myParams.put(TOKEN_PARAM_INTERNAL_USERID, userId);             myParams.put(TOKEN_PARAM_USER_ROLE, userRole);
        myParams.put(TOKEN_PARAM_APP_SESSION_ID, appSessionId);  myParams.put(TOKEN_PARAM_APP_SESSION_STARTED_DATE, appSessionStartedDate);
        myParams.put(TOKEN_PARAM_USER_ESIGN, eSign); 
        String token = JWT.create()
                .withHeader(myParams)
                .withIssuer(ISSUER)
                .sign(algorithm);
        this.userDB=userDBId;
        this.userDBPassword=userDBPassword;
        this.personId=userId;
        this.userRole=userRole;
        this.userEsign=eSign;
        this.appSessionId=appSessionId;
        this.appSessionStartDate=Date.valueOf(appSessionStartedDate);        
        return token;
        
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
}
