package com.jf.java.web.ls.model;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Callable;


/**
 * General model for containing information responded by an HTTP server.
 * 
 * @author jfarrugia
 * @date 2 Mar 2015
 *
 */
public class HttpResponse
{
	/** HTTP Response status */
	private int status;
	/** HTTP Response status message */
	private String statusMessage;
	/** HTTP Response headers */
	private Map<String, String> headers;
	/** InputStream for the content of the response */
	private InputStream contentStream;
	/** Number of bytes in the content */
	private long contentLength;
	/** Callback to invoke one the response is processed*/
	private Callable<Void> closeCallback;
	
	/**
	 * Constructor for type HttpResponse.
	 *
	 */
	public HttpResponse()
	{
	}

	/**
	 * Returns the status.
	 *
	 * @return the status
	 */
	public final int getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status the status to set
	 */
	public final void setStatus(int status)
	{
		this.status = status;
	}

	/**
	 * Returns the contentStream.
	 *
	 * @return the contentStream
	 */
	public final InputStream getContentStream()
	{
		return contentStream;
	}

	/**
	 * Sets the contentStream.
	 * 
	 * @param contentStream the contentStream to set
	 */
	public final void setContentStream(InputStream contentStream)
	{
		this.contentStream = contentStream;
	}

	/**
	 * Returns the statusMessage.
	 *
	 * @return the statusMessage
	 */
	public final String getStatusMessage()
	{
		return statusMessage;
	}

	/**
	 * Sets the statusMessage.
	 * 
	 * @param statusMessage the statusMessage to set
	 */
	public final void setStatusMessage(String statusMessage)
	{
		this.statusMessage = statusMessage;
	}

	/**
	 * Returns the headers.
	 *
	 * @return the headers
	 */
	public final Map<String, String> getHeaders()
	{
		return headers;
	}

	/**
	 * Sets the headers.
	 * 
	 * @param headers the headers to set
	 */
	public final void setHeaders(Map<String, String> headers)
	{
		this.headers = headers;
	}

	/**
	 * Returns the contentLength.
	 *
	 * @return the contentLength
	 */
	public final long getContentLength()
	{
		return contentLength;
	}

	/**
	 * Sets the contentLength.
	 * 
	 * @param contentLength the contentLength to set
	 */
	public final void setContentLength(long contentLength)
	{
		this.contentLength = contentLength;
	}

	/**
	 * Sets the closeCallback.
	 * 
	 * @param closeCallback the closeCallback to set
	 */
	public final void setCloseCallback(Callable<Void> closeCallback)
	{
		this.closeCallback = closeCallback;
	}

	/**
	 * Close any associated resources with the response
	 * @throws Exception in case the closing fails
	 */
	public void close() throws Exception
	{
		closeCallback.call();
	}
}