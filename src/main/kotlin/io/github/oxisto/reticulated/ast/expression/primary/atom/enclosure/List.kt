package io.github.oxisto.reticulated.ast.expression.primary.atom.enclosure

import io.github.oxisto.reticulated.ast.Container
import io.github.oxisto.reticulated.ast.expression.Expression

class List(val elts: kotlin.collections.List<Expression>) : Enclosure(), Container<Expression> {

  override fun toString(): String {
    return "List(elts=$elts)"
  }

  override val children: List<Expression>
    get() = elts
}
