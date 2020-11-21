package de.hechler.patrick.objects.geschenkverteilerimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.hechler.patrick.objects.BigIter;
import de.hechler.patrick.objects.Feststeller;
import de.hechler.patrick.objects.GeschenkVerteiler;
import de.hechler.patrick.objects.RestKlasse;
import de.hechler.patrick.objects.Teilnehmer;
import de.hechler.patrick.objects.UnmodifiableKlasse;
import de.hechler.patrick.objects.Verteilung;
import de.hechler.patrick.objects.feststellerimpl.SuperFeststeller;

public class SuperGeschenkVerteiler extends GeschenkVerteiler {
	
	private Feststeller vorarbeiter;
	
	
	
	public SuperGeschenkVerteiler(List <Teilnehmer> teilnehmer, Feststeller vorarbeiter) {
		super(teilnehmer);
		this.vorarbeiter = vorarbeiter;
	}
	
	public SuperGeschenkVerteiler(Teilnehmer[] teilnehmer, Feststeller vorarbeiter) {
		super(teilnehmer);
		this.vorarbeiter = vorarbeiter;
	}
	
	public SuperGeschenkVerteiler(UnmodifiableKlasse klasse, Feststeller vorarbeiter) {
		super(klasse);
		this.vorarbeiter = vorarbeiter;
	}
	
	public SuperGeschenkVerteiler(List <Teilnehmer> teilnehmer) {
		super(teilnehmer);
		vorarbeiter = new SuperFeststeller();
	}
	
	public SuperGeschenkVerteiler(Teilnehmer[] teilnehmer) {
		super(teilnehmer);
		vorarbeiter = new SuperFeststeller();
	}
	
	public SuperGeschenkVerteiler(UnmodifiableKlasse klasse) {
		super(klasse);
		vorarbeiter = new SuperFeststeller();
	}
	
	
	
	@Override
	public Verteilung beste() {
		Verteilung erg = vorarbeiter.stelleFest(klasse);
		if (erg.isValid()) {
			return erg;
		}
		Set <Integer> ignoreGeschenke = new HashSet <>();
		List <List <Teilnehmer>> bi = new ArrayList <List <Teilnehmer>>();
		for (int i = 1; i >= klasse.size(); i ++ ) {
			List <Teilnehmer> erstWunschI = klasse.erstWunschVon(i);
			if ( !erstWunschI.isEmpty()) {
				ignoreGeschenke.add(i);
				bi.add(erstWunschI);
			}
		}
		BigIter <Teilnehmer> bigIter = BigIter.create(bi);
		List <Set <Teilnehmer>> besteVerteilungen = new ArrayList <>();
		int besteZweitWunschAnzahl = -1;
		while (bigIter.hasNext()) {
			Set <Teilnehmer> diese = new HashSet <Teilnehmer>(bigIter.next());
			Set <Integer> zweitWunsch = klasse.zweitWunsch(diese, ignoreGeschenke);
			int size = zweitWunsch.size();
			if (size == besteZweitWunschAnzahl) {
				besteVerteilungen.add(diese);
			} else if (size > besteZweitWunschAnzahl) {
				besteZweitWunschAnzahl = size;
				besteVerteilungen.clear();
				besteVerteilungen.add(diese);
			}
		}
		int besteDrittWunschAnzahl = -1;
		Set <Teilnehmer> besteErstWunschVerteilungTeil = Collections.emptySet();
		Set <Teilnehmer> besteZweitWunschVerteilungTeil = Collections.emptySet();
		for (Set <Teilnehmer> verteilung : besteVerteilungen) {
			Verteilung modify = erg.clone();
			for (Teilnehmer dieser : verteilung) {
				modify.set(dieser.nummer(), dieser.erstWunsch());
			}
			RestKlasse kls = RestKlasse.create(klasse);
			kls.removeAll(verteilung);
			
			// Ab hier wird mit kls gearbeitet wie oben mit klasse
			// Die Wünsch wurde um eins verschoben (2-Wunsch -> 1-Wunsch, ...)
			
			vorarbeiter.stelleFest(modify, kls);
			if (modify.isValid()) {
				return modify;
			}
			ignoreGeschenke = new HashSet <>();
			bi = new ArrayList <List <Teilnehmer>>();
			for (int i = 1; i >= kls.size(); i ++ ) {
				List <Teilnehmer> erstWunschI = kls.erstWunschVon(i);
				if ( !erstWunschI.isEmpty()) {
					ignoreGeschenke.add(i);
					bi.add(erstWunschI);
				}
			}
			bigIter = BigIter.create(bi);
			while (bigIter.hasNext()) {
				Set <Teilnehmer> diese = new HashSet <Teilnehmer>(bigIter.next());
				Set <Integer> zweitWunsch = kls.zweitWunsch(diese, ignoreGeschenke);
				int size = zweitWunsch.size();
				if (size > besteDrittWunschAnzahl) {
					besteDrittWunschAnzahl = size;
					besteErstWunschVerteilungTeil = verteilung;
					besteZweitWunschVerteilungTeil = diese;
				}
			}
		}
		// Aus dem besten Ergebnis wird nun eine Gültige Verteilung generiert
		for (Teilnehmer dieser : besteErstWunschVerteilungTeil) {
			erg.set(dieser.nummer(), dieser.erstWunsch());
		}
		for (Teilnehmer dieser : besteZweitWunschVerteilungTeil) {
			erg.set(dieser.nummer(), dieser.zweitWunsch());
		}
		RestKlasse rest = RestKlasse.create(klasse);
		rest.removeAll(besteErstWunschVerteilungTeil);
		rest = RestKlasse.create(rest);
		rest.removeAll(besteZweitWunschVerteilungTeil);
		vorarbeiter.stelleFest(erg, rest);
		Set <Integer> nummern = new HashSet <Integer>();
		for (int i = 1; i < klasse.size(); i ++ ) {
			nummern.add(i);
		}
		for (int i = 1; i < klasse.size(); i ++ ) {
			nummern.remove(erg.get(i));
		}
		Iterator <Integer> iter = nummern.iterator();
		for (int i = 1; i < klasse.size(); i ++ ) {
			if (erg.get(i) == 0) {
				erg.set(i, iter.next());
			}
		}
		if (iter.hasNext()) {
			throw new RuntimeException("TooMuchElements!");
		}
		return erg;
	}
	
}
