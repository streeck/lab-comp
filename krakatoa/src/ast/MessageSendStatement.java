//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class MessageSendStatement extends Statement { 


   public void genC( PW pw ) {
      pw.printIdent("");
      // messageSend.genC(pw);
      pw.println(";");
   }

   @Override
   public void genKra(PW pw) {

   }

   private MessageSend  messageSend;

}


