public class Main {
    private static final int TIME_SLEEP = 1000;
    private static final int COUNT_GARCONS = 2;
    private static final int MAX_COUNT_GUESTS = 5;

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();

        System.out.println("Повар на работе!");

        for (int i = 1; i <= COUNT_GARCONS; i++) {
            new Thread(restaurant::garconActions, "Официант_" + i).start();
        }

        for (int i = 1; i <= MAX_COUNT_GUESTS; i++) {
            new Thread(restaurant::guestActions, "Посетитель_" + i).start();

            try {
                Thread.sleep(TIME_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
