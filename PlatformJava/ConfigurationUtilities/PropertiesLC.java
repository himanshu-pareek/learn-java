import java.util.*;
import java.io.*;

public class PropertiesLC {

  private static final String DEFAULT_PROPERTIES_FILE = "default.properties";
  private static final String APP_PROPERTIES_FILE = "app.properties";

  private static void getAppPropertiesFromUser (Properties properties) {

    Scanner sc = new Scanner (System.in);
    int choice = 1000;
    while (choice != 0) {
      System.out.printf("Add a property?(1) / Quit?(0): ");
      choice = sc.nextInt();
      if (choice == 1) {
        System.out.printf("Key: ");
        String key = sc.next();
        System.out.printf("Value: ");
        String value = sc.next();
        key = key.trim();
        value = value.trim();
        if (key.isEmpty()) {
          System.out.println("Invalid key. Try again.");
        } else if (value.isEmpty()) {
          System.out.println("Invalid value. Try again.");
        } else {
          System.out.printf("Saving %s - %s in properties...\n", key, value);
          properties.setProperty (key, value);
        }
      }
    }
  }

  public static void main (String[] args) {
    // Create and load default properties
    Properties defaultProps = new Properties();
    try (FileInputStream in = new FileInputStream(DEFAULT_PROPERTIES_FILE)) {
      defaultProps.load (in);
    } catch (IOException ex) {
      System.out.printf ("UNABLE TO LOAD DEFAULT PROPERTIES: %s\n", ex.getMessage());
    }

    // Create application properties with defaults
    Properties appProperties = new Properties (defaultProps);
    

    // Load application properties from last invocation
    try (FileInputStream in = new FileInputStream (APP_PROPERTIES_FILE)) {
      appProperties.load (in);
    } catch (IOException ex) {
      System.out.printf ("UNABLE TO LOAD APP PROPERTIES: %s\n", ex.getMessage());
    }

    // getAppPropertiesFromUser (appProperties);

    System.out.println("------------ KEYS - VALUES ------------");
    for (Object objectKey: appProperties.keySet()) {
      String key = (String) objectKey;
      String value = appProperties.getProperty (key);
      System.out.println(key + " - " + value);
    }

    System.out.println("\n------------ NAMES - VALUES -----------");
    for (String name: appProperties.stringPropertyNames()){
      String value = appProperties.getProperty (name);
      System.out.println(name + " - " + value);
    }

    // Store application properties
    try (FileOutputStream out = new FileOutputStream (APP_PROPERTIES_FILE)) {
      appProperties.store (out, "Application Properties - Himanshu");
    } catch (IOException ex) {
      System.out.printf ("UNABLE TO STORE APP PROPERTIES: %s\n", ex.getMessage());
    }


  }
}

