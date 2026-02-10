import java.util.*;
import java.io.*;

// ===== MODIFIED: RouteManager with file persistence =====
public class RouteManager {
    private List<Route> routes;
    private Set<String> locations;
    private final String ROUTE_FILE = "routes.txt";

    public RouteManager() {
        routes = new ArrayList<>();
        locations = new HashSet<>();
        loadRoutesFromFile();
    }

    // ===== NEW: Load routes from file =====
    private void loadRoutesFromFile() {
        routes.clear();
        locations.clear();
        File file = new File(ROUTE_FILE);
        if (!file.exists()) {
            // If no file, load defaults
            loadDefaultRoutes();
            saveRoutesToFile();
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(ROUTE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                Route r = Route.fromFileString(line);
                routes.add(r);
                locations.add(r.source);
                locations.add(r.destination);
            }
        } catch (IOException e) {
            System.out.println("Error loading routes: " + e.getMessage());
        }
    }

    // ===== NEW: Save routes to file =====
    public void saveRoutesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ROUTE_FILE))) {
            for (Route r : routes) {
                bw.write(r.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving routes: " + e.getMessage());
        }
    }

    // ===== NEW: Load default routes if no file =====
    private void loadDefaultRoutes() {
        routes.add(new Route("A", "B", 5, 30));
        routes.add(new Route("A", "C", 3, 25));
        routes.add(new Route("B", "D", 4, 35));
        routes.add(new Route("C", "D", 2, 20));
        routes.add(new Route("A", "D", 6, 50));
        for (Route r : routes) {
            locations.add(r.source);
            locations.add(r.destination);
        }
    }

    public void displayAllRoutes() {
        for (Route r : routes) {
            r.display();
        }
    }

    public void findRoutes(String src, String dest) throws InterruptedException {
        System.out.println("\nSearching for routes...");
        Thread.sleep(1000); // simulate delay
        boolean found = false;
        for (Route r : routes) {
            if (r.source.equalsIgnoreCase(src) && r.destination.equalsIgnoreCase(dest)) {
                r.display();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No direct route found from " + src + " to " + dest);
        }
    }

    public void sortByTime() {
        routes.sort(Comparator.comparingInt(r -> r.timeInMinutes));
        System.out.println("\nRoutes sorted by travel time:");
        displayAllRoutes();
    }

    public void sortByStops() {
        routes.sort(Comparator.comparingInt(r -> r.stops));
        System.out.println("\nRoutes sorted by number of stops:");
        displayAllRoutes();
    }

    public void showLocations() {
        System.out.println("\nAvailable Locations:");
        for (String loc : locations) {
            System.out.println("- " + loc);
        }
    }

    // ===== NEW: Add/Edit/Delete Routes =====
    public void addRoute(Route r) {
        routes.add(r);
        locations.add(r.source);
        locations.add(r.destination);
        saveRoutesToFile();
    }

    public void deleteRoute(int index) {
        if (index >= 0 && index < routes.size()) {
            routes.remove(index);
            saveRoutesToFile();
        }
    }

    public List<Route> getRoutes() {
        return routes;
    }

    // ===== NEW: Find a single route by source and destination =====
    public Route findRoute(String src, String dest) {
        for (Route r : routes) {
            if (r.source.equalsIgnoreCase(src) && r.destination.equalsIgnoreCase(dest)) {
                return r;
            }
        }
        return null;
    }
}
