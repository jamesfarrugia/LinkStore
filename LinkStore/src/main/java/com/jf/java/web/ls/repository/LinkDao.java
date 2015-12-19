package com.jf.java.web.ls.repository;

import java.util.ArrayList;
import java.util.List;

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
		
		buffer.append("INSERT INTO LINKS VALUES(");
		buffer.append(id);
		buffer.append(",'");
		buffer.append(link.getUrl());
		buffer.append("',");
		buffer.append(link.getAdded());
		buffer.append(",'");
		buffer.append(link.getTitle());
		buffer.append("','");
		buffer.append(link.getDescription());
		buffer.append("');");
		
		context.execute(buffer.toString());
	}
	
	/**
	 * Selects all the links in the database
	 * @return a list of all the links in the database
	 */
	public List<Link> listLinks()
	{
		List<Link> links = new ArrayList<Link>();
		Result<Record> result = context.fetch("SELECT * FROM LINKS");
		
		for (Record record : result)
		{
			Link link = new Link();
			
			link.setId((Integer)record.getValue("id"));
			link.setUrl(record.getValue("url").toString());
			link.setAdded((long)record.getValue("added"));
			link.setTitle(record.getValue("title").toString());
			link.setDescription(record.getValue("description").toString());
			
			links.add(link);
		}
		
		return links;
	}
}
