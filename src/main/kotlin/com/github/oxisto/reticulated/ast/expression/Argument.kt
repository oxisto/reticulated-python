package com.github.oxisto.reticulated.ast.expression

import com.github.oxisto.reticulated.ast.Node

class Argument(val expression: Expression) : Node() {

  init {
    expression.parent = this
  }

}