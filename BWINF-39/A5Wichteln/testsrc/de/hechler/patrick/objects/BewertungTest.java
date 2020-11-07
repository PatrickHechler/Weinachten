package de.hechler.patrick.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BewertungTest {

	private Bewertung bewertung; 
	
	@BeforeEach
	void setUp() throws Exception {
		bewertung = new Bewertung();

	}

	@AfterEach
	void tearDown() throws Exception {
		bewertung = null;
	}

	@Test
	void testIncRichtigeErste() {
		assertEquals("0.0.0", bewertung.toString());
		bewertung.incRichtigeErste();
		assertEquals("1.0.0", bewertung.toString());
		for (int i=0; i<1000; i++) {
			bewertung.incRichtigeErste();
		}
		assertEquals("1001.0.0", bewertung.toString());
	}

	@Test
	void testIncRichtigeZweite() {
		assertEquals("0.0.0", bewertung.toString());
		bewertung.incRichtigeZweite();
		assertEquals("0.1.0", bewertung.toString());
		for (int i=0; i<1000; i++) {
			bewertung.incRichtigeZweite();
		}
		assertEquals("0.1001.0", bewertung.toString());
	}

	@Test
	void testIncRichtigeDritte() {
		assertEquals("0.0.0", bewertung.toString());
		bewertung.incRichtigeDritte();
		assertEquals("0.0.1", bewertung.toString());
		for (int i=0; i<1000; i++) {
			bewertung.incRichtigeDritte();
		}
		assertEquals("0.0.1001", bewertung.toString());
	}

	@Test
	void testCompareTo() {
		Bewertung bewertung2 = new Bewertung();
		assertTrue(bewertung.compareTo(bewertung2) == 0);  // 000 = 000
		bewertung.incRichtigeDritte();
		bewertung.incRichtigeDritte();
		bewertung.incRichtigeDritte();
		assertTrue(bewertung.compareTo(bewertung2) > 0);   // 003 > 000
		bewertung2.incRichtigeZweite();
		bewertung2.incRichtigeZweite();
		assertTrue(bewertung.compareTo(bewertung2) < 0);   // 003 < 020
		bewertung.incRichtigeErste();
		assertTrue(bewertung.compareTo(bewertung2) > 0);   // 103 > 020
		bewertung2.incRichtigeErste();
		bewertung.incRichtigeZweite();
		bewertung.incRichtigeZweite();
		bewertung2.incRichtigeDritte();
		bewertung2.incRichtigeDritte();
		bewertung2.incRichtigeDritte();
		assertTrue(bewertung.compareTo(bewertung2) == 0);   // 123 = 123
		
	}

	@Test
	void testToString() {
		assertEquals("0.0.0", bewertung.toString());
		bewertung.incRichtigeErste();
		bewertung.incRichtigeZweite();
		bewertung.incRichtigeDritte();
		for (int i=0; i<1000; i++) {
			bewertung.incRichtigeErste();
			bewertung.incRichtigeZweite();
			bewertung.incRichtigeDritte();
		}
		assertEquals("1001.1001.1001", bewertung.toString());
	}

}
