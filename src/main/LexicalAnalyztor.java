package main;

public class LexicalAnalyztor {
	private InputParser inputParser;
	private Kind kind;
	private StringBuffer token;
	private int position;
	private static char TERMINATE = '\0';
	
	public LexicalAnalyztor(InputParser inputParser) {
		this.inputParser = inputParser;
		this.inputParser.next();
	}
	
	public void scan() {
		this.token = new StringBuffer();
		while(inputParser.getLook() != TERMINATE && Character.isWhitespace(inputParser.getLook())) {
			inputParser.next();
		}
		
		if(inputParser.getLook() == TERMINATE) {
			kind = Kind.NOTHING;
			return;
		}
		
		position = inputParser.getIndex() - 1;
		if(!Character.isAlphabetic(inputParser.getLook()) && !Character.isDigit(inputParser.getLook())) {
			token.append(inputParser.getLook());
			kind = Kind.SPECIAL;
			inputParser.next();
			return;
		}
		
		do {
			if(!Character.isAlphabetic(inputParser.getLook()) && !Character.isDigit(inputParser.getLook())
					&& inputParser.getLook() != '.' && inputParser.getLook() != '*') {
				break;
			}
			token.append(inputParser.getLook());
			inputParser.next();
		} while(inputParser.getLook() != TERMINATE && !Character.isWhitespace(inputParser.getLook()));
		
		if(isTokenNumber()) {
			kind = Kind.NUMBER;
		} else {
			kind = Kind.WORD;
		}
	}
	
	public String getToken() {
		return token.toString();
	}
	
	public Kind getKind() {
		return kind;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void rollbackInputParser(int index) {
		inputParser.rollbackTo(index);
		scan();
	}
	
	private boolean isTokenNumber() {
		try {
			Integer.parseInt(token.toString());
		} catch (NumberFormatException e) {
			try {
				Double.parseDouble(token.toString());
			} catch (NumberFormatException ex) {
				return false;
			}
		}
		return true;
	}
}
