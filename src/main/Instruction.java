package main;

public enum Instruction {
	BAD,
	
	//Stack instructions
	PUSH,
	
	//Arithmetic instructions
	MINUS,
	ADD,
	SUB,
	MUL,
	DIV,
	
	//Variable operations
	GET,
	SET,
	PRINT,
	JUMP,
	
	//Turtle commands and loop
	FD,
	RT,
	LT,
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
