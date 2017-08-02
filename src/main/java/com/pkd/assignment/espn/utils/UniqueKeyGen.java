package com.pkd.assignment.espn.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Provash
 * This class has implemented unique key generation algo.
 * current implementation Brief :
 *	1) initialize default key value ( It could be fetch from DB or extern property file)
 *	2) create unique based on 36 letter base number system. We can increase the number system letter for more unique value
 *	3) currently it will create 36^6 unique queue
 * TODO : 
 * 	1) Need to implement tolerance mechanism 
 *  2) externalize current and initial key setting
 */
public class UniqueKeyGen {
	private final String initialKey;
	private String currentKey;
	private static UniqueKeyGen keyGen = null;

	// minimum number in 36 base system.
	private static final String defaultKey = "000000";
	private static final int KEY_LENGTH = 6;
	private static final int NUM_BASE = 36;
	private static final int MIN_VALUE_IN_CURRENT_NUMBER_SYS = 0;
	private static final long MAX_VALUE_IN_CURRENT_NUMBER_SYS = ((NUM_BASE * 100000) + (NUM_BASE * 10000)
			+ (NUM_BASE * 1000) + (NUM_BASE * 100) + (NUM_BASE * 10) + (NUM_BASE * 1));

	private static final Map<Character, Integer> KEY_LETTER_VALUE_MAP = new HashMap<>();
	static {
		KEY_LETTER_VALUE_MAP.put('0', 0);
		KEY_LETTER_VALUE_MAP.put('1', 1);
		KEY_LETTER_VALUE_MAP.put('2', 2);
		KEY_LETTER_VALUE_MAP.put('3', 3);
		KEY_LETTER_VALUE_MAP.put('4', 4);
		KEY_LETTER_VALUE_MAP.put('5', 5);
		KEY_LETTER_VALUE_MAP.put('6', 6);
		KEY_LETTER_VALUE_MAP.put('7', 7);
		KEY_LETTER_VALUE_MAP.put('8', 8);
		KEY_LETTER_VALUE_MAP.put('9', 9);
		KEY_LETTER_VALUE_MAP.put('A', 10);
		KEY_LETTER_VALUE_MAP.put('B', 11);
		KEY_LETTER_VALUE_MAP.put('C', 12);
		KEY_LETTER_VALUE_MAP.put('D', 13);
		KEY_LETTER_VALUE_MAP.put('E', 14);
		KEY_LETTER_VALUE_MAP.put('F', 15);
		KEY_LETTER_VALUE_MAP.put('G', 16);
		KEY_LETTER_VALUE_MAP.put('H', 17);
		KEY_LETTER_VALUE_MAP.put('I', 18);
		KEY_LETTER_VALUE_MAP.put('J', 19);
		KEY_LETTER_VALUE_MAP.put('K', 20);
		KEY_LETTER_VALUE_MAP.put('L', 21);
		KEY_LETTER_VALUE_MAP.put('M', 22);
		KEY_LETTER_VALUE_MAP.put('N', 23);
		KEY_LETTER_VALUE_MAP.put('O', 24);
		KEY_LETTER_VALUE_MAP.put('P', 25);
		KEY_LETTER_VALUE_MAP.put('Q', 26);
		KEY_LETTER_VALUE_MAP.put('R', 27);
		KEY_LETTER_VALUE_MAP.put('S', 28);
		KEY_LETTER_VALUE_MAP.put('T', 29);
		KEY_LETTER_VALUE_MAP.put('U', 30);
		KEY_LETTER_VALUE_MAP.put('V', 31);
		KEY_LETTER_VALUE_MAP.put('W', 32);
		KEY_LETTER_VALUE_MAP.put('X', 33);
		KEY_LETTER_VALUE_MAP.put('Y', 34);
		KEY_LETTER_VALUE_MAP.put('Z', 35);
	}

