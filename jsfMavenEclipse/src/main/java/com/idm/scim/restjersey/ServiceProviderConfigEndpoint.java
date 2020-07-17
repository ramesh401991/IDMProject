package com.idm.scim.restjersey;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;

import com.unboundid.scim2.common.exceptions.ScimException;
import com.unboundid.scim2.common.types.AuthenticationScheme;
import com.unboundid.scim2.common.types.BulkConfig;
import com.unboundid.scim2.common.types.ChangePasswordConfig;
import com.unboundid.scim2.common.types.ETagConfig;
import com.unboundid.scim2.common.types.FilterConfig;
import com.unboundid.scim2.common.types.PatchConfig;
import com.unboundid.scim2.common.types.ServiceProviderConfigResource;
import com.unboundid.scim2.common.types.SortConfig;
import com.unboundid.scim2.server.annotations.ResourceType;
import com.unboundid.scim2.server.resources.AbstractServiceProviderConfigEndpoint;

@ResourceType(description = "SCIM 2.0 Service Provider Config", name = "ServiceProviderConfig", schema = ServiceProviderConfigResource.class, discoverable = false)
@Path("ServiceProviderConfig")
public class ServiceProviderConfigEndpoint extends AbstractServiceProviderConfigEndpoint {

	@Override
	public ServiceProviderConfigResource getServiceProviderConfig() throws ScimException {

		ServiceProviderConfigResource serviceProviderConfigResource = null;

		try {

			String serviceProviderHelpdocUri = "/documentation/ServiceProviderConfig";

			PatchConfig patch = new PatchConfig(false);

			BulkConfig bulk = new BulkConfig(false, 0, 0);

			FilterConfig filter = new FilterConfig(false, 0);

			ChangePasswordConfig changePassword = new ChangePasswordConfig(false);

			SortConfig sort = new SortConfig(false);

			ETagConfig etag = new ETagConfig(false);

			URI authenticationSchemeURI = new URI("/authentication");

			URI authenticationDocumentationURI = new URI("/documentation/authentication");

			AuthenticationScheme tokenBasedAuthentication = new AuthenticationScheme("Bearer Token", "Bearer Token based Authentication", authenticationSchemeURI, authenticationDocumentationURI, "Bearer Token", true);

			List<AuthenticationScheme> authSchemes = new ArrayList<AuthenticationScheme>();

			authSchemes.add(tokenBasedAuthentication);

			serviceProviderConfigResource = new ServiceProviderConfigResource(serviceProviderHelpdocUri, patch, bulk, filter, changePassword, sort, etag, authSchemes);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return serviceProviderConfigResource;

	}

}
