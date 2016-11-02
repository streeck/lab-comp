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
   private MethodList privateMethodLis;

   public KraClass( String name ) {
      super(name);
      publicMethodList = new MethodList();
      privateMethodLis = new MethodList();
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
      privateMethodLis.addElement(m);
   }
   public MethodList getPublicMethodList() {
      return publicMethodList;
   }

   public void setPublicMethodList(MethodList publicMethodList) {
      this.publicMethodList = publicMethodList;
   }

   public MethodList getPrivateMethodLis() {
      return privateMethodLis;
   }

   public void setPrivateMethodLis(MethodList privateMethodLis) {
      this.privateMethodLis = privateMethodLis;
   }

   //Retorna True se o metodo já existir, ou na lista de publico, ou na lista de privados.
   public boolean existMethod(Method method) {
       if( publicMethodList.exist(method) || privateMethodLis.exist(method) )
           return true;
       return false;
   }

   public boolean existInstanceVariable(InstanceVariable v){
       return instanceVariableList.exist(v);
   }
   // private MethodList publicMethodList, privateMethodList;
   // metodos publicos get e set para obter e iniciar as variaveis acima,
   // entre outros metodos
}
