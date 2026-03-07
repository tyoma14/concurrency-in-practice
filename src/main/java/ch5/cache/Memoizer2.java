package ch5.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Artyom Zheltyshev on 02.03.2026
 */
public class Memoizer2<A, V> implements Computable<A, V> {

    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> computable;

    public Memoizer2(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);
        if (result == null) {
            System.out.println("Compute for arg=" + arg);
            result = computable.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
