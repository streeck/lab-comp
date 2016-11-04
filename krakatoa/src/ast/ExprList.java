//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.*;

public class ExprList {
    private ArrayList<Expr> exprList;

    public ExprList() {
        setExprList(new ArrayList<Expr>());
    }

    public void addElement( Expr expr ) {
        getExprList().add(expr);
    }

    public void genC( PW pw ) {

        int size = getExprList().size();
        for ( Expr e : getExprList()) {
        	e.genC(pw, false);
            if ( --size > 0 )
                pw.print(", ");
        }
    }

    public void genKra(PW pw){
        if (!exprList.isEmpty()) {
            int size = exprList.size();
            for (Expr expr : exprList) {
                if (expr != null) {
                    expr.genKra(pw, false);
                    size = size - 1;
                    if (size > 0) {
                        pw.print(", ");
                    }
                }
            }
        }
    }

    public boolean containsBooleanType() {
        for(Expr e: getExprList()){
            if(e.getType() == Type.booleanType)
                return true;
        }
        return false;
    }

    public int getSize() {
      return getExprList().size();
    }

    public ArrayList<Expr> getExprList() {
        return exprList;
    }

    public void setExprList(ArrayList<Expr> exprList) {
        this.exprList = exprList;
    }
    public Iterator<Expr> elements() {
        return getExprList().iterator();
    }

}
