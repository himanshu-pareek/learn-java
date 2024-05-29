import java.io.UnsupportedEncodingException;

public class ByteEncodingDemo {
  public static void main (String[] args) {
    String original = "A" + "\u00ea" + "\u00f1" + "\u00fc" + "C";
    System.out.println (original);

    try {
      byte[] utf8Bytes = original.getBytes ("UTF-8");
      byte[] defaultBytes = original.getBytes ();

      String roundTrip = new String (utf8Bytes, "UTF-8");
      System.out.printf ("Round trip = %s\n", roundTrip);

      printBytes (utf8Bytes, "UTF-8 BYTES");
      printBytes (defaultBytes, "DEFAULT BYTES");
    } catch (UnsupportedEncodingException e) {
      System.out.println (e.getMessage());
    }
  }

  private static void printBytes (byte[] bytes, String label) {
    System.out.println();
    for (int i = 0; i < bytes.length; i++) {
      System.out.printf ("%s[%d] = %s\n", label, i, UnicodeFormatter.byteToHex (bytes[i]));
    }
  }
}

