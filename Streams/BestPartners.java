import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.util.stream.Collectors;
import java.util.function.Function;

record Author(String name) implements Comparable<Author> {
    public int compareTo(Author other) {
        return this.name.compareTo(other.name);
    }
}

record Article (
		String title,
		int inceptionYear,
		List<Author> authors
) {}

record PairOfAuthors(Author first, Author second) {
    public static Optional<PairOfAuthors> of(
	Author first,
	Author second
    ) {
        if (first.compareTo(second) > 0) {
            return Optional.of(new PairOfAuthors(first, second));
        } else {
            return Optional.empty();
        }
    }
}

public class BestPartners {
    public static void main() {
	// TODO: Fill the articles list
	List<Article> articles = List.of(new Article("A1", 2026, List.of(new Author("a"), new Author("b"), new Author("c"))), new Article("A2", 2025, List.of(new Author("c"), new Author("d"), new Author("b"))));

	Map<PairOfAuthors, Integer> F = new HashMap<>();
	var res = articles.stream()
	    .map(Article::authors)
	    .flatMap(authors -> {
		    //	for (Article article: articles) {
		    //	    var authors = article.authors();
		    List<PairOfAuthors> authorPairs = new ArrayList<>();
		    for (var a1 : authors) {
			for (var a2 : authors) {
			    var x = PairOfAuthors.of(a1, a2);
			    if (x.isPresent()) {
				var pair = x.get();
				//				F.put(pair, F.getOrDefault(pair, 0) + 1);
				authorPairs.add(pair);
			    }
			}
		    }
		    return authorPairs.stream();
		})
	    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

	System.out.println(F);
	System.out.println(res);
    }
}

