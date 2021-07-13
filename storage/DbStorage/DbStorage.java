package storage.DbStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class DbStorage {
     Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","egor");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}

