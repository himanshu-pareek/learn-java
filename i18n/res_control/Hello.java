import java.util.*;

public class Hello {

  private static ResourceBundle.Control rbControl = new ResourceBundle.Control() {
    @Override
    public List<Locale> getCandidateLocales (
        String baseName,
        Locale locale
    ) {
      if (baseName == null) {
        throw new NullPointerException();
      }

      if (locale.equals (new Locale ("zh", "HK"))) {
        return List.of (
            locale,
            Locale.TAIWAN,
            // no Locale.CHINESE here
            Locale.ROOT
        );
      }

      if (locale.equals (Locale.TAIWAN)) {
        return List.of (
            locale,
            // no Locale.CHINESE here
            Locale.ROOT
        );
      }

      return super.getCandidateLocales (baseName, locale);
    }
  };


  private static void test (Locale locale) {
    var bundle = ResourceBundle.getBundle("RBControl", locale, rbControl);
    System.out.printf ("\n\n ------- %s --------- \n\n", locale.getDisplayName());
    for (String key : List.of ("region", "language")) {
      String value = bundle.getString (key);
      System.out.printf ("\t%s : %s\n", key, value);
    }
  }

  public static void main(String[] args) {

    test (Locale.CHINA);
    test (new Locale ("zh", "HK"));
    test (Locale.TAIWAN);
    test (Locale.CANADA);
  }
}

