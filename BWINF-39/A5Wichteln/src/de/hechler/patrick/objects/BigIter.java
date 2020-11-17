package de.hechler.patrick.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BigIter <E> implements Iterator <List <E>> {
	
	private List <List <E>> inhalt;
	private List <E> erg;
	private Iterator <E>[] iters;
	private final long cntBorder;
	private long cnt;
	
	
	
	private BigIter(List <List <E>> inahlt, long cntBorder, Iterator <E>[] iters) {
		this.inhalt = inahlt;
		this.cntBorder = cntBorder;
		this.iters = iters;
	}
	
	@SuppressWarnings("unchecked")
	public static <E> BigIter <E> create(List <List <E>> inhalt) {
		Iterator <E>[] iters = new Iterator[inhalt.size()];
		long cntBorder = 1;
		for (int i = 0; i < inhalt.size(); i ++ ) {
			List <E> diese = inhalt.get(i);
			cntBorder *= diese.size();
			iters[i] = diese.iterator();
		}
		if (inhalt.size() == 0) {
			cntBorder = 0;
		}
		BigIter <E> erg = new BigIter <E>(inhalt, cntBorder, iters);
		if (cntBorder != 0) {
			erg.erg = new ArrayList <E>(inhalt.size());
			erg.erg.add(null);
			for (int i = 1; i < iters.length; i ++ ) {
				erg.erg.add(iters[i].next());
			}
		}
		return erg;
	}
	
	
	
	@Override
	public boolean hasNext() {
		return cnt < cntBorder;
	}
	
	@Override
	public List <E> next() {
		int i = 0;
		cnt ++ ;
		while (true) {
			if (iters[i].hasNext()) {
				erg.set(i, iters[i].next());
				return erg;
			}
			iters[i] = inhalt.get(i).iterator();
			erg.set(i, iters[i].next());
			i ++ ;
		}
	}
	
}
