package io.github.oxisto.reticulated.ast.expression

class Call(val primary: Primary, val argumentList: ArgumentList = ArgumentList()) : Primary() {

  init {
    primary.parent = this
    argumentList.parent = this
  }

}