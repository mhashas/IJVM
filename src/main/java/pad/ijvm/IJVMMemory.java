package pad.ijvm;

import java.util.ArrayList;

public class IJVMMemory {
	private ArrayList<Frame> frames;

	private int[] constants;
	private byte[] instructionArea;

	private int programCounter;

	IJVMMemory(Block constants, Block instructions) {
		this.constants = ByteConverter.convertByteArrayToIntegerArray(constants.getData());
		this.instructionArea = instructions.getData();
		this.programCounter = 0;
		
		this.frames = new ArrayList<>();
		frames.add(new Frame(0));	
	}

	public ArrayList<Integer> getStack() {
		return this.getCurrentFrame().getStack();
	}


	public ArrayList<Frame> getFrames() {
		return frames;
	}

	
	public int getConstant(int index) {
		return this.constants[index];
	}

	public byte[] getInstructions() {
		return this.instructionArea;
	}
	
	public byte getInstruction(int index) {
		return this.instructionArea[index];
	}


	public int getProgramCounter() {
		return this.programCounter;
	}

	public void setProgramCounter(int programCounter) {
		this.programCounter = programCounter;
	}

	public int getStackSize() {
		return this.getCurrentFrame().getStackSize();
	}

	
	public void push(int element) {
		this.getCurrentFrame().pushStackVariable(element);
	}
	
	public void pushArray(int[] elements) {
		for (int i = 0; i < elements.length; i++) {
			push(elements[i]);
		}
	}
	
	public int pop() {
		return this.getCurrentFrame().popStackVariable();
	}
	
	public int peek() {
		return this.getCurrentFrame().peekStackVariable();
	}
	
	public Frame getCurrentFrame() {
		return frames.get(frames.size() - 1);
	}
		
	public void incrementProgramCounter(int length) {
		this.programCounter += length;
	}
}
