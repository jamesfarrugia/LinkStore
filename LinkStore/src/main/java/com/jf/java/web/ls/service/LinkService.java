package com.jf.java.web.ls.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.jf.java.web.ls.model.Link;

/**
 * Service to handle the application level management of the links.  Receives
 * new links from the controllers, adds via the DAO, etc.
 * 
 * @author jfarrugia
 *
 */
@Service
public class LinkService
{
	/** Temporary list of links currently in memory */
	private List<Link> links;
	
	/**
	 * Initialises the list once the service has been constructed.  As yet
	 * no autowiring is done to this class, but may need to do some ops once 
	 * they are injected in the future.
	 */
	@PostConstruct
	public void init()
	{
		links = new ArrayList<Link>();
		Link l = new Link();
		l.setUrl("google.com");
		l.setDescription("The engine");
		l.setTitle("Google");
		l.setAdded(System.currentTimeMillis());
		links.add(l);
	}
	
	/**
	 * Adds a new link to the in-memory list.
	 * 
	 * TODO Add to persitance via DAO
	 * 
	 * @param link the link to add
	 */
	public void addLink(Link link)
	{
		this.links.add(link);
	}
	
	/**
	 * Returns a list of links in an unmodifiable list.
	 * 
	 * @return an immutable list of links.
	 */
	public List<Link> getLinks()
	{
		return Collections.unmodifiableList(links);
	}
}
