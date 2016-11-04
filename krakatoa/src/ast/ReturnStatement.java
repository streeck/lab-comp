package ast;

/**
 * Created by Angela on 28/10/2016.
 */
public class ReturnStatement extends Statement {
    private Expr expr;

    public ReturnStatement(Expr e) {
        this.setExpr(e);
    }

    @Override
    public void genC(PW pw) {

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
