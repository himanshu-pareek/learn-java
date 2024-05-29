public class UnicodeFormatter {

  public static String byteToHex (byte b) {

    char[] hexDigits = {
      '0', '1', '2', '3', '4', '5', '6', '7',
      '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    char[] result = { hexDigits[(b >> 4) & 0x0f], hexDigits[b & 0x0f] };

    return new String (result);
  }

  public static String charToHex (char c) {
    byte hi = (byte) (c >>> 8);
    byte lo = (byte) (c & 0xff);

    return byteToHex (hi) + byteToHex (lo);
  }
}

