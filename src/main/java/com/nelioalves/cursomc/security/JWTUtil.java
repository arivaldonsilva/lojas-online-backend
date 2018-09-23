package com.nelioalves.cursomc.security;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private String expiration;

	public String generateToken(String username) {
		Calendar cal = Calendar.getInstance();
		Long time =System.currentTimeMillis();
		cal.setTimeInMillis(time);
		System.out.println("CRIADO EM: "+cal.getTime());
		cal.setTimeInMillis(time+Integer.parseInt(expiration));
		System.out.println("VÁLIDO ATÉ: "+cal.getTime());
//		SimpleDateFormat sdf = new SimpleDateFormat();
//		Date dataToken = null;
//		try {
//			System.out.println(cal.getTime());
//			dataToken = sdf.parse(System.currentTimeMillis() + expiration);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		return Jwts.builder().setSubject(username).setExpiration(cal.getTime())
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}
}
