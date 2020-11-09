package de.hechler.patrick.objects;

public abstract class LoadableVerteilungsGenerator extends VerteilungsGenerator {
	
	protected LoadableVerteilungsGenerator(Klasse klasse, boolean richtigeStartVerteilung) {
		super(klasse, richtigeStartVerteilung);
	}
	
	public abstract void load();
	
	@Override
	public Verteilung besteVerbleibende() {
		load();
		return super.besteVerbleibende();
	}
	
}
