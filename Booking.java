public class Booking {
    String username;
    Route route;
    int seatsBooked;

    public Booking(String username, Route route, int seatsBooked) {
        this.username = username;
        this.route = route;
        this.seatsBooked = seatsBooked;
    }

    public String toFileString() {
        return username + "," + route.source + "," + route.destination + "," + seatsBooked;
    }

    public static Booking fromFileString(String line, RouteManager manager) {
        String[] parts = line.split(",");
        Route r = manager.findRoute(parts[1], parts[2]);
        if (r == null) return null;
        return new Booking(parts[0], r, Integer.parseInt(parts[3]));
    }

    public void display() {
        System.out.println("User: " + username + " | Route: " + route.source + "->" + route.destination + " | Seats: " + seatsBooked);
    }
}
