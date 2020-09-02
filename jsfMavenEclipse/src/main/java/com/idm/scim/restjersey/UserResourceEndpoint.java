package com.idm.scim.restjersey;

import static com.unboundid.scim2.common.utils.ApiConstants.MEDIA_TYPE_SCIM;
import static com.unboundid.scim2.common.utils.ApiConstants.QUERY_PARAMETER_FILTER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.idm.scim.dto.User;
import com.idm.scim.hibernate.dao.IUserDAO;
import com.idm.scim.hibernate.dao.UserDAO;
import com.unboundid.scim2.common.GenericScimResource;
import com.unboundid.scim2.common.exceptions.ScimException;
import com.unboundid.scim2.common.messages.ErrorResponse;
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

@ResourceType(description = "User Account", name = "User", schema = UserResource.class, discoverable = true)
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

		User filteredUser=null;	
		Collection<User> users = new ArrayList<User>();
		GenericScimResource preparedResource = null;
		Collection<GenericScimResource> preparedResources = new ArrayList<GenericScimResource>();
		try {
		if (filterString != null) {
			
			if(filterString.contains("userName")) {
				String[] userName = filterString.split("=");
				userName[1] = userName[1].substring(1, userName[1].length()-2);
				filteredUser = userDAO.getUserByUserName(userName[1]);
				if(filteredUser!=null) {
					users.add(filteredUser); 
				}else {
					throw new ScimException(404, "User not found", "User not found");
				}
			}
		}else {
			users = userDAO.fetchUsers();
		}

		// https://tools.ietf.org/html/draft-ietf-scim-api-19#section-4 says
		// query params should be ignored for discovery endpoints so we can't use
		// SimpleSearchResults.
		ResourcePreparer<GenericScimResource> preparer = new ResourcePreparer<GenericScimResource>(USER_RESOURCE_TYPE_DEFINITION, uriInfo);
		
		Collection<UserResource> userResourceTypes = mapUsersToUserResourceType(users);
		preparedResources = new ArrayList<GenericScimResource>(userResourceTypes.size());
		for (UserResource userResourceType : userResourceTypes) {
			preparedResource = userResourceType.asGenericScimResource();
			preparer.setResourceTypeAndLocation(preparedResource);
			preparedResources.add(preparedResource);
		}} catch (Exception e) {
			ErrorResponse er = new ErrorResponse(404);
			er.setScimType("User not found");
			er.setDetail("User not found");
			er.setId("404");
			preparedResource = er.asGenericScimResource();
			preparedResources.add(preparedResource);
		}
		return new ListResponse<GenericScimResource>(preparedResources);
	}
	
	@GET
	@Secured
	@Path("/{id}")
	@Produces({ MEDIA_TYPE_SCIM, MediaType.APPLICATION_JSON })
	public GenericScimResource getUserByID(@PathParam("id") long id, @Context final UriInfo uriInfo) throws ScimException {

		// https://tools.ietf.org/html/draft-ietf-scim-api-19#section-4 says
		// query params should be ignored for discovery endpoints so we can't use
		// SimpleSearchResults.
		ResourcePreparer<GenericScimResource> preparer = new ResourcePreparer<GenericScimResource>(USER_RESOURCE_TYPE_DEFINITION, uriInfo);
		User user = userDAO.getUserByID(id);
		UserResource userResourceType = mapUserToUserResourceType(user);
		GenericScimResource preparedResource = userResourceType.asGenericScimResource();
		preparer.setResourceTypeAndLocation(preparedResource);
		return preparedResource.asGenericScimResource();
	}

	/*
	 * @GET
	 * 
	 * @Secured
	 * 
	 * @Path("/{userName}")
	 * 
	 * @Produces({ MEDIA_TYPE_SCIM, MediaType.APPLICATION_JSON }) public
	 * GenericScimResource getUserByUserName(@PathParam("userName") String
	 * userName, @Context final UriInfo uriInfo) throws ScimException {
	 * 
	 * // https://tools.ietf.org/html/draft-ietf-scim-api-19#section-4 says // query
	 * params should be ignored for discovery endpoints so we can't use //
	 * SimpleSearchResults. ResourcePreparer<GenericScimResource> preparer = new
	 * ResourcePreparer<GenericScimResource>(USER_RESOURCE_TYPE_DEFINITION,
	 * uriInfo); User user = userDAO.getUserByUserName(userName); UserResource
	 * userResourceType = mapUserToUserResourceType(user); GenericScimResource
	 * preparedResource = userResourceType.asGenericScimResource();
	 * preparer.setResourceTypeAndLocation(preparedResource); return
	 * preparedResource.asGenericScimResource(); }
	 */
	
	@PUT
	@Secured
	@Path("/{id}")
	@Consumes({ MEDIA_TYPE_SCIM, MediaType.APPLICATION_JSON })
	@Produces({ MEDIA_TYPE_SCIM, MediaType.APPLICATION_JSON })
	public GenericScimResource updateUserByID(@PathParam("id") long id,UserResource userResource, @Context final UriInfo uriInfo) throws ScimException {

		User user = mapUserResourceTypeToUser(userResource);
		user.setId(String.valueOf(id));
		String result;
		GenericScimResource preparedResource = null;
		try {
			result = userDAO.update(user);
			if (result.contains("fail")) {
				throw new ScimException(409, "User Updation", "Error Updating User");
			}
			preparedResource = userResource.asGenericScimResource();
		} catch (Exception e) {
			ErrorResponse er = new ErrorResponse(409);
			er.setScimType("User Updation");
			er.setDetail("Error Updating User");
			er.setId(user.getId());
			er.setExternalId(user.getExternalId());
			er.setSchemaUrns(userResource.getSchemaUrns());
			preparedResource = er.asGenericScimResource();
		}
		return preparedResource.asGenericScimResource();
	}
	
	
	@POST
	@Secured
	@Consumes({ MEDIA_TYPE_SCIM, MediaType.APPLICATION_JSON })
	@Produces({ MEDIA_TYPE_SCIM, MediaType.APPLICATION_JSON })
	public GenericScimResource provisionUser(UserResource userResource, @Context final UriInfo uriInfo) throws ScimException {

		// https://tools.ietf.org/html/draft-ietf-scim-api-19#section-4 says
		// query params should be ignored for discovery endpoints so we can't use
		// SimpleSearchResults.
		User user = mapUserResourceTypeToUser(userResource);
		String result;
		GenericScimResource preparedResource = null;
		try {
			result = userDAO.insert(user);
			if (result.equals("fail")) {
				throw new ScimException(409, "User Addition", "Error Creating User");
			}
			preparedResource = userResource.asGenericScimResource();
		} catch (Exception e) {
			ErrorResponse er = new ErrorResponse(409);
			er.setScimType("User Addition");
			er.setDetail("Error creating User");
			er.setId(user.getId());
			er.setExternalId(user.getExternalId());
			er.setSchemaUrns(userResource.getSchemaUrns());
			preparedResource = er.asGenericScimResource();
		}
		return preparedResource.asGenericScimResource();
	}

	/**
	 * Maps Users to User Resource Type
	 * 
	 * @return collection of user resource
	 */
	private Collection<UserResource> mapUsersToUserResourceType(Collection<User> users) {
		Collection<UserResource> usersResourceType = new ArrayList<UserResource>();
		UserResource userResource = null;
		for (User user : users) {
			userResource = mapUserToUserResourceType(user);
			usersResourceType.add(userResource);
		}

		return usersResourceType;
	}

	/**
	 * Maps User to User Resource Type
	 * 
	 * @return User resource
	 */
	private UserResource mapUserToUserResourceType(User user) {
		UserResource userResource = new UserResource();
		Address addr = new Address();
		List<Address> addresses = new ArrayList<Address>();
		Name name = new Name();
		Email email = new Email();
		List<Email> emails = new ArrayList<Email>();
		PhoneNumber phoneNumber = new PhoneNumber();
		Role role = new Role();
		List<Role> roles = new ArrayList<Role>();
		EnterpriseUserExtension extendedUser = new EnterpriseUserExtension();

		// Set Address
		addr.setStreetAddress(user.getAddress());
		userResource.setAddresses(addresses);

		// Set UserName
		userResource.setUserName(user.getUserName());

		// Set Display Name
		userResource.setDisplayName(user.getFirstName() + " " + user.getLastName());

		// Set Id
		userResource.setId(user.getId());

		// Set Name
		name.setFamilyName(user.getLastName());
		name.setGivenName(user.getFirstName());
		userResource.setName(name);

		// Set Emails
		email.setType("work");
		email.setValue(user.getEmail());
		email.setPrimary(true);
		emails.add(email);
		userResource.setEmails(emails);

		// Set Phone Number
		phoneNumber.setType("mobile");
		phoneNumber.setValue(user.getMobile());
		phoneNumber.setPrimary(true);

		// Set User Type
		userResource.setUserType("Employee");

		// Set Status
		userResource.setActive(true);

		// Set Groups
		role.setType("role");
		role.setValue(user.getRole());
		roles.add(role);
		userResource.setRoles(roles);

		// Set Enterprise
		extendedUser = new EnterpriseUserExtension();
		extendedUser.setCostCenter("CC_123450");
		extendedUser.setDepartment("IDM");
		userResource.setExtension(extendedUser);

		return userResource;
	}

	/**
	 * Maps User to User Resource Type
	 * 
	 * @return User resource
	 */
	private User mapUserResourceTypeToUser(UserResource userResource) {
		User user = new User();

		// Set Address
		if (userResource.getAddresses() != null && !userResource.getAddresses().isEmpty()) {
			user.setAddress(userResource.getAddresses().get(0).getStreetAddress());
		}

		// Set UserName
		user.setUserName(userResource.getUserName());

		// Set Id
		user.setId(userResource.getId());

		// Set Name
		if (userResource.getName() != null) {
			user.setFirstName(userResource.getName().getGivenName());
			user.setLastName(userResource.getName().getFamilyName());
		}

		// Set Emails
		if (userResource.getEmails() != null && !userResource.getEmails().isEmpty()) {

			for (Email email : userResource.getEmails()) {
				if (email.getPrimary()) {
					user.setEmail(email.getValue());
				}
			}

		}

		// Set Phone Number
		if (userResource.getPhoneNumbers() != null && !userResource.getPhoneNumbers().isEmpty()) {

			for (PhoneNumber phoneNumber : userResource.getPhoneNumbers()) {
				if (phoneNumber.getPrimary()) {
					user.setMobile(phoneNumber.getValue());
				}
			}

		}

		// Set Groups
		if (userResource.getRoles() != null && !userResource.getRoles().isEmpty()) {
			for (Role role : userResource.getRoles()) {
				user.setRole(role.getValue());
			}
		}

		// Set Password
		user.setPassword(userResource.getPassword());

		return user;
	}

}
