import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.security.MessageDigest;

public class HashEx {
	public byte[] fileHash(String filename) {
		byte[] digest = null;

		// lazily catch all exceptions...
		try {
			// read in the file to hash
			Path path = Paths.get(filename);
			byte[] data = Files.readAllBytes(path);

			// create class instance to create SHA-256 hash
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// process the file
			md.update(data);
			// generate a hash of the file
			digest = md.digest();

			System.out.println("hash generated: ");
			// print each byte in hex
			for (byte b : digest) {
				System.out.print(String.format("%02x", b));
			}
			System.out.println();
		} catch(Exception e) {
			System.out.println(e.toString());
			System.exit(1);
		}

		return digest;
	}

	public static void main(String args[]) {

		// lazily catch all exceptions...
		try {
			// read in the file to hash
			Path path = Paths.get(args[0]);
			byte[] data = Files.readAllBytes(path);

			// create class instance to create SHA-256 hash
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// process the file
			md.update(data);
			// generate a hash of the file
			byte[] digest = md.digest();

			System.out.println("size: " + digest.length*8);

			// print each byte in hex
			for (byte b : digest) {
				System.out.print(String.format("%02x", b));
			}
			System.out.println();
		} catch(Exception e) {
			System.out.println(e.toString());
		}
	}
}
