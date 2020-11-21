package de.hechler.patrick.objects.feststellerimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hechler.patrick.objects.Bewertung;
import de.hechler.patrick.objects.Klasse;
import de.hechler.patrick.objects.Teilnehmer;
import de.hechler.patrick.objects.Verteilung;

class SuperFeststellerTest {

	private SuperFeststeller feststeller;
	
	@BeforeEach
	public void setup() {
		feststeller = new SuperFeststeller();
	}
	
	@AfterEach
	public void tearDown() {
		feststeller = null;
	}
	
	@Test
	void testKeineErstwuenschee() {
		String klassenText = 
				  "4 "
			    + "1 2 3 "
			    + "1 2 3 "
			    + "2 3 4 "
			    + "2 3 4";
		Klasse klasse = Klasse.lade(new Scanner(klassenText));
		Verteilung verteilung = feststeller.stelleFest(klasse);
		assertEquals(0, verteilung.get(1));
		assertEquals(0, verteilung.get(2));
		assertEquals(0, verteilung.get(3));
		assertEquals(0, verteilung.get(4));
		
		Klasse origKlasse = Klasse.lade(new Scanner(klassenText));
		Bewertung origBewertung = origKlasse.bewerte(verteilung);
		assertEquals("0.0.0", origBewertung.toString());

		Bewertung bewertung = klasse.bewerte(verteilung);
		
//		String oops = klasse.toString();
//		assertEquals(
//				"4\n" + 
//				"1 2 3\n" + 
//				"1 2 3\n" + 
//				"2 3 4\n" + 
//				"2 3 4\n", oops);
		
		assertEquals("0.0.0", bewertung.toString());
	}

	@Test
	void testEinzelnerErstwuenschee() {
		String klassenText = 
				  "4 "
			    + "1 2 3 "
			    + "3 1 2 "
			    + "1 2 3 "
			    + "1 2 3";
		Klasse klasse = Klasse.lade(new Scanner(klassenText));
		Verteilung verteilung = feststeller.stelleFest(klasse);
		assertEquals(0, verteilung.get(1));
		assertEquals(3, verteilung.get(2));
		assertEquals(0, verteilung.get(3));
		assertEquals(0, verteilung.get(4));
		
		Klasse origKlasse = Klasse.lade(new Scanner(klassenText));
		Bewertung origBewertung = origKlasse.bewerte(verteilung);
		assertEquals("1.0.0", origBewertung.toString());

		Bewertung bewertung = klasse.bewerte(verteilung);
		assertEquals("0.0.0", bewertung.toString());
	}

	
	@Test
	void testXMMErstwuenschee() {
		String klassenText = 
				  "6 "
			    + "1 5 6 "
			    + "1 5 6 "
			    + "2 1 3 "
			    + "2 5 6 "
			    + "3 5 6 "
			    + "3 5 6";
		Klasse klasse = Klasse.lade(new Scanner(klassenText));
		Verteilung verteilung = feststeller.stelleFest(klasse);
		assertEquals(0, verteilung.get(1));
		assertEquals(0, verteilung.get(2));
		assertEquals(2, verteilung.get(3));
		assertEquals(0, verteilung.get(4));
		assertEquals(0, verteilung.get(5));
		assertEquals(0, verteilung.get(6));
		
		Klasse origKlasse = Klasse.lade(new Scanner(klassenText));
		Bewertung origBewertung = origKlasse.bewerte(verteilung);
		assertEquals("1.0.0", origBewertung.toString());

		Bewertung bewertung = klasse.bewerte(verteilung);
		assertEquals("0.0.0", bewertung.toString());
	}

	

	private String getErstVerteilung(String klassenText) {
		Klasse klasse = Klasse.lade(new Scanner(klassenText));
		Klasse origKlasse = Klasse.lade(new Scanner(klassenText));
		
		Verteilung verteilung = feststeller.stelleFest(klasse);

		// sammele alle verteileten erstwuensche.
		List<Integer> verteilt = new ArrayList<>();
		for (int i=1; i<=klasse.size(); i++) {
			int geschenk = verteilung.get(i);
			if (geschenk != 0) {
				verteilt.add(geschenk);
			}
		}

		// fuer jeden verteilten erstwunsch wurde ein Teilnehmer aus Klasse entfernt.
		assertEquals(origKlasse.size()-+verteilt.size(), klasse.size());

		// die verteilten erstwuensche wurden den richtigen Teilnehmern zugeordnet.
		for(Teilnehmer t:origKlasse) {
			int geschenk = verteilung.get(t.nummer());
			if (geschenk != 0) {
				assertEquals(t.erstWunsch(), geschenk);
			}
		}
		// allen verbleibenden teilnehmern wurde keine Erstwunsch zugeordnet.
		for(Teilnehmer t:klasse) {
			assertEquals(0, verteilung.get(t.nummer()));
		}

		// erzeuge einen lesbaren sortierten rueckgabewert
		Collections.sort(verteilt);
		StringBuilder result = new StringBuilder();
		verteilt.forEach(n -> result.append(Integer.toString(n)).append(" "));
		
		return result.toString().trim();
	}

}
