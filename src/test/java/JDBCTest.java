import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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

    @Test
    public void allOwnersWhichNotEighteenYears() throws SQLException{
        ResultSet resultSetTwo = statement.executeQuery("SELECT car.name, owner.name, owner.age" +
                "FROM car" +
                "LEFT JOIN owner on car.id_owner=owner.id" +
                "WHERE owner.age < 18");

        Integer age = resultSetTwo.getInt("age");
        String name = resultSetTwo.getString("name");

        while (resultSetTwo.next()){
            assertTrue(age > 17, "Owners which not eight year ".concat(name));
        }
    }

    @Test
    public void insertData () throws SQLException{
        boolean emptyId = true;
        Long newId = 0L;
        while (emptyId){
            //ResultSet resultSetTwo = statement.executeQuery("SELECT max(owner.id) FROM owner");
            newId = System.currentTimeMillis();
            ResultSet resultSet = statement.executeQuery("SELECT id FROM owner where id =" + newId);
            emptyId = resultSet.next();
        }
        Long finalNewId = newId;
        assertThrows(SQLException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                statement.execute(" insert into owner (id,name,age) values (" + finalNewId + ", 'Vasya', 26);");
            }
        });
    }

    @Test
    public void insertNewCar () throws SQLException {
        boolean emptyId = true;
        Long newId = 0L;
        while (emptyId){
            newId = System.currentTimeMillis();
            ResultSet resultSet = statement.executeQuery("SELECT id FROM car where id =" + newId);
            emptyId = resultSet.next();
        }
        Long finalNewId = newId;
        assertThrows(SQLException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                statement.execute("insert into car (id,name,id_owner) values (" + finalNewId + ", 'Honda', 9);");
            }
        });
    }

    @Test
    public  void updateCarOwner() throws  SQLException{
        assertThrows(SQLException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                statement.execute("UPDATE car SET name=\"Mitsubishi\" where id=1566123619790");
            }
        });
    }

   @Test
    public void deleteOwnerWithTwoCar() throws SQLException{
        assertThrows(SQLException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                statement.execute("DELETE FROM owner WHERE id in(\n" +
                        "SELECT owner.id\n" +
                        "FROM car\n" +
                        "LEFT JOIN owner ON car.id_owner = owner.id\n" +
                        "GROUP by owner.name\n" +
                        "HAVING count(owner.name) = 2)");
            }
        });
   }

}

