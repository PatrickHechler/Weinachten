package de.hechler.patrick.haupt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import de.hechler.patrick.objects.Bewertung;
import de.hechler.patrick.objects.GeschenkVerteiler;
import de.hechler.patrick.objects.UnmodifiableKlasse;
import de.hechler.patrick.objects.Verteilung;
import de.hechler.patrick.objects.geschenkverteilerimpl.SuperGeschenkVerteiler;

public class Haupt {
	
	private static GeschenkVerteiler verteiler;
	private static UnmodifiableKlasse bewerter;
	private static List <PrintStream> savers;
	
	public static void main(String[] args) {
		/*
		 * haupt(new String[] {"-f", "./beispieldaten/wichteln6.txt", "-s", "./solved/solve6.txt", "-?", "-help" });
		 * 
		 * }
		 * 
		 * public static void haupt(String[] args) {
		 */
		try {
			config(args);
		} catch (FileNotFoundException e) {
			System.err.println("The file could not be found!");
			e.printStackTrace();
			System.exit(1);
		}
		if (verteiler == null) {
			return;
		}
		int size = bewerter.size();
		long start = System.currentTimeMillis();
		Verteilung ver = verteiler.beste();
		long fertig = System.currentTimeMillis();
		String time;
		long ms = fertig - start;
		int s = (int) (0.001 * ms);
		if (s > 0) {
			time = "benötigte Zeit: " + s + "s";
		} else {
			time = "benötigte Zeit: " + ms + "ms";
		}
		Bewertung bewertung = bewerter.bewerte(ver);
		for (PrintStream save : savers) {
			save.println(time);
			save.println("Bewertung: " + bewertung);
			save.println("anzahl Teilnehmer: " + size);
			ver.print(save);
		}
	}
	
	private static void config(String[] args) throws FileNotFoundException {
		boolean console = false;
		File file = null;
		InputStream in;
		savers = new ArrayList <>();
		savers.add(System.out);
		for (int i = 0; i < args.length; i ++ ) {
			switch (args[i].toLowerCase()) {
			case "-c":
			case "-console":
				console = true;
				if (file != null) {
					System.err.println("wrong args, can't read from the console and a File!");
					help(System.err);
					System.exit(1);
				}
				break;
			case "-f":
			case "-file":
				if (file != null) {
					System.err.println("wrong args, can't read from two Files!");
					help(System.err);
					System.exit(1);
				}
				i ++ ;
				if (args.length < i) {
					System.err.println("wrong args, there are not enugh elements in the Array!");
					help(System.err);
					System.exit(1);
				}
				file = new File(args[i]);
				break;
			case "-s":
			case "-save":
				i ++ ;
				if (args.length < i) {
					System.err.println("wrong args, there are not enugh elements in the Array!");
					help(System.err);
					System.exit(1);
				}
				savers.add(new PrintStream(new File(args[i])));
				break;
			case "-q":
			case "-quiet":
				if (savers.get(0) == System.out) {
					savers.remove(0);
				} else {
					System.err.println("I can't be duoble quiet!");
					help(System.err);
					System.exit(1);
				}
				break;
			case "-?":
			case "-help":
				help(System.out);
				break;
			default:
				System.err.println("unknown args!");
				help(System.err);
				System.exit(1);
			}
		}
		if (args.length == 0) {
			console = true;
		}
		if (console) {
			System.out.println("Gebe nun die Klasse ein:");
			in = System.in;
		} else if (file != null) {
			in = new FileInputStream(file);
		} else {
			verteiler = null;
			bewerter = null;
			return;
		}
		bewerter = UnmodifiableKlasse.lade(in);
		verteiler = new SuperGeschenkVerteiler(bewerter.clone());
	}
	
	private static void help(PrintStream print) {
		print.println("-c");
		print.println("-console");
		print.println("                  Lade die Klasse aus der Konsole");
		print.println("-f <filepath>");
		print.println("-file <filepath>");
		print.println("                  Lade die Klasse aus der Datei mit dem Pfad <filepath>");
		print.println("-s <filepath>");
		print.println("-save <filepath>");
		print.println("                  Speichere das Ergebnis in der Dateil mit dem Pfad <filepath>");
		print.println("-q");
		print.println("-quiet");
		print.println("                  Gebe das Ergebnis nicht mit der Konsole aus");
		print.println("-?");
		print.println("-help");
		print.println("                  Gebe dies aus");
	}
	
}
