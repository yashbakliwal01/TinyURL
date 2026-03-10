package com.tinyurl.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Base62EncoderTest {
	
	Base62Encoder encoder = new Base62Encoder();
	
	@Test
	public void testEncode() {
		String encoded = encoder.encode(12345L);
		assert(encoded != null && !encoded.isEmpty());
		Assertions.assertTrue(encoded.length()>0);
	}
	
	@Test
	public void testGenerateRandomCode() {
		String code1 = encoder.generateRandomCode();
		String code2 = encoder.generateRandomCode();
		
		Assertions.assertNotNull(code1);
		Assertions.assertNotNull(code2);
		Assertions.assertEquals(6, code1.length());
		Assertions.assertEquals(6, code2.length());
		Assertions.assertNotEquals(code1, code2);
	}
}
