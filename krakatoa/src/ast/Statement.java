//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

abstract public class Statement {

	abstract public void genC(PW pw);

	abstract public void genKra(PW pw);

    //Para a classe AssignStatement
    //public abstract boolean searchVariable(String variableName);
    //public abstract Variable getVariable(String variableName);
}
