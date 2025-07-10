package gift.car;

public class Car {
    private final String name;
    private int position;

    public Car(String name) {
        this.name = name;
    }

    public void move(Condition condition) {
        if (condition.isMovable()) {
            position++;
        }
    }
}
