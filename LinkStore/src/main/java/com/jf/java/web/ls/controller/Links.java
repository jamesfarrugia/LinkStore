package com.jf.java.web.ls.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jf.java.web.ls.service.LinkService;

@Controller
public class Links
{
	@Autowired private LinkService service;
	
	@RequestMapping(value = "links", method = RequestMethod.GET)
	public @ResponseBody List<String> list() 
	{
		return service.getLinks();
	}
	
	@RequestMapping(value = "links", method = RequestMethod.POST)
	public @ResponseBody void add(
			@RequestParam("url") String url) 
	{
		service.addLink(url);
	}
}
