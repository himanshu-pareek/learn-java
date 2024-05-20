import java.util.concurrent.*;

public class SleepMessages {
  public static void main (String[] args) {
    String[] importantInfos = {
      "Goat eats grass.",
      "Cow eats grass.",
      "Dot eats cats.",
      "Cat eats rats.",
      "Rat eats wheat."
    };

    for (int i = 0; i < importantInfos.length; i++) {
      // Pause for 4 seconds
      try {
        Thread.sleep (4000);
      } catch (InterruptedException ex) {
        System.out.println (ex.getLocalizedMessage());
      }

      // Print a message
      System.out.printf ("Gyaan - %s\n", importantInfos[i]);
    }
  }
}

