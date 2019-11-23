package com.github.oxisto.reticulated.ast.statement

import com.github.oxisto.reticulated.ast.simple.SimpleStatement

class StatementList(val statements: List<SimpleStatement>) : Statement() {

  override fun isCompoundStatement(): Boolean {
    return false
  }

  override fun isStatementList(): Boolean {
    return true
  }

}