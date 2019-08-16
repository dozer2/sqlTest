import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class JDBCTest {

    private  static Statement statement;

    @BeforeAll
    public static void before(){
        statement=DatabaseService.getStatment();
    }

    @AfterAll
    public static void after()
    {
        DatabaseService.closeConnection(DatabaseService.getConnection());
    }

    @Test
    public void allOwnerMustBeHaveCar() throws SQLException {
        ResultSet resultSet=statement.executeQuery("select id_owner,name from car");
        while(resultSet.next()){
            String name= resultSet.getString("name");
            assertTrue(resultSet.getInt("id_owner")>0,"Car with name ".concat(name).concat(" dont't have owner"));
        }
    }

    //Написать тест проверяющий есть ли машины у владелeц
    @Test
    public void whetherTheOwnerHasCars() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT owner.id, owner.name as owner, car.name\n" +
                "FROM owner\n" +
                "LEFT JOIN car ON owner.id = car.id_owner;");
        while (resultSet.next()) {
            String ownerWithoutCar = resultSet.getString("owner");
            assertTrue(resultSet.getString("name") == null,
                    "Owner with name ".concat(ownerWithoutCar).concat(" is have car"));

        }
    }

    //Написать тест проверяющий есть ли владельцы с более чем двумя машинами
    @Test
    public void OwnersWithTwoOrMoreCars() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT owner.id, owner.name as owner, count (car.name) as 'count cars'\n" +
                "FROM owner\n" +
                "LEFT JOIN car ON owner.id = car.id_owner\n" +
                "GROUP by owner.id;");
        while (resultSet.next()) {
            String ownerWithTwoOrMoreCars = resultSet.getString("owner");
            assertFalse(resultSet.getInt("count cars") > 1,
                    "Owner with name ".concat(ownerWithTwoOrMoreCars).concat
                            (" have ".concat(resultSet.getString("count cars").concat(" car"))));

        }
    }
}

