package id.co.bsi.hello_spring.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.bsi.hello_spring.model.User;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserUtil {
    private static final String FILE_PATH = "users.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<User> loadUsers() {
        try {
            File file = Paths.get(FILE_PATH).toFile();
            if (!file.exists()) return new ArrayList<>();
            return mapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveUsers(List<User> users) {
        try {
            mapper.writeValue(Paths.get(FILE_PATH).toFile(), users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Optional<User> findUserByEmail(String email) {
        return loadUsers().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public static Optional<User> findUserByToken(String token) {
        return loadUsers().stream()
                .filter(user -> user.getToken().equals(token))
                .findFirst();
    }
}


