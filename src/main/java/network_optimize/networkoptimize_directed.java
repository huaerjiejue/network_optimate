package network_optimize;

import org.jetbrains.annotations.NotNull;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class networkoptimize_directed<T> extends networkoptimize_undirected<T> implements network_optimize<T> {

    /**
     * Difference with undirected graph:
     * in this graph, the flow from different direction between two nodes is different.
     * such as,
     * the flow from node 1 to node 2 is different from the flow from node 2 to node 1.
     */
    private Map<Map<T, T>, Double> Flows;

    public networkoptimize_directed() {
        super();
        Flows = new java.util.HashMap<>();
    }

    private void fill_Flows(boolean if_all_zero) {
        if (if_all_zero) {
            for (int i = 0; i < graph.vertexSet().size(); i++) {
                for (int j = 0; j < graph.vertexSet().size(); j++) {
                    if (i != j) {
                        Map<T, T> temp = new java.util.HashMap<>();
                        temp.put((T) graph.vertexSet().toArray()[i], (T) graph.vertexSet().toArray()[j]);
                        Flows.put(temp, (double) 0);
                    }
                }
            }
        } else {
            System.out.println("Enter the flows of each edge :");
            java.util.Scanner sc = new java.util.Scanner(System.in);
            for (int i = 0; i < graph.vertexSet().size(); i++) {
                for (int j = 0; j < graph.vertexSet().size(); j++) {
                    if (i != j) {
                        Map<T, T> temp = new java.util.HashMap<>();
                        temp.put((T) graph.vertexSet().toArray()[i], (T) graph.vertexSet().toArray()[j]);
                        System.out.println("Enter the flow of edge" + i + "->" + j + ":");
                        double flow = sc.nextDouble();
                        Flows.put(temp, flow);
                    }
                }
            }
        }
    }


    private void fill_Flows_with_map(Map<Map<T, T>, Double> Flows) {
        // 清除原有的Flows
        this.Flows.clear();
        if (Flows == null) {
            throw new NullPointerException("Flows is null");
        }
        this.Flows.putAll(Flows);
    }

    @Override
    public void fill_graph() {
        super.fill_graph();
    }

    @Override
    public void setAlpha(double alpha) {
        super.setAlpha(alpha);
    }

    @Override
    public void init_loads(boolean if_all_zero) {
        super.init_loads(if_all_zero);
    }


    @Override
    public void init_loads_with_map(Map<T, Double> Loads) {
        super.init_loads_with_map(Loads);
    }


    @Override
    public void show_graph() {
        super.show_graph();
    }


    @Override
    public void show_loads() {
        super.show_loads();
    }


    @Override
    public void show_alpha() {
        super.show_alpha();
    }

    @Override
    public void optimize(@NotNull DirectedGraph<T, DefaultEdge> graph, Map<T, Double> Loads) {
        int num = super.get_num_of_graph();
        if (num == 0) {
            throw new NullPointerException("graph is null");
        }
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (i == j) {
                    continue;
                }
                T source = (T) graph.vertexSet().toArray()[i];
                T target = (T) graph.vertexSet().toArray()[j];
                // 从i到j的流量
                Map<T, T> temp = new java.util.HashMap<>();
                temp.put(source, target);
                double flow = Flows.get(temp);
                // 从i到j的所有最短路径
                List<GraphPath<T, DefaultEdge>> paths = super.get_all_shortest_paths(source, target);
                List<Double> costs = new ArrayList<>();
                for (GraphPath<T, DefaultEdge> path : paths) {
                    double cost = compute_cost(path.getVertexList());
                    costs.add(cost);
                }
                // cost最小的路径及其索引
                double min_cost = java.util.Collections.min(costs);
                int index = costs.indexOf(min_cost);
                // 更新Loads
                List<T> vertexList = paths.get(index).getVertexList();
                super.update_loads(vertexList, flow);
                System.out.println("the shortest path is :" + paths.get(index).getVertexList());
                System.out.println("the cost is :" + min_cost);
                System.out.println(Loads);
            }
        }
    }
}
