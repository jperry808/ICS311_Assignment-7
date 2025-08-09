public class Main {
    public static void main(String[] args) throws Exception {
        // Create People objects (each has their own RSA keypair)
        Person jett = new Person("jett");
        Person jacob   = new Person("jacob");
        Person wilson = new Person("wilson");
        Person dave  = new Person("dave");
        Person charles  = new Person("charles");

        // Build a small graph of friends
        Graph g = new Graph();
        g.addEdge("jett", "jacob");
        g.addEdge("jacob", "wilson");
        g.addEdge("wilson", "charles");
        g.addEdge("jacob", "charles");

        // Jett is encrypting his message to be sent to Charles
        String secret1 = "Hi Charles, lets meet for lunch at 11:00";
        Message m1 = Mailer.sendEncrypted(jett, charles, secret1);

        // Try to deliver via graph routing)
        if (!Router.deliver(g, m1)) {
            System.out.println("[Jett -> Charles] No path found");
        } else {
            System.out.println("[Jett -> Charles] Metadata: " + m1.metadata);
            System.out.println("[Charles] Decrypted: " + Mailer.receiveDecrypted(charles, m1));
        }

        System.out.println();

        // Charles is encrypting his message to be sent to Dave
        // Because Dave and Charles have no connection, this will fail
        String secret2 = "This is a test to see if the BFS routing works";
        Message m2 = Mailer.sendEncrypted(charles, dave, secret2);

        if (!Router.deliver(g, m2)) {
            System.out.println("[Charles -> Dave] No path found");
        } else {
            System.out.println("[Charles -> Dave] Metadata: " + m2.metadata);
            System.out.println("[Dave] Decrypted: " + Mailer.receiveDecrypted(dave, m2));
        }
    }
}
