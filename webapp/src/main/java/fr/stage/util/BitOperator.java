package fr.stage.util;

public class BitOperator {

    public static byte byteAnd(byte a, byte b) {
	return (byte) (a & b);
    }

    public static byte byteEquals(byte a, byte b) {
	if ((a & b) == b)
	    return 0;
	else
	    return 1;
    }
}
