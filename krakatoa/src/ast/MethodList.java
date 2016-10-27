package ast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Angela on 27/10/2016.
 */
public class MethodList {
    private ArrayList<Method> methodList;

    public MethodList() {
        methodList = new ArrayList<Method>();
    }

    public void addElement(Method m) {
        methodList.add(m);
    }

    public Iterator<Method> elements() {
        return this.methodList.iterator();
    }

    public int getSize() {
        return methodList.size();
    }


}
