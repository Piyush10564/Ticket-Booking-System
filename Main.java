import java.util.Scanner;
import java.io.*;

// ===== MODIFIED: Main.java with login, registration, logging, and route add/delete =====
public class Main {

    // ===== NEW: Logging method =====
    public static void logAction(String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("log.txt", true))) {
            bw.write(java.time.LocalDateTime.now() + ": " + message);
            bw.newLine();
        } catch (IOException e) {
            // Ignore logging errors
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RouteManager manager = new RouteManager();
        UserManager userManager = new UserManager();
        Scanner sc = new Scanner(System.in);

        // ===== NEW: Login/Registration =====
        boolean authenticated = false;
        while (!authenticated) {
            System.out.println("\n========== SMART BUS ROUTE PLANNER ==========");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.print("Enter your choice: ");
            String loginChoice = sc.nextLine();
            if (loginChoice.equals("1")) {
                System.out.print("Enter new username: ");
                String username = sc.nextLine();
                System.out.print("Enter new password: ");
                String password = sc.nextLine();
                if (userManager.register(username, password)) {
                    System.out.println("Registration successful! Please login.");
                    logAction("User registered: " + username);
                } else {
                    System.out.println("Username already exists. Try again.");
                }
            } else if (loginChoice.equals("2")) {
                System.out.print("Enter username: ");
                String username = sc.nextLine();
                System.out.print("Enter password: ");
                String password = sc.nextLine();
                if (userManager.login(username, password)) {
                    System.out.println("Login successful! Welcome, " + username + ".");
                    logAction("User logged in: " + username);
                    authenticated = true;
                } else {
                    System.out.println("Invalid credentials. Try again.");
                    logAction("Failed login attempt for username: " + username);
                }
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        // ===== Main menu =====
        while (true) {
            System.out.println("\n========== SMART BUS ROUTE PLANNER ==========");
            System.out.println("1. Show All Routes");
            System.out.println("2. Filter by Source & Destination");
            System.out.println("3. Sort Routes by Time");
            System.out.println("4. Sort Routes by Number of Stops");
            System.out.println("5. Show All Locations");
            System.out.println("6. Add New Route"); // NEW
            System.out.println("7. Delete Route");  // NEW
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    manager.displayAllRoutes();
                    logAction("Displayed all routes");
                    break;
                case 2:
                    System.out.print("Enter Source: ");
                    String src = sc.nextLine();
                    System.out.print("Enter Destination: ");
                    String dest = sc.nextLine();
                    manager.findRoutes(src, dest);
                    logAction("Filtered routes: " + src + " to " + dest);
                    break;
                case 3:
                    manager.sortByTime();
                    logAction("Sorted routes by time");
                    break;
                case 4:
                    manager.sortByStops();
                    logAction("Sorted routes by stops");
                    break;
                case 5:
                    manager.showLocations();
                    logAction("Displayed locations");
                    break;
                case 6:
                    // ===== NEW: Add Route =====
                    System.out.print("Enter Source: ");
                    String newSrc = sc.nextLine();
                    System.out.print("Enter Destination: ");
                    String newDest = sc.nextLine();
                    System.out.print("Enter Number of Stops: ");
                    int stops = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Time in Minutes: ");
                    int time = Integer.parseInt(sc.nextLine());
                    manager.addRoute(new Route(newSrc, newDest, stops, time));
                    System.out.println("Route added successfully!");
                    logAction("Added route: " + newSrc + " -> " + newDest);
                    break;
                case 7:
                    // ===== NEW: Delete Route =====
                    System.out.println("Routes:");
                    int idx = 0;
                    for (Route r : manager.getRoutes()) {
                        System.out.print(idx + ". ");
                        r.display();
                        idx++;
                    }
                    System.out.print("Enter route index to delete: ");
                    int delIdx = Integer.parseInt(sc.nextLine());
                    manager.deleteRoute(delIdx);
                    System.out.println("Route deleted (if index valid).");
                    logAction("Deleted route at index: " + delIdx);
                    break;
                case 8:
                    System.out.println("Exiting Smart Bus Route Planner. Goodbye!");
                    logAction("User exited application");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}
