package main;

public enum Instruction {
	BAD, 	// 0
	
	//Stack instructions
	PUSH,	// 1
	
	//Arithmetic instructions
	MINUS,	// 2
	ADD,	// 3
	SUB,	// 4
	MUL,	// 5
	DIV,	// 6

	//Variable operations
	GET,	// 7
	SET,	// 8
	PRINT,	// 9
	JUMP,	// 10
	
	//Turtle commands and loop
	FD,		// 11
	RT,		// 12
	LT,		// 13
	LOOP,	// 14
	COLOR,	// 15
	DOT,	// 16
	CLEAR;	// 17
	
	public static Instruction getInstruction(int index) {
		Instruction[] values = Instruction.values();
		if(index < values.length) {
			return values[index];
		}
		return null;
	}
}
