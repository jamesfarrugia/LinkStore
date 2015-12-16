package com.jf.java.web.ls.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.java.web.ls.model.Link;
import com.jf.java.web.ls.service.LinkService;

/**
 * REST controller for the links stored in the system.  Returns a list of links
 * for the generic /links on GET and creates a new one on the POST.
 * 
 * @author jfarrugia
 *
 */
@Controller
public class Links
{
	/** Service providing link management */
	@Autowired private LinkService service;
	
	/**
	 * Returns a list of links to the view resolver.
	 * @return a list of {@link Link} objects.
	 */
	@RequestMapping(value = "links", method = RequestMethod.GET)
	public @ResponseBody List<Link> list() 
	{
		return service.getLinks();
	}
	
	/**
	 * Creates a new link and passes it to the service for it to persist.
	 * @param url URL of the link
	 * @param title title if assigned from the client
	 * @param description description if assigned by the client
	 */
	@RequestMapping(value = "links", method = RequestMethod.POST)
	public @ResponseBody void add(
			@RequestParam("url") String url,
			@RequestParam("title") String title,
			@RequestParam("description") String description) 
	{
		Link link = new Link();
		
		link.setUrl(url);
		link.setTitle(title);
		link.setDescription(description);
		link.setAdded(System.currentTimeMillis());
		
		service.addLink(link);
	}
}
