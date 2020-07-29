package io.github.oxisto.reticulated.ast.expression.comparison

import io.github.oxisto.reticulated.ast.expression.booleanops.BaseBooleanOp

/**
 * This class is the base for all comparisons.
 */
abstract class BaseComparison : BaseBooleanOp() {
  abstract override fun toString(): String
}