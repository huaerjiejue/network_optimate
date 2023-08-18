package network_optimize;

import org.jgrapht.DirectedGraph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Map;

public interface network_optimize<T> {
    public void setAlpha(double alpha);
    public void fill_graph();
    public void init_loads(boolean if_all_zero);
    public void init_loads_with_map(Map<T, Double> Loads);
    public void show_graph();
    public void show_loads();
    public void show_alpha();
    public void optimize(DirectedGraph<T, DefaultEdge> graph, Map<T, Double> Loads);
    public void optimize_shunt(DirectedGraph<T, DefaultEdge> graph, Map<T, Double> Loads);

}
