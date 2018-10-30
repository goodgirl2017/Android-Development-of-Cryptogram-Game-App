package edu.gatech.seclass.sdpcryptogram;

import org.junit.Test;

import edu.gatech.seclass.sdpcryptogram.utility.CommonUtils;

import static org.junit.Assert.assertEquals;

public class CommonUtilsTest {

    @Test
    public void testGetUniqueAlphabets1() {
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", CommonUtils.getUniqueAlphabets("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
    }

    @Test
    public void testGetUniqueAlphabets2() {
        assertEquals("", CommonUtils.getUniqueAlphabets(""));
    }

    @Test
    public void testGetUniqueAlphabets3() {
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", CommonUtils.getUniqueAlphabets("The quick brown fox jumps over the lazy dog"));
    }

    @Test
    public void testGetUniqueAlphabets4() {
        assertEquals("AEFHORSTWX", CommonUtils.getUniqueAlphabets("Fort Worth!! Texas??"));
    }

    @Test
    public void testEncode1() {
        assertEquals("Duoq Yuoqa!! Qcmbp??", CommonUtils.encode("Fort Worth!! Texas??", "AEFHORSTWX", "BCDAUOPQYM"));
    }

    @Test
    public void testDecode1() {
        assertEquals("Duoq Yuoqa!! Qcmbp??", CommonUtils.decode("Fort Worth!! Texas??", "BCDAUOPQYM", "AEFHORSTWX"));
    }
}
