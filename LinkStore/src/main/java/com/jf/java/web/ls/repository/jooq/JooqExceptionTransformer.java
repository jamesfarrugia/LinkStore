package com.jf.java.web.ls.repository.jooq;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultExecuteListener;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

/**
 * Transforms jOOQ Exceptions into better Spring ones.
 * 
 * @author jfarrugia
 *
 */
public class JooqExceptionTransformer extends DefaultExecuteListener
{
	/** UID */
	private static final long serialVersionUID = 1L;

	@Override
	public void exception(ExecuteContext ctx)
	{
		SQLDialect dialect = ctx.configuration().dialect();
		SQLExceptionTranslator translator = null;
		
		if (dialect != null) 
			translator = new SQLErrorCodeSQLExceptionTranslator(dialect.name());
		else
			new SQLStateSQLExceptionTranslator();

		ctx.exception(translator.translate("jOOQ", ctx.sql(), ctx.sqlException()));
	}
}