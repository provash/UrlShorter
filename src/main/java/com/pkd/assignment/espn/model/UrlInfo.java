package com.pkd.assignment.espn.model;

import java.util.Date;
/**
 * @author Provash
 * Model class to store DB record
 */
public class UrlInfo {
	private String shortUrl;
	private String longUrl;
	private RecordStatus status;
	private Date creationDate;
	private Date expDate;
	
	public String getShortUrl() {
		return shortUrl;
	}
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public String getLongUrl() {
		return longUrl;
	}
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	
	public RecordStatus getStatus() {
		return status;
	}
	public void setStatus(RecordStatus status) {
		this.status = status;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	
}
