package com.idm.scim.restjersey;

import static com.unboundid.scim2.common.utils.ApiConstants.MEDIA_TYPE_SCIM;
import static com.unboundid.scim2.common.utils.ApiConstants.QUERY_PARAMETER_FILTER;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.unboundid.scim2.common.GenericScimResource;
import com.unboundid.scim2.common.exceptions.ForbiddenException;
import com.unboundid.scim2.common.exceptions.ScimException;
import com.unboundid.scim2.common.types.ResourceTypeResource;
import com.unboundid.scim2.server.annotations.ResourceType;
import com.unboundid.scim2.server.resources.ResourceTypesEndpoint;

@ResourceType(description = "SCIM 2.0 Resource Type", name = "ResourceType", schema = ResourceTypeResource.class, discoverable = true)
@Path("ResourceTypes")
public class ResourceTypesEndPoint extends ResourceTypesEndpoint {

	
	@Path("/User")
	@GET
	@Produces({ MEDIA_TYPE_SCIM, MediaType.APPLICATION_JSON })
	public GenericScimResource getAccountSchema(@QueryParam(QUERY_PARAMETER_FILTER) final String filterString, @Context final UriInfo uriInfo) throws ScimException {
		if (filterString != null) {
			throw new ForbiddenException("Filtering not allowed");
		}

		// https://tools.ietf.org/html/draft-ietf-scim-api-19#section-4 says
		// query params should be ignored for discovery endpoints so we can't use
		// SimpleSearchResults.
		//ResourcePreparer<GenericScimResource> preparer = new ResourcePreparer<GenericScimResource>(RESOURCE_TYPE_DEFINITION, uriInfo);
		Collection<ResourceTypeResource> resourceTypes = getResourceTypes();
		GenericScimResource preparedResource = null;
		for (ResourceTypeResource resourceType : resourceTypes) {
			if(resourceType.getName().equalsIgnoreCase("user")) {
				preparedResource = resourceType.asGenericScimResource();
				//preparer.setResourceTypeAndLocation(preparedResource);
				break;
			}
		}
		return preparedResource;
	}

}
