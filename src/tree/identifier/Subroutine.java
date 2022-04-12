package tree.identifier;

import main.Instruction;
import main.VirtualMachine;
import tree.Syntax;

import java.util.HashMap;
import java.util.Map;

public class Subroutine extends Identifier {

    private Map<String, Variable> variables;
    private int parameterCount;
    private Syntax body;
    private int bodyAddr;

    public Subroutine(String name, Map<String, Variable> variables, Syntax body) {
        super(name);
        this.variables = new HashMap<>(variables);
        this.parameterCount = this.variables.size();
        this.body = body;
    }

    @Override
    public void execute(VirtualMachine vm) {

    }

    @Override
    public void generate(VirtualMachine vm) {
        vm.setMemValue(Instruction.JUMP.ordinal());
        vm.setMemValue(0);
        bodyAddr = vm.getCurrentAddr();
        int n = variables.size() - parameterCount;
        for(int i = 0; i < n; i++) {
            vm.setMemValue(Instruction.PUSH.ordinal());
            vm.setMemValue(0);
        }
        body.generate(vm);
        vm.setMemValue(Instruction.RETURN.ordinal());
        vm.setMemValue(parameterCount);
        vm.setMemValueToAddr(bodyAddr - 1, vm.getCurrentAddr());
    }

    @Override
    public void translate(int indent) {

    }

    @Override
    public int evaluate() {
        return 0;
    }

    public int getSubroutineAddr() {
        return bodyAddr;
    }

    public void setBody(Syntax body) {
        this.body = body;
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }

    public int getParameterCount() {
        return parameterCount;
    }
}
