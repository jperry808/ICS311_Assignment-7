import java.util.List;

public class Router {

    // Try to deliver the message along a BFS path; annotate metadata with the route.
    public static boolean deliver(Graph g, Message msg) {
        List<String> path = g.shortestPath(msg.senderId, msg.receiverId);
        if (path.isEmpty()) {
            msg.metadata.put("route", "unreachable");
            return false;
        }
        msg.metadata.put("route", String.join(" -> ", path));
        return true;
    }
}
