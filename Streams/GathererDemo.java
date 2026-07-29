import java.util.Comparator;
import java.util.HashSet;
import java.util.SequencedSet;
import java.util.Set;
import java.util.TreeSet;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import java.util.stream.Gatherer;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

// Ref: https://dev.java/learn/api/streams/gatherers
public class GathererDemo {
    public static void main(String[] args) {
	{
	    System.out.println("---- Simple Gatherer using Integrator ----");
	    Gatherer.Integrator<Void, String, String> integrator = (_, element, downstream) -> downstream.push(element);
	    Gatherer<String, ?, String> identityGatherer = Gatherer.of(integrator);

	    var result = Stream.of("one", "two", "three")
		.gather(identityGatherer)
		.toList();

	    System.out.println("result: " + result);
	}

	{
	    System.out.println("---- Simple Gatherer with Mapper ----");
	    Function<String, String> mapper = String::toUpperCase;
	    var mappingGatherer = mapping(mapper);
	    var result = Stream.of("one", "two", "three")
		.gather(mappingGatherer)
		.toList();
	    System.out.println("result: " + result);
	}

	{
	    System.out.println("---- Simple Gatherer with Filter ----");
	    Predicate<String> filter = s -> s.length() == 3;
	    var filteringGatherer = filtering(filter);
	    var result = Stream.of("one", "two", "three")
		.gather(filteringGatherer)
		.toList();
	    System.out.println("result: " + result);
	}

	{
	    System.out.println("---- Simple Gtherer with Flat Mapper ----");
	    Function<String, Stream<String>> flatMapper = s -> s.chars().mapToObj(Character::toString);
	    Gatherer<String, ?, String> mappingGatherer = flatMapping(flatMapper);
	    var result = Stream.of("one", "two", "three")
		.gather(mappingGatherer)
		.toList();
	    System.out.println("result: " + result);
	}

	{
	    System.out.println("---- dropWhile using gatherer ----");
	    Predicate<Integer> predicate = x -> x < 4;
	    var result = Stream.of(1, 2, 3, 4, 5, 6, 7, 4, 3, 1)
		.gather(dropWhile(predicate))
		.toList();
	    System.out.println("result: " + result);
	}

	{
	    System.out.println("---- distinct using gatherer ----");
	    var result = Stream.of(1, 2, 1, 7, 4, 4, 2, 5, 3)
		.gather(distinct())
		.toList();
	    System.out.println("result: " + result);
	}

	{
	    System.out.println("---- limit using gatherer ----");
	    var result = Stream.of(1, 2, 3, 4, 5, 6)
		.gather(limit(3))
		.toList();
	    System.out.println("result: " + result);
	}

	{
	    System.out.println("---- Finisher using gatherer ----");
	    var result = Stream.of("one", "two", "three", "four")
		.gather(mapThenFinish(String::toUpperCase, () -> "DONE"))
		.toList();
	    System.out.println("result: " + result);
	}

	{
	    System.out.println("---- sort+dictinct using gatherer ----");
	    var result = Stream.of("one", "two", "three", "four")
		.gather(sortAndDistinct(String::compareTo))
		.toList();
	    System.out.println("result: " + result);
	}

	{
	    System.out.println("--- sort+distince using parallel gatherer ----");
	    var result = Stream.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
		.gather(sortAndDistinctParallel(String::compareTo))
		.toList();
	    System.out.println("result: " + result);
	}

	{
	    System.out.println("---- Folding ----");
	    var fold = Gatherers.<String, String>fold(() -> "{ ", (str, element) -> str + element);
	    var finisher = Gatherer.<String, String>ofSequential((_, element, downstream) -> {
		    element += " }";
		    return downstream.push(element);
		});
	    var result = Stream.of(1, 2, 3, 4)
		.map(Object::toString)
		.gather(fold.andThen(finisher))
		.findFirst().orElseThrow();
	    System.out.println("result: " + result);
	}	    
    }

    static <E, R> Gatherer<E, ?, R> mapping(Function<E, R> mapper) {
       	Gatherer.Integrator<Void, E, R> integrator = (_, element, downstream) -> downstream.push(mapper.apply(element));
       	Gatherer<E, ?, R> gatherer = Gatherer.of(integrator);
       	return gatherer;
    }

