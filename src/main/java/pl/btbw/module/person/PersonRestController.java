package pl.btbw.module.person;

import javax.ws.rs.Path;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/person")
public class PersonRestController {

	@GET
	@Path("/get")
	public String actionGET(@QueryParam("name") String name) {
		return "name: " + name;
	}

	@POST
	@Path("/post")
	public String actionPOST(@FormParam("name") String name) {
		return "name: " + name;
	}

	@POST
	@Path("/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public String actionJSON(Person person) {
		return "person json: " + person;
	}

	@POST
	@Path("/xml")
	@Consumes(MediaType.APPLICATION_XML)
	public String actionXML(Person person) {
		return "person xml: " + person;
	}
}