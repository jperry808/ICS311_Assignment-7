import java.util.*;

public class Graph {
    private final Map<String, List<String>> adj = new HashMap<>();

    // Ensure a node exists in the adjacency map.
    private void ensure(String id) {
        adj.computeIfAbsent(id, k -> new ArrayList<>());
    }

    // Add an undirected edge between a and b (communication path).
    public void addEdge(String a, String b) {
        ensure(a);
        ensure(b);
        adj.get(a).add(b);
        adj.get(b).add(a);
    }

    // Breadth First Search algorithm used to find user connections.
    public List<String> shortestPath(String src, String dst) {
        if (src == null || dst == null) return Collections.emptyList();
        if (!adj.containsKey(src) || !adj.containsKey(dst)) return Collections.emptyList();
        if (src.equals(dst)) return List.of(src);

        Queue<String> q = new ArrayDeque<>();
        Map<String, String> parent = new HashMap<>();
        q.add(src);
        parent.put(src, null);

        while (!q.isEmpty()) {
            String u = q.poll();
            for (String v : adj.getOrDefault(u, List.of())) {
                if (!parent.containsKey(v)) {
                    parent.put(v, u);
                    if (v.equals(dst)) {
                        LinkedList<String> path = new LinkedList<>();
                        for (String x = dst; x != null; x = parent.get(x)) {
                            path.addFirst(x);
                        }
                        return path;
                    }
                    q.add(v);
                }
            }
        }
        return Collections.emptyList();
    }
}
