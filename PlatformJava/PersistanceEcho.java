import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PersistanceEcho {
  private static final String PERSISTANCEECHO = "persistance_echo";
  private static final String FILE_NAME = "PersistanceEcho.txt";

  public static void main (String[] args) {
    Properties properties = new Properties();

    String echo = null;
    if (args.length > 0) {
      System.out.println ("Getting the value of echo from arguments...");
      echo = String.join (" ", args);
    } else {
      System.out.println ("Getting the value of echo from environment variables...");
      echo = System.getenv (PERSISTANCEECHO);
    }

    if (echo == null) {
      System.out.printf ("Getting the value of echo from %s file...\n", FILE_NAME);
      try (FileInputStream in = new FileInputStream(FILE_NAME)) {
        properties.load (in);
      } catch (IOException e) {
        System.out.println ("UNABLE TO LOAD PROPERTIES: " + e);
      }
    } else {
      properties.setProperty (PERSISTANCEECHO, echo);
    }

    // Print the value
    System.out.println ("Echo - " + properties.getProperty (PERSISTANCEECHO));

    // Store the value in file
    System.out.printf ("Storing the value of echo into file %s...\n", FILE_NAME);
    try (FileOutputStream out = new FileOutputStream (FILE_NAME)) {
      properties.store (out, "Persistable Echo");
    } catch (IOException e) {
      System.out.println ("UNABLE TO STORE PROPERTIES: " + e);
    }
  }
}

