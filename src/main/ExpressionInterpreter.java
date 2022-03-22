package main;

public class ExpressionInterpreter {
    private LexicalAnalyzator lexicalAnalyzator;

    public ExpressionInterpreter(LexicalAnalyzator lexicalAnalyzator) {
        this.lexicalAnalyzator = lexicalAnalyzator;
        this.lexicalAnalyzator.scan();
    }

    public double evaluate() {
        return evaluateOr();
    }

    private double evaluateOr() {
        double result = evaluateAnd();
        while(true) {
            if (lexicalAnalyzator.getToken().equals("or")) {
                lexicalAnalyzator.scan();
                result = (result + evaluateAnd()) % 2;
            } else {
                return result;
            }
        }
    }

    private double evaluateAnd() {
        double result = evaluateNot();
        while(true) {
            if (lexicalAnalyzator.getToken().equals("or")) {
                lexicalAnalyzator.scan();
                result *= evaluateNot();
            } else {
                return result;
            }
        }
    }

    private double evaluateNot() {
        if (!lexicalAnalyzator.getToken().equals("not")) {
            return evaluateCompare();
        }
        lexicalAnalyzator.scan();
        double result = evaluateCompare();
        if(result == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    private double evaluateCompare() {
        double result = evaluateAddOrSub();
        while(true) {
            if (lexicalAnalyzator.getToken().equals("<")) {
                lexicalAnalyzator.scan();
                return result < evaluateAddOrSub() ? 1 : 0;
            } else if (lexicalAnalyzator.getToken().equals("<=")) {
                lexicalAnalyzator.scan();
                return result <= evaluateAddOrSub() ? 1 : 0;
            } else if (lexicalAnalyzator.getToken().equals(">")) {
                lexicalAnalyzator.scan();
                return result > evaluateAddOrSub() ? 1 : 0;
            } else if (lexicalAnalyzator.getToken().equals(">=")) {
                lexicalAnalyzator.scan();
                return result >= evaluateAddOrSub() ? 1 : 0;
            } else if (lexicalAnalyzator.getToken().equals("==")) {
                lexicalAnalyzator.scan();
                return result == evaluateAddOrSub() ? 1 : 0;
            } else if (lexicalAnalyzator.getToken().equals("!=")) {
                lexicalAnalyzator.scan();
                return result != evaluateAddOrSub() ? 1 : 0;
            } else {
                return result;
            }
        }
    }

    private double evaluateBraces() {
        if(!lexicalAnalyzator.getToken().equals("(")) {
            return number();
        }
        lexicalAnalyzator.scan();
        double result = evaluateAddOrSub();
        check(Kind.SPECIAL, ")");
        lexicalAnalyzator.scan();
        return result;
    }

    private double evaluateAddOrSub() {
        double result = evaluateMultOrDiv();
        while(true) {
            if (lexicalAnalyzator.getToken().equals("+")) {
                lexicalAnalyzator.scan();
                result += evaluateMultOrDiv();
            } else if (lexicalAnalyzator.getToken().equals("-")) {
                lexicalAnalyzator.scan();
                result -= evaluateMultOrDiv();
            } else {
                return result;
            }
        }
    }

    private double evaluateMultOrDiv() {
        double result = evaluatePower();
        while(true) {
            if (lexicalAnalyzator.getToken().equals("*")) {
                lexicalAnalyzator.scan();
                result *= evaluatePower();
            } else if (lexicalAnalyzator.getToken().equals("/")) {
                lexicalAnalyzator.scan();
                result /= evaluatePower();
            } else {
                return result;
            }
        }
    }

    private double evaluatePower() {
        double result = evaluateMinus();
        if (lexicalAnalyzator.getToken().equals("^")) {
            lexicalAnalyzator.scan();
            double count = evaluateMinus();
            return Math.pow(result, count);
        }
        return result;
    }

    private double evaluateMinus() {
        if (!lexicalAnalyzator.getToken().equals("-")) {
            return evaluateBraces();
        }
        lexicalAnalyzator.scan();
        return -evaluateBraces();
    }

    private double number() {
        check(Kind.NUMBER, "");
        double result = Double.valueOf(lexicalAnalyzator.getToken());
        lexicalAnalyzator.scan();
        return result;
    }

    private void check(Kind kind, String value) {
        if (lexicalAnalyzator.getKind() != kind) {
            throw new IllegalArgumentException("Expected " + kind + " but got " + lexicalAnalyzator.getKind()
                    + " with value '" + lexicalAnalyzator.getToken() + "'in position " + lexicalAnalyzator.getPosition());
        }
        if(kind == Kind.SPECIAL && !value.isEmpty()){
            if(!value.equals(lexicalAnalyzator.getToken())) {
                throw new IllegalArgumentException("Expected opening or closing bracket but got " + lexicalAnalyzator.getKind()
                        + " with value '" + lexicalAnalyzator.getToken() + "'in position " + lexicalAnalyzator.getPosition());
            }
        }

    }
}
