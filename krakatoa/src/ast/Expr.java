//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

abstract public class Expr {
    abstract public void genC( PW pw, boolean putParenthesis );
      // new method: the type of the expression
    abstract public Type getType();
}