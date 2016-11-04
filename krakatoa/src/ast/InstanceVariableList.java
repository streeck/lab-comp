//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.*;

public class InstanceVariableList {
    private ArrayList<InstanceVariable> instanceVariableList;


    public InstanceVariableList() {
       instanceVariableList = new ArrayList<InstanceVariable>();
    }

    public void addElement(InstanceVariable instanceVariable) {
       instanceVariableList.add( instanceVariable );
    }

    public Iterator<InstanceVariable> elements() {
    	return this.instanceVariableList.iterator();
    }

    public int getSize() {
        return instanceVariableList.size();
    }

    //Retorna True se a varivel informada já existir no array
    public boolean exist(InstanceVariable variable) {
        String varName = variable.getName();
        String auxName;
        //Verifica se a variavel já foi declarada.
        if(!instanceVariableList.isEmpty()){
            for(InstanceVariable aux: instanceVariableList){
              auxName = aux.getName();
                if(auxName.equals(varName))
                    return true;
            }
        }

        return false;
    }

    public boolean exist(String variable) {
        //Verifica se a variavel já foi declarada.
        if(!instanceVariableList.isEmpty()){
            for(InstanceVariable aux: instanceVariableList){
                if(aux.getName().equals(variable))
                    return true;
            }
        }
        return false;
    }
}
