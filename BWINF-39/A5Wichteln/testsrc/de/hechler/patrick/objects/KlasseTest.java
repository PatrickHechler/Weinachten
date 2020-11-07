package de.hechler.patrick.objects;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KlasseTest {

	private Klasse klasse;
	
	private final static String fuenferKlasse = 
			  "5\n"
			+ "1 2 3\n"
			+ "2 4 3\n"
			+ "1 4 2\n"
			+ "5 3 2\n"
			+ "5 2 1\n";
	
	@BeforeEach
	void setUp() throws Exception {
		klasse = Klasse.lade(new ByteArrayInputStream(fuenferKlasse.getBytes()));
	}

	@AfterEach
	void tearDown() throws Exception {
		klasse = null;
	}

	@Test
	void testLade() {
		// test initial klasse
		assertEquals(fuenferKlasse, klasse.toString());
		
		// test with space insted of line breaks
		String modKlass = fuenferKlasse.replace("\n", " ");
		klasse = Klasse.lade(new ByteArrayInputStream(modKlass.getBytes()));
		assertEquals(fuenferKlasse, klasse.toString());

		// test with multiple whitespaces insted of spaces
		modKlass = "\t" + modKlass.replace(" ", "  \t \r\n  ");
		klasse = Klasse.lade(new ByteArrayInputStream(modKlass.getBytes()));
		assertEquals(fuenferKlasse, klasse.toString());

		// Remove trailing whitespace
		modKlass = modKlass.trim();
		klasse = Klasse.lade(new ByteArrayInputStream(modKlass.getBytes()));
		assertEquals(fuenferKlasse, klasse.toString());

		// not enough data throws NoSuchElementException
		final String errKlass = modKlass.substring(0, modKlass.length()-1);
		assertThrows(NoSuchElementException.class, () -> {
			klasse = Klasse.lade(new ByteArrayInputStream(errKlass.getBytes()));
		});

		// additional data is ignored
		final String tooBigKlass = modKlass+"\n1 2 3";
		klasse = Klasse.lade(new ByteArrayInputStream(tooBigKlass.getBytes()));
		assertEquals(fuenferKlasse, klasse.toString());

		// invalid ids are ignored
		final String wrongIDsKlass = "1\n99 99 99\n";
		klasse = Klasse.lade(new ByteArrayInputStream(wrongIDsKlass.getBytes()));
		assertEquals(wrongIDsKlass, klasse.toString());
		
	}

	@Test
	void testSize() {
		assertEquals(5,  klasse.size());
	}

	@Test
	void testTeilnehmer() {
		Teilnehmer teilnehmer = klasse.teilnehmer(1);
		assertEquals("T[#1:1,2,3]", teilnehmer.toString());
		teilnehmer = klasse.teilnehmer(2);
		assertEquals("T[#2:2,4,3]", teilnehmer.toString());
		teilnehmer = klasse.teilnehmer(5);
		assertEquals("T[#5:5,2,1]", teilnehmer.toString());
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> klasse.teilnehmer(6));
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> klasse.teilnehmer(0));
	}

	@Test
	void testEinzelnErstWunsch() {
		Set<Integer> erstWuensche = klasse.einzelnErstWunsch();
		assertEquals(1, erstWuensche.size());
		assertFalse(erstWuensche.contains(1));
		assertTrue(erstWuensche.contains(2));
		assertFalse(erstWuensche.contains(5));
	}

	@Test
	void testEinzelnZweitWunsch() {
		fail("Not yet implemented");
	}

	@Test
	void testEinzelnZweitWunschSetOfTeilnehmer() {
		fail("Not yet implemented");
	}

	@Test
	void testEinzelnDrittWunsch() {
		fail("Not yet implemented");
	}

	@Test
	void testEinzelnDrittWunschSetOfTeilnehmerSetOfTeilnehmer() {
		fail("Not yet implemented");
	}

	@Test
	void testHabenErstWunsch() {
		fail("Not yet implemented");
	}

	@Test
	void testHabenZweitWunsch() {
		fail("Not yet implemented");
	}

	@Test
	void testHabenDrittWunsch() {
		fail("Not yet implemented");
	}

	@Test
	void testBewerte() {
		fail("Not yet implemented");
	}

}
