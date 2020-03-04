package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.Node

class Argument(val expression: Expression) : Node() {

  init {
    expression.parent = this
  }

}