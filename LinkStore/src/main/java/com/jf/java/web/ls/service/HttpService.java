package com.jf.java.web.ls.service;

import java.util.concurrent.Callable;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jf.java.web.ls.model.HttpResponse;

/**
 * Simple service class to make HTTP requests to get website data.
 * 
 * @author jfarrugia
 *
 */
@Service
public class HttpService
{
	/** Class logger */
	private Logger log;
	
	/**
	 * Constructor for type HTTP.
	 *
	 */
	public HttpService()
	{
		log = LoggerFactory.getLogger(getClass());
	}
	
	/**
	 * Performs and HTTP GET to the specified URL.
	 * 
	 * @param url the URL to GET
	 * @return an HttpResponse with the resulting details
	 * @throws Exception in case the call fails
	 */
	public HttpResponse get(String url) throws Exception
	{
		final CloseableHttpClient httpclient;
		httpclient = HttpClients.createDefault();

		HttpRequestBase method = new HttpGet(url);

		log.debug("Request to " + method.getRequestLine());

		final CloseableHttpResponse response = httpclient.execute(method);
		
		HttpEntity entity = response.getEntity();

		HttpResponse result = new HttpResponse();
		StatusLine status = response.getStatusLine();
		result.setStatus(status.getStatusCode());
		result.setStatusMessage(status.getReasonPhrase());

		result.setContentStream(entity.getContent());
		result.setContentLength(entity.getContentLength());
		
		/* Declare a callback so that the associated response 
		 * is closed when the wrapper is closed. */
		result.setCloseCallback(new Callable<Void>()
		{
			@Override
			public Void call() throws Exception
			{
				log.debug("Closing HTTP response");
				response.close();
				httpclient.close();
				return null;
			}
		});
		
		
		return result;
	}
}
