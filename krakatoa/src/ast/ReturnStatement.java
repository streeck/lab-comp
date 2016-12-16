//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662

package ast;


public class ReturnStatement extends Statement {
    private Expr expr;

    public ReturnStatement(Expr e) {
        this.setExpr(e);
    }

    @Override
    public void genC(PW pw, String className) {

    }

    @Override
    public void genKra(PW pw) {
        pw.print("return (");
        expr.genKra(pw, false);
        pw.println(");");
    }

    public Expr getExpr() {
        return expr;
    }

    public void setExpr(Expr expr) {
        this.expr = expr;
    }
}
