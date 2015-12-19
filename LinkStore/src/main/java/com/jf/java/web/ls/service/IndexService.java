package com.jf.java.web.ls.service;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexableField;
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
	 */
	public void index(Link link)
	{
		try
		{
			String path = env.getProperty("index.path");
			Directory dir = FSDirectory.open(Paths.get(path));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
	
			IndexWriter writer = new IndexWriter(dir, iwc);
			
			Document linkDocument = new Document();
			
			String idStr = Integer.toString(link.getId());
			String meta = link.getTitle() + " " + link.getDescription() + " " + link.getUrl();
			
			IndexableField id = new TextField("id", idStr, Store.YES);
			IndexableField field = new TextField("meta", meta, Store.YES);
			
			linkDocument.add(id);
			linkDocument.add(field);
			writer.addDocument(linkDocument);
			
			writer.commit();
			writer.close();
		}
		catch (Exception err)
		{
			err.printStackTrace();
		}
	}
}
