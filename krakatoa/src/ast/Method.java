//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import lexer.Symbol;

public class Method extends Variable{
    private ParamList paramList;
    private Symbol qualifier;
    private StatementList stmtList;

     public Method(Type type, String name, Symbol qualifier){
        super(name, type);
        this.setQualifier(qualifier);
     }

    public ParamList getParamList() {
        return paramList;
    }

    public void setParamList(ParamList paramList) {
        this.paramList = paramList;
    }

    public Symbol getQualifier() {
        return qualifier;
    }

    public void setQualifier(Symbol qualifier) {
        this.qualifier = qualifier;
    }

    public StatementList getStmtList() {
        return stmtList;
    }

    public void setStmtList(StatementList stmtList) {
        this.stmtList = stmtList;
    }

    public boolean hasReturn() {
        if(stmtList.searchReturn())
            return true;
        return false;
    }

    public boolean hasVariable(String variableName){
        if(stmtList.searchVariable(variableName))
            return true;
        return false;
    }

    public Variable getVariable(String name) {
        return stmtList.getVariable(name);
    }

    public void genKra(PW pw, boolean publicMethod) {
//        if (publicMethod) {
//            pw.print("public ");
//        } else {
//            pw.print("private ");
//        }
        pw.print(qualifier);
        pw.print(this.getType().getName() + " ");
        pw.print(this.getName() + "(");
        if (paramList != null) {
            paramList.genKra(pw);
        }
        pw.println(") {");
        pw.add();
        if (localVariableList != null) {
            localVariableList.genKra(pw);
        }
        if (stmtList != null) {
            stmtList.genKra(pw);
        }
        pw.sub();
        pw.printlnIdent("}");
    }
}
