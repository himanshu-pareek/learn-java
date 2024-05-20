import java.util.Locale;

public class LanguageRangeDemo {
  public static void main (String[] args) {
    // Create Locale from TAG
    Locale locale = Locale.forLanguageTag ("en-US");

    // Define some language range objects
    Locale.LanguageRange range1 = new Locale.LanguageRange (
        "en-US",
        Locale.LanguageRange.MAX_WEIGHT
    );
    Locale.LanguageRange range2 = new Locale.LanguageRange("en-GB*",0.5);
    Locale.LanguageRange range3 = new Locale.LanguageRange("fr-FR",Locale.LanguageRange.MIN_WEIGHT);
    System.out.println (range1);
    System.out.println (range2);
    System.out.println (range3);
  }
}


