package extendedeuclidian;
import java.math.BigInteger;
public class ExtendedEuclidean {
    public static class ExtendedEuclideanResult {
        BigInteger coefficientBezoutX;
        BigInteger coefficientBezoutY;
        BigInteger gcd;

        public ExtendedEuclideanResult(BigInteger coefficientBezoutX, BigInteger coefficientBezoutY, BigInteger gcd) {
            this.coefficientBezoutX = coefficientBezoutX;
            this.coefficientBezoutY = coefficientBezoutY;
            this.gcd = gcd;
        }

        public BigInteger getCoefficientBezoutX() {
            return coefficientBezoutX;
        }

        public BigInteger getCoefficientBezoutY() {
            return coefficientBezoutY;
        }

        public BigInteger getGcd() {
            return gcd;
        }
    }
    public static ExtendedEuclideanResult compute(BigInteger a, BigInteger b) {
        if (a.equals(BigInteger.ZERO)) {
            return new ExtendedEuclideanResult(
                    BigInteger.ZERO, BigInteger.ONE, b
            );
        }

        ExtendedEuclideanResult gcdResult = compute(b.mod(a), a);
        ExtendedEuclideanResult result =  new ExtendedEuclideanResult(
                gcdResult.coefficientBezoutY.subtract(b.divide(a).multiply(gcdResult.coefficientBezoutX)), //1 - (x1 * b / a)
                gcdResult.coefficientBezoutX,
                gcdResult.gcd
        );

        return result;
    }
}
