package de.hechler.patrick.objects;

public abstract class LoadableVerteilungsGenerator extends VerteilungsGenerator {
	
	protected boolean loaded;
	
	
	
	protected LoadableVerteilungsGenerator(Klasse klasse, boolean richtigeStartVerteilung) {
		super(klasse, richtigeStartVerteilung);
	}
	
	public abstract void load();
	
	@Override
	public Verteilung besteVerbleibende() {
		if ( !loaded) {
			load();
			loaded = true;
		}
		return super.besteVerbleibende();
	}
	
}
