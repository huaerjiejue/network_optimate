package network_optimate;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.util.Scanner;

public class optimate<T> {
    private WeightedGraph<T, DefaultEdge> graph;

    public optimate() {
        this.graph = new DirectedWeightedMultigraph<>(DefaultEdge.class);
    }

    public void fill_graph() {
        System.out.println("Enter the number of nodes :");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("Enter the node :");
            T node = (T) sc.next();
            graph.addVertex(node);
        }
        System.out.println("Is the graph has weighted edges ? (y/n)");
        String ans = sc.next();
        if (ans.equals("y")) {
            System.out.println("Enter the number of edges :");
            int m = sc.nextInt();
            for (int i = 0; i < m; i++) {
                System.out.println("Enter the source node :");
                T source = (T) sc.next();
                System.out.println("Enter the destination node :");
                T destination = (T) sc.next();
                System.out.println("Enter the weight of the edge :");
                int weight = sc.nextInt();
                graph.setEdgeWeight(graph.addEdge(source, destination), weight);
            }
        } else {
            System.out.println("Enter the number of edges :");
            int m = sc.nextInt();
            for (int i = 0; i < m; i++) {
                System.out.println("Enter the source node :");
                T source = (T) sc.next();
                System.out.println("Enter the destination node :");
                T destination = (T) sc.next();
                graph.addEdge(source, destination);
            }
        }
    }


    public static void main(String[] args) {
        optimate<String> opt = new optimate<>();
        System.out.println("Fill the graph :");
    }
}
