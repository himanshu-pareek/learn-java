module pepsico {
  requires softdrink;

  provides dev.javarush.softdrink.SoftDrink with
    dev.javarush.pepsi.Pepsi;
}

