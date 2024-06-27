import java.util.*;
import java.security.*;
import java.security.spec.*;
import java.io.*;

public class VerifySignature {

  public static void main (String[] args) throws Exception {

    if (args.length != 3) {

      System.out.println ("🛑 Usage: java VerifySignature <Public Key File> <Signature File> <Data File>");
      System.exit (1);

    }

    String keyFile = args[0];
    String sigFile = args[1];
    String dataFile = args[2];

    System.out.printf ("\n\tVerifying \n\t\tsignature ✍🏼 stored in `%s` using \n\t\tthe public key 🔑 stored in `%s` for \n\t\tthe data 📦 stored in `%s`\n\n", sigFile, keyFile, dataFile);

    System.out.printf ("⏳Computing public key 🔑 from file 📄 %s\n", keyFile);
    PublicKey publicKey = getPublicKey (keyFile);
    System.out.printf ("✅Computed.\n\n");

    System.out.printf ("⏳Reading signature ✍🏼 from file 📄 %s\n", sigFile);
    byte[] sig = readFile (sigFile);
    System.out.printf ("✅Read.\n\n");

    System.out.printf ("⏳Verifying signature ✍🏼 with public key 🔑 for the given data 📦\n");
    Signature dsa = Signature.getInstance ("SHA1withDSA");
    dsa.initVerify (publicKey);

    FileInputStream fis = new FileInputStream (dataFile);
    BufferedInputStream buffis = new BufferedInputStream (fis);
    byte[] buffer = new byte[1024];
    int len = 0;
    while ((len = buffis.read (buffer)) >= 0) {
      dsa.update (buffer, 0, len);
    }
    buffis.close();

    boolean verifies = dsa.verify(sig);
    System.out.printf ("✅Verified\n\n");

    if (verifies) {
      System.out.printf ("\t\t🎉🎉🎉🎉 The signature is valid 🎉🎉🎉🎉\n\n");
    } else {
      System.out.printf ("\t\t💀💀💀💀 The signature is not valid 💀💀💀💀\n\n");
    }
  }

  private static PublicKey getPublicKey (String fileName) throws Exception {
    byte[] keyBytes = readFile (fileName);
    return getPublicKey (keyBytes);
  }

  private static PublicKey getPublicKey (byte[] keyBytes) throws Exception {
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec (keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance ("DSA");
    return keyFactory.generatePublic (keySpec);
  }

  private static byte[] readFile (String fileName) throws Exception {
    try (FileInputStream fis = new FileInputStream (fileName)) {
      byte[] data = new byte[fis.available()];
      fis.read (data);
      return data;
    } catch (Exception e) { throw e; }
  }
}

