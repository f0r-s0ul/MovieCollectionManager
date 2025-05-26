package structure;

import utilities.Validable;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Validable, Serializable {
    private Integer x; //Поле не может быть null
    private Float y; //Максимальное значение поля: 791, Поле не может быть null

    public Coordinates(Integer x, Float y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }
    public void setY(Float y) {
        this.y = y;
    }

    @Override
    public boolean validate() {
        if (x == null || y == null) return false;
        return y <= 791.0f;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates other = (Coordinates) o;
        return Objects.equals(x, other.x) && Objects.equals(y, other.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "x = " + x + " y = "+ y;
    }
}
