package com.idm.scim.restjersey;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.idm.scim.dto.Credentials;

@Path("/authentication")
public class AuthenticationEndpoint {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(Credentials credentials) {

		try {

			JSONObject jsonToken = new JSONObject();

			// Authenticate the user using the credentials provided
			ServiceUtils.authenticate(credentials);

			// Issue a token for the user
			String token = ServiceUtils.issueToken(credentials.getUsername());

			jsonToken.put("token", token);
			jsonToken.put("expiry", "30 min");

			// Return the token on the response
			return Response.status(200).entity(jsonToken.toString()).build();

		} catch (Exception e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}

}
