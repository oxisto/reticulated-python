package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.ast.expression.Expression
import com.github.oxisto.reticulated.ast.simple.ExpressionStatement
import com.github.oxisto.reticulated.ast.simple.SimpleStatement
import com.github.oxisto.reticulated.ast.statement.Statement
import com.github.oxisto.reticulated.grammar.Python3BaseVisitor
import com.github.oxisto.reticulated.grammar.Python3Parser

class SimpleStatementVisitor(val scope: Scope) : Python3BaseVisitor<SimpleStatement>() {

  override fun visitSimple_stmt(ctx: Python3Parser.Simple_stmtContext?): SimpleStatement {
    if (ctx == null) {
      throw EmptyContextException()
    }

    // not sure how to handle this
    val expression = ctx.getChild(0).accept(ExpressionVisitor(this.scope))

    return ExpressionStatement(expression)
  }


}