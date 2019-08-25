import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Connection;
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

    @Test
    public void allOwnerMustBeHaveCar() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select id_owner,name from car");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            assertTrue(resultSet.getInt("id_owner") > 0, "Car with name ".concat(name).concat(" dont't have owner"));
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

    //assertSame для проверки
    @Test
    public void AssertSameOwnersWithTwoOrMoreCars() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT owner.id, owner.name as owner, count (car.name) as 'count cars'\n" +
                "FROM owner\n" +
                "LEFT JOIN car ON owner.id = car.id_owner\n" +
                "WHERE owner.name = 'as';");
        assertSame(3, resultSet.getInt("count cars"), "Test failed cause owner as have "
                .concat(resultSet.getString("count cars").concat(" cars")));

    }


    //Тест на проверку добавления данных
    @Test
    public void insertData() throws SQLException {
        boolean emptyId = true; //инициализация булевой переменной
        Long newId = 0L; //инициализация переменной типа Лонг
        while (emptyId) { //цикл пока переменная тру
            newId = System.currentTimeMillis(); //получение текущего времени в мс в переменную (рандом)
            ResultSet resultSet = statement.executeQuery("SELECT id FROM owner where id =" + newId); //выборка
            emptyId = resultSet.next(); //проверка, если тру (такой ид есть), то цикл продолжается пока не найдет свободный (состояние фолс)
        }
        Long finalNewId = newId; //присвоение финальной переменной (для анонимного класса) свободного ид
        assertThrows(SQLException.class, new Executable() { // Утверждает, что выполнение execute вызывает исключение ожидаемого типа и возвращает исключение.
            @Override
            public void execute() throws Throwable {
                statement.execute(" insert into owner (name,id,age) values ('Vasya', " + finalNewId + ", 26);");
            }
        });
    }


    //Написать тест на проверку возможности добоавления автомобиля у которого есть пользователь
    @Test
    public void insertCarWithOwner () throws SQLException {
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
                statement.execute("insert into car (id,name,id_owner) values (" + finalNewId + ", 'MAZ', 77);");
            }
        });
    }


    //Написать тест на проверку возможности изменния автомобиля у пользователя
    @Test
    public  void updateCar() throws  SQLException{
        assertThrows(SQLException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                statement.execute("UPDATE car SET name= 'TESLA' where id=4;");
            }
        }
        );
    }

    
    //написать тест на проверку  возможности удаления пользователя у которого есть 2 автомобиля
    @Test
    public void deleteOwnerWithTwoCars() throws SQLException{
        assertThrows(SQLException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                statement.execute("SELECT * FROM owner WHERE id in(\n" +
                        "SELECT owner.id FROM car\n" +
                        "LEFT JOIN owner ON car.id_owner = owner.id\n" +
                        "GROUP by owner.name\n" +
                        "HAVING count(owner.name) = 2);");
            }
        }
        );
    }
}