import java.util.Locale;

import java.text.BreakIterator;

public class CharacterBoundaryDemo {
  public static void main (String[] args) {
    BreakIterator characterIterator = BreakIterator.getCharacterInstance (new Locale ("ar", "SA"));

    String house = "\u0628" + "\u064e" + "\u064a" + "\u0652" + "\u067a" + "\u064f";

    listPositions (house, characterIterator);
  }

  private static void listPositions (String text, BreakIterator iterator) {

    iterator.setText (text);
    int boundary = iterator.first();

    while (boundary != BreakIterator.DONE) {
      System.out.println (boundary);
      boundary = iterator.next();
    }
  }

}

