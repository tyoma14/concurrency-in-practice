package ch5.cache;

/**
 * Created by Artyom Zheltyshev on 01.03.2026
 */
public interface Computable<A, V> {

    V compute(A arg) throws InterruptedException;

}
