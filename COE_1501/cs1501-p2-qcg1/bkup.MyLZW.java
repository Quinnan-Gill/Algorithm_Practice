import java.lang.*;

public class MyLZW {
    private static final int R = 256;        // number of input chars
    private static final int L = (int) Math.pow(2, 16);       // number of codewords = 2^W
    private static final double threshold = 1.1;

    public static void nothing_compress() {
        BinaryStdOut.write(0, 2);
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        int i = 0;
        int W = 9;
        char[] tst_input = input.toCharArray();
        while (input.length() > i) {
            String s = st.longestPrefixOf(tst_input, i);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();
            if (t+i < input.length() && code < L)  {  // Add s to symbol table.
                st.put(substring(input, i, t + i + 1), code++);
                if (W < 16 && code == Math.pow(2, W)+1) {
                    W++;
                }
            }
            i += t;            // Scan past s in input.
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    public static void reset_compress() {
        BinaryStdOut.write(1, 2);
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        int i = 0;
        int W = 9;
        char[] tst_input = input.toCharArray();
        while (input.length() > i) {
            if (code == L) {
                st = new TST<Integer>();
                for(int j=0; j < R; j++) {
                    st.put("" + (char) j, j);
                }
                code = R+1;
                W = 9;
            }
            String s = st.longestPrefixOf(tst_input, i);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();
            if (t+i < input.length() && code < L)  {  // Add s to symbol table.
                st.put(substring(input, i, t + i + 1), code++);
                if (W < 16 && code == Math.pow(2, W)+1) {
                    W++;
                }
            }
            i += t;            // Scan past s in input.
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    public static void monitor_compress() {
        BinaryStdOut.write(2, 2);
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;

        int i = 0;
        int W=9;
        long generated = 2; // Wrote two for the switch
        long processed=0;
        float current;
        float prev = 0;
        float cmp_ratio;

        int count = 0;

        char[] tst_input = input.toCharArray();
        while (input.length() > i) {
            processed = 16*i; // Each character in the array is 16 bits (2 bytes)
            // add W for the number of bits beginning written from the code book
            generated += W;

            current = ((float) processed) / (float) generated;
            if (code == L) {
                if (prev == 0) {
                    prev = current;
                    // System.err.println("Setting prev: " + prev);
                    count = 0;
                }

            }

            cmp_ratio = prev / current;
            count++;
            if (cmp_ratio > threshold) {
                System.err.println("prev: " + prev + " / current: " + current + " = cmp_ratio: " + cmp_ratio);
                System.err.println("processed: " + processed + " generated: " + generated);
                System.err.println("count: " + count);
                st = new TST<Integer>();
                for(int j=0; j < R; j++) {
                    st.put("" + (char) j, j);
                }
                code = R+1;
                W = 9;
                prev = 0;
            }
            String s = st.longestPrefixOf(tst_input, i);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();
            if (t+i < input.length() && code < L)  {  // Add s to symbol table.
                st.put(substring(input, i, t + i + 1), code++);
                if (W < 16 && code == Math.pow(2, W)+1) {
                    W++;
                }
            }
            i += t;
        }
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    public static String substring(String input, int start, int end) {
        StringBuilder sb = new StringBuilder();
        int size;

        if (input.length() < end) {
            size = input.length();
        } else {
            size = end;
        }
        for(int i=start; i < size; i++) {
            sb.append(input.charAt(i));
        }
        return sb.toString();
    }

    public static void expand() {
        int option = BinaryStdIn.readInt(2);

        switch (option) {
            case 0:
                nothing_expand();
                break;
            case 1:
                reset_expand();
                break;
            case 2:
                monitor_expand();
                break;
            default:
                System.err.println("Invalid Input");
        }
    }

    public static void nothing_expand() {
        String[] st = new String[L];
        int i; // next available codeword value
        int W = 9;

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            // System.out.println("codeword: " + codeword + "length: " + st.length);
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) {
                // System.out.println("i: " + i + " val: " + val + s.charAt(0));
                st[i++] = val + s.charAt(0);
                if (i == Math.pow(2, W) && W != 16) {
                    W++;
                }
            }
            val = s;
        }
        BinaryStdOut.close();
    }

    public static void reset_expand() {
        String[] st = new String[L];
        int i; // next available codeword value
        int W = 9;

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            if (i == L-1) {
                st = new String[L];
                for (i=0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";
                W = 9;

                codeword = BinaryStdIn.readInt(W);
                if (codeword == R) return;           // expanded message is empty string
                val = st[codeword];
                BinaryStdOut.write(val);
            }
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            // System.out.println("i: " + i + " s: " + s + " codeword: " + codeword);
            if (i < L) {
                st[i++] = val + s.charAt(0);
                if (W < 16 && i == Math.pow(2, W)) {
                    W++;
                }
            }
            val = s;
        }
        BinaryStdOut.close();
    }

    public static void monitor_expand() {
        String[] st = new String[L];
        int i; // next available codeword value
        int W = 9;

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        long generated = 0;
        long processed = 2 + W; // Wrote two for the switch and read an int
        float current = 0;
        float prev = 0;
        float cmp_ratio;

        int count = 0;

        while (true) {
            BinaryStdOut.write(val);
            // number of characters times size of character

            generated += 16 * val.length();
            processed += W;

            current = (float) (generated) / (float) (processed);

            if (i >= L-1) {
                if (prev == 0) {
                    prev = current;
                    count = 0;
                }

            }

            cmp_ratio = prev / current;
            count++;

            if (cmp_ratio > threshold) {
                st = new String[L];
                for (i=0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";
                W = 9;

                codeword = BinaryStdIn.readInt(W);
                processed += W;
                if (codeword == R) return;           // expanded message is empty string
                val = st[codeword];
                BinaryStdOut.write(val);
                generated += 16 * val.length();
                prev = 0;
            }

            codeword = BinaryStdIn.readInt(W);
            // number of bits read (W due to variable width encoding)

            if (codeword == R) {
                break;
            }
            // System.err.println("codeword: " + codeword + "length: " + st.length);
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) {
                st[i++] = val + s.charAt(0);
                if (i == Math.pow(2, W) && W != 16) {
                    W++;
                }
            }
            val = s;
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) {
            if (args.length < 2){
                System.err.println("Invalid Input");
            } else if (args[1].equals("n"))
                nothing_compress();
            else if (args[1].equals("r"))
                reset_compress();
            else if (args[1].equals("m"))
                monitor_compress();
            else {
                System.err.println("Invalid Input");
            }
        }
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
