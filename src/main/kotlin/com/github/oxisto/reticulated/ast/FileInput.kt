package com.github.oxisto.reticulated.ast;

import java.util.ArrayList;

class FileInput(val statements: ArrayList<Statement> = ArrayList()) : Node() {

  init {
    for(stmt in statements) {
      stmt.parent = this
    }
  }

  override fun toString(): String {
    return "FileInput(statements=$statements)"
  }

}
