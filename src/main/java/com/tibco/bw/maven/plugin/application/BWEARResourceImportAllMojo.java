package com.tibco.bw.maven.plugin.application;


import com.tibco.bw.maven.plugin.utils.FileUtilsProject;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileFilter;
import java.util.Properties;

/**
 * Display bwimport information on bw6-maven-plugin-resource.      
 * Call mvn com.tibco.plugins:bw6-maven-plugin-resource:bwimport -Dprofile=ProfileNameFile -Dpropertyfile=propertyFile
 * @phase N/A
 */
@Mojo(name = "bwimportall" ,defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class BWEARResourceImportAllMojo extends AbstractMojo {

    @Component
    private MavenSession session;

    @Component
    private MavenProject project;

	@Parameter(property="project.build.directory")
    private File outputDirectory;

	@Parameter(property="project.basedir")
	private File projectBasedir;


	@Parameter(property="profile",required=true)
	private String profile;

	@Parameter(property="propertyfile",required=false)
	private String propertyfile;



    /**
     * Execute Method.
     *
     */
    public void execute() throws MojoExecutionException {
		try {


			getLog().info("bwresourceImportALL Mojo started execution");
			//FileUtilsProject.getApplicationMSrcResources(projectBasedir).getAbsolutePath();
			File directory = null;
			try {
				if (FileUtilsProject.getApplicationMetaInf(projectBasedir) == null) {
					getLog().info("com.tibco.bw.maven.plugin.utils.FileUtilsProject.getApplicationMetaInf(projectBasedir): ");
					getLog().info("projectBasedir: " + projectBasedir.toString());
					getLog().info("bwresourceImport: Skip Import properties");
				} else if (FileUtilsProject.getApplicationMSrcResources(projectBasedir) == null) {
					getLog().info("FileUtilsProject.getApplicationMSrcResources(projectBasedir): ");
					getLog().info("projectBasedir: " + projectBasedir.toString());
					getLog().info("bwresourceImport: Skip Import properties.RESOURCE Path(src/resources) not exists.");
				} else {
					directory = new File(FileUtilsProject.getApplicationMSrcResources(projectBasedir).getAbsolutePath());
					//getLog().info(directory.listFiles().toString());
					File[] filesList = directory.listFiles();
					for (File f : filesList) {
						if (f.isDirectory())
							getLog().info(f.getName());
						if (f.isFile()) {

							FileUtilsProject generateFile = new FileUtilsProject();
							String FileWithoutExtension = generateFile.getFileNameWithoutExtension(f);
							getLog().info("FileWithoutExtension :" + FileWithoutExtension);
							generateFile.setImportPropertyFieToDefaultVars(this, FileWithoutExtension + ".properties", FileWithoutExtension + ".substvar", projectBasedir);

						}
					}
				}

				getLog().info("bwresource ImportALL Mojo finished execution");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e1) {
			throw new MojoExecutionException("Failed to Import BW ALL property file ", e1);
		}
	}







    /**
     * Finds the folder name META-INF inside the Application Project.
     * 
     * @return the META-INF folder
     * 
     * @throws Exception
     */
	private File getApplicationMetaInf() throws Exception {
		File[] fileList = projectBasedir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				
				if(pathname.getName().indexOf("META-INF") != -1) {
        			return true;
				}
				return false;
			}
		});
       return fileList[0];
	}

	private File getResourceProperties() throws Exception {
		projectBasedir.getParent();

		File[] fileList = projectBasedir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if(pathname.getName().indexOf("META-INF") != -1) {
					return true;
				}
				return false;
			}
		});
		return fileList[0];
	}


    
//    /**
//     * Finds the JAR file for the Module.
//     * 
//     * @param target the Module Output directory which is the target directory.
//     * 
//     * @return the Module JAR.
//     * 
//     * @throws Exception
//     */
//	private File getModuleJar(File target) throws Exception {
//      File[] files = BWFileUtils.getFilesForType(target, ".jar");
//      files = BWFileUtils.sortFilesByDateDesc(files);
//      if(files.length == 0) {
//       	throw new Exception("Module is not built yet. Please check your Application PO for the Module entry.");
//      }
//      return files[0];
//	}



	/**
	 * Loads the TibcoXMLfile in a Document object (DOM)
	 * 
	 * @param file the TIBCO.xml file
	 * 
	 * @return the root Document object for the TIBCO.xml file
	 * 
	 * @throws Exception
	 */
	private Document loadTibcoXML(File file) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setNamespaceAware(true);
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(file);
		getLog().debug("Loaded Tibco.xml file");
		return doc;
	}
	
	private boolean CheckExistsProperties() throws Exception {

		try {
			return new File(FileUtilsProject.getApplicationMSrcResources(projectBasedir).getAbsolutePath() + "/"
					+ propertyfile).exists();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		
	}
	



}
