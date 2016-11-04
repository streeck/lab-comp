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
}
