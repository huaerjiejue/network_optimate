package network_optimate;

import org.jetbrains.annotations.NotNull;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.*;

import java.util.List;
import java.util.Scanner;

public class operate_undirected<T> {

    private WeightedGraph<T, DefaultEdge> graph;
    private List<Integer> Loads;
    private int alpha;

    public operate_undirected() {
        this.graph = new WeightedMultigraph<>(DefaultEdge.class);
        this.Loads = null;
        this.alpha = 1;
    }

    public List<Integer> init_loads(boolean if_all_zero) {
        if (if_all_zero) {
            for (int i = 0; i < graph.vertexSet().size(); i++) {
                Loads.add(0);
            }
        } else {
            for (int i = 0; i < graph.vertexSet().size(); i++) {
                System.out.println("Enter the load of node" + (i + 1) + ":");
                Scanner sc = new Scanner(System.in);
                int load = sc.nextInt();
                Loads.add(load);
            }
        }
        return Loads;
    }

    public int compute_cost(@NotNull List<Integer> path) {
        int cost = 0;
        int length = path.size();
        cost += (length - 1);
        for (int i = 1; i < length - 1; i++) {
            cost += (alpha * Loads.get(path.get(i)) - 1);
        }
        return cost;
    }


    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }


    public void update_loads(@NotNull List<Integer> path) {
        int length = path.size();
        for (int i = 1; i < length - 1; i++) {
            Loads.set(path.get(i) - 1, Loads.get(path.get(i) - 1) + 1);
        }
    }


    public void fill_graph() {
        // Vertex
        System.out.println("Enter the number of nodes :");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            System.out.println("Enter the node" + i + ":");
            T node = (T) sc.next();
            graph.addVertex(node);
        }

        // Edge
        System.out.println("Is the graph has weighted edges ? (y/n)");
        String ans = sc.next();
        if (ans.equals("y")) {
            System.out.println("Enter the number of edges :");
            int m = sc.nextInt();
            for (int i = 1; i <= m; i++) {
                System.out.println("the edge" + i + ":");
                System.out.println("Enter the source node of :");
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
            for (int i = 1; i <= m; i++) {
                System.out.println("the edge" + i + ":");
                System.out.println("Enter the source node of :");
                T source = (T) sc.next();
                System.out.println("Enter the destination node :");
                T destination = (T) sc.next();
                graph.addEdge(source, destination);
            }
        }
    }

    public void show_graph() {
        System.out.println(graph);
    }


    public void show_loads() {
        System.out.println(Loads);
    }


    public static void main(String[] args) {
        operate_undirected<String> opt = new operate_undirected<>();  // 无向图
        System.out.println("Fill the graph :");
        System.out.println("Fill the graph :");
        opt.fill_graph();
        System.out.println("The graph is :");
        opt.show_graph();
    }
}
