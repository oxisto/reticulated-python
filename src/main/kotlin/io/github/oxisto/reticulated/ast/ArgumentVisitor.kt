package io.github.oxisto.reticulated.ast

import io.github.oxisto.reticulated.ast.expression.Argument
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class ArgumentVisitor(val scope: Scope) : Python3BaseVisitor<Argument>() {

  override fun visitArgument(ctx: Python3Parser.ArgumentContext?): Argument {
    if (ctx == null) {
      throw EmptyContextException()
    }

    val expression = ctx.getChild(0).accept(ExpressionVisitor(this.scope))

    return Argument(expression)
  }

}