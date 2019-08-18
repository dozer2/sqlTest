import java.util.List;
import java.util.Objects;

public class Car {
    int whell;
    int engine;
    int door;
    String mark;
    Owner owner;

    public Car(int whell, int engine, int door, String mark, Owner owner) {
        this.whell = whell;
        this.engine = engine;
        this.door = door;
        this.mark = mark;
        this.owner = owner;
    }

    public int getWhell() {
        return whell;
    }

    public void setWhell(int whell) {
        this.whell = whell;
    }

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public int getDoor() {
        return door;
    }

    public void setDoor(int door) {
        this.door = door;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return whell == car.whell &&
                engine == car.engine &&
                door == car.door &&
                Objects.equals(mark, car.mark) &&
                Objects.equals(owner, car.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(whell, engine, door, mark, owner);
    }
}
