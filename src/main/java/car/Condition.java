package car;

public class Condition {
    public boolean isMovable() {
        return false;
    }
}

class Stop extends Condition {
    @Override
    public boolean isMovable() {
        return false;
    }
}

class Go extends Condition {
    @Override
    public boolean isMovable() {
        return true;
    }
}
