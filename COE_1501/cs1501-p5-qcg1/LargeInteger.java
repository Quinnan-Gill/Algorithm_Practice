import java.util.Random;
import java.math.BigInteger;

public class LargeInteger {

	private final byte[] ONE = {(byte) 1};
    private final byte[] ZERO = {(byte) 0};

	private byte[] val;

	/**
	 * Construct the LargeInteger from a given byte array
	 * @param b the byte array that this LargeInteger should represent
	 */
	public LargeInteger(byte[] b) {
		val = b;
	}

	/**
	 * Construct the LargeInteger by generatin a random n-bit number that is
	 * probably prime (2^-100 chance of being composite).
	 * @param n the bitlength of the requested integer
	 * @param rnd instance of java.util.Random to use in prime generation
	 */
	public LargeInteger(int n, Random rnd) {
		val = BigInteger.probablePrime(n, rnd).toByteArray();
	}

	/**
	 * Return this LargeInteger's val
	 * @return val
	 */
	public byte[] getVal() {
		return val;
	}

	/**
	 * Return the number of bytes in val
	 * @return length of the val byte array
	 */
	public int length() {
		return val.length;
	}

	/**
	 * Add a new byte as the most significant in this
	 * @param extension the byte to place as most significant
	 */
	public void extend(byte extension) {
		byte[] newv = new byte[val.length + 1];
		newv[0] = extension;
		for (int i = 0; i < val.length; i++) {
			newv[i + 1] = val[i];
		}
		val = newv;
	}

	/**
	 * If this is negative, most significant bit will be 1 meaning most
	 * significant byte will be a negative signed number
	 * @return true if this is negative, false if positive
	 */
	public boolean isNegative() {
		return (val[0] < 0);
	}

	/**
	 * Computes the sum of this and other
	 * @param other the other LargeInteger to sum with this
	 */
	public LargeInteger add(LargeInteger other) {
		byte[] a, b;
		// If operands are of different sizes, put larger first ...
		if (val.length < other.length()) {
			a = other.getVal();
			b = val;
		}
		else {
			a = val;
			b = other.getVal();
		}

		// ... and normalize size for convenience
		if (b.length < a.length) {
			int diff = a.length - b.length;

			byte pad = (byte) 0;
			if (b[0] < 0) {
				pad = (byte) 0xFF;
			}

			byte[] newb = new byte[a.length];
			for (int i = 0; i < diff; i++) {
				newb[i] = pad;
			}

			for (int i = 0; i < b.length; i++) {
				newb[i + diff] = b[i];
			}

			b = newb;
		}

		// Actually compute the add
		int carry = 0;
		byte[] res = new byte[a.length];
		for (int i = a.length - 1; i >= 0; i--) {
			// Be sure to bitmask so that cast of negative bytes does not
			//  introduce spurious 1 bits into result of cast
			carry = ((int) a[i] & 0xFF) + ((int) b[i] & 0xFF) + carry;

			// Assign to next byte
			res[i] = (byte) (carry & 0xFF);

			// Carry remainder over to next byte (always want to shift in 0s)
			carry = carry >>> 8;
		}

		LargeInteger res_li = new LargeInteger(res);

		// If both operands are positive, magnitude could increase as a result
		//  of addition
		if (!this.isNegative() && !other.isNegative()) {
			// If we have either a leftover carry value or we used the last
			//  bit in the most significant byte, we need to extend the result
			if (res_li.isNegative()) {
				res_li.extend((byte) carry);
			}
		}
		// Magnitude could also increase if both operands are negative
		else if (this.isNegative() && other.isNegative()) {
			if (!res_li.isNegative()) {
				res_li.extend((byte) 0xFF);
			}
		}

		// Note that result will always be the same size as biggest input
		//  (e.g., -127 + 128 will use 2 bytes to store the result value 1)
		return res_li;
	}

	/**
	 * Negate val using two's complement representation
	 * @return negation of this
	 */
	public LargeInteger negate() {
		byte[] neg = new byte[val.length];
		int offset = 0;

		// Check to ensure we can represent negation in same length
		//  (e.g., -128 can be represented in 8 bits using two's
		//  complement, +128 requires 9)
		if (val[0] == (byte) 0x80) { // 0x80 is 10000000
			boolean needs_ex = true;
			for (int i = 1; i < val.length; i++) {
				if (val[i] != (byte) 0) {
					needs_ex = false;
					break;
				}
			}
			// if first byte is 0x80 and all others are 0, must extend
			if (needs_ex) {
				neg = new byte[val.length + 1];
				neg[0] = (byte) 0;
				offset = 1;
			}
		}

		// flip all bits
		for (int i  = 0; i < val.length; i++) {
			neg[i + offset] = (byte) ~val[i];
		}

		LargeInteger neg_li = new LargeInteger(neg);

		// add 1 to complete two's complement negation
		return neg_li.add(new LargeInteger(ONE));
	}

