package storage.DbStorage;

import entity.User;
import storage.storageInterfaces.UserStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUserStorage extends DbStorage implements UserStorage {




    public static void main(String[] args) throws SQLException {
        DbUserStorage db=new DbUserStorage();
        System.out.println(db.existsById(1));

    }
    @Override
    public void save(User user) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users values(DEFAULT,?,?,?,?,?,?) returning id");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setBoolean(6,user.getApprove());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user.setId(resultSet.getInt(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public String delete(User user) {
        String rez=null;
        if (user != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("update users set name = 'Unnamed', username = 'deleted', password='', role=''");
                preparedStatement.execute();
                rez="Пользователь удален";
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            rez="Пользователь не удален";

        }
       return rez;
    }

    @Override
    public void upgradeUsername(User user, String newUsername) {
        if (user == null || newUsername == null || newUsername.equals("")) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set username=? where id=?");
            preparedStatement.setString(1, newUsername);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void upgradeUserPassword(User user, String newPassword) {
        if (user == null || newPassword == null || newPassword.equals("")) return;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users  set  password=? where id =?");
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public User getUserById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int anInt = resultSet.getInt(2);
            String string = resultSet.getString(3);
            String string1 = resultSet.getString(4);
            String string2 = resultSet.getString(5);
            String string3 = resultSet.getString(6);
            boolean apr=resultSet.getBoolean(7);
            return new User(anInt, string, string1, string2, string3,apr);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        if(username==null || username.isBlank()) return null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username=?");
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()) return null;
            int id = resultSet.getInt(2);
            String name = resultSet.getString(3);
            String userName = resultSet.getString(4);
            String password = resultSet.getString(5);
            String role = resultSet.getString(6);
            boolean apr=resultSet.getBoolean(7);
            return new User(id, name, userName, password, role,apr);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean existsById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existsByUsername(String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username=?");
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
