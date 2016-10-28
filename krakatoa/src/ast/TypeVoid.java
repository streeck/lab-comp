//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class TypeVoid extends Type {
    
    public TypeVoid() {
        super("void");
    }
    
   public String getCname() {
      return "void";
   }

}