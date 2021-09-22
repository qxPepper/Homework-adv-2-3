import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {
    private static final int TIME_SLEEP = 1000;

    private boolean isOrder;
    private boolean isReady;

    Lock lock = new ReentrantLock();
    Condition makeOrder = lock.newCondition();
    Condition completeOrder = lock.newCondition();

    void guestActions() {
        System.out.println(Thread.currentThread().getName() + " в ресторане.");
        lock.lock();
        try {
            Thread.sleep(TIME_SLEEP);

            System.out.println(Thread.currentThread().getName() + " сделал заказ.");
            while (!isOrder) {
                makeOrder.await();
            }

            Thread.sleep(TIME_SLEEP);
            System.out.println(Thread.currentThread().getName() + " приступил к еде.");

            Thread.sleep(TIME_SLEEP);
            System.out.println(Thread.currentThread().getName() + " вышел из ресторана.");

            isReady = true;
            completeOrder.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void garconActions() {
        System.out.println(Thread.currentThread().getName() + " на работе!");
        lock.lock();
        try {
            Thread.sleep(TIME_SLEEP);
            System.out.println(Thread.currentThread().getName() + " взял заказ.");
            isOrder = true;

            makeOrder.signalAll();

            Thread.sleep(TIME_SLEEP);
            System.out.println("Повар готовит блюдо.");
            Thread.sleep(TIME_SLEEP);
            System.out.println("Повар закончил готовить блюдо.");

            Thread.sleep(TIME_SLEEP);
            System.out.println(Thread.currentThread().getName() + " несет заказ.");

            while (isReady) {
                completeOrder.await();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
