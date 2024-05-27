import java.text.Collator;
import java.text.CollationKey;

import java.util.Arrays;
import java.util.Locale;

public class CollationKeysDemo {
  public static void main (String[] args) {
    Collator enUSCollator = Collator.getInstance (new Locale ("en", "US"));

    String[] words = {
      "peach",
      "apricot",
      "grape",
      "lemon"
    };

    CollationKey[] collationKeys = new CollationKey[words.length];

    for (int i = 0; i < collationKeys.length; i++) {
      collationKeys[i] = enUSCollator.getCollationKey (words[i]);
    }

    sortArray (collationKeys);
    printArray (collationKeys);
  }

  private static void sortArray (CollationKey[] keys) {
    Arrays.sort (keys);
  }

  private static void printArray (CollationKey[] keys) {
    for (var key: keys) {
      System.out.println (key.getSourceString());
    }
  }
}

