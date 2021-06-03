package primalityTest;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class MillerRabinTestTest {
    private static final int certainty = 100;

    @Test
    void isZeroPrimeShouldThrowArithmeticException() {
        assertThrows(ArithmeticException.class,
                () -> MillerRabinTest.isProbablyPrime(BigInteger.ZERO, certainty),
                "BigInteger: modulus not positive");
    }

    @Test
    void isNegativePrimeShouldThrowArithmeticException() {
        assertThrows(ArithmeticException.class,
                () -> MillerRabinTest.isProbablyPrime(BigInteger.valueOf(-1000), certainty),
                "BigInteger: modulus not positive");
    }

    @Test
    void isOnePrimeShouldReturnFalse() {
        assertFalse(MillerRabinTest.isProbablyPrime(BigInteger.ONE, certainty));
    }

    @Test
    void isTwoPrimeShouldReturnTrue() {
        assertTrue(MillerRabinTest.isProbablyPrime(BigInteger.TWO, certainty));
    }

    @Test
    void negativeCertaintyAlwaysReturnFalse() {
        assertFalse(MillerRabinTest.isProbablyPrime(BigInteger.valueOf(13), -1));
        assertFalse(MillerRabinTest.isProbablyPrime(BigInteger.valueOf(1000), -1));
    }

    @Test
    void zeroCertaintyAlwaysReturnFalse() {
        assertFalse(MillerRabinTest.isProbablyPrime(BigInteger.valueOf(13), 0));
        assertFalse(MillerRabinTest.isProbablyPrime(BigInteger.valueOf(1000), 0));
    }

    @Test
    void isProbablyPrimeShouldReturnTrueForSmallPrimes() {
        for (BigInteger prime : PrimesConstant.primes) {
            assertTrue(FermatPrimalityTest.isProbablyPrime(prime, certainty));
        }
    }

    @Test
    void isProbablyPrimeShouldReturnFalseForSmallNotPrimes() {
        for (BigInteger notPrime : PrimesConstant.notPrimes) {
            assertFalse(FermatPrimalityTest.isProbablyPrime(notPrime, certainty));
        }
    }

    @Test
    void isProbablyPrimeShouldReturnFalseForNotPrimesBiggerThanInt() {
        int numberOfIsPrimeFalseReturns = 0;
        for (BigInteger bigInteger : PrimesConstant.bigNotPrimes) {
            if (!MillerRabinTest.isProbablyPrime(bigInteger, certainty)) {
                ++numberOfIsPrimeFalseReturns;
            }
        }

        assertEquals(99, numberOfIsPrimeFalseReturns, 1);
    }

    @Test
    void isProbablyPrimeShouldReturnTrueForPrimesBiggerThanInt() {
        int numberOfIsPrimeTrueReturns = 0;
        for (BigInteger bigInteger : PrimesConstant.bigPrimes) {
            if (MillerRabinTest.isProbablyPrime(bigInteger, certainty)) {
                ++numberOfIsPrimeTrueReturns;
            }
        }

        assertEquals(99, numberOfIsPrimeTrueReturns, 1);
    }
}