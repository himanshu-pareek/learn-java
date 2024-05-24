import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;

public class PredefinedFormatsDemo {
  public static void main (String[] args) {
    DateFormat dateFormatter = DateFormat
      .getDateInstance(DateFormat.DEFAULT, new Locale ("hi", "IN"));
    var today = new Date();
    var dateOut = dateFormatter.format (today);
    System.out.println ("Current date - " + dateOut);
    var dateTimeFormatter = DateFormat.getDateTimeInstance (DateFormat.LONG, DateFormat.LONG, new Locale ("hi", "IN"));
    System.out.println ("Current date & time - " + dateTimeFormatter.format(new Date()));
    dateTimeFormatter = DateFormat.getDateTimeInstance (DateFormat.DEFAULT, DateFormat.DEFAULT, new Locale ("hi", "IN"));
    System.out.println ("Current date & time - " + dateTimeFormatter.format(new Date()));
  }
}
