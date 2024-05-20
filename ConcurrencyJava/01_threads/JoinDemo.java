import java.util.concurrent.*;

public class JoinDemo {
  public static void main(String[] args) {
    Thread t1 = new Thread(() -> {
      try {
        Thread.sleep (2000);
        System.out.println ("t1 - " + Thread.currentThread().getName());
      } catch (InterruptedException ex) {
        System.out.println ("INTERRUPTED: " + ex);
        ex.printStackTrace();
      }
    });
    Thread t2 = new Thread(() -> {
      try {
        Thread.sleep (3000);
        System.out.println ("t2 - " + Thread.currentThread().getName());
      } catch (InterruptedException ex) {
        System.out.println ("INTERRUPTED: " + ex);
        ex.printStackTrace();
      }
    });
    t1.start();
    t2.start();
    try {
      t1.join ();
    } catch (InterruptedException ex) {
      System.out.println ("INTERRUPTED: " + ex);
      ex.printStackTrace();
    }
    System.out.println ("Completed t1 & t2");
  }
}

