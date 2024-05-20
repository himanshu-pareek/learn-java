import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.List;
import java.util.ArrayList;

public class FixedThreadPoolDemo {
  static class Task implements Runnable {
    private final String message;

    public Task (String message) {
      this.message = message;
    }

    @Override
    public void run() {
      try {
        Thread.sleep (1000);
        System.out.printf ("%s: %s\n", Thread.currentThread().getName(), this.message);
      } catch (InterruptedException e) {}
    }

    @Override
    public String toString() {
      return String.format ("{message: %s}", this.message);
    }
  }

  public static void main (String[] args) {
    System.out.println ("Starting application...");
    ExecutorService executorService = Executors.newFixedThreadPool (5);

    List<String> messages = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      messages.add ("Message " + (i + 1));
    }

    messages.stream()
      .map(message -> new Task (message))
      .map (task -> executorService.submit (task))
      .forEach (System.out::println);

    System.out.println ("Stopping application...");
    executorService.shutdown();
  }
}


