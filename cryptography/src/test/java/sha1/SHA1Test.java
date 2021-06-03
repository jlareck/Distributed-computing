package sha1;

import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SHA1Test {
    @Test
    public void hashValueShouldBeTheSameAsInCalculators() throws NoSuchAlgorithmException {
        String word = "somevalue";
        String binary = SHA1.convertToBinary(word);
        int messLength = binary.length();
        String result = SHA1.calculateMod(word, binary);
        assertEquals(result, "359249010a1d7f1f47ac8e5cbaaff40fb1a34070");
    }
    @Test
    public void emptyStringTest() throws NoSuchAlgorithmException {
        String word = "";
        String binary = SHA1.convertToBinary(word);
        int messLength = binary.length();
        String result = SHA1.calculateMod(word, binary);
        assertEquals(result, "da39a3ee5e6b4b0d3255bfef95601890afd80709");
    }

}
