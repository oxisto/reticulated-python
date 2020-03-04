package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.simple.target.Target

class AttributeRef(val primary: Primary, val id: Identifier) : Target, Primary() {

  override fun isCall(): Boolean {
    return false
  }

  override fun toString(): String {
    return "AttributeRef(primary=$primary\".\"identifier=$id)"
  }

}