//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Angela on 28/10/2016.
 */
public class VariableList {
    private ArrayList<Variable> varList;

    public VariableList(){
        this.varList = new ArrayList<Variable>();
    }
    public void addElement(Variable v) {
        varList.add(v);
    }

    public Iterator<Variable> elements() {
        return this.varList.iterator();
    }

    public int getSize() {
        return varList.size();
    }

}
