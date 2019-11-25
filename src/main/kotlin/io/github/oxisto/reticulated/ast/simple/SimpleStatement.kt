package io.github.oxisto.reticulated.ast.simple

import io.github.oxisto.reticulated.ast.Node

abstract class SimpleStatement : Node() {

  fun asExpressionStatement(): ExpressionStatement {
    return this as ExpressionStatement
  }

  abstract fun isExpressionStatement(): Boolean

}