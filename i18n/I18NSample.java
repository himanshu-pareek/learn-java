import java.util.*;

public class I18NSample {
  public static void main(String[] args) {
    String language;
    String country;

    if (args.length < 2) {
      language = "en";
      country = "US";
    } else {
      language = args[0];
      country = args[1];
    }


    Locale currentLocale;
    ResourceBundle messages;

    currentLocale = new Locale (language, country);

    messages = ResourceBundle.getBundle ("messages", currentLocale);
    System.out.println (messages.getString ("greeting"));
    System.out.println (messages.getString ("inquiry"));
    System.out.println (messages.getString ("farewell"));
  }
}

