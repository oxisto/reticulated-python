package io.github.oxisto.reticulated.ast.simple

import io.github.oxisto.reticulated.ast.Node

abstract class SimpleStatement : Node() {

  fun asExpressionStatement(): ExpressionStatement {
    return this as ExpressionStatement
  }

  fun asAssignmentStatement(): AssignmentExpression {
    return this as AssignmentExpression
  }

  fun asImportStatement(): ImportStatement {
    return this as ImportStatement
  }

  abstract fun isExpressionStatement(): Boolean
  abstract fun isAssignmentStatement(): Boolean
  abstract fun isImportStatement(): Boolean

}