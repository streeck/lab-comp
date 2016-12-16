//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

public class StatementAssert extends Statement {
	public StatementAssert(Expr expr, int lineNumber, String message) {
		this.expr = expr;
		this.lineNumber = lineNumber;
		this.message = message;
	}
	@Override
	public void genC(PW pw, String className) {
		pw.printIdent("if ( !( ");
		expr.genC(pw, false);
		pw.println(" ) ) {");
		pw.add();
		pw.printlnIdent("puts(\"" + message +  "\");");
		pw.sub();
		pw.printlnIdent("}");
	}

	@Override
	public void genKra(PW pw) {
		pw.printIdent("if ( !( ");
		expr.genKra(pw, false);
		pw.println(" ) ) {");
		pw.add();
		pw.printlnIdent("write(\"" + message +  "\");");
		pw.sub();
		pw.printlnIdent("}");
	}

	public Expr getExpr() {
		return expr;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getMessage() {
		return message;
	}

	private Expr expr;
	private int lineNumber;
	private String message;
}
