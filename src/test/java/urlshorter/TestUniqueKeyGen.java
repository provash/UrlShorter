package urlshorter;

import org.junit.Assert;
import org.junit.Test;

import com.pkd.assignment.espn.utils.UniqueKeyGen;

public class  TestUniqueKeyGen {
    private final String curKey = "0000A0";
    private final String nextDefaultKey = "000001";
    private final String nextKey = "0000A1";
    private final String maxKey = "ZZZZZZ";

    
    @Test
	public void testGetNextKey(){
    	String retNextKey = UniqueKeyGen.getKeyGeneratorInst(null).getNextKey();
    	Assert.assertEquals("Next doest match with default initialization: expected 000001", nextDefaultKey, retNextKey);
	}
    
    @Test
	public void testSetCurrentKey(){
    	UniqueKeyGen keyGen = UniqueKeyGen.getKeyGeneratorInst(null);
    	keyGen.setCurrentKey(curKey);
    	String retNextKey1 = keyGen.getNextKey();
    	Assert.assertEquals("Next key doest not match : expected 0000A1", nextKey, retNextKey1);
    	
    	keyGen.setCurrentKey(maxKey);
    	String retNextKey2 = keyGen.getNextKey();
    	Assert.assertEquals("Next key does not match : expected 000001", nextDefaultKey, retNextKey2);
    	
	}
	

}
