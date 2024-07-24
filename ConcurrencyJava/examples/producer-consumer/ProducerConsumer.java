public class ProducerConsumer {

  public static void main(String[] args) throws Exception {
    System.out.println("\n\t\t--------- Producer - Consumer Demo --------\n\n");

    MessageQueue queue = new MessageQueue(3);

    var producer = new Producer(queue);
    var producer2 = new Producer(queue);
    var consumer = new Consumer(queue);
    var consumer2 = new Consumer(queue);

    var producerThread = new Thread(producer);
    var producerThread2 = new Thread(producer2);

    var consumerThread = new Thread(consumer);
    var consumerThread2 = new Thread(consumer2);

    producerThread.start();
    producerThread2.start();
    consumerThread.start();
    consumerThread2.start();

    producerThread.join();
    producerThread2.join();
    consumerThread.join();
    consumerThread2.join();
  }

}

class Producer implements Runnable {
  private final MessageQueue queue;

  Producer(MessageQueue queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      String content = "Content " + (i + 1);
      queue.pushMessage(new Message(i, content));
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class Consumer implements Runnable {
  private final MessageQueue queue;

  Consumer(MessageQueue queue) {
    this.queue = queue;
  }

  @Override
  public void run() {
    for (int i = 0; i < 10; i++) {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Message content = queue.pollMessage();
      System.out.println("Received " + content);
    }
  }
}

class MessageQueue {
  Message[] messages;
  int indexToPut;
  int indexToGet;
  int size;

  private final Object IS_NOT_FULL = new Object();
  private final Object IS_NOT_EMPTY = new Object();

  MessageQueue(int size) {
    this.messages = new Message[size];
    indexToGet = 0;
    indexToPut = 0;
    size = 0;
  }

  private boolean isEmpty() {
    return size == 0;
  }

  private void waitForNotFull() {
    while (isFull()) {
      synchronized (IS_NOT_FULL) {
        try {
          IS_NOT_FULL.wait();
        } catch (InterruptedException e) {
        }
      }
    }
  }

  private boolean isFull() {
    return size == this.messages.length;
  }

  private void waitForNotEmpty() {
    while (isEmpty()) {
      synchronized (IS_NOT_EMPTY) {
        try {
          IS_NOT_EMPTY.wait();
        } catch (InterruptedException e) {
        }
      }
    }
  }

  public void pushMessage(Message message) {
    waitForNotFull();
    push(message);
    notifyNotEmpty();
  }

  synchronized private void push(Message message) {
    System.out.printf("Putting %s at index %d\n", message.toString(), indexToPut);
    this.messages[indexToPut] = message;
    incrementIndexToPut();
    ++size;
  }

  private void incrementIndexToPut() {
    ++indexToPut;
    if (indexToPut >= this.messages.length) {
      indexToPut = 0;
    }
  }

  private void notifyNotEmpty() {
    synchronized (IS_NOT_EMPTY) {
      IS_NOT_EMPTY.notifyAll();
    }
  }

  public Message pollMessage() {
    waitForNotEmpty();
    Message message = poll();
    notifyNotFull();
    return message;
  }

  synchronized private Message poll() {
    Message message = this.messages[indexToGet];
    incrementIndexToGet();
    --size;
    return message;
  }

  private void incrementIndexToGet() {
    ++indexToGet;
    if (indexToGet >= this.messages.length) {
      indexToGet = 0;
    }
  }

  private void notifyNotFull() {
    synchronized (IS_NOT_FULL) {
      IS_NOT_FULL.notifyAll();
    }
  }
}

record Message(int id, String text) {
}
