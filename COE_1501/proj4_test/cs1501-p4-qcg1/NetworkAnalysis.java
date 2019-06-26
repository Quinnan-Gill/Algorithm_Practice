import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.*;

public class NetworkAnalysis {
    private String filePath;
    private NetworkGraph graph;

    public NetworkAnalysis(String filename) {
        filePath = new File("").getAbsolutePath() + "/";
        loadGraph(filename);
        // test();
        terminal();
    }

    public void loadGraph(String filename) {
        String word;
        File file;
        BufferedReader reader = null;

        try {
            file = new File(filePath + filename);
            reader = new BufferedReader(new FileReader(file));

            int i = 0;
            while ((word = reader.readLine()) != null) {
                if (i == 0) {
                    graph = new NetworkGraph(Integer.parseInt(word));
                    i++;
                } else {
                    if(word.matches("^(#).*$")) continue;
                    graph.addEdge(new Edge(word));
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // graph.printGraph();
    }

    private void test() {
        System.out.println("-----Testing Copper Only--------");
        CopperOnly co = new CopperOnly(graph);
        if (co.copperConnected())
            System.out.println("It is copper connected");
        else {
            System.out.println("It is NOT copper connected");
        }
        System.out.println("--------------------------------\n");

        // System.out.println("-----Remove Two Routers--------");
        // ArticulationPoint ap = new ArticulationPoint(graph);
        // System.out.println(ap.numOfArticulationPoints());
        // System.out.println("--------------------------------\n");

        System.out.println("-----Shortest Path--------");
        TriConnected tc = new TriConnected(graph);
        System.out.println(tc.hasArticulationPoints());
        System.out.println("--------------------------------\n");

        System.out.println("-----Minimum Spanning Tree--------");
        MinLatencySPT mspt = new MinLatencySPT(graph);
        mspt.printTree();
        System.out.println("--------------------------------\n");

        System.out.println("-----Shortest Path--------");
        Djikstra dj = new Djikstra(graph, 0);
        dj.printPath(4);
        System.out.println("--------------------------------\n");
    }

    private void terminal() {
        String input = "";
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                printMenu();
                input = reader.readLine();
                System.out.println("");

                if (!input.matches("[1-5]{1}")) {
                    System.out.println("\nINVALID INPUT: Enter value between 1 and 5\n");
                    continue;
                }
                switch(Integer.parseInt(input)) {
                    case 1:
                        System.out.println("\n-----Shortest Path--------");
                        int start = -1;
                        int end = -1;
                        do {
                            System.out.print("Start Vertex: ");
                            start = Integer.parseInt(reader.readLine());
                            if (graph.invalidVertex(start)) {
                                System.out.println("Please add a vertex below " + graph.V());
                            }
                        } while (graph.invalidVertex(start) && start > -1);

                        do {
                            System.out.print("End Vertex: ");
                            end = Integer.parseInt(reader.readLine());
                            if (graph.invalidVertex(end)) {
                                System.out.println("Please add a vertex below " + graph.V());
                            }
                        } while (graph.invalidVertex(end) && end > -1);
                        System.out.println("");

                        Djikstra dj = new Djikstra(graph, start);
                        dj.printPath(end);
                        System.out.println("--------------------------------\n");
                        break;
                    case 2:
                        System.out.println("\n-----Testing Copper Only--------");
                        CopperOnly co = new CopperOnly(graph);
                        if (co.copperConnected())
                            System.out.println("It is copper connected");
                        else {
                            System.out.println("It is NOT copper connected");
                        }
                        System.out.println("--------------------------------\n");
                        break;
                    case 3:
                        System.out.println("\n-----Minimum Spanning Tree--------");
                        MinLatencySPT mspt = new MinLatencySPT(graph);
                        mspt.printTree();
                        System.out.println("--------------------------------\n");
                        break;
                    case 4:
                        System.out.println("\n-----Two Routers Fail--------");
                        TriConnected tc = new TriConnected(graph);
                        if (tc.hasArticulationPoints()) {
                            System.out.println("If two routers fail then graph is disconnected");
                        } else {
                            System.out.println("If two routers fail then graph is still connected");
                        }
                        System.out.println("--------------------------------\n");
                        break;
                    case 5:
                        System.out.println("Thank you");
                        return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printMenu() {
        System.out.println("1) The Lowst Latency Path Between Two Routers");
        System.out.println("2) The Graph is Only Copper Connect");
        System.out.println("3) Minimum Average Latency Spanning Tree");
        System.out.println("4) Still connect if two vertices fail");
        System.out.println("5) Quit");
        System.out.print("\n>");
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java NetworkAnalysis [data_filename]");
            return;
        }
        NetworkAnalysis na = new NetworkAnalysis(args[0]);
    }
}
