package com.idm.scim.restjersey;

import javax.ws.rs.Path;

import com.unboundid.scim2.common.types.SchemaResource;
import com.unboundid.scim2.server.annotations.ResourceType;
import com.unboundid.scim2.server.resources.SchemasEndpoint;

@ResourceType(description = "SCIM 2.0 Schema", name = "Schema", schema = SchemaResource.class, discoverable = false)
@Path("Schemas")
public class SchemaEndPoint extends SchemasEndpoint {

}
