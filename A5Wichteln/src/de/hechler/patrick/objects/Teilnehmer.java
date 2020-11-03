package de.hechler.patrick.objects;

public class Teilnehmer {
	
	private final int nummer;
	private int[] wunsch;
	
	
	
	public Teilnehmer(int eigeneNummer, int erstWunsch, int zweitWunsch, int drittWunsch) {
		if (eigeneNummer < 1 || erstWunsch < 1 || zweitWunsch < 1 || drittWunsch < 1) {
			throw new RuntimeException("Die folgenden vars müssen min. 1 sein: eigenNummer = " + eigeneNummer + ", erstWunsch = " + erstWunsch + ", zweitWunsch = "
					+ zweitWunsch + ", drittWunsch = " + drittWunsch + "!");
		}
		nummer = eigeneNummer;
		wunsch = new int[] {erstWunsch, zweitWunsch, drittWunsch };
	}
	
	
	
	public void bewerte(int zugeteilt, Bewertung bew) {
		if (zugeteilt == wunsch[0]) {
			bew.incRichtigeErste();
		}
		if (zugeteilt == wunsch[1]) {
			bew.incRichtigeZweite();
		}
		if (zugeteilt == wunsch[2]) {
			bew.incRichtigeDritte();
		}
	}
	
	public int erstWunsch() {
		return wunsch[0];
	};
	
	public int zweitWunsch() {
		return wunsch[1];
	};
	
	public int drittWunsch() {
		return wunsch[2];
	};
	
	public int nummer() {
		return nummer;
	}
	
	@Override
	public String toString() {
		return "T[#"+nummer+":"+erstWunsch()+","+zweitWunsch()+","+drittWunsch()+"]";
	}
}