	/**
	 * Implement subtraction as simply negation and addition
	 * @param other LargeInteger to subtract from this
	 * @return difference of this and other
	 */
	public LargeInteger subtract(LargeInteger other) {
		return this.add(other.negate());
	}

	/**
	 * Compute the product of this and other
	 * @param other LargeInteger to multiply by this
	 * @return product of this and other
	 */
	public LargeInteger multiply(LargeInteger other) {
        byte[] a, b;
		boolean negative = false;

        if (other.isNegative()) {
            a = other.negate().getVal();
            negative = !negative;
        } else {
            a = other.getVal();
        }

        if (isNegative()) {
            b = negate().getVal();
            negative = !negative;
        } else {
            b = getVal();
        }

		LargeInteger m1 = new LargeInteger(a);
		LargeInteger m2 = new LargeInteger(b);


		if (b.length < a.length) {
            b = padding(a, b);
        } else if(b.length > a.length){
            a = padding(b, a);
        }

        int size = 2 * b.length;

        byte[] partProds;
		LargeInteger product = new LargeInteger(new byte[size+1]);

        int shift = 0;
        for(int i=b.length-1; i >= 0; i--) {
            int result = 0;
			partProds = new byte[size + 1];
            int index = size;
            for(int j=a.length-1; j >= 0; j--) {
                result = ((int) a[i] & 0xFF) * ((int) b[j] & 0xFF) + result;
                partProds[index-shift] = (byte) (result & 0xFF);
                result = result >>> 8;
                index--;
            }
            partProds[index-shift] = (byte) (result & 0xFF);
            shift++;

			product = product.add(new LargeInteger(partProds));
        }

		product = product.smallify();

        if(negative) {
            product = product.negate();
        }

        return product;
	}

	public LargeInteger smallify() {
		byte[] new_val = new byte[1];
		boolean nonzero = false;
		int index = 0;

		for(int i=0; i < val.length; i++) {
			if (val[i] != 0 && !nonzero) {
				nonzero = true;
				new_val = new byte[val.length - i+1];
				index = 1;
			}
			if (nonzero) {
				new_val[index] = val[i];
				index++;
			}
		}
		return new LargeInteger(new_val);
	}

	public LargeInteger bigify(int size) {
		int diff = size - val.length;

		if (diff < 0) throw new IllegalArgumentException("loss of percision");
		byte[] new_val = new byte[size];
		for(int i=0; i < val.length; i++) {
			new_val[diff+i] = val[i];
		}
		return new LargeInteger(new_val);
	}

    private byte[] padding(byte[] a, byte[] b) {
        int diff = a.length - b.length;

        byte pad = (byte) 0;
		assert !(b[0] < 0);

        byte[] newb = new byte[a.length];
        for (int i = 0; i < diff; i++) {
            newb[i] = pad;
        }

        for (int i = 0; i < b.length; i++) {
            newb[i + diff] = b[i];
        }

        return newb;
    }

	/**
	 * Run the extended Euclidean algorithm on this and other
	 * @param other another LargeInteger
	 * @return an array structured as follows:
	 *   0:  the GCD of this and other
	 *   1:  a valid x value
	 *   2:  a valid y value
	 * such that this * x + other * y == GCD in index 0
	 */
    public LargeInteger[] XGCD(LargeInteger other) {
        return XGCD(this, other);
    }

	private LargeInteger[] XGCD(LargeInteger p, LargeInteger q) {
		LargeInteger[] vals = new LargeInteger[3];
		LargeInteger[] q_r = new LargeInteger[2];

		p = p.smallify();
		q = q.smallify();

		if(q.isZero()) {
			return new LargeInteger[] {p, new LargeInteger(ONE),
										new LargeInteger(ZERO)};
		}

		q_r = divide(p, q);
		q_r[0] = q_r[0].smallify();
		q_r[1] = q_r[1].smallify();

		vals = XGCD(q, q_r[1]);
		LargeInteger d = vals[0];
		LargeInteger a = vals[2];

		LargeInteger b = vals[1].subtract(q_r[0].multiply(vals[2]));

		return new LargeInteger[] {d, a, b};
	}

    public LargeInteger[] divide(LargeInteger n, LargeInteger d) {
        LargeInteger[] q_r = new LargeInteger[2];
        if (d.isZero()) {
            throw new ArithmeticException("divide by zero");
        }
        if (d.isNegative()) {
            q_r = divide(n, d.negate());
            return q_r;
        }
        if (n.isNegative()) {
            q_r = divide(n.negate(), d);
            if (q_r[1].isZero()) {
                q_r[0] = q_r[0].negate();
                q_r[1] = new LargeInteger(ZERO);
                return q_r;
            } else {
                q_r[0] = q_r[0].negate();
                q_r[1] = d.subtract(q_r[1]);
                return q_r;
            }
        }
        return unsigned_div2(n, d);
    }

