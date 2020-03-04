package io.github.oxisto.reticulated.ast.simple

import io.github.oxisto.reticulated.ast.expression.Expression
import io.github.oxisto.reticulated.ast.simple.target.Target

class AssignmentExpression(
        val target: Target,
        val expression: Expression
) : SimpleStatement() {

  override fun isExpressionStatement(): Boolean {
    return false
  }

  override fun isAssignmentStatement(): Boolean {
    return false
  }

  override fun isImportStatement(): Boolean {
    return false
  }

}