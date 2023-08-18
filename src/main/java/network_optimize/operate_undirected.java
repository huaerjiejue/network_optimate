package network_optimize;

import org.jetbrains.annotations.NotNull;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class operate_undirected<T> implements operate<T> {

    private DirectedGraph<T, DefaultEdge> graph;
    private Map<T, Integer> Loads;
    private int alpha;

    public operate_undirected() {
        this.graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        this.Loads = null;
        this.alpha = 1;
    }

    @Override
    public void init_loads(boolean if_all_zero) {
        if (if_all_zero) {
            for (int i = 0; i < graph.vertexSet().size(); i++) {
                Loads.put((T) graph.vertexSet().toArray()[i], 0);
            }
        } else {
            System.out.println("Enter the loads of each node :");
            Scanner sc = new Scanner(System.in);
            for (int i = 0; i < graph.vertexSet().size(); i++) {
                System.out.println("Enter the load of node" + i + ":");
                int load = sc.nextInt();
                Loads.put((T) graph.vertexSet().toArray()[i], load);
            }
        }
    }

    @Override
    public void init_loads_with_map(Map<T, Integer> Loads) {
        // 清除原有的Loads
        this.Loads.clear();
        if (Loads == null) {
            throw new NullPointerException("Loads is null");
        }
        this.Loads.putAll(Loads);
    }

    public int compute_cost(@NotNull List<T> path) {
        int cost = 0;
        int length = path.size();
        cost += (length - 1);
        for (int i = 1; i < length - 1; i++) {
            cost += Loads.get(path.get(i)) * alpha;
        }
        return cost;
    }


    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }


    public void update_loads(@NotNull List<T> path) {
        int length = path.size();
        for (int i = 1; i < length - 1; i++) {
            Loads.put(path.get(i), Loads.get(path.get(i)) + 1);
        }
    }


    @Override
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
        System.out.println("Enter the number of edges :");
        int m = sc.nextInt();
        for (int i = 1; i <= m; i++) {
            System.out.println("Enter the edge" + i + ":");
            T node1 = (T) sc.next();
            T node2 = (T) sc.next();
            // 无向图，使用DirectedGraph为了下面的操作
            graph.addEdge(node1, node2);
            graph.addEdge(node2, node1);
        }
    }

    @Override
    public void show_graph() {
        System.out.println(graph);
    }


    @Override
    public void show_loads() {
        System.out.println(Loads);
    }


    public List<GraphPath<T, DefaultEdge>> get_all_shortest_paths(T source, T destination) {
        AllDirectedPaths<T, DefaultEdge> allDirectedPaths = new AllDirectedPaths<>(graph);
        List<GraphPath<T, DefaultEdge>> paths = allDirectedPaths.getAllPaths(source, destination, true, null);
        return paths;
    }


    @Override
    public void optimize(@NotNull WeightedGraph<T, DefaultEdge> graph, Map<T, Integer> Loads) {
        int num = graph.vertexSet().size();
        for(int i = 0; i < num; i++) {
            for(int j = i + 1; j < num; j++) {
                T source = (T) graph.vertexSet().toArray()[i];
                T destination = (T) graph.vertexSet().toArray()[j];
                // 找到所有最短路径
                List<GraphPath<T, DefaultEdge>> paths = get_all_shortest_paths(source, destination);
                List<Integer> costs = new java.util.ArrayList<>();
                // 找到cost最小的路径
                for(GraphPath<T, DefaultEdge> path : paths) {
                    costs.add(compute_cost(path.getVertexList()));
                }
                int min_cost = java.util.Collections.min(costs);
                int index = costs.indexOf(min_cost);
                // 更新Loads
                update_loads(paths.get(index).getVertexList());
            }
        }


    }



}
