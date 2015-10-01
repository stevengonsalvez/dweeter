package com.fl3x.uitls;


import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.security.Key;

import io.jsonwebtoken.*;

import java.util.Collection;
import java.util.Date;    
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class JWTUtil {

	//Sample method to construct a JWT

	public static String createJWT(String id, String secret, String issuer, String subject, long ttlMillis) {

	//The JWT signature algorithm we will be using to sign the token
	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	long nowMillis = System.currentTimeMillis();
	Date now = new Date(nowMillis);
	
	 String tokenKey = new String(DatatypeConverter.printBase64Binary(secret.getBytes()));
     
	//We will sign our JWT with our ApiKey secret
	
	
	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(tokenKey);
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
	
	public static Map<String,String> decodeJWT(String jwt, String secret) {
		//This line will throw an exception if it is not a signed JWS (as expected)
		
		String tokenKey = new String(DatatypeConverter.printBase64Binary(secret.getBytes()));
		
		
		Claims claims = Jwts.parser()
		   .setSigningKey(DatatypeConverter.parseBase64Binary(tokenKey))
		   .parseClaimsJws(jwt).getBody();
		
		Map<String, String> decodedJwt = new HashMap<>();
		
		decodedJwt.put("ID" , claims.getId());
		decodedJwt.put("Subject" , claims.getSubject());
		decodedJwt.put("Issuer" , claims.getIssuer());
		decodedJwt.put("Expiration" , claims.getExpiration().toString());
		
		return decodedJwt;
		
		}
	
	 public static void main(String args[])
	    {
		 final String id = "test";
         final String secret = "ThisIsAVerySafeKeyToUse";
         final String issuer = " steven gonsalvez";
         final String subject = "THIS";
         final long time = 3000 ;
         
         String outToken = JWTUtil.createJWT(id, secret, issuer, subject, time);
         
         System.out.println("JWT token: " + outToken); 
         
         System.out.println("decoded map: " + JWTUtil.decodeJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJKb2huIERvZSIsImlhdCI6MTQ0MzY1OTIwMywic3ViIjoiU1RBRkYiLCJpc3MiOiJmbDN4IiwiZXhwIjoxNDQzNzQ1NjAzfQ.zkHOFKxZfHNi8PzQaNpMGC-v62OF_07ECevuyLXNe-M", secret));
         
		 
	    }
	
	
	
}
