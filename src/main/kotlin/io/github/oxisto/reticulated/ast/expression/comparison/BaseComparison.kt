package io.github.oxisto.reticulated.ast.expression.comparison

import io.github.oxisto.reticulated.ast.expression.booleanops.BaseBooleanOp

abstract class BaseComparison : BaseBooleanOp() {
  abstract override fun toString(): String
}