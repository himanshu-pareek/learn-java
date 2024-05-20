import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;

public class SafeLock {

  static class Friend {
    private final String name;
    private final Lock lock = new ReentrantLock();

    public Friend (String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    public boolean impendingBow (Friend bower) {
      Boolean myLock = false;
      Boolean yourLock = false;

      try {
        myLock = lock.tryLock();
        yourLock = bower.lock.tryLock();
      } finally {
        if (!(myLock && yourLock)) {
          if (myLock) {
            lock.unlock();
          }
          if (yourLock) {
            bower.lock.unlock();
          }
        }
      }
      return myLock && yourLock;
    }

    public void bow(Friend bower) {
      if (impendingBow(bower)) {
        try {
          System.out.printf ("%s: %s has bowed to me\n", name, bower.name);
          bower.bowBack(this);
        } finally {
          lock.unlock();
          bower.lock.unlock();
        }
      } else {
        System.out.printf ("%s: %s started to bow me. But, he saw that I was already bowing to him\n", name, bower.name);
      }
    }

    public void bowBack(Friend bower) {
      System.out.printf ("%s: %s bowed back to me.\n", name, bower.name);
    }
  }

  static class BowLoop implements Runnable {
    private Friend bower;
    private Friend bowee;

    public BowLoop (Friend bower, Friend bowee) {
      this.bower = bower;
      this.bowee = bowee;
    }

    @Override
    public void run () {
      Random random = new Random();
      while (true) {
        try {
          Thread.sleep (random.nextInt(1000));
        } catch (InterruptedException ex) {}
        bower.bow (bowee);
      }
    }
  }

  public static void main (String[] main) {
    final var alphonse = new Friend ("Alphonse");
    final var gaston = new Friend ("Gaston");
    new Thread (new BowLoop (alphonse, gaston)).start();
    new Thread (new BowLoop (gaston, alphonse)).start();
  }
}

