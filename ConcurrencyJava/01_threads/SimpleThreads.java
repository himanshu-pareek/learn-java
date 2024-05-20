import java.util.concurrent.*;

public class SimpleThreads {
  private static void threadMessage (String message) {
    String threadName = Thread.currentThread().getName();
    System.out.printf ("%s: %s\n", threadName, message);
  }

  public static void main(String[] args) throws InterruptedException {
    // Patience in milliseconds
    // Default - 1 hour
    long patience = 60 * 60 * 1000;

    if (args.length > 0) {
      try {
        patience = Long.parseLong (args[0]) * 1000;
      } catch (NumberFormatException ex) {
        System.err.println ("Argument must be an integer.");
        System.exit (1);
      }
    }

    threadMessage ("Starting messageLoop thread.");
    long startTime = System.currentTimeMillis();
    Thread t = new Thread (new MessageLoop());
    t.start();

    while (t.isAlive()) {
      threadMessage ("Still waiting...");
      t.join (1000);
      if (((System.currentTimeMillis() - startTime) > patience) && t.isAlive()) {
        threadMessage ("Tired of waiting!!!");
        t.interrupt();
        t.join();
      }
    }
    threadMessage("Finally!");
  }
}

class MessageLoop implements Runnable  {
  String[] importantInfos;

  MessageLoop () {
    importantInfos = new String[] {
      "Leopard eats dogs.",
      "Dog eats cats.",
      "Cat eats rats.",
      "Rat eats wheat.",
      "Wheat eats sunlight :)"
    };
  }

  private void threadMessage(String message) {
    String threadName = Thread.currentThread().getName();
    System.out.printf ("%s: %s\n", threadName, message);
  }

  public void run() {
    try {
      for (int i = 0; i < importantInfos.length; i++) {
        // Pause for 4 seconds
        Thread.sleep (4000);
        // Print a message
        threadMessage (importantInfos[i]);
      }
    } catch (InterruptedException e) {
      threadMessage ("I wasn't done.");
      e.printStackTrace();
    }
  }
}


