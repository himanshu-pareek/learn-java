void main() {
	{
		Stream<Integer> ints = Stream.of(1, 2, 3, 4);
		int sum = ints.reduce(0, (a, b) -> a + b);
		IO.println("sum = " + sum);
	}

	{
		Stream<Integer> ints = Stream.of(1, 2, 3, 4);
		int sum = ints.reduce(10, (a, b) -> a + b);
		IO.println("sum = " + sum);
	}

	{
		Stream<Integer> ints = Stream.of(1, 2, 3, 4);
		int sum = ints.parallel().reduce(10, (a, b) -> a + b);
		IO.println("sum = " + sum);
	}

	{
		Stream<Integer> ints = Stream.of(2, 8, 1, 5, 3);
		Optional<Integer> optional = ints.reduce((a, b) -> {
			IO.println("Working on " + a + " and " + b);
			return a > b ? a : b;
		});

		if (optional.isPresent()) {
			IO.println("result = " + optional.orElseThrow());
		} else {
			IO.println("No result could be computed");
		}
	}

	{
		Stream<String> strings = Stream.of("one", "two", "three", "four", "five");

		BinaryOperator<Integer> combiner = (len1, len2) -> len1 + len2;

		BiFunction<Integer, String, Integer> accumulator =
			(partialReduction, element) -> partialReduction + element.length();

		int result = strings.reduce(0, accumulator, combiner);
		IO.println("sum = " + result);
	}
}

