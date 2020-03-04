package io.github.oxisto.reticulated.ast.simple

import io.github.oxisto.reticulated.ast.expression.Expression

class ExpressionStatement(val expression: Expression) : SimpleStatement() {

  override fun isExpressionStatement(): Boolean {
    return true
  }

  override fun isAssignmentStatement(): Boolean {
    return false
  }

  override fun isImportStatement(): Boolean {
    return false
  }

  override fun toString(): String {
    return "ExpressionStatement(expression=$expression)"
  }

}