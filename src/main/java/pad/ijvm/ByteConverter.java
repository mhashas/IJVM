package pad.ijvm;

import java.util.Arrays;

public class ByteConverter {
	public static final int WORD_LENGTH = 4;
	public static final int NULL_BYTE = 0x00;
	public static final int HEX_MASK = 0xFF;
	public static final int FIRST_OFFSET = 24;
	public static final int SECOND_OFFSET = 16;
	public static final int THIRD_OFFSET = 8;
	
	public static int convertByteWordToInteger(byte[] word) {
		return ((word[0] & HEX_MASK) << FIRST_OFFSET) | ((word[1] & HEX_MASK) << SECOND_OFFSET)
				| ((word[2] & HEX_MASK) << THIRD_OFFSET) | (word[3] & HEX_MASK);
	}
	
	public static int[] convertByteArrayToIntegerArray(byte[] byteData) {
		int length = byteData.length;
		
		if (length % WORD_LENGTH != 0 || length == 0) {
			System.err.println("Not divisible by word length");
			return new int[0];
		}
		
		int result_length = length / WORD_LENGTH;
		int[] result = new int[result_length];
		
		for (int i = 0; i < result_length; i++) {
			result[i] = convertByteWordToInteger(Arrays.copyOfRange(byteData, i * WORD_LENGTH, (i+1)* WORD_LENGTH));
		}
		
		return result;
	}
	
	public static int convertBytesToInt(byte firstByte, byte secondByte) {
		int result = firstByte * 256 + secondByte;
		return result < 0 ? result + 256 : result;
	}
}
