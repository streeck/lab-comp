//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import com.sun.org.apache.bcel.internal.classfile.LocalVariable;

import java.util.*;

public class LocalVariableList {
    private ArrayList<Variable> localList;

    public LocalVariableList() {
       localList = new ArrayList<Variable>();
    }

    public void addElement(Variable v) {
       localList.add(v);
    }

    public Iterator<Variable> elements() {
        return localList.iterator();
    }

    public int getSize() {
        return localList.size();
    }

    public boolean hasVariable(String name) {
        for(Variable v: localList){
            if(v.getName().equals("name")) return true;
        }
        return false;
    }

    public Variable getVariable(String name) {
        for(Variable v: localList){
            if(v.getName().equals("name")) return v;
        }
        return null;
    }
}
