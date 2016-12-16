//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class VariableExpr extends Expr {
    
    public VariableExpr( Variable v ) {
        this.v = v;
    }
    
    public void genC( PW pw, boolean putParenthesis ) {
        pw.print(" _" + v.getName());
    }

    @Override
    public void genKra(PW pw, boolean putParenthesis) {
        if (putParenthesis) {
            pw.print("(");
            pw.print(v.getName());
            pw.print(")");
        } else
            pw.print(v.getName());
    }

    public Type getType() {
        return v.getType();
    }
    
    private Variable v;
}