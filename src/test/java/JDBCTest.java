import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class JDBCTest {



    @AfterAll
    public static void close()
    {
        DatabaseService.closeConnection(DatabaseService.getConnection());
    }

    @Test
    public void allOwnerMustBeHaveCar() throws SQLException {
     Statement statement=DatabaseService.getStatment();
     ResultSet resultSet=statement.executeQuery("select id_owner from car");
     while(resultSet.next()){
      assertTrue(resultSet.getInt("id_owner")>0);
     }

    }




}
