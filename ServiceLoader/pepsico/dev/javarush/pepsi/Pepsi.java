package dev.javarush.pepsi;

import dev.javarush.softdrink.SoftDrink;

public class Pepsi implements SoftDrink {

  @Override
  public String getName() {
    return "Pepsi :)";
  }

  @Override
  public int getPrice() {
    return 75;
  }
}

