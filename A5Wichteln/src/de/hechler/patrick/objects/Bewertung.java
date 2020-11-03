package de.hechler.patrick.objects;


public class Bewertung implements Comparable <Bewertung> {
	
	private int[] richtigePlatzierungen;
	
	
	
	public Bewertung() {
		richtigePlatzierungen = new int[3];
	}
	
	
	
	public void incRichtigeErste() {
		richtigePlatzierungen[0] ++ ;
	}
	
	public void incRichtigeZweite() {
		richtigePlatzierungen[1] ++ ;
	}
	
	public void incRichtigeDritte() {
		richtigePlatzierungen[2] ++ ;
	}
	
	@Override
	public int compareTo(Bewertung other) {
		if (other == null) {
			return 1;
		}
		if (richtigePlatzierungen[0] == other.richtigePlatzierungen[0]) {
			if (richtigePlatzierungen[1] == other.richtigePlatzierungen[1]) {
				if (richtigePlatzierungen[2] == other.richtigePlatzierungen[2]) {
					return 0;
				}
				return richtigePlatzierungen[2] > other.richtigePlatzierungen[2] ? 1 : -1;
			}
			return richtigePlatzierungen[1] > other.richtigePlatzierungen[1] ? 1 : -1;
		}
		return richtigePlatzierungen[0] > other.richtigePlatzierungen[0] ? 1 : -1;
	}
	
	@Override
	public String toString() {
		return richtigePlatzierungen[0] + "." + richtigePlatzierungen[1] + "." + richtigePlatzierungen[2];
	}
	
}
