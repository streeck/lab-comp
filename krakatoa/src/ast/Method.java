//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import lexer.Symbol;

import java.util.Iterator;

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

    public void genKra(PW pw) {
        if (qualifier == Symbol.PUBLIC) {
            pw.printIdent("public ");
        } else {
            pw.printIdent("private ");
        }
        pw.print(this.getType().getName() + " ");
        pw.print(this.getName() + "(");
        if (paramList != null) {
            paramList.genKra(pw);
        }
        pw.println(") {");
        pw.add();
        Iterator<Statement> stmts = stmtList.elements();
        while (stmts.hasNext()) {
            Statement stmt = stmts.next();
            if (stmt instanceof AssignStatement) {
                LocalVariableList varList = ((AssignStatement) stmt).getVarList();
                varList.genKra(pw);
            }
        }
        if (stmtList != null) {
            stmtList.genKra(pw);
        }
        pw.sub();
        pw.printlnIdent("}");
    }

    public void genC(PW pw, String className) {
        pw.print(getType().getCname());
        pw.print(" _" + className + "_" + getName() + "(_class_" + className + " *this");
        if (paramList != null) {
            if (paramList.getSize() > 0)
                pw.print(", ");
            paramList.genC(pw);
        }
        pw.println(" ) {");
        pw.add();

        if (stmtList != null) {
            stmtList.genC(pw, className);
        }
        pw.sub();
        pw.printlnIdent("}");
    }
}
