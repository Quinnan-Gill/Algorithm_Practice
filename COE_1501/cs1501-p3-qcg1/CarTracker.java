public class CarTracker {
    public static void main(String args[]) {
        // IndirectMinPQ<Integer> pq = new IndirectMinPQ<Integer>(new PriceComparator());
        // pq.insert(new Car("dog", 12));
        // pq.insert(new Car("cat", 14));
        // pq.insert(new Car("pig", 15));
        // pq.insert(new Car("panda", 1));
        // System.out.println("");
        // pq.printPQ();
        // // pq.delMin();
        // pq.remove("pig");
        //
        // System.out.println("");
        // pq.printPQ();
        //
        // System.out.println("");
        // pq.update("panda", new Integer(27));
        // pq.printPQ();

        CarDatabase cd = new CarDatabase();
        cd.load();
        cd.run();
    }
}
