package io.github.oxisto.reticulated.ast.statement

import io.github.oxisto.reticulated.ast.Suite

/**
 * A compound statement contains a group of other statements.
 *
 * Reference: https://docs.python.org/3/reference/compound_stmts.html
 */
abstract class CompoundStatement(val suite: Suite) : Statement() {

  init {
    suite.parent = this
  }

  override fun isCompoundStatement(): Boolean {
    return true
  }

  override fun isStatementList(): Boolean {
    return false
  }

}