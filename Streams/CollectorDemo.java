import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

import java.util.stream.Collector;

public class CollectorDemo {
    public static void main(String[] args) {
	{
	    System.out.println("---------- toList() ----------");
	    var squares = List.of(1, 2, 3, 10)
		.stream()
		.map(x -> x * x)
		.toList();
	    System.out.println("Squares: " + squares);
	}

	{
	    System.out.println("------- collect -------------");
	    var result = List.of(1, 2, 3, 10)
		.stream()
		.map(x -> x * x)
		.collect(() -> new StringBuilder(), (res, ele) -> {
			if (!res.isEmpty()) {
			    res.append(" - ");
			}
			res.append("" + ele);
		    }, (r1, r2) -> r1.append(r2));
	    System.out.println("Result: " + result);
	}

	{
	    System.out.println("-------- Histogram of len ------");
	    Collection<String> strings =
		List.of("one", "two", "three", "four", 
			"five", "six", "seven", "eight", 
			"nine", "ten", "eleven", "twelve");
	    Supplier<Map<Integer, Integer>> initializer = () -> new HashMap<>();
	    BiConsumer<Map<Integer, Integer>, Integer> accumulator = (freq, wordLength) -> freq.put(wordLength, freq.getOrDefault(wordLength, 0) + 1);
	    BiConsumer<Map<Integer, Integer>, Map<Integer, Integer>> combiner = (f1, f2) -> {
		for (Map.Entry<Integer, Integer> entry: f2.entrySet()) {
		    int key = entry.getKey();
		    int value = entry.getValue();
		    f1.put(key, f1.getOrDefault(key, 0) + value);
		}
	    };
	    System.out.println(strings.stream()
		.map(String::length)
			       .collect(initializer, accumulator, combiner));
	}

	{
	    System.out.println("----- MyCollector Test ----");
	    var result = List.of(1, 2, 3)
		.stream()
		.map(x -> x + x / 2)
		.collect(new MyCollector<>());
	    System.out.println("Result: " + result);
	    System.out.println("Is result a collection? " + (result instanceof Collection));
	    System.out.println("Is result an ArrayList? " + (result instanceof ArrayList));
	}
	    
    }
}

class MyCollector<T> implements Collector<T, Collection<T>, Collection<T>> {
    @Override
    public Set<Characteristics> characteristics() {
	return Set.of(Characteristics.CONCURRENT, Characteristics.UNORDERED);
    }

    @Override
    public Function<Collection<T>, Collection<T>> finisher() {
	return Function.identity();
    }

    @Override
    public BinaryOperator<Collection<T>> combiner() {
	return (c1, c2) -> {
	    c1.addAll(c2);
	    return c1;
	};
    }

    @Override
    public BiConsumer<Collection<T>, T> accumulator() {
	return (result, element) -> result.add(element);
    }

    @Override
    public Supplier<Collection<T>> supplier() {
	return () -> new ArrayList<>();
    }
}

class 

