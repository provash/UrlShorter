package com.pkd.assignment.espn.utils;

/**
 * @author Provash
 * This class will be use to configure application settings
 * TODO: Need to externalize db property and default key value in properties file.
 */
public class UrlShorterConfiguration {
	private static String shorterURLRootPath;
	
	public static void setShorterURLRootPath(String rootUrl){
		if(shorterURLRootPath == null){
			shorterURLRootPath = rootUrl;
			System.out.println("UrlShorterConfiguration.shorterURLRootPath ::"+UrlShorterConfiguration.shorterURLRootPath );
		}
	}

	public static String getShorterURLRootPath() {
		return shorterURLRootPath;
	}
	
}
