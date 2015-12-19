package com.jf.java.web.ls.service;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
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
	/** DAO to store raw link information in DB */
	@Autowired private LinkDao dao;
	/** Indexer service to build full text index of the links' descriptions */
	@Autowired private IndexService index;
	/** Searcher service to look through the link index */
	@Autowired private SearchService searcher;
	
	/**
	 * Adds a new link via the DAO and submits it to the index.
	 * 
	 * @param link the link to add
	 */
	public void addLink(Link link)
	{
		link.setAdded(System.currentTimeMillis());
		
		if (link.getTitle() == null)
			link.setTitle(link.getUrl());
		
		if (link.getDescription() == null)
			link.setDescription("");
		
		this.dao.add(link);
		this.index.index(link);
	}
	
	/**
	 * Returns a list of links from the DAO.  By default these are sorted by
	 * date added, most recent first
	 * 
	 * @param maximum optional maximum number of links to return
	 * @return an list of links.
	 */
	public List<Link> getLinks(Integer maximum)
	{
		return this.dao.listLinks(maximum);
	}
	
	/**
	 * Searches through the links' meta data, gets a list of IDs and returns
	 * a list of link details.
	 * 
	 * @param query the query to search
	 * @return a list of links who have the term, or close to it, in the meta data
	 * @throws ParseException in case the query cannot be parsed
	 * @throws IOException in case the index cannot be opened
	 */
	public List<Link> searchLinks(String query) 
	throws ParseException, IOException
	{
		List<Integer> ids = searcher.search(query);
		return dao.getLinks(ids);
	}
}
