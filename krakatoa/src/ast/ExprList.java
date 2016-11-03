//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.*;

public class ExprList {
    private ArrayList<Expr> exprList;

    public ExprList() {
        exprList = new ArrayList<Expr>();
    }

    public void addElement( Expr expr ) {
        exprList.add(expr);
    }

    public void genC( PW pw ) {

        int size = exprList.size();
        for ( Expr e : exprList ) {
        	e.genC(pw, false);
            if ( --size > 0 )
                pw.print(", ");
        }
    }

    public boolean containsBooleanType() {
        for(Expr e: exprList){
            if(e.getType() == Type.booleanType)
                return true;
        }
        return false;
    }
}
