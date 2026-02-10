public class Route {
    String source;
    String destination;
    int stops;
    int timeInMinutes;

    public Route(String source, String destination, int stops, int timeInMinutes) {
        this.source = source;
        this.destination = destination;
        this.stops = stops;
        this.timeInMinutes = timeInMinutes;
    }

    public void display() {
        System.out.println("Route: " + source + " -> " + destination +
                " | Stops: " + stops + " | Time: " + timeInMinutes + " mins");
    }

    // ===== NEW: For saving/loading from file =====
    public String toFileString() {
        return source + "," + destination + "," + stops + "," + timeInMinutes;
    }

    public static Route fromFileString(String line) {
        String[] parts = line.split(",");
        return new Route(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
    }
}
