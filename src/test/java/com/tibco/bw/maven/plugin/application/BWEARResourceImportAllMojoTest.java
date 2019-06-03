package com.tibco.bw.maven.plugin.application;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.Test;

import java.io.File;

public class BWEARResourceImportAllMojoTest extends AbstractMojoTestCase{

	@Test
	public void testExecuteNotProfile() {

	        File pom = getTestFile( "src/test/resources/unit/BWEARResourceImportAllMojo/pom.xml" );
	        //assertNotNull( pom );
	        assertTrue( pom.exists() );

		BWEARResourceImportAllMojo myMojo;
			try {
				myMojo = (BWEARResourceImportAllMojo) lookupMojo( "bwimportall", pom );

		        assertNotNull( myMojo );
		        myMojo.execute();
				File profile = getTestFile( "src/test/resources/unit/BWEARResourceImportAllMojo/MATEA-INF/perro.substvar" );

				assertFalse(profile.exists());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



	    }

	@Test
	public void testExecuteProfileExist() {
		File profile = getTestFile( "src/test/resources/unit/BWEARResourceImportAllProfileExistMojo/META-INF/DESA.substvar.backup" );
		profile.deleteOnExit();
		File profile2 = getTestFile( "src/test/resources/unit/BWEARResourceImportAllProfileExistMojo/META-INF/INT.substvar.backup" );
		profile2.deleteOnExit();
		File pom = getTestFile( "src/test/resources/unit/BWEARResourceImportAllProfileExistMojo/pom.xml" );
		//assertNotNull( pom );
		assertTrue( pom.exists() );

		BWEARResourceImportAllMojo myMojo;
		try {
			myMojo = (BWEARResourceImportAllMojo) lookupMojo( "bwimportall", pom );

			assertNotNull( myMojo );
			myMojo.execute();
			profile = getTestFile( "src/test/resources/unit/BWEARResourceImportAllProfileExistMojo/META-INF/DESA.substvar.backup" );

			assertTrue(profile.exists());
			profile2 = getTestFile( "src/test/resources/unit/BWEARResourceImportAllProfileExistMojo/META-INF/INT.substvar.backup" );

			assertTrue(profile2.exists());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}



}


