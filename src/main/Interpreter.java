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
		while(lexicalAnalyztor.getKind() == Kind.WORD || lexicalAnalyztor.getKind() == Kind.NUMBER) {
			if(lexicalAnalyztor.getKind() == Kind.NUMBER) {
				Integer count = Integer.valueOf(lexicalAnalyztor.getToken());
				lexicalAnalyztor.scan();
				switch (lexicalAnalyztor.getToken()) {
					case "*":
						iterate(count);
						break;
				}
			} else {
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
							iterate(Integer.valueOf(lexicalAnalyztor.getToken()));
						}
						break;
					case "zmaz":
						turtle.clean();
						break;
					case "farba":
						lexicalAnalyztor.scan();
						Integer red = Integer.valueOf(lexicalAnalyztor.getToken());
						lexicalAnalyztor.scan();
						Integer green = Integer.valueOf(lexicalAnalyztor.getToken());
						lexicalAnalyztor.scan();
						Integer blue = Integer.valueOf(lexicalAnalyztor.getToken());
						turtle.setStroke(red, green, blue);
						break;
					case "bod":
						lexicalAnalyztor.scan();
						if(lexicalAnalyztor.getKind() == Kind.NUMBER) {
							turtle.drawDot(Integer.valueOf(lexicalAnalyztor.getToken()));
						}
						break;
				}
			}
			lexicalAnalyztor.scan();
		}
	}
	
	private void iterate(int count) {
		lexicalAnalyztor.scan();
		if(lexicalAnalyztor.getToken().equals("[")) {
			lexicalAnalyztor.scan();
			int start = lexicalAnalyztor.getPosition();
			for(int i = 0; i < count; i++) {
				lexicalAnalyztor.rollbackInputParser(start);
				interpret();
			}
		}
	}

}
