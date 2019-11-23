package com.github.oxisto.reticulated.ast

class ParameterList(val children: List<Identifier> = ArrayList()) : Node() {

  init {
    for (child in children) {
      child.parent = this
    }
  }

  override fun toString(): String {
    return "ParameterList(children=$children)"
  }

}