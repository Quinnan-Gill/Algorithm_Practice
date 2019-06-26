import java.util.Random;
import java.math.BigInteger;

public class Test {
    public static void main(String[] args) {
        // test1();
        // test2();
        run();
    }
    private static void run() {
        byte[] a = {(byte) 0xFF};
        byte[] b = {(byte) 0x0, (byte) 0x0};

        // System.out.println("\n------------Mult 1-------------");
        // testMult(a, b);
        // System.out.println("-------------------------------\n");

        byte[] c = {(byte) 0x1F, (byte) 0x1F};
        byte[] d = {(byte) 0x8, (byte) 0x8};

        // System.out.println("\n------------Mult 2-------------");
        // testMult(c, d);
        // System.out.println("-------------------------------\n");

        byte[] e = {(byte) 0xFF};
        byte[] f = {(byte) 0x8};
        //
        // System.out.println("\n------------Mult 3-------------");
        // testMult(e, f);
        // System.out.println("-------------------------------\n");

        // System.out.println("\n------------Mult 4-------------");
        // largeMult();
        // System.out.println("-------------------------------\n");

        // System.out.println("\n------------Comp 1-------------");
        // testComp(a, b);
        // System.out.println("-------------------------------\n");
        //
        // System.out.println("\n------------Comp 2-------------");
        // testComp(c, d);
        // System.out.println("-------------------------------\n");
        //
        // System.out.println("\n------------Comp 3-------------");
        // testComp(e, f);
        // System.out.println("\n----^e vs f^----f vs e-----");
        // testComp(f, e);
        // System.out.println("-------------------------------\n");
        //
        // System.out.println("\n------------Div  1-------------");
        // testComp(a, b);
        // System.out.println("-------------------------------\n");
        //
        // System.out.println("\n------------Div  2-------------");
        // testComp(c, d);
        // System.out.println("-------------------------------\n");
        //
        // System.out.println("\n------------Div  3-------------");
        // testDiv(c, d);
        // // System.out.println("\n----^e vs f^----f vs e-----");
        // // testComp(f, e);
        // System.out.println("-------------------------------\n");

        // System.out.println("\n------------Div  4-------------");
        // largeDiv();
        // // System.out.println("\n----^e vs f^----f vs e-----");
        // // testComp(f, e);
        // System.out.println("-------------------------------\n");

        // System.out.println("\n------------Div  5-------------");
        // negDiv();
        // // System.out.println("\n----^e vs f^----f vs e-----");
        // // testComp(f, e);
        // System.out.println("-------------------------------\n");

        // System.out.println("\n------------SMAL 1-------------");
        // testSmall();
        // // System.out.println("\n----^e vs f^----f vs e-----");
        // // testComp(f, e);
        // System.out.println("-------------------------------\n");

        // System.out.println("\n------------BIG  1-------------");
        // testBig();
        // System.out.println("-------------------------------\n");

        // System.out.println("\n------------XGCD 1-------------");
        // testXGCD(e, f);
        // // System.out.println("\n----^e vs f^----f vs e-----");
        // // testComp(f, e);
        // System.out.println("-------------------------------\n");

        // System.out.println("\n------------XGCD 2-------------");
        // largeXGCD();
        // // System.out.println("\n----^e vs f^----f vs e-----");
        // // testComp(f, e);
        // System.out.println("-------------------------------\n");

        // System.out.println("\n------------Right 1-------------");
        // testRight();
        // System.out.println("-------------------------------\n");
        //
        // System.out.println("\n------------Left 1-------------");
        // testLeft();
        // System.out.println("-------------------------------\n");

        // System.out.println("\n------------Mult N-------------");
        // testNegMult();
        // System.out.println("-------------------------------\n");

        System.out.println("\n------------Exp  3-------------");
        testExp();
        // System.out.println("\n----^e vs f^----f vs e-----");
        // testComp(f, e);
        System.out.println("-------------------------------\n");
    }

    public static String toBinaryString(byte n) {
        StringBuilder res = new StringBuilder();
        byte displayMask = (byte) 0x80;
        for (int i=1; i<=8; i++) {
            res.append((n & displayMask)==0?'0':'1');
            int temp = ((int) n & 0xFF) << 1;
            n = (byte) (temp & 0xFF);
        }

        return res.toString();
    }

