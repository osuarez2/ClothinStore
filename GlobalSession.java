public class GlobalSession {
    private static int userId = 0;
    private static String userName = "Guest";
    private static String userEmail = "";

    // Set user data
    public static void setUser(int id, String name, String email) {
        userId = id;
        userName = name;
        userEmail = email;
    }

    // Get user data
    public static int getUserId() {
        return userId;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    // Optional: Reset session
    public static void reset() {
        userId = 0;
        userName = "Guest";
        userEmail = "";
    }
}
