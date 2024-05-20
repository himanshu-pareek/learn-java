import java.util.*;
import java.text.*;

public class PercentFormatDemo {
  private static void displayPercent (Locale locale) {
    Double percent = 0.75;

    NumberFormat percentFormatter = NumberFormat.getPercentInstance(locale);
    String percentToDisplay = percentFormatter.format (percent);

    System.out.printf ("\n %s - %s \n", locale.getDisplayName(), percentToDisplay);
  }
  public static void main (String[] args) {
    displayPercent (Locale.ENGLISH);
    displayPercent (Locale.UK);
    displayPercent (new Locale ("hi", "IN"));
    displayPercent (new Locale ("fr", "FR"));
    displayPercent (new Locale ("de", "DE"));
    displayPercent (Locale.CHINA);
  }
}


