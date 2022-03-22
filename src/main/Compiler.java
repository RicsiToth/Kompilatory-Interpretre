package main;

public class Compiler {
	private LexicalAnalyzator lexicalAnalyzator;
	private VirtualMachine virtualMachine;
	
	public Compiler(LexicalAnalyzator lexicalAnalyzator, VirtualMachine virtualMachine) {
		this.lexicalAnalyzator = lexicalAnalyzator;
		this.lexicalAnalyzator.scan();
		this.virtualMachine = virtualMachine;
	}

	/* Toto je kompilator, ktory vytvara bytecode v mem vo virtualnom machine*/

	public void compile(int endOfMem) {
		try {
			while (lexicalAnalyzator.getKind() != Kind.NOTHING) {
				switch (lexicalAnalyzator.getKind()) {
					case WORD:
						compileWord(endOfMem);
						break;
					case NUMBER:
						compileNumber(endOfMem);
						break;
					default:
						return;
				}
				lexicalAnalyzator.scan();
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}

	private void compileNumber(int endOfMem) {
		Integer count = Integer.valueOf(lexicalAnalyzator.getToken());
		lexicalAnalyzator.scan();
		switch (lexicalAnalyzator.getToken()) {
			case "*":
				iterate(count, endOfMem);
				break;
		}
	}

	private void compileWord(int endOfMem) {
		switch (lexicalAnalyzator.getToken()) {
			case "dopredu":
			case "dp":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER, "");
				virtualMachine.setMemValue(Instruction.FD.ordinal());
				virtualMachine.setMemValue(Integer.valueOf(lexicalAnalyzator.getToken()));
				break;
			case "vlavo":
			case "vl":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER, "");
				virtualMachine.setMemValue(Instruction.LT.ordinal());
				virtualMachine.setMemValue(Integer.valueOf(lexicalAnalyzator.getToken()));
				break;
			case "vpravo":
			case "vp":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER, "");
				virtualMachine.setMemValue(Instruction.RT.ordinal());
				virtualMachine.setMemValue(Integer.valueOf(lexicalAnalyzator.getToken()));
				break;
			case "opakuj":
			case "op":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER, "");
				iterate(Integer.valueOf(lexicalAnalyzator.getToken()), endOfMem);
				break;
			case "zmaz":
				lexicalAnalyzator.scan();
				virtualMachine.setMemValue(Instruction.CLEAR.ordinal());
				break;
			case "farba":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER, "");
				int red = Integer.valueOf(lexicalAnalyzator.getToken());
		
				lexicalAnalyzator.scan();
				check(Kind.NUMBER, "");
				int green = Integer.valueOf(lexicalAnalyzator.getToken());
				
				lexicalAnalyzator.scan();
				check(Kind.NUMBER, "");
				int blue = Integer.valueOf(lexicalAnalyzator.getToken());

				virtualMachine.setMemValue(Instruction.COLOR.ordinal());
				virtualMachine.setMemValue(red);
				virtualMachine.setMemValue(green);
				virtualMachine.setMemValue(blue);
				break;
			case "bod":
				lexicalAnalyzator.scan();
				check(Kind.NUMBER, "");
				virtualMachine.setMemValue(Instruction.DOT.ordinal());
				virtualMachine.setMemValue(Integer.valueOf(lexicalAnalyzator.getToken()));
				break;
		}
	}
	
	private void iterate(int count, int endOfMem) {
		lexicalAnalyzator.scan();
		check(Kind.SPECIAL, "[");
		lexicalAnalyzator.scan();
		virtualMachine.setMemValue(Instruction.SET.ordinal());
		virtualMachine.setMemValue(endOfMem);
		virtualMachine.setMemValue(count);
		int bodyAddr = virtualMachine.getCurrentAddr();
		compile(endOfMem - 1);
		check(Kind.SPECIAL, "]");
		virtualMachine.setMemValue(Instruction.LOOP.ordinal());
		virtualMachine.setMemValue(endOfMem);
		virtualMachine.setMemValue(bodyAddr);
	}
	
	private void check(Kind kind, String value) {
		if (lexicalAnalyzator.getKind() != kind) {
			throw new IllegalArgumentException("Expected " + kind + " but got " + lexicalAnalyzator.getKind()
			+ " with value '" + lexicalAnalyzator.getToken() + "'in position " + lexicalAnalyzator.getPosition());
		}
		if(kind == Kind.SPECIAL && !value.isEmpty()){
			if(!value.equals("]") && !value.equals("[")) {
				throw new IllegalArgumentException("Expected opening or closing bracket but got " + lexicalAnalyzator.getKind()
						+ " with value '" + lexicalAnalyzator.getToken() + "'in position " + lexicalAnalyzator.getPosition());
			}
		}

	}
}
