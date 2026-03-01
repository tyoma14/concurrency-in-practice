package ch5;

/**
 * Created by Artyom Zheltyshev on 13.02.2026
 */
public class BoundedHashSetDemo {

    public static void main(String[] args) throws InterruptedException {
        BoundedHashSet<Integer> bhs = new BoundedHashSet<>(4);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            bhs.remove(1);
        });
        thread.start();
        bhs.add(1);
        bhs.add(2);
        bhs.add(3);
        bhs.add(4);
        bhs.add(5);
    }

}
