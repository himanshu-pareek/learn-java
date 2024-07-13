import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import dev.javarush.jmx.standard.HelloMBean;
import dev.javarush.jmx.standard.Hello;
import dev.javarush.jmx.mx.QueueSamplerMXBean;
import dev.javarush.jmx.mx.QueueSampler;

public class Main {

  public static void main (String[] args) throws Exception {

    MBeanServer server = ManagementFactory.getPlatformMBeanServer();

    // Create and register standard bean
    ObjectName mBeanName = new ObjectName ("dev.javarush.jmx.standard:type=Hello");
    HelloMBean mBean = new Hello();
    server.registerMBean (mBean, mBeanName);

    // Create and register mx bean
    ObjectName mxBeanName = new ObjectName("dev.javarush.jmx.mx:type=QueueSampler");
    Queue<String> queue = new ArrayBlockingQueue<>(10);
    queue.add("Request-1");
    queue.add("Request-2");
    queue.add("Request-3");
    QueueSamplerMXBean mxBean = new QueueSampler(queue);
    server.registerMBean(mxBean, mxBeanName);

    System.out.println ("🕣Waiting forever...");
    Thread.sleep (Long.MAX_VALUE);
  }
}

