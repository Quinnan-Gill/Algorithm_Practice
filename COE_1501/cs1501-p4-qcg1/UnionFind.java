public class UnionFind {
    private int[] id;
    private int[] size;

    public UnionFind(int n) {
        id = new int[n];
        size = new int[size];
        for (int i=0; i < n; i++) {
            id[i] = i;
        }
    }
}
