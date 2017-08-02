package urlshorter;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pkd.assignment.espn.dao.UrlShorterEmbededDbDao;
import com.pkd.assignment.espn.service.UrlShorterService;
import com.pkd.assignment.espn.service.UrlShorterServiceImpl;
import com.pkd.assignment.espn.utils.UrlShorterConfiguration;

public class TestUrlShorterServiceImpl {
	private UrlShorterService urlShorter;
	private final String shortUrlTestData = "http://localhost:8080/su/000001";
	private final String shortUrlKey = "000001";
	private final String longUrlTestData = "https://www.google.com";
	
	@BeforeClass
	public static void setupBeforClass(){
		UrlShorterEmbededDbDao.createDatabaseConnection();
		UrlShorterConfiguration.setShorterURLRootPath("http://localhost:8080/su/");
	}
	
	@Before
	public void setup(){
		urlShorter = new UrlShorterServiceImpl(new UrlShorterEmbededDbDao());
	}
	
	@Test
	public void testCreateShortUrl(){
		String retShortUrl = urlShorter.createShortUrl(longUrlTestData);
		Assert.assertEquals("Does not match with expected value :: ", shortUrlTestData, retShortUrl);
	}
	
	@Test
	public void testGetLongUrl(){
		String retLongUrl = urlShorter.getLongUrl(shortUrlKey);
		Assert.assertEquals("Does not match with expected value :: ", longUrlTestData, retLongUrl);
	}
	
	@AfterClass
	public static void shutdownAfterClass(){
		UrlShorterEmbededDbDao.closeDatabaseConnection();
	}
}
