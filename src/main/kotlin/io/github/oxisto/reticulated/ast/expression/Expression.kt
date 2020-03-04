package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.Node

abstract class Expression : Node() {

  fun asCall(): Call {
    return this as Call
  }

  abstract fun isCall(): Boolean
  abstract override fun toString(): String

}