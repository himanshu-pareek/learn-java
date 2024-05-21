import java.util.List;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Currency;
import java.text.NumberFormat;
import java.text.MessageFormat;

// AWT
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Color;

// Swing
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JFormattedTextField;

public class InternationalizedMortgageCalculator extends JPanel 
  implements PropertyChangeListener {
  
  InternationalizedMortgageCalculator (Locale locale) {

    System.out.println ("\nShifting locale to " + locale.getDisplayName() + " ...\n");

    setUpLocaleActions (locale);

    labels = ResourceBundle.getBundle ("resources.Resources", locale);
    setUpFormats (locale);

    setUpLabels (locale);

    setUpTextFields (locale);

    associateLabelsWithTextFields ();

    showLabelsAndTextFields ();
  }

  public static void main (String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Locale currentLocale = new Locale.Builder()
          .setLanguage("en")
          .setRegion("US")
          .build();

        UIManager.put("swing.boldMetal", false);

        createAndShowGUI (currentLocale);

      }
    });

  }

  // Formatting
  private Currency currencyInstance;
  private NumberFormat amountFormat;
  private NumberFormat rateFormat;
  private NumberFormat paymentFormat;

  private void setUpFormats (Locale locale) {
    currencyInstance = Currency.getInstance (locale);
    amountFormat = NumberFormat.getNumberInstance();
    rateFormat = NumberFormat.getNumberInstance();
    rateFormat.setMinimumFractionDigits (3);

    paymentFormat = NumberFormat.getCurrencyInstance (locale);
    paymentFormat.setCurrency (currencyInstance);
  }

  // Set up labels
  private JLabel amountLabel, rateLabel, numPeriodsLabel, paymentLabel;

  private void setUpLabels (Locale locale) {

    amountLabel = new JLabel (
        MessageFormat.format (
          labels.getString("AMOUNT_STRING"),
          currencyInstance.getDisplayName (),
          currencyInstance.getSymbol ()
        )
    );
    rateLabel = new JLabel (labels.getString("RATE_STRING"));
    numPeriodsLabel = new JLabel (labels.getString ("NUM_PERIODS_STRING"));
    paymentLabel = new JLabel (labels.getString ("PAYMENT_STRING"));
  }

  // Set up text fields
  private double amount = 100000;
  private double rate = 7.5;
  private int numPeriods = 30;

  private JFormattedTextField amountField, rateField, numPeriodsField,
          paymentField;
  private void setUpTextFields (Locale locale) {
    double payment = computePayment (amount, rate, numPeriods);

    // amount field
    amountField = new JFormattedTextField (amountFormat);
    amountField.setValue (amount);
    amountField.setColumns (10);
    amountField.addPropertyChangeListener ("value", this);

    rateField = new JFormattedTextField (rateFormat);
    rateField.setValue (rate);
    rateField.setColumns (10);
    rateField.addPropertyChangeListener ("value", this);

    numPeriodsField = new JFormattedTextField ();
    numPeriodsField.setValue (numPeriods);
    numPeriodsField.setColumns (10);
    numPeriodsField.addPropertyChangeListener ("value", this);

    paymentField = new JFormattedTextField (paymentFormat);
    paymentField.setValue (payment);
    paymentField.setColumns (10);
    paymentField.setEditable (false);
    paymentField.setForeground (Color.red);
  }

  // Associate labels with text fields
  private void associateLabelsWithTextFields () {
    amountLabel.setLabelFor (amountField);
    rateLabel.setLabelFor (rateField);
    numPeriodsLabel.setLabelFor (numPeriodsField);
    paymentLabel.setLabelFor (paymentField);
  }

  // Display labels and text fields
  private void showLabelsAndTextFields () {
    JPanel labelsAndFieldsPane = new JPanel (new GridLayout (4, 2));
    labelsAndFieldsPane.add (amountLabel);
    labelsAndFieldsPane.add (amountField);

    labelsAndFieldsPane.add (rateLabel);
    labelsAndFieldsPane.add (rateField);

    labelsAndFieldsPane.add (numPeriodsLabel);
    labelsAndFieldsPane.add (numPeriodsField);

    labelsAndFieldsPane.add (paymentLabel);
    labelsAndFieldsPane.add (paymentField);

    this.setBorder (BorderFactory.createEmptyBorder (20, 20, 20, 20));
    this.add (labelsAndFieldsPane, BorderLayout.CENTER);
  }

  // Set up actions to change locales of the app
  private Action englishUSLocaleAction, englishUKLocaleAction,
          frenchFranceLocaleAction, frenchCanadaLocaleAction,
          arabicSaudiArabiaLocaleAction;

  private void setUpLocaleActions (Locale locale) {
    englishUSLocaleAction = new ChangeLocaleAction (
        "English, United States locale, en-US",
        "This is the english locale for US.",
        KeyEvent.VK_U,
        new Locale.Builder()
          .setLanguage("en")
          .setRegion("US")
          .build()
    );

    englishUKLocaleAction = new ChangeLocaleAction (
        "English, United Kingdom locale, en-UK",
        "This is the english locale for UK.",
        KeyEvent.VK_G,
        new Locale.Builder()
          .setLanguage("en")
          .setRegion("GB")
          .build()
    );
    
    frenchFranceLocaleAction = new ChangeLocaleAction (
        "French, France Locale, fr-FR",
        "This is the french locale.",
        KeyEvent.VK_F,
        new Locale.Builder()
          .setLanguage("fr")
          .setRegion("FR")
          .build()
    );

    frenchCanadaLocaleAction = new ChangeLocaleAction (
        "French, Kanada locale, fr-CA",
        "This is the french locale for Canada.",
        KeyEvent.VK_C,
        new Locale.Builder()
          .setLanguage("fr")
          .setRegion("CA")
          .build()
    );

    arabicSaudiArabiaLocaleAction = new ChangeLocaleAction (
        "Arabic, Saudi arabia locale, ar-SA",
        "This is the arabic locale.",
        KeyEvent.VK_S,
        new Locale.Builder()
          .setLanguage("ar")
          .setRegion("SA")
          .build()
    );
  }

  public JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    JMenu mainMenu = new JMenu(labels.getString("LOCALE"));

    List<Action> actions = List.of (
        englishUSLocaleAction,
        englishUKLocaleAction,
        frenchFranceLocaleAction,
        frenchCanadaLocaleAction,
        arabicSaudiArabiaLocaleAction
    );

    actions.stream()
      .map(JMenuItem::new)
      .map(menuItem -> {
        menuItem.setIcon (null);
        return menuItem;
      }).forEach(mainMenu::add);

    menuBar.add (mainMenu);
    return menuBar;
  }


  private static JFrame frame;
  private static ResourceBundle labels;

  private static void createAndShowGUI (Locale locale) {
    var app = new InternationalizedMortgageCalculator(locale);

    if (frame == null) {
      frame = new JFrame ();
      frame.setMinimumSize (new Dimension (500, 300));
    } else {
      frame.getContentPane().removeAll();
    }

    frame.setTitle (labels.getString ("WINDOW_TITLE"));

    frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

    // Add the app to frame
    frame.add (app);
    frame.setJMenuBar (app.createMenuBar());
    frame.applyComponentOrientation (
        ComponentOrientation.getOrientation (locale)
    );

    frame.pack();
    frame.setVisible (true);
  }

  @Override
  public void propertyChange (PropertyChangeEvent e) {
    Object source = e.getSource();
    if (source == amountField) {
      amount = ((Number) amountField.getValue()).doubleValue();
    } else if (source == rateField) {
      rate = ((Number) rateField.getValue()).doubleValue();
    } else if (source == numPeriodsField) {
      numPeriods = ((Number) numPeriodsField.getValue()).intValue();
    }

    double payment = computePayment (amount, rate, numPeriods);
    paymentField.setValue (payment);
  }

  private double computePayment (
    double amount,
    double rate,
    int numPeriods
  ) {
    double I, partial, denominator, answer;

    numPeriods *= 12; // Number of months

    if (rate > 0.01) {
      I = rate / 100.0 / 12.0;
      partial = Math.pow (1 + I, (0.0 - numPeriods));
      denominator = (1 - partial) / I;
    } else {
      denominator = numPeriods;
    }

    answer = (-1 * amount) / denominator;
    return answer;
  }

  class ChangeLocaleAction extends AbstractAction {
    private String description;
    private Locale locale;

    public ChangeLocaleAction (
        String text,
        String description,
        Integer mnemonicValue,
        Locale locale
    ) {
      super (text);
      this.description = description;
      this.locale = locale;
      putValue (SHORT_DESCRIPTION, description);
      putValue (MNEMONIC_KEY, mnemonicValue);
    }

    public void actionPerformed (ActionEvent e) {
      createAndShowGUI (this.locale);
    }
  }
}

