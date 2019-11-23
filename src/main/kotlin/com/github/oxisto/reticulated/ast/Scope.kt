package com.github.oxisto.reticulated.ast

import com.github.oxisto.reticulated.ast.expression.Identifier

enum class ScopeType
{
  GLOBAL,
  FUNCTION,
  BLOCK
}

class Scope(val parent: Scope? = null, val type: ScopeType = ScopeType.GLOBAL) {

  val variables = ArrayList<Identifier>()

  fun addVariable(variable: Identifier) {
    this.variables += variable

    println("Added variable %s to %s scope".format(variable, this.type))
  }

}