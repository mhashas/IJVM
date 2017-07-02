package pad.ijvm;

import pad.ijvm.interfaces.IJVMInterface;
import java.io.PrintStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

class IJVM implements IJVMInterface {
	IJVMMemory memory = null;
	PrintStream out;

	IJVM() {
		out = System.out;
	}

	public void addBinary(File binaryFile) throws IOException {
		byte[] bytes = new byte[(int) binaryFile.length()];
		bytes = readBinaryFile(binaryFile);
		BinaryFile parsedFile = new BinaryFile(bytes);
		this.memory = new IJVMMemory(parsedFile.getConstantsBlock(), parsedFile.getInstructionsBlock());
	}
	
	public byte[] readBinaryFile(File binaryFile) throws IOException {
		byte[] bytes = new byte[(int) binaryFile.length()];
		FileInputStream fileStream = new FileInputStream(binaryFile);
		fileStream.read(bytes);
		fileStream.close();
		return bytes;
		
	}
	public void clear() {
		this.memory = null;
	}

	private void iAdd() {
		this.memory.push(this.memory.pop() + this.memory.pop());
	}

	private void iSub() {
		this.memory.push(-this.memory.pop() + this.memory.pop());
	}

	private void dup() {
		this.memory.push(this.memory.peek());
	}

	private void biPush(byte element) {
		this.memory.push((int) element);
	}

	private void swap() {
		int top = this.memory.pop();
		int bottom = this.memory.pop();
		this.memory.push(top);
		this.memory.push(bottom);
	}

	private void iAnd() {
		this.memory.push(this.memory.pop() & this.memory.pop());
	}

	private void iOr() {
		this.memory.push(this.memory.pop() | this.memory.pop());
	}

	private void goTo(byte firstParameter, byte secondParameter) {
		this.memory.incrementProgramCounter(ByteConverter.convertBytesToInt(firstParameter, secondParameter) - 2);
	}

	private boolean ifEq(byte firstParameter, byte secondParameter) {
		if (this.memory.pop() == 0) {
			goTo(firstParameter, secondParameter);
			return true;
		} else {
			return false;
		}
	}

	private boolean ifEqCmpEq(byte firstParameter, byte secondParameter) {
		if (this.memory.pop() == this.memory.pop()) {
			goTo(firstParameter, secondParameter);
			return true;
		} else {
			return false;
		}
	}

	private boolean ifLt(byte firstParameter, byte secondParameter) {
		if (this.memory.pop() < 0) {
			goTo(firstParameter, secondParameter);
			return true;
		} else {
			return false;
		}
	}

	private void ldcW(byte firstParameter, byte secondParameter) {
		this.memory.push(this.memory.getConstant(ByteConverter.convertBytesToInt(firstParameter, secondParameter)));
	}

	private void iLoad(byte firstParameter) {
		this.memory.push(this.memory.getCurrentFrame().getLocalVariable((int) firstParameter));
	}

	private void iStore(byte firstParameter) {
		this.memory.getCurrentFrame().modifyLocalVariable((int) firstParameter, this.memory.pop());
	}

	private void out() {
		out.print((char) this.memory.pop());
	}

	private void iInc(byte firstParameter, byte secondParameter) {
		int value = this.memory.getCurrentFrame().getLocalVariable(firstParameter) + secondParameter;
		this.memory.getCurrentFrame().modifyLocalVariable(firstParameter, value);
	}

	private void invokeVirtual(byte firstParameter) {
		int returnPC = this.memory.getProgramCounter() + 2;
		this.memory.setProgramCounter(this.memory.getConstant(firstParameter));

		int numberOfArguments = ByteConverter.convertBytesToInt(
				this.memory.getInstruction(this.getProgramCounter()),
				this.memory.getInstruction(this.getProgramCounter() + 1));

		int[] arguments = new int[numberOfArguments];
		for (int i = numberOfArguments - 1; i > 0; i--) {
			arguments[i] = this.memory.pop();
		}
		arguments[0] = 0;

		this.memory.incrementProgramCounter(2);
		int frameSize = ByteConverter.convertBytesToInt(this.memory.getInstruction(this.memory.getProgramCounter()),
				this.memory.getInstruction(this.memory.getProgramCounter() + 1));
		this.memory.incrementProgramCounter(2);

		this.memory.getFrames().add(new Frame(returnPC));

		for (int i = 0; i < arguments.length; i++) {
			this.memory.getCurrentFrame().modifyLocalVariable(i, arguments[i]);
		}
	}

