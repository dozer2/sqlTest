import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {


    //соединение с базой данных
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\qa.db");
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


    //класс, который позволяет выполнять запросы
    public static Statement getStatment() {
        try {
            return getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


    //закрывает базу данных
    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) { //проверяем,что база не нулл и не закрыта
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        }


}
