package com.jf.java.web.ls.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jf.java.web.ls.model.Link;
import com.jf.java.web.ls.repository.LinkDao;

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
	@Autowired private LinkDao dao;
	
	/**
	 * Adds a new link via the DAO.
	 * 
	 * @param link the link to add
	 */
	public void addLink(Link link)
	{
		this.dao.add(link);
	}
	
	/**
	 * Returns a list of links from the DAO
	 * 
	 * @return an list of links.
	 */
	public List<Link> getLinks()
	{
		return this.dao.listLinks();
	}
}
