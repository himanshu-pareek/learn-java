import java.text.Collator;
import java.text.RuleBasedCollator;
import java.text.ParseException;

public class CustomCollationRule {
  public static void main (String[] args) {
    System.out.println ("Hello to CustomCollationRule...");

    String englishRules = (
        "< a,A < b,B < c,C < d,D < e,E < f,F " +
        "< g,G < h,H < i,I < j,J < k,K < l,L " +
        "< m,M < n,N < o,O < p,P < q,Q < r,R " +
        "< s,S < t,T < u,U < v,V < w,W < x,X " +
        "< y,Y < z,Z"
    );

    String smallNTilde = "\u00F1";
    String capitalNTilde = "\u00D1";

    String traditionalSpanishRules = (
        "< a,A < b,B < c,C " +
        "< ch, cH, Ch, CH " +
        "< d,D < e,E < f,F " +
        "< g,G < h,H < i,I < j,J < k,K < l,L " +
        "< ll, lL, Ll, LL " +
        "< m,M < n,N " +
        "< " + smallNTilde + "," + capitalNTilde + " " +
        "< o,O < p,P < q,Q < r,R " +
        "< s,S < t,T < u,U < v,V < w,W < x,X " +
        "< y,Y < z,Z");

    String[] words = {
      "llama",
      "curioso",
      "luz",
      "chalina"
    };


    try {

      Collator enCollator = new RuleBasedCollator (englishRules);
      Collator spCollator = new RuleBasedCollator (traditionalSpanishRules);

      sortStrings (enCollator, words);
      printStrings (words);

      System.out.println ();

      sortStrings (spCollator, words);
      printStrings (words);
    } catch (ParseException exception) {
      System.out.println (exception.getMessage());
    }
  }

  private static void sortStrings (Collator collator, String[] words) {
    String tmp;
    for (int i = 0; i < words.length; i++) {
      for (int j = i + 1; j < words.length; j++) {
        if (collator.compare(words[i], words[j]) > 0) {
          tmp = words[i];
          words[i] = words[j];
          words[j] = tmp;
        }
      }
    }
  }

  private static void printStrings (String[] words) {
    for (String word: words) {
      System.out.println (word);
    }
  }
}

