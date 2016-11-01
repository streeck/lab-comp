
package comp;

import ast.*;
import lexer.*;
import java.io.*;
import java.util.*;

public class Compiler {

	// compile must receive an input with an character less than
	// p_input.lenght
	public Program compile(char[] input, PrintWriter outError) {

		ArrayList<CompilationError> compilationErrorList = new ArrayList<>();
		signalError = new ErrorSignaller(outError, compilationErrorList);
		symbolTable = new SymbolTable();
		lexer = new Lexer(input, signalError);
		signalError.setLexer(lexer);

		Program program = null;
		lexer.nextToken();
		program = program(compilationErrorList);
		return program;
	}

	private Program program(ArrayList<CompilationError> compilationErrorList) {
		// Program ::= KraClass { KraClass }
		ArrayList<MetaobjectCall> metaobjectCallList = new ArrayList<>();
		ArrayList<KraClass> kraClassList = new ArrayList<>();
		try {
			while ( lexer.token == Symbol.MOCall ) {
				metaobjectCallList.add(metaobjectCall());
			}
			kraClassList.add(classDec());
			while ( lexer.token == Symbol.CLASS )
				kraClassList.add(classDec());
			if ( lexer.token != Symbol.EOF ) {
				signalError.showError("End of file expected");
			}
		}
		catch( RuntimeException e) {
			// if there was an exception, there is a compilation signalError
		}
		Program program = new Program(kraClassList, metaobjectCallList, compilationErrorList);
		return program;
	}

	/**  parses a metaobject call as <code>{@literal @}ce(...)</code> in <br>
     * <code>
     * @ce(5, "'class' expected") <br>
     * clas Program <br>
     *     public void run() { } <br>
     * end <br>
     * </code>
     * 
	   
	 */
	@SuppressWarnings("incomplete-switch")
	private MetaobjectCall metaobjectCall() {
		String name = lexer.getMetaobjectName();
		lexer.nextToken();
		ArrayList<Object> metaobjectParamList = new ArrayList<>();
		if ( lexer.token == Symbol.LEFTPAR ) {
			// metaobject call with parameters
			lexer.nextToken();
			while ( lexer.token == Symbol.LITERALINT || lexer.token == Symbol.LITERALSTRING ||
					lexer.token == Symbol.IDENT ) {
				switch ( lexer.token ) {
				case LITERALINT:
					metaobjectParamList.add(lexer.getNumberValue());
					break;
				case LITERALSTRING:
					metaobjectParamList.add(lexer.getLiteralStringValue());
					break;
				case IDENT:
					metaobjectParamList.add(lexer.getStringValue());
				}
				lexer.nextToken();
				if ( lexer.token == Symbol.COMMA ) 
					lexer.nextToken();
				else
					break;
			}
			if ( lexer.token != Symbol.RIGHTPAR ) 
				signalError.showError("')' expected after metaobject call with parameters");
			else
				lexer.nextToken();
		}
		if ( name.equals("nce") ) {
			if ( metaobjectParamList.size() != 0 )
				signalError.showError("Metaobject 'nce' does not take parameters");
		}
		else if ( name.equals("ce") ) {
			if ( metaobjectParamList.size() != 3 && metaobjectParamList.size() != 4 )
				signalError.showError("Metaobject 'ce' take three or four parameters");
			if ( !( metaobjectParamList.get(0) instanceof Integer)  )
				signalError.showError("The first parameter of metaobject 'ce' should be an integer number");
			if ( !( metaobjectParamList.get(1) instanceof String) ||  !( metaobjectParamList.get(2) instanceof String) )
				signalError.showError("The second and third parameters of metaobject 'ce' should be literal strings");
			if ( metaobjectParamList.size() >= 4 && !( metaobjectParamList.get(3) instanceof String) )  
				signalError.showError("The fourth parameter of metaobject 'ce' should be a literal string");
			
		}
			
		return new MetaobjectCall(name, metaobjectParamList);
	}

