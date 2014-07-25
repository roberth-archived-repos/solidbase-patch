package solidbase.core;

import java.sql.SQLException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.StringContains;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeprecatedVersionIntegrationTest
{
	@Test
	public void testDeprecated1() throws SQLException
	{
		TestUtil.dropHSQLDBSchema( "jdbc:hsqldb:mem:testdb", "sa", null );
		PatchProcessor patcher = Setup.setupPatchProcessor( "testpatch1.sql" );

		patcher.patch( "1.0.2" );
		TestUtil.verifyVersion( patcher, "1.0.2", null, 2, null );

		patcher.end();
	}

	@Test(dependsOnMethods="testDeprecated1")
	public void testDeprecated2()
	{
		PatchProcessor patcher = Setup.setupPatchProcessor( "testpatch-deprecated-version-1.sql" );

		try
		{
			patcher.patch( "1.0.1" );
			Assert.fail( "Expected a FatalException" );
		}
		catch( FatalException e )
		{
			MatcherAssert.assertThat( e.getMessage(), CoreMatchers.anyOf(
					new StringContains( "The current database version 1.0.2 is not available in the upgrade file."),
					new StringContains( "The current database version 1.0.3 is not available in the upgrade file.")
					));
		}

		patcher.end();
	}
}
