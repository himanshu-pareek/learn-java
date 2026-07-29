import java.nio.file.Path;
import java.nio.file.Files;

void main() {
    {
        record City(String name, int population) {
        }
        record Country(String name, List<City> cities) {
        }

        City newYork = new City("New York", 8_258);
        City losAngeles = new City("Los Angeles", 3_821);
        Country usa = new Country("USA", List.of(newYork, losAngeles));

        City london = new City("London", 8_866);
        City manchester = new City("Manchester", 568);
        Country uk = new Country("United Kingdom", List.of(london, manchester));

        City paris = new City("Paris", 2_103);
        City marseille = new City("Marseille", 877);
        Country france = new Country("France", List.of(paris, marseille));

        List<Country> countries = List.of(usa, uk, france);

        var populationSummary = countries.stream()
                .flatMap(country -> country.cities().stream())
                .mapToInt(City::population)
                .summaryStatistics();
        IO.println("City population summary: " + populationSummary);
    }

    {
        Function<String, Stream<Integer>> flatParser = s -> {
            try {
                return Stream.of(Integer.parseInt(s));
            } catch (NumberFormatException _) {
            }
            return Stream.empty();
        };

        List<String> strings = List.of("1", " ", "2", "3 ", "", "3");

        List<Integer> ints =
                strings.stream()
                        .flatMap(flatParser)
                        .toList();
        IO.println("ints = " + ints);
    }

    {
        List<String> strings = List.of("1", " ", "2", "3 ", "", "3");

        List<Integer> ints =
                strings.stream()
                        .<Integer>mapMulti(
                                (string, consumer) -> {
                                    try {
                                        consumer.accept(Integer.parseInt(string));
                                    } catch (NumberFormatException ignored) {
                                        // Uncomment the following if you need to replace all the
                                        // invalid numbers with -1
                                        // consumer.accept(-1);
                                    }
                                })
                        .toList();
        IO.println("ints = " + ints);
    }

    {
        List<Integer> ints = List.of(1, 4, 2, 1, 3, 3);

        List<Integer> distinctNumbers =
                ints.stream()
                        .distinct()
                        .toList();
        IO.println("distinct ints: " + distinctNumbers);
    }

    {
        var numbers = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");
        List<String> naturallySorted = numbers.stream()
                .sorted()
                .toList();
        IO.println("naturallySorted: " + naturallySorted);

        List<String> sortedByLength = numbers.stream()
                .sorted(Comparator.comparing(String::length))
                .toList();
        IO.println("Sorted by length: " + sortedByLength);
    }
    {
        var ints = IntStream.iterate(0, i -> i + 1)
                .map(i -> i /3)
                .distinct()
                .limit(5)
                .toArray();
        IO.println("ints = " + Arrays.toString(ints));
    }
    {
        // The following code with run forever, since sorted will wait for all
        // the elements (prerequisite of sorting)
//        var ints = IntStream.iterate(0, i -> i + 1)
//                .map(i -> i /3)
//                .sorted()
//                .limit(5)
//                .toArray();
//        IO.println("ints = " + Arrays.toString(ints));
    }
    {
        List<Integer> ints = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> result =
                ints.stream()
                        .skip(2)
                        .limit(5)
                        .toList();
        IO.println("result = " + result);
    }
    {
        List<Integer> unDropped = Stream.of(1, 2, 3, 4, 3, 2, 1)
                .dropWhile(x -> x < 4)
                .toList();
        IO.println("Un-dropped elements: " + unDropped);
    }
    {
        List<Integer> takenElements = Stream.of(1, 2, 3, 4, 3, 2, 1)
                .takeWhile(x -> x < 4)
                .toList();
        IO.println("Taken elements: " + takenElements);
    }
    {
        List<Integer> list0 = List.of(1, 2, 3);
        List<Integer> list1 = List.of(4, 5, 6);
        List<Integer> list2 = List.of(7, 8, 9);

        // 1st pattern: concat
        List<Integer> concat =
                Stream.concat(list0.stream(), list1.stream())
                        .toList();

        // 2nd pattern: flatMap
        List<Integer> flatMap =
                Stream.of(list0.stream(), list1.stream(), list2.stream())
                        .flatMap(Function.identity())
                        .toList();

        IO.println("concat  = " + concat);
        IO.println("flatMap = " + flatMap);
    }
    {
        // WARNING: Don't use `peek` in production code and don't use it to do some side-effects
        List<String> strings = List.of("one", "two", "three", "four");

        List<String> result =
                strings.stream()
                        .peek(s -> IO.println("Starting with = " + s))
                        .filter(s -> s.startsWith("t"))
                        .peek(s -> IO.println("Filtered = " + s))
                        .map(String::toUpperCase)
                        .peek(s -> IO.println("Mapped = " + s))
                        .toList();
        IO.println("result = " + result);
    }

    {
	    Iterator<Integer> iterator = new Iterator<>() {
		    private int index = 0;
		    public boolean hasNext() {
			    return index < 10;
		    }
		    public Integer next() {
			    return ++index;
		    }
	    };

	    long estimateSize = 10L;
	    int characteristics = 0;
	    Spliterator<Integer> spliterator = Spliterators.spliterator(iterator, estimateSize, characteristics);

	    boolean parallel = false;
	    Stream<Integer> stream = StreamSupport.stream(spliterator, parallel);

	    List<Integer> ints = stream.map(x -> x * 2).toList();
	    IO.println("ints = " + ints);
    }

    {
	    Stream<String> iterated = Stream.iterate("+", s -> s.length() <= 5, s -> s + "+");
	    iterated.forEach(IO::println);
    }

    {
	    // Print the number of left curly parenthesis ({) in this file
	    Path file = Path.of("StreamsDemo.java");
	    try (Stream<String> lines = Files.lines(file)) {
		    long count = lines.flatMap(line -> line.chars().mapToObj(Character::toString))
			    .filter(c -> c.equals("{"))
			    .count();
		    IO.println("Number of left curly paren ({): " + count);
	    } catch (IOException e) {
		    e.printStackTrace();
	    }
	}
}
