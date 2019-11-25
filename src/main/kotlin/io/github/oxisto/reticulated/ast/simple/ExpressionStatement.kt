package io.github.oxisto.reticulated.ast.simple

import io.github.oxisto.reticulated.ast.expression.Expression

class ExpressionStatement(val expression: Expression) : SimpleStatement() {

  override fun isExpressionStatement(): Boolean {
    return true
  }

}