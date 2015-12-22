package com.jf.java.web.ls.repository;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectSeekStep1;
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
	
	/* Table definition is done here rather than via jOOQ generation since this
	 * is magnitudes simpler than anything enterprise.*/
	
	/** Table name for containing the links */
	private final String LINKS	=	"LINKS";
	
	/** Class for field names. */
	private final class Fields
	{
		final static String ID		=	"id";
		final static String URL		=	"url";
		final static String ADDED	=	"added";
		final static String TITLE	=	"title";
		final static String DESC	=	"description";
	}
	
	/**
	 * Adds a new link to the database
	 * @param link the link to add
	 */
	public synchronized void add(Link link)
	{
		int id = (Integer)context.selectCount().from(LINKS).
					fetchOne().getValue(0);
		
		link.setId(id);
		
		context.insertInto(table(LINKS),
				field(Fields.ID), field(Fields.URL), field(Fields.ADDED), 
				field(Fields.TITLE), field(Fields.DESC)).
				values(
				id, link.getUrl(), link.getAdded(), link.getTitle(), 
				link.getDescription()).execute();
	}
	
	/**
	 * Selects all the links in the database, ordered by date, most recent first
	 * and limited by the maximum parameter, if specified.
	 * 
	 * @param maximum optional maximum number of rows
	 * @return a list of all the links in the database
	 */
	public List<Link> listLinks(Integer maximum)
	{
		SelectSeekStep1<Record, Object> query = 
				context.
				select().
				from(table(LINKS)).
				orderBy(field(Fields.ADDED).desc());
		
		if (maximum != null)
			query.limit(maximum);
		
		Result<Record> result = query.fetch();
		
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
		SelectConditionStep<Record> query = context.
			selectFrom(table(LINKS)).
			where(field(Fields.ID).eq(id));
		
		return recordToLink(query.fetchOne());
	}
	
	/**
	 * Gets all the links who have the ID in the passed ID list.
	 * 
	 * @param ids the list of IDs to select
	 * @return the list of link objects matched in the query
	 */
	public List<Link> getLinks(List<Integer> ids)
	{
		SelectConditionStep<Record> query = context.
				selectFrom(table(LINKS)).
				where(field(Fields.ID).in(ids));
		
		Result<Record> result = query.fetch();
		
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
		
		link.setId((Integer)record.getValue(Fields.ID));
		link.setUrl(record.getValue(Fields.URL).toString());
		link.setAdded((long)record.getValue(Fields.ADDED));
		link.setTitle(record.getValue(Fields.TITLE).toString());
		link.setDescription(record.getValue(Fields.DESC).toString());
		
		return link;
	}
}
