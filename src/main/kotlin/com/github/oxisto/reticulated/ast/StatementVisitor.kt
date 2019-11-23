package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.ast.expression.Identifier
import com.github.oxisto.reticulated.ast.simple.SimpleStatement
import com.github.oxisto.reticulated.ast.statement.*
import com.github.oxisto.reticulated.grammar.Python3BaseVisitor
import com.github.oxisto.reticulated.grammar.Python3Parser
import org.antlr.v4.runtime.tree.TerminalNode

class StatementVisitor(val scope: Scope) : Python3BaseVisitor<Statement>() {


  override fun visitStmt(ctx: Python3Parser.StmtContext?): Statement {
    if (ctx == null) {
      throw EmptyContextException()
    }

    if (ctx.childCount == 1 && ctx.getChild(0) is Python3Parser.Compound_stmtContext) {
      // its a compound statement
      return ctx.getChild(0).accept(this)
    } else {
      // create a statement list
      val list = ArrayList<SimpleStatement>()

      // loop through children
      for (tree in ctx.children) {
        list.add(tree.accept(SimpleStatementVisitor(this.scope)))
      }

      return StatementList(list)
    }
  }

  override fun visitFuncdef(ctx: Python3Parser.FuncdefContext?): Statement {
    if (ctx == null) {
      throw EmptyContextException()
    }

    // TODO: decorators

    // assume that the first child is 'def'

    // TODO: check, if result is really an identifier
    // second is the name
    val id = ctx.getChild(1)?.accept(IdentifierVisitor(this.scope)) ?: throw Exception("Empty function name")

    // create a new scope for this function
    val functionScope = Scope(this.scope, ScopeType.FUNCTION);

    // third is the parameter list
    val parameterList = ctx.getChild(2).accept(Visitor(functionScope)) as ParameterList;

    // forth is ':'

    // TODO: parse optional return type hint

    // last is the suite
    val suite = ctx.getChild(ctx.childCount-1).accept(Visitor(functionScope)) as Suite;

    // create a new function definition
    val def = FunctionDefinition(id, parameterList, Suite())

    return def;
  }


}