import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {

    public static Connection getConnection() {//Creating connection...
        //Этот интерфейс обеспечивает нас методами для работы с БД. Все взаимодействия с БД происходят исключительно через Connection
        try {
            return DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\qa.db");
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public static Statement getStatement() {// метод getConnection().createStatement() используется для создания экземпляра класса Statement.
        // Экземпляр Statement используется для выполнения SQL – запросов

        try {
            return getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        }


}
