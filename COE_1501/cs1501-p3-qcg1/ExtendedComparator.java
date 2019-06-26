import java.util.Comparator;

public interface ExtendedComparator<T, Val> extends Comparator<T>{
    Val getPrimaryValue(T a);
    Val updateVal(T a, Val b);
    String getId(T a);
    void test();
}
