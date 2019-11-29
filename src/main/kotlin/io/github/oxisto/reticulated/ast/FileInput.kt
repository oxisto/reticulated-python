package io.github.oxisto.reticulated.ast;

import io.github.oxisto.reticulated.ast.statement.Statement
import java.util.*

class FileInput(val statements: ArrayList<Statement> = ArrayList()) : Node() {

  init {
    for (stmt in statements) {
      stmt.parent = this
    }
  }

  override fun toString(): String {
    return "FileInput(statements=$statements)"
  }

}
