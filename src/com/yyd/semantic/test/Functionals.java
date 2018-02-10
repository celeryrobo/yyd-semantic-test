package com.yyd.semantic.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@FunctionalInterface
interface Function<P, R> {
	R invoke(P arg);
}

public class Functionals {
	// map
	public static <P, R> List<R> map(Function<P, R> function, P[] args) {
		return map(function, Arrays.asList(args));
	}

	public static <P, R> List<R> map(Function<P, R> function, Iterable<P> args) {
		return map(function, args.iterator());
	}

	public static <P, R> List<R> map(Function<P, R> function, Iterator<P> args) {
		List<R> list = new ArrayList<>();
		while (args.hasNext()) {
			R r = function.invoke(args.next());
			list.add(r);
		}
		return list;
	}

	// filter
	public static <T> List<T> filter(Function<T, Boolean> function, T[] args) {
		return filter(function, Arrays.asList(args));
	}

	public static <T> List<T> filter(Function<T, Boolean> function, Iterable<T> args) {
		return filter(function, args.iterator());
	}

	public static <T> List<T> filter(Function<T, Boolean> function, Iterator<T> args) {
		List<T> list = new ArrayList<>();
		while (args.hasNext()) {
			T r = args.next();
			Boolean b = function.invoke(r);
			if (b) {
				list.add(r);
			}
		}
		return list;
	}

	// max
	public static <T extends Comparable<T>> T max(T[] args) {
		return max(Arrays.asList(args));
	}

	public static <T extends Comparable<T>> T max(Iterable<T> args) {
		return max(args.iterator());
	}

	public static <T extends Comparable<T>> T max(Iterator<T> args) {
		T result = null;
		while (args.hasNext()) {
			T arg = args.next();
			if (result == null) {
				result = arg;
			} else {
				if (result.compareTo(arg) < 0) {
					result = arg;
				}
			}
		}
		return result;
	}

	// min
	public static <T extends Comparable<T>> T min(T[] args) {
		return min(Arrays.asList(args));
	}

	public static <T extends Comparable<T>> T min(Iterable<T> args) {
		return min(args.iterator());
	}

	public static <T extends Comparable<T>> T min(Iterator<T> args) {
		T result = null;
		while (args.hasNext()) {
			T arg = args.next();
			if (result == null) {
				result = arg;
			} else {
				if (result.compareTo(arg) > 0) {
					result = arg;
				}
			}
		}
		return result;
	}
}
