public class Breaker {
    public static boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        Breaker.state = state;
    }

    private static boolean state = true;

}
