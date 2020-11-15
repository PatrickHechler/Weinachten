package de.hechler.patrick.objects;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class BigIterTest {
	
	private Integer[][] backtrackListSmall = {
			{1, 2, 3},
			{4},
			{5, 6}
	};
	

	private Integer[][] backtrackListMedium = {
			{1, 2, 3},
			{4, 5, 6, 7},
			{8, 9},
			{10, 11, 12, 13, 14, 15}
	};
	
	private String[][] backtrackListLarge = {
			{"a", "b", "c", "d", "e", "f", "g"},
			{"h", "i", "j", "k", "l", "m", "n"},
			{"o", "p", "q", "r", "s", "t", "u"},
			{"v", "w", "x", "y", "z", "ä", "ö"},
			{"a", "b", "c", "d", "e", "f", "g"},
			{"h", "i", "j", "k", "l", "m", "n"},
			{"o", "p", "q", "r", "s", "t", "u"},
	};
	
	private String[][] backtrackListXLarge = {
			{"a", "b", "c", "d", "e", "f", "g"},
			{"h", "i", "j", "k", "l", "m", "n"},
			{"o", "p", "q", "r", "s", "t", "u"},
			{"v", "w", "x", "y", "z", "ä", "ö"},
			{"a", "b", "c", "d", "e", "f", "g"},
			{"h", "i", "j", "k", "l", "m", "n"},
			{"o", "p", "q", "r", "s", "t", "u"},
			{"v", "w", "x", "y", "z", "ä", "ö"},
			{"a", "b", "c", "d", "e", "f", "g"},
			{"h", "i", "j", "k", "l", "m", "n"},
			{"o", "p", "q", "r", "s", "t", "u"},
			{"o", "p"},
	};
	

	@Test
	void testBigIterSmall() {
		System.out.println("SMALL");
		List<List<Integer>> bl1 = array2List(backtrackListSmall); 
		BigIter<Integer> bigiter = BigIter.create(bl1);
		while (bigiter.hasNext()) {
			List<Integer> currentPermutation = bigiter.next();
			System.out.println(currentPermutation);
		}
	}


	@Test
	void testBigIterMedium() {
		System.out.println("MEDIUM");
		List<List<Integer>> bl1 = array2List(backtrackListMedium); 
		BigIter<Integer> bigiter = BigIter.create(bl1);
		while (bigiter.hasNext()) {
			List<Integer> currentPermutation = bigiter.next();
			System.out.println(currentPermutation);
		}
	}	
	
	@Test
	void testBigIterLarge() {
		int cnt = 0;
		long startTime = System.currentTimeMillis();
		List<List<String>> bl1 = array2List(backtrackListLarge); 
		BigIter<String> bigiter = BigIter.create(bl1);
		while (bigiter.hasNext()) {
			bigiter.next();
			cnt += 1;
		}
		long dauer = System.currentTimeMillis() - startTime;
		System.out.println(cnt + " Kombination in " + (0.001*dauer)+" Sekunden");
		assertTrue(cnt > 0);
	}

	
	@Test
	@Disabled
	void testBigIterXLarge() {
		long cnt = 0;
		long startTime = System.currentTimeMillis();
		List<List<String>> bl1 = array2List(backtrackListXLarge); 
		BigIter<String> bigiter = BigIter.create(bl1);
		while (bigiter.hasNext()) {
			bigiter.next();
			cnt += 1;
		}
		long dauer = System.currentTimeMillis() - startTime;
		System.out.println(cnt + " Kombination in " + (0.001*dauer)+" Sekunden");
		assertTrue(cnt > 0);
	}

	private <T> List<List<T>> array2List(T[][] deepArray) {
		List<List<T>> result = new ArrayList<>();
		for (T[] alternatives: deepArray) {
			result.add(Arrays.asList(alternatives));
		}
		return result;
	}

}
