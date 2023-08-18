package network_optimize;

import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;
import java.util.Map;

public interface operate<T> {
    public void setAlpha(int alpha);
    public void fill_graph();
    public void init_loads(boolean if_all_zero);
    public void init_loads_with_map(Map<T, Integer> Loads);
    public void show_graph();
    public void show_loads();
    public void optimize(WeightedGraph<T, DefaultEdge> graph, Map<T, Integer> Loads);

}
