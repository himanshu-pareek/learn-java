class Friend {
  private String name;

  public Friend (String name) {
    this.name = name;
  }

  public String getName () {
    return this.name;
  }

  public synchronized void bow(Friend bower) {
    System.out.printf ("%s: %s has bowed to me\n", this.name, bower.getName());
    bower.bowBack(this);
  }

  public synchronized void bowBack (Friend bower) {
    System.out.printf ("%s: %s has bowed back to me\n", this.name, bower.getName());
  }
}

public class Deadlock {
  public static void main (String[] args) {
    final Friend alphonse = new Friend ("Alphonse");
    final Friend gastove = new Friend ("Gastove");
    new Thread (() -> {
      alphonse.bow (gastove);
    }).start();
    new Thread (() -> {
      gastove.bow (alphonse);
    }).start();
  }
}


















