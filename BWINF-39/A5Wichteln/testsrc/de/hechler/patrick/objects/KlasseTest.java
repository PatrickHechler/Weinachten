package de.hechler.patrick.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	
	private final static String dreierKlasse = 
			  "3\n"
			+ "1 2 3\n"
			+ "1 2 3\n"
			+ "1 2 3\n";

	private final static String viererKlasse = 
			  "4\n"
			+ "1 2 3\n"
			+ "1 2 3\n"
			+ "1 2 3\n"
			+ "1 2 3\n";

	private final static String sechserKlasse = 
			  "6\n"
			+ "1 2 3\n"
			+ "2 3 4\n"
			+ "3 4 5\n"
			+ "4 5 6\n"
			+ "5 6 1\n"
			+ "6 1 2\n";
	
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
		klasse = Klasse.lade(new ByteArrayInputStream(sechserKlasse.getBytes()));
		assertEquals(6,  klasse.size());
		klasse = Klasse.lade(new ByteArrayInputStream(viererKlasse.getBytes()));
		assertEquals(4,  klasse.size());
		klasse = Klasse.lade(new ByteArrayInputStream(dreierKlasse.getBytes()));
		assertEquals(3,  klasse.size());
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
		assertTrue(erstWuensche.contains(2));
		
		klasse = Klasse.lade(new ByteArrayInputStream(sechserKlasse.getBytes()));
		erstWuensche = klasse.einzelnErstWunsch();
		assertEquals(6, erstWuensche.size());

		klasse = Klasse.lade(new ByteArrayInputStream(viererKlasse.getBytes()));
		erstWuensche = klasse.einzelnErstWunsch();
		assertEquals(0, erstWuensche.size());
		
		klasse = Klasse.lade(new ByteArrayInputStream(dreierKlasse.getBytes()));
		erstWuensche = klasse.einzelnErstWunsch();
		assertEquals(0, erstWuensche.size());
	}

	@Test
	void testEinzelnZweitWunsch() {
		Set<Integer> zweitWuensche = klasse.einzelnZweitWunsch();
		assertEquals(1, zweitWuensche.size());
		assertTrue(zweitWuensche.contains(3));
		
		klasse = Klasse.lade(new ByteArrayInputStream(sechserKlasse.getBytes()));
		zweitWuensche = klasse.einzelnZweitWunsch();
		assertEquals(6, zweitWuensche.size());
		
		klasse = Klasse.lade(new ByteArrayInputStream(viererKlasse.getBytes()));
		zweitWuensche = klasse.einzelnZweitWunsch();
		assertEquals(0, zweitWuensche.size());
		
		klasse = Klasse.lade(new ByteArrayInputStream(dreierKlasse.getBytes()));
		zweitWuensche = klasse.einzelnZweitWunsch();
		assertEquals(0, zweitWuensche.size());
	}

	@Test
	void testEinzelnDrittWunsch() {
		Set<Integer> drittWuensche = klasse.einzelnDrittWunsch();
		assertEquals(1, drittWuensche.size());
		assertTrue(drittWuensche.contains(1));

		klasse = Klasse.lade(new ByteArrayInputStream(sechserKlasse.getBytes()));
		drittWuensche = klasse.einzelnDrittWunsch();
		assertEquals(6, drittWuensche.size());

		klasse = Klasse.lade(new ByteArrayInputStream(viererKlasse.getBytes()));
		drittWuensche = klasse.einzelnDrittWunsch();
		assertEquals(0, drittWuensche.size());
		
		klasse = Klasse.lade(new ByteArrayInputStream(dreierKlasse.getBytes()));
		drittWuensche = klasse.einzelnDrittWunsch();
		assertEquals(0, drittWuensche.size());
	}


