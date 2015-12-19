package com.jf.java.web.ls.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Class to read and write links t oa persistent storage, namely, the SQLite 
 * database.
 * 
 * @author jfarrugia
 *
 */
@Repository
public class LinkDao
{
	@Autowired private DSLContext context;
	
	/**
	 * TODO remove
	 */
	public void test()
	{
		context.query("SELECT * FROM LINKS").execute();
	}
}
