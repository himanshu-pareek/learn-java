import java.util.Locale;

import java.text.BreakIterator;

public class WordBoundaryDemo {
  public static void main (String[] args) {
    Locale locale = new Locale ("en", "US");

    BreakIterator wordIterator = BreakIterator.getWordInstance (locale);

    String text = "She stopped. She said, \"Hello, there.\" and then went on.";

    markBoundaries (text, wordIterator);
    extractWords (text, wordIterator);
  }

  private static void listPositions (String text, BreakIterator iterator) {

    iterator.setText (text);
    int boundary = iterator.first();

    while (boundary != BreakIterator.DONE) {
      System.out.println (boundary);
      boundary = iterator.next();
    }
  }

  private static void markBoundaries (String text, BreakIterator iterator) {
    StringBuffer markers = new StringBuffer();
    markers.setLength (text.length() + 1);
    for (int i = 0; i < markers.length(); i++) {
      markers.setCharAt (i, ' ');
    }

    iterator.setText (text);
    int boundary = iterator.first();

    while (boundary != BreakIterator.DONE) {
      markers.setCharAt (boundary, '^');
      boundary = iterator.next();
    }

    System.out.println (text);
    System.out.println (markers);
  }

  private static void extractWords (String text, BreakIterator iterator) {
    iterator.setText (text);

    int start = iterator.first();
    
    while (start != BreakIterator.DONE) {
      int end = iterator.next();
      if (end == BreakIterator.DONE) {
        break;
      }

      String word = text.substring (start, end);

      if (Character.isLetterOrDigit (word.charAt (0))) {
        System.out.println (word);
      }

      start = end;
    }
  }

}