//	@Test
//	void testHabenErstWunsch() {
//		List<Teilnehmer> ersteEinser = klasse.habenErstWunsch(1);
//		assertEquals(2, ersteEinser.size());
//		List<Teilnehmer> ersteZweier = klasse.habenErstWunsch(2);
//		assertEquals(1, ersteZweier.size());
//		List<Teilnehmer> ersteDreier = klasse.habenErstWunsch(3);
//		assertEquals(0, ersteDreier.size());
//		List<Teilnehmer> ersteVierer = klasse.habenErstWunsch(4);
//		assertEquals(0, ersteVierer.size());
//		List<Teilnehmer> ersteFuenfer = klasse.habenErstWunsch(5);
//		assertEquals(2, ersteFuenfer.size());
//	}
//
//	@Test
//	void testHabenZweitWunsch() {
//		List<Teilnehmer> zweiteEinser = klasse.habenZweitWunsch(1);
//		assertEquals(0, zweiteEinser.size());
//		List<Teilnehmer> zweiteZweier = klasse.habenZweitWunsch(2);
//		assertEquals(2, zweiteZweier.size());
//		List<Teilnehmer> zweiteDreier = klasse.habenZweitWunsch(3);
//		assertEquals(1, zweiteDreier.size());
//		List<Teilnehmer> zweiteVierer = klasse.habenZweitWunsch(4);
//		assertEquals(2, zweiteVierer.size());
//		List<Teilnehmer> zweiteFuenfer = klasse.habenZweitWunsch(5);
//		assertEquals(0, zweiteFuenfer.size());
//	}
//
//	@Test
//	void testHabenDrittWunsch() {
//		List<Teilnehmer> dritteEinser = klasse.habenDrittWunsch(1);
//		assertEquals(1, dritteEinser.size());
//		List<Teilnehmer> dritteZweier = klasse.habenDrittWunsch(2);
//		assertEquals(2, dritteZweier.size());
//		List<Teilnehmer> dritteDreier = klasse.habenDrittWunsch(3);
//		assertEquals(2, dritteDreier.size());
//		List<Teilnehmer> dritteVierer = klasse.habenDrittWunsch(4);
//		assertEquals(0, dritteVierer.size());
//		List<Teilnehmer> dritteFuenfer = klasse.habenDrittWunsch(5);
//		assertEquals(0, dritteFuenfer.size());
//	}
//
//	
//	
//	@Test
//	void testEinzelnZweitWunschSetOfTeilnehmer() {
//		// 2 4 4 3 2 -> 3
//		Set<Integer> einzelnZweitWunsch = klasse.einzelnZweitWunsch(Collections.emptySet());
//		assertEquals(1, einzelnZweitWunsch.size());
//		assertTrue(einzelnZweitWunsch.contains(3));
//
//		Set<Teilnehmer> ignoreTeilnehmer = new HashSet<>();
//		
//		// 2 4 4 (3) 2  -> -
//		ignoreTeilnehmer.add(klasse.teilnehmer(4));
//		einzelnZweitWunsch = klasse.einzelnZweitWunsch(ignoreTeilnehmer);
//		assertEquals(0, einzelnZweitWunsch.size());
//		
//		// 2 4 (4) 3 2  -> 3 4
//		ignoreTeilnehmer.clear();
//		ignoreTeilnehmer.add(klasse.teilnehmer(3));
//		einzelnZweitWunsch = klasse.einzelnZweitWunsch(ignoreTeilnehmer);
//		assertEquals(2, einzelnZweitWunsch.size());
//		assertTrue(einzelnZweitWunsch.contains(3));
//		assertTrue(einzelnZweitWunsch.contains(4));
//		
//		// (2) (4) (4) 3 2  -> 2 3
//		ignoreTeilnehmer.clear();
//		ignoreTeilnehmer.add(klasse.teilnehmer(1));
//		ignoreTeilnehmer.add(klasse.teilnehmer(2));
//		ignoreTeilnehmer.add(klasse.teilnehmer(3));
//		einzelnZweitWunsch = klasse.einzelnZweitWunsch(ignoreTeilnehmer);
//		assertEquals(2, einzelnZweitWunsch.size());
//		assertTrue(einzelnZweitWunsch.contains(2));
//		assertTrue(einzelnZweitWunsch.contains(3));
//		
//		// (2) (4) (4) (3) (2)  -> -
//		ignoreTeilnehmer.clear();
//		ignoreTeilnehmer.add(klasse.teilnehmer(1));
//		ignoreTeilnehmer.add(klasse.teilnehmer(2));
//		ignoreTeilnehmer.add(klasse.teilnehmer(3));
//		ignoreTeilnehmer.add(klasse.teilnehmer(4));
//		ignoreTeilnehmer.add(klasse.teilnehmer(5));
//		einzelnZweitWunsch = klasse.einzelnZweitWunsch(ignoreTeilnehmer);
//		assertEquals(0, einzelnZweitWunsch.size());
//		
//	}
//
//	@Test
//	void testEinzelnDrittWunschSetOfTeilnehmerSetOfTeilnehmer() {
//		// 3 3 2 2 1 -> 1
//		Set<Integer> einzelnDrittWunsch = klasse.einzelnDrittWunsch(Collections.emptySet(), Collections.emptySet());
//		assertEquals(1, einzelnDrittWunsch.size());
//		assertTrue(einzelnDrittWunsch.contains(1));
//
//		Set<Teilnehmer> ignoreTeilnehmerA = new HashSet<>();
//		Set<Teilnehmer> ignoreTeilnehmerB = new HashSet<>();
//		
//		// 3 (3) 2 <2> 1 -> 1 2 3
//		ignoreTeilnehmerA.add(klasse.teilnehmer(2));
//		ignoreTeilnehmerB.add(klasse.teilnehmer(4));
//		einzelnDrittWunsch = klasse.einzelnDrittWunsch(ignoreTeilnehmerA, ignoreTeilnehmerB);
//		assertEquals(3, einzelnDrittWunsch.size());
//		assertTrue(einzelnDrittWunsch.contains(1));
//		assertTrue(einzelnDrittWunsch.contains(2));
//		assertTrue(einzelnDrittWunsch.contains(3));
//		
//		// 3 (<3>) 2 <2> (1) -> 2 3
//		ignoreTeilnehmerA.clear();
//		ignoreTeilnehmerB.clear();
//		ignoreTeilnehmerA.add(klasse.teilnehmer(2));
//		ignoreTeilnehmerA.add(klasse.teilnehmer(5));
//		ignoreTeilnehmerB.add(klasse.teilnehmer(2));
//		ignoreTeilnehmerB.add(klasse.teilnehmer(4));
//		einzelnDrittWunsch = klasse.einzelnDrittWunsch(ignoreTeilnehmerA, ignoreTeilnehmerB);
//		assertEquals(2, einzelnDrittWunsch.size());
//		assertTrue(einzelnDrittWunsch.contains(2));
//		assertTrue(einzelnDrittWunsch.contains(3));
//		
//	}

	@Test
	void testBewerte() {
		
	}

}
