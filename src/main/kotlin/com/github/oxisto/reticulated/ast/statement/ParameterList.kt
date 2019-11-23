package com.github.oxisto.reticulated.ast.statement

import com.github.oxisto.reticulated.ast.Node

class ParameterList(val parameters: List<Parameter> = ArrayList()) : Node() {

  init {
    for (child in parameters) {
      child.parent = this
    }
  }

  override fun toString(): String {
    return "ParameterList(parameters=$parameters)"
  }

}