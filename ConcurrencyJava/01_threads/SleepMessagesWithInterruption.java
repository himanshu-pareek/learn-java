import java.util.concurrent.*;

public class SleepMessagesWithInterruption {
  public static void main (String[] args) {
    String[] importantInfos = {
      "Goat eats grass.",
      "Cow eats grass.",
      "Dot eats cats.",
      "Cat eats rats.",
      "Rat eats wheat."
    };

    Thread t = new Thread(() -> {
      for (int i = 0; i < importantInfos.length; i++) {
        try {
          Thread.sleep (4000);
          if (Thread.currentThread().isInterrupted()) {
            System.out.println("Thread is interrupted.");
          }
          System.out.println ("INFO: " + importantInfos[i]);
        } catch (InterruptedException ex) {
          System.out.println ("INTERRUPTED: " + ex);
          ex.printStackTrace();
          break;
        }
      }
    });
    t.start();

    try {
      Thread.sleep (8000);
    } catch (InterruptedException e) {
      System.out.println ("INTERRUPTED THREAD MAIN");
    }

    try {
      t.interrupt();
    } catch (Exception e) {
      System.out.println ("INTERRUPT ERROR: " + e);
      e.printStackTrace();
    }
  }
}

