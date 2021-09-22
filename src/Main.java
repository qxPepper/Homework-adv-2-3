public class Main {
    private static final int TIME_SLEEP_1 = 500;
    private static final int TIME_SLEEP_2 = 1500;

    public static void main(String[] args) throws InterruptedException {
        Restaurant restaurant = new Restaurant();

        System.out.println("Повар на работе!");
        new Thread(restaurant::garconActions, "Официант_1").start();
        new Thread(restaurant::garconActions, "Официант_2").start();

        new Thread(restaurant::guestActions, "Посетитель_1").start();

        Thread.sleep(TIME_SLEEP_1);
        new Thread(restaurant::guestActions, "Посетитель_2").start();

        Thread.sleep(TIME_SLEEP_2);
        new Thread(restaurant::guestActions, "Посетитель_3").start();
    }
}
