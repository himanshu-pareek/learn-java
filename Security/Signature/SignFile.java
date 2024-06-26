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

		if (args.length != 1) {

			System.out.println ("Usage: SignFile <file name>");
			System.exit (1);

		}

		String fileName = args[0];
		System.out.println ("File to sign - " + fileName);

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
		System.out.println ("Supplying the data from the file to the signature...");
		updateSignature (signature, fileName);
		System.out.println ("Supplied.\n");

		// 4. Generate the signature
		System.out.println ("Signing the data...");
		byte[] sign = signature.sign();
		System.out.println ("Signed");
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


