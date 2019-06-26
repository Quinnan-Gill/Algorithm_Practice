public class PriceComparator implements ExtendedComparator<Car, Integer>{
    public int compare(Car a, Car b) {
        if (a.price < b.price) {
            return -1;
        } else if (a.price == b.price) {
            return 0;
        } else {
            return 1;
        }
    }
    public Integer getPrimaryValue(Car a) {
        return new Integer(a.price);
    }
    public Integer updateVal(Car a, Integer b) {
        int old_val = a.price;
        a.price = b.intValue();
        return new Integer(old_val);
    }
    public String getId(Car a) {
        return a.vin;
    }
    public void test() {
        System.out.println("test");
    }
}
