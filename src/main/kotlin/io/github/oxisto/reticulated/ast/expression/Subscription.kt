package io.github.oxisto.reticulated.ast.expression

class Subscription(val primary: Primary, val expressionList: List<Expression>) : Primary() {

  override fun isCall(): Boolean {
    return false
  }

}