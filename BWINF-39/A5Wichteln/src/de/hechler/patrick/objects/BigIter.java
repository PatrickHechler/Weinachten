package de.hechler.patrick.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BigIter <E> implements Iterator <List <E>> {
	
	private List <List <E>> inahlt;
	private List <E> erg;
	private Iterator <E>[] iters;
	private final long cntBorder;
	private long cnt;
	
	
	
	@SuppressWarnings("unchecked")
	public BigIter(List <List <E>> inahlt) {
		this.inahlt = inahlt;
		iters = new Iterator[inahlt.size()];
		long zw = 1;
		for (int i = 0; i < inahlt.size(); i ++ ) {
			zw *= inahlt.get(i).size();
			iters[i] = inahlt.get(i).iterator();
		}
		cntBorder = zw;
		if (cntBorder != 0) {
			erg = new ArrayList <E>(inahlt.size());
			erg.add(null);
			for (int i = 1; i < iters.length; i ++ ) {
				erg.add(iters[i].next());
			}
		}
	}
	
	
	
	@Override
	public boolean hasNext() {
		return cnt < cntBorder;
	}
	
	@Override
	public List <E> next() {
		int i = 0;
		cnt ++;
		while (true) {
			if (iters[i].hasNext()) {
				erg.set(i, iters[i].next());
				return erg;
			}
			iters[i] = inahlt.get(i).iterator();
			erg.set(i, iters[i].next());
			i ++;
		}
	}
	
}
