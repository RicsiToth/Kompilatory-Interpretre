package main;

public class Interpreter {
	
	private LexicalAnalyztor lexicalAnalyztor;
	private Turtle turtle;
	
	public Interpreter(LexicalAnalyztor lexicalAnalyztor, Turtle turtle) {
		this.lexicalAnalyztor = lexicalAnalyztor;
		this.lexicalAnalyztor.scan();
		this.turtle = turtle;
	}
	
	public void interpret() {
		while(lexicalAnalyztor.getKind() == Kind.WORD) {
			switch(lexicalAnalyztor.getToken()) {
				case "dopredu":
				case "dp":
					lexicalAnalyztor.scan();
					if(lexicalAnalyztor.getKind() == Kind.NUMBER) {
						turtle.forward(Double.valueOf(lexicalAnalyztor.getToken()));
					}
					break;
				case "vlavo":
				case "vl":
					lexicalAnalyztor.scan();
					if(lexicalAnalyztor.getKind() == Kind.NUMBER) {
						turtle.turnLeft(Double.valueOf(lexicalAnalyztor.getToken()));
					}
					break;
				case "vpravo":
				case "vp":
					lexicalAnalyztor.scan();
					if(lexicalAnalyztor.getKind() == Kind.NUMBER) {
						turtle.turnRight(Double.valueOf(lexicalAnalyztor.getToken()));
					}
					break;
				case "opakuj":
				case "op":
					lexicalAnalyztor.scan();
					if(lexicalAnalyztor.getKind() == Kind.NUMBER) {
						int count = Integer.valueOf(lexicalAnalyztor.getToken());
						lexicalAnalyztor.scan();
						if(lexicalAnalyztor.getToken().equals("[")) {
							lexicalAnalyztor.scan();
							int start = lexicalAnalyztor.getPosition();
							for(int i = 0; i < count; i++) {
								lexicalAnalyztor.rollbackInputParser(start);
								interpret();
							}
						}
						if(lexicalAnalyztor.getToken().equals("]")) {
							lexicalAnalyztor.scan();
						}
					}
					break;
			}
			lexicalAnalyztor.scan();
		}
	}

}
