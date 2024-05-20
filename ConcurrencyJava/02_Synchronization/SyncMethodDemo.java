public class SyncMethodDemo {
  public static void main (String[] args) {
    // Code goes here
    SynchronizedCounter counter = new SynchronizedCounter();
    Thread t1 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        counter.increment();
      }
    });
    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 1000; i++) {
        counter.decrement();
      }
    });
    t1.start();
    t2.start();
    try {
      t1.join();
      t2.join();
    } catch (InterruptedException ex) {
      System.err.println ("INTERRUPTED: " + ex);
      System.exit (1);
    }
    System.out.println ("COUNTER: " + counter.value());
  }
}

class SynchronizedCounter {
  private int counter;

  public synchronized void increment() {
    ++counter;
  }

  public synchronized void decrement() {
    --counter;
  }

  public synchronized int value() {
    return counter;
  }
}

