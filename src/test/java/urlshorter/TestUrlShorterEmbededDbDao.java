package urlshorter;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pkd.assignment.espn.dao.UrlShorterDao;
import com.pkd.assignment.espn.dao.UrlShorterEmbededDbDao;
import com.pkd.assignment.espn.model.UrlInfo;

public class TestUrlShorterEmbededDbDao {
	private UrlShorterDao dao =null;
	private final String shortUrlKeyTestData = "ABCDEF";
	private final String longUrlTestData = "https://www.google.com";
	private final String shortUrlKeyTestData1 = "ABCDE1";
	private final String longUrlTestData1 = "https://www.google1.com";
	private UrlInfo info =null;
	
	@BeforeClass
	public static void setupForClass(){
		UrlShorterEmbededDbDao.createDatabaseConnection();
	}
	
	@Before
	public void setup(){
		dao = new UrlShorterEmbededDbDao();
	}
	
	@Test
	public void testInsertShorterUrlInfo(){
		info = new UrlInfo();
    	info.setShortUrl(shortUrlKeyTestData);
    	info.setLongUrl(longUrlTestData);
    	
		boolean ret = dao.insertShorterUrlInfo(info);
    	Assert.assertTrue("Data inserted successfully", ret);
	}
	
	@Test
	public void testFetchLongUrlInfo(){
		UrlInfo info1 = new UrlInfo();
    	info1.setShortUrl(shortUrlKeyTestData1);
    	info1.setLongUrl(longUrlTestData1);
    	
		boolean ret = dao.insertShorterUrlInfo(info1);
    	Assert.assertTrue("Data inserted successfully", ret);
    	
		String longUrlRet = dao.fetchLongUrlInfo(shortUrlKeyTestData1);
		Assert.assertEquals("Fetch from database not match::", longUrlTestData1, longUrlRet);
	}
	
	@AfterClass
	public static void tearDown(){
		UrlShorterEmbededDbDao.closeDatabaseConnection();
	}
}
