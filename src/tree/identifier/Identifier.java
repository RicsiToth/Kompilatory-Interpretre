package tree.identifier;

import tree.Syntax;

public abstract class Identifier implements Syntax {

    protected String name;

    public Identifier(String name) {
        this.name = name;
    }
}
