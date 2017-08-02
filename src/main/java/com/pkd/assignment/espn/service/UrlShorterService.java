package com.pkd.assignment.espn.service;

public interface UrlShorterService {
	String createShortUrl(String longUrl);
	String getLongUrl(String shortUrlKey);
	int getNumberOfUse(String shortUrlKey);
	boolean isUrlActive(String shortUrlKey);
}
