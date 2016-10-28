//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import lexer.Symbol;

/**
 * Created by Angela on 27/10/2016.
 */
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
}
