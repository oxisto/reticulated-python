package io.github.oxisto.reticulated.ast

import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.simple.SimpleStatement
import io.github.oxisto.reticulated.ast.statement.*
import io.github.oxisto.reticulated.ast.statement.FunctionDefinition
import io.github.oxisto.reticulated.ast.statement.ParameterList
import io.github.oxisto.reticulated.ast.statement.Statement
import io.github.oxisto.reticulated.ast.statement.StatementList
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class StatementVisitor(val scope: Scope) : Python3BaseVisitor<Statement>() {

  override fun visitStmt(ctx: Python3Parser.StmtContext?): Statement {
    if (ctx == null) {
      throw EmptyContextException()
    }

    return if (ctx.childCount == 1 && ctx.getChild(0) is Python3Parser.Compound_stmtContext) {
      // its a compound statement
      ctx.getChild(0).accept(this)
    } else {
      // create a statement list
      val list = ArrayList<SimpleStatement>()

      // loop through children
      for (tree in ctx.children) {
        list.add(tree.accept(SimpleStatementVisitor(this.scope)))
      }

      StatementList(list)
    }
  }

  override fun visitFuncdef(ctx: Python3Parser.FuncdefContext?): Statement {
    if (ctx == null) {
      throw EmptyContextException()
    }

    // TODO: decorators

    // assume that the first child is 'def'

    // second is the name
    val id = ctx.getChild(1).accept(IdentifierVisitor(this.scope))

    // create a new scope for this function
    val functionScope = Scope(this.scope, ScopeType.FUNCTION);

    // third is the parameter list
    val parameterList = ctx.getChild(2).accept(Visitor(functionScope)) as ParameterList;

    // forth is ':' or '->'
    val op = ctx.getChild(3)
    var expression: Expression? = null
    if (op.text == "->") {
      // fifth is the optional type hint
      expression = ctx.getChild(4).accept(ExpressionVisitor(functionScope))
    }

    // last is the suite
    val suite = ctx.getChild(ctx.childCount - 1).accept(Visitor(functionScope)) as Suite;

    // create a new function definition
    val def = FunctionDefinition(id, parameterList, suite, expression)

    // add it to the function scope
    functionScope.handleParameterList(parameterList)

    return def;
  }

}