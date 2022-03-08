package main;

import turtle.Turtle;

public class VirtualMachine {
	private int pc = 0;
	private int addr = 0;
	private int endAddr;
	private boolean terminated = false;
	private int[] mem;
	private Turtle turtle;
	
	public VirtualMachine(Turtle turtle, int lenght) {
		this.turtle = turtle;
		mem = new int[lenght];
		endAddr = lenght - 1;
	}

	public void reset(int pc) {
		if(pc <= this.pc) {
			this.pc = pc;
			terminated = false;
		}
	}
	
	public boolean isTerminated() {
		return terminated;
	}
	
	public void setMemValue(int value) {
		mem[addr] = value;
		addr++;
	}

	public int getEndAddr() {
		endAddr--;
		return endAddr + 1;
	}

	public void setEndAddr(int endAddr) {
		this.endAddr = endAddr;
	}

	public int getCurrentAddr() {
		return addr;
	}

	public Turtle getTurtle() {
		return turtle;
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
			case DOT:
				pc++;
				turtle.drawDot(mem[pc]);
				pc++;
				break;
			case CLEAR:
				turtle.clean();
				pc++;
				break;
			case COLOR:
				pc++;
				int red = mem[pc];
				pc++;
				int green = mem[pc];
				pc++;
				int blue = mem[pc];
				turtle.setStroke(red, green, blue);
				pc++;
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
			case DOT:
				pc++;
				System.out.println(prevPc + "   DOT   " + mem[pc]);
				pc++;
				break;
			case CLEAR:
				System.out.println(prevPc + "   CLEAR");
				pc++;
				break;
			case COLOR:
				pc++;
				int red = mem[pc];
				pc++;
				int green = mem[pc];
				pc++;
				int blue = mem[pc];
				System.out.println(prevPc + "   COLOR   " + red + ", " + green + ", " + blue);
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
