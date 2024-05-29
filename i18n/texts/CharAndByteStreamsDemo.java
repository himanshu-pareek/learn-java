import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;

public class CharAndByteStreamsDemo {
  public static void main (String[] args) {
    String jaString = "\u65e5\u672c\u8a9e\u6587\u5b57\u5217";

    System.out.println ("jaString = " + jaString);

    writeOutput (jaString);

    String inputString = readInput ();

    System.out.println ("inputString = " + inputString);
  }

  private static void writeOutput (String s) {
    try {
      FileOutputStream outputStream = new FileOutputStream ("test.b");

      Writer writer = new OutputStreamWriter (outputStream, "UTF-8");

      writer.write (s);
      writer.close();

    } catch (IOException e) {
      System.out.println ("EXCEPTION: " + e);
    }
  }

  private static String readInput () {
    StringBuffer buffer = new StringBuffer();
    try {
      FileInputStream inputStream = new FileInputStream ("test.b");

      InputStreamReader streamReader = new InputStreamReader (inputStream, "UTF-8");

      Reader reader = new BufferedReader (streamReader);

      int ch;

      while ((ch = reader.read()) > -1) {
        buffer.append ((char) ch);
      }

      reader.close();

      return buffer.toString();
    } catch (IOException e) {
      System.out.println ("EXCEPTION: " + e);
    }
    return null;
  }
      
}

