/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LabPLANET.utilities;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;   
import io.jsonwebtoken.*;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Administrator
 */
public class LabPLANETWebToken {

 
 
//Sample method to construct a JWT
public static String createJWT(String id, String issuer, String subject, long ttlMillis) {
 
    String getSecret = "LabPLANETConDosCojones";
    
    //The JWT signature algorithm we will be using to sign the token
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
 
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);
 
    //We will sign our JWT with our ApiKey secret    
//    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(getSecret);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
 
    //Let's set the JWT Claims
    JwtBuilder builder = Jwts.builder().setId(id)
                                .setIssuedAt(now)
                                .setSubject(subject)
                                .setIssuer(issuer)
                                .signWith(signatureAlgorithm, signingKey);
 
    //if it has been specified, let's add the expiration
    if (ttlMillis >= 0) {
    long expMillis = nowMillis + ttlMillis;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
    }
 
    //Builds the JWT and serializes it to a compact, URL-safe string
    return builder.compact();
}    

//Sample method to validate and read the JWT
public static void parseJWT(String jwt) {
     String getSecret = "LabPLANETConDosCojones";
    //This line will throw an exception if it is not a signed JWS (as expected)
    Claims claims = Jwts.parser()         
       //.setSigningKey(DatatypeConverter.parseBase64Binary(apiKey.getSecret()))
       .setSigningKey(DatatypeConverter.parseBase64Binary(getSecret))            
       .parseClaimsJws(jwt).getBody();
    System.out.println("ID: " + claims.getId());
    System.out.println("Subject: " + claims.getSubject());
    System.out.println("Issuer: " + claims.getIssuer());
    System.out.println("Expiration: " + claims.getExpiration());
}

}
