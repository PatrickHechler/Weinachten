package de.hechler.patrick.objects.geschenkverteilerimpl;

import static org.junit.Assert.fail;

import java.util.Random;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import de.hechler.patrick.objects.Bewertung;
import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.Verteilung;


class SuperGeschenkVerteilerTest {

	
	@Test
	void test_01_NichtOptimal4() {
		compareResults(
				  "5 "
				+ "5 4 3 "
				+ "3 4 5 "
				+ "3 4 5 "
				+ "5 2 1 "
				+ "4 2 5 "
		);
	}
	

	@Test
	void test_02_NichtOptimal5() {
		compareResults(
				  "5 "
				+ "1 2 5 "
				+ "4 3 5 "
				+ "1 4 2 "
				+ "1 2 5 "
				+ "1 2 5 "
		);
	}
	

	
	@Test
	void test_03_BesserAlsOptimal() {
		compareResults(
				  "5 "
				+ "1 3 5 "
				+ "1 4 3 "
				+ "1 4 3 "
				+ "3 5 2 "
				+ "1 4 3 "
		);
	}

	
	
	@Test
	void testNichtOptimal3() {
		compareResults(
				  "10 "
				+ " 9  3  1 "
				+ " 8 10  1 "
				+ " 7 10  8 "
				+ " 9  5  7 "
				+ " 5  7  8 "
				+ "10  1  2 "
				+ " 9  8 10 "
				+ " 3  7  5 "
				+ " 3  6  4 "
				+ " 3 10  7 "
		);
	}

	 
	
	@Test
	void testOptBewertungFalsch() {
		compareResults(
				  "4 "
				+ "3 4 2 "
				+ "2 4 1 "
				+ "1 3 2 "
				+ "1 3 2 "
		);
	}

	
	
	@Test
	void testNichtOptimal2() {
		compareResults(
				  "4 "
				+ "3 1 4 "
				+ "1 3 4 "
				+ "3 1 4 "
				+ "3 2 4 "
		);
	}

	
	@Test
	void testNichtOptimal1() {
		compareResults(
				  "4 "
				+ "2 1 4 "
				+ "1 4 3 "
				+ "1 2 4 "
				+ "3 2 1 "
		);
	}

	 
	@Test
	void testOptGenNull() {
		compareResults(
				  "4 "
				+ "3 1 2 "
				+ "2 1 3 "
				+ "4 3 1 "
				+ "1 4 3"
		);
		 
	}
	
	@Test
	void testVierer() {
		for (int i=0; i<10000; i++) {
			String klassenText = generateKlassenText(4, i);
			compareResults(klassenText);
		}
	}

	@Test
	void testfuenfer() {
		for (int i=0; i<10000; i++) {
			String klassenText = generateKlassenText(5, i);
			compareResults(klassenText);
		}
	}

	@Test
	void testZehner() {
		for (int i=0; i<100; i++) {
			String klassenText = generateKlassenText(10, i);
			compareResults(klassenText);
		}
	}

	private String generateKlassenText(int size, long seed) {
		Random rand = new Random(seed);
		StringBuilder result = new StringBuilder();
		result.append(Integer.toString(size)).append(' ');
		for (int i=0; i<size; i++) {
			int a = rand.nextInt(size)+1;
			result.append(Integer.toString(a)).append(' ');
			int b = rand.nextInt(size)+1;
			while (b == a) {
				b = rand.nextInt(size)+1;
			}
			result.append(Integer.toString(b)).append(' ');
			int c = rand.nextInt(size)+1;
			while ((c == a) || (c == b)) {
				c = rand.nextInt(size)+1;
			}
			result.append(Integer.toString(c)).append(' ');
		}
		return result.toString();
	}
	
	
	void compareResults(String klassenText) {
		try {
			Klasse bewertungKl = Klasse.lade(new Scanner(klassenText));
			System.out.println("SUP:");
			Klasse optKl = Klasse.lade(new Scanner(klassenText));
			SuperGeschenkVerteiler opt = new SuperGeschenkVerteiler(optKl);
			Verteilung optVer = opt.beste();
			optVer.print();
			Bewertung optBewertung = bewertungKl.bewerte(optVer);
			System.out.println(optBewertung);
	
			System.out.println();
			System.out.println("OPT:");
			//TODO Referenz einbauen
//			Klasse bruKl = Klasse.lade(new Scanner(klassenText));
//			OptimierterVerteilungsGenerator bru = new OptimierterVerteilungsGenerator(bruKl);
//			Verteilung bruVer = bru.besteVerbleibende();
//			bruVer.print();
//			Bewertung bruBewertung = bewertungKl.bewerte(bruVer);
//			System.out.println(bruBewertung);
//			if (!bruBewertung.equals(optBewertung)) {
//				throw new RuntimeException("unterschiedliche Bewertungen SUP: "+optBewertung+", OPT: "+bruBewertung);
//			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(klassenText);
			fail(e.getMessage()+": "+klassenText);
		}
	
	}
	
}