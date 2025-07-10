package gift.car;

public class Main {
    public static void main(String[] args) {
        var car = new Car("부릉");
        car.move(new Go());
        car.move(new Stop());
    }
}
