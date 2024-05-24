import java.util.Locale;
import java.util.ResourceBundle;

import java.text.MessageFormat;
import java.text.ChoiceFormat;
import java.text.Format;
import java.text.NumberFormat;

public class ChoiceFormatDemo {
  private static void showMessages (Locale locale) {

    System.out.println ("\n\nShowing messages in " + locale.getDisplayName());

    // 1. Create resource bundle
    ResourceBundle messages = ResourceBundle.getBundle ("messages", locale);

    // 2. Create a message formatter
    MessageFormat formatter = new MessageFormat ("");
    formatter.setLocale (locale);

    // 3. Create a choice formatter
    double[] fileLimits = {0, 1, 2};
    String[] fileStrings = {
      messages.getString ("noFiles"),
      messages.getString ("oneFile"),
      messages.getString ("multipleFiles")
    };
    ChoiceFormat choiceFormat = new ChoiceFormat (fileLimits, fileStrings);

    // 4. Apply the pattern
    String pattern = messages.getString("pattern");
    formatter.applyPattern (pattern);

    // 5. Assign the formats
    Format[] formats = { choiceFormat, null, NumberFormat.getInstance(locale) };
    formatter.setFormats (formats);

    // 6. Set the arguments and format the message
    for (int numFiles = 0; numFiles <= 4; numFiles++) {
      Object[] messageArguments = {
        numFiles,
        "XDisc",
        numFiles
      };

      String result = formatter.format (messageArguments);
      System.out.println (result);
    }
  }

  public static void main (String[] args) {
    showMessages (Locale.US);
    // showMessages (new Locale ("hi", "IN"));
    showMessages (new Locale ("fr", "FR"));
  }
}


