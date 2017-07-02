package pad.ijvm;

import java.util.ArrayList;

public class Frame {
	private ArrayList<Integer> localVariables;
	private int numberOfVariables;
	
	private ArrayList<Integer> stack;
	private int stackSize;

	private int sourceProgramCounter;

	Frame(int programCounter) {
		this.sourceProgramCounter = programCounter;
		this.localVariables = new ArrayList<>();
		this.numberOfVariables = 0;
		this.stackSize = 0;
		this.stack = new ArrayList<>();
	}

	public ArrayList<Integer> getLocalVariables() {
		return localVariables;
	}

	public void setLocalVariables(ArrayList<Integer> variables) {
		this.localVariables = variables;
	}

	public int getNumberOfVariables() {
		return numberOfVariables;
	}

	public void setNumberOfVariables(int stackSize) {
		this.numberOfVariables = stackSize;
	}

	public int getSourceProgramCounter() {
		return sourceProgramCounter;
	}

	public void setSourceProgramCounter(int sourceProgramCounter) {
		this.sourceProgramCounter = sourceProgramCounter;
	}

	public void pushLocalVariable(int element) {
		numberOfVariables++;
		localVariables.add(element);
	}

	public int getLocalVariable(int index) {
		if (isValidIndex(index)) {
			return localVariables.get(index);
		} else {
			return -1;
		}
	}

	public int removeLocalVariable(int index) {
		if (isValidIndex(index)) {
			return localVariables.remove(index);
		} else {
			return 0;
		}

	}

	public void modifyLocalVariable(int index, int newValue) {
		if (isValidIndex(index)) {
			localVariables.set(index, newValue);
		} else {
			// if given index is bigger than stackSize, push dummy variables to fill
			if (index > this.numberOfVariables - 1) {
				pushDummyVariables(index - this.numberOfVariables - 1);
				pushLocalVariable(newValue);
			}
		}
	}

	// checks if a given index is valid
	public boolean isValidIndex(int index) {
		if (index > numberOfVariables - 1 || index < 0) {
			System.err.println("Invalid index given in Frame.getVar()");
			return false;
		}

		return true;
	}

	public void pushDummyVariables(int numberOfVariables) {
		while (numberOfVariables > 0) {
			pushLocalVariable(0);
			numberOfVariables--;
		}
	}
	
	public void pushStackVariable(int element) {
		this.stack.add(element);
		this.stackSize++;
	}
	
	public int popStackVariable() {
		this.stackSize--;
		return this.stack.remove(stackSize);
	}
	
	public int peekStackVariable() {
		return this.stack.get(stackSize - 1);
	}
	
	public int getStackSize() {
		return this.stackSize;
	}
	
	public ArrayList<Integer> getStack() {
		return this.stack;
	}
}
