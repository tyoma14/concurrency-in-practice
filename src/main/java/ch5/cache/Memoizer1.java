package ch5.cache;

import annotation.GuardedBy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Artyom Zheltyshev on 02.03.2026
 */
public class Memoizer1<A, V> implements Computable<A, V> {

    @GuardedBy("this")
    private final Map<A, V> cache = new HashMap<>();
    private final Computable<A, V> computable;

    public Memoizer1(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            System.out.println("Compute for arg=" + arg);
            result = computable.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
