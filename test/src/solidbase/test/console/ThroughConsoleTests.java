/*--
 * Copyright 2006 Ren� M. de Bloois
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package solidbase.test.console;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import solidbase.Main;
import solidbase.core.Patcher;
import solidbase.test.TestUtil;


public class ThroughConsoleTests
{
	@BeforeMethod
	protected void init()
	{
		Patcher.end();
	}

	@Test
	public void testConsole() throws Exception
	{
		MockConsole console = new MockConsole();
		console.addAnswer( "prod" );
		console.addAnswer( "app1" );
		console.addAnswer( "" );
		console.addAnswer( "1.0.2" );

		Main.console = console;

		Main.pass2( new String[] { "-verbose" } );

		String output = TestUtil.generalizeOutput( console.getOutput() );

		//		System.out.println( "[[[" + output + "]]]" );

		Assert.assertEquals( output,
				"Reading property file file:/.../solidbase-default.properties\n" +
				"Reading property file X:\\...\\solidbase.properties\n" +
				"SolidBase v1.5.x (C) 2006-200x Ren� M. de Bloois\n" +
				"\n" +
				"Available database:\n" +
				"    prod (DHL Production)\n" +
				"    test (test)\n" +
				"Select a database from the above: \n" +
				"Available applications in database 'prod':\n" +
				"    app1 (app1 description)\n" +
				"    app2 (app2)\n" +
				"Select an application from the above: \n" +
				"DEBUG: driverName=org.hsqldb.jdbcDriver, url=jdbc:hsqldb:mem:testtc1, user=sa\n" +
				"Connecting to database 'prod', application 'app1'...\n" +
				"Input password for user 'sa': The database has no version yet.\n" +
				"Opening file 'file:/.../testpatch1.sql'\n" +
				"    Encoding is 'ISO-8859-1'\n" +
				"Possible targets are: 1.0.1, 1.0.2\n" +
				"Input target version: Patching to \"1.0.1\"\n" +
				"Creating table DBVERSION.\n" +
				"Creating table DBVERSIONLOG.\n" +
				"DEBUG: version=null, target=1.0.1, statements=2\n" +
				"Patching \"1.0.1\" to \"1.0.2\".\n" +
				"Inserting admin user.\n" +
				"DEBUG: version=1.0.1, target=1.0.2, statements=2\n" +
				"The database has been patched.\n" +
				"\n" +
				"Current database version is \"1.0.2\".\n"
		);
	}

	@Test
	public void testConsole2() throws Exception
	{
		MockConsole console = new MockConsole();
		console.addAnswer( "prod" );
		console.addAnswer( "" );
		console.addAnswer( "1.0.2" );

		Main.console = console;

		Main.pass2( "-verbose", "-config", "solidbase2.properties" );

		String output = TestUtil.generalizeOutput( console.getOutput() );

		//		System.out.println( "[[[" + output + "]]]" );

		Assert.assertEquals( output,
				"Reading property file file:/.../solidbase-default.properties\n" +
				"Reading property file X:\\...\\solidbase2.properties\n" +
				"SolidBase v1.5.x (C) 2006-200x Ren� M. de Bloois\n" +
				"\n" +
				"Available database:\n" +
				"    prod (DHL Production)\n" +
				"    test (test)\n" +
				"Select a database from the above: \n" +
				"DEBUG: driverName=org.hsqldb.jdbcDriver, url=jdbc:hsqldb:mem:testtc2, user=sa\n" +
				"Connecting to database 'prod', application 'default'...\n" +
				"Input password for user 'sa': The database has no version yet.\n" +
				"Opening file 'file:/.../testpatch1.sql'\n" +
				"    Encoding is 'ISO-8859-1'\n" +
				"Possible targets are: 1.0.1, 1.0.2\n" +
				"Input target version: Patching to \"1.0.1\"\n" +
				"Creating table DBVERSION.\n" +
				"Creating table DBVERSIONLOG.\n" +
				"DEBUG: version=null, target=1.0.1, statements=2\n" +
				"Patching \"1.0.1\" to \"1.0.2\".\n" +
				"Inserting admin user.\n" +
				"DEBUG: version=1.0.1, target=1.0.2, statements=2\n" +
				"The database has been patched.\n" +
				"\n" +
				"Current database version is \"1.0.2\".\n"
		);
	}
}
