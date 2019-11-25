package io.github.oxisto.reticulated.ast.expression

open class Literal<T>(val value: T?) : Atom() {

  override fun isCall(): Boolean {
    return false
  }

}