import java.util.*;

public class Hello {
  public static void main (String[] args) {
    System.out.println ("Hello...");

    Locale[] supportedLocales = {
      Locale.ENGLISH,
      new Locale("hi", "IN"),
      Locale.FRENCH
    };

    for (Locale locale: supportedLocales) {
      System.out.printf ("\n--------- %s ----------\n\n", locale.getDisplayName());
      ResourceBundle labels = ResourceBundle.getBundle("LabelsBundle", locale);

      Enumeration bundleKeys = labels.getKeys();

      while (bundleKeys.hasMoreElements()) {
        String key = (String) bundleKeys.nextElement();
        Object value = labels.getObject (key);
        System.out.println ("Key = " + key + " - Value = " + value);
      }
    }
  }
}

