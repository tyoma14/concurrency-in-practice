package ch5.cache;

import ch5.TestHarness;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by Artyom Zheltyshev on 07.03.2026
 */
public class MemoizerDemo {

    public static void main(String[] args) throws InterruptedException {
        BigInteger n1 = new BigInteger("39283913029984948374");
        BigInteger n2 = new BigInteger("39283332131984948374");
        BigInteger n3 = BigInteger.valueOf(39283913545454352L);
        Memoizer<BigInteger, BigInteger[]> memoizedFactorizer = new Memoizer<>(new TrialDivisionFactorizer());
        TestHarness testHarness = new TestHarness();
        testHarness.timeTasks(Runtime.getRuntime().availableProcessors(), () -> {
            try {
                BigInteger[] factors1 = memoizedFactorizer.compute(n1);
                System.out.println("Factors of " + n1 + " are " + Arrays.toString(factors1));
                BigInteger[] factors2 = memoizedFactorizer.compute(n2);
                System.out.println("Factors of " + n2 + " are " + Arrays.toString(factors2));
                BigInteger[] factors3 = memoizedFactorizer.compute(n3);
                System.out.println("Factors of " + n3 + " are " + Arrays.toString(factors3));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

}
