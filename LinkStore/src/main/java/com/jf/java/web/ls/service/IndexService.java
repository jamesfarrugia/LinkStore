package com.jf.java.web.ls.service;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.jf.java.web.ls.model.Link;

/**
 * Indexer powering the search service.
 * 
 * @author james
 *
 */
@Service
public class IndexService
{
	@Autowired private Environment env;
	
	/**
	 * Indexes a new link.
	 * 
	 * @param link the link to index
	 * @throws IOException
	 */
	public void index(Link link) throws IOException
	{
		String path = env.getProperty("index.path");
		Directory dir = FSDirectory.open(Paths.get(path));
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

		IndexWriter writer = new IndexWriter(dir, iwc);
		
		Document linkDocument = new Document();
		
		writer.addDocument(linkDocument);
		
		writer.commit();
		writer.close();
	}
}
