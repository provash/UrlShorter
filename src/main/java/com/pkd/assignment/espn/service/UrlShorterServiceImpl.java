package com.pkd.assignment.espn.service;

import org.glassfish.jersey.internal.util.Base64;

import com.pkd.assignment.espn.dao.UrlShorterDao;
import com.pkd.assignment.espn.dao.UrlShorterEmbededDbDao;
import com.pkd.assignment.espn.model.UrlInfo;
import com.pkd.assignment.espn.utils.UniqueKeyGen;
import com.pkd.assignment.espn.utils.UrlShorterConfiguration;

/**
 * @author Provash Service class to implement create shorter url and other util
 *         function
 */
public class UrlShorterServiceImpl implements UrlShorterService {
	private final UrlShorterDao dao;
	private final UniqueKeyGen keyGenerator = UniqueKeyGen.getKeyGeneratorInst(null);

	public UrlShorterServiceImpl(UrlShorterEmbededDbDao urlShorterEmbededDbDao) {
		dao = urlShorterEmbededDbDao;
	}

	public String createShortUrl(String longUrl) {
		UrlInfo urlInfo = new UrlInfo();
		final String encodedLongUrl = encodeLongUrl(longUrl);
		final String shortUrlKey = generateShortUrl(encodedLongUrl);

		urlInfo.setShortUrl(shortUrlKey);
		urlInfo.setLongUrl(encodedLongUrl);

		if (dao.insertShorterUrlInfo(urlInfo)) {
			keyGenerator.setCurrentKey(shortUrlKey);
			String shortUrl = UrlShorterConfiguration.getShorterURLRootPath() + shortUrlKey;
			return shortUrl;
		}
		return null;
	}

	public String getLongUrl(String shortUrlKey) {
		String encodeLongUrl = dao.fetchLongUrlInfo(shortUrlKey);
		return decodeLongUrl(encodeLongUrl);
	}

	public int getNumberOfUse(String shortUrlKey) {
		throw new RuntimeException("Unimplemented functionality");
	}

	public boolean isUrlActive(String shortUrlKey) {
		throw new RuntimeException("Unimplemented functionality");
	}

	private String generateShortUrl(String encodedLongUrl) {
		return keyGenerator.getNextKey();
	}

	private String encodeLongUrl(String longUrl) {
		String encodedLongUrl = Base64.encodeAsString(longUrl);
		System.out.println("ecncoded value is " + encodedLongUrl);
		return encodedLongUrl;
	}

	private String decodeLongUrl(String encodedLongUrl) {
		String decodedLongURL = Base64.decodeAsString(encodedLongUrl);
		System.out.println("Decoded value is " + encodedLongUrl + "::" + decodedLongURL);
		return decodedLongURL;
	}
}
