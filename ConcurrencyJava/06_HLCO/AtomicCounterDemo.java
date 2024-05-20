import java.util.concurrent.atomic.AtomicInteger;

class AtomicCounter {

  private AtomicInteger counter = new AtomicInteger (0);

  public void increment() {
    this.counter.incrementAndGet();
  }

  public void decrement() {
    this.counter.decrementAndGet();
  }

  public int value() {
    return this.counter.get();
  }
}

public class AtomicCounterDemo {
  public static void main (String[] args) throws InterruptedException {
    AtomicCounter counter = new AtomicCounter();

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

    t1.join();
    t2.join();
    System.out.println ("Counter value: " + counter.value());
  }
}