	private KraClass classDec() {
		// Note que os metodos desta classe nao correspondem exatamente as
		// regras
		// da gramatica. Este metodo classDec, por exemplo, implementa
		// a producao KraClass (veja abaixo) e partes de outras producoes.

		/*
		 * KraClass ::= ``class'' Id [ ``extends'' Id ] "{" MemberList "}"
		 * MemberList ::= { Qualifier Member } 
		 * Member ::= InstVarDec | MethodDec
		 * InstVarDec ::= Type IdList ";" 
		 * MethodDec ::= Qualifier Type Id "("[ FormalParamDec ] ")" "{" StatementList "}" 
		 * Qualifier ::= [ "static" ]  ( "private" | "public" )
		 */
		KraClass kraClass, superClass;
        InstanceVariableList variableList = new InstanceVariableList();
        //MethodList methodList = new MethodList();
		Method method = null;
        Boolean publicQualifier = false; //Indica se o qualifier é publico ou privado

		if ( lexer.token != Symbol.CLASS ) signalError.showError("'class' expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.IDENT )
			signalError.show(ErrorSignaller.ident_expected);
		String className = lexer.getStringValue();

		//Verifica se a classe ja foi declarada anteriormente
		kraClass = symbolTable.getInGlobal(className);
		if(kraClass != null) signalError.showError("Class "+className+"already declared");
		else { //Caso nao tenha sido, ela eh colocada na tabela de classes
			   //CurrentClass guarda a classe atual para futuras verificações
			kraClass = new KraClass(className);
			symbolTable.putInGlobal(className, kraClass);
			currentClass = kraClass;
		}
		lexer.nextToken();
		if ( lexer.token == Symbol.EXTENDS ) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.show(ErrorSignaller.ident_expected);
			String superclassName = lexer.getStringValue();

            //Verifica se a superclasse já foi declarada previamente.
			//Verifica também se a superclasse tem o mesmo nome que a classe avaliada no momento.
            superClass = symbolTable.getInGlobal(superclassName);
            if(superClass == null) signalError.showError("Super Class wasn't declared");
            else if(superclassName.equals(className)) signalError.showError("Super Class has the same name name as the class");
			else kraClass.setSuperclass(superClass);

            lexer.nextToken();
		}
		if ( lexer.token != Symbol.LEFTCURBRACKET )
			signalError.showError("{ expected", true);
		lexer.nextToken();

		while (lexer.token == Symbol.PRIVATE || lexer.token == Symbol.PUBLIC) {

			Symbol qualifier;
			switch (lexer.token) {
			case PRIVATE:
				lexer.nextToken();
				qualifier = Symbol.PRIVATE;
                publicQualifier = false;
				break;
			case PUBLIC:
				lexer.nextToken();
				qualifier = Symbol.PUBLIC;
                publicQualifier = true;
				break;
			default:
				signalError.showError("private, or public expected");
				qualifier = Symbol.PUBLIC;
                publicQualifier = true;
			}
			Type t = type();
			if ( lexer.token != Symbol.IDENT )
				signalError.showError("Identifier expected");
			String name = lexer.getStringValue();
			lexer.nextToken();
			if ( lexer.token == Symbol.LEFTPAR ){
				method  = methodDec(t, name, qualifier);
                //Verifica se o methodo ja existe na lista
                //Se nao existir, add. Se existir mostra erro
                if(!kraClass.exitMethod(method)) {
                    //Da set nos métodos publicos ou privados com base no qualifier
                    if (publicQualifier) kraClass.addPublicMethod(method);
                    else kraClass.addPrivateMethod(method);
                }else signalError.showError("Method "+method.getName()+" already declared.");

			}
			else if ( qualifier != Symbol.PRIVATE )
				signalError.showError("Attempt to declare a public instance variable");
			else
                variableList = instanceVarDec(t, name);
		}
		if ( lexer.token != Symbol.RIGHTCURBRACKET )
			signalError.showError("public/private or \"}\" expected");

		kraClass.setInstanceVariableList(variableList);
		lexer.nextToken();

		currentClass = kraClass;
	return kraClass;
	}

