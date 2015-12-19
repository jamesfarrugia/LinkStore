package com.jf.java.web.ls.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jf.java.web.ls.model.Link;

/**
 * Service for searching through all the links in the system database.  The
 * search is done using a full text indexing all of all descriptions and meta-
 * data.
 * 
 * @author james
 *
 */
@Service
public class SearchService
{
	/**
	 * Searches the index to return a result of matching links.
	 * 
	 * @param query the query to run
	 * @return a list of links matching the search query
	 */
	public List<Link> search(String query)
	{
		List<Link> result = new ArrayList<Link>();
		
		return result;
	}
}
