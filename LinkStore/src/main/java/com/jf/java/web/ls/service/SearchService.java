package com.jf.java.web.ls.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

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
	@Autowired private Environment env;
	
	/** Index reader */
	private IndexReader reader;
	
	private boolean initialised;
	
	/**
	 * Initialises the index reader if it has not been initialised yet and if
	 * an index exists in the directory.
	 * 
	 * @throws IOException
	 */
	private void init() throws IOException
	{
		if (initialised)
			return;
		
		String path = env.getProperty("index.path");
		Directory dir = FSDirectory.open(Paths.get(path));
		if (!DirectoryReader.indexExists(dir))
			return;
		
		reader = DirectoryReader.open(dir);
		initialised = true;
	}
	
	/**
	 * Searches the index to return a result of matching links.
	 * 
	 * @param userQuery the query to run
	 * @return a list of link IDs matching the search query
	 * @throws ParseException 
	 * @throws IOException 
	 */
	public List<Integer> search(String userQuery) throws ParseException, IOException
	{
		List<Integer> result = new ArrayList<Integer>();
		
		init();
		if (!initialised)
			return result;
		
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		QueryParser queryParser = new QueryParser("meta", new StandardAnalyzer());
		Query query = queryParser.parse(userQuery);
		TopDocs topDocs = indexSearcher.search(query,10);
		
		for (ScoreDoc doc : topDocs.scoreDocs)
		{
			Document linkDoc = indexSearcher.doc(doc.doc);
			String linkId = linkDoc.get("id");
			result.add(Integer.parseInt(linkId));
		}
		
		return result;
	}
}