    private static void testMult(byte[] a, byte[] b) {
        LargeInteger m1 = new LargeInteger(a);
        LargeInteger m2 = new LargeInteger(b);
        System.out.println("m1: " + m1);
        System.out.println("m2: " + m2);

        System.out.println("");
        System.out.println("");

        LargeInteger res = m1.multiply(m2);
        byte[] res_b = res.getVal();
        for(int i=0; i < res_b.length; i++) {
            System.out.print(toBinaryString(res_b[i]) /*+ " "*/);
        }
        System.out.println("");
    }

    public static void testDiv(byte[] a, byte[] b) {
        LargeInteger m1 = new LargeInteger(a);
        LargeInteger m2 = new LargeInteger(b);
        System.out.println("m1: " + m1);
        System.out.println("m2: " + m2);

        System.out.println("");
        System.out.println("");

        LargeInteger[] q_r = m1.divide(m1, m2);

        System.out.println("Quotient: ");
        System.out.println("q: " + q_r[0]);

        System.out.println("");
        System.out.println("Remainder: ");
        System.out.println("q: " + q_r[1]);
        System.out.println("");
    }

    private static void testXGCD(byte[] a, byte[] b) {
        for(int i=0; i < a.length; i++) {
            System.out.print(toBinaryString(a[i]) /*+ " "*/);
        }
        System.out.println("");

        for(int i=0; i < b.length; i++) {
            System.out.print(toBinaryString(b[i]) /*+ " "*/);
        }
        System.out.println("");
        System.out.println("");

        LargeInteger m1 = new LargeInteger(a);
        LargeInteger m2 = new LargeInteger(b);

        LargeInteger[] d_x_y = m1.XGCD(m2);

        System.out.println("d: " + d_x_y[0]);
        System.out.println("x: " + d_x_y[1]);
        System.out.println("y: " + d_x_y[2]);
    }

