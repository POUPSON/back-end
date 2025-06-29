//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package PFE.CDSIR_AGENCY.config;

import PFE.CDSIR_AGENCY.entity.Administrateur;
import PFE.CDSIR_AGENCY.entity.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
	@Value("${app.jwt.secret}")
	private String secret;
	@Value("${app.jwt.expiration}")
	private long expiration;

	public String generateToken(Client client) {
		Map<String, Object> claims = new HashMap();
		claims.put("userId", client.getId());
		claims.put("email", client.getEmail());
		claims.put("role", client.getRole());
		return this.buildToken(claims, client.getEmail());
	}

	public String generateTokenAdmin(Administrateur admin) {
		Map<String, Object> claims = new HashMap();
		claims.put("userId", admin.getId());
		claims.put("numeroCni", admin.getNumeroCni());
		claims.put("role", admin.getRole().name());
		return this.buildToken(claims, admin.getNumeroCni());
	}

	private String buildToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + this.expiration)).signWith(this.getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractEmail(String token) {
		return (String)this.extractClaim(token, Claims::getSubject);
	}

	public String extractNumeroCni(String token) {
		return (String)this.extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = this.extractAllClaims(token);
		return (T)claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return (Claims)Jwts.parserBuilder().setSigningKey(this.getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		String username = this.extractEmail(token);
		return username.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
	}

	public boolean validateAdminToken(String token, UserDetails userDetails) {
		String numeroCni = this.extractNumeroCni(token);
		return numeroCni.equals(userDetails.getUsername()) && !this.isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return this.extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) {
		return (Date)this.extractClaim(token, Claims::getExpiration);
	}

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(this.secret.getBytes());
	}
}
