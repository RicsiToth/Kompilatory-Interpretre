package tree;

import main.Kind;
import main.LexicalAnalyzator;
import tree.binary.arithmetic.*;
import tree.binary.logical.And;
import tree.binary.logical.Or;
import tree.binary.relational.*;
import tree.turtle.Fd;
import tree.turtle.Lt;
import tree.turtle.Rt;
import tree.unary.*;

public class TreeParser {

    private LexicalAnalyzator lexicalAnalyzator;

    public TreeParser(LexicalAnalyzator lexicalAnalyzator) {
        this.lexicalAnalyzator = lexicalAnalyzator;
        lexicalAnalyzator.scan();
    }

    public Block parse() {
        Block result = new Block();
        while(lexicalAnalyzator.getKind() == Kind.WORD) {
            switch (lexicalAnalyzator.getToken()) {
                case "dopredu":
                case "dp":
                    lexicalAnalyzator.scan();
                    check(Kind.NUMBER, "");
                    result.add(new Fd(new Constant(Integer.valueOf(lexicalAnalyzator.getToken()))));
                    lexicalAnalyzator.scan();
                    break;
                case "vlavo":
                case "vl":
                    lexicalAnalyzator.scan();
                    check(Kind.NUMBER, "");
                    result.add(new Lt(new Constant(Integer.valueOf(lexicalAnalyzator.getToken()))));
                    lexicalAnalyzator.scan();
                    break;
                case "vpravo":
                case "vp":
                    lexicalAnalyzator.scan();
                    check(Kind.NUMBER, "");
                    result.add(new Rt(new Constant(Integer.valueOf(lexicalAnalyzator.getToken()))));
                    lexicalAnalyzator.scan();
                    break;
                case "opakuj":
                case "op":
                    lexicalAnalyzator.scan();
                    check(Kind.NUMBER, "");
                    Constant count = new Constant(Integer.valueOf(lexicalAnalyzator.getToken()));
                    lexicalAnalyzator.scan();
                    check(Kind.SPECIAL, "[");
                    lexicalAnalyzator.scan();
                    result.add(new Repeat(count, parse()));
                    check(Kind.SPECIAL, "]");
                    lexicalAnalyzator.scan();
                    break;
            }
        }
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

    public Syntax parseExpression() {
        return parseOr();
    }

    private Syntax parseOr() {
        Syntax left = parseAnd();
        while(true) {
            if (lexicalAnalyzator.getToken().equals("or")) {
                lexicalAnalyzator.scan();
                left = new Or(left, parseAnd());
            } else {
                return left;
            }
        }
    }

    private Syntax parseAnd() {
        Syntax left = parseNot();
        while(true) {
            if (lexicalAnalyzator.getToken().equals("and")) {
                lexicalAnalyzator.scan();
                left = new And(left, parseNot());
            } else {
                return left;
            }
        }
    }

    private Syntax parseNot() {
        if (!lexicalAnalyzator.getToken().equals("not")) {
            return parseCompare();
        }
        lexicalAnalyzator.scan();
        return new Not(parseCompare());
    }

    private Syntax parseCompare() {
        Syntax left = parseAddOrSub();
        while(true) {
            if (lexicalAnalyzator.getToken().equals("<")) {
                lexicalAnalyzator.scan();
                return new Less(left, parseAddOrSub());
            } else if (lexicalAnalyzator.getToken().equals("<=")) {
                lexicalAnalyzator.scan();
                return new LessEq(left, parseAddOrSub());
            } else if (lexicalAnalyzator.getToken().equals(">")) {
                lexicalAnalyzator.scan();
                return new Greater(left, parseAddOrSub());
            } else if (lexicalAnalyzator.getToken().equals(">=")) {
                lexicalAnalyzator.scan();
                return new GreaterEq(left, parseAddOrSub());
            } else if (lexicalAnalyzator.getToken().equals("==")) {
                lexicalAnalyzator.scan();
                return new Eq(left, parseAddOrSub());
            } else if (lexicalAnalyzator.getToken().equals("!=")) {
                lexicalAnalyzator.scan();
                return new NotEq(left, parseAddOrSub());
            } else {
                return left;
            }
        }
    }

    private Syntax parseBraces() {
        if(!lexicalAnalyzator.getToken().equals("(")) {
            return parseAbsolute();
        }
        lexicalAnalyzator.scan();
        Syntax result = parseAddOrSub();
        check(Kind.SPECIAL, ")");
        lexicalAnalyzator.scan();
        return new Braces(result);
    }

    private Syntax parseAbsolute() {
        if(!lexicalAnalyzator.getToken().equals("|")) {
            return number();
        }
        lexicalAnalyzator.scan();
        Syntax result = parseAddOrSub();
        check(Kind.SPECIAL, "|");
        lexicalAnalyzator.scan();
        return new Absolute(result);
    }

    private Syntax parseAddOrSub() {
        Syntax result = parseMultOrDiv();
        while(true) {
            if (lexicalAnalyzator.getToken().equals("+")) {
                lexicalAnalyzator.scan();
                result = new Add(result, parseMultOrDiv());
            } else if (lexicalAnalyzator.getToken().equals("-")) {
                lexicalAnalyzator.scan();
                result = new Sub(result, parseMultOrDiv());
            } else {
                return result;
            }
        }
    }

    private Syntax parseMultOrDiv() {
        Syntax result = parsePower();
        while(true) {
            if (lexicalAnalyzator.getToken().equals("*")) {
                lexicalAnalyzator.scan();
                result = new Mul(result, parseMultOrDiv());
            } else if (lexicalAnalyzator.getToken().equals("/")) {
                lexicalAnalyzator.scan();
                result = new Div(result, parseMultOrDiv());
            } else {
                return result;
            }
        }
    }

    private Syntax parsePower() {
        Syntax result = parseSqrt();
        if (lexicalAnalyzator.getToken().equals("^")) {
            lexicalAnalyzator.scan();
            return new Pow(result, parseSqrt());
        }
        return result;
    }

    private Syntax parseSqrt() {
        if (lexicalAnalyzator.getToken().equals("sqrt")) {
            lexicalAnalyzator.scan();
            return new Sqrt(parseMinus());
        }
        return parseMinus();
    }

    private Syntax parseMinus() {
        if (!lexicalAnalyzator.getToken().equals("-")) {
            return parseBraces();
        }
        lexicalAnalyzator.scan();
        return new Minus(parseBraces());
    }

    private Syntax number() {
        check(Kind.NUMBER, "");
        int result = Integer.valueOf(lexicalAnalyzator.getToken());
        lexicalAnalyzator.scan();
        return new Constant(result);
    }
}
