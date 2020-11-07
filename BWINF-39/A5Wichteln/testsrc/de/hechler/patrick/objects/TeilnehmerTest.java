package de.hechler.patrick.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TeilnehmerTest {

	@Test
	void testTeilnehmer() {
		Teilnehmer teilnehmer = new Teilnehmer(13, 14, 15, 16);
		assertEquals(13, teilnehmer.nummer());
		assertEquals(14, teilnehmer.erstWunsch());
		assertEquals(15, teilnehmer.zweitWunsch());
		assertEquals(16, teilnehmer.drittWunsch());
	}

	@Test
	void testBewerte() {
		Teilnehmer teilnehmer = new Teilnehmer(13, 14, 15, 16);
		Bewertung bewertung = new Bewertung();
		teilnehmer.bewerte(12, bewertung);
		assertEquals("0.0.0", bewertung.toString());
		teilnehmer.bewerte(13, bewertung);
		assertEquals("0.0.0", bewertung.toString());
		teilnehmer.bewerte(14, bewertung);
		assertEquals("1.0.0", bewertung.toString());
		teilnehmer.bewerte(14, bewertung);
		assertEquals("2.0.0", bewertung.toString());
		teilnehmer.bewerte(16, bewertung);
		assertEquals("2.0.1", bewertung.toString());
		teilnehmer.bewerte(17, bewertung);
		assertEquals("2.0.1", bewertung.toString());
		teilnehmer.bewerte(16, bewertung);
		assertEquals("2.0.2", bewertung.toString());
		teilnehmer.bewerte(16, bewertung);
		assertEquals("2.0.3", bewertung.toString());
		teilnehmer.bewerte(15, bewertung);
		assertEquals("2.1.3", bewertung.toString());
	}

}
