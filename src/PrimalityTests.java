import java.math.*;
import java.util.Random;
import java.util.Scanner;

public class PrimalityTests {

    private static Random random;

    public static boolean FermatTest(BigInteger p, int numberOfTests) {
        if (p.compareTo(BigInteger.ONE) <= 0) {
            return false;
        }
        if (p.compareTo(BigInteger.valueOf(3)) <= 0) {
            return true;
        }
        BigInteger a;
        for (int i = 0; i < numberOfTests; i++) {
            do {
                a = new BigInteger(p.bitLength() - 1, random);
            } while (a.equals(BigInteger.ZERO) || a.equals(BigInteger.ONE));
            // a^(p-1) ≢ 1 (mod p)
            if (!a.modPow(p.subtract(BigInteger.ONE), p).equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }

    public static boolean MillerRabinTest(BigInteger n, int numberOfTests) {
        if (n.compareTo(BigInteger.ONE) <= 0) {
            return false;
        }
        if (n.compareTo(BigInteger.valueOf(3)) <= 0) {
            return true;
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return false;
        } else {
            for (int j = 0; j < numberOfTests; j++) {
                BigInteger a;
                do {
                    a = new BigInteger(n.bitLength() - 1, random);
                } while (a.equals(BigInteger.ZERO) || a.equals(BigInteger.ONE));

                // gcd(n, a) ≠ 1
                if (!n.gcd(a).equals(BigInteger.ONE)) {
                    return false;
                }

                // d = (n-1) / (2^S)
                BigInteger d = n.subtract(BigInteger.ONE);

                // S = max{ r | 2^r osztója (n-1)-nek }
                int S = 0;
                while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                    d = d.divide(BigInteger.TWO);
                    S++;
                }

                // a^d ≡ 1 (mod n)
                if (a.modPow(d, n).equals(BigInteger.ONE)) {
                    return true;
                }

                // ∃ r ∈ {0 , ... , S-1}: a^(d*2^r) ≡ -1 (mod n)
                for (int i = 0; i <= S - 1; i++) {
                    if (a.modPow(d.multiply(BigInteger.TWO.pow(i)), n).equals(BigInteger.ONE.negate())) {
                        return true;
                    }
                }

            }
            return false;
        }
    }


    public static void main(String[] args) {
        random = new Random();

        Scanner stdin = new Scanner(System.in);
        System.out.println("Enter a number:");
        BigInteger n = new BigInteger(stdin.next());

        System.out.println("Result according to the Fermat Test: ");
        if (FermatTest(n, 100))
            System.out.println("The entered number is probably prime");
        else
            System.out.println("The entered number is composite");

        System.out.println("Result according to the Miller-Rabin Test: ");
        if (MillerRabinTest(n, 100))
            System.out.println("The entered number is probably prime");
        else
            System.out.println("The entered number is composite");
    }
}
