import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {

        // args -> Queue Capacity, Number of Producers, Number of Consumers
        final int CAPACITY = args.length >= 1 ? Integer.parseInt(args[0]) : 3;
        final int NUM_PRODUCERS = args.length >= 2 ? Integer.parseInt(args[1]) : 2;
        final int NUM_CONSUMERS = args.length >= 3 ? Integer.parseInt(args[2]) : 2;

        BlockingQueue<Message> queue = new LinkedBlockingQueue<>(CAPACITY);

        List<Thread> producerThreads = new ArrayList<>();
        for (int i = 0; i < NUM_PRODUCERS; i++) {
            producerThreads.add(new Thread(new Producer(queue)));
        }

        List<Thread> consumerThreads = new ArrayList<>();
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            consumerThreads.add(new Thread(new Consumer(queue)));
        }

        producerThreads.forEach(Thread::start);
        consumerThreads.forEach(Thread::start);

        for (Thread thread : producerThreads) {
            thread.join();
        }
        for (Thread thread : consumerThreads) {
            thread.join();
        }
    }
}

class Producer implements Runnable {

    private final BlockingQueue<Message> queue;

    Producer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            var message = new Message(i, "Message " + (i + 1));
            try {
                this.queue.put(message);
            } catch (InterruptedException e) {
            }
            System.out.printf("🚮 %s put message %s into the queue\n", Thread.currentThread().getName(), message);
        }
    }
}

class Consumer implements Runnable {

    private final BlockingQueue<Message> queue;

    Consumer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Message message = null;
            try {
                Thread.sleep(1000);
                message = this.queue.take();
            } catch (InterruptedException e) {
            }
            System.out.printf("📦 %s got message %s from the queue\n", Thread.currentThread().getName(), message);
        }
    }
}

record Message(int id, String text) {
}
