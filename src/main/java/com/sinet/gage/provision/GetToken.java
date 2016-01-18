package com.sinet.gage.provision;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * Get token from JWT 
 * 
 * @author Team Gage
 *
 */
public class GetToken {

	public String token;
	
	
	public String getToken(String username){
		token=Jwts.builder().setSubject(username)
        .claim("user", username).setIssuedAt(new Date()).setExpiration((new Date(System.currentTimeMillis() + 1800000)))
        .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
		return token;
	}
	
	
}
