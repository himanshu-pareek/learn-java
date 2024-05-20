package dev.javarush.client;

import dev.javarush.softdrink.SoftDrink;
import java.util.ServiceLoader;

public class Client {
  public static void main (String[] args) {
    System.out.println ("Let's find softdrinks...");

    var softDrinks = ServiceLoader.load (SoftDrink.class);

    for (var softDrink: softDrinks) {
      System.out.printf ("%s - %d\n", softDrink.getName(), softDrink.getPrice());
    }
  }
}

