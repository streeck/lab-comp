//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class ParenthesisExpr extends Expr {
    
    public ParenthesisExpr( Expr expr ) {
        this.expr = expr;
    }
    
    public void genC( PW pw, boolean putParenthesis ) {
        pw.print("(");
        expr.genC(pw, false);
        pw.printIdent(")");
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        pw.print("(");
        expr.genKra(pw, false);
        pw.print(")");
    }

    public Type getType() {
        return expr.getType();
    }
    
    private Expr expr;
}