//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.*;

public class ParamList {

    public ParamList() {
       setParamList(new ArrayList<Variable>());
    }

    public void addElement(Variable v) {
       getParamList().add(v);
    }

    public Iterator<Variable> elements() {
        return getParamList().iterator();
    }

    public int getSize() {
        return getParamList().size();
    }

    private ArrayList<Variable> paramList;

    public ArrayList<Variable> getParamList() {
        return paramList;
    }

    public void setParamList(ArrayList<Variable> paramList) {
        this.paramList = paramList;
    }

    public void genKra(PW pw) {
        int size = paramList.size();
        for (Variable var : paramList) {
            pw.print(var.getType().getName());
            pw.print(" ");
            var.genKra(pw);

            size = size - 1;
            if (size > 0) {
                pw.print(", ");
            }
        }
    }

    public void genC(PW pw) {
        int size = paramList.size();
        for (Variable param : paramList) {
            pw.print(param.getType().getName());
            pw.print(" ");
            param.genC(pw);

            size = size - 1;
            if (size > 0) {
                pw.print(", ");
            }
        }
    }
}
