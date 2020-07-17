package com.idm.scim.restjersey;

import static com.unboundid.scim2.common.utils.ApiConstants.MEDIA_TYPE_SCIM;
import static com.unboundid.scim2.common.utils.ApiConstants.QUERY_PARAMETER_FILTER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
import com.unboundid.scim2.common.types.Address;
import com.unboundid.scim2.common.types.Email;
import com.unboundid.scim2.common.types.EnterpriseUserExtension;
import com.unboundid.scim2.common.types.Name;
import com.unboundid.scim2.common.types.PhoneNumber;
import com.unboundid.scim2.common.types.Role;
import com.unboundid.scim2.common.types.UserResource;
import com.unboundid.scim2.server.annotations.ResourceType;
import com.unboundid.scim2.server.utils.ResourcePreparer;
import com.unboundid.scim2.server.utils.ResourceTypeDefinition;

@ResourceType(description = "SCIM 2.0 User Resource Type", name = "UserResourceType", schema = UserResource.class, discoverable = true)
@Path("Users")
public class UserResourceEndpoint {

	private static final ResourceTypeDefinition USER_RESOURCE_TYPE_DEFINITION = ResourceTypeDefinition.fromJaxRsResource(UserResourceEndpoint.class);


	public static IUserDAO userDAO = new UserDAO();

	/**
	 * Service SCIM request to retrieve all User resource types defined at the
	 * service provider using GET.
	 *
	 * @param filterString The filter string used to request a subset of resources.
	 *                     Will throw 403 Forbidden if specified.
	 * @param uriInfo      UriInfo of the request.
	 * @return All resource types in a ListResponse container.
	 * @throws ScimException If an error occurs.
	 */
	@GET
	@Secured
	@Produces({ MEDIA_TYPE_SCIM, MediaType.APPLICATION_JSON })
	public ListResponse<GenericScimResource> search(@QueryParam(QUERY_PARAMETER_FILTER) final String filterString, @Context final UriInfo uriInfo) throws ScimException {
		if (filterString != null) {
			throw new ForbiddenException("Filtering not allowed");
		}

		// https://tools.ietf.org/html/draft-ietf-scim-api-19#section-4 says
		// query params should be ignored for discovery endpoints so we can't use
		// SimpleSearchResults.
		ResourcePreparer<GenericScimResource> preparer = new ResourcePreparer<GenericScimResource>(USER_RESOURCE_TYPE_DEFINITION, uriInfo);
		Collection<User> users = userDAO.fetchUsers();
		Collection<UserResource> userResourceTypes  = mapUsersToUserResourceType(users);
		Collection<GenericScimResource> preparedResources = new ArrayList<GenericScimResource>(userResourceTypes.size());
		for (UserResource userResourceType : userResourceTypes) {
			GenericScimResource preparedResource = userResourceType.asGenericScimResource();
			preparer.setResourceTypeAndLocation(preparedResource);
			preparedResources.add(preparedResource);
		}
		return new ListResponse<GenericScimResource>(preparedResources);
	}
	/**
	 * Maps Users to User Resource Type
	 * @return collection of user resource
	 */
	private Collection<UserResource> mapUsersToUserResourceType(Collection<User> users){
		Collection<UserResource> usersResourceType = new ArrayList<UserResource>();
		UserResource userResource = null;
		Address addr = null;
		List<Address> addresses = null;
		Name name = null;
		Email email=null;
		List<Email> emails = null;
		PhoneNumber phoneNumber = null;
		Role role = null;
		List<Role> roles = null;
		EnterpriseUserExtension extendedUser = null;
		for(User user: users) {
			userResource = new UserResource();
			
			// Set Address
			addr = new Address();
			addr.setStreetAddress(user.getAddress());
			addresses = new ArrayList<Address>();
			userResource.setAddresses(addresses);
			
			//Set UserName
			userResource.setUserName(user.getUserName());
			
			//Set Display Name
			userResource.setDisplayName(user.getFirstName()+ " " + user.getLastName());
			
			//Set Id
			userResource.setId(user.getId());
			
			//Set Name
			name = new Name();
			name.setFamilyName(user.getLastName());
			name.setGivenName(user.getFirstName());
			userResource.setName(name);
			
			//Set Emails
			email = new Email();
			email.setType("work");
			email.setValue(user.getEmail());
			email.setPrimary(true);
			emails = new ArrayList<Email>();
			emails.add(email);
			userResource.setEmails(emails);
			
			//Set Phone Number
			phoneNumber = new PhoneNumber();
			phoneNumber.setType("mobile");
			phoneNumber.setValue(user.getMobile());
			phoneNumber.setPrimary(true);
			
			//Set User Type
			userResource.setUserType("Employee");
			
			//Set Status
			userResource.setActive(true);
			
			//Set Groups
			role = new Role();
			role.setType("role");
			role.setValue(user.getRole());
			roles = new ArrayList<Role>();
			roles.add(role);
			userResource.setRoles(roles);
			
			//Set Enterprise
			extendedUser = new EnterpriseUserExtension();
			extendedUser.setCostCenter("CC_123450");
			extendedUser.setDepartment("IDM");
			userResource.setExtension(extendedUser);
			
			usersResourceType.add(userResource);
			
		}
		
		return usersResourceType;
	}

}
