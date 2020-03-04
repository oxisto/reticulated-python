package io.github.oxisto.reticulated.ast.expression

import io.github.oxisto.reticulated.ast.expression.argument.ArgumentList

class Call(val primary: Primary, val argumentList: ArgumentList = ArgumentList()) : Primary() {

  init {
    primary.parent = this
    argumentList.parent = this
  }

  override fun isCall(): Boolean {
    return true
  }

}