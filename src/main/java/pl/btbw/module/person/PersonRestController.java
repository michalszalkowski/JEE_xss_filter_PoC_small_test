package pl.btbw.module.person;

import javax.ws.rs.Path;
import javax.ws.rs.*;

@Path("/person")
public class PersonRestController {

	@GET
	@Path("/get")
	public String nameGET(@QueryParam("name") String name) {
		return "name: " + name;
	}

	@POST
	@Path("/post")
	public String namePOST(@FormParam("name") String name) {
		return "name: " + name;
	}
}