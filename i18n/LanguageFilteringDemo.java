import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class LanguageFilteringDemo {
  public static void main (String[] args) {
    Collection<Locale> availableLocales = List.of (
        Locale.forLanguageTag("en-US"),
        Locale.forLanguageTag("ja"),
        Locale.forLanguageTag("zh-cmn-Hans-CN"),
        Locale.forLanguageTag("en-GB")
    );

    String ranges = "en-US;q=1.0,en-GB;q=0.5,fr-FR;q=0.0";

    List<Locale.LanguageRange> priorityRanges = 
      Locale.LanguageRange.parse (ranges);

    Collection<Locale> matchedLocales = Locale.filter (
        priorityRanges,
        availableLocales
    );

    for (Locale matchedLocale: matchedLocales) {
      System.out.println (matchedLocale + " - " + matchedLocale.getDisplayName());
    }
  }
}

