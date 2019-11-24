package com.github.oxisto.reticulated.ast.statement

import com.github.oxisto.reticulated.ast.Container
import com.github.oxisto.reticulated.ast.Node

class ParameterList(val parameters: List<Parameter> = ArrayList()) : Node(), Container<Parameter> {

  init {
    for (child in parameters) {
      child.parent = this
    }
  }

  override val children get() = this.parameters

  override fun toString(): String {
    return "ParameterList(parameters=$parameters)"
  }

}