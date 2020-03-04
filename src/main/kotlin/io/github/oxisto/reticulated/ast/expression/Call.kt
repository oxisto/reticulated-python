package io.github.oxisto.reticulated.ast.expression

class Call(val primary: Primary, val callTrailer: CallTrailer) : Primary() {

  init {
    primary.parent = this
    callTrailer.parent = this
  }

  override fun isCall(): Boolean {
    return true
  }

  override fun toString(): String {
    return "Call(primary=$primary callTrailer=$callTrailer)"
  }

}