import java.util.*;
import java.text.*;


public class NumberFormatDemo {
  private static void displayNumber(Locale currentLocale) {

    Integer quantity = new Integer(123456);
    Double amount = new Double(345987.246);
    NumberFormat numberFormatter;
    String quantityOut;
    String amountOut;

    numberFormatter = NumberFormat.getNumberInstance(currentLocale);
    quantityOut = numberFormatter.format(quantity);
    amountOut = numberFormatter.format(amount);
    System.out.println(quantityOut + "   " + currentLocale.toString());
    System.out.println(amountOut + "   " + currentLocale.toString());
  }
  public static void main (String[] args) {
    displayNumber (Locale.ENGLISH);
    displayNumber (Locale.UK);
    displayNumber (new Locale ("hi", "IN"));
    displayNumber (new Locale ("fr", "FR"));
    displayNumber (new Locale ("de", "DE"));
    displayNumber (Locale.CHINA);
  }
}


