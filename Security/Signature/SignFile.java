import java.io.*;
import java.util.*;
import java.security.*;

public class SignFile {

	private static KeyPair generateKeyPair () throws Exception {

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance ("DSA");
		SecureRandom random = SecureRandom.getInstanceStrong ();
		keyGen.initialize (1024, random);
		return keyGen.generateKeyPair();
	}

	public static void main (String[] args) throws Exception {

		if (args.length < 1) {

			System.out.println ("Usage: SignFile <file name>");
			System.exit (1);

		}

		String fileName = args[0];
    String sigFileName = "sig";
    String pubKeyFileName = "pubkey";
    if (args.length >= 2) {
      sigFileName = args[1];
    }
    if (args.length >= 3) {
      pubKeyFileName = args[2];
    }
		System.out.printf ("\n\tFile to sign - %s\n", fileName);
    System.out.printf ("\tSignature will be stored in - %s\n", sigFileName);
    System.out.printf ("\tPublic key will be stored in - %s\n\n", pubKeyFileName);


		// 1. Generate Private and Public key
		System.out.println ("⏳Generating private - public key pair...");
		KeyPair keyPair = generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		System.out.println ("✅Generated.\n");

		// 2. Initialize Signature object with private key
		System.out.println ("⏳Initializing Signature object using the private key...");
		Signature signature = createSignature (privateKey);
		System.out.println ("✅Initialized.\n");

		// 3. Supply the file data to the signature
		System.out.println ("⏳Supplying the data from the file to the signature...");
		updateSignature (signature, fileName);
		System.out.println ("✅Supplied.\n");

		// 4. Generate the signature
		System.out.println ("⏳Signing the data...");
		byte[] sign = signature.sign();
		System.out.println ("✅Signed.\n");

    // 5. Store the signature in a file
    System.out.printf ("⏳Storing signature in %s...\n", sigFileName);
    writeToFile (sign, sigFileName);
    System.out.println ("✅Stored.\n");

    // 6. Store the public key in a file
    System.out.printf ("⏳Storing public key in %s...\n", pubKeyFileName);
    writeToFile (publicKey.getEncoded(), pubKeyFileName);
    System.out.println ("✅Stored.\n");
	}

  private static void writeToFile (byte[] data, String fileName) throws Exception {

    try (FileOutputStream out = new FileOutputStream (fileName)) {

      out.write (data);

    } catch (Exception e) { throw e; }
  }

	private static void updateSignature (Signature signature, String fileName) throws Exception {

		try (
				FileInputStream fis = new FileInputStream (fileName);
				BufferedInputStream stream = new BufferedInputStream (fis)
		    ) {

			byte[] buffer = new byte[1024];
			int len = 0;

			while ((len = stream.read (buffer)) >= 0) {

				signature.update (buffer, 0, len);
			}
		    } catch (Exception e) { throw e; }
	}


	private static Signature createSignature (PrivateKey privateKey) throws Exception {

		Signature dsa = Signature.getInstance ("SHA1withDSA");
		dsa.initSign (privateKey);
		return dsa;

	}
}


