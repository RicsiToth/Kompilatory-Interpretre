package main;

public enum Instruction {
	BAD,
	FD,
	RT,
	LT,
	SET,
	LOOP,
	COLOR,
	DOT,
	CLEAR;
	
	public static Instruction getInstruction(int index) {
		Instruction[] values = Instruction.values();
		if(index < values.length) {
			return values[index];
		}
		return null;
	}
}
