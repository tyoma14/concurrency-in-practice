package ch5;

/**
 * Created by Artyom Zheltyshev on 02.02.2026
 */
public class TestHarnessDemo {

    public static void main(String[] args) throws InterruptedException {
        TestHarness testHarness = new TestHarness();
        long duration = testHarness.timeTasks(64, () -> System.out.println("Do some work..."));
        System.out.println("Duration: " + duration);
    }

}
