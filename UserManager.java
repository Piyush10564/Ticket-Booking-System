// ===== NEW FILE: UserManager.java =====
import java.util.*;
import java.io.*;

public class UserManager {
    private List<User> users;
    private final String USER_FILE = "users.txt";

    public UserManager() {
        users = new ArrayList<>();
        loadUsersFromFile();
    }

    private void loadUsersFromFile() {
        users.clear();
        File file = new File(USER_FILE);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public void saveUsersToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User u : users) {
                bw.write(u.getUsername() + "," + u.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public boolean register(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return false; // already exists
            }
        }
        users.add(new User(username, password));
        saveUsersToFile();
        return true;
    }

    public boolean login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
