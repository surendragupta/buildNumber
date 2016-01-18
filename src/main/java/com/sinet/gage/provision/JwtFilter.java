package com.sinet.gage.provision;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
/**
 * JwtFilter 
 * 
 * @author Team Gage
 *
 */
public class JwtFilter extends GenericFilterBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);
    @Override
    public void doFilter(final ServletRequest req,
                         final ServletResponse res,
                         final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
        }

        final String token = authHeader.substring(7); // The part after "Bearer "

        
        Claims claims= getClaims(token);
        String refreshedToken =	new GetToken().getToken(claims.getSubject());
        request.setAttribute("refreshedToken", refreshedToken);
             
        chain.doFilter(req, res);
    }
    public Claims getClaims(String token) throws ServletException{
    		  Claims claims =null;
    		  try {
    	         claims = Jwts.parser().setSigningKey("secretkey")
    	              .parseClaimsJws(token).getBody().setExpiration(new Date(System.currentTimeMillis() + 900000));
    	         
    	          LOGGER.debug("Subject user: " + claims.getSubject());
    	          LOGGER.debug("Expiration: " + claims.getExpiration());
    	         }catch (final SignatureException e) {
    	        	LOGGER.error("Invalid Token"+ e.getLocalizedMessage());
    	            throw new ServletException("Invalid token.");
    	        }
    		 return claims;
    		  
    	  }
}
