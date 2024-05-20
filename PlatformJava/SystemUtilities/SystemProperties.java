public class SystemProperties {
  public static void main (String[] args) {
    String[] propertyNames = {
      "file.separator",
      "java.class.path",
      "java.home",
      "java.vendor",
      "java.vendor.url",
      "java.version",
      "line.separator",
      "os.arch",
      "os.version",
      "os.name",
      "path.separator",
      "user.dir",
      "user.home",
      "user.name"
    };

    for (String propertyName: propertyNames) {
      String propertyValue = System.getProperty (propertyName);
      System.out.printf ("%s - %s\n", propertyName, propertyValue);
    }
  }
}

