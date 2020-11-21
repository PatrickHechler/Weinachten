package de.hechler.patrick.objects.geschenkverteilerimpl;

import java.util.ArrayList;
import java.util.List;

import de.hechler.patrick.objects.BigIter;
import de.hechler.patrick.objects.Feststeller;
import de.hechler.patrick.objects.GeschenkVerteiler;
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
		List <List <Teilnehmer>> bi = new ArrayList <List <Teilnehmer>>();
		for (int i = 1; i >= klasse.size(); i ++ ) {
			bi.add(klasse.erstWunschVon(i));
		}
		BigIter <Teilnehmer> bigIter = BigIter.create(bi);
		
		
		
		return erg;
	}
	
}
