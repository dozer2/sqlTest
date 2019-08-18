import java.util.Objects;

public class MyInt {
    int s;

    public MyInt(int s) {
        this.s = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyInt myInt = (MyInt) o;
        return s == myInt.s;
    }

    @Override
    public int hashCode() {
        return Objects.hash(s);
    }
}
