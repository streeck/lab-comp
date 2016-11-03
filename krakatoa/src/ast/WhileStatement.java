//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662

package ast;

/**
 * Created by Angela on 28/10/2016.
 */
public class WhileStatement extends Statement {
    private Expr expr;
    private Statement stmt;

    public WhileStatement(Expr e, Statement s){
        this.expr = e;
        this.stmt = s;
    }

    @Override
    public void genC(PW pw) {

    }
}
