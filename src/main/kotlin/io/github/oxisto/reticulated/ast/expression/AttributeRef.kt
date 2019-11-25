package io.github.oxisto.reticulated.ast.expression

class AttributeRef(val primary: Primary, val id: Identifier) : Primary() {

  override fun isCall(): Boolean {
    return false
  }

}