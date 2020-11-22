package de.hechler.patrick.haupt;

import java.io.File;

import de.hechler.patrick.objects.GeschenkVerteiler;
import de.hechler.patrick.objects.UnmodifiableKlasse;

public class Haupt {
	
	private static GeschenkVerteiler verteiler;
	private static UnmodifiableKlasse bewerter;
	
	public static void main(String[] args) {
		config(args);
		
		
		
	}
	
	private static void config(String[] args) {
		boolean console = false;
		File file = null;
		for (int i = 0; i < args.length; i ++ ) {
			switch (args[i].toLowerCase()) {
			case "-c":
			case "-console":
				console = true;
				break;
			case "-f":
			case "-file":
				i ++ ;
				file = new File(args[i]);
			}
		}
	}
	
}
