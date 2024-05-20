import java.util.concurrent.*;

public class HelloThread {
  public static void main(String[] args) {
    Thread myThread = new Thread(new MyThread());
    myThread.start();
  }
}

class MyThread implements Runnable {

  @Override
  public void run() {
    System.out.println("Hello, world!");
  }
}

