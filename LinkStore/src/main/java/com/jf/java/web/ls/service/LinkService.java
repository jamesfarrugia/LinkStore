package com.jf.java.web.ls.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class LinkService
{
	private List<String> links;
	
	@PostConstruct
	public void init()
	{
		links = new ArrayList<String>();
	}
	
	public void addLink(String link)
	{
		this.links.add(link);
	}
	
	public List<String> getLinks()
	{
		return Collections.unmodifiableList(links);
	}
}
