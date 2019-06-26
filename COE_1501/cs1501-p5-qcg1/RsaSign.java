import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Random;

public class RsaSign {
    private LargeInteger n;
    private LargeInteger e;
    private LargeInteger d;

    File f;

    public RsaSign() {
        n = null;
        e = null;
        d = null;
        f = null;
    }

    private final LargeInteger ONE = new LargeInteger(new byte[] {(byte) 1});
    private final int SIZE = (512 / 8) + 1;

    private void sign(String filename) {
        String filePath = new File("").getAbsolutePath() + "/";

        f = new File(filePath + filename);
        File privKey = new File(filePath + "privKey.rsa");

        if (!f.exists()) {
            throw new IllegalArgumentException("file does not exist");
        }

        if (!privKey.exists()) {
            throw new IllegalArgumentException("private key does not exist");
        }

        byte[] n_byte = new byte[SIZE];
        byte[] d_byte = new byte[SIZE];

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(privKey);
            fis.read(d_byte, 0, d_byte.length);
            fis.read(n_byte, 0, n_byte.length);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        n = new LargeInteger(n_byte);
        d = new LargeInteger(d_byte);

        writeSig(filename);
    }

    public void writeSig(String filename) {
        HashEx h = new HashEx();

        String filePath = new File("").getAbsolutePath() + "/";

        LargeInteger hashInt = new LargeInteger(h.fileHash(filename));

        System.out.println(filePath + filename + ".sig");

        File sigFile = new File(filePath + filename + ".sig");

        assert d != null;
        assert n != null;

        LargeInteger modExp = hashInt.modularExp(d, n);

        FileOutputStream fos = null;

        try {
            sigFile.createNewFile();
        } catch(IOException ie) {
            ie.printStackTrace();
        }

        try {
            fos = new FileOutputStream(sigFile);
            modExp = modExp.smallify().bigify(SIZE);
            assert modExp.getVal().length == SIZE;
            fos.write(modExp.getVal(), 0, SIZE);
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

    private void verify(String filename) {
        HashEx h = new HashEx();

        String filePath = new File("").getAbsolutePath() + "/";

        File file = new File(filePath + filename);
        File fileSig = new File(filePath + filename + ".sig");
        File pubKey = new File(filePath + "pubKey.rsa");

        if (!file.exists()) {
            throw new IllegalArgumentException("file does not exist");
        }

        if(!fileSig.exists()) {
            throw new IllegalArgumentException("file signature does not exist");
        }

        if(!pubKey.exists()) {
            throw new IllegalArgumentException("private key does not exist");
        }


        LargeInteger hashInt = new LargeInteger(h.fileHash(filename));
        byte[] sig_byte = new byte[SIZE];

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(fileSig);
            fis.read(sig_byte, 0, sig_byte.length);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        LargeInteger signature = new LargeInteger(sig_byte);

        byte[] n_byte = new byte[SIZE];
        byte[] e_byte = new byte[SIZE];

        try {
            fis = new FileInputStream(pubKey);
            fis.read(e_byte, 0, e_byte.length);
            fis.read(n_byte, 0, n_byte.length);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        n = new LargeInteger(n_byte);
        e = new LargeInteger(e_byte);

        LargeInteger verify = signature.modularExp(e, n);

        verify = verify.smallify();
        verify = verify.bigify(SIZE);
        hashInt = verify.bigify(SIZE);


        if (verify.isEqual(hashInt)) {
            System.out.println("------------------------------");
            System.out.println("-----Successfully  Signed-----");
            System.out.println("------------------------------");
        } else {
            System.out.println("------------------------------");
            System.out.println("-------Signature FAILED-------");
            System.out.println("------------------------------");
        }
    }



    public void printVals() {
        System.out.println("n: " + n);
        System.out.println("");

        System.out.println("d: " + d);
        System.out.println("");

        System.out.println("e: " + e);
        System.out.println("");
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java RsaSign [Option: s|v] FILE");
            System.exit(1);
        }
        RsaSign rsa = new RsaSign();

        if (args[0].compareTo("s") == 0) {
            rsa.sign(args[1]);
        } else if(args[0].compareTo("v") == 0) {
            rsa.verify(args[1]);
        } else {
            System.out.println("Invalid option");
            System.exit(1);
        }
    }
}
