import java.util.Date;
import java.util.Locale;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CustomizingFormatDemo {
  private void showCurrentDate (Locale locale) {
    Date today = new Date();

    SimpleDateFormat formatter = new SimpleDateFormat ("EEE d MMM yy @ HH:mm:ss", locale);

    System.out.println("Locale - " + locale.getDisplayName());
    System.out.println("Current- " + formatter.format (today));
  }

  public static void main (String[] args) {
    
    CustomizingFormatDemo demo = new CustomizingFormatDemo();
    demo.showCurrentDate (Locale.US);
    demo.showCurrentDate (new Locale ("hi", "IN"));
    demo.showCurrentDate (new Locale ("fr", "FR"));
    demo.showCurrentDate (new Locale ("de", "De"));
  }
}