    private LargeInteger[] unsigned_div(LargeInteger n, LargeInteger d) {
        LargeInteger[] q_r = new LargeInteger[2];
		LargeInteger r_next;
		LargeInteger d_neg = d.negate();

        q_r[0] = new LargeInteger(ZERO);
		q_r[1] = n;
		r_next = q_r[1].add(d_neg);
        while (!r_next.isNegative()) {
            q_r[0] =  q_r[0].add(new LargeInteger(ONE));
            q_r[1] = r_next.smallify();
			r_next = r_next.add(d_neg);
        }
        return q_r;
    }

	private LargeInteger[] unsigned_div2(LargeInteger n, LargeInteger d) {
		LargeInteger[] q_r = new LargeInteger[2];

		n = n.smallify();
		d = d.smallify();

		int num = n.getVal().length;
		int den = d.getVal().length;

		assert !n.isNegative();
		assert !d.isNegative();

		if (num < den) {
			q_r[0] = new LargeInteger(ZERO);
			q_r[1] = n;
			return q_r;
		}

		byte[] quo = new byte[num];

		d = d.shiftLeft(num-1);

		LargeInteger r_next = new LargeInteger(ZERO);

		for(int i=0; i < quo.length; i++) {
			r_next = n.subtract(d);
			while(!r_next.isNegative()) {
				n = r_next;
				quo[i] += (byte) 1;
				r_next = n.subtract(d);
			}
			d = d.shiftRight(1);
		}

		q_r[0] = new LargeInteger(quo);
		q_r[1] = n;


        return q_r;
    }

	public LargeInteger shiftLeft(int l) {
		byte[] new_val = new byte[val.length + l];
		for (int i=0; i < val.length; i++) {
			new_val[i] = val[i];
		}

		return new LargeInteger(new_val);
	}

	public LargeInteger shiftRight(int r) {
		if (val.length < r) throw new IllegalArgumentException("val.length < r");
		byte[] new_val = new byte[val.length - r];
		for(int i=0; i < new_val.length; i++) {
			new_val[i] = val[i];
		}

		return new LargeInteger(new_val);
	}

    private boolean isZero() {
        for(int i=val.length-1; i >= 0; i--) {
            if (val[i] != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compute the result of raising this to the power of y mod n
     * @param y exponent to raise this to
     * @param n modulus value to use
     * @return this^y mod n
     */
    public LargeInteger modularExp(LargeInteger y, LargeInteger n) {
		LargeInteger ans = new LargeInteger(ONE);

		byte[] exp = y.getVal();

		int index = 0;
		int x = 7;

		int size = exp.length*8;

    	for (int i=0; i < size; i++){
			progressPercentage(i, size);
			byte value = exp[index];

			ans = ans.multiply(ans);
			ans = ans.divide(ans, n)[1];
			ans = ans.smallify();

			if ((value & (1L << x)) != 0) {
				ans = ans.multiply(this);
				ans = ans.divide(ans, n)[1];
			}
			if (x == 0) {
				index++;
				x = 7;
			} else {
				x--;
			}
        }

		System.out.println("\n");

        return ans;
    }

	public void progressPercentage(int done, int total) {
        int size = 50;
        String iconLeftBoundary = "[";
        String iconDone = "=";
        String iconRemain = ".";
        String iconRightBoundary = "]";

        if (done > total) {
            throw new IllegalArgumentException();
        }
        int donePercents = (100 * done) / total;
        int doneLength = size * donePercents / 100;

        StringBuilder bar = new StringBuilder(iconLeftBoundary);
        for (int i = 0; i < size; i++) {
            if (i < doneLength) {
                bar.append(iconDone);
            } else {
                bar.append(iconRemain);
            }
        }
        bar.append(iconRightBoundary);

        System.out.print("\r" + bar + " " + donePercents + "%");

        if (done == total) {
            System.out.print("\n");
        }
    }

    private String toBinaryString(byte n) {
        StringBuilder res = new StringBuilder();
        byte displayMask = (byte) 0x80;
        for (int i=1; i<=8; i++) {
            res.append((n & displayMask)==0?'0':'1');
            int temp = ((int) n & 0xFF) << 1;
            n = (byte) (temp & 0xFF);
        }

        return res.toString();
    }

	public boolean isEqual(LargeInteger other){
		byte[] a = other.getVal();
		assert val.length == a.length;

		for(int i=0; i < val.length; i++) {
			if ((val[i] & 0xFF) != (a[i] & 0xFF)) {
				return false;
			}
		}

		return true;
	}

	public boolean isOne() {
		byte[] one = smallify().getVal();
		return one.length == 2 && one[1] == 1;
	}

    public String toString() {
        byte[] a = getVal();
        String result = "0b";
        for(int i=0; i < a.length; i++) {
            result += toBinaryString(a[i]);
        }
        return result;
    }
}
