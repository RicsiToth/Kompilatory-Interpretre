package main;

public class Compiler {
	private LexicalAnalyztor lexicalAnalyztor;
	private VirtualMachine virtualMachine;
	
	public Compiler(LexicalAnalyztor lexicalAnalyztor, VirtualMachine virtualMachine) {
		this.lexicalAnalyztor = lexicalAnalyztor;
		this.lexicalAnalyztor.scan();
		this.virtualMachine = virtualMachine;
	}

	/* Toto je kompilator, ktory vytvara bytecode v mem vo virtualnom machine*/

	public void compile(int endOfMem) {
		try {
			while (lexicalAnalyztor.getKind() != Kind.NOTHING) {
				switch (lexicalAnalyztor.getKind()) {
					case WORD:
						compileWord(endOfMem);
						break;
					case NUMBER:
						compileNumber(endOfMem);
						break;
					default:
						return;
				}
				lexicalAnalyztor.scan();
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}

	private void compileNumber(int endOfMem) {
		Integer count = Integer.valueOf(lexicalAnalyztor.getToken());
		lexicalAnalyztor.scan();
		switch (lexicalAnalyztor.getToken()) {
			case "*":
				iterate(count, endOfMem);
				break;
		}
	}

	private void compileWord(int endOfMem) {
		switch (lexicalAnalyztor.getToken()) {
			case "dopredu":
			case "dp":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				virtualMachine.setMemValue(Instruction.FD.ordinal());
				virtualMachine.setMemValue(Integer.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "vlavo":
			case "vl":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				virtualMachine.setMemValue(Instruction.LT.ordinal());
				virtualMachine.setMemValue(Integer.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "vpravo":
			case "vp":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				virtualMachine.setMemValue(Instruction.RT.ordinal());
				virtualMachine.setMemValue(Integer.valueOf(lexicalAnalyztor.getToken()));
				break;
			case "opakuj":
			case "op":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				iterate(Integer.valueOf(lexicalAnalyztor.getToken()), endOfMem);
				break;
			case "zmaz":
				lexicalAnalyztor.scan();
				virtualMachine.setMemValue(Instruction.CLEAR.ordinal());
				break;
			case "farba":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				int red = Integer.valueOf(lexicalAnalyztor.getToken());
		
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				int green = Integer.valueOf(lexicalAnalyztor.getToken());
				
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				int blue = Integer.valueOf(lexicalAnalyztor.getToken());

				virtualMachine.setMemValue(Instruction.COLOR.ordinal());
				virtualMachine.setMemValue(red);
				virtualMachine.setMemValue(green);
				virtualMachine.setMemValue(blue);
				break;
			case "bod":
				lexicalAnalyztor.scan();
				check(Kind.NUMBER, "");
				virtualMachine.setMemValue(Instruction.DOT.ordinal());
				virtualMachine.setMemValue(Integer.valueOf(lexicalAnalyztor.getToken()));
				break;
		}
	}
	
	private void iterate(int count, int endOfMem) {
		lexicalAnalyztor.scan();
		check(Kind.SPECIAL, "[");
		lexicalAnalyztor.scan();
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
		if (lexicalAnalyztor.getKind() != kind) {
			throw new IllegalArgumentException("Expected " + kind + " but got " + lexicalAnalyztor.getKind()
			+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
		}
		if(kind == Kind.SPECIAL && !value.isEmpty()){
			if(!value.equals("]") && !value.equals("[")) {
				throw new IllegalArgumentException("Expected opening or closing bracket but got " + lexicalAnalyztor.getKind()
						+ " with value '" + lexicalAnalyztor.getToken() + "'in position " + lexicalAnalyztor.getPosition());
			}
		}

	}
}