	private static final Map<Integer, Character> KEY_VALUE_LETTER_MAP = new HashMap<>();
	static {
		KEY_VALUE_LETTER_MAP.put(0, '0');
		KEY_VALUE_LETTER_MAP.put(1, '1');
		KEY_VALUE_LETTER_MAP.put(2, '2');
		KEY_VALUE_LETTER_MAP.put(3, '3');
		KEY_VALUE_LETTER_MAP.put(4, '4');
		KEY_VALUE_LETTER_MAP.put(5, '5');
		KEY_VALUE_LETTER_MAP.put(6, '6');
		KEY_VALUE_LETTER_MAP.put(7, '7');
		KEY_VALUE_LETTER_MAP.put(8, '8');
		KEY_VALUE_LETTER_MAP.put(9, '9');
		KEY_VALUE_LETTER_MAP.put(10, 'A');
		KEY_VALUE_LETTER_MAP.put(11, 'B');
		KEY_VALUE_LETTER_MAP.put(12, 'C');
		KEY_VALUE_LETTER_MAP.put(13, 'D');
		KEY_VALUE_LETTER_MAP.put(14, 'E');
		KEY_VALUE_LETTER_MAP.put(15, 'F');
		KEY_VALUE_LETTER_MAP.put(16, 'G');
		KEY_VALUE_LETTER_MAP.put(17, 'H');
		KEY_VALUE_LETTER_MAP.put(18, 'I');
		KEY_VALUE_LETTER_MAP.put(19, 'J');
		KEY_VALUE_LETTER_MAP.put(20, 'K');
		KEY_VALUE_LETTER_MAP.put(21, 'L');
		KEY_VALUE_LETTER_MAP.put(22, 'M');
		KEY_VALUE_LETTER_MAP.put(23, 'N');
		KEY_VALUE_LETTER_MAP.put(24, 'O');
		KEY_VALUE_LETTER_MAP.put(25, 'P');
		KEY_VALUE_LETTER_MAP.put(26, 'Q');
		KEY_VALUE_LETTER_MAP.put(27, 'R');
		KEY_VALUE_LETTER_MAP.put(28, 'S');
		KEY_VALUE_LETTER_MAP.put(29, 'T');
		KEY_VALUE_LETTER_MAP.put(30, 'U');
		KEY_VALUE_LETTER_MAP.put(31, 'V');
		KEY_VALUE_LETTER_MAP.put(32, 'W');
		KEY_VALUE_LETTER_MAP.put(33, 'X');
		KEY_VALUE_LETTER_MAP.put(34, 'Y');
		KEY_VALUE_LETTER_MAP.put(35, 'Z');
	}

	private UniqueKeyGen(final String initialKey) {
		this.initialKey = initialKey;
		this.currentKey = initialKey;
	}

	public static UniqueKeyGen getKeyGeneratorInst(String initialKey) {
		if (keyGen == null) {
			synchronized (UniqueKeyGen.class) {
				if (keyGen == null) {
					if (initialKey == null || initialKey.length() < 6)
						keyGen = new UniqueKeyGen(defaultKey);
					else
						keyGen = new UniqueKeyGen(initialKey);
				}
			}
		}
		return keyGen;
	}

	public synchronized void setCurrentKey(String currentKey) {
		this.currentKey = currentKey;
	}

	public synchronized String getNextKey() {
		long currentKeyInLong = getIntValue(currentKey.toCharArray());
		if (currentKeyInLong < MAX_VALUE_IN_CURRENT_NUMBER_SYS) {
			return nextKey();
		}else{
			currentKey = initialKey;
			System.out.println("Reach Maximum unique creation stage :: reset to initial key");
			return nextKey();
		}
	}

	private String nextKey() {
		int carry = 0;
		StringBuilder curKey = new StringBuilder(currentKey);
		StringBuilder nextKey = new StringBuilder();
		int index = KEY_LENGTH-1;
		while (index >= 0) {
			int keyLetterIntValue = KEY_LETTER_VALUE_MAP.get(curKey.charAt(index));
			int sum = 0;
			if(index == (KEY_LENGTH-1))
				sum = keyLetterIntValue + carry + 1;
			else
				sum = keyLetterIntValue + carry;
			
			int updatedkeyLetterIntValue = sum % NUM_BASE;
			carry = sum / NUM_BASE;
			nextKey.append(KEY_VALUE_LETTER_MAP.get(updatedkeyLetterIntValue));
			index--;
		}
		return nextKey.reverse().toString();
	}

	private static long getIntValue(char[] keyArray) {
		int multiplier = 100000;
		long intValue = 0;
		for (char keyLetter : keyArray) {
			int keyLetterIntValue = KEY_LETTER_VALUE_MAP.get(keyLetter);
			intValue += (keyLetterIntValue * multiplier);
			multiplier = multiplier / 10;
		}
		return intValue;
	}
}
