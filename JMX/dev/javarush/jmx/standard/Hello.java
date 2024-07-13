package dev.javarush.jmx.standard;

import javax.management.*;

public class Hello extends NotificationBroadcasterSupport implements HelloMBean {

  @Override
  public void sayHello() {
    System.out.println ("Hello, world🌎");
  }

  @Override
  public int add (int x, int y) {
    return x + y;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getCacheSize () {
    return this.cacheSize;
  }

  @Override
  public synchronized void setCacheSize (int cacheSize) {
    int oldCacheSize = this.cacheSize;
    this.cacheSize = cacheSize;

    System.out.println ("Cache size is - " + this.cacheSize);

    Notification notification = new AttributeChangeNotification (
        this,
        this.sequenceNumber++,
        System.currentTimeMillis(),
        "Cache size changed",
        "CacheSize",
        "int",
        oldCacheSize,
        this.cacheSize
        );

    sendNotification (notification);
  }

  @Override
  public MBeanNotificationInfo[] getNotificationInfo() {
    String[] types = new String[]{
      AttributeChangeNotification.ATTRIBUTE_CHANGE
    };

    String name = AttributeChangeNotification.class.getName();
    String description = "An attribute of this MBean has changed.";
    MBeanNotificationInfo info = new MBeanNotificationInfo (types, name, description);
    return new MBeanNotificationInfo[]{ info };
  }

  private final String name = "NameHello";
  private int cacheSize = DEFAULT_CACHE_SIZE;
  private long sequenceNumber = 1L;

  private static final int DEFAULT_CACHE_SIZE = 200;
}


