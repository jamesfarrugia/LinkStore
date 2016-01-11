package com.jf.java.web.ls.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.queryparser.classic.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jf.java.web.ls.model.HttpResponse;
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
	/** Class logger */
	private Logger log;
	
	/** DAO to store raw link information in DB */
	@Autowired private LinkDao dao;
	/** Indexer service to build full text index of the links' descriptions */
	@Autowired private IndexService index;
	/** Searcher service to look through the link index */
	@Autowired private SearchService searcher;
	/** HTTP service for getting URL data */
	@Autowired private HttpService http;
	
	/**
	 * Constructor for type HTTP.
	 *
	 */
	public LinkService()
	{
		log = LoggerFactory.getLogger(getClass());
	}
	
	/**
	 * Adds a new link via the DAO and submits it to the index.
	 * 
	 * @param link the link to add
	 */
	public void addLink(Link link)
	{
		link.setAdded(System.currentTimeMillis());
		
		try
		{
			doAutoPopulate(link);
		}
		catch (Exception err)
		{
			log.error("Failed to process link", err);
		}
		
		this.dao.add(link);
		this.index.index(link);
	}
	
	private void doAutoPopulate(Link link) throws Exception
	{
		HttpResponse pageResponse = http.get(link.getUrl());
		
		if (pageResponse.getStatus() != 200)
			throw new Exception("Failed to get page content");
		
		StringWriter writer = new StringWriter();
		IOUtils.copy(pageResponse.getContentStream(), writer, "UTF8");
		pageResponse.close();
		
		Document html = Jsoup.parse(writer.toString());
		Elements titleElements = html.getElementsByTag("title");
		
		if (link.getTitle() == null)
		{
			if (titleElements.size() > 0)
				link.setTitle(titleElements.get(0).ownText());
			else
				link.setTitle(link.getUrl());
		}
		
		if (link.getDescription() == null)
			link.setDescription("");
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
