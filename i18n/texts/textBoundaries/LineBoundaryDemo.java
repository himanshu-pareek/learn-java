import java.util.Locale;

import java.text.BreakIterator;

public class LineBoundaryDemo {
  public static void main (String[] args) {
    Locale locale = new Locale ("en", "US");

    BreakIterator lineIterator = BreakIterator.getLineInstance (locale);

    String text = "She said, \"Hello there,\" and then " +
    "went on down the street. When she stopped " +
    "to look at the fur coats in a shop " +
    "window, her dog growled. \"Sorry Jake,\" " +
    "she said. \"I didn't know you would take " +
    "it personally.\"";

    formatLines (text, 30, lineIterator);
    
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

  private static void formatLines (
      String text,
      int maxLength,
      BreakIterator iterator
  ) {

    iterator.setText (text);

    int start = iterator.first();
    int end = iterator.next();

    int lineLength = 0;

    while (end != BreakIterator.DONE) {

      String word = text.substring (start, end);

      lineLength += word.length();

      if (lineLength >= maxLength) {

        System.out.println ();
        lineLength = word.length();
      }

      System.out.print (word);

      start = end;
      end = iterator.next();
    }
  }

}

