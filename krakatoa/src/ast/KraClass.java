//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;
/*
 * Krakatoa Class
 */
public class KraClass extends Type {

   private String name;
   private KraClass superclass;
   private InstanceVariableList instanceVariableList;
   private MethodList publicMethodList;
   private MethodList privateMethodList;

   public KraClass( String name ) {
      super(name);
      publicMethodList = new MethodList();
      privateMethodList = new MethodList();
      instanceVariableList = new InstanceVariableList();
   }

   public String getCname() {
      return getName();
   }

   public void setName(String name) {
      this.name = name;
   }

   public KraClass getSuperclass() {
      return superclass;
   }

   public void setSuperclass(KraClass superclass) {
      this.superclass = superclass;
   }

   public InstanceVariableList getInstanceVariableList() {
      return instanceVariableList;
   }

   public void setInstanceVariableList(InstanceVariableList instanceVariableList) {
      this.instanceVariableList = instanceVariableList;
   }

   public void addPublicMethod(Method m){
      publicMethodList.addElement(m);
   }

   public void addPrivateMethod(Method m){
      privateMethodList.addElement(m);
   }
   public MethodList getPublicMethodList() {
      return publicMethodList;
   }

   public void setPublicMethodList(MethodList publicMethodList) {
      this.publicMethodList = publicMethodList;
   }

   public MethodList getPrivateMethodLis() {
      return privateMethodList;
   }

   public void setPrivateMethodLis(MethodList privateMethodList) {
      this.privateMethodList = privateMethodList;
   }

   //Retorna True se o metodo já existir, ou na lista de publico, ou na lista de privados.
   public boolean existMethod(Method method) {
       if( publicMethodList.exist(method) || privateMethodList.exist(method) )
           return true;
       return false;
   }
   public boolean existMethod(String method) {
      if( publicMethodList.exist(method) || privateMethodList.exist(method) )
         return true;
      return false;
   }
   public boolean existInstanceVariable(InstanceVariable v){
       return instanceVariableList.exist(v);
   }
   public boolean existInstanceVariable(String v){
       return instanceVariableList.exist(v);
   }

   public Method fetchMethod(String ident) {
     if (this.publicMethodList.exist(ident)) {
       return this.publicMethodList.getVariable(ident);
     } else {
       return this.privateMethodList.getVariable(ident);
     }
   }
   // private MethodList publicMethodList, privateMethodList;
   // metodos publicos get e set para obter e iniciar as variaveis acima,
   // entre outros metodos
}
