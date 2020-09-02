package com.idm.scim.restjersey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.fasterxml.jackson.databind.node.TextNode;
import com.unboundid.scim2.client.ScimService;
import com.unboundid.scim2.common.GenericScimResource;
import com.unboundid.scim2.common.exceptions.BadRequestException;
import com.unboundid.scim2.common.exceptions.ScimException;
import com.unboundid.scim2.common.filters.Filter;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.types.Email;
import com.unboundid.scim2.common.types.Name;
import com.unboundid.scim2.common.types.Role;
import com.unboundid.scim2.common.types.UserResource;

public class ScimClient {

		  public static void main (String[] args) {
			// Create a ScimService
			  HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("rpalla", "test");
			  Client client = ClientBuilder.newClient().register(feature);
			  WebTarget target = client.target("http://192.168.0.66:8080/jsfMavenEclipse/scim/v2");
			  ScimService scimService = new ScimService(target);
			  try {
			  // Create a user
			  UserResource user = new UserResource();
			  user.setUserName("babs");
			  user.setPassword("secret");
			  Name name = new Name()
			    .setGivenName("Barbara")
			    .setFamilyName("Jensen");
			  user.setName(name);
			  Email email = new Email()
			    .setType("home")
			    .setPrimary(true)
			    .setValue("babs@example.com");
			  user.setEmails(Collections.singletonList(email));
			 // user = scimService.create("Users", user);

			  //temporary
			  user.setId("2");
			  
			  
			  // Retrieve the user as a UserResource and replace with a modified instance using PUT
			  user = scimService.retrieve("Users", user.getId(), UserResource.class);
			  List<Role> roles = new ArrayList<Role>();
			  Role role = new Role();
			  role.setValue("admin");
			  roles.add(role);
			  user.setRoles(roles);
			  user.setUserName("babs");
			  email = new Email()
					    .setType("home")
					    .setPrimary(true)
					    .setValue("babs@example.com");
					  user.setEmails(Collections.singletonList(email));
					  
					  user.setId("30");
			  
			  user = scimService.replace(user);

			  // Retrieve the user as a GenericScimResource and replace with a modified instance using PUT
			  GenericScimResource genericUser =
			      scimService.retrieve("Users", user.getId(), GenericScimResource.class);
			  genericUser.replaceValue("displayName", TextNode.valueOf("Babs Jensen"));
			  genericUser = scimService.replaceRequest(genericUser).invoke();

			  // Perform a partial modification of the user using PATCH
			  scimService.modifyRequest("Users", user.getId())
			             .replaceValue("displayName", "Babs")
			             .invoke(GenericScimResource.class);

			  // Perform a password change using PATCH
			  scimService.modifyRequest("Users", user.getId())
			             .replaceValue("password", "new-password")
			             .invoke(GenericScimResource.class);

			  // Search for users with the same last name as our user
				ListResponse<UserResource> searchResponse =
				    scimService.searchRequest("Users")
				          .filter(Filter.eq("name.familyName", user.getName().getFamilyName()).toString())
				          .page(1, 5)
				          .attributes("name")
				          .invoke(UserResource.class);
			} catch (BadRequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ScimException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	
}
