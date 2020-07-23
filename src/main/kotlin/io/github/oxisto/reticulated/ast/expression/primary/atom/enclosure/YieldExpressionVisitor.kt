package io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure

import io.github.oxisto.reticulated.ast.Scope
import io.github.oxisto.reticulated.grammar.Python3BaseVisitor
import io.github.oxisto.reticulated.grammar.Python3Parser

class YieldExpressionVisitor(val scope: Scope) : Python3BaseVisitor<Enclosure>() {
  override fun visitYield_expr(ctx: Python3Parser.Yield_exprContext): Enclosure {
    // TODO: Implement Yield Expression visitor
    return super.visitYield_expr(ctx)
  }
}