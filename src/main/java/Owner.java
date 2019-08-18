import java.util.List;
import java.util.Objects;

public class Owner {
    List<Car> car;
    String name;
    int age;


    public Owner(List<Car> car, String name, int age) {
        this.car = car;
        this.name = name;
        this.age = age;
    }

    public List<Car> getCar() {
        return car;
    }

    public void setCar(List<Car> car) {
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return age == owner.age &&
                Objects.equals(car, owner.car) &&
//                Objects.equals(name, owner.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash( name, age);
    }
}
