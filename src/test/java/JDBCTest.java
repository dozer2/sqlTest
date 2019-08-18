import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;

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

    @Test
    public void equalsCarClass()
    {
        Owner o1= new Owner(null,"fedor",12);
        Car carOne= new Car(4,4,2,"ferrari", o1);
        Car carTwo= new Car(4,4,2,"maz",o1);
        o1.setCar(Arrays.asList(carOne,carTwo));
        Owner o2=new Owner(Arrays.asList(carOne,carTwo),"fedor",12);
        MyInt a = new MyInt(4);
        MyInt b = new MyInt(4);
        assertSame(a,b);


    }

}
