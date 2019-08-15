import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class JDBCTest {

    private static Statement statement;

    @BeforeAll
    public static void before() {
        statement = DatabaseService.getStatment();
    }

    @AfterAll
    public static void after() {
        DatabaseService.closeConnection(DatabaseService.getConnection());
    }

    //Список машин без владельцев
    @Test
    public void allOwnerMustBeHaveCar() throws SQLException {
        ResultSet resultSet=statement.executeQuery("select id_owner,name from car");
        while(resultSet.next()){
            String name = resultSet.getString("name");
            assertTrue(resultSet.getInt("id_owner")>0,"Car with name".concat(name).concat(" don't have owner"));
        }
    }

    //Список владельцев без машин
    @Test
    public void allOwnersDoNotHaveACar() throws SQLException {
        ResultSet resultSetOwnersWithoutCar = statement.executeQuery("SELECT owner.name, car.id_owner\n" +
                "FROM owner\n" +
                "LEFT JOIN car ON owner.id = car.id_owner\n" +
                "WHERE car.name is null\n" +
                "ORDER BY owner.name;");
        while (resultSetOwnersWithoutCar.next()) {
            String name = resultSetOwnersWithoutCar.getString("name");
            Integer id_owner = resultSetOwnersWithoutCar.getInt("id_owner");
            assertTrue(id_owner == null,"Owner with name ".concat(name).concat(" don't have a car"));
        }
    }

    //Список владельцев с более чем 2 машинами
    @Test
    public void allOwnersWithMoreThanTwoCars() throws SQLException{
        ResultSet resultSetOwnersWithMoreThanTwoCars = statement.executeQuery("SELECT owner.name,count(owner.name) AS count_car\n" +
                "FROM car\n" +
                "LEFT JOIN owner ON car.id_owner = owner.id\n" +
                "GROUP by owner.name\n" +
                "HAVING count(owner.name) > 2;");
        while ((resultSetOwnersWithMoreThanTwoCars.next())) {
            String name = resultSetOwnersWithMoreThanTwoCars.getString("name");
            Integer count_car = resultSetOwnersWithMoreThanTwoCars.getInt("count_car");
            assertTrue(count_car <= 2, "Owners with more than two cars ".concat(name));
        }
    }

    //AssertSame для проверки
    @Test
    public void SameMoreThenTwoCarInOneOwner() throws SQLException {

        ResultSet resultSet2 = statement.executeQuery("SELECT owner.name, car.id_owner, count(owner.name) as count_cars\n" +
                "FROM car\n" +
                "LEFT JOIN owner on\tcar.id_owner = owner.id\n" +
                "group by owner.name\n" +
                "HAVING count(owner.name) > 1;");

        Integer count_cars = resultSet2.getInt("count_cars");
        Integer id_owner = resultSet2.getInt("id_owner");

        if (id_owner == 8 ){
            assertSame(3,count_cars);
        } else System.out.println("FAIL");

    }
}

