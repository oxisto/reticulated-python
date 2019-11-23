package com.github.oxisto.reticulated.ast

class FunctionDefinition(val id: Identifier) : CompoundStatement() {

  // TODO: decorators

  var parameters: ArrayList<String> = ArrayList();

  override fun toString(): String {
    return "FunctionDefinition(id=$id, parameters=$parameters)"
  }

}