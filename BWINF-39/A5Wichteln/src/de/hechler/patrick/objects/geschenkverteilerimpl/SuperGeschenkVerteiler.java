package de.hechler.patrick.objects.geschenkverteilerimpl;

import java.util.List;

import de.hechler.patrick.objects.GeschenkVerteiler;
import de.hechler.patrick.objects.Teilnehmer;
import de.hechler.patrick.objects.UnmodifiableKlasse;
import de.hechler.patrick.objects.Verteilung;

public class SuperGeschenkVerteiler extends GeschenkVerteiler {
	
	public SuperGeschenkVerteiler(List <Teilnehmer> teilnehmer) {
		super(teilnehmer);
	}
	
	public SuperGeschenkVerteiler(Teilnehmer[] teilnehmer) {
		super(teilnehmer);
	}
	
	public SuperGeschenkVerteiler(UnmodifiableKlasse klasse) {
		super(klasse);
	}
	
	
	
	@Override
	public Verteilung beste() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
