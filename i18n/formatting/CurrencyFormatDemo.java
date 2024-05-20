import java.util.*;
import java.text.*;


public class CurrencyFormatDemo {
  private static void displayCurrency (Locale currentLocale) {

    Integer quantity = new Integer(123456);
    Double amount = new Double(345987.246);
    NumberFormat numberFormatter;
    String quantityOut;
    String amountOut;
    Currency currentCurrency = Currency.getInstance (currentLocale);

    numberFormatter = NumberFormat.getCurrencyInstance(currentLocale);
    System.out.println (currentLocale.getDisplayName() + ", " +
        currentCurrency.getDisplayName() + " : " +
        numberFormatter.format (amount));
  }
  public static void main (String[] args) {
    displayCurrency (Locale.US);
    displayCurrency (Locale.UK);
    displayCurrency (new Locale ("hi", "IN"));
    displayCurrency (new Locale ("fr", "FR"));
    displayCurrency (new Locale ("de", "DE"));
    displayCurrency (Locale.CHINA);
  }
}


