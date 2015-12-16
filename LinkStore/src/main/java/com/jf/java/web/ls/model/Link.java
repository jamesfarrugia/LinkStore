package com.jf.java.web.ls.model;

/**
 * Basic Link model which contains the basic information about the link.
 * 
 * @author jfarrugia
 *
 */
public class Link
{
	/** URL of the link */
	private String url;
	/** Title assigned to the link */
	private String title;
	/** Short description on the link */
	private String description;
	/** Timestamp when it was added */
	private long added;

	/**
	 * Constructor for the Link model.
	 */
	public Link()
	{
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the added
	 */
	public long getAdded()
	{
		return added;
	}

	/**
	 * @param added
	 *            the added to set
	 */
	public void setAdded(long added)
	{
		this.added = added;
	}
}
