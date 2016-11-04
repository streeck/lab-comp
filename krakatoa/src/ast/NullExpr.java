//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class NullExpr extends Expr {
    
   public void genC( PW pw, boolean putParenthesis ) {
      pw.printIdent("NULL");
   }

   @Override
   public void genKra(PW pw, boolean putParenthesis) {
      pw.print("null");
   }

   public Type getType() {
      //# corrija
      return null;
   }
}