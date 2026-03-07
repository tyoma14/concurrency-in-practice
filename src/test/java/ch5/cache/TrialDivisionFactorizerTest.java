package ch5.cache;

import junit.framework.TestCase;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Created by Artyom Zheltyshev on 07.03.2026
 */
public class TrialDivisionFactorizerTest extends TestCase {

    public void testCompute() {
        TrialDivisionFactorizer factorizer = new TrialDivisionFactorizer();
        BigInteger n = BigInteger.valueOf(1263);
        BigInteger[] expFactors = {BigInteger.valueOf(3), BigInteger.valueOf(421)};
        BigInteger[] actFactors = factorizer.compute(n);
        assertTrue(Arrays.deepEquals(expFactors, actFactors));
    }
}