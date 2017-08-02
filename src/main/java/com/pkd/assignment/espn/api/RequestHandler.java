package com.pkd.assignment.espn.api;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.pkd.assignment.espn.dao.UrlShorterEmbededDbDao;
import com.pkd.assignment.espn.service.UrlShorterService;
import com.pkd.assignment.espn.service.UrlShorterServiceImpl;
import com.pkd.assignment.espn.utils.UrlShorterConfiguration;

/**
 * @author Provash This class will be use as a controller. This has two
 *         functionality 1) create shorter url and 2) redirect to exact url
 */

@Path("/")
public class RequestHandler {
	UrlShorterService urlShorter = new UrlShorterServiceImpl(new UrlShorterEmbededDbDao());

	@GET
	@Path("/{urlKey}")
	public Response redirectToExactPath(@PathParam("urlKey") String urlKey) {
		System.out.println("*****Received short Url Key :: " + urlKey);
		String largeURL = urlShorter.getLongUrl(urlKey);

		try {
			return Response.temporaryRedirect(new URI(largeURL)).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Not able to redirect actual location. Try later")
				.build();
	}

	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createShorterURL(@Context HttpServletRequest request, String longUrl) {
		System.out.println("*****Received Long Url :: " + longUrl);
		if (UrlShorterConfiguration.getShorterURLRootPath() == null) {
			UrlShorterConfiguration.setShorterURLRootPath(request.getRequestURL().toString());
		}
		String s = urlShorter.createShortUrl(longUrl);
		if (s != null) {
			return Response.ok(s, MediaType.TEXT_PLAIN).build();
		} else {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Not able to create shorter error. Try later")
					.build();
		}
	}

}
