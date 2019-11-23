package com.github.oxisto.reticulated.ast

class Identifier(val name: String) : Node() {

  override fun toString(): String {
    return "Identifier(name='$name')"
  }

}