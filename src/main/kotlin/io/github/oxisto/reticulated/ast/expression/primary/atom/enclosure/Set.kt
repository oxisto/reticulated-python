package io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure

import io.github.oxisto.reticulated.ast.expression.Expression

class Set(val elts: kotlin.collections.List<Expression>) : Enclosure() {

  override fun toString(): String {
    return "Set(elts=$elts)"
  }
}