    static <E> Gatherer<E, ?, E> filtering(Predicate<E> filter) {
       	Gatherer.Integrator<Void, E, E> integrator = (_, element, downstream) -> {
       	    if (filter.test(element)) {
       		return downstream.push(element);
       	    } else {
       		return true;
       	    }
       	};
       	return Gatherer.of(integrator);
    }

    static <E, R> Gatherer<E, ?, R> flatMapping(Function<E, Stream<R>> flatMapper) {
	Gatherer.Integrator<Void, E, R> integrator = (_, element, downstream) -> {
	    try (var flatMapped = flatMapper.apply(element)) {
		flatMapped.sequential().forEach(downstream::push);
	    }
	    return true;
	};
	return Gatherer.of(integrator);
    }

    static <E> Gatherer<E, ?, E> dropWhile(Predicate<E> predicate) {
	class Box {
	    boolean open = false;
	}

	Supplier<Box> initializer = Box::new;
	Gatherer.Integrator<Box, E, E> integrator = (box, element, downstream) -> {
	    if (!box.open && !predicate.test (element)) {
		box.open = true;
	    }
	    if (box.open) {
		downstream.push(element);
	    }
	    return true;
	};
	return Gatherer.ofSequential(initializer, integrator);
    }

    static <E> Gatherer<E, ?, E> distinct() {
	Gatherer.Integrator<Set<E>, E, E> integrator = (seen, element, downstream) -> {
	    if (!seen.contains(element)) {
		seen.add(element);
		return downstream.push(element);
	    }
	    return true;
	};
	return Gatherer.ofSequential(HashSet::new, integrator);
    }

    static <E> Gatherer<E, ?, E> limit(long maxNum) {
	class Box {
	    long counter = 0L;
	}
	Gatherer.Integrator<Box, E, E> integrator = (box, element, downstream) -> {
	    if (box.counter < maxNum) {
		++box.counter;
		return downstream.push(element);
	    } else {
		return false;
	    }
	};
	return Gatherer.ofSequential(Box::new, integrator);
    }

    static <E, R> Gatherer<E, ?, R> mapThenFinish(Function<E, R> mapper, Supplier<R> finishSupplier) {
	Gatherer.Integrator<Void, E, R> integrator = (_, element, downstream) -> {
	    return downstream.push(mapper.apply(element));
	};
	BiConsumer<Void, Gatherer.Downstream<? super R>> finisher = (_, downstream) -> downstream.push(finishSupplier.get());
	Gatherer<E, ?, R> gatherer = Gatherer.of(integrator, finisher);
	return gatherer;
    }

    static <E> Gatherer<E, ?, E> sortAndDistinct(Comparator<E> comparator) {
	Supplier<SequencedSet<E>> initializer = () -> new TreeSet<>(comparator);
	Gatherer.Integrator<SequencedSet<E>, E, E> integrator = (seen, element, _) -> {
	    seen.add(element);
	    return true;
	};
	BiConsumer<SequencedSet<E>, Gatherer.Downstream<? super E>> finisher = (elements, downstream) -> {
	    elements.stream()
	    .takeWhile(_ -> !downstream.isRejecting())
	    .forEach(downstream::push);
	};
	Gatherer<E, SequencedSet<E>, E> gatherer = Gatherer.ofSequential(initializer, integrator, finisher);
	return gatherer;
    }

    static <E> Gatherer<E, ?, E> sortAndDistinctParallel(Comparator<E> comparator) {
	Supplier<SequencedSet<E>> initializer = () -> new TreeSet<>(comparator);
	Gatherer.Integrator<SequencedSet<E>, E, E> integrator = (set, element, _) -> {
	    set.add(element);
	    return true;
	};
	BinaryOperator<SequencedSet<E>> combiner = (s1, s2) -> {
	    s1.addAll(s2);
	    return s1;
	};
	BiConsumer<SequencedSet<E>, Gatherer.Downstream<? super E>> finisher = (elements, downstream) -> {
	    elements.stream()
	    .allMatch(downstream::push);
	};

	return Gatherer.of(initializer, integrator, combiner, finisher);
    }

}
