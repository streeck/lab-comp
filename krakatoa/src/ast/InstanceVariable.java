//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class InstanceVariable extends Variable {

    public InstanceVariable( String name, Type type ) {
        super(name, type);
    }

    public void genKra(PW pw) {
        pw.print("private ");
        pw.print(this.getType().getName());
        pw.print(" ");
        pw.print(this.getName());
        pw.println(";");
    }
}