package com.pkd.assignment.espn.utils;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import com.pkd.assignment.espn.api.RequestHandler;
import com.pkd.assignment.espn.dao.UrlShorterDao;
import com.pkd.assignment.espn.dao.UrlShorterEmbededDbDao;
import com.pkd.assignment.espn.model.UrlInfo;


public class ServerManager {
	
    public static void main(String[] args) throws Exception {
    	UrlShorterEmbededDbDao.createDatabaseConnection();
    	UrlShorterEmbededDbDao.eqecuteAndPrintQueryRes(UrlShorterEmbededDbDao.FETCH_ALL_TABLES_INFO);
    	UrlShorterDao dao = new UrlShorterEmbededDbDao();
    	UrlInfo i = new UrlInfo();
    	i.setShortUrl("abcdef");
    	i.setLongUrl("www.google.com");
    	dao.insertShorterUrlInfo(i);
    	UrlShorterEmbededDbDao.eqecuteAndPrintQueryRes(UrlShorterEmbededDbDao.FETCH_ALL_URLINFO_DATA);
    	
    	/*String s = dao.fetchLongUrlInfo("abcdef");
    	System.out.println("Result from Fetch :: "+s);
    	UrlShorterEmbededDbDao.closeDatabaseConnection();*/
    	/*UniqueKeyGen gen = UniqueKeyGen.getKeyGeneratorInst(null);
    	int count = 0;
    	while(count < 10000000){
    		String currentKey =  gen.getNextKey();
    		System.out.println("Generate Key :: "+currentKey);
    		gen.setCurrentKey(currentKey);
    		count++;
    	}
    	*/
    	
    	
	/*String webPort = "8082";

	 Server server = new Server(Integer.valueOf(webPort));
	ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

	context.setContextPath("/");
	server.setHandler(context);
	ServletHolder restHolder = context.addServlet(ServletContainer.class, "/*");
	restHolder.setInitOrder(0);

	restHolder
		.setInitParameter("jersey.config.server.provider.classnames", RequestHandler.class.getCanonicalName());

	server.start();
	server.join();*/
    }

}
