package com.github.oxisto.reticulated.ast.expression

import com.github.oxisto.reticulated.ast.expression.Atom
import com.github.oxisto.reticulated.ast.expression.Primary

open class Identifier(val name: String) : Atom() {

  override fun toString(): String {
    return "Identifier(name='$name')"
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Identifier

    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    return name.hashCode()
  }

}