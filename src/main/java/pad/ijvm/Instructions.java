package pad.ijvm;


import java.util.Arrays;


public class Instructions {
    static final byte BIPUSH = 0X10;
    static final byte DUP = 0X59;
    static final byte ERR = 0XFE - 0x100;
    static final byte GOTO = 0xA7 - 0x100;
    static final byte HALT = 0xFF - 0x100;
    static final byte IADD = 0x60;
    static final byte IAND = 0x7E;
    static final byte IFEQ = 0x99 - 0x100;
    static final byte IFLT = 0x9B - 0x100;
    static final byte IF_ICMPEQ = 0x9F - 0x100;
    static final byte IINC = 0x84 - 0x100;
    static final byte ILOAD = 0x15;
    static final byte IN = 0xFC - 0x100;
    static final byte INVOKEVIRTUAL = 0xB6 - 0x100;
    static final byte IOR = 0xB0 - 0x100;
    static final byte IRETURN = 0xAC - 0x100;
    static final byte ISTORE = 0x36;
    static final byte ISUB = 0x64;
    static final byte LDC_W = 0x13;
    static final byte NOP = 0x00;
    static final byte OUT = 0xFD - 0x100;
    static final byte POP = 0x57;
    static final byte SWAP = 0x5F;
    static final byte WIDE = 0xC4 - 0x100;
    static final int MAX_SHORT_SIZE = 0xFFFF;  
}

