package dev.javarush.jmx.mx;

public interface QueueSamplerMXBean {

  public QueueSample getQueueSample();

  public void clearQueue();

}

