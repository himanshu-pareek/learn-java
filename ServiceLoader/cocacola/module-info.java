module cocacola {
  requires softdrink;

  provides dev.javarush.softdrink.SoftDrink with
    dev.javarush.coke.Coke,
    dev.javarush.coke.DietCoke;
}