    private static void testSmall() {
        byte[] e = {(byte) 0, (byte) 0, (byte) 0, (byte) 0,
                    (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                    (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                    (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                    (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                    (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                    (byte) 0, (byte) 0, (byte) 0x1, (byte) 0x1};

        LargeInteger m1 = new LargeInteger(e);

        System.out.println("m1: " + m1);
        System.out.println("small: " + m1.smallify());
    }

    private static void testBig() {
        byte[] e = {(byte) 0x0, (byte) 0x1, (byte) 0x1, (byte) 0,
                    (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                    (byte) 0, (byte) 0, (byte) 0, (byte) 0,
                    (byte) 0, (byte) 0, (byte) 0x1, (byte) 0x1};

        LargeInteger m1 = new LargeInteger(e);

        System.out.println("m1: " + m1);
        System.out.println("small: " + m1.smallify());

        System.out.println("big: " + m1.bigify(32));
    }

    private static void largeDiv() {
        // byte[] e = {(byte) 0xFF, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F,
        //             (byte) 0x1F, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F,
        //             (byte) 0x1F, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F,
        //             (byte) 0x1F, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F,
        //             (byte) 0x1F, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F,
        //             (byte) 0x1F, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F,
        //             (byte) 0x1F, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F};

        byte[] e = {(byte) 50, (byte) 2};
        byte[] f = {(byte) 20, (byte) 2};

        // LargeInteger m1 = new LargeInteger(1024, new Random());
        // LargeInteger m2 = new LargeInteger(512, new Random());
        LargeInteger m1 = new LargeInteger(e);
        LargeInteger m2 = new LargeInteger(f);

        System.out.println("m1: " + m1);
        System.out.println("");
        System.out.println("m2: " + m2);
        System.out.println("");
        System.out.println("m1: " + new BigInteger(m1.getVal()));
        System.out.println("");
        System.out.println("m2: " + new BigInteger(m2.getVal()));
        System.out.println("");

        LargeInteger[] q_r = m1.divide(m1, m2);

        System.out.println("q: " + new BigInteger(q_r[0].getVal()));
        System.out.println("");
        System.out.println("r: " + new BigInteger(q_r[1].getVal()));
        System.out.println("");
    }

    private static void largeMult() {
        LargeInteger m1 = new LargeInteger(1024, new Random());
        LargeInteger m2 = new LargeInteger(1024, new Random());

        System.out.println("m1: " + m1);
        System.out.println("");
        System.out.println("m2: " + m2);
        System.out.println("");

        LargeInteger p = m1.multiply(m2);

        System.out.println("p: 0b" + p);
        System.out.println("");
    }

    private static void largeXGCD() {
        LargeInteger m1 = new LargeInteger(512, new Random());
        LargeInteger m2 = new LargeInteger(512, new Random());

        System.out.println("m1: " + m1);
        System.out.println("");
        System.out.println("m2: " + m2);
        System.out.println("");

        LargeInteger[] d_x_y = m1.XGCD(m2);

        System.out.println("d: " + d_x_y[0]);
        System.out.println("");
        System.out.println("x: " + d_x_y[1]);
        System.out.println("");
        System.out.println("y: " + d_x_y[2]);
        System.out.println("");
    }

    private static void testExp() {
        byte[] base = {(byte) 0x1F, (byte) 0xFF};
        byte[] mod = {(byte)  2, (byte) 0xFF};
        byte[] ex = {(byte) 0xFF, (byte) 0x89};

        LargeInteger m1 = new LargeInteger(base);
        LargeInteger m2 = new LargeInteger(mod);
        LargeInteger m3 = new LargeInteger(ex);

        System.out.println("base: " + m1);
        System.out.println("");
        System.out.println("mod: " + m2);
        System.out.println("");
        System.out.println("exp: " + m3);
        System.out.println("");

        LargeInteger exp = m1.modularExp(m3, m2);

        System.out.println("mod Exp: " + exp);
        System.out.println("");

    }

    private static void testNegMult() {
        byte[] e = {(byte) 0xFF, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F};
        byte[] f = {(byte) 0xFF, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F};

        // byte[] e = {(byte) 0x0, (byte) 0xE0/*, (byte) 0xE0, (byte) 0xE1*/};
        // byte[] f = {(byte) 0x0, (byte) 0xE0/*, (byte) 0xE0, (byte) 0xE1*/};

        LargeInteger m1 = new LargeInteger(e);
        LargeInteger m2 = new LargeInteger(f);

        System.out.println("m1: " + m1);
        System.out.println("");
        System.out.println("m2: " + m2);
        System.out.println("");

        LargeInteger prod = m1.multiply(m2);

        System.out.println("prod: " + prod);
    }

    private static void testRight() {
        byte[] e = {(byte) 0xFF, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F};
        byte[] f = {(byte) 0xFF, (byte) 0x1F, (byte) 0x0, (byte) 0x0};

        LargeInteger m1 = new LargeInteger(e);
        LargeInteger m2 = new LargeInteger(f);

        System.out.println("m1: " + m1);
        System.out.println("");
        System.out.println("m2: " + m2);
        System.out.println("");

        m1 = m1.shiftRight(2);
        m2 = m2.shiftRight(2);

        System.out.println(">> m1: " + m1);
        System.out.println("");
        System.out.println(">> m2: " + m2);
        System.out.println("");
    }

    private static void testLeft() {
        byte[] e = {(byte) 0xFF, (byte) 0x1F, (byte) 0x1F, (byte) 0x1F};
        byte[] f = {(byte) 0xFF, (byte) 0x1F, (byte) 0x0, (byte) 0x0};

        LargeInteger m1 = new LargeInteger(e);
        LargeInteger m2 = new LargeInteger(f);

        System.out.println("m1: " + m1);
        System.out.println("");
        System.out.println("m2: " + m2);
        System.out.println("");

        m1 = m1.shiftLeft(2);
        m2 = m2.shiftLeft(2);

        System.out.println("<< m1: " + m1);
        System.out.println("");
        System.out.println("<< m2: " + m2);
        System.out.println("");
    }

    private static void negDiv() {
        byte[] e = {(byte) 0xf9};
        byte[] f = {(byte) 0x28};

        LargeInteger m1 = new LargeInteger(e);
        LargeInteger m2 = new LargeInteger(f);

        System.out.println("m1: " + m1);
        System.out.println("");
        System.out.println("m2: " + m2);
        System.out.println("");

        LargeInteger[] q_r = m1.divide(m1, m2);

        System.out.println("q: " + new BigInteger(q_r[0].getVal()));
        System.out.println("");
        System.out.println("r: " + new BigInteger(q_r[1].getVal()));
        System.out.println("");
    }
}
