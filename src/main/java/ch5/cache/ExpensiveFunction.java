package ch5.cache;

import java.math.BigInteger;

/**
 * Created by Artyom Zheltyshev on 01.03.2026
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {

    @Override
    public BigInteger compute(String arg) {
        return new BigInteger(arg);
    }
}
