import java.util.*;
import java.text.*;

public class AvailableLocales {
  public static void main (String[] args) {
    System.out.println ("Available locales for DateFormat");
    Locale[] availableLocales = DateFormat.getAvailableLocales();
    for (Locale availableLocale: availableLocales) {
      System.out.println (availableLocale.getDisplayName());
    }
  }
}

