package pad.ijvm;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Block {	
	private int origin;
	private byte[] byteData;
	
	public int getOrigin() {
		return this.origin;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}

	public byte[] getData() {
		return this.byteData;
	}

	public void setData(byte[] data) {
		this.byteData = data;
	}
	
	Block(int origin, byte[] data) {
		this.origin = origin;
		this.byteData = data;
	}
	
	Block(ByteBuffer buffer) {
		this.origin = buffer.getInt();
		int dataSize = buffer.getInt();
		this.byteData = new byte[dataSize];
		
		for (int i = 0; i < dataSize; i++) {
			this.byteData[i] = buffer.get();
		}
	}
}
