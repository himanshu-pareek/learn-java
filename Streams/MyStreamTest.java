import java.util.*;

public class MyStreamTest {
    public static void main(String[] args) {
	new MyStreamTest().runAllTests();
    }

    private void runAllTests() {
	testSuccessfulStreamCreation();
	testForEach();
	testMapReturnsStream();
	testMapAndForEach();
	testFilterReturnsStream();
	testFilterAndForEach();
	testFilterAndMap();
	testToList();
	testStreamCanNotBeOperatedAgain();
    }

    private void testStreamCanNotBeOperatedAgain() {
	    var stream = MyStream.of(List.of(1, 2, 3, 4));
	    var as = stream.map(x -> x);
	    try {
		    stream.map(x -> x);
		    System.err.println("Stream can not be operated more than once");
		    assert false;
	    } catch (IllegalStateException e) {
		    assert true;
	    }
    }

    private void testToList() {
	    List<Integer> numbers = List.of(1, 2, 3, 4);
	    var doubles = MyStream.of(numbers)
		    .map(number -> number * 2)
		    .toList();
	    System.out.println("testToList :: doubles: " + doubles);
	    assert doubles != null;
	    assert doubles instanceof List;
	    assert doubles.size() == numbers.size();
	    for (int i = 0; i < numbers.size(); i++) {
		    assert doubles.get(i) == 2 * numbers.get(i);
	    }
    }

    private void testFilterAndMap() {
	    List<String> names = List.of("John", "Jane", "Bob", "August", "Abraham", "Jill");
	    List<String> result = new ArrayList<>();
	    MyStream.of(names)
		    .filter(name -> name.length() <= 4)
		    .map(name -> name.toUpperCase())
		    .forEach(result::add);
	    System.out.println("testFilterAndMap :: result: " + result);
	    assert result.size() == 4;
    }

    private void testFilterAndForEach() {
	    List<String> names = List.of("John", "Jane", "Bob", "August", "Abraham", "Jill");
	    List<String> result = new ArrayList<>();
	    MyStream.of(names)
		    .filter(name -> name.length() == 4)
		    .forEach(name -> result.add (name));
	    System.out.println("testFilterAndForEach :: result: " + result);
	    assert result.size() == 3;
    }

    private void testFilterReturnsStream() {
	List<Integer> numbers = List.of(1, 2, 3, 4);
	var as = MyStream.of(numbers).filter(number -> number % 2 == 0);
	assert as != null;
	assert as instanceof MyStream;
    }

    private void testMapAndForEach() {
	List<String> names = List.of("John", "Jane", "Bob", "August", "Abraham", "Jill");
	List<Integer> nameLens = new ArrayList<>();
	MyStream.of(names)
	    .map(name -> name.length())
	    .forEach(len -> nameLens.add(len));
	System.out.println("testMapAndForEach :: nameLens: " + nameLens);
	assert nameLens.size() == names.size();
	for (int i = 0; i < names.size(); i++) {
	    assert nameLens.get(i).equals(names.get(i).length());
	}
    }

    private void testMapReturnsStream() {
	List<Integer> numbers = List.of(1, 2, 3);
	var stream = MyStream.of(numbers);
	var anotherStream = stream.map(number -> 2 * number);
	assert anotherStream != null;
	assert anotherStream instanceof MyStream;
    }

    private void testForEach() {
	List<Integer> numbers = List.of(1, 2, 3, 4);
	var stream = MyStream.of(numbers);
	StringBuilder result = new StringBuilder();
	stream.forEach(number -> result.append(":" + number));
	System.out.println("testForEach :: result: " + result);
	assert result.toString().equals(":1:2:3:4");
    }

    private void testSuccessfulStreamCreation() {
	List<String> names = List.of("John Doe", "Jane Doe", "Bob Builder");
	var stream = MyStream.of(names);
	assert stream != null;
	assert stream instanceof MyStream;
    }
}
	
