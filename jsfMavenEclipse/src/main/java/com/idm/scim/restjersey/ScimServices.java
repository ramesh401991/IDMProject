package com.idm.scim.restjersey;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idm.scim.hibernate.dao.IUserDAO;
import com.idm.scim.hibernate.dao.UserDAO;

@Path("/UsersN")
public class ScimServices {

	public static IUserDAO userDAO = new UserDAO();
	
	@GET
	@Secured
	@Produces("application/scim+json")
	public Response users() {
			
		// Creating Object of ObjectMapper define in Jakson Api 
        ObjectMapper Obj = new ObjectMapper(); 
        
        try { 
        	  
            // get User object as a json string 
            String jsonStr = Obj.writeValueAsString(userDAO.fetchUsers()); 
  
            // Displaying JSON String 
            System.out.println(jsonStr); 
            
            return Response.status(200).entity(jsonStr).build();
        } 
  
        catch (IOException e) { 
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } 
		
	}

}
