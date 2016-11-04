//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;


public class CompositeStatement extends  Statement {
    private StatementList statementList;

    public void genC( PW pw ) {
    }

    @Override
    public void genKra(PW pw) {
        pw.println("{");
        pw.add();
        statementList.genKra(pw);
        pw.sub();
        pw.printlnIdent("}");
    }

    public StatementList getStatementList() {
        return statementList;
    }

    public void setStatementList(StatementList statementList) {
        this.statementList = statementList;
    }
}
