/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databases;

import LabPLANET.utilities.LabPLANETArray;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Administrator
 */
public class Token {
    String KEY = "mi clave";
    String ISSUER = "LabPLANETdestrangisInTheNight";
    
    private Boolean _isValidToken(String jwtToken){       
        try {
            Claims claims = Jwts.parser()         
               .setSigningKey(DatatypeConverter.parseBase64Binary(KEY))
               .parseClaimsJws(jwtToken).getBody();

            System.out.println("ID: " + claims.getId());
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issuer: " + claims.getIssuer());
            System.out.println("Expiration: " + claims.getExpiration());
            
            return true;
        }catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e){
            return false;
        }
    }
    
    /**
     *
     * @return
     */
    public String[] tokenParamsList(){
        String[] diagnoses = new String[0];        
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "userDB");
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "userDBPassword");
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "internalUserID");
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "userRole");
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "appSessionId");
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "appSessionStartedDate");
        diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, "eSign");
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
            
            diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, true);
            diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, jwt);
            return diagnoses;
            
        } catch (JWTVerificationException exception){
            diagnoses = LabPLANETArray.addValueToArray1D(diagnoses, false);
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
        
       if ((Boolean) tokenObj[0]==false) return "LABPLANET_FALSE";

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
            infoFromToken = LabPLANETArray.addValueToArray1D(infoFromToken, paramValue);
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
        myParams.put("userDB", userDBId);                   myParams.put("userDBPassword", userDBPassword);
        myParams.put("internalUserID", userId);             myParams.put("userRole", userRole);
        myParams.put("appSessionId", appSessionId);  myParams.put("appSessionStartedDate", appSessionStartedDate);
        myParams.put("eSign", eSign); 
        String token = JWT.create()
                .withHeader(myParams)
                .withIssuer(ISSUER)
                .sign(algorithm);
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
