package de.hechler.patrick.objects.geschenkverteilerimpl;

import static org.junit.Assert.fail;

import java.util.Random;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import de.hechler.patrick.objects.Bewertung;
import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.UnmodifiableKlasse;
import de.hechler.patrick.objects.Verteilung;
import de.hechler.patrick.objects.generatorenimpl.OptimierterVerteilungsGenerator;


class SuperGeschenkVerteilerTest {
	
	
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
		for (int i=0; i<100; i++) {
			String klassenText = generateKlassenText(4, i);
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
			System.out.println("SUP:");
			UnmodifiableKlasse optKl = UnmodifiableKlasse.lade(new Scanner(klassenText));
			SuperGeschenkVerteiler opt = new SuperGeschenkVerteiler(optKl);
			Verteilung optVer = opt.beste();
			optVer.print();
			Bewertung optBewertung = optKl.bewerte(optVer);
			System.out.println(optBewertung);
	
			System.out.println();
			System.out.println("OPT:");
			Klasse bruKl = Klasse.lade(new Scanner(klassenText));
			OptimierterVerteilungsGenerator bru = new OptimierterVerteilungsGenerator(bruKl);
			Verteilung bruVer = bru.besteVerbleibende();
			bruVer.print();
			Bewertung bruBewertung = optKl.bewerte(bruVer);
			System.out.println(bruBewertung);
			if (!bruBewertung.equals(optBewertung)) {
				throw new RuntimeException("unterschiedliche Bewertungen SUP: "+optBewertung+", OPT: "+bruBewertung);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println(klassenText);
			fail(e.getMessage()+": "+klassenText);
		}
	
	}
	
}