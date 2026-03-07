package ch5.cache;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artyom Zheltyshev on 07.03.2026
 */
public class TrialDivisionFactorizer implements Computable<BigInteger, BigInteger[]> {

    @Override
    public BigInteger[] compute(BigInteger n) {
        BigInteger i = BigInteger.valueOf(2L);
        List<BigInteger> factors = new ArrayList<>();
        while (i.pow(2).compareTo(n) <= 0) {
            if (n.remainder(i).equals(BigInteger.ZERO)) {
                factors.add(i);
                n = n.divide(i);
            }
            i = i.add(BigInteger.ONE);
        }
        factors.add(n);
        BigInteger[] result = new BigInteger[factors.size()];
        return factors.toArray(result);
    }
}
