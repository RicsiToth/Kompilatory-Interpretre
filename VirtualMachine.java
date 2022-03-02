package main;

public class VirtualMachine {
	private int pc = 0;
	private boolean terminated = false;
	private int[] mem;
	private Turtle turtle;
	
	public VirtualMachine(Turtle turtle, int lenght) {
		this.turtle = turtle;
		mem = new int[lenght];
	}

	public void reset(int pc) {
		if(pc < this.pc) {
			this.pc = pc;
			terminated = false;
		}
	}
	
	public boolean isTerminated() {
		return terminated;
	}
	
	public int setMemValue(int addr, int value) {
		mem[addr] = value;
		return addr + 1;
	}
	
	public void execute() {
		int index;
		switch(Instruction.getInstruction(mem[pc])) {
			case FD:
				pc++;
				turtle.forward(mem[pc]);
				pc++;
				break;
			case LT:
				pc++;
				turtle.turnLeft(mem[pc]);
				pc++;
				break;
			case RT:
				pc++;
				turtle.turnRight(mem[pc]);
				pc++;
				break;
			case SET:
				pc++;
				//Address where the counter goes
				index = mem[pc];
				pc++;
				//Setting the counter to the address
				mem[index] = mem[pc];
				pc++;
				break;
			case LOOP:
				pc++;
				//Get the address where the counter is
				index = mem[pc];
				pc++;
				mem[index] = mem[index] - 1;
				if(mem[index] > 0) {
					pc = mem[pc];
				} else {
					pc++;
				}
				break;
			default:
				terminated = true;
				return;
		}
	}
	
	public void disassemble() {
		reset(0);
		int index;
		while(!terminated) {
			int prevPc = pc;
			switch(Instruction.getInstruction(mem[pc])) {
			case FD:
				pc++;
				System.out.println(prevPc + "   FD   " + mem[pc]);
				pc++;
				break;
			case LT:
				pc++;
				System.out.println(prevPc + "   LT   " + mem[pc]);
				pc++;
				break;
			case RT:
				pc++;
				System.out.println(prevPc + "   RT   " + mem[pc]);
				pc++;
				break;
			case SET:
				pc++;
				//Address where the counter goes
				index = mem[pc];
				pc++;
				//Setting the counter to the address
				System.out.println(prevPc + "   SET   [" + index  + "], "+ mem[pc]);
				pc++;
				break;
			case LOOP:
				pc++;
				//Get the address where the counter is
				index = mem[pc];
				pc++;
				System.out.println(prevPc + "   LOOP   [" + index  + "], "+ mem[pc]);
				pc++;
				break;
			default:
				terminated = true;
				break;
			}	
		}
		reset(0);
	}
	
}
