package com.github.oxisto.reticulated.ast.expression

import com.github.oxisto.reticulated.ast.expression.Atom
import com.github.oxisto.reticulated.ast.expression.Primary

class Identifier(val name: String) : Atom() {

  override fun toString(): String {
    return "Identifier(name='$name')"
  }

}