package com.idm.scim.restjersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/v2/Users")
public class ScimServices {

	@GET
	@Produces("application/scim+json")
	public Response users() {
		JSONObject jsonObject = new JSONObject();
		Double fahrenheit = 98.24;
		Double celsius;
		celsius = (fahrenheit - 32) * 5 / 9;
		jsonObject.put("F Value", fahrenheit);
		jsonObject.put("C Value", celsius);
 
		String result = "@Produces(\"application/json\") Output: \n\nF to C Converter Output: \n\n" + jsonObject;
		return Response.status(200).entity(result).build();
	}

	/*
	 * @GET
	 * 
	 * @Produces("application/xml") public String convertCtoF() {
	 * 
	 * Double fahrenheit; Double celsius = 36.8; fahrenheit = ((celsius * 9) / 5) +
	 * 32;
	 * 
	 * String result =
	 * "@Produces(\"application/xml\") Output: \n\nC to F Converter Output: \n\n" +
	 * fahrenheit; return "<ctofservice>" + "<celsius>" + celsius + "</celsius>" +
	 * "<ctofoutput>" + result + "</ctofoutput>" + "</ctofservice>"; }
	 */

}
