package dev.javarush.coke;

import dev.javarush.softdrink.SoftDrink;

public class DietCoke implements SoftDrink {

  @Override
  public String getName() {
    return "Diet coke";
  }

  @Override
  public int getPrice() {
    return 239;
  }
}

