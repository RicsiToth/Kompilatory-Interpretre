package tree;

import main.Kind;
import main.LexicalAnalyztor;
import main.VirtualMachine;

public class TreeParser {

    private LexicalAnalyztor lexicalAnalyztor;

    public TreeParser(LexicalAnalyztor lexicalAnalyztor) {
        this.lexicalAnalyztor = lexicalAnalyztor;
        lexicalAnalyztor.scan();
    }

    public Block parse() {
        Block result = new Block();
        while(lexicalAnalyztor.getKind() == Kind.WORD) {
            switch (lexicalAnalyztor.getToken()) {
                case "dopredu":
                case "dp":
                    lexicalAnalyztor.scan();
                    check(Kind.NUMBER, "");
                    result.add(new Fd(new Constant(Integer.valueOf(lexicalAnalyztor.getToken()))));
                    lexicalAnalyztor.scan();
                    break;
                case "vlavo":
                case "vl":
                    lexicalAnalyztor.scan();
                    check(Kind.NUMBER, "");
                    result.add(new Lt(new Constant(Integer.valueOf(lexicalAnalyztor.getToken()))));
                    lexicalAnalyztor.scan();
                    break;
                case "vpravo":
                case "vp":
                    lexicalAnalyztor.scan();
                    check(Kind.NUMBER, "");
                    result.add(new Rt(new Constant(Integer.valueOf(lexicalAnalyztor.getToken()))));
                    lexicalAnalyztor.scan();
                    break;
                case "opakuj":
                case "op":
                    lexicalAnalyztor.scan();
                    check(Kind.NUMBER, "");
                    Constant count = new Constant(Integer.valueOf(lexicalAnalyztor.getToken()));
                    lexicalAnalyztor.scan();
                    check(Kind.SPECIAL, "[");
                    lexicalAnalyztor.scan();
                    result.add(new Repeat(count, parse()));
                    check(Kind.SPECIAL, "]");
                    lexicalAnalyztor.scan();
                    break;
            }
        }
        return result;
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
