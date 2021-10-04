import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {
    private static final int TIME_SLEEP = 1000;
    private static final int MAX_COUNT_GUESTS = 5;

    private int countGuests;

    Lock lock = new ReentrantLock();
    Condition bringDish = lock.newCondition();
    Condition makeDish = lock.newCondition();

    void garconActions() {
        System.out.println(Thread.currentThread().getName() + " на работе!");
        lock.lock();

        try {
            while (true) {
                //Volatile, threadlocal atomics - следующая лекция, поэтому пока без них.
                while (countGuests == MAX_COUNT_GUESTS) {
                    return;
                }

                Thread.sleep(TIME_SLEEP);
                System.out.println(Thread.currentThread().getName() + " взял заказ.");

                makeDish.await();

                Thread.sleep(TIME_SLEEP);
                System.out.println(Thread.currentThread().getName() + " несет заказ.");

                bringDish.signalAll();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void guestActions() {
        System.out.println(Thread.currentThread().getName() + " в ресторане.");
        lock.lock();
        try {
            System.out.println("Повар готовит блюдо.");
            Thread.sleep(TIME_SLEEP);
            System.out.println("Повар закончил готовить блюдо.");

            countGuests++;

            makeDish.signalAll();

            bringDish.await();

            Thread.sleep(TIME_SLEEP);
            System.out.println(Thread.currentThread().getName() + " приступил к еде.");
            Thread.sleep(TIME_SLEEP);
            System.out.println(Thread.currentThread().getName() + " вышел из ресторана.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}