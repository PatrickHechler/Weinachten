package de.hechler.patrick.objects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Verteilung {
	
	private int[] geschenke;
	
	
	
	public Verteilung(int size, boolean richtig) {
		geschenke = new int[size];
		if (richtig) {
			int i = 0;
			while (i < geschenke.length) {
				geschenke[i] = i += 1;
			}
		}
	}
	
	public Verteilung(int[] geschenke) {
		this.geschenke = geschenke;
	}
	
	
	
	public void set(int teilnehmerNummer, int geschenkNummer) {
		geschenke[teilnehmerNummer - 1] = geschenkNummer;
	}
	
	public int get(int teilnehmerNummer) {
		return geschenke[teilnehmerNummer - 1];
	}
	
	@Override
	public Verteilung clone() {
		Verteilung ergebnis;
		try {
			ergebnis = (Verteilung) super.clone();
		} catch (Exception e) {
			return new Verteilung(geschenke.clone());
		}
		ergebnis.geschenke = geschenke.clone();
		return ergebnis;
	}
	
	public boolean isValid() {
		Set <Integer> tester;
		tester = new HashSet <Integer>(geschenke.length);
		for (int teste : geschenke) {
			if (teste < 1 || teste > geschenke.length || !tester.add(teste)) {
				return false;
			}
		}
		return true;
	}
	
	public void sortFromTo(int teilnehmerNummerStart, int teilnehmerNummerEnde) {
		Arrays.sort(geschenke, teilnehmerNummerStart - 1, teilnehmerNummerEnde);
	}
	
	public void sortFrom(int teilnehmerNummer) {
		Arrays.sort(geschenke, teilnehmerNummer - 1, geschenke.length);
	}
	
	public void sort() {
		Arrays.sort(geschenke);
	}
	
	public boolean isReversed() {
		for (int i = 0; i < geschenke.length; i ++ ) {
			if (geschenke[i] != geschenke.length - i) {
				return false;
			}
		}
		return true;
	}
	
	
	public void print() {
		int len = (geschenke.length + "").length();
		for (int i = 0; i < geschenke.length; i ++ ) {
			String teilnehmer = "" + (i + 1);
			String geschenk = "" + geschenke[i];
			while (teilnehmer.length() < len) {
				teilnehmer += " ";
			}
			while (geschenk.length() < len) {
				geschenk += " ";
			}
			System.out.println("Teilnehmer[" + teilnehmer + "]" + " kriegt: Geschenk[" + geschenk + "]");
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if ( !getClass().equals(obj.getClass())) {
			return false;
		}
		return Objects.deepEquals(geschenke, ((Verteilung) obj).geschenke);
	}
	
}
