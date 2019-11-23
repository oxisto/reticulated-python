package com.github.oxisto.reticulated.ast

import javax.swing.plaf.nimbus.State

class FunctionDefinition(val id: Identifier,
                         val parameterList: ParameterList = ParameterList(), suite: Suite) : CompoundStatement(suite) {

  init {
    id.parent = this
    parameterList.parent = this
  }

  // TODO: decorators

  override fun toString(): String {
    return "FunctionDefinition(id=$id, parameters=$parameterList)"
  }

}