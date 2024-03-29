package com.idm.scim.restjersey;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

	private static final String REALM = "scim";
	private static final String AUTHENTICATION_SCHEME = "Bearer";
	private static final String AUTHENTICATION_SCHEME_BASIC = "BASIC";

	/*
	 * @Override public void filter(ContainerRequestContext requestContext) throws
	 * IOException {
	 * 
	 * // Get the Authorization header from the request String authorizationHeader =
	 * requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
	 * 
	 * // Validate the Authorization header if
	 * (!isTokenBasedAuthentication(authorizationHeader)) {
	 * abortWithUnauthorized(requestContext); return; }
	 * 
	 * // Extract the token from the Authorization header String token =
	 * authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
	 * 
	 * try {
	 * 
	 * // Validate the token ServiceUtils.validateToken(token);
	 * 
	 * } catch (Exception e) { abortWithUnauthorized(requestContext); } }
	 */

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		try {
			// Get the Authorization header from the request
			String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

			// Validate the Authorization header
			if (isTokenBasedAuthentication(authorizationHeader)) {
				// Extract the token from the Authorization header
				String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
				// Validate the token
				ServiceUtils.validateToken(token);
				return;
			} else if (isBasicAuthentication(authorizationHeader)) {
				// Extract the token from the Authorization header
				String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
				// Validate the token
				ServiceUtils.validateCredentails(token);
				
			} else {
				abortWithUnauthorized(requestContext);
			}

		} catch (Exception e) {
			abortWithUnauthorized(requestContext);
		}
	}

	private boolean isTokenBasedAuthentication(String authorizationHeader) {

		// Check if the Authorization header is valid
		// It must not be null and must be prefixed with "Bearer" plus a whitespace
		// The authentication scheme comparison must be case-insensitive
		return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
	}

	private boolean isBasicAuthentication(String authorizationHeader) {
		// Check if the Authorization header is valid
		// It must not be null and must be prefixed with "Basic" plus a whitespace
		// The authentication scheme comparison must be case-insensitive
		return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION_SCHEME_BASIC.toLowerCase() + " ");
	}

	private void abortWithUnauthorized(ContainerRequestContext requestContext) {

		// Abort the filter chain with a 401 status code response
		// The WWW-Authenticate header is sent along with the response
		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE, AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"").build());
	}

}
