//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.function.Executable;
//import org.sqlite.SQLiteException;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class JDBCTest {
//
//    private static Statement statement;
//
//    @BeforeAll
//    public static void before() {
//        statement = DatabaseService.getStatement();//
//    }
//
//    @AfterAll
//    public static void after() {
//        DatabaseService.closeConnection(DatabaseService.getConnection());
//    }
//
//    //
//    @Test
//    public void allOwnerMustBeHaveCar() throws SQLException {
//        ResultSet resultSet = statement.executeQuery("select id_owner,name from car");//Результат (ResultSet) Экземпляры этого элемента содержат
//        // данные, которые были получены в результате выполнения SQL – запроса. Он работает как итератор и “пробегает” по полученным данным.
//        while (resultSet.next()) {
//            String name = resultSet.getString("name");
//            assertTrue(resultSet.getInt("id_owner") > 0, "Car with name ".concat(name).concat(" dont't have owner"));
//        }
//    }
//
//    //Написать тест проверяющий есть ли машины у владельца
//    @Test
//    public void hasTheOwnerACar() throws SQLException {
//
//        ResultSet resultSet = statement.executeQuery("SELECT car.*, owner.*\n" +
//                "FROM car\n" +
//                "LEFT JOIN owner ON car.id_owner = owner.id\n" +
//                "ORDER BY car.id;");
//        while (resultSet.next()) {
//
//            Integer id = resultSet.getInt("id");
//            String name = resultSet.getString("name");
//            String ownername = resultSet.getString("ownername");
//            Integer idowner = resultSet.getInt("id_owner");
//            //System.out.println("Id= " + id + "  Name= " + name + "     IdOwner" + idowner + "  OwnerName " + ownername);
//
//            assertTrue(ownername == null, "Owner with idowner=".concat(idowner.toString()).concat(" - don't have a car"));
//
//            assertTrue(name == null, "Incorrect Value in car.name, where car.id=".concat(id.toString()));
//
//            assertFalse(name.equals("0"), "Owner " + idowner + " - don't have a car");
//        }
//    }
//
//    //Написать тест проверяющий есть ли владельцы с более чем двумя машинами
//    @Test
//    public void MoreThenTwoCarInOneOwner() throws SQLException {
//
//        ResultSet resultSet2 = statement.executeQuery("SELECT owner.*, car.*, count(car.id_owner) as numberOfOwners\n" +
//                "FROM (car\n" +
//                "--INNER JOIN owner on car.id_owner=owner.id)\n" +
//                "LEFT JOIN owner ON car.id_owner = owner.id)\n" +
//                "group by ownername;");
//
//        Integer id = resultSet2.getInt("id");
//        String name = resultSet2.getString("name");
//        String ownername = resultSet2.getString("ownername");
//        Integer idowner = resultSet2.getInt("id_owner");
//        Integer numberOfOwners = resultSet2.getInt("numberOfOwners");
//
//        assertTrue(numberOfOwners < 2, "Owner " + ownername + " имеет больше 1й машины");
//    }
//
//    //Использовать assertSame для проверки
//    @Test
//    public void SameMoreThenTwoCarInOneOwner() throws SQLException {
//
//        ResultSet resultSet2 = statement.executeQuery("SELECT owner.*, car.*, count(car.id_owner) as numberOfOwners\n" +
//                "FROM (car\n" +
//                "--INNER JOIN owner on car.id_owner=owner.id)\n" +
//                "LEFT JOIN owner ON car.id_owner = owner.id)\n" +
//                "group by ownername;");
//
//        Integer id = resultSet2.getInt("id");
//        String name = resultSet2.getString("name");
//        String ownername = resultSet2.getString("ownername");
//        Integer idowner = resultSet2.getInt("id_owner");
//        Integer numberOfOwners = resultSet2.getInt("numberOfOwners");
//
//        if (numberOfOwners > 1)
//            assertSame("as", ownername);
//    }
//
//    @Test
//    public void check18thOwner() throws SQLException {
//
//        ResultSet resultSet3 = statement.executeQuery("select owner.age, owner.ownername, car.name\n" +
//                "FROM owner\n" +
//                "LEFT join car on car.id_owner=owner.id\n" +
//                "--WHERE owner.age>=1;");
//
//        int age = resultSet3.getInt("age");
//        String owner = resultSet3.getString("ownername");
//
//        assertTrue(age > 0, "Несовершеннолетний!  ".concat(owner).concat("  DETECTED"));
//    }
//
//    @Test
//    public void insertData() throws SQLException {
//        boolean emptyId = true;
//        Long newId = 0L;
//
//        while (emptyId) {
//
//            newId = System.currentTimeMillis();
//            ResultSet resultSet = statement.executeQuery("SELECT id FROM owner where id =" + newId);
//            emptyId = resultSet.next();
//        }
//        Long finalNewId = newId;
//
//        assertThrows(SQLException.class, new Executable() {
//                    @Override
//                    public void execute() throws Throwable {
//                        try {
//                            boolean result = statement.execute("insert into owner (id,ownername,age) values (" + finalNewId + ",'Oleg', 26); ");
//                        } catch (SQLiteException e) {
//                            System.out.println(e);
//                        }
//                    }
//                }
//        );
//    }
//
//    @Test//тест на проверку возможности добавления автомобиля у которого есть пользователь
//    public void carWithOwnerAlreadyExist() throws SQLException {
//
//        boolean emptyId = true;
//        Long newId = 0L;
//
//        while (emptyId) {
//
//            newId = System.currentTimeMillis();
//            ResultSet resultSet2 = statement.executeQuery("SELECT id FROM owner where id =" + newId);
//            emptyId = resultSet2.next();
//
//        }
//        Long finalNewId = newId;
//        ResultSet resultSet = statement.executeQuery("SELECT car.name, owner.ownername From car left join owner on car.id_owner=owner.id;");
//        while (resultSet.next()) {
//            String carName = resultSet.getString("name");
//            String ownerName = resultSet.getString("ownername");
//
//            if (ownerName != null) {
//
//                assertThrows(SQLException.class, new Executable() {
//                            @Override
//                            public void execute() throws Throwable {
//                                try {
//                                    boolean result = statement.execute("insert into car (id, name ,id_owner) values (" + finalNewId + ", '" + carName + "' , 10);");
//                                } catch (SQLiteException e) {
//                                    System.out.println(e);
//                                }
//                            }
//                        }
//                );
//            }
//        }
//    }
//
//    @Test//тест на проверку возможности изменения автомобиля у пользователя
//    public void editCarOpportunity() throws SQLException {
//        String editedName = "editable CarName";
//
//        ResultSet resultSet = statement.executeQuery("select id, name from car  ");
//        while (resultSet.next()) {
//
//            String nameCar = resultSet.getString("name");
//            int idCar = resultSet.getInt("id");
//            if (nameCar != null) {
//
//                assertThrows(SQLException.class, new Executable() {
//                    @Override
//                    public void execute() throws Throwable {
//                        boolean result = statement.execute("UPDATE car\n" +
//                                "set name ='" + editedName + "'\n" +
//                                "where id =" + idCar);
//                    }
//                });
//            }
//
//        }
//    }
//
//    @Test //тест на проверку возможности удаления пользователя у которого есть 2 автомобиля
//    public void deleteCarOpportunity() throws SQLException {
//
//        ResultSet resultSet = statement.executeQuery("Select car.*, owner.ownername, count (car.id_owner) as numberOfOwners\n" +
//                "from (car\n" +
//                "      left join owner on car.id_owner=owner.id)\n" +
//                "group by ownername;");
//
//        while (resultSet.next()) {
//            int carCounter = resultSet.getInt("numberOfOwners");
//            int idCar = resultSet.getInt("id");
//            int idOwner = resultSet.getInt("id_owner");
//            String ownerName = resultSet.getString("ownername");
//            if (carCounter == 2) {
//                assertThrows(SQLException.class, new Executable() {
//                    @Override
//                    public void execute() throws Throwable {
//                        boolean result = statement.execute("Delete from owner where ownername='" + ownerName + "';");
//                    }
//                });
//            }
//        }
//    }
//    // assertEquals для сравнения объектов ПО ЗНАЧЕНИЮ использует equals()метод (который вы должны переопределить в своем классе, чтобы действительно сравнить его экземпляры)
//
//    // assertSame использует == оператор для их сравнения. (СРАВНИВАЮТСЯ ССЫЛКИ НА ЗАГОЛОВКИ)
//    // Таким образом, разница точно такая же, как между equals(сравнить идентификатор) и ==(сравнить по значению)
//
//    @Test//проверка с использованием Matches - сравнение строк на равенство
//            public void checkRow() throws Exception{
//        String str = "Welcome in test method";
//        String str2 = "MET";
//
//        assertTrue(str.matches("(.*)Welcome(.*)"),"Есть в наличии слово Welcome в строке:  "+str);
////        System.out.println(str.matches("(.*)Welcome(.*)"));//Проверка на наличие слова Welcome в строке
////        System.out.println(str.matches("Welcome(.*)"));
////        System.out.println(str.matches("(.*)test method"));
////        System.out.println(str.matches("(.*)test Method"));
//
//        assertTrue(str.regionMatches(true,16,str2,0,2));//сравнение двух строк TRUE - игнор регистра,
//        // toffset - с какого символа начать в первой строке (нумерация с нуля),
//        //вторая строка: ooffset - начало диапазона поиска, len-конец диапазона поиска
//
//
//    }
//
//
//
//}