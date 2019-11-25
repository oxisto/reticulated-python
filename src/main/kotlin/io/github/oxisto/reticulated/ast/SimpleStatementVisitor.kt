package io.github.oxisto.reticulated.ast

import io.github.oxisto.reticulated.ast.simple.ExpressionStatement
import io.github.oxisto.reticulated.ast.simple.SimpleStatement
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

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