	private void iReturn() {
		int returnValue = this.memory.pop();
		
		this.memory.setProgramCounter(this.memory.getCurrentFrame().getSourceProgramCounter());
		this.memory.getFrames().remove(this.memory.getCurrentFrame());
		
		this.memory.push(returnValue);
	}

	@Override
	public int topOfStack() {
		if (this.memory.getStackSize() > 0) {
			return this.memory.peek();
		} else {
			System.err.println("Error :TOS");
			return 0;
		}
	}

	@Override
	public int[] getStackContents() {
		return this.memory.getStack().stream().mapToInt(i -> i).toArray();
	}

	@Override
	public byte[] getText() {
		return this.memory.getInstructions();
	}

	@Override
	public int getProgramCounter() {
		return this.memory.getProgramCounter();
	}

	@Override
	public int getLocalVariable(int i) {
		return this.memory.getCurrentFrame().getLocalVariable(i);
	}

	@Override
	public int getConstant(int i) {
		return this.memory.getConstant(i);
	}

	@Override
	public void step() {
		switch (getInstruction()) {
		case Instructions.IADD:
			iAdd();
			break;
		case Instructions.DUP:
			dup();
			break;
		case Instructions.BIPUSH:
			biPush(readInstructionArgument());
			break;
		case Instructions.ISUB:
			iSub();
			break;
		case Instructions.SWAP:
			swap();
			break;
		case Instructions.NOP:
			break;
		case Instructions.OUT:
			out();
			break;
		case Instructions.IAND:
			iAnd();
			break;
		case Instructions.IOR:
			iOr();
			break;
		case Instructions.POP:
			memory.pop();
			break;
		case Instructions.IFEQ:
			if (ifEq(readInstructionArgument(), readInstructionArgument()))
				return;
			break;
		case Instructions.IF_ICMPEQ:
			if (ifEqCmpEq(readInstructionArgument(), readInstructionArgument()))
				return;
			break;
		case Instructions.IFLT:
			if (ifLt(readInstructionArgument(), readInstructionArgument()))
				return;
			break;
		case Instructions.GOTO:
			goTo(readInstructionArgument(), readInstructionArgument());
			return;
		case Instructions.HALT:
			System.err.println("HALT getInstruction()");
			return;
		case Instructions.LDC_W:
			ldcW(readInstructionArgument(), readInstructionArgument());
			break;
		case Instructions.ILOAD:
			iLoad(readInstructionArgument());
			break;
		case Instructions.ISTORE:
			iStore(readInstructionArgument());
			break;
		case Instructions.IINC:
			iInc(readInstructionArgument(), readInstructionArgument());
			break;
		case Instructions.IRETURN:
			iReturn();
			return;
		case Instructions.INVOKEVIRTUAL:
			invokeVirtual(readInstructionArgument());
			return;
		case Instructions.IN:
			break;
		default:
			System.err.println("Error: Unknown instruction found");
			break;
		}
		this.memory.incrementProgramCounter(1);
		return;
	}

	@Override
	public void run() {
		while (this.memory.getInstructions().length != getProgramCounter() && getInstruction() != Instructions.HALT) {
			if (getInstruction() == Instructions.ERR) {
				break;
			}
			step();
		}
		return;

	}

	@Override
	public byte getInstruction() {
		return this.memory.getInstruction(this.memory.getProgramCounter());
	}

	@Override
	public void setOutput(PrintStream out) {
		this.out = out;
	}

	@Override
	public void setInput(InputStream in) {
		return;
	}

	public byte readInstructionArgument() {
		this.memory.incrementProgramCounter(1);
		return getInstruction();
	}
}
