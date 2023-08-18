package network_optimize;

import java.util.Map;

public class networkoptimize_directed<T> extends networkoptimize_undirected<T> implements network_optimize<T> {
    public networkoptimize_directed() {
        super();
    }

    @Override
    public void fill_graph() {
        super.fill_graph();
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


}
