//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662

package ast;

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

    @Override
    public void genKra(PW pw) {
        pw.print("while (");
        expr.genKra(pw, false);
        pw.print(") ");

        pw.add();
        stmt.genKra(pw);
        pw.sub();
    }
}
