//Angela Rodrigues Ferreira 552070
//Charles David de Moraes 489662
package ast;

import java.util.*;
import comp.CompilationError;

public class Program {

	public Program(ArrayList<KraClass> classList, ArrayList<MetaobjectCall> metaobjectCallList, 
			       ArrayList<CompilationError> compilationErrorList) {
		this.classList = classList;
		this.metaobjectCallList = metaobjectCallList;
		this.compilationErrorList = compilationErrorList;
	}


	public void genKra(PW pw) {
		for (KraClass kraClass : classList) {
			kraClass.genKra(pw);
			pw.println("");
		}
	}

	public void genC(PW pw) {
		pw.println("#include <stdlib.h>");
//		pw.println("#include <malloc.h>");
		pw.println("#include <stdio.h>");

		pw.println("");
		pw.println("typedef int boolean;");
		pw.println("#define true 1");
		pw.println("#define false 0");

		pw.println("");
		pw.println("typedef");
		pw.add();
		pw.printlnIdent("void (*Func)();");
		pw.sub();

		pw.println("");
		for (KraClass kraClass : classList) {
			kraClass.genC(pw);
			pw.printlnIdent("");
		}

		pw.add();
		pw.println("int main() {");
		pw.printlnIdent("_class_Program *program;");

		pw.println("");
		pw.printlnIdent("program = new_Program();");
		pw.printlnIdent("( ( void (*)(_class_Program *) ) program->vt[0] )(program);");
		pw.printlnIdent("return 0;");
		pw.sub();
		pw.println("}");
	}
	
	public ArrayList<KraClass> getClassList() {
		return classList;
	}


	public ArrayList<MetaobjectCall> getMetaobjectCallList() {
		return metaobjectCallList;
	}
	

	public boolean hasCompilationErrors() {
		return compilationErrorList != null && compilationErrorList.size() > 0 ;
	}

	public ArrayList<CompilationError> getCompilationErrorList() {
		return compilationErrorList;
	}

	
	private ArrayList<KraClass> classList;
	private ArrayList<MetaobjectCall> metaobjectCallList;
	
	ArrayList<CompilationError> compilationErrorList;

	
}