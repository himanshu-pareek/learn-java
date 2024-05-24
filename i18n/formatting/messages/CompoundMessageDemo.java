import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Date;

import java.text.MessageFormat;

public class CompoundMessageDemo {
  private static void showCompoundMessage (Locale locale) {
    ResourceBundle messages = ResourceBundle.getBundle ("messages", locale);
    Object[] messageArguments = {
      messages.getString("planet.mars"),
      7,
      new Date()
    };

    // Creating the formatter
    MessageFormat formatter = new MessageFormat("");
    formatter.setLocale (locale);

    // Fetch the pattern string
    formatter.applyPattern (messages.getString ("message.template"));

    // Apply message arguments on the pattern
    String output = formatter.format (messageArguments);

    System.out.println ("Locale - " + locale.getDisplayName());
    System.out.println ("\t" + output);
  }

  public static void main (String[] args) {
    System.out.println ("Compund message demo");

    showCompoundMessage (Locale.US);
    showCompoundMessage (new Locale ("hi", "IN"));
  }
}

