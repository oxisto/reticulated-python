package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.Container
import io.github.oxisto.reticulated.ast.simple.SimpleStatement

/**
 * A list of simple statements, usually on the same line, separated by semicolon.
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html#grammar-token-stmt-list
 */
class StatementList(val statements: List<SimpleStatement>) : Statement(), Container<SimpleStatement> {

  override fun isCompoundStatement(): Boolean {
    return false
  }

  override fun isStatementList(): Boolean {
    return true
  }

  override val children get() = this.statements

}