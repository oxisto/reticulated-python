package io.github.oxisto.reticulated.ast.expression

class ConditionalExpression : Expression() {

  override fun isCall(): Boolean {
    return false
  }

}