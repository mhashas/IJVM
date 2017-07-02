package pad.ijvm;

import java.nio.ByteBuffer;

public class BinaryFile {	
	private Block instructions;
	private Block constants;
	private int magicNumber;

	BinaryFile(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		parseFile(buffer);
	}

	private void parseFile(ByteBuffer buffer) {
		this.magicNumber = buffer.getInt();
		this.constants = new Block(buffer);
		this.instructions = new Block(buffer);
	}

	public Block getConstantsBlock() {
		return this.constants;
	}

	public Block getInstructionsBlock() {
		return this.instructions;
	}
	
	public int getMagicNumber() {
		return this.magicNumber;
	}
}
