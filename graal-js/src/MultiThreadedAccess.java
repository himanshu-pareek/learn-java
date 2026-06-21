import org.graalvm.polyglot.*;

import java.util.function.Consumer;
import java.util.function.Function;

import java.util.List;
import java.util.ArrayList;

public class MultiThreadedAccess {
  public static void main(String[] args) throws InterruptedException {
    Consumer<Integer> sleep = delay -> {
        // delay is in milli seconds
        System.out.printf("Thread %s is sleeping for %d milli seconds\n", Thread.currentThread().getName(), delay);
        
        try {
          Thread.sleep(delay);
        } catch (InterruptedException e) {
          System.err.printf("Thread %s interrupted\n", Thread.currentThread().getName());
        }
        
      };

    try (Context context = Context.newBuilder().allowAllAccess(true).build()) {

      context.getBindings("js").putMember("sleep", sleep);
      Value value = context.eval("js", "({ do() { sleep (2000); console.log('done'); } })");

      System.out.println(value);

      System.out.println(value.getMemberKeys());

      List<Thread> threads = new ArrayList<>();
      for (int i = 0; i < 2; i++) {
        threads.add (new Thread(() -> value.getMember("do").execute()));
      }

      for (Thread t: threads) {
        t.start();
      }

      for (Thread t: threads) {
        t.join();
      }
    }
  }
}

