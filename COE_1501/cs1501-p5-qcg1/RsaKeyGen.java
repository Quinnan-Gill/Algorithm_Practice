import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Random;

public class RsaKeyGen {
    private final LargeInteger ONE = new LargeInteger(new byte[] {(byte) 1});
    private final int SIZE = (512 / 8) + 1;

    public RsaKeyGen() {
        String filePath = new File("").getAbsolutePath() + "/";

        File pubKey = new File(filePath + "pubkey.rsa");
        File privKey = new File(filePath + "privKey.rsa");

        if(pubKey.exists() && privKey.exists()) {
            if (keyExists()) {
                generate(pubKey, privKey);
            }
        } else {
            generate(pubKey, privKey);
        }
    }

    private void generate(File pubKey, File privKey) {
        LargeInteger p;
        LargeInteger q;
        LargeInteger n;
        LargeInteger e;
        LargeInteger d;
        LargeInteger[] g_z_d;

        FileOutputStream fos = null;

        System.out.println("Generating p and q");
        p = new LargeInteger(256, new Random());
        q = new LargeInteger(256, new Random());

        System.out.println("Generating n");
        n = p.multiply(q);

        System.out.println("Generating phi");
        LargeInteger phi = p.subtract(ONE).multiply(q.subtract(ONE));

        e = new LargeInteger(496, new Random()); // have it be 496 bits
        e = e.bigify(SIZE);

        g_z_d = e.XGCD(phi);

        while (!g_z_d[0].isOne()) {
            g_z_d = e.XGCD(phi);
            e = new LargeInteger(496, new Random());
            e = e.bigify(SIZE);
        }

        d = g_z_d[1];

        if(d.isNegative()) { // if negaivate take the -d % N
            // System.out.println("d is negative");
            // System.out.println("before divide: " + d.negate());
            d = d.divide(d, n)[1];
        }

        System.out.println("Creating keys");
        try {
            pubKey.createNewFile();
            privKey.createNewFile();
        } catch(IOException ie) {
            ie.printStackTrace();
        }


        System.out.println("Generating public key");
        try {
            fos = new FileOutputStream(pubKey);
            e = e.smallify().bigify(SIZE);
            fos.write(e.getVal(), 0, SIZE);
            fos.flush();
            n = n.smallify().bigify(SIZE);
            fos.write(n.getVal(), 0, SIZE);
            fos.flush();
        } catch(IOException ie) {
            ie.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }

        System.out.println("Generating private key");
        try {
            fos = new FileOutputStream(privKey);
            d = d.bigify(SIZE);
            fos.write(d.getVal(), 0, SIZE);
            fos.flush();
            n = n.bigify(SIZE);
            fos.write(n.getVal(), 0, SIZE);
            fos.flush();
        } catch(IOException ie) {
            ie.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    private boolean keyExists() {
        BufferedReader reader = null;
        boolean over = false;

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("RSA Key has already been generated would you like (y/n)? ");
            over = reader.readLine().compareTo("y") == 0;
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return over;
    }

    public static void main(String[] args) {
        RsaKeyGen rsa = new RsaKeyGen();
    }
}
