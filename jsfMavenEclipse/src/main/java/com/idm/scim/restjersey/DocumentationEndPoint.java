package com.idm.scim.restjersey;

import static com.unboundid.scim2.common.utils.ApiConstants.MEDIA_TYPE_SCIM;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.unboundid.scim2.common.GenericScimResource;

@Path("documentation")
public class DocumentationEndPoint {

	
	@GET
	@Path("ServiceProviderConfig")
	@Produces({MEDIA_TYPE_SCIM, MediaType.APPLICATION_JSON})
	public GenericScimResource getServiceProviderConfigDocumentation() {
		//Include the Documentation for Service Provider Config
		return new GenericScimResource();
	}
	
	@GET
	@Path("authentication")
	@Produces({MEDIA_TYPE_SCIM, MediaType.APPLICATION_JSON})
	public GenericScimResource getAuthenticationDocumentation() {
		//Include the Documentation for Authentication
		return new GenericScimResource();
	}
	
}
