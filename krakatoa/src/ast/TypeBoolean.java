//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class TypeBoolean extends Type {

   public TypeBoolean() { super("boolean"); }

   @Override
   public String getCname() {
      return "int";
   }

}
