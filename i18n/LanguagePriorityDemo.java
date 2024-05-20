import java.util.List;
import java.util.Locale;

public class LanguagePriorityDemo {
  public static void main (String[] args) {
    Locale locale = Locale.forLanguageTag("en-US");

    String ranges = "en-US;q=1.0,en-GB;q=0.5,fr-FR;q=0.0";

    List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse (ranges);

    System.out.println (languageRanges);
  }
}

