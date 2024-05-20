package dev.javarush.coke;

import dev.javarush.softdrink.SoftDrink;

public class Coke implements SoftDrink {

  @Override
  public String getName() {
    return "Coca cola";
  }

  @Override
  public int getPrice() {
    return 100;
  }
}

