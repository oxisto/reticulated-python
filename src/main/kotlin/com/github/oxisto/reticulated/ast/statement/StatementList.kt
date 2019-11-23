package com.github.oxisto.reticulated.ast.statement

import com.github.oxisto.reticulated.ast.simple.SimpleStatement

/**
 * A list of simple statements.
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html#grammar-token-stmt-list
 */
class StatementList(val statements: List<SimpleStatement>) : Statement() {

  override fun isCompoundStatement(): Boolean {
    return false
  }

  override fun isStatementList(): Boolean {
    return true
  }

}