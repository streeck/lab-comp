//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import lexer.*;

public class UnaryExpr extends Expr {

	public UnaryExpr(Expr expr, Symbol op) {
		this.expr = expr;
		this.op = op;
	}

	@Override
	public void genC(PW pw, boolean putParenthesis) {
		switch (op) {
		case PLUS:
			pw.print("+");
			break;
		case MINUS:
			pw.print("-");
			break;
		case NOT:
			pw.print("!");
			break;
		default:
			pw.print(" internal error at UnaryExpr::genC");

		}
		expr.genC(pw, false);
	}

	@Override
	public void genKra(PW pw, boolean putParenthesis) {
		switch (op) {
			case PLUS:
				pw.print("+");
				break;
			case MINUS:
				pw.print("-");
				break;
			case NOT:
				pw.print("!");
				break;
			default:
				pw.print(" internal error at UnaryExpr::genKra");
		}
		expr.genKra(pw, true);
	}

	@Override
	public Type getType() {
		return expr.getType();
	}

	private Expr	expr;
	private Symbol	op;
}
