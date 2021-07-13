package storage.storageInterfaces;

import entity.User;

public interface UserStorage {
    void save(User user);
    String delete(User user);
    void upgradeUsername(User user, String newUsername);
    void upgradeUserPassword(User user,String newPassword);
    User getUserById(int id);
    User getUserByUsername(String username);
    boolean existsById(int id);
    boolean existsByUsername(String username);
}
