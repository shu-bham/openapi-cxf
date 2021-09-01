package server;

public class Car {
    private String name;
    private String value;

    public Car(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Car() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
