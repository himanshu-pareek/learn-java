import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

public class RegexTestHarness {
  public static void main(String[] args) {
    Console console = System.console();
    if (console == null) {
      System.err.println ("No system console.");
      System.exit (1);
    }

    while (true) {

      Pattern pattern = null;
      Matcher matcher = null;
      try {
        String patternInput = console.readLine ("%nEnter your regex: ");
        pattern = Pattern.compile (patternInput);

        String matcherInput = console.readLine ("Enter input string to search: ");
        matcher = pattern.matcher (matcherInput);
      } catch (PatternSyntaxException e) {
        console.format ("There is a problem with the regular expression.%n");
        console.format ("The pattern in question is %s%n", e.getPattern());
        console.format ("The description is %s%n", e.getDescription());
        console.format ("The message is %s%n", e.getMessage());
        console.format ("The index is %s%n", e.getIndex());
        continue;
      }

      boolean found = false;
      while (matcher.find()) {
        console.format ("I found the text \"%s\" at [ %d, %d ).%n", matcher.group(), matcher.start(), matcher.end());
        found = true;
      }

      if (!found) {
        console.format ("No match found.%n");
      }
    }
  }
}


