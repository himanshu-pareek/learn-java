import java.util.Random;

class Drop {
  private String message;
  private boolean empty = true;

  public synchronized String take() {
    while (empty) {
      try {
        wait();
      } catch (InterruptedException e) {}
    }
    empty = true;
    notifyAll();
    return message;
  }

  public synchronized void put (String message) {
    while (!empty) {
      try {
        wait();
      } catch (InterruptedException e) {}
    }
    this.message = message;
    this.empty = false;
    notifyAll();
  }
}

class Producer implements Runnable {
  private Drop drop;

  public Producer (Drop drop) {
    this.drop = drop;
  }

  @Override
  public void run() {
    String[] importantInfos = {
      "Message One",
      "Message Two",
      "Message three",
      "Message Four",
      "Message Five",
      "DONE"
    };

    Random random = new Random();

    for (String importantInfo: importantInfos) {
      try {
        Thread.sleep (random.nextInt (4000));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      drop.put (importantInfo);
    }
  }
}

class Consumer implements Runnable {
  private Drop drop;

  Consumer (Drop drop) {
    this.drop = drop;
  }

  @Override
  public void run() {
    Random random = new Random();
    for (String message = drop.take(); !message.equals("DONE"); message = drop.take()) {
      System.out.printf ("MESSAGE RECEIVED: %s\n", message);
      try {
        Thread.sleep (random.nextInt(3000));
      } catch (InterruptedException e) {}
    }
  }
}

public class ProducerConsumerExample {
  public static void main(String[] args) {
    Drop drop = new Drop();
    new Thread(new Producer(drop)).start();
    new Thread(new Consumer(drop)).start();
  }
}

