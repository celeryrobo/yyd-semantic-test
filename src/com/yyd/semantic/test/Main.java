package com.yyd.semantic.test;

import java.util.LinkedList;
import java.util.List;

import static com.yyd.semantic.test.Functionals.map;
import static com.yyd.semantic.test.Functionals.filter;
import static com.yyd.semantic.test.Functionals.max;
import static com.yyd.semantic.test.Functionals.min;

public class Main {

	public static void main(String[] args) throws Exception {
		List<Integer> list = new LinkedList<>();
		list.add(0);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		List<Integer> rs = map((e) -> e << 1, list);
		System.out.println(rs);
		Integer[] ss = { 1, 2, 3 };
		rs = map((e) -> e * 10, ss);
		System.out.println(rs);
		rs = map((e) -> e * 100, filter((e) -> e >= 4, list));
		System.out.println(rs);
		System.out.println(max(rs));
		System.out.println(min(rs));
	}
}
