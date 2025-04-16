package id.co.bsi.hello_spring.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.bsi.hello_spring.model.User;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Bikin Class UserUtil
public class UserUtil {

    //Simpan file ke user.json dan bikin konversi file object java ke json pake Jackson
    private static final String FILE_PATH = "users.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    // Membaca data user dari file JSON atau kembalikan list kosong jika gagal
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
    // Menyimpan list user ke file JSON
    public static void saveUsers(List<User> users) {
        try {
            mapper.writeValue(Paths.get(FILE_PATH).toFile(), users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Mencari user berdasarkan email
    public static Optional<User> findUserByEmail(String email) {
        return loadUsers().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
    
    // Mencari user berdasarkan token
    public static Optional<User> findUserByToken(String token) {
        return loadUsers().stream()
                .filter(user -> user.getToken().equals(token))
                .findFirst();
    }
}


