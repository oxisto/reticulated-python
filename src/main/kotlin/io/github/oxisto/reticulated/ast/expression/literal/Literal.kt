package io.github.oxisto.reticulated.ast.expression.literal

import io.github.oxisto.reticulated.ast.expression.Atom

abstract class Literal<T>() : Atom() {

  override fun isCall(): Boolean {
    return false
  }

  abstract override fun toString(): String
}