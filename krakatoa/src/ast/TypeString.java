//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class TypeString extends Type {
    
    public TypeString() {
        super("String");
    }
    
   public String getCname() {
      return "char *";
   }

}