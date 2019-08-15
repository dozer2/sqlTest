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
        statement = DatabaseService.getStatement();//
    }

    @AfterAll
    public static void after() {
        DatabaseService.closeConnection(DatabaseService.getConnection());
    }

    //
    @Test
    public void allOwnerMustBeHaveCar() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select id_owner,name from car");//Результат (ResultSet) Экземпляры этого элемента содержат
        // данные, которые были получены в результате выполнения SQL – запроса. Он работает как итератор и “пробегает” по полученным данным.
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            assertTrue(resultSet.getInt("id_owner") > 0, "Car with name ".concat(name).concat(" dont't have owner"));
        }
    }

    //Написать тест проверяющий есть ли машины у владельца
    @Test
    public void hasTheOwnerACar() throws SQLException {

        ResultSet resultSet = statement.executeQuery("SELECT car.*, owner.*\n" +
                "FROM car\n" +
                "LEFT JOIN owner ON car.id_owner = owner.id\n" +
                "ORDER BY car.id;");
        while (resultSet.next()) {

            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String ownername = resultSet.getString("ownername");
            Integer idowner = resultSet.getInt("id_owner");
            //System.out.println("Id= " + id + "  Name= " + name + "     IdOwner" + idowner + "  OwnerName " + ownername);

            assertTrue(ownername == null, "Owner with idowner=".concat(idowner.toString()).concat(" - don't have a car"));

            assertTrue(name == null, "Incorrect Value in car.name, where car.id=".concat(id.toString()));

            assertFalse(name.equals("0"), "Owner " + idowner + " - don't have a car");
        }
    }

    //Написать тест проверяющий есть ли владельцы с более чем двумя машинами
    @Test
    public void MoreThenTwoCarInOneOwner() throws SQLException {

        ResultSet resultSet2 = statement.executeQuery("SELECT owner.*, car.*, count(car.id_owner) as numberOfOwners\n" +
                "FROM (car\n" +
                "--INNER JOIN owner on car.id_owner=owner.id)\n" +
                "LEFT JOIN owner ON car.id_owner = owner.id)\n" +
                "group by ownername;");

        Integer id = resultSet2.getInt("id");
        String name = resultSet2.getString("name");
        String ownername = resultSet2.getString("ownername");
        Integer idowner = resultSet2.getInt("id_owner");
        Integer numberOfOwners = resultSet2.getInt("numberOfOwners");

        assertTrue(numberOfOwners < 2, "Owner " + ownername + " имеет больше 1й машины");
    }

    //Использовать assertSame для проверки
    @Test
    public void SameMoreThenTwoCarInOneOwner() throws SQLException {

        ResultSet resultSet2 = statement.executeQuery("SELECT owner.*, car.*, count(car.id_owner) as numberOfOwners\n" +
                "FROM (car\n" +
                "--INNER JOIN owner on car.id_owner=owner.id)\n" +
                "LEFT JOIN owner ON car.id_owner = owner.id)\n" +
                "group by ownername;");

        Integer id = resultSet2.getInt("id");
        String name = resultSet2.getString("name");
        String ownername = resultSet2.getString("ownername");
        Integer idowner = resultSet2.getInt("id_owner");
        Integer numberOfOwners = resultSet2.getInt("numberOfOwners");

        if (numberOfOwners > 1)
            assertSame("as", ownername);
    }

    @Test
    public void check18thOwner() throws SQLException{

        ResultSet resultSet3=statement.executeQuery("select owner.age, owner.ownername, car.name\n" +
                "FROM owner\n" +
                "LEFT join car on car.id_owner=owner.id\n" +
                "--WHERE owner.age>=1;");

        int age =resultSet3.getInt("age");
        String owner=resultSet3.getString("ownername");

            assertTrue(age>0,"Несовершеннолетний!  ".concat(owner).concat("  DETECTED"));
    }
}