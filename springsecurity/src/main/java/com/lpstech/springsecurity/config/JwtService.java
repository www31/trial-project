package com.lpstech.springsecurity.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	/**
	 *  Hex Key:		0c3b22e0ba4ca490dd8b849128e75d407cd75a33d1e38d7bda42b13531cf00c6
	 *	Plain text key:	CRY SELF MASK DOES OVEN ACID SKEW BONG LET BALL DOTE JAIL FACT MICE GLOM JUKE ROW MOLT SHED GAD ANTE CARL TROT CANT
	 *	Reverse:		0c3b22e0ba4ca490dd8b849128e75d407cd75a33d1e38d7bda42b13531cf00c6
	 */
	private static final String SECRET_KEY = "0c3b22e0ba4ca490dd8b849128e75d407cd75a33d1e38d7bda42b13531cf00c6";
	private static final Integer ONETHOU_MS = 1000;
	private static final Integer SIXTY_MINS = 60;
	private static final Integer TWENTYFOUR_HRS = 24;
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public String generateToken(
		Map<String, Object> extraClaims,
		UserDetails userDetails
	) {
		return Jwts
				.builder()
				.claims().empty().add(extraClaims).and()
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + ONETHOU_MS * SIXTY_MINS * TWENTYFOUR_HRS))
				.signWith(getSignInKey(), Jwts.SIG.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getSignInKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
				
	}

	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}


}