	private InstanceVariableList instanceVarDec(Type type, String name) {
		// InstVarDec ::= [ "static" ] "private" Type IdList ";"
        InstanceVariableList variableList = new InstanceVariableList();
		InstanceVariable v = null;
		InstanceVariable variable;
        //Add a variavel na lista de variaveis de instancia
        variableList.addElement( new InstanceVariable(name,type));

        while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.showError("Identifier expected");
			String variableName = lexer.getStringValue();
			variable = new InstanceVariable(variableName,type);
			//Add a variavel na lista
			//Se a variavel nao exisitir na lista, ela addiciona, se ja existir exibe erro de redeclaracao
			if(!variableList.exist(variable))
				variableList.addElement(variable);
			else signalError.showError("Instance Variable "+variable.getName()+" already declared");

			lexer.nextToken();
		}
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();
        return variableList;
	}

	private Method methodDec(Type type, String name, Symbol qualifier) {
		/*
		 * MethodDec ::= Qualifier Return Id "("[ FormalParamDec ] ")" "{"
		 *                StatementList "}"
		 */
        Method method = new Method(type, name, qualifier);
		lexer.nextToken();
		if ( lexer.token != Symbol.RIGHTPAR ){
			method.setParamList(formalParamDec());
		}
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.showError(") expected");

		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTCURBRACKET ) signalError.showError("{ expected");

		lexer.nextToken();
		method.setStmtList(statementList());
		if ( lexer.token != Symbol.RIGHTCURBRACKET ) signalError.showError("} expected");

		lexer.nextToken();
        return method;
	}

	private ParamList formalParamDec() {
		// FormalParamDec ::= ParamDec { "," ParamDec }
		ParamList paramList = new ParamList();
		paramList.addElement(paramDec());
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			paramList.addElement(paramDec());
		}
		return paramList;
	}

	private Parameter paramDec() {
		// ParamDec ::= Type Id
		Parameter parameter;
		Type type;
		String name;
		type = type();
		if ( lexer.token != Symbol.IDENT ) signalError.showError("Identifier expected");

		//Nao sei ao certo se é assim que se pega o nome do paramentro a partir do lexer.
		name = lexer.getStringValue();

		parameter = new Parameter(name, type);
		lexer.nextToken();

		return parameter;
	}

	private Type type() {
		// Type ::= BasicType | Id
		Type result;

		switch (lexer.token) {
		case VOID:
			result = Type.voidType;
			break;
		case INT:
			result = Type.intType;
			break;
		case BOOLEAN:
			result = Type.booleanType;
			break;
		case STRING:
			result = Type.stringType;
			break;
		case IDENT:
			// # corrija: faca uma busca na TS para buscar a classe
			// IDENT deve ser uma classe.
			result = null;
			break;
		default:
			signalError.showError("Type expected");
			result = Type.undefinedType;
		}
		lexer.nextToken();
		return result;
	}

	private StatementList statementList() {
		// CompStatement ::= "{" { Statement } "}"
		StatementList stmtList = new StatementList();
		Symbol tk;
		// statements always begin with an identifier, if, read, write, ...
		while ((tk = lexer.token) != Symbol.RIGHTCURBRACKET
				&& tk != Symbol.ELSE)
			stmtList.addElement(statement());

		return stmtList;
	}

	private Statement statement() {
		/*
		 * Statement ::= Assignment ``;'' | IfStat |WhileStat | MessageSend
		 *                ``;'' | ReturnStat ``;'' | ReadStat ``;'' | WriteStat ``;'' |
		 *               ``break'' ``;'' | ``;'' | CompStatement | LocalDec
		 */
        Statement stmt = null;
		switch (lexer.token) {
		case THIS:
		case IDENT:
		case SUPER:
		case INT:
		case BOOLEAN:
		case STRING:
			return assignExprLocalDec();
			//break;
		case ASSERT:
			return assertStatement();
			//break;
		case RETURN:
			return returnStatement();
			//break;
		case READ:
			return readStatement();
			//break;
		case WRITE:
			return writeStatement();
			//break;
		case WRITELN:
			return writelnStatement();
			//break;
		case IF:
			return ifStatement();
			//break;
		case BREAK:
			return breakStatement();
			//break;
		case WHILE:
			return whileStatement();
			//break;
		case SEMICOLON:
			return nullStatement();
			//break;
		case LEFTCURBRACKET:
			return compositeStatement();
			//break;
		default:
			signalError.showError("Statement expected");
		}
		return stmt;
	}

	private StatementAssert assertStatement() {
		lexer.nextToken();
		int lineNumber = lexer.getLineNumber();
		Expr e = expr();
		if ( e.getType() != Type.booleanType )
			signalError.showError("boolean expression expected");
		if ( lexer.token != Symbol.COMMA ) {
			this.signalError.showError("',' expected after the expression of the 'assert' statement");
		}
		lexer.nextToken();
		if ( lexer.token != Symbol.LITERALSTRING ) {
			this.signalError.showError("A literal string expected after the ',' of the 'assert' statement");
		}
		String message = lexer.getLiteralStringValue();
		lexer.nextToken();
		if ( lexer.token == Symbol.SEMICOLON )
			lexer.nextToken();
		
		return new StatementAssert(e, lineNumber, message);
	}

	private LocalVariableList localDec() {
		// LocalDec ::= Type IdList ";"
		LocalVariableList localVariableList = new LocalVariableList();
		Type type = type();
		if ( lexer.token != Symbol.IDENT ) signalError.showError("Identifier expected");
		Variable v = new Variable(lexer.getStringValue(), type);
        //Add a variavel, caso ela não exista ainda, na tabela local
        Variable aux = symbolTable.putInLocal(lexer.getStringValue(), v);
        //Verifica se ela já existia
        if(aux == null) localVariableList.addElement(v);
        else signalError.showError("Variable " + lexer.getStringValue() + "is being redeclared");

        lexer.nextToken();

        while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.showError("Identifier expected");
			v = new Variable(lexer.getStringValue(), type);
            //Add a variavel, caso ela não exista ainda, na tabela local
            aux = symbolTable.getInLocal(lexer.getStringValue());
            //Verifica se ela já existia
            if(aux == null){
                symbolTable.putInLocal(lexer.getStringValue(), v);
                localVariableList.addElement(v);
            }
            else signalError.showError("Variable " + lexer.getStringValue() + "is being redeclared");
			lexer.nextToken();
		}
		return localVariableList;
	}

	private CompositeStatement compositeStatement() {
		lexer.nextToken();
        CompositeStatement compositeStatement = new CompositeStatement();
        compositeStatement.setStatementList(statementList());
		if ( lexer.token != Symbol.RIGHTCURBRACKET )
			signalError.showError("} expected");
		else
			lexer.nextToken();
        return compositeStatement;
	}
	/*
	 * retorne true se 'name' é uma classe declarada anteriormente. É necessário
	 * fazer uma busca na tabela de símbolos para isto.
	 */
	private boolean isType(String name) {
		return this.symbolTable.getInGlobal(name) != null;
	}

	/*
	 * AssignExprLocalDec ::= Expression [ ``$=$'' Expression ] | LocalDec
	 */
	//Mudei o tipo de retorno para um STMT. Antes era um Expr
    //Se for só declaracao, a parte direita e esquerda vai ser null
    //Se for a parte de expressao, a var List vai estar null
    //Fazer verificacao quando for gerar codigo
	private AssignStatement assignExprLocalDec() {
        LocalVariableList varList = null;
        Expr left = null;
        Expr right = null;
		if ( lexer.token == Symbol.INT || lexer.token == Symbol.BOOLEAN
				|| lexer.token == Symbol.STRING ||
				// token é uma classe declarada textualmente antes desta
				// instrução
				(lexer.token == Symbol.IDENT && isType(lexer.getStringValue())) ) {
			/*
			 * uma declaração de variável. 'lexer.token' é o tipo da variável
			 * 
			 * AssignExprLocalDec ::= Expression [ ``$=$'' Expression ] | LocalDec 
			 * LocalDec ::= Type IdList ``;''
			 */
			varList = localDec();
		}
		else {
			/*
			 * AssignExprLocalDec ::= Expression [ ``$=$'' Expression ]
			 */
			left = expr();
			if ( lexer.token == Symbol.ASSIGN ) {
				lexer.nextToken();
				right = expr();
				if ( lexer.token != Symbol.SEMICOLON )
					signalError.showError("';' expected", true);
				else
					lexer.nextToken();
			}
		}
		return new AssignStatement(varList,left, right);
	}

	private ExprList realParameters() {
		ExprList anExprList = null;

		if ( lexer.token != Symbol.LEFTPAR ) signalError.showError("( expected");
		lexer.nextToken();
		if ( startExpr(lexer.token) ) anExprList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.showError(") expected");
		lexer.nextToken();
		return anExprList;
	}

	private WhileStatement whileStatement() {
        Expr e;
        Statement stmt;
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.showError("( expected");
		lexer.nextToken();
		e = expr();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.showError(") expected");
		lexer.nextToken();
		stmt = statement();

        return new WhileStatement(e,stmt);
	}

	private IfStatement ifStatement() {
        Statement stmtIf, stmtElse;
        Expr e;
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.showError("( expected");
		lexer.nextToken();
		e = expr();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.showError(") expected");
		lexer.nextToken();
		stmtIf = statement();

        IfStatement ifStatement = new IfStatement(stmtIf, e);
		if ( lexer.token == Symbol.ELSE ) {
			lexer.nextToken();
			stmtElse = statement();
            ifStatement.setStmtElse(stmtElse);
		}

		return ifStatement;
	}

	private ReturnStatement returnStatement() {
        Expr e;
		lexer.nextToken();
		e = expr();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();

        return new ReturnStatement(e);
	}

	private ReadStatement readStatement() {
        VariableList varList = new VariableList();
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.showError("( expected");
		lexer.nextToken();
		while (true) {
			if ( lexer.token == Symbol.THIS ) {
				lexer.nextToken();
				if ( lexer.token != Symbol.DOT ) signalError.showError(". expected");
				lexer.nextToken();
			}
			if ( lexer.token != Symbol.IDENT )
				signalError.show(ErrorSignaller.ident_expected);

			String name = lexer.getStringValue();
            //Fazer verificacoes --- recuperar variavel.

			lexer.nextToken();
			if ( lexer.token == Symbol.COMMA )
				lexer.nextToken();
			else
				break;
		}

		if ( lexer.token != Symbol.RIGHTPAR ) signalError.showError(") expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();

        return new ReadStatement(varList);
	}

	private WriteStatement writeStatement() {
        ExprList exprList;
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.showError("( expected");
		lexer.nextToken();
		exprList = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.showError(") expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();
        return new WriteStatement(exprList);
	}

	private WritelnStatement writelnStatement(){
        ExprList e;
		lexer.nextToken();
		if ( lexer.token != Symbol.LEFTPAR ) signalError.showError("( expected");
		lexer.nextToken();
		e = exprList();
		if ( lexer.token != Symbol.RIGHTPAR ) signalError.showError(") expected");
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();
        return new WritelnStatement(e);
	}

	private BreakStatement breakStatement() {
        BreakStatement breakstmt = new BreakStatement();
		lexer.nextToken();
		if ( lexer.token != Symbol.SEMICOLON )
			signalError.show(ErrorSignaller.semicolon_expected);
		lexer.nextToken();

        return breakstmt;
	}

	private NullStatement nullStatement() {
        lexer.nextToken();
        return new NullStatement();
	}

	private ExprList exprList() {
		// ExpressionList ::= Expression { "," Expression }

		ExprList anExprList = new ExprList();
		anExprList.addElement(expr());
		while (lexer.token == Symbol.COMMA) {
			lexer.nextToken();
			anExprList.addElement(expr());
		}
		return anExprList;
	}

	private Expr expr() {

		Expr left = simpleExpr();
		Symbol op = lexer.token;
		if ( op == Symbol.EQ || op == Symbol.NEQ || op == Symbol.LE
				|| op == Symbol.LT || op == Symbol.GE || op == Symbol.GT ) {
			lexer.nextToken();
			Expr right = simpleExpr();
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

	private Expr simpleExpr() {
		Symbol op;

		Expr left = term();
		while ((op = lexer.token) == Symbol.MINUS || op == Symbol.PLUS
				|| op == Symbol.OR) {
			lexer.nextToken();
			Expr right = term();
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

	private Expr term() {
		Symbol op;

		Expr left = signalFactor();
		while ((op = lexer.token) == Symbol.DIV || op == Symbol.MULT
				|| op == Symbol.AND) {
			lexer.nextToken();
			Expr right = signalFactor();
			left = new CompositeExpr(left, op, right);
		}
		return left;
	}

	private Expr signalFactor() {
		Symbol op;
		if ( (op = lexer.token) == Symbol.PLUS || op == Symbol.MINUS ) {
			lexer.nextToken();
			return new SignalExpr(op, factor());
		}
		else
			return factor();
	}

	/*
	 * Factor ::= BasicValue | "(" Expression ")" | "!" Factor | "null" |
	 *      ObjectCreation | PrimaryExpr
	 * 
	 * BasicValue ::= IntValue | BooleanValue | StringValue 
	 * BooleanValue ::=  "true" | "false" 
	 * ObjectCreation ::= "new" Id "(" ")" 
	 * PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"  | 
	 *                 Id  |
	 *                 Id "." Id | 
	 *                 Id "." Id "(" [ ExpressionList ] ")" |
	 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
	 *                 "this" | 
	 *                 "this" "." Id | 
	 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
	 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
	 */
	private Expr factor() {

		Expr anExpr;
		ExprList exprList;
		String messageName, id;

		switch (lexer.token) {
		// IntValue
		case LITERALINT:
			return literalInt();
			// BooleanValue
		case FALSE:
			lexer.nextToken();
			return LiteralBoolean.False;
			// BooleanValue
		case TRUE:
			lexer.nextToken();
			return LiteralBoolean.True;
			// StringValue
		case LITERALSTRING:
			String literalString = lexer.getLiteralStringValue();
			lexer.nextToken();
			return new LiteralString(literalString);
			// "(" Expression ")" |
		case LEFTPAR:
			lexer.nextToken();
			anExpr = expr();
			if ( lexer.token != Symbol.RIGHTPAR ) signalError.showError(") expected");
			lexer.nextToken();
			return new ParenthesisExpr(anExpr);

			// "null"
		case NULL:
			lexer.nextToken();
			return new NullExpr();
			// "!" Factor
		case NOT:
			lexer.nextToken();
			anExpr = expr();
			return new UnaryExpr(anExpr, Symbol.NOT);
			// ObjectCreation ::= "new" Id "(" ")"
		case NEW:
			lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.showError("Identifier expected");

			String className = lexer.getStringValue();
			/*
			 * // encontre a classe className in symbol table KraClass 
			 *      aClass = symbolTable.getInGlobal(className); 
			 *      if ( aClass == null ) ...
			 */

			lexer.nextToken();
			if ( lexer.token != Symbol.LEFTPAR ) signalError.showError("( expected");
			lexer.nextToken();
			if ( lexer.token != Symbol.RIGHTPAR ) signalError.showError(") expected");
			lexer.nextToken();
			/*
			 * return an object representing the creation of an object
			 */
			return null;
			/*
          	 * PrimaryExpr ::= "super" "." Id "(" [ ExpressionList ] ")"  | 
          	 *                 Id  |
          	 *                 Id "." Id | 
          	 *                 Id "." Id "(" [ ExpressionList ] ")" |
          	 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
          	 *                 "this" | 
          	 *                 "this" "." Id | 
          	 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
          	 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
			 */
		case SUPER:
			// "super" "." Id "(" [ ExpressionList ] ")"
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				signalError.showError("'.' expected");
			}
			else
				lexer.nextToken();
			if ( lexer.token != Symbol.IDENT )
				signalError.showError("Identifier expected");
			messageName = lexer.getStringValue();
			/*
			 * para fazer as confer�ncias sem�nticas, procure por 'messageName'
			 * na superclasse/superclasse da superclasse etc
			 */
			lexer.nextToken();
			exprList = realParameters();
			break;
		case IDENT:
			/*
          	 * PrimaryExpr ::=  
          	 *                 Id  |
          	 *                 Id "." Id | 
          	 *                 Id "." Id "(" [ ExpressionList ] ")" |
          	 *                 Id "." Id "." Id "(" [ ExpressionList ] ")" |
			 */

			String firstId = lexer.getStringValue();
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				// Id
				// retorne um objeto da ASA que representa um identificador
				return null;
			}
			else { // Id "."
				lexer.nextToken(); // coma o "."
				if ( lexer.token != Symbol.IDENT ) {
					signalError.showError("Identifier expected");
				}
				else {
					// Id "." Id
					lexer.nextToken();
					id = lexer.getStringValue();
					if ( lexer.token == Symbol.DOT ) {
						// Id "." Id "." Id "(" [ ExpressionList ] ")"
						/*
						 * se o compilador permite vari�veis est�ticas, � poss�vel
						 * ter esta op��o, como
						 *     Clock.currentDay.setDay(12);
						 * Contudo, se vari�veis est�ticas n�o estiver nas especifica��es,
						 * sinalize um erro neste ponto.
						 */
						lexer.nextToken();
						if ( lexer.token != Symbol.IDENT )
							signalError.showError("Identifier expected");
						messageName = lexer.getStringValue();
						lexer.nextToken();
						exprList = this.realParameters();

					}
					else if ( lexer.token == Symbol.LEFTPAR ) {
						// Id "." Id "(" [ ExpressionList ] ")"
						exprList = this.realParameters();
						/*
						 * para fazer as confer�ncias sem�nticas, procure por
						 * m�todo 'ident' na classe de 'firstId'
						 */
					}
					else {
						// retorne o objeto da ASA que representa Id "." Id
					}
				}
			}
			break;
		case THIS:
			/*
			 * Este 'case THIS:' trata os seguintes casos: 
          	 * PrimaryExpr ::= 
          	 *                 "this" | 
          	 *                 "this" "." Id | 
          	 *                 "this" "." Id "(" [ ExpressionList ] ")"  | 
          	 *                 "this" "." Id "." Id "(" [ ExpressionList ] ")"
			 */
			lexer.nextToken();
			if ( lexer.token != Symbol.DOT ) {
				// only 'this'
				// retorne um objeto da ASA que representa 'this'
				// confira se n�o estamos em um m�todo est�tico
				return null;
			}
			else {
				lexer.nextToken();
				if ( lexer.token != Symbol.IDENT )
					signalError.showError("Identifier expected");
				id = lexer.getStringValue();
				lexer.nextToken();
				// j� analisou "this" "." Id
				if ( lexer.token == Symbol.LEFTPAR ) {
					// "this" "." Id "(" [ ExpressionList ] ")"
					/*
					 * Confira se a classe corrente possui um m�todo cujo nome �
					 * 'ident' e que pode tomar os par�metros de ExpressionList
					 */
					exprList = this.realParameters();
				}
				else if ( lexer.token == Symbol.DOT ) {
					// "this" "." Id "." Id "(" [ ExpressionList ] ")"
					lexer.nextToken();
					if ( lexer.token != Symbol.IDENT )
						signalError.showError("Identifier expected");
					lexer.nextToken();
					exprList = this.realParameters();
				}
				else {
					// retorne o objeto da ASA que representa "this" "." Id
					/*
					 * confira se a classe corrente realmente possui uma
					 * vari�vel de inst�ncia 'ident'
					 */
					return null;
				}
			}
			break;
		default:
			signalError.showError("Expression expected");
		}
		return null;
	}

	private LiteralInt literalInt() {

		LiteralInt e = null;

		// the number value is stored in lexer.getToken().value as an object of
		// Integer.
		// Method intValue returns that value as an value of type int.
		int value = lexer.getNumberValue();
		lexer.nextToken();
		return new LiteralInt(value);
	}

	private static boolean startExpr(Symbol token) {

		return token == Symbol.FALSE || token == Symbol.TRUE
				|| token == Symbol.NOT || token == Symbol.THIS
				|| token == Symbol.LITERALINT || token == Symbol.SUPER
				|| token == Symbol.LEFTPAR || token == Symbol.NULL
				|| token == Symbol.IDENT || token == Symbol.LITERALSTRING;

	}

	private SymbolTable		symbolTable;
	private Lexer			lexer;
	private ErrorSignaller	signalError;
	private KraClass 		currentClass;
	private Method          currentMethod;
}
