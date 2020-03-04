package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.simple.target.Target

class Subscription(val primary: Primary, val expressionList: List<Expression>) : Target, Primary() {

  override fun isCall(): Boolean {
    return false
  }

  override fun toString(): String {
    return "Subscription(primary=$primary [ expressionList=$expressionList ] )"
  }

}