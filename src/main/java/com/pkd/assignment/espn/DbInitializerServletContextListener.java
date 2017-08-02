package com.pkd.assignment.espn;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.pkd.assignment.espn.dao.UrlShorterEmbededDbDao;
import com.pkd.assignment.espn.utils.UniqueKeyGen;

/**
 * @author Provash ServletContextListener subclass to initialize db connection
 *         on web application start.
 */
public class DbInitializerServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("*****Initializing Database :: urlShorterDB*****");
		UrlShorterEmbededDbDao.createDatabaseConnection();
		/**
		 * TODO: fetch max key value from db and set it to unique key generator
		 */
		UniqueKeyGen.getKeyGeneratorInst(null);
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("*****Shutdown Database :: urlShorterDB*****");
		UrlShorterEmbededDbDao.closeDatabaseConnection();
	}

}
