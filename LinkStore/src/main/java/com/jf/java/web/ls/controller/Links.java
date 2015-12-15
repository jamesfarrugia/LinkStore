package com.jf.java.web.ls.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("links")
public class Links
{
	@RequestMapping("/list")
	public @ResponseBody List<String> list() 
	{
		List<String> links = new ArrayList<String>();
		
		links.add("google.com");
		links.add("twiter.com");
		
		return links;
	}
}
