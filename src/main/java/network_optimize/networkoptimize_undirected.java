package network_optimize;

import org.jetbrains.annotations.NotNull;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class networkoptimize_undirected<T> implements network_optimize<T> {

    public DirectedGraph<T, DefaultEdge> graph;
    public Map<T, Double> Loads;
    public Double alpha;

    public networkoptimize_undirected() {
        this.graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        this.alpha = 1.0;
        this.Loads = new java.util.HashMap<>();
    }

    @Override
    public void init_loads(boolean if_all_zero) {
        if (if_all_zero) {
            for (int i = 0; i < graph.vertexSet().size(); i++) {
                Loads.put((T) graph.vertexSet().toArray()[i], (double) 0);
            }
        } else {
            System.out.println("Enter the loads of each node :");
            Scanner sc = new Scanner(System.in);
            for (int i = 0; i < graph.vertexSet().size(); i++) {
                System.out.println("Enter the load of node" + i + ":");
                int load = sc.nextInt();
                Loads.put((T) graph.vertexSet().toArray()[i], (double) load);
            }
        }
    }

    @Override
    public void init_loads_with_map(Map<T, Double> Loads) {
        // 清除原有的Loads
        this.Loads.clear();
        if (Loads == null) {
            throw new NullPointerException("Loads is null");
        }
        this.Loads.putAll(Loads);
    }

    public double compute_cost(@NotNull List<T> path) {
        int cost = 0;
        int length = path.size();
        cost += (length - 1);
        for (int i = 1; i < length - 1; i++) {
            cost += Loads.get(path.get(i)) * alpha;
        }
        return cost;
    }


    @Override
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }


    public void update_loads(@NotNull List<T> path, double weight) {
        int length = path.size();
        for (int i = 1; i < length - 1; i++) {
            Loads.put(path.get(i), Loads.get(path.get(i)) + weight);
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
        System.out.println("Format : node1 node2");
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

    @Override
    public void show_alpha() {
        System.out.println(alpha);
    }


    public List<GraphPath<T, DefaultEdge>> get_all_shortest_paths(T source, T destination) {
        AllDirectedPaths<T, DefaultEdge> allDirectedPaths = new AllDirectedPaths<>(graph);
        List<GraphPath<T, DefaultEdge>> paths = allDirectedPaths.getAllPaths(source, destination, true, null);
        return paths;
    }


    public GraphPath<T, DefaultEdge> get_shortest_path(T source, T destination) {
        DijkstraShortestPath<T, DefaultEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);
        GraphPath<T, DefaultEdge> path = dijkstraShortestPath.getPath(source, destination);
        return path;
    }

    public int get_num_of_graph() {
        return graph.vertexSet().size();
    }


    @Override
    public void optimize(@NotNull DirectedGraph<T, DefaultEdge> graph, Map<T, Double> Loads) {
        int num = get_num_of_graph();
        for (int i = 0; i < num; i++) {
            for (int j = i + 1; j < num; j++) {
                T source = (T) graph.vertexSet().toArray()[i];
                T destination = (T) graph.vertexSet().toArray()[j];
                // 找到所有最短路径
                List<GraphPath<T, DefaultEdge>> paths = get_all_shortest_paths(source, destination);
                List<Double> costs = new java.util.ArrayList<>();
                // 找到cost最小的路径
                for (GraphPath<T, DefaultEdge> path : paths) {
                    costs.add(compute_cost(path.getVertexList()));
                }
                Double min_cost = java.util.Collections.min(costs);
                int index = costs.indexOf(min_cost);
                // 更新Loads
                update_loads(paths.get(index).getVertexList(), 1);
                System.out.println("the shortest path is :" + paths.get(index).getVertexList());
                System.out.println("the cost is :" + min_cost);
                System.out.println(Loads);
            }
        }

    }


    @Override
    public void optimize_shunt(@NotNull DirectedGraph<T, DefaultEdge> graph, Map<T, Double> Loads) {
        int num = graph.vertexSet().size();
        for (int i = 0; i < num; i++) {
            for (int j = i + 1; j < num; j++) {
                T source = (T) graph.vertexSet().toArray()[i];
                T destination = (T) graph.vertexSet().toArray()[j];
                // 找到所有最短路径集合
                List<GraphPath<T, DefaultEdge>> paths_all = get_all_shortest_paths(source, destination);
                // 找到最短路径及其长度
                GraphPath<T, DefaultEdge> shortest_path = get_shortest_path(source, destination);
                int len = shortest_path.getLength();
                List<GraphPath<T, DefaultEdge>> paths = new java.util.ArrayList<>();
                // 找到所有长度为len的路径
                for (GraphPath<T, DefaultEdge> path : paths_all) {
                    if (path.getLength() == len) {
                        paths.add(path);
                    }
                }
                List<Double> costs = new java.util.ArrayList<>();
                int sum = 0;
                for (GraphPath<T, DefaultEdge> path : paths) {
                    costs.add(compute_cost(path.getVertexList()));
                    sum += compute_cost(path.getVertexList());
                }
                // 每个节点都要有流量经过，负载越大，流量越小,总和为1
                int size = paths.size();
                for (int k = 0; k < size; k++) {
//                    System.out.println(paths.get(k).getVertexList());
                    double weight = (double) (sum - costs.get(k)) / (sum * (size - 1));
//                    System.out.println(weight);
                    update_loads(paths.get(k).getVertexList(), weight);
                }
                // print
                System.out.println("Load is :" + Loads);


            }
        }

    }


}
