package com.jf.java.web.ls.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jf.java.web.ls.model.Link;

/**
 * Class to read and write links to a persistent storage, namely, the SQLite 
 * database.
 * 
 * @author jfarrugia
 *
 */
@Repository
public class LinkDao
{
	/** jOOQ DSL */
	@Autowired private DSLContext context;
	
	/**
	 * Adds a new link to the database
	 * @param link the link to add
	 */
	public synchronized void add(Link link)
	{
		StringBuffer buffer = new StringBuffer();
		
		int id = (Integer)context.selectCount().from("LINKS").
					fetchOne().getValue(0);
		
		link.setId(id);
		
		buffer.append("INSERT INTO LINKS VALUES(");
		buffer.append(id);
		buffer.append(",'");
		buffer.append(link.getUrl().replaceAll("'", "''"));
		buffer.append("',");
		buffer.append(link.getAdded());
		buffer.append(",'");
		buffer.append(link.getTitle().replaceAll("'", "''"));
		buffer.append("','");
		buffer.append(link.getDescription().replaceAll("'", "''"));
		buffer.append("');");
		
		context.execute(buffer.toString());
	}
	
	/**
	 * Selects all the links in the database
	 * @return a list of all the links in the database
	 */
	public List<Link> listLinks()
	{
		Result<Record> result = context.fetch("SELECT * FROM LINKS");
		
		return recordsToLinks(result);
	}
	
	/**
	 * Returns a single link having the passed ID.
	 * 
	 * @param id the ID of the link to get
	 * @return the link with the passed ID
	 */
	public Link getLink(int id)
	{
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM LINKS WHERE id=");
		query.append(id);
		query.append(";");
		
		return recordToLink(context.fetchOne(query.toString()));
	}
	
	/**
	 * Gets all the links who have the ID in the passed ID list.
	 * 
	 * @param ids the list of IDs to select
	 * @return the list of link objects matched in the query
	 */
	public List<Link> getLinks(List<Integer> ids)
	{
		StringJoiner joiner = new StringJoiner(",");
		for (Integer id : ids)
			joiner.add(Integer.toString(id));
		
		StringBuffer query = new StringBuffer();
		query.append("SELECT * FROM LINKS WHERE id IN(");
		query.append(joiner.toString());
		query.append(");");
		
		Result<Record> result = context.fetch(query.toString());
		
		return recordsToLinks(result);
	}
	
	/**
	 * Converts a result of records into a list of link models.
	 * 
	 * @param records the result of a query
	 * @return a list of link models
	 */
	private List<Link> recordsToLinks(Result<Record> records)
	{
		List<Link> links = new ArrayList<Link>();
		
		for (Record record : records)
		{
			Link link = recordToLink(record);
			links.add(link);
		}
		
		return links;
	}
	
	/**
	 * Creates a link model from the passed jOOQ record object.
	 * 
	 * @param record the record to convert
	 * @return the model of the link
	 */
	private Link recordToLink(Record record)
	{
		Link link = new Link();
		
		link.setId((Integer)record.getValue("id"));
		link.setUrl(record.getValue("url").toString());
		link.setAdded((long)record.getValue("added"));
		link.setTitle(record.getValue("title").toString());
		link.setDescription(record.getValue("description").toString());
		
		return link;
	}
}
