package com.idm.scim.restjersey;

import static com.unboundid.scim2.common.utils.ApiConstants.MEDIA_TYPE_SCIM;
import static com.unboundid.scim2.common.utils.ApiConstants.QUERY_PARAMETER_FILTER;

import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.idm.scim.dto.User;
import com.idm.scim.hibernate.dao.IUserDAO;
import com.idm.scim.hibernate.dao.UserDAO;
import com.unboundid.scim2.common.GenericScimResource;
import com.unboundid.scim2.common.exceptions.ForbiddenException;
import com.unboundid.scim2.common.exceptions.ScimException;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.types.Group;
import com.unboundid.scim2.common.types.GroupResource;
import com.unboundid.scim2.server.annotations.ResourceType;
import com.unboundid.scim2.server.utils.ResourcePreparer;
import com.unboundid.scim2.server.utils.ResourceTypeDefinition;

@ResourceType(description = "SCIM 2.0 Group Resource Type", name = "GroupResourceType", schema = GroupResource.class, discoverable = true)
@Path("Groups")
public class GroupResourceEndPoint {

	/*
	 * private static final ResourceTypeDefinition GROUP_RESOURCE_TYPE_DEFINITION =
	 * ResourceTypeDefinition.fromJaxRsResource(GroupResourceEndPoint.class);
	 * 
	 * 
	 * public static IUserDAO userDAO = new UserDAO();
	 * 
	 *//**
		 * Service SCIM request to retrieve all Group resource types defined at the
		 * service provider using GET.
		 *
		 * @param filterString The filter string used to request a subset of resources.
		 *                     Will throw 403 Forbidden if specified.
		 * @param uriInfo      UriInfo of the request.
		 * @return All resource types in a ListResponse container.
		 * @throws ScimException If an error occurs.
		 *//*
			 * @GET
			 * 
			 * @Produces({ MEDIA_TYPE_SCIM, MediaType.APPLICATION_JSON }) public
			 * ListResponse<GenericScimResource> search(@QueryParam(QUERY_PARAMETER_FILTER)
			 * final String filterString, @Context final UriInfo uriInfo) throws
			 * ScimException { if (filterString != null) { throw new
			 * ForbiddenException("Filtering not allowed"); }
			 * 
			 * // https://tools.ietf.org/html/draft-ietf-scim-api-19#section-4 says // query
			 * params should be ignored for discovery endpoints so we can't use //
			 * SimpleSearchResults. ResourcePreparer<GenericScimResource> preparer = new
			 * ResourcePreparer<GenericScimResource>(GROUP_RESOURCE_TYPE_DEFINITION,
			 * uriInfo); Collection<Group> groupResourceTypes = userDAO.fetchGroups();
			 * Collection<GenericScimResource> preparedResources = new
			 * ArrayList<GenericScimResource>(groupResourceTypes.size()); for (Group
			 * groupResourceType : groupResourceTypes) { GenericScimResource
			 * preparedResource = groupResourceType.asGenericScimResource();
			 * preparer.setResourceTypeAndLocation(preparedResource);
			 * preparedResources.add(preparedResource); } return new
			 * ListResponse<GenericScimResource>(preparedResources); }
			 */

}
