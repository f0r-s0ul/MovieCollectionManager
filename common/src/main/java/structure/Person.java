package structure;

import utilities.Validable;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Person implements Validable, Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private ZonedDateTime birthday; //Поле может быть null
    private double height; //Значение поля должно быть больше 0
    private String passportID; //Значение этого поля должно быть уникальным, Поле не может быть null
    private Color hairColor; //Поле может быть null


    public Person(String name, ZonedDateTime birthday, double height, String passportID, Color hairColor) {
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.passportID = passportID;
        this.hairColor = hairColor;
    }

    public String getPassportID() {
        return passportID;
    }
    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }
    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }

    public Color getHairColor() {
        return hairColor;
    }
    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    @Override
    public boolean validate() {
        if (name == null || name.isEmpty()) return false;
        if (height <= 0) return false;
        return passportID != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person other = (Person) o;
        return Objects.equals(name, other.name) && Objects.equals(birthday, other.birthday) && Objects.equals(height, other.height) && Objects.equals(passportID,other.passportID) && Objects.equals(hairColor, other.hairColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, height, passportID, hairColor);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
