package tree;

import main.Kind;
import main.LexicalAnalyzator;
import main.VirtualMachine;
import sun.swing.PrintColorUIResource;
import tree.binary.arithmetic.*;
import tree.binary.logical.And;
import tree.binary.logical.Or;
import tree.binary.relational.*;
import tree.ternary.IfExpression;
import tree.turtle.Fd;
import tree.turtle.Lt;
import tree.turtle.Rt;
import tree.unary.*;

public class TreeParser {

    private LexicalAnalyzator lexicalAnalyzator;
    private VirtualMachine vm;

    public TreeParser(LexicalAnalyzator lexicalAnalyzator, VirtualMachine vm) {
        this.lexicalAnalyzator = lexicalAnalyzator;
        lexicalAnalyzator.scan();
        this.vm = vm;
    }

    public Block parse() {
        Block result = new Block();
        while(lexicalAnalyzator.getKind() == Kind.WORD) {
            switch (lexicalAnalyzator.getToken()) {
	            case "vypis":
	            	lexicalAnalyzator.scan();
	            	result.add(new Print(parseExpression()));
	            	break;
                case "dopredu":
                case "dp":
                    lexicalAnalyzator.scan();
                    result.add(new Fd(parseExpression()));
                    break;
                case "vlavo":
                case "vl":
                    lexicalAnalyzator.scan();
                    result.add(new Lt(parseExpression()));
                    break;
                case "vpravo":
                case "vp":
                    lexicalAnalyzator.scan();
                    result.add(new Rt(parseExpression()));
                    break;
                case "opakuj":
                case "op":
                    lexicalAnalyzator.scan();
                    Syntax countExpression = parseExpression();
                    check(Kind.SPECIAL, "[");
                    lexicalAnalyzator.scan();
                    result.add(new Repeat(countExpression, parse()));
                    check(Kind.SPECIAL, "]");
                    lexicalAnalyzator.scan();
                    break;
                case "ak":
                    lexicalAnalyzator.scan();
                    Syntax ifCondition = parseExpression();
                    check(Kind.SPECIAL, "[");
                    lexicalAnalyzator.scan();
                    Syntax bodyForTrue = parse();
                    check(Kind.SPECIAL, "]");
                    lexicalAnalyzator.scan();
                    Syntax bodyForFalse = null;
                    if(lexicalAnalyzator.getToken().equals("[")) {
                        lexicalAnalyzator.scan();
                        bodyForFalse = parse();
                        check(Kind.SPECIAL, "]");
                        lexicalAnalyzator.scan();
                    }
                    result.add(new If(ifCondition, bodyForTrue, bodyForFalse));
                    break;
                case "kym":
                    lexicalAnalyzator.scan();
                    Syntax whileCondition = parseExpression();
                    check(Kind.SPECIAL, "[");
                    lexicalAnalyzator.scan();
                    result.add(new While(whileCondition, parse()));
                    check(Kind.SPECIAL, "]");
                    lexicalAnalyzator.scan();
                    break;
                case "definuj":
                    lexicalAnalyzator.scan();
                    check(Kind.WORD, "");
                    String subroutineName = lexicalAnalyzator.getToken();
                    if (vm.getSubroutine(subroutineName) != null || vm.getVariable(subroutineName) != null) {
                        throw new IllegalArgumentException(subroutineName + " is already in use!");
                    }
                    lexicalAnalyzator.scan();
                    check(Kind.SPECIAL, "[");
                    lexicalAnalyzator.scan();
                    Subroutine subroutine = new Subroutine();
                    vm.addSubroutine(subroutineName, subroutine);
                    subroutine.setBody(parse());
                    check(Kind.SPECIAL, "]");
                    lexicalAnalyzator.scan();
                    result.add(subroutine);
                    break;
                case "pre":
                    lexicalAnalyzator.scan();
                    check(Kind.WORD, "");
                    String variableName = lexicalAnalyzator.getToken();
                    Syntax from = parse();
                    check(Kind.SPECIAL, "...");
                    lexicalAnalyzator.scan();
                    Syntax to = parseExpression();
                    check(Kind.SPECIAL, "[");
                    lexicalAnalyzator.scan();
                    result.add(new RepeatInterval(variableName, from, to, parse()));
                    check(Kind.SPECIAL, "]");
                    lexicalAnalyzator.scan();
                    break;
                default:
                	String name = lexicalAnalyzator.getToken();
                	lexicalAnalyzator.scan();
                	if(!lexicalAnalyzator.getToken().equals("=")) {
                        if(vm.getSubroutine(name) == null) {
                            throw new IllegalArgumentException(name + " is unknown!");
                        }
                        result.add(new Call(name));
                    } else {
                        if(vm.getSubroutine(name) != null) {
                            throw new IllegalArgumentException(name + " is a subroutine!");
                        }
                        lexicalAnalyzator.scan();
                        result.add(new Assign(new Variable(name), parseExpression()));
                        if (vm.getVariable(name) == null) {
                            vm.addVariable(name, 2 + vm.getVariablesLength());
                        }
                    }
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
        return parseTernaryIf();
    }

    private Syntax parseTernaryIf() {
        Syntax condition = parseOr();
        if(lexicalAnalyzator.getToken().equals("?")) {
            lexicalAnalyzator.scan();
            Syntax ifTrue = parseExpression();
            check(Kind.SPECIAL, ":");
            lexicalAnalyzator.scan();
            return new IfExpression(condition, ifTrue, parseExpression());
        }
        return condition;
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
            return operand();
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
    
    private Syntax operand() {
    	 if (lexicalAnalyzator.getKind() == Kind.WORD) {
    		if(vm.getVariable(lexicalAnalyzator.getToken()) == null) {
    			throw new IllegalArgumentException("There is no variable with name " + lexicalAnalyzator.getToken());
    		}
    		Syntax result = new Variable(lexicalAnalyzator.getToken());
    		lexicalAnalyzator.scan();
    		return result;
    	} else {
    		return number();
    	}
    }

    private Syntax number() {
        check(Kind.NUMBER, "");
        int result = Integer.valueOf(lexicalAnalyzator.getToken());
        lexicalAnalyzator.scan();
        return new Constant(result);
    }
}
