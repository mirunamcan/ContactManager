package mmocanu.fr.contactmanager.user;

import java.util.prefs.Preferences;

public class UserSession {
    private static UserSession instance;

    private static Preferences prefs = Preferences.userNodeForPackage(UserSession.class);

    private UserDTO user;

    private UserSession(UserDTO user) {
        this.user = user;
        saveUserInPrefs();
    }

    public static UserSession getInstance(UserDTO user) {
        if (instance == null && user != null) {
            instance = new UserSession(user);
        }
        return instance;
    }

    public UserDTO getUser() {
        return user;
    }

    public void cleanUserSession() {
        user = null;
        instance = null;
        prefs.remove("username");
        prefs.remove("userId");
    }

    public static int getSavedUserId() {
        return prefs.getInt("userId", -1);
    }

    private void saveUserInPrefs() {
        prefs.put("username", user.getUsername());
        prefs.putInt("userId", user.getId());
    }
}