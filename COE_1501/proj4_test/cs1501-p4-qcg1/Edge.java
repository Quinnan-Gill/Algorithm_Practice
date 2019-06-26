class Edge implements Comparable<Edge>{
    public int from;
    public int to;
    public boolean copper;
    public int bandwidth;
    public int length;

    private final int C_L = 230000000;
    private final int F_L = 200000000;

    public Edge() {
        from = -1;
        to = -1;
        copper = false;
        bandwidth = 0;
        length = 0;
    }

    public Edge(String unparsed) {
        String[] vals = unparsed.split(" ");

        from = Integer.parseInt(vals[0]);
        to = Integer.parseInt(vals[1]);
        if (vals[2].compareTo("copper") == 0) copper = true;
        bandwidth = Integer.parseInt(vals[3]);
        length = Integer.parseInt(vals[4]);
    }

    public double weight() {
        if (copper)
            return (double) length / (double) C_L;
        else
            return (double) length / (double) F_L;
    }

    public int either() {
        return from;
    }

    public int other(int vertex) {
        if      (vertex == from) return to;
        else if (vertex == to) return from;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    public int compareTo(Edge that) {
        return Double.compare(this.weight(), that.weight());
    }

    public void printEdge() {
        System.out.println("from: " + from);
        System.out.println("\tto: " + to);
        System.out.println("\tcopper: " + copper);
        System.out.println("\tweight: " + weight());
        System.out.println("\tbandwidth: " + bandwidth);
    }

    public String toString() {
        return "from: " + from + " to: " + to;
    }
}
