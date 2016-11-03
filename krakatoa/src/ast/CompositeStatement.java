//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

/**
 * Created by Angela on 28/10/2016.
 */
public class CompositeStatement extends  Statement {
    private StatementList statementList;

    public void genC( PW pw ) {
    }

    public StatementList getStatementList() {
        return statementList;
    }

    public void setStatementList(StatementList statementList) {
        this.statementList = statementList;
    }
}
