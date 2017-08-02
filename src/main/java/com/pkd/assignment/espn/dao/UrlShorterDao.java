package com.pkd.assignment.espn.dao;

import com.pkd.assignment.espn.model.UrlInfo;

public interface UrlShorterDao {
	public boolean insertShorterUrlInfo(UrlInfo info);
	public String fetchLongUrlInfo(String shortUrl);
}
