package pad.ijvm;

import pad.ijvm.interfaces.IJVMInterface;
import pad.ijvm.IJVM;

import java.io.File;
import java.io.IOException;

public class MachineFactory {

	/**
	 * Creates a new machine instance, loads the binary and returns it
	 * 
	 * @param binary
	 * @return
	 * @throws IOException
	 */
	public static IJVMInterface createIJVMInstance(File binary) throws IOException {
		IJVM instance = new IJVM();

		instance.addBinary(binary);

		return instance;
	}

}
