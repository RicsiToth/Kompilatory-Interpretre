package main;

public enum Instruction {
	BAD, 			// 0
	
	//Stack instructions
	PUSH,			// 1
	
	//Arithmetic instructions
	MINUS,			// 2
	ADD,			// 3
	SUB,			// 4
	MUL,			// 5
	DIV,			// 6

	//Logical instructions
	AND,			// 7
	OR,				// 8
	NOT,			// 9
	LOWER,			// 10
	LOWER_EQUAL,	// 11
	EQUAL,			// 12
	DIFFERENT,		// 13
	GREATER_EQUAL,	// 14
	GREATER,		// 15

	//Variable operations
	GET,			// 16
	SET,			// 17
	PRINT,			// 18
	JUMP,			// 19
	
	//Turtle commands and loop
	FD,				// 20
	RT,				// 21
	LT,				// 22
	LOOP,			// 23
	COLOR,			// 24
	DOT,			// 25
	CLEAR,			// 26

	JUMP_IF_FALSE, 	// 27
	CALL,			// 28
	RETURN,			// 29
	GET_LOCAL,		// 30
	SET_LOCAL;		// 31

	
	public static Instruction getInstruction(int index) {
		Instruction[] values = Instruction.values();
		if(index < values.length) {
			return values[index];
		}
		return null;
	}
}
