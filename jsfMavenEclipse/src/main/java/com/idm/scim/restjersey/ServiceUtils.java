package com.idm.scim.restjersey;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.idm.scim.dto.Credentials;
import com.idm.scim.hibernate.dao.IUserDAO;
import com.idm.scim.hibernate.dao.UserDAO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class ServiceUtils {

	public static IUserDAO userDAO = new UserDAO();

	public final static String SECRET_KEY = "This is Sample SCIM Implementation in Java by Ramesh Palla from Identic Group AG";

	public static void authenticate(Credentials creds) throws Exception {
		// Authenticate against a database, LDAP, file or whatever
		if (!userDAO.validateUser(creds)) {
			throw new Exception("Provided details are invalid");
		}
		// Throw an Exception if the credentials are invalid
	}

	public static String issueToken(String username) {

		// Issue a token (can be a random String persisted to a database or a JWT token)
		// The issued token must be associated to a user
		// Return the issued token
		JwtBuilder builder = null;
		try {
			long ttlMillis = 1800000;

			long nowMillis = System.currentTimeMillis();
			Date now = new Date(nowMillis);

			// Let's set the JWT Claims
			builder = Jwts.builder().setId(UUID.randomUUID().toString()).setIssuedAt(now).setSubject(username).setIssuer("com.idm.scim").signWith(getSigningKey());

			// if it has been specified, let's add the expiration
			if (ttlMillis > 0) {
				long expMillis = nowMillis + ttlMillis;
				Date exp = new Date(expMillis);
				builder.setExpiration(exp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Builds the JWT and serializes it to a compact, URL-safe string
		return builder.compact();

	}

	public static void validateToken(String token) throws Exception {
		// Check if the token was issued by the server and if it's not expired
		// Throw an Exception if the token is invalid
		// This line will throw an exception if it is not a signed JWS (as expected)
		// The JWT signature algorithm we will be using to sign the token
		Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);

	}

	public static Key getSigningKey() {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(ServiceUtils.SECRET_KEY);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		return signingKey;

	}

}